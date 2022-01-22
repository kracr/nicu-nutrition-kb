package com.inicu.models;

import com.inicu.postgres.entities.SaCnsSeizures;

public class SeizuresCurrentEventPOJO {

	SaCnsSeizures currentSeizures;

	public SeizuresCurrentEventPOJO() {
		super();
		this.currentSeizures = new SaCnsSeizures();
	}

	public SaCnsSeizures getCurrentSeizures() {
		return currentSeizures;
	}

	public void setCurrentSeizures(SaCnsSeizures currentSeizures) {
		this.currentSeizures = currentSeizures;
	}

}
