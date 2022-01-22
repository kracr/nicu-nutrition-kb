package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.InvestigationOrdered;
import com.inicu.postgres.entities.SaSepsis;

public class SepsisPastEventPOJO {

	SaSepsis pastEventObj;

	Object pastEventObjList;

	List<InvestigationOrdered> pastInvestigationsList;

	public SaSepsis getPastEventObj() {
		return pastEventObj;
	}

	public void setPastEventObj(SaSepsis pastEventObj) {
		this.pastEventObj = pastEventObj;
	}

	public Object getPastEventObjList() {
		return pastEventObjList;
	}

	public void setPastEventObjList(Object pastEventObjList) {
		this.pastEventObjList = pastEventObjList;
	}

	public List<InvestigationOrdered> getPastInvestigationsList() {
		return pastInvestigationsList;
	}

	public void setPastInvestigationsList(List<InvestigationOrdered> pastInvestigationsList) {
		this.pastInvestigationsList = pastInvestigationsList;
	}
}
