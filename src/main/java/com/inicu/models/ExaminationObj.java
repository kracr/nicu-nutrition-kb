package com.inicu.models;
/**
 * 
 * @author iNICU 
 *
 */

public class ExaminationObj {
	private Object isBackChecked;
	private Object isCDHNegativeChecked;
	private Object isAnusChecked;
	private Object isCCDScreenChecked;
	
	public ExaminationObj() {
		
		this.isBackChecked = false;
		this.isCDHNegativeChecked = false;
		this.isAnusChecked =false;
		this.isCCDScreenChecked = false;
	}
	public Object getIsBackChecked() {
		return isBackChecked;
	}
	public Object getIsCCDScreenChecked() {
		return isCCDScreenChecked;
	}
	public void setIsCCDScreenChecked(Object isCCDScreenChecked) {
		this.isCCDScreenChecked = isCCDScreenChecked;
	}
	public void setIsBackChecked(Object isBackChecked) {
		this.isBackChecked = isBackChecked;
	}
	public Object getIsCDHNegativeChecked() {
		return isCDHNegativeChecked;
	}
	public void setIsCDHNegativeChecked(Object isCDHNegativeChecked) {
		this.isCDHNegativeChecked = isCDHNegativeChecked;
	}
	public Object getIsAnusChecked() {
		return isAnusChecked;
	}
	public void setIsAnusChecked(Object isAnusChecked) {
		this.isAnusChecked = isAnusChecked;
	}
	
	
}
