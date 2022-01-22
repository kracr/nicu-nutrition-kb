package com.inicu.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Transient;

import com.inicu.postgres.entities.BabyPrescription;
import com.inicu.postgres.entities.NursingEpisode;
import com.inicu.postgres.entities.RespSupport;
import com.inicu.postgres.entities.SaRespApnea;

public class ApneaPOJO {

	private SaRespApnea currentApnea;

	private RespSupport respSupport;

	private Boolean apneaEvent;

	private NursingEpisode currentEpisode;

	private List<SaRespApnea> pastApnea;

	private List<NursingEpisode> pastNursingEpisode;

	private HashMap<String, RespSupport> pastRespSupportMap;

	private Integer numberOfEpisode;

	private Integer numberOfHours;

	private Integer numberOfSpontaneous;

	private Integer numberOfStimulation;

	private Integer numberOfPPV;

	private Integer cummulativeDaysOfApnea = 0;

	@Transient
	List<BabyPrescription> prescriptionList;

	private String inactiveProgressNote;

	public ApneaPOJO() {
		super();
		apneaEvent = false;
		currentApnea = new SaRespApnea();
		respSupport = new RespSupport();
		currentEpisode = new NursingEpisode();
		pastRespSupportMap = new HashMap<String, RespSupport>();
		this.prescriptionList = new ArrayList<BabyPrescription>();
	}

	public SaRespApnea getCurrentApnea() {
		return currentApnea;
	}

	public void setCurrentApnea(SaRespApnea currentApnea) {
		this.currentApnea = currentApnea;
	}

	public RespSupport getRespSupport() {
		return respSupport;
	}

	public void setRespSupport(RespSupport respSupport) {
		this.respSupport = respSupport;
	}

	public Boolean getApneaEvent() {
		return apneaEvent;
	}

	public void setApneaEvent(Boolean apneaEvent) {
		this.apneaEvent = apneaEvent;
	}

	public NursingEpisode getCurrentEpisode() {
		return currentEpisode;
	}

	public void setCurrentEpisode(NursingEpisode currentEpisode) {
		this.currentEpisode = currentEpisode;
	}

	public List<SaRespApnea> getPastApnea() {
		return pastApnea;
	}

	public void setPastApnea(List<SaRespApnea> pastApnea) {
		this.pastApnea = pastApnea;
	}

	public List<NursingEpisode> getPastNursingEpisode() {
		return pastNursingEpisode;
	}

	public void setPastNursingEpisode(List<NursingEpisode> pastNursingEpisode) {
		this.pastNursingEpisode = pastNursingEpisode;
	}

	public HashMap<String, RespSupport> getPastRespSupportMap() {
		return pastRespSupportMap;
	}

	public void setPastRespSupportMap(HashMap<String, RespSupport> pastRespSupportMap) {
		this.pastRespSupportMap = pastRespSupportMap;
	}

	public Integer getNumberOfEpisode() {
		return numberOfEpisode;
	}

	public void setNumberOfEpisode(Integer numberOfEpisode) {
		this.numberOfEpisode = numberOfEpisode;
	}

	public Integer getNumberOfHours() {
		return numberOfHours;
	}

	public void setNumberOfHours(Integer numberOfHours) {
		this.numberOfHours = numberOfHours;
	}

	public Integer getNumberOfSpontaneous() {
		return numberOfSpontaneous;
	}

	public void setNumberOfSpontaneous(Integer numberOfSpontaneous) {
		this.numberOfSpontaneous = numberOfSpontaneous;
	}

	public Integer getNumberOfStimulation() {
		return numberOfStimulation;
	}

	public void setNumberOfStimulation(Integer numberOfStimulation) {
		this.numberOfStimulation = numberOfStimulation;
	}

	public Integer getNumberOfPPV() {
		return numberOfPPV;
	}

	public void setNumberOfPPV(Integer numberOfPPV) {
		this.numberOfPPV = numberOfPPV;
	}

	public Integer getCummulativeDaysOfApnea() {
		return cummulativeDaysOfApnea;
	}

	public void setCummulativeDaysOfApnea(Integer cummulativeDaysOfApnea) {
		this.cummulativeDaysOfApnea = cummulativeDaysOfApnea;
	}

	public List<BabyPrescription> getPrescriptionList() {
		return prescriptionList;
	}

	public void setPrescriptionList(List<BabyPrescription> prescriptionList) {
		this.prescriptionList = prescriptionList;
	}

	public String getInactiveProgressNote() {
		return inactiveProgressNote;
	}

	public void setInactiveProgressNote(String inactiveProgressNote) {
		this.inactiveProgressNote = inactiveProgressNote;
	}

	@Override
	public String toString() {
		return "ApneaPOJO [currentApnea=" + currentApnea + ", respSupport=" + respSupport + ", apneaEvent=" + apneaEvent
				+ ", currentEpisode=" + currentEpisode + ", pastApnea=" + pastApnea + ", pastNursingEpisode="
				+ pastNursingEpisode + ", pastRespSupportMap=" + pastRespSupportMap + ", numberOfEpisode="
				+ numberOfEpisode + ", numberOfHours=" + numberOfHours + ", numberOfSpontaneous=" + numberOfSpontaneous
				+ ", numberOfStimulation=" + numberOfStimulation + ", numberOfPPV=" + numberOfPPV
				+ ", cummulativeDaysOfApnea=" + cummulativeDaysOfApnea + ", prescriptionList=" + prescriptionList + "]";
	}

}
