package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "nurse_tasks")
@NamedQuery(name = "NurseTasks.findAll", query = "SELECT n FROM NurseTasks n")
public class NurseTasks implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "nurse_tasks_id")
	private Long nursetasksid;

	private Timestamp creationtime;
	
	private Timestamp modificationtime;

	@Temporal(TemporalType.DATE)
	private Date visitdate;
	
	private String uhid;
	
	private String name;
	
	private String loggedUser;
	
	private String vitals;
	
	private String ventilator;
	
	private String investigations;
	
	private String intake;
	
	private String output;
	
	private String medications;
	
	@Column(name = "preparation_medication")
	private String preparationMedication;
	
	@Column(name = "preparation_nutrition")
	private String preparationNutrition;
	
	@Column(name = "is_selected", columnDefinition = "bool")
	private Boolean isSelected;
	
	@Column(name = "status_vitals", columnDefinition = "bool")
	private Boolean statusVitals;
	
	@Column(name = "status_ventilator", columnDefinition = "bool")
	private Boolean statusVentilator;
	
	@Column(name = "status_medications", columnDefinition = "bool")
	private Boolean statusMedications;
	
	@Column(name = "status_investigations", columnDefinition = "bool")
	private Boolean statusInvestigations;
	
	@Column(name = "status_intake", columnDefinition = "bool")
	private Boolean statusIntake;
	
	@Column(name = "status_output", columnDefinition = "bool")
	private Boolean statusOutput;
	
	@Column(name = "status_preparation_medication", columnDefinition = "bool")
	private Boolean statusPreparationMedication;

	@Column(name = "status_preparation_nutrition", columnDefinition = "bool")
	private Boolean statusPreparationNutrition;


	@Column(name = "nurse_comments")
	private String nurseComment;

	public String getNurseComment() {
		return nurseComment;
	}

	public void setNurseComment(String nurseComment) {
		this.nurseComment = nurseComment;
	}

	public Long getNursetasksid() {
		return nursetasksid;
	}

	public void setNursetasksid(Long nursetasksid) {
		this.nursetasksid = nursetasksid;
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

	public Date getVisitdate() {
		return visitdate;
	}

	public void setVisitdate(Date visitdate) {
		this.visitdate = visitdate;
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(String loggedUser) {
		this.loggedUser = loggedUser;
	}

	public String getVitals() {
		return vitals;
	}

	public void setVitals(String vitals) {
		this.vitals = vitals;
	}

	public String getVentilator() {
		return ventilator;
	}

	public void setVentilator(String ventilator) {
		this.ventilator = ventilator;
	}

	public String getInvestigations() {
		return investigations;
	}

	public void setInvestigations(String investigations) {
		this.investigations = investigations;
	}

	public String getIntake() {
		return intake;
	}

	public void setIntake(String intake) {
		this.intake = intake;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}
	
	public String getPreparationMedication() {
		return preparationMedication;
	}

	public void setPreparationMedication(String preparationMedication) {
		this.preparationMedication = preparationMedication;
	}

	public String getPreparationNutrition() {
		return preparationNutrition;
	}

	public void setPreparationNutrition(String preparationNutrition) {
		this.preparationNutrition = preparationNutrition;
	}

	public Boolean getStatusInvestigations() {
		return statusInvestigations;
	}

	public void setStatusInvestigations(Boolean statusInvestigations) {
		this.statusInvestigations = statusInvestigations;
	}

	public Boolean getStatusIntake() {
		return statusIntake;
	}

	public void setStatusIntake(Boolean statusIntake) {
		this.statusIntake = statusIntake;
	}

	public Boolean getStatusOutput() {
		return statusOutput;
	}

	public void setStatusOutput(Boolean statusOutput) {
		this.statusOutput = statusOutput;
	}

	public String getMedications() {
		return medications;
	}

	public void setMedications(String medications) {
		this.medications = medications;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	public Boolean getStatusVitals() {
		return statusVitals;
	}

	public void setStatusVitals(Boolean statusVitals) {
		this.statusVitals = statusVitals;
	}

	public Boolean getStatusVentilator() {
		return statusVentilator;
	}

	public void setStatusVentilator(Boolean statusVentilator) {
		this.statusVentilator = statusVentilator;
	}

	public Boolean getStatusMedications() {
		return statusMedications;
	}

	public void setStatusMedications(Boolean statusMedications) {
		this.statusMedications = statusMedications;
	}

	public Boolean getStatusPreparationMedication() {
		return statusPreparationMedication;
	}

	public void setStatusPreparationMedication(Boolean statusPreparationMedication) {
		this.statusPreparationMedication = statusPreparationMedication;
	}

	public Boolean getStatusPreparationNutrition() {
		return statusPreparationNutrition;
	}

	public void setStatusPreparationNutrition(Boolean statusPreparationNutrition) {
		this.statusPreparationNutrition = statusPreparationNutrition;
	}
}