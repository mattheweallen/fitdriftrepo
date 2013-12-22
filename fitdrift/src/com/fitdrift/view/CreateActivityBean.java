package com.fitdrift.view;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import com.fitdrift.domain.activity.MyMap;


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
	private MyMap selectedMap;
	private String mapMapName;
	

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
}
