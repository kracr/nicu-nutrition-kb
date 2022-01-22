package com.inicu.models;

import java.util.ArrayList;
import java.util.List;

import com.inicu.postgres.entities.InvestigationOrdered;
import com.inicu.postgres.entities.SaHypokalemia;

public class HypoKalemiaPastEventsPOJO {

	List<SaHypokalemia> pastHypokalemiaEvents;

	List<InvestigationOrdered> pastInvestigations;

	public HypoKalemiaPastEventsPOJO() {
		super();
		this.pastHypokalemiaEvents = new ArrayList<SaHypokalemia>();
		this.pastInvestigations = new ArrayList<InvestigationOrdered>();
	}

	public List<SaHypokalemia> getPastHypokalemiaEvents() {
		return pastHypokalemiaEvents;
	}

	public List<InvestigationOrdered> getPastInvestigations() {
		return pastInvestigations;
	}

	public void setPastHypokalemiaEvents(List<SaHypokalemia> pastHypokalemiaEvents) {
		this.pastHypokalemiaEvents = pastHypokalemiaEvents;
	}

	public void setPastInvestigations(List<InvestigationOrdered> pastInvestigations) {
		this.pastInvestigations = pastInvestigations;
	}

}
