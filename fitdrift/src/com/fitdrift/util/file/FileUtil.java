package com.fitdrift.util.file;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.primefaces.model.UploadedFile;
import org.xml.sax.SAXException;

import com.fitdrift.domain.activity.Activity;
import com.fitdrift.domain.activity.ActivityPoint;
import com.fitdrift.model.AthleticgisFacade;
import com.fitdrift.util.file.fit.FitDecoder;
import com.fitdrift.util.file.gpx.GPXParser;
import com.fitdrift.util.file.gpx.beans.GPX;
import com.fitdrift.util.file.gpx.beans.Track;
import com.fitdrift.util.file.gpx.beans.Waypoint;
import com.fitdrift.util.gis.GISCalculator;

/**
 * Utility class for files.
 * 
 * @author Matthew Allen
 * @version 20131208
 */
public class FileUtil {
	
	/**
	 * Finds a files extension.
	 * Returns the string after the last dot in fileName.
	 * 
	 * @param fileName
	 * @return
	 */
	public static String fileExtension(String fileName) {
		String extension = null;

		int i = fileName.lastIndexOf('.');
		if (i > 0) {
			extension = fileName.substring(i + 1);
		}
		return extension;
	}

	/**
	 * 
	 * @param file
	 * @param username
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static void uploadAcitvityPointsFromGPX(UploadedFile file, String username) throws ParserConfigurationException, SAXException, IOException {
		InputStream in = file.getInputstream();
		GPXParser p = new GPXParser();
		GPX gpx = p.parseGPX(in);
		Activity a = new Activity();
		a.setName(file.getFileName());
		a.setUser(AthleticgisFacade.findUserByUsername(username));

		List<ActivityPoint> activityPoints = new ArrayList<ActivityPoint>();
		for (Track t : gpx.getTracks()) {
			for (Waypoint wp : t.getTrackPoints()) {
				// System.out.println(wp.getLatitude() + "," +
				// wp.getLongitude());
				ActivityPoint activityPoint = new ActivityPoint();
				activityPoint.setLatitude(wp.getLatitude());
				activityPoint.setLongitude(wp.getLongitude());
				activityPoint.setTime(new Timestamp(wp.getTime().getTime()));
				activityPoint.setElevation(wp.getElevation());
				activityPoints.add(activityPoint);
			}
		}
		a.setActivitypoints(activityPoints);
		
		//distance TODO is elevation non null
		GISCalculator gisc = new GISCalculator();
		
		a.setDistance(gisc.computePathDistance(activityPoints) * 0.000621371); //0.000621371 convert meters to miles

		//user defined MyMap not used
		a.setUseMyMap(false);
		
		//distance
		
		AthleticgisFacade.persistActivityAndActivityPoints(a, activityPoints);

		in.close();
	}
	
	/**
	 * 
	 * @param file
	 * @param username
	 * @throws IOException
	 */
	public static void uploadFitFile(UploadedFile file, String username) throws IOException {
		List<ActivityPoint> activityPoints = FitDecoder.decode(file.getInputstream());
		Activity a = new Activity();
		a.setName(file.getFileName());
		a.setUser(AthleticgisFacade.findUserByUsername(username));
		a.setActivitypoints(activityPoints);
//		for(ActivityPoint ap : activityPoints) {
//			System.out.println("Lat: " + ap.getLatitude() + "," + "Long: " + ap.getLongitude() + "," + "Elevation: " + ap.getElevation() + "," + "Time: " + ap.getTime());
//		}
		//distance TODO is elevation non null, and is distance already in FIT file?
		GISCalculator gisc = new GISCalculator();
		
		a.setDistance(gisc.computePathDistance(activityPoints) * 0.000621371); //0.000621371 convert meters to miles
		
		//user defined MyMap not used
		a.setUseMyMap(false);
		//distance
		
		long startTime = activityPoints.get(0).getTime().getTime();
		long endTime = activityPoints.get(activityPoints.size()-1).getTime().getTime(); 
		a.setStartTime(startTime); //start time end time milliseconds
		a.setEndTime(endTime);
		long duration = (endTime - startTime); //duration in milliseconds
		a.setDurationMilliseconds(duration);
		a.setActivitytype_id(1L); //set default activity to running, or whatever 1L is
		
		AthleticgisFacade.persistActivityAndActivityPoints(a, activityPoints);
	}

}
