package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;



import java.sql.Timestamp;


/**
 * The persistent class for the pupil_reactivity database table.
 * 
 */
@Entity
@Table(name="pupil_reactivity")
@NamedQuery(name="PupilReactivity.findAll", query="SELECT p FROM PupilReactivity p")
public class PupilReactivity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long pupilreactivityid;

	private String comments;

	private Timestamp creationtime;

	private String equality;

	@Column(name="left_pupilsize")
	private String leftPupilsize;

	@Column(name="left_reactivity")
	private String leftReactivity;

	private Timestamp modificationtime;

	@Column(name="pupil_time")
	private String pupilRectivityTime;

	@Column(name="right_pupilsize")
	private String rightPupilsize;

	@Column(name="right_reactivity")
	private String rightReactivity;

	private String uhid;


	public PupilReactivity() {
	}

	public Long getPupilreactivityid() {
		return this.pupilreactivityid;
	}

	public void setPupilreactivityid(Long pupilreactivityid) {
		this.pupilreactivityid = pupilreactivityid;
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

	public String getLeftPupilsize() {
		return this.leftPupilsize;
	}

	public void setLeftPupilsize(String leftPupilsize) {
		this.leftPupilsize = leftPupilsize;
	}

	public String getLeftReactivity() {
		return this.leftReactivity;
	}

	public void setLeftReactivity(String leftReactivity) {
		this.leftReactivity = leftReactivity;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	
	public String getRightPupilsize() {
		return this.rightPupilsize;
	}

	public void setRightPupilsize(String rightPupilsize) {
		this.rightPupilsize = rightPupilsize;
	}

	public String getRightReactivity() {
		return this.rightReactivity;
	}

	public void setRightReactivity(String rightReactivity) {
		this.rightReactivity = rightReactivity;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}
	public String getPupilTime()
	{
		return this.pupilRectivityTime;
	}
	public void setPupilTime(String pupil_rectivity_time)
	{
		this.pupilRectivityTime = pupil_rectivity_time;
	}
	
}