package edu.brown.cs.jchoi21.profitestimator;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;

import edu.brown.cs.nbrennan.job.Job;

public class ProfitEstimator {
  public ProfitEstimator() {
  }

  // Uber token :"_jOzhJLJy-WM0eV74N9d3CA4vZqGkJd4R4-KFlFu"
  private static final int EARTH_RADIUS_MI = 3959;

  public static double estimateProfitWithUber(LatLng latlng, Job job) {
    return job.profit - estimateUberCost(haversineDistance(latlng.get_lat(),
        latlng.get_lng(), job.lat, job.lng));
  }

  private static double estimateUberCost(double distInMiles) {
    return 3.0 + (2.2 * distInMiles);
  }

  private static double haversineDistance(double lat1, double lng1, double lat2,
      double lng2) {
    double dLatSin = sin(toRadians(lat2 - lat1) / 2);
    double dLngSin = sin(toRadians(lng2 - lng1) / 2);
    double a = pow(dLatSin, 2)
        + pow(dLngSin, 2) * cos(toRadians(lat1)) * cos(toRadians(lat2));
    return EARTH_RADIUS_MI * 2 * atan2(sqrt(a), sqrt(1 - a));
  }

  public static int estimateTimeDriving(LatLng latlng, Job job) {
    return 0;
//    return (int) (60 * 1.5 * haversineDistance(latlng.get_lat(),
//        latlng.get_lng(), job.lat, job.lng));
  }
}
