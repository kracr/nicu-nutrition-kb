package com.inicu.models;

import java.sql.Time;
import java.sql.Timestamp;

public class TimeObj {
	private Timestamp date;
	private Object hours;
	private Object minutes;
	private Object seconds;
	private Object meridium;
	public TimeObj(){
		this.hours="";
		this.minutes = "";
		this.meridium = "";
		this.seconds = "";
	}
	public Object getHours() {
		return hours;
	}
	public void setHours(Object hours) {
		this.hours = hours;
	}
	public Object getMinutes() {
		return minutes;
	}
	public void setMinutes(Object minutes) {
		this.minutes = minutes;
	}
	public Object getMeridium() {
		return meridium;
	}
	public void setMeridium(Object meridium) {
		this.meridium = meridium;
	}
	public Object getSeconds() {
		return seconds;
	}
	public void setSeconds(Object seconds) {
		this.seconds = seconds;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	
	
	

	/*"timeOfBirth": {
	    "hours": "1",
	    "minutes": "2",
	    "meridium": "AM"
	  },*/
}