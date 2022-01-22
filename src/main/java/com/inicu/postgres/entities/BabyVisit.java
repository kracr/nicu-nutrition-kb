package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * The persistent class for the baby_visit database table.
 * 
 */
@Entity
@Table(name = "baby_visit")
@NamedQuery(name = "BabyVisit.findAll", query = "SELECT b FROM BabyVisit b")
public class BabyVisit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long visitid;

	@Column(name = "corrected_ga")
	private String correctedGa;
	
	@Column(columnDefinition = "float4")
	private Float admissionWeight;
	
	@Column(name = "admission_length",columnDefinition = "float4")
	private Float admissionLength;
	
	@Column(name = "admission_head_circumference",columnDefinition = "float4")
	private Float admissionHeadCircumference;

	private Timestamp creationtime;

	private String currentage;

	@Column(columnDefinition = "float4")
	private Float currentdateheadcircum;

	@Column(columnDefinition = "float4")
	private Float currentdateheight;

	@Column(columnDefinition = "float4")
	private Float currentdateweight;

	@Column(name = "ga_at_birth")
	private String gaAtBirth;

	private Timestamp modificationtime;

	private String nicuday;

	private String uhid;

	@Temporal(TemporalType.DATE)
	private Date visitdate;
	
	private Time visittime;

	@Column(columnDefinition = "float4")
	private Float workingweight;

	@Column(name = "weight_for_cal",columnDefinition = "float4")
	private Float weightForCal;

	private String loggeduser;

	// surgery details ..
	private String surgery;

	private String surgeon;

	private String neonatologist;

	@Transient
	private Float prevDateWeight;

	@Transient
	private String actualGestation; // gestation by usg.

	private String episodeid;

	@Column(columnDefinition = "bool")
	private Boolean admission_entry;
	
	private String daysAfterBirth;
	
	private Integer gestationWeek;
	
	private Integer gestationDays;
	
	private String comments;

	@Transient
	private String dateOfBirth;

	@Transient
	private Timestamp entryDateOfBabyVisit;

	public Timestamp getEntryDateOfBabyVisit() {
		return entryDateOfBabyVisit;
	}

	public void setEntryDateOfBabyVisit(Timestamp entryDateOfBabyVisit) {
		this.entryDateOfBabyVisit = entryDateOfBabyVisit;
	}

	public BabyVisit() {
		super();
	}

	public Long getVisitid() {
		return this.visitid;
	}

	public void setVisitid(Long visitid) {
		this.visitid = visitid;
	}

	public String getCorrectedGa() {
		return this.correctedGa;
	}

	public void setCorrectedGa(String correctedGa) {
		this.correctedGa = correctedGa;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getCurrentage() {
		return this.currentage;
	}

	public void setCurrentage(String currentage) {
		this.currentage = currentage;
	}

	public Float getCurrentdateheadcircum() {
		return this.currentdateheadcircum;
	}

	public void setCurrentdateheadcircum(Float currentdateheadcircum) {
		this.currentdateheadcircum = currentdateheadcircum;
	}

	public Float getCurrentdateheight() {
		return this.currentdateheight;
	}

	public void setCurrentdateheight(Float currentdateheight) {
		this.currentdateheight = currentdateheight;
	}

	public Float getCurrentdateweight() {
		return this.currentdateweight;
	}

	public void setCurrentdateweight(Float currentdateweight) {
		this.currentdateweight = currentdateweight;
	}

	public String getGaAtBirth() {
		return this.gaAtBirth;
	}

	public void setGaAtBirth(String gaAtBirth) {
		this.gaAtBirth = gaAtBirth;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getNicuday() {
		return this.nicuday;
	}

	public void setNicuday(String nicuday) {
		this.nicuday = nicuday;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public Date getVisitdate() {
		return this.visitdate;
	}

	public void setVisitdate(Date visitdate) {
		this.visitdate = visitdate;
	}

	public Float getWorkingweight() {
		return this.workingweight;
	}

	public void setWorkingweight(Float workingweight) {
		this.workingweight = workingweight;
	}

	public Float getWeightForCal() {
		return weightForCal;
	}

	public void setWeightForCal(Float weightForCal) {
		this.weightForCal = weightForCal;
	}

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public String getSurgery() {
		return surgery;
	}

	public void setSurgery(String surgery) {
		this.surgery = surgery;
	}

	public String getSurgeon() {
		return surgeon;
	}

	public void setSurgeon(String surgeon) {
		this.surgeon = surgeon;
	}

	public String getNeonatologist() {
		return neonatologist;
	}

	public void setNeonatologist(String neonatologist) {
		this.neonatologist = neonatologist;
	}

	public Float getPrevDateWeight() {
		return prevDateWeight;
	}

	public void setPrevDateWeight(Float prevDateWeight) {
		this.prevDateWeight = prevDateWeight;
	}

	public String getActualGestation() {
		return actualGestation;
	}

	public void setActualGestation(String actualGestation) {
		this.actualGestation = actualGestation;
	}

	public String getEpisodeid() {
		return episodeid;
	}

	public void setEpisodeid(String episodeid) {
		this.episodeid = episodeid;
	}

	public Boolean getAdmission_entry() {
		return admission_entry;
	}

	public void setAdmission_entry(Boolean admission_entry) {
		this.admission_entry = admission_entry;
	}

	public String getDaysAfterBirth() {
		return daysAfterBirth;
	}

	public void setDaysAfterBirth(String daysAfterBirth) {
		this.daysAfterBirth = daysAfterBirth;
	}

	public Integer getGestationWeek() {
		return gestationWeek;
	}

	public Integer getGestationDays() {
		return gestationDays;
	}

	public void setGestationWeek(Integer gestationWeek) {
		this.gestationWeek = gestationWeek;
	}

	public void setGestationDays(Integer gestationDays) {
		this.gestationDays = gestationDays;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Time getVisittime() {
		return visittime;
	}

	public void setVisittime(Time visittime) {
		this.visittime = visittime;
	}
	public Float getAdmissionWeight() {
		return admissionWeight;
	}

	public void setAdmissionWeight(Float admissionWeight) {
		this.admissionWeight = admissionWeight;
	}
	public Float getAdmissionLength() {
		return admissionLength;
	}

	public void setAdmissionLength(Float admissionLength) {
		this.admissionLength = admissionLength;
	}
	public Float getAdmissionHeadCircumference() {
		return admissionHeadCircumference;
	}

	public void setAdmissionHeadCircumference(Float admissionHeadCircumference) {
		this.admissionHeadCircumference = admissionHeadCircumference;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
}