package com.inicu.models;

import com.inicu.postgres.entities.SaIEM;

public class IEMCurrentEventPOJO {

	SaIEM currentIem;

	public IEMCurrentEventPOJO() {
		super();
		this.currentIem = new SaIEM();
	}

	public SaIEM getCurrentIem() {
		return currentIem;
	}

	public void setCurrentIem(SaIEM currentIem) {
		this.currentIem = currentIem;
	}
}
