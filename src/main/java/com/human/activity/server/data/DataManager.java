package com.human.activity.server.data;

import org.apache.spark.api.java.JavaRDD;

import com.datastax.spark.connector.japi.CassandraRow;
import com.human.activity.util.Constants;

public class DataManager {

	public static JavaRDD<double[]> toDouble(JavaRDD<CassandraRow> data) {

		// first transform CassandraRDD into a RDD<Map>
		return data.map(CassandraRow::toMap)
				// then build a double array from the RDD<Map>
				.map(entry -> new double[] { (double) entry.get(Constants.ACC_X), (double) entry.get(Constants.ACC_Y),
						(double) entry.get(Constants.ACC_Z) });
	}

	public static JavaRDD<long[]> withTimestamp(JavaRDD<CassandraRow> data) {

		// first transform CassandraRDD into a RDD<Map>
		return data.map(CassandraRow::toMap)
				// then build a double array from the RDD<Map>
				.map(entry -> new long[] { (long) entry.get("timestamp"),
						((Double) entry.get(Constants.ACC_Y)).longValue() });
	}

}
