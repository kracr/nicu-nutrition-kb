package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.ScoreSepsis;

public class SepsisScoreJSON {

	List<ScoreSepsis> sepsisScoreList;

	public List<ScoreSepsis> getSepsisScoreList() {
		return sepsisScoreList;
	}

	public void setSepsisScoreList(List<ScoreSepsis> sepsisScoreList) {
		this.sepsisScoreList = sepsisScoreList;
	}

}
