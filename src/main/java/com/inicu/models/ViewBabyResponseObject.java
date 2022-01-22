package com.inicu.models;

public class ViewBabyResponseObject {
    private int statusCode;
    private String message;

    private Boolean isVideoAvailable;
    private Boolean isTimingMatching;
    private Boolean admissionStatus;
    private String liveVideoUrl;

    public ViewBabyResponseObject() { }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Boolean getVideoAvailable() {
        return isVideoAvailable;
    }

    public void setVideoAvailable(Boolean videoAvailable) {
        isVideoAvailable = videoAvailable;
    }

    public String getLiveVideoUrl() {
        return liveVideoUrl;
    }

    public void setLiveVideoUrl(String liveVideoUrl) {
        this.liveVideoUrl = liveVideoUrl;
    }

    public Boolean getTimingMatching() {
        return isTimingMatching;
    }

    public void setTimingMatching(Boolean timingMatching) {
        isTimingMatching = timingMatching;
    }

    public Boolean getAdmissionStatus() {
        return admissionStatus;
    }

    public void setAdmissionStatus(Boolean admissionStatus) {
        this.admissionStatus = admissionStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


