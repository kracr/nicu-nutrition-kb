package com.inicu.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.inicu.postgres.entities.BabyPrescription;
import com.inicu.postgres.entities.RespSupport;
import com.inicu.postgres.entities.SaRespRds;

public class RespiratoryDistressPOJO {

	private SaRespRds currentRespDistress;

	private List<SaRespRds> pastRespDistress;

	private BabyPrescription babyPrescriptionEmptyObj;

	private List<BabyPrescription> prescriptionList;

	private List<RespDistressGraph> respDistressGraph;

	private HashMap<String, RespSupport> pastRespSupport;

	private String inactiveProgressNote;

	//Not in use anymore
	//private List<String> associatedEvents;

	public RespiratoryDistressPOJO() {
		super();
		this.currentRespDistress = new SaRespRds();
		babyPrescriptionEmptyObj = new BabyPrescription();
		prescriptionList = new ArrayList<BabyPrescription>();
		pastRespSupport = new HashMap<String, RespSupport>();
	}

	public SaRespRds getCurrentRespDistress() {
		return currentRespDistress;
	}

	public List<SaRespRds> getPastRespDistress() {
		return pastRespDistress;
	}

	public void setCurrentRespDistress(SaRespRds currentRespDistress) {
		this.currentRespDistress = currentRespDistress;
	}

	public void setPastRespDistress(List<SaRespRds> pastRespDistress) {
		this.pastRespDistress = pastRespDistress;
	}

	public BabyPrescription getBabyPrescriptionEmptyObj() {
		return babyPrescriptionEmptyObj;
	}

	public List<BabyPrescription> getPrescriptionList() {
		return prescriptionList;
	}

	public void setBabyPrescriptionEmptyObj(BabyPrescription babyPrescriptionEmptyObj) {
		this.babyPrescriptionEmptyObj = babyPrescriptionEmptyObj;
	}

	public void setPrescriptionList(List<BabyPrescription> prescriptionList) {
		this.prescriptionList = prescriptionList;
	}

	public List<RespDistressGraph> getRespDistressGraph() {
		return respDistressGraph;
	}

	public void setRespDistressGraph(List<RespDistressGraph> respDistressGraph) {
		this.respDistressGraph = respDistressGraph;
	}

	public HashMap<String, RespSupport> getPastRespSupport() {
		return pastRespSupport;
	}

	public void setPastRespSupport(HashMap<String, RespSupport> pastRespSupport) {
		this.pastRespSupport = pastRespSupport;
	}

	public String getInactiveProgressNote() {
		return inactiveProgressNote;
	}

	public void setInactiveProgressNote(String inactiveProgressNote) {
		this.inactiveProgressNote = inactiveProgressNote;
	}

	//	public List<String> getAssociatedEvents() {
//		return associatedEvents;
//	}
//
//	public void setAssociatedEvents(List<String> associatedEvents) {
//		this.associatedEvents = associatedEvents;
//	}

}
