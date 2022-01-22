package com.inicu.models;

public class HypernatremiaEventPOJO {

	HypernatremiaCurrentEventPOJO currentEvent;
	
	HypernatremiaPastEventsPOJO pastEvents;

	public HypernatremiaEventPOJO() {
		super();
		// TODO Auto-generated constructor stub
		this.currentEvent = new HypernatremiaCurrentEventPOJO();
		this.pastEvents = new HypernatremiaPastEventsPOJO();
	}

	public HypernatremiaCurrentEventPOJO getCurrentEvent() {
		return currentEvent;
	}

	public void setCurrentEvent(HypernatremiaCurrentEventPOJO currentEvent) {
		this.currentEvent = currentEvent;
	}

	public HypernatremiaPastEventsPOJO getPastEvents() {
		return pastEvents;
	}

	public void setPastEvents(HypernatremiaPastEventsPOJO pastEvents) {
		this.pastEvents = pastEvents;
	}
	
	
}
