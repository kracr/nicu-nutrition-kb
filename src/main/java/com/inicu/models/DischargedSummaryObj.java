package com.inicu.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.persistence.Transient;

import com.inicu.postgres.entities.*;
import com.inicu.postgres.utility.CalendarUtility;
import com.inicu.postgres.utility.HqlSqlQueryConstants;

public class DischargedSummaryObj {
	
	private Boolean isDischargeBaby;
	private String loggedUser;
	private String uhid;
	private Object babyStatus;
	private Object comments;
//	private List<DischargeAdviceDetail> dischargeAdviceTemplates;
	private BabyDetail babyDetails;
	private ParentDetail parenteDetail;
	private BabyVisit birthVisit;
	private BabyVisit admissionVisit;
	private BabyVisit dischargeVisit;
	
	private List<RefAdviceOnDischarge> adviceTemplate;
	
	private List<DischargeAdviceDetail> adviceOnDischarge;
	
	private List<DischargeAdviceDetail> pastadviceOnDischarge;

	private SystemicExaminationNotes systemicExaminationNotes;

	@Transient
	private String ongoingMedication;

	@Transient
	private String ongoingNutrition;

	@Transient
	private RespSupport ongoingRespSupport;

	private Timestamp edd;

	@Transient
	private Boolean diagnosisChanged;

//	@Transient
//	private Boolean adviceSelected;
	
	@Transient
	private Boolean doctorChanged;

	private String finalDiagnosis;
	DischargeNotesDetail dischargeNotes;
	private String whichPage = "";
	DischargeOutcome outcome;
	private String assessmentMessage = null;
	private List<BabyPrescription> prescriptionList;
	private String outcomeNote = null;
	
	@Transient
	private String dischargeMedicationList;
	
	@Transient
	List<TestResultsViewPOJO> testResult;
	
	@Transient
	GenPhyExam babyPhysicalExamData;
	
	@Transient
	String babySystemicExamData;
	
	@Transient
	String SystemicExamNotes;
	
	@Transient 
	BirthToNicu birthToNicuDetails;
	
	@Transient 
	AntenatalHistoryDetail babyAntenatalHistoryDetails;
	
	@Transient 
	List<ScreenRop> screenRop;
	
	@Transient 
	List<ScreenUSG> screenUsg;
	
	@Transient 
	List<ScreenNeurological> screenNeurological;
	
	@Transient 
	List<ScreenMetabolic> screenMetabolical;
	
	@Transient 
	List<ScreenHearing> screenHearing;
	
	@Transient 
	List<ScreenMiscellaneous> screenMisc;
	
	@Transient 
	String obstetricCondtiton;
	
	@Transient 
	String maternalInvestigation;
	
	@Transient
	RefHospitalbranchname hospitalDetails;

	@Transient
	String bpdRespiratoryNote;
	
	@Transient
	String icdCode;

	List<KeyValueObj> logoImage;
	
	@Transient
	List<String> activeDoctors;
	
	@Transient
	Boolean adviceOnDischargeStatus;


	public List<KeyValueObj> getLogoImage() {
		return logoImage;
	}

	public void setLogoImage(List<KeyValueObj> logoImage) {
		this.logoImage = logoImage;
	}

	public SystemicExaminationNotes getSystemicExaminationNotes() {
		return systemicExaminationNotes;
	}

	public void setSystemicExaminationNotes(SystemicExaminationNotes systemicExaminationNotes) {
		this.systemicExaminationNotes = systemicExaminationNotes;
	}

	public DischargedSummaryObj() {
		this.dischargeNotes = new DischargeNotesDetail();
		this.systemicExaminationNotes=new SystemicExaminationNotes();
		this.babyPhysicalExamData = new GenPhyExam();
		this.birthToNicuDetails = new BirthToNicu();
		this.loggedUser = "";
		this.babyStatus = "";
		this.diagnosisChanged=false;
		this.doctorChanged=false;
		this.adviceOnDischarge = new ArrayList<DischargeAdviceDetail>();
		this.adviceOnDischargeStatus = false;
//		this.adviceSelected=false;
		// removed stable status so that its not selected as default in basic discharge
		// summary.
		this.comments = "";}
	
//		this.dischargeAdviceTemplates = new ArrayList<DischargeAdviceDetail>() {
//			{
//				DischargeAdviceDetail template = null;
//				template = new DischargeAdviceDetail();
//				template.setAdviceTempId("ADV01");
//				template.setIsedit(false);
//				template.setIsselected(false);
//				template.setFixedvalue("Feeding as Advised");
//				template.setEditedvalue("");
//				add(template);
//				template = new DischargeAdviceDetail();
//				template.setAdviceTempId("ADV02");
//				template.setIsedit(false);
//				template.setIsselected(false);
//				template.setFixedvalue("Second dose of Hepatitis B due on");
//				template.setEditedvalue(CalendarUtility.dateFormatUTF.format(new java.util.Date()));
//				add(template);
//				template = new DischargeAdviceDetail();
//				template.setAdviceTempId("ADV03");
//				template.setIsedit(false);
//				template.setIsselected(false);
//				template.setFixedvalue("Vitanova drops (800 U/ml)");
//				template.setEditedvalue("1ml per oral once daily");
//				add(template);
//				template = new DischargeAdviceDetail();
//				template.setAdviceTempId("ADV04");
//				template.setIsedit(false);
//				template.setIsselected(false);
//				template.setFixedvalue("Ferronia XT drops");
//				template.setEditedvalue("0.4ml per oral once daily");
//				add(template);
//				template = new DischargeAdviceDetail();
//				template.setAdviceTempId("ADV05");
//				template.setIsedit(false);
//				template.setIsselected(false);
//				template.setFixedvalue("Syrup Nodosis");
//				template.setEditedvalue("1 ml per feed (3hourly) per oral-to continue");
//				add(template);
//				template = new DischargeAdviceDetail();
//				template.setAdviceTempId("ADV06");
//				template.setIsedit(false);
//				template.setIsselected(false);
//				template.setFixedvalue("ROP review on");
//				template.setEditedvalue(CalendarUtility.dateFormatUTF.format(new java.util.Date()));
//				add(template);
//				template = new DischargeAdviceDetail();
//				template.setAdviceTempId("ADV07");
//				template.setIsedit(false);
//				template.setIsselected(false);
//				template.setFixedvalue("Developmental assessment at");
//				template.setEditedvalue("6 weeks, 3 months, 6 months, 12 months, 18months and 2 years");
//				add(template);
//				template = new DischargeAdviceDetail();
//				template.setAdviceTempId("ADV08");
//				template.setIsedit(false);
//				template.setIsselected(false);
//				template.setFixedvalue("Audiological assessment after");
//				template.setEditedvalue("2 months Dr. Asha Agarwal (98110161532)");
//				add(template);
//				template = new DischargeAdviceDetail();
//				template.setAdviceTempId("ADV09");
//				template.setIsedit(false);
//				template.setIsselected(false);
//				template.setFixedvalue("Burping to be done after each feed");
//				template.setEditedvalue("");
//				add(template);
//				template = new DischargeAdviceDetail();
//				template.setAdviceTempId("ADV10");
//				template.setIsedit(false);
//				template.setIsselected(false);
//				template.setFixedvalue("A-Z drops");
//				template.setEditedvalue("0.5 ml daily.");
//				add(template);
//				template = new DischargeAdviceDetail();
//				template.setAdviceTempId("ADV11");
//				template.setIsedit(false);
//				template.setIsselected(false);
//				template.setFixedvalue("Nasoclear nasal drops");
//				template.setEditedvalue("2 drops in each nostril sos.");
//				add(template);
//				template = new DischargeAdviceDetail();
//				template.setAdviceTempId("ADV12");
//				template.setIsedit(false);
//				template.setIsselected(false);
//				template.setFixedvalue("Baby should not be made to sleep on the stomach/abdomen");
//				template.setEditedvalue("");
//				add(template);
//				template = new DischargeAdviceDetail();
//				template.setAdviceTempId("ADV13");
//				template.setIsedit(false);
//				template.setIsselected(false);
//				template.setFixedvalue("Follow the instructions given to the mother for bath/soap/oil massage");
//				template.setEditedvalue("");
//				add(template);
//				template = new DischargeAdviceDetail();
//				template.setAdviceTempId("ADV14");
//				template.setIsedit(false);
//				template.setIsselected(false);
//				template.setFixedvalue("Watch for danger signs like cynosis, fast breathing, chest indrawing, abnormal movements of body, refusal to feed and report to emergency if present");
//				template.setEditedvalue("");
//				add(template);
//				template = new DischargeAdviceDetail();
//				template.setAdviceTempId("ADV15");
//				template.setIsedit(false);
//				template.setIsselected(false);
//				template.setFixedvalue("Other");
//				template.setEditedvalue("");
//				add(template);
//				
//			}
//		};
//	}

	public Boolean getDoctorChanged() {
		return doctorChanged;
	}

	public void setDoctorChanged(Boolean doctorChanged) {
		this.doctorChanged = doctorChanged;
	}

	public Boolean getDiagnosisChanged() {
		return diagnosisChanged;
	}

	public void setDiagnosisChanged(Boolean diagnosisChanged) {
		this.diagnosisChanged = diagnosisChanged;
	}

	public String getSystemicExamNotes() {
		return SystemicExamNotes;
	}

	public void setSystemicExamNotes(String systemicExamNotes) {
		SystemicExamNotes = systemicExamNotes;
	}

    public RespSupport getOngoingRespSupport() {
        return ongoingRespSupport;
    }

    public void setOngoingRespSupport(RespSupport ongoingRespSupport) {
        this.ongoingRespSupport = ongoingRespSupport;
    }

    public String getOngoingMedication() {
        return ongoingMedication;
    }

    public void setOngoingMedication(String ongoingMedication) {
        this.ongoingMedication = ongoingMedication;
    }

    public String getOngoingNutrition() {
		return ongoingNutrition;
	}

	public void setOngoingNutrition(String ongoingNutrition) {
		this.ongoingNutrition = ongoingNutrition;
	}



	public Object getBabyStatus() {
		return babyStatus;
	}

	public void setBabyStatus(Object babyStatus) {
		this.babyStatus = babyStatus;
	}

	public Object getComments() {
		return comments;
	}

	public void setComments(Object comments) {
		this.comments = comments;
	}

	public String getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(String loggedUser) {
		this.loggedUser = loggedUser;
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

//	public List<DischargeAdviceDetail> getDischargeAdviceTemplates() {
//		return dischargeAdviceTemplates;
//	}

	public BabyDetail getBabyDetails() {
		return babyDetails;
	}

	public ParentDetail getParenteDetail() {
		return parenteDetail;
	}

	public BabyVisit getBirthVisit() {
		return birthVisit;
	}

	public BabyVisit getAdmissionVisit() {
		return admissionVisit;
	}

	public BabyVisit getDischargeVisit() {
		return dischargeVisit;
	}

//	public void setDischargeAdviceTemplates(List<DischargeAdviceDetail> dischargeAdviceTemplates) {
//		this.dischargeAdviceTemplates = dischargeAdviceTemplates;
//	}

	public void setBabyDetails(BabyDetail babyDetails) {
		this.babyDetails = babyDetails;
	}

	public void setParenteDetail(ParentDetail parenteDetail) {
		this.parenteDetail = parenteDetail;
	}

	public void setBirthVisit(BabyVisit birthVisit) {
		this.birthVisit = birthVisit;
	}

	public void setAdmissionVisit(BabyVisit admissionVisit) {
		this.admissionVisit = admissionVisit;
	}

	public void setDischargeVisit(BabyVisit dischargeVisit) {
		this.dischargeVisit = dischargeVisit;
	}

	public Timestamp getEdd() {
		return edd;
	}

	public void setEdd(Timestamp edd) {
		this.edd = edd;
	}

	public String getFinalDiagnosis() {
		return finalDiagnosis;
	}

	public void setFinalDiagnosis(String finalDiagnosis) {
		this.finalDiagnosis = finalDiagnosis;
	}

	public DischargeNotesDetail getDischargeNotes() {
		return dischargeNotes;
	}

	public void setDischargeNotes(DischargeNotesDetail dischargeNotes) {
		this.dischargeNotes = dischargeNotes;
	}

	public Boolean getIsDischargeBaby() {
		return isDischargeBaby;
	}

	public void setIsDischargeBaby(Boolean isDischargeBaby) {
		this.isDischargeBaby = isDischargeBaby;
	}

	public String getWhichPage() {
		return whichPage;
	}

	public void setWhichPage(String whichPage) {
		this.whichPage = whichPage;
	}

	public DischargeOutcome getOutcome() {
		return outcome;
	}

	public void setOutcome(DischargeOutcome outcome) {
		this.outcome = outcome;
	}

	public String getAssessmentMessage() {
		return assessmentMessage;
	}

	public void setAssessmentMessage(String assessmentMessage) {
		this.assessmentMessage = assessmentMessage;
	}
	
	public List<BabyPrescription> getPrescriptionList() {
		return prescriptionList;
	}

	public void setPrescriptionList(List<BabyPrescription> prescriptionList) {
		this.prescriptionList = prescriptionList;
	}	

	public List<TestResultsViewPOJO> getTestResult() {
		return testResult;
	}

	public void setTestResult(List<TestResultsViewPOJO> testResult) {
		this.testResult = testResult;
	}

	public GenPhyExam getBabyPhysicalExamData() {
		return babyPhysicalExamData;
	}

	public void setBabyPhysicalExamData(GenPhyExam babyPhysicalExamData) {
		this.babyPhysicalExamData = babyPhysicalExamData;
	}

	public String getBabySystemicExamData() {
		return babySystemicExamData;
	}

	public void setBabySystemicExamData(String babySystemicExamData) {
		this.babySystemicExamData = babySystemicExamData;
	}

	public BirthToNicu getBirthToNicuDetails() {
		return birthToNicuDetails;
	}

	public void setBirthToNicuDetails(BirthToNicu birthToNicuDetails) {
		this.birthToNicuDetails = birthToNicuDetails;
	}

	public AntenatalHistoryDetail getBabyAntenatalHistoryDetails() {
		return babyAntenatalHistoryDetails;
	}

	public void setBabyAntenatalHistoryDetails(AntenatalHistoryDetail babyAntenatalHistoryDetails) {
		this.babyAntenatalHistoryDetails = babyAntenatalHistoryDetails;
	}

	public List<ScreenRop> getScreenRop() {
		return screenRop;
	}

	public void setScreenRop(List<ScreenRop> screenRop) {
		this.screenRop = screenRop;
	}

	public List<ScreenUSG> getScreenUsg() {
		return screenUsg;
	}

	public void setScreenUsg(List<ScreenUSG> screenUsg) {
		this.screenUsg = screenUsg;
	}

	public List<ScreenNeurological> getScreenNeurological() {
		return screenNeurological;
	}

	public void setScreenNeurological(List<ScreenNeurological> screenNeurological) {
		this.screenNeurological = screenNeurological;
	}

	public List<ScreenMetabolic> getScreenMetabolical() {
		return screenMetabolical;
	}

	public void setScreenMetabolical(List<ScreenMetabolic> screenMetabolical) {
		this.screenMetabolical = screenMetabolical;
	}

	public List<ScreenHearing> getScreenHearing() {
		return screenHearing;
	}

	public void setScreenHearing(List<ScreenHearing> screenHearing) {
		this.screenHearing = screenHearing;
	}

	public String getObstetricCondtiton() {
		return obstetricCondtiton;
	}

	public void setObstetricCondtiton(String obstetricCondtiton) {
		this.obstetricCondtiton = obstetricCondtiton;
	}

	public String getMaternalInvestigation() {
		return maternalInvestigation;
	}

	public void setMaternalInvestigation(String maternalInvestigation) {
		this.maternalInvestigation = maternalInvestigation;
	}

	public RefHospitalbranchname getHospitalDetails() {
		return hospitalDetails;
	}

	public void setHospitalDetails(RefHospitalbranchname hospitalDetails) {
		this.hospitalDetails = hospitalDetails;
	}

	public String getDischargeMedicationList() {
		return dischargeMedicationList;
	}

	public void setDischargeMedicationList(String dischargeMedicationList) {
		this.dischargeMedicationList = dischargeMedicationList;
	}

	public String getBpdRespiratoryNote() {
		return bpdRespiratoryNote;
	}

	public void setBpdRespiratoryNote(String bpdRespiratoryNote) {
		this.bpdRespiratoryNote = bpdRespiratoryNote;
	}

	public String getIcdCode() {
		return icdCode;
	}

	public void setIcdCode(String icdCode) {
		this.icdCode = icdCode;
	}

	public List<String> getActiveDoctors() {
		return activeDoctors;
	}

	public void setActiveDoctors(List<String> activeDoctors) {
		this.activeDoctors = activeDoctors;
	}

	public List<RefAdviceOnDischarge> getAdviceTemplate() {
		return adviceTemplate;
	}

	public void setAdviceTemplate(List<RefAdviceOnDischarge> adviceTemplate) {
		this.adviceTemplate = adviceTemplate;
	}

	public List<DischargeAdviceDetail> getAdviceOnDischarge() {
		return adviceOnDischarge;
	}

	public void setAdviceOnDischarge(List<DischargeAdviceDetail> adviceOnDischarge) {
		this.adviceOnDischarge = adviceOnDischarge;
	}

	public Boolean getAdviceOnDischargeStatus() {
		return adviceOnDischargeStatus;
	}

	public void setAdviceOnDischargeStatus(Boolean adviceOnDischargeStatus) {
		this.adviceOnDischargeStatus = adviceOnDischargeStatus;
	}

	public List<DischargeAdviceDetail> getPastadviceOnDischarge() {
		return pastadviceOnDischarge;
	}

	public void setPastadviceOnDischarge(List<DischargeAdviceDetail> pastadviceOnDischarge) {
		this.pastadviceOnDischarge = pastadviceOnDischarge;
	}

	public List<ScreenMiscellaneous> getScreenMisc() {
		return screenMisc;
	}

	public void setScreenMisc(List<ScreenMiscellaneous> screenMisc) {
		this.screenMisc = screenMisc;
	}

	public String getOutcomeNote() {
		return outcomeNote;
	}

	public void setOutcomeNote(String outcomeNote) {
		this.outcomeNote = outcomeNote;
	}

//	public Boolean getAdviceSelected() {
//		return adviceSelected;
//	}
//
//	public void setAdviceSelected(Boolean adviceSelected) {
//		this.adviceSelected = adviceSelected;
//	}
//	
//	
	
	
	
	
	
	

}
