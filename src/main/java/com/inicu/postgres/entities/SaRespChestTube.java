package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "sa_resp_chesttube")
@NamedQuery(name = "SaRespChestTube.findAll", query = "SELECT s FROM SaRespChestTube s")
public class SaRespChestTube implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long respchesttubeid;

	private Timestamp creationtime;

	private String uhid;
	
	private String resppneumothoraxid;
	
	@Column(name = "chesttube_value")
	private String chesttubeValue;
	
	@Column(name = "chesttube_adjusted_value")
	String chestTubeAdjustedValue;

	@Column(columnDefinition="bool")
	Boolean isLeftChestTube;
	
	@Column(columnDefinition="bool")
	Boolean isTubeReadyToAdjust;
	
	@Column(columnDefinition="bool")
	Boolean isTubeRemoved;

	@Column(columnDefinition="bool")
	Boolean isActive;
	
	private Timestamp removetime;
	
	@Transient private Boolean isClampReadyToAdjust;
	
	
	private String clampStatus;
	
	public SaRespChestTube() {
		super();
		this.isTubeReadyToAdjust = false;
		this.isActive = true;
		// TODO Auto-generated constructor stub
	}

	public Long getRespchesttubeid() {
		return respchesttubeid;
	}

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public String getUhid() {
		return uhid;
	}

	public String getResppneumothoraxid() {
		return resppneumothoraxid;
	}

	public String getChesttubeValue() {
		return chesttubeValue;
	}

	public String getChestTubeAdjustedValue() {
		return chestTubeAdjustedValue;
	}

	public Boolean getIsLeftChestTube() {
		return isLeftChestTube;
	}

	public void setRespchesttubeid(Long respchesttubeid) {
		this.respchesttubeid = respchesttubeid;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public void setResppneumothoraxid(String resppneumothoraxid) {
		this.resppneumothoraxid = resppneumothoraxid;
	}

	public void setChesttubeValue(String chesttubeValue) {
		this.chesttubeValue = chesttubeValue;
	}

	public void setChestTubeAdjustedValue(String chestTubeAdjustedValue) {
		this.chestTubeAdjustedValue = chestTubeAdjustedValue;
	}

	public void setIsLeftChestTube(Boolean isLeftChestTube) {
		this.isLeftChestTube = isLeftChestTube;
	}

	public Boolean getIsTubeReadyToAdjust() {
		return isTubeReadyToAdjust;
	}

	public void setIsTubeReadyToAdjust(Boolean isTubeReadyToAdjust) {
		this.isTubeReadyToAdjust = isTubeReadyToAdjust;
	}

	public Boolean getIsTubeRemoved() {
		return isTubeRemoved;
	}

	public void setIsTubeRemoved(Boolean isTubeRemoved) {
		this.isTubeRemoved = isTubeRemoved;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Timestamp getRemovetime() {
		return removetime;
	}

	public void setRemovetime(Timestamp removetime) {
		this.removetime = removetime;
	}

	public Boolean getIsClampReadyToAdjust() {
		return isClampReadyToAdjust;
	}

	public void setIsClampReadyToAdjust(Boolean isClampReadyToAdjust) {
		this.isClampReadyToAdjust = isClampReadyToAdjust;
	}

	public String getClampStatus() {
		return clampStatus;
	}

	public void setClampStatus(String clampStatus) {
		this.clampStatus = clampStatus;
	}

	
}
