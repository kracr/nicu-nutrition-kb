package com.inicu.models;

public class AcidosisEventPOJO {
	
	AcidosisCurrentEventPOJO currentEvent;
	
	AcidosisPastEventsPOJO pastEvents;

	public AcidosisEventPOJO() {
		super();
		this.currentEvent = new AcidosisCurrentEventPOJO();
		this.pastEvents = new AcidosisPastEventsPOJO();		
	}

	public AcidosisCurrentEventPOJO getCurrentEvent() {
		return currentEvent;
	}

	public void setCurrentEvent(AcidosisCurrentEventPOJO currentEvent) {
		this.currentEvent = currentEvent;
	}

	public AcidosisPastEventsPOJO getPastEvents() {
		return pastEvents;
	}

	public void setPastEvents(AcidosisPastEventsPOJO pastEvents) {
		this.pastEvents = pastEvents;
	}
	
	
	
	
	

}
