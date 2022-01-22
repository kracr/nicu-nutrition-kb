package com.inicu.models;

import com.inicu.postgres.entities.BabyPrescription;
import com.inicu.postgres.entities.SaNec;

import java.util.ArrayList;
import java.util.List;

public class NecEventPOJO {

    SaNec currentEvent;
    NecPastEventPOJO pastEvents;
    List<BabyPrescription> prescriptionList;
    String inactiveProgressNote;

    public NecEventPOJO() {
        super();
        this.currentEvent = new SaNec();
        this.pastEvents = new NecPastEventPOJO();
        this.prescriptionList=new ArrayList<BabyPrescription>();
    }

    public SaNec getCurrentEvent() {
        return currentEvent;
    }

    public void setCurrentEvent(SaNec currentEvent) {
        this.currentEvent = currentEvent;
    }

    public NecPastEventPOJO getPastEvents() {
        return pastEvents;
    }

    public void setPastEvents(NecPastEventPOJO pastEvents) {
        this.pastEvents = pastEvents;
    }

    public List<BabyPrescription> getPrescriptionList() {
        return prescriptionList;
    }

    public void setPrescriptionList(List<BabyPrescription> prescriptionList) {
        this.prescriptionList = prescriptionList;
    }

	public String getInactiveProgressNote() {
		return inactiveProgressNote;
	}

	public void setInactiveProgressNote(String inactiveProgressNote) {
		this.inactiveProgressNote = inactiveProgressNote;
	}
}
