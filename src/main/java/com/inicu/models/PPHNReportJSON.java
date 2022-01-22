package com.inicu.models;

public class PPHNReportJSON {

	private Integer pphn_onset;
	private Integer pphn_duration;
	private String pphn_onset_type;

	public PPHNReportJSON() {
		
	}

	public Integer getPphn_onset() {
		return pphn_onset;
	}

	public void setPphn_onset(Integer pphn_onset) {
		this.pphn_onset = pphn_onset;
	}

	public Integer getPphn_duration() {
		return pphn_duration;
	}

	public void setPphn_duration(Integer pphn_duration) {
		this.pphn_duration = pphn_duration;
	}

	public String getPphn_onset_type() {
		return pphn_onset_type;
	}

	public void setPphn_onset_type(String pphn_onset_type) {
		this.pphn_onset_type = pphn_onset_type;
	}

}
