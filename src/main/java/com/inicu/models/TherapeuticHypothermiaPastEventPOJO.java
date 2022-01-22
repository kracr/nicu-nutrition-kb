package com.inicu.models;

import com.inicu.postgres.entities.InvestigationOrdered;

import java.util.List;

public class TherapeuticHypothermiaPastEventPOJO {

    Object pastTherapeuticHypothermia;

    List<InvestigationOrdered> pastInvestigations;

    public Object getPastTherapeuticHypothermia() {
        return pastTherapeuticHypothermia;
    }

    public void setPastTherapeuticHypothermia(Object pastTherapeuticHypothermia) {
        this.pastTherapeuticHypothermia = pastTherapeuticHypothermia;
    }

    public List<InvestigationOrdered> getPastInvestigations() {
        return pastInvestigations;
    }

    public void setPastInvestigations(List<InvestigationOrdered> pastInvestigations) {
        this.pastInvestigations = pastInvestigations;
    }
}
