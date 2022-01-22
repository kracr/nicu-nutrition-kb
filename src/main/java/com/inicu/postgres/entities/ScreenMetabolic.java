package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

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
 * The persistent class for the screen_metabolic database table.
 * 
 */
@Entity
@Table(name = "screen_metabolic")
@NamedQuery(name = "ScreenMetabolic.findAll", query = "SELECT s FROM ScreenMetabolic s")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScreenMetabolic implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long screen_metabolic_id;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String uhid;

	private String episodeid;

	private String loggeduser;

	private Timestamp screening_time;

	private Integer cga_weeks;

	private Integer cga_days;

	private Integer pna_weeks;

	private Integer pna_days;

	private String metabolic_screening;

	@Column(columnDefinition = "bool")
	private Boolean screening_panel;

	private Integer reassess_time;

	private String reassess_timetype;

	private Date reassess_date;

	private String reassess_comments;
	
	@Transient
	private String screening_message;
	
	@Transient
	private String screening_panel_message;

	public ScreenMetabolic() {
		super();
		this.reassess_timetype = "Days";
	}

	public Long getScreen_metabolic_id() {
		return screen_metabolic_id;
	}

	public void setScreen_metabolic_id(Long screen_metabolic_id) {
		this.screen_metabolic_id = screen_metabolic_id;
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

	public String getEpisodeid() {
		return episodeid;
	}

	public void setEpisodeid(String episodeid) {
		this.episodeid = episodeid;
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

	public String getMetabolic_screening() {
		return metabolic_screening;
	}

	public void setMetabolic_screening(String metabolic_screening) {
		this.metabolic_screening = metabolic_screening;
	}

	public Boolean getScreening_panel() {
		return screening_panel;
	}

	public void setScreening_panel(Boolean screening_panel) {
		this.screening_panel = screening_panel;
	}

	public Integer getReassess_time() {
		return reassess_time;
	}

	public void setReassess_time(Integer reassess_time) {
		this.reassess_time = reassess_time;
	}

	public String getReassess_timetype() {
		return reassess_timetype;
	}

	public void setReassess_timetype(String reassess_timetype) {
		this.reassess_timetype = reassess_timetype;
	}

	public Date getReassess_date() {
		return reassess_date;
	}

	public void setReassess_date(Date reassess_date) {
		this.reassess_date = reassess_date;
	}

	public String getReassess_comments() {
		return reassess_comments;
	}

	public void setReassess_comments(String reassess_comments) {
		this.reassess_comments = reassess_comments;
	}

	public String getScreening_panel_message() {
		return screening_panel_message;
	}

	public void setScreening_panel_message(String screening_panel_message) {
		this.screening_panel_message = screening_panel_message;
	}

	public String getScreening_message() {
		return screening_message;
	}

	public void setScreening_message(String screening_message) {
		this.screening_message = screening_message;
	}

	@Override
	public String toString() {
		return "ScreenMetabolic [screen_metabolic_id=" + screen_metabolic_id + ", creationtime=" + creationtime
				+ ", modificationtime=" + modificationtime + ", uhid=" + uhid + ", episodeid=" + episodeid
				+ ", loggeduser=" + loggeduser + ", screening_time=" + screening_time + ", cga_weeks=" + cga_weeks
				+ ", cga_days=" + cga_days + ", pna_weeks=" + pna_weeks + ", pna_days=" + pna_days
				+ ", metabolic_screening=" + metabolic_screening + ", screening_panel=" + screening_panel
				+ ", reassess_time=" + reassess_time + ", reassess_timetype=" + reassess_timetype + ", reassess_date="
				+ reassess_date + ", reassess_comments=" + reassess_comments + "]";
	}

}
