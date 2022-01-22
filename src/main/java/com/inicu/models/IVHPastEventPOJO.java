package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.InvestigationOrdered;

public class IVHPastEventPOJO {

	Object pastIvhEvent;

	List<InvestigationOrdered> pastInvestigations;
	
	public Object getPastIvhEvent() {
		return pastIvhEvent;
	}

	public void setPastIvhEvent(Object pastIvhEvent) {
		this.pastIvhEvent = pastIvhEvent;
	}

	public List<InvestigationOrdered> getPastInvestigations() {
		return pastInvestigations;
	}

	public void setPastInvestigations(List<InvestigationOrdered> pastInvestigations) {
		this.pastInvestigations = pastInvestigations;
	}
}
