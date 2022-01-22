package com.inicu.models;

import java.util.List;

public class QIJSON {

	Integer noOfBabies = 0;
	Integer noOfBabiesIp = 0;
	Integer noOfBabiesOp = 0;

	Integer noOfMortality = 0;
	Integer noOfMortalityIp = 0;
	Integer noOfMortalityOp = 0;

	Integer noOfNurses = 0;
	Integer noOfDoctors = 0;

	Float breastFeedingRate = 0f;
	Float earlyOnsetSepsisRate = 0f;
	Float lateOnsetSepsisRate = 0f;
	List<QISepsisPatientData> sepsisUhidList;
	Float clabsiRate = 0f;
	Float centralLineUtilization = 0f;
	Float ventilatorUtilization = 0f;
	Float noOfSurgicalCases = 0f;
	Float antibioticUtilization = 0f;

	Integer meanGestationWeek = 0;
	Integer meanGestationDay = 0;
	Float meanWeight = 0f;
	Float avgLengthOfStay = 0f;
	Float readmissionRate = 0f;
	Float bedOccupancy = 0f;

	Float mortality_nec = 0f;
	Float mortality_bpd = 0f;
	Float mortality_retinopathy = 0f;
	Float mortality_vlbw = 0f;
	Float mortality_sepsis = 0f;
	Float mortality_ivh = 0f;

	public Integer getNoOfBabies() {
		return noOfBabies;
	}

	public void setNoOfBabies(Integer noOfBabies) {
		this.noOfBabies = noOfBabies;
	}

	public Integer getNoOfBabiesIp() {
		return noOfBabiesIp;
	}

	public void setNoOfBabiesIp(Integer noOfBabiesIp) {
		this.noOfBabiesIp = noOfBabiesIp;
	}

	public Integer getNoOfBabiesOp() {
		return noOfBabiesOp;
	}

	public void setNoOfBabiesOp(Integer noOfBabiesOp) {
		this.noOfBabiesOp = noOfBabiesOp;
	}

	public Integer getNoOfMortality() {
		return noOfMortality;
	}

	public void setNoOfMortality(Integer noOfMortality) {
		this.noOfMortality = noOfMortality;
	}

	public Integer getNoOfMortalityIp() {
		return noOfMortalityIp;
	}

	public void setNoOfMortalityIp(Integer noOfMortalityIp) {
		this.noOfMortalityIp = noOfMortalityIp;
	}

	public Integer getNoOfMortalityOp() {
		return noOfMortalityOp;
	}

	public void setNoOfMortalityOp(Integer noOfMortalityOp) {
		this.noOfMortalityOp = noOfMortalityOp;
	}

	public Integer getNoOfNurses() {
		return noOfNurses;
	}

	public void setNoOfNurses(Integer noOfNurses) {
		this.noOfNurses = noOfNurses;
	}

	public Integer getNoOfDoctors() {
		return noOfDoctors;
	}

	public void setNoOfDoctors(Integer noOfDoctors) {
		this.noOfDoctors = noOfDoctors;
	}

	public Float getBreastFeedingRate() {
		return breastFeedingRate;
	}

	public void setBreastFeedingRate(Float breastFeedingRate) {
		this.breastFeedingRate = breastFeedingRate;
	}

	public Float getEarlyOnsetSepsisRate() {
		return earlyOnsetSepsisRate;
	}

	public void setEarlyOnsetSepsisRate(Float earlyOnsetSepsisRate) {
		this.earlyOnsetSepsisRate = earlyOnsetSepsisRate;
	}

	public Float getLateOnsetSepsisRate() {
		return lateOnsetSepsisRate;
	}

	public void setLateOnsetSepsisRate(Float lateOnsetSepsisRate) {
		this.lateOnsetSepsisRate = lateOnsetSepsisRate;
	}

	public List<QISepsisPatientData> getSepsisUhidList() {
		return sepsisUhidList;
	}

	public void setSepsisUhidList(List<QISepsisPatientData> sepsisUhidList) {
		this.sepsisUhidList = sepsisUhidList;
	}

	public Float getClabsiRate() {
		return clabsiRate;
	}

	public void setClabsiRate(Float clabsiRate) {
		this.clabsiRate = clabsiRate;
	}

	public Float getCentralLineUtilization() {
		return centralLineUtilization;
	}

	public void setCentralLineUtilization(Float centralLineUtilization) {
		this.centralLineUtilization = centralLineUtilization;
	}

	public Float getVentilatorUtilization() {
		return ventilatorUtilization;
	}

	public void setVentilatorUtilization(Float ventilatorUtilization) {
		this.ventilatorUtilization = ventilatorUtilization;
	}

	public Float getNoOfSurgicalCases() {
		return noOfSurgicalCases;
	}

	public void setNoOfSurgicalCases(Float noOfSurgicalCases) {
		this.noOfSurgicalCases = noOfSurgicalCases;
	}

	public Float getAntibioticUtilization() {
		return antibioticUtilization;
	}

	public void setAntibioticUtilization(Float antibioticUtilization) {
		this.antibioticUtilization = antibioticUtilization;
	}

	public Integer getMeanGestationWeek() {
		return meanGestationWeek;
	}

	public void setMeanGestationWeek(Integer meanGestationWeek) {
		this.meanGestationWeek = meanGestationWeek;
	}

	public Integer getMeanGestationDay() {
		return meanGestationDay;
	}

	public void setMeanGestationDay(Integer meanGestationDay) {
		this.meanGestationDay = meanGestationDay;
	}

	public Float getMeanWeight() {
		return meanWeight;
	}

	public void setMeanWeight(Float meanWeight) {
		this.meanWeight = meanWeight;
	}

	public Float getAvgLengthOfStay() {
		return avgLengthOfStay;
	}

	public void setAvgLengthOfStay(Float avgLengthOfStay) {
		this.avgLengthOfStay = avgLengthOfStay;
	}

	public Float getReadmissionRate() {
		return readmissionRate;
	}

	public void setReadmissionRate(Float readmissionRate) {
		this.readmissionRate = readmissionRate;
	}

	public Float getBedOccupancy() {
		return bedOccupancy;
	}

	public void setBedOccupancy(Float bedOccupancy) {
		this.bedOccupancy = bedOccupancy;
	}

	public Float getMortality_nec() {
		return mortality_nec;
	}

	public void setMortality_nec(Float mortality_nec) {
		this.mortality_nec = mortality_nec;
	}

	public Float getMortality_bpd() {
		return mortality_bpd;
	}

	public void setMortality_bpd(Float mortality_bpd) {
		this.mortality_bpd = mortality_bpd;
	}

	public Float getMortality_retinopathy() {
		return mortality_retinopathy;
	}

	public void setMortality_retinopathy(Float mortality_retinopathy) {
		this.mortality_retinopathy = mortality_retinopathy;
	}

	public Float getMortality_vlbw() {
		return mortality_vlbw;
	}

	public void setMortality_vlbw(Float mortality_vlbw) {
		this.mortality_vlbw = mortality_vlbw;
	}

	public Float getMortality_sepsis() {
		return mortality_sepsis;
	}

	public void setMortality_sepsis(Float mortality_sepsis) {
		this.mortality_sepsis = mortality_sepsis;
	}

	public Float getMortality_ivh() {
		return mortality_ivh;
	}

	public void setMortality_ivh(Float mortality_ivh) {
		this.mortality_ivh = mortality_ivh;
	}

	@Override
	public String toString() {
		return "QIJSON [noOfBabies=" + noOfBabies + ", noOfBabiesIp=" + noOfBabiesIp + ", noOfBabiesOp=" + noOfBabiesOp
				+ ", noOfMortality=" + noOfMortality + ", noOfMortalityIp=" + noOfMortalityIp + ", noOfMortalityOp="
				+ noOfMortalityOp + ", noOfNurses=" + noOfNurses + ", noOfDoctors=" + noOfDoctors
				+ ", breastFeedingRate=" + breastFeedingRate + ", earlyOnsetSepsisRate=" + earlyOnsetSepsisRate
				+ ", clabsiRate=" + clabsiRate + ", centralLineUtilization=" + centralLineUtilization
				+ ", ventilatorUtilization=" + ventilatorUtilization + ", noOfSurgicalCases=" + noOfSurgicalCases
				+ ", antibioticUtilization=" + antibioticUtilization + ", meanGestationWeek=" + meanGestationWeek
				+ ", meanGestationDay=" + meanGestationDay + ", meanWeight=" + meanWeight + ", avgLengthOfStay="
				+ avgLengthOfStay + ", readmissionRate=" + readmissionRate + ", bedOccupancy=" + bedOccupancy
				+ ", mortality_nec=" + mortality_nec + ", mortality_bpd=" + mortality_bpd + ", mortality_retinopathy="
				+ mortality_retinopathy + ", mortality_vlbw=" + mortality_vlbw + ", mortality_sepsis="
				+ mortality_sepsis + ", mortality_ivh=" + mortality_ivh + "]";
	}

}
