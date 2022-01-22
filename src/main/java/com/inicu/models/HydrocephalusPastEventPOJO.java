package com.inicu.models;

import com.inicu.postgres.entities.InvestigationOrdered;

import java.util.List;

public class HydrocephalusPastEventPOJO {
    Object pastHydrocephalusEvent;

    List<InvestigationOrdered> pastInvestigations;

    public Object getPastHydrocephalusEvent() {
        return pastHydrocephalusEvent;
    }

    public void setPastHydrocephalusEvent(Object pastHydrocephalusEvent) {
        this.pastHydrocephalusEvent = pastHydrocephalusEvent;
    }

    public List<InvestigationOrdered> getPastInvestigations() {
        return pastInvestigations;
    }

    public void setPastInvestigations(List<InvestigationOrdered> pastInvestigations) {
        this.pastInvestigations = pastInvestigations;
    }
}

