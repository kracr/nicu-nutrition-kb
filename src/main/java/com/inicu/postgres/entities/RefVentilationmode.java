package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ref_ventilationmode database table.
 * 
 */
@Entity
@Table(name="ref_ventilationmode")
@NamedQuery(name="RefVentilationmode.findAll", query="SELECT r FROM RefVentilationmode r")
public class RefVentilationmode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String ventmodeid;

	private String description;

	@Column(name="ventilation_type")
	private String ventilationType;

	private String ventilationmode;
	
	private String status;
	

	public RefVentilationmode() {
	}

	public String getVentmodeid() {
		return this.ventmodeid;
	}

	public void setVentmodeid(String ventmodeid) {
		this.ventmodeid = ventmodeid;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVentilationType() {
		return this.ventilationType;
	}

	public void setVentilationType(String ventilationType) {
		this.ventilationType = ventilationType;
	}

	public String getVentilationmode() {
		return this.ventilationmode;
	}

	public void setVentilationmode(String ventilationmode) {
		this.ventilationmode = ventilationmode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	

}