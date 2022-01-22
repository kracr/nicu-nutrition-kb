package com.inicu.models;

import com.inicu.postgres.entities.SaHyperglycemia;

public class HyperglycemiaCurrentEventPOJO {

	SaHyperglycemia currentHyperglycemia;

	public HyperglycemiaCurrentEventPOJO() {
		super();
		this.currentHyperglycemia = new SaHyperglycemia();
	}

	public SaHyperglycemia getCurrentHyperglycemia() {
		return currentHyperglycemia;
	}

	public void setCurrentHyperglycemia(SaHyperglycemia currentHyperglycemia) {
		this.currentHyperglycemia = currentHyperglycemia;
	}

	@Override
	public String toString() {
		return "HyperglycemiaCurrentEventPOJO [currentHyperglycemia=" + currentHyperglycemia + "]";
	}

}
