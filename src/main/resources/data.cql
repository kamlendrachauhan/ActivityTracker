CREATE KEYSPACE activityrecognition WITH replication = {'class':'SimpleStrategy', 'replication_factor':1};

USE activityrecognition;

CREATE TABLE acceleration (
  user_id text,
  timestamp bigint,
  x double,
  y double,
  z double,
  PRIMARY KEY (user_id, timestamp)
);

CREATE TABLE training (
  user_id text,
  activity text,
  timestamp bigint,
  x double,
  y double,
  z double,
  PRIMARY KEY ((user_id, activity), timestamp)
);

CREATE TABLE trainingAcceleration (
  userID text,
  activity text,
  timestamp bigint,
  x double,
  y double,
  z double,
  PRIMARY KEY ((userid, activity), timestamp)
);

CREATE TABLE result (
  user_id text,
  timestamp bigint,
  prediction text,
  PRIMARY KEY (user_id, timestamp)
);

