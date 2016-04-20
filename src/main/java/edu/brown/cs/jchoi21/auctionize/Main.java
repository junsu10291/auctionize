package edu.brown.cs.jchoi21.auctionize;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import edu.brown.cs.aecooper.server.WebServer;
import edu.brown.cs.jchoi21.parser.DatabaseCreator;
import edu.brown.cs.jchoi21.parser.Parser;
import edu.brown.cs.jchoi21.parser.Preprocessor;
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
    DatabaseCreator.create();
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
    if (db == null) {
      System.out.println("ERROR: Please specify a database file");
      System.exit(1);
    }

    //new WebServer();
  }






}
