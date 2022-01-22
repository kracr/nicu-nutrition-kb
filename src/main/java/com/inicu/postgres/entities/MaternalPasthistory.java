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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The persistent class for the maternal_pasthistory database table.
 * 
 */
@Entity
@Table(name="maternal_pasthistory")
@NamedQuery(name="MaternalPasthistory.findAll", query="SELECT m FROM MaternalPasthistory m")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaternalPasthistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long pasthistoryid;

	private String complications;

	private Timestamp creationtime;

	@Column(name="mode_of_delivery")
	private String modeOfDelivery;

	private Timestamp modificationtime;

	private String outcomes;

	@Column(name="past_delivery_year")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Timestamp pastDeliveryYear;

	private String uhid;

	public MaternalPasthistory() {
	}

	public Long getPasthistoryid() {
		return this.pasthistoryid;
	}

	public void setPasthistoryid(Long pasthistoryid) {
		this.pasthistoryid = pasthistoryid;
	}

	public String getComplications() {
		return this.complications;
	}

	public void setComplications(String complications) {
		this.complications = complications;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getModeOfDelivery() {
		return this.modeOfDelivery;
	}

	public void setModeOfDelivery(String modeOfDelivery) {
		this.modeOfDelivery = modeOfDelivery;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getOutcomes() {
		return this.outcomes;
	}

	public void setOutcomes(String outcomes) {
		this.outcomes = outcomes;
	}

	public Timestamp getPastDeliveryYear() {
		return this.pastDeliveryYear;
	}

	public void setPastDeliveryYear(Timestamp pastDeliveryYear) {
		this.pastDeliveryYear = pastDeliveryYear;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

}