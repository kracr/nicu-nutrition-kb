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
 * The persistent class for the ref_inicu_bbox database table.
 * 
 */
@Entity
@Table(name="ref_inicu_bbox")
@NamedQuery(name="RefInicuBbox.findAll", query="SELECT r FROM RefInicuBbox r")
public class RefInicuBbox implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="bbox_id")
	private Long bboxId;

	@Column(columnDefinition="bool")
	private Boolean active;

	private String bboxname;
	
	private String branchname;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String tinyboxname;

	public RefInicuBbox() {
	}

	public Long getBboxId() {
		return this.bboxId;
	}

	public void setBboxId(Long bboxId) {
		this.bboxId = bboxId;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getBboxname() {
		return this.bboxname;
	}

	public void setBboxname(String bboxname) {
		this.bboxname = bboxname;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getBranchname() {
		return branchname;
	}

	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}

	public String getTinyboxname() {
		return tinyboxname;
	}

	public void setTinyboxname(String tinyboxname) {
		this.tinyboxname = tinyboxname;
	}
}