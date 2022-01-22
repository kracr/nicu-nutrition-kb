package com.inicu.models;

import com.inicu.postgres.entities.SaHyponatremia;

public class HyponatremiaCurrentEventPOJO {

	
	SaHyponatremia currentHyponatremia;

	public HyponatremiaCurrentEventPOJO() {
		super();
		this.currentHyponatremia = new SaHyponatremia();
	}

	public SaHyponatremia getCurrentHyponatremia() {
		return currentHyponatremia;
	}

	public void setCurrentHyponatremia(SaHyponatremia currentHyponatremia) {
		this.currentHyponatremia = currentHyponatremia;
	}
	
	
}
