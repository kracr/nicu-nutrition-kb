package com.inicu.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.inicu.postgres.entities.ApneaEvent;
import com.inicu.postgres.entities.DeclinedApneaEvent;
import com.inicu.postgres.entities.NursingDailyassesment;
import com.inicu.postgres.entities.SaJaundice;
import com.inicu.postgres.entities.SaRespPphn;
import com.inicu.postgres.utility.BasicConstants;

public class NursingObservationPojo {

	// baby visits data........

	private BabyVisitsObj babyVisit;
	private Integer apneaDuration; 
	private List<ApneaEvent> apneaEventList = new ArrayList<ApneaEvent>();
	
	private List<DeclinedApneaEvent> declinedApneaEventList = new ArrayList<DeclinedApneaEvent>();
	private HashMap<String, Object> bloodGasInfo;
	private HashMap<String, Object> vitalParametersInfo;
	private HashMap<String, Object> neuroVitalsInfo;
	private HashMap<String, Object> intakeInfo;
	private HashMap<String, Object> outputInfo;
	private HashMap<String, Object> cathetersInfo;
	private HashMap<String, Object> ventilatorInfo;
	private HashMap<String, Object> dailyAssessmentInfo;
	private HashMap<String, Object> miscInfo;
	private SaRespPphn inoObject;

	private String uhid;
	private String loggedUser;
	private String time;
	private TimeObj nnEntryTime; // used by vikash
	private Boolean photoTherapy;
	private String userDate;
	private Timestamp entryDate;
	private Timestamp creationtime;

	public HashMap<String, Object> getVitalParametersInfo() {
		return vitalParametersInfo;
	}

	public void setVitalParametersInfo(HashMap<String, Object> vitalParametersInfo) {
		this.vitalParametersInfo = vitalParametersInfo;
	}

	public NursingObservationPojo() {
		// set here two entries for each field of pojo..so extra null check not
		// required.
		HashMap<String, Object> vitalParametersInfo = new HashMap<String, Object>();
		HashMap<String, Object> bloodGasInfo = new HashMap<String, Object>();
		HashMap<String, Object> neuroVitalsInfo = new HashMap<String, Object>();
		HashMap<String, Object> intakeInfo = new HashMap<String, Object>();
		HashMap<String, Object> outputInfo = new HashMap<String, Object>();
		HashMap<String, Object> cathetersInfo = new HashMap<String, Object>();
		HashMap<String, Object> ventilatorInfo = new HashMap<String, Object>();
		HashMap<String, Object> dailyAssessmentInfo = new HashMap<String, Object>();
		HashMap<String, Object> miscInfo = new HashMap<String, Object>();
		dailyAssessmentInfo.put(BasicConstants.EMPTY_OBJ_STR, new NursingDailyassesment());
		dailyAssessmentInfo.put(BasicConstants.PREVIOUS_DATA_STR, new ArrayList<NursingDailyassesment>());
		this.bloodGasInfo = bloodGasInfo;
		this.vitalParametersInfo = vitalParametersInfo;
		this.neuroVitalsInfo = neuroVitalsInfo;
		this.intakeInfo = intakeInfo;
		this.outputInfo = outputInfo;
		this.cathetersInfo = cathetersInfo;
		this.ventilatorInfo = ventilatorInfo;
		this.dailyAssessmentInfo = dailyAssessmentInfo;
		this.miscInfo = miscInfo;
		this.nnEntryTime = new TimeObj();
		this.loggedUser = "";
		this.apneaEventList = new ArrayList<ApneaEvent>();
	}

	public Integer getApneaDuration() {
		return apneaDuration;
	}

	public void setApneaDuration(Integer apneaDuration) {
		this.apneaDuration = apneaDuration;
	}

	public HashMap<String, Object> getBloodGasInfo() {
		return this.bloodGasInfo;
	}

	public void setBloodGasInfo(HashMap<String, Object> bloodGasInfo) {
		this.bloodGasInfo = bloodGasInfo;
	}

	public HashMap<String, Object> getNeuroVitalsInfo() {
		return this.neuroVitalsInfo;
	}

	public void setNeuroVitalsInfo(HashMap<String, Object> neuroVitalsInfo) {
		this.neuroVitalsInfo = neuroVitalsInfo;
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public void setPhotoTherapy(Boolean photoTherapy) {
		this.photoTherapy = photoTherapy;
	}

	public HashMap<String, Object> getIntakeInfo() {
		return intakeInfo;
	}

	public void setIntakeInfo(HashMap<String, Object> intakeInfo) {
		this.intakeInfo = intakeInfo;
	}

	public String getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(String loggedUser) {
		this.loggedUser = loggedUser;
	}

	public HashMap<String, Object> getOutputInfo() {
		return outputInfo;
	}

	public void setOutputInfo(HashMap<String, Object> outputInfo) {
		this.outputInfo = outputInfo;
	}

	public HashMap<String, Object> getCathetersInfo() {
		return cathetersInfo;
	}

	public void setCathetersInfo(HashMap<String, Object> cathetersInfo) {
		this.cathetersInfo = cathetersInfo;
	}

	public HashMap<String, Object> getVentilatorInfo() {
		return this.ventilatorInfo;
	}

	public void setVentilatorInfo(HashMap<String, Object> ventilatorInfo) {
		this.ventilatorInfo = ventilatorInfo;
	}

	public HashMap<String, Object> getDailyAssessmentInfo() {
		return this.dailyAssessmentInfo;
	}

	public void setDailyAssessmentInfo(HashMap<String, Object> dailyAssessmentInfo) {
		this.dailyAssessmentInfo = dailyAssessmentInfo;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTime() {
		return this.time;
	}

	public TimeObj getNnEntryTime() {
		return nnEntryTime;
	}

	public void setNnEntryTime(TimeObj nnEntryTime) {
		this.nnEntryTime = nnEntryTime;
	}

	public BabyVisitsObj getBabyVisit() {
		return babyVisit;
	}

	public void setBabyVisit(BabyVisitsObj babyVisit) {
		this.babyVisit = babyVisit;
	}

	public HashMap<String, Object> getMiscInfo() {
		return this.miscInfo;
	}

	public void setMiscInfo(HashMap<String, Object> miscInfo) {
		this.miscInfo = miscInfo;
	}

	public Boolean getPhotoTherapy() {
		return this.photoTherapy;
	}

	public void setPhototTheropy(Boolean photoTherapy) {
		this.photoTherapy = photoTherapy;
	}

	public String getUserDate() {
		return userDate;
	}

	public void setUserDate(String userDate) {
		this.userDate = userDate;
	}

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public Timestamp getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}

	public SaRespPphn getInoObject() {
		return inoObject;
	}

	public void setInoObject(SaRespPphn inoObject) {
		this.inoObject = inoObject;
	}

	public List<ApneaEvent> getApneaEventList() {
		return apneaEventList;
	}

	public void setApneaEventList(List<ApneaEvent> apneaEventList) {
		this.apneaEventList = apneaEventList;
	}

	public List<DeclinedApneaEvent> getDeclinedApneaEventList() {
		return declinedApneaEventList;
	}

	public void setDeclinedApneaEventList(List<DeclinedApneaEvent> declinedApneaEventList) {
		this.declinedApneaEventList = declinedApneaEventList;
	}
	
	
}
