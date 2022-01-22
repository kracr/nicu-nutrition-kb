package com.inicu.models;

import java.sql.Timestamp;
import java.util.List;

import com.inicu.postgres.entities.*;

public class RespiratorySystemEventPOJO {

	private String eventName;

	private String SurfactantDose;

	private List<BabyPrescription> pastPrescriptionList;

	private RespiratoryDistressPOJO respiratoryDistress;

	private ApneaPOJO apnea;

	private RespSysBpdPOJO bpd;

	private RespSysOtherPOJO others;

	private PphnPOJO pphn;

	private PneumothoraxPOJO pneumothorax;

	private RespSupport respSupport;

	private List<RespSupport> respUsage;

	private Integer pastDownesScore;

	private Integer pastSilverScore;
	
	private Timestamp pastSilverTime;
	
	private Timestamp pastDownesTime;

	private List<VwRespsystem> pastTableList;

	private boolean stopTreatmentFlag = false;
	
	private String orderSelectedText;

	private List<ScoreSilverman> scoreSilvermenList;

	public RespiratorySystemEventPOJO() {
		super();
	}

	public RespSupport getRespSupport() {
		return respSupport;
	}

	public void setRespSupport(RespSupport respSupport) {
		this.respSupport = respSupport;
	}

	public String getEventName() {
		return eventName;
	}

	public String getSurfactantDose() {
		return SurfactantDose;
	}

	public void setSurfactantDose(String surfactantDose) {
		SurfactantDose = surfactantDose;
	}

	public List<BabyPrescription> getPastPrescriptionList() {
		return pastPrescriptionList;
	}

	public void setPastPrescriptionList(List<BabyPrescription> pastPrescriptionList) {
		this.pastPrescriptionList = pastPrescriptionList;
	}

	public RespiratoryDistressPOJO getRespiratoryDistress() {
		return respiratoryDistress;
	}

	public ApneaPOJO getApnea() {
		return apnea;
	}

	public RespSysOtherPOJO getOthers() {
		return others;
	}

	public PphnPOJO getPphn() {
		return pphn;
	}

	public void setPphn(PphnPOJO pphn) {
		this.pphn = pphn;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public void setRespiratoryDistress(RespiratoryDistressPOJO respiratoryDistress) {
		this.respiratoryDistress = respiratoryDistress;
	}

	public void setApnea(ApneaPOJO apnea) {
		this.apnea = apnea;
	}

	public RespSysBpdPOJO getBpd() {
		return bpd;
	}

	public void setBpd(RespSysBpdPOJO bpd) {
		this.bpd = bpd;
	}

	public void setOthers(RespSysOtherPOJO others) {
		this.others = others;
	}

	public PneumothoraxPOJO getPneumothorax() {
		return pneumothorax;
	}

	public void setPneumothorax(PneumothoraxPOJO pneumothorax) {
		this.pneumothorax = pneumothorax;
	}

	public Integer getPastDownesScore() {
		return pastDownesScore;
	}

	public void setPastDownesScore(Integer pastDownesScore) {
		this.pastDownesScore = pastDownesScore;
	}

	public List<RespSupport> getRespUsage() {
		return respUsage;
	}

	public void setRespUsage(List<RespSupport> respUsage) {
		this.respUsage = respUsage;
	}

	public Integer getPastSilverScore() {
		return pastSilverScore;
	}

	public void setPastSilverScore(Integer pastSilverScore) {
		this.pastSilverScore = pastSilverScore;
	}

	public Timestamp getPastSilverTime() {
		return pastSilverTime;
	}

	public void setPastSilverTime(Timestamp pastSilverTime) {
		this.pastSilverTime = pastSilverTime;
	}

	public Timestamp getPastDownesTime() {
		return pastDownesTime;
	}

	public void setPastDownesTime(Timestamp pastDownesTime) {
		this.pastDownesTime = pastDownesTime;
	}

	public List<VwRespsystem> getPastTableList() {
		return pastTableList;
	}

	public void setPastTableList(List<VwRespsystem> pastTableList) {
		this.pastTableList = pastTableList;
	}

	public boolean isStopTreatmentFlag() {
		return stopTreatmentFlag;
	}

	public void setStopTreatmentFlag(boolean stopTreatmentFlag) {
		this.stopTreatmentFlag = stopTreatmentFlag;
	}

	public String getOrderSelectedText() {
		return orderSelectedText;
	}

	public void setOrderSelectedText(String orderSelectedText) {
		this.orderSelectedText = orderSelectedText;
	}

	public List<ScoreSilverman> getScoreSilvermenList() {
		return scoreSilvermenList;
	}

	public void setScoreSilvermenList(List<ScoreSilverman> scoreSilvermenList) {
		this.scoreSilvermenList = scoreSilvermenList;
	}
}
