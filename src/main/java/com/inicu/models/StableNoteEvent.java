package com.inicu.models;

import java.sql.Timestamp;

import javax.persistence.Column;

public class StableNoteEvent {

	private Timestamp creationtime;
	
	private String eventType;

	private String duration;

	private String hr;
	
	private String spo2;
	
	private String recovery;

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getHr() {
		return hr;
	}

	public void setHr(String hr) {
		this.hr = hr;
	}

	public String getSpo2() {
		return spo2;
	}

	public void setSpo2(String spo2) {
		this.spo2 = spo2;
	}

	public String getRecovery() {
		return recovery;
	}

	public void setRecovery(String recovery) {
		this.recovery = recovery;
	}

		
	
}
