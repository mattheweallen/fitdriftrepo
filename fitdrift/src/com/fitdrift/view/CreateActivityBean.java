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
	private boolean indoors;
	private boolean useMyMap;
	private MyMap selectedMap;
	private String mapMapName;
	private String distance;
	@ManagedProperty(value = "#{userInfoBean}")
    private UserInfoBean userInfoBean;
	private String startTime;
	private String endTime;
	private String weight;

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
		if(distance != null && distance.length() > 0) {
			a.setDistance(Double.parseDouble(distance));
		}
		//TODO when get back out will have to check whether startTime < endTime. If other way around assume something like started 23:47 and ended 00:15 
		a.setStartTime(convertTimeStringToLong(startTime));
		a.setEndTime(convertTimeStringToLong(endTime));
		if(weight != null && weight.length() > 0) {
			a.setWeight(Double.parseDouble(weight));
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
			Double distance = calc.computeMarkerPathDistance(myMapMarkers) / 1000;
			DecimalFormat df = new DecimalFormat("#.##");

			this.distance = df.format(distance);
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
		distance = null;
	}

	/**
	 * @return the distance
	 */
	public String getDistance() {
		return distance;
	}

	/**
	 * @param distance
	 *            the distance to set
	 */
	public void setDistance(String distance) {
		this.distance = distance;
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
	 * @return the weight
	 */
	public String getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(String weight) {
		this.weight = weight;
	}
}