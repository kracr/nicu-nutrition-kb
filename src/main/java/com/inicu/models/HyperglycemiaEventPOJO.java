package com.inicu.models;

public class HyperglycemiaEventPOJO {

	HyperglycemiaCurrentEventPOJO currentEvent;

	HyperglycemiaPastEventsPOJO pastEvents;

	public HyperglycemiaEventPOJO() {
		super();
		this.currentEvent = new HyperglycemiaCurrentEventPOJO();
		this.pastEvents = new HyperglycemiaPastEventsPOJO();
	}

	public HyperglycemiaCurrentEventPOJO getCurrentEvent() {
		return currentEvent;
	}

	public void setCurrentEvent(HyperglycemiaCurrentEventPOJO currentEvent) {
		this.currentEvent = currentEvent;
	}

	public HyperglycemiaPastEventsPOJO getPastEvents() {
		return pastEvents;
	}

	public void setPastEvents(HyperglycemiaPastEventsPOJO pastEvents) {
		this.pastEvents = pastEvents;
	}

	@Override
	public String toString() {
		return "HyperglycemiaEventPOJO [currentEvent=" + currentEvent + ", pastEvents=" + pastEvents + "]";
	}

}
