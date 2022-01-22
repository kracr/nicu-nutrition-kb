package com.inicu.models;

import java.util.ArrayList;
import java.util.List;

public class PatientChartVentilatorObject {

    List<Float> spo2;
    List<Float> cft;
    List<Float> sfRatio;
    List<Float> pressureSupport;
    List<Float> peep;
    List<Float> ti;
    List<Float> te;
    List<Float> map;
    List<Float> fio2;
    List<Float> flow;
    List<Float> frequency;
    List<Float> minVolumne;
    List<Float> tidalVolumne;
    List<Float> dco2;
    List<Float> deliveredTv;
    List<Float> rpm;
    List<Float> humidification;

    List<String> modeOfVentilation;


    public PatientChartVentilatorObject(){
        this.spo2=new ArrayList<>();
        this.cft=new ArrayList<>();
        this.sfRatio=new ArrayList<>();
        this.modeOfVentilation=new ArrayList<>();
        this.pressureSupport=new ArrayList<>();
        this.peep=new ArrayList<>();
        this.ti=new ArrayList<>();
        this.te=new ArrayList<>();
        this.map=new ArrayList<>();
        this.fio2=new ArrayList<>();
        this.flow=new ArrayList<>();
        this.frequency=new ArrayList<>();
        this.minVolumne=new ArrayList<>();
        this.tidalVolumne=new ArrayList<>();
        this.dco2=new ArrayList<>();
        this.deliveredTv=new ArrayList<>();
        this.rpm=new ArrayList<>();
        this.humidification=new ArrayList<>();
    }

    public List<Float> getCft() {
        return cft;
    }

    public void setCft(List<Float> cft) {
        this.cft = cft;
    }

    public List<Float> getSpo2() {
        return spo2;
    }

    public void setSpo2(List<Float> spo2) {
        this.spo2 = spo2;
    }

    public List<Float> getSfRatio() {
        return sfRatio;
    }

    public void setSfRatio(List<Float> sfRatio) {
        this.sfRatio = sfRatio;
    }

    public List<String> getModeOfVentilation() {
        return modeOfVentilation;
    }

    public void setModeOfVentilation(List<String> modeOfVentilation) {
        this.modeOfVentilation = modeOfVentilation;
    }

    public List<Float> getPressureSupport() {
        return pressureSupport;
    }

    public void setPressureSupport(List<Float> pressureSupport) {
        this.pressureSupport = pressureSupport;
    }

    public List<Float> getPeep() {
        return peep;
    }

    public void setPeep(List<Float> peep) {
        this.peep = peep;
    }

    public List<Float> getTi() {
        return ti;
    }

    public void setTi(List<Float> ti) {
        this.ti = ti;
    }

    public List<Float> getTe() {
        return te;
    }

    public void setTe(List<Float> te) {
        this.te = te;
    }

    public List<Float> getMap() {
        return map;
    }

    public void setMap(List<Float> map) {
        this.map = map;
    }

    public List<Float> getFio2() {
        return fio2;
    }

    public void setFio2(List<Float> fio2) {
        this.fio2 = fio2;
    }

    public List<Float> getFlow() {
        return flow;
    }

    public void setFlow(List<Float> flow) {
        this.flow = flow;
    }

    public List<Float> getFrequency() {
        return frequency;
    }

    public void setFrequency(List<Float> frequency) {
        this.frequency = frequency;
    }

    public List<Float> getMinVolumne() {
        return minVolumne;
    }

    public void setMinVolumne(List<Float> minVolumne) {
        this.minVolumne = minVolumne;
    }

    public List<Float> getTidalVolumne() {
        return tidalVolumne;
    }

    public void setTidalVolumne(List<Float> tidalVolumne) {
        this.tidalVolumne = tidalVolumne;
    }

    public List<Float> getDco2() {
        return dco2;
    }

    public void setDco2(List<Float> dco2) {
        this.dco2 = dco2;
    }

    public List<Float> getDeliveredTv() {
        return deliveredTv;
    }

    public void setDeliveredTv(List<Float> deliveredTv) {
        this.deliveredTv = deliveredTv;
    }

    public List<Float> getRpm() {
        return rpm;
    }

    public void setRpm(List<Float> rpm) {
        this.rpm = rpm;
    }

    public List<Float> getHumidification() {
        return humidification;
    }

    public void setHumidification(List<Float> humidification) {
        this.humidification = humidification;
    }
}
