package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the vw_nursingnotes database table.
 * 
 */
//@Entity
@Table(name="vw_nursingnotes")
@NamedQuery(name="VwNursingnote.findAll", query="SELECT v FROM VwNursingnote v")
public class VwNursingnote implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(columnDefinition="float4")
	private Float aaperhr;

	@Column(name="aspire_ml",columnDefinition="float4")
	private Float aspireMl;
	
	@Column(columnDefinition="bool")
	private Boolean blood;

	@Column(name="bo_colour")
	private String boColour;

	private String bp;

	@Temporal(TemporalType.DATE)
	private Date creationdate;

	private Timestamp creationtime;
	@Column(columnDefinition="bool")
	private Boolean ecg;

	private String episode;

	private String feedmethod;

	private String feedtype;

	
	@Column(columnDefinition="float4")
	private Float feedvolume;
	
	@Column(columnDefinition="float4")
	private Float heartrate;

	
	private String  hmfvalue;
	
	@Column(columnDefinition="float4")
	private Float ivperhr;
	
	@Column(columnDefinition="float4")
	private Float  ivtotal;

	private String ivtype;

	@Column(columnDefinition="float4")
	private Float lipidperhr;

	private Timestamp modificationtime;
	
	@Id
	private Long nursingnoteid;

	private String nursingnotestime;
	
	@Column(columnDefinition="bool")
	private Boolean oxygen;

	private String pnrate;

	@Column(columnDefinition="float4")
	private Float pulserate;

	@Column(columnDefinition="float4")
	private Float resprate;
	
	@Column(columnDefinition="float4")
	private Float sao2;

	@Column(name="tempaxilla_f")
	private String tempaxillaF;

	@Column(name="tempbed_c")
	private String tempbedC;


	private String uhid;

	@Column(name="urine_ml",columnDefinition="float4")
	private Float urineMl;

	@Column(columnDefinition="bool")
	private Boolean passstool;
	
	private String stool;
	
	public Float getAaperhr() {
		return aaperhr;
	}

	public void setAaperhr(Float aaperhr) {
		this.aaperhr = aaperhr;
	}

	public Float getAspireMl() {
		return aspireMl;
	}

	public void setAspireMl(Float aspireMl) {
		this.aspireMl = aspireMl;
	}

	public Boolean getBlood() {
		return blood;
	}

	public void setBlood(Boolean blood) {
		this.blood = blood;
	}

	public String getBoColour() {
		return boColour;
	}

	public void setBoColour(String boColour) {
		this.boColour = boColour;
	}

	public String getBp() {
		return bp;
	}

	public void setBp(String bp) {
		this.bp = bp;
	}

	public Date getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public Boolean getEcg() {
		return ecg;
	}

	public void setEcg(Boolean ecg) {
		this.ecg = ecg;
	}

	public String getEpisode() {
		return episode;
	}

	public void setEpisode(String episode) {
		this.episode = episode;
	}

	public String getFeedmethod() {
		return feedmethod;
	}
	

	public void setFeedmethod(String feedmethod) {
		this.feedmethod = feedmethod;
	}

	public String getFeedtype() {
		return feedtype;
	}

	public void setFeedtype(String feedtype) {
		this.feedtype = feedtype;
	}

	public Float getFeedvolume() {
		return feedvolume;
	}

	public void setFeedvolume(Float feedvolume) {
		this.feedvolume = feedvolume;
	}

	public Float getHeartrate() {
		return heartrate;
	}

	public void setHeartrate(Float heartrate) {
		this.heartrate = heartrate;
	}

	public String getHmfvalue() {
		return hmfvalue;
	}

	public void setHmfvalue(String hmfvalue) {
		this.hmfvalue = hmfvalue;
	}

	public Float getIvperhr() {
		return ivperhr;
	}

	public void setIvperhr(Float ivperhr) {
		this.ivperhr = ivperhr;
	}

	public Float getIvtotal() {
		return ivtotal;
	}

	public void setIvtotal(Float ivtotal) {
		this.ivtotal = ivtotal;
	}

	public String getIvtype() {
		return ivtype;
	}

	public void setIvtype(String ivtype) {
		this.ivtype = ivtype;
	}

	public Float getLipidperhr() {
		return lipidperhr;
	}

	public void setLipidperhr(Float lipidperhr) {
		this.lipidperhr = lipidperhr;
	}

	public Timestamp getModificationtime() {
		return modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public Long getNursingnoteid() {
		return nursingnoteid;
	}

	public void setNursingnoteid(Long nursingnoteid) {
		this.nursingnoteid = nursingnoteid;
	}

	public String getNursingnotestime() {
		return nursingnotestime;
	}

	public void setNursingnotestime(String nursingnotestime) {
		this.nursingnotestime = nursingnotestime;
	}

	public Boolean getOxygen() {
		return oxygen;
	}

	public void setOxygen(Boolean oxygen) {
		this.oxygen = oxygen;
	}

	public String getPnrate() {
		return pnrate;
	}

	public void setPnrate(String pnrate) {
		this.pnrate = pnrate;
	}

	public Float getPulserate() {
		return pulserate;
	}

	public void setPulserate(Float pulserate) {
		this.pulserate = pulserate;
	}

	public Float getResprate() {
		return resprate;
	}

	public void setResprate(Float resprate) {
		this.resprate = resprate;
	}



	public Float getSao2() {
		return sao2;
	}

	public void setSao2(Float sao2) {
		this.sao2 = sao2;
	}

	public Boolean getPassstool() {
		return passstool;
	}

	public void setPassstool(Boolean passstool) {
		this.passstool = passstool;
	}

	public String getStool() {
		return stool;
	}

	public void setStool(String stool) {
		this.stool = stool;
	}

	public String getTempaxillaF() {
		return tempaxillaF;
	}

	public void setTempaxillaF(String tempaxillaF) {
		this.tempaxillaF = tempaxillaF;
	}

	public String getTempbedC() {
		return tempbedC;
	}

	public void setTempbedC(String tempbedC) {
		this.tempbedC = tempbedC;
	}



	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public Float getUrineMl() {
		return urineMl;
	}

	public void setUrineMl(Float urineMl) {
		this.urineMl = urineMl;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public VwNursingnote() {
		super();
		this.aaperhr = Float.valueOf("0");
		this.aspireMl = Float.valueOf("0");
		this.blood = blood;
		this.boColour = boColour;
		this.bp = bp;
		this.creationdate = creationdate;
		this.creationtime = creationtime;
		this.ecg = ecg;
		this.episode = episode;
		this.feedmethod = feedmethod;
		this.feedtype = feedtype;
		this.feedvolume = Float.valueOf("0");
		this.heartrate = heartrate;
		this.hmfvalue = "";
		this.ivperhr = Float.valueOf("0");
		this.ivtotal = Float.valueOf("0");
		this.ivtype = ivtype;
		this.lipidperhr = Float.valueOf("0");
		this.modificationtime = modificationtime;
		this.nursingnoteid = nursingnoteid;
		this.nursingnotestime = nursingnotestime;
		this.oxygen = oxygen;
		this.pnrate = pnrate;
		this.pulserate = pulserate;
		this.resprate = resprate;
		this.sao2 = sao2;
		this.tempaxillaF = tempaxillaF;
		this.tempbedC = tempbedC;
		this.uhid = uhid;
		this.urineMl = urineMl;
		this.passstool = false;
		this.stool = "";
	}


	
}