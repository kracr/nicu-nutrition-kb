package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the discharge_notes_detail database table.
 * 
 */
@Entity
@Table(name = "discharge_notes_detail")
@NamedQuery(name = "DischargeNotesDetail.findAll", query = "SELECT d FROM DischargeNotesDetail d")
public class DischargeNotesDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "discharge_notes_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long dischargeNotesId;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String uhid;

	@Column(name = "curr_antenatal_details")
	private String currAntenatalDetails;

	@Column(name = "antenatal_details")
	private String antenatalDetails;

	@Column(name = "curr_birth_details")
	private String currBirthDetails;

	@Column(name = "birth_details")
	private String birthDetails;

	@Column(name = "curr_status_at_admission")
	private String currStatusAtAdmission;

	@Column(name = "status_at_admission")
	private String statusAtAdmission;

	@Column(name = "curr_growth")
	private String currGrowth;

	@Column(name = "growth")
	private String growth;

	private String stableNotes;

	@Column(name = "prev_provisional_notes_rds")
	private String prevProvisionalNotesRds;

	@Column(name = "provisional_notes_rds")
	private String provisionalNotesRds;

	@Column(name = "rdsnotes")
	private String rdsNotes;

	@Column(name = "prev_provisional_notes_apnea")
	private String prevProvisionalNotesApnea;

	@Column(name = "provisional_notes_apnea")
	private String provisionalNotesApnea;

	@Column(name = "apneanotes")
	private String apneaNotes;

	@Column(name = "prev_provisional_notes_pphn")
	private String prevProvisionalNotesPphn;

	@Column(name = "provisional_notes_pphn")
	private String provisionalNotesPphn;

	@Column(name = "pphnnotes")
	private String pphnNotes;

	@Column(name = "prev_provisional_notes_pneumothorax")
	private String prevProvisionalNotesPneumothorax;

	@Column(name = "provisional_notes_pneumothorax")
	private String provisionalNotesPneumothorax;

	@Column(name = "pneumonotes")
	private String pneumoNotes;

	@Column(name = "prev_provisional_notes_jaundice")
	private String prevProvisionalNotesJaundice;
	
	@Column(name = "prev_provisional_notes_shock")
	private String prevProvisionalNotesShock;

	private String jaundice;
	
	private String shock;

	@Column(name = "provisional_notes_jaundice")
	private String provisionalNotesJaundice;
	
	@Column(name = "provisional_notes_shock")
	private String provisionalNotesShock;

	@Column(name = "prev_provisional_notes_feedintolerance")
	private String prevProvisionalNotesFeedIntolerance;

	private String feedintolerance;

	@Column(name = "provisional_notes_feedintolerance")
	private String provisionalNotesFeedIntolerance;

	@Column(name = "prev_provisional_notes_asphyxia")
	private String prevProvisionalNotesAsphyxia;

	@Column(name = "provisional_notes_asphyxia")
	private String provisionalNotesAsphyxia;

	@Column(name = "asphyxianotes")
	private String asphyxiaNotes;

	@Column(name = "prev_provisional_notes_seizure")
	private String prevProvisionalNotesSeizure;

	@Column(name = "provisional_notes_seizure")
	private String provisionalNotesSeizure;

	@Column(name = "seizurenotes")
	private String seizureNotes;

	@Column(name = "prev_provisional_notes_sepsis")
	private String prevProvisionalNotesSepsis;

	@Column(name = "provisional_notes_sepsis")
	private String provisionalNotesSepsis;

	@Column(name = "sepsisnotes")
	private String sepsisNotes;

	@Column(name = "prev_provisional_notes_miscellaneous")
	private String prevProvisionalNotesMiscellaneous;

	@Column(name = "provisional_notes_miscellaneous")
	private String provisionalNotesMiscellaneous;

	@Column(name = "miscellaneousnotes")
	private String miscellaneousNotes;

	@Column(name = "curr_treatment")
	private String currTreatment;

	private String treatment;

	@Column(name = "curr_screening")
	private String currScreening;

	private String screening;

	@Column(name = "curr_procedure")
	private String currProcedure;

	private String procedure;

	@Column(name = "curr_bloodProduct")
	private String currBloodProduct;

	private String bloodProduct;

	@Column(name = "curr_nutrition")
	private String currNutrition;

	private String nutrition;

	@Column(name = "curr_vaccination")
	private String currVaccination;

	private String vaccination;

	private String advice;

	@Column(columnDefinition = "bool")
	private Boolean isAntenatalHistory;

	@Column(columnDefinition = "bool")
	private Boolean isBirthDetails;

	@Column(columnDefinition = "bool")
	private Boolean isStatusAtAdmission;

	@Column(columnDefinition = "bool")
	private Boolean isGrowth;

	@Column(columnDefinition = "bool")
	private Boolean isStableNotes;

	@Column(columnDefinition = "bool")
	private Boolean isRdsNotes;

	@Column(columnDefinition = "bool")
	private Boolean isApneaNotes;

	@Column(columnDefinition = "bool")
	private Boolean isPphnNotes;

	@Column(columnDefinition = "bool")
	private Boolean isPneumoNotes;

	@Column(columnDefinition = "bool")
	private Boolean isJaundice;
	
	@Column(columnDefinition = "bool")
	private Boolean isShock;

	@Column(columnDefinition = "bool")
	private Boolean isFeedIntolerance;

	@Column(columnDefinition = "bool")
	private Boolean isAsphyxiaNotes;

	@Column(columnDefinition = "bool")
	private Boolean isSeizureNotes;

	@Column(columnDefinition = "bool")
	private Boolean isSepsisNotes;

	@Column(columnDefinition = "bool")
	private Boolean isMiscellaneousNotes;

	@Column(columnDefinition = "bool")
	private Boolean isTreatment;

	@Column(columnDefinition = "bool")
	private Boolean isBloodProduct;

	@Column(columnDefinition = "bool")
	private Boolean isProcedure;

	@Column(columnDefinition = "bool")
	private Boolean isScreening;

	@Column(columnDefinition = "bool")
	private Boolean isNutrition;

	@Column(columnDefinition = "bool")
	private Boolean isVaccination;


	// New Column Added
	@Column(columnDefinition="bool")
	private Boolean isNecNotes;

	@Column(name="necnotes")
	private String necNotes;

	@Column(name="prev_provisional_notes_nec")
	private String prevProvisionalNotesNec;

	@Column(name="provisional_notes_nec")
	private String provisionalNotesNec;
	/**
	 * Purpose: Hospital Course in Discharge Summary
	 * 
	 * @Updated on: 28/6/2019
	 * @author:Ekpreet kaur
	 * 
	 */

	@Column(columnDefinition = "bool")
	private Boolean isVapNotes;

	@Column(columnDefinition = "bool")
	private Boolean isClabsiNotes;

	@Column(columnDefinition = "bool")
	private Boolean isIntrauterineNotes;

	@Column(columnDefinition = "bool")
	private Boolean isEncephalopathyNotes;

	@Column(columnDefinition = "bool")
	private Boolean isNeuromuscularDisorderNotes;

	@Column(columnDefinition = "bool")
	private Boolean isIVHNotes;

	@Column(columnDefinition = "bool")
	private Boolean isHydrocephalusNotes;

	@Column(columnDefinition = "bool")
	private Boolean isRenalNotes;

	@Column(columnDefinition = "bool")
	private Boolean isHypoglycemiaNotes;

	@Column(name = "prev_provisional_notes_vap")
	private String prevProvisionalNotesvap;

	@Column(name = "provisional_notes_vap")
	private String provisionalNotesVap;

	@Column(name = "vapnotes")
	private String vapNotes;

	@Column(name = "prev_provisional_notes_clabsi")
	private String prevProvisionalNotesClabsi;

	@Column(name = "provisional_notes_clabsi")
	private String provisionalNotesClabsi;

	@Column(name = "clabsinotes")
	private String clabsiNotes;

	@Column(name = "prev_provisional_notes_intrauterine")
	private String prevProvisionalNotesIntrauterine;

	@Column(name = "provisional_notes_intrauterine")
	private String provisionalNotesIntrauterine;

	@Column(name = "intrauterinenotes")
	private String intrauterineNotes;

	@Column(name = "prev_provisional_notes_hypoglycemia")
	private String prevProvisionalNotesHypoglycemia;

	@Column(name = "provisional_notes_hypoglycemia")
	private String provisionalNoteshypoglycemia;

	@Column(name = "hypoglycemianotes")
	private String hypoglycemiaNotes;

	@Column(name = "prev_provisional_notes_renal")
	private String prevProvisionalNotesRenal;

	@Column(name = "provisional_notes_renal")
	private String provisionalNotesRenal;

	@Column(name = "renalnotes")
	private String renalNotes;

	@Column(name = "prev_provisional_notes_encephalopathy")
	private String prevProvisionalNotesEncephalopathy;

	@Column(name = "provisional_notes_encephalopathy")
	private String provisionalNotesEncephalopathy;

	@Column(name = "encephalopathynotes")
	private String encephalopathyNotes;

	@Column(name = "prev_provisional_notes_neuromuscularDisorder")
	private String prevProvisionalNotesNeuromuscularDisorder;

	@Column(name = "provisional_notes_neuromuscularDisorder")
	private String provisionalNotesNeuromuscularDisorder;

	@Column(name = "neuromuscularDisordernotes")
	private String neuromuscularDisorderNotes;

	@Column(name = "prev_provisional_notes_ivh")
	private String prevProvisionalNotesIvh;

	@Column(name = "provisional_notes_ivh")
	private String provisionalNotesIvh;

	@Column(name = "ivhnotes")
	private String ivhNotes;
	
	@Column(name = "prev_provisional_notes_hydrocephalus")
	private String prevProvisionalNotesHydrocephalus;

	@Column(name = "provisional_notes_hydrocephalus")
	private String provisionalNotesHydrocephalus;

	@Column(name = "hydrocephalusnotes")
	private String hydrocephalusNotes;
	
	private String doctor;
	
	@Column(name = "follow_up_note")
	private String followUpNote;

	public DischargeNotesDetail() {
		this.isAntenatalHistory = false;
		this.isBirthDetails = false;
		this.isStatusAtAdmission = false;
		this.isGrowth = false;
		this.isStableNotes = false;
		this.isRdsNotes = false;
		this.isApneaNotes = false;
		this.isPphnNotes = false;
		this.isPneumoNotes = false;
		this.isJaundice = false;
		this.isShock = false;
		this.isAsphyxiaNotes = false;
		this.isSeizureNotes = false;
		this.isSepsisNotes = false;
		this.isMiscellaneousNotes = false;
		this.isTreatment = false;
		this.isBloodProduct = false;
		this.isProcedure = false;
		this.isScreening = false;
		this.isNutrition = false;
		this.isVaccination = false;
		this.isVapNotes = false;
		this.isClabsiNotes = false;
		this.isIntrauterineNotes = false;
		this.isRenalNotes = false;
		this.isHypoglycemiaNotes = false;
		this.isEncephalopathyNotes = false;
		this.isNeuromuscularDisorderNotes = false;
		this.isIVHNotes = false;
		this.isHydrocephalusNotes = false;
		this.isNecNotes=false;
	}

	public Boolean getIsNecNotes() {
		return this.isNecNotes;
	}

	public void setIsNecNotes(Boolean necNotes) {
		this.isNecNotes = necNotes;
	}

	public void setNecNotes(String necNotes) {
		this.necNotes = necNotes;
	}

	public String getNecNotes() {
		return this.necNotes;
	}


	public String getPrevProvisionalNotesNec() {
		return prevProvisionalNotesNec;
	}

	public void setPrevProvisionalNotesNec(String prevProvisionalNotesNec) {
		this.prevProvisionalNotesNec = prevProvisionalNotesNec;
	}

	public String getProvisionalNotesNec() {
		return provisionalNotesNec;
	}

	public void setProvisionalNotesNec(String provisionalNotesNec) {
		this.provisionalNotesNec = provisionalNotesNec;
	}

	public Long getDischargeNotesId() {
		return this.dischargeNotesId;
	}

	public void setDischargeNotesId(Long dischargeNotesId) {
		this.dischargeNotesId = dischargeNotesId;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getCurrAntenatalDetails() {
		return currAntenatalDetails;
	}

	public void setCurrAntenatalDetails(String currAntenatalDetails) {
		this.currAntenatalDetails = currAntenatalDetails;
	}

	public String getAntenatalDetails() {
		return this.antenatalDetails;
	}

	public void setAntenatalDetails(String antenatalDetails) {
		this.antenatalDetails = antenatalDetails;
	}

	public String getCurrStatusAtAdmission() {
		return currStatusAtAdmission;
	}

	public void setCurrStatusAtAdmission(String currStatusAtAdmission) {
		this.currStatusAtAdmission = currStatusAtAdmission;
	}

	public String getCurrBirthDetails() {
		return currBirthDetails;
	}

	public void setBirthDetails(String birthDetails) {
		this.birthDetails = birthDetails;
	}

	public void setCurrBirthDetails(String currBirthDetails) {
		this.currBirthDetails = currBirthDetails;
	}

	public String getBirthDetails() {
		return this.birthDetails;
	}

	public String getStatusAtAdmission() {
		return this.statusAtAdmission;
	}

	public void setStatusAtAdmission(String statusAtAdmission) {
		this.statusAtAdmission = statusAtAdmission;
	}

	public String getCurrGrowth() {
		return currGrowth;
	}

	public void setCurrGrowth(String currGrowth) {
		this.currGrowth = currGrowth;
	}

	public String getGrowth() {
		return growth;
	}

	public void setGrowth(String growth) {
		this.growth = growth;
	}

	public String getStableNotes() {
		return stableNotes;
	}

	public void setStableNotes(String stableNotes) {
		this.stableNotes = stableNotes;
	}

	public String getPrevProvisionalNotesRds() {
		return prevProvisionalNotesRds;
	}

	public void setPrevProvisionalNotesRds(String prevProvisionalNotesRds) {
		this.prevProvisionalNotesRds = prevProvisionalNotesRds;
	}

	public String getProvisionalNotesRds() {
		return provisionalNotesRds;
	}

	public void setProvisionalNotesRds(String provisionalNotesRds) {
		this.provisionalNotesRds = provisionalNotesRds;
	}

	public String getRdsNotes() {
		return rdsNotes;
	}

	public void setRdsNotes(String rdsNotes) {
		this.rdsNotes = rdsNotes;
	}

	public String getPrevProvisionalNotesApnea() {
		return prevProvisionalNotesApnea;
	}

	public void setPrevProvisionalNotesApnea(String prevProvisionalNotesApnea) {
		this.prevProvisionalNotesApnea = prevProvisionalNotesApnea;
	}

	public String getProvisionalNotesApnea() {
		return provisionalNotesApnea;
	}

	public void setProvisionalNotesApnea(String provisionalNotesApnea) {
		this.provisionalNotesApnea = provisionalNotesApnea;
	}

	public String getApneaNotes() {
		return apneaNotes;
	}

	public void setApneaNotes(String apneaNotes) {
		this.apneaNotes = apneaNotes;
	}

	public String getPrevProvisionalNotesPphn() {
		return prevProvisionalNotesPphn;
	}

	public void setPrevProvisionalNotesPphn(String prevProvisionalNotesPphn) {
		this.prevProvisionalNotesPphn = prevProvisionalNotesPphn;
	}

	public String getProvisionalNotesPphn() {
		return provisionalNotesPphn;
	}

	public void setProvisionalNotesPphn(String provisionalNotesPphn) {
		this.provisionalNotesPphn = provisionalNotesPphn;
	}

	public String getPphnNotes() {
		return pphnNotes;
	}

	public void setPphnNotes(String pphnNotes) {
		this.pphnNotes = pphnNotes;
	}

	public String getPrevProvisionalNotesPneumothorax() {
		return prevProvisionalNotesPneumothorax;
	}

	public void setPrevProvisionalNotesPneumothorax(String prevProvisionalNotesPneumothorax) {
		this.prevProvisionalNotesPneumothorax = prevProvisionalNotesPneumothorax;
	}

	public String getProvisionalNotesPneumothorax() {
		return provisionalNotesPneumothorax;
	}

	public void setProvisionalNotesPneumothorax(String provisionalNotesPneumothorax) {
		this.provisionalNotesPneumothorax = provisionalNotesPneumothorax;
	}

	public String getPneumoNotes() {
		return pneumoNotes;
	}

	public void setPneumoNotes(String pneumoNotes) {
		this.pneumoNotes = pneumoNotes;
	}

	public String getPrevProvisionalNotesJaundice() {
		return prevProvisionalNotesJaundice;
	}

	public void setPrevProvisionalNotesJaundice(String prevProvisionalNotesJaundice) {
		this.prevProvisionalNotesJaundice = prevProvisionalNotesJaundice;
	}

	public String getProvisionalNotesJaundice() {
		return provisionalNotesJaundice;
	}

	public void setProvisionalNotesJaundice(String provisionalNotesJaundice) {
		this.provisionalNotesJaundice = provisionalNotesJaundice;
	}

	public String getJaundice() {
		return this.jaundice;
	}

	public void setJaundice(String jaundice) {
		this.jaundice = jaundice;
	}

	public String getPrevProvisionalNotesAsphyxia() {
		return prevProvisionalNotesAsphyxia;
	}

	public void setPrevProvisionalNotesAsphyxia(String prevProvisionalNotesAsphyxia) {
		this.prevProvisionalNotesAsphyxia = prevProvisionalNotesAsphyxia;
	}

	public String getProvisionalNotesAsphyxia() {
		return provisionalNotesAsphyxia;
	}

	public void setProvisionalNotesAsphyxia(String provisionalNotesAsphyxia) {
		this.provisionalNotesAsphyxia = provisionalNotesAsphyxia;
	}

	public String getAsphyxiaNotes() {
		return asphyxiaNotes;
	}

	public void setAsphyxiaNotes(String asphyxiaNotes) {
		this.asphyxiaNotes = asphyxiaNotes;
	}

	public String getPrevProvisionalNotesSeizure() {
		return prevProvisionalNotesSeizure;
	}

	public void setPrevProvisionalNotesSeizure(String prevProvisionalNotesSeizure) {
		this.prevProvisionalNotesSeizure = prevProvisionalNotesSeizure;
	}

	public String getProvisionalNotesSeizure() {
		return provisionalNotesSeizure;
	}

	public void setProvisionalNotesSeizure(String provisionalNotesSeizure) {
		this.provisionalNotesSeizure = provisionalNotesSeizure;
	}

	public String getSeizureNotes() {
		return seizureNotes;
	}

	public void setSeizureNotes(String seizureNotes) {
		this.seizureNotes = seizureNotes;
	}

	public String getPrevProvisionalNotesSepsis() {
		return prevProvisionalNotesSepsis;
	}

	public void setPrevProvisionalNotesSepsis(String prevProvisionalNotesSepsis) {
		this.prevProvisionalNotesSepsis = prevProvisionalNotesSepsis;
	}

	public String getProvisionalNotesSepsis() {
		return provisionalNotesSepsis;
	}

	public void setProvisionalNotesSepsis(String provisionalNotesSepsis) {
		this.provisionalNotesSepsis = provisionalNotesSepsis;
	}

	public String getSepsisNotes() {
		return sepsisNotes;
	}

	public void setSepsisNotes(String sepsisNotes) {
		this.sepsisNotes = sepsisNotes;
	}

	public String getPrevProvisionalNotesMiscellaneous() {
		return prevProvisionalNotesMiscellaneous;
	}

	public void setPrevProvisionalNotesMiscellaneous(String prevProvisionalNotesMiscellaneous) {
		this.prevProvisionalNotesMiscellaneous = prevProvisionalNotesMiscellaneous;
	}

	public String getProvisionalNotesMiscellaneous() {
		return provisionalNotesMiscellaneous;
	}

	public void setProvisionalNotesMiscellaneous(String provisionalNotesMiscellaneous) {
		this.provisionalNotesMiscellaneous = provisionalNotesMiscellaneous;
	}

	public String getMiscellaneousNotes() {
		return miscellaneousNotes;
	}

	public void setMiscellaneousNotes(String miscellaneousNotes) {
		this.miscellaneousNotes = miscellaneousNotes;
	}

	public String getCurrTreatment() {
		return currTreatment;
	}

	public void setCurrTreatment(String currTreatment) {
		this.currTreatment = currTreatment;
	}

	public String getTreatment() {
		return treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}

	public String getCurrScreening() {
		return currScreening;
	}

	public void setCurrScreening(String currScreening) {
		this.currScreening = currScreening;
	}

	public String getScreening() {
		return screening;
	}

	public void setScreening(String screening) {
		this.screening = screening;
	}

	public String getCurrProcedure() {
		return currProcedure;
	}

	public void setCurrProcedure(String currProcedure) {
		this.currProcedure = currProcedure;
	}

	public String getProcedure() {
		return procedure;
	}

	public void setProcedure(String procedure) {
		this.procedure = procedure;
	}

	public String getCurrBloodProduct() {
		return currBloodProduct;
	}

	public void setCurrBloodProduct(String currBloodProduct) {
		this.currBloodProduct = currBloodProduct;
	}

	public String getBloodProduct() {
		return bloodProduct;
	}

	public void setBloodProduct(String bloodProduct) {
		this.bloodProduct = bloodProduct;
	}

	public String getCurrNutrition() {
		return currNutrition;
	}

	public void setCurrNutrition(String currNutrition) {
		this.currNutrition = currNutrition;
	}

	public String getNutrition() {
		return nutrition;
	}

	public void setNutrition(String nutrition) {
		this.nutrition = nutrition;
	}

	public String getCurrVaccination() {
		return currVaccination;
	}

	public void setCurrVaccination(String currVaccination) {
		this.currVaccination = currVaccination;
	}

	public String getVaccination() {
		return vaccination;
	}

	public void setVaccination(String vaccination) {
		this.vaccination = vaccination;
	}

	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

	public Boolean getIsAntenatalHistory() {
		return isAntenatalHistory;
	}

	public void setIsAntenatalHistory(Boolean isAntenatalHistory) {
		this.isAntenatalHistory = isAntenatalHistory;
	}

	public Boolean getIsBirthDetails() {
		return isBirthDetails;
	}

	public void setIsBirthDetails(Boolean isBirthDetails) {
		this.isBirthDetails = isBirthDetails;
	}

	public Boolean getIsStatusAtAdmission() {
		return isStatusAtAdmission;
	}

	public void setIsStatusAtAdmission(Boolean isStatusAtAdmission) {
		this.isStatusAtAdmission = isStatusAtAdmission;
	}

	public Boolean getIsGrowth() {
		return isGrowth;
	}

	public void setIsGrowth(Boolean isGrowth) {
		this.isGrowth = isGrowth;
	}

	public Boolean getIsStableNotes() {
		return isStableNotes;
	}

	public void setIsStableNotes(Boolean isStableNotes) {
		this.isStableNotes = isStableNotes;
	}

	public Boolean getIsRdsNotes() {
		return isRdsNotes;
	}

	public void setIsRdsNotes(Boolean isRdsNotes) {
		this.isRdsNotes = isRdsNotes;
	}

	public Boolean getIsApneaNotes() {
		return isApneaNotes;
	}

	public void setIsApneaNotes(Boolean isApneaNotes) {
		this.isApneaNotes = isApneaNotes;
	}

	public Boolean getIsPphnNotes() {
		return isPphnNotes;
	}

	public void setIsPphnNotes(Boolean isPphnNotes) {
		this.isPphnNotes = isPphnNotes;
	}

	public Boolean getIsPneumoNotes() {
		return isPneumoNotes;
	}

	public void setIsPneumoNotes(Boolean isPneumoNotes) {
		this.isPneumoNotes = isPneumoNotes;
	}

	public Boolean getIsJaundice() {
		return isJaundice;
	}

	public void setIsJaundice(Boolean isJaundice) {
		this.isJaundice = isJaundice;
	}

	public Boolean getIsAsphyxiaNotes() {
		return isAsphyxiaNotes;
	}

	public void setIsAsphyxiaNotes(Boolean isAsphyxiaNotes) {
		this.isAsphyxiaNotes = isAsphyxiaNotes;
	}

	public Boolean getIsSeizureNotes() {
		return isSeizureNotes;
	}

	public void setIsSeizureNotes(Boolean isSeizureNotes) {
		this.isSeizureNotes = isSeizureNotes;
	}

	public Boolean getIsSepsisNotes() {
		return isSepsisNotes;
	}

	public void setIsSepsisNotes(Boolean isSepsisNotes) {
		this.isSepsisNotes = isSepsisNotes;
	}

	public Boolean getIsMiscellaneousNotes() {
		return isMiscellaneousNotes;
	}

	public void setIsMiscellaneousNotes(Boolean isMiscellaneousNotes) {
		this.isMiscellaneousNotes = isMiscellaneousNotes;
	}

	public Boolean getIsTreatment() {
		return isTreatment;
	}

	public void setIsTreatment(Boolean isTreatment) {
		this.isTreatment = isTreatment;
	}

	public Boolean getIsBloodProduct() {
		return isBloodProduct;
	}

	public void setIsBloodProduct(Boolean isBloodProduct) {
		this.isBloodProduct = isBloodProduct;
	}

	public Boolean getIsProcedure() {
		return isProcedure;
	}

	public void setIsProcedure(Boolean isProcedure) {
		this.isProcedure = isProcedure;
	}

	public Boolean getIsScreening() {
		return isScreening;
	}

	public void setIsScreening(Boolean isScreening) {
		this.isScreening = isScreening;
	}

	public Boolean getIsNutrition() {
		return isNutrition;
	}

	public void setIsNutrition(Boolean isNutrition) {
		this.isNutrition = isNutrition;
	}

	public Boolean getIsVaccination() {
		return isVaccination;
	}

	public void setIsVaccination(Boolean isVaccination) {
		this.isVaccination = isVaccination;
	}

	public String getPrevProvisionalNotesFeedIntolerance() {
		return prevProvisionalNotesFeedIntolerance;
	}

	public void setPrevProvisionalNotesFeedIntolerance(String prevProvisionalNotesFeedIntolerance) {
		this.prevProvisionalNotesFeedIntolerance = prevProvisionalNotesFeedIntolerance;
	}

	public String getFeedIntolerance() {
		return feedintolerance;
	}

	public void setFeedIntolerance(String feedIntolerance) {
		this.feedintolerance = feedIntolerance;
	}

	public String getProvisionalNotesFeedIntolerance() {
		return provisionalNotesFeedIntolerance;
	}

	public void setProvisionalNotesFeedIntolerance(String provisionalNotesFeedIntolerance) {
		this.provisionalNotesFeedIntolerance = provisionalNotesFeedIntolerance;
	}

	public Boolean getIsFeedIntolerance() {
		return isFeedIntolerance;
	}

	public void setIsFeedIntolerance(Boolean isFeedIntolerance) {
		this.isFeedIntolerance = isFeedIntolerance;
	}

	public String getFeedintolerance() {
		return feedintolerance;
	}

	public void setFeedintolerance(String feedintolerance) {
		this.feedintolerance = feedintolerance;
	}

	public Boolean getIsVapNotes() {
		return isVapNotes;
	}

	public void setIsVapNotes(Boolean isVapNotes) {
		this.isVapNotes = isVapNotes;
	}

	public Boolean getIsClabsiNotes() {
		return isClabsiNotes;
	}

	public void setIsClabsiNotes(Boolean isClabsiNotes) {
		this.isClabsiNotes = isClabsiNotes;
	}

	public Boolean getIsIntrauterineNotes() {
		return isIntrauterineNotes;
	}

	public void setIsIntrauterineNotes(Boolean isIntrauterineNotes) {
		this.isIntrauterineNotes = isIntrauterineNotes;
	}

	public Boolean getIsEncephalopathyNotes() {
		return isEncephalopathyNotes;
	}

	public void setIsEncephalopathyNotes(Boolean isEncephalopathyNotes) {
		this.isEncephalopathyNotes = isEncephalopathyNotes;
	}

	public Boolean getIsNeuromuscularDisorderNotes() {
		return isNeuromuscularDisorderNotes;
	}

	public void setIsNeuromuscularDisorderNotes(Boolean isNeuromuscularDisorderNotes) {
		this.isNeuromuscularDisorderNotes = isNeuromuscularDisorderNotes;
	}

	public Boolean getIsIVHNotes() {
		return isIVHNotes;
	}

	public void setIsIVHNotes(Boolean isIVHNotes) {
		this.isIVHNotes = isIVHNotes;
	}

	public Boolean getIsHydrocephalusNotes() {
		return isHydrocephalusNotes;
	}

	public void setIsHydrocephalusNotes(Boolean isHydrocephalusNotes) {
		this.isHydrocephalusNotes = isHydrocephalusNotes;
	}

	public Boolean getIsRenalNotes() {
		return isRenalNotes;
	}

	public void setIsRenalNotes(Boolean isRenalNotes) {
		this.isRenalNotes = isRenalNotes;
	}

	public Boolean getIsHypoglycemiaNotes() {
		return isHypoglycemiaNotes;
	}

	public void setIsHypoglycemiaNotes(Boolean isHypoglycemiaNotes) {
		this.isHypoglycemiaNotes = isHypoglycemiaNotes;
	}

	public String getPrevProvisionalNotesvap() {
		return prevProvisionalNotesvap;
	}

	public void setPrevProvisionalNotesvap(String prevProvisionalNotesvap) {
		this.prevProvisionalNotesvap = prevProvisionalNotesvap;
	}

	public String getProvisionalNotesVap() {
		return provisionalNotesVap;
	}

	public void setProvisionalNotesVap(String provisionalNotesVap) {
		this.provisionalNotesVap = provisionalNotesVap;
	}

	public String getVapNotes() {
		return vapNotes;
	}

	public void setVapNotes(String vapNotes) {
		this.vapNotes = vapNotes;
	}

	public String getPrevProvisionalNotesClabsi() {
		return prevProvisionalNotesClabsi;
	}

	public void setPrevProvisionalNotesClabsi(String prevProvisionalNotesClabsi) {
		this.prevProvisionalNotesClabsi = prevProvisionalNotesClabsi;
	}

	public String getProvisionalNotesClabsi() {
		return provisionalNotesClabsi;
	}

	public void setProvisionalNotesClabsi(String provisionalNotesClabsi) {
		this.provisionalNotesClabsi = provisionalNotesClabsi;
	}

	public String getClabsiNotes() {
		return clabsiNotes;
	}

	public void setClabsiNotes(String clabsiNotes) {
		this.clabsiNotes = clabsiNotes;
	}

	public String getPrevProvisionalNotesIntrauterine() {
		return prevProvisionalNotesIntrauterine;
	}

	public void setPrevProvisionalNotesIntrauterine(String prevProvisionalNotesIntrauterine) {
		this.prevProvisionalNotesIntrauterine = prevProvisionalNotesIntrauterine;
	}

	public String getProvisionalNotesIntrauterine() {
		return provisionalNotesIntrauterine;
	}

	public void setProvisionalNotesIntrauterine(String provisionalNotesIntrauterine) {
		this.provisionalNotesIntrauterine = provisionalNotesIntrauterine;
	}

	public String getIntrauterineNotes() {
		return intrauterineNotes;
	}

	public void setIntrauterineNotes(String intrauterineNotes) {
		this.intrauterineNotes = intrauterineNotes;
	}

	public String getPrevProvisionalNotesHypoglycemia() {
		return prevProvisionalNotesHypoglycemia;
	}

	public void setPrevProvisionalNotesHypoglycemia(String prevProvisionalNotesHypoglycemia) {
		this.prevProvisionalNotesHypoglycemia = prevProvisionalNotesHypoglycemia;
	}

	public String getProvisionalNoteshypoglycemia() {
		return provisionalNoteshypoglycemia;
	}

	public void setProvisionalNoteshypoglycemia(String provisionalNoteshypoglycemia) {
		this.provisionalNoteshypoglycemia = provisionalNoteshypoglycemia;
	}

	public String getHypoglycemiaNotes() {
		return hypoglycemiaNotes;
	}

	public void setHypoglycemiaNotes(String hypoglycemiaNotes) {
		this.hypoglycemiaNotes = hypoglycemiaNotes;
	}

	public String getPrevProvisionalNotesRenal() {
		return prevProvisionalNotesRenal;
	}

	public void setPrevProvisionalNotesRenal(String prevProvisionalNotesRenal) {
		this.prevProvisionalNotesRenal = prevProvisionalNotesRenal;
	}

	public String getProvisionalNotesRenal() {
		return provisionalNotesRenal;
	}

	public void setProvisionalNotesRenal(String provisionalNotesRenal) {
		this.provisionalNotesRenal = provisionalNotesRenal;
	}

	public String getRenalNotes() {
		return renalNotes;
	}

	public void setRenalNotes(String renalNotes) {
		this.renalNotes = renalNotes;
	}

	public String getPrevProvisionalNotesEncephalopathy() {
		return prevProvisionalNotesEncephalopathy;
	}

	public void setPrevProvisionalNotesEncephalopathy(String prevProvisionalNotesEncephalopathy) {
		this.prevProvisionalNotesEncephalopathy = prevProvisionalNotesEncephalopathy;
	}

	public String getProvisionalNotesEncephalopathy() {
		return provisionalNotesEncephalopathy;
	}

	public void setProvisionalNotesEncephalopathy(String provisionalNotesEncephalopathy) {
		this.provisionalNotesEncephalopathy = provisionalNotesEncephalopathy;
	}

	public String getEncephalopathyNotes() {
		return encephalopathyNotes;
	}

	public void setEncephalopathyNotes(String encephalopathyNotes) {
		this.encephalopathyNotes = encephalopathyNotes;
	}

	public String getPrevProvisionalNotesNeuromuscularDisorder() {
		return prevProvisionalNotesNeuromuscularDisorder;
	}

	public void setPrevProvisionalNotesNeuromuscularDisorder(String prevProvisionalNotesNeuromuscularDisorder) {
		this.prevProvisionalNotesNeuromuscularDisorder = prevProvisionalNotesNeuromuscularDisorder;
	}

	public String getProvisionalNotesNeuromuscularDisorder() {
		return provisionalNotesNeuromuscularDisorder;
	}

	public void setProvisionalNotesNeuromuscularDisorder(String provisionalNotesNeuromuscularDisorder) {
		this.provisionalNotesNeuromuscularDisorder = provisionalNotesNeuromuscularDisorder;
	}

	
	public String getNeuromuscularDisorderNotes() {
		return neuromuscularDisorderNotes;
	}

	public void setNeuromuscularDisorderNotes(String neuromuscularDisorderNotes) {
		this.neuromuscularDisorderNotes = neuromuscularDisorderNotes;
	}

	public String getPrevProvisionalNotesIvh() {
		return prevProvisionalNotesIvh;
	}

	public void setPrevProvisionalNotesIvh(String prevProvisionalNotesIvh) {
		this.prevProvisionalNotesIvh = prevProvisionalNotesIvh;
	}

	public String getProvisionalNotesIvh() {
		return provisionalNotesIvh;
	}

	public void setProvisionalNotesIvh(String provisionalNotesIvh) {
		this.provisionalNotesIvh = provisionalNotesIvh;
	}

	public String getIvhNotes() {
		return ivhNotes;
	}

	public void setIvhNotes(String ivhNotes) {
		this.ivhNotes = ivhNotes;
	}

	public String getPrevProvisionalNotesHydrocephalus() {
		return prevProvisionalNotesHydrocephalus;
	}

	public void setPrevProvisionalNotesHydrocephalus(String prevProvisionalNotesHydrocephalus) {
		this.prevProvisionalNotesHydrocephalus = prevProvisionalNotesHydrocephalus;
	}

	public String getProvisionalNotesHydrocephalus() {
		return provisionalNotesHydrocephalus;
	}

	public void setProvisionalNotesHydrocephalus(String provisionalNotesHydrocephalus) {
		this.provisionalNotesHydrocephalus = provisionalNotesHydrocephalus;
	}

	public String getHydrocephalusNotes() {
		return hydrocephalusNotes;
	}

	public void setHydrocephalusNotes(String hydrocephalusNotes) {
		this.hydrocephalusNotes = hydrocephalusNotes;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public String getFollowUpNote() {
		return followUpNote;
	}

	public void setFollowUpNote(String followUpNote) {
		this.followUpNote = followUpNote;
	}

	public String getPrevProvisionalNotesShock() {
		return prevProvisionalNotesShock;
	}

	public void setPrevProvisionalNotesShock(String prevProvisionalNotesShock) {
		this.prevProvisionalNotesShock = prevProvisionalNotesShock;
	}

	public String getShock() {
		return shock;
	}

	public void setShock(String shock) {
		this.shock = shock;
	}

	public String getProvisionalNotesShock() {
		return provisionalNotesShock;
	}

	public void setProvisionalNotesShock(String provisionalNotesShock) {
		this.provisionalNotesShock = provisionalNotesShock;
	}

	public Boolean getIsShock() {
		return isShock;
	}

	public void setIsShock(Boolean isShock) {
		this.isShock = isShock;
	}	
	
}
