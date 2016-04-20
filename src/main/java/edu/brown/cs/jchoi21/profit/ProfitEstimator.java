package edu.brown.cs.jchoi21.profit;

import java.util.List;
import java.util.Map;

import com.uber.sdk.rides.client.Response;
import com.uber.sdk.rides.client.Session;
import com.uber.sdk.rides.client.Session.Environment;
import com.uber.sdk.rides.client.UberRidesServices;
import com.uber.sdk.rides.client.UberRidesSyncService;
import com.uber.sdk.rides.client.error.ApiException;
import com.uber.sdk.rides.client.error.NetworkException;
import com.uber.sdk.rides.client.model.PriceEstimate;
import com.uber.sdk.rides.client.model.PriceEstimatesResponse;

import edu.brown.cs.jchoi21.parser.JobEntry;
import edu.brown.cs.jchoi21.parser.LatLng;

public class ProfitEstimator {
  public ProfitEstimator() {
  }
  
  public static double estimateProfit(LatLng latlng, JobEntry job) {
    Map<String, String> attributes = job.get_attributes();
     
    float startLat = (float) latlng.get_lat();
    float startLng = (float) latlng.get_lng();
    float jobLat = Float.parseFloat(attributes.get("latitude"));
    float jobLng = Float.parseFloat(attributes.get("longitude"));
    
    double pay = Double.parseDouble(attributes.get("pay"));
    double duration = Double.parseDouble(attributes.get("duration"));
    
    double uberPriceEstimate = 0.0;
    
    // create session
    Session session = new Session.Builder()
            .setServerToken("_jOzhJLJy-WM0eV74N9d3CA4vZqGkJd4R4-KFlFu")
            .setEnvironment(Environment.PRODUCTION)
            .build();
    
    // create service
    UberRidesSyncService service = UberRidesServices.createSync(session);
    
    //
    try {
      Response<PriceEstimatesResponse> hi = service.getPriceEstimates(startLat, startLng, jobLat, jobLng);
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
    
    double profit = pay - (uberPriceEstimate / duration);
        
    return profit;
  }
  
}
