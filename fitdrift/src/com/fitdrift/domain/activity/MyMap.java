package com.fitdrift.domain.activity;

import java.io.Serializable;
import java.sql.Timestamp;
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

import com.fitdrift.domain.user.User;

/**
 * This class holds data related to a user defined map.
 * Entity class for MyMap.
 * 
 * @author Matthew Allen
 * @version 20131201
 */
@Entity
@Table
public class MyMap implements Serializable {
	private static final long serialVersionUID = 1290865495351059752L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Long mymap_id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@Column
	private String name;

	@Column
	private Timestamp date;

	@OneToMany(mappedBy = "myMap", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<MyMapMarker> myMapMarkers;

	/**
	 * @return the mymap_id
	 */
	public Long getMymap_id() {
		return mymap_id;
	}

	/**
	 * @param mymap_id the mymap_id to set
	 */
	public void setMymap_id(Long mymap_id) {
		this.mymap_id = mymap_id;
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
	 * @return the myMapMarkers
	 */
	public List<MyMapMarker> getMyMapMarkers() {
		return myMapMarkers;
	}

	/**
	 * @param myMapMarkers the myMapMarkers to set
	 */
	public void setMyMapMarkers(List<MyMapMarker> myMapMarkers) {
		this.myMapMarkers = myMapMarkers;
	}
}
