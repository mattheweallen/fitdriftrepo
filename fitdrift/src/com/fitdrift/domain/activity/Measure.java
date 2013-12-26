package com.fitdrift.domain.activity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Measure
 *
 */
@Entity
@Table
public class Measure implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Long measure_id;
	
	@Column
	private Timestamp date;
	
	@Column
	private Long measuretype_id;
	
	@Column
	private Double value;
}
