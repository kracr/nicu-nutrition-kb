package com.inicu.models;

import java.util.ArrayList;
import java.util.List;

public class NIBPDataObject {

    List<Float> nbp_s;
    List<Float> nbp_d;
    List<Float> nbp_m;

    List<Float> nbpDiaGraph;
    List<Float> nbpMeanGraph;
    List<Float> nbpSysGraph;

    float minNbp_s;
    float minNbp_d;
    float minNbp_m;

    float maxNbp_s;
    float maxNbp_d;
    float maxNbp_m;

    public NIBPDataObject(){

        this.nbpDiaGraph=new ArrayList<>();
        this.nbpMeanGraph=new ArrayList<>();
        this.nbpSysGraph=new ArrayList<>();

        this.nbp_d=new ArrayList<>();
        this.nbp_s=new ArrayList<>();
        this.nbp_m=new ArrayList<>();

        this.minNbp_d=0;
        this.minNbp_m=0;
        this.minNbp_s=0;

        this.maxNbp_s=0;
        this.maxNbp_d=0;
        this.maxNbp_m=0;
    }


    public List<Float> getNbpDiaGraph() {
        return nbpDiaGraph;
    }

    public void setNbpDiaGraph(List<Float> nbpDiaGraph) {
        this.nbpDiaGraph = nbpDiaGraph;
    }

    public List<Float> getNbpMeanGraph() {
        return nbpMeanGraph;
    }

    public void setNbpMeanGraph(List<Float> nbpMeanGraph) {
        this.nbpMeanGraph = nbpMeanGraph;
    }

    public List<Float> getNbpSysGraph() {
        return nbpSysGraph;
    }

    public void setNbpSysGraph(List<Float> nbpSysGraph) {
        this.nbpSysGraph = nbpSysGraph;
    }

    public float getMinNbp_s() {
        return minNbp_s;
    }

    public void setMinNbp_s(float minNbp_s) {
        this.minNbp_s = minNbp_s;
    }

    public float getMinNbp_d() {
        return minNbp_d;
    }

    public void setMinNbp_d(float minNbp_d) {
        this.minNbp_d = minNbp_d;
    }

    public float getMinNbp_m() {
        return minNbp_m;
    }

    public void setMinNbp_m(float minNbp_m) {
        this.minNbp_m = minNbp_m;
    }

    public float getMaxNbp_s() {
        return maxNbp_s;
    }

    public void setMaxNbp_s(float maxNbp_s) {
        this.maxNbp_s = maxNbp_s;
    }

    public float getMaxNbp_d() {
        return maxNbp_d;
    }

    public void setMaxNbp_d(float maxNbp_d) {
        this.maxNbp_d = maxNbp_d;
    }

    public float getMaxNbp_m() {
        return maxNbp_m;
    }

    public void setMaxNbp_m(float maxNbp_m) {
        this.maxNbp_m = maxNbp_m;
    }

    public List<Float> getNbp_s() {
        return nbp_s;
    }

    public void setNbp_s(List<Float> nbp_s) {
        this.nbp_s = nbp_s;
    }

    public List<Float> getNbp_d() {
        return nbp_d;
    }

    public void setNbp_d(List<Float> nbp_d) {
        this.nbp_d = nbp_d;
    }

    public List<Float> getNbp_m() {
        return nbp_m;
    }

    public void setNbp_m(List<Float> nbp_m) {
        this.nbp_m = nbp_m;
    }
}
