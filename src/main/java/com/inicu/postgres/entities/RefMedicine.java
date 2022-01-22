package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the ref_medicine database table.
 * 
 */
@Entity
@Table(name="ref_medicine")
@NamedQuery(name="RefMedicine.findAll", query="SELECT r FROM RefMedicine r")
public class RefMedicine implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long refmedicineid;
	
	private String medid;

	@Column(name = "lower_frequency")
	private String lowerFrequency;

	@Column(name = "upper_frequency")
	private String upperFrequency;
	
	@Column(columnDefinition = "Float4", name = "lower_dose")
	private Float lowerDose;
	
	@Column(columnDefinition = "Float4", name = "upper_dose")
	private Float upperDose;

	@Column(name = "lower_gestation")
	private Integer lowerGestation;
	
	@Column(name = "upper_gestation")
	private Integer upperGestation;

	@Column(name = "lower_dol")
	private Integer lowerDol;
	
	@Column(name = "upper_dol")
	private Integer upperDol;

	@Column(columnDefinition="bool")
	private Boolean isactive;

	private String medname;
	
	@Column(columnDefinition = "bool")
	private Boolean bolus;
	
	@Column(name = "dose_unit")
	private String doseUnit;
	
	@Column(name = "route")
	private String route;
	
	@Column(name = "mode")
	private String mode;
	
	@Column(name = "infusion_time")
	private Integer infusionTime;
	
	@Column(name = "upper_infusion_time")
	private Integer upperInfusionTime;
	
	@Column(name = "infusion_volume")
	private Integer infusionVolume;
	
	@Column(name = "diluent_type")
	private String diluentType;
	
	@Column(columnDefinition = "Float4",name = "overfill_volume")
	private Float overfillVolume;
	
	@Column(columnDefinition = "Float4", name = "lower_weight")
	private Float lowerWeight;
	
	@Column(columnDefinition = "Float4", name = "upper_weight")
	private Float upperWeight;

	private String indication;
	
	private String perday;
	

	public RefMedicine() {
	}

	public String getMedid() {
		return this.medid;
	}

	public void setMedid(String medid) {
		this.medid = medid;
	}

	public Boolean getIsactive() {
		return this.isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	public String getMedname() {
		return this.medname;
	}

	public void setMedname(String medname) {
		this.medname = medname;
	}

	public Long getRefmedicineid() {
		return refmedicineid;
	}

	public void setRefmedicineid(Long refmedicineid) {
		this.refmedicineid = refmedicineid;
	}

	public String getLowerFrequency() {
		return lowerFrequency;
	}

	public void setLowerFrequency(String lowerFrequency) {
		this.lowerFrequency = lowerFrequency;
	}

	public String getUpperFrequency() {
		return upperFrequency;
	}

	public void setUpperFrequency(String upperFrequency) {
		this.upperFrequency = upperFrequency;
	}

	public Float getLowerDose() {
		return lowerDose;
	}

	public void setLowerDose(Float lowerDose) {
		this.lowerDose = lowerDose;
	}

	public Float getUpperDose() {
		return upperDose;
	}

	public void setUpperDose(Float upperDose) {
		this.upperDose = upperDose;
	}

	public Integer getLowerGestation() {
		return lowerGestation;
	}

	public void setLowerGestation(Integer lowerGestation) {
		this.lowerGestation = lowerGestation;
	}

	public Integer getUpperGestation() {
		return upperGestation;
	}

	public void setUpperGestation(Integer upperGestation) {
		this.upperGestation = upperGestation;
	}

	public Integer getLowerDol() {
		return lowerDol;
	}

	public void setLowerDol(Integer lowerDol) {
		this.lowerDol = lowerDol;
	}

	public Integer getUpperDol() {
		return upperDol;
	}

	public void setUpperDol(Integer upperDol) {
		this.upperDol = upperDol;
	}

	public Boolean isBolus() {
		return bolus;
	}

	public void setBolus(Boolean bolus) {
		this.bolus = bolus;
	}

	public String getDoseUnit() {
		return doseUnit;
	}

	public void setDoseUnit(String doseUnit) {
		this.doseUnit = doseUnit;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Integer getInfusionTime() {
		return infusionTime;
	}

	public void setInfusionTime(Integer infusionTime) {
		this.infusionTime = infusionTime;
	}

	public Boolean getBolus() {
		return bolus;
	}

	public Integer getInfusionVolume() {
		return infusionVolume;
	}

	public void setInfusionVolume(Integer infusionVolume) {
		this.infusionVolume = infusionVolume;
	}

	public Float getLowerWeight() {
		return lowerWeight;
	}

	public void setLowerWeight(Float lowerWeight) {
		this.lowerWeight = lowerWeight;
	}

	public Float getUpperWeight() {
		return upperWeight;
	}

	public void setUpperWeight(Float upperWeight) {
		this.upperWeight = upperWeight;
	}

	public String getIndication() {
		return indication;
	}

	public void setIndication(String indication) {
		this.indication = indication;
	}

	public String getPerday() {
		return perday;
	}

	public void setPerday(String perday) {
		this.perday = perday;
	}

	public Integer getUpperInfusionTime() {
		return upperInfusionTime;
	}

	public void setUpperInfusionTime(Integer upperInfusionTime) {
		this.upperInfusionTime = upperInfusionTime;
	}

	public String getDiluentType() {
		return diluentType;
	}

	public void setDiluentType(String diluentType) {
		this.diluentType = diluentType;
	}

	public Float getOverfillVolume() {
		return overfillVolume;
	}

	public void setOverfillVolume(Float overfillVolume) {
		this.overfillVolume = overfillVolume;
	}
	
}