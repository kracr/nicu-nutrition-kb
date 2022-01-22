package com.inicu.models;

public class SignificantMaterialObj {
	private Object isLeakingPvChecked;
	private Object isPrematurityChecked;
	private Object isLBWChecked;
	private Object isPIHChecked;
	private Object isOligoChecked;
	public SignificantMaterialObj(){
		isLeakingPvChecked =false;
		isPrematurityChecked = false;
		isLBWChecked = false;
		isPIHChecked =false;
		isOligoChecked =false;
	}
	public Object getIsLeakingPvChecked() {
		return isLeakingPvChecked;
	}
	public void setIsLeakingPvChecked(Object isLeakingPvChecked) {
		this.isLeakingPvChecked = isLeakingPvChecked;
	}
	public Object getIsPrematurityChecked() {
		return isPrematurityChecked;
	}
	public void setIsPrematurityChecked(Object isPrematurityChecked) {
		this.isPrematurityChecked = isPrematurityChecked;
	}
	public Object getIsLBWChecked() {
		return isLBWChecked;
	}
	public void setIsLBWChecked(Object isLBWChecked) {
		this.isLBWChecked = isLBWChecked;
	}
	public Object getIsPIHChecked() {
		return isPIHChecked;
	}
	public void setIsPIHChecked(Object isPIHChecked) {
		this.isPIHChecked = isPIHChecked;
	}
	public Object getIsOligoChecked() {
		return isOligoChecked;
	}
	public void setIsOligoChecked(Object isOligoChecked) {
		this.isOligoChecked = isOligoChecked;
	}
	
}
