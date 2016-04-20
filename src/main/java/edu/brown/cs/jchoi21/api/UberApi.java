package edu.brown.cs.jchoi21.api;

import com.uber.sdk.rides.client.Session;
import com.uber.sdk.rides.client.Session.Environment;

public class UberApi {
  Session session = new Session.Builder()
          .setServerToken("YOUR_SERVER_TOKEN")
          .setEnvironment(Environment.PRODUCTION)
          .build();
  
}
