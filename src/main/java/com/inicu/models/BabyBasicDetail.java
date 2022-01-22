package com.inicu.models;

import com.inicu.postgres.utility.BasicUtils;

public class BabyBasicDetail {

	// UHID
	private String babydetailid;
	private String babyName;
	private String babyNumber;
	private String babyType;

	// Other extra parameters
	private String nicuRoom;
	private String bedNumber;
	private boolean selected;
	private boolean display;

	public BabyBasicDetail() {
	}

	public BabyBasicDetail(Object[] babyDetailArray) {
		this.babydetailid = BasicUtils.toStringSafe(babyDetailArray[0]);
		this.babyName = BasicUtils.toStringSafe(babyDetailArray[1]);
		this.babyNumber = BasicUtils.toStringSafe(babyDetailArray[2]);
		this.babyType = BasicUtils.toStringSafe(babyDetailArray[3]);
	}

	public String getBabydetailid() {
		return babydetailid;
	}

	public void setBabydetailid(String babydetailid) {
		this.babydetailid = babydetailid;
	}

	public String getBabyName() {
		return babyName;
	}

	public void setBabyName(String babyName) {
		this.babyName = babyName;
	}


	public String getBabyNumber() {
		return babyNumber;
	}


	public void setBabyNumber(String babyNumber) {
		this.babyNumber = babyNumber;
	}


	public String getBabyType() {
		return babyType;
	}


	public void setBabyType(String babyType) {
		this.babyType = babyType;
	}

	public String getNicuRoom() {
		return nicuRoom;
	}

	public void setNicuRoom(String nicuRoom) {
		this.nicuRoom = nicuRoom;
	}

	public String getBedNumber() {
		return bedNumber;
	}

	public void setBedNumber(String bedNumber) {
		this.bedNumber = bedNumber;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isDisplay() {
		return display;
	}

	public void setDisplay(boolean display) {
		this.display = display;
	}
}
