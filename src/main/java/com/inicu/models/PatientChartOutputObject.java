package com.inicu.models;

import java.util.ArrayList;
import java.util.List;

public class PatientChartOutputObject {

    private List<Float> totalOutputVolumne;
    private List<Float> AbdGirth;
    private List<Float> urine;
    private List<String> stool;
    private List<String> bloodInStool;
    private List<Float> gastricAspirate;
    private List<String> aspirateColor;
    private List<Float> stoma;

    public  PatientChartOutputObject(){
        this.totalOutputVolumne=new ArrayList<>();
        this.AbdGirth=new ArrayList<>();
        this.urine=new ArrayList<>();
        this.stool=new ArrayList<>();
        this.bloodInStool=new ArrayList<>();
        this.gastricAspirate=new ArrayList<>();
        this.aspirateColor=new ArrayList<>();
        this.stoma=new ArrayList<>();
    }

    public List<Float> getStoma() {
        return stoma;
    }

    public void setStoma(List<Float> stoma) {
        this.stoma = stoma;
    }

    public List<Float> getTotalOutputVolumne() {
        return totalOutputVolumne;
    }

    public void setTotalOutputVolumne(List<Float> totalOutputVolumne) {
        this.totalOutputVolumne = totalOutputVolumne;
    }

    public List<Float> getAbdGirth() {
        return AbdGirth;
    }

    public void setAbdGirth(List<Float> abdGirth) {
        AbdGirth = abdGirth;
    }

    public List<Float> getUrine() {
        return urine;
    }

    public void setUrine(List<Float> urine) {
        this.urine = urine;
    }

    public List<String> getStool() {
        return stool;
    }

    public void setStool(List<String> stool) {
        this.stool = stool;
    }

    public List<String> getBloodInStool() {
        return bloodInStool;
    }

    public void setBloodInStool(List<String> bloodInStool) {
        this.bloodInStool = bloodInStool;
    }

    public List<Float> getGastricAspirate() {
        return gastricAspirate;
    }

    public void setGastricAspirate(List<Float> gastricAspirate) {
        this.gastricAspirate = gastricAspirate;
    }

    public List<String> getAspirateColor() {
        return aspirateColor;
    }

    public void setAspirateColor(List<String> aspirateColor) {
        this.aspirateColor = aspirateColor;
    }
}
