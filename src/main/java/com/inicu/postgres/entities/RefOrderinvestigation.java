package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ref_orderinvestigation database table.
 * 
 */
@Entity
@Table(name="ref_orderinvestigation")
@NamedQuery(name="RefOrderinvestigation.findAll", query="SELECT r FROM RefOrderinvestigation r")
public class RefOrderinvestigation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String orderinvestigationid;

	private String description;

	private String orderinvestigation;

	public RefOrderinvestigation() {
	}

	public String getOrderinvestigationid() {
		return this.orderinvestigationid;
	}

	public void setOrderinvestigationid(String orderinvestigationid) {
		this.orderinvestigationid = orderinvestigationid;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrderinvestigation() {
		return this.orderinvestigation;
	}

	public void setOrderinvestigation(String orderinvestigation) {
		this.orderinvestigation = orderinvestigation;
	}

}