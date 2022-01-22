package com.inicu.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class PatientChartData {

    private PatientChartVitalObject patientChartVitalObject;
    private PatientChartVentilatorObject patientChartVentilatorObject;
    private PatientChartBloodGasObject patientChartBloodGasObject;
    private PatientChartEventsObject patientChartEventsObject;

    // Intake Output variables
    private PatientChartEnteralObject patientChartEnteralObject;
    private PatientChartParentralObject patientChartParentralObject;

    private PatientChartOutputObject patientChartOutputObject;
    private PatientChartInvestigationObject patientChartInvestigationObject;

    private float currentDayPn;
    private float currentDayEn;
    private float totalCurrentIntake;

    private float previousDayPn;
    private float previousDayEn;
    private float totalPreviosIntake;

    private List<Float> cummumlativeIntakeOutputBalance;

    private HashMap<String,List<String>> patientChartMedicationObject;

    public PatientChartData(){
        this.cummumlativeIntakeOutputBalance=new ArrayList<>();
        this.patientChartMedicationObject=new LinkedHashMap<>();

    }

    public float getTotalCurrentIntake() {
        return totalCurrentIntake;
    }

    public void setTotalCurrentIntake(float totalCurrentIntake) {
        this.totalCurrentIntake = totalCurrentIntake;
    }

    public float getTotalPreviosIntake() {
        return totalPreviosIntake;
    }

    public void setTotalPreviosIntake(float totalPreviosIntake) {
        this.totalPreviosIntake = totalPreviosIntake;
    }

    public HashMap<String, List<String>> getPatientChartMedicationObject() {
        return patientChartMedicationObject;
    }

    public void setPatientChartMedicationObject(HashMap<String, List<String>> patientChartMedicationObject) {
        this.patientChartMedicationObject = patientChartMedicationObject;
    }

    public PatientChartEnteralObject getPatientChartEnteralObject() {
        return patientChartEnteralObject;
    }

    public void setPatientChartEnteralObject(PatientChartEnteralObject patientChartEnteralObject) {
        this.patientChartEnteralObject = patientChartEnteralObject;
    }

    public PatientChartParentralObject getPatientChartParentralObject() {
        return patientChartParentralObject;
    }

    public void setPatientChartParentralObject(PatientChartParentralObject patientChartParentralObject) {
        this.patientChartParentralObject = patientChartParentralObject;
    }

    public PatientChartOutputObject getPatientChartOutputObject() {
        return patientChartOutputObject;
    }

    public void setPatientChartOutputObject(PatientChartOutputObject patientChartOutputObject) {
        this.patientChartOutputObject = patientChartOutputObject;
    }

    public PatientChartInvestigationObject getPatientChartInvestigationObject() {
        return patientChartInvestigationObject;
    }

    public void setPatientChartInvestigationObject(PatientChartInvestigationObject patientChartInvestigationObject) {
        this.patientChartInvestigationObject = patientChartInvestigationObject;
    }

    public float getCurrentDayPn() {
        return currentDayPn;
    }

    public void setCurrentDayPn(float currentDayPn) {
        this.currentDayPn = currentDayPn;
    }

    public float getCurrentDayEn() {
        return currentDayEn;
    }

    public void setCurrentDayEn(float currentDayEn) {
        this.currentDayEn = currentDayEn;
    }

    public float getPreviousDayPn() {
        return previousDayPn;
    }

    public void setPreviousDayPn(float previousDayPn) {
        this.previousDayPn = previousDayPn;
    }

    public float getPreviousDayEn() {
        return previousDayEn;
    }

    public void setPreviousDayEn(float previousDayEn) {
        this.previousDayEn = previousDayEn;
    }

    public List<Float> getCummumlativeIntakeOutputBalance() {
        return cummumlativeIntakeOutputBalance;
    }

    public void setCummumlativeIntakeOutputBalance(List<Float> cummumlativeIntakeOutputBalance) {
        this.cummumlativeIntakeOutputBalance = cummumlativeIntakeOutputBalance;
    }

    public PatientChartVitalObject getPatientChartVitalObject() {
        return patientChartVitalObject;
    }

    public void setPatientChartVitalObject(PatientChartVitalObject patientChartVitalObject) {
        this.patientChartVitalObject = patientChartVitalObject;
    }

    public PatientChartVentilatorObject getPatientChartVentilatorObject() {
        return patientChartVentilatorObject;
    }

    public void setPatientChartVentilatorObject(PatientChartVentilatorObject patientChartVentilatorObject) {
        this.patientChartVentilatorObject = patientChartVentilatorObject;
    }

    public PatientChartBloodGasObject getPatientChartBloodGasObject() {
        return patientChartBloodGasObject;
    }

    public void setPatientChartBloodGasObject(PatientChartBloodGasObject patientChartBloodGasObject) {
        this.patientChartBloodGasObject = patientChartBloodGasObject;
    }

    public PatientChartEventsObject getPatientChartEventsObject() {
        return patientChartEventsObject;
    }

    public void setPatientChartEventsObject(PatientChartEventsObject patientChartEventsObject) {
        this.patientChartEventsObject = patientChartEventsObject;
    }
}
