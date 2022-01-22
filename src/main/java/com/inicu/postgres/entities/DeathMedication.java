package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="death_medication")
@NamedQuery(name="DeathMedication.findAll", query="SELECT s FROM DeathMedication s")
public class DeathMedication implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="death_medication_id")
	private Long deathMedicationId;
	
	private String uhid;
	
	private String medicine;
	
	@Column(columnDefinition="float4")
	private Float dose;
	
	private String unit;
	
	private String frequency;

	public Long getDeathMedicationId() {
		return deathMedicationId;
	}

	public void setDeathMedicationId(Long deathMedicationId) {
		this.deathMedicationId = deathMedicationId;
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getMedicine() {
		return medicine;
	}

	public void setMedicine(String medicine) {
		this.medicine = medicine;
	}

	public Float getDose() {
		return dose;
	}

	public void setDose(Float dose) {
		this.dose = dose;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	
	
	


}
