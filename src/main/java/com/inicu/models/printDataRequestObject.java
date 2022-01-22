package com.inicu.models;

import java.sql.Timestamp;
import java.util.List;

public class printDataRequestObject {

    private List<String> uhidList;

    private boolean initialAssessment;
    private boolean doctorsNotes;
    private boolean doctorsOrders;
    private boolean nursingOutput;
    private boolean medicationChart;
    private boolean pnChart;
    private boolean dischargeSummary;

    public printDataRequestObject() {
        this.initialAssessment = false;
        this.doctorsNotes = false;
        this.doctorsOrders = false;
        this.nursingOutput = false;
        this.medicationChart = false;
        this.pnChart = false;
        this.dischargeSummary = false;
    }

    public List<String> getUhidList() {
        return uhidList;
    }

    public void setUhidList(List<String> uhidList) {
        this.uhidList = uhidList;
    }

    public boolean isInitialAssessment() {
        return initialAssessment;
    }

    public void setInitialAssessment(boolean initialAssessment) {
        this.initialAssessment = initialAssessment;
    }

    public boolean isDoctorsNotes() {
        return doctorsNotes;
    }

    public void setDoctorsNotes(boolean doctorsNotes) {
        this.doctorsNotes = doctorsNotes;
    }

    public boolean isDoctorsOrders() {
        return doctorsOrders;
    }

    public void setDoctorsOrders(boolean doctorsOrders) {
        this.doctorsOrders = doctorsOrders;
    }

    public boolean isNursingOutput() {
        return nursingOutput;
    }

    public void setNursingOutput(boolean nursingOutput) {
        this.nursingOutput = nursingOutput;
    }

    public boolean isMedicationChart() {
        return medicationChart;
    }

    public void setMedicationChart(boolean medicationChart) {
        this.medicationChart = medicationChart;
    }

    public boolean isPnChart() {
        return pnChart;
    }

    public void setPnChart(boolean pnChart) {
        this.pnChart = pnChart;
    }

    public boolean isDischargeSummary() {
        return dischargeSummary;
    }

    public void setDischargeSummary(boolean dischargeSummary) {
        this.dischargeSummary = dischargeSummary;
    }
}
