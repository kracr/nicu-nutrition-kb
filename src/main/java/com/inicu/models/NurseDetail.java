package com.inicu.models;

import javax.persistence.Column;

public class NurseDetail {
	
	private Long nurseBabyMappingId;
	
	private Long nurseId;

	// nurse name
	private String nurse;

	private String nurseUsername;
	
	private String shiftType;

	public Long getNurseId() {
		return nurseId;
	}

	public void setNurseId(Long nurseId) {
		this.nurseId = nurseId;
	}

	public String getNurse() {
		return nurse;
	}

	public void setNurse(String nurse) {
		this.nurse = nurse;
	}

	public String getNurseUsername() {
		return nurseUsername;
	}

	public void setNurseUsername(String nurseUsername) {
		this.nurseUsername = nurseUsername;
	}

	public String getShiftType() {
		return shiftType;
	}

	public void setShiftType(String shiftType) {
		this.shiftType = shiftType;
	}

	public Long getNurseBabyMappingId() {
		return nurseBabyMappingId;
	}

	public void setNurseBabyMappingId(Long nurseBabyMappingId) {
		this.nurseBabyMappingId = nurseBabyMappingId;
	}
}
