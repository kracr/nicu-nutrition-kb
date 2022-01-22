package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.InvestigationOrdered;
import com.inicu.postgres.entities.SaInfectVap;

public class VapPastEventPOJO {
	SaInfectVap pastEventObj;

	Object pastEventObjList;

	List<InvestigationOrdered> pastInvestigationsList;

	public SaInfectVap getPastEventObj() {
		return pastEventObj;
	}

	public void setPastEventObj(SaInfectVap pastEventObj) {
		this.pastEventObj = pastEventObj;
	}

	public List<InvestigationOrdered> getPastInvestigationsList() {
		return pastInvestigationsList;
	}

	public void setPastInvestigationsList(List<InvestigationOrdered> pastInvestigationsList) {
		this.pastInvestigationsList = pastInvestigationsList;
	}

	public Object getPastEventObjList() {
		return pastEventObjList;
	}

	public void setPastEventObjList(Object pastEventObjList) {
		this.pastEventObjList = pastEventObjList;
	}
}
