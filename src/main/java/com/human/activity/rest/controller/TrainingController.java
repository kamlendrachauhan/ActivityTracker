package com.human.activity.rest.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.human.activity.rest.model.Result;
import com.human.activity.rest.model.ResultModel;
import com.human.activity.rest.model.TrainingAcceleration;
import com.human.activity.rest.model.TrainingAccelerationModel;
import com.human.activity.rest.model.UserTimestamp;

@RestController
@RequestMapping("/training")
public class TrainingController {

	private static final Logger log = LoggerFactory.getLogger(TrainingController.class);

	@Autowired
	private CassandraOperations cassandraTemplate;

	@RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<?> newAcceleration(@RequestBody @Valid TrainingAccelerationModel trainingAccelerationModel) {

		if (log.isInfoEnabled()) {
			log.info("/POST /training with values {}", trainingAccelerationModel);
		}

		TrainingAcceleration trainingAcceleration = new TrainingAcceleration(trainingAccelerationModel);
		cassandraTemplate.insert(trainingAcceleration);

		return status(CREATED).build();
	}

	@RequestMapping(value = "/result", method = POST, consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<?> newResult(@RequestBody @Valid ResultModel resultModel) {

		if (log.isInfoEnabled()) {
			log.info("/POST /training with values {}", resultModel);
		}

		Result result = new Result();
		UserTimestamp userTimestamp = new UserTimestamp();
		userTimestamp.setTimestamp(resultModel.getTimestamp());
		userTimestamp.setUser_id("TEST_USER");
		result.setPrediction(resultModel.getActivity());
		result.setUserTimestamp(userTimestamp);

		cassandraTemplate.insert(result);

		return status(CREATED).build();
	}

	public CassandraOperations getCassandraTemplate() {
		return cassandraTemplate;
	}

	public void setCassandraTemplate(CassandraOperations cassandraTemplate) {
		this.cassandraTemplate = cassandraTemplate;
	}

}
