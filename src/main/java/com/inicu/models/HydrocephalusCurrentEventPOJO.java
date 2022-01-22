package com.inicu.models;

import com.inicu.postgres.entities.SaCnsHydrocephalus;

public class HydrocephalusCurrentEventPOJO {
    SaCnsHydrocephalus currentHydrocephalus;

    public HydrocephalusCurrentEventPOJO() {
        super();
        this.currentHydrocephalus = new SaCnsHydrocephalus();
    }

    public SaCnsHydrocephalus getCurrentHydrocephalus() {
        return currentHydrocephalus;
    }

    public void setCurrentHydrocephalus(SaCnsHydrocephalus currentHydrocephalus) {
        this.currentHydrocephalus = currentHydrocephalus;
    }
}
