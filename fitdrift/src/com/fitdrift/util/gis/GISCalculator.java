package com.fitdrift.util.gis;

import java.util.List;

import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.LatLngBounds;

import com.fitdrift.domain.activity.*;

public class GISCalculator {
	
	public static Double covertFromWGS84ToDegrees(Integer semicircles) {
		//2^31 = 2147483648
		return ((1.0*semicircles)/2147483648.0) * 180.0;  //Math.pow(2.0, 31)) * 180.0;
		//return 0.0;
		//System.out.println("This is semi" + semicircles);
		//eturn 1.0*semicircles;
	}
	
	public static Double convertFromDegreesToWGS84(Double degrees) {
		//maybe convert to integer?
		return 1.0*degrees * ( 2147483648.0 / 180.0 );
	}

	// activityPoints should be ordered from earliest point to latest
	public double computePathDistance(List<ActivityPoint> aps) {
		double distance = 0;
		for (int i = 0; i < aps.size() - 1; i++) {
			distance = distance
					+ distance(aps.get(i).getLatitude(), aps.get(i + 1)
							.getLatitude(), aps.get(i).getLongitude(),
							aps.get(i + 1).getLongitude(), aps.get(i)
									.getElevation(), aps.get(i + 1)
									.getElevation());
		}
		return distance*0.621371; //convert km to miles
	}
	
	public double computeMarkerPathDistance(List<MyMapMarker> markers) {
		double distance = 0;
		for (int i = 0; i < markers.size() - 1; i++) {
			distance = distance
					+ distance(markers.get(i).getLatitude(), markers.get(i + 1)
							.getLatitude(), markers.get(i).getLongitude(),
							markers.get(i + 1).getLongitude(), markers.get(i)
									.getElevation(), markers.get(i + 1)
									.getElevation());
		}
		return distance*0.621371; //convert km to miles
	}

	/*
	 * Calculate distance between two points in latitude and longitude taking
	 * into account height difference. If you are not interested in height
	 * difference pass 0.0. Uses Haversine method as its base.
	 * 
	 * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
	 * el2 End altitude in meters
	 */
	private double distance(double lat1, double lat2, double lon1, double lon2,
			double el1, double el2) {

		final int R = 6371; // Radius of the earth

		Double latDistance = deg2rad(lat2 - lat1);
		Double lonDistance = deg2rad(lon2 - lon1);
		Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
				* Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = R * c * 1000; // convert to meters

		double height = el1 - el2;
		distance = Math.pow(distance, 2) + Math.pow(height, 2);
		return Math.sqrt(distance);
	}

	private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	public LatLngBounds getLatLngBounds(List<ActivityPoint> aps) {
		

		Double minLat = 90.0;
		Double maxLat = -90.0;
		Double minLon = 180.0;
		Double maxLon = -180.0;
		

		for (ActivityPoint a : aps) {

			Double lat = a.getLatitude();
			Double lon = a.getLongitude();

			maxLat = Math.max(lat, maxLat);
			minLat = Math.min(lat, minLat);
			maxLon = Math.max(lon, maxLon);
			minLon = Math.min(lon, minLon);
		}
		
		System.out.println("sw: " + minLat + "," + minLon + "ne" + maxLat + "," + maxLon);
		
		LatLng sw = new LatLng(minLat, minLon);
		LatLng ne = new LatLng(maxLat, maxLon);
		
		
		LatLngBounds latLngBounds = new LatLngBounds(ne, sw);
		return latLngBounds;
	}
}
