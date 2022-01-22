package com.inicu.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.inicu.postgres.entities.BabyPrescription;
import com.inicu.postgres.entities.RespSupport;
import com.inicu.postgres.entities.SaRespPphn;

public class PphnPOJO {

	private SaRespPphn currentPphn;

	private List<SaRespPphn> pastPphn;

	private BabyPrescription SildenafilObj;

	private List<BabyPrescription> prescriptionList;

	private RespSupport respSupport;

	private HashMap<String, RespSupport> pastRespSupport;
	
	//Not in use anymore
	//private List<String> associatedEvents;

	private String inactiveProgressNote;

	public PphnPOJO() {
		super();
		currentPphn = new SaRespPphn();
		currentPphn.setMethaemoglobinLevel("NA");
		SildenafilObj = new BabyPrescription();
		this.prescriptionList = new ArrayList<BabyPrescription>();
		respSupport = new RespSupport();
		pastRespSupport = new HashMap<String, RespSupport>();
	}

	public SaRespPphn getCurrentPphn() {
		return currentPphn;
	}

	public void setCurrentPphn(SaRespPphn currentPphn) {
		this.currentPphn = currentPphn;
	}

	public List<SaRespPphn> getPastPphn() {
		return pastPphn;
	}

	public void setPastPphn(List<SaRespPphn> pastPphn) {
		this.pastPphn = pastPphn;
	}

	public BabyPrescription getSildenafilObj() {
		return SildenafilObj;
	}

	public void setSildenafilObj(BabyPrescription sildenafilObj) {
		SildenafilObj = sildenafilObj;
	}

	public List<BabyPrescription> getPrescriptionList() {
		return prescriptionList;
	}

	public void setPrescriptionList(List<BabyPrescription> prescriptionList) {
		this.prescriptionList = prescriptionList;
	}

	public RespSupport getRespSupport() {
		return respSupport;
	}

	public void setRespSupport(RespSupport respSupport) {
		this.respSupport = respSupport;
	}

	public HashMap<String, RespSupport> getPastRespSupport() {
		return pastRespSupport;
	}

	public void setPastRespSupport(HashMap<String, RespSupport> pastRespSupport) {
		this.pastRespSupport = pastRespSupport;
	}

//	public List<String> getAssociatedEvents() {
//		return associatedEvents;
//	}
//
//	public void setAssociatedEvents(List<String> associatedEvents) {
//		this.associatedEvents = associatedEvents;
//	}


	public String getInactiveProgressNote() {
		return inactiveProgressNote;
	}

	public void setInactiveProgressNote(String inactiveProgressNote) {
		this.inactiveProgressNote = inactiveProgressNote;
	}

	@Override
	public String toString() {
		return "PphnPOJO [currentPphn=" + currentPphn + ", pastPphn=" + pastPphn + ", SildenafilObj=" + SildenafilObj
				+ ", prescriptionList=" + prescriptionList + ", respSupport=" + respSupport + ", pastRespSupport="
				+ pastRespSupport + "]";
	}

}
