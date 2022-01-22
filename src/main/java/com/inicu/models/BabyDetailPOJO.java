package com.inicu.models;

import java.util.List;

public class BabyDetailPOJO {
	
	List<BabyBasicDetail> babyList;
	
	ResponseMessageObject response ;
	
	public List<BabyBasicDetail> getBabyList() {
		return babyList;
	}
	public void setBabyList(List<BabyBasicDetail> babyList) {
		this.babyList = babyList;
	}
	public ResponseMessageObject getResponse() {
		return response;
	}
	public void setResponse(ResponseMessageObject response) {
		this.response = response;
	}
	
	
	

}
