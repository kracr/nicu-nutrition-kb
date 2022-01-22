package com.inicu.models;

import com.inicu.postgres.entities.SaHypercalcemia;

public class HypercalcemiaCurrentEventPOJO {
	
	SaHypercalcemia currentHypercalcemia;

	public HypercalcemiaCurrentEventPOJO() {
		super();
		// TODO Auto-generated constructor stub
		currentHypercalcemia = new SaHypercalcemia();
	}

	public SaHypercalcemia getCurrentHypercalcemia() {
		return currentHypercalcemia;
	}

	public void setCurrentHypercalcemia(SaHypercalcemia currentHypercalcemia) {
		this.currentHypercalcemia = currentHypercalcemia;
	}
}
