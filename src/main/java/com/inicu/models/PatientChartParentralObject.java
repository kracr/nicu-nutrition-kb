package com.inicu.models;

import java.util.ArrayList;
import java.util.List;

public class PatientChartParentralObject {

    private List<Float> administeredFeedVolume;
    private List<Float> intraLipid;
    private List<Float> calcium;
    private List<Float> bloodProduct;
    private List<Float> heparin;

    public PatientChartParentralObject(){
        this.administeredFeedVolume=new ArrayList<>();
        this.intraLipid=new ArrayList<>();
        this.calcium=new ArrayList<>();
        this.bloodProduct=new ArrayList<>();
        this.heparin=new ArrayList<>();
    }

    public List<Float> getBloodProduct() {
        return bloodProduct;
    }

    public void setBloodProduct(List<Float> bloodProduct) {
        this.bloodProduct = bloodProduct;
    }

    public List<Float> getHeparin() {
        return heparin;
    }

    public void setHeparin(List<Float> heparin) {
        this.heparin = heparin;
    }

    public List<Float> getAdministeredFeedVolume() {
        return administeredFeedVolume;
    }

    public void setAdministeredFeedVolume(List<Float> administeredFeedVolume) {
        this.administeredFeedVolume = administeredFeedVolume;
    }

    public List<Float> getIntraLipid() {
        return intraLipid;
    }

    public void setIntraLipid(List<Float> intraLipid) {
        this.intraLipid = intraLipid;
    }

    public List<Float> getCalcium() {
        return calcium;
    }

    public void setCalcium(List<Float> calcium) {
        this.calcium = calcium;
    }
}
