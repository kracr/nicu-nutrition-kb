package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.RefOphthalmologist;
import com.inicu.postgres.entities.ScreenRop;

public class RopMasterPojo {

	ScreenRop currentObj;

	List<ScreenRop> pastRopList;

	List<KeyValueObj> treatmentAction;
	
	List<RefOphthalmologist> ophthalmologistNameList;

	public RopMasterPojo() {
		super();
		this.currentObj = new ScreenRop();
	}

	public ScreenRop getCurrentObj() {
		return currentObj;
	}

	public void setCurrentObj(ScreenRop currentObj) {
		this.currentObj = currentObj;
	}

	public List<ScreenRop> getPastRopList() {
		return pastRopList;
	}

	public void setPastRopList(List<ScreenRop> pastRopList) {
		this.pastRopList = pastRopList;
	}

	public List<KeyValueObj> getTreatmentAction() {
		return treatmentAction;
	}

	public void setTreatmentAction(List<KeyValueObj> treatmentAction) {
		this.treatmentAction = treatmentAction;
	}

	public List<RefOphthalmologist> getOphthalmologistNameList() {
		return ophthalmologistNameList;
	}

	public void setOphthalmologistNameList(List<RefOphthalmologist> ophthalmologistNameList) {
		this.ophthalmologistNameList = ophthalmologistNameList;
	}

	@Override
	public String toString() {
		return "RopMasterPojo [currentObj=" + currentObj + ", pastRopList=" + pastRopList + ", treatmentAction="
				+ treatmentAction + ", ophthalmologistNameList=" + ophthalmologistNameList + "]";
	}

}
