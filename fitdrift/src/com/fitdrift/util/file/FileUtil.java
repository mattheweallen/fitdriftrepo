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

public class FileUtil {
	public static String fileExtension(String fileName) {
		String extension = null;

		int i = fileName.lastIndexOf('.');
		if (i > 0) {
			extension = fileName.substring(i + 1);
		}
		return extension;
	}

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
		a.setWaypoints(activityPoints);

		AthleticgisFacade.persistActivityAndActivityPoints(a, activityPoints);

		in.close();
	}
	
	public static void uploadFitFile(UploadedFile file, String username) throws IOException {
		List<ActivityPoint> activityPoints = FitDecoder.decode(file.getInputstream());
		Activity a = new Activity();
		a.setName(file.getFileName());
		a.setUser(AthleticgisFacade.findUserByUsername(username));
		a.setWaypoints(activityPoints);
		for(ActivityPoint ap : activityPoints) {
			System.out.println("Lat: " + ap.getLatitude() + "," + "Long: " + ap.getLongitude() + "," + "Elevation: " + ap.getElevation() + "," + "Time: " + ap.getTime());
		}
		AthleticgisFacade.persistActivityAndActivityPoints(a, activityPoints);
	}

}
