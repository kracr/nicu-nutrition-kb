package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the ref_gestation database table.
 * 
 */
@Entity
@Table(name="ref_gestation")
@NamedQuery(name="RefGestation.findAll", query="SELECT r FROM RefGestation r")
public class RefGestation implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy=GenerationType.AUTO)
	private Integer gesid;

	private Integer gestation;

	public Integer getGesid() {
		return this.gesid;
	}
	public void setGesid(Integer gesid) {
		this.gesid = gesid;
	}
	public Integer getGestation() {
		return this.gestation;
	}
	public void setGestation(Integer gestation) {
		this.gestation = gestation;
	}
	
}