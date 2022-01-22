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
@Table(name = "fitting_result")
@NamedQuery(name = "FittingResult.findAll", query = "SELECT b FROM FittingResult b")
public class FittingResult implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long fitting_result_id;
	
	private Timestamp creationtime;

	private Timestamp modificationtime;

	@Column(name = "kvalue")
	private double kvalue;                   // kvalue for gamma distribution

	@Column(name = "thetaValue")
	private double thetaValue;               // thetaValue for gamma distribution

	@Column(name = "alphaValue")
	private double alphaValue;               // alphaValue for weibull distribution
	
	@Column(name = "betaValue")
	private double betaValue;               // betaValue for weibull distribution
	
	@Column(name = "mean_fit")
	private double meanFit;                   // best estimate mean (mu) from non-linear regression
	
	@Column(name= "standard_deviation_fit")
    private double standardDeviationFit;      // best estimate standard deviation (sigma) from non-linear regression
	
	@Column(name= "scale_constant_fit")
    private double scaleConstantFit;          // best estimate scale constant (Ao) from non-linear regression
	    
	@Column(name= "ss")
	private double ss;
		
	@Column(name = "regression_type")
	private String regressionType;

	@Column(name = "signal_type")
	private String signalType;
	
	@Column(name= "degree_freedom")
	private double degreeFreedom;

	@Column(name = "recommended_status")
	private String recommendedStatus;	

	
	
	public double getKvalue() {
		return kvalue;
	}

	public void setKvalue(double kvalue) {
		this.kvalue = kvalue;
	}

	public double getThetaValue() {
		return thetaValue;
	}

	public void setThetaValue(double thetaValue) {
		this.thetaValue = thetaValue;
	}

	public double getAlphaValue() {
		return alphaValue;
	}

	public void setAlphaValue(double alphaValue) {
		this.alphaValue = alphaValue;
	}

	public double getBetaValue() {
		return betaValue;
	}

	public void setBetaValue(double betaValue) {
		this.betaValue = betaValue;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getRecommendedStatus() {
		return recommendedStatus;
	}

	public void setRecommendedStatus(String recommendedStatus) {
		this.recommendedStatus = recommendedStatus;
	}

	public Long getFitting_result_id() {
		return fitting_result_id;
	}

	public void setFitting_result_id(Long fitting_result_id) {
		this.fitting_result_id = fitting_result_id;
	}

	public double getMeanFit() {
		return meanFit;
	}

	public void setMeanFit(double meanFit) {
		this.meanFit = meanFit;
	}

	public double getStandardDeviationFit() {
		return standardDeviationFit;
	}

	public void setStandardDeviationFit(double standardDeviationFit) {
		this.standardDeviationFit = standardDeviationFit;
	}

	public double getScaleConstantFit() {
		return scaleConstantFit;
	}

	public void setScaleConstantFit(double scaleConstantFit) {
		this.scaleConstantFit = scaleConstantFit;
	}



	public double getSs() {
		return ss;
	}

	public void setSs(double ss) {
		this.ss = ss;
	}

	public String getRegressionType() {
		return regressionType;
	}

	public void setRegressionType(String regressionType) {
		this.regressionType = regressionType;
	}

	public String getSignalType() {
		return signalType;
	}

	public void setSignalType(String signalType) {
		this.signalType = signalType;
	}

	public double getDegreeFreedom() {
		return degreeFreedom;
	}

	public void setDegreeFreedom(double degreeFreedom) {
		this.degreeFreedom = degreeFreedom;
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
