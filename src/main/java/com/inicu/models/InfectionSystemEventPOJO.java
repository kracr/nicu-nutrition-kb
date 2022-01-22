package com.inicu.models;

import java.util.ArrayList;
import java.util.List;

import com.inicu.postgres.entities.SaInfection;

public class InfectionSystemEventPOJO {
	
	private String eventName;

	private InfectionSystemCommonEventPOJO commonEventsInfo;
	
	private SepsisEventPOJO sepsisEvent;
	
	private VapEventPOJO vapEvent;
	
	private ClabsiEventPOJO clabsiEvent;
	
	private IntrauterineEventPOJO intrauterineEvent;

	private NecEventPOJO necEvent;
	
	private SaInfection currentEvent; 
	
	private List<SaInfection> infectionList;
	
	public InfectionSystemEventPOJO() {
		super();
		// TODO Auto-generated constructor stub
		this.commonEventsInfo = new InfectionSystemCommonEventPOJO();
		this.sepsisEvent = new SepsisEventPOJO();
		this.vapEvent = new VapEventPOJO();
		this.clabsiEvent = new ClabsiEventPOJO();
		this.intrauterineEvent=new IntrauterineEventPOJO();
		this.currentEvent = new SaInfection();
		this.infectionList = new ArrayList<SaInfection>();
		this.necEvent=new NecEventPOJO();
	}

	public NecEventPOJO getNecEvent() {
		return necEvent;
	}

	public void setNecEvent(NecEventPOJO necEvent) {
		this.necEvent = necEvent;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public InfectionSystemCommonEventPOJO getCommonEventsInfo() {
		return commonEventsInfo;
	}

	public void setCommonEventsInfo(InfectionSystemCommonEventPOJO commonEventsInfo) {
		this.commonEventsInfo = commonEventsInfo;
	}

	public SepsisEventPOJO getSepsisEvent() {
		return sepsisEvent;
	}

	public void setSepsisEvent(SepsisEventPOJO sepsisEvent) {
		this.sepsisEvent = sepsisEvent;
	}

	public VapEventPOJO getVapEvent() {
		return vapEvent;
	}

	public void setVapEvent(VapEventPOJO vapEvent) {
		this.vapEvent = vapEvent;
	}

	public ClabsiEventPOJO getClabsiEvent() {
		return clabsiEvent;
	}

	public void setClabsiEvent(ClabsiEventPOJO clabsiEvent) {
		this.clabsiEvent = clabsiEvent;
	}
	
	public IntrauterineEventPOJO getIntrauterineEvent() {
		return intrauterineEvent;
	}

	public void setIntrauterineEvent(IntrauterineEventPOJO intrauterineEvent) {
		this.intrauterineEvent = intrauterineEvent;
	}

	public SaInfection getCurrentEvent() {
		return currentEvent;
	}

	public void setCurrentEvent(SaInfection currentEvent) {
		this.currentEvent = currentEvent;
	}

	public List<SaInfection> getInfectionList() {
		return infectionList;
	}

	public void setInfectionList(List<SaInfection> infectionList) {
		this.infectionList = infectionList;
	}
	
	
}
