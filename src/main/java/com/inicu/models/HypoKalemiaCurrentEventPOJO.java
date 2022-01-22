package com.inicu.models;

import com.inicu.postgres.entities.SaHypokalemia;

public class HypoKalemiaCurrentEventPOJO {

	SaHypokalemia currentHypokalemia;

	public HypoKalemiaCurrentEventPOJO() {
		super();
		this.currentHypokalemia = new SaHypokalemia();
	}

	public SaHypokalemia getCurrentHypokalemia() {
		return currentHypokalemia;
	}

	public void setCurrentHypokalemia(SaHypokalemia currentHypokalemia) {
		this.currentHypokalemia = currentHypokalemia;
	}

}
