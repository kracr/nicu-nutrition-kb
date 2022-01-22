package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the vw_pupil_reactivity database table.
 * 
 */
@Entity
@Table(name="vw_pupil_reactivity")
@NamedQuery(name="VwPupilReactivity.findAll", query="SELECT v FROM VwPupilReactivity v")
public class VwPupilReactivity implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer ccp;

	private String comments;

	private Timestamp creationtime;

	private String equality;

	private String gcs;

	private Integer icp;

	private String leftpupilsize;

	private String leftreactivity;
	@Id
	@Column(name="nn_neorovitalsid")
	private Long nnNeorovitalsid;

	@Column(name="nn_neurovitals_time")
	private String nnNeurovitalsTime;

	@Column(name="pupil_time")
	private String pupilTime;

	private String rightpupilsize;

	private String rightreactivity;

	@Column(name="sedation_score")
	private String sedationScore;

	private String uhid;

	public VwPupilReactivity() {
		sedationScore = "";
		gcs = "";
		rightpupilsize = "";
		leftpupilsize = "";
		uhid = "";
	}

	public Integer getCcp() {
		return this.ccp;
	}

	public void setCcp(Integer ccp) {
		this.ccp = ccp;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getEquality() {
		return this.equality;
	}

	public void setEquality(String equality) {
		this.equality = equality;
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

	public String getLeftpupilsize() {
		return this.leftpupilsize;
	}

	public void setLeftpupilsize(String leftpupilsize) {
		this.leftpupilsize = leftpupilsize;
	}

	public String getLeftreactivity() {
		return this.leftreactivity;
	}

	public void setLeftreactivity(String leftreactivity) {
		this.leftreactivity = leftreactivity;
	}

	public Long getNnNeorovitalsid() {
		return this.nnNeorovitalsid;
	}

	public void setNnNeorovitalsid(Long nnNeorovitalsid) {
		this.nnNeorovitalsid = nnNeorovitalsid;
	}

	public String getNnNeurovitalsTime() {
		return this.nnNeurovitalsTime;
	}

	public void setNnNeurovitalsTime(String nnNeurovitalsTime) {
		this.nnNeurovitalsTime = nnNeurovitalsTime;
	}

	public String getPupilTime() {
		return this.pupilTime;
	}

	public void setPupilTime(String pupilTime) {
		this.pupilTime = pupilTime;
	}

	public String getRightpupilsize() {
		return this.rightpupilsize;
	}

	public void setRightpupilsize(String rightpupilsize) {
		this.rightpupilsize = rightpupilsize;
	}

	public String getRightreactivity() {
		return this.rightreactivity;
	}

	public void setRightreactivity(String rightreactivity) {
		this.rightreactivity = rightreactivity;
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

}