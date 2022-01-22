package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the discharge_summary database table.
 * 
 */
@Entity
@Table(name="discharge_summary")
@NamedQuery(name="DischargeSummary.findAll", query="SELECT d FROM DischargeSummary d")
public class DischargeSummary implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long babydischargeid;

	private String actualgestation;

	private String ageatdischarge;

	private String babyname;

	private Timestamp creationtime;

	private Timestamp dateofadmission;

	private Timestamp dateofbirth;

	@Column(name="discharge_cns")
	private String dischargeCns;

	@Column(name="discharge_cvs")
	private String dischargeCvs;

	@Column(name="discharge_hr")
	private String dischargeHr;

	@Column(name="discharge_pa")
	private String dischargePa;

	@Column(name="discharge_perfusion")
	private String dischargePerfusion;

	@Column(name="discharge_rr")
	private String dischargeRr;

	@Column(name="discharge_rs")
	private String dischargeRs;

	@Column(name="discharge_saturation")
	private String dischargeSaturation;

	private String dischargestatus;

	private Timestamp edd;

	private String finaldiagnosis;

	@Column(name="followup_bera")
	private String followupBera;

	@Temporal(TemporalType.DATE)
	@Column(name="followup_date")
	private Date followupDate;
	

	private Timestamp dateofdischarge;
	

	@Transient
	private String followupDateStr ;
	
	
	public String getFollowupDateStr() {
		return followupDateStr;
	}

	public void setFollowupDateStr(String followupDateStr) {
		this.followupDateStr = followupDateStr;
	}

	@Column(name="followup_doctor")
	private String followupDoctor;

	private String gender;

	private String gestationageatbirth;

	private String hcircumatbirth;

	private String hcircumonadmission;

	private String hcircumondischarge;

	@Column(name="inout_patient_status")
	private String inoutPatientStatus;

	private String lengthatbirth;

	private String lengthonadmission;

	private String lengthondischarge;

	private Timestamp lmp;

	private Timestamp modificationtime;

	@Column(name="treatment_on_discharge")
	private String treatmentOnDischarge;

	private String uhid;

	private String weightatbirth;

	private String weightonadmission;

	private String weightondischarge;

	

	public Long getBabydischargeid() {
		return this.babydischargeid;
	}

	public void setBabydischargeid(Long babydischargeid) {
		this.babydischargeid = babydischargeid;
	}

	public String getActualgestation() {
		return this.actualgestation;
	}

	public void setActualgestation(String actualgestation) {
		this.actualgestation = actualgestation;
	}

	public String getAgeatdischarge() {
		return this.ageatdischarge;
	}

	public void setAgeatdischarge(String ageatdischarge) {
		this.ageatdischarge = ageatdischarge;
	}

	public String getBabyname() {
		return this.babyname;
	}

	public void setBabyname(String babyname) {
		this.babyname = babyname;
	}

	
	
	public Timestamp getDateofadmission() {
		return this.dateofadmission;
	}

	public void setDateofadmission(Timestamp dateofadmission) {
		this.dateofadmission = dateofadmission;
	}

	public Timestamp getDateofbirth() {
		return this.dateofbirth;
	}

	public void setDateofbirth(Timestamp dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public String getDischargeCns() {
		return this.dischargeCns;
	}

	public void setDischargeCns(String dischargeCns) {
		this.dischargeCns = dischargeCns;
	}

	public String getDischargeCvs() {
		return this.dischargeCvs;
	}

	public void setDischargeCvs(String dischargeCvs) {
		this.dischargeCvs = dischargeCvs;
	}

	public String getDischargeHr() {
		return this.dischargeHr;
	}

	public void setDischargeHr(String dischargeHr) {
		this.dischargeHr = dischargeHr;
	}

	public String getDischargePa() {
		return this.dischargePa;
	}

	public void setDischargePa(String dischargePa) {
		this.dischargePa = dischargePa;
	}

	public String getDischargePerfusion() {
		return this.dischargePerfusion;
	}

	public void setDischargePerfusion(String dischargePerfusion) {
		this.dischargePerfusion = dischargePerfusion;
	}

	public String getDischargeRr() {
		return this.dischargeRr;
	}

	public void setDischargeRr(String dischargeRr) {
		this.dischargeRr = dischargeRr;
	}

	public String getDischargeRs() {
		return this.dischargeRs;
	}

	public void setDischargeRs(String dischargeRs) {
		this.dischargeRs = dischargeRs;
	}

	public String getDischargeSaturation() {
		return this.dischargeSaturation;
	}

	public void setDischargeSaturation(String dischargeSaturation) {
		this.dischargeSaturation = dischargeSaturation;
	}

	public String getDischargestatus() {
		return this.dischargestatus;
	}

	public void setDischargestatus(String dischargestatus) {
		this.dischargestatus = dischargestatus;
	}

	public Timestamp getEdd() {
		return this.edd;
	}

	public void setEdd(Timestamp edd) {
		this.edd = edd;
	}

	public String getFinaldiagnosis() {
		return this.finaldiagnosis;
	}

	public void setFinaldiagnosis(String finaldiagnosis) {
		this.finaldiagnosis = finaldiagnosis;
	}

	public String getFollowupBera() {
		return this.followupBera;
	}

	public void setFollowupBera(String followupBera) {
		this.followupBera = followupBera;
	}

	
	public Date getFollowupDate() {
		return followupDate;
	}

	public void setFollowupDate(Date followupDate) {
		this.followupDate = followupDate;
	}

	public String getFollowupDoctor() {
		return this.followupDoctor;
	}

	public void setFollowupDoctor(String followupDoctor) {
		this.followupDoctor = followupDoctor;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getGestationageatbirth() {
		return this.gestationageatbirth;
	}

	public void setGestationageatbirth(String gestationageatbirth) {
		this.gestationageatbirth = gestationageatbirth;
	}

	public String getHcircumatbirth() {
		return this.hcircumatbirth;
	}

	public void setHcircumatbirth(String hcircumatbirth) {
		this.hcircumatbirth = hcircumatbirth;
	}

	public String getHcircumonadmission() {
		return this.hcircumonadmission;
	}

	public void setHcircumonadmission(String hcircumonadmission) {
		this.hcircumonadmission = hcircumonadmission;
	}

	public String getHcircumondischarge() {
		return this.hcircumondischarge;
	}

	public void setHcircumondischarge(String hcircumondischarge) {
		this.hcircumondischarge = hcircumondischarge;
	}

	public String getInoutPatientStatus() {
		return this.inoutPatientStatus;
	}

	public void setInoutPatientStatus(String inoutPatientStatus) {
		this.inoutPatientStatus = inoutPatientStatus;
	}

	public String getLengthatbirth() {
		return this.lengthatbirth;
	}

	public void setLengthatbirth(String lengthatbirth) {
		this.lengthatbirth = lengthatbirth;
	}

	public String getLengthonadmission() {
		return this.lengthonadmission;
	}

	public void setLengthonadmission(String lengthonadmission) {
		this.lengthonadmission = lengthonadmission;
	}

	public String getLengthondischarge() {
		return this.lengthondischarge;
	}

	public void setLengthondischarge(String lengthondischarge) {
		this.lengthondischarge = lengthondischarge;
	}

	public Timestamp getLmp() {
		return this.lmp;
	}

	public void setLmp(Timestamp lmp) {
		this.lmp = lmp;
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

	public String getTreatmentOnDischarge() {
		return this.treatmentOnDischarge;
	}

	public void setTreatmentOnDischarge(String treatmentOnDischarge) {
		this.treatmentOnDischarge = treatmentOnDischarge;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getWeightatbirth() {
		return this.weightatbirth;
	}

	public void setWeightatbirth(String weightatbirth) {
		this.weightatbirth = weightatbirth;
	}

	public String getWeightonadmission() {
		return this.weightonadmission;
	}

	public void setWeightonadmission(String weightonadmission) {
		this.weightonadmission = weightonadmission;
	}

	public String getWeightondischarge() {
		return this.weightondischarge;
	}

	public void setWeightondischarge(String weightondischarge) {
		this.weightondischarge = weightondischarge;
	}

	

	public Timestamp getDateofdischarge() {
		return dateofdischarge;
	}

	public void setDateofdischarge(Timestamp dateofdischarge) {
		this.dateofdischarge = dateofdischarge;
	}

	public DischargeSummary() {
		
		this.hcircumondischarge = "";
		/*
		super();
		this.babydischargeid = babydischargeid;
		this.actualgestation = actualgestation;
		this.ageatdischarge = ageatdischarge;
		this.babyname = babyname;
		this.creationtime = creationtime;
		this.dateofadmission = dateofadmission;
		this.dateofbirth = dateofbirth;
		this.dischargeCns = dischargeCns;
		this.dischargeCvs = dischargeCvs;
		this.dischargeHr = dischargeHr;
		this.dischargePa = dischargePa;
		this.dischargePerfusion = dischargePerfusion;
		this.dischargeRr = dischargeRr;
		this.dischargeRs = dischargeRs;
		this.dischargeSaturation = dischargeSaturation;
		this.dischargestatus = dischargestatus;
		this.edd = edd;
		this.finaldiagnosis = finaldiagnosis;
		this.followupBera = followupBera;
		this.followupDate = followupDate;
		this.dateofdischarge = dateofdischarge;
		this.followupDateStr = followupDateStr;
		this.followupDoctor = followupDoctor;
		this.gender = gender;
		this.gestationageatbirth = gestationageatbirth;
		this.hcircumatbirth = hcircumatbirth;
		this.hcircumonadmission = hcircumonadmission;
		this.hcircumondischarge = hcircumondischarge;
		this.inoutPatientStatus = inoutPatientStatus;
		this.lengthatbirth = lengthatbirth;
		this.lengthonadmission = lengthonadmission;
		this.lengthondischarge = lengthondischarge;
		this.lmp = lmp;
		this.modificationtime = modificationtime;
		this.treatmentOnDischarge = treatmentOnDischarge;
		this.uhid = uhid;
		this.weightatbirth = weightatbirth;
		this.weightonadmission = weightonadmission;
		this.weightondischarge = weightondischarge;
	*/}

	
	
}