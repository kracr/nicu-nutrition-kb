package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the nursing_neurovitals database table.
 * 
 */
@Entity
@Table(name="nursing_neurovitals")
@NamedQuery(name="NursingNeuroVitals.findAll", query="SELECT n FROM NursingNeuroVitals n")
public class NursingNeuroVitals implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="nn_neorovitalsid")
	private Long nnNeorovitalsid;

	private Integer ccp;

	private Timestamp creationtime;

	private String gcs;

	private String loggeduser;
	private Integer icp;

	private Timestamp modificationtime;

	@Column(name="nn_neurovitals_time")
	private String nnNeurovitalsTime;

	@Column(name="sedation_score")
	private String sedationScore;

	private String uhid;

	public NursingNeuroVitals() {
	}

	public Long getNnNeorovitalsid() {
		return this.nnNeorovitalsid;
	}

	public void setNnNeorovitalsid(Long nnNeorovitalsid) {
		this.nnNeorovitalsid = nnNeorovitalsid;
	}

	public Integer getCcp() {
		return this.ccp;
	}

	public void setCcp(Integer ccp) {
		this.ccp = ccp;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getGcs() {
		return this.gcs;
	}

	public void setGcs(String gcs) {
		this.gcs = gcs;
	}

	public Integer getIcp() {
		return this.icp;
	}

	public void setIcp(Integer icp) {
		this.icp = icp;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getNnNeurovitalsTime() {
		return this.nnNeurovitalsTime;
	}

	public void setNnNeurovitalsTime(String nnNeurovitalsTime) {
		this.nnNeurovitalsTime = nnNeurovitalsTime;
	}

	public String getSedationScore() {
		return this.sedationScore;
	}

	public void setSedationScore(String sedationScore) {
		this.sedationScore = sedationScore;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}
	public String getLoggeduser() {
		return this.loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

}