package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "satisfaction_form")
@NamedQuery(name = "SatisfactionForm.findAll", query = "SELECT r FROM SatisfactionForm r")
public class SatisfactionForm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long satisfactionformid;

	private Timestamp creationtime;

	private Timestamp modificationtime;
	
	private String uhid;
	private String type;
	private String loggeduser;
	private Integer experience;
	private Integer easeofuse;
	private Integer quality;
	private Integer information;
	
	public Long getSatisfactionformid() {
		return satisfactionformid;
	}
	public void setSatisfactionformid(Long satisfactionformid) {
		this.satisfactionformid = satisfactionformid;
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
	public String getUhid() {
		return uhid;
	}
	public void setUhid(String uhid) {
		this.uhid = uhid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLoggeduser() {
		return loggeduser;
	}
	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}
	public Integer getExperience() {
		return experience;
	}
	public void setExperience(Integer experience) {
		this.experience = experience;
	}
	public Integer getEaseofuse() {
		return easeofuse;
	}
	public void setEaseofuse(Integer easeofuse) {
		this.easeofuse = easeofuse;
	}
	public Integer getQuality() {
		return quality;
	}
	public void setQuality(Integer quality) {
		this.quality = quality;
	}
	public Integer getInformation() {
		return information;
	}
	public void setInformation(Integer information) {
		this.information = information;
	}
	
	


}
