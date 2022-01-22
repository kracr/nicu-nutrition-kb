package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.NursingIntakeOutput;

public class NursingIntakeOutputPrintJSON {

	List<NursingIntakeOutput> intakeOutputList;

	Long fromTime;

	Long toTime;

	Long dateofAdmission;

	public Float getNursingWeight() {
		return nursingWeight;
	}

	public void setNursingWeight(Float nursingWeight) {
		this.nursingWeight = nursingWeight;
	}

	Float nursingWeight;


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

	public List<NursingIntakeOutput> getIntakeOutputList() {
		return intakeOutputList;
	}

	public void setIntakeOutputList(List<NursingIntakeOutput> intakeOutputList) {
		this.intakeOutputList = intakeOutputList;
	}

	@Override
	public String toString() {
		return "NursingIntakeOutputPrintJSON [intakeOutputList=" + intakeOutputList + ", fromTime=" + fromTime
				+ ", toTime=" + toTime + ", dateofAdmission=" + dateofAdmission + "]";
	}

}
