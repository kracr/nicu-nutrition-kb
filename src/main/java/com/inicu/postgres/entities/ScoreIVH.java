package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "score_ivh")
@NamedQuery(name = "ScoreIVH.findAll", query = "SELECT s FROM ScoreIVH s")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScoreIVH implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long ivhscoreid;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String uhid;
	
	private Integer grade1;

	private Integer grade2;

	private Integer grade3;

	private Integer grade4;

	private Integer ivhscore;
	
	public ScoreIVH() {
		super();
	}

	public Long getIvhscoreid() {
		return ivhscoreid;
	}

	public void setIvhscoreid(Long ivhscoreid) {
		this.ivhscoreid = ivhscoreid;
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

	public Integer getGrade1() {
		return grade1;
	}

	public void setGrade1(Integer grade1) {
		this.grade1 = grade1;
	}

	public Integer getGrade2() {
		return grade2;
	}

	public void setGrade2(Integer grade2) {
		this.grade2 = grade2;
	}

	public Integer getGrade3() {
		return grade3;
	}

	public void setGrade3(Integer grade3) {
		this.grade3 = grade3;
	}

	public Integer getGrade4() {
		return grade4;
	}

	public void setGrade4(Integer grade4) {
		this.grade4 = grade4;
	}

	public Integer getIvhscore() {
		return ivhscore;
	}

	public void setIvhscore(Integer ivhscore) {
		this.ivhscore = ivhscore;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
