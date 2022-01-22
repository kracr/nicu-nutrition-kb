package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.ScoreBind;

public class BindMasterJSON {

	int totalScore;
	ScoreBind ScoreBindObj;
	List<GenericScoreStatusJSON> bindStatusList;

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	public ScoreBind getScoreBindObj() {
		return ScoreBindObj;
	}

	public void setScoreBindObj(ScoreBind scoreBindObj) {
		ScoreBindObj = scoreBindObj;
	}

	public List<GenericScoreStatusJSON> getBindStatusList() {
		return bindStatusList;
	}

	public void setBindStatusList(List<GenericScoreStatusJSON> bindStatusList) {
		this.bindStatusList = bindStatusList;
	}

	@Override
	public String toString() {
		return "BindMasterJSON [totalScore=" + totalScore + ", ScoreBindObj=" + ScoreBindObj + ", bindStatusList="
				+ bindStatusList + "]";
	}

}
