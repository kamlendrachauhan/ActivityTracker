package com.human.activity.rest.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.human.activity.server.job.PredictWorker;

@Configuration
@ComponentScan({ "com.human.activity.rest", "com.human.activity.server" })
@EnableAutoConfiguration
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

		// RecognizeActivity.main();
		// To start the execution of Prediction Activity
		PredictWorker instance = PredictWorker.getInstance();
		instance.startWorker();

	}

}
