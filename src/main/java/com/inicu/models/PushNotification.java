package com.inicu.models;

public class PushNotification {


    private String uhid;
    private String message;
    private String timeType;
    private String response;
    private String delayString;
    private boolean delay;

    public PushNotification(){
        this.delay = false;
        this.message = "Okay String";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isDelay() {
        return delay;
    }

    public void setDelay(boolean delay) {
        this.delay = delay;
    }

    public String getDelayString() {
        return delayString;
    }

    public void setDelayString(String delayString) {
        this.delayString = delayString;
    }

    public String getUhid() {
        return uhid;
    }

    public void setUhid(String uhid) {
        this.uhid = uhid;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }
}
