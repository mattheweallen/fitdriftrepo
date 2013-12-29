package com.fitdrift.domain.activity;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: ActivityType
 *
 * @author Matthew Allen
 * @version 20131226
 */
@Entity
@Table
public class ActivityType implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Long activitytype_id;
	
	@Column
	private String description;

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
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof ActivityType) && (activitytype_id != null)
	            ? activitytype_id.equals(((ActivityType) obj).getActivitytype_id())
	            : (obj == this);
	}
	
	@Override
    public int hashCode() {
        return (activitytype_id != null)
            ? (this.getClass().hashCode() + activitytype_id.hashCode())
            : super.hashCode();
    }
	
	@Override
	public String toString() {
		return activitytype_id + ":" + description;
	}
}
