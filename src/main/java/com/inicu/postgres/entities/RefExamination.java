package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ref_examination database table.
 * 
 */
@Entity
@Table(name="ref_examination")
@NamedQuery(name="RefExamination.findAll", query="SELECT r FROM RefExamination r")
public class RefExamination implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String exid;

	private String examination;

	public RefExamination() {
	}

	public String getExid() {
		return this.exid;
	}

	public void setExid(String exid) {
		this.exid = exid;
	}

	public String getExamination() {
		return this.examination;
	}

	public void setExamination(String examination) {
		this.examination = examination;
	}

}