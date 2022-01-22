package com.inicu.models;

import com.inicu.postgres.entities.*;

import java.util.ArrayList;
import java.util.List;

public class TpnFeedPojo {

	TpnFeedCurrentObj currentFeedInfo;
	List<BabyfeedDetail> babyFeedList;
	List<BabyPrescription> babyPrescriptionList;
	List<DoctorBloodProducts> babyBloodProductList;
	List<CentralLine> babyCentralLineList;
	BabyPrescription babyPrescEmptyObj;
	FeedCalculatorPOJO feedCalulator;
	List<FeedCalculatorPOJO> calculatorList;

	OralfeedDetail oralFeedEmptyObj;
	List<OralfeedDetail> oralFeedList;
	List<OralfeedDetail> pastOralFeedList;

	EnFeedDetail emptyEnFeedDetailObj;
	List<EnFeedDetail> enFeedDetailList;
	List<EnFeedDetail> pastEnFeedDetailList;
	BabyfeedDetail lastFeedInfo;
	String additionalPNStatus;
	
	List<RefEnAddtivesBrand> addtivesbrandList;

	List<KeyValueObj> logoImage;
	List<IncrementFeed> incrementFeed;

	private String anthroprometryWeight;
	private String anthroprometryLength;
	private String anthroprometryHeadCircumference;


	public List<KeyValueObj> getLogoImage() {
		return logoImage;
	}

	public void setLogoImage(List<KeyValueObj> logoImage) {
		this.logoImage = logoImage;
	}

	public TpnFeedPojo() {
		super();
		this.feedCalulator = new FeedCalculatorPOJO();
		this.oralFeedEmptyObj = new OralfeedDetail();
		this.oralFeedList = new ArrayList<OralfeedDetail>();
		this.pastOralFeedList = new ArrayList<OralfeedDetail>();
		this.emptyEnFeedDetailObj = new EnFeedDetail();
		this.enFeedDetailList = new ArrayList<EnFeedDetail>();
		this.calculatorList = new ArrayList<FeedCalculatorPOJO>();
		this.enFeedDetailList.add(this.emptyEnFeedDetailObj);
		this.anthroprometryWeight=null;
		this.anthroprometryHeadCircumference=null;
		this.anthroprometryLength=null;
	}

	public String getAnthroprometryWeight() {
		return anthroprometryWeight;
	}

	public void setAnthroprometryWeight(String anthroprometryWeight) {
		this.anthroprometryWeight = anthroprometryWeight;
	}

	public String getAnthroprometryLength() {
		return anthroprometryLength;
	}

	public void setAnthroprometryLength(String anthroprometryLength) {
		this.anthroprometryLength = anthroprometryLength;
	}

	public String getAnthroprometryHeadCircumference() {
		return anthroprometryHeadCircumference;
	}

	public void setAnthroprometryHeadCircumference(String anthroprometryHeadCircumference) {
		this.anthroprometryHeadCircumference = anthroprometryHeadCircumference;
	}

	public TpnFeedCurrentObj getCurrentFeedInfo() {
		return currentFeedInfo;
	}

	public void setCurrentFeedInfo(TpnFeedCurrentObj currentFeedInfo) {
		this.currentFeedInfo = currentFeedInfo;
	}

	public List<BabyfeedDetail> getBabyFeedList() {
		return babyFeedList;
	}

	public void setBabyFeedList(List<BabyfeedDetail> babyFeedList) {
		this.babyFeedList = babyFeedList;
	}

	public List<BabyPrescription> getBabyPrescriptionList() {
		return babyPrescriptionList;
	}

	public void setBabyPrescriptionList(List<BabyPrescription> babyPrescriptionList) {
		this.babyPrescriptionList = babyPrescriptionList;
	}

	public List<DoctorBloodProducts> getBabyBloodProductList() {
		return babyBloodProductList;
	}

	public void setBabyBloodProductList(List<DoctorBloodProducts> babyBloodProductList) {
		this.babyBloodProductList = babyBloodProductList;
	}

	public BabyPrescription getBabyPrescEmptyObj() {
		return babyPrescEmptyObj;
	}

	public void setBabyPrescEmptyObj(BabyPrescription babyPrescEmptyObj) {
		this.babyPrescEmptyObj = babyPrescEmptyObj;
	}

	public FeedCalculatorPOJO getFeedCalulator() {
		return feedCalulator;
	}

	public void setFeedCalulator(FeedCalculatorPOJO feedCalulator) {
		this.feedCalulator = feedCalulator;
	}

	public OralfeedDetail getOralFeedEmptyObj() {
		return oralFeedEmptyObj;
	}

	public List<OralfeedDetail> getOralFeedList() {
		return oralFeedList;
	}

	public void setOralFeedEmptyObj(OralfeedDetail oralFeedEmptyObj) {
		this.oralFeedEmptyObj = oralFeedEmptyObj;
	}

	public void setOralFeedList(List<OralfeedDetail> oralFeedList) {
		this.oralFeedList = oralFeedList;
	}

	public List<OralfeedDetail> getPastOralFeedList() {
		return pastOralFeedList;
	}

	public void setPastOralFeedList(List<OralfeedDetail> pastOralFeedList) {
		this.pastOralFeedList = pastOralFeedList;
	}

	public EnFeedDetail getEmptyEnFeedDetailObj() {
		return emptyEnFeedDetailObj;
	}

	public void setEmptyEnFeedDetailObj(EnFeedDetail emptyEnFeedDetailObj) {
		this.emptyEnFeedDetailObj = emptyEnFeedDetailObj;
	}

	public List<EnFeedDetail> getEnFeedDetailList() {
		return enFeedDetailList;
	}

	public void setEnFeedDetailList(List<EnFeedDetail> enFeedDetailList) {
		this.enFeedDetailList = enFeedDetailList;
	}

	public List<EnFeedDetail> getPastEnFeedDetailList() {
		return pastEnFeedDetailList;
	}

	public void setPastEnFeedDetailList(List<EnFeedDetail> pastEnFeedDetailList) {
		this.pastEnFeedDetailList = pastEnFeedDetailList;
	}

	public BabyfeedDetail getLastFeedInfo() {
		return lastFeedInfo;
	}

	public void setLastFeedInfo(BabyfeedDetail lastFeedInfo) {
		this.lastFeedInfo = lastFeedInfo;
	}

	public List<CentralLine> getBabyCentralLineList() {
		return babyCentralLineList;
	}

	public void setBabyCentralLineList(List<CentralLine> babyCentralLineList) {
		this.babyCentralLineList = babyCentralLineList;
	}

	public List<RefEnAddtivesBrand> getAddtivesbrandList() {
		return addtivesbrandList;
	}

	public void setAddtivesbrandList(List<RefEnAddtivesBrand> addtivesbrandList) {
		this.addtivesbrandList = addtivesbrandList;
	}

	public List<FeedCalculatorPOJO> getCalculatorList() {
		return calculatorList;
	}

	public void setCalculatorList(List<FeedCalculatorPOJO> calculatorList) {
		this.calculatorList = calculatorList;
	}

	public String getAdditionalPNStatus() {
		return additionalPNStatus;
	}

	public void setAdditionalPNStatus(String additionalPNStatus) {
		this.additionalPNStatus = additionalPNStatus;
	}

	public List<IncrementFeed> getIncrementFeed() {
		return incrementFeed;
	}

	public void setIncrementFeed(List<IncrementFeed> incrementFeed) {
		this.incrementFeed = incrementFeed;
	}

}
