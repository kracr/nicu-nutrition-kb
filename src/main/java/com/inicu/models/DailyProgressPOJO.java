package com.inicu.models;

import com.inicu.postgres.entities.DailyProgressNotes;
import com.inicu.postgres.entities.NursingOutput;

public class DailyProgressPOJO {

    private DailyProgressNotes doctorProgressNotes;
    private DoctorOrdersPOJO doctorOrders;
    private NursingAllDataPOJO nursingOutput;
    private BabyPrescriptionObject medicationChart;
    private TpnFeedPojo pnChart;

    public DailyProgressNotes getDoctorProgressNotes() {
        return doctorProgressNotes;
    }

    public void setDoctorProgressNotes(DailyProgressNotes doctorProgressNotes) {
        this.doctorProgressNotes = doctorProgressNotes;
    }

    public DoctorOrdersPOJO getDoctorOrders() {
        return doctorOrders;
    }

    public void setDoctorOrders(DoctorOrdersPOJO doctorOrders) {
        this.doctorOrders = doctorOrders;
    }

    public NursingAllDataPOJO getNursingOutput() {
        return nursingOutput;
    }

    public void setNursingOutput(NursingAllDataPOJO nursingOutput) {
        this.nursingOutput = nursingOutput;
    }

    public BabyPrescriptionObject getMedicationChart() {
        return medicationChart;
    }

    public void setMedicationChart(BabyPrescriptionObject medicationChart) {
        this.medicationChart = medicationChart;
    }

    public TpnFeedPojo getPnChart() {
        return pnChart;
    }

    public void setPnChart(TpnFeedPojo pnChart) {
        this.pnChart = pnChart;
    }
}
