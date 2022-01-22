package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ref_masterfeedtype database table.
 * 
 */
@Entity
@Table(name="ref_masterfeedtype")
@NamedQuery(name="RefMasterfeedtype.findAll", query="SELECT r FROM RefMasterfeedtype r")
public class RefMasterfeedtype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String feedtypeid;

	private String description;

	private String feedtypename;
	
	@Column(columnDefinition="bool")
	private Boolean feedtype;

	public RefMasterfeedtype() {
	}

	public String getFeedtypeid() {
		return this.feedtypeid;
	}

	public void setFeedtypeid(String feedtypeid) {
		this.feedtypeid = feedtypeid;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFeedtypename() {
		return this.feedtypename;
	}

	public void setFeedtypename(String feedtypename) {
		this.feedtypename = feedtypename;
	}

	public Boolean getFeedtype() {
		return feedtype;
	}

	public void setFeedtype(Boolean feedtype) {
		this.feedtype = feedtype;
	}

	
	
}