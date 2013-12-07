package com.fitdrift.domain.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Matthew Allen
 * @version 20131206
 * Entity class for UserPreferences.
 * This entity is not currently being used.
 * Will expand when more UserPreferences are added
 * Right now preferences are stored in User.
 * This Entity will hold data related to UserPreferences.
 */
@Entity
@Table(name="userpreferences")
public class UserPreferences implements Serializable {
	private static final long serialVersionUID = -5787327626871467868L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Long userpreferences_id;

	/**
	 * @return the userpreferences_id
	 */
	public Long getUserpreferences_id() {
		return userpreferences_id;
	}

	/**
	 * @param userpreferences_id the userpreferences_id to set
	 */
	public void setUserpreferences_id(Long userpreferences_id) {
		this.userpreferences_id = userpreferences_id;
	}
}
