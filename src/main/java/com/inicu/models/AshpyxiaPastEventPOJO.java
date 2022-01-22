package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.InvestigationOrdered;

public class AshpyxiaPastEventPOJO {
	
	Object pastAsphyxiaEvent;

	List<InvestigationOrdered> pastInvestigations;

	public Object getPastAsphyxiaEvent() {
		return pastAsphyxiaEvent;
	}

	public void setPastAsphyxiaEvent(Object pastAsphyxiaEvent) {
		this.pastAsphyxiaEvent = pastAsphyxiaEvent;
	}

	public List<InvestigationOrdered> getPastInvestigations() {
		return pastInvestigations;
	}

	public void setPastInvestigations(List<InvestigationOrdered> pastInvestigations) {
		this.pastInvestigations = pastInvestigations;
	}
	
	
	
}
