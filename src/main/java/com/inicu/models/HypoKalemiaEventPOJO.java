package com.inicu.models;

public class HypoKalemiaEventPOJO {

	HypoKalemiaCurrentEventPOJO currentEvent;

	HypoKalemiaPastEventsPOJO pastEvents;

	public HypoKalemiaEventPOJO() {
		super();
		this.currentEvent = new HypoKalemiaCurrentEventPOJO();
		this.pastEvents = new HypoKalemiaPastEventsPOJO();
	}

	public HypoKalemiaCurrentEventPOJO getCurrentEvent() {
		return currentEvent;
	}

	public HypoKalemiaPastEventsPOJO getPastEvents() {
		return pastEvents;
	}

	public void setCurrentEvent(HypoKalemiaCurrentEventPOJO currentEvent) {
		this.currentEvent = currentEvent;
	}

	public void setPastEvents(HypoKalemiaPastEventsPOJO pastEvents) {
		this.pastEvents = pastEvents;
	}

}
