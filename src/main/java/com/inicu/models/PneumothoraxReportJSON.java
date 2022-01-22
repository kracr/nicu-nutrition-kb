package com.inicu.models;

public class PneumothoraxReportJSON {

	private Integer pneumothorax_onset;
	private Integer pneumothorax_duration;
	private String pneumothorax_onset_type;

	public PneumothoraxReportJSON() {
		
	}

	public Integer getPneumothorax_onset() {
		return pneumothorax_onset;
	}



	public void setPneumothorax_onset(Integer pneumothorax_onset) {
		this.pneumothorax_onset = pneumothorax_onset;
	}



	public Integer getPneumothorax_duration() {
		return pneumothorax_duration;
	}



	public void setPneumothorax_duration(Integer pneumothorax_duration) {
		this.pneumothorax_duration = pneumothorax_duration;
	}




	public String getPneumothorax_onset_type() {
		return pneumothorax_onset_type;
	}



	public void setPneumothorax_onset_type(String pneumothorax_onset_type) {
		this.pneumothorax_onset_type = pneumothorax_onset_type;
	}



	@Override
	public String toString() {
		return "PneumothoraxReportJSON [pneumothorax_onset=" + pneumothorax_onset + ", pneumothorax_duration="
				+ pneumothorax_duration + ", pneumothorax_onset_type=" + pneumothorax_onset_type + "]";
	}

	
}
