package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the vw_doctornotes_usage_final database table.
 * 
 */
@Entity
@Table(name="vw_doctornotes_usage_final")
@NamedQuery(name="VwDoctornotesUsageFinal.findAll", query="SELECT v FROM VwDoctornotesUsageFinal v")
public class VwDoctornotesUsageFinal implements Serializable {
	private static final long serialVersionUID = 1L;

	private String assesments;

	private String babyfeed;

	@Temporal(TemporalType.DATE)
	private Date creationtime;

	private String criticality;

	private String doctornotes;

	private String niculevel;

	private String prescription;

	@Id
	private String uhid;
	
	private String babyname;

	

	public VwDoctornotesUsageFinal() {
		super();
		this.assesments = "0";;
		this.babyfeed = "0";;
		this.creationtime = creationtime;
		this.criticality = criticality;
		this.doctornotes = "0";
		this.niculevel = niculevel;
		this.prescription = "0";
		this.uhid = uhid;
		this.babyname = babyname;
	}

	public String getAssesments() {
		return this.assesments;
	}

	public void setAssesments(String assesments) {
		this.assesments = assesments;
	}

	public String getBabyfeed() {
		return this.babyfeed;
	}

	public void setBabyfeed(String babyfeed) {
		this.babyfeed = babyfeed;
	}

	public Date getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Date creationtime) {
		this.creationtime = creationtime;
	}

	public String getCriticality() {
		return this.criticality;
	}

	public void setCriticality(String criticality) {
		this.criticality = criticality;
	}

	public String getDoctornotes() {
		return this.doctornotes;
	}

	public void setDoctornotes(String doctornotes) {
		this.doctornotes = doctornotes;
	}

	public String getNiculevel() {
		return this.niculevel;
	}

	public void setNiculevel(String niculevel) {
		this.niculevel = niculevel;
	}

	public String getPrescription() {
		return this.prescription;
	}

	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getBabyname() {
		return babyname;
	}

	public void setBabyname(String babyname) {
		this.babyname = babyname;
	}

	
	
	
}