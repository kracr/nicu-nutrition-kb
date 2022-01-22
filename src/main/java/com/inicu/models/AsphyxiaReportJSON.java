package com.inicu.models;

public class AsphyxiaReportJSON {

	private Integer asphyxia_onset;
	private Integer asphyxia_duration;
	private String asphyxia_onset_type;

	public AsphyxiaReportJSON() {
		
	}

	public Integer getAsphyxia_onset() {
		return asphyxia_onset;
	}

	public void setAsphyxia_onset(Integer asphyxia_onset) {
		this.asphyxia_onset = asphyxia_onset;
	}

	public Integer getAsphyxia_duration() {
		return asphyxia_duration;
	}

	public void setAsphyxia_duration(Integer asphyxia_duration) {
		this.asphyxia_duration = asphyxia_duration;
	}

	public String getAsphyxia_onset_type() {
		return asphyxia_onset_type;
	}

	public void setAsphyxia_onset_type(String asphyxia_onset_type) {
		this.asphyxia_onset_type = asphyxia_onset_type;
	}

	@Override
	public String toString() {
		return "AsphyxiaReportJSON [asphyxia_onset=" + asphyxia_onset + ", asphyxia_duration=" + asphyxia_duration
				+ ", asphyxia_onset_type=" + asphyxia_onset_type + "]";
	}


}
