package com.inicu.models;

import java.util.List;

public class GenericScoreStatusJSON {

	String clinicalStatus;
	int statusIndex;
	List<GenericScoreJSON> scoreList;

	public String getClinicalStatus() {
		return clinicalStatus;
	}

	public void setClinicalStatus(String clinicalStatus) {
		this.clinicalStatus = clinicalStatus;
	}

	public int getStatusIndex() {
		return statusIndex;
	}

	public void setStatusIndex(int statusIndex) {
		this.statusIndex = statusIndex;
	}

	public List<GenericScoreJSON> getScoreList() {
		return scoreList;
	}

	public void setScoreList(List<GenericScoreJSON> scoreList) {
		this.scoreList = scoreList;
	}

	@Override
	public String toString() {
		return "GenericScoreStatusJSON [clinicalStatus=" + clinicalStatus + ", statusIndex=" + statusIndex
				+ ", scoreList=" + scoreList + "]";
	}
}
