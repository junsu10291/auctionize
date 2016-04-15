package edu.brown.cs.wdencker.graph;

import edu.brown.cs.jchoi21.parser.JobEntry;

public class JobWeight extends EdgeWeight<Double> {
  public JobEntry job1;
  public JobEntry job2;

  public JobWeight(Double weight) {
    super(weight);
  }

  public JobWeight(JobEntry job1, JobEntry job2) {
    super(getJobWeight(job1, job2));
    this.job1 = job1;
    this.job2 = job2;
  }

  private static double getJobWeight(JobEntry job1, JobEntry job2) {
    // should be implemented in job weight class
    return 1.0;
  }

}
