package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.inicu.models.KeyValueObj;

/**
 * The persistent class for the baby_detail database table.
 * 
 */
@Entity
@Table(name = "medicine")
@NamedQuery(name = "Medicines.findAll", query = "SELECT m FROM Medicines m")
public class Medicines implements Serializable {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	@Id
	@GenericGenerator(name = "med_seq_id", strategy = "com.inicu.postgres.utility.MedicineIDGenerator", parameters = @Parameter(name = "name", value = "MED-medicine-medid"))
	@GeneratedValue(generator = "med_seq_id")
	private String medid;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String userid;

	private String medname;

	private String brand;

	@Column(columnDefinition = "float4")
	private Float suspensiondose;

	private String suspensiondoseunit;

	private String frequency;

	@Column(columnDefinition = "bool")
	private Boolean isactive;

	private String description;

	@Column(columnDefinition = "float4")
	private Float dosemultiplier;

	private String dosemultiplierunit;

	private String medicationtype;
	
	private String storage;
	
	private String monitoring;
	
	private String interactions;
	
	private String effects;
	
	private String assessment;

	@Transient
	private boolean isEdit;

	@Transient
	private KeyValueObj typeKeyVal;

	@Transient
	private KeyValueObj freqKeyVal;

	@Transient
	private boolean isUnitMG;

	@Transient
	private String doseEachDay;

	@Transient
	private String mentionOthers;
	
	@Column(columnDefinition = "float4", name = "maximum_conc_iv")
	private Float maximumConcIV;
	
	@Column(columnDefinition = "float4", name = "maximum_conc_im")
	private Float maximumConcIM;
	
	@Column(columnDefinition = "float4", name = "maximum_rate")
	private Float maximumRate;

	public String getMentionOthers() {
		return mentionOthers;
	}

	public void setMentionOthers(String mentionOthers) {
		this.mentionOthers = mentionOthers;
	}

	@Column(columnDefinition = "bool")
	private Boolean formulaperdose;

	private String othertype;

	@Transient
	private String eachDose;

	@Transient
	private String medicineTypeStr;

	private String information;

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public String getMedicineTypeStr() {
		return medicineTypeStr;
	}

	public void setMedicineTypeStr(String medicineTypeStr) {
		this.medicineTypeStr = medicineTypeStr;
	}

	public String getEachDose() {
		return eachDose;
	}

	public void setEachDose(String eachDose) {
		this.eachDose = eachDose;
	}

	public String getOthertype() {
		return othertype;
	}

	public void setOthertype(String othertype) {
		this.othertype = othertype;
	}

	public Boolean isFormulaperdose() {
		return formulaperdose;
	}

	public void setFormulaperdose(Boolean formulaperdose) {
		this.formulaperdose = formulaperdose;
	}

	public Medicines() {
		this.isUnitMG = true;
		this.doseEachDay = "true";
		this.suspensiondoseunit = "Mg";
	}

	public boolean isUnitMG() {
		return isUnitMG;
	}

	public void setUnitMG(boolean isUnitMG) {
		this.isUnitMG = isUnitMG;
	}

	public String isDoseEachDay() {
		return doseEachDay;
	}

	public void setDoseEachDay(String isDoseEachDay) {
		this.doseEachDay = isDoseEachDay;
	}

	public KeyValueObj getTypeKeyVal() {
		return typeKeyVal;
	}

	public void setTypeKeyVal(KeyValueObj typeKeyVal) {
		this.typeKeyVal = typeKeyVal;
	}

	public KeyValueObj getFreqKeyVal() {
		return freqKeyVal;
	}

	public void setFreqKeyVal(KeyValueObj freqKeyVal) {
		this.freqKeyVal = freqKeyVal;
	}

	public boolean isEdit() {
		return isEdit;
	}

	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}

	public String getMedid() {
		return medid;
	}

	public void setMedid(String medid) {
		this.medid = medid;
	}

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public Timestamp getModificationtime() {
		return modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getMedname() {
		return medname;
	}

	public void setMedname(String medname) {
		this.medname = medname;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Float getSuspensiondose() {
		return suspensiondose;
	}

	public void setSuspensiondose(Float suspensiondose) {
		this.suspensiondose = suspensiondose;
	}

	public String getSuspensiondoseunit() {
		return suspensiondoseunit;
	}

	public void setSuspensiondoseunit(String suspensiondoseunit) {
		this.suspensiondoseunit = suspensiondoseunit;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float getDosemultiplier() {
		return dosemultiplier;
	}

	public void setDosemultiplier(Float dosemultiplier) {
		this.dosemultiplier = dosemultiplier;
	}

	public String getDosemultiplierunit() {
		return dosemultiplierunit;
	}

	public void setDosemultiplierunit(String dosemultiplierunit) {
		this.dosemultiplierunit = dosemultiplierunit;
	}

	public String getMedicationtype() {
		return medicationtype;
	}

	public void setMedicationtype(String medicationtype) {
		this.medicationtype = medicationtype;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	public String getMonitoring() {
		return monitoring;
	}

	public void setMonitoring(String monitoring) {
		this.monitoring = monitoring;
	}

	public String getInteractions() {
		return interactions;
	}

	public void setInteractions(String interactions) {
		this.interactions = interactions;
	}

	public String getEffects() {
		return effects;
	}

	public void setEffects(String effects) {
		this.effects = effects;
	}

	public Float getMaximumConcIV() {
		return maximumConcIV;
	}

	public void setMaximumConcIV(Float maximumConcIV) {
		this.maximumConcIV = maximumConcIV;
	}

	public Float getMaximumConcIM() {
		return maximumConcIM;
	}

	public void setMaximumConcIM(Float maximumConcIM) {
		this.maximumConcIM = maximumConcIM;
	}

	public Float getMaximumRate() {
		return maximumRate;
	}

	public void setMaximumRate(Float maximumRate) {
		this.maximumRate = maximumRate;
	}

	public Boolean getFormulaperdose() {
		return formulaperdose;
	}

	public String getAssessment() {
		return assessment;
	}

	public void setAssessment(String assessment) {
		this.assessment = assessment;
	}

}
