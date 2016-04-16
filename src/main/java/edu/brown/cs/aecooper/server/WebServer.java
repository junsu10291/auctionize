package edu.brown.cs.aecooper.server;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigInteger;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import edu.brown.cs.aecooper.server.WebServer.Authenticate;
import edu.brown.cs.aecooper.server.WebServer.HomeHandler;
import edu.brown.cs.aecooper.server.WebServer.ThrowsException;
import edu.brown.cs.nbrennan.Job;
import edu.brown.cs.nbrennan.MapSetup;
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
	public WebServer(){
		activeUsers = Collections.synchronizedList(new ArrayList<BigInteger>());
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
	}
	
	/**
	   * bacon home page.
	   */
	  private class FrontHandler implements TemplateViewRoute {
		    @Override
		    public ModelAndView handle(Request req, Response res) {
		      Map<String, Object> variables =
		        ImmutableMap.of("title", "Auctionize: It's Straight Cash Homie!");
		      return new ModelAndView(variables, "main.ftl");
		    }
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
	      Set<Job> jobs = MapSetup.generateJobs();
	      return GSON.toJson(jobs);
	    }
	  }
		public class HomeHandler implements TemplateViewRoute {
			@Override
		    public ModelAndView handle(Request req, Response res) {
		      Map<String, Object> variables =
		        ImmutableMap.of("title", "Home");
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
				if(!activeUsers.contains(userId)){
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
	      System.out.printf("ERROR: Unable use %s for template loading.\n", templates);
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
