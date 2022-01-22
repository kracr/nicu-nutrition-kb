package com.inicu.analytics.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.inicu.analytics.RegressionConstants;

import flanagan.analysis.Regression;
import flanagan.math.Fmath;


@Entity
@Table(name = "high_morbidity_score")
@NamedQuery(name = "HighMorbidityScore.findAll", query = "SELECT b FROM HighMorbidityScore b")
public class HighMorbidityScore implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long morbidityid;
	
	private Timestamp creationtime;

	private Timestamp modificationtime;

	@Column(name = "uhid")
	private String uhid;
	
	@Column(name = "basesignalmeanhr")
	private double basesignalmeanhr;         // Base Mean HR

	@Column(name = "basesignalvarhr")
	private double basesignalvarhr;     	// Base Variance HR

	@Column(name = "residualsignalvarhr")
	private double residualsignalvarhr; 	// Residual Variance HR
		
	@Column(name = "basesignalmeanrr")
	private double basesignalmeanrr;         // Base Mean RR

	@Column(name = "basesignalvarrr")
	private double basesignalvarrr;     	// Base Variance RR
	
	@Column(name= "residualsignalvarrr")
    private double residualsignalvarrr; 	// Residual Variance RR
		
	@Column(name= "physiscore")				// physiscore 
	private double physiscore;
	
	@Column(name= "timespentunder85spo2")	// timespentunder85spo2
	private double timespentunder85spo2;
	
	@Column(name= "meanspo2")				// mean SpO2
	private double meanspo2;

	@Column(name = "recommended_status")
	private String recommendedStatus;
	
	public String getRecommendedStatus() {
		return recommendedStatus;
	}

	public void setRecommendedStatus(String recommendedStatus) {
		this.recommendedStatus = recommendedStatus;
	}

	public Long getMorbidityid() {
		return morbidityid;
	}

	public void setMorbidityid(Long morbidityid) {
		this.morbidityid = morbidityid;
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

	public double getBasesignalmeanhr() {
		return basesignalmeanhr;
	}

	public void setBasesignalmeanhr(double basesignalmeanhr) {
		this.basesignalmeanhr = basesignalmeanhr;
	}

	public double getBasesignalvarhr() {
		return basesignalvarhr;
	}

	public void setBasesignalvarhr(double basesignalvarhr) {
		this.basesignalvarhr = basesignalvarhr;
	}

	public double getResidualsignalvarhr() {
		return residualsignalvarhr;
	}

	public void setResidualsignalvarhr(double residualsignalvarhr) {
		this.residualsignalvarhr = residualsignalvarhr;
	}

	public double getBasesignalmeanrr() {
		return basesignalmeanrr;
	}

	public void setBasesignalmeanrr(double basesignalmeanrr) {
		this.basesignalmeanrr = basesignalmeanrr;
	}

	public double getBasesignalvarrr() {
		return basesignalvarrr;
	}

	public void setBasesignalvarrr(double basesignalvarrr) {
		this.basesignalvarrr = basesignalvarrr;
	}

	public double getResidualsignalvarrr() {
		return residualsignalvarrr;
	}

	public void setResidualsignalvarrr(double residualsignalvarrr) {
		this.residualsignalvarrr = residualsignalvarrr;
	}

	public double getPhysiscore() {
		return physiscore;
	}

	public void setPhysiscore(double physiscore) {
		this.physiscore = physiscore;
	}

	public double getTimespentunder85spo2() {
		return timespentunder85spo2;
	}

	public void setTimespentunder85spo2(double timespentunder85spo2) {
		this.timespentunder85spo2 = timespentunder85spo2;
	}

	public double getMeanspo2() {
		return meanspo2;
	}

	public void setMeanspo2(double meanspo2) {
		this.meanspo2 = meanspo2;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
