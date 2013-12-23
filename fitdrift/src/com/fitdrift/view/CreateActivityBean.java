package com.fitdrift.view;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

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
public class CreateActivityBean {
	private Date activityDate;
	private String activityType;
	private boolean indoors;
	private boolean useMyMap;
	private MyMap selectedMap;
	private String mapMapName;
	private String distance;
	

	/**
	 * @return the activityDate
	 */
	public Date getActivityDate() {
		return activityDate;
	}

	/**
	 * @param activityDate the activityDate to set
	 */
	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}
	
	public String createActivity() {
		return "createactivity";
	}

	/**
	 * @return the activityType
	 */
	public String getActivityType() {
		return activityType;
	}

	/**
	 * @param activityType the activityType to set
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
	 * @param indoors the indoors to set
	 */
	public void setIndoors(boolean indoors) {
		this.indoors = indoors;
	}
	
	public String addMyMap() {
		
		
		if(selectedMap != null) {
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
	 * @param selectedMap the selectedMap to set
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
	 * @param mapMapName the mapMapName to set
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
	 * @param useMyMap the useMyMap to set
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
	 * @param distance the distance to set
	 */
	public void setDistance(String distance) {
		this.distance = distance;
	}
}
