package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the discharge_birthdetail database table.
 * 
 */
@Entity
@Table(name="discharge_birthdetail")
@NamedQuery(name="DischargeBirthdetail.findAll", query="SELECT d FROM DischargeBirthdetail d")
public class DischargeBirthdetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private Long maternaldetailid;

	@Column(name="antenatal_risk")
	private String antenatalRisk;

	@Column(name="apgar_score_10min")
	private String apgarScore10min;

	@Column(name="apgar_score_1min")
	private String apgarScore1min;

	@Column(name="apgar_score_5min")
	private String apgarScore5min;

	private String cns;

	

	private String cry;

	@Column(name="cvs_brief")
	private String cvsBrief;

	private String deliverymode;

	@Column(name="hr_rate")
	private String hrRate;

	private String liquor;

	@Column(name="maternal_comments")
	private String maternalComments;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String motherbloodgroup;

	private String pa;

	private String prefusion;

	private String presentation;

	private String probleminnicu;

	@Column(name="rr_rate")
	private String rrRate;

	@Column(name="rs_brief")
	private String rsBrief;

	private String saturation;

	private String termorpreterm;

	private String uhid;

	public DischargeBirthdetail() {
	}

	public Long getMaternaldetailid() {
		return this.maternaldetailid;
	}

	public void setMaternaldetailid(Long maternaldetailid) {
		this.maternaldetailid = maternaldetailid;
	}

	public String getAntenatalRisk() {
		return this.antenatalRisk;
	}

	public void setAntenatalRisk(String antenatalRisk) {
		this.antenatalRisk = antenatalRisk;
	}

	public String getApgarScore10min() {
		return this.apgarScore10min;
	}

	public void setApgarScore10min(String apgarScore10min) {
		this.apgarScore10min = apgarScore10min;
	}

	public String getApgarScore1min() {
		return this.apgarScore1min;
	}

	public void setApgarScore1min(String apgarScore1min) {
		this.apgarScore1min = apgarScore1min;
	}

	public String getApgarScore5min() {
		return this.apgarScore5min;
	}

	public void setApgarScore5min(String apgarScore5min) {
		this.apgarScore5min = apgarScore5min;
	}

	public String getCns() {
		return this.cns;
	}

	public void setCns(String cns) {
		this.cns = cns;
	}

	

	public String getCry() {
		return this.cry;
	}

	public void setCry(String cry) {
		this.cry = cry;
	}

	public String getCvsBrief() {
		return this.cvsBrief;
	}

	public void setCvsBrief(String cvsBrief) {
		this.cvsBrief = cvsBrief;
	}

	public String getDeliverymode() {
		return this.deliverymode;
	}

	public void setDeliverymode(String deliverymode) {
		this.deliverymode = deliverymode;
	}

	public String getHrRate() {
		return this.hrRate;
	}

	public void setHrRate(String hrRate) {
		this.hrRate = hrRate;
	}

	public String getLiquor() {
		return this.liquor;
	}

	public void setLiquor(String liquor) {
		this.liquor = liquor;
	}

	public String getMaternalComments() {
		return this.maternalComments;
	}

	public void setMaternalComments(String maternalComments) {
		this.maternalComments = maternalComments;
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

	public String getMotherbloodgroup() {
		return this.motherbloodgroup;
	}

	public void setMotherbloodgroup(String motherbloodgroup) {
		this.motherbloodgroup = motherbloodgroup;
	}

	public String getPa() {
		return this.pa;
	}

	public void setPa(String pa) {
		this.pa = pa;
	}

	public String getPrefusion() {
		return this.prefusion;
	}

	public void setPrefusion(String prefusion) {
		this.prefusion = prefusion;
	}

	public String getPresentation() {
		return this.presentation;
	}

	public void setPresentation(String presentation) {
		this.presentation = presentation;
	}

	public String getProbleminnicu() {
		return this.probleminnicu;
	}

	public void setProbleminnicu(String probleminnicu) {
		this.probleminnicu = probleminnicu;
	}

	public String getRrRate() {
		return this.rrRate;
	}

	public void setRrRate(String rrRate) {
		this.rrRate = rrRate;
	}

	public String getRsBrief() {
		return this.rsBrief;
	}

	public void setRsBrief(String rsBrief) {
		this.rsBrief = rsBrief;
	}

	public String getSaturation() {
		return this.saturation;
	}

	public void setSaturation(String saturation) {
		this.saturation = saturation;
	}

	public String getTermorpreterm() {
		return this.termorpreterm;
	}

	public void setTermorpreterm(String termorpreterm) {
		this.termorpreterm = termorpreterm;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

}