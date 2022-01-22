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

/**
 * The persistent class for the score_silverman database table.
 * 
 * @author Sourabh Verma
 */
@Entity
@Table(name = "score_silverman")
@NamedQuery(name = "ScoreSilverman.findAll", query = "SELECT s FROM ScoreSilverman s")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScoreSilverman implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long silvermanscoreid;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String uhid;

	private Integer upperchest;

	private Integer lowerchest;

	private Integer xiphoidretract;

	private Integer narasdilat;

	private Integer expirgrunt;

	private Integer silvermanscore;
	
	private Timestamp entrydate;

	public ScoreSilverman() {
		super();
	}

	public Long getSilvermanscoreid() {
		return silvermanscoreid;
	}

	public void setSilvermanscoreid(Long silvermanscoreid) {
		this.silvermanscoreid = silvermanscoreid;
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

	public Integer getUpperchest() {
		return upperchest;
	}

	public void setUpperchest(Integer upperchest) {
		this.upperchest = upperchest;
	}

	public Integer getLowerchest() {
		return lowerchest;
	}

	public void setLowerchest(Integer lowerchest) {
		this.lowerchest = lowerchest;
	}

	public Integer getXiphoidretract() {
		return xiphoidretract;
	}

	public void setXiphoidretract(Integer xiphoidretract) {
		this.xiphoidretract = xiphoidretract;
	}

	public Integer getNarasdilat() {
		return narasdilat;
	}

	public void setNarasdilat(Integer narasdilat) {
		this.narasdilat = narasdilat;
	}

	public Integer getExpirgrunt() {
		return expirgrunt;
	}

	public void setExpirgrunt(Integer expirgrunt) {
		this.expirgrunt = expirgrunt;
	}

	public Integer getSilvermanscore() {
		return silvermanscore;
	}

	public void setSilvermanscore(Integer silvermanscore) {
		this.silvermanscore = silvermanscore;
	}

	public Timestamp getEntrydate() {
		return entrydate;
	}

	public void setEntrydate(Timestamp entrydate) {
		this.entrydate = entrydate;
	}
	
}
