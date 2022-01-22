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
 * The persistent class for the score_ballard database table.
 * 
 */
@Entity
@Table(name = "score_ballard")
@NamedQuery(name = "ScoreBallard.findAll", query = "SELECT s FROM ScoreBallard s")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScoreBallard implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long ballardscoreid;

	@Column(name = "arm_recoi")
	private Integer armRecoi;

	private Integer breast;

	private Timestamp creationtime;

	@Column(name = "eye_ear")
	private Integer eyeEar;

	@Column(name = "genital_female")
	private Integer genitalFemale;

	@Column(name = "genital_male")
	private Integer genitalMale;

	@Column(name = "gestation_ballard")
	private Integer gestationBallard;

	private Integer healtoear;

	private Integer lanugo;

	private String maturity;

	private Timestamp modificationtime;

	@Column(name = "nero_score")
	private Integer neroScore;

	@Column(name = "physical_score")
	private Integer physicalScore;

	@Column(name = "plantar_surface")
	private Integer plantarSurface;

	@Column(name = "popliteal_angle")
	private Integer poplitealAngle;

	private Integer posture;

	@Column(name = "scarf_sign")
	private Integer scarfSign;

	private Integer skin;

	@Column(name = "square_window")
	private Integer squareWindow;

	@Column(name = "total_score")
	private Integer totalScore;

	private String uhid;

	public ScoreBallard() {
		super();
	}

	public Long getBallardscoreid() {
		return this.ballardscoreid;
	}

	public void setBallardscoreid(Long ballardscoreid) {
		this.ballardscoreid = ballardscoreid;
	}

	public Integer getArmRecoi() {
		return this.armRecoi;
	}

	public void setArmRecoi(Integer armRecoi) {
		this.armRecoi = armRecoi;
	}

	public Integer getBreast() {
		return this.breast;
	}

	public void setBreast(Integer breast) {
		this.breast = breast;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public Integer getEyeEar() {
		return this.eyeEar;
	}

	public void setEyeEar(Integer eyeEar) {
		this.eyeEar = eyeEar;
	}

	public Integer getGenitalFemale() {
		return this.genitalFemale;
	}

	public void setGenitalFemale(Integer genitalFemale) {
		this.genitalFemale = genitalFemale;
	}

	public Integer getGenitalMale() {
		return this.genitalMale;
	}

	public void setGenitalMale(Integer genitalMale) {
		this.genitalMale = genitalMale;
	}

	public Integer getGestationBallard() {
		return this.gestationBallard;
	}

	public void setGestationBallard(Integer gestationBallard) {
		this.gestationBallard = gestationBallard;
	}

	public Integer getHealtoear() {
		return this.healtoear;
	}

	public void setHealtoear(Integer healtoear) {
		this.healtoear = healtoear;
	}

	public Integer getLanugo() {
		return this.lanugo;
	}

	public void setLanugo(Integer lanugo) {
		this.lanugo = lanugo;
	}

	public String getMaturity() {
		return this.maturity;
	}

	public void setMaturity(String maturity) {
		this.maturity = maturity;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public Integer getNeroScore() {
		return this.neroScore;
	}

	public void setNeroScore(Integer neroScore) {
		this.neroScore = neroScore;
	}

	public Integer getPhysicalScore() {
		return this.physicalScore;
	}

	public void setPhysicalScore(Integer physicalScore) {
		this.physicalScore = physicalScore;
	}

	public Integer getPlantarSurface() {
		return this.plantarSurface;
	}

	public void setPlantarSurface(Integer plantarSurface) {
		this.plantarSurface = plantarSurface;
	}

	public Integer getPoplitealAngle() {
		return this.poplitealAngle;
	}

	public void setPoplitealAngle(Integer poplitealAngle) {
		this.poplitealAngle = poplitealAngle;
	}

	public Integer getPosture() {
		return this.posture;
	}

	public void setPosture(Integer posture) {
		this.posture = posture;
	}

	public Integer getScarfSign() {
		return this.scarfSign;
	}

	public void setScarfSign(Integer scarfSign) {
		this.scarfSign = scarfSign;
	}

	public Integer getSkin() {
		return this.skin;
	}

	public void setSkin(Integer skin) {
		this.skin = skin;
	}

	public Integer getSquareWindow() {
		return this.squareWindow;
	}

	public void setSquareWindow(Integer squareWindow) {
		this.squareWindow = squareWindow;
	}

	public Integer getTotalScore() {
		return this.totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

}