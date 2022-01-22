package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.InvestigationOrdered;
import com.inicu.postgres.entities.SaInfectClabsi;

public class ClabsiPastEventPOJO {
	SaInfectClabsi pastEventObj;

	Object pastEventObjList;

	List<InvestigationOrdered> pastInvestigationsList;

	public SaInfectClabsi getPastEventObj() {
		return pastEventObj;
	}

	public void setPastEventObj(SaInfectClabsi pastEventObj) {
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
