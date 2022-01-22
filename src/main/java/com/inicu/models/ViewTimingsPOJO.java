package com.inicu.models;

public class ViewTimingsPOJO {

    private String fromTime;
    private String toTime;

    public ViewTimingsPOJO(){

    }

    public ViewTimingsPOJO(String fromTime, String toTime) {
        this.fromTime = fromTime;
        this.toTime = toTime;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }
}
