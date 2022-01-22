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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The persistent class for the score_sepsis database table.
 * 
 * @author Sourabh Verma
 */
@Entity
@Table(name = "score_sepsis")
@NamedQuery(name = "ScoreSepsis.findAll", query = "SELECT s FROM ScoreSepsis s")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScoreSepsis implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long sepsisscoreid;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String uhid;

	@Column(name = "total_wbccount")
	private Integer totalWbccount;

	@Column(name = "total_pmncount")
	private Integer totalPmncount;

	@Column(name = "immature_pmncount")
	private Integer immaturePmncount;

	@Column(name = "it_pmnratio")
	private Integer itPmnratio;

	@Column(name = "im_pmnratio")
	private Integer imPmnratio;

	@Column(name = "degenerative_pmn")
	private Integer degenerativePmn;

	private Integer plateletcount;

	private Integer sepsisscore;

	public ScoreSepsis() {
		super();
	}

	public Long getSepsisscoreid() {
		return sepsisscoreid;
	}

	public void setSepsisscoreid(Long sepsisscoreid) {
		this.sepsisscoreid = sepsisscoreid;
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

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public Integer getTotalWbccount() {
		return totalWbccount;
	}

	public void setTotalWbccount(Integer totalWbccount) {
		this.totalWbccount = totalWbccount;
	}

	public Integer getTotalPmncount() {
		return totalPmncount;
	}

	public void setTotalPmncount(Integer totalPmncount) {
		this.totalPmncount = totalPmncount;
	}

	public Integer getImmaturePmncount() {
		return immaturePmncount;
	}

	public void setImmaturePmncount(Integer immaturePmncount) {
		this.immaturePmncount = immaturePmncount;
	}

	public Integer getItPmnratio() {
		return itPmnratio;
	}

	public void setItPmnratio(Integer itPmnratio) {
		this.itPmnratio = itPmnratio;
	}

	public Integer getImPmnratio() {
		return imPmnratio;
	}

	public void setImPmnratio(Integer imPmnratio) {
		this.imPmnratio = imPmnratio;
	}

	public Integer getDegenerativePmn() {
		return degenerativePmn;
	}

	public void setDegenerativePmn(Integer degenerativePmn) {
		this.degenerativePmn = degenerativePmn;
	}

	public Integer getPlateletcount() {
		return plateletcount;
	}

	public void setPlateletcount(Integer plateletcount) {
		this.plateletcount = plateletcount;
	}

	public Integer getSepsisscore() {
		return sepsisscore;
	}

	public void setSepsisscore(Integer sepsisscore) {
		this.sepsisscore = sepsisscore;
	}

}
