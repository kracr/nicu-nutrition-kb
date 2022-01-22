package com.inicu.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.inicu.postgres.entities.HeadtotoeExamination;
import com.inicu.postgres.entities.ScoreBallard;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientInfoAddChildObj {

	
//	upto here................
	
	
	//phase 2 admission form.....
	//pasted here from top....
	
	private GestationObj  actualGestation; //gestation by usg.
	private GestationObj gestationByLmp;
	private Object bloodGroup;
	private Object cry; //patient child info object
	private TimeObj timeOfCry; //patient child info object
	
	private Object birthWeight; //patient child info object
	private Object birthLength ; //patient child info object
	private Object birthHeadCircum; //patient child info object
	
	//new ................
	private Object spo2;
	private Object rr;
	private Object bp;
	private Object temprature;
	private Object pulserate;
	
	
	private ScoreBallard ballardScoreCurrent;
	
	private Object injectionVitaminK;
	private Object antenatalSteroids;
	private Object anteNatalCheckup;
	private Object flatusTube;
	
	private HeadtotoeExamination headToToe;
	
	private Object birthMarks;
	private Object birthMarksComments;
	private Object birthInjuries;
	private Object birthInjuriesComments;
	private Object comments;
	
	private Object isFetalDistres;
	private Object isMeconiumPresent;
	private Object isShiftedToIPPR;
	private Object isSurfactantGiven;
	private Object surfactantDose;
	private Object timeToRegularRespiration;
	
	private ApgarScoreObj apgarScore;
	private Object ctgNormal;
	private Object ctgComments;
	private Object resuscitationComments;
	
	
	


	public PatientInfoAddChildObj() {
		super();
		this.actualGestation = new GestationObj();
		this.gestationByLmp = new GestationObj();
		this.bloodGroup = bloodGroup;
		this.cry = cry;
		this.timeOfCry = timeOfCry;
		this.birthWeight = birthWeight;
		this.birthLength = birthLength;
		this.birthHeadCircum = birthHeadCircum;
		this.spo2 = "";
		this.rr = "";
		this.bp = "";
		this.temprature = "";
		this.pulserate = "";
		this.ballardScoreCurrent = new ScoreBallard();
		this.injectionVitaminK = injectionVitaminK;
		this.antenatalSteroids = antenatalSteroids;
		this.anteNatalCheckup = anteNatalCheckup;
		this.flatusTube = flatusTube;
		this.headToToe = new HeadtotoeExamination();
		this.birthMarks = birthMarks;
		this.birthMarksComments = "";
		this.birthInjuries = birthInjuries;
		this.birthInjuriesComments = "";
		this.comments = "";
		this.isFetalDistres = isFetalDistres;
		this.isMeconiumPresent = isMeconiumPresent;
		this.isShiftedToIPPR = isShiftedToIPPR;
		this.isSurfactantGiven = isSurfactantGiven;
		this.surfactantDose = surfactantDose;
		this.timeToRegularRespiration = "";
		this.apgarScore = new ApgarScoreObj();
		this.ctgNormal = ctgNormal;
		this.ctgComments = "";
		this.resuscitationComments = "";
	}


	public GestationObj getGestationByLmp() {
		return gestationByLmp;
	}


	public void setGestationByLmp(GestationObj gestationByLmp) {
		this.gestationByLmp = gestationByLmp;
	}


	public GestationObj getActualGestation() {
		return actualGestation;
	}


	public void setActualGestation(GestationObj actualGestation) {
		this.actualGestation = actualGestation;
	}


	public Object getBloodGroup() {
		return bloodGroup;
	}


	public void setBloodGroup(Object bloodGroup) {
		this.bloodGroup = bloodGroup;
	}




	public Object getIsShiftedToIPPR() {
		return isShiftedToIPPR;
	}


	public void setIsShiftedToIPPR(Object isShiftedToIPPR) {
		this.isShiftedToIPPR = isShiftedToIPPR;
	}


	public Object getCry() {
		return cry;
	}


	public void setCry(Object cry) {
		this.cry = cry;
	}


	public TimeObj getTimeOfCry() {
		return timeOfCry;
	}


	public void setTimeOfCry(TimeObj timeOfCry) {
		this.timeOfCry = timeOfCry;
	}


	public Object getBirthWeight() {
		return birthWeight;
	}


	public void setBirthWeight(Object birthWeight) {
		this.birthWeight = birthWeight;
	}


	public Object getBirthLength() {
		return birthLength;
	}


	public void setBirthLength(Object birthLength) {
		this.birthLength = birthLength;
	}


	

	public Object getBirthHeadCircum() {
		return birthHeadCircum;
	}


	public void setBirthHeadCircum(Object birthHeadCircum) {
		this.birthHeadCircum = birthHeadCircum;
	}


	public Object getSpo2() {
		return spo2;
	}


	public void setSpo2(Object spo2) {
		this.spo2 = spo2;
	}


	public Object getRr() {
		return rr;
	}


	public void setRr(Object rr) {
		this.rr = rr;
	}


	public Object getBp() {
		return bp;
	}


	public void setBp(Object bp) {
		this.bp = bp;
	}


	public Object getTemprature() {
		return temprature;
	}


	public void setTemprature(Object temprature) {
		this.temprature = temprature;
	}


	public Object getPulserate() {
		return pulserate;
	}


	public void setPulserate(Object pulserate) {
		this.pulserate = pulserate;
	}


	
	public ScoreBallard getBallardScoreCurrent() {
		return ballardScoreCurrent;
	}


	public void setBallardScoreCurrent(ScoreBallard ballardScoreCurrent) {
		this.ballardScoreCurrent = ballardScoreCurrent;
	}


	public Object getAnteNatalCheckup() {
		return anteNatalCheckup;
	}


	public void setAnteNatalCheckup(Object anteNatalCheckup) {
		this.anteNatalCheckup = anteNatalCheckup;
	}


	public Object getFlatusTube() {
		return flatusTube;
	}


	public void setFlatusTube(Object flatusTube) {
		this.flatusTube = flatusTube;
	}


	public Object getInjectionVitaminK() {
		return injectionVitaminK;
	}


	public void setInjectionVitaminK(Object injectionVitaminK) {
		this.injectionVitaminK = injectionVitaminK;
	}


	public Object getAntenatalSteroids() {
		return antenatalSteroids;
	}


	public void setAntenatalSteroids(Object antenatalSteroids) {
		this.antenatalSteroids = antenatalSteroids;
	}


	public HeadtotoeExamination getHeadToToe() {
		return headToToe;
	}


	public void setHeadToToe(HeadtotoeExamination headToToe) {
		this.headToToe = headToToe;
	}


	public Object getBirthMarks() {
		return birthMarks;
	}


	public void setBirthMarks(Object birthMarks) {
		this.birthMarks = birthMarks;
	}


	public Object getBirthMarksComments() {
		return birthMarksComments;
	}


	public void setBirthMarksComments(Object birthMarksComments) {
		this.birthMarksComments = birthMarksComments;
	}


	public Object getBirthInjuries() {
		return birthInjuries;
	}


	public void setBirthInjuries(Object birthInjuries) {
		this.birthInjuries = birthInjuries;
	}


	public Object getBirthInjuriesComments() {
		return birthInjuriesComments;
	}


	public void setBirthInjuriesComments(Object birthInjuriesComments) {
		this.birthInjuriesComments = birthInjuriesComments;
	}


	public Object getComments() {
		return comments;
	}


	public void setComments(Object comments) {
		this.comments = comments;
	}


	public Object getIsFetalDistres() {
		return isFetalDistres;
	}


	public void setIsFetalDistres(Object isFetalDistres) {
		this.isFetalDistres = isFetalDistres;
	}


	public Object getIsMeconiumPresent() {
		return isMeconiumPresent;
	}


	public void setIsMeconiumPresent(Object isMeconiumPresent) {
		this.isMeconiumPresent = isMeconiumPresent;
	}


	public Object getIsSurfactantGiven() {
		return isSurfactantGiven;
	}


	public void setIsSurfactantGiven(Object isSurfactantGiven) {
		this.isSurfactantGiven = isSurfactantGiven;
	}


	public Object getSurfactantDose() {
		return surfactantDose;
	}


	public void setSurfactantDose(Object surfactantDose) {
		this.surfactantDose = surfactantDose;
	}


	public Object getTimeToRegularRespiration() {
		return timeToRegularRespiration;
	}


	public void setTimeToRegularRespiration(Object timeToRegularRespiration) {
		this.timeToRegularRespiration = timeToRegularRespiration;
	}


	

	

	

	public ApgarScoreObj getApgarScore() {
		return apgarScore;
	}


	public void setApgarScore(ApgarScoreObj apgarScore) {
		this.apgarScore = apgarScore;
	}


	public Object getCtgNormal() {
		return ctgNormal;
	}


	public void setCtgNormal(Object ctgNormal) {
		this.ctgNormal = ctgNormal;
	}


	public Object getCtgComments() {
		return ctgComments;
	}


	public void setCtgComments(Object ctgComments) {
		this.ctgComments = ctgComments;
	}


	public Object getResuscitationComments() {
		return resuscitationComments;
	}


	public void setResuscitationComments(Object resuscitationComments) {
		this.resuscitationComments = resuscitationComments;
	}


	
	
}
