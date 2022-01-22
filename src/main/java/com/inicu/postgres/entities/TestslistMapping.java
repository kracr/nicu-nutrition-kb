package com.inicu.postgres.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="tests_list_mapping")
@NamedQuery(name="TestslistMapping.findAll", query="SELECT t FROM TestslistMapping t")
public class TestslistMapping {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long tests_list_mapping_id;
	
	@Column(columnDefinition = "timestamp with time zone")
	private Timestamp creationtime;
	
	@Column(columnDefinition = "timestamp with time zone")
	private Timestamp modificationtime;

	private String loggeduser;
	
	private String vendortestid;
	
	private String inicutestid;
	
	private String assessmentcategory;
	
	@Column(columnDefinition = "bool")
	private Boolean isactive;

	public Long getTests_list_mapping_id() {
		return tests_list_mapping_id;
	}

	public void setTests_list_mapping_id(Long tests_list_mapping_id) {
		this.tests_list_mapping_id = tests_list_mapping_id;
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

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public String getVendortestid() {
		return vendortestid;
	}

	public void setVendortestid(String vendortestid) {
		this.vendortestid = vendortestid;
	}

	public String getInicutestid() {
		return inicutestid;
	}

	public void setInicutestid(String inicutestid) {
		this.inicutestid = inicutestid;
	}

	public String getAssessmentcategory() {
		return assessmentcategory;
	}

	public void setAssessmentcategory(String assessmentcategory) {
		this.assessmentcategory = assessmentcategory;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}
	
}
