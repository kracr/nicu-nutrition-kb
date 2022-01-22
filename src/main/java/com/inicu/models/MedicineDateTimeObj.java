package com.inicu.models;

import java.util.List;

import com.inicu.models.GivenMedicineTimeObj;


public class MedicineDateTimeObj {

	private String date;
	
	private List<GivenMedicineTimeObj> time;
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public List<GivenMedicineTimeObj> getTime() {
		return time;
	}
	public void setTime(List<GivenMedicineTimeObj> time) {
		this.time = time;
	}
	
	
}

  