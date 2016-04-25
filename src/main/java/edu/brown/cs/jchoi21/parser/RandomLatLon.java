package edu.brown.cs.jchoi21.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class RandomLatLon {
	private static double y0 = 41.834168;
	private static double x0 = -71.413026;
	
	private static double radius = 16093.44; // 10 miles in meters

	public static ArrayList<Double> getLocation() {
	    Random random = new Random();
	    // Convert radius from meters to degrees
	    double radiusInDegrees = radius / 111000f;

	    double u = random.nextDouble();
	    double v = random.nextDouble();
	    double w = radiusInDegrees * Math.sqrt(u);
	    double t = 2 * Math.PI * v;
	    double x = w * Math.cos(t);
	    double y = w * Math.sin(t);

	    // Adjust the x-coordinate for the shrinking of the east-west distances
	    double new_x = x / Math.cos(y0);

	    Double foundLongitude = new_x + x0;
	    Double foundLatitude = y + y0;
	    return new ArrayList<Double>(Arrays.asList(foundLongitude, foundLatitude));
	}
	
}
