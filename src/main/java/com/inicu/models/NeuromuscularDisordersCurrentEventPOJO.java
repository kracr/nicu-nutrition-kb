package com.inicu.models;

import com.inicu.postgres.entities.SaCnsNeuromuscularDisorders;

public class NeuromuscularDisordersCurrentEventPOJO {
    SaCnsNeuromuscularDisorders currentNeuromuscularDisorders ;

    public NeuromuscularDisordersCurrentEventPOJO() {
        super();
        this.currentNeuromuscularDisorders = new SaCnsNeuromuscularDisorders();
    }

    public SaCnsNeuromuscularDisorders getCurrentNeuromuscularDisorders() {
        return currentNeuromuscularDisorders;
    }

    public void setCurrentNeuromuscularDisorders(SaCnsNeuromuscularDisorders currentNeuromuscularDisorders) {
        this.currentNeuromuscularDisorders = currentNeuromuscularDisorders;
    }
}
