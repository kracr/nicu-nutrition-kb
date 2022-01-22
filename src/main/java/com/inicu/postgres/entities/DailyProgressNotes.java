package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.inicu.models.*;

/**
 * The persistent class for the daily_progress_notes database table.
 */
@Entity
@Table(name = "daily_progress_notes")
@NamedQuery(name = "DailyProgressNotes.findAll", query = "SELECT b FROM DailyProgressNotes b")
public class DailyProgressNotes implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long daily_progress_notes_id;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String uhid;

	private Date note_date;

	@Transient
	HashMap<String, List<EventDetailsPOJO>> progressNotesList; 
	
	@Transient
	List<HashMap<String, List<EventDetailsPOJO>>> finalProgressNotesList;
	
	@Transient
	private MorningNoteJSON morningNote;
	
	@Transient
	private String previousTime;
	
	@Transient
	private String currentTime;
	
	@Transient
	private String tcb;
	
	@Transient
	private String respSupport;
	
	@Transient
	private String currentRespSupport;
	
	@Transient
	private String rbs;
	
	@Transient
	private String generalStableNote;

	@Transient
	private BabyVisit basicDetails;

	@Transient
	private String diagnosis;


	public String getProvisionalDiagnosis() {
		return provisionalDiagnosis;
	}

	public void setProvisionalDiagnosis(String provisionalDiagnosis) {
		this.provisionalDiagnosis = provisionalDiagnosis;
	}

	@Transient
	private String provisionalDiagnosis;

	@Transient
	private String vital;
	
	@Transient
	private String currentVital;

	@Transient
	private String intake_output;

	@Transient
	private String lab_details;

	@Transient
	private String medication;
	
	@Transient
	private String procedure;
	
	@Transient
	private String screening;

	@Transient
	private String bloodProduct;
	
	@Transient
	private String plan;
	
	@Transient
	private String currentMedications;
	
	@Transient
	private String intakeOutputNote;


	@Transient
	private String outputNote;
	
	@Transient
	private String procedureNote;
	
	@Transient
	private String screeningNote;

	@Transient
	private String bloodProductNote;
	
	@Transient
	private Integer nicuDays;
	
	@Transient
	private Integer gestationDays;
	
	@Transient
	private Integer gestationWeeks;
	
	@Transient
	private String urineOutput;
	
	@Transient
	private Integer stool;
	
	@Transient
	private Integer vomit;
	
	@Transient
	private String abdominalGirth;
	
	@Transient
	private List<StableNote> stableNotesList;
	
	@Transient
	private String progressNoteFormatType;
	
	@Transient
	private Integer currentDol;
	
	@Transient
	private Integer currentGestationWeeks;
	
	@Transient
	private Integer currentGestationDays;
	
	@Transient
	List<KeyValueObj> logoImage;
	
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
	List<KeyValueObj> signatureImage;

	@Transient
	List<KeyValueObj> consultantSignatureImage;

	@Transient
	private String loggedUserFullName;
	
	@Transient
	private String doctorOrders;


	@Transient
	private HashMap<String ,String> labReports;

	@Transient
	private List<TestResultObject> testResultObjectList;

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

	public List<ScreenMiscellaneous> getScreenMisc() {
		return screenMisc;
	}

	public void setScreenMisc(List<ScreenMiscellaneous> screenMisc) {
		this.screenMisc = screenMisc;
	}

	public List<KeyValueObj> getLogoImage() {
		return logoImage;
	}

	public void setLogoImage(List<KeyValueObj> logoImage) {
		this.logoImage = logoImage;
	}

	public DailyProgressNotes() {
		this.morningNote = new MorningNoteJSON();
	}

	public String getUrineOutput() {
		return urineOutput;
	}



	public void setUrineOutput(String urineOutput) {
		this.urineOutput = urineOutput;
	}


	public List<StableNote> getStableNotesList() {
		return stableNotesList;
	}

	public void setStableNotesList(List<StableNote> stableNotesList) {
		this.stableNotesList = stableNotesList;
	}

	public Integer getStool() {
		return stool;
	}

	public String getProgressNoteFormatType() {
		return progressNoteFormatType;
	}

	public void setProgressNoteFormatType(String progressNoteFormatType) {
		this.progressNoteFormatType = progressNoteFormatType;
	}

	public void setStool(Integer stool) {
		this.stool = stool;
	}



	public Integer getVomit() {
		return vomit;
	}



	public void setVomit(Integer vomit) {
		this.vomit = vomit;
	}



	public String getAbdominalGirth() {
		return abdominalGirth;
	}



	public void setAbdominalGirth(String abdominalGirth) {
		this.abdominalGirth = abdominalGirth;
	}



	public Long getDaily_progress_notes_id() {
		return daily_progress_notes_id;
	}

	public void setDaily_progress_notes_id(Long daily_progress_notes_id) {
		this.daily_progress_notes_id = daily_progress_notes_id;
	}

	public MorningNoteJSON getMorningNote() {
		return morningNote;
	}

	public void setMorningNote(MorningNoteJSON morningNote) {
		this.morningNote = morningNote;
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

	public Date getNote_date() {
		return note_date;
	}

	public void setNote_date(Date note_date) {
		this.note_date = note_date;
	}

	public BabyVisit getBasicDetails() {
		return basicDetails;
	}

	public void setBasicDetails(BabyVisit basicDetails) {
		this.basicDetails = basicDetails;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public String getVital() {
		return vital;
	}

	public void setVital(String vital) {
		this.vital = vital;
	}

	public String getIntake_output() {
		return intake_output;
	}

	public void setIntake_output(String intake_output) {
		this.intake_output = intake_output;
	}

	public String getLab_details() {
		return lab_details;
	}

	public void setLab_details(String lab_details) {
		this.lab_details = lab_details;
	}

	public String getMedication() {
		return medication;
	}

	public void setMedication(String medication) {
		this.medication = medication;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public String getTcb() {
		return tcb;
	}

	public void setTcb(String tcb) {
		this.tcb = tcb;
	}

	public Integer getNicuDays() {
		return nicuDays;
	}

	public void setNicuDays(Integer nicuDays) {
		this.nicuDays = nicuDays;
	}

	public Integer getGestationDays() {
		return gestationDays;
	}

	public void setGestationDays(Integer gestationDays) {
		this.gestationDays = gestationDays;
	}

	public Integer getGestationWeeks() {
		return gestationWeeks;
	}

	public void setGestationWeeks(Integer gestationWeeks) {
		this.gestationWeeks = gestationWeeks;
	}

	public String getIntakeOutputNote() {
		return intakeOutputNote;
	}

	public void setIntakeOutputNote(String intakeOutputNote) {
		this.intakeOutputNote = intakeOutputNote;
	}


	public String getOutputNote() {
		return outputNote;
	}

	public void setOutputNote(String outputNote) {
		this.outputNote = outputNote;
	}

	public String getProcedure() {
		return procedure;
	}

	public void setProcedure(String procedure) {
		this.procedure = procedure;
	}

	public String getProcedureNote() {
		return procedureNote;
	}

	public void setProcedureNote(String procedureNote) {
		this.procedureNote = procedureNote;
	}

	public Integer getCurrentDol() {
		return currentDol;
	}

	public void setCurrentDol(Integer currentDol) {
		this.currentDol = currentDol;
	}

	public Integer getCurrentGestationWeeks() {
		return currentGestationWeeks;
	}

	public void setCurrentGestationWeeks(Integer currentGestationWeeks) {
		this.currentGestationWeeks = currentGestationWeeks;
	}

	public Integer getCurrentGestationDays() {
		return currentGestationDays;
	}

	public void setCurrentGestationDays(Integer currentGestationDays) {
		this.currentGestationDays = currentGestationDays;
	}

	public String getScreening() {
		return screening;
	}

	public void setScreening(String screening) {
		this.screening = screening;
	}

	public String getScreeningNote() {
		return screeningNote;
	}

	public void setScreeningNote(String screeningNote) {
		this.screeningNote = screeningNote;
	}

	public String getBloodProduct() {
		return bloodProduct;
	}

	public void setBloodProduct(String bloodProduct) {
		this.bloodProduct = bloodProduct;
	}

	public String getBloodProductNote() {
		return bloodProductNote;
	}

	public void setBloodProductNote(String bloodProductNote) {
		this.bloodProductNote = bloodProductNote;
	}
	
	public String getRbs() {
		return rbs;
	}

	public void setRbs(String rbs) {
		this.rbs = rbs;
	}

	public String getRespSupport() {
		return respSupport;
	}

	public void setRespSupport(String respSupport) {
		this.respSupport = respSupport;
	}

	public String getCurrentVital() {
		return currentVital;
	}

	public void setCurrentVital(String currentVital) {
		this.currentVital = currentVital;
	}

	public HashMap<String, List<EventDetailsPOJO>> getProgressNotesList() {
		return progressNotesList;
	}

	public void setProgressNotesList(HashMap<String, List<EventDetailsPOJO>> progressNotesList) {
		this.progressNotesList = progressNotesList;
	}
	
	public String getCurrentRespSupport() {
		return currentRespSupport;
	}

	public void setCurrentRespSupport(String currentRespSupport) {
		this.currentRespSupport = currentRespSupport;
	}

	public String getCurrentMedications() {
		return currentMedications;
	}

	public void setCurrentMedications(String currentMedications) {
		this.currentMedications = currentMedications;
	}

	public String getGeneralStableNote() {
		return generalStableNote;
	}

	public void setGeneralStableNote(String generalStableNote) {
		this.generalStableNote = generalStableNote;
	}

	public String getPreviousTime() {
		return previousTime;
	}

	public void setPreviousTime(String previousTime) {
		this.previousTime = previousTime;
	}

	public String getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}

	public List<KeyValueObj> getSignatureImage() {
		return signatureImage;
	}

	public void setSignatureImage(List<KeyValueObj> signatureImage) {
		this.signatureImage = signatureImage;
	}

	public List<KeyValueObj> getConsultantSignatureImage() {
		return consultantSignatureImage;
	}

	public void setConsultantSignatureImage(List<KeyValueObj> consultantSignatureImage) {
		this.consultantSignatureImage = consultantSignatureImage;
	}

	public String getLoggedUserFullName() {
		return loggedUserFullName;
	}

	public void setLoggedUserFullName(String loggedUserFullName) {
		this.loggedUserFullName = loggedUserFullName;
	}

	public String getDoctorOrders() {
		return doctorOrders;
	}

	public void setDoctorOrders(String doctorOrders) {
		this.doctorOrders = doctorOrders;
	}

	public List<HashMap<String, List<EventDetailsPOJO>>> getFinalProgressNotesList() {
		return finalProgressNotesList;
	}

	public void setFinalProgressNotesList(List<HashMap<String, List<EventDetailsPOJO>>> finalProgressNotesList) {
		this.finalProgressNotesList = finalProgressNotesList;
	}

	public HashMap<String, String> getLabReports() {
		return labReports;
	}

	public void setLabReports(HashMap<String, String> labReports) {
		this.labReports = labReports;
	}

	public List<TestResultObject> getTestResultObjectList() {
		return testResultObjectList;
	}

	public void setTestResultObjectList(List<TestResultObject> testResultObjectList) {
		this.testResultObjectList = testResultObjectList;
	}

	@Override
	public String toString() {
		return "DailyProgressNotes{" +
				"daily_progress_notes_id=" + daily_progress_notes_id +
				", creationtime=" + creationtime +
				", modificationtime=" + modificationtime +
				", uhid='" + uhid + '\'' +
				", note_date=" + note_date +
				", progressNotesList=" + progressNotesList +
				", finalProgressNotesList=" + finalProgressNotesList +
				", morningNote=" + morningNote +
				", previousTime='" + previousTime + '\'' +
				", currentTime='" + currentTime + '\'' +
				", tcb='" + tcb + '\'' +
				", respSupport='" + respSupport + '\'' +
				", currentRespSupport='" + currentRespSupport + '\'' +
				", rbs='" + rbs + '\'' +
				", generalStableNote='" + generalStableNote + '\'' +
				", basicDetails=" + basicDetails +
				", diagnosis='" + diagnosis + '\'' +
				", provisionalDiagnosis='" + provisionalDiagnosis + '\'' +
				", vital='" + vital + '\'' +
				", currentVital='" + currentVital + '\'' +
				", intake_output='" + intake_output + '\'' +
				", lab_details='" + lab_details + '\'' +
				", medication='" + medication + '\'' +
				", procedure='" + procedure + '\'' +
				", screening='" + screening + '\'' +
				", bloodProduct='" + bloodProduct + '\'' +
				", plan='" + plan + '\'' +
				", currentMedications='" + currentMedications + '\'' +
				", intakeOutputNote='" + intakeOutputNote + '\'' +
				", outputNote='" + outputNote + '\'' +
				", procedureNote='" + procedureNote + '\'' +
				", screeningNote='" + screeningNote + '\'' +
				", bloodProductNote='" + bloodProductNote + '\'' +
				", nicuDays=" + nicuDays +
				", gestationDays=" + gestationDays +
				", gestationWeeks=" + gestationWeeks +
				", urineOutput='" + urineOutput + '\'' +
				", stool=" + stool +
				", vomit=" + vomit +
				", abdominalGirth='" + abdominalGirth + '\'' +
				", stableNotesList=" + stableNotesList +
				", progressNoteFormatType='" + progressNoteFormatType + '\'' +
				", currentDol=" + currentDol +
				", currentGestationWeeks=" + currentGestationWeeks +
				", currentGestationDays=" + currentGestationDays +
				", logoImage=" + logoImage +
				", screenRop=" + screenRop +
				", screenUsg=" + screenUsg +
				", screenNeurological=" + screenNeurological +
				", screenMetabolical=" + screenMetabolical +
				", screenHearing=" + screenHearing +
				", screenMisc=" + screenMisc +
				", signatureImage=" + signatureImage +
				", consultantSignatureImage=" + consultantSignatureImage +
				", loggedUserFullName='" + loggedUserFullName + '\'' +
				", doctorOrders='" + doctorOrders + '\'' +
				", labReports=" + labReports +
				", testResultObjectList=" + testResultObjectList +
				'}';
	}
}
