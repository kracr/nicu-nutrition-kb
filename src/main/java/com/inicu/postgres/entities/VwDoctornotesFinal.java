package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the vw_doctornotes_final database table.
 * 
 */
@Entity
@Table(name="vw_doctornotes_final")
@NamedQuery(name="VwDoctornotesFinal.findAll", query="SELECT v FROM VwDoctornotesFinal v")
public class VwDoctornotesFinal implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private Long doctornoteid;
	
	@Temporal(TemporalType.DATE)
	private Date creationdate;
	
	
	private Timestamp noteentrytime;
	/*private String currentage;

	@Column(columnDefinition="Float4")
	private Float currentdateheight;

	@Column(columnDefinition="Float4")
	private Float currentdateweight;
*/
	private Timestamp creationtime;

	private Timestamp modificationtime;

	
	private String doctornotes;

	@Temporal(TemporalType.DATE)
	private Date dateofadmission;

	@Column(name="ivfluidml_perhr",columnDefinition="float4")
	private Float ivfluidmlPerhr;

	private String ivfluidtype;

	@Column(name="total_ivfluids",columnDefinition="float4")
	private Float totalIvfluids;



	private String diagnosis;

	/*@Column(columnDefinition="Float4")
	private Float diffheight;

	@Column(columnDefinition="Float4")
	private Float diffweight;
*/
	@Column(columnDefinition="bool")
	private Boolean ebm;

	@Column(columnDefinition="Float4")
	private Float ebmperdayml;

	private String feedduration;

	@Column(columnDefinition="Float4")
	private Float feedvolume;

	private String gender;

	@Column(columnDefinition="bool")
	private Boolean hmf;

	@Column(columnDefinition="Float4")
	private Float hmfperdayml;

	private String issues;

	@Column(columnDefinition="bool")
	private Boolean lbfm;

	@Column(columnDefinition="Float4")
	private Float lbfmperdayml;

	private String plan;

	@Column(columnDefinition="bool")
	private Boolean tfm;

	@Column(columnDefinition="Float4")
	private Float tfmperdayml;

	@Column(name="totalfeed_ml_day",columnDefinition="Float4")
	private Float totalfeedMlDay;

	@Column(name="totalfluid_ml_day",columnDefinition="Float4")
	private Float totalfluidMlDay;

	private String uhid;

	/*@Temporal(TemporalType.DATE)
	private Date visitdate;
*/
	//new parameter after changed notes ui..

	@Column(columnDefinition="bool")
	private Boolean productmilk;

	@Column(columnDefinition="float4")
	private Float productmilkperdayml;

	@Column(name="hmf_ebm",columnDefinition="bool")
	private Boolean hmfEbm;

	@Column(name="hmf_ebm_perdayml",columnDefinition="float4")
	private Float hmfEbmPerdayml;

	private String feedmethod;

	private String followupnotes;

	private String leftreactivity;
	
	private String leftpupilsize;
/*
	private Long totalinput;

	private Long totaloutput;
*/	
	
	private String babyname;
	
	private Integer gestationdaysbylmp;

	private Integer gestationweekbylmp;

	private String admittingdoctor;
	
	@Column(columnDefinition="bool")
	private Boolean formulamilk;
	
	  private String formulamilkperdayml;	
	
	
	public String getAdmittingdoctor() {
		return admittingdoctor;
	}

	public void setAdmittingdoctor(String admittingdoctor) {
		this.admittingdoctor = admittingdoctor;
	}

	public Integer getGestationdaysbylmp() {
		return gestationdaysbylmp;
	}

	public void setGestationdaysbylmp(Integer gestationdaysbylmp) {
		this.gestationdaysbylmp = gestationdaysbylmp;
	}

	public Integer getGestationweekbylmp() {
		return gestationweekbylmp;
	}

	public void setGestationweekbylmp(Integer gestationweekbylmp) {
		this.gestationweekbylmp = gestationweekbylmp;
	}



	public String getBabyname() {
		return babyname;
	}

	public void setBabyname(String babyname) {
		this.babyname = babyname;
	}

	public Date getDateofadmission() {
		return this.dateofadmission;
	}

	public void setDateofadmission(Date dateofadmission) {
		this.dateofadmission = dateofadmission;
	}


	public Float getIvfluidmlPerhr() {
		return ivfluidmlPerhr;
	}

	public void setIvfluidmlPerhr(Float ivfluidmlPerhr) {
		this.ivfluidmlPerhr = ivfluidmlPerhr;
	}

	public String getIvfluidtype() {
		return ivfluidtype;
	}

	public void setIvfluidtype(String ivfluidtype) {
		this.ivfluidtype = ivfluidtype;
	}

	public Float getTotalIvfluids() {
		return totalIvfluids;
	}

	public void setTotalIvfluids(Float totalIvfluids) {
		this.totalIvfluids = totalIvfluids;
	}

	public String getDiagnosis() {
		return this.diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}


	public Boolean getEbm() {
		return this.ebm;
	}

	public void setEbm(Boolean ebm) {
		this.ebm = ebm;
	}

	public Float getEbmperdayml() {
		return this.ebmperdayml;
	}

	public void setEbmperdayml(Float ebmperdayml) {
		this.ebmperdayml = ebmperdayml;
	}

	public String getFeedduration() {
		return this.feedduration;
	}

	public void setFeedduration(String feedduration) {
		this.feedduration = feedduration;
	}

	public Float getFeedvolume() {
		return this.feedvolume;
	}

	public void setFeedvolume(Float feedvolume) {
		this.feedvolume = feedvolume;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Boolean getHmf() {
		return this.hmf;
	}

	public void setHmf(Boolean hmf) {
		this.hmf = hmf;
	}

	public Float getHmfperdayml() {
		return this.hmfperdayml;
	}

	public void setHmfperdayml(Float hmfperdayml) {
		this.hmfperdayml = hmfperdayml;
	}

	public String getIssues() {
		return this.issues;
	}

	public void setIssues(String issues) {
		this.issues = issues;
	}

	public Boolean getLbfm() {
		return this.lbfm;
	}

	public void setLbfm(Boolean lbfm) {
		this.lbfm = lbfm;
	}

	public Float getLbfmperdayml() {
		return this.lbfmperdayml;
	}

	public void setLbfmperdayml(Float lbfmperdayml) {
		this.lbfmperdayml = lbfmperdayml;
	}

	public String getPlan() {
		return this.plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public Boolean getTfm() {
		return this.tfm;
	}

	public void setTfm(Boolean tfm) {
		this.tfm = tfm;
	}

	public Float getTfmperdayml() {
		return this.tfmperdayml;
	}

	public void setTfmperdayml(Float tfmperdayml) {
		this.tfmperdayml = tfmperdayml;
	}

	public Float getTotalfeedMlDay() {
		return this.totalfeedMlDay;
	}

	public void setTotalfeedMlDay(Float totalfeedMlDay) {
		this.totalfeedMlDay = totalfeedMlDay;
	}

	public Float getTotalfluidMlDay() {
		return this.totalfluidMlDay;
	}

	public void setTotalfluidMlDay(Float totalfluidMlDay) {
		this.totalfluidMlDay = totalfluidMlDay;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}


	public String getDoctornotes() {
		return doctornotes;
	}

	public void setDoctornotes(String doctornotes) {
		this.doctornotes = doctornotes;
	}

	public Boolean getProductmilk() {
		return productmilk;
	}

	public void setProductmilk(Boolean productmilk) {
		this.productmilk = productmilk;
	}

	public Float getProductmilkperdayml() {
		return productmilkperdayml;
	}

	public void setProductmilkperdayml(Float productmilkperdayml) {
		this.productmilkperdayml = productmilkperdayml;
	}

	public Boolean getHmfEbm() {
		return hmfEbm;
	}

	public void setHmfEbm(Boolean hmfEbm) {
		this.hmfEbm = hmfEbm;
	}

	public Float getHmfEbmPerdayml() {
		return hmfEbmPerdayml;
	}

	public void setHmfEbmPerdayml(Float hmfEbmPerdayml) {
		this.hmfEbmPerdayml = hmfEbmPerdayml;
	}

	public String getFeedmethod() {
		return feedmethod;
	}

	public void setFeedmethod(String feedmethod) {
		this.feedmethod = feedmethod;
	}

	public String getFollowupnotes() {
		return followupnotes;
	}

	public void setFollowupnotes(String followupnotes) {
		this.followupnotes = followupnotes;
	}



	public String getLeftreactivity() {
		return leftreactivity;
	}

	public void setLeftreactivity(String leftreactivity) {
		this.leftreactivity = leftreactivity;
	}

	public String getLeftpupilsize() {
		return leftpupilsize;
	}

	public void setLeftpupilsize(String leftpupilsize) {
		this.leftpupilsize = leftpupilsize;
	}

	public Date getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}

	public Long getDoctornoteid() {
		return doctornoteid;
	}

	public void setDoctornoteid(Long doctornoteid) {
		this.doctornoteid = doctornoteid;
	}

	public VwDoctornotesFinal() {
		
		this.doctornoteid = Long.valueOf(0);;
		this.creationdate =null;
/*		this.currentage = "";
		this.currentdateheight = Float.valueOf(0);;
		this.currentdateweight = Float.valueOf(0);;
*/		this.doctornotes = "";
		this.dateofadmission = new Date();
		this.ivfluidmlPerhr = Float.valueOf(0);;
		this.ivfluidtype = "";
		this.totalIvfluids = Float.valueOf(0);;
		this.diagnosis = "";
/*		this.diffheight = Float.valueOf(0);;
		this.diffweight = Float.valueOf(0);;
*/		this.ebm = false;
		this.ebmperdayml = Float.valueOf(0);;
		this.feedvolume = Float.valueOf(0);;
		this.gender = "";
		this.hmf = false;
		this.hmfperdayml = Float.valueOf(0);;
		this.issues = "";
		this.lbfm = false;
		this.lbfmperdayml = Float.valueOf(0);;
		this.plan = "";
		this.tfm = false;
		this.tfmperdayml = Float.valueOf(0);;
		this.totalfeedMlDay = Float.valueOf(0);;
		this.totalfluidMlDay = Float.valueOf(0);;
		this.uhid = "";
/*		this.visitdate = null;
*/		this.productmilk = false;
		this.productmilkperdayml = Float.valueOf(0);;
		this.hmfEbm = false;
		this.hmfEbmPerdayml = Float.valueOf(0);
		this.feedmethod = "";
		this.followupnotes = "";
		this.leftreactivity = "";
		this.leftpupilsize="";
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

	
	public Boolean getFormulamilk() {
		return formulamilk;
	}

	public void setFormulamilk(Boolean formulamilk) {
		this.formulamilk = formulamilk;
	}

	public String getFormulamilkperdayml() {
		return formulamilkperdayml;
	}

	public void setFormulamilkperdayml(String formulamilkperdayml) {
		this.formulamilkperdayml = formulamilkperdayml;
	}

	public Timestamp getNoteentrytime() {
		return noteentrytime;
	}

	public void setNoteentrytime(Timestamp noteentrytime) {
		this.noteentrytime = noteentrytime;
	}

	


}