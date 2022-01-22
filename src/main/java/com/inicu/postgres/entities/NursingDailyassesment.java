package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;

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




@Entity
@Table(name="nursing_dailyassesment")
@NamedQuery(name="NuringDailyassesment.findAll", query="SELECT n FROM NursingDailyassesment n")
public class NursingDailyassesment implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="nn_assesmentid")
	private Long nnAssesmentid;

	@Column(name="action_sore")
	private String actionSore;

	@Column(name="back_care",columnDefinition="boolean")
	private boolean backCare;

	@Column(name="chest_pt",columnDefinition="boolean")
	private boolean chestPt;

	private Timestamp creationtime;

	@Column(name="cuff_check",columnDefinition="boolean")
	private boolean cuffCheck;

	@Column(name="dailyassesment_time")
	private String dailyassesmentTime;

	@Column(name="describe_sore")
	private String describeSore;

	@Column(name="eye_care",columnDefinition="boolean")
	private boolean eyeCare;

	@Column(name="hair_care",columnDefinition="boolean")
	private boolean hairCare;

	@Column(name="limb_pt",columnDefinition="boolean")
	private boolean limbPt;

	private String loggeduser;

	private Timestamp modificationtime;

	@Column(name="mother_care",columnDefinition="boolean")
	private boolean motherCare;

	@Column(name="pedal_pulse_left",columnDefinition="boolean")
	private boolean pedalPulseLeft;

	@Column(name="pedal_pulse_right",columnDefinition="boolean")
	private boolean pedalPulseRight;

	private String pressuresite;

	@Column(name="radial_pulse_left",columnDefinition="boolean")
	private boolean radialPulseLeft;

	@Column(name="radial_pulse_right",columnDefinition="boolean")
	private boolean radialPulseRight;

	private String skinintegrity;

	@Column(name="sponge_bath",columnDefinition="boolean")
	private boolean spongeBath;

	@Column(name="trach_etcare",columnDefinition="boolean")
	private boolean trachEtcare;

	private String uhid;

	public NursingDailyassesment() {
		spongeBath = false;
		radialPulseRight = false;
		radialPulseLeft = false;
		pedalPulseRight = false;
		pedalPulseLeft = false;
		backCare = false;
		chestPt = false;
		cuffCheck = false;
		eyeCare = false;
		hairCare = false;
		limbPt = false;
		motherCare = false;
		trachEtcare = false;
	}

	public Long getNnAssesmentid() {
		return this.nnAssesmentid;
	}

	public void setNnAssesmentid(Long nnAssesmentid) {
		this.nnAssesmentid = nnAssesmentid;
	}

	public String getActionSore() {
		return this.actionSore;
	}

	public void setActionSore(String actionSore) {
		this.actionSore = actionSore;
	}

	public Boolean getBackCare() {
		return this.backCare;
	}

	public void setBackCare(boolean backCare) {
		this.backCare = backCare;
	}

	public Boolean getChestPt() {
		return this.chestPt;
	}

	public void setChestPt(boolean chestPt) {
		this.chestPt = chestPt;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public Boolean getCuffCheck() {
		return this.cuffCheck;
	}

	public void setCuffCheck(boolean cuffCheck) {
		this.cuffCheck = cuffCheck;
	}

	public String getDailyassesmentTime() {
		return this.dailyassesmentTime;
	}

	public void setDailyassesmentTime(String dailyassesmentTime) {
		this.dailyassesmentTime = dailyassesmentTime;
	}

	public String getDescribeSore() {
		return this.describeSore;
	}

	public void setDescribeSore(String describeSore) {
		this.describeSore = describeSore;
	}

	public Boolean getEyeCare() {
		return this.eyeCare;
	}

	public void setEyeCare(boolean eyeCare) {
		this.eyeCare = eyeCare;
	}

	public Boolean getHairCare() {
		return this.hairCare;
	}

	public void setHairCare(boolean hairCare) {
		this.hairCare = hairCare;
	}

	public Boolean getLimbPt() {
		return this.limbPt;
	}

	public void setLimbPt(boolean limbPt) {
		this.limbPt = limbPt;
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

	public Boolean getMotherCare() {
		return this.motherCare;
	}

	public void setMotherCare(boolean motherCare) {
		this.motherCare = motherCare;
	}

	public Boolean getPedalPulseLeft() {
		return this.pedalPulseLeft;
	}

	public void setPedalPulseLeft(boolean pedalPulseLeft) {
		this.pedalPulseLeft = pedalPulseLeft;
	}

	public Boolean getPedalPulseRight() {
		return this.pedalPulseRight;
	}

	public void setPedalPulseRight(boolean pedalPulseRight) {
		this.pedalPulseRight = pedalPulseRight;
	}

	public String getPressuresite() {
		return this.pressuresite;
	}

	public void setPressuresite(String pressuresite) {
		this.pressuresite = pressuresite;
	}

	public Boolean getRadialPulseLeft() {
		return this.radialPulseLeft;
	}

	public void setRadialPulseLeft(boolean radialPulseLeft) {
		this.radialPulseLeft = radialPulseLeft;
	}

	public Boolean getRadialPulseRight() {
		return this.radialPulseRight;
	}

	public void setRadialPulseRight(boolean radialPulseRight) {
		this.radialPulseRight = radialPulseRight;
	}

	public String getSkinintegrity() {
		return this.skinintegrity;
	}

	public void setSkinintegrity(String skinintegrity) {
		this.skinintegrity = skinintegrity;
	}

	public Boolean getSpongeBath() {
		return this.spongeBath;
	}

	public void setSpongeBath(boolean spongeBath) {
		this.spongeBath = spongeBath;
	}

	public Boolean getTrachEtcare() {
		return this.trachEtcare;
	}

	public void setTrachEtcare(boolean trachEtcare) {
		this.trachEtcare = trachEtcare;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

}