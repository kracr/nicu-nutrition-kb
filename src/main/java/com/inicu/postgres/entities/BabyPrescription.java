package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.inicu.models.KeyValueObj;
import com.inicu.models.TimeObj;

/**
 * The persistent class for the baby_prescription database table.
 * 
 */
@Entity
@Table(name = "baby_prescription")
@NamedQuery(name = "BabyPrescription.findAll", query = "SELECT b FROM BabyPrescription b")
public class BabyPrescription implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "baby_presid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long babyPresid;
	
	@Column(name = "ref_baby_presid")
	private Long refBabyPresid;

	private String medid;

	private String comments;

	private Timestamp creationtime;

	@Column(columnDefinition = "Float4")
	private Float dose;
	
	@Column(columnDefinition = "Float4")
	private Float topical_drops;
	
	private String topical_frequency;

	private Timestamp enddate;

	private String frequency;

	private String medicationtype;

	private String medicinename;

	private Timestamp modificationtime;

	private String route;
	
	private String reason;

	private Timestamp startdate;
	
	private Timestamp medicineOrderDate;

	private String starttime;

	@Transient
	private TimeObj startTimeObject;

	private String uhid;
	private String loggeduser;

	@Column(columnDefinition = "bool")
	private Boolean isactive;
	
	@Column(columnDefinition = "bool")
	private Boolean isEdit;
	
	@Column(columnDefinition = "bool")
	private Boolean isContinueMedication;

	private String calculateddose;

	@Transient
	private String dilusion;

	@Transient
	private String frequencyInt;

	@Transient
	private String medicationTypeStr;

	@Transient
	private String medicationFreqStr;

	private String eventid;

	private String eventname;

	@Transient
	private Boolean isInEditMode;

	@Transient
	private TimeObj stopTime;
	
	@Transient
	private Integer medicineDay;

	@Column(columnDefinition = "bool")
	private Boolean bolus;

	private String freq_type;

	private String dilution_type;

	@Column(columnDefinition = "float4")
	private Float dilution_volume;

	@Column(columnDefinition = "float4")
	private Float rate;

	private String po_type;

	private String dose_unit;

	private String dose_unit_time;

	@Column(columnDefinition = "float4")
	private Float drug_strength;

	@Column(columnDefinition = "float4")
	private Float med_quantity;

	@Column(columnDefinition = "float4")
	private Float inf_volume;

	private String inf_mode;

	private Integer inf_time;

	@Column(columnDefinition = "float4")
	private Float overfill_volume;

	@Column(columnDefinition = "float4")
	private Float overfill_factor;

	@Column(columnDefinition = "float4")
	private Float actual_inf_volume;

	@Column(columnDefinition = "float4")
	private Float actual_med_volume;

	@Column(columnDefinition = "float4")
	private Float actual_dil_volume;

	@Column(columnDefinition = "float4")
	private Float actual_dose_volume;

	@Column(columnDefinition = "float4")
	private Float patient_receive;

	@Column(columnDefinition = "float4")
	private Float cal_dose_volume;

	private String instruction;

	private Timestamp due_date;
	
	private String sachets;
	
	@Column(columnDefinition = "Float4")
	private Float volume;
	
	@Column(name = "volume_meq",columnDefinition = "Float4")
	private Float volumeMeq;
	
	@Column(name = "feed_per_ml",columnDefinition = "Float4")
	private Float feedPerMl;
	
	private String quantity;
	
	@Column(columnDefinition = "bool", name = "is_continue")
	private Boolean isContinue;
	
	@Column(name = "continue_reason")
	private String continueReason;
	
	private String indication;

	@Transient
	private Boolean isStopped;

	// column added by umesh
	private String isAntibioticGiven;

	@Transient
	List<KeyValueObj> signatureImage;
	
	@Transient
	private Boolean isIntermediateConinue;

	@Transient
	private boolean isMedicationExecuted;
	
	@Column(columnDefinition = "float4")
	private Float dopamine_volume;
	
	@Column(columnDefinition = "float4")
	private Float dobutamine_volume;

	public BabyPrescription() {
		this.bolus = false;
		this.isInEditMode = false;
		this.dose_unit = "mg/kg";
		this.dose_unit_time = "day";
		this.overfill_volume = 0f;
		this.stopTime = new TimeObj();
		this.isMedicationExecuted=false;
	}

	public String getIsAntibioticGiven() {
		return isAntibioticGiven;
	}

	public void setIsAntibioticGiven(String isAntibioticGiven) {
		this.isAntibioticGiven = isAntibioticGiven;
	}

	public Long getBabyPresid() {
		return this.babyPresid;
	}

	public void setBabyPresid(Long babyPresid) {
		this.babyPresid = babyPresid;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public Float getDose() {
		return this.dose;
	}

	public void setDose(Float dose) {
		this.dose = dose;
	}

	public Timestamp getEnddate() {
		return this.enddate;
	}

	public void setEnddate(Timestamp enddate) {
		this.enddate = enddate;
	}

	public String getFrequency() {
		return this.frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getMedicationtype() {
		return this.medicationtype;
	}

	public void setMedicationtype(String medicationtype) {
		this.medicationtype = medicationtype;
	}

	public String getMedicinename() {
		return this.medicinename;
	}

	public void setMedicinename(String medicinename) {
		this.medicinename = medicinename;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getRoute() {
		return this.route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public Timestamp getStartdate() {
		return this.startdate;
	}

	public void setStartdate(Timestamp startdate) {
		this.startdate = startdate;
	}

	public String getStarttime() {
		return this.starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getUhid() {
		return this.uhid;
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

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	public String getDilusion() {
		return dilusion;
	}

	public void setDilusion(String dilusion) {
		this.dilusion = dilusion;
	}

	public String getFrequencyInt() {
		return frequencyInt;
	}

	public void setFrequencyInt(String frequencyInt) {
		this.frequencyInt = frequencyInt;
	}

	public String getCalculateddose() {
		return calculateddose;
	}

	public void setCalculateddose(String calculateddose) {
		this.calculateddose = calculateddose;
	}

	public String getMedicationTypeStr() {
		return medicationTypeStr;
	}

	public void setMedicationTypeStr(String medicationTypeStr) {
		this.medicationTypeStr = medicationTypeStr;
	}

	public String getMedicationFreqStr() {
		return medicationFreqStr;
	}

	public void setMedicationFreqStr(String medicationFreqStr) {
		this.medicationFreqStr = medicationFreqStr;
	}

	public String getEventid() {
		return eventid;
	}

	public void setEventid(String eventid) {
		this.eventid = eventid;
	}

	public String getEventname() {
		return eventname;
	}

	public void setEventname(String eventname) {
		this.eventname = eventname;
	}

	public TimeObj getStartTimeObject() {
		return startTimeObject;
	}

	public void setStartTimeObject(TimeObj startTimeObject) {
		this.startTimeObject = startTimeObject;
	}

	public Boolean getIsInEditMode() {
		return isInEditMode;
	}

	public void setIsInEditMode(Boolean isInEditMode) {
		this.isInEditMode = isInEditMode;
	}

	public TimeObj getStopTime() {
		return stopTime;
	}

	public void setStopTime(TimeObj stopTime) {
		this.stopTime = stopTime;
	}

	public Boolean getBolus() {
		return bolus;
	}

	public void setBolus(Boolean bolus) {
		this.bolus = bolus;
	}

	public String getFreq_type() {
		return freq_type;
	}

	public void setFreq_type(String freq_type) {
		this.freq_type = freq_type;
	}

	public String getDilution_type() {
		return dilution_type;
	}

	public void setDilution_type(String dilution_type) {
		this.dilution_type = dilution_type;
	}

	public Float getDilution_volume() {
		return dilution_volume;
	}

	public void setDilution_volume(Float dilution_volume) {
		this.dilution_volume = dilution_volume;
	}

	public Float getRate() {
		return rate;
	}

	public void setRate(Float rate) {
		this.rate = rate;
	}

	public String getMedid() {
		return medid;
	}

	public void setMedid(String medid) {
		this.medid = medid;
	}

	public String getPo_type() {
		return po_type;
	}

	public void setPo_type(String po_type) {
		this.po_type = po_type;
	}

	public String getDose_unit() {
		return dose_unit;
	}

	public void setDose_unit(String dose_unit) {
		this.dose_unit = dose_unit;
	}

	public String getDose_unit_time() {
		return dose_unit_time;
	}

	public void setDose_unit_time(String dose_unit_time) {
		this.dose_unit_time = dose_unit_time;
	}

	public Float getDrug_strength() {
		return drug_strength;
	}

	public void setDrug_strength(Float drug_strength) {
		this.drug_strength = drug_strength;
	}

	public Float getMed_quantity() {
		return med_quantity;
	}

	public void setMed_quantity(Float med_quantity) {
		this.med_quantity = med_quantity;
	}

	public Float getInf_volume() {
		return inf_volume;
	}

	public void setInf_volume(Float inf_volume) {
		this.inf_volume = inf_volume;
	}

	public String getInf_mode() {
		return inf_mode;
	}

	public void setInf_mode(String inf_mode) {
		this.inf_mode = inf_mode;
	}

	public Integer getInf_time() {
		return inf_time;
	}

	public void setInf_time(Integer inf_time) {
		this.inf_time = inf_time;
	}

	public Float getOverfill_volume() {
		return overfill_volume;
	}

	public void setOverfill_volume(Float overfill_volume) {
		this.overfill_volume = overfill_volume;
	}

	public Float getOverfill_factor() {
		return overfill_factor;
	}

	public void setOverfill_factor(Float overfill_factor) {
		this.overfill_factor = overfill_factor;
	}

	public Float getActual_inf_volume() {
		return actual_inf_volume;
	}

	public void setActual_inf_volume(Float actual_inf_volume) {
		this.actual_inf_volume = actual_inf_volume;
	}

	public Float getActual_med_volume() {
		return actual_med_volume;
	}

	public void setActual_med_volume(Float actual_med_volume) {
		this.actual_med_volume = actual_med_volume;
	}

	public Float getActual_dil_volume() {
		return actual_dil_volume;
	}

	public void setActual_dil_volume(Float actual_dil_volume) {
		this.actual_dil_volume = actual_dil_volume;
	}

	public Float getActual_dose_volume() {
		return actual_dose_volume;
	}

	public void setActual_dose_volume(Float actual_dose_volume) {
		this.actual_dose_volume = actual_dose_volume;
	}

	public Float getPatient_receive() {
		return patient_receive;
	}

	public void setPatient_receive(Float patient_receive) {
		this.patient_receive = patient_receive;
	}

	public Float getCal_dose_volume() {
		return cal_dose_volume;
	}

	public void setCal_dose_volume(Float cal_dose_volume) {
		this.cal_dose_volume = cal_dose_volume;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public Timestamp getDue_date() {
		return due_date;
	}

	public void setDue_date(Timestamp due_date) {
		this.due_date = due_date;
	}

	public Float getTopical_drops() {
		return topical_drops;
	}

	public void setTopical_drops(Float topical_drops) {
		this.topical_drops = topical_drops;
	}

	public String getTopical_frequency() {
		return topical_frequency;
	}

	public void setTopical_frequency(String topical_frequency) {
		this.topical_frequency = topical_frequency;
	}

	public String getSachets() {
		return sachets;
	}

	public void setSachets(String sachets) {
		this.sachets = sachets;
	}

	public Float getVolume() {
		return volume;
	}

	public void setVolume(Float volume) {
		this.volume = volume;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public Boolean getIsContinue() {
		return isContinue;
	}

	public void setIsContinue(Boolean isContinue) {
		this.isContinue = isContinue;
	}

	public String getContinueReason() {
		return continueReason;
	}

	public void setContinueReason(String continueReason) {
		this.continueReason = continueReason;
	}

	public Integer getMedicineDay() {
		return medicineDay;
	}

	public void setMedicineDay(Integer medicineDay) {
		this.medicineDay = medicineDay;
	}

	public Long getRefBabyPresid() {
		return refBabyPresid;
	}

	public void setRefBabyPresid(Long refBabyPresid) {
		this.refBabyPresid = refBabyPresid;
	}

	public Timestamp getMedicineOrderDate() {
		return medicineOrderDate;
	}

	public void setMedicineOrderDate(Timestamp medicineOrderDate) {
		this.medicineOrderDate = medicineOrderDate;
	}

	public Boolean getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(Boolean isEdit) {
		this.isEdit = isEdit;
	}

	public Boolean getIsContinueMedication() {
		return isContinueMedication;
	}

	public void setIsContinueMedication(Boolean isContinueMedication) {
		this.isContinueMedication = isContinueMedication;
	}

	public String getIndication() {
		return indication;
	}

	public void setIndication(String indication) {
		this.indication = indication;
	}

	public Boolean getIsStopped() {
		return isStopped;
	}

	public void setIsStopped(Boolean isStopped) {
		this.isStopped = isStopped;
	}

	public Float getVolumeMeq() {
		return volumeMeq;
	}

	public void setVolumeMeq(Float volumeMeq) {
		this.volumeMeq = volumeMeq;
	}

	public Float getFeedPerMl() {
		return feedPerMl;
	}

	public void setFeedPerMl(Float feedPerMl) {
		this.feedPerMl = feedPerMl;
	}

	public List<KeyValueObj> getSignatureImage() {
		return signatureImage;
	}

	public void setSignatureImage(List<KeyValueObj> signatureImage) {
		this.signatureImage = signatureImage;
	}

	public Boolean getIsIntermediateConinue() {
		return isIntermediateConinue;
	}

	public void setIsIntermediateConinue(Boolean isIntermediateConinue) {
		this.isIntermediateConinue = isIntermediateConinue;
	}

	public boolean isMedicationExecuted() {
		return isMedicationExecuted;
	}

	public void setMedicationExecuted(boolean medicationExecuted) {
		isMedicationExecuted = medicationExecuted;
	}

	public Float getDopamine_volume() {
		return dopamine_volume;
	}

	public void setDopamine_volume(Float dopamine_volume) {
		this.dopamine_volume = dopamine_volume;
	}

	public Float getDobutamine_volume() {
		return dobutamine_volume;
	}

	public void setDobutamine_volume(Float dobutamine_volume) {
		this.dobutamine_volume = dobutamine_volume;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public String toString() {
		return "BabyPrescription{" +
				"babyPresid=" + babyPresid +
				", refBabyPresid=" + refBabyPresid +
				", medid='" + medid + '\'' +
				", comments='" + comments + '\'' +
				", creationtime=" + creationtime +
				", dose=" + dose +
				", topical_drops=" + topical_drops +
				", topical_frequency='" + topical_frequency + '\'' +
				", enddate=" + enddate +
				", frequency='" + frequency + '\'' +
				", medicationtype='" + medicationtype + '\'' +
				", medicinename='" + medicinename + '\'' +
				", modificationtime=" + modificationtime +
				", route='" + route + '\'' +
				", startdate=" + startdate +
				", medicineOrderDate=" + medicineOrderDate +
				", starttime='" + starttime + '\'' +
				", startTimeObject=" + startTimeObject +
				", uhid='" + uhid + '\'' +
				", loggeduser='" + loggeduser + '\'' +
				", isactive=" + isactive +
				", isEdit=" + isEdit +
				", isContinueMedication=" + isContinueMedication +
				", calculateddose='" + calculateddose + '\'' +
				", dilusion='" + dilusion + '\'' +
				", frequencyInt='" + frequencyInt + '\'' +
				", medicationTypeStr='" + medicationTypeStr + '\'' +
				", medicationFreqStr='" + medicationFreqStr + '\'' +
				", eventid='" + eventid + '\'' +
				", eventname='" + eventname + '\'' +
				", isInEditMode=" + isInEditMode +
				", stopTime=" + stopTime +
				", medicineDay=" + medicineDay +
				", bolus=" + bolus +
				", freq_type='" + freq_type + '\'' +
				", dilution_type='" + dilution_type + '\'' +
				", dilution_volume=" + dilution_volume +
				", rate=" + rate +
				", po_type='" + po_type + '\'' +
				", dose_unit='" + dose_unit + '\'' +
				", dose_unit_time='" + dose_unit_time + '\'' +
				", drug_strength=" + drug_strength +
				", med_quantity=" + med_quantity +
				", inf_volume=" + inf_volume +
				", inf_mode='" + inf_mode + '\'' +
				", inf_time=" + inf_time +
				", overfill_volume=" + overfill_volume +
				", overfill_factor=" + overfill_factor +
				", actual_inf_volume=" + actual_inf_volume +
				", actual_med_volume=" + actual_med_volume +
				", actual_dil_volume=" + actual_dil_volume +
				", actual_dose_volume=" + actual_dose_volume +
				", patient_receive=" + patient_receive +
				", cal_dose_volume=" + cal_dose_volume +
				", instruction='" + instruction + '\'' +
				", due_date=" + due_date +
				", sachets='" + sachets + '\'' +
				", volume=" + volume +
				", volumeMeq=" + volumeMeq +
				", feedPerMl=" + feedPerMl +
				", quantity='" + quantity + '\'' +
				", isContinue=" + isContinue +
				", continueReason='" + continueReason + '\'' +
				", indication='" + indication + '\'' +
				", isStopped=" + isStopped +
				", isAntibioticGiven='" + isAntibioticGiven + '\'' +
				", signatureImage=" + signatureImage +
				", isIntermediateConinue=" + isIntermediateConinue +
				", isMedicationExecuted=" + isMedicationExecuted +
				'}';
	}
}
