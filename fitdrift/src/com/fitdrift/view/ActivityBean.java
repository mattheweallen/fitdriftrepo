package com.fitdrift.view;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Polyline;

import com.fitdrift.domain.activity.ActivityPoint;
import com.fitdrift.model.AthleticgisFacade;
import com.fitdrift.util.gis.GISCalculator;

/**
 * This class is used as a model for pagination for table in mymaps page.
 * 
 * @author Matthew Allen
 * @version 20131207
 *
 */
@ManagedBean
@ViewScoped
public class ActivityBean implements Serializable {
	private static final long serialVersionUID = -4430301657324644250L;
	String activityName;
	private MapModel polylineModel = new DefaultMapModel();
	@ManagedProperty(value = "#{request.getParameter('activityId')}")
    private String activityId;
	private Boolean emptyMap = false;
	
	@PostConstruct
    public void initialize() {
		Polyline polyline = new Polyline();
		polyline.setStrokeWeight(2);
		polyline.setStrokeColor("#FF0000");
		polyline.setStrokeOpacity(1.0);
		List<ActivityPoint> activityPoints = AthleticgisFacade
				.findActivityPointsByActivityId(Long.parseLong(activityId));
		
		if(activityPoints != null && activityPoints.size() > 0) {
			for (ActivityPoint ap : activityPoints) {
				polyline.getPaths().add(
						new LatLng(ap.getLatitude(), ap.getLongitude()));
			}
			GISCalculator calc = new GISCalculator();
		    Double distance = calc.computePathDistance(activityPoints)/1000;
		    DecimalFormat df = new DecimalFormat("#.##");
		    addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Distance", "Your total distance is " + df.format(distance) + " miles."));
			
			polylineModel.addOverlay(polyline);
		} else {
			emptyMap = true;
		}
	}
	
	public MapModel getPolylineModel() {
		
		return polylineModel;
	}

	public void onPolylineSelect(OverlaySelectEvent event) {
		addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Polyline Selected", null));
	}

	public void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getActivityName() {
		if(activityId != null) {
			activityName = AthleticgisFacade.findActivityById(Long.parseLong(activityId)).getName();
		}
		return activityName;
	}
	
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	
	public String updateActivity() {
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String aId = params.get("activityId");
		Long id = Long.parseLong(aId);
		if(activityName != null && activityName.length() > 0) {
			AthleticgisFacade.mergeActivity(activityName, id);
		}
		return "dashboard?faces-redirect=true";
	}
	
	public String removeActivity() {
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String aId = params.get("activityId");
		Long id = Long.parseLong(aId);
		
		AthleticgisFacade.removeActivity(id);
		return "dashboard?faces-redirect=true";
	}

	/**
	 * @return the emptyMap
	 */
	public Boolean getEmptyMap() {
		return emptyMap;
	}

	/**
	 * @param emptyMap the emptyMap to set
	 */
	public void setEmptyMap(Boolean emptyMap) {
		this.emptyMap = emptyMap;
	}
}
