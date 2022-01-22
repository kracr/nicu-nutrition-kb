package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.InvestigationOrdered;
import com.inicu.postgres.entities.SaHypocalcemia;

public class HypocalcemiaPastEventsPOJO {
	
	List<SaHypocalcemia> pastHypocalcemiaEvents;
	
	List<InvestigationOrdered> pastInvestigations;

	public List<SaHypocalcemia> getPastHypocalcemiaEvents() {
		return pastHypocalcemiaEvents;
	}

	public void setPastHypocalcemiaEvents(List<SaHypocalcemia> pastHypocalcemiaEvents) {
		this.pastHypocalcemiaEvents = pastHypocalcemiaEvents;
	}

	public List<InvestigationOrdered> getPastInvestigations() {
		return pastInvestigations;
	}

	public void setPastInvestigations(List<InvestigationOrdered> pastInvestigations) {
		this.pastInvestigations = pastInvestigations;
	}

	
}
