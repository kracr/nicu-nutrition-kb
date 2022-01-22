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
 * The persistent class for the antenatal_steroid_detail database table.
 * 
 */
@Entity
@Table(name = "antenatal_steroid_detail")
@NamedQuery(name = "AntenatalSteroidDetail.findAll", query = "SELECT a FROM AntenatalSteroidDetail a")
public class AntenatalSteroidDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "antenatal_steroid_id")
	private Long antenatalSteroidId;

	private Timestamp creationtime;

	// first dose date and time
	private Timestamp givendate;

	private String giventimehours;

	private String giventimeminutes;

	private Integer giventimeseconds;

	private Timestamp modificationtime;

	private Integer numberofdose;

	private String steroidname;

	private String uhid;

	@Column(columnDefinition = "bool")
	private Boolean isBirthLastDoseTextGT24;

	@Column(name = "given_meridian")
	private String givenMeridian;

	// Trying to add the episode id to this table
	@Column(name="episodeid")
	private String episodeid;

	// second dose date and time
	@Column(name="second_dose_entrytime")
	private Timestamp secondDoseEntrytime;

	// third dose date and time
	@Column(name="third_dose_entrytime")
	private Timestamp thirdDoseEntrytime;

	// fourth dose date and time
	@Column(name="fourth_dose_entrytime")
	private Timestamp fourthDoseEntrytime;

	public AntenatalSteroidDetail() {
	}

	public Long getAntenatalSteroidId() {
		return this.antenatalSteroidId;
	}

	public void setAntenatalSteroidId(Long antenatalSteroidId) {
		this.antenatalSteroidId = antenatalSteroidId;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public Timestamp getGivendate() {
		return this.givendate;
	}

	public void setGivendate(Timestamp givendate) {
		this.givendate = givendate;
	}

	public String getGiventimehours() {
		return this.giventimehours;
	}

	public void setGiventimehours(String giventimehours) {
		this.giventimehours = giventimehours;
	}

	public String getGiventimeminutes() {
		return this.giventimeminutes;
	}

	public void setGiventimeminutes(String giventimeminutes) {
		this.giventimeminutes = giventimeminutes;
	}

	public Integer getGiventimeseconds() {
		return this.giventimeseconds;
	}

	public void setGiventimeseconds(Integer giventimeseconds) {
		this.giventimeseconds = giventimeseconds;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public Integer getNumberofdose() {
		return this.numberofdose;
	}

	public void setNumberofdose(Integer numberofdose) {
		this.numberofdose = numberofdose;
	}

	public String getSteroidname() {
		return this.steroidname;
	}

	public void setSteroidname(String steroidname) {
		this.steroidname = steroidname;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public Boolean getIsBirthLastDoseTextGT24() {
		return isBirthLastDoseTextGT24;
	}

	public void setIsBirthLastDoseTextGT24(Boolean isBirthLastDoseTextGT24) {
		this.isBirthLastDoseTextGT24 = isBirthLastDoseTextGT24;
	}

	public String getGivenMeridian() {
		return givenMeridian;
	}

	public void setGivenMeridian(String givenMeridian) {
		this.givenMeridian = givenMeridian;
	}

	public String getEpisodeid() {
		return episodeid;
	}

	public void setEpisodeid(String episodeid) {
		this.episodeid = episodeid;
	}

	public Timestamp getSecondDoseEntrytime() {
		return secondDoseEntrytime;
	}

	public void setSecondDoseEntrytime(Timestamp secondDoseEntrytime) {
		this.secondDoseEntrytime = secondDoseEntrytime;
	}

	public Timestamp getThirdDoseEntrytime() {
		return thirdDoseEntrytime;
	}

	public void setThirdDoseEntrytime(Timestamp thirdDoseEntrytime) {
		this.thirdDoseEntrytime = thirdDoseEntrytime;
	}

	public Timestamp getFourthDoseEntrytime() {
		return fourthDoseEntrytime;
	}

	public void setFourthDoseEntrytime(Timestamp fourthDoseEntrytime) {
		this.fourthDoseEntrytime = fourthDoseEntrytime;
	}
}