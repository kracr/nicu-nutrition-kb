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

/**
 * The persistent class for the nursingorder_apnea database table.
 * 
 */
@Entity
@Table(name = "nursingorder_apnea")
@NamedQuery(name = "NursingorderApnea.findAll", query = "SELECT n FROM NursingorderApnea n")
public class NursingorderApnea implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long nursingorderapneaid;

	private String apneaid;

	@Column(name = "caffeine_actiontime")
	private Timestamp caffeineActiontime;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	@Column(name = "nursing_comments")
	private String nursingComments;

	@Column(name = "respiratory_support_actiontime")
	private Timestamp respiratorySupportActiontime;

	private String uhid;

	public NursingorderApnea() {
		super();
	}

	public Long getNursingorderapneaid() {
		return this.nursingorderapneaid;
	}

	public void setNursingorderapneaid(Long nursingorderapneaid) {
		this.nursingorderapneaid = nursingorderapneaid;
	}

	public String getApneaid() {
		return this.apneaid;
	}

	public void setApneaid(String apneaid) {
		this.apneaid = apneaid;
	}

	public Timestamp getCaffeineActiontime() {
		return this.caffeineActiontime;
	}

	public void setCaffeineActiontime(Timestamp caffeineActiontime) {
		this.caffeineActiontime = caffeineActiontime;
	}

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

	public String getNursingComments() {
		return this.nursingComments;
	}

	public void setNursingComments(String nursingComments) {
		this.nursingComments = nursingComments;
	}

	public Timestamp getRespiratorySupportActiontime() {
		return this.respiratorySupportActiontime;
	}

	public void setRespiratorySupportActiontime(Timestamp respiratorySupportActiontime) {
		this.respiratorySupportActiontime = respiratorySupportActiontime;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

}