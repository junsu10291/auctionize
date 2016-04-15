package edu.brown.cs.wdencker.graph;

import java.util.List;

import edu.brown.cs.jchoi21.parser.JobEntry;

public class JobGraph extends WeightedGraph<JobEntry, Double> {
  public JobGraph() {
    super();
  }

  public JobGraph(List<JobEntry> jobs) {
    for (int i = 0; i < jobs.size(); i++) {
      for (int j = 0; j < jobs.size(); j++) {
        if (i != j) {
          JobEntry job1 = jobs.get(i);
          JobEntry job2 = jobs.get(j);
          addDirectedEdge(job1, job2, new JobWeight(job1, job2));
        }
      }
    }
  }

  @Override
  public void addDirectedEdge(JobEntry from, JobEntry to,
      EdgeWeight<Double> weight) {
    if (from.get_date().compareTo(to.get_date()) < 0) {
      super.addDirectedEdge(from, to, weight);
    }
  }

}
