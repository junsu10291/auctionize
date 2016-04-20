package edu.brown.cs.nbrennan.parser;

public class JobEntry {
  private String _title;
  private String _location;
  private String _date;
  private String _pay;
  
  public JobEntry(String title, String location, String date) {
    this._title = title;
    this._location = location;
    this._date = date;
  }

  public String get_title() {
    return _title;
  }

  public String get_location() {
    return _location;
  }

  public String get_date() {
    return _date;
  }

  public String get_pay() {
    return _pay;
  }
}
