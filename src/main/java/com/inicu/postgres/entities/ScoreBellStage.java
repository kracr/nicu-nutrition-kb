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
@Table(name = "score_bellstage")
@NamedQuery(name = "ScoreBellStage.findAll", query = "SELECT s FROM ScoreBellStage s")
public class ScoreBellStage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long bellstagescoreid;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String uhid;

	private String systemsign;
	private Integer systemsignscore;

	private String intestinalsign;
	private Integer intestinalsignscore;

	private String radiologicalsign;
	private Integer radiologicalsignscore;
	
	private String treatmentsign;
	private Integer treatmentscore;

	private Integer bellstagescore;

	public ScoreBellStage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getBellstagescoreid() {
		return bellstagescoreid;
	}

	public void setBellstagescoreid(Long bellstagescoreid) {
		this.bellstagescoreid = bellstagescoreid;
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

	public String getSystemsign() {
		return systemsign;
	}

	public void setSystemsign(String systemsign) {
		this.systemsign = systemsign;
	}

	public Integer getSystemsignscore() {
		return systemsignscore;
	}

	public void setSystemsignscore(Integer systemsignscore) {
		this.systemsignscore = systemsignscore;
	}

	public String getIntestinalsign() {
		return intestinalsign;
	}

	public void setIntestinalsign(String intestinalsign) {
		this.intestinalsign = intestinalsign;
	}

	public Integer getIntestinalsignscore() {
		return intestinalsignscore;
	}

	public void setIntestinalsignscore(Integer intestinalsignscore) {
		this.intestinalsignscore = intestinalsignscore;
	}

	public String getRadiologicalsign() {
		return radiologicalsign;
	}

	public void setRadiologicalsign(String radiologicalsign) {
		this.radiologicalsign = radiologicalsign;
	}

	public Integer getRadiologicalsignscore() {
		return radiologicalsignscore;
	}

	public void setRadiologicalsignscore(Integer radiologicalsignscore) {
		this.radiologicalsignscore = radiologicalsignscore;
	}

	public Integer getBellstagescore() {
		return bellstagescore;
	}

	public void setBellstagescore(Integer bellstagescore) {
		this.bellstagescore = bellstagescore;
	}

	public String getTreatmentsign() {
		return treatmentsign;
	}

	public void setTreatmentsign(String treatmentsign) {
		this.treatmentsign = treatmentsign;
	}

	public Integer getTreatmentscore() {
		return treatmentscore;
	}

	public void setTreatmentscore(Integer treatmentscore) {
		this.treatmentscore = treatmentscore;
	}
}
