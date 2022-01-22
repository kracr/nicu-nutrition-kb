package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the declined_apnea_event database table.
 * 
 */
@Entity
@Table(name="declined_apnea_event")
@NamedQuery(name="DeclinedApneaEvent.findAll", query="SELECT d FROM DeclinedApneaEvent d")
public class DeclinedApneaEvent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="declined_apnea_event_id")
	private Long declinedApneaEventId;

	@Column(name="creation_time")
	private Timestamp creationTime;

	private String uhid;

	@Column(name="event_type")
	private String eventType;

	@Column(name="heart_rate")
	private String heartRate;
	
	private String spo2;
	
	@Column(name="pulse_rate")
	private String pulseRate;

	@Column(name="start_time")
	private Timestamp startTime;

	private Integer duration;

	@Column(columnDefinition="bool")
	private Boolean isSecond;
	
	private String reason;
	
	@Column(name="events_done")
	private String eventsDone;

	@Column(columnDefinition="bool")
	@Transient
	private Boolean visibility;
	
	public Timestamp getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getHeartRate() {
		return heartRate;
	}

	public void setHeartRate(String heartRate) {
		this.heartRate = heartRate;
	}

	public String getSpo2() {
		return spo2;
	}

	public void setSpo2(String spo2) {
		this.spo2 = spo2;
	}

	public String getPulseRate() {
		return pulseRate;
	}

	public void setPulseRate(String pulseRate) {
		this.pulseRate = pulseRate;
	}
	
	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Boolean getIsSecond() {
		return isSecond;
	}

	public void setIsSecond(Boolean isSecond) {
		this.isSecond = isSecond;
	}	

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getEventsDone() {
		return eventsDone;
	}

	public void setEventsDone(String eventsDone) {
		this.eventsDone = eventsDone;
	}

	public Boolean getVisibility() {
		return visibility;
	}

	public void setVisibility(Boolean visibility) {
		this.visibility = visibility;
	}
	
}