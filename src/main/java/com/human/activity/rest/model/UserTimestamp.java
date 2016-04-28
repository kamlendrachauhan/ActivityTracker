package com.human.activity.rest.model;


import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@PrimaryKeyClass
public class UserTimestamp implements Serializable{


    /**
	 * 
	 */
	private static final long serialVersionUID = -5519611500865844488L;

	@PrimaryKeyColumn(ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private String user_id;

    @NotNull
    private Long timestamp;


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
