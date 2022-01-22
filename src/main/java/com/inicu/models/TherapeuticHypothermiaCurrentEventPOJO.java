package com.inicu.models;

import com.inicu.postgres.entities.ProcedureExchangeTransfusion;
import com.inicu.postgres.entities.TherapeuticHypothermia;

import java.util.ArrayList;
import java.util.List;

public class TherapeuticHypothermiaCurrentEventPOJO {

    private TherapeuticHypothermia therapeutic_hypothermia_object;

    List<TherapeuticHypothermia> allTherapeuticObjectList;


    public TherapeuticHypothermiaCurrentEventPOJO() {
        super();
        this.therapeutic_hypothermia_object = new TherapeuticHypothermia();
        this.allTherapeuticObjectList=new ArrayList<TherapeuticHypothermia>();
    }

    public List<TherapeuticHypothermia> getAllTherapeuticObjectList() {
        return allTherapeuticObjectList;
    }

    public void setAllTherapeuticObjectList(List<TherapeuticHypothermia> allTherapeuticObjectList) {
        this.allTherapeuticObjectList = allTherapeuticObjectList;
    }

    public TherapeuticHypothermia getTherapeutic_hypothermia_object() {
        return therapeutic_hypothermia_object;
    }

    public void setTherapeutic_hypothermia_object(TherapeuticHypothermia therapeutic_hypothermia_object) {
        this.therapeutic_hypothermia_object = therapeutic_hypothermia_object;
    }
}


