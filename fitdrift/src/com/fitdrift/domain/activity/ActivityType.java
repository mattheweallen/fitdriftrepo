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
	private Long activity_id;
	
	public ActivityType() {
		super();
	}
   
}
