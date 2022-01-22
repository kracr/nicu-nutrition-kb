package com.inicu.models;

import com.inicu.postgres.entities.ScoreApgar;

public class ApgarScoreObj {

	
	private ScoreApgar oneMin;
	
	private ScoreApgar fiveMin;
	
	private ScoreApgar tenMin;
	
	

	public ApgarScoreObj() {
		super();
		this.oneMin = new ScoreApgar();
		this.fiveMin = new ScoreApgar();
		this.tenMin = new ScoreApgar();
	}

	public ScoreApgar getOneMin() {
		return oneMin;
	}

	public void setOneMin(ScoreApgar oneMin) {
		this.oneMin = oneMin;
	}

	public ScoreApgar getFiveMin() {
		return fiveMin;
	}

	public void setFiveMin(ScoreApgar fiveMin) {
		this.fiveMin = fiveMin;
	}

	public ScoreApgar getTenMin() {
		return tenMin;
	}

	public void setTenMin(ScoreApgar tenMin) {
		this.tenMin = tenMin;
	}
	
	
	
	
}
