package com.fitdrift.domain.activity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Matthew Allen
 * @version 20131201
 * Entity class for MyMap.
 * This class holds data related to a user defined map.
 */
@Entity
@Table
public class MyMapMarker implements Serializable {
	private static final long serialVersionUID = 6092534054769274560L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Long id;
	
	@ManyToOne //(fetch=FetchType.EAGER)
	@JoinColumn(name="mymap_id")
	private MyMap myMap;
	
	@Column
	private String name;
	
	@Column
	private Double latitude;
	@Column
	private Double longitude;
	@Column
	private Double elevation;
	@Column
	private Timestamp time;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the myMap
	 */
	public MyMap getMyMap() {
		return myMap;
	}
	/**
	 * @param myMap the myMap to set
	 */
	public void setMyMap(MyMap myMap) {
		this.myMap = myMap;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the latitude
	 */
	public Double getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the longitude
	 */
	public Double getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	/**
	 * @return the elevation
	 */
	public Double getElevation() {
		return elevation;
	}
	/**
	 * @param elevation the elevation to set
	 */
	public void setElevation(Double elevation) {
		this.elevation = elevation;
	}
	/**
	 * @return the time
	 */
	public Timestamp getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(Timestamp time) {
		this.time = time;
	}

}
