package com.fitdrift.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import org.primefaces.model.LazyDataModel;

import com.fitdrift.domain.activity.Activity;
import com.fitdrift.domain.activity.ActivitySubType;
import com.fitdrift.domain.activity.Measure;
import com.fitdrift.model.AthleticgisFacade;
import com.fitdrift.util.ViewUtil;
import com.fitdrift.view.model.ActivityDataModel;
import com.fitdrift.view.model.MeasureDataModel;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This bean is behind the dashboard page. Its primary purpose is to create a 
 * paginated lazy data model.
 * 
 * @author Matthew Allen
 * @version 20131208
 */
@ManagedBean
@ViewScoped
public class DashboardBean implements Serializable {
	private static final long serialVersionUID = 1L;
	//List<Activity> activities;
	LazyDataModel<Activity> lazyModel;
	LazyDataModel<Measure> lazyMeasureModel;
	Activity selectedActivity;
	@ManagedProperty(value = "#{userInfoBean}")
    private UserInfoBean userInfoBean;
	private DashboardModel model; 
	private List<ActivitySubType> activitySubTypes;
	private ActivitySubType selectedActivitySubType;
	private Double measureValue;
	private List<Object[]> weeklySummary;
	
	public DashboardBean() {  
        model = new DefaultDashboardModel();  
        DashboardColumn column1 = new DefaultDashboardColumn();  
        DashboardColumn column2 = new DefaultDashboardColumn();  
       // DashboardColumn column3 = new DefaultDashboardColumn();  
        
        //column1.addWidget("weather");
        //column1.addWidget("theme");
        column1.addWidget("weeklyActivitySummary");
        column1.addWidget("activities");  
        column1.addWidget("measures");  
//        column1.addWidget("finance");  
//          
        column2.addWidget("theme");
        column2.addWidget("measureWidget");
        
        //heatMap
       // column2.addWidget("heatMap");
       // column2.addWidget("lifestyle");  
          
//          
//        column3.addWidget("politics");  
//  
        model.addColumn(column1);  
        model.addColumn(column2);  
//        model.addColumn(column3);  
    } 
	
	public String saveMeasure() {
		Measure m = new Measure();
		m.setDate(new Timestamp(new Date().getTime()));
		m.setActivitySubType(selectedActivitySubType);
		m.setUser(AthleticgisFacade.findUserByUsername(userInfoBean
				.getUsername()));
		
		m.setValue(measureValue);
		
		AthleticgisFacade.persist(m);
		
		selectedActivitySubType = null;
		measureValue = null;
		
		
		return null;
	}
	
	public UserInfoBean getUserInfoBean() {
		return userInfoBean;
	}

	public void setUserInfoBean(UserInfoBean userInfoBean) {
		this.userInfoBean = userInfoBean;
	}
	
	/**
	 * @return the selectedActivity
	 */
	public Activity getSelectedActivity() {
		return selectedActivity;
	}

	/**
	 * @param selectedActivity the selectedActivity to set
	 */
	public void setSelectedActivity(Activity selectedActivity) {
		this.selectedActivity = selectedActivity;
	}
	
	/**
	 * @return the lazyModel
	 */
	public LazyDataModel<Activity> getLazyModel() {
		if (lazyModel == null) {
			lazyModel = new ActivityDataModel(userInfoBean.getUser_id(), userInfoBean.getIsAdmin());
		}
		return lazyModel;
		//return new ActivityDataModel(userInfoBean.getUser_id());
	}
	
	/**
	 * @return the lazyModel
	 */
	public LazyDataModel<Measure> getLazyMeasureModel() {
		if (lazyMeasureModel == null) {
			lazyMeasureModel = new MeasureDataModel(userInfoBean.getUser_id(), userInfoBean.getIsAdmin());
		}
		return lazyMeasureModel;
		//return new ActivityDataModel(userInfoBean.getUser_id());
	}

	/**
	 * @return the model
	 */
	public DashboardModel getModel() {
		return model;
	}

//	public List<Activity> getActivities() {
//		//return activityModel.getActivities();
//		//AthleticgisFacade athleticgisFacade = new AthleticgisFacade();
//		//hard coded user_id use UserInfo
//		//fix, show all for admin, show particular for non admin
//		
//		//attempt at lazy loading
//		//if(activities == null || activities.size() == 0) {
//			if(!userInfoBean.getIsAdmin()) {
//				activities = AthleticgisFacade.findActivitiesByUserId(userInfoBean.getUser_id());
//			} else {
//				activities = AthleticgisFacade.findAllActivities();
//			}
//		//}
//		return activities;
//	}
	
	
//	public void fileUploadListener(FileUploadEvent event) throws Exception {
//		UploadedFile item = event.getUploadedFile();
//	    File file = new File();
//	    file.setLength(item.getData().length);
//	    file.setName(item.getName());
//	    file.setData(item.getData());
//	    files.add(file);
//	    uploadsAvailable--;
//	}
//	public void setActivityName(List<Activity> activities) {
//		this.activities = activities;
//	}
	
	/**
	 * @return the activitySubTypes
	 */
	public List<ActivitySubType> getActivitySubTypes() {
		if(activitySubTypes == null) {
			activitySubTypes = AthleticgisFacade
					.findAllActivitySubTypeByActivitytype_id(2L); //2L is Measurement ActvityType
		}
		return activitySubTypes;
	}

	/**
	 * @return the selectedActivitySubType
	 */
	public ActivitySubType getSelectedActivitySubType() {
		return selectedActivitySubType;
	}

	/**
	 * @param selectedActivitySubType the selectedActivitySubType to set
	 */
	public void setSelectedActivitySubType(ActivitySubType selectedActivitySubType) {
		this.selectedActivitySubType = selectedActivitySubType;
	}

	/**
	 * @return the measureValue
	 */
	public Double getMeasureValue() {
		return measureValue;
	}

	/**
	 * @param measureValue the measureValue to set
	 */
	public void setMeasureValue(Double measureValue) {
		this.measureValue = measureValue;
	}

	/**
	 * @return the weeklySummary
	 */
	public List<Object[]> getWeeklySummary() {
		if(weeklySummary == null) {
			Timestamp endDate = new Timestamp(new Date().getTime());
			Timestamp startDate = new Timestamp(new Date().getTime() - 1000*60*60*24*7); //subtract 7 days from current date
			//Timestamp startDate = new Timestamp(0L);
			List<Object[]> summary = AthleticgisFacade.summarizeActivityByUserByTime(userInfoBean.getUser().getUser_id(), startDate, endDate);
			weeklySummary = new ArrayList<Object[]>();
			for(Object[] o : summary) {			
				Object[] objArr = new Object[3];
				objArr[0] = o[0];
				objArr[1] = ViewUtil.convertLongTimeToString((Long)o[1]);
				//objArr[2] = ViewUtil.convertLongTimeToString((Long)o[2]);
				weeklySummary.add(objArr);
			}
		}
		return weeklySummary;
	}
}
