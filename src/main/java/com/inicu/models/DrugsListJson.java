package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.Medicines;
import com.inicu.postgres.entities.RefMedBrand;

public class DrugsListJson {

	List<Medicines> drugsList;
	ResponseMessageObject message;
	Medicines drug;
	List<RefMedBrand> brandList;
	RefMedBrand brand;
	List<KeyValueObj> unitDropDown;
	List<KeyValueObj> typeDropDown;
	List<KeyValueObj> freqDropDown;
	
	public List<KeyValueObj> getTypeDropDown() {
		return typeDropDown;
	}
	public void setTypeDropDown(List<KeyValueObj> typeDropDown) {
		this.typeDropDown = typeDropDown;
	}
	public List<KeyValueObj> getFreqDropDown() {
		return freqDropDown;
	}
	public void setFreqDropDown(List<KeyValueObj> freqDropDown) {
		this.freqDropDown = freqDropDown;
	}
	public List<KeyValueObj> getUnitDropDown() {
		return unitDropDown;
	}
	public void setUnitDropDown(List<KeyValueObj> unitDropDown) {
		this.unitDropDown = unitDropDown;
	}
	public Medicines getDrug() {
		return drug;
	}
	public void setDrug(Medicines drug) {
		this.drug = drug;
	}
	public List<Medicines> getDrugsList() {
		return drugsList;
	}
	public void setDrugsList(List<Medicines> drugsList) {
		this.drugsList = drugsList;
	}
	public ResponseMessageObject getMessage() {
		return message;
	}
	public void setMessage(ResponseMessageObject message) {
		this.message = message;
	}
	public List<RefMedBrand> getBrandList() {
		return brandList;
	}
	public void setBrandList(List<RefMedBrand> brandList) {
		this.brandList = brandList;
	}
	public RefMedBrand getBrand() {
		return brand;
	}
	public void setBrand(RefMedBrand brand) {
		this.brand = brand;
	}
	
}
