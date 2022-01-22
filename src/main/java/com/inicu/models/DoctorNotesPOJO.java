package com.inicu.models;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import com.inicu.postgres.entities.BabyVisit;
import com.inicu.postgres.entities.DeviceMonitorDetail;
import com.inicu.postgres.entities.NursingVitalparameter;
import com.inicu.postgres.entities.StableNote;
import com.inicu.postgres.entities.VwDoctornotesListFinal;

public class DoctorNotesPOJO {

	BabyVisit basicDetails;

	String vitalDetails;

	Timestamp vital_time;

	NursingVitalparameter nursingVitalObj;

	DeviceMonitorDetail deviceVitalObj;

	HashMap<String, Object> intakeDetails;

	HashMap<String, Object> outputDetails;

	String diagnosis;

	String initialAssessmentNotes;

	HashMap<String, Object> admissionFormNotesDetails;

	HashMap<String, List<VwDoctornotesListFinal>> assessmentDetails;

	HashMap<String, String> systemEventsMapping;

	StableNote stableNoteObj;

	String nursingEventStr;

	public DoctorNotesPOJO() {
		super();
		this.basicDetails = new BabyVisit();
		this.intakeDetails = new HashMap<String, Object>();
		this.outputDetails = new HashMap<String, Object>();
		this.admissionFormNotesDetails = new HashMap<String, Object>();
		this.assessmentDetails = new HashMap<String, List<VwDoctornotesListFinal>>();
		this.systemEventsMapping = new HashMap<String, String>() {
			private static final long serialVersionUID = 1L;

			{
				put("RespSystems", "'RDS', 'Pneumothorax', 'Apnea', 'PPHN'");

				put("Jaundice", "'Jaundice'");

				put("Infection", "'Sepsis', 'VAP', 'CLABSI'");

				put("CNS", "'Asphyxia', 'Seizures'");

				put("Stable Notes", "'Stable Notes'");

				put("Misc", "'Misc'");

			}
		};
	}

	public HashMap<String, Object> getIntakeDetails() {
		return intakeDetails;
	}

	public HashMap<String, Object> getOutputDetails() {
		return outputDetails;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public BabyVisit getBasicDetails() {
		return basicDetails;
	}

	public void setBasicDetails(BabyVisit basicDetails) {
		this.basicDetails = basicDetails;
	}

	public void setIntakeDetails(HashMap<String, Object> intakeDetails) {
		this.intakeDetails = intakeDetails;
	}

	public void setOutputDetails(HashMap<String, Object> outputDetails) {
		this.outputDetails = outputDetails;
	}

	public HashMap<String, Object> getAdmissionFormNotesDetails() {
		return admissionFormNotesDetails;
	}

	public void setAdmissionFormNotesDetails(HashMap<String, Object> admissionFormNotesDetails) {
		this.admissionFormNotesDetails = admissionFormNotesDetails;
	}

	public HashMap<String, String> getSystemEventsMapping() {
		return systemEventsMapping;
	}

	public void setSystemEventsMapping(HashMap<String, String> systemEventsMapping) {
		this.systemEventsMapping = systemEventsMapping;
	}

	public HashMap<String, List<VwDoctornotesListFinal>> getAssessmentDetails() {
		return assessmentDetails;
	}

	public void setAssessmentDetails(HashMap<String, List<VwDoctornotesListFinal>> assessmentDetails) {
		this.assessmentDetails = assessmentDetails;
	}

	public String getInitialAssessmentNotes() {
		return initialAssessmentNotes;
	}

	public void setInitialAssessmentNotes(String initialAssessmentNotes) {
		this.initialAssessmentNotes = initialAssessmentNotes;
	}

	public String getVitalDetails() {
		return vitalDetails;
	}

	public void setVitalDetails(String vitalDetails) {
		this.vitalDetails = vitalDetails;
	}

	public Timestamp getVital_time() {
		return vital_time;
	}

	public void setVital_time(Timestamp vital_time) {
		this.vital_time = vital_time;
	}

	public NursingVitalparameter getNursingVitalObj() {
		return nursingVitalObj;
	}

	public void setNursingVitalObj(NursingVitalparameter nursingVitalObj) {
		this.nursingVitalObj = nursingVitalObj;
	}

	public DeviceMonitorDetail getDeviceVitalObj() {
		return deviceVitalObj;
	}

	public void setDeviceVitalObj(DeviceMonitorDetail deviceVitalObj) {
		this.deviceVitalObj = deviceVitalObj;
	}

	public StableNote getStableNoteObj() {
		return stableNoteObj;
	}

	public void setStableNoteObj(StableNote stableNoteObj) {
		this.stableNoteObj = stableNoteObj;
	}

	public String getNursingEventStr() {
		return nursingEventStr;
	}

	public void setNursingEventStr(String nursingEventStr) {
		this.nursingEventStr = nursingEventStr;
	}

}
