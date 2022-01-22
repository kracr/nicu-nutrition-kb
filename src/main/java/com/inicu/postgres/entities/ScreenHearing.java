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
 * The persistent class for the screen_hearing database table.
 * 
 */
@Entity
@Table(name = "screen_hearing")
@NamedQuery(name = "ScreenHearing.findAll", query = "SELECT s FROM ScreenHearing s")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScreenHearing implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long screen_hearing_id;

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

	private String indication;

	private String risk_factor;

	private String risk_factor_other;

	private String screening_test;

	private String oae_left;

	private String oae_right;

	private String abr_left;

	private String abr_right;

	@Column(columnDefinition = "bool")
	private Boolean treatment_given;

	private String treatment;

	private Integer reassess_time;

	private String reassess_timetype;

	private Date reassess_date;

	private String reassess_comments;

	private String progress_note;
	
	@Transient
	private String oae;
	
	@Transient
	private String abr;
	
	@Transient
	private String screening_message;

	public ScreenHearing() {
		super();
		this.treatment_given = false;
		this.reassess_timetype = "Days";
		this.indication = "Routine";
	}

	public Long getScreen_hearing_id() {
		return screen_hearing_id;
	}

	public void setScreen_hearing_id(Long screen_hearing_id) {
		this.screen_hearing_id = screen_hearing_id;
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

	public String getIndication() {
		return indication;
	}

	public void setIndication(String indication) {
		this.indication = indication;
	}

	public String getRisk_factor() {
		return risk_factor;
	}

	public void setRisk_factor(String risk_factor) {
		this.risk_factor = risk_factor;
	}

	public String getRisk_factor_other() {
		return risk_factor_other;
	}

	public void setRisk_factor_other(String risk_factor_other) {
		this.risk_factor_other = risk_factor_other;
	}

	public String getScreening_test() {
		return screening_test;
	}

	public void setScreening_test(String screening_test) {
		this.screening_test = screening_test;
	}

	public String getOae_left() {
		return oae_left;
	}

	public void setOae_left(String oae_left) {
		this.oae_left = oae_left;
	}

	public String getOae_right() {
		return oae_right;
	}

	public void setOae_right(String oae_right) {
		this.oae_right = oae_right;
	}

	public String getAbr_left() {
		return abr_left;
	}

	public void setAbr_left(String abr_left) {
		this.abr_left = abr_left;
	}

	public String getAbr_right() {
		return abr_right;
	}

	public void setAbr_right(String abr_right) {
		this.abr_right = abr_right;
	}

	public Boolean getTreatment_given() {
		return treatment_given;
	}

	public void setTreatment_given(Boolean treatment_given) {
		this.treatment_given = treatment_given;
	}

	public String getTreatment() {
		return treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
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

	public String getProgress_note() {
		return progress_note;
	}

	public void setProgress_note(String progress_note) {
		this.progress_note = progress_note;
	}

	public String getOae() {
		return oae;
	}

	public void setOae(String oae) {
		this.oae = oae;
	}

	public String getAbr() {
		return abr;
	}

	public void setAbr(String abr) {
		this.abr = abr;
	}

	public String getScreening_message() {
		return screening_message;
	}

	public void setScreening_message(String screening_message) {
		this.screening_message = screening_message;
	}

	@Override
	public String toString() {
		return "ScreenHearing [screen_hearing_id=" + screen_hearing_id + ", creationtime=" + creationtime
				+ ", modificationtime=" + modificationtime + ", uhid=" + uhid + ", episodeid=" + episodeid
				+ ", loggeduser=" + loggeduser + ", screening_time=" + screening_time + ", cga_weeks=" + cga_weeks
				+ ", cga_days=" + cga_days + ", pna_weeks=" + pna_weeks + ", pna_days=" + pna_days + ", indication="
				+ indication + ", risk_factor=" + risk_factor + ", risk_factor_other=" + risk_factor_other
				+ ", screening_test=" + screening_test + ", oae_left=" + oae_left + ", oae_right=" + oae_right
				+ ", abr_left=" + abr_left + ", abr_right=" + abr_right + ", treatment_given=" + treatment_given
				+ ", treatment=" + treatment + ", reassess_time=" + reassess_time + ", reassess_timetype="
				+ reassess_timetype + ", reassess_date=" + reassess_date + ", reassess_comments=" + reassess_comments
				+ ", progress_note=" + progress_note + "]";
	}

}
