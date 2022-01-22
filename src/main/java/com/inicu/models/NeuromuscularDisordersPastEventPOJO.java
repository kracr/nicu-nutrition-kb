package com.inicu.models;

import com.inicu.postgres.entities.InvestigationOrdered;
import com.inicu.postgres.entities.NursingEpisode;

import java.util.List;

public class NeuromuscularDisordersPastEventPOJO {
    Object pastNeuromuscularDisordersEvents; // object will be replaced with view later

    List<NursingEpisode> pastEpisodeList;

    List<InvestigationOrdered> pastInvestigations;

    public Object getPastNeuromuscularDisordersEvents() {
        return pastNeuromuscularDisordersEvents;
    }

    public void setPastNeuromuscularDisordersEvents(Object pastNeuromuscularDisordersEvents) {
        this.pastNeuromuscularDisordersEvents = pastNeuromuscularDisordersEvents;
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

