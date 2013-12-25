package com.fitdrift.view;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.fitdrift.domain.activity.Activity;
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
	private boolean indoors;
	private boolean useMyMap;
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

	/**
	 * @return the activityDate
	 */
	public Date getActivityDate() {
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
		Activity a = new Activity();
		//TODO maybe replace Activity with Activity Type
		a.setName("My Activity on " + activityDate);
		a.setUser(AthleticgisFacade.findUserByUsername(userInfoBean.getUsername()));
		a.setDate(new Timestamp(activityDate.getTime()));
		if(distance != null) {
			a.setDistance(distance);
		}
		//TODO when get back out will have to check whether startTime < endTime. If other way around assume something like started 23:47 and ended 00:15 
		a.setStartTime(convertTimeStringToLong(startTime));
		a.setEndTime(convertTimeStringToLong(endTime));
		if(weight != null) {
			a.setWeight(weight);
		}
		//TODO persistActivityAndActivityPoints look at this method, sets activity time based on first activity point. 
		AthleticgisFacade.persistActivityAndActivityPoints(a, null);
		
			
		return "dashboard?faces-redirect=true";
	}
	
	private Long convertTimeStringToLong(String strTime) {
		Long time = null;
		String[] arr = strTime.split(":");
		Long hoursInSeconds = Long.parseLong(arr[0])*60*60;
		Long minutesInSeconds = Long.parseLong(arr[1])*60;
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

	public String addMyMap() {

		if (selectedMap != null) {
			System.out.println(selectedMap.getName());
			this.mapMapName = selectedMap.getName();

			List<MyMapMarker> myMapMarkers = AthleticgisFacade
					.findMyMapMarkersByMymap_id(selectedMap.getMymap_id());

			GISCalculator calc = new GISCalculator();
			Double d = calc.computeMarkerPathDistance(myMapMarkers) / 1000;
			//DecimalFormat df = new DecimalFormat("#.##");

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
		selectedMap = null;
		distance = null;
	}

	/**
	 * @return the userInfoBean
	 */
	public UserInfoBean getUserInfoBean() {
		return userInfoBean;
	}

	/**
	 * @param userInfoBean the userInfoBean to set
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
	 * @param startTime the startTime to set
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
	 * @param endTime the endTime to set
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
	 * @param distance the distance to set
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
	 * @param weight the weight to set
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
	 * @param height the height to set
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
	 * @param waist the waist to set
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
	 * @param hip the hip to set
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
	 * @param thigh the thigh to set
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
	 * @param aveHeartRate the aveHeartRate to set
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
	 * @param calories the calories to set
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
	 * @param activitySubType the activitySubType to set
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
	 * @param activityGear the activityGear to set
	 */
	public void setActivityGear(String activityGear) {
		this.activityGear = activityGear;
	}

	
}