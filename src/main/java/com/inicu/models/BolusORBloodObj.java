package com.inicu.models;

public class BolusORBloodObj {

	//common for bolus and blood
	private Long id;
	
	private String bolustype;
	
	private String dose;

	private String duration;
	
	private String starttime;
	
	private String uhid;
	
	private String bolusBloodTime;
	
	//specific to blood product..
	private String bloodgroup;   
	
	private String bloodproduct;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	
	public String getBolustype() {
		return bolustype;
	}

	public void setBolustype(String bolustype) {
		this.bolustype = bolustype;
	}

	public String getDose() {
		return dose;
	}

	public void setDose(String dose) {
		this.dose = dose;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getBloodgroup() {
		return bloodgroup;
	}

	public void setBloodgroup(String bloodgroup) {
		this.bloodgroup = bloodgroup;
	}

	public String getBloodproduct() {
		return bloodproduct;
	}

	public void setBloodproduct(String bloodproduct) {
		this.bloodproduct = bloodproduct;
	}

	public String getBolusBloodTime() {
		return bolusBloodTime;
	}

	public void setBolusBloodTime(String bolusBloodTime) {
		this.bolusBloodTime = bolusBloodTime;
	}
	
	
	
	
}
