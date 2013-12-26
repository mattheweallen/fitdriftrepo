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
}
