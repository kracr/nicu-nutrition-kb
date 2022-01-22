package com.inicu.models;

import java.util.ArrayList;
import java.util.List;

public class PatientChartVitalObject {

    List<String> positionList;
    List<String> babyColorList;
    List<String> consciousnessList;
    List<String> leftPupil;
    List<String> rightPupil;


    public PatientChartVitalObject(){
        this.positionList=new ArrayList<>();
        this.babyColorList=new ArrayList<>();
        this.consciousnessList=new ArrayList<>();
        this.leftPupil=new ArrayList<>();
        this.rightPupil=new ArrayList<>();

    }

    public List<String> getPositionList() {
        return positionList;
    }

    public void setPositionList(List<String> positionList) {
        this.positionList = positionList;
    }

    public List<String> getBabyColorList() {
        return babyColorList;
    }

    public void setBabyColorList(List<String> babyColorList) {
        this.babyColorList = babyColorList;
    }

    public List<String> getConsciousnessList() {
        return consciousnessList;
    }

    public void setConsciousnessList(List<String> consciousnessList) {
        this.consciousnessList = consciousnessList;
    }

    public List<String> getLeftPupil() {
        return leftPupil;
    }

    public void setLeftPupil(List<String> leftPupil) {
        this.leftPupil = leftPupil;
    }

    public List<String> getRightPupil() {
        return rightPupil;
    }

    public void setRightPupil(List<String> rightPupil) {
        this.rightPupil = rightPupil;
    }
}
