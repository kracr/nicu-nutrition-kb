package com.inicu.models;

import java.util.HashMap;
import java.util.List;

import com.inicu.postgres.entities.BabyPrescription;
import com.inicu.postgres.entities.RespSupport;
import com.inicu.postgres.entities.SaRespOther;

public class RespSysOtherPOJO {

	private SaRespOther currentOther;

	private RespSupport respSupport;

	private BabyPrescription babyPrescriptionEmptyObj;

	private List<BabyPrescription> currentBabyPrescriptionList;

	private List<SaRespOther> pastOtherList;

	private HashMap<String, RespSupport> pastRespSupportMap = new HashMap<String, RespSupport>();

	public RespSysOtherPOJO() {
		super();
		currentOther = new SaRespOther();
		currentOther.setAgeinhours(true);
		respSupport = new RespSupport();
		babyPrescriptionEmptyObj = new BabyPrescription();
	}

	public SaRespOther getCurrentOther() {
		return currentOther;
	}

	public void setCurrentOther(SaRespOther currentOther) {
		this.currentOther = currentOther;
	}

	public RespSupport getRespSupport() {
		return respSupport;
	}

	public void setRespSupport(RespSupport respSupport) {
		this.respSupport = respSupport;
	}

	public HashMap<String, RespSupport> getPastRespSupportMap() {
		return pastRespSupportMap;
	}

	public void setPastRespSupportMap(HashMap<String, RespSupport> pastRespSupportMap) {
		this.pastRespSupportMap = pastRespSupportMap;
	}

	public BabyPrescription getBabyPrescriptionEmptyObj() {
		return babyPrescriptionEmptyObj;
	}

	public void setBabyPrescriptionEmptyObj(BabyPrescription babyPrescriptionEmptyObj) {
		this.babyPrescriptionEmptyObj = babyPrescriptionEmptyObj;
	}

	public List<BabyPrescription> getCurrentBabyPrescriptionList() {
		return currentBabyPrescriptionList;
	}

	public void setCurrentBabyPrescriptionList(List<BabyPrescription> currentBabyPrescriptionList) {
		this.currentBabyPrescriptionList = currentBabyPrescriptionList;
	}

	public List<SaRespOther> getPastOtherList() {
		return pastOtherList;
	}

	public void setPastOtherList(List<SaRespOther> pastOtherList) {
		this.pastOtherList = pastOtherList;
	}

	@Override
	public String toString() {
		return "RespSysOtherPOJO [currentOther=" + currentOther + ", respSupport=" + respSupport
				+ ", babyPrescriptionEmptyObj=" + babyPrescriptionEmptyObj + ", currentBabyPrescriptionList="
				+ currentBabyPrescriptionList + ", pastOtherList=" + pastOtherList + ", pastRespSupportMap="
				+ pastRespSupportMap + "]";
	}

}
