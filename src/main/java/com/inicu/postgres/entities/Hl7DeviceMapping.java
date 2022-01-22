package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the hl7_device_mapping database table.
 * 
 */
@Entity
@Table(name="hl7_device_mapping")
@NamedQuery(name="Hl7DeviceMapping.findAll", query="SELECT h FROM Hl7DeviceMapping h")
public class Hl7DeviceMapping implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long devicemappingid;

	private Timestamp creationtime;

	@Column(name="hl7_bedid")
	private String hl7Bedid;

	@Column(name="hl7_roomid")
	private String hl7Roomid;

	@Column(name="inicu_bedid")
	private String inicuBedid;

	@Column(name="inicu_roomid")
	private String inicuRoomid;
	
	private String uhid;
	
	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	private Timestamp time_to;
	
	@Column(columnDefinition="bool")
	private Boolean isactive;

	private Timestamp modificationtime;

	public Hl7DeviceMapping() {
	}
	
	public Timestamp getTime_to() {
		return time_to;
	}



	public void setTime_to(Timestamp time_to) {
		this.time_to = time_to;
	}



	public Boolean getIsactive() {
		return isactive;
	}



	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}



	public Long getDevicemappingid() {
		return this.devicemappingid;
	}

	public void setDevicemappingid(Long devicemappingid) {
		this.devicemappingid = devicemappingid;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getHl7Bedid() {
		return this.hl7Bedid;
	}

	public void setHl7Bedid(String hl7Bedid) {
		this.hl7Bedid = hl7Bedid;
	}

	public String getHl7Roomid() {
		return this.hl7Roomid;
	}

	public void setHl7Roomid(String hl7Roomid) {
		this.hl7Roomid = hl7Roomid;
	}

	public String getInicuBedid() {
		return this.inicuBedid;
	}

	public void setInicuBedid(String inicuBedid) {
		this.inicuBedid = inicuBedid;
	}

	public String getInicuRoomid() {
		return this.inicuRoomid;
	}

	public void setInicuRoomid(String inicuRoomid) {
		this.inicuRoomid = inicuRoomid;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

}