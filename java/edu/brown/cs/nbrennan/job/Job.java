package edu.brown.cs.nbrennan.job;

import java.time.LocalTime;

public class Job {

  public final String id;
  public final String title;
  public final String category;
  public final double lat;
  public final double lng;
  public final LocalTime start;
  public final LocalTime end;
  public final double profit;

  private Job(String id, String title, String category, double lat, double lng,
      LocalTime start, LocalTime end, double profit) {
    this.id = id;
    this.title = title;
    this.category = category;
    this.lat = lat;
    this.lng = lng;
    this.start = start;
    this.end = end;
    this.profit = profit;
  }

  public static class Builder {

    private String id = "";
    private String title = "";
    private String category = "";
    private double lat = 0;
    private double lng = 0;
    private LocalTime start = LocalTime.of(0, 0);
    private LocalTime end = LocalTime.of(0, 0);
    private double profit = 0;

    public Builder() {
    }

    public Job build() {
      return new Job(id, title, category, lat, lng, start, end, profit);
    }

    public Builder id(String id) {
      this.id = id;
      return this;
    }

    public Builder title(String title) {
      this.title = title;
      return this;
    }

    public Builder category(String category) {
      this.category = category;
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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Job other = (Job) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }
}
