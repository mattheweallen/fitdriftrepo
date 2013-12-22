package com.fitdrift.view;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * This class supports the createactivity.xhtml
 * 
 * @author Matthew Allen
 * @version 20131217
 *
 */
@ManagedBean
@RequestScoped
public class CreateActivityBean {
	private Date activityDate;
	private String activityType;
	private boolean indoors;

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
}
