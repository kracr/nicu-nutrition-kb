package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the associate_assesment_final database table.
 * 
 */
@Entity
@Table(name="active_assesment_final")
@NamedQuery(name="ActiveAssesmentFinal.findAll", query="SELECT a FROM ActiveAssesmentFinal a")
public class ActiveAssesmentFinal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="associated_event")
	private String associatedEvent;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String uhid;

	public ActiveAssesmentFinal() {
	}

	public String getAssociatedEvent() {
		return this.associatedEvent;
	}

	public void setAssociatedEvent(String associatedEvent) {
		this.associatedEvent = associatedEvent;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

}