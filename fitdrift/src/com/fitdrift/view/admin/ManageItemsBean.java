package com.fitdrift.view.admin;

import java.io.Serializable;
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
public class ManageItemsBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8346671629203770523L;

	private String activityTypeDescription;
	
	private String activitySubTypeDescription;
	
	
	//private List<SelectItem> activitySelectItems = new ArrayList<SelectItem>();
	private List<ActivityType> activityTypes;
	//private Long activityTypeId;
	private ActivityType selectedActivityType;
	
//	public void onChangeValue(ValueChangeEvent e) {
//		System.out.println(e.getNewValue());
//	}
	
	public List<ActivityType> getActivityTypes() {
		if(activityTypes == null) {
			activityTypes = AthleticgisFacade.findAllActivityType(); 
//			for(ActivityType at : activityTypes) {
//				activitySelectItems.add(new SelectItem(at.getActivitytype_id(), at.getDescription()));
//			}
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
		if(selectedActivityType != null) {
			return AthleticgisFacade.findAllActivitySubTypeByActivitytype_id(selectedActivityType.getActivitytype_id());
		} else {
			return null;
		}
	}
	
	
	
	public void addActivityType(ActionEvent e) {
		//selectedActivityType = null;
		ActivityType at = new ActivityType();
		at.setDescription(activityTypeDescription);
		AthleticgisFacade.persist(at);
		//return null;
		activityTypes = AthleticgisFacade.findAllActivityType(); 
	//	activitySelectItems.clear();
//		for(ActivityType a : activityTypes) {
//			activitySelectItems.add(new SelectItem(a.getActivitytype_id(), a.getDescription()));
//		}
	}
	
	public void addActivitySubType(ActionEvent event) {
		ActivitySubType ast = new ActivitySubType();
		ast.setActivitytype_id(selectedActivityType.getActivitytype_id());
		ast.setDescription(activitySubTypeDescription);
		AthleticgisFacade.persist(ast);
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

//	/**
//	 * @return the activitySelectItems
//	 */
//	public List<SelectItem> getActivitySelectItems() {
//		return activitySelectItems;
//	}
//
//	/**
//	 * @param activitySelectItems the activitySelectItems to set
//	 */
//	public void setActivitySelectItems(List<SelectItem> activitySelectItems) {
//		this.activitySelectItems = activitySelectItems;
//	}

//	/**
//	 * @return the activityTypeId
//	 */
//	public Long getActivityTypeId() {
//		return activityTypeId;
//	}
//
//	/**
//	 * @param activityTypeId the activityTypeId to set
//	 */
//	public void setActivityTypeId(Long activityTypeId) {
//		this.activityTypeId = activityTypeId;
//	}

	/**
	 * @return the selectedActivityType
	 */
	public ActivityType getSelectedActivityType() {
		return selectedActivityType;
	}

	/**
	 * @param selectedActivityType the selectedActivityType to set
	 */
	public void setSelectedActivityType(ActivityType selectedActivityType) {
		this.selectedActivityType = selectedActivityType;
	}

}
