package com.fitdrift.view;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;  
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;  
import javax.faces.event.ActionEvent;  

import com.fitdrift.domain.activity.MyMap;
import com.fitdrift.domain.activity.MyMapMarker;
import com.fitdrift.model.AthleticgisFacade;
import com.fitdrift.util.gis.GISCalculator;

/**
 * Managed Bean for createpath.xhtml
 * Primary purpose is to hold user defined markers on map.
 * 
 * @author Matthew Allen
 * @version 20131203
 *
 */
@ManagedBean
@ViewScoped
public class CreatePathBean implements Serializable {
	private static final long serialVersionUID = -8907242457718889989L;
	private List<MyMapMarker> myMapMarkers = new ArrayList<MyMapMarker>();
    private String title;  
    private double lat;  
    private double lng; 
    private String myMapName;
    @ManagedProperty(value = "#{userInfoBean}")
	private UserInfoBean userInfoBean;

	/**
	 * @return the myMapName
	 */
	public String getMyMapName() {
		return myMapName;
	}

	/**
	 * @param myMapName the myMapName to set
	 */
	public void setMyMapName(String myMapName) {
		this.myMapName = myMapName;
	}

    /**
	 * @return the userInfoBean
	 */
	public UserInfoBean getUserInfoBean() {
		return userInfoBean;
	}

	/**
	 * @param userInfoBean the userInfoBean to set
	 */
	public void setUserInfoBean(UserInfoBean userInfoBean) {
		this.userInfoBean = userInfoBean;
	}
    
	/**
	 * add a new message to the FacesContext.
	 * 
	 * @param message
	 */
    public void addMessage(FacesMessage message) {  
        FacesContext.getCurrentInstance().addMessage(null, message);  
    }  
      
    public String getTitle() {  
        return title;  
    }  
  
    public void setTitle(String title) {  
        this.title = title;  
    }  
  
    public double getLat() {  
        return lat;  
    }  
  
    public void setLat(double lat) {  
        this.lat = lat;  
    }  
  
    public double getLng() {  
        return lng;  
    }  
  
    public void setLng(double lng) {  
        this.lng = lng;  
    }  
    
    /**
     * Responds to event where user adds a new marker to map.
     * addMarker to myMarkers and update distance.
     * 
     * @param actionEvent
     */
    public void addMarker(ActionEvent actionEvent) {  
        MyMapMarker m = new MyMapMarker();  
        m.setLatitude(lat);
        m.setLongitude(lng);
        
        //this is necessary elevation is used in calculating distance
        //hopefully will get elevation from google service, will make distance more accurate
        m.setElevation(0.0);
        
        m.setName(title);
        m.setTime(new Timestamp(new Date().getTime()));
        myMapMarkers.add(m);
        GISCalculator calc = new GISCalculator();
        Double distance = calc.computeMarkerPathDistance(myMapMarkers)*0.000621371; //convert meters to miles
        DecimalFormat df = new DecimalFormat("#.##");
        addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Point Added", "Lat:" + df.format(lat) + ", Lng:" + df.format(lng) + ". Your total distance is " + df.format(distance) + " miles."));  
    }
    
    /**
     * This is the location where the map will be centered at the beginning of creating a map.
     * 
     * @return
     */
    public String getCenter() {
		// could maybe figure out a center based on users ip to get an approximate location.
		return "43.83193516,-91.22337865";
	}
    
    /**
     * 
     * @return
     */
    public String savePath() {
    	
    	if(myMapMarkers != null && myMapMarkers.size() > 0) {
    		MyMap myMap = new MyMap();
	    	myMap.setUser(AthleticgisFacade.findUserByUsername(userInfoBean.getUsername()));
	    	if(myMapName != null && myMapName.length() > 0) {
	    		myMap.setName(myMapName);
	    	} else {
	    		myMap.setName("My Map Created " +(new Date()).toString());
	    	}
	    	AthleticgisFacade.persistMapAndMyMapMarkers(myMap, myMapMarkers);
    	}
    	return "mymaps?faces-redirect=true";
    }
    
    /**
     * 
     * @return
     */
    public String restartPath() {
    	myMapMarkers = new ArrayList<MyMapMarker>();
    	return "createpath?faces-redirect=true";
    }
}
