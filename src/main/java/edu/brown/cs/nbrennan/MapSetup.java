package edu.brown.cs.nbrennan;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import freemarker.template.Configuration;
import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

public class MapSetup {
  
  private static final Gson GSON = new Gson();
  private static final int NUM_JOBS = 100;
  private static final double LAT_BOTTOM = 41.814392;
  private static final double LAT_TOP = 41.837545;
  private static final double LNG_LEFT = -71.433681;
  private static final double LNG_RIGHT = -71.372184;

  public static void main(String[] args) {

    

    Spark.externalStaticFileLocation("src/main/resources/static");
    Spark.exception(Exception.class, new ExceptionPrinter());
    FreeMarkerEngine freeMarker = createEngine();
    Spark.get("/map", new MapHandler(), freeMarker);
    Spark.post("/jobs", new JobsHandler());
  }

  public static Set<Job> generateJobs() {
    Set<Job> jobs = new HashSet<>();
    for (int i = 0; i < NUM_JOBS; i++) {
      String title = "Job " + i;
      String description = "This is the description of job " + i + ".";
      double lat = randomDoubleBetween(LAT_BOTTOM, LAT_TOP);
      double lng = randomDoubleBetween(LNG_LEFT, LNG_RIGHT);
      LocalTime start = LocalTime.of(randomIntBetween(6, 22), 0);
      LocalTime end = LocalTime.of(start.getHour() + 1, 0);
      double profit = randomDoubleBetween(0, 50);
      Job job = new Job.Builder().title(title).description(description).lat(lat)
          .lng(lng).start(start).end(end).profit(profit).build();
      jobs.add(job);
    }
    return jobs;
  }

  private static double randomDoubleBetween(double min, double max) {
    return Math.random() * (max - min) + min;
  }

  private static int randomIntBetween(int min, int max) {
    return (int) (Math.random() * (max - min)) + min;
  }

  private static class MapHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> variables = ImmutableMap.of();
      return new ModelAndView(variables, "map.ftl");
    }
  }
  
  private static class JobsHandler implements Route {
    @Override
    public Object handle(final Request req, final Response res) {
      Set<Job> jobs = generateJobs();
      return GSON.toJson(jobs);
    }
  }

  /**
   * Prints exceptions.
   */
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

  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration();
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.\n",
          templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }
}
