package com.fitdrift.domain.activity;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: StatisticType
 *
 */
@Entity

public class StatisticType implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Long statistictype_id;
	
	@Column
	private String description;

	/**
	 * @return the statistictype_id
	 */
	public Long getStatistictype_id() {
		return statistictype_id;
	}

	/**
	 * @param statistictype_id the statistictype_id to set
	 */
	public void setStatistictype_id(Long statistictype_id) {
		this.statistictype_id = statistictype_id;
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
