package com.inicu.models;

public class JaundiceReportJSON {

	private Integer jaundice_onset;
	private Integer jaundice_duration;
	private String jaundice_cause;
	private Boolean jaundice_phototherapy;
	private Integer phototherapy_duration_hrs;
	private Boolean jaundice_exchange;

	public JaundiceReportJSON() {
		super();
	}

	public Integer getJaundice_onset() {
		return jaundice_onset;
	}

	public void setJaundice_onset(Integer jaundice_onset) {
		this.jaundice_onset = jaundice_onset;
	}

	public Integer getJaundice_duration() {
		return jaundice_duration;
	}

	public void setJaundice_duration(Integer jaundice_duration) {
		this.jaundice_duration = jaundice_duration;
	}

	public String getJaundice_cause() {
		return jaundice_cause;
	}

	public void setJaundice_cause(String jaundice_cause) {
		this.jaundice_cause = jaundice_cause;
	}

	public Boolean getJaundice_phototherapy() {
		return jaundice_phototherapy;
	}

	public void setJaundice_phototherapy(Boolean jaundice_phototherapy) {
		this.jaundice_phototherapy = jaundice_phototherapy;
	}

	public Integer getPhototherapy_duration_hrs() {
		return phototherapy_duration_hrs;
	}

	public void setPhototherapy_duration_hrs(Integer phototherapy_duration_hrs) {
		this.phototherapy_duration_hrs = phototherapy_duration_hrs;
	}

	public Boolean getJaundice_exchange() {
		return jaundice_exchange;
	}

	public void setJaundice_exchange(Boolean jaundice_exchange) {
		this.jaundice_exchange = jaundice_exchange;
	}

	@Override
	public String toString() {
		return "JaundiceReportJSON [jaundice_onset=" + jaundice_onset + ", jaundice_duration=" + jaundice_duration
				+ ", jaundice_cause=" + jaundice_cause + ", jaundice_phototherapy=" + jaundice_phototherapy
				+ ", phototherapy_duration_hrs=" + phototherapy_duration_hrs + ", jaundice_exchange="
				+ jaundice_exchange + "]";
	}

}
