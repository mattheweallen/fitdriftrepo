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
	
	public ActivitySubType() {
		super();
	}
   
}
