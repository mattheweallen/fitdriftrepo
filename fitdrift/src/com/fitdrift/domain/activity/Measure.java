package com.fitdrift.domain.activity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.persistence.*;

import com.fitdrift.domain.user.User;

/**
 * Entity implementation class for Entity: Measure
 *
 */
@Entity
@Table
public class Measure implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Long measure_id;
	
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(updatable = false, nullable = false)
//	private Long measureGroup_id;
	
	@Column
	private Timestamp date;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "activitysubtype_id")
	private ActivitySubType activitySubType;
	
	@Column
	private Double value;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;
	
	@Transient
	private String formattedMeasureDate;

	/**
	 * @return the measure_id
	 */
	public Long getMeasure_id() {
		return measure_id;
	}

	/**
	 * @param measure_id the measure_id to set
	 */
	public void setMeasure_id(Long measure_id) {
		this.measure_id = measure_id;
	}

	/**
	 * @return the date
	 */
	public Timestamp getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Timestamp date) {
		this.date = date;
	}

	/**
	 * @return the value
	 */
	public Double getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Double value) {
		this.value = value;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the activitySubType
	 */
	public ActivitySubType getActivitySubType() {
		return activitySubType;
	}

	/**
	 * @param activitySubType the activitySubType to set
	 */
	public void setActivitySubType(ActivitySubType activitySubType) {
		this.activitySubType = activitySubType;
	}

	/**
	 * @return the formattedMeasureDate
	 */
	public String getFormattedMeasureDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.yyyy");
		formattedMeasureDate = sdf.format(date);
		return formattedMeasureDate;
	}
}
