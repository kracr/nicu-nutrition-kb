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
import javax.persistence.Transient;

/**
 * The persistent class for the sa_resp_apnea database table.
 * 
 */
@Entity
@Table(name = "nursing_medication")
@NamedQuery(name = "NursingMedication.findAll", query = "SELECT s FROM NursingMedication s")
public class NursingMedication implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long nursing_medication_id;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String uhid;

	private String loggeduser;

	private String baby_presid;

	@Column(columnDefinition = "Float4")
	private Float given_dose;

	private Timestamp given_time;

	private Timestamp next_dose;

	@Column(columnDefinition = "Float4")
	private Float delivered_volume;

	private String nursing_comment;

	@Column(columnDefinition = "bool")
	private Boolean stop_flag;

	private Integer day_number;
	
	private String brand;
	
	private String episodeid;

	@Transient
	private Boolean visibility;

	@Column(columnDefinition = "bool")
	private Boolean assessment_medicine;
	
	@Column(columnDefinition = "bool")
	private Boolean flag;

	public NursingMedication() {
		super();
		this.assessment_medicine = false;
	}

	public Long getNursing_medication_id() {
		return nursing_medication_id;
	}

	public void setNursing_medication_id(Long nursing_medication_id) {
		this.nursing_medication_id = nursing_medication_id;
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

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public String getBaby_presid() {
		return baby_presid;
	}

	public void setBaby_presid(String baby_presid) {
		this.baby_presid = baby_presid;
	}

	public Timestamp getGiven_time() {
		return given_time;
	}

	public void setGiven_time(Timestamp given_time) {
		this.given_time = given_time;
	}

	public Float getDelivered_volume() {
		return delivered_volume;
	}

	public void setDelivered_volume(Float delivered_volume) {
		this.delivered_volume = delivered_volume;
	}

	public Float getGiven_dose() {
		return given_dose;
	}

	public void setGiven_dose(Float given_dose) {
		this.given_dose = given_dose;
	}

	public Timestamp getNext_dose() {
		return next_dose;
	}

	public void setNext_dose(Timestamp next_dose) {
		this.next_dose = next_dose;
	}

	public String getNursing_comment() {
		return nursing_comment;
	}

	public void setNursing_comment(String nursing_comment) {
		this.nursing_comment = nursing_comment;
	}

	public Boolean getStop_flag() {
		return stop_flag;
	}

	public void setStop_flag(Boolean stop_flag) {
		this.stop_flag = stop_flag;
	}

	public Integer getDay_number() {
		return day_number;
	}

	public void setDay_number(Integer day_number) {
		this.day_number = day_number;
	}

	public Boolean getVisibility() {
		return visibility;
	}

	public void setVisibility(Boolean visibility) {
		this.visibility = visibility;
	}

	public Boolean getAssessment_medicine() {
		return assessment_medicine;
	}

	public void setAssessment_medicine(Boolean assessment_medicine) {
		this.assessment_medicine = assessment_medicine;
	}
	
	public String getEpisodeid() {
		return episodeid;
	}

	public void setEpisodeid(String episodeid) {
		this.episodeid = episodeid;
	}
	
	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return "NursingMedication [nursing_medication_id=" + nursing_medication_id + ", creationtime=" + creationtime
				+ ", modificationtime=" + modificationtime + ", uhid=" + uhid + ", loggeduser=" + loggeduser
				+ ", baby_presid=" + baby_presid + ", given_dose=" + given_dose + ", given_time=" + given_time
				+ ", next_dose=" + next_dose + ", delivered_volume=" + delivered_volume + ", nursing_comment="
				+ nursing_comment + ", stop_flag=" + stop_flag + ", day_number=" + day_number + ", visibility="
				+ visibility + ", assessment_medicine=" + assessment_medicine + "]";
	}

}
