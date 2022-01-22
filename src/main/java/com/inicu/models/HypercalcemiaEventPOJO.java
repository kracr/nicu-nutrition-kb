package com.inicu.models;

public class HypercalcemiaEventPOJO {
	
	HypercalcemiaCurrentEventPOJO currentEvent;
	
	HypercalcemiaPastEventsPOJO pastEvents;

	public HypercalcemiaEventPOJO() {
		super();
		// TODO Auto-generated constructor stub
		this.currentEvent = new HypercalcemiaCurrentEventPOJO();
		this.pastEvents = new HypercalcemiaPastEventsPOJO();
	}

	public HypercalcemiaCurrentEventPOJO getCurrentEvent() {
		return currentEvent;
	}

	public void setCurrentEvent(HypercalcemiaCurrentEventPOJO currentEvent) {
		this.currentEvent = currentEvent;
	}

	public HypercalcemiaPastEventsPOJO getPastEvents() {
		return pastEvents;
	}

	public void setPastEvents(HypercalcemiaPastEventsPOJO pastEvents) {
		this.pastEvents = pastEvents;
	}
}
