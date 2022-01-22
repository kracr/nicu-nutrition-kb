package com.inicu.postgres.entities;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//@Entity
@Table(name="vw_sa_jaundice")
public class ViewSAJaundice {

	@Id
	@Column(name="sajaundiceid", columnDefinition="int8")
	private BigInteger jaundiceId;
	
	@Column(name="uhid")
	private String uhidNo;
	
	@Column(name="creationtime")
	private Timestamp creationtime;
	
	@Column(name="ageofonset")
	private Integer ageOfOnset;
	
	@Column(name="durationofjaundice")
	private Integer durationJaundice;
	
	@Column(name="causeofjaundice")
	private String causeOfJaundice;
	
	@Column(name="dct", columnDefinition="bool")
	private Boolean dct;
	
	@Column(name="exchangetrans", columnDefinition="bool")
	private Boolean exchangeTrans;
	
	@Column(name="noofexchange")
	private String noOfExchange;
	
	@Column(name="phototherapy", columnDefinition="bool")
	private Boolean photoTherapy;
	
	@Column(name="durphoto")
	private String durPhoto;
	
	@Column(name="hemolysis", columnDefinition="bool")
	private Boolean hemolysis;
	
	@Column(name="recticct")
	private String recticct;
	
	@Column(name="ivig", columnDefinition="bool")
	private Boolean ivig;
	
	@Column(name="maxbili")
	private String maxBill;
	
	private String comment;
	
	
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public BigInteger getJaundiceId() {
		return jaundiceId;
	}

	public void setJaundiceId(BigInteger jaundiceId) {
		this.jaundiceId = jaundiceId;
	}

	public String getUhidNo() {
		return uhidNo;
	}

	public void setUhidNo(String uhidNo) {
		this.uhidNo = uhidNo;
	}

	

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public Integer getAgeOfOnset() {
		return ageOfOnset;
	}

	public void setAgeOfOnset(Integer ageOfOnset) {
		this.ageOfOnset = ageOfOnset;
	}

	public String getCauseOfJaundice() {
		return causeOfJaundice;
	}

	public void setCauseOfJaundice(String causeOfJaundice) {
		this.causeOfJaundice = causeOfJaundice;
	}

	public Boolean getDct() {
		return dct;
	}

	public void setDct(Boolean dct) {
		this.dct = dct;
	}

	public Boolean getExchangeTrans() {
		return exchangeTrans;
	}

	public void setExchangeTrans(Boolean exchangeTrans) {
		this.exchangeTrans = exchangeTrans;
	}

	public String getNoOfExchange() {
		return noOfExchange;
	}

	public void setNoOfExchange(String noOfExchange) {
		this.noOfExchange = noOfExchange;
	}

	public Boolean getPhotoTherapy() {
		return photoTherapy;
	}

	public void setPhotoTherapy(Boolean photoTherapy) {
		this.photoTherapy = photoTherapy;
	}

	public String getDurPhoto() {
		return durPhoto;
	}

	public void setDurPhoto(String durPhoto) {
		this.durPhoto = durPhoto;
	}

	public Boolean getHemolysis() {
		return hemolysis;
	}

	public void setHemolysis(Boolean hemolysis) {
		this.hemolysis = hemolysis;
	}

	public String getRecticct() {
		return recticct;
	}

	public void setRecticct(String recticct) {
		this.recticct = recticct;
	}

	public Boolean getIvig() {
		return ivig;
	}

	public void setIvig(Boolean ivig) {
		this.ivig = ivig;
	}

	public String getMaxBill() {
		return maxBill;
	}

	public void setMaxBill(String maxBill) {
		this.maxBill = maxBill;
	}

	public Integer getDurationJaundice() {
		return durationJaundice;
	}

	public void setDurationJaundice(Integer durationJaundice) {
		this.durationJaundice = durationJaundice;
	}

	/**
	 * method modified 
	 * for Doctor notes/ assessment sheet use case.
	 * 
	 */
	@Override
	public String toString() {
		
		String notes = "Jaundice [";
//		if(ageOfOnset!=null){
//			notes = notes+", age of onset:"+ageOfOnset;
//		}
//		
//		if(durationJaundice!=null){
//			notes = notes+", jaundice duration:"+durationJaundice;
//		}
		
		if(causeOfJaundice!=null){
			notes = notes+", cause of jaundice:"+causeOfJaundice;
		}
		
//		if(dct!=null){
//			notes = notes+", dct:"+dct;
//		}
//		
//		if(exchangeTrans!=null){
//			notes = notes+", exchange Trans:"+exchangeTrans;
//		}
//		
//		if(noOfExchange!=null){
//			notes = notes+", No. of Exchange:"+noOfExchange;
//		}
//		
//		if(photoTherapy!=null){
//			notes = notes+", photoTherapy:"+photoTherapy;
//		}
//		
//		if(durPhoto!=null){
//			notes = notes+", Dur Photo:"+durPhoto;
//		}
//		
//		if(hemolysis!=null){
//			notes = notes+", Hemolysis:"+hemolysis;
//		}
//		
//		if(recticct!=null){
//			notes = notes+", Rectic CT:"+recticct;
//		}
//		
//		if(ivig!=null){
//			notes = notes+", ivig:"+ivig;
//		}
//		
//		if(maxBill!=null){
//			notes = notes+", Max Bill:"+maxBill;
//		}
		
		notes = notes+" ]";
		return notes;
//		return "ViewSAJaundice [jaundiceId=" + jaundiceId + ", uhidNo="
//				+ uhidNo + ", creationdate=" + creationdate + ", ageOfOnset="
//				+ ageOfOnset + ", durationJaundice=" + durationJaundice
//				+ ", causeOfJaundice=" + causeOfJaundice + ", dct=" + dct
//				+ ", exchangeTrans=" + exchangeTrans + ", noOfExchange="
//				+ noOfExchange + ", photoTherapy=" + photoTherapy
//				+ ", durPhoto=" + durPhoto + ", hemolysis=" + hemolysis
//				+ ", recticct=" + recticct + ", ivig=" + ivig + ", maxBill="
//				+ maxBill + "]";
	}
	
}
