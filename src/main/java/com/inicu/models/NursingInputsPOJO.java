package com.inicu.models;

import java.util.ArrayList;
import java.util.List;

import com.inicu.postgres.entities.BabyfeedDetail;
import com.inicu.postgres.entities.EnFeedDetail;
import com.inicu.postgres.entities.IncrementFeed;
import com.inicu.postgres.entities.NursingIntakeOutput;
import com.inicu.postgres.entities.OralfeedDetail;
import com.inicu.postgres.entities.RefEnAddtivesBrand;
import com.inicu.postgres.entities.RefMasterfeedtype;

public class NursingInputsPOJO {

	List<String> hours;

	List<String> minutes;

	String entryDate = "";

	String feedMethodStr = "";

	BabyfeedDetail babyFeedObj;
	
	List<BabyfeedDetail> babyFeedList;

	List<RefMasterfeedtype> refFeedTypeList;

	List<OralfeedDetail> oralFeedList;

	NursingIntakeOutput currentNursingObj;

	List<NursingIntakeOutput> pastNursingIntakeList;
	
	List<NursingIntakeOutput> currentNursingIntakeList;

	List<EnFeedDetail> enFeedList;
	
	List<RefEnAddtivesBrand> addtivesbrandList;
	
	private String minAbdomenGirth;

	String prevabdomenGirth;

	List<IncrementFeed> incrementFeed;

	public NursingInputsPOJO() {
		super();
		this.oralFeedList = new ArrayList<OralfeedDetail>();
		this.currentNursingObj = new NursingIntakeOutput();
		this.pastNursingIntakeList = new ArrayList<NursingIntakeOutput>();
	}

	public String getPrevabdomenGirth() {
		return prevabdomenGirth;
	}

	public void setPrevabdomenGirth(String prevabdomenGirth) {
		this.prevabdomenGirth = prevabdomenGirth;
	}
	public List<String> getHours() {
		return hours;
	}

	public void setHours(List<String> hours) {
		this.hours = hours;
	}

	public List<String> getMinutes() {
		return minutes;
	}

	public void setMinutes(List<String> minutes) {
		this.minutes = minutes;
	}

	public String getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}

	public String getFeedMethodStr() {
		return feedMethodStr;
	}

	public void setFeedMethodStr(String feedMethodStr) {
		this.feedMethodStr = feedMethodStr;
	}

	public BabyfeedDetail getBabyFeedObj() {
		return babyFeedObj;
	}

	public void setBabyFeedObj(BabyfeedDetail babyFeedObj) {
		this.babyFeedObj = babyFeedObj;
	}

	public List<RefMasterfeedtype> getRefFeedTypeList() {
		return refFeedTypeList;
	}

	public void setRefFeedTypeList(List<RefMasterfeedtype> refFeedTypeList) {
		this.refFeedTypeList = refFeedTypeList;
	}

	public List<OralfeedDetail> getOralFeedList() {
		return oralFeedList;
	}

	public void setOralFeedList(List<OralfeedDetail> oralFeedList) {
		this.oralFeedList = oralFeedList;
	}

	public NursingIntakeOutput getCurrentNursingObj() {
		return currentNursingObj;
	}

	public void setCurrentNursingObj(NursingIntakeOutput currentNursingObj) {
		this.currentNursingObj = currentNursingObj;
	}

	public List<NursingIntakeOutput> getPastNursingIntakeList() {
		return pastNursingIntakeList;
	}

	public void setPastNursingIntakeList(List<NursingIntakeOutput> pastNursingIntakeList) {
		this.pastNursingIntakeList = pastNursingIntakeList;
	}

	public List<EnFeedDetail> getEnFeedList() {
		return enFeedList;
	}

	public void setEnFeedList(List<EnFeedDetail> enFeedList) {
		this.enFeedList = enFeedList;
	}

	public List<RefEnAddtivesBrand> getAddtivesbrandList() {
		return addtivesbrandList;
	}

	public void setAddtivesbrandList(List<RefEnAddtivesBrand> addtivesbrandList) {
		this.addtivesbrandList = addtivesbrandList;
	}

	public List<BabyfeedDetail> getBabyFeedList() {
		return babyFeedList;
	}

	public void setBabyFeedList(List<BabyfeedDetail> babyFeedList) {
		this.babyFeedList = babyFeedList;
	}

	public List<NursingIntakeOutput> getCurrentNursingIntakeList() {
		return currentNursingIntakeList;
	}

	public void setCurrentNursingIntakeList(List<NursingIntakeOutput> currentNursingIntakeList) {
		this.currentNursingIntakeList = currentNursingIntakeList;
	}
	
	public String getMinAbdomenGirth() {
		return minAbdomenGirth;
	}

	public void setMinAbdomenGirth(String minAbdomenGirth) {
		this.minAbdomenGirth = minAbdomenGirth;
	}

	public List<IncrementFeed> getIncrementFeed() {
		return incrementFeed;
	}

	public void setIncrementFeed(List<IncrementFeed> incrementFeed) {
		this.incrementFeed = incrementFeed;
	}
	
	

}
