package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.InvestigationOrdered;
import com.inicu.postgres.entities.SaHyponatremia;

public class HyponatremiaPastEventsPOJO {
	
	List<SaHyponatremia> pastHyponatremiaEvents;
	
	List<InvestigationOrdered> pastInvestigations;

	public List<SaHyponatremia> getPastHyponatremiaEvents() {
		return pastHyponatremiaEvents;
	}

	public void setPastHyponatremiaEvents(List<SaHyponatremia> pastHyponatremiaEvents) {
		this.pastHyponatremiaEvents = pastHyponatremiaEvents;
	}

	public List<InvestigationOrdered> getPastInvestigations() {
		return pastInvestigations;
	}

	public void setPastInvestigations(List<InvestigationOrdered> pastInvestigations) {
		this.pastInvestigations = pastInvestigations;
	}
	
	

}
