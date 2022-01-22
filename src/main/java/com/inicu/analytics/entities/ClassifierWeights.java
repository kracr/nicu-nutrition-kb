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
@Table(name = "classifier_weights")
@NamedQuery(name = "ClassifierWeights.findAll", query = "SELECT b FROM ClassifierWeights b")
public class ClassifierWeights implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long classifierid;
	
	private Timestamp creationtime;

	private Timestamp modificationtime;

	@Column(name = "weightBaseMeanHR")
	private double weightBaseMeanHR;         // weight of Base Mean HR

	@Column(name = "weightBaseVarianceHR")
	private double weightBaseVarianceHR;     // weight of Base Variance HR

	@Column(name = "weightResidualVarianceHR")
	private double weightResidualVarianceHR; // weight of Residual Variance HR
		
	@Column(name = "weightBaseMeanRR")
	private double weightBaseMeanRR;         // weight of Base Mean RR

	@Column(name = "weightBaseVarianceRR")
	private double weightBaseVarianceRR;     // weight of Base Variance RR
	
	@Column(name= "weightResidualVarianceRR")
    private double weightResidualVarianceRR; // weight of Residual Variance RR
	
	@Column(name= "weightMeanOxygenation")
    private double weightMeanOxygenation;          // weight of mean oxygenation 
	    
	@Column(name= "weightTimeSpentUnder85SpO2")		// weight of time spent under 85% SpO2
	private double weightTimeSpentUnder85SpO2;
	
	@Column(name= "interceptValue")		// weight of time spent under 85% SpO2
	private double interceptValue;
	

	public double getInterceptValue() {
		return interceptValue;
	}

	public void setInterceptValue(double interceptValue) {
		this.interceptValue = interceptValue;
	}

	public Long getClassifierid() {
		return classifierid;
	}

	public void setClassifierid(Long classifierid) {
		this.classifierid = classifierid;
	}

	public double getWeightBaseMeanHR() {
		return weightBaseMeanHR;
	}

	public void setWeightBaseMeanHR(double weightBaseMeanHR) {
		this.weightBaseMeanHR = weightBaseMeanHR;
	}

	public double getWeightBaseVarianceHR() {
		return weightBaseVarianceHR;
	}

	public void setWeightBaseVarianceHR(double weightBaseVarianceHR) {
		this.weightBaseVarianceHR = weightBaseVarianceHR;
	}

	public double getWeightResidualVarianceHR() {
		return weightResidualVarianceHR;
	}

	public void setWeightResidualVarianceHR(double weightResidualVarianceHR) {
		this.weightResidualVarianceHR = weightResidualVarianceHR;
	}

	public double getWeightBaseMeanRR() {
		return weightBaseMeanRR;
	}

	public void setWeightBaseMeanRR(double weightBaseMeanRR) {
		this.weightBaseMeanRR = weightBaseMeanRR;
	}

	public double getWeightBaseVarianceRR() {
		return weightBaseVarianceRR;
	}

	public void setWeightBaseVarianceRR(double weightBaseVarianceRR) {
		this.weightBaseVarianceRR = weightBaseVarianceRR;
	}

	public double getWeightResidualVarianceRR() {
		return weightResidualVarianceRR;
	}

	public void setWeightResidualVarianceRR(double weightResidualVarianceRR) {
		this.weightResidualVarianceRR = weightResidualVarianceRR;
	}

	public double getWeightMeanOxygenation() {
		return weightMeanOxygenation;
	}

	public void setWeightMeanOxygenation(double weightMeanOxygenation) {
		this.weightMeanOxygenation = weightMeanOxygenation;
	}

	public double getWeightTimeSpentUnder85SpO2() {
		return weightTimeSpentUnder85SpO2;
	}

	public void setWeightTimeSpentUnder85SpO2(double weightTimeSpentUnder85SpO2) {
		this.weightTimeSpentUnder85SpO2 = weightTimeSpentUnder85SpO2;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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
	
}
