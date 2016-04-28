package com.human.activity.rest.model;

import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import com.human.activity.rest.model.UserTimestamp;

import java.io.Serializable;


@Table
public class Result implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PrimaryKey
    private UserTimestamp userTimestamp;

    private String prediction;

    public Result() {
    }

    public UserTimestamp getUserTimestamp() {
        return userTimestamp;
    }

    public void setUserTimestamp(UserTimestamp userTimestamp) {
        this.userTimestamp = userTimestamp;
    }

    public String getPrediction() {
        return prediction;
    }

    public void setPrediction(String prediction) {
        this.prediction = prediction;
    }
}