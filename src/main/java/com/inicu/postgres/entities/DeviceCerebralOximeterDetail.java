package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the device_monitor_detail database table.
 * 
 */
@Entity
@Table(name="device_cerebral_oximeter_detail")
@NamedQuery(name="DeviceCerebralOximeterDetail.findAll", query="SELECT d FROM DeviceCerebralOximeterDetail d")
public class DeviceCerebralOximeterDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long devicecerebraloximeterid;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String uhid;
	
	private String beddeviceid;

	private String channel1_rso2;
	
	private String channel2_rso2;
	
	private String channel3_rso2;

	private String channel4_rso2;
	
	private Timestamp starttime;

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

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getBeddeviceid() {
		return beddeviceid;
	}

	public void setBeddeviceid(String beddeviceid) {
		this.beddeviceid = beddeviceid;
	}

	public String getChannel1_rso2() {
		return channel1_rso2;
	}

	public void setChannel1_rso2(String channel1_rso2) {
		this.channel1_rso2 = channel1_rso2;
	}

	public String getChannel2_rso2() {
		return channel2_rso2;
	}

	public void setChannel2_rso2(String channel2_rso2) {
		this.channel2_rso2 = channel2_rso2;
	}

	public String getChannel3_rso2() {
		return channel3_rso2;
	}

	public void setChannel3_rso2(String channel3_rso2) {
		this.channel3_rso2 = channel3_rso2;
	}

	public String getChannel4_rso2() {
		return channel4_rso2;
	}

	public void setChannel4_rso2(String channel4_rso2) {
		this.channel4_rso2 = channel4_rso2;
	}

	public Timestamp getStarttime() {
		return starttime;
	}

	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}
}