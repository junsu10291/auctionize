package edu.brown.cs.nbrennan.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser {  
  public Parser(String category) throws IOException {
    Document doc = Jsoup.connect("https://providence.craigslist.org/").get();
    Elements listings = doc.select("div#center").select("li");
    Elements selectedListings = listings.select("a:matches(" + category + ")");
    String url = "";
    
    // list with category "contained" --> select the specific one
    for (Element element : selectedListings) {
      if (element.text().equals(category)) {
        url = element.attr("abs:href");
      }    
    }
    
    if (url.equals("")) {
      throw new IOException("Category doesn't exist");
    } 
      
    List<JobEntry> entries = retrieveListings(url);
    for (JobEntry jobEntry : entries) {
      System.out.print(jobEntry.get_date() + " " + jobEntry.get_title() + " " + jobEntry.get_location());
      System.out.println();
    }
    System.out.println(entries.size());
  }
  
  public List<JobEntry> retrieveListings(String url) throws IOException {
    List<JobEntry> entries = new ArrayList<>();
    boolean lastPage = false;
    Document doc = Jsoup.connect(url).get();
    
    while(!lastPage) {
      Elements listings = doc.getElementsByClass("row").select("span.txt");
      
      for (Element element : listings) {
        String title = element.select("span.pl").select("a").text();
        String location = element.select("span.l2").select("small").text();
        String date = element.select("span.pl").select("time").attr("title");
        entries.add(new JobEntry(title, location, date));
      }
          
      lastPage = doc.select("div.paginator").select("div.buttongroup").select("div:not(.lastpage)").size() == 0;
      String nextPageUrl = doc.select("div.paginator").select("a.next").attr("abs:href");
      doc = Jsoup.connect(nextPageUrl).get();
    }
    
    return entries;
  }
}
