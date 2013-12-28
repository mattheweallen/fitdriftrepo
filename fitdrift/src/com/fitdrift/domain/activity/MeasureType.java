package com.fitdrift.domain.activity;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: MeasureType
 *
 */
@Entity
@Table
public class MeasureType implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Long measuretype_id;
	
	@Column
	private String description;

	/**
	 * @return the measuretype_id
	 */
	public Long getMeasuretype_id() {
		return measuretype_id;
	}

	/**
	 * @param measuretype_id the measuretype_id to set
	 */
	public void setMeasuretype_id(Long measuretype_id) {
		this.measuretype_id = measuretype_id;
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
