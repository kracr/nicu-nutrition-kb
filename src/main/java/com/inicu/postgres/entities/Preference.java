package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the preference database table.
 * 
 */
@Entity
@NamedQuery(name="Preference.findAll", query="SELECT p FROM Preference p")
public class Preference implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String preference;

	private String prefid;

	public Preference() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getPreference() {
		return this.preference;
	}

	public void setPreference(String preference) {
		this.preference = preference;
	}

	public String getPrefid() {
		return this.prefid;
	}

	public void setPrefid(String prefid) {
		this.prefid = prefid;
	}

}