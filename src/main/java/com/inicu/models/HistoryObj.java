package com.inicu.models;

public class HistoryObj {
	private Object isGDMChecked;
	private Object isSteroidsRecievedChecked;
	private Object isRegisteredChecked;
	private Object isIllnessChecked;
	
	public HistoryObj (){
		isGDMChecked= false;
		isSteroidsRecievedChecked =false;
		isRegisteredChecked =false;
		isIllnessChecked =false;
	}
	public Object getIsGDMChecked() {
		return isGDMChecked;
	}
	public void setIsGDMChecked(Object isGDMChecked) {
		this.isGDMChecked = isGDMChecked;
	}
	public Object getIsSteroidsRecievedChecked() {
		return isSteroidsRecievedChecked;
	}
	public void setIsSteroidsRecievedChecked(Object isSteroidsRecievedChecked) {
		this.isSteroidsRecievedChecked = isSteroidsRecievedChecked;
	}
	public Object getIsRegisteredChecked() {
		return isRegisteredChecked;
	}
	public void setIsRegisteredChecked(Object isRegisteredChecked) {
		this.isRegisteredChecked = isRegisteredChecked;
	}
	public Object getIsIllnessChecked() {
		return isIllnessChecked;
	}
	public void setIsIllnessChecked(Object isIllnessChecked) {
		this.isIllnessChecked = isIllnessChecked;
	}
	
}
