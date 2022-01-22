package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="vw_assesment_discharge_final")
@NamedQuery(name="VwAssessmentDischargeFinal.findAll", query="SELECT v FROM VwAssessmentDischargeFinal v")
public class VwAssessmentDischargeFinal implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	private String uniquekey;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private Long id;
	
	private Timestamp creationtime;
	
	private String uhid;
	
	private String category;
	
	private String episodeid;

	public String getUniquekey() {
		return uniquekey;
	}

	public void setUniquekey(String uniquekey) {
		this.uniquekey = uniquekey;
	}

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getEpisodeid() {
		return episodeid;
	}

	public void setEpisodeid(String episodeid) {
		this.episodeid = episodeid;
	}

	

}
