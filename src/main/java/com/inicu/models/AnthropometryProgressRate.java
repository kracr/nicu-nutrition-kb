package com.inicu.models;

public class AnthropometryProgressRate {

    private float weightGrowthRate;
    private float lengthGrowthRate;
    private float headCircumferenceRate;
    private String tempString;

    public AnthropometryProgressRate(){
        this.weightGrowthRate=0;
        this.lengthGrowthRate=0;
        this.headCircumferenceRate=0;
        this.tempString="";
    }


    public String getTempString() {
        return tempString;
    }

    public void setTempString(String tempString) {
        this.tempString = tempString;
    }

    public float getWeightGrowthRate() {
        return weightGrowthRate;
    }

    public void setWeightGrowthRate(float weightGrowthRate) {
        this.weightGrowthRate = weightGrowthRate;
    }

    public float getLengthGrowthRate() {
        return lengthGrowthRate;
    }

    public void setLengthGrowthRate(float lengthGrowthRate) {
        this.lengthGrowthRate = lengthGrowthRate;
    }

    public float getHeadCircumferenceRate() {
        return headCircumferenceRate;
    }

    public void setHeadCircumferenceRate(float headCircumferenceRate) {
        this.headCircumferenceRate = headCircumferenceRate;
    }
}


