package com.human.activity.server.model;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.tree.RandomForest;
import org.apache.spark.mllib.tree.model.RandomForestModel;
import scala.Tuple2;

import java.util.HashMap;
import java.util.Map;

public class RandomForests {

  JavaRDD<LabeledPoint> trainingData;
  JavaRDD<LabeledPoint> testData;

  public RandomForests(JavaRDD<LabeledPoint> trainingData, JavaRDD<LabeledPoint> testData) {
    this.trainingData = trainingData;
    this.testData = testData;
  }

  /**
   * Train a RandomForest model
   */
  public Double createModel(JavaSparkContext sc) {
    // parameters
    Map<Integer, Integer> categoricalFeaturesInfo = new HashMap<Integer, Integer>();
    int numTrees = 15;
    int numClasses = 10;
    String featureSubsetStrategy = "auto";
    String impurity = "gini";
    int maxDepth = 20;
    int maxBins = 32;

    // create model
    RandomForestModel model = RandomForest.trainClassifier(trainingData, numClasses, categoricalFeaturesInfo, numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins, 12345);
    model.save(sc.sc(), "activityrecognition_rf");
    System.out.println("RandomForests Tree Model ----- > "+model.toDebugString());

    // Evaluate model on test instances and compute test error
    JavaPairRDD<Double, Double> predictionAndLabel = testData.mapToPair(p -> new Tuple2<Double, Double>(model.predict(p.features()), p.label()));
    Double testErr = 1.0 * predictionAndLabel.filter(pl -> !pl._1().equals(pl._2())).count() / testData.count();

    return testErr;
  }
}
