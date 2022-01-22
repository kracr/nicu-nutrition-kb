package com.inicu.models;

import com.inicu.postgres.entities.VwNursingnote;

/**
 * 
 * @author iNICU 
 *
 */
public class NursingNotesFeedsObj {

	private String time;
	private VwNursingnote feedDetails;  

	public NursingNotesFeedsObj() {
		super();
		this.time = "";
		this.feedDetails = new VwNursingnote();
	}
	public String getTime() {
		return time;
	}
	public VwNursingnote getFeedDetails() {
		return feedDetails;
	}
	public void setFeedDetails(VwNursingnote feedDetails) {
		this.feedDetails = feedDetails;
	}
	public void setTime(String time) {
		this.time = time;
	}


}
