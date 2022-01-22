package com.inicu.models;

public class NotificationObject {

    private String uhid;
    private String morningFromTime;
    private String eveningFromTime;

    private Boolean morningTimeEnabled;
    private Boolean eveningTimeEnabled;

    private String branchName;

    public NotificationObject(){ }

    public String getUhid() {
        return uhid;
    }

    public void setUhid(String uhid) {
        this.uhid = uhid;
    }

    public String getMorningFromTime() {
        return morningFromTime;
    }

    public void setMorningFromTime(String morningFromTime) {
        this.morningFromTime = morningFromTime;
    }

    public String getEveningFromTime() {
        return eveningFromTime;
    }

    public void setEveningFromTime(String eveningFromTime) {
        this.eveningFromTime = eveningFromTime;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Boolean getMorningTimeEnabled() {
        return morningTimeEnabled;
    }

    public void setMorningTimeEnabled(Boolean morningTimeEnabled) {
        this.morningTimeEnabled = morningTimeEnabled;
    }

    public Boolean getEveningTimeEnabled() {
        return eveningTimeEnabled;
    }

    public void setEveningTimeEnabled(Boolean eveningTimeEnabled) {
        this.eveningTimeEnabled = eveningTimeEnabled;
    }
}
