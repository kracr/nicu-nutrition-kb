package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;


/**
 * The persistent class for the device_exceptions database table.
 * 
 */
@Entity
@Table(name="device_exceptions")
@NamedQuery(name="DeviceException.findAll", query="SELECT i FROM DeviceException i")
public class DeviceException implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long exceptionid;
	
	private Timestamp creationtime;
	
	private Timestamp modificationtime;
	
	private String deviceserialno;
	
	private String exceptionmessage;

	public Long getExceptionid() {
		return exceptionid;
	}

	public void setExceptionid(Long exceptionid) {
		this.exceptionid = exceptionid;
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

	public String getExceptionmessage() {
		return exceptionmessage;
	}

	public void setExceptionmessage(String exceptionmessage) {
		this.exceptionmessage = exceptionmessage;
	}

		
}