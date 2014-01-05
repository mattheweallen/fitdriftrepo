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
	
	@OneToMany(mappedBy = "activity", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Statistic> statistics;
	
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
	private Boolean useMyMap;
	
	@Transient
	private String formattedActivityDate;
	
	@Column
	private Double calories;
	
	@Column
	private Double aveHeartRate;
	
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
	 * @return the formattedActivityDate
	 */
	public String getFormattedActivityDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.yyyy");
		formattedActivityDate = sdf.format(date);
		return formattedActivityDate;
	}

	/**
	 * @return the statistics
	 */
	public List<Statistic> getStatistics() {
		return statistics;
	}

	/**
	 * @param statistics the statistics to set
	 */
	public void setStatistics(List<Statistic> statistics) {
		this.statistics = statistics;
	}

	/**
	 * @return the activitytype_id
	 */
	public Long getActivitytype_id() {
		return activitytype_id;
	}

	/**
	 * @param activitytype_id the activitytype_id to set
	 */
	public void setActivitytype_id(Long activitytype_id) {
		this.activitytype_id = activitytype_id;
	}

	/**
	 * @return the activitysubtype_id
	 */
	public Long getActivitysubtype_id() {
		return activitysubtype_id;
	}

	/**
	 * @param activitysubtype_id the activitysubtype_id to set
	 */
	public void setActivitysubtype_id(Long activitysubtype_id) {
		this.activitysubtype_id = activitysubtype_id;
	}

	/**
	 * @return the equipmenttype_id
	 */
	public Long getEquipmenttype_id() {
		return equipmenttype_id;
	}

	/**
	 * @param equipmenttype_id the equipmenttype_id to set
	 */
	public void setEquipmenttype_id(Long equipmenttype_id) {
		this.equipmenttype_id = equipmenttype_id;
	}

	/**
	 * @param formattedActivityDate the formattedActivityDate to set
	 */
	public void setFormattedActivityDate(String formattedActivityDate) {
		this.formattedActivityDate = formattedActivityDate;
	}

	/**
	 * @return the useMyMap
	 */
	public Boolean getUseMyMap() {
		return useMyMap;
	}

	/**
	 * @param useMyMap the useMyMap to set
	 */
	public void setUseMyMap(Boolean useMyMap) {
		this.useMyMap = useMyMap;
	}

	/**
	 * @return the calories
	 */
	public Double getCalories() {
		return calories;
	}

	/**
	 * @param calories the calories to set
	 */
	public void setCalories(Double calories) {
		this.calories = calories;
	}

	/**
	 * @return the aveHeartRate
	 */
	public Double getAveHeartRate() {
		return aveHeartRate;
	}

	/**
	 * @param aveHeartRate the aveHeartRate to set
	 */
	public void setAveHeartRate(Double aveHeartRate) {
		this.aveHeartRate = aveHeartRate;
	}
}
