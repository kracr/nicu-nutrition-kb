package com.inicu.models;

import java.util.ArrayList;
import java.util.List;

import com.inicu.postgres.entities.BabyPrescription;
import com.inicu.postgres.entities.RespSupport;
import com.inicu.postgres.entities.VwRespiratoryUsageFinal;

public class MetabolicSystemEventPOJO {

	private String eventName;

	HypoglycemiaJSON hypoglycemiaEvent;

	HyperglycemiaEventPOJO hyperglycemiaEvent;

	HyponatremiaEventPOJO hyponatremiaEvent;

	HypernatremiaEventPOJO hypernatremiaEvent;

	HypoKalemiaEventPOJO hypokalemiaEvent;

	HyperkalemiaEventPOJO hyperkalemiaEvent;

	IEMEventPOJO iemEvent;

	AcidosisEventPOJO acidosisEvent;

	HypercalcemiaEventPOJO hypercalcemiaEvent;

	HypocalcemiaEventPOJO hypocalcemiaEvent;

	private BabyPrescription babyPrescriptionEmptyObj;

	private List<BabyPrescription> currentHypocalcemiaPrescriptionList;

	private List<BabyPrescription> currentHypokalemiaPrescriptionList;

	private List<BabyPrescription> currentHyperkalemiaPrescriptionList;

	private List<BabyPrescription> currentBabyPrescriptionList;

	private List<BabyPrescription> pastPrescriptionList;

	private List<BabyPrescription> currentAntibioticsPrescriptionList;

	private RespSupport respSupport;
	
	private List<VwRespiratoryUsageFinal> respUsage;

	public MetabolicSystemEventPOJO() {
		super();
		this.hypoglycemiaEvent = new HypoglycemiaJSON();
		this.hyperglycemiaEvent = new HyperglycemiaEventPOJO();
		this.hyponatremiaEvent = new HyponatremiaEventPOJO();
		this.hypernatremiaEvent = new HypernatremiaEventPOJO();
		this.hyperkalemiaEvent = new HyperkalemiaEventPOJO();
		this.babyPrescriptionEmptyObj = new BabyPrescription();
		this.iemEvent = new IEMEventPOJO();
		this.hypercalcemiaEvent = new HypercalcemiaEventPOJO();
		this.hypocalcemiaEvent = new HypocalcemiaEventPOJO();
		this.currentHypocalcemiaPrescriptionList = new ArrayList<BabyPrescription>();
		this.currentHypokalemiaPrescriptionList = new ArrayList<BabyPrescription>();
		this.currentHyperkalemiaPrescriptionList = new ArrayList<BabyPrescription>();
		this.currentBabyPrescriptionList = new ArrayList<BabyPrescription>();
		this.pastPrescriptionList = new ArrayList<BabyPrescription>();
		this.currentAntibioticsPrescriptionList = new ArrayList<BabyPrescription>();
	}

	
	public HypoglycemiaJSON getHypoglycemiaEvent() {
		return hypoglycemiaEvent;
	}


	public void setHypoglycemiaEvent(HypoglycemiaJSON hypoglycemiaEvent) {
		this.hypoglycemiaEvent = hypoglycemiaEvent;
	}


	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public BabyPrescription getBabyPrescriptionEmptyObj() {
		return babyPrescriptionEmptyObj;
	}

	public List<BabyPrescription> getCurrentBabyPrescriptionList() {
		return currentBabyPrescriptionList;
	}

	public List<BabyPrescription> getPastPrescriptionList() {
		return pastPrescriptionList;
	}

	public void setBabyPrescriptionEmptyObj(BabyPrescription babyPrescriptionEmptyObj) {
		this.babyPrescriptionEmptyObj = babyPrescriptionEmptyObj;
	}

	public List<BabyPrescription> getCurrentHypocalcemiaPrescriptionList() {
		return currentHypocalcemiaPrescriptionList;
	}

	public void setCurrentHypocalcemiaPrescriptionList(List<BabyPrescription> currentHypocalcemiaPrescriptionList) {
		this.currentHypocalcemiaPrescriptionList = currentHypocalcemiaPrescriptionList;
	}

	public List<BabyPrescription> getCurrentHypokalemiaPrescriptionList() {
		return currentHypokalemiaPrescriptionList;
	}

	public void setCurrentHypokalemiaPrescriptionList(List<BabyPrescription> currentHypokalemiaPrescriptionList) {
		this.currentHypokalemiaPrescriptionList = currentHypokalemiaPrescriptionList;
	}

	public List<BabyPrescription> getCurrentHyperkalemiaPrescriptionList() {
		return currentHyperkalemiaPrescriptionList;
	}

	public void setCurrentHyperkalemiaPrescriptionList(List<BabyPrescription> currentHyperkalemiaPrescriptionList) {
		this.currentHyperkalemiaPrescriptionList = currentHyperkalemiaPrescriptionList;
	}

	public void setCurrentBabyPrescriptionList(List<BabyPrescription> currentBabyPrescriptionList) {
		this.currentBabyPrescriptionList = currentBabyPrescriptionList;
	}

	public void setPastPrescriptionList(List<BabyPrescription> pastPrescriptionList) {
		this.pastPrescriptionList = pastPrescriptionList;
	}

	public HyperglycemiaEventPOJO getHyperglycemiaEvent() {
		return hyperglycemiaEvent;
	}

	public void setHyperglycemiaEvent(HyperglycemiaEventPOJO hyperglycemiaEvent) {
		this.hyperglycemiaEvent = hyperglycemiaEvent;
	}

	public HyponatremiaEventPOJO getHyponatremiaEvent() {
		return hyponatremiaEvent;
	}

	public void setHyponatremiaEvent(HyponatremiaEventPOJO hyponatremiaEvent) {
		this.hyponatremiaEvent = hyponatremiaEvent;
	}

	public HypernatremiaEventPOJO getHypernatremiaEvent() {
		return hypernatremiaEvent;
	}

	public void setHypernatremiaEvent(HypernatremiaEventPOJO hypernatremiaEvent) {
		this.hypernatremiaEvent = hypernatremiaEvent;
	}

	public HyperkalemiaEventPOJO getHyperkalemiaEvent() {
		return hyperkalemiaEvent;
	}

	public void setHyperkalemiaEvent(HyperkalemiaEventPOJO hyperkalemiaEvent) {
		this.hyperkalemiaEvent = hyperkalemiaEvent;
	}

	public IEMEventPOJO getIemEvent() {
		return iemEvent;
	}

	public void setIemEvent(IEMEventPOJO iemEvent) {
		this.iemEvent = iemEvent;
	}

	public AcidosisEventPOJO getAcidosisEvent() {
		return acidosisEvent;
	}

	public void setAcidosisEvent(AcidosisEventPOJO acidosisEvent) {
		this.acidosisEvent = acidosisEvent;
	}

	public HypoKalemiaEventPOJO getHypokalemiaEvent() {
		return hypokalemiaEvent;
	}

	public void setHypokalemiaEvent(HypoKalemiaEventPOJO hypokalemiaEvent) {
		this.hypokalemiaEvent = hypokalemiaEvent;
	}

	public HypercalcemiaEventPOJO getHypercalcemiaEvent() {
		return hypercalcemiaEvent;
	}

	public void setHypercalcemiaEvent(HypercalcemiaEventPOJO hypercalcemiaEvent) {
		this.hypercalcemiaEvent = hypercalcemiaEvent;
	}

	public List<BabyPrescription> getCurrentAntibioticsPrescriptionList() {
		return currentAntibioticsPrescriptionList;
	}

	public void setCurrentAntibioticsPrescriptionList(List<BabyPrescription> currentAntibioticsPrescriptionList) {
		this.currentAntibioticsPrescriptionList = currentAntibioticsPrescriptionList;
	}

	public RespSupport getRespSupport() {
		return respSupport;
	}

	public void setRespSupport(RespSupport respSupport) {
		this.respSupport = respSupport;
	}

	public HypocalcemiaEventPOJO getHypocalcemiaEvent() {
		return hypocalcemiaEvent;
	}

	public void setHypocalcemiaEvent(HypocalcemiaEventPOJO hypocalcemiaEvent) {
		this.hypocalcemiaEvent = hypocalcemiaEvent;
	}

	public List<VwRespiratoryUsageFinal> getRespUsage() {
		return respUsage;
	}

	public void setRespUsage(List<VwRespiratoryUsageFinal> respUsage) {
		this.respUsage = respUsage;
	}
	
	
}
