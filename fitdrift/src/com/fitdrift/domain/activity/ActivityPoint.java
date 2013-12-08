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
 * Entity class for ActivityPoint.
 * This class will hold data related to an instant in time for an activity.
 * 
 * @author Matthew Allen
 * @version 20131129
 
 */
@Entity
@Table
public class ActivityPoint implements Serializable {
	private static final long serialVersionUID = 6667575305066831369L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Long id;
	
	@ManyToOne //(fetch=FetchType.EAGER)
	@JoinColumn(name="activity_id")
	private Activity activity;
	
	@Column
	private Double latitude;
	@Column
	private Double longitude;
	@Column
	private Double elevation;
	@Column
	private Timestamp time;
	
	/**
	 * @return
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @return the activity
	 */
	public Activity getActivity() {
		return activity;
	}
	/**
	 * @param activity the activity to set
	 */
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	
	/**
	 * @return
	 */
	public Double getLatitude() {
		return latitude;
	}
	
	/**
	 * @param latitude
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	/**
	 * @return
	 */
	public Double getLongitude() {
		return longitude;
	}
	
	/**
	 * @param longitude
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	/**
	 * @return
	 */
	public Double getElevation() {
		return elevation;
	}
	
	/**
	 * @param elevation
	 */
	public void setElevation(Double elevation) {
		this.elevation = elevation;
	}
	
	/**
	 * @return
	 */
	public Timestamp getTime() {
		return time;
	}
	
	/**
	 * @param time
	 */
	public void setTime(Timestamp time) {
		this.time = time;
	}
}
