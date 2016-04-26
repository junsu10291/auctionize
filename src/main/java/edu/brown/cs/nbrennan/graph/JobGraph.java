package edu.brown.cs.nbrennan.graph;

import java.time.LocalTime;
import java.util.List;

import edu.brown.cs.jchoi21.profitestimator.LatLng;
import edu.brown.cs.jchoi21.profitestimator.ProfitEstimator;
import edu.brown.cs.nbrennan.job.Job;

public class JobGraph extends WeightedGraph<Job, Double> {

  public JobGraph(List<Job> jobs) {
    super();
    for (int i = 0; i < jobs.size(); i++) {
      for (int j = 0; j < jobs.size(); j++) {
        if (i != j) {
          Job job1 = jobs.get(i);
          Job job2 = jobs.get(j);
          LocalTime job1EndPlusTravel = job1.end.plusSeconds(ProfitEstimator
              .estimateTime(new LatLng(job2.lat, job2.lng), job1));
          if (job2.start.isAfter(job1EndPlusTravel)) {
            addDirectedEdge(job1, job2, new JobWeight(job1, job2));
          }
        }
      }
    }
  }
}
