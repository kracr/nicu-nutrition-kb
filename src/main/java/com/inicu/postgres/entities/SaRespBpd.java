package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;



/**
 * The persistent class for the sa_resp_bpd database table.
 * 
 */
@Entity
@Table(name="sa_resp_bpd")
@NamedQuery(name="SaRespBpd.findAll", query="SELECT s FROM SaRespBpd s")
public class SaRespBpd implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long respbpdid;
	private String uhid;
	private String loggeduser;
	//Not in use anymore
	private String resp_system_status;
    private String eventstatus;
    private String ageatassesment;
    private String gestationalage;
    private String cumulativedaysonoxygen;
    private String cumulativedaysonventilator;
    private String respiratorystatus;
    private String severityofbpd;
    private String treatmentplan;
    private String medicaltreatment;
    private String antibiotic;
    private String othertreatment;
    private String bpd_plan;
    private String reassestime;
    private String reassesstimetype;
    private String progressnotes;
	private Timestamp creationtime;
	private Timestamp modificationtime;
	
	@Transient
	private List<String> treatmentActionList;
	
	
	
	public List<String> getTreatmentActionList() {
		return treatmentActionList;
	}
	public void setTreatmentActionList(List<String> treatmentActionList) {
		this.treatmentActionList = treatmentActionList;
	}
	public Long getRespbpdid() {
		return respbpdid;
	}
	public void setRespbpdid(Long respbpdid) {
		this.respbpdid = respbpdid;
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
	
	public String getProgressnotes() {
		return progressnotes;
	}
	public void setProgressnotes(String progressnotes) {
		this.progressnotes = progressnotes;
	}
	public String getResp_system_status() {
		return resp_system_status;
	}
	public void setResp_system_status(String resp_system_status) {
		this.resp_system_status = resp_system_status;
	}
	public String getEventstatus() {
		return eventstatus;
	}
	public void setEventstatus(String eventstatus) {
		this.eventstatus = eventstatus;
	}
	public String getAgeatassesment() {
		return ageatassesment;
	}
	public void setAgeatassesment(String ageatassesment) {
		this.ageatassesment = ageatassesment;
	}
	public String getGestationalage() {
		return gestationalage;
	}
	public void setGestationalage(String gestationalage) {
		this.gestationalage = gestationalage;
	}
	public String getCumulativedaysonoxygen() {
		return cumulativedaysonoxygen;
	}
	public void setCumulativedaysonoxygen(String cumulativedaysonoxygen) {
		this.cumulativedaysonoxygen = cumulativedaysonoxygen;
	}
	public String getCumulativedaysonventilator() {
		return cumulativedaysonventilator;
	}
	public void setCumulativedaysonventilator(String cumulativedaysonventilator) {
		this.cumulativedaysonventilator = cumulativedaysonventilator;
	}
	public String getRespiratorystatus() {
		return respiratorystatus;
	}
	public void setRespiratorystatus(String respiratorystatus) {
		this.respiratorystatus = respiratorystatus;
	}
	public String getSeverityofbpd() {
		return severityofbpd;
	}
	public void setSeverityofbpd(String severityofbpd) {
		this.severityofbpd = severityofbpd;
	}
	public String getTreatmentplan() {
		return treatmentplan;
	}
	public void setTreatmentplan(String treatmentplan) {
		this.treatmentplan = treatmentplan;
	}
	public String getMedicaltreatment() {
		return medicaltreatment;
	}
	public void setMedicaltreatment(String medicaltreatment) {
		this.medicaltreatment = medicaltreatment;
	}
	public String getAntibiotic() {
		return antibiotic;
	}
	public void setAntibiotic(String antibiotic) {
		this.antibiotic = antibiotic;
	}
	public String getOthertreatment() {
		return othertreatment;
	}
	public void setOthertreatment(String othertreatment) {
		this.othertreatment = othertreatment;
	}
	public String getBpd_plan() {
		return bpd_plan;
	}
	public void setBpd_plan(String bpd_plan) {
		this.bpd_plan = bpd_plan;
	}
	public String getReassestime() {
		return reassestime;
	}
	public void setReassestime(String reassestime) {
		this.reassestime = reassestime;
	}
	public String getReassesstimetype() {
		return reassesstimetype;
	}
	public void setReassesstimetype(String reassesstimetype) {
		this.reassesstimetype = reassesstimetype;
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

			
}