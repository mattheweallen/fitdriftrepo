package com.fitdrift.view.admin;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import com.fitdrift.domain.activity.ActivitySubType;
import com.fitdrift.domain.activity.ActivityType;
import com.fitdrift.domain.activity.Equipment;
import com.fitdrift.model.AthleticgisFacade;

@ManagedBean
@ViewScoped
public class ManageItemsBean {
	private String activityTypeDescription;
	
	private String activitySubTypeDescription;
	
	
	private List<SelectItem> activitySelectItems = new ArrayList<SelectItem>();
	private List<ActivityType> activityTypes;
	private Long activityTypeId;
	
	public List<ActivityType> getActivityTypes() {
		if(activityTypes == null) {
			activityTypes = AthleticgisFacade.findAllActivityType(); 
			for(ActivityType at : activityTypes) {
				activitySelectItems.add(new SelectItem(at.getActivitytype_id(), at.getDescription()));
			}
		}
		return activityTypes;
	}
	
	/**
	 * @param activityTypes the activityTypes to set
	 */
	public void setActivityTypes(List<ActivityType> activityTypes) {
		this.activityTypes = activityTypes;
	}
	
	public List<ActivitySubType> getActivitySubTypes() {
		if(activityTypeId != null) {
			return AthleticgisFacade.findAllActivitySubTypeByActivitytype_id(activityTypeId);
		} else {
			return null;
		}
	}
	
	
	
	public void addActivityType(ActionEvent e) {
		//selectedActivityType = null;
		ActivityType at = new ActivityType();
		at.setDescription(activityTypeDescription);
		AthleticgisFacade.persistActivityType(at);
		//return null;
		activityTypes = AthleticgisFacade.findAllActivityType(); 
	}
	
	public void addActivitySubType(ActionEvent event) {
		ActivitySubType ast = new ActivitySubType();
		ast.setActivitytype_id(activityTypeId);
		ast.setDescription(activitySubTypeDescription);
		AthleticgisFacade.persistActivitySubType(ast);
		//return null;
	}
	
	

	/**
	 * @return the activityType
	 */
	public String getActivityTypeDescription() {
		return activityTypeDescription;
	}

	/**
	 * @param activityType the activityType to set
	 */
	public void setActivityTypeDescription(String activityTypeDescription) {
		this.activityTypeDescription = activityTypeDescription;
	}



	/**
	 * @return the activitySubTypeDescription
	 */
	public String getActivitySubTypeDescription() {
		return activitySubTypeDescription;
	}

	/**
	 * @param activitySubTypeDescription the activitySubTypeDescription to set
	 */
	public void setActivitySubTypeDescription(String activitySubTypeDescription) {
		this.activitySubTypeDescription = activitySubTypeDescription;
	}

	/**
	 * @return the activitySelectItems
	 */
	public List<SelectItem> getActivitySelectItems() {
		return activitySelectItems;
	}

	/**
	 * @param activitySelectItems the activitySelectItems to set
	 */
	public void setActivitySelectItems(List<SelectItem> activitySelectItems) {
		this.activitySelectItems = activitySelectItems;
	}

	/**
	 * @return the activityTypeId
	 */
	public Long getActivityTypeId() {
		return activityTypeId;
	}

	/**
	 * @param activityTypeId the activityTypeId to set
	 */
	public void setActivityTypeId(Long activityTypeId) {
		this.activityTypeId = activityTypeId;
	}

}
