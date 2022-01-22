package com.inicu.models;

import com.inicu.postgres.entities.SaAcidosis;

public class AcidosisCurrentEventPOJO {

	SaAcidosis currentAcidosis;

	
	public AcidosisCurrentEventPOJO() {
		super();
		this.currentAcidosis = new SaAcidosis();
	}


	public SaAcidosis getCurrentAcidosis() {
		return currentAcidosis;
	}


	public void setCurrentAcidosis(SaAcidosis currentAcidosis) {
		this.currentAcidosis = currentAcidosis;
	}

	
	
}
