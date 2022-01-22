package com.inicu.models;

import com.inicu.postgres.entities.SaHyperkalemia;

public class HyperkalemiaCurrentEventPOJO {

	SaHyperkalemia currentHyperkalemia;

	public HyperkalemiaCurrentEventPOJO() {
		super();
		this.currentHyperkalemia = new SaHyperkalemia();
	}

	public SaHyperkalemia getCurrentHyperkalemia() {
		return currentHyperkalemia;
	}

	public void setCurrentHyperkalemia(SaHyperkalemia currentHyperkalemia) {
		this.currentHyperkalemia = currentHyperkalemia;
	}

	@Override
	public String toString() {
		return "HyperkalemiaCurrentEventPOJO [currentHyperkalemia=" + currentHyperkalemia + "]";
	}

}
