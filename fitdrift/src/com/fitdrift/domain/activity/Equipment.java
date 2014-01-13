package com.fitdrift.domain.activity;

import java.io.Serializable;

import javax.persistence.*;

import com.fitdrift.domain.user.User;

/**
 * Entity implementation class for Entity: Equipment
 *
 */
@Entity

public class Equipment implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Long equipmenttype_id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@Column
	private Long activitytype_id;
	
	@Column
	private String description;
	
	@Column 
	private Double weight;

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
}
