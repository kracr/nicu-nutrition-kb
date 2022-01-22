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

/**
 * The persistent class for the nursingorder_jaundice database table.
 * 
 * @author Sourabh Verma
 */
@Entity
@Table(name = "nursingorder_jaundice")
@NamedQuery(name = "NursingOrderJaundice.findAll", query = "SELECT b FROM NursingOrderJaundice b")
public class NursingOrderJaundice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long nursingorderjaundiceid;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String sajaundiceid;

	private String uhid;

	private String actiontype;

	private String phototherapyvalue;

	@Column(columnDefinition = "bool")
	private Boolean exchangetrans;

	private String ivigvalue;

	private String nursing_comments;

	private Timestamp phototherapy_actiontime;

	private Timestamp exchangetrans_actiontime;

	private Timestamp ivigvalue_actiontime;

	public NursingOrderJaundice() {
		super();
	}

	public Long getNursingorderjaundiceid() {
		return nursingorderjaundiceid;
	}

	public void setNursingorderjaundiceid(Long nursingorderjaundiceid) {
		this.nursingorderjaundiceid = nursingorderjaundiceid;
	}

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public Timestamp getModificationtime() {
		return modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getSajaundiceid() {
		return sajaundiceid;
	}

	public void setSajaundiceid(String sajaundiceid) {
		this.sajaundiceid = sajaundiceid;
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getActiontype() {
		return actiontype;
	}

	public void setActiontype(String actiontype) {
		this.actiontype = actiontype;
	}

	public String getPhototherapyvalue() {
		return phototherapyvalue;
	}

	public void setPhototherapyvalue(String phototherapyvalue) {
		this.phototherapyvalue = phototherapyvalue;
	}

	public Boolean getExchangetrans() {
		return exchangetrans;
	}

	public void setExchangetrans(Boolean exchangetrans) {
		this.exchangetrans = exchangetrans;
	}

	public String getIvigvalue() {
		return ivigvalue;
	}

	public void setIvigvalue(String ivigvalue) {
		this.ivigvalue = ivigvalue;
	}

	public String getNursing_comments() {
		return nursing_comments;
	}

	public void setNursing_comments(String nursing_comments) {
		this.nursing_comments = nursing_comments;
	}

	public Timestamp getPhototherapy_actiontime() {
		return phototherapy_actiontime;
	}

	public void setPhototherapy_actiontime(Timestamp phototherapy_actiontime) {
		this.phototherapy_actiontime = phototherapy_actiontime;
	}

	public Timestamp getExchangetrans_actiontime() {
		return exchangetrans_actiontime;
	}

	public void setExchangetrans_actiontime(Timestamp exchangetrans_actiontime) {
		this.exchangetrans_actiontime = exchangetrans_actiontime;
	}

	public Timestamp getIvigvalue_actiontime() {
		return ivigvalue_actiontime;
	}

	public void setIvigvalue_actiontime(Timestamp ivigvalue_actiontime) {
		this.ivigvalue_actiontime = ivigvalue_actiontime;
	}

	@Override
	public String toString() {
		return "NursingOrderJaundice [nursingorderjaundiceid=" + nursingorderjaundiceid + ", creationtime="
				+ creationtime + ", modificationtime=" + modificationtime + ", sajaundiceid=" + sajaundiceid + ", uhid="
				+ uhid + ", actiontype=" + actiontype + ", phototherapyvalue=" + phototherapyvalue + ", exchangetrans="
				+ exchangetrans + ", ivigvalue=" + ivigvalue + ", nursing_comments=" + nursing_comments
				+ ", phototherapy_actiontime=" + phototherapy_actiontime + ", exchangetrans_actiontime="
				+ exchangetrans_actiontime + ", ivigvalue_actiontime=" + ivigvalue_actiontime + "]";
	}

}
