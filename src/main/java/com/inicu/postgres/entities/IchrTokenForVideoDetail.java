package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the device_monitor_detail database table.
 * 
 */
@Entity
@Table(name="ichr_token_for_video_detail")
@NamedQuery(name="IchrTokenForVideoDetail.findAll", query="SELECT d FROM IchrTokenForVideoDetail d")
public class IchrTokenForVideoDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long ichr_token_for_video_detail_id;

	
	private Timestamp creationtime;

	
	private Timestamp modificationtime;

	private String uhid;
	
	private String token;
	
	private Timestamp tokenEndTime;
	

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}



	public Long getIchr_token_for_video_detail_id() {
		return ichr_token_for_video_detail_id;
	}

	public void setIchr_token_for_video_detail_id(Long ichr_token_for_video_detail_id) {
		this.ichr_token_for_video_detail_id = ichr_token_for_video_detail_id;
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Timestamp getTokenEndTime() {
		return tokenEndTime;
	}

	public void setTokenEndTime(Timestamp tokenEndTime) {
		this.tokenEndTime = tokenEndTime;
	}


}