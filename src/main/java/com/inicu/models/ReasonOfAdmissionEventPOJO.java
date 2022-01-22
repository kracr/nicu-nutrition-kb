package com.inicu.models;

import java.util.List;

public class ReasonOfAdmissionEventPOJO {

	String eventName;
	List<ReasonOfAdmissionEventCausePOJO> causeList;

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public List<ReasonOfAdmissionEventCausePOJO> getCauseList() {
		return causeList;
	}

	public void setCauseList(List<ReasonOfAdmissionEventCausePOJO> causeList) {
		this.causeList = causeList;
	}

	@Override
	public String toString() {
		return "ReasonOfAdmissionEventPOJO [eventName=" + eventName + ", causeList=" + causeList + "]";
	}

}
