package com.inicu.models;

import com.inicu.postgres.entities.SaHypocalcemia;

public class HypocalcemiaCurrentEventPOJO {
	
	SaHypocalcemia currentHypocalcemia;
	public HypocalcemiaCurrentEventPOJO() {
		super();
		currentHypocalcemia = new SaHypocalcemia();
		
	}
	public SaHypocalcemia getCurrentHypocalcemia() {
		return currentHypocalcemia;
	}
	public void setCurrentHypocalcemia(SaHypocalcemia currentHypocalcemia) {
		this.currentHypocalcemia = currentHypocalcemia;
	}
	
	

	
}
