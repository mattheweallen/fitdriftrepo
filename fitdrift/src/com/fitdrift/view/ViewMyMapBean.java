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

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.model.map.Polyline;

import com.fitdrift.domain.activity.MyMapMarker;
import com.fitdrift.model.AthleticgisFacade;
import com.fitdrift.util.gis.GISCalculator;

/**
 * This bean builds maps for previously user defined maps. 
 * 
 * @author Matthew Allen
 * @version 20131208
 */
@ManagedBean
@ViewScoped
public class ViewMyMapBean implements Serializable {
        private static final long serialVersionUID = -4430301657324644250L;
        @ManagedProperty(value = "#{request.getParameter('mymap_id')}")
        private String mymap_id;
        private MapModel polylineModel = new DefaultMapModel();
        private String myMapName;

        @PostConstruct
        public void initialize() {
                Polyline polyline = new Polyline();
                polyline.setStrokeWeight(2);
                polyline.setStrokeColor("#FF0000");
                polyline.setStrokeOpacity(1.0);
                List<MyMapMarker> myMapMarkers = AthleticgisFacade
                                .findMyMapMarkersByMymap_id(Long.parseLong(mymap_id));
                for (MyMapMarker m : myMapMarkers) {
                        polyline.getPaths().add(
                                        new LatLng(m.getLatitude(), m.getLongitude()));

                        // add markers
                        Marker marker = new Marker(new LatLng(m.getLatitude(),
                                        m.getLongitude()), m.getName());
                        polylineModel.addOverlay(marker);
                }
                // GISCalculator g = new GISCalculator();
                // System.out.println("Distance is " +
                // g.computePathDistance(activityPoints));

                polylineModel.addOverlay(polyline);

                GISCalculator calc = new GISCalculator();
                Double distance = calc.computeMarkerPathDistance(myMapMarkers) / 1000;
                DecimalFormat df = new DecimalFormat("#.##");
                addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Distance",
                                "Your total distance is " + df.format(distance) + " km."));
        }

        public void addMessage(FacesMessage message) {
                FacesContext.getCurrentInstance().addMessage(null, message);
        }

        /**
         * @return the polylineModel
         */
        public MapModel getPolylineModel() {
                return polylineModel;
        }

        public String getMymap_id() {
                return mymap_id;
        }

        public void setMymap_id(String mymap_id) {
                this.mymap_id = mymap_id;
        }

        public String updateMyMap() {
                Map<String, String> params = FacesContext.getCurrentInstance()
                                .getExternalContext().getRequestParameterMap();
                String mId = params.get("mymap_id");
                Long id = Long.parseLong(mId);
                if (myMapName != null && myMapName.length() > 0) {
                        AthleticgisFacade.mergeMyMap(myMapName, id);
                }
                return "mymaps?faces-redirect=true";
        }

        /**
         * @return the myMapName
         */
        public String getMyMapName() {
                if (mymap_id != null) {
                        myMapName = AthleticgisFacade.findMyMapById(Long.parseLong(mymap_id)).getName();
                }
                return myMapName;
        }

        /**
         * @param myMapName the myMapName to set
         */
        public void setMyMapName(String myMapName) {
                
                this.myMapName = myMapName;
        }
}