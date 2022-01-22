package com.inicu.models;

import com.inicu.postgres.entities.ClinicalAlertSettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VitalParamtersResponse {

    private Boolean status;
    private String message;
    private Integer statusCode;

    private PatientChartData patientChartData;
    private PatientGraphData patientGraphData;
    private HashMap<String,AlertMinMax> clinicalAlertSettingsList;

    public VitalParamtersResponse(){
        this.status=true;
        this.message="Patient Chart";
        this.statusCode=200;
        this.patientChartData=new PatientChartData();
        this.patientGraphData=new PatientGraphData();
        this.clinicalAlertSettingsList=new HashMap<>();
    }

    public HashMap<String, AlertMinMax> getClinicalAlertSettingsList() {
        return clinicalAlertSettingsList;
    }

    public void setClinicalAlertSettingsList(HashMap<String, AlertMinMax> clinicalAlertSettingsList) {
        this.clinicalAlertSettingsList = clinicalAlertSettingsList;
    }

    public PatientChartData getPatientChartData() {
        return patientChartData;
    }

    public void setPatientChartData(PatientChartData patientChartData) {
        this.patientChartData = patientChartData;
    }

    public PatientGraphData getPatientGraphData() {
        return patientGraphData;
    }

    public void setPatientGraphData(PatientGraphData patientGraphData) {
        this.patientGraphData = patientGraphData;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

}
