package com.inicu.models;

import java.util.List;

public class GenericScoreJSON {

	int scoreValue;
	String scoreIndex;
	String scoreName;
	List<GenericScoreSignJSON> signList;

	public int getScoreValue() {
		return scoreValue;
	}

	public void setScoreValue(int scoreValue) {
		this.scoreValue = scoreValue;
	}

	public String getScoreIndex() {
		return scoreIndex;
	}

	public void setScoreIndex(String scoreIndex) {
		this.scoreIndex = scoreIndex;
	}

	public String getScoreName() {
		return scoreName;
	}

	public void setScoreName(String scoreName) {
		this.scoreName = scoreName;
	}

	public List<GenericScoreSignJSON> getSignList() {
		return signList;
	}

	public void setSignList(List<GenericScoreSignJSON> signList) {
		this.signList = signList;
	}

	@Override
	public String toString() {
		return "GenericScoreJSON [scoreValue=" + scoreValue + ", scoreIndex=" + scoreIndex + ", scoreName=" + scoreName
				+ ", signList=" + signList + "]";
	}

}
