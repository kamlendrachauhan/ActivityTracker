package com.human.activity.rest.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.human.activity.server.job.PredictWorker;
import com.human.activity.server.job.RecognizeActivity;

@Configuration
@ComponentScan({ "com.human.activity.rest", "com.human.activity.server" })
@EnableAutoConfiguration
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

		if (args.length > 0) {
			System.out.println("Choosen Activity Type : "+args[0]);
			if ("TrainModel".equalsIgnoreCase(args[0])) {
				RecognizeActivity.main();
			} else {
				// To start the execution of Prediction Activity
				PredictWorker instance = PredictWorker.getInstance();
				instance.startWorker();
			}
		} else {
			// To start the execution of Prediction Activity
			PredictWorker instance = PredictWorker.getInstance();
			instance.startWorker();

		}
	}

}
