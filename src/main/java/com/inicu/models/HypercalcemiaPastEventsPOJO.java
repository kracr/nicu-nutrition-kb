package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.InvestigationOrdered;
import com.inicu.postgres.entities.SaHypercalcemia;

public class HypercalcemiaPastEventsPOJO {
	
	List<SaHypercalcemia> pastHypercalcemiaEvents;
	
	List<InvestigationOrdered> pastInvestigations;

	public List<SaHypercalcemia> getPastHypercalcemiaEvents() {
		return pastHypercalcemiaEvents;
	}

	public void setPastHypercalcemiaEvents(List<SaHypercalcemia> pastHypercalcemiaEvents) {
		this.pastHypercalcemiaEvents = pastHypercalcemiaEvents;
	}

	public List<InvestigationOrdered> getPastInvestigations() {
		return pastInvestigations;
	}

	public void setPastInvestigations(List<InvestigationOrdered> pastInvestigations) {
		this.pastInvestigations = pastInvestigations;
	}
}
