package com.inicu.postgres.entities;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the bed_device_detail database table.
 * 
 */
@Entity
@Table(name="bed_device_detail")
@NamedQuery(name="BedDeviceDetail.findAll", query="SELECT b FROM BedDeviceDetail b")
public class BedDeviceDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long beddeviceid;

	private String bedid;

	private Timestamp creationtime;

	private String deviceid;

	private Timestamp modificationtime;
	
	private String uhid;
	
	@Column(columnDefinition="bool")
	private boolean status;

	@Column(columnDefinition="int8")
	private Long bbox_device_id;
	
	private Timestamp time_to;

    private Timestamp time_from;
	
	public Timestamp getTime_to() {
		return time_to;
	}

	public void setTime_to(Timestamp time_to) {
		this.time_to = time_to;
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Long getBbox_device_id() {
		return bbox_device_id;
	}

	public void setBbox_device_id(Long bbox_device_id) {
		this.bbox_device_id = bbox_device_id;
	}

	public BedDeviceDetail() {
	}

	public Long getBeddeviceid() {
		return this.beddeviceid;
	}

	public void setBeddeviceid(Long beddeviceid) {
		this.beddeviceid = beddeviceid;
	}

	public String getBedid() {
		return this.bedid;
	}

	public void setBedid(String bedid) {
		this.bedid = bedid;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getDeviceid() {
		return this.deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

    public Timestamp getTime_from() {
        return time_from;
    }

    public void setTime_from(Timestamp time_from) {
        this.time_from = time_from;
    }
}