package com.inicu.models;


import com.inicu.postgres.entities.SaCnsEncephalopathy;

public class EncephalopathyCurrentEventPOJO {
    SaCnsEncephalopathy currentEncephalopathy;

    public EncephalopathyCurrentEventPOJO() {
        super();
        this.currentEncephalopathy = new SaCnsEncephalopathy();
    }

    public SaCnsEncephalopathy getCurrentEncephalopathy() {
        return currentEncephalopathy;
    }

    public void setCurrentEncephalopathy(SaCnsEncephalopathy currentEncephalopathy) {
        this.currentEncephalopathy = currentEncephalopathy;
    }
}
