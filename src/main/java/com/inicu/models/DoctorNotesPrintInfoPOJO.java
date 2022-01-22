package com.inicu.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

public class DoctorNotesPrintInfoPOJO {

	private Boolean isNotesBySystem;
	
	@NotBlank
	private String uhid;
	
	@NotBlank
	private List<String> moduleName;
	
	@NotBlank
	private String dateFrom;
	
	@NotBlank
	private String timeFrom;
	
	@NotBlank
	private String dateTo;
	
	@NotBlank
	private String timeTo;
	

	public DoctorNotesPrintInfoPOJO() {
		super();
		}

	public Boolean getIsNotesBySystem() {
		return isNotesBySystem;
	}

	public String getUhid() {
		return uhid;
	}

	

	public String getDateFrom() {
		return dateFrom;
	}

	public String getTimeFrom() {
		return timeFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public String getTimeTo() {
		return timeTo;
	}

	public void setIsNotesBySystem(Boolean isNotesBySystem) {
		this.isNotesBySystem = isNotesBySystem;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}


	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public void setTimeFrom(String timeFrom) {
		this.timeFrom = timeFrom;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public void setTimeTo(String timeTo) {
		this.timeTo = timeTo;
	}

	public List<String> getModuleName() {
		return moduleName;
	}

	public void setModuleName(List<String> moduleName) {
		this.moduleName = moduleName;
	}

	

	

	
	
	
	
}
