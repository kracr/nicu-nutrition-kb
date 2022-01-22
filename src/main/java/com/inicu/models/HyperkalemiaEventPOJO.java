package com.inicu.models;

public class HyperkalemiaEventPOJO {

	HyperkalemiaCurrentEventPOJO currentEvent;

	HyperkalemiaPastEventsPOJO pastEvents;

	public HyperkalemiaEventPOJO() {
		super();
		this.currentEvent = new HyperkalemiaCurrentEventPOJO();
		this.pastEvents = new HyperkalemiaPastEventsPOJO();
	}

	public HyperkalemiaCurrentEventPOJO getCurrentEvent() {
		return currentEvent;
	}

	public void setCurrentEvent(HyperkalemiaCurrentEventPOJO currentEvent) {
		this.currentEvent = currentEvent;
	}

	public HyperkalemiaPastEventsPOJO getPastEvents() {
		return pastEvents;
	}

	public void setPastEvents(HyperkalemiaPastEventsPOJO pastEvents) {
		this.pastEvents = pastEvents;
	}

	@Override
	public String toString() {
		return "HyperkalemiaEventPOJO [currentEvent=" + currentEvent + ", pastEvents=" + pastEvents + "]";
	}

}
