package edu.brown.cs.nbrennan.job;

import java.time.LocalTime;

public class Job {

  public final String id;
  public final String title;
  public final String description;
  public final double lat;
  public final double lng;
  public final LocalTime start;
  public final LocalTime end;
  public final double profit;

  private Job(String id, String title, String description, double lat,
      double lng, LocalTime start, LocalTime end, double profit) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.lat = lat;
    this.lng = lng;
    this.start = start;
    this.end = end;
    this.profit = profit;
  }

  public static class Builder {

    private String id = "";
    private String title = "";
    private String description = "";
    private double lat = 0;
    private double lng = 0;
    private LocalTime start = LocalTime.of(0, 0);
    private LocalTime end = LocalTime.of(0, 0);
    private double profit = 0;

    public Builder() {
    }

    public Job build() {
      return new Job(id, title, description, lat, lng, start, end, profit);
    }

    public Builder id(String id) {
      this.id = id;
      return this;
    }

    public Builder title(String title) {
      this.title = title;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder lat(double lat) {
      this.lat = lat;
      return this;
    }

    public Builder lng(double lng) {
      this.lng = lng;
      return this;
    }

    public Builder start(LocalTime start) {
      this.start = start;
      return this;
    }

    public Builder end(LocalTime end) {
      this.end = end;
      return this;
    }

    public Builder profit(double profit) {
      this.profit = profit;
      return this;
    }
  }
}
