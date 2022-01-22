package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the nursingorder_rds database table.
 * 
 */
@Entity
@Table(name="nursingorder_rds")
@NamedQuery(name="NursingorderRd.findAll", query="SELECT n FROM NursingorderRd n")
public class NursingorderRd implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long nursingorderrdsid;

	private Timestamp creationtime;

	@Column(name="medicine_actiontime")
	private Timestamp medicineActiontime;

	private Timestamp modificationtime;

	@Column(name="nursing_comments")
	private String nursingComments;

	private String rdsid;

	@Column(name="respiratory_support_actiontime")
	private Timestamp respiratorySupportActiontime;

	@Column(name="surfactant_actiontime")
	private Timestamp surfactantActiontime;

	private String uhid;

	public NursingorderRd() {
	}

	public Long getNursingorderrdsid() {
		return this.nursingorderrdsid;
	}

	public void setNursingorderrdsid(Long nursingorderrdsid) {
		this.nursingorderrdsid = nursingorderrdsid;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public Timestamp getMedicineActiontime() {
		return this.medicineActiontime;
	}

	public void setMedicineActiontime(Timestamp medicineActiontime) {
		this.medicineActiontime = medicineActiontime;
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

	public Timestamp getRespiratorySupportActiontime() {
		return this.respiratorySupportActiontime;
	}

	public void setRespiratorySupportActiontime(Timestamp respiratorySupportActiontime) {
		this.respiratorySupportActiontime = respiratorySupportActiontime;
	}

	public Timestamp getSurfactantActiontime() {
		return this.surfactantActiontime;
	}

	public void setSurfactantActiontime(Timestamp surfactantActiontime) {
		this.surfactantActiontime = surfactantActiontime;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

}