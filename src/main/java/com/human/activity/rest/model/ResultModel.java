package com.human.activity.rest.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.data.cassandra.mapping.Table;


@Table
public class ResultModel implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
    private Long timestamp;

    private String prediction;

    public ResultModel() {
    }

    public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getPrediction() {
        return prediction;
    }

    public void setPrediction(String prediction) {
        this.prediction = prediction;
    }
}