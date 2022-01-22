package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.InvestigationOrdered;
import com.inicu.postgres.entities.SaHypernatremia;

public class HypernatremiaPastEventsPOJO {

	List<SaHypernatremia> pastHypernatremiaEvents;
	
	List<InvestigationOrdered> pastInvestigations;

	public List<SaHypernatremia> getPastHypernatremiaEvents() {
		return pastHypernatremiaEvents;
	}

	public void setPastHypernatremiaEvents(List<SaHypernatremia> pastHypernatremiaEvents) {
		this.pastHypernatremiaEvents = pastHypernatremiaEvents;
	}

	public List<InvestigationOrdered> getPastInvestigations() {
		return pastInvestigations;
	}

	public void setPastInvestigations(List<InvestigationOrdered> pastInvestigations) {
		this.pastInvestigations = pastInvestigations;
	}
}
