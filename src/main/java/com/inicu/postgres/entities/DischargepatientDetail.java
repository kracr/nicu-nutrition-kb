package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the dischargepatient_detail database table.
 * 
 */
@Entity
@Table(name="dischargepatient_detail")
@NamedQuery(name="DischargepatientDetail.findAll", query="SELECT d FROM DischargepatientDetail d")
public class DischargepatientDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private Long dischargepatientid;

	@Column(name="additional_doctor_notes")
	private String additionalDoctorNotes;

	@Column(name="baby_discharge_status")
	private String babyDischargeStatus;

	private Timestamp creationtime;

	@Column(name="discharge_print_flag")
	private String dischargePrintFlag;

	
	private Timestamp dischargedate;

	private String loggeduser;

	private Timestamp modificationtime;

	private String uhid;

	public DischargepatientDetail() {
	}

	public Long getDischargepatientid() {
		return this.dischargepatientid;
	}

	public void setDischargepatientid(Long dischargepatientid) {
		this.dischargepatientid = dischargepatientid;
	}

	public String getAdditionalDoctorNotes() {
		return this.additionalDoctorNotes;
	}

	public void setAdditionalDoctorNotes(String additionalDoctorNotes) {
		this.additionalDoctorNotes = additionalDoctorNotes;
	}

	public String getBabyDischargeStatus() {
		return this.babyDischargeStatus;
	}

	public void setBabyDischargeStatus(String babyDischargeStatus) {
		this.babyDischargeStatus = babyDischargeStatus;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getDischargePrintFlag() {
		return this.dischargePrintFlag;
	}

	public void setDischargePrintFlag(String dischargePrintFlag) {
		this.dischargePrintFlag = dischargePrintFlag;
	}

	public Timestamp getDischargedate() {
		return this.dischargedate;
	}

	public void setDischargedate(Timestamp dischargedate) {
		this.dischargedate = dischargedate;
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

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

}