package com.inicu.models;

import com.inicu.postgres.entities.BabyDetail;
import com.inicu.postgres.entities.DailyProgressNotes;
import com.inicu.postgres.entities.DischargeSummary;

public class PrintDataPOJO {

    private BabyDetail babyDetail;
    private AdvanceAdmitPatientPojo initialAssessment;
    private DailyProgressPOJO dailyNotes;
    private DischargeSummary dischargeSummary;

    public PrintDataPOJO(){
        this.dailyNotes=new DailyProgressPOJO();
    }

    public BabyDetail getBabyDetail() {
        return babyDetail;
    }

    public void setBabyDetail(BabyDetail babyDetail) {
        this.babyDetail = babyDetail;
    }

    public AdvanceAdmitPatientPojo getInitialAssessment() {
        return initialAssessment;
    }

    public void setInitialAssessment(AdvanceAdmitPatientPojo initialAssessment) {
        this.initialAssessment = initialAssessment;
    }

    public DailyProgressPOJO getDailyNotes() {
        return dailyNotes;
    }

    public void setDailyNotes(DailyProgressPOJO dailyNotes) {
        this.dailyNotes = dailyNotes;
    }

    public DischargeSummary getDischargeSummary() {
        return dischargeSummary;
    }

    public void setDischargeSummary(DischargeSummary dischargeSummary) {
        this.dischargeSummary = dischargeSummary;
    }
}
