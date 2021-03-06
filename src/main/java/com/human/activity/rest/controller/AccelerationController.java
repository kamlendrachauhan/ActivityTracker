package com.human.activity.rest.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.human.activity.rest.model.Acceleration;
import com.human.activity.rest.model.AccelerationModel;
import com.human.activity.rest.model.Result;

@RestController
@RequestMapping("/acceleration")
public class AccelerationController {

	private static final Logger log = LoggerFactory.getLogger(AccelerationController.class);
	@Autowired
	private CassandraOperations cassandraTemplate;

	@RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<?> newAcceleration(@RequestBody @Valid AccelerationModel accelerationModel) {
	
		Acceleration acceleration = new Acceleration(accelerationModel);

		if (log.isInfoEnabled()) {
			log.info("/POST /acceleration with values {}", acceleration);
		}

		cassandraTemplate.insert(acceleration);

		return status(CREATED).build();
		
	}

	@RequestMapping(method = RequestMethod.GET, produces = TEXT_HTML_VALUE)
	public ResponseEntity<String> getLastPrediction() {
		Result prediction = cassandraTemplate
				.select("select * from result where user_id='TEST_USER' limit 1", Result.class).get(0);

		StringBuilder builder = new StringBuilder("<header><META HTTP-EQUIV=\"refresh\" CONTENT=\"5\"></header>");
		builder.append(
				"<body><div>*****************************************************************************************</div>");
		builder.append("<div>            Predicted activity = " + prediction.getPrediction() + "</div>");
		builder.append(
				"<div>*****************************************************************************************</div>");
		builder.append(
				"<div>*****************************************************************************************</div><body>");

		ResponseEntity<String> response = new ResponseEntity<String>(builder.toString(), FOUND);

		return response;
	}

	@RequestMapping(value = "/prediction", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getLastestPrediction() {
		Result prediction = cassandraTemplate
				.select("select * from result where user_id='TEST_USER' order by timestamp desc limit 1", Result.class)
				.get(0);

		ResponseEntity<String> response = new ResponseEntity<String>(prediction.getPrediction(), HttpStatus.OK);

		return response;
	}

	@RequestMapping(value = "/summary", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getSummaryOfPrediction() {
		Select select = QueryBuilder.select("group_and_count(prediction)").from("activityrecognition", "result");
		select.where(QueryBuilder.eq("user_id", "TEST_USER"));
		select.limit(1);

		String result = cassandraTemplate.queryForObject(select, String.class);

		ResponseEntity<String> response = new ResponseEntity<String>(result, HttpStatus.OK);

		return response;
	}

	public CassandraOperations getCassandraTemplate() {
		return cassandraTemplate;
	}

	public void setCassandraTemplate(CassandraOperations cassandraTemplate) {
		this.cassandraTemplate = cassandraTemplate;
	}

}
