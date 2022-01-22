package com.inicu.models;

import java.util.ArrayList;
import java.util.List;

public class PatientChartEventsObject {

    List<String> seizures;
    List<String> apnea ;
    List<String> desaturation;

    public PatientChartEventsObject(){
        this.seizures=new ArrayList<>();
        this.apnea=new ArrayList<>();
        this.desaturation=new ArrayList<>();
    }

    public List<String> getSeizures() {
        return seizures;
    }

    public void setSeizures(List<String> seizures) {
        this.seizures = seizures;
    }

    public List<String> getApnea() {
        return apnea;
    }

    public void setApnea(List<String> apnea) {
        this.apnea = apnea;
    }

    public List<String> getDesaturation() {
        return desaturation;
    }

    public void setDesaturation(List<String> desaturation) {
        this.desaturation = desaturation;
    }
}
