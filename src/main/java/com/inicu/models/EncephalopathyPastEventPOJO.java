package com.inicu.models;

import com.inicu.postgres.entities.InvestigationOrdered;
import com.inicu.postgres.entities.NursingEpisode;
import com.inicu.postgres.entities.SaCnsEncephalopathy;

import java.util.List;

public class EncephalopathyPastEventPOJO {

    Object pastEncephalopathyEvents; // object will be replaced with view later

    List<NursingEpisode> pastEpisodeList;

    List<InvestigationOrdered> pastInvestigations;

    public Object getPastEncephalopathyEvents() {
        return pastEncephalopathyEvents;
    }

    public void setPastEncephalopathyEvents(Object pastEncephalopathyEvents) {
        this.pastEncephalopathyEvents = pastEncephalopathyEvents;
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
