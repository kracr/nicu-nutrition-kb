package com.inicu.models;
import java.sql.Timestamp;

public class DeviceGraphAssessmentJSON {
	
	Timestamp startDate;
	Timestamp endDate;
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

}

