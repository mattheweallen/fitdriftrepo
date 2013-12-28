package com.fitdrift.domain.activity;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Statistic
 *
 */
@Entity
@Table
public class Statistic implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Long statistic_id;
	
	@ManyToOne //(fetch=FetchType.EAGER)
	@JoinColumn(name="activity_id")
	private Activity activity;
	
	@Column
	private Long statistictype_id;
	
	@Column
	private Double value;

	/**
	 * @return the statistic_id
	 */
	public Long getStatistic_id() {
		return statistic_id;
	}

	/**
	 * @param statistic_id the statistic_id to set
	 */
	public void setStatistic_id(Long statistic_id) {
		this.statistic_id = statistic_id;
	}

	/**
	 * @return the activity
	 */
	public Activity getActivity() {
		return activity;
	}

	/**
	 * @param activity the activity to set
	 */
	public void setActivity(Activity activity) {
		this.activity = activity;
	}

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
	 * @return the value
	 */
	public Double getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Double value) {
		this.value = value;
	}
}
