package com.inicu.models;

public class BabyVisitsObj {

	
	private Object dob;
	private Object noteEntryDate;
	private Object birthWeight;
	private Object previousWeight;
	private Object presentWeight;
	private Object workingWeight;
	private Object weightChangeFromPrevious;
	private Object weightChangeFromBirth;
	private Object birthHeight;
	private Object presentHeight;
	private Object previousHeight;
	private Object birthHeadcircum;
	private Object presentHeadcircum;
	private Object previousHeadcircum;
 
	private Object birthGestationdays; //both togeather become ga at birth
	private Object birthGestationweeks;
	private Object daysAfterAdmission;
	private Object daysAfterBirth;
	
	private Object correctedGestationdays; //both togeather become ga at birth
	private Object correctedGestationweeks;
	
	private Object surgery ="";
	private Object surgeon ="";
	private Object neonatologist ="";
	
	private String loggeduser;
	
	public Object getNoteEntryDate() {
		return noteEntryDate;
	}
	public void setNoteEntryDate(Object noteEntryDate) {
		this.noteEntryDate = noteEntryDate;
	}
	
	public Object getBirthWeight() {
		return birthWeight;
	}
	public void setBirthWeight(Object birthWeight) {
		this.birthWeight = birthWeight;
	}
	public Object getPreviousWeight() {
		return previousWeight;
	}
	public void setPreviousWeight(Object previousWeight) {
		this.previousWeight = previousWeight;
	}
	public Object getPresentWeight() {
		return presentWeight;
	}
	public void setPresentWeight(Object presentWeight) {
		this.presentWeight = presentWeight;
	}
	public Object getWorkingWeight() {
		return workingWeight;
	}
	public void setWorkingWeight(Object workingWeight) {
		this.workingWeight = workingWeight;
	}
	public Object getWeightChangeFromPrevious() {
		return weightChangeFromPrevious;
	}
	public void setWeightChangeFromPrevious(Object weightChangeFromPrevious) {
		this.weightChangeFromPrevious = weightChangeFromPrevious;
	}
	public Object getWeightChangeFromBirth() {
		return weightChangeFromBirth;
	}
	public void setWeightChangeFromBirth(Object weightChangeFromBirth) {
		this.weightChangeFromBirth = weightChangeFromBirth;
	}
	public Object getBirthHeight() {
		return birthHeight;
	}
	public void setBirthHeight(Object birthHeight) {
		this.birthHeight = birthHeight;
	}
	public Object getPresentHeight() {
		return presentHeight;
	}
	public void setPresentHeight(Object presentHeight) {
		this.presentHeight = presentHeight;
	}
	public Object getPreviousHeight() {
		return previousHeight;
	}
	public void setPreviousHeight(Object previousHeight) {
		this.previousHeight = previousHeight;
	}
	public Object getBirthHeadcircum() {
		return birthHeadcircum;
	}
	public void setBirthHeadcircum(Object birthHeadcircum) {
		this.birthHeadcircum = birthHeadcircum;
	}
	public Object getPresentHeadcircum() {
		return presentHeadcircum;
	}
	public void setPresentHeadcircum(Object presentHeadcircum) {
		this.presentHeadcircum = presentHeadcircum;
	}
	public Object getPreviousHeadcircum() {
		return previousHeadcircum;
	}
	public void setPreviousHeadcircum(Object previousHeadcircum) {
		this.previousHeadcircum = previousHeadcircum;
	}
	public Object getDob() {
		return dob;
	}
	public void setDob(Object dob) {
		this.dob = dob;
	}
	
	public Object getBirthGestationdays() {
		return birthGestationdays;
	}
	public void setBirthGestationdays(Object birthGestationdays) {
		this.birthGestationdays = birthGestationdays;
	}
	public Object getBirthGestationweeks() {
		return birthGestationweeks;
	}
	public void setBirthGestationweeks(Object birthGestationweeks) {
		this.birthGestationweeks = birthGestationweeks;
	}
	public Object getDaysAfterAdmission() {
		return daysAfterAdmission;
	}
	public void setDaysAfterAdmission(Object daysAfterAdmission) {
		this.daysAfterAdmission = daysAfterAdmission;
	}
	public Object getDaysAfterBirth() {
		return daysAfterBirth;
	}
	public void setDaysAfterBirth(Object daysAfterBirth) {
		this.daysAfterBirth = daysAfterBirth;
	}
	public Object getCorrectedGestationdays() {
		return correctedGestationdays;
	}
	public void setCorrectedGestationdays(Object correctedGestationdays) {
		this.correctedGestationdays = correctedGestationdays;
	}
	public Object getCorrectedGestationweeks() {
		return correctedGestationweeks;
	}
	public void setCorrectedGestationweeks(Object correctedGestationweeks) {
		this.correctedGestationweeks = correctedGestationweeks;
	}
	public Object getSurgery() {
		return surgery;
	}
	public void setSurgery(Object surgery) {
		this.surgery = surgery;
	}
	public Object getSurgeon() {
		return surgeon;
	}
	public void setSurgeon(Object surgeon) {
		this.surgeon = surgeon;
	}
	public Object getNeonatologist() {
		return neonatologist;
	}
	public void setNeonatologist(Object neonatologist) {
		this.neonatologist = neonatologist;
	}
	public String getLoggeduser() {
		return loggeduser;
	}
	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}
	public BabyVisitsObj() {
		this.loggeduser = "";
	}
	
	
	
	
}
