package com.inicu.models;

import java.util.ArrayList;
import java.util.List;

public class PatientChartEnteralObject {


    private List<String> feedMethod;
    private List<String> feedType;
    private List<Float> feedVolume;


    public PatientChartEnteralObject(){
        this.feedMethod=new ArrayList<>();
        this.feedType=new ArrayList<>();
        this.feedVolume=new ArrayList<>();

    }

    public List<String> getFeedMethod() {
        return feedMethod;
    }

    public void setFeedMethod(List<String> feedMethod) {
        this.feedMethod = feedMethod;
    }

    public List<String> getFeedType() {
        return feedType;
    }

    public void setFeedType(List<String> feedType) {
        this.feedType = feedType;
    }

    public List<Float> getFeedVolume() {
        return feedVolume;
    }

    public void setFeedVolume(List<Float> feedVolume) {
        this.feedVolume = feedVolume;
    }
}
