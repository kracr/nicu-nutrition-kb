package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the vw_notification_final database table.
 * 
 */
@Entity
@Table(name = "vw_notification_final")
@NamedQuery(name = "VwNotificationFinal.findAll", query = "SELECT v FROM VwNotificationFinal v")
public class VwNotificationFinal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private double differenceinhrs;

	@Id
	private String eventstatus;

	@Id
	private String eventtype;

	@Id
	private Timestamp eventtime;

	@Id
	private String uhid;

	public VwNotificationFinal() {
	}

	public double getDifferenceinhrs() {
		return this.differenceinhrs;
	}

	public void setDifferenceinhrs(double differenceinhrs) {
		this.differenceinhrs = differenceinhrs;
	}

	public String getEventstatus() {
		return this.eventstatus;
	}

	public void setEventstatus(String eventstatus) {
		this.eventstatus = eventstatus;
	}

	public Timestamp getEventtime() {
		return this.eventtime;
	}

	public void setEventtime(Timestamp eventtime) {
		this.eventtime = eventtime;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getEventtype() {
		return eventtype;
	}

	public void setEventtype(String eventtype) {
		this.eventtype = eventtype;
	}

}