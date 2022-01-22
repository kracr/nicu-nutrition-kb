package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "ref_causeofmetabolic")
@NamedQuery(name = "RefCauseofmetabolic.findAll", query = "Select s from RefCauseofmetabolic s")
public class RefCauseofmetabolic implements Serializable {

	private static final long serialVersionID = 1L;

	@Id
	private String causeofmetabolicid;

	private String causeofmetabolic;

	private String event;

	private String description;

	private Integer seq;

	public String getCauseofmetabolicid() {
		return causeofmetabolicid;
	}

	public void setCauseofmetabolicid(String causeofmetabolicid) {
		this.causeofmetabolicid = causeofmetabolicid;
	}

	public String getCauseofmetabolic() {
		return causeofmetabolic;
	}

	public void setCauseofmetabolic(String causeofmetabolic) {
		this.causeofmetabolic = causeofmetabolic;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	

}
