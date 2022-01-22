package com.inicu.models;

public class RdsReportJSON {

	private Integer rds_onset;
	private Integer rds_duration;
	private String rds_cause;
	private Integer max_downes;

	private Boolean resp_support;
	private Integer duration_lowflow = 0;
	private Integer duration_highflow = 0;
	private Integer duration_cpap = 0;
	private Integer duration_mv = 0;
	private Integer duration_hfo = 0;

	private Boolean rds_surfactant;
	private Integer age_at_surfactant;
	private String surfactant_type;
	private Integer surfactant_dose;

	private Integer no_antibiotics;
	private Integer antibiotics_duration;

	public RdsReportJSON() {
		super();
		this.resp_support = false;
		this.rds_surfactant = false;

	}

	public Integer getRds_onset() {
		return rds_onset;
	}

	public void setRds_onset(Integer rds_onset) {
		this.rds_onset = rds_onset;
	}

	public Integer getRds_duration() {
		return rds_duration;
	}

	public void setRds_duration(Integer rds_duration) {
		this.rds_duration = rds_duration;
	}

	public String getRds_cause() {
		return rds_cause;
	}

	public void setRds_cause(String rds_cause) {
		this.rds_cause = rds_cause;
	}

	public Integer getMax_downes() {
		return max_downes;
	}

	public void setMax_downes(Integer max_downes) {
		this.max_downes = max_downes;
	}

	public Boolean getResp_support() {
		return resp_support;
	}

	public void setResp_support(Boolean resp_support) {
		this.resp_support = resp_support;
	}

	public Integer getDuration_lowflow() {
		return duration_lowflow;
	}

	public void setDuration_lowflow(Integer duration_lowflow) {
		this.duration_lowflow = duration_lowflow;
	}

	public Integer getDuration_highflow() {
		return duration_highflow;
	}

	public void setDuration_highflow(Integer duration_highflow) {
		this.duration_highflow = duration_highflow;
	}

	public Integer getDuration_cpap() {
		return duration_cpap;
	}

	public void setDuration_cpap(Integer duration_cpap) {
		this.duration_cpap = duration_cpap;
	}

	public Integer getDuration_mv() {
		return duration_mv;
	}

	public void setDuration_mv(Integer duration_mv) {
		this.duration_mv = duration_mv;
	}

	public Integer getDuration_hfo() {
		return duration_hfo;
	}

	public void setDuration_hfo(Integer duration_hfo) {
		this.duration_hfo = duration_hfo;
	}

	public Boolean getRds_surfactant() {
		return rds_surfactant;
	}

	public void setRds_surfactant(Boolean rds_surfactant) {
		this.rds_surfactant = rds_surfactant;
	}

	public Integer getAge_at_surfactant() {
		return age_at_surfactant;
	}

	public void setAge_at_surfactant(Integer age_at_surfactant) {
		this.age_at_surfactant = age_at_surfactant;
	}

	public String getSurfactant_type() {
		return surfactant_type;
	}

	public void setSurfactant_type(String surfactant_type) {
		this.surfactant_type = surfactant_type;
	}

	public Integer getSurfactant_dose() {
		return surfactant_dose;
	}

	public void setSurfactant_dose(Integer surfactant_dose) {
		this.surfactant_dose = surfactant_dose;
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

	@Override
	public String toString() {
		return "RdsReportJSON [rds_onset=" + rds_onset + ", rds_duration=" + rds_duration + ", rds_cause=" + rds_cause
				+ ", max_downes=" + max_downes + ", resp_support=" + resp_support + ", duration_lowflow="
				+ duration_lowflow + ", duration_highflow=" + duration_highflow + ", duration_cpap=" + duration_cpap
				+ ", duration_mv=" + duration_mv + ", duration_hfo=" + duration_hfo + ", rds_surfactant="
				+ rds_surfactant + ", age_at_surfactant=" + age_at_surfactant + ", surfactant_type=" + surfactant_type
				+ ", surfactant_dose=" + surfactant_dose + ", no_antibiotics=" + no_antibiotics
				+ ", antibiotics_duration=" + antibiotics_duration + "]";
	}

}
