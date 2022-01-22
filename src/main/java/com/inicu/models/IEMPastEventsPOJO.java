package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.InvestigationOrdered;
import com.inicu.postgres.entities.SaIEM;

public class IEMPastEventsPOJO {

	List<SaIEM> pastIemEvents;
	
	List<InvestigationOrdered> pastInvestigations;

	public List<SaIEM> getPastIemEvents() {
		return pastIemEvents;
	}

	public void setPastIemEvents(List<SaIEM> pastIemEvents) {
		this.pastIemEvents = pastIemEvents;
	}

	public List<InvestigationOrdered> getPastInvestigations() {
		return pastInvestigations;
	}

	public void setPastInvestigations(List<InvestigationOrdered> pastInvestigations) {
		this.pastInvestigations = pastInvestigations;
	}
}
