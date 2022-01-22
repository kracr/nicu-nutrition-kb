package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.InvestigationOrdered;
import com.inicu.postgres.entities.NursingVitalparameter;
import com.inicu.postgres.entities.SaHypoglycemia;

public class HypoglycemiaPastEventsPOJO {

	List<SaHypoglycemia> pastHypoglycemiaEvents;

	List<NursingVitalparameter> nursingHypoglycemicEvent;

	List<InvestigationOrdered> pastInvestigations;

	public List<SaHypoglycemia> getPastHypoglycemiaEvents() {
		return pastHypoglycemiaEvents;
	}

	public void setPastHypoglycemiaEvents(List<SaHypoglycemia> pastHypoglycemiaEvents) {
		this.pastHypoglycemiaEvents = pastHypoglycemiaEvents;
	}

	public List<NursingVitalparameter> getNursingHypoglycemicEvent() {
		return nursingHypoglycemicEvent;
	}

	public void setNursingHypoglycemicEvent(List<NursingVitalparameter> nursingHypoglycemicEvent) {
		this.nursingHypoglycemicEvent = nursingHypoglycemicEvent;
	}

	public List<InvestigationOrdered> getPastInvestigations() {
		return pastInvestigations;
	}

	public void setPastInvestigations(List<InvestigationOrdered> pastInvestigations) {
		this.pastInvestigations = pastInvestigations;
	}

}
