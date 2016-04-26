package edu.brown.cs.nbrennan.auctionize;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import com.google.common.collect.ImmutableMap;


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
    

    new WebServer();
  }






}