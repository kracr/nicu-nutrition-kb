package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the apnea_event database table.
 * 
 */
@Entity
@Table(name="apnea_event")
@NamedQuery(name="ApneaEvent.findAll", query="SELECT a FROM ApneaEvent a")
public class ApneaEvent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="apnea_event_id")
	private Long apneaEventId;

	@Column(name="creation_time")
	private Timestamp creationTime;

	@Column(name="server_time")
	private Timestamp serverTime;

	private String uhid;

	@Column(name="event_type")
	private String eventType;

	@Column(name="heart_rate")
	private Integer heartRate;
	
	private Integer spo2;
	
	@Column(name="pulse_rate")
	private Integer pulseRate;
	
	private String marked;

	public Timestamp getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
	}

	public Timestamp getServerTime() {
		return serverTime;
	}

	public void setServerTime(Timestamp serverTime) {
		this.serverTime = serverTime;
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

	public Integer getHeartRate() {
		return heartRate;
	}

	public void setHeartRate(Integer heartRate) {
		this.heartRate = heartRate;
	}

	public Integer getSpo2() {
		return spo2;
	}

	public void setSpo2(Integer spo2) {
		this.spo2 = spo2;
	}

	public Integer getPulseRate() {
		return pulseRate;
	}

	public void setPulseRate(Integer pulseRate) {
		this.pulseRate = pulseRate;
	}

	public String getMarked() {
		return marked;
	}

	public void setMarked(String marked) {
		this.marked = marked;
	}

	public Long getApneaEventId() {
		return apneaEventId;
	}

	public void setApneaEventId(Long apneaEventId) {
		this.apneaEventId = apneaEventId;
	}
}