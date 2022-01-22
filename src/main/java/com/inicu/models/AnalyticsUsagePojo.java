package com.inicu.models;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class AnalyticsUsagePojo {

	private String uhid;
	private String babyName;
	private String nicuLevel;
	private String admissionStatus;
	private Integer lengthOfStay;
	private String criticality;
	private String gender;
	private Float birthweight;
	private Float dischargebirthweight;
	private Integer gestationweekbylmp;
	private Integer gestationdaysbylmp;
	private Integer dischargegestationweekbylmp;
	private Integer dischargegestationdaysbylmp;


	private String inout_patient_status;




	private String dischargedDate;
	private String lastVisitDateDate;
	private String dateofbirth;
	private String dateofadmission;
	private String initialAssessment;

		private String initialAssessmentScore;


	private String initialMandatoryAssessment;
	private String initialEssentialAssessment;
	private Integer assessment;
	private Integer stableNotes;

	private String assessmentStr;
	private String stableNotesStr;

	private String medication;
	private String nutrition;
	private Integer labOrder;
	private Integer labReports;
	private Integer procedure;
	private String neonatologist;
	private Double doctor_adoptionScore;
	private Double doctor_qualityScore;

	private String anthropometry;
	private Integer height;
	private Integer head;
	private String vitals;
	private String ventilatorCount="0";
	private String intake_output;
	private Integer events;
	private Integer sample_sent;
	private Integer nursing_notes;
	private Double nurse_adoptionScore;
	private Double nurse_qualityScore;


	private Double babyStayDuringPeriod;

	private Double babyStayDuringPeriodInhrs;

	private String admissionweight;



	String reasonForAdmission = "0";
	String antenatalHistory = "0";


	String birthToNICU = "0";
	String statusAtAdmission = "0";
	String genPhysicalExam = "0";
	String systematicPhysicalExam = "0";


	String reasonForAdmissionField = "0";
	String gpalField = "0";
	String conceptionField = "0";
	String gestationByField = "0";

	String maternalMedicalDiseaseField = "0";
	String maternalInfectionsField = "0";
	String otherRiskField = "0";
	String antenatalSteroidField = "0";
	String labourField = "0";
	String presenationField = "0";
	String modeOfDeliveryField = "0";
	String birthToNICUField = "0";
	String birthWeightField = "0";
	String admissionWeightField = "0";
	String genPhysicalExamField = "0";
	String systematicPhysicalExamField = "0";

	private double fentonBirth;
	private double fentonDischarge;
	private double intergrowthBirth;
	private double intergrowthDischarge;
	private double zScoreBirth;
	private double zScoreDischarge;
	private String episodeid;
	private Timestamp hisdischargedate;
	private String hisdischargestatus;

	public String getAssessmentStr() {
		return assessmentStr;
	}

	public void setAssessmentStr(String assessmentStr) {
		this.assessmentStr = assessmentStr;
	}

	public String getStableNotesStr() {
		return stableNotesStr;
	}

	public void setStableNotesStr(String stableNotesStr) {
		this.stableNotesStr = stableNotesStr;
	}

	// Box name and Its Brand
	private String boxname;
	private String boxbrand;

	public AnalyticsUsagePojo(){
		this.assessmentStr="";
		this.stableNotesStr="";
		this.initialAssessment="0";
		this.doctor_adoptionScore=0.0;
		this.doctor_qualityScore=0.0;
		this.fentonBirth=0;
		this.fentonDischarge=0;
		this.intergrowthBirth=0;
		this.intergrowthDischarge=0;
		this.zScoreBirth=0;
		this.zScoreDischarge=0;
		this.assessment=0;
		this.stableNotes=0;
		this.medication="0";
		this.nutrition="0";
		this.labOrder=0;
		this.labReports=0;
		this.procedure=0;
		this.anthropometry="0";
		this.height=0;
		this.head=0;
		this.vitals="0";
		this.intake_output="0";
		this.events=0;
		this.sample_sent=0;
		this.nursing_notes=0;
		this.nurse_adoptionScore=0.0;
		this.nurse_qualityScore=0.0;
		this.boxname=null;
		this.boxbrand=null;
	}

	public String getBoxname() {
		return boxname;
	}

	public void setBoxname(String boxname) {
		this.boxname = boxname;
	}

	public String getBoxbrand() {
		return boxbrand;
	}

	public void setBoxbrand(String boxbrand) {
		this.boxbrand = boxbrand;
	}

	private HashMap<String, LinkedHashMap<Timestamp, Double>> usageGraphObj;


	public HashMap<String, LinkedHashMap<Timestamp, Double>> getUsageGraphObj() {
		return usageGraphObj;
	}

	public void setUsageGraphObj(HashMap<String, LinkedHashMap<Timestamp, Double>> usageGraphObj) {
		this.usageGraphObj = usageGraphObj;
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getBabyName() {
		return babyName;
	}

	public void setBabyName(String babyName) {
		this.babyName = babyName;
	}

	public String getNicuLevel() {
		return nicuLevel;
	}

	public void setNicuLevel(String nicuLevel) {
		this.nicuLevel = nicuLevel;
	}

	public String getAdmissionStatus() {
		return admissionStatus;
	}

	public void setAdmissionStatus(String admissionStatus) {
		this.admissionStatus = admissionStatus;
	}

	public Integer getLengthOfStay() {
		return lengthOfStay;
	}

	public void setLengthOfStay(Integer lengthOfStay) {
		this.lengthOfStay = lengthOfStay;
	}

	public String getCriticality() {
		return criticality;
	}

	public void setCriticality(String criticality) {
		this.criticality = criticality;
	}

	public String getInitialAssessment() {
		return initialAssessment;
	}

	public void setInitialAssessment(String initialAssessment) {
		this.initialAssessment = initialAssessment;
	}

	public Integer getAssessment() {
		return assessment;
	}

	public void setAssessment(Integer assessment) {
		this.assessment = assessment;
	}

	public Integer getStableNotes() {
		return stableNotes;
	}

	public void setStableNotes(Integer stableNotes) {
		this.stableNotes = stableNotes;
	}

	public String getMedication() {
		return medication;
	}

	public void setMedication(String medication) {
		this.medication = medication;
	}

	public String getNutrition() {
		return nutrition;
	}

	public void setNutrition(String nutrition) {
		this.nutrition = nutrition;
	}

	public Integer getLabOrder() {
		return labOrder;
	}

	public void setLabOrder(Integer labOrder) {
		this.labOrder = labOrder;
	}

	public Integer getProcedure() {
		return procedure;
	}

	public void setProcedure(Integer procedure) {
		this.procedure = procedure;
	}

	public String getNeonatologist() {
		return neonatologist;
	}

	public void setNeonatologist(String neonatologist) {
		this.neonatologist = neonatologist;
	}

	public Double getDoctor_adoptionScore() {
		return doctor_adoptionScore;
	}

	public void setDoctor_adoptionScore(Double doctor_adoptionScore) {
		this.doctor_adoptionScore = doctor_adoptionScore;
	}

	public Double getDoctor_qualityScore() {
		return doctor_qualityScore;
	}

	public void setDoctor_qualityScore(Double doctor_qualityScore) {
		this.doctor_qualityScore = doctor_qualityScore;
	}

	public String getAnthropometry() {
		return anthropometry;
	}

	public void setAnthropometry(String anthropometry) {
		this.anthropometry = anthropometry;
	}

	public String getVitals() {
		return vitals;
	}

	public void setVitals(String vitals) {
		this.vitals = vitals;
	}

	public String getIntake_output() {
		return intake_output;
	}

	public void setIntake_output(String intake_output) {
		this.intake_output = intake_output;
	}

	public Integer getEvents() {
		return events;
	}

	public void setEvents(Integer events) {
		this.events = events;
	}

	public Integer getSample_sent() {
		return sample_sent;
	}

	public void setSample_sent(Integer sample_sent) {
		this.sample_sent = sample_sent;
	}

	public Double getNurse_adoptionScore() {
		return nurse_adoptionScore;
	}

	public void setNurse_adoptionScore(Double nurse_adoptionScore) {
		this.nurse_adoptionScore = nurse_adoptionScore;
	}

	public Double getNurse_qualityScore() {
		return nurse_qualityScore;
	}

	public void setNurse_qualityScore(Double nurse_qualityScore) {
		this.nurse_qualityScore = nurse_qualityScore;
	}

	public String getEpisodeid() {
		return episodeid;
	}

	public void setEpisodeid(String episodeid) {
		this.episodeid = episodeid;
	}

	public String getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(String dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public String getDateofadmission() {
		return dateofadmission;
	}

	public void setDateofadmission(String dateofadmission) {
		this.dateofadmission = dateofadmission;
	}

	public Integer getLabReports() {
		return labReports;
	}

	public void setLabReports(Integer labReports) {
		this.labReports = labReports;
	}

	public Float getBirthweight() {
		return birthweight;
	}

	public void setBirthweight(Float birthweight) {
		this.birthweight = birthweight;
	}

	public Integer getGestationweekbylmp() {
		return gestationweekbylmp;
	}

	public void setGestationweekbylmp(Integer gestationweekbylmp) {
		this.gestationweekbylmp = gestationweekbylmp;
	}

	public Integer getGestationdaysbylmp() {
		return gestationdaysbylmp;
	}

	public void setGestationdaysbylmp(Integer gestationdaysbylmp) {
		this.gestationdaysbylmp = gestationdaysbylmp;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getHead() {
		return head;
	}

	public void setHead(Integer head) {
		this.head = head;
	}

	public double getFentonBirth() {
		return fentonBirth;
	}

	public void setFentonBirth(double fentonBirth) {
		this.fentonBirth = fentonBirth;
	}

	public double getFentonDischarge() {
		return fentonDischarge;
	}

	public void setFentonDischarge(double fentonDischarge) {
		this.fentonDischarge = fentonDischarge;
	}

	public double getIntergrowthBirth() {
		return intergrowthBirth;
	}

	public void setIntergrowthBirth(double intergrowthBirth) {
		this.intergrowthBirth = intergrowthBirth;
	}

	public double getIntergrowthDischarge() {
		return intergrowthDischarge;
	}

	public void setIntergrowthDischarge(double intergrowthDischarge) {
		this.intergrowthDischarge = intergrowthDischarge;
	}

	public Float getDischargebirthweight() {
		return dischargebirthweight;
	}

	public void setDischargebirthweight(Float dischargebirthweight) {
		this.dischargebirthweight = dischargebirthweight;
	}

	public Integer getDischargegestationweekbylmp() {
		return dischargegestationweekbylmp;
	}

	public void setDischargegestationweekbylmp(Integer dischargegestationweekbylmp) {
		this.dischargegestationweekbylmp = dischargegestationweekbylmp;
	}

	public Integer getDischargegestationdaysbylmp() {
		return dischargegestationdaysbylmp;
	}

	public void setDischargegestationdaysbylmp(Integer dischargegestationdaysbylmp) {
		this.dischargegestationdaysbylmp = dischargegestationdaysbylmp;
	}

	public String getDischargedDate() {
		return dischargedDate;
	}

	public void setDischargedDate(String dischargedDate) {
		this.dischargedDate = dischargedDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLastVisitDateDate() {
		return lastVisitDateDate;
	}

	public void setLastVisitDateDate(String lastVisitDateDate) {
		this.lastVisitDateDate = lastVisitDateDate;
	}

	public double getzScoreBirth() {
		return zScoreBirth;
	}

	public void setzScoreBirth(double zScoreBirth) {
		this.zScoreBirth = zScoreBirth;
	}

	public double getzScoreDischarge() {
		return zScoreDischarge;
	}

	public void setzScoreDischarge(double zScoreDischarge) {
		this.zScoreDischarge = zScoreDischarge;
	}

	public Integer getNursing_notes() {
		return nursing_notes;
	}

	public void setNursing_notes(Integer nursing_notes) {
		this.nursing_notes = nursing_notes;
	}

	public Timestamp getHisdischargedate() {
		return hisdischargedate;
	}

	public void setHisdischargedate(Timestamp hisdischargedate) {
		this.hisdischargedate = hisdischargedate;
	}

	public String getHisdischargestatus() {
		return hisdischargestatus;
	}

	public void setHisdischargestatus(String hisdischargestatus) {
		this.hisdischargestatus = hisdischargestatus;
	}

	public String getVentilatorCount() {
		return ventilatorCount;
	}

	public void setVentilatorCount(String ventilatorCount) {
		this.ventilatorCount = ventilatorCount;
	}

	public Double getBabyStayDuringPeriod() {
		return babyStayDuringPeriod;
	}

	public void setBabyStayDuringPeriod(double babyStayDuringPeriod) {
		this.babyStayDuringPeriod = babyStayDuringPeriod;
	}


	public Double getBabyStayDuringPeriodInhrs() {
		return babyStayDuringPeriodInhrs;
	}

	public void setBabyStayDuringPeriodInhrs(double babyStayDuringPeriodInhrs) {
		this.babyStayDuringPeriodInhrs = babyStayDuringPeriodInhrs;
	}


	public String getInitialMandatoryAssessment() {
		return initialMandatoryAssessment;
	}

	public void setInitialMandatoryAssessment(String initialMandatoryAssessment) {
		this.initialMandatoryAssessment = initialMandatoryAssessment;
	}

	public String getInitialEssentialAssessment() {
		return initialEssentialAssessment;
	}

	public void setInitialEssentialAssessment(String initialEssentialAssessment) {
		this.initialEssentialAssessment = initialEssentialAssessment;
	}


	public String getAdmissionweight() {
		return admissionweight;
	}

	public void setAdmissionweight(String admissionweight) {
		this.admissionweight = admissionweight;
	}

	public String getInitialAssessmentScore() {
		return initialAssessmentScore;
	}

	public void setInitialAssessmentScore(String initialAssessmentScore) {
		this.initialAssessmentScore = initialAssessmentScore;
	}

	public String getReasonForAdmission() {
		return reasonForAdmission;
	}

	public void setReasonForAdmission(String reasonForAdmission) {
		this.reasonForAdmission = reasonForAdmission;
	}

	public String getAntenatalHistory() {
		return antenatalHistory;
	}

	public void setAntenatalHistory(String antenatalHistory) {
		this.antenatalHistory = antenatalHistory;
	}

	public String getBirthToNICU() {
		return birthToNICU;
	}

	public void setBirthToNICU(String birthToNICU) {
		this.birthToNICU = birthToNICU;
	}

	public String getStatusAtAdmission() {
		return statusAtAdmission;
	}

	public void setStatusAtAdmission(String statusAtAdmission) {
		this.statusAtAdmission = statusAtAdmission;
	}

	public String getGenPhysicalExam() {
		return genPhysicalExam;
	}

	public void setGenPhysicalExam(String genPhysicalExam) {
		this.genPhysicalExam = genPhysicalExam;
	}

	public String getSystematicPhysicalExam() {
		return systematicPhysicalExam;
	}

	public void setSystematicPhysicalExam(String systematicPhysicalExam) {
		this.systematicPhysicalExam = systematicPhysicalExam;
	}


	public String getReasonForAdmissionField() {
		return reasonForAdmissionField;
	}

	public void setReasonForAdmissionField(String reasonForAdmissionField) {
		this.reasonForAdmissionField = reasonForAdmissionField;
	}

	public String getgpalField() {
		return gpalField;
	}

	public void setgpalField(String gpalField) {
		this.gpalField = gpalField;
	}

	public String getConceptionField() {
		return conceptionField;
	}

	public void setConceptionField(String conceptionField) {
		this.conceptionField = conceptionField;
	}

	public String getGestationByField() {
		return gestationByField;
	}

	public void setGestationByField(String gestationByField) {
		this.gestationByField = gestationByField;
	}

	public String getMaternalMedicalDiseaseField() {
		return maternalMedicalDiseaseField;
	}

	public void setMaternalMedicalDiseaseField(String maternalMedicalDiseaseField) {
		this.maternalMedicalDiseaseField = maternalMedicalDiseaseField;
	}

	public String getMaternalInfectionsField() {
		return maternalInfectionsField;
	}

	public void setMaternalInfectionsField(String maternalInfectionsField) {
		this.maternalInfectionsField = maternalInfectionsField;
	}

	public String getOtherRiskField() {
		return otherRiskField;
	}

	public void setOtherRiskField(String otherRiskField) {
		this.otherRiskField = otherRiskField;
	}

	public String getAntenatalSteroidField() {
		return antenatalSteroidField;
	}

	public void setAntenatalSteroidField(String antenatalSteroidField) {
		this.antenatalSteroidField = antenatalSteroidField;
	}

	public String getLabourField() {
		return labourField;
	}

	public void setLabourField(String labourField) {
		this.labourField = labourField;
	}

	public String getPresenationField() {
		return presenationField;
	}

	public void setPresenationField(String presenationField) {
		this.presenationField = presenationField;
	}

	public String getModeOfDeliveryField() {
		return modeOfDeliveryField;
	}

	public void setModeOfDeliveryField(String modeOfDeliveryField) {
		this.modeOfDeliveryField = modeOfDeliveryField;
	}

	public String getBirthToNICUField() {
		return birthToNICUField;
	}

	public void setBirthToNICUField(String birthToNICUField) {
		this.birthToNICUField = birthToNICUField;
	}

	public String getBirthWeightField() {
		return birthWeightField;
	}

	public void setBirthWeightField(String birthWeightField) {
		this.birthWeightField = birthWeightField;
	}

	public String getAdmissionWeightField() {
		return admissionWeightField;
	}

	public void setAdmissionWeightField(String admissionWeightField) {
		this.admissionWeightField = admissionWeightField;
	}

	public String getGenPhysicalExamField() {
		return genPhysicalExamField;
	}

	public void setGenPhysicalExamField(String genPhysicalExamField) {
		this.genPhysicalExamField = genPhysicalExamField;
	}

	public String getSystematicPhysicalExamField() {
		return systematicPhysicalExamField;
	}

	public void setSystematicPhysicalExamField(String systematicPhysicalExamField) {
		this.systematicPhysicalExamField = systematicPhysicalExamField;
	}

	public String getInout_patient_status() {
		return inout_patient_status;
	}

	public void setInout_patient_status(String inout_patient_status) {
		this.inout_patient_status = inout_patient_status;
	}

}
