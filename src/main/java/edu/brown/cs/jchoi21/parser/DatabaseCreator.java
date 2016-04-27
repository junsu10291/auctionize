package edu.brown.cs.jchoi21.parser;

import java.awt.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import java.sql.Time;

import java.time.temporal.ChronoUnit;

import edu.brown.cs.nbrennan.job.Job;

public class DatabaseCreator {
	public static void create(){
		String csvFile = "JobDataFinal2.txt";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = "\t";
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("H:mm");
		ArrayList<Job> jobs = new ArrayList<>();
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				String[] job = line.split(cvsSplitBy);
				ArrayList<Double> randomLatLon = RandomLatLon.getLocation();
				LocalTime startTime = LocalTime.parse(job[2], timeFormat);
				LocalTime endTime = LocalTime.parse(job[3], timeFormat);
				long hoursBetween = ChronoUnit.HOURS.between(startTime, endTime);
				long minutesBetween = ChronoUnit.MINUTES.between(startTime, endTime) % 60;
				double hourlyWage = new Random().nextInt(8) + 7;
				double profit = hourlyWage * (hoursBetween + minutesBetween/60);
				//System.out.println("start: " + startTime + " | end: " + endTime + 
				//						" hoursBetween: " + hoursBetween + " minutesBetwen: " + minutesBetween);
				//System.out.println("profit: " + profit);
				Job newJob = new Job.Builder().id(UUID.randomUUID().toString())
												.title(job[0])
												.category(job[1])
												.start(startTime)
												.end(endTime)
												.lng(randomLatLon.get(0))
												.lat(randomLatLon.get(1))
												.profit(profit)
												.build();
				jobs.add(newJob);
				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		insertJobstoDB(jobs,"auctionize.db");
	}

	public static void insertJobstoDB(ArrayList<Job> jobs, String db) {
	    Connection c = null;
	    PreparedStatement prep = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:" + db);
	      c.setAutoCommit(false);
	      System.out.println("Opened database successfully");
	      
	      String sql;
	      for(Job job : jobs){
	    	  System.out.println("start: " + job.start);
	    	  System.out.println("start in Time: " + Time.valueOf(job.start));
	    	  sql = "INSERT INTO JOBS (id, title, type, lat, lon, start, end, profit) " +
	    			  "VALUES (?,?,?,?,?,?,?,?);";
	    	  prep = c.prepareStatement(sql);
	    	  prep.setString(1,job.id);
	    	  prep.setString(2, job.title);
	    	  prep.setString(3, job.category);
	    	  prep.setDouble(4, job.lat);
	    	  prep.setDouble(5, job.lng);
	    	  prep.setString(6, (job.start).toString());
	    	  prep.setString(7, (job.end).toString());
	    	  prep.setDouble(8,job.profit);
	    	  prep.executeUpdate();
	      }
	      c.commit();
	      System.out.println("*******");
	      sql = "select * from jobs where type='PET';";
	      prep = c.prepareStatement(sql);
	      DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("H:mm");
	      try (ResultSet rs = prep.executeQuery()) {
	    	  while(rs.next()){
		    	  LocalTime start = LocalTime.parse(rs.getString("start"), timeFormat);;
		    	  System.out.println(start);
		      }
	        }
	     
	      prep.close();
	      
	      
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	}
}
