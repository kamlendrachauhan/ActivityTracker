package com.human.activity.server.job;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;
import static com.human.activity.server.data.ExtractFeature.computeAvgAbsDifference;
import static com.human.activity.server.data.ExtractFeature.computeResultantAcc;

import java.util.ArrayList;
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
		String predictedActivity = predict(this.sc);

		Result result = new Result();
		UserTimestamp userTimestamp = new UserTimestamp();
		userTimestamp.setTimestamp(System.currentTimeMillis());
		userTimestamp.setUser_id("TEST_USER");
		result.setUserTimestamp(userTimestamp);
		System.out.println("----------- Activity  ---------" + predictedActivity);
		result.setPrediction(predictedActivity);

		List<Result> listOfResult = new ArrayList<>();
		listOfResult.add(result);

		if (this.cassandraTemplate == null) {
			Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").withPort(9042).build();
			Session session = cluster.connect("activityrecognition");
			CassandraOperations cassandraOps = new CassandraTemplate(session);
			this.cassandraTemplate = cassandraOps;

		}
		cassandraTemplate.insert(result);
	}

	public static synchronized String main() {

		// Persist to the result table

		// JavaRDD<Result> resultRDD = sc.parallelize(listOfResult);
		// javaFunctions(resultRDD).writerBuilder("activityrecognition",
		// "result", mapToRow(Result.class))
		// .saveToCassandra();

		// timer.schedule(new ResultCalculatorTask(),
		// Constants.NUMBER_OF_SECONDS_DELAY);
		return "";
	}

	public static double predict_Old(JavaSparkContext sc) {

		DecisionTreeModel model = DecisionTreeModel.load(sc.sc(), "activityrecognition");

		double[] feature = { 3.3809183673469394, -6.880102040816324, 0.8790816326530612, 50.08965378708187,
				84.13105050494424, 20.304453787081833, 5.930491461890875, 7.544194085797583, 3.519248229904206,
				12.968485972481643, 7.50031E8 };

		Vector sample = Vectors.dense(feature);
		double prediction = model.predict(sample);

		return prediction;

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

	private static Vector computeFeature(JavaSparkContext sc) {

		double[] features = new double[11];

		// retrieve data from Cassandra and create an CassandraRDD
		CassandraJavaRDD<CassandraRow> cassandraRowsRDD = javaFunctions(sc).cassandraTable("activityrecognition",
				"acceleration");

		// user ID is hard coded in REST API app
		JavaRDD<CassandraRow> data = cassandraRowsRDD.select("timestamp", "x", "y", "z").where("user_id=?", "TEST_USER")
				.withDescOrder().limit(100l); // load the last 100 acceleration.

		if (data.count() > 0) {
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
		}
		return Vectors.dense(features);
	}

	@Override
	public void run() {
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
