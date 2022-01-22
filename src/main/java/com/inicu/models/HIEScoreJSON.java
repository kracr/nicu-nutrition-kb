package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.ScoreHIE;

public class HIEScoreJSON {
	
	List<ScoreHIE> hieScoreList;

	public List<ScoreHIE> getHieScoreList() {
		return hieScoreList;
	}

	public void setHieScoreList(List<ScoreHIE> hieScoreList) {
		this.hieScoreList = hieScoreList;
	}
}
