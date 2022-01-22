package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.ScreenMiscellaneous;
import com.inicu.postgres.entities.ScreenNeurological;

public class MiscellaneousMasterPOJO {

	ScreenMiscellaneous currentObj;

	List<ScreenMiscellaneous> pastMiscellaneousList;

	List<KeyValueItem> screeningList;
	
	public MiscellaneousMasterPOJO() {
		super();
		this.currentObj = new ScreenMiscellaneous();
	}

	public ScreenMiscellaneous getCurrentObj() {
		return currentObj;
	}

	public void setCurrentObj(ScreenMiscellaneous currentObj) {
		this.currentObj = currentObj;
	}

	public List<ScreenMiscellaneous> getPastMiscellaneousList() {
		return pastMiscellaneousList;
	}

	public void setPastMiscellaneousList(List<ScreenMiscellaneous> pastMiscellaneousList) {
		this.pastMiscellaneousList = pastMiscellaneousList;
	}
	
	
	public List<KeyValueItem> getScreeningList() {
		return screeningList;
	}

	public void setScreeningList(List<KeyValueItem> screeningList) {
		this.screeningList = screeningList;
	}

	@Override
	public String toString() {
		return "MiscellaneousMasterPOJO [currentObj=" + currentObj + ", pastNeurologicalList=" + pastMiscellaneousList
				+ "]";
	}

}
