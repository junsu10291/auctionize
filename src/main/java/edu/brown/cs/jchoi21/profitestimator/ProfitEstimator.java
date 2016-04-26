package edu.brown.cs.jchoi21.profitestimator;

import java.util.List;

import com.uber.sdk.rides.client.Response;
import com.uber.sdk.rides.client.Session;
import com.uber.sdk.rides.client.Session.Environment;
import com.uber.sdk.rides.client.UberRidesServices;
import com.uber.sdk.rides.client.UberRidesSyncService;
import com.uber.sdk.rides.client.error.ApiException;
import com.uber.sdk.rides.client.error.NetworkException;
import com.uber.sdk.rides.client.model.PriceEstimate;
import com.uber.sdk.rides.client.model.PriceEstimatesResponse;

import edu.brown.cs.nbrennan.job.Job;

public class ProfitEstimator {
  public ProfitEstimator() {
  }

  private static UberRidesSyncService service = UberRidesServices
      .createSync(new Session.Builder()
          .setServerToken("_jOzhJLJy-WM0eV74N9d3CA4vZqGkJd4R4-KFlFu")
          .setEnvironment(Environment.PRODUCTION).build());

  public static double estimateProfit(LatLng latlng, Job job) {
    float startLat = (float) latlng.get_lat();
    float startLng = (float) latlng.get_lng();
    float jobLat = (float) job.lat;
    float jobLng = (float) job.lng;

    double pay = job.profit;
    double uberPriceEstimate = 0.0;

    try {
      Response<PriceEstimatesResponse> hi = service.getPriceEstimates(startLat,
          startLng, jobLat, jobLng);
      PriceEstimatesResponse pricesEstimateRes = hi.getBody();
      List<PriceEstimate> prices = pricesEstimateRes.getPrices();

      for (PriceEstimate priceEstimate : prices) {
        if (priceEstimate.getDisplayName().equals("uberX")) {
          uberPriceEstimate = priceEstimate.getHighEstimate();
        }
      }
    } catch (ApiException | NetworkException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    double profit = pay - uberPriceEstimate;

    return profit;
  }

  public static int estimateTime(LatLng latlng, Job job) {
    float startLat = (float) latlng.get_lat();
    float startLng = (float) latlng.get_lng();
    float jobLat = (float) job.lat;
    float jobLng = (float) job.lng;

    int uberTimeEstimate = 0;

    try {
      Response<PriceEstimatesResponse> hi = service.getPriceEstimates(startLat,
          startLng, jobLat, jobLng);
      PriceEstimatesResponse pricesEstimateRes = hi.getBody();
      List<PriceEstimate> prices = pricesEstimateRes.getPrices();

      for (PriceEstimate priceEstimate : prices) {
        if (priceEstimate.getDisplayName().equals("uberX")) {
          uberTimeEstimate = priceEstimate.getDuration();
        }
      }
    } catch (ApiException | NetworkException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return uberTimeEstimate;
  }

}
