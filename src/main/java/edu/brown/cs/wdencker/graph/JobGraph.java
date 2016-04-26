package edu.brown.cs.wdencker.graph;

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

  @Override
  public void addVertex(Job node) {
    super.addVertex(node);
    for (Job job : this) {
      if (!node.equals(job)) {
        LocalTime jobEndPlusTravel = job.end.plusSeconds(
            ProfitEstimator.estimateTime(new LatLng(node.lat, node.lng), job));
        if (node.start.isAfter(jobEndPlusTravel)) {
          addDirectedEdge(job, node, new JobWeight(job, node));
        }
        LocalTime nodeEndPlusTravel = node.end.plusSeconds(
            ProfitEstimator.estimateTime(new LatLng(job.lat, job.lng), node));
        if (job.start.isAfter(nodeEndPlusTravel)) {
          addDirectedEdge(node, job, new JobWeight(node, job));
        }
      }
    }
  }
}
