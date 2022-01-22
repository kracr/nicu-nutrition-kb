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
import javax.persistence.Transient;

@Entity
@Table(name = "doctor_tasks")
@NamedQuery(name = "DoctorTasks.findAll", query = "SELECT n FROM DoctorTasks n")
public class DoctorTasks implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "doctor_tasks_id")
	private Long doctortasksid;

	private Timestamp creationtime;
	
	private Timestamp modificationtime;

	@Temporal(TemporalType.DATE)
	private Date visitdate;
	
	private String uhid;
	
	private String name;
	
	private String loggedUser;
	
	private String investigations;
	
	private String addMedicine;
	
	private String stopMedicine;
	
	private String comments;
	
	private String reports;
	
	private String assessments;
	
	private String feeds;
	
	@Column(name = "is_selected", columnDefinition = "bool")
	private Boolean isSelected;
	
	@Column(name = "status_investigation", columnDefinition = "bool")
	private Boolean statusInvestigation;
	
	@Column(name = "status_add_medicine", columnDefinition = "bool")
	private Boolean statusAddMedicine;
	
	@Column(name = "status_stop_medicine", columnDefinition = "bool")
	private Boolean statusStopMedicine;
	
	@Column(name = "status_comments", columnDefinition = "bool")
	private Boolean statusComments;
	
	@Column(name = "status_reports", columnDefinition = "bool")
	private Boolean statusReports;
	
	@Column(name = "status_assessments", columnDefinition = "bool")
	private Boolean statusAssessments;
	
	@Column(name = "status_feeds", columnDefinition = "bool")
	private Boolean statusFeeds;

	public Long getDoctortasksid() {
		return doctortasksid;
	}

	public void setDoctortasksid(Long doctortasksid) {
		this.doctortasksid = doctortasksid;
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

	public String getInvestigations() {
		return investigations;
	}

	public void setInvestigations(String investigations) {
		this.investigations = investigations;
	}

	public String getAddMedicine() {
		return addMedicine;
	}

	public void setAddMedicine(String addMedicine) {
		this.addMedicine = addMedicine;
	}

	public String getStopMedicine() {
		return stopMedicine;
	}

	public void setStopMedicine(String stopMedicine) {
		this.stopMedicine = stopMedicine;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getReports() {
		return reports;
	}

	public void setReports(String reports) {
		this.reports = reports;
	}

	public String getAssessments() {
		return assessments;
	}

	public void setAssessments(String assessments) {
		this.assessments = assessments;
	}

	public String getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(String loggedUser) {
		this.loggedUser = loggedUser;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	public Boolean getStatusInvestigation() {
		return statusInvestigation;
	}

	public void setStatusInvestigation(Boolean statusInvestigation) {
		this.statusInvestigation = statusInvestigation;
	}

	public Boolean getStatusAddMedicine() {
		return statusAddMedicine;
	}

	public void setStatusAddMedicine(Boolean statusAddMedicine) {
		this.statusAddMedicine = statusAddMedicine;
	}

	public Boolean getStatusStopMedicine() {
		return statusStopMedicine;
	}

	public void setStatusStopMedicine(Boolean statusStopMedicine) {
		this.statusStopMedicine = statusStopMedicine;
	}

	public Boolean getStatusComments() {
		return statusComments;
	}

	public void setStatusComments(Boolean statusComments) {
		this.statusComments = statusComments;
	}

	public Boolean getStatusReports() {
		return statusReports;
	}

	public void setStatusReports(Boolean statusReports) {
		this.statusReports = statusReports;
	}

	public Boolean getStatusAssessments() {
		return statusAssessments;
	}

	public void setStatusAssessments(Boolean statusAssessments) {
		this.statusAssessments = statusAssessments;
	}

	public String getFeeds() {
		return feeds;
	}

	public void setFeeds(String feeds) {
		this.feeds = feeds;
	}

	public Boolean getStatusFeeds() {
		return statusFeeds;
	}

	public void setStatusFeeds(Boolean statusFeeds) {
		this.statusFeeds = statusFeeds;
	}

}
