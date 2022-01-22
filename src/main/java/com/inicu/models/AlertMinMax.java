package com.inicu.models;

import java.util.List;

public class AlertMinMax {

    private int minValue;
    private int maxValue;
    private String dependencies;

    public AlertMinMax(){
        this.minValue=-100;
        this.maxValue=-100;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public String getDependencies() {
        return dependencies;
    }

    public void setDependencies(String dependencies) {
        this.dependencies = dependencies;
    }
}
