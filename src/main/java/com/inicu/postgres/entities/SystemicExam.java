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
 * The persistent class for the systemic_exam database table.
 * 
 * @author Sourabh Verma
 */
@Entity
@Table(name = "systemic_exam")
@NamedQuery(name = "SystemicExam.findAll", query = "SELECT s FROM SystemicExam s")
public class SystemicExam implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long systemic_exam_id;

	@Column(columnDefinition = "timestamp with time zone")
	private Timestamp creationtime;

	@Column(columnDefinition = "timestamp with time zone")
	private Timestamp modificationtime;

	private String uhid;

	private String episodeid;

	private String loggeduser;

	@Column(columnDefinition = "bool")
	private Boolean jaundice;
	
	@Column(columnDefinition = "bool")
	private Boolean renal;


	@Transient
	private String respiratorySystem;

	@Column(columnDefinition = "bool")
	private Boolean rds;

	@Column(columnDefinition = "bool")
	private Boolean apnea;

	@Column(columnDefinition = "bool")
	private Boolean pphn;

	@Column(columnDefinition = "bool")
	private Boolean pneumothorax;

	@Transient
	private String infection;

	@Column(columnDefinition = "bool")
	private Boolean sepsis;

	@Column(columnDefinition = "bool")
	private Boolean nec;

	@Column(columnDefinition = "bool")
	private Boolean vap;

	@Column(columnDefinition = "bool")
	private Boolean clabsi;

	@Transient
	private String cns;

	@Column(columnDefinition = "bool")
	private Boolean asphyxia;

	@Column(columnDefinition = "bool")
	private Boolean seizures;

	@Column(columnDefinition = "bool")
	private Boolean ivh;

	@Transient
	private String metabolic;

	@Column(columnDefinition = "bool")
	private Boolean hypoglycemia;
	
	@Column(columnDefinition = "bool")
	private Boolean encephalopathy;

	@Column(columnDefinition = "bool")
	private Boolean neuromuscularDisorder;
	
	@Column(columnDefinition = "bool")
	private Boolean hydrocephalus;
	
	@Column(columnDefinition = "bool")
	private Boolean intrauterine;



	@Transient
	private String gi;

	@Column(columnDefinition = "bool")
	private Boolean feedIntolerance;
	
	@Transient
	private String cvs;

	@Column(columnDefinition = "bool")
	private Boolean shock;

	public SystemicExam() {
		super();
	}

	public Boolean getNec() {
		return nec;
	}

	public void setNec(Boolean nec) {
		this.nec = nec;
	}

	public Long getSystemic_exam_id() {
		return systemic_exam_id;
	}

	public void setSystemic_exam_id(Long systemic_exam_id) {
		this.systemic_exam_id = systemic_exam_id;
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

	public String getEpisodeid() {
		return episodeid;
	}

	public void setEpisodeid(String episodeid) {
		this.episodeid = episodeid;
	}

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public Boolean getJaundice() {
		return jaundice;
	}

	public void setJaundice(Boolean jaundice) {
		this.jaundice = jaundice;
	}

	public String getRespiratorySystem() {
		return respiratorySystem;
	}

	public void setRespiratorySystem(String respiratorySystem) {
		this.respiratorySystem = respiratorySystem;
	}

	public Boolean getRds() {
		return rds;
	}

	public void setRds(Boolean rds) {
		this.rds = rds;
	}

	public Boolean getApnea() {
		return apnea;
	}

	public void setApnea(Boolean apnea) {
		this.apnea = apnea;
	}

	public Boolean getPphn() {
		return pphn;
	}

	public void setPphn(Boolean pphn) {
		this.pphn = pphn;
	}

	public Boolean getPneumothorax() {
		return pneumothorax;
	}

	public void setPneumothorax(Boolean pneumothorax) {
		this.pneumothorax = pneumothorax;
	}

	public String getInfection() {
		return infection;
	}

	public void setInfection(String infection) {
		this.infection = infection;
	}

	public Boolean getSepsis() {
		return sepsis;
	}

	public void setSepsis(Boolean sepsis) {
		this.sepsis = sepsis;
	}

	public Boolean getVap() {
		return vap;
	}

	public void setVap(Boolean vap) {
		this.vap = vap;
	}

	public Boolean getClabsi() {
		return clabsi;
	}

	public void setClabsi(Boolean clabsi) {
		this.clabsi = clabsi;
	}

	public String getCns() {
		return cns;
	}

	public void setCns(String cns) {
		this.cns = cns;
	}

	public Boolean getAsphyxia() {
		return asphyxia;
	}

	public void setAsphyxia(Boolean asphyxia) {
		this.asphyxia = asphyxia;
	}

	public Boolean getSeizures() {
		return seizures;
	}

	public void setSeizures(Boolean seizures) {
		this.seizures = seizures;
	}

	public Boolean getIvh() {
		return ivh;
	}

	public void setIvh(Boolean ivh) {
		this.ivh = ivh;
	}
	
	

	public Boolean getRenal() {
		return renal;
	}

	public void setRenal(Boolean renal) {
		this.renal = renal;
	}

	@Override
	public String toString() {
		return "SystemicExam [systemic_exam_id=" + systemic_exam_id + ", creationtime=" + creationtime
				+ ", modificationtime=" + modificationtime + ", uhid=" + uhid + ", episodeid=" + episodeid
				+ ", loggeduser=" + loggeduser + ", jaundice=" + jaundice + ", respiratorySystem=" + respiratorySystem
				+ ", rds=" + rds + ", apnea=" + apnea + ", pphn=" + pphn + ", pneumothorax=" + pneumothorax
				+ ", infection=" + infection + ", sepsis=" + sepsis + ", vap=" + vap + ", clabsi=" + clabsi + ", cns="
				+ cns + ", asphyxia=" + asphyxia + ", seizures=" + seizures + ", ivh=" + ivh + "]";
	}

	public String getMetabolic() {
		return metabolic;
	}

	public void setMetabolic(String metabolic) {
		this.metabolic = metabolic;
	}

	public Boolean getHypoglycemia() {
		return hypoglycemia;
	}

	public void setHypoglycemia(Boolean hypoglycemia) {
		this.hypoglycemia = hypoglycemia;
	}

	public String getGi() {
		return gi;
	}

	public void setGi(String gi) {
		this.gi = gi;
	}

	public Boolean getFeedIntolerance() {
		return feedIntolerance;
	}

	public void setFeedIntolerance(Boolean feedIntolerance) {
		this.feedIntolerance = feedIntolerance;
	}

	public Boolean getEncephalopathy() {
		return encephalopathy;
	}

	public void setEncephalopathy(Boolean encephalopathy) {
		this.encephalopathy = encephalopathy;
	}

	public Boolean getNeuromuscularDisorder() {
		return neuromuscularDisorder;
	}

	public void setNeuromuscularDisorder(Boolean neuromuscularDisorder) {
		this.neuromuscularDisorder = neuromuscularDisorder;
	}

	public Boolean getHydrocephalus() {
		return hydrocephalus;
	}

	public void setHydrocephalus(Boolean hydrocephalus) {
		this.hydrocephalus = hydrocephalus;
	}

	public Boolean getIntrauterine() {
		return intrauterine;
	}

	public void setIntrauterine(Boolean intrauterine) {
		this.intrauterine = intrauterine;
	}

	public String getCvs() {
		return cvs;
	}

	public void setCvs(String cvs) {
		this.cvs = cvs;
	}

	public Boolean getShock() {
		return shock;
	}

	public void setShock(Boolean shock) {
		this.shock = shock;
	}
	

}
