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
import com.fitdrift.view.model.ActivityDataModel;

import java.io.Serializable;

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
	Activity selectedActivity;
	@ManagedProperty(value = "#{userInfoBean}")
    private UserInfoBean userInfoBean;
	private DashboardModel model; 
	
	public DashboardBean() {  
        model = new DefaultDashboardModel();  
        DashboardColumn column1 = new DefaultDashboardColumn();  
       // DashboardColumn column2 = new DefaultDashboardColumn();  
       // DashboardColumn column3 = new DefaultDashboardColumn();  
        
        column1.addWidget("weather");
        column1.addWidget("activities");  
//        column1.addWidget("finance");  
//          
//        column2.addWidget("lifestyle");  
          
//          
//        column3.addWidget("politics");  
//  
        model.addColumn(column1);  
//        model.addColumn(column2);  
//        model.addColumn(column3);  
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
}
