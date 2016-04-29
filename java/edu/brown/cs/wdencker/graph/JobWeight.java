package edu.brown.cs.wdencker.graph;

import edu.brown.cs.jchoi21.profitestimator.LatLng;
import edu.brown.cs.jchoi21.profitestimator.ProfitEstimator;
import edu.brown.cs.nbrennan.job.Job;

public class JobWeight extends EdgeWeight<Double> {
  public Job job1;
  public Job job2;

  public JobWeight(Double weight) {
    super(weight);
  }

  public JobWeight(Job job1, Job job2) {
    super(getJobWeight(job1, job2));
    this.job1 = job1;
    this.job2 = job2;
  }

  private static double getJobWeight(Job job1, Job job2) {
    return ProfitEstimator.estimateProfit(new LatLng(job1.lat, job1.lng), job2) * -1;
  }

}
