package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.ScreenUSG;

public class USGMasterPojo {

	ScreenUSG currentObj;

	List<ScreenUSG> pastUSGList;

	public USGMasterPojo() {
		super();
		this.currentObj = new ScreenUSG();
	}

	public ScreenUSG getCurrentObj() {
		return currentObj;
	}

	public void setCurrentObj(ScreenUSG currentObj) {
		this.currentObj = currentObj;
	}

	public List<ScreenUSG> getPastUSGList() {
		return pastUSGList;
	}

	public void setPastUSGList(List<ScreenUSG> pastUSGList) {
		this.pastUSGList = pastUSGList;
	}

	@Override
	public String toString() {
		return "USGMasterPojo [currentObj=" + currentObj + ", pastUSGList=" + pastUSGList + "]";
	}

}
