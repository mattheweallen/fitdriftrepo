package com.fitdrift.domain.user;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fitdrift.domain.activity.Activity;
import com.fitdrift.domain.activity.Equipment;
import com.fitdrift.domain.activity.Measure;

/**
 * @author Matthew Allen
 * @version 20131206
 * Entity class for User.
 * This Entity holds data related to User from the users table.
 * The table users is used by Spring Security.
 */
@Entity
@Table(name="users")
public class User implements Serializable {
	private static final long serialVersionUID = -1042291323559226622L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Long user_id;

	@OneToMany(mappedBy = "user", fetch=FetchType.LAZY, orphanRemoval=true)
	private Set<UserRole> userroles;

//	@OneToOne(fetch=FetchType.EAGER)
//	@JoinColumn(name="userdetail_id")
//	private UserDetail userDetail;
	
	@OneToMany(mappedBy = "user", fetch=FetchType.LAZY, orphanRemoval=true)
	private Set<Activity> activities;
	
	@OneToMany(mappedBy = "user", fetch=FetchType.LAZY, orphanRemoval=true)
	private Set<Measure> measures;
	
	@OneToMany(mappedBy = "user", fetch=FetchType.LAZY, orphanRemoval=true)
	private Set<Equipment> equipment;
	
	@OneToMany(mappedBy = "user", fetch=FetchType.LAZY, orphanRemoval=true)
	private Set<UserSignIn> userSignIns;

	@Column
	private String username;

	@Column
	private String password;

	@Column
	private int enabled;
	
	@Column 
	private String theme;

	/**
	 * @return the theme
	 */
	public String getTheme() {
		return theme;
	}

	/**
	 * @param theme the theme to set
	 */
	public void setTheme(String theme) {
		this.theme = theme;
	}

	/**
	 * @return the activities
	 */
	public Set<Activity> getActivities() {
		return activities;
	}

	/**
	 * @param activities the activities to set
	 */
	public void setActivities(Set<Activity> activities) {
		this.activities = activities;
	}

	/**
	 * @return the user_id
	 */
	public Long getUser_id() {
		return user_id;
	}

	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	/**
	 * @return the userroles
	 */
	public Set<UserRole> getUserroles() {
		return userroles;
	}

	/**
	 * @param userroles
	 *            the userroles to set
	 */
	public void setUserroles(Set<UserRole> userroles) {
		this.userroles = userroles;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the enabled
	 */
	public int getEnabled() {
		return enabled;
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 */
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the measures
	 */
	public Set<Measure> getMeasures() {
		return measures;
	}

	/**
	 * @param measures the measures to set
	 */
	public void setMeasures(Set<Measure> measures) {
		this.measures = measures;
	}

	/**
	 * @return the equipment
	 */
	public Set<Equipment> getEquipment() {
		return equipment;
	}

	/**
	 * @param equipment the equipment to set
	 */
	public void setEquipment(Set<Equipment> equipment) {
		this.equipment = equipment;
	}

	/**
	 * @return the userSignIns
	 */
	public Set<UserSignIn> getUserSignIns() {
		return userSignIns;
	}

	/**
	 * @param userSignIns the userSignIns to set
	 */
	public void setUserSignIns(Set<UserSignIn> userSignIns) {
		this.userSignIns = userSignIns;
	}
	
//	/**
//	 * @return the userDetail
//	 */
//	public UserDetail getUserDetail() {
//		return userDetail;
//	}
//
//	/**
//	 * @param userDetail the userDetail to set
//	 */
//	public void setUserDetail(UserDetail userDetail) {
//		this.userDetail = userDetail;
//	}
}
