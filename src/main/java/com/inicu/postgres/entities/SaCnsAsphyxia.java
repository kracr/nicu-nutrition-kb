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

import com.inicu.models.ScoreLevene;

/**
 * The persistent class for the sa_cns_asphyxia database table.
 * 
 */
@Entity
@Table(name = "sa_cns_asphyxia")
@NamedQuery(name = "SaCnsAsphyxia.findAll", query = "SELECT s FROM SaCnsAsphyxia s")
public class SaCnsAsphyxia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long sacnsasphyxiaid;

	private String ageatonset;

	@Column(columnDefinition = "bool")
	private Boolean ageinhoursdays;
	
	// Not in use anymore

//	private String associatedevent;
//
//	private String associatedeventid;

	@Column(name = "causeof_asphyxia")
	private String causeofAsphyxia;

	@Column(name = "causeofasphyxia_other")
	private String causeofasphyxiaOther;

	//Not in use anymore
	@Column(name = "cns_system_status")
	private String cnsSystemStatus;

	private Timestamp creationtime;

	private String eventstatus;

	private String history;

	@Column(columnDefinition = "bool")
	private Boolean isacidosis;

	@Column(columnDefinition = "bool")
	private Boolean isrespiration;

	@Column(columnDefinition = "bool")
	private Boolean isseizures;

	@Column(columnDefinition = "bool")
	private Boolean issensorium;

	@Column(columnDefinition = "bool")
	private Boolean isshock;

	private String loggeduser;

	private Timestamp modificationtime;

	@Column(name = "otherplan_comments")
	private String otherplanComments;

	@Column(name = "othertreatment_comments")
	private String othertreatmentComments;

	private String progressnotes;

	private String reassestime;

	@Column(name = "reassestime_type")
	private String reassestimeType;

	@Column(name = "sensorium_type")
	private String sensoriumType;

	private String treatmentaction;

	private String treatmentplan;

	private String uhid;

	private String downesscoreid;

	private String thompsonscoreid;

	private String sarnatscoreid;

	private String levenescoreid;

	@Transient
	private List<String> treatmentactionlist;

	@Transient
	private List<String> treatmentactioncause;

	@Transient
	private List<String> treatmentactionplanlist;

	@Column(columnDefinition = "bool")
	private Boolean ishypoglycemia;

	@Column(columnDefinition = "bool")
	private Boolean isrenal;

	@Column(columnDefinition = "bool")
	private Boolean isrds;

	@Column(columnDefinition = "bool")
	private Boolean isPPHN;

	@Column(columnDefinition = "bool")
	private Boolean isCHF;

	@Column(columnDefinition = "bool")
	private Boolean isIVH;

	@Transient
	private String sensoriumStage;

	private Integer ageatassesment;

	@Column(columnDefinition = "bool")
	private Boolean isageofassesmentinhours;

	private String riskfactor;

	@Transient
	private List<String> riskfactorList;

	@Column(name = "riskfactor_other")
	private String riskfactorOther;

	private String treatmentSelectedText;

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
	private ScoreDownes downesScore;

	@Transient
	private ScoreThompson thompsonScore;

	@Transient
	private ScoreSarnat sarnatScore;

	@Transient
	private ScoreLevene leveneScore;

	@Transient
	private RespSupport respSupport;

	@Transient
	private List<BabyPrescription> babyPrescription;

	@Column(name = "medication_str")
	private String medicationStr;
	
	@Column(name = "clinical_note")
	private String clinicalNote;

	@Transient
	private Boolean isNewEntry;

	private String episodeid;

	@Column(columnDefinition = "bool")
	private Boolean iseditednotes;

	public SaCnsAsphyxia() {
		super();
		this.isNewEntry=true;
		this.ageinhoursdays = true;
		this.episodeNumber = 1;
	}

	public Boolean getIsNewEntry() {
		return isNewEntry;
	}

	public void setIsNewEntry(Boolean newEntry) {
		isNewEntry = newEntry;
	}

	public Boolean getPPHN() {
		return isPPHN;
	}

	public void setPPHN(Boolean PPHN) {
		isPPHN = PPHN;
	}

	public Boolean getCHF() {
		return isCHF;
	}

	public void setCHF(Boolean CHF) {
		isCHF = CHF;
	}

	public Boolean getIVH() {
		return isIVH;
	}

	public void setIVH(Boolean IVH) {
		isIVH = IVH;
	}

	public String getTreatmentSelectedText() {
		return treatmentSelectedText;
	}

	public void setTreatmentSelectedText(String treatmentSelectedText) {
		this.treatmentSelectedText = treatmentSelectedText;
	}

	public List<String> getTreatmentactionlist() {
		return treatmentactionlist;
	}

	public void setTreatmentactionlist(List<String> treatmentactionlist) {
		this.treatmentactionlist = treatmentactionlist;
	}

	public String getDownesscoreid() {
		return downesscoreid;
	}

	public void setDownesscoreid(String downesscoreid) {
		this.downesscoreid = downesscoreid;
	}

	public String getThompsonscoreid() {
		return thompsonscoreid;
	}

	public void setThompsonscoreid(String thompsonscoreid) {
		this.thompsonscoreid = thompsonscoreid;
	}

	public String getSarnatscoreid() {
		return sarnatscoreid;
	}

	public void setSarnatscoreid(String sarnatscoreid) {
		this.sarnatscoreid = sarnatscoreid;
	}

	public String getLevenescoreid() {
		return levenescoreid;
	}

	public void setLevenescoreid(String levenescoreid) {
		this.levenescoreid = levenescoreid;
	}

	public List<String> getTreatmentactioncause() {
		return treatmentactioncause;
	}

	public void setTreatmentactioncause(List<String> treatmentactioncause) {
		this.treatmentactioncause = treatmentactioncause;
	}

	public List<String> getTreatmentactionplanlist() {
		return treatmentactionplanlist;
	}

	public void setTreatmentactionplanlist(List<String> treatmentactionplanlist) {
		this.treatmentactionplanlist = treatmentactionplanlist;
	}

	public Long getSacnsasphyxiaid() {
		return this.sacnsasphyxiaid;
	}

	public void setSacnsasphyxiaid(Long sacnsasphyxiaid) {
		this.sacnsasphyxiaid = sacnsasphyxiaid;
	}

	public String getAgeatonset() {
		return this.ageatonset;
	}

	public void setAgeatonset(String ageatonset) {
		this.ageatonset = ageatonset;
	}

	public Boolean getAgeinhoursdays() {
		return this.ageinhoursdays;
	}

	public void setAgeinhoursdays(Boolean ageinhoursdays) {
		this.ageinhoursdays = ageinhoursdays;
	}

//	public String getAssociatedevent() {
//		return this.associatedevent;
//	}
//
//	public void setAssociatedevent(String associatedevent) {
//		this.associatedevent = associatedevent;
//	}
//
//	public String getAssociatedeventid() {
//		return this.associatedeventid;
//	}
//
//	public void setAssociatedeventid(String associatedeventid) {
//		this.associatedeventid = associatedeventid;
//	}

	public String getCauseofAsphyxia() {
		return this.causeofAsphyxia;
	}

	public void setCauseofAsphyxia(String causeofAsphyxia) {
		this.causeofAsphyxia = causeofAsphyxia;
	}

	public String getCauseofasphyxiaOther() {
		return this.causeofasphyxiaOther;
	}

	public void setCauseofasphyxiaOther(String causeofasphyxiaOther) {
		this.causeofasphyxiaOther = causeofasphyxiaOther;
	}

	public String getCnsSystemStatus() {
		return this.cnsSystemStatus;
	}

	public void setCnsSystemStatus(String cnsSystemStatus) {
		this.cnsSystemStatus = cnsSystemStatus;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getEventstatus() {
		return this.eventstatus;
	}

	public void setEventstatus(String eventstatus) {
		this.eventstatus = eventstatus;
	}

	public String getHistory() {
		return this.history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public Boolean getIsacidosis() {
		return this.isacidosis;
	}

	public void setIsacidosis(Boolean isacidosis) {
		this.isacidosis = isacidosis;
	}

	public Boolean getIsrespiration() {
		return this.isrespiration;
	}

	public void setIsrespiration(Boolean isrespiration) {
		this.isrespiration = isrespiration;
	}

	public Boolean getIsseizures() {
		return this.isseizures;
	}

	public void setIsseizures(Boolean isseizures) {
		this.isseizures = isseizures;
	}

	public Boolean getIssensorium() {
		return this.issensorium;
	}

	public void setIssensorium(Boolean issensorium) {
		this.issensorium = issensorium;
	}

	public Boolean getIsshock() {
		return this.isshock;
	}

	public void setIsshock(Boolean isshock) {
		this.isshock = isshock;
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

	public String getClinicalNote() {
		return clinicalNote;
	}

	public void setClinicalNote(String clinicalNote) {
		this.clinicalNote = clinicalNote;
	}

	public String getOtherplanComments() {
		return this.otherplanComments;
	}

	public void setOtherplanComments(String otherplanComments) {
		this.otherplanComments = otherplanComments;
	}

	public String getOthertreatmentComments() {
		return this.othertreatmentComments;
	}

	public void setOthertreatmentComments(String othertreatmentComments) {
		this.othertreatmentComments = othertreatmentComments;
	}

	public String getProgressnotes() {
		return this.progressnotes;
	}

	public void setProgressnotes(String progressnotes) {
		this.progressnotes = progressnotes;
	}

	public String getReassestime() {
		return this.reassestime;
	}

	public void setReassestime(String reassestime) {
		this.reassestime = reassestime;
	}

	public String getReassestimeType() {
		return this.reassestimeType;
	}

	public void setReassestimeType(String reassestimeType) {
		this.reassestimeType = reassestimeType;
	}

	public String getSensoriumType() {
		return this.sensoriumType;
	}

	public void setSensoriumType(String sensoriumType) {
		this.sensoriumType = sensoriumType;
	}

	public String getTreatmentaction() {
		return this.treatmentaction;
	}

	public void setTreatmentaction(String treatmentaction) {
		this.treatmentaction = treatmentaction;
	}

	public String getTreatmentplan() {
		return this.treatmentplan;
	}

	public void setTreatmentplan(String treatmentplan) {
		this.treatmentplan = treatmentplan;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public Boolean getIshypoglycemia() {
		return ishypoglycemia;
	}

	public Boolean getIsrenal() {
		return isrenal;
	}

	public Boolean getIsrds() {
		return isrds;
	}

	public Boolean getIsPPHN() {
		return isPPHN;
	}

	public Boolean getIsCHF() {
		return isCHF;
	}

	public Boolean getIsIVH() {
		return isIVH;
	}

	public String getSensoriumStage() {
		return sensoriumStage;
	}

	public void setIshypoglycemia(Boolean ishypoglycemia) {
		this.ishypoglycemia = ishypoglycemia;
	}

	public void setIsrenal(Boolean isrenal) {
		this.isrenal = isrenal;
	}

	public void setIsrds(Boolean isrds) {
		this.isrds = isrds;
	}

	public void setIsPPHN(Boolean isPPHN) {
		this.isPPHN = isPPHN;
	}

	public void setIsCHF(Boolean isCHF) {
		this.isCHF = isCHF;
	}

	public void setIsIVH(Boolean isIVH) {
		this.isIVH = isIVH;
	}

	public void setSensoriumStage(String sensoriumStage) {
		this.sensoriumStage = sensoriumStage;
	}

	public Integer getAgeatassesment() {
		return ageatassesment;
	}

	public Boolean getIsageofassesmentinhours() {
		return isageofassesmentinhours;
	}

	public void setAgeatassesment(Integer ageatassesment) {
		this.ageatassesment = ageatassesment;
	}

	public void setIsageofassesmentinhours(Boolean isageofassesmentinhours) {
		this.isageofassesmentinhours = isageofassesmentinhours;
	}

	public String getRiskFactor() {
		return riskfactor;
	}

	public void setRiskFactor(String riskfactor) {
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

	public String getRiskfactor() {
		return riskfactor;
	}

	public void setRiskfactor(String riskfactor) {
		this.riskfactor = riskfactor;
	}

	public ScoreDownes getDownesScore() {
		return downesScore;
	}

	public void setDownesScore(ScoreDownes downesScore) {
		this.downesScore = downesScore;
	}

	public ScoreThompson getThompsonScore() {
		return thompsonScore;
	}

	public void setThompsonScore(ScoreThompson thompsonScore) {
		this.thompsonScore = thompsonScore;
	}

	public ScoreSarnat getSarnatScore() {
		return sarnatScore;
	}

	public void setSarnatScore(ScoreSarnat sarnatScore) {
		this.sarnatScore = sarnatScore;
	}

	public ScoreLevene getLeveneScore() {
		return leveneScore;
	}

	public void setLeveneScore(ScoreLevene leveneScore) {
		this.leveneScore = leveneScore;
	}

	public RespSupport getRespSupport() {
		return respSupport;
	}

	public void setRespSupport(RespSupport respSupport) {
		this.respSupport = respSupport;
	}

	public List<BabyPrescription> getBabyPrescription() {
		return babyPrescription;
	}

	public void setBabyPrescription(List<BabyPrescription> babyPrescription) {
		this.babyPrescription = babyPrescription;
	}

	public String getMedicationStr() {
		return medicationStr;
	}

	public void setMedicationStr(String medicationStr) {
		this.medicationStr = medicationStr;
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