package edu.brown.cs.nbrennan.auctionize;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.internal.bind.JsonAdapterAnnotationTypeAdapterFactory;

import edu.brown.cs.jchoi21.parser.DatabaseCreator;

import edu.brown.cs.nbrennan.job.Job;

import edu.brown.cs.nbrennan.parser.Parser;
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
  private static final Gson JSON = new Gson();

  public static void main(String[] args) {
    try {
      Parser parser = new Parser("education");
    } catch (IOException e) {
      System.out.println("Error fetching url");
    }

    new Main(args).run();
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
    new WebServer(getJobs());
  }

  private Map<String, Job> getJobs() {
    Map<String, Job> jobs = new HashMap<>();
    DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("H:mm");
    try (Connection conn = DriverManager
        .getConnection("jdbc:sqlite:auctionize.db")) {
      String query = "SELECT * from jobs;";
      try (PreparedStatement ps = conn.prepareStatement(query)) {
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            String id = rs.getString(1);
            Job job = new Job.Builder().id(id).title(rs.getString(2))
                .category(rs.getString(3)).lat(rs.getDouble(4))
                .lng(rs.getDouble(5)).start(LocalTime.parse(rs.getString(6), timeFormat))
                .end(LocalTime.parse(rs.getString(7), timeFormat)).profit(rs.getDouble(8))
                .build();
            jobs.put(id, job);
          }
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return jobs;
  }
}
