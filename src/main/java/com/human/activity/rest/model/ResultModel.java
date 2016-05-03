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

    private String activity;

    public ResultModel() {
    }

    public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}


}