package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.ScoreBellStage;

public class BellMasterJSON {

	int totalScore;
	ScoreBellStage ScoreBellStageObj;
	List<GenericScoreStatusJSON> bellStageStatusList;

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	public ScoreBellStage getScoreBellStageObj() {
		return ScoreBellStageObj;
	}

	public void setScoreBellStageObj(ScoreBellStage scoreBellStageObj) {
		ScoreBellStageObj = scoreBellStageObj;
	}

	public List<GenericScoreStatusJSON> getBellStageStatusList() {
		return bellStageStatusList;
	}

	public void setBellStageStatusList(List<GenericScoreStatusJSON> bellStageStatusList) {
		this.bellStageStatusList = bellStageStatusList;
	}
}
