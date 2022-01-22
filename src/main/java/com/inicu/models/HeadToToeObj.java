package com.inicu.models;

public class HeadToToeObj {
	private Object isHeadChecked;
	private Object isPalateChecked;
	private Object isHeartChecked;
	private Object isFingerAndToesChecked;
	private Object isNGPastChecked;
	public HeadToToeObj(){
		isHeadChecked =false;
		isPalateChecked =false;
		isHeartChecked=false;
		isFingerAndToesChecked =false;
		isNGPastChecked =false;
	}
	public Object getIsHeadChecked() {
		return isHeadChecked;
	}
	public void setIsHeadChecked(Object isHeadChecked) {
		this.isHeadChecked = isHeadChecked;
	}
	public Object getIsPalateChecked() {
		return isPalateChecked;
	}
	public void setIsPalateChecked(Object isPalateChecked) {
		this.isPalateChecked = isPalateChecked;
	}
	public Object getIsHeartChecked() {
		return isHeartChecked;
	}
	public void setIsHeartChecked(Object isHeartChecked) {
		this.isHeartChecked = isHeartChecked;
	}
	public Object getIsFingerAndToesChecked() {
		return isFingerAndToesChecked;
	}
	public void setIsFingerAndToesChecked(Object isFingerAndToesChecked) {
		this.isFingerAndToesChecked = isFingerAndToesChecked;
	}
	public Object getIsNGPastChecked() {
		return isNGPastChecked;
	}
	public void setIsNGPastChecked(Object isNGPastChecked) {
		this.isNGPastChecked = isNGPastChecked;
	}
	
}
