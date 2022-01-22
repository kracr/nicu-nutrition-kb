package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.SaFeedgrowth;


public class FeedingGrowthMasterJSON {
	
	SaFeedgrowth feedGrowth;
	
	ResponseMessageObject response;
	
	List<SaFeedgrowth> listFeedGrowth;
	
	String userId = "";
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public SaFeedgrowth getFeedGrowth() {
		return feedGrowth;
	}

	public void setFeedGrowth(SaFeedgrowth feedGrowth) {
		this.feedGrowth = feedGrowth;
	}

	public ResponseMessageObject getResponse() {
		return response;
	}

	public void setResponse(ResponseMessageObject response) {
		this.response = response;
	}

	public List<SaFeedgrowth> getListFeedGrowth() {
		return listFeedGrowth;
	}

	public void setListFeedGrowth(List<SaFeedgrowth> listFeedGrowth) {
		this.listFeedGrowth = listFeedGrowth;
	}
}
