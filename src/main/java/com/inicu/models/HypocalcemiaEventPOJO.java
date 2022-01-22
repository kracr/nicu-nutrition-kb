package com.inicu.models;

public class HypocalcemiaEventPOJO {
	
	HypocalcemiaCurrentEventPOJO currentEvent;
	HypocalcemiaPastEventsPOJO pastEvents;
	

	public HypocalcemiaEventPOJO() {
		super();
		// TODO Auto-generated constructor stub
		this.currentEvent = new HypocalcemiaCurrentEventPOJO();
		this.pastEvents = new HypocalcemiaPastEventsPOJO();
	}


	public HypocalcemiaCurrentEventPOJO getCurrentEvent() {
		return currentEvent;
	}


	public void setCurrentEvent(HypocalcemiaCurrentEventPOJO currentEvent) {
		this.currentEvent = currentEvent;
	}


	public HypocalcemiaPastEventsPOJO getPastEvents() {
		return pastEvents;
	}


	public void setPastEvents(HypocalcemiaPastEventsPOJO pastEvents) {
		this.pastEvents = pastEvents;
	}

	
	
}
