package com.fitdrift.util;

public class ViewUtil {
	public static String convertLongTimeToString(Long longTime) {
//		Long time = null;
//		String[] arr = strTime.split(":");
//		Long hoursInSeconds = Long.parseLong(arr[0]) * 60 * 60;
//		Long minutesInSeconds = Long.parseLong(arr[1]) * 60;
//		Long seconds = Long.parseLong(arr[2]);
//		time = hoursInSeconds + minutesInSeconds + seconds;
//		return time;
		
		
		//String timeString = null;
		
		//longTime now in milliseconds, convert milliseconds to seconds
		long secondTime = longTime/1000;
		
		Long hours = secondTime/(60*60);
		Long minutes = (secondTime - hours * 60*60)/60;
		Long seconds = secondTime - hours*60*60 - minutes*60;
		String durString = "";
		if(hours < 10) {
			durString = "0"+hours;
		} else {
			durString = hours.toString();
		}
		if(minutes < 10) {
			durString = durString + ":0" + minutes;
		} else {
			durString = durString + ":" + minutes;
		}
		if(seconds < 10) {
			durString = durString + ":0" + seconds;
		} else {
			durString = durString + ":" + seconds;
		}
		return durString;
		
	}
	
	public static String equipmentType(String activityTypeDescription) {
		if("Work".equals(activityTypeDescription)) {
			return "Project";
		} else if("Ride".equals(activityTypeDescription)) {
			return "Bike";
		} else if("Run".equals(activityTypeDescription)) {
			return "Shoes";
		} else if("Ski".equals(activityTypeDescription)) {
			return "Skis";
		} else {
			return "Gear";
		}
	}
}
