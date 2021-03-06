package com.fitdrift.domain.activity;

import java.io.Serializable;
import java.util.Set;

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
	
	@Column
	private String description;
	
	@OneToMany(mappedBy = "activitySubType", fetch=FetchType.LAZY, orphanRemoval=true)
	private Set<Measure> measures;

	/**
	 * @return the activitysubtype_id
	 */
	public Long getActivitysubtype_id() {
		return activitysubtype_id;
	}

	/**
	 * @param activitysubtype_id the activitysubtype_id to set
	 */
	public void setActivitysubtype_id(Long activitysubtype_id) {
		this.activitysubtype_id = activitysubtype_id;
	}

	/**
	 * @return the activitytype_id
	 */
	public Long getActivitytype_id() {
		return activitytype_id;
	}

	/**
	 * @param activitytype_id the activitytype_id to set
	 */
	public void setActivitytype_id(Long activitytype_id) {
		this.activitytype_id = activitytype_id;
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
   
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof ActivitySubType) && (activitysubtype_id != null)
	            ? activitysubtype_id.equals(((ActivitySubType) obj).getActivitysubtype_id())
	            : (obj == this);
	}
	
	@Override
    public int hashCode() {
        return (activitysubtype_id != null)
            ? (this.getClass().hashCode() + activitysubtype_id.hashCode())
            : super.hashCode();
    }
	
	@Override
	public String toString() {
		return activitysubtype_id + ":" + activitytype_id + ":" + description;
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
}
