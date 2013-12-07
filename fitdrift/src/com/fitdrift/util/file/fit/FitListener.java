package com.fitdrift.util.file.fit;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.fitdrift.domain.activity.ActivityPoint;
import com.fitdrift.util.gis.GISCalculator;
import com.garmin.fit.RecordMesg;
import com.garmin.fit.RecordMesgListener;

public class FitListener implements RecordMesgListener {
	List<ActivityPoint> activityPoints = new ArrayList<ActivityPoint>();
	
	/**
	 * @return the activityPoints
	 */
	public List<ActivityPoint> getActivityPoints() {
		return activityPoints;
	}

	@Override
	public void onMesg(RecordMesg mesg) {
		
		
		//GISCalculator calc = new GISCalculator();
		
		
		
		
		
		
		
		//System.out.println("128" + mesg.getTime128());
		//System.out.println(mesg.getTimeFromCourse());
//		System.out.println(mesg.getTimestamp());
//		System.out.println(mesg.getTimestamp().getTimestamp());
//		System.out.println(new Timestamp(mesg.getTimestamp().getDate().getTime()));
		
		if(mesg.getPositionLat() != null && mesg.getPositionLong() != null && mesg.getAltitude() != null && mesg.getTimestamp() != null) {
			ActivityPoint activityPoint = new ActivityPoint();
			//System.out.println("lat: " + mesg.getPositionLat());
			activityPoint.setLatitude(GISCalculator.covertFromWGS84ToDegrees(mesg.getPositionLat()));
			activityPoint.setLongitude(GISCalculator.covertFromWGS84ToDegrees(mesg.getPositionLong()));
			activityPoint.setElevation(mesg.getAltitude()*1.0);
			activityPoint.setTime(new Timestamp(mesg.getTimestamp().getDate().getTime()));
			activityPoints.add(activityPoint);
		}
		
		
//		if(mesg.getPositionLat() != null) {
//			//System.out.println("lat: " + mesg.getPositionLat());
//			activityPoint.setLatitude(GISCalculator.covertFromWGS84ToDegrees(mesg.getPositionLat()));
//		}
//		
//		if(mesg.getPositionLong() != null) {
//			//System.out.println("lng: " + mesg.getPositionLong());
//			activityPoint.setLongitude(GISCalculator.covertFromWGS84ToDegrees(mesg.getPositionLong()));
//		}
//		
//		if(mesg.getAltitude() != null) {
//			//System.out.println("alt: " + mesg.getAltitude());
//			activityPoint.setElevation(mesg.getAltitude()*1.0);
//		}
//		
//		if(mesg.getTimestamp() != null) {
//			//System.out.println("time: " + mesg.getTimestamp());
//			activityPoint.setTime(new Timestamp(mesg.getTimestamp().getDate().getTime()));
//		}
		
	}
}
