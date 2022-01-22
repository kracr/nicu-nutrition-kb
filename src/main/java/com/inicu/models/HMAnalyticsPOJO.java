package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.HMObservableStatus;

public class HMAnalyticsPOJO {

	public List<HMObservableStatus> hmList;
	public List<HMObservableStatus> lmList;
	public List<HMObservableStatus> getHmList() {
		return hmList;
	}
	public void setHmList(List<HMObservableStatus> hmList) {
		this.hmList = hmList;
	}
	public List<HMObservableStatus> getLmList() {
		return lmList;
	}
	public void setLmList(List<HMObservableStatus> lmList) {
		this.lmList = lmList;
	}
	
}
