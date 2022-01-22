package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.InvestigationOrdered;
import com.inicu.postgres.entities.NursingEpisode;

public class SeizuresPastEventPOJO {

	Object pastSeizuresEvents; // object will be replaced with view later

	List<NursingEpisode> pastEpisodeList;

	List<InvestigationOrdered> pastInvestigations;

	public Object getPastSeizuresEvents() {
		return pastSeizuresEvents;
	}

	public void setPastSeizuresEvents(Object pastSeizuresEvents) {
		this.pastSeizuresEvents = pastSeizuresEvents;
	}

	public List<NursingEpisode> getPastEpisodeList() {
		return pastEpisodeList;
	}

	public void setPastEpisodeList(List<NursingEpisode> pastEpisodeList) {
		this.pastEpisodeList = pastEpisodeList;
	}

	public List<InvestigationOrdered> getPastInvestigations() {
		return pastInvestigations;
	}

	public void setPastInvestigations(List<InvestigationOrdered> pastInvestigations) {
		this.pastInvestigations = pastInvestigations;
	}
}
