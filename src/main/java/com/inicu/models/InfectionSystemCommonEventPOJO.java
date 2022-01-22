package com.inicu.models;

import java.util.ArrayList;
import java.util.List;

import com.inicu.postgres.entities.BabyPrescription;
import com.inicu.postgres.entities.ScoreDownes;
import com.inicu.postgres.entities.VwAssesmentInfectionFinal;

public class InfectionSystemCommonEventPOJO {

	private List<BabyPrescription> pastPrescriptionList;

	private BabyPrescription babyPrescriptionEmptyObj;

	private List<VwAssesmentInfectionFinal> pastInfectionHistory;

	private boolean downeFlag = false;

	private ScoreDownes downeScoreObj;

	public InfectionSystemCommonEventPOJO() {
		super();
		// TODO Auto-generated constructor stub
		this.babyPrescriptionEmptyObj = new BabyPrescription();
		this.pastPrescriptionList = new ArrayList<BabyPrescription>();
		this.pastInfectionHistory = new ArrayList<VwAssesmentInfectionFinal>();
		this.downeScoreObj = new ScoreDownes();
	}

	public List<BabyPrescription> getPastPrescriptionList() {
		return pastPrescriptionList;
	}

	public void setPastPrescriptionList(List<BabyPrescription> pastPrescriptionList) {
		this.pastPrescriptionList = pastPrescriptionList;
	}

	public BabyPrescription getBabyPrescriptionEmptyObj() {
		return babyPrescriptionEmptyObj;
	}

	public void setBabyPrescriptionEmptyObj(BabyPrescription babyPrescriptionEmptyObj) {
		this.babyPrescriptionEmptyObj = babyPrescriptionEmptyObj;
	}

	public List<VwAssesmentInfectionFinal> getPastInfectionHistory() {
		return pastInfectionHistory;
	}

	public void setPastInfectionHistory(List<VwAssesmentInfectionFinal> pastInfectionHistory) {
		this.pastInfectionHistory = pastInfectionHistory;
	}

	public boolean isDowneFlag() {
		return downeFlag;
	}

	public void setDowneFlag(boolean downeFlag) {
		this.downeFlag = downeFlag;
	}

	public ScoreDownes getDowneScoreObj() {
		return downeScoreObj;
	}

	public void setDowneScoreObj(ScoreDownes downeScoreObj) {
		this.downeScoreObj = downeScoreObj;
	}
}
