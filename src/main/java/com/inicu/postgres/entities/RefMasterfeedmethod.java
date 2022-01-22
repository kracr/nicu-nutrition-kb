package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the ref_masterfeedmethod database table.
 * 
 */
@Entity
@Table(name="ref_masterfeedmethod")
@NamedQuery(name="RefMasterfeedmethod.findAll", query="SELECT r FROM RefMasterfeedmethod r")
public class RefMasterfeedmethod implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue (strategy=GenerationType.AUTO)
	private String feedmethodid;

	private String description;

	private String feedmethodname;

	public RefMasterfeedmethod() {
	}

	public String getFeedmethodid() {
		return this.feedmethodid;
	}

	public void setFeedmethodid(String feedmethodid) {
		this.feedmethodid = feedmethodid;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFeedmethodname() {
		return this.feedmethodname;
	}

	public void setFeedmethodname(String feedmethodname) {
		this.feedmethodname = feedmethodname;
	}

}