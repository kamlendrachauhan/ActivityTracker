package com.human.activity.server.job;

public class PredictWorker {

	public static void startWorker() {
		PredictActivity predictActivity = new PredictActivity();

		Thread predictThread = new Thread(predictActivity);
		predictThread.start();
	}
}
