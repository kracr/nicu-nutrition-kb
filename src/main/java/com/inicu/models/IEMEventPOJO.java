package com.inicu.models;

public class IEMEventPOJO {

	IEMCurrentEventPOJO currentEvent;
	
	IEMPastEventsPOJO pastEvents;
	
	public IEMEventPOJO() {
		super();
		this.currentEvent = new IEMCurrentEventPOJO();
		this.pastEvents = new IEMPastEventsPOJO();
	}

	public IEMCurrentEventPOJO getCurrentEvent() {
		return currentEvent;
	}

	public void setCurrentEvent(IEMCurrentEventPOJO currentEvent) {
		this.currentEvent = currentEvent;
	}

	public IEMPastEventsPOJO getPastEvents() {
		return pastEvents;
	}

	public void setPastEvents(IEMPastEventsPOJO pastEvents) {
		this.pastEvents = pastEvents;
	}
}
