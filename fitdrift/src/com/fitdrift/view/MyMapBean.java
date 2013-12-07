package com.fitdrift.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.LazyDataModel;

import com.fitdrift.domain.activity.MyMap;
import com.fitdrift.model.AthleticgisFacade;
import com.fitdrift.view.model.MyMapDataModel;

import java.io.Serializable;
import java.util.Map;

@ManagedBean
@ViewScoped
public class MyMapBean implements Serializable {
	private static final long serialVersionUID = 7887424442895953028L;
	LazyDataModel<MyMap> lazyModel;
	//Activity selectedActivity;
	
	@ManagedProperty(value = "#{userInfoBean}")
    private UserInfoBean userInfoBean;
	
	public UserInfoBean getUserInfoBean() {
		return userInfoBean;
	}

	public void setUserInfoBean(UserInfoBean userInfoBean) {
		this.userInfoBean = userInfoBean;
	}
	
//	/**
//	 * @return the selectedActivity
//	 */
//	public Activity getSelectedActivity() {
//		return selectedActivity;
//	}
//
//	/**
//	 * @param selectedActivity the selectedActivity to set
//	 */
//	public void setSelectedActivity(Activity selectedActivity) {
//		this.selectedActivity = selectedActivity;
//	}
	
	/**
	 * @return the lazyModel
	 */
	public LazyDataModel<MyMap> getLazyModel() {
		if (lazyModel == null) {
			lazyModel = new MyMapDataModel(userInfoBean.getUser_id(), userInfoBean.getIsAdmin());
		}
		return lazyModel;
		//return new ActivityDataModel(userInfoBean.getUser_id());
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
	public String removeMyMap() {
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String mId = params.get("mymap_id");
		Long id = Long.parseLong(mId);
		
		AthleticgisFacade.removeMyMap(id);
		
		return "mymaps?faces-redirect=true";
	}
}
