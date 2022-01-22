package com.inicu.models;

import com.inicu.postgres.entities.InvestigationOrdered;

import java.util.List;

public class IntrauterinePastEventPOJO {
	
	Object pastEventObj;
	List<InvestigationOrdered> pastInvestigationsList;

	public Object getPastEventObj() {
		return pastEventObj;
	}

	public void setPastEventObj(Object pastEventObj) {
		this.pastEventObj = pastEventObj;
	}

	public List<InvestigationOrdered> getPastInvestigationsList() {
		return pastInvestigationsList;
	}

	public void setPastInvestigationsList(List<InvestigationOrdered> pastInvestigationsList) {
		this.pastInvestigationsList = pastInvestigationsList;
	}
}
