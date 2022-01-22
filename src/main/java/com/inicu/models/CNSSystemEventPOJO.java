package com.inicu.models;

public class CNSSystemEventPOJO {

	private String eventName;

	private CNSSystemCommonEventPOJO commonEventsInfo; // common obj in all events put here...like respiratory system.

	private AsphyxiaEventPOJO asphyxiaEvent; // separate event

	private SeizuresEventPOJO seizuresEvent;

	private EncephalopathyEventPOJO encephalopathyEvent;

	private NeuromuscularDisordersEventPOJO neuromuscularDisorderEvent;
	private HydrocephalusEventPOJO hydrocephalusEvent;
	private IVHEventPOJO ivhEvent;

	private boolean stopTreatmentFlag = false;

	public CNSSystemEventPOJO() {
		super();
		this.commonEventsInfo = new CNSSystemCommonEventPOJO();
		this.asphyxiaEvent = new AsphyxiaEventPOJO();
		this.seizuresEvent = new SeizuresEventPOJO();
		this.ivhEvent = new IVHEventPOJO();
		this.encephalopathyEvent=new EncephalopathyEventPOJO();
		this.neuromuscularDisorderEvent=new NeuromuscularDisordersEventPOJO();
		this.hydrocephalusEvent=new HydrocephalusEventPOJO();
	}

	public HydrocephalusEventPOJO getHydrocephalusEvent() {
		return hydrocephalusEvent;
	}

	public void setHydrocephalusEvent(HydrocephalusEventPOJO hydrocephalusEvent) {
		this.hydrocephalusEvent = hydrocephalusEvent;
	}

	public NeuromuscularDisordersEventPOJO getNeuromuscularDisorderEvent() {
		return neuromuscularDisorderEvent;
	}

	public void setNeuromuscularDisorderEvent(NeuromuscularDisordersEventPOJO neuromuscularDisorderEvent) {
		this.neuromuscularDisorderEvent = neuromuscularDisorderEvent;
	}


	public String getEventName() {
		return eventName;
	}

	public CNSSystemCommonEventPOJO getCommonEventsInfo() {
		return commonEventsInfo;
	}

	public AsphyxiaEventPOJO getAsphyxiaEvent() {
		return asphyxiaEvent;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public void setCommonEventsInfo(CNSSystemCommonEventPOJO commonEventsInfo) {
		this.commonEventsInfo = commonEventsInfo;
	}

	public void setAsphyxiaEvent(AsphyxiaEventPOJO asphyxiaEvent) {
		this.asphyxiaEvent = asphyxiaEvent;
	}

	public SeizuresEventPOJO getSeizuresEvent() {
		return seizuresEvent;
	}

	public void setSeizuresEvent(SeizuresEventPOJO seizuresEvent) {
		this.seizuresEvent = seizuresEvent;
	}

	public IVHEventPOJO getIvhEvent() {
		return ivhEvent;
	}

	public EncephalopathyEventPOJO getEncephalopathyEvent() {
		return encephalopathyEvent;
	}

	public void setEncephalopathyEvent(EncephalopathyEventPOJO encephalopathyEvent) {
		this.encephalopathyEvent = encephalopathyEvent;
	}

	public void setIvhEvent(IVHEventPOJO ivhEvent) {
		this.ivhEvent = ivhEvent;
	}

	public boolean isStopTreatmentFlag() {
		return stopTreatmentFlag;
	}

	public void setStopTreatmentFlag(boolean stopTreatmentFlag) {
		this.stopTreatmentFlag = stopTreatmentFlag;
	}



}
