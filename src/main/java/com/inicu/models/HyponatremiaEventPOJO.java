package com.inicu.models;

import java.util.List;


public class HyponatremiaEventPOJO {

	HyponatremiaCurrentEventPOJO currentEvent;
	
	HyponatremiaPastEventsPOJO pastEvents;
	
	

	public HyponatremiaEventPOJO() {
		super();
		this.currentEvent = new HyponatremiaCurrentEventPOJO();
		this.pastEvents = new HyponatremiaPastEventsPOJO();
	}

	public HyponatremiaCurrentEventPOJO getCurrentEvent() {
		return currentEvent;
	}

	public void setCurrentEvent(HyponatremiaCurrentEventPOJO currentEvent) {
		this.currentEvent = currentEvent;
	}

	public HyponatremiaPastEventsPOJO getPastEvents() {
		return pastEvents;
	}

	public void setPastEvents(HyponatremiaPastEventsPOJO pastEvents) {
		this.pastEvents = pastEvents;
	}
	
	
	
	
	
	
	
}
