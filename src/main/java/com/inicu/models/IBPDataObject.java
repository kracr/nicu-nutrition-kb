package com.inicu.models;

import java.util.ArrayList;
import java.util.List;

public class IBPDataObject {

    List<Float> ibp_s;
    List<Float> ibp_d;
    List<Float> ibp_m;

    List<Float> ibpDiaGraph;
    List<Float> ibpMeanGraph;
    List<Float> ibpSysGraph;

    float minIbp_s;
    float minIbp_d;
    float minIbp_m;

    float maxIbp_s;
    float maxIbp_d;
    float maxIbp_m;

    public IBPDataObject() {
        this.ibp_s =new ArrayList<>();
        this.ibp_d = new ArrayList<>();
        this.ibp_m = new ArrayList<>();

        this.ibpDiaGraph=new ArrayList<>();
        this.ibpMeanGraph=new ArrayList<>();
        this.ibpSysGraph=new ArrayList<>();

        this.minIbp_d=0;
        this.minIbp_m=0;
        this.minIbp_s=0;

        this.maxIbp_s=0;
        this.maxIbp_d=0;
        this.maxIbp_m=0;
    }

    public List<Float> getIbpDiaGraph() {
        return ibpDiaGraph;
    }

    public void setIbpDiaGraph(List<Float> ibpDiaGraph) {
        this.ibpDiaGraph = ibpDiaGraph;
    }

    public List<Float> getIbpMeanGraph() {
        return ibpMeanGraph;
    }

    public void setIbpMeanGraph(List<Float> ibpMeanGraph) {
        this.ibpMeanGraph = ibpMeanGraph;
    }

    public List<Float> getIbpSysGraph() {
        return ibpSysGraph;
    }

    public void setIbpSysGraph(List<Float> ibpSysGraph) {
        this.ibpSysGraph = ibpSysGraph;
    }

    public float getMinIbp_s() {
        return minIbp_s;
    }

    public void setMinIbp_s(float minIbp_s) {
        this.minIbp_s = minIbp_s;
    }

    public float getMinIbp_d() {
        return minIbp_d;
    }

    public void setMinIbp_d(float minIbp_d) {
        this.minIbp_d = minIbp_d;
    }

    public float getMinIbp_m() {
        return minIbp_m;
    }

    public void setMinIbp_m(float minIbp_m) {
        this.minIbp_m = minIbp_m;
    }

    public float getMaxIbp_s() {
        return maxIbp_s;
    }

    public void setMaxIbp_s(float maxIbp_s) {
        this.maxIbp_s = maxIbp_s;
    }

    public float getMaxIbp_d() {
        return maxIbp_d;
    }

    public void setMaxIbp_d(float maxIbp_d) {
        this.maxIbp_d = maxIbp_d;
    }

    public float getMaxIbp_m() {
        return maxIbp_m;
    }

    public void setMaxIbp_m(float maxIbp_m) {
        this.maxIbp_m = maxIbp_m;
    }

    public List<Float> getIbp_s() {
        return ibp_s;
    }

    public void setIbp_s(List<Float> ibp_s) {
        this.ibp_s = ibp_s;
    }

    public List<Float> getIbp_d() {
        return ibp_d;
    }

    public void setIbp_d(List<Float> ibp_d) {
        this.ibp_d = ibp_d;
    }

    public List<Float> getIbp_m() {
        return ibp_m;
    }

    public void setIbp_m(List<Float> ibp_m) {
        this.ibp_m = ibp_m;
    }
}
