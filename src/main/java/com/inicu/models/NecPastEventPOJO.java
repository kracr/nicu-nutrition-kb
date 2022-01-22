package com.inicu.models;

import com.inicu.postgres.entities.InvestigationOrdered;
import com.inicu.postgres.entities.SaNec;

import java.util.List;

public class NecPastEventPOJO {
    List<SaNec> pastEventObj;
    List<InvestigationOrdered> pastInvestigationsList;

    public List<SaNec> getPastEventObj() {
        return pastEventObj;
    }

    public void setPastEventObj(List<SaNec> pastEventObj) {
        this.pastEventObj = pastEventObj;
    }

    public List<InvestigationOrdered> getPastInvestigationsList() {
        return pastInvestigationsList;
    }

    public void setPastInvestigationsList(List<InvestigationOrdered> pastInvestigationsList) {
        this.pastInvestigationsList = pastInvestigationsList;
    }
}
