package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.InvestigationOrdered;
import com.inicu.postgres.entities.NursingVitalparameter;
import com.inicu.postgres.entities.SaHyperglycemia;

public class HyperglycemiaPastEventsPOJO {

	List<SaHyperglycemia> pastHyperglycemiaEvents;

	List<NursingVitalparameter> nursingHyperglycemicEvent;

	List<InvestigationOrdered> pastInvestigations;

	public List<SaHyperglycemia> getPastHyperglycemiaEvents() {
		return pastHyperglycemiaEvents;
	}

	public void setPastHyperglycemiaEvents(List<SaHyperglycemia> pastHyperglycemiaEvents) {
		this.pastHyperglycemiaEvents = pastHyperglycemiaEvents;
	}

	public List<NursingVitalparameter> getNursingHyperglycemicEvent() {
		return nursingHyperglycemicEvent;
	}

	public void setNursingHyperglycemicEvent(List<NursingVitalparameter> nursingHyperglycemicEvent) {
		this.nursingHyperglycemicEvent = nursingHyperglycemicEvent;
	}

	public List<InvestigationOrdered> getPastInvestigations() {
		return pastInvestigations;
	}

	public void setPastInvestigations(List<InvestigationOrdered> pastInvestigations) {
		this.pastInvestigations = pastInvestigations;
	}

	@Override
	public String toString() {
		return "HyperglycemiaPastEventsPOJO [pastHyperglycemiaEvents=" + pastHyperglycemiaEvents
				+ ", nursingHyperglycemicEvent=" + nursingHyperglycemicEvent + ", pastInvestigations="
				+ pastInvestigations + "]";
	}

}
