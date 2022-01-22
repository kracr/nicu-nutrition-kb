package com.inicu.models;

import java.util.List;
import com.inicu.postgres.entities.ScoreIVH;

public class IVHScoreJSON {
	
	List<ScoreIVH> ivhScoreList;
	
	public List<ScoreIVH> getIVHScoreList() {
		return ivhScoreList;
	}

	public void setIVHScoreList(List<ScoreIVH> ivhScoreList) {
		this.ivhScoreList = ivhScoreList;
	}
}
