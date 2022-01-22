package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ref_significantmaterial database table.
 * 
 */
@Entity
@Table(name="ref_significantmaterial")
@NamedQuery(name="RefSignificantmaterial.findAll", query="SELECT r FROM RefSignificantmaterial r")
public class RefSignificantmaterial implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String smid;

	private String material;

	public RefSignificantmaterial() {
	}

	public String getSmid() {
		return this.smid;
	}

	public void setSmid(String smid) {
		this.smid = smid;
	}

	public String getMaterial() {
		return this.material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

}