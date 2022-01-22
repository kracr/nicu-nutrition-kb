
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
 * The persistent class for the antenatal_history_detail database table.
 * 
 */
@Entity
@Table(name = "antenatal_history_detail")
@NamedQuery(name = "AntenatalHistoryDetail.findAll", query = "SELECT a FROM AntenatalHistoryDetail a")
public class AntenatalHistoryDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "antenatal_history_id")
	private Long antenatalHistoryId;

	@Column(columnDefinition = "bool")
	private Boolean torch;

	@Column(columnDefinition = "bool")
	private Boolean isresuscitationMedication;

	@Column(columnDefinition = "bool")
	private Boolean ict;

	@Column(name = "ict_date")
	private Timestamp ictDate;

	@Column(name = "ict_titre")
	private Integer ictTitre;

	@Column(columnDefinition = "bool")
	private Boolean anitd;

	@Column(name = "antid_firstdose")
	private Integer antidFirstdose;

	@Column(name = "antid_secdose")
	private Integer antidSecdose;

	@Column(name = "a_score")
	private Integer aScore;

	@Column(name = "abnormal_umbilical_doppler_type")
	private String abnormalUmbilicalDopplerType;

	@Column(name = "anaesthesia_type")
	private String anaesthesiaType;

	@Column(name = "antenatel_checkup_status")
	private String antenatelCheckupStatus;

	@Column(name = "chronic_kidney_disease", columnDefinition = "bool")
	private Boolean chronicKidneyDisease;

	@Column(name = "conception_details")
	private String conceptionDetails;

	@Column(name = "conception_type")
	private String conceptionType;

	private Timestamp creationtime;

	@Column(columnDefinition = "bool")
	private Boolean diabetes;
	
	@Column(columnDefinition = "bool")
	private Boolean iugr;
	
	private String mgso4;

	@Column(name = "edd_by")
	private String eddBy;

	@Transient
	private String eddTimestampStr;

	@Column(name = "edd_timestamp")
	private Timestamp eddTimestamp;

	@Column(columnDefinition = "bool")
	private Boolean fever;

	@Column(columnDefinition = "bool")
	private Boolean isFirsttrimesterscreening;

	private String firsttrimesterscreening;

	@Column(name = "g_score")
	private Integer gScore;

	@Column(columnDefinition = "bool")
	private Boolean gdm;

	@Column(name = "gestational_hypertension", columnDefinition = "bool")
	private Boolean gestationalHypertension;

	@Column(name = "gestationby_lmp_days")
	private Integer gestationbyLmpDays;

	@Column(name = "gestationby_lmp_weeks")
	private Integer gestationbyLmpWeeks;

	@Column(name = "getstationby_usg_days")
	private Integer getstationbyUsgDays;

	@Column(name = "getstationby_usg_weeks")
	private Integer getstationbyUsgWeeks;

	@Column(name = "hepb_type")
	private String hepbType;
	
	@Column(name = "hbsag")
	private String hbsag;
	
	@Column(name = "hbeag")
	private String hbeag;
	
	@Column(name = "history_of_alcohol", columnDefinition = "bool")
	private Boolean historyOfAlcohol;

	@Column(name = "history_of_infections", columnDefinition = "bool")
	private Boolean historyOfInfections;

	private String historyOfIvInfectionText;

	@Column(name = "history_of_smoking", columnDefinition = "bool")
	private Boolean historyOfSmoking;

	@Column(columnDefinition = "bool")
	private Boolean hypertension;

	@Column(columnDefinition = "bool")
	private Boolean hyperthyroidism;

	@Column(columnDefinition = "bool")
	private Boolean hypothyroidism;

	@Column(name = "is_augmented", columnDefinition = "bool")
	private Boolean isAugmented;

	@Column(name = "is_labour", columnDefinition = "bool")
	private Boolean isLabour;

	@Column(name = "is_lscs_elective", columnDefinition = "bool")
	private Boolean isLscsElective;

	@Column(columnDefinition = "bool")
	private Boolean ishepb;

	@Column(columnDefinition = "bool")
	private Boolean ishiv;

	@Column(columnDefinition = "bool")
	private Boolean isthreeantenatalcheckupdone;

	@Column(name = "l_score")
	private Integer lScore;

	@Column(name = "lscs_indication")
	private String lscsIndication;

	@Column(name = "labour_type")
	private String labourType;

	@Transient
	private String lmpTimestampStr;

	@Column(name = "lmp_timestamp")
	private Timestamp lmpTimestamp;

	@Column(name = "mode_of_delivery")
	private String modeOfDelivery;

	private Timestamp modificationtime;

	@Column(name = "mother_blood_group_abo")
	private String motherBloodGroupAbo;

	@Column(name = "mother_blood_group_rh")
	private String motherBloodGroupRh;
	
	@Column(name = "father_blood_group_abo")
	private String fatherBloodGroupAbo;

	@Column(name = "father_blood_group_rh")
	private String fatherBloodGroupRh;

	@Column(columnDefinition = "bool")
	private Boolean oligohydraminos;

	@Column(name = "other_medications")
	private String otherMedications;

	@Column(name = "other_meternal_investigations")
	private String otherMeternalInvestigations;

	@Column(columnDefinition = "bool")
	private Boolean isOtherMeternalInvestigations;

	@Column(name = "other_presentation_type")
	private String otherPresentationType;

	@Column(name = "p_score")
	private Integer pScore;

	@Column(columnDefinition = "bool")
	private Boolean polyhydraminos;

	@Column(columnDefinition = "bool")
	private Boolean pprom;

	@Column(name = "pregnancy_type")
	private String pregnancyType;

	@Column(columnDefinition = "bool")
	private Boolean prematurity;

	@Column(name = "presentation_type")
	private String presentationType;

	@Column(columnDefinition = "bool")
	private Boolean prom;

	private String promText;

	@Column(columnDefinition = "bool")
	private Boolean isSecondtrimesterscreening;

	private String secondtrimesterscreening;

	@Column(name = "tetanus_toxoid", columnDefinition = "bool")
	private Boolean tetanusToxoid;

	@Column(name = "tetanus_toxoid_doses")
	private Integer tetanusToxoidDoses;

	@Column(columnDefinition = "bool")
	private Boolean isThirdtrimesterscreening;

	private String thirdtrimesterscreening;

	@Column(name = "total_pregnancy")
	private Integer totalPregnancy;

	@Column(name = "trimester_type")
	private String trimesterType;

	private String uhid;

	@Column(name = "umbilical_doppler")
	private String umbilicalDoppler;

	@Column(columnDefinition = "bool")
	private Boolean uti;

	@Column(columnDefinition = "bool")
	private Boolean vdrl;

	@Column(columnDefinition = "bool")
	private Boolean chorioamniotis;

	@Column(columnDefinition = "bool")
	private Boolean noneOther;

	@Column(columnDefinition = "bool")
	private Boolean noneInfection;
	
	@Column(columnDefinition = "bool")
	private Boolean noneDisease;
	
	@Column(columnDefinition = "bool")
	private Boolean noneHO;
	
	private String otherRiskFactors;

	private String isAntenatalSteroidGiven;

	private String episodeid;

	@Transient
	AntenatalSteroidDetail firstSteroidDetail;

	@Transient
	AntenatalSteroidDetail secondSteroidDetail;

	private String notKnownType;

	private String torchText;

	private String hivType;

	private String vdrlType;

	private String antenatalHistoryText;

	@Column(name = "mode_of_delivery_other_text")
	private String modeOfDeliveryOtherText;

	@Column(columnDefinition = "bool")
	private Boolean isCourseRepeated;
	
	@Transient
	private String etTimestampStr;

	@Column(name = "et_timestamp")
	private Timestamp etTimestamp;
	
	@Column(name = "medication_allergy")
	private String medicationallergy;

	public AntenatalHistoryDetail() {
		this.firstSteroidDetail = new AntenatalSteroidDetail();
		this.secondSteroidDetail = new AntenatalSteroidDetail();
	}

	public Long getAntenatalHistoryId() {
		return antenatalHistoryId;
	}

	public Boolean getTorch() {
		return torch;
	}

	public Boolean getIct() {
		return ict;
	}

	public Timestamp getIctDate() {
		return ictDate;
	}

	public Integer getIctTitre() {
		return ictTitre;
	}

	public Boolean getAnitd() {
		return anitd;
	}

	public Integer getAntidFirstdose() {
		return antidFirstdose;
	}

	public Integer getAntidSecdose() {
		return antidSecdose;
	}

	public Integer getaScore() {
		return aScore;
	}

	public String getAbnormalUmbilicalDopplerType() {
		return abnormalUmbilicalDopplerType;
	}

	public String getAnaesthesiaType() {
		return anaesthesiaType;
	}

	public String getAntenatelCheckupStatus() {
		return antenatelCheckupStatus;
	}

	public Boolean getChronicKidneyDisease() {
		return chronicKidneyDisease;
	}

	public String getConceptionDetails() {
		return conceptionDetails;
	}

	public String getConceptionType() {
		return conceptionType;
	}

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public Boolean getDiabetes() {
		return diabetes;
	}

	public String getEddBy() {
		return eddBy;
	}

	public Timestamp getEddTimestamp() {
		return eddTimestamp;
	}

	public Boolean getFever() {
		return fever;
	}

	public Boolean getIsFirsttrimesterscreening() {
		return isFirsttrimesterscreening;
	}

	public String getFirsttrimesterscreening() {
		return firsttrimesterscreening;
	}

	public Integer getgScore() {
		return gScore;
	}

	public Boolean getGdm() {
		return gdm;
	}

	public Boolean getGestationalHypertension() {
		return gestationalHypertension;
	}

	public Integer getGestationbyLmpDays() {
		return gestationbyLmpDays;
	}

	public Integer getGestationbyLmpWeeks() {
		return gestationbyLmpWeeks;
	}

	public Integer getGetstationbyUsgDays() {
		return getstationbyUsgDays;
	}

	public Integer getGetstationbyUsgWeeks() {
		return getstationbyUsgWeeks;
	}

	public String getHepbType() {
		return hepbType;
	}

	public Boolean getHistoryOfAlcohol() {
		return historyOfAlcohol;
	}

	public Boolean getHistoryOfInfections() {
		return historyOfInfections;
	}

	public String getHistoryOfIvInfectionText() {
		return historyOfIvInfectionText;
	}

	public Boolean getHistoryOfSmoking() {
		return historyOfSmoking;
	}

	public Boolean getHypertension() {
		return hypertension;
	}

	public Boolean getHyperthyroidism() {
		return hyperthyroidism;
	}

	public Boolean getHypothyroidism() {
		return hypothyroidism;
	}

	public Boolean getIsAugmented() {
		return isAugmented;
	}

	public Boolean getIsLabour() {
		return isLabour;
	}

	public Boolean getIsLscsElective() {
		return isLscsElective;
	}

	public Boolean getIshepb() {
		return ishepb;
	}

	public Boolean getIshiv() {
		return ishiv;
	}

	public Boolean getIsthreeantenatalcheckupdone() {
		return isthreeantenatalcheckupdone;
	}

	public Integer getlScore() {
		return lScore;
	}

	public String getLscsIndication() {
		return lscsIndication;
	}

	public void setLscsIndication(String lscsIndication) {
		this.lscsIndication = lscsIndication;
	}

	public String getLabourType() {
		return labourType;
	}

	public Timestamp getLmpTimestamp() {
		return lmpTimestamp;
	}

	public String getModeOfDelivery() {
		return modeOfDelivery;
	}

	public Timestamp getModificationtime() {
		return modificationtime;
	}

	public String getMotherBloodGroupAbo() {
		return motherBloodGroupAbo;
	}

	public String getMotherBloodGroupRh() {
		return motherBloodGroupRh;
	}

	public Boolean getOligohydraminos() {
		return oligohydraminos;
	}

	public String getOtherMedications() {
		return otherMedications;
	}

	public String getOtherMeternalInvestigations() {
		return otherMeternalInvestigations;
	}

	public Boolean getIsOtherMeternalInvestigations() {
		return isOtherMeternalInvestigations;
	}

	public String getOtherPresentationType() {
		return otherPresentationType;
	}

	public Integer getpScore() {
		return pScore;
	}

	public Boolean getPolyhydraminos() {
		return polyhydraminos;
	}

	public Boolean getPprom() {
		return pprom;
	}

	public String getPregnancyType() {
		return pregnancyType;
	}

	public Boolean getPrematurity() {
		return prematurity;
	}

	public String getPresentationType() {
		return presentationType;
	}

	public Boolean getProm() {
		return prom;
	}

	public String getPromText() {
		return promText;
	}

	public Boolean getIsSecondtrimesterscreening() {
		return isSecondtrimesterscreening;
	}

	public String getSecondtrimesterscreening() {
		return secondtrimesterscreening;
	}

	public Boolean getTetanusToxoid() {
		return tetanusToxoid;
	}

	public Integer getTetanusToxoidDoses() {
		return tetanusToxoidDoses;
	}

	public Boolean getIsThirdtrimesterscreening() {
		return isThirdtrimesterscreening;
	}

	public String getThirdtrimesterscreening() {
		return thirdtrimesterscreening;
	}

	public Integer getTotalPregnancy() {
		return totalPregnancy;
	}

	public String getTrimesterType() {
		return trimesterType;
	}

	public String getUhid() {
		return uhid;
	}

	public String getUmbilicalDoppler() {
		return umbilicalDoppler;
	}

	public Boolean getUti() {
		return uti;
	}

	public Boolean getVdrl() {
		return vdrl;
	}

	public Boolean getChorioamniotis() {
		return chorioamniotis;
	}

	public String getOtherRiskFactors() {
		return otherRiskFactors;
	}

	

	public String getIsAntenatalSteroidGiven() {
		return isAntenatalSteroidGiven;
	}

	public void setIsAntenatalSteroidGiven(String isAntenatalSteroidGiven) {
		this.isAntenatalSteroidGiven = isAntenatalSteroidGiven;
	}

	public String getEpisodeid() {
		return episodeid;
	}

	public AntenatalSteroidDetail getFirstSteroidDetail() {
		return firstSteroidDetail;
	}

	public AntenatalSteroidDetail getSecondSteroidDetail() {
		return secondSteroidDetail;
	}

	public String getNotKnownType() {
		return notKnownType;
	}

	public String getTorchText() {
		return torchText;
	}

	public String getHivType() {
		return hivType;
	}

	public String getVdrlType() {
		return vdrlType;
	}

	public String getAntenatalHistoryText() {
		return antenatalHistoryText;
	}

	public void setAntenatalHistoryId(Long antenatalHistoryId) {
		this.antenatalHistoryId = antenatalHistoryId;
	}

	public void setTorch(Boolean torch) {
		this.torch = torch;
	}

	public void setIct(Boolean ict) {
		this.ict = ict;
	}

	public void setIctDate(Timestamp ictDate) {
		this.ictDate = ictDate;
	}

	public void setIctTitre(Integer ictTitre) {
		this.ictTitre = ictTitre;
	}

	public void setAnitd(Boolean anitd) {
		this.anitd = anitd;
	}

	public void setAntidFirstdose(Integer antidFirstdose) {
		this.antidFirstdose = antidFirstdose;
	}

	public void setAntidSecdose(Integer antidSecdose) {
		this.antidSecdose = antidSecdose;
	}

	public void setaScore(Integer aScore) {
		this.aScore = aScore;
	}

	public void setAbnormalUmbilicalDopplerType(String abnormalUmbilicalDopplerType) {
		this.abnormalUmbilicalDopplerType = abnormalUmbilicalDopplerType;
	}

	public void setAnaesthesiaType(String anaesthesiaType) {
		this.anaesthesiaType = anaesthesiaType;
	}

	public void setAntenatelCheckupStatus(String antenatelCheckupStatus) {
		this.antenatelCheckupStatus = antenatelCheckupStatus;
	}

	public void setChronicKidneyDisease(Boolean chronicKidneyDisease) {
		this.chronicKidneyDisease = chronicKidneyDisease;
	}

	public void setConceptionDetails(String conceptionDetails) {
		this.conceptionDetails = conceptionDetails;
	}

	public void setConceptionType(String conceptionType) {
		this.conceptionType = conceptionType;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public void setDiabetes(Boolean diabetes) {
		this.diabetes = diabetes;
	}

	public void setEddBy(String eddBy) {
		this.eddBy = eddBy;
	}

	public void setEddTimestamp(Timestamp eddTimestamp) {
		this.eddTimestamp = eddTimestamp;
	}

	public void setFever(Boolean fever) {
		this.fever = fever;
	}

	public void setIsFirsttrimesterscreening(Boolean isFirsttrimesterscreening) {
		this.isFirsttrimesterscreening = isFirsttrimesterscreening;
	}

	public void setFirsttrimesterscreening(String firsttrimesterscreening) {
		this.firsttrimesterscreening = firsttrimesterscreening;
	}

	public void setgScore(Integer gScore) {
		this.gScore = gScore;
	}

	public void setGdm(Boolean gdm) {
		this.gdm = gdm;
	}

	public void setGestationalHypertension(Boolean gestationalHypertension) {
		this.gestationalHypertension = gestationalHypertension;
	}

	public void setGestationbyLmpDays(Integer gestationbyLmpDays) {
		this.gestationbyLmpDays = gestationbyLmpDays;
	}

	public void setGestationbyLmpWeeks(Integer gestationbyLmpWeeks) {
		this.gestationbyLmpWeeks = gestationbyLmpWeeks;
	}

	public void setGetstationbyUsgDays(Integer getstationbyUsgDays) {
		this.getstationbyUsgDays = getstationbyUsgDays;
	}

	public void setGetstationbyUsgWeeks(Integer getstationbyUsgWeeks) {
		this.getstationbyUsgWeeks = getstationbyUsgWeeks;
	}

	public void setHepbType(String hepbType) {
		this.hepbType = hepbType;
	}

	public void setHistoryOfAlcohol(Boolean historyOfAlcohol) {
		this.historyOfAlcohol = historyOfAlcohol;
	}

	public void setHistoryOfInfections(Boolean historyOfInfections) {
		this.historyOfInfections = historyOfInfections;
	}

	public void setHistoryOfIvInfectionText(String historyOfIvInfectionText) {
		this.historyOfIvInfectionText = historyOfIvInfectionText;
	}

	public void setHistoryOfSmoking(Boolean historyOfSmoking) {
		this.historyOfSmoking = historyOfSmoking;
	}

	public void setHypertension(Boolean hypertension) {
		this.hypertension = hypertension;
	}

	public void setHyperthyroidism(Boolean hyperthyroidism) {
		this.hyperthyroidism = hyperthyroidism;
	}

	public void setHypothyroidism(Boolean hypothyroidism) {
		this.hypothyroidism = hypothyroidism;
	}

	public void setIsAugmented(Boolean isAugmented) {
		this.isAugmented = isAugmented;
	}

	public void setIsLabour(Boolean isLabour) {
		this.isLabour = isLabour;
	}

	public void setIsLscsElective(Boolean isLscsElective) {
		this.isLscsElective = isLscsElective;
	}

	public void setIshepb(Boolean ishepb) {
		this.ishepb = ishepb;
	}

	public void setIshiv(Boolean ishiv) {
		this.ishiv = ishiv;
	}

	public void setIsthreeantenatalcheckupdone(Boolean isthreeantenatalcheckupdone) {
		this.isthreeantenatalcheckupdone = isthreeantenatalcheckupdone;
	}

	public void setlScore(Integer lScore) {
		this.lScore = lScore;
	}

	public void setLabourType(String labourType) {
		this.labourType = labourType;
	}

	public void setLmpTimestamp(Timestamp lmpTimestamp) {
		this.lmpTimestamp = lmpTimestamp;
	}

	public void setModeOfDelivery(String modeOfDelivery) {
		this.modeOfDelivery = modeOfDelivery;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public void setMotherBloodGroupAbo(String motherBloodGroupAbo) {
		this.motherBloodGroupAbo = motherBloodGroupAbo;
	}

	public void setMotherBloodGroupRh(String motherBloodGroupRh) {
		this.motherBloodGroupRh = motherBloodGroupRh;
	}

	public void setOligohydraminos(Boolean oligohydraminos) {
		this.oligohydraminos = oligohydraminos;
	}

	public void setOtherMedications(String otherMedications) {
		this.otherMedications = otherMedications;
	}

	public void setOtherMeternalInvestigations(String otherMeternalInvestigations) {
		this.otherMeternalInvestigations = otherMeternalInvestigations;
	}

	public void setIsOtherMeternalInvestigations(Boolean isOtherMeternalInvestigations) {
		this.isOtherMeternalInvestigations = isOtherMeternalInvestigations;
	}

	public void setOtherPresentationType(String otherPresentationType) {
		this.otherPresentationType = otherPresentationType;
	}

	public void setpScore(Integer pScore) {
		this.pScore = pScore;
	}

	public void setPolyhydraminos(Boolean polyhydraminos) {
		this.polyhydraminos = polyhydraminos;
	}

	public void setPprom(Boolean pprom) {
		this.pprom = pprom;
	}

	public void setPregnancyType(String pregnancyType) {
		this.pregnancyType = pregnancyType;
	}

	public void setPrematurity(Boolean prematurity) {
		this.prematurity = prematurity;
	}

	public void setPresentationType(String presentationType) {
		this.presentationType = presentationType;
	}

	public void setProm(Boolean prom) {
		this.prom = prom;
	}

	public void setPromText(String promText) {
		this.promText = promText;
	}

	public void setIsSecondtrimesterscreening(Boolean isSecondtrimesterscreening) {
		this.isSecondtrimesterscreening = isSecondtrimesterscreening;
	}

	public void setSecondtrimesterscreening(String secondtrimesterscreening) {
		this.secondtrimesterscreening = secondtrimesterscreening;
	}

	public void setTetanusToxoid(Boolean tetanusToxoid) {
		this.tetanusToxoid = tetanusToxoid;
	}

	public void setTetanusToxoidDoses(Integer tetanusToxoidDoses) {
		this.tetanusToxoidDoses = tetanusToxoidDoses;
	}

	public void setIsThirdtrimesterscreening(Boolean isThirdtrimesterscreening) {
		this.isThirdtrimesterscreening = isThirdtrimesterscreening;
	}

	public void setThirdtrimesterscreening(String thirdtrimesterscreening) {
		this.thirdtrimesterscreening = thirdtrimesterscreening;
	}

	public void setTotalPregnancy(Integer totalPregnancy) {
		this.totalPregnancy = totalPregnancy;
	}

	public void setTrimesterType(String trimesterType) {
		this.trimesterType = trimesterType;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public void setUmbilicalDoppler(String umbilicalDoppler) {
		this.umbilicalDoppler = umbilicalDoppler;
	}

	public void setUti(Boolean uti) {
		this.uti = uti;
	}

	public void setVdrl(Boolean vdrl) {
		this.vdrl = vdrl;
	}

	public void setChorioamniotis(Boolean chorioamniotis) {
		this.chorioamniotis = chorioamniotis;
	}

	public void setOtherRiskFactors(String otherRiskFactors) {
		this.otherRiskFactors = otherRiskFactors;
	}

	

	public void setEpisodeid(String episodeid) {
		this.episodeid = episodeid;
	}

	public void setFirstSteroidDetail(AntenatalSteroidDetail firstSteroidDetail) {
		this.firstSteroidDetail = firstSteroidDetail;
	}

	public void setSecondSteroidDetail(AntenatalSteroidDetail secondSteroidDetail) {
		this.secondSteroidDetail = secondSteroidDetail;
	}

	public void setNotKnownType(String notKnownType) {
		this.notKnownType = notKnownType;
	}

	public void setTorchText(String torchText) {
		this.torchText = torchText;
	}

	public void setHivType(String hivType) {
		this.hivType = hivType;
	}

	public void setVdrlType(String vdrlType) {
		this.vdrlType = vdrlType;
	}

	public void setAntenatalHistoryText(String antenatalHistoryText) {
		this.antenatalHistoryText = antenatalHistoryText;
	}

	public Boolean getIsCourseRepeated() {
		return isCourseRepeated;
	}

	public void setIsCourseRepeated(Boolean isCourseRepeated) {
		this.isCourseRepeated = isCourseRepeated;
	}

	public Boolean getIsresuscitationMedication() {
		return isresuscitationMedication;
	}

	public void setIsresuscitationMedication(Boolean isresuscitationMedication) {
		this.isresuscitationMedication = isresuscitationMedication;
	}

	public String getEddTimestampStr() {
		return eddTimestampStr;
	}

	public void setEddTimestampStr(String eddTimestampStr) {
		this.eddTimestampStr = eddTimestampStr;
	}

	public String getLmpTimestampStr() {
		return lmpTimestampStr;
	}

	public void setLmpTimestampStr(String lmpTimestampStr) {
		this.lmpTimestampStr = lmpTimestampStr;
	}

	public String getModeOfDeliveryOtherText() {
		return modeOfDeliveryOtherText;
	}

	public void setModeOfDeliveryOtherText(String modeOfDeliveryOtherText) {
		this.modeOfDeliveryOtherText = modeOfDeliveryOtherText;
	}

	public String getEtTimestampStr() {
		return etTimestampStr;
	}

	public void setEtTimestampStr(String etTimestampStr) {
		this.etTimestampStr = etTimestampStr;
	}

	public Timestamp getEtTimestamp() {
		return etTimestamp;
	}

	public void setEtTimestamp(Timestamp etTimestamp) {
		this.etTimestamp = etTimestamp;
	}

	public String getFatherBloodGroupAbo() {
		return fatherBloodGroupAbo;
	}

	public void setFatherBloodGroupAbo(String fatherBloodGroupAbo) {
		this.fatherBloodGroupAbo = fatherBloodGroupAbo;
	}

	public String getFatherBloodGroupRh() {
		return fatherBloodGroupRh;
	}

	public void setFatherBloodGroupRh(String fatherBloodGroupRh) {
		this.fatherBloodGroupRh = fatherBloodGroupRh;
	}



	public String getHbsag() {
		return hbsag;
	}

	public void setHbsag(String hbsag) {
		this.hbsag = hbsag;
	}

	public String getHbeag() {
		return hbeag;
	}

	public void setHbeag(String hbeag) {
		this.hbeag = hbeag;
	}

	

	public Boolean getNoneOther() {
		return noneOther;
	}

	public void setNoneOther(Boolean noneOther) {
		this.noneOther = noneOther;
	}

	public Boolean getNoneInfection() {
		return noneInfection;
	}

	public void setNoneInfection(Boolean noneInfection) {
		this.noneInfection = noneInfection;
	}

	public Boolean getNoneDisease() {
		return noneDisease;
	}

	public void setNoneDisease(Boolean noneDisease) {
		this.noneDisease = noneDisease;
	}

	public Boolean getNoneHO() {
		return noneHO;
	}

	public void setNoneHO(Boolean noneHO) {
		this.noneHO = noneHO;
	}

	public String getMedicationallergy() {
		return medicationallergy;
	}

	public void setMedicationallergy(String medicationallergy) {
		this.medicationallergy = medicationallergy;
	}

	public Boolean getIugr() {
		return iugr;
	}

	public void setIugr(Boolean iugr) {
		this.iugr = iugr;
	}

	public String getMgso4() {
		return mgso4;
	}

	public void setMgso4(String mgso4) {
		this.mgso4 = mgso4;
	}
	
	

}