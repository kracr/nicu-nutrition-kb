package com.inicu.models;

import java.util.ArrayList;
import java.util.List;

public class PatientChartBloodGasObject {

    List<Float> ph;
    List<Float> pco2;
    List<Float> etco2;
    List<Float> po2;
    List<Float> hco3;
    List<Float> be;
    List<Float> so2;
    List<Float> lactose;
    List<Float> hct;
    List<Float> tHbc;
    List<Float> osmolarity;
//    List<Float> oi;
//    List<Float> pf;

    public PatientChartBloodGasObject(){
        this.ph=new ArrayList<>();
        this.pco2=new ArrayList<>();
        this.etco2=new ArrayList<>();
        this.po2=new ArrayList<>();
        this.hco3=new ArrayList<>();
        this.be=new ArrayList<>();
        this.so2=new ArrayList<>();
        this.lactose=new ArrayList<>();
        this.hct=new ArrayList<>();
        this.tHbc=new ArrayList<>();
        this.osmolarity=new ArrayList<>();
//        this.oi=new ArrayList<>();
//        this.pf=new ArrayList<>();
    }


    public List<Float> getPh() {
        return ph;
    }

    public void setPh(List<Float> ph) {
        this.ph = ph;
    }

    public List<Float> getPco2() {
        return pco2;
    }

    public void setPco2(List<Float> pco2) {
        this.pco2 = pco2;
    }

    public List<Float> getEtco2() {
        return etco2;
    }

    public void setEtco2(List<Float> etco2) {
        this.etco2 = etco2;
    }

    public List<Float> getPo2() {
        return po2;
    }

    public void setPo2(List<Float> po2) {
        this.po2 = po2;
    }

    public List<Float> getHco3() {
        return hco3;
    }

    public void setHco3(List<Float> hco3) {
        this.hco3 = hco3;
    }

    public List<Float> getBe() {
        return be;
    }

    public void setBe(List<Float> be) {
        this.be = be;
    }

    public List<Float> getSo2() {
        return so2;
    }

    public void setSo2(List<Float> so2) {
        this.so2 = so2;
    }

    public List<Float> getLactose() {
        return lactose;
    }

    public void setLactose(List<Float> lactose) {
        this.lactose = lactose;
    }

    public List<Float> getHct() {
        return hct;
    }

    public void setHct(List<Float> hct) {
        this.hct = hct;
    }

    public List<Float> gettHbc() {
        return tHbc;
    }

    public void settHbc(List<Float> tHbc) {
        this.tHbc = tHbc;
    }

    public List<Float> getOsmolarity() {
        return osmolarity;
    }

    public void setOsmolarity(List<Float> osmolarity) {
        this.osmolarity = osmolarity;
    }

//    public List<Float> getOi() {
//        return oi;
//    }
//
//    public void setOi(List<Float> oi) {
//        this.oi = oi;
//    }
//
//    public List<Float> getPf() {
//        return pf;
//    }
//
//    public void setPf(List<Float> pf) {
//        this.pf = pf;
//    }
}
