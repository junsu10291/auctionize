package edu.brown.cs.nbrennan.auctionize;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigInteger;
import java.time.LocalTime;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import edu.brown.cs.nbrennan.job.Job;
import edu.brown.cs.wdencker.graph.GraphSearch;
import edu.brown.cs.wdencker.graph.JobGraph;
import edu.brown.cs.wdencker.graph.Step;
import freemarker.template.Configuration;
import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

public class WebServer {

  private final static Gson GSON = new Gson();
  private List<BigInteger> activeUsers;
  private Map<String, Job> jobs;
  private JobGraph graph;

  public WebServer(Map<String, Job> jobs) {
    this.jobs = jobs;
    activeUsers = Collections.synchronizedList(new ArrayList<BigInteger>());
    this.graph = new JobGraph(new ArrayList<>(jobs.values()));
    this.runSparkServer();
  }

  private void runSparkServer() {
    Spark.externalStaticFileLocation("src/main/resources/static");
    Spark.exception(Exception.class, new ExceptionPrinter());
    FreeMarkerEngine freeMarker = createEngine();
    Spark.get("/", new FrontHandler(), freeMarker);
    Spark.get("/home", new HomeHandler(), freeMarker);
    Spark.get("/throwexception", new ThrowsException());
    // Setup Spark Routes
    Spark.post("/authenticate", new Authenticate());
    Spark.get("/map", new MapHandler(), freeMarker);
    Spark.post("/jobs", new JobsHandler());
    Spark.post("/path", new PathHandler());
  }

  /**
   * bacon home page.
   */
  private class FrontHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> variables = ImmutableMap.of("title",
          "Auctionize: It's Straight Cash Homie!");
      return new ModelAndView(variables, "main.ftl");
    }
  }

  private class MapHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> variables = ImmutableMap.of();
      return new ModelAndView(variables, "map.ftl");
    }
  }

  static class PostHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> variables = ImmutableMap.of();
      return new ModelAndView(variables, "postJob.ftl");
    }
  }

  private class JobsHandler implements Route {
    @Override
    public Object handle(final Request req, final Response res) {
      return GSON.toJson(jobs);
    }
  }

  private class PathHandler implements Route {
    @Override
    public Object handle(final Request req, final Response res) {
      QueryParamsMap qm = req.queryMap();
      double homeLat = Double.parseDouble(qm.value("homeLat"));
      double homeLng = Double.parseDouble(qm.value("homeLng"));
      LocalTime start = LocalTime.of(Integer.parseInt(qm.value("startHours")),
          Integer.parseInt(qm.value("startMinutes")));
      LocalTime end = LocalTime.of(Integer.parseInt(qm.value("endHours")),
          Integer.parseInt(qm.value("endMinutes")));
      Job homeStart = new Job.Builder().lat(homeLat).lng(homeLng).start(start)
          .end(start).id("homeStart").profit(0.0).build();
      Job homeEnd = new Job.Builder().lat(homeLat).lng(homeLng).start(end)
          .end(end).id("homeEnd").profit(0.0).build();
      graph.addVertex(homeStart);
      graph.addVertex(homeEnd);
      List<Step<Job, Double>> steps = GraphSearch.bellmanFord(homeStart,
          homeEnd, graph);
      List<String> ids = new ArrayList<>();
      for (Step<Job, Double> step : steps) {
        ids.add(step.getTo().id);
      }
      ids.remove(ids.size() - 1);
      graph.removeVertex(homeStart);
      graph.removeVertex(homeEnd);
      return GSON.toJson(ids);
    }
  }

  public class HomeHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> variables = ImmutableMap.of("title", "Home");
      return new ModelAndView(variables, "home.ftl");
    }
  }

  public class ThrowsException implements Route {
    @Override
    public Object handle(Request request, Response response) {
      response.status(404);
      response.body("Resource not found");
      return response;
    }
  }

  public class Authenticate implements Route {
    @Override
    public Object handle(Request req, Response res) {
      QueryParamsMap qm = req.queryMap();
      BigInteger userId = new BigInteger(qm.value("userId"));
      System.out.println(userId);
      if (!activeUsers.contains(userId)) {
        activeUsers.add(userId);
      }
      res.redirect("/home");
      return res;
    }
  }

  /**
   * Creates FreemarkerEngine.
   * @return FreeMarkerEngine instance with configuration
   */
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

  /**
   * Prints exceptions
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
}
