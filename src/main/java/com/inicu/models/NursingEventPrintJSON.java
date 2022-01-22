package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.NursingEpisode;

public class NursingEventPrintJSON {
	
	List<NursingEpisode> episodeList;

	Long fromTime;

	Long toTime;

	Long dateofAdmission;

	public List<NursingEpisode> getEpisodeList() {
		return episodeList;
	}

	public void setEpisodeList(List<NursingEpisode> episodeList) {
		this.episodeList = episodeList;
	}

	public Long getFromTime() {
		return fromTime;
	}

	public void setFromTime(Long fromTime) {
		this.fromTime = fromTime;
	}

	public Long getToTime() {
		return toTime;
	}

	public void setToTime(Long toTime) {
		this.toTime = toTime;
	}

	public Long getDateofAdmission() {
		return dateofAdmission;
	}

	public void setDateofAdmission(Long dateofAdmission) {
		this.dateofAdmission = dateofAdmission;
	}

	@Override
	public String toString() {
		return "NursingEventPrintJSON [episodeList=" + episodeList
				+ ", fromTime=" + fromTime + ", toTime=" + toTime
				+ ", dateofAdmission=" + dateofAdmission + "]";
	}

}
