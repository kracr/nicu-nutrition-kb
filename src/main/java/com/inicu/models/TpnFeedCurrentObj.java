package com.inicu.models;

import java.sql.Timestamp;

import com.inicu.postgres.entities.BabyPrescription;
import com.inicu.postgres.entities.BabyfeedDetail;
import com.inicu.postgres.entities.BloodProduct;

public class TpnFeedCurrentObj {

	BabyfeedDetail babyFeed;    
	BloodProduct babyBloodProduct;
	private String currentWeight;
	private Timestamp feedGivenDateTime;
	private String feedGivenHrs;
	private String feedGivenMinutes;
	private String feedGivenMeridian;
	private Boolean isNewEntry;
	
	public TpnFeedCurrentObj() {
		
		this.babyFeed = new BabyfeedDetail();
		this.babyBloodProduct = new BloodProduct();
		
	}
	public BabyfeedDetail getBabyFeed() {
		return babyFeed;
	}
	public void setBabyFeed(BabyfeedDetail babyFeed) {
		this.babyFeed = babyFeed;
	}
	public BloodProduct getBabyBloodProduct() {
		return babyBloodProduct;
	}
	public void setBabyBloodProduct(BloodProduct babyBloodProduct) {
		this.babyBloodProduct = babyBloodProduct;
	}
	public String getCurrentWeight() {
		return currentWeight;
	}
	public void setCurrentWeight(String currentWeight) {
		this.currentWeight = currentWeight;
	}
	public Timestamp getFeedGivenDateTime() {
		return feedGivenDateTime;
	}
	public String getFeedGivenHrs() {
		return feedGivenHrs;
	}
	public String getFeedGivenMinutes() {
		return feedGivenMinutes;
	}
	
	public void setFeedGivenDateTime(Timestamp feedGivenDateTime) {
		this.feedGivenDateTime = feedGivenDateTime;
	}
	public void setFeedGivenHrs(String feedGivenHrs) {
		this.feedGivenHrs = feedGivenHrs;
	}
	public void setFeedGivenMinutes(String feedGivenMinutes) {
		this.feedGivenMinutes = feedGivenMinutes;
	}
	
	public String getFeedGivenMeridian() {
		return feedGivenMeridian;
	}
	public void setFeedGivenMeridian(String feedGivenMeridian) {
		this.feedGivenMeridian = feedGivenMeridian;
	}
	public Boolean getIsNewEntry() {
		return isNewEntry;
	}
	public void setIsNewEntry(Boolean isNewEntry) {
		this.isNewEntry = isNewEntry;
	}
	
	
	
	
}
