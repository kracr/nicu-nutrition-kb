package com.inicu.models;

import java.util.List;

public class NursingObservationPojoAll {

	private BabyVisitsObj babyVisit;
	
	private List bloodGasInfo;	
	private List vitalParametersInfo;
	private List neuroVitalsInfo;
	private List intakeInfo;
	private List outputInfo;
	private List cathetersInfo;
	private List ventilatorInfo;
	private Object dailyAssessmentInfo;
	private List miscInfo;
	
	
	public List getBloodGasInfo() {
		return bloodGasInfo;
	}
	public void setBloodGasInfo(List bloodGasInfo) {
		this.bloodGasInfo = bloodGasInfo;
	}
	public List getVitalParametersInfo() {
		return vitalParametersInfo;
	}
	public void setVitalParametersInfo(List vitalParametersInfo) {
		this.vitalParametersInfo = vitalParametersInfo;
	}
	public List getNeuroVitalsInfo() {
		return neuroVitalsInfo;
	}
	public void setNeuroVitalsInfo(List neuroVitalsInfo) {
		this.neuroVitalsInfo = neuroVitalsInfo;
	}
	public List getIntakeInfo() {
		return intakeInfo;
	}
	public void setIntakeInfo(List intakeInfo) {
		this.intakeInfo = intakeInfo;
	}
	public List getOutputInfo() {
		return outputInfo;
	}
	public void setOutputInfo(List outputInfo) {
		this.outputInfo = outputInfo;
	}
	public List getCathetersInfo() {
		return cathetersInfo;
	}
	public void setCathetersInfo(List cathetersInfo) {
		this.cathetersInfo = cathetersInfo;
	}
	public List getVentilatorInfo() {
		return ventilatorInfo;
	}
	public void setVentilatorInfo(List ventilatorInfo) {
		this.ventilatorInfo = ventilatorInfo;
	}
	
	public Object getDailyAssessmentInfo() {
		return dailyAssessmentInfo;
	}
	public void setDailyAssessmentInfo(Object dailyAssessmentInfo) {
		this.dailyAssessmentInfo = dailyAssessmentInfo;
	}
	public List getMiscInfo() {
		return miscInfo;
	}
	public void setMiscInfo(List miscInfo) {
		this.miscInfo = miscInfo;
	}
	public BabyVisitsObj getBabyVisit() {
		return babyVisit;
	}
	public void setBabyVisit(BabyVisitsObj babyVisit) {
		this.babyVisit = babyVisit;
	}
	
	
	
}
