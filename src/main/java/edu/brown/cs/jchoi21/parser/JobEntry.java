package edu.brown.cs.jchoi21.parser;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class JobEntry {
  // remove?
//  private String _title;
//  private String _location;
//  private String _date;
//  private String _pay;
//  private String _id;
//  private String _content;
//  private LatLng _latLng;
//  private double _duration;
  
  private HashMap<String, String> _attributes;
  
  public JobEntry(HashMap<String, String> attributes) {    
    this._attributes = attributes;
  }

  public Map<String, String> get_attributes() {
    return Collections.unmodifiableMap(_attributes);
  }

  @Override
  public String toString() {
    return "JobEntry [_attributes=" + _attributes + "]";
  }
}
