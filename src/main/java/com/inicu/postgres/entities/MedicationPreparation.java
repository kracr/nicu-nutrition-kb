package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the medication_preparation database table.
 * 
 */
@Entity
@Table(name = "medication_preparation")
@NamedQuery(name = "MedicationPreparation.findAll", query = "SELECT b FROM MedicationPreparation b")
public class MedicationPreparation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long medication_preparation_id;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private Long baby_presid;

	private String brand;

	private String content_type;

	@Column(columnDefinition = "Float4")
	private Float content_dose;

	private String content_dose_type;

	@Column(columnDefinition = "Float4")
	private Float med_volume;

	@Column(columnDefinition = "Float4")
	private Float med_strength;

	@Column(columnDefinition = "Float4")
	private Float reconstituted_volume;

	private String reconstituted_type;

	@Column(columnDefinition = "Float4")
	private Float final_strength;

	private String nursing_instruction;

	private String loggeduser;

	private String uhid;

	public MedicationPreparation() {
		super();
		this.content_type = "Liquid";
		this.content_dose_type = "mg";
	}

	public Long getMedication_preparation_id() {
		return medication_preparation_id;
	}

	public void setMedication_preparation_id(Long medication_preparation_id) {
		this.medication_preparation_id = medication_preparation_id;
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

	public Long getBaby_presid() {
		return baby_presid;
	}

	public void setBaby_presid(Long baby_presid) {
		this.baby_presid = baby_presid;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getContent_type() {
		return content_type;
	}

	public void setContent_type(String content_type) {
		this.content_type = content_type;
	}

	public Float getContent_dose() {
		return content_dose;
	}

	public void setContent_dose(Float content_dose) {
		this.content_dose = content_dose;
	}

	public String getContent_dose_type() {
		return content_dose_type;
	}

	public void setContent_dose_type(String content_dose_type) {
		this.content_dose_type = content_dose_type;
	}

	public Float getMed_volume() {
		return med_volume;
	}

	public void setMed_volume(Float med_volume) {
		this.med_volume = med_volume;
	}

	public Float getMed_strength() {
		return med_strength;
	}

	public void setMed_strength(Float med_strength) {
		this.med_strength = med_strength;
	}

	public Float getReconstituted_volume() {
		return reconstituted_volume;
	}

	public void setReconstituted_volume(Float reconstituted_volume) {
		this.reconstituted_volume = reconstituted_volume;
	}

	public String getReconstituted_type() {
		return reconstituted_type;
	}

	public void setReconstituted_type(String reconstituted_type) {
		this.reconstituted_type = reconstituted_type;
	}

	public Float getFinal_strength() {
		return final_strength;
	}

	public void setFinal_strength(Float final_strength) {
		this.final_strength = final_strength;
	}

	public String getNursing_instruction() {
		return nursing_instruction;
	}

	public void setNursing_instruction(String nursing_instruction) {
		this.nursing_instruction = nursing_instruction;
	}

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	@Override
	public String toString() {
		return "MedicationPreparation [medication_preparation_id=" + medication_preparation_id + ", creationtime="
				+ creationtime + ", modificationtime=" + modificationtime + ", baby_presid=" + baby_presid + ", brand="
				+ brand + ", content_type=" + content_type + ", content_dose=" + content_dose + ", content_dose_type="
				+ content_dose_type + ", med_volume=" + med_volume + ", med_strength=" + med_strength
				+ ", reconstituted_volume=" + reconstituted_volume + ", reconstituted_type=" + reconstituted_type
				+ ", final_strength=" + final_strength + ", nursing_instruction=" + nursing_instruction
				+ ", loggeduser=" + loggeduser + ", uhid=" + uhid + "]";
	}

}
