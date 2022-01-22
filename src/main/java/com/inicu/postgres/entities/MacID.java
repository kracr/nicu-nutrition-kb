package com.inicu.postgres.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="macids")
@NamedQuery(name="MacID.findAll", query="SELECT d FROM DeviceDetail d")
public class MacID {
	@Id
	private String macid;
	
	public String getMacid() {
		return macid;
	}

	public void setMacid(String macid) {
		this.macid = macid;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	@Column(columnDefinition="bool")
	private Boolean available;
	
	
}
