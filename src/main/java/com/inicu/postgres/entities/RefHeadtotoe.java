package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ref_headtotoe database table.
 * 
 */
@Entity
@Table(name="ref_headtotoe")
@NamedQuery(name="RefHeadtotoe.findAll", query="SELECT r FROM RefHeadtotoe r")
public class RefHeadtotoe implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String htid;

	private String headtotoe;

	public RefHeadtotoe() {
	}

	public String getHtid() {
		return this.htid;
	}

	public void setHtid(String htid) {
		this.htid = htid;
	}

	public String getHeadtotoe() {
		return this.headtotoe;
	}

	public void setHeadtotoe(String headtotoe) {
		this.headtotoe = headtotoe;
	}

}