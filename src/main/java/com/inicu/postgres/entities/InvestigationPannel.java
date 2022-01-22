package com.inicu.postgres.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the investigation_pannel database table.
 * 
 */
@Entity
@Table(name="investigation_pannel")
@NamedQuery(name="InvestigationPannel.findAll", query="SELECT i FROM InvestigationPannel i")
public class InvestigationPannel {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="investigationpannelid")
	private Long investigationPannelId;

	private Timestamp creationtime;

	private Timestamp modificationtime;
	
	@Column(name="loggeduser")
	private String loggedUser;

	@Column(name="branchname")
	private String branchName;

	
	@Column(name="pannel_name")
	private String pannelName;

	@Column(name="test_id")
	private String testId;

	@Column(name="test_name")
	private String testName;

	public Long getInvestigationPannelId() {
		return investigationPannelId;
	}

	public void setInvestigationPannelId(Long investigationPannelId) {
		this.investigationPannelId = investigationPannelId;
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

	public String getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(String loggedUser) {
		this.loggedUser = loggedUser;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getPannelName() {
		return pannelName;
	}

	public void setPannelName(String pannelName) {
		this.pannelName = pannelName;
	}

	public String getTestId() {
		return testId;
	}

	public void setTestId(String testId) {
		this.testId = testId;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}
}
