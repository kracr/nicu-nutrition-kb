package com.inicu.models;

import java.util.List;

public class NurseBaby {
	
	private Long nurseId;
	
	private String nurse;
	
	private String nurseUsername;
	
	List<BabyBasicDetail> babyList;

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

	public List<BabyBasicDetail> getBabyList() {
		return babyList;
	}

	public void setBabyList(List<BabyBasicDetail> babyList) {
		this.babyList = babyList;
	}
	
	

}
