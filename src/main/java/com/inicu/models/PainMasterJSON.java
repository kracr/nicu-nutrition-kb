package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.ScorePain;

public class PainMasterJSON {

	List<ScorePain> patientScorePainList;
	List<GenericScoreStatusJSON> painStatusList;

	public List<ScorePain> getPatientScorePainList() {
		return patientScorePainList;
	}

	public void setPatientScorePainList(List<ScorePain> patientScorePainList) {
		this.patientScorePainList = patientScorePainList;
	}

	public List<GenericScoreStatusJSON> getPainStatusList() {
		return painStatusList;
	}

	public void setPainStatusList(List<GenericScoreStatusJSON> painStatusList) {
		this.painStatusList = painStatusList;
	}

}
