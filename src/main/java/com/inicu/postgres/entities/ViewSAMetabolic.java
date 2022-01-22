package com.inicu.postgres.entities;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="vw_sa_metabolic")
public class ViewSAMetabolic {
	
	@Id
	@Column(name="sametabolicid", columnDefinition="int8")
	private BigInteger id;
	
	@Column(name="uhid")
	private String uhid;
	
	@Column(name="creationtime")
	private Timestamp creationtime;
	
	@Column(name="hypoglycemia", columnDefinition="bool")
	private Boolean hypoglycemia;
	
	@Column(name="minimumbs")
	private String minimumBS;
	
	@Column(name="dayhypoglycemia")
	private String dayHypoglycemia;
	
	@Column(name="durationhypoglycemia")
	private String duration;
	
	@Column(name="maxgdr")
	private String maxGDR;
	
	@Column(name="treatmentUsed")
	private String treatmentUsed;
	
	@Column(name="comments")
	private String comments;
	
	private String maximumbs;
	
	private String insulinlevel;
	
	@Column(columnDefinition="bool")
	private Boolean tms;
	
	@Column(columnDefinition="bool")
	private Boolean gcms;
	
	private String symptoms;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getUhid() {
		return uhid;
	}

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	
	public Boolean getHypoglycemia() {
		return hypoglycemia;
	}

	public void setHypoglycemia(Boolean hypoglycemia) {
		this.hypoglycemia = hypoglycemia;
	}

	public String getMinimumBS() {
		return minimumBS;
	}

	public void setMinimumBS(String minimumBS) {
		this.minimumBS = minimumBS;
	}

	public String getDayHypoglycemia() {
		return dayHypoglycemia;
	}

	public void setDayHypoglycemia(String dayHypoglycemia) {
		this.dayHypoglycemia = dayHypoglycemia;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getMaxGDR() {
		return maxGDR;
	}

	public void setMaxGDR(String maxGDR) {
		this.maxGDR = maxGDR;
	}

	public String getTreatmentUsed() {
		return treatmentUsed;
	}

	public void setTreatmentUsed(String treatmentUsed) {
		this.treatmentUsed = treatmentUsed;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	public String getMaximumbs() {
		return maximumbs;
	}

	public void setMaximumbs(String maximumbs) {
		this.maximumbs = maximumbs;
	}

	public String getInsulinlevel() {
		return insulinlevel;
	}

	public void setInsulinlevel(String insulinlevel) {
		this.insulinlevel = insulinlevel;
	}

	
	public Boolean getTms() {
		return tms;
	}

	public void setTms(Boolean tms) {
		this.tms = tms;
	}

	public Boolean getGcms() {
		return gcms;
	}

	public void setGcms(Boolean gcms) {
		this.gcms = gcms;
	}

	
	public String getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(String symptoms) {
		this.symptoms = symptoms;
	}

	/*
	 * 
	 * modified for doctor notes/ assessment use case
	 * 
	 */
	@Override
	public String toString() {
		
		String notes = "Metabolic [";
		
//		if(hypoglycemia!=null){
//			notes = notes+", Hypoglycemia:"+hypoglycemia;
//		}
		
		if(minimumBS!=null){
			notes = notes+", minimum BS:"+minimumBS;
		}
//		
//		if(dayHypoglycemia!=null){
//			notes = notes+", Day Hypoglycemia:"+dayHypoglycemia;
//		}
//		
//		if(duration!=null){
//			notes = notes+", Duration of Hypoglycemia:"+duration;
//		}
		
		if(maxGDR!=null){
			notes = notes+", Max. GDR:"+maxGDR;
		}
		
//		if(treatmentUsed!=null){
//			notes = notes+", Treatment Used:"+treatmentUsed;
//		}
//		
//		if(comments!=null){
//			notes = notes+", Comments:"+comments;
//		}
//		
//		if(maximumbs!=null){
//			notes = notes+", maximum BS:"+maximumbs;
//		}
//		
//		if(insulinlevel!=null){
//			notes = notes+", insulin level:"+insulinlevel;
//		}
//		
//		if(tms!=null){
//			notes = notes+", tms:"+tms;
//		}
//		
//		if(gcms!=null){
//			notes = notes+", notes:"+gcms;
//		}
		notes = notes+" ]";
		return notes;
//		return "ViewSAMetabolic [uhid=" + uhid + ", hypoglycemia="
//				+ hypoglycemia + ", minimumBS=" + minimumBS
//				+ ", dayHypoglycemia=" + dayHypoglycemia + ", duration="
//				+ duration + ", maxGDR=" + maxGDR + ", treatmentUsed="
//				+ treatmentUsed + ", comments=" + comments + "]";
	}

	
}
