package com.inicu.postgres.entities;

import com.inicu.postgres.utility.BasicUtils;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;

/**
 * The persistent class for the vw_respiratory_usage_final database table.
 * 
 */
@Entity
@Table(name = "vw_respiratory_usage_final")
@NamedQuery(name = "VwRespiratoryUsageFinal.findAll", query = "SELECT v FROM VwRespiratoryUsageFinal v")
public class VwRespiratoryUsageFinal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Timestamp creationtime;

	@Column(columnDefinition = "float8")
	private Float differenceinhrs;

	private Timestamp endtime;

	private String eventid;

	private String eventname;

	@Column(columnDefinition = "bool")
	private Boolean isactive;

	@Column(name = "rs_vent_type")
	private String rsVentType;

	private String uhid;

	@Transient
	private List<String> events;

	@Column(columnDefinition = "float8")
	private Float differenceinmins;

	public VwRespiratoryUsageFinal(VwRespiratoryUsageFinal vwRespiratoryUsageFinal ){
		this.creationtime = vwRespiratoryUsageFinal.getCreationtime();
		this.differenceinhrs = Float.valueOf(BasicUtils.CalCulateHourDiff(vwRespiratoryUsageFinal.getCreationtime(),vwRespiratoryUsageFinal.getEndtime()));
		this.endtime = vwRespiratoryUsageFinal.getEndtime();
		this.eventid = vwRespiratoryUsageFinal.getEventid();
		this.eventname = vwRespiratoryUsageFinal.getEventname();
		this.isactive = vwRespiratoryUsageFinal.getIsactive();
		this.rsVentType = vwRespiratoryUsageFinal.getRsVentType();
		this.uhid = vwRespiratoryUsageFinal.getUhid();

	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}
	
	public List<String> getEvents() {
		return events;
	}

	public void setEvents(List<String> events) {
		this.events = events;
	}

	public VwRespiratoryUsageFinal() {
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public Float getDifferenceinhrs() {
		return this.differenceinhrs;
	}

	public void setDifferenceinhrs(Float differenceinhrs) {
		this.differenceinhrs = differenceinhrs;
	}

	public Timestamp getEndtime() {
		return this.endtime;
	}

	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}

	public String getEventid() {
		return this.eventid;
	}

	public void setEventid(String eventid) {
		this.eventid = eventid;
	}

	public String getEventname() {
		return this.eventname;
	}

	public void setEventname(String eventname) {
		this.eventname = eventname;
	}

	public Boolean getIsactive() {
		return this.isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	public String getRsVentType() {
		return this.rsVentType;
	}

	public void setRsVentType(String rsVentType) {
		this.rsVentType = rsVentType;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public Float getDifferenceinmins() {
		return differenceinmins;
	}

	public void setDifferenceinmins(Float differenceinmins) {
		this.differenceinmins = differenceinmins;
	}
}