package edu.brown.cs.jchoi21.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser {  
  HashMap<String, String> _attributes = new HashMap<>();
  
  public Parser(String category) throws IOException {
    // connect
    Document doc = Jsoup.connect("https://providence.craigslist.org/").get();
    
    // get category
    Elements listings = doc.select("div#center").select("li");
    Elements selectedListings = listings.select("a:matches(" + category + ")");
    
    String url = "";
    // list with category "contained" in --> select specific one
    for (Element element : selectedListings) {
      if (element.text().equals(category)) {
        url = element.attr("abs:href");
      }    
    }
    
    // throw error if category doens't exit
    if (url.equals("")) {
      throw new IOException("Category doesn't exist");
    } 
    
    // retrieve the listings
    List<JobEntry> entries = retrieveListings(url);
    
    for (JobEntry jobEntry : entries) {
      System.out.println(jobEntry);
    }
    
    System.out.println("# of entries: " + entries.size());
  }
  
  private List<JobEntry> retrieveListings(String url) throws IOException {
    List<JobEntry> entries = new ArrayList<>();
    boolean lastPage = false;
    Document doc = Jsoup.connect(url).get();
    
    // while not last page
    while(!lastPage) {
      Elements listings = doc.getElementsByClass("row").select("span.txt");
      
      for (Element element : listings) {
        _attributes = new HashMap<>();
        
        String title = element.select("span.pl").select("a").text();
        String id = element.select("span.pl").select("a").attr("data-id");
        String location = element.select("span.l2").select("small").text();
        String date = element.select("span.pl").select("time").attr("title");
        
        // retrieving content
        String contentUrl = element.select("span.pl").select("a").attr("abs:href");
        HashMap<String, String> content = retrieveContent(contentUrl);
        
        // put into hashmap
        _attributes.put("title", title);
        _attributes.put("id", id);
        _attributes.put("location", location);
        _attributes.put("date", date); 
        _attributes.putAll(content);
        
        entries.add(new JobEntry(_attributes));
      }
          
      // reset lastpage boolean & url
      lastPage = doc.select("div.paginator").select("div.buttongroup").select("div:not(.lastpage)").size() == 0;
      String nextPageUrl = doc.select("div.paginator").select("a.next").attr("abs:href");
      doc = Jsoup.connect(nextPageUrl).get();
    }
    
    return entries;
  }
  
  private HashMap<String, String> retrieveContent(String url) throws IOException {
    Document doc = Jsoup.connect(url).get();
    String content = doc.select("section#postingbody").text();
    
    HashMap<String, String> body = new HashMap<>();
    
    String latitude = null;
    String longitude = null;
        
    Element map = doc.select("div.mapAndAttrs").select("div#map").first();
    
    if (map != null) { // hasmap
      if (!map.attr("data-latitude").equals("") && !map.attr("data-longitude").equals("")) {
     // get latitude and longitude coords
        latitude = map.attr("data-latitude");
        longitude = map.attr("data-longitude");
      }   
    }
    
    // add to list
    body.put("content", content);
    body.put("latitude", latitude);
    body.put("longitude", longitude);
    
    // TODO: retrieve price
    
    
    return body;
  }
}
