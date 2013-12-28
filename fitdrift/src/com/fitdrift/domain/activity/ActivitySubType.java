package com.fitdrift.domain.activity;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: ActivitySubType
 *
 */
@Entity
@Table
public class ActivitySubType implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Long activitysubtype_id;

	@Column
	private Long activitytype_id;
	
	@Column
	private String description;

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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
   
}
