package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


/**
 * The persistent class for the discharge_infection database table.
 * 
 */
@Entity
@Table(name="discharge_infection")
@NamedQuery(name="DischargeInfection.findAll", query="SELECT d FROM DischargeInfection d")
public class DischargeInfection implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private Long infectioninicuid;

	private String antibiotics;

	

	@Column(name="culture_report")
	private String cultureReport;

	private String duration;

	@Temporal(TemporalType.DATE)
	@Column(name="infection_date")
	private Date infectionDate;

	private String inotropes;
	
	@Transient
	private Map<String, Boolean> inotropesObj = new HashMap<String, Boolean>(){
		{
			put("Dopamine",false);
			put("Dobutamine",false);
			put("Adrenaline",false);
			put("Noradrenaline",false);
			put("Milrinone",false);
		}
	};


	@Column(columnDefinition="bool")
	private Boolean meningitis;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String organism;

	private String sensitivity;

	@Column(name="septic_shock")
	private String septicShock;

	private String uhid;

	public DischargeInfection() {
	}

	public Long getInfectioninicuid() {
		return this.infectioninicuid;
	}

	public void setInfectioninicuid(Long infectioninicuid) {
		this.infectioninicuid = infectioninicuid;
	}

	public String getAntibiotics() {
		return this.antibiotics;
	}

	public void setAntibiotics(String antibiotics) {
		this.antibiotics = antibiotics;
	}

	

	

	public String getCultureReport() {
		return cultureReport;
	}

	public void setCultureReport(String cultureReport) {
		this.cultureReport = cultureReport;
	}

	public String getDuration() {
		return this.duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	

	public Date getInfectionDate() {
		return infectionDate;
	}

	public void setInfectionDate(Date infectionDate) {
		this.infectionDate = infectionDate;
	}

	public String getInotropes() {
		return this.inotropes;
	}

	public void setInotropes(String inotropes) {
		this.inotropes = inotropes;
	}

	

	
	public Boolean getMeningitis() {
		return meningitis;
	}

	public void setMeningitis(Boolean meningitis) {
		this.meningitis = meningitis;
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

	public String getOrganism() {
		return this.organism;
	}

	public void setOrganism(String organism) {
		this.organism = organism;
	}

	public String getSensitivity() {
		return this.sensitivity;
	}

	public void setSensitivity(String sensitivity) {
		this.sensitivity = sensitivity;
	}

	public String getSepticShock() {
		return this.septicShock;
	}

	public void setSepticShock(String septicShock) {
		this.septicShock = septicShock;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public Map<String, Boolean> getInotropesObj() {
		return inotropesObj;
	}

	public void setInotropesObj(Map<String, Boolean> inotropesObj) {
		this.inotropesObj = inotropesObj;
	}

	
	
}