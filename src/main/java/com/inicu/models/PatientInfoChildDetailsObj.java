package com.inicu.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.inicu.postgres.entities.MaternalPasthistory;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientInfoChildDetailsObj {
	
	
//	From :-----------------------------------------In Mother Current Pregnancy:---------------
	private Object mothersAge;
	private Object mothersBloodGroup;
	private Object smoke;
	private Object alcohol;
	private Object gScore;
	private Object aScore;
	private Object pScore;
	private Object lScore;
	private Object lmp;
	private Object edd;
	private Object deliverymode;
	private Object deliverymodeType;
	private Object breechDelivery;
	
	private List<MaternalPasthistory> maternalPastHistoryPastLsit;
	private MaternalPasthistory maternalPastHistoryCurrent;
	
	private Object antinatalVaccinations;
	private Object antenatalSuplements;
	private Object maternalIllness;
	private Object maternalMedications;
	
	// up-To:-----------------------------------------In Mother Current Pregnancy:---------------
	
	public PatientInfoChildDetailsObj() {
		super();
		this.mothersAge = mothersAge;
		this.smoke = smoke;
		this.alcohol = alcohol;
		this.gScore = gScore;
		this.aScore = aScore;
		this.pScore = pScore;
		this.lScore = lScore;
		this.lmp = lmp;
		this.edd = edd;
		this.deliverymode = deliverymode;
		this.deliverymodeType = deliverymodeType;
		this.breechDelivery = breechDelivery;
		this.maternalPastHistoryPastLsit = new ArrayList<MaternalPasthistory>();
		this.maternalPastHistoryCurrent = new MaternalPasthistory();
		this.antinatalVaccinations = antinatalVaccinations;
		this.antenatalSuplements = antenatalSuplements;
		this.maternalIllness = maternalIllness;
		this.maternalMedications = "";
	}

	public Object getMothersAge() {
		return mothersAge;
	}

	public void setMothersAge(Object mothersAge) {
		this.mothersAge = mothersAge;
	}

	public Object getSmoke() {
		return smoke;
	}

	public void setSmoke(Object smoke) {
		this.smoke = smoke;
	}

	public Object getAlcohol() {
		return alcohol;
	}

	public void setAlcohol(Object alcohol) {
		this.alcohol = alcohol;
	}

	public Object getgScore() {
		return gScore;
	}

	public void setgScore(Object gScore) {
		this.gScore = gScore;
	}

	public Object getaScore() {
		return aScore;
	}

	public void setaScore(Object aScore) {
		this.aScore = aScore;
	}

	public Object getpScore() {
		return pScore;
	}

	public void setpScore(Object pScore) {
		this.pScore = pScore;
	}

	public Object getlScore() {
		return lScore;
	}

	public void setlScore(Object lScore) {
		this.lScore = lScore;
	}

	public Object getLmp() {
		return lmp;
	}

	public void setLmp(Object lmp) {
		this.lmp = lmp;
	}

	public Object getEdd() {
		return edd;
	}

	public void setEdd(Object edd) {
		this.edd = edd;
	}

	public Object getDeliverymode() {
		return deliverymode;
	}

	public void setDeliverymode(Object deliverymode) {
		this.deliverymode = deliverymode;
	}

	public Object getDeliverymodeType() {
		return deliverymodeType;
	}

	public void setDeliverymodeType(Object deliverymodeType) {
		this.deliverymodeType = deliverymodeType;
	}

	public Object getBreechDelivery() {
		return breechDelivery;
	}

	public void setBreechDelivery(Object breechDelivery) {
		this.breechDelivery = breechDelivery;
	}

	public List<MaternalPasthistory> getMaternalPastHistoryPastLsit() {
		return maternalPastHistoryPastLsit;
	}

	public void setMaternalPastHistoryPastLsit(
			List<MaternalPasthistory> maternalPastHistoryPastLsit) {
		this.maternalPastHistoryPastLsit = maternalPastHistoryPastLsit;
	}

	public MaternalPasthistory getMaternalPastHistoryCurrent() {
		return maternalPastHistoryCurrent;
	}

	public void setMaternalPastHistoryCurrent(
			MaternalPasthistory maternalPastHistoryCurrent) {
		this.maternalPastHistoryCurrent = maternalPastHistoryCurrent;
	}

	public Object getAntinatalVaccinations() {
		return antinatalVaccinations;
	}

	public void setAntinatalVaccinations(Object antinatalVaccinations) {
		this.antinatalVaccinations = antinatalVaccinations;
	}

	public Object getAntenatalSuplements() {
		return antenatalSuplements;
	}

	public void setAntenatalSuplements(Object antenatalSuplements) {
		this.antenatalSuplements = antenatalSuplements;
	}

	public Object getMaternalIllness() {
		return maternalIllness;
	}

	public void setMaternalIllness(Object maternalIllness) {
		this.maternalIllness = maternalIllness;
	}

	public Object getMaternalMedications() {
		return maternalMedications;
	}

	public void setMaternalMedications(Object maternalMedications) {
		this.maternalMedications = maternalMedications;
	}

	public Object getMothersBloodGroup() {
		return mothersBloodGroup;
	}

	public void setMothersBloodGroup(Object mothersBloodGroup) {
		this.mothersBloodGroup = mothersBloodGroup;
	}
	
	

	
	
	
	
}
