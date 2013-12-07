package com.fitdrift.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Polyline;

import com.fitdrift.domain.activity.ActivityPoint;
import com.fitdrift.model.AthleticgisFacade;
import com.fitdrift.util.gis.GISCalculator;

@ManagedBean
@RequestScoped
public class MapBean implements Serializable {
	private static final long serialVersionUID = 6904408126194392139L;

	private MapModel polylineModel = new DefaultMapModel();
	@ManagedProperty(value = "#{request.getParameter('activityId')}")
	private String activityId;

	public MapBean() {
		// polylineModel = new DefaultMapModel();

		// //Shared coordinates
		// LatLng coord1 = new LatLng(36.879466, 30.667648);
		// LatLng coord2 = new LatLng(36.883707, 30.689216);
		// LatLng coord3 = new LatLng(36.879703, 30.706707);
		// LatLng coord4 = new LatLng(36.885233, 30.702323);
		//
		// //Polyline
		// Polyline polyline = new Polyline();
		// polyline.getPaths().add(coord1);
		// polyline.getPaths().add(coord2);
		// polyline.getPaths().add(coord3);
		// polyline.getPaths().add(coord4);
		//
		// polyline.setStrokeWeight(2);
		// polyline.setStrokeColor("#FF0000");
		// polyline.setStrokeOpacity(1.0);
		//
		// polylineModel.addOverlay(polyline);
	}
	
	@PostConstruct
    public void initialize() {
		Polyline polyline = new Polyline();
		polyline.setStrokeWeight(2);
		polyline.setStrokeColor("#FF0000");
		polyline.setStrokeOpacity(1.0);
		List<ActivityPoint> activityPoints = AthleticgisFacade
				.findActivityPointsByActivityId(Long.parseLong(activityId));
		for (ActivityPoint ap : activityPoints) {
			polyline.getPaths().add(
					new LatLng(ap.getLatitude(), ap.getLongitude()));
		}
		GISCalculator calc = new GISCalculator();
	    Double distance = calc.computePathDistance(activityPoints)/1000;
	    DecimalFormat df = new DecimalFormat("#.##");
	    addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Distance", "Your total distance is " + df.format(distance) + " km."));
		
		polylineModel.addOverlay(polyline);
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

	public String getCenter() {
		// did an average of the coordinates in getCoordinates
		return "43.83193516,-91.22337865";
	}

	/**
	 * @return the activityId
	 */
	public String getActivityId() {
		return activityId;
	}

	/**
	 * @param activityId
	 *            the activityId to set
	 */
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
}