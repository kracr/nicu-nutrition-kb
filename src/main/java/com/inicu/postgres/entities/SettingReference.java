package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the setting_reference database table.
 * 
 */
@Entity
@Table(name="setting_reference")
@NamedQuery(name="SettingReference.findAll", query="SELECT s FROM SettingReference s")
public class SettingReference implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long settingid;

	private String category;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String refid;

	private String refvalue;

	public SettingReference() {
	}

	public Long getSettingid() {
		return this.settingid;
	}

	public void setSettingid(Long settingid) {
		this.settingid = settingid;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	public String getRefid() {
		return this.refid;
	}

	public void setRefid(String refid) {
		this.refid = refid;
	}

	public String getRefvalue() {
		return this.refvalue;
	}

	public void setRefvalue(String refvalue) {
		this.refvalue = refvalue;
	}

}