package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;


/**
 * The persistent class for the device_exceptions database table.
 * 
 */
@Entity
@Table(name="device_heartbeat")
@NamedQuery(name="DeviceHeartbeat.findAll", query="SELECT i FROM DeviceHeartbeat i")
public class DeviceHeartbeat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
	private Long heartbeatid;
	
	private Timestamp creationtime;
	
	private Timestamp modificationtime;
	
	private String deviceserialno;
	
	private String devicedata;

	public Long getheartbeatid() {
		return heartbeatid;
	}

	public void setheartbeatid(Long exceptionid) {
		this.heartbeatid = exceptionid;
	}

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public Timestamp getModificationtime() {
		return modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getDeviceserialno() {
		return deviceserialno;
	}

	public void setDeviceserialno(String deviceserialno) {
		this.deviceserialno = deviceserialno;
	}

	public String getdatamessage() {
		return devicedata;
	}

	public void setdatamessage(String exceptionmessage) {
		this.devicedata = exceptionmessage;
	}

		
}