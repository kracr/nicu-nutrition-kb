package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.NursingBloodGas;
import com.inicu.postgres.entities.NursingVentilaor;

public class NursingBGPrintJSON {

	List<NursingBloodGas> bloodGasList;

	Long fromTime;

	Long toTime;

	Long dateofAdmission;

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

	public List<NursingBloodGas> getBloodGasList() {
		return bloodGasList;
	}

	public void setBloodGasList(List<NursingBloodGas> bloodGasList) {
		this.bloodGasList = bloodGasList;
	}

	@Override
	public String toString() {
		return "NursingBGPrintJSON [bloodGasList=" + bloodGasList + ", fromTime=" + fromTime + ", toTime=" + toTime
				+ ", dateofAdmission=" + dateofAdmission + "]";
	}

}
