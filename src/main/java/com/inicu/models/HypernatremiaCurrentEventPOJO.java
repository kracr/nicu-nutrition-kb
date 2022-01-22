package com.inicu.models;

import com.inicu.postgres.entities.SaHypernatremia;

public class HypernatremiaCurrentEventPOJO {

	SaHypernatremia currentSaHypernatremia;

	public HypernatremiaCurrentEventPOJO() {
		super();
		// TODO Auto-generated constructor stub
		this.currentSaHypernatremia = new SaHypernatremia();
	}

	public SaHypernatremia getCurrentSaHypernatremia() {
		return currentSaHypernatremia;
	}

	public void setCurrentSaHypernatremia(SaHypernatremia currentSaHypernatremia) {
		this.currentSaHypernatremia = currentSaHypernatremia;
	}
}
