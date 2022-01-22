package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.ScreenHearing;

public class HearingMasterPojo {

	ScreenHearing currentObj;

	List<ScreenHearing> pastHearingList;

	public HearingMasterPojo() {
		super();
		this.currentObj = new ScreenHearing();
	}

	public ScreenHearing getCurrentObj() {
		return currentObj;
	}

	public void setCurrentObj(ScreenHearing currentObj) {
		this.currentObj = currentObj;
	}

	public List<ScreenHearing> getPastHearingList() {
		return pastHearingList;
	}

	public void setPastHearingList(List<ScreenHearing> pastHearingList) {
		this.pastHearingList = pastHearingList;
	}

	@Override
	public String toString() {
		return "HearingMasterPojo [currentObj=" + currentObj + ", pastHearingList=" + pastHearingList + "]";
	}

}
