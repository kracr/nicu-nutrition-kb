package com.inicu.postgres.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="ref_kuppuswamy_education")
@NamedQuery(name="RefKuppuswamyEducation.findAll", query="SELECT r FROM RefKuppuswamyEducation r")
public class RefKuppuswamyEducation {

	@Id
	@Column(name="id")
	private String id;
	
	@Column(name="education")
	private String education;

	@Column(name="score")
	private String score;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	
}
