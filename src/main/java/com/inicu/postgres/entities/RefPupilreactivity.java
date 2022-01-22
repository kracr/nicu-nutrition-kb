package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ref_pupilreactivity database table.
 * 
 */
@Entity
@Table(name="ref_pupilreactivity")
@NamedQuery(name="RefPupilreactivity.findAll", query="SELECT r FROM RefPupilreactivity r")
public class RefPupilreactivity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String reactivityid;

	private String description;

	private String reactivity;

	public RefPupilreactivity() {
	}

	public String getReactivityid() {
		return this.reactivityid;
	}

	public void setReactivityid(String reactivityid) {
		this.reactivityid = reactivityid;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReactivity() {
		return this.reactivity;
	}

	public void setReactivity(String reactivity) {
		this.reactivity = reactivity;
	}

}