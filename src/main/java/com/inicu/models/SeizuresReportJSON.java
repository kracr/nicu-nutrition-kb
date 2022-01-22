package com.inicu.models;

public class SeizuresReportJSON {

	private Integer seizures_onset;
	private Integer seizures_duration;
	private String seizures_onset_type;

	public SeizuresReportJSON() {
		
	}

	public Integer getSeizures_onset() {
		return seizures_onset;
	}

	public void setSeizures_onset(Integer seizures_onset) {
		this.seizures_onset = seizures_onset;
	}

	public Integer getSeizures_duration() {
		return seizures_duration;
	}

	public void setSeizures_duration(Integer seizures_duration) {
		this.seizures_duration = seizures_duration;
	}

	public String getSeizures_onset_type() {
		return seizures_onset_type;
	}

	public void setSeizures_onset_type(String seizures_onset_type) {
		this.seizures_onset_type = seizures_onset_type;
	}

	@Override
	public String toString() {
		return "SeizuresReportJSON [seizures_onset=" + seizures_onset + ", seizures_duration=" + seizures_duration
				+ ", seizures_onset_type=" + seizures_onset_type + "]";
	}

}
