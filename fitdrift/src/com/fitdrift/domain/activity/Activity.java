package com.fitdrift.domain.activity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fitdrift.domain.user.User;

/**
 * Entity class for Activity.
 * This hold data related to a summary of an activity.
 * 
 * @author Matthew Allen
 * @version 20131226
 */
@Entity
@Table
public class Activity implements Serializable {
	private static final long serialVersionUID = -1601934048327683904L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Long activity_id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@Column
	private String name;

	@Column
	private Timestamp date;

	@OneToMany(mappedBy = "activity", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<ActivityPoint> activitypoints;
	
	@Column
	private Long activitytype_id;
	
	@Column
	private Long activitysubtype_id;

	@Column
	private Long equipmenttype_id;
	
	@Column
	private Boolean indoors;
	
	@Column
	private Long startTime;
	
	@Column
	private Long endTime;
	
	@Column
	private Double distance;
	
	@Column
	private Double weight;
	
	@Transient
	private String formattedActivityDate;
	
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the activity_id
	 */
	public Long getActivity_id() {
		return activity_id;
	}

	/**
	 * @param activity_id
	 *            the activity_id to set
	 */
	public void setActivity_id(Long activity_id) {
		this.activity_id = activity_id;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the activitypoints
	 */
	public List<ActivityPoint> getActivitypoints() {
		return activitypoints;
	}

	/**
	 * @param waypoints
	 *            the activitypoints to set
	 */
	public void setActivitypoints(List<ActivityPoint> activitypoints) {
		this.activitypoints = activitypoints;
	}

	/**
	 * @return the date
	 */
	public Timestamp getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Timestamp date) {
		this.date = date;
	}

	/**
	 * @return the indoors
	 */
	public Boolean getIndoors() {
		return indoors;
	}

	/**
	 * @param indoors the indoors to set
	 */
	public void setIndoors(Boolean indoors) {
		this.indoors = indoors;
	}

	/**
	 * @return the startTime
	 */
	public Long getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Long getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the distance
	 */
	public Double getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(Double distance) {
		this.distance = distance;
	}

	/**
	 * @return the weight
	 */
	public Double getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(Double weight) {
		this.weight = weight;
	}

	/**
	 * @return the formattedActivityDate
	 */
	public String getFormattedActivityDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.yyyy");
		formattedActivityDate = sdf.format(date);
		return formattedActivityDate;
	}
}
