package com.inicu.models;

public class GenericScoreSignJSON {

	int index;
	String clinicalSign;
	Long dbRefId;
	boolean selected = false;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getClinicalSign() {
		return clinicalSign;
	}

	public void setClinicalSign(String clinicalSign) {
		this.clinicalSign = clinicalSign;
	}

	public Long getDbRefId() {
		return dbRefId;
	}

	public void setDbRefId(Long dbRefId) {
		this.dbRefId = dbRefId;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public String toString() {
		return "GenericScoreSignJSON [index=" + index + ", clinicalSign=" + clinicalSign + ", dbRefId=" + dbRefId
				+ ", selected=" + selected + "]";
	}

}
