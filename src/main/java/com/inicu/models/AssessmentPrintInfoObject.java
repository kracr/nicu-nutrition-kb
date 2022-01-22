package com.inicu.models;


import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class AssessmentPrintInfoObject {

	
	@NotBlank
	private String uhid;
	
	@NotBlank
	private String moduleName;
	
	@NotBlank
	private String dateFrom;
	
	@NotBlank
	private String timeFrom;
	
	@NotBlank
	private String dateTo;
	
	@NotBlank
	private String timeTo;

	public String getUhid() {
		return uhid;
	}

	public String getModuleName() {
		return moduleName;
	}

	
	public String getTimeFrom() {
		return timeFrom;
	}

	
	public String getTimeTo() {
		return timeTo;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	

	public void setTimeFrom(String timeFrom) {
		this.timeFrom = timeFrom;
	}

	

	public void setTimeTo(String timeTo) {
		this.timeTo = timeTo;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}
	
	
	
}
