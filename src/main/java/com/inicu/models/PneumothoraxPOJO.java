package com.inicu.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.inicu.postgres.entities.BabyPrescription;
import com.inicu.postgres.entities.RespSupport;
import com.inicu.postgres.entities.SaRespPneumo;

public class PneumothoraxPOJO {

	private SaRespPneumo currentRespPneumo;

	private List<SaRespPneumo> pastRespPneumo;

	private BabyPrescription babyPrescriptionEmptyObj;

	private List<BabyPrescription> prescriptionList;

	private RespSupport respSupport;

	private HashMap<String, RespSupport> pastRespSupport;

	private ChestTubePOJO chestTubeObj;

	private List<String> associatedEvents;

	private String inactiveProgressNote;

	public PneumothoraxPOJO() {
		super();
		this.currentRespPneumo = new SaRespPneumo();
		this.babyPrescriptionEmptyObj = new BabyPrescription();
		respSupport = new RespSupport();
		this.prescriptionList = new ArrayList<BabyPrescription>();
		pastRespSupport = new HashMap<String, RespSupport>();
		chestTubeObj = new ChestTubePOJO();
	}

	public SaRespPneumo getCurrentRespPneumo() {
		return currentRespPneumo;
	}

	public void setCurrentRespPneumo(SaRespPneumo currentRespPneumo) {
		this.currentRespPneumo = currentRespPneumo;
	}

	public List<SaRespPneumo> getPastRespPneumo() {
		return pastRespPneumo;
	}

	public void setPastRespPneumo(List<SaRespPneumo> pastRespPneumo) {
		this.pastRespPneumo = pastRespPneumo;
	}

	public BabyPrescription getBabyPrescriptionEmptyObj() {
		return babyPrescriptionEmptyObj;
	}

	public void setBabyPrescriptionEmptyObj(BabyPrescription babyPrescriptionEmptyObj) {
		this.babyPrescriptionEmptyObj = babyPrescriptionEmptyObj;
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

	public List<String> getAssociatedEvents() {
		return associatedEvents;
	}

	public void setAssociatedEvents(List<String> associatedEvents) {
		this.associatedEvents = associatedEvents;
	}

	public ChestTubePOJO getChestTubeObj() {
		return chestTubeObj;
	}

	public void setChestTubeObj(ChestTubePOJO chestTubeObj) {
		this.chestTubeObj = chestTubeObj;
	}

	public String getInactiveProgressNote() {
		return inactiveProgressNote;
	}

	public void setInactiveProgressNote(String inactiveProgressNote) {
		this.inactiveProgressNote = inactiveProgressNote;
	}
}
