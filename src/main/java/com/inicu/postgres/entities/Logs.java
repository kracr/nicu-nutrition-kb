package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the iniculogs database table.
 * 
 */
@Entity
@Table(name="iniculogs")
@NamedQuery(name="Logs.findAll", query="SELECT i FROM Logs i")
public class Logs implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long logid;

	private String action;

	private Timestamp creationtime;

	private String description;

	private String loggeduser;

	private String pagename;

	private String patientdoctorid;

	public Logs() {
	}

	public Long getLogid() {
		return this.logid;
	}

	public void setLogid(Long logid) {
		this.logid = logid;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLoggeduser() {
		return this.loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public String getPagename() {
		return this.pagename;
	}

	public void setPagename(String pagename) {
		this.pagename = pagename;
	}

	public String getUhid() {
		return this.patientdoctorid ;
	}

	public void setUhid(String patientdoctorid ) {
		this.patientdoctorid  = patientdoctorid ;
	}

}
