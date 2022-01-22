package com.inicu.models;

import java.util.ArrayList;
import java.util.List;

public class PatientGraphData {

    List<Float> centralTemperature;
    List<Float> peripheralTemperature;
    List<Float> hr;
    List<Float> cvp;
    List<Float> rr;

    NIBPDataObject nibp;
    IBPDataObject ibp;

    float minCentralTemperature;
    float minPeripheralTemperature;
    float minHr;
    float minCvp;
    float minRr;

    float maxCentralTemperature;
    float maxPeripheralTemperature;
    float maxHr;
    float maxCvp;
    float maxRr;

    public PatientGraphData(){

        // setting the initial values
        this.maxCentralTemperature=45;
        this.minCentralTemperature=25;

        this.maxPeripheralTemperature=45;
        this.minPeripheralTemperature=25;

        this.nibp=new NIBPDataObject();
        this.ibp=new IBPDataObject();

        this.centralTemperature=new ArrayList<>();
        this.peripheralTemperature=new ArrayList<>();

        this.hr=new ArrayList<>();
        this.cvp=new ArrayList<>();
        this.rr=new ArrayList<>();


    }

    public float getMinHr() {
        return minHr;
    }

    public void setMinHr(float minHr) {
        this.minHr = minHr;
    }

    public float getMinCvp() {
        return minCvp;
    }

    public void setMinCvp(float minCvp) {
        this.minCvp = minCvp;
    }

    public float getMinRr() {
        return minRr;
    }

    public void setMinRr(float minRr) {
        this.minRr = minRr;
    }

    public float getMaxHr() {
        return maxHr;
    }

    public void setMaxHr(float maxHr) {
        this.maxHr = maxHr;
    }

    public float getMaxCvp() {
        return maxCvp;
    }

    public void setMaxCvp(float maxCvp) {
        this.maxCvp = maxCvp;
    }

    public float getMaxRr() {
        return maxRr;
    }

    public void setMaxRr(float maxRr) {
        this.maxRr = maxRr;
    }

    public float getMinCentralTemperature() {
        return minCentralTemperature;
    }

    public void setMinCentralTemperature(float minCentralTemperature) {
        this.minCentralTemperature = minCentralTemperature;
    }

    public float getMinPeripheralTemperature() {
        return minPeripheralTemperature;
    }

    public void setMinPeripheralTemperature(float minPeripheralTemperature) {
        this.minPeripheralTemperature = minPeripheralTemperature;
    }

    public float getMaxCentralTemperature() {
        return maxCentralTemperature;
    }

    public void setMaxCentralTemperature(float maxCentralTemperature) {
        this.maxCentralTemperature = maxCentralTemperature;
    }

    public float getMaxPeripheralTemperature() {
        return maxPeripheralTemperature;
    }

    public void setMaxPeripheralTemperature(float maxPeripheralTemperature) {
        this.maxPeripheralTemperature = maxPeripheralTemperature;
    }


    public List<Float> getCentralTemperature() {
        return centralTemperature;
    }

    public void setCentralTemperature(List<Float> centralTemperature) {
        this.centralTemperature = centralTemperature;
    }

    public List<Float> getPeripheralTemperature() {
        return peripheralTemperature;
    }

    public void setPeripheralTemperature(List<Float> peripheralTemperature) {
        this.peripheralTemperature = peripheralTemperature;
    }

    public List<Float> getHr() {
        return hr;
    }

    public void setHr(List<Float> hr) {
        this.hr = hr;
    }

    public List<Float> getCvp() {
        return cvp;
    }

    public void setCvp(List<Float> cvp) {
        this.cvp = cvp;
    }

    public List<Float> getRr() {
        return rr;
    }

    public void setRr(List<Float> rr) {
        this.rr = rr;
    }

    public NIBPDataObject getNibp() {
        return nibp;
    }

    public void setNibp(NIBPDataObject nibp) {
        this.nibp = nibp;
    }

    public IBPDataObject getIbp() {
        return ibp;
    }

    public void setIbp(IBPDataObject ibp) {
        this.ibp = ibp;
    }
}
