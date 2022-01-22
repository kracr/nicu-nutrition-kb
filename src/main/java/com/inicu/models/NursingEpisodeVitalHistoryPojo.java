package com.inicu.models;

import java.sql.Timestamp;

import com.inicu.postgres.entities.NursingEpisode;
import com.inicu.postgres.entities.NursingVitalparameter;

public class NursingEpisodeVitalHistoryPojo {

	public NursingEpisodeVitalHistoryPojo() {
		super();
	}

	Timestamp creationTime;
	String nnVitalparameterTime;
	String eventType;
	NursingEpisode previousEpisodeData = new NursingEpisode();
	NursingVitalparameter previousVitalParamData = new NursingVitalparameter();

	public Timestamp getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
	}

	public String getNnVitalparameterTime() {
		return nnVitalparameterTime;
	}

	public void setNnVitalparameterTime(String nnVitalparameterTime) {
		this.nnVitalparameterTime = nnVitalparameterTime;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public NursingEpisode getPreviousEpisodeData() {
		return previousEpisodeData;
	}

	public void setPreviousEpisodeData(NursingEpisode previousEpisodeData) {
		this.previousEpisodeData = previousEpisodeData;
	}

	public NursingVitalparameter getPreviousVitalParamData() {
		return previousVitalParamData;
	}

	public void setPreviousVitalParamData(NursingVitalparameter previousVitalParamData) {
		this.previousVitalParamData = previousVitalParamData;
	}

	@Override
	public String toString() {
		return "NursingEpisodeVitalHistoryPojo [creationTime=" + creationTime + ", nnVitalparameterTime="
				+ nnVitalparameterTime + ", eventType=" + eventType + ", previousEpisodeData=" + previousEpisodeData
				+ ", previousVitalParamData=" + previousVitalParamData + "]";
	}

}
