package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.InvestigationOrdered;
import com.inicu.postgres.entities.SaHyperkalemia;

public class HyperkalemiaPastEventsPOJO {

	List<SaHyperkalemia> pastHyperkalemiaEvents;

	List<InvestigationOrdered> pastInvestigations;

	public List<SaHyperkalemia> getPastHyperkalemiaEvents() {
		return pastHyperkalemiaEvents;
	}

	public void setPastHyperkalemiaEvents(List<SaHyperkalemia> pastHyperkalemiaEvents) {
		this.pastHyperkalemiaEvents = pastHyperkalemiaEvents;
	}

	public List<InvestigationOrdered> getPastInvestigations() {
		return pastInvestigations;
	}

	public void setPastInvestigations(List<InvestigationOrdered> pastInvestigations) {
		this.pastInvestigations = pastInvestigations;
	}

	@Override
	public String toString() {
		return "HyperkalemiaPastEventsPOJO [pastHyperkalemiaEvents=" + pastHyperkalemiaEvents + ", pastInvestigations="
				+ pastInvestigations + "]";
	}

}
