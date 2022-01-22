package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the nursing_misc database table.
 * 
 */
@Entity
@Table(name="nursing_misc")
@NamedQuery(name="NursingMisc.findAll", query="SELECT n FROM NursingMisc n")
public class NursingMisc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="nn_miscid")
	private Long nnMiscid;

	@Column(name="abd_grith")
	private String abdGrith;

	private String comments;

	private Timestamp creationtime;

	private String hgt;

	private String loggeduser;

	private Timestamp modificationtime;

	@Column(name="nn_misc_time")
	private String nnMiscTime;

	@Column(name="phototherapy_end_date")
	private Timestamp phototherapyEndDate;


	@Column(name="phototherapy_start_date")
	private Timestamp phototherapyStartDate;

	private String uhid;
	
	@Column(name="phototherapy_type")
	private String phototherapyType;

	public NursingMisc() {
		phototherapyType = "";
	}

	public Long getNnMiscid() {
		return this.nnMiscid;
	}

	public void setNnMiscid(Long nnMiscid) {
		this.nnMiscid = nnMiscid;
	}

	public String getAbdGrith() {
		return this.abdGrith;
	}

	public void setAbdGrith(String abdGrith) {
		this.abdGrith = abdGrith;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getHgt() {
		return this.hgt;
	}

	public void setHgt(String hgt) {
		this.hgt = hgt;
	}

	public String getLoggeduser() {
		return this.loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getNnMiscTime() {
		return this.nnMiscTime;
	}

	public void setNnMiscTime(String nnMiscTime) {
		this.nnMiscTime = nnMiscTime;
	}

	public Timestamp getPhototherapyEndDate() {
		return this.phototherapyEndDate;
	}

	public void setPhototherapyEndDate(Timestamp phototherapyEndDate) {
		this.phototherapyEndDate = phototherapyEndDate;
	}

	public Timestamp getPhototherapyStartDate() {
		return this.phototherapyStartDate;
	}

	public void setPhototherapyStartDate(Timestamp phototherapyStartDate) {
		this.phototherapyStartDate = phototherapyStartDate;
	}

	
	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}
	public String getPhototherapyType() {
		return this.phototherapyType;
	}

	public void setPhototherapyType(String phototherapyType) {
		this.phototherapyType = phototherapyType;
	}
	

}