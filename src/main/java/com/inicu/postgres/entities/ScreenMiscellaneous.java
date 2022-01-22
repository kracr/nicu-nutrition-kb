package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The persistent class for the screen_miscellaneous database table.
 * 
 */
@Entity
@Table(name = "screen_miscellaneous")
@NamedQuery(name = "ScreenMiscellaneous.findAll", query = "SELECT s FROM ScreenMiscellaneous s")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScreenMiscellaneous implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long screen_miscellaneous_id;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String uhid;

	private String loggeduser;

	private Timestamp screening_time;

	private Integer cga_weeks;

	private Integer cga_days;

	private Integer pna_weeks;

	private Integer pna_days;
	
	@Column(name="screening_type")
	private String screeningType;
	
	private String findings;
	
	@Column(name = "site")
	private String site;
	
	@Column(name = "view_type")
	private String view;
	
	@Column(name = "CT_Type")
	private String cttype;
	
	@Column(name = "progress_notes")
	private String notes;
	
	private String current_findings;
	@Transient
	private String screening_message;
	
	
	public ScreenMiscellaneous() {
		super();
	}

	public Long getScreen_miscellaneous_id() {
		return screen_miscellaneous_id;
	}

	public void setScreen_miscellaneous_id(Long screen_miscellaneous_id) {
		this.screen_miscellaneous_id = screen_miscellaneous_id;
	}

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public Timestamp getModificationtime() {
		return modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public Timestamp getScreening_time() {
		return screening_time;
	}

	public void setScreening_time(Timestamp screening_time) {
		this.screening_time = screening_time;
	}

	public Integer getCga_weeks() {
		return cga_weeks;
	}

	public void setCga_weeks(Integer cga_weeks) {
		this.cga_weeks = cga_weeks;
	}

	public Integer getCga_days() {
		return cga_days;
	}

	public void setCga_days(Integer cga_days) {
		this.cga_days = cga_days;
	}

	public Integer getPna_weeks() {
		return pna_weeks;
	}

	public void setPna_weeks(Integer pna_weeks) {
		this.pna_weeks = pna_weeks;
	}

	public Integer getPna_days() {
		return pna_days;
	}

	public void setPna_days(Integer pna_days) {
		this.pna_days = pna_days;
	}

	public String getScreeningType() {
		return screeningType;
	}

	public void setScreeningType(String screeningType) {
		this.screeningType = screeningType;
	}

	public String getFindings() {
		return findings;
	}

	public void setFindings(String findings) {
		this.findings = findings;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public String getCttype() {
		return cttype;
	}

	public void setCttype(String cttype) {
		this.cttype = cttype;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getCurrent_findings() {
		return current_findings;
	}

	public void setCurrent_findings(String current_findings) {
		this.current_findings = current_findings;
	}

	public String getScreening_message() {
		return screening_message;
	}

	public void setScreening_message(String screening_message) {
		this.screening_message = screening_message;
	}
	
	
	
	
	
}
