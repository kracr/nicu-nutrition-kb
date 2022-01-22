package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the vw_assessmentcause_final database table.
 * 
 * @author Sourabh Verma
 */
@Entity
@Table(name = "vw_assessmentcause_final")
@NamedQuery(name = "VwAssessmentcauseFinal.findAll", query = "SELECT v FROM VwAssessmentcauseFinal v")
public class VwAssessmentcauseFinal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String eventid;

	private String event;

	private String causes;

	public String getEventid() {
		return eventid;
	}

	public void setEventid(String eventid) {
		this.eventid = eventid;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getCauses() {
		return causes;
	}

	public void setCauses(String causes) {
		this.causes = causes;
	}

}
