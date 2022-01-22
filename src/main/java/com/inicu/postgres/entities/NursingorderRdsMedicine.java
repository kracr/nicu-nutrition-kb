package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the nursingorder_rds_medicine database table.
 * 
 */
@Entity
@Table(name="nursingorder_rds_medicine")
@NamedQuery(name="NursingorderRdsMedicine.findAll", query="SELECT n FROM NursingorderRdsMedicine n")
public class NursingorderRdsMedicine implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="nursingorderrds_medicineid")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long nursingorderrdsMedicineid;

	private Timestamp creationtime;

	private String medicineid;

	private Timestamp modificationtime;

	@Column(name="nursing_comments")
	private String nursingComments;

	private String rdsid;

	private String uhid;
	
	@Column(name="babypresc_id")
	private String babyprescId;

	@Column(name="medicine_actiontime")
	private Timestamp medicineactiontime;
	
	public Timestamp getMedicineactiontime() {
		return medicineactiontime;
	}

	public void setMedicineactiontime(Timestamp medicineactiontime) {
		this.medicineactiontime = medicineactiontime;
	}

	public NursingorderRdsMedicine() {
	}

	public Long getNursingorderrdsMedicineid() {
		return this.nursingorderrdsMedicineid;
	}

	public void setNursingorderrdsMedicineid(Long nursingorderrdsMedicineid) {
		this.nursingorderrdsMedicineid = nursingorderrdsMedicineid;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getMedicineid() {
		return this.medicineid;
	}

	public void setMedicineid(String medicineid) {
		this.medicineid = medicineid;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getNursingComments() {
		return this.nursingComments;
	}

	public void setNursingComments(String nursingComments) {
		this.nursingComments = nursingComments;
	}

	public String getRdsid() {
		return this.rdsid;
	}

	public void setRdsid(String rdsid) {
		this.rdsid = rdsid;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getBabyprescId() {
		return babyprescId;
	}

	public void setBabyprescId(String babyprescId) {
		this.babyprescId = babyprescId;
	}
	
	

}