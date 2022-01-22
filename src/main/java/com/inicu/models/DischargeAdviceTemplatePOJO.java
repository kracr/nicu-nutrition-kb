package com.inicu.models;

public class DischargeAdviceTemplatePOJO {
	private Boolean isEdit ;
	
	private Boolean isSelected;
	
	private String editedValue;
	
	private  String fixedValue;

	public Boolean getIsEdit() {
		return isEdit;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public String getEditedValue() {
		return editedValue;
	}

	public String getFixedValue() {
		return fixedValue;
	}

	public void setIsEdit(Boolean isEdit) {
		this.isEdit = isEdit;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	public void setEditedValue(String editedValue) {
		this.editedValue = editedValue;
	}

	public void setFixedValue(String fixedValue) {
		this.fixedValue = fixedValue;
	}
	
	
}
