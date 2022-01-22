package com.inicu.models;

public class DistributionCurvesPOJO {
	
	DistributionData heartRate;
	DistributionData pulserate;
	DistributionData respiratoryRate;
	DistributionData spo2Rate;
	DistributionData meanBPRate;
	public DistributionData getHeartRate() {
		return heartRate;
	}
	public void setHeartRate(DistributionData heartRate) {
		this.heartRate = heartRate;
	}
	public DistributionData getPulserate() {
		return pulserate;
	}
	public void setPulserate(DistributionData pulserate) {
		this.pulserate = pulserate;
	}
	public DistributionData getRespiratoryRate() {
		return respiratoryRate;
	}
	public void setRespiratoryRate(DistributionData respiratoryRate) {
		this.respiratoryRate = respiratoryRate;
	}
	public DistributionData getSpo2Rate() {
		return spo2Rate;
	}
	public void setSpo2Rate(DistributionData spo2Rate) {
		this.spo2Rate = spo2Rate;
	}
	public DistributionData getMeanBPRate() {
		return meanBPRate;
	}
	public void setMeanBPRate(DistributionData meanBPRate) {
		this.meanBPRate = meanBPRate;
	}
	
}
