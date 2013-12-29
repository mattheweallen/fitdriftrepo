package com.fitdrift.view;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.fitdrift.domain.activity.ActivitySubType;

@FacesConverter("com.fitdrift.view.ActivitySubTypeConverter")
public class ActivitySubTypeConverter implements Converter {
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		ActivitySubType activitySubType = null;
		
		if (arg2 != null && arg2.length() > 4) {
			String[] arr = arg2.split(":");
			
			activitySubType = new ActivitySubType();
			activitySubType.setActivitysubtype_id(Long.parseLong(arr[0]));
			activitySubType.setActivitytype_id(Long.parseLong(arr[1]));
			activitySubType.setDescription(arr[2]);			
		}

		return activitySubType;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return arg2.toString();
	}

}
