package edu.brown.cs.jchoi21.profitestimator;

public class LatLng {
  private double _lat;
  private double _lng;
  
  public LatLng(double lat, double lng) {
    this._lat = lat;
    this._lng = lng;
  }

  public double get_lat() {
    return _lat;
  }

  public double get_lng() {
    return _lng;
  }

  @Override
  public String toString() {
    return "LatLng [_lat=" + _lat + ", _lng=" + _lng + "]";
  }
}
