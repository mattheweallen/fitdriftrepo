package com.fitdrift.view.admin;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.fitdrift.domain.activity.ActivityType;
import com.fitdrift.model.AthleticgisFacade;

@ManagedBean
@ViewScoped
public class ManageItemsBean {
	public List<ActivityType> getActivityTypes() {
		return AthleticgisFacade.findAllActivityType();
	}
}
