package com.inicu.models;

public class SepsisReportJSON {

	private Integer sepsis_onset;
	private Integer sepsis_duration;
	private String sepsis_cause;
	private String sepsis_onset_type;
	private Integer no_antibiotics;
	private Integer antibiotics_duration;
	private Boolean sepsis_nec;

	public SepsisReportJSON() {
		super();
		this.sepsis_nec = false;
	}

	public Integer getSepsis_onset() {
		return sepsis_onset;
	}

	public void setSepsis_onset(Integer sepsis_onset) {
		this.sepsis_onset = sepsis_onset;
	}

	public Integer getSepsis_duration() {
		return sepsis_duration;
	}

	public void setSepsis_duration(Integer sepsis_duration) {
		this.sepsis_duration = sepsis_duration;
	}

	public String getSepsis_cause() {
		return sepsis_cause;
	}

	public void setSepsis_cause(String sepsis_cause) {
		this.sepsis_cause = sepsis_cause;
	}

	public String getSepsis_onset_type() {
		return sepsis_onset_type;
	}

	public void setSepsis_onset_type(String sepsis_onset_type) {
		this.sepsis_onset_type = sepsis_onset_type;
	}

	public Integer getNo_antibiotics() {
		return no_antibiotics;
	}

	public void setNo_antibiotics(Integer no_antibiotics) {
		this.no_antibiotics = no_antibiotics;
	}

	public Integer getAntibiotics_duration() {
		return antibiotics_duration;
	}

	public void setAntibiotics_duration(Integer antibiotics_duration) {
		this.antibiotics_duration = antibiotics_duration;
	}

	public Boolean getSepsis_nec() {
		return sepsis_nec;
	}

	public void setSepsis_nec(Boolean sepsis_nec) {
		this.sepsis_nec = sepsis_nec;
	}

	@Override
	public String toString() {
		return "SepsisReportJSON [sepsis_onset=" + sepsis_onset + ", sepsis_duration=" + sepsis_duration
				+ ", sepsis_cause=" + sepsis_cause + ", sepsis_onset_type=" + sepsis_onset_type + ", no_antibiotics="
				+ no_antibiotics + ", antibiotics_duration=" + antibiotics_duration + ", sepsis_nec=" + sepsis_nec
				+ "]";
	}

}
