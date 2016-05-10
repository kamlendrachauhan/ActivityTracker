package com.human.activity.server.job;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;
import static com.human.activity.server.data.ExtractFeature.computeAvgAbsDifference;
import static com.human.activity.server.data.ExtractFeature.computeResultantAcc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.tree.model.DecisionTreeModel;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.spark.connector.japi.CassandraRow;
import com.datastax.spark.connector.japi.rdd.CassandraJavaRDD;
import com.human.activity.rest.model.Result;
import com.human.activity.rest.model.UserTimestamp;
import com.human.activity.server.data.DataManager;
import com.human.activity.server.data.ExtractFeature;
import com.human.activity.util.Constants;

public class PredictActivity implements Runnable {

	private CassandraOperations cassandraTemplate;
	private SparkConf sparkConf;
	private JavaSparkContext sc;
	private static DecisionTreeModel model;
	private static Timer timer;
	private static Session session;
	private static boolean isDataAvailable;

	public PredictActivity() {

	}

	public PredictActivity(CassandraOperations cassandraOperations) {
		sparkConf = new SparkConf().setAppName("User's physical activity recognition")
				.set("spark.cassandra.connection.host", "127.0.0.1").setMaster("local[*]");

		sc = new JavaSparkContext(sparkConf);
		model = DecisionTreeModel.load(sc.sc(), "activityrecognition");

		this.cassandraTemplate = cassandraOperations;
	}

	public void startPrediction() {
		try {
			String predictedActivity = predict(this.sc);
			if (isDataAvailable) {
				Result result = new Result();
				UserTimestamp userTimestamp = new UserTimestamp();
				userTimestamp.setTimestamp(System.currentTimeMillis());
				userTimestamp.setUser_id("TEST_USER");
				result.setUserTimestamp(userTimestamp);
				System.out.println("----------- Activity  ---------> " + predictedActivity);
				result.setPrediction(predictedActivity);

				List<Result> listOfResult = new ArrayList<>();
				listOfResult.add(result);

				if (this.cassandraTemplate == null) {
					Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").withPort(9042).build();
					session = cluster.connect("activityrecognition");
					CassandraOperations cassandraOps = new CassandraTemplate(session);
					this.cassandraTemplate = cassandraOps;

				}
				cassandraTemplate.insert(result);
			}
		} catch (Exception ex) {
			System.out.println("Error occured " + ex.getMessage());
		}
	}

	public static String predict(JavaSparkContext sc) {

		// load the defined model

		Vector feature = computeFeature(sc);

		double prediction = model.predict(feature);

		String result = "No activity predicted";

		switch ((int) prediction) {
		case 0:
			result = "Walking";
			break;
		case 1:
			result = "Jogging";
			break;
		case 2:
			result = "Standing";
			break;
		case 3:
			result = "Sitting";
			break;
		case 4:
			result = "Upstairs";
			break;
		case 5:
			result = "Downstairs";
			break;
		}

		return result;

	}

	public static double predictOld(JavaSparkContext sc) {

		DecisionTreeModel model = DecisionTreeModel.load(sc.sc(), "activityrecognition");

		double[] feature = { -0.9689999999999999, 9.449899999999996, -0.17310000000000003, 0.03594040404040402,
				0.007996959595959599, 0.009480191919191925, 0.12651999999999997, 0.06350400000000028,
				0.07619000000000002, 9.503374874590646, 5.00010505050505E7 };

		Vector sample = Vectors.dense(feature);
		double prediction = model.predict(sample);

		return prediction;

	}

	private static Vector computeFeature(JavaSparkContext sc) {

		double[] features = new double[11];

		// retrieve data from Cassandra and create an CassandraRDD
		CassandraJavaRDD<CassandraRow> cassandraRowsRDD = javaFunctions(sc).cassandraTable("activityrecognition",
				"acceleration");

		// user ID is hard coded in REST API app
		JavaRDD<CassandraRow> data = cassandraRowsRDD.select("timestamp", "x", "y", "z").where("user_id=?", "TEST_USER")
				.withDescOrder().limit(300l); // load the last 100 acceleration.

		/*try {
			if (data.count() > 0) {
				String query = "TRUNCATE acceleration;";

				if (session == null || session.isClosed()) {
					Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").withPort(9042).build();
					session = cluster.connect("activityrecognition");
				}
				session.execute(query);
			}
		} catch (Exception exception) {
			System.out.println("ERROR ----- " + exception.getMessage());
		}*/
		if (data.count() > 0) {
			isDataAvailable = true;
			// transform into double array
			JavaRDD<double[]> doubles = DataManager.toDouble(data);
			// transform into vector without timestamp
			JavaRDD<Vector> vectors = doubles.map(Vectors::dense);
			// data with only timestamp and acc
			JavaRDD<long[]> timestamp = DataManager.withTimestamp(data);

			////////////////////////////////////////
			// extract features from this windows //
			////////////////////////////////////////
			ExtractFeature extractFeature = new ExtractFeature(vectors);

			// the average acceleration
			double[] mean = extractFeature.computeAvgAcc();

			// the variance
			double[] variance = extractFeature.computeVariance();

			// the average absolute difference
			double[] avgAbsDiff = computeAvgAbsDifference(doubles, mean);

			// the average resultant acceleration
			double resultant = computeResultantAcc(doubles);

			// the average time between peaks
			double avgTimePeak = extractFeature.computeAvgTimeBetweenPeak(timestamp);

			features = new double[] { mean[0], mean[1], mean[2], variance[0], variance[1], variance[2], avgAbsDiff[0],
					avgAbsDiff[1], avgAbsDiff[2], resultant, avgTimePeak };
			System.out.println("Features Calculated :: -------- > " + Arrays.toString(features));

		} else {
			// No data in database dont predict anything
			isDataAvailable = false;
		}
		return Vectors.dense(features);
	}

	@Override
	public void run() {
		System.out.println("-------------------------------> Predicted value : " + PredictActivity.predictOld(sc));
		

		timer = new Timer();
		timer.schedule(new ResultCalculatorTask(), Constants.NUMBER_OF_SECONDS_DELAY,
				Constants.NUMBER_OF_SECONDS_DELAY);
		PredictActivity predictA = new PredictActivity();
		predictA.startPrediction();
		// PredictActivity.main();
	}

	class ResultCalculatorTask extends TimerTask {

		@Override
		public void run() {
			System.out.println("Running Results Job");
			startPrediction();
		}

	}
}
