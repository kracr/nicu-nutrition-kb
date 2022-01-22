package com.inicu.models;


import java.util.ArrayList;
import java.util.List;

public class PatientChartInvestigationObject {

    private List<Float> rbs;
    private List<Float> tcb;
    private List<Float> naPlus;
    private List<Float> kPlus;
    private List<Float> clMinus;
    private List<Float> caPlusPlus;
    private List<Float> anionGap;

    public PatientChartInvestigationObject(){
        this.rbs=new ArrayList<>();
        this.tcb=new ArrayList<>();
        this.naPlus=new ArrayList<>();
        this.kPlus=new ArrayList<>();
        this.clMinus=new ArrayList<>();
        this.caPlusPlus=new ArrayList<>();
        this.anionGap=new ArrayList<>();
    }

    public List<Float> getRbs() {
        return rbs;
    }

    public void setRbs(List<Float> rbs) {
        this.rbs = rbs;
    }

    public List<Float> getTcb() {
        return tcb;
    }

    public void setTcb(List<Float> tcb) {
        this.tcb = tcb;
    }

    public List<Float> getNaPlus() {
        return naPlus;
    }

    public void setNaPlus(List<Float> naPlus) {
        this.naPlus = naPlus;
    }

    public List<Float> getkPlus() {
        return kPlus;
    }

    public void setkPlus(List<Float> kPlus) {
        this.kPlus = kPlus;
    }

    public List<Float> getClMinus() {
        return clMinus;
    }

    public void setClMinus(List<Float> clMinus) {
        this.clMinus = clMinus;
    }

    public List<Float> getCaPlusPlus() {
        return caPlusPlus;
    }

    public void setCaPlusPlus(List<Float> caPlusPlus) {
        this.caPlusPlus = caPlusPlus;
    }

    public List<Float> getAnionGap() {
        return anionGap;
    }

    public void setAnionGap(List<Float> anionGap) {
        this.anionGap = anionGap;
    }
}


