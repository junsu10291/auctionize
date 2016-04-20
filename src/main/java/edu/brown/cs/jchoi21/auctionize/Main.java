package edu.brown.cs.jchoi21.auctionize;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import edu.brown.cs.jchoi21.parser.JobEntry;
import edu.brown.cs.jchoi21.parser.LatLng;
import edu.brown.cs.jchoi21.profit.ProfitEstimator;
import freemarker.template.Configuration;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;


public class Main {
  public static void main(String[] args) {
//    try {
//      Parser parser = new Parser("labor");
//    } catch (IOException e) {
//      System.out.println("Error fetching url");
//    }
    
    HashMap<String, String> blah = new HashMap<>();
    blah.put("latitude", "41.818844");
    blah.put("longitude", "-71.429531");
    blah.put("pay", "14.00"); // per hour
    blah.put("duration", "3");
    
    System.out.println("Profit / hour: " + ProfitEstimator.estimateProfit(new LatLng(41.843019f, -71.393997f), new JobEntry(blah)));
    
    //new Main(args).run();
  }

  private String[] args;
  private File db;

  private Main(String[] args) {
    this.args = args;
  }

  private void run() {
    OptionParser parser = new OptionParser();

    parser.accepts("gui");
    OptionSpec<File> fileSpec = parser.nonOptions().ofType(File.class);
    OptionSet options = parser.parse(args);

    db = options.valueOf(fileSpec);
    if (db == null) {
      System.out.println("ERROR: Please specify a star file");
      System.exit(1);
    }

    if (options.has("gui")) {
      runSparkServer();
    } else {
      // Process commands
    }
  }

  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration();
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.\n", templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

  private void runSparkServer() {
    Spark.externalStaticFileLocation("src/main/resources/static");
    Spark.exception(Exception.class, new ExceptionPrinter());

    FreeMarkerEngine freeMarker = createEngine();

    // Setup Spark Routes
    Spark.get("/stars", new FrontHandler(), freeMarker);
  }

  private class FrontHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> variables =
        ImmutableMap.of("title", "Stars: Query the database",
                        "db", db);
      return new ModelAndView(variables, "query.ftl");
    }
  }


  private static class ExceptionPrinter implements ExceptionHandler {
    @Override
    public void handle(Exception e, Request req, Response res) {
      res.status(500);
      StringWriter stacktrace = new StringWriter();
      try (PrintWriter pw = new PrintWriter(stacktrace)) {
        pw.println("<pre>");
        e.printStackTrace(pw);
        pw.println("</pre>");
      }
      res.body(stacktrace.toString());
    }
  }


}
