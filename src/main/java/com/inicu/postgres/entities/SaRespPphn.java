package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
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
 * The persistent class for the sa_resp_pphn database table.
 * 
 * @author Sourabh Verma
 */

@Entity
@Table(name = "sa_resp_pphn")
@NamedQuery(name = "SaRespPphn.findAll", query = "SELECT s FROM SaRespPphn s")
public class SaRespPphn implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long resppphnid;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String uhid;

	private String loggeduser;


	@Column(columnDefinition = "bool")
	private Boolean iseditednotes;


	//not in use anymore
	@Column(name = "resp_system_status")
	private String respSystemStatus;

	private String eventstatus;

	private String ageatonset;

	@Column(columnDefinition = "bool")
	private Boolean ageinhoursdays;

	private String downesscoreid;
	
	private String riskfactor;

	@Transient
	private List<String> riskfactorList;
	
	@Column(name = "riskfactor_other")
	private String riskfactorOther;

	@Column(name = "labile_oxygenation", columnDefinition = "bool")
	private Boolean labileOxygenation;

	@Column(name = "systolic_bp")
	private String systolicBp;

	@Column(name = "oxgenation_index")
	private String oxgenationIndex;

	@Column(name = "lab_preductal")
	private String labPreductal;

	@Column(name = "lab_postductal")
	private String labPostductal;

	@Column(name = "labile_difference")
	private String labileDifference;

	@Column(name = "pulmonary_pressure")
	private String pulmonaryPressure;

	private String treatmentaction;
	
	@Column(name = "clinical_note")
	private String clinicalNote;

	private String treatmentSelectedText;

	private String sufactantname;

	@Column(name = "surfactant_dose")
	private String surfactantDose;

	@Column(columnDefinition = "bool")
	private Boolean isinsuredone;

	@Column(name = "action_after_surfactant")
	private String actionAfterSurfactant;

	@Column(name = "pphn_ino")
	private String pphnIno;

	@Column(name = "methaemoglobin_level")
	private String methaemoglobinLevel;

	@Column(name = "other_pulmonaryvasodialotrs")
	private String otherPulmonaryvasodialotrs;

	private String pphnplan;

	private String reassestime;

	@Column(name = "reassestime_type")
	private String reassestimeType;

	private String othercomments;

	private String silvermanscoreid;

	private String causeofpphn;

	private String progressnotes;

	private String associatedevent;

	@Transient
	private List<String> treatmentActionList;

	@Transient
	private List<String> pphnCauseList;

	@Transient
	private List<String> orderInvestigationList;

	@Transient
	private List<String> orderInvestigationListStr;

	@Transient
	private List<String> pphnPlanList;

	@Column(name = "causeofpphn_other")
	private String causeofpphnOther;

	@Column(name = "treatment_other")
	private String treatmentOther;

	private Timestamp timeofassessment;

	@Column(columnDefinition = "float4")
	private Integer ageatassesment;

	@Column(columnDefinition = "bool")
	private Boolean isageofassesmentinhours;

	// addition of columns to enable manual date/time of assessment
	@Column(name = "assessment_date")
	private Date assessmentDate;

	@Column(name = "assessment_hour")
	private String assessmentHour;

	@Column(name = "assessment_min")
	private String assessmentMin;

	@Column(name = "assessment_meridiem", columnDefinition = "bool")
	private Boolean assessmentMeridiem;

	@Column(name = "assessment_time")
	private Timestamp assessmentTime;

	@Column(name = "episode_number")
	private Integer episodeNumber;

	@Transient
	private RespSupport respSupport;

	@Transient
	private ScoreDownes downesScore;

	@Transient
	private List<BabyPrescription> babyPrescription;

	@Transient
	private Boolean isNewEntry;

	@Column(name = "medication_str")
	private String medicationStr;
	
	//Fields added for INO
	@Column(columnDefinition = "bool")
	private Boolean gestation;
	
	@Column(columnDefinition = "bool")
	private Boolean dol;
	
	private Integer spo2;
	
	private Timestamp spo2Time;
	
	private Integer fio2;
	
	private Timestamp fio2Time;
	
	@Column(columnDefinition = "Float4")
	private Float map;
		
	@Column(columnDefinition = "Float4")
	private Float pao2; 
	
	private Timestamp pao2Time;
	
	@Column(columnDefinition = "Float4")
	private Float flowrate; 
	
	@Column(columnDefinition = "Float4", name = "oxygenation_index")
	private Float oxygenationIndex; 
	
	private String inoStatus;
	
	@Column(columnDefinition = "Float4")
	private Float inodose; 
	
	@Column(columnDefinition = "Float4")
	private Float inorate;
	
	@Column(columnDefinition = "Float4")
	private Float methb;
	
	private String oicalculatortype;


	private String icdCauseofPPHN;

	@Transient
	private List icdCauseofpphnList;

	private String episodeid;

	public SaRespPphn() {
		super();
		this.episodeNumber = 1;
		this.isageofassesmentinhours = true;
		this.isNewEntry = true;

	}

	public String getIcdCauseofPPHN() {
		return icdCauseofPPHN;
	}

	public void setIcdCauseofPPHN(String icdCauseofPPHN) {
		this.icdCauseofPPHN = icdCauseofPPHN;
	}

	public List getIcdCauseofpphnList() {
		return icdCauseofpphnList;
	}

	public void setIcdCauseofpphnList(List icdCauseofpphnList) {
		this.icdCauseofpphnList = icdCauseofpphnList;
	}

	public Long getResppphnid() {
		return resppphnid;
	}

	public void setResppphnid(Long resppphnid) {
		this.resppphnid = resppphnid;
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

	public String getRespSystemStatus() {
		return respSystemStatus;
	}

	public void setRespSystemStatus(String respSystemStatus) {
		this.respSystemStatus = respSystemStatus;
	}

	public String getEventstatus() {
		return eventstatus;
	}

	public void setEventstatus(String eventstatus) {
		this.eventstatus = eventstatus;
	}

	public String getAgeatonset() {
		return ageatonset;
	}

	public void setAgeatonset(String ageatonset) {
		this.ageatonset = ageatonset;
	}

	public Boolean getAgeinhoursdays() {
		return ageinhoursdays;
	}

	public void setAgeinhoursdays(Boolean ageinhoursdays) {
		this.ageinhoursdays = ageinhoursdays;
	}
	
	public String getRiskfactor() {
		return riskfactor;
	}

	public void setRiskfactor(String riskfactor) {
		this.riskfactor = riskfactor;
	}

	public List<String> getRiskfactorList() {
		return riskfactorList;
	}

	public void setRiskfactorList(List<String> riskfactorList) {
		this.riskfactorList = riskfactorList;
	}
	
	public String getRiskfactorOther() {
		return riskfactorOther;
	}

	public void setRiskfactorOther(String riskfactorOther) {
		this.riskfactorOther = riskfactorOther;
	}

	public Float getMethb() {
		return methb;
	}

	public void setMethb(Float methb) {
		this.methb = methb;
	}

	public String getDownesscoreid() {
		return downesscoreid;
	}

	public void setDownesscoreid(String downesscoreid) {
		this.downesscoreid = downesscoreid;
	}

	public Boolean getLabileOxygenation() {
		return labileOxygenation;
	}

	public void setLabileOxygenation(Boolean labileOxygenation) {
		this.labileOxygenation = labileOxygenation;
	}

	public String getSystolicBp() {
		return systolicBp;
	}

	public void setSystolicBp(String systolicBp) {
		this.systolicBp = systolicBp;
	}

	public String getOxgenationIndex() {
		return oxgenationIndex;
	}

	public void setOxgenationIndex(String oxgenationIndex) {
		this.oxgenationIndex = oxgenationIndex;
	}

	public String getLabPreductal() {
		return labPreductal;
	}

	public void setLabPreductal(String labPreductal) {
		this.labPreductal = labPreductal;
	}

	public String getLabPostductal() {
		return labPostductal;
	}

	public void setLabPostductal(String labPostductal) {
		this.labPostductal = labPostductal;
	}

	public String getLabileDifference() {
		return labileDifference;
	}

	public void setLabileDifference(String labileDifference) {
		this.labileDifference = labileDifference;
	}

	public String getPulmonaryPressure() {
		return pulmonaryPressure;
	}

	public void setPulmonaryPressure(String pulmonaryPressure) {
		this.pulmonaryPressure = pulmonaryPressure;
	}

	public String getTreatmentaction() {
		return treatmentaction;
	}

	public void setTreatmentaction(String treatmentaction) {
		this.treatmentaction = treatmentaction;
	}

	public String getSufactantname() {
		return sufactantname;
	}

	public void setSufactantname(String sufactantname) {
		this.sufactantname = sufactantname;
	}

	public String getSurfactantDose() {
		return surfactantDose;
	}

	public void setSurfactantDose(String surfactantDose) {
		this.surfactantDose = surfactantDose;
	}

	public Boolean getIsinsuredone() {
		return isinsuredone;
	}

	public void setIsinsuredone(Boolean isinsuredone) {
		this.isinsuredone = isinsuredone;
	}

	public String getActionAfterSurfactant() {
		return actionAfterSurfactant;
	}

	public void setActionAfterSurfactant(String actionAfterSurfactant) {
		this.actionAfterSurfactant = actionAfterSurfactant;
	}

	public String getPphnIno() {
		return pphnIno;
	}

	public void setPphnIno(String pphnIno) {
		this.pphnIno = pphnIno;
	}

	public String getMethaemoglobinLevel() {
		return methaemoglobinLevel;
	}

	public void setMethaemoglobinLevel(String methaemoglobinLevel) {
		this.methaemoglobinLevel = methaemoglobinLevel;
	}

	public String getOtherPulmonaryvasodialotrs() {
		return otherPulmonaryvasodialotrs;
	}

	public void setOtherPulmonaryvasodialotrs(String otherPulmonaryvasodialotrs) {
		this.otherPulmonaryvasodialotrs = otherPulmonaryvasodialotrs;
	}

	public String getPphnplan() {
		return pphnplan;
	}

	public void setPphnplan(String pphnplan) {
		this.pphnplan = pphnplan;
	}

	public String getReassestime() {
		return reassestime;
	}

	public void setReassestime(String reassestime) {
		this.reassestime = reassestime;
	}

	public String getReassestimeType() {
		return reassestimeType;
	}

	public void setReassestimeType(String reassestimeType) {
		this.reassestimeType = reassestimeType;
	}

	public String getOthercomments() {
		return othercomments;
	}

	public void setOthercomments(String othercomments) {
		this.othercomments = othercomments;
	}

	public String getSilvermanscoreid() {
		return silvermanscoreid;
	}

	public void setSilvermanscoreid(String silvermanscoreid) {
		this.silvermanscoreid = silvermanscoreid;
	}

	public String getCauseofpphn() {
		return causeofpphn;
	}

	public void setCauseofpphn(String causeofpphn) {
		this.causeofpphn = causeofpphn;
	}

	public String getProgressnotes() {
		return progressnotes;
	}

	public void setProgressnotes(String progressnotes) {
		this.progressnotes = progressnotes;
	}

	public String getAssociatedevent() {
		return associatedevent;
	}

	public void setAssociatedevent(String associatedevent) {
		this.associatedevent = associatedevent;
	}

	public List<String> getTreatmentActionList() {
		return treatmentActionList;
	}

	public void setTreatmentActionList(List<String> treatmentActionList) {
		this.treatmentActionList = treatmentActionList;
	}

	public List<String> getPphnCauseList() {
		return pphnCauseList;
	}

	public void setPphnCauseList(List<String> pphnCauseList) {
		this.pphnCauseList = pphnCauseList;
	}

	public List<String> getOrderInvestigationList() {
		return orderInvestigationList;
	}

	public void setOrderInvestigationList(List<String> orderInvestigationList) {
		this.orderInvestigationList = orderInvestigationList;
	}

	public List<String> getPphnPlanList() {
		return pphnPlanList;
	}

	public void setPphnPlanList(List<String> pphnPlanList) {
		this.pphnPlanList = pphnPlanList;
	}

	public String getCauseofpphnOther() {
		return causeofpphnOther;
	}

	public void setCauseofpphnOther(String causeofpphnOther) {
		this.causeofpphnOther = causeofpphnOther;
	}

	public String getTreatmentOther() {
		return treatmentOther;
	}

	public void setTreatmentOther(String treatmentOther) {
		this.treatmentOther = treatmentOther;
	}

	public Timestamp getTimeofassessment() {
		return timeofassessment;
	}

	public void setTimeofassessment(Timestamp timeofassessment) {
		this.timeofassessment = timeofassessment;
	}

	public Integer getAgeatassesment() {
		return ageatassesment;
	}

	public void setAgeatassesment(Integer ageatassesment) {
		this.ageatassesment = ageatassesment;
	}

	public Boolean getIsageofassesmentinhours() {
		return isageofassesmentinhours;
	}

	public void setIsageofassesmentinhours(Boolean isageofassesmentinhours) {
		this.isageofassesmentinhours = isageofassesmentinhours;
	}

	public String getTreatmentSelectedText() {
		return treatmentSelectedText;
	}

	public void setTreatmentSelectedText(String treatmentSelectedText) {
		this.treatmentSelectedText = treatmentSelectedText;
	}

	public Date getAssessmentDate() {
		return assessmentDate;
	}

	public void setAssessmentDate(Date assessmentDate) {
		this.assessmentDate = assessmentDate;
	}

	public String getAssessmentHour() {
		return assessmentHour;
	}

	public void setAssessmentHour(String assessmentHour) {
		this.assessmentHour = assessmentHour;
	}

	public String getAssessmentMin() {
		return assessmentMin;
	}

	public void setAssessmentMin(String assessmentMin) {
		this.assessmentMin = assessmentMin;
	}

	public Boolean getAssessmentMeridiem() {
		return assessmentMeridiem;
	}

	public void setAssessmentMeridiem(Boolean assessmentMeridiem) {
		this.assessmentMeridiem = assessmentMeridiem;
	}

	public Timestamp getAssessmentTime() {
		return assessmentTime;
	}

	public void setAssessmentTime(Timestamp assessmentTime) {
		this.assessmentTime = assessmentTime;
	}

	public Integer getEpisodeNumber() {
		return episodeNumber;
	}

	public void setEpisodeNumber(Integer episodeNumber) {
		this.episodeNumber = episodeNumber;
	}

	public ScoreDownes getDownesScore() {
		return downesScore;
	}

	public String getClinicalNote() {
		return clinicalNote;
	}

	public void setClinicalNote(String clinicalNote) {
		this.clinicalNote = clinicalNote;
	}

	public void setDownesScore(ScoreDownes downesScore) {
		this.downesScore = downesScore;
	}

	public RespSupport getRespSupport() {
		return respSupport;
	}

	public List<BabyPrescription> getBabyPrescription() {
		return babyPrescription;
	}

	public void setRespSupport(RespSupport respSupport) {
		this.respSupport = respSupport;
	}

	public void setBabyPrescription(List<BabyPrescription> babyPrescription) {
		this.babyPrescription = babyPrescription;
	}

	public Boolean getIsNewEntry() {
		return isNewEntry;
	}

	public void setIsNewEntry(Boolean isNewEntry) {
		this.isNewEntry = isNewEntry;
	}

	public List<String> getOrderInvestigationListStr() {
		return orderInvestigationListStr;
	}

	public void setOrderInvestigationListStr(List<String> orderInvestigationListStr) {
		this.orderInvestigationListStr = orderInvestigationListStr;
	}

	public String getMedicationStr() {
		return medicationStr;
	}

	public void setMedicationStr(String medicationStr) {
		this.medicationStr = medicationStr;
	}

	public Boolean getGestation() {
		return gestation;
	}

	public void setGestation(Boolean gestation) {
		this.gestation = gestation;
	}

	public Boolean getDol() {
		return dol;
	}

	public void setDol(Boolean dol) {
		this.dol = dol;
	}

	public Integer getSpo2() {
		return spo2;
	}

	public void setSpo2(Integer spo2) {
		this.spo2 = spo2;
	}

	public Integer getFio2() {
		return fio2;
	}

	public void setFio2(Integer fio2) {
		this.fio2 = fio2;
	}

	public Float getMap() {
		return map;
	}

	public void setMap(Float map) {
		this.map = map;
	}

	public Float getPao2() {
		return pao2;
	}

	public void setPao2(Float pao2) {
		this.pao2 = pao2;
	}

	public Float getFlowrate() {
		return flowrate;
	}

	public void setFlowrate(Float flowrate) {
		this.flowrate = flowrate;
	}

	public Float getOxygenationIndex() {
		return oxygenationIndex;
	}

	public void setOxygenationIndex(Float oxygenationIndex) {
		this.oxygenationIndex = oxygenationIndex;
	}

	public String getInoStatus() {
		return inoStatus;
	}

	public void setInoStatus(String inoStatus) {
		this.inoStatus = inoStatus;
	}

	public Float getInodose() {
		return inodose;
	}

	public void setInodose(Float inodose) {
		this.inodose = inodose;
	}

	public Float getInorate() {
		return inorate;
	}

	public void setInorate(Float inorate) {
		this.inorate = inorate;
	}

	public String getOicalculatortype() {
		return oicalculatortype;
	}

	public void setOicalculatortype(String oicalculatortype) {
		this.oicalculatortype = oicalculatortype;
	}

	public Timestamp getSpo2Time() {
		return spo2Time;
	}

	public void setSpo2Time(Timestamp spo2Time) {
		this.spo2Time = spo2Time;
	}

	public Timestamp getFio2Time() {
		return fio2Time;
	}

	public void setFio2Time(Timestamp fio2Time) {
		this.fio2Time = fio2Time;
	}

	public Timestamp getPao2Time() {
		return pao2Time;
	}

	public void setPao2Time(Timestamp pao2Time) {
		this.pao2Time = pao2Time;
	}

	public String getEpisodeid() {
		return episodeid;
	}

	public void setEpisodeid(String episodeid) {
		this.episodeid = episodeid;
	}


	public Boolean getIseditednotes() {
		return iseditednotes;
	}

	public void setIseditednotes(Boolean iseditednotes) {
		this.iseditednotes = iseditednotes;
	}
}
