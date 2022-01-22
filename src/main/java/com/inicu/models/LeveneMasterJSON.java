package com.inicu.models;

import java.util.List;

public class LeveneMasterJSON {

	int totalScore;
	ScoreLevene ScoreLeveneObj;
	List<GenericScoreStatusJSON> leveneStatusList;

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	public ScoreLevene getScoreLeveneObj() {
		return ScoreLeveneObj;
	}

	public void setScoreLeveneObj(ScoreLevene scoreLeveneObj) {
		ScoreLeveneObj = scoreLeveneObj;
	}

	public List<GenericScoreStatusJSON> getLeveneStatusList() {
		return leveneStatusList;
	}

	public void setLeveneStatusList(List<GenericScoreStatusJSON> leveneStatusList) {
		this.leveneStatusList = leveneStatusList;
	}

	@Override
	public String toString() {
		return "LeveneMasterJSON [totalScore=" + totalScore + ", ScoreLeveneObj=" + ScoreLeveneObj
				+ ", leveneStatusList=" + leveneStatusList + "]";
	}

}
