package com.fitdrift.view;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.fitdrift.domain.activity.ActivityType;

@FacesConverter("com.fitdrift.view.ActivityTypeConverter")
public class ActivityTypeConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		ActivityType activityType = null;
		//System.out.println(arg1);
		//System.out.println(arg2);
		
		if (arg2 != null && arg2.length() > 2) {
			String[] arr = arg2.split(":");
			
			activityType = new ActivityType();
			activityType.setActivitytype_id(Long.parseLong(arr[0]));
			activityType.setDescription(arr[1]);

			// use Apache common URL validator to validate URL
			//UrlValidator urlValidator = new UrlValidator();
			// if URL is invalid
//			if (!urlValidator.isValid(url.toString())) {
//
//				FacesMessage msg = new FacesMessage("URL Conversion error.",
//						"Invalid URL format.");
//				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
//				throw new ConverterException(msg);
//			}

			
		}

		return activityType;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return arg2.toString();
	}

}
