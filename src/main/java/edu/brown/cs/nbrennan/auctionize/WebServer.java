package edu.brown.cs.nbrennan.auctionize;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigInteger;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import edu.brown.cs.jchoi21.parser.DatabaseCreator;
import edu.brown.cs.wdencker.graph.JobGraph;

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
  
  // true if the tutorial should be displayed (first time loading the page)
  // false otherwise
  private boolean tutorialStatus = true;

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
    Spark.get("/post", new PostHandler(), freeMarker);
    Spark.post("/jobs", new JobsHandler());
    Spark.post("/path", new PathHandler());
    Spark.post("/postJob", new NewJob());
    Spark.post("/getTutorialStatus", new getTutorialStatusHandler());
  }

  /**
   * bacon home page.
   */
  private class FrontHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> variables = ImmutableMap.of("title",
          "Working Weekend");
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
  
  private class NewJob implements Route {
    @Override
    public Object handle(final Request req, final Response res) {
    	System.out.println("yo");
    	QueryParamsMap qm = req.queryMap();
    	System.out.println("lat: " + qm.value("lat") +
    			"lon: " + qm.value("lon"));
    	String title = qm.value("title");
    	String category = qm.value("type");
    	String start = qm.value("start");
    	String end = qm.value("end");
    	Double lat = Double.valueOf(qm.value("lat"));
    	Double lon = Double.valueOf(qm.value("lon"));
    	Double profit = Double.valueOf(qm.value("pay"));
    	DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("H:mm");
    	String id = UUID.randomUUID().toString();
    	Job newJob = new Job.Builder().id(id)
				.title(title)
				.category(category)
				.start(LocalTime.parse(start, timeFormat))
				.end(LocalTime.parse(end, timeFormat))
				.lng(lon)
				.lat(lat)
				.profit(profit)
				.build();
    	jobs.put(id, newJob);
    	ArrayList<Job> jobsList = new ArrayList<>(1);
    	jobsList.add(newJob);
    	DatabaseCreator.insertJobstoDB(jobsList, "auctionize.db");
    	
    	return GSON.toJson("success");
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
      String[] jobIDs = GSON.fromJson(qm.value("included"), String[].class);
      List<Job> includedJobs = new ArrayList<>();
      for (String id : jobIDs){
        includedJobs.add(jobs.get(id));
      }
      graph = new JobGraph(includedJobs);
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
  
  private class getTutorialStatusHandler implements Route {
    @Override
    public Object handle(final Request req, final Response res) {
      boolean oldStatus = tutorialStatus;
      tutorialStatus = false;
      return oldStatus;
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
