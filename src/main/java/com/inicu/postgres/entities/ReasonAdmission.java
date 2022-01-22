package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The persistent class for the reason_admission database table.
 * 
 * @author Sourabh Verma
 */
@Entity
@Table(name = "reason_admission")
@NamedQuery(name = "ReasonAdmission.findAll", query = "SELECT b FROM ReasonAdmission b")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReasonAdmission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long reason_admission_id;

	@Column(columnDefinition = "timestamp with time zone")
	private Timestamp creationtime;

	private String uhid;

	private String cause_event;

	private String cause_value;

	private String cause_value_other;

	private String loggeduser;

	private String episodeid;

	public Long getReason_admission_id() {
		return reason_admission_id;
	}

	public void setReason_admission_id(Long reason_admission_id) {
		this.reason_admission_id = reason_admission_id;
	}

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getCause_event() {
		return cause_event;
	}

	public void setCause_event(String cause_event) {
		this.cause_event = cause_event;
	}

	public String getCause_value() {
		return cause_value;
	}

	public void setCause_value(String cause_value) {
		this.cause_value = cause_value;
	}

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public String getEpisodeid() {
		return episodeid;
	}

	public void setEpisodeid(String episodeid) {
		this.episodeid = episodeid;
	}

	public String getCause_value_other() {
		return cause_value_other;
	}

	public void setCause_value_other(String cause_value_other) {
		this.cause_value_other = cause_value_other;
	}

	@Override
	public String toString() {
		return "ReasonAdmission [reason_admission_id=" + reason_admission_id + ", creationtime=" + creationtime
				+ ", uhid=" + uhid + ", cause_event=" + cause_event + ", cause_value=" + cause_value + ", loggeduser="
				+ loggeduser + ", episodeid=" + episodeid + "]";
	}

}
