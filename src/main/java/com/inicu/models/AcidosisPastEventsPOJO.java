package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.InvestigationOrdered;
import com.inicu.postgres.entities.SaAcidosis;

public class AcidosisPastEventsPOJO {

	
	List<SaAcidosis> pastAcidosisEvents;
	List<InvestigationOrdered> pastInvestigations;
	public List<SaAcidosis> getPastAcidosisEvents() {
		return pastAcidosisEvents;
	}
	public void setPastAcidosisEvents(List<SaAcidosis> pastAcidosisEvents) {
		this.pastAcidosisEvents = pastAcidosisEvents;
	}
	public List<InvestigationOrdered> getPastInvestigations() {
		return pastInvestigations;
	}
	public void setPastInvestigations(List<InvestigationOrdered> pastInvestigations) {
		this.pastInvestigations = pastInvestigations;
	}
		
}
