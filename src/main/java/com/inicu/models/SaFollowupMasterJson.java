package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.SaFollowup;

public class SaFollowupMasterJson {

	SaFollowup followup;
	
	List<SaFollowup> listFollowup;
	
	ResponseMessageObject response;
	
	String userId = "";

	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public SaFollowup getFollowup() {
		return followup;
	}

	public void setFollowup(SaFollowup followup) {
		this.followup = followup;
	}

	public List<SaFollowup> getListFollowup() {
		return listFollowup;
	}

	public void setListFollowup(List<SaFollowup> listFollowup) {
		this.listFollowup = listFollowup;
	}

	public ResponseMessageObject getResponse() {
		return response;
	}

	public void setResponse(ResponseMessageObject response) {
		this.response = response;
	}
	
	
}
