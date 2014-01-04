package com.fitdrift.view;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.model.map.Polyline;

import com.fitdrift.domain.activity.Activity;
import com.fitdrift.domain.activity.ActivityPoint;
import com.fitdrift.domain.activity.ActivitySubType;
import com.fitdrift.domain.activity.ActivityType;
import com.fitdrift.domain.activity.Measure;
import com.fitdrift.domain.activity.MyMap;
import com.fitdrift.domain.activity.MyMapMarker;
import com.fitdrift.model.AthleticgisFacade;
import com.fitdrift.util.gis.GISCalculator;

/**
 * This class supports the createactivity.xhtml
 * 
 * @author Matthew Allen
 * @version 20131217
 * 
 */
@ManagedBean
@ViewScoped
public class CreateActivityBean implements Serializable {
	/**
         * 
         */
	private static final long serialVersionUID = 4050031415234475481L;
	private Date activityDate;
	private String activityType;
	private String activitySubType;
	private String activityGear;
	private boolean indoors = false;
	private boolean useMyMap = false;
	private MyMap selectedMap;
	private String mapMapName;
	private Double distance;
	@ManagedProperty(value = "#{userInfoBean}")
	private UserInfoBean userInfoBean;
	private String startTime;
	private String endTime;
	private Double weight;
	private Double height;
	private Double waist;
	private Double hip;
	private Double thigh;
	private Double aveHeartRate;
	private Double calories;
	private Double systolic;
	private Double diastolic;
	private Double pulse;
	private String activityName;
	// private String defaultActivityName;
	// private List<SelectItem> activityTypeSelectItems;
	// private List<SelectItem> activitySubTypeSelectItems;
	// private Long activityTypeId;
	private ActivityType selectedActivityType;
	// private Long activitySubTypeId;
	private List<ActivityType> activityTypes;

	private ActivitySubType selectedActivitySubType;
	private List<ActivitySubType> activitySubTypes;

	private MapModel polylineModel = new DefaultMapModel();
	private String coordinates;

	public void initialize() {
		Polyline polyline = new Polyline();
		polyline.setStrokeWeight(2);
		polyline.setStrokeColor("#FF8C00");
		polyline.setStrokeOpacity(1.0);
		List<MyMapMarker> myMapMarkers = AthleticgisFacade
				.findMyMapMarkersByMymap_id(selectedMap.getMymap_id());
		for (MyMapMarker m : myMapMarkers) {
			polyline.getPaths().add(
					new LatLng(m.getLatitude(), m.getLongitude()));

			// add markers
			Marker marker = new Marker(new LatLng(m.getLatitude(),
					m.getLongitude()), m.getName());
			polylineModel.addOverlay(marker);
		}
		// GISCalculator g = new GISCalculator();
		// System.out.println("Distance is " +
		// g.computePathDistance(activityPoints));

		polylineModel.addOverlay(polyline);

		//GISCalculator calc = new GISCalculator();
		//Double distance = calc.computeMarkerPathDistance(myMapMarkers) / 1000;
		//DecimalFormat df = new DecimalFormat("#.##");
		//distStr = df.format(distance) + " miles";
		// addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Distance",
		// "Your total distance is " + df.format(distance) + " miles."));
	}

	/**
	 * @return the activityDate
	 */
	public Date getActivityDate() {
		if (activityDate == null) {
			activityDate = new Date();
		}
		return activityDate;
	}

	/**
	 * @param activityDate
	 *            the activityDate to set
	 */
	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}

	public String createActivity() {
		if ("Measurement".equals(selectedActivityType.getDescription())) { // measurement
			Measure m = new Measure();
			m.setDate(new Timestamp(activityDate.getTime()));
			m.setMeasuretype_id(selectedActivitySubType.getActivitysubtype_id());
			m.setUser(AthleticgisFacade.findUserByUsername(userInfoBean
					.getUsername()));
			if ("Weight".equals(selectedActivitySubType.getDescription())) {
				m.setValue(weight);
			} else if ("Height"
					.equals(selectedActivitySubType.getDescription())) {
				m.setValue(height);
			} else if ("Pulse".equals(selectedActivitySubType.getDescription())) {
				m.setValue(pulse);
			} else if ("Waist".equals(selectedActivitySubType.getDescription())) {
				m.setValue(waist);
			} else if ("Hip".equals(selectedActivitySubType.getDescription())) {
				m.setValue(hip);
			} else if ("Thigh".equals(selectedActivitySubType.getDescription())) {
				m.setValue(thigh);
			} else if ("Systolic".equals(selectedActivitySubType
					.getDescription())) {
				m.setValue(systolic);
			} else if ("Diastolic".equals(selectedActivitySubType
					.getDescription())) {
				m.setValue(diastolic);
			}
			AthleticgisFacade.persist(m);
		} else {
			Activity a = new Activity();

			a.setIndoors(indoors);

			// TODO maybe replace Activity with Activity Type
			if (activityName != null && activityName.length() > 0) {
				a.setName(activityName);
			} else {
				a.setName(getDefaultActivityName());
			}
			a.setUser(AthleticgisFacade.findUserByUsername(userInfoBean
					.getUsername()));
			a.setDate(new Timestamp(activityDate.getTime()));
			if (distance != null) {
				a.setDistance(distance);
			}
			// TODO when get back out will have to check whether startTime <
			// endTime. If other way around assume something like started 23:47
			// and ended 00:15
			a.setStartTime(convertTimeStringToLong(startTime));
			a.setEndTime(convertTimeStringToLong(endTime));

			if (selectedActivityType.getActivitytype_id() != null) {
				a.setActivitytype_id(selectedActivityType.getActivitytype_id());
			}

			if (selectedActivitySubType != null
					&& selectedActivitySubType.getActivitysubtype_id() != null) {
				a.setActivitysubtype_id(selectedActivitySubType
						.getActivitysubtype_id());
			}

			a.setUseMyMap(useMyMap);

			List<ActivityPoint> aps = null;

			if (useMyMap) {
				if (selectedMap != null) {
					// System.out.println(selectedMap.getName());
					// this.mapMapName = selectedMap.getName();
					//
					// List<MyMapMarker> myMapMarkers = AthleticgisFacade
					// .findMyMapMarkersByMymap_id(selectedMap.getMymap_id());
					//
					// GISCalculator calc = new GISCalculator();
					// Double d = calc.computeMarkerPathDistance(myMapMarkers) /
					// 1000;
					// //DecimalFormat df = new DecimalFormat("#.##");
					//
					// this.distance = d;
					List<MyMapMarker> myMapMarkers = AthleticgisFacade
							.findMyMapMarkersByMymap_id(selectedMap
									.getMymap_id());
					if (myMapMarkers != null && myMapMarkers.size() > 0) {

						aps = new ArrayList<ActivityPoint>();
						for (MyMapMarker mp : myMapMarkers) {
							ActivityPoint ap = new ActivityPoint();
							ap.setLatitude(mp.getLatitude());
							ap.setLongitude(mp.getLongitude());
							aps.add(ap);
						}
						// a.setActivitypoints(aps);

					}
				}
			}

			// if(weight != null) {
			// a.setWeight(weight);
			// }
			// TODO persistActivityAndActivityPoints look at this method, sets
			// activity time based on first activity point.
			AthleticgisFacade.persistActivityAndActivityPoints(a, aps);
		}

		return "dashboard?faces-redirect=true";
	}

	private Long convertTimeStringToLong(String strTime) {
		Long time = null;
		String[] arr = strTime.split(":");
		Long hoursInSeconds = Long.parseLong(arr[0]) * 60 * 60;
		Long minutesInSeconds = Long.parseLong(arr[1]) * 60;
		Long seconds = Long.parseLong(arr[2]);
		time = hoursInSeconds + minutesInSeconds + seconds;
		return time;
	}

	/**
	 * @return the activityType
	 */
	public String getActivityType() {
		return activityType;
	}

	/**
	 * @param activityType
	 *            the activityType to set
	 */
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	/**
	 * @return the indoors
	 */
	public boolean isIndoors() {
		return indoors;
	}

	/**
	 * @param indoors
	 *            the indoors to set
	 */
	public void setIndoors(boolean indoors) {
		this.indoors = indoors;
	}

	
	/**
	 * @return the polylineModel
	 */
	public MapModel getPolylineModel() {
		//initialize();
		
		return polylineModel;
	}
	
	public String addMyMap() {

		if (selectedMap != null) {
			initialize();
			//coordinates = "new google.maps.LatLng(45, -90)";
			//coordinates = "[{ \"lat\":43.810909 , \"lng\":-91.256091 }, { \"lat\":43.822571 , \"lng\":-91.243666 }, { \"lat\":43.812151 , \"lng\":-91.257099 }]";
			// System.out.println(selectedMap.getName());
			this.mapMapName = selectedMap.getName();

			List<MyMapMarker> myMapMarkers = AthleticgisFacade
					.findMyMapMarkersByMymap_id(selectedMap.getMymap_id());
			
			StringBuffer sb = new StringBuffer("[");
			int i = 0;
			for(MyMapMarker m : myMapMarkers) {
				sb.append("{\"lat\":");
				sb.append(m.getLatitude());
				sb.append(",\"lng\":");
				sb.append(m.getLongitude());
				if(i < myMapMarkers.size() - 1) {
					sb.append("},");
				} else {
					sb.append("}");
				}
				i++;
			}
			sb.append("]");
			
			coordinates = sb.toString();
			

			GISCalculator calc = new GISCalculator();
			Double d = calc.computeMarkerPathDistance(myMapMarkers) * 0.000621371; // convert
																					// meters
																					// to
																					// miles
			// DecimalFormat df = new DecimalFormat("#.##");

			this.distance = d;
		}
		return null;
	}

	/**
	 * @return the selectedMap
	 */
	public MyMap getSelectedMap() {
		return selectedMap;
	}

	/**
	 * @param selectedMap
	 *            the selectedMap to set
	 */
	public void setSelectedMap(MyMap selectedMap) {
		this.selectedMap = selectedMap;
	}

	/**
	 * @return the mapMapName
	 */
	public String getMapMapName() {
		return mapMapName;
	}

	/**
	 * @param mapMapName
	 *            the mapMapName to set
	 */
	public void setMapMapName(String mapMapName) {
		this.mapMapName = mapMapName;
	}

	/**
	 * @return the useMyMap
	 */
	public boolean isUseMyMap() {
		return useMyMap;
	}

	/**
	 * @param useMyMap
	 *            the useMyMap to set
	 */
	public void setUseMyMap(boolean useMyMap) {
		this.useMyMap = useMyMap;
	}

	public void useMyMapChecked() {
		mapMapName = null;
		//selectedMap = null;
		distance = null;
	}

	/**
	 * @return the userInfoBean
	 */
	public UserInfoBean getUserInfoBean() {
		return userInfoBean;
	}

	/**
	 * @param userInfoBean
	 *            the userInfoBean to set
	 */
	public void setUserInfoBean(UserInfoBean userInfoBean) {
		this.userInfoBean = userInfoBean;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the distance
	 */
	public Double getDistance() {
		return distance;
	}

	/**
	 * @param distance
	 *            the distance to set
	 */
	public void setDistance(Double distance) {
		this.distance = distance;
	}

	/**
	 * @return the weight
	 */
	public Double getWeight() {
		return weight;
	}

	/**
	 * @param weight
	 *            the weight to set
	 */
	public void setWeight(Double weight) {
		this.weight = weight;
	}

	/**
	 * @return the height
	 */
	public Double getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(Double height) {
		this.height = height;
	}

	/**
	 * @return the waist
	 */
	public Double getWaist() {
		return waist;
	}

	/**
	 * @param waist
	 *            the waist to set
	 */
	public void setWaist(Double waist) {
		this.waist = waist;
	}

	/**
	 * @return the hip
	 */
	public Double getHip() {
		return hip;
	}

	/**
	 * @param hip
	 *            the hip to set
	 */
	public void setHip(Double hip) {
		this.hip = hip;
	}

	/**
	 * @return the thigh
	 */
	public Double getThigh() {
		return thigh;
	}

	/**
	 * @param thigh
	 *            the thigh to set
	 */
	public void setThigh(Double thigh) {
		this.thigh = thigh;
	}

	/**
	 * @return the aveHeartRate
	 */
	public Double getAveHeartRate() {
		return aveHeartRate;
	}

	/**
	 * @param aveHeartRate
	 *            the aveHeartRate to set
	 */
	public void setAveHeartRate(Double aveHeartRate) {
		this.aveHeartRate = aveHeartRate;
	}

	/**
	 * @return the calories
	 */
	public Double getCalories() {
		return calories;
	}

	/**
	 * @param calories
	 *            the calories to set
	 */
	public void setCalories(Double calories) {
		this.calories = calories;
	}

	/**
	 * @return the activitySubType
	 */
	public String getActivitySubType() {
		return activitySubType;
	}

	/**
	 * @param activitySubType
	 *            the activitySubType to set
	 */
	public void setActivitySubType(String activitySubType) {
		this.activitySubType = activitySubType;
	}

	/**
	 * @return the activityGear
	 */
	public String getActivityGear() {
		return activityGear;
	}

	/**
	 * @param activityGear
	 *            the activityGear to set
	 */
	public void setActivityGear(String activityGear) {
		this.activityGear = activityGear;
	}

	/**
	 * @return the systolic
	 */
	public Double getSystolic() {
		return systolic;
	}

	/**
	 * @param systolic
	 *            the systolic to set
	 */
	public void setSystolic(Double systolic) {
		this.systolic = systolic;
	}

	/**
	 * @return the diastolic
	 */
	public Double getDiastolic() {
		return diastolic;
	}

	/**
	 * @param diastolic
	 *            the diastolic to set
	 */
	public void setDiastolic(Double diastolic) {
		this.diastolic = diastolic;
	}

	/**
	 * @return the pulse
	 */
	public Double getPulse() {
		return pulse;
	}

	/**
	 * @param pulse
	 *            the pulse to set
	 */
	public void setPulse(Double pulse) {
		this.pulse = pulse;
	}

	/**
	 * @return the activityName
	 */
	public String getActivityName() {
		return activityName;
	}

	/**
	 * @param activityName
	 *            the activityName to set
	 */
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	/**
	 * @return the defaultActivityName
	 */
	public String getDefaultActivityName() {
		String actStr = "Activity";

		if (selectedActivityType != null) {
			actStr = selectedActivityType.getDescription();
		}

		return "My " + actStr + " on " + new Date();
	}

	// /**
	// * @return the activityTypeSelectItems
	// */
	// public List<SelectItem> getActivityTypeSelectItems() {
	// if(activityTypeSelectItems == null) {
	// List<ActivityType> activityTypes =
	// AthleticgisFacade.findAllActivityType();
	// activityTypeSelectItems = new ArrayList<SelectItem>();
	// for(ActivityType at : activityTypes) {
	// activityTypeSelectItems.add(new SelectItem(at.getActivitytype_id(),
	// at.getDescription()));
	// }
	// }
	// return activityTypeSelectItems;
	// }
	//
	// /**
	// * @param activityTypeSelectItems the activityTypeSelectItems to set
	// */
	// public void setActivityTypeSelectItems(List<SelectItem>
	// activityTypeSelectItems) {
	// this.activityTypeSelectItems = activityTypeSelectItems;
	// }

	// /**
	// * @return the activitySubTypeSelectItems
	// */
	// public List<SelectItem> getActivitySubTypeSelectItems() {
	// if(selectedActivityType != null &&
	// selectedActivityType.getActivitytype_id() != null) {
	// List<ActivitySubType> activitySubTypes =
	// AthleticgisFacade.findAllActivitySubTypeByActivitytype_id(selectedActivityType.getActivitytype_id());
	// activitySubTypeSelectItems = new ArrayList<SelectItem>();
	// for(ActivitySubType ast : activitySubTypes) {
	// activitySubTypeSelectItems.add(new
	// SelectItem(ast.getActivitysubtype_id(), ast.getDescription()));
	// }
	// } else {
	// return null;
	// }
	// return activitySubTypeSelectItems;
	// }

	/**
	 * @return the selectedActivityType
	 */
	public ActivityType getSelectedActivityType() {
		return selectedActivityType;
	}

	/**
	 * @param selectedActivityType
	 *            the selectedActivityType to set
	 */
	public void setSelectedActivityType(ActivityType selectedActivityType) {
		this.selectedActivityType = selectedActivityType;
	}

	/**
	 * @return the activityTypes
	 */
	public List<ActivityType> getActivityTypes() {
		if (activityTypes == null) {
			activityTypes = AthleticgisFacade.findAllActivityType();
		}

		return activityTypes;
	}

	/**
	 * @param activityTypes
	 *            the activityTypes to set
	 */
	public void setActivityTypes(List<ActivityType> activityTypes) {
		this.activityTypes = activityTypes;
	}

	/**
	 * @return the selectedActivitySubType
	 */
	public ActivitySubType getSelectedActivitySubType() {
		return selectedActivitySubType;
	}

	/**
	 * @param selectedActivitySubType
	 *            the selectedActivitySubType to set
	 */
	public void setSelectedActivitySubType(
			ActivitySubType selectedActivitySubType) {
		this.selectedActivitySubType = selectedActivitySubType;
	}

	/**
	 * @return the activitySubTypes
	 */
	public List<ActivitySubType> getActivitySubTypes() {
		if (selectedActivityType != null
				&& selectedActivityType.getActivitytype_id() != null) {
			activitySubTypes = AthleticgisFacade
					.findAllActivitySubTypeByActivitytype_id(selectedActivityType
							.getActivitytype_id());
		} else {
			return null;
		}
		return activitySubTypes;
	}

	/**
	 * @param activitySubTypes
	 *            the activitySubTypes to set
	 */
	public void setActivitySubTypes(List<ActivitySubType> activitySubTypes) {
		this.activitySubTypes = activitySubTypes;
	}

	/**
	 * @return the coordinates
	 */
	public String getCoordinates() {
		return coordinates;
	}

	/**
	 * @param coordinates the coordinates to set
	 */
	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	

}