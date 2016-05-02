package com.human.activity.server.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;

public class PredictWorker {

	private static PredictWorker prediectWorker;

	private PredictWorker() {

	}

	public static PredictWorker getInstance() {
		if (prediectWorker == null)
			prediectWorker = new PredictWorker();
		return prediectWorker;
	}

	@Autowired
	private CassandraOperations cassandraTemplate;

	public void startWorker() {
		PredictActivity predictActivity = new PredictActivity(this.cassandraTemplate);

		Thread predictThread = new Thread(predictActivity);
		predictThread.start();
	}

	public CassandraOperations getCassandraTemplate() {
		return cassandraTemplate;
	}

	public void setCassandraTemplate(CassandraOperations cassandraTemplate) {
		this.cassandraTemplate = cassandraTemplate;
	}

}
