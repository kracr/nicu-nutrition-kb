package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
 * The persistent class for the screen_rop database table.
 * 
 */
@Entity
@Table(name = "screen_rop")
@NamedQuery(name = "ScreenRop.findAll", query = "SELECT s FROM ScreenRop s")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScreenRop implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long screen_rop_id;

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

	private String risk_factor;

	private String risk_factor_other;

	@Column(columnDefinition = "bool")
	private Boolean is_rop;

	@Column(columnDefinition = "bool")
	private Boolean is_rop_left;

	private String rop_left_zone;

	private String rop_left_stage;

	@Column(columnDefinition = "bool")
	private Boolean rop_left_plus;

	@Column(columnDefinition = "bool")
	private Boolean is_rop_right;

	private String rop_right_zone;

	private String rop_right_stage;

	@Column(columnDefinition = "bool")
	private Boolean rop_right_plus;

	@Transient
	private List<String> treatmentList;

	private String treatmentaction;

	@Column(columnDefinition = "bool")
	private Boolean no_treatment_left;

	@Column(columnDefinition = "bool")
	private Boolean no_treatment_right;

	@Column(columnDefinition = "bool")
	private Boolean left_laser;

	private Timestamp left_laser_date;

	private String left_laser_comment;

	@Column(columnDefinition = "bool")
	private Boolean right_laser;

	private Timestamp right_laser_date;

	private String right_laser_comment;

	@Column(columnDefinition = "bool")
	private Boolean left_avastin;

	private Timestamp left_avastin_date;

	@Column(columnDefinition = "Float4")
	private Float left_avastin_dose;

	@Column(columnDefinition = "bool")
	private Boolean right_avastin;

	private Timestamp right_avastin_date;

	@Column(columnDefinition = "Float4")
	private Float right_avastin_dose;

	@Column(columnDefinition = "bool")
	private Boolean left_surgery;

	private Timestamp left_surgery_date;

	private String left_surgery_comment;

	@Column(columnDefinition = "bool")
	private Boolean right_surgery;

	private Timestamp right_surgery_date;

	private String right_surgery_comment;

	private String other_treatment;

	private Integer reassess_time;

	private String reassess_timetype;

	private Date reassess_date;

	private String reassess_comments;

	private String progress_note;
	
	private String ophthalmologist;
	
	private String current_findings;
	
	@Transient
	private String findings_message;

	@Transient
	private String laser_message;
	
	@Transient
	private String screening_message;

	public ScreenRop() {
		super();
		this.reassess_timetype = "Days";
		this.treatmentList = new ArrayList<String>();
	}

	public Long getScreen_rop_id() {
		return screen_rop_id;
	}

	public void setScreen_rop_id(Long screen_rop_id) {
		this.screen_rop_id = screen_rop_id;
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

	public Boolean getIs_rop() {
		return is_rop;
	}

	public void setIs_rop(Boolean is_rop) {
		this.is_rop = is_rop;
	}

	public Boolean getIs_rop_left() {
		return is_rop_left;
	}

	public void setIs_rop_left(Boolean is_rop_left) {
		this.is_rop_left = is_rop_left;
	}

	public String getRop_left_zone() {
		return rop_left_zone;
	}

	public void setRop_left_zone(String rop_left_zone) {
		this.rop_left_zone = rop_left_zone;
	}

	public String getRop_left_stage() {
		return rop_left_stage;
	}

	public void setRop_left_stage(String rop_left_stage) {
		this.rop_left_stage = rop_left_stage;
	}

	public Boolean getRop_left_plus() {
		return rop_left_plus;
	}

	public void setRop_left_plus(Boolean rop_left_plus) {
		this.rop_left_plus = rop_left_plus;
	}

	public Boolean getIs_rop_right() {
		return is_rop_right;
	}

	public void setIs_rop_right(Boolean is_rop_right) {
		this.is_rop_right = is_rop_right;
	}

	public String getRop_right_zone() {
		return rop_right_zone;
	}

	public void setRop_right_zone(String rop_right_zone) {
		this.rop_right_zone = rop_right_zone;
	}

	public String getRop_right_stage() {
		return rop_right_stage;
	}

	public void setRop_right_stage(String rop_right_stage) {
		this.rop_right_stage = rop_right_stage;
	}

	public Boolean getRop_right_plus() {
		return rop_right_plus;
	}

	public void setRop_right_plus(Boolean rop_right_plus) {
		this.rop_right_plus = rop_right_plus;
	}

	public List<String> getTreatmentList() {
		return treatmentList;
	}

	public void setTreatmentList(List<String> treatmentList) {
		this.treatmentList = treatmentList;
	}

	public String getTreatmentaction() {
		return treatmentaction;
	}

	public void setTreatmentaction(String treatmentaction) {
		this.treatmentaction = treatmentaction;
	}

	public Boolean getNo_treatment_left() {
		return no_treatment_left;
	}

	public void setNo_treatment_left(Boolean no_treatment_left) {
		this.no_treatment_left = no_treatment_left;
	}

	public Boolean getNo_treatment_right() {
		return no_treatment_right;
	}

	public void setNo_treatment_right(Boolean no_treatment_right) {
		this.no_treatment_right = no_treatment_right;
	}

	public Boolean getLeft_laser() {
		return left_laser;
	}

	public void setLeft_laser(Boolean left_laser) {
		this.left_laser = left_laser;
	}

	public Timestamp getLeft_laser_date() {
		return left_laser_date;
	}

	public void setLeft_laser_date(Timestamp left_laser_date) {
		this.left_laser_date = left_laser_date;
	}

	public String getLeft_laser_comment() {
		return left_laser_comment;
	}

	public void setLeft_laser_comment(String left_laser_comment) {
		this.left_laser_comment = left_laser_comment;
	}

	public Boolean getRight_laser() {
		return right_laser;
	}

	public void setRight_laser(Boolean right_laser) {
		this.right_laser = right_laser;
	}

	public Timestamp getRight_laser_date() {
		return right_laser_date;
	}

	public void setRight_laser_date(Timestamp right_laser_date) {
		this.right_laser_date = right_laser_date;
	}

	public String getRight_laser_comment() {
		return right_laser_comment;
	}

	public void setRight_laser_comment(String right_laser_comment) {
		this.right_laser_comment = right_laser_comment;
	}

	public Boolean getLeft_avastin() {
		return left_avastin;
	}

	public void setLeft_avastin(Boolean left_avastin) {
		this.left_avastin = left_avastin;
	}

	public Timestamp getLeft_avastin_date() {
		return left_avastin_date;
	}

	public void setLeft_avastin_date(Timestamp left_avastin_date) {
		this.left_avastin_date = left_avastin_date;
	}

	public Float getLeft_avastin_dose() {
		return left_avastin_dose;
	}

	public void setLeft_avastin_dose(Float left_avastin_dose) {
		this.left_avastin_dose = left_avastin_dose;
	}

	public Boolean getRight_avastin() {
		return right_avastin;
	}

	public void setRight_avastin(Boolean right_avastin) {
		this.right_avastin = right_avastin;
	}

	public Timestamp getRight_avastin_date() {
		return right_avastin_date;
	}

	public void setRight_avastin_date(Timestamp right_avastin_date) {
		this.right_avastin_date = right_avastin_date;
	}

	public Float getRight_avastin_dose() {
		return right_avastin_dose;
	}

	public void setRight_avastin_dose(Float right_avastin_dose) {
		this.right_avastin_dose = right_avastin_dose;
	}

	public Boolean getLeft_surgery() {
		return left_surgery;
	}

	public void setLeft_surgery(Boolean left_surgery) {
		this.left_surgery = left_surgery;
	}

	public Timestamp getLeft_surgery_date() {
		return left_surgery_date;
	}

	public void setLeft_surgery_date(Timestamp left_surgery_date) {
		this.left_surgery_date = left_surgery_date;
	}

	public String getLeft_surgery_comment() {
		return left_surgery_comment;
	}

	public void setLeft_surgery_comment(String left_surgery_comment) {
		this.left_surgery_comment = left_surgery_comment;
	}

	public Boolean getRight_surgery() {
		return right_surgery;
	}

	public void setRight_surgery(Boolean right_surgery) {
		this.right_surgery = right_surgery;
	}

	public Timestamp getRight_surgery_date() {
		return right_surgery_date;
	}

	public void setRight_surgery_date(Timestamp right_surgery_date) {
		this.right_surgery_date = right_surgery_date;
	}

	public String getRight_surgery_comment() {
		return right_surgery_comment;
	}

	public void setRight_surgery_comment(String right_surgery_comment) {
		this.right_surgery_comment = right_surgery_comment;
	}

	public String getOther_treatment() {
		return other_treatment;
	}

	public void setOther_treatment(String other_treatment) {
		this.other_treatment = other_treatment;
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

	public String getOphthalmologist() {
		return ophthalmologist;
	}

	public void setOphthalmologist(String ophthalmologist) {
		this.ophthalmologist = ophthalmologist;
	}

	public String getCurrent_findings() {
		return current_findings;
	}

	public void setCurrent_findings(String current_findings) {
		this.current_findings = current_findings;
	}

	public String getFindings_message() {
		return findings_message;
	}

	public void setFindings_message(String findings_message) {
		this.findings_message = findings_message;
	}

	public String getLaser_message() {
		return laser_message;
	}

	public void setLaser_message(String laser_message) {
		this.laser_message = laser_message;
	}

	public String getScreening_message() {
		return screening_message;
	}

	public void setScreening_message(String screening_message) {
		this.screening_message = screening_message;
	}

	@Override
	public String toString() {
		return "ScreenRop [screen_rop_id=" + screen_rop_id + ", creationtime=" + creationtime + ", modificationtime="
				+ modificationtime + ", uhid=" + uhid + ", episodeid=" + episodeid + ", loggeduser=" + loggeduser
				+ ", screening_time=" + screening_time + ", cga_weeks=" + cga_weeks + ", cga_days=" + cga_days
				+ ", pna_weeks=" + pna_weeks + ", pna_days=" + pna_days + ", risk_factor=" + risk_factor
				+ ", risk_factor_other=" + risk_factor_other + ", is_rop=" + is_rop + ", is_rop_left=" + is_rop_left
				+ ", rop_left_zone=" + rop_left_zone + ", rop_left_stage=" + rop_left_stage + ", rop_left_plus="
				+ rop_left_plus + ", is_rop_right=" + is_rop_right + ", rop_right_zone=" + rop_right_zone
				+ ", rop_right_stage=" + rop_right_stage + ", rop_right_plus=" + rop_right_plus + ", treatmentList="
				+ treatmentList + ", treatmentaction=" + treatmentaction + ", no_treatment_left=" + no_treatment_left
				+ ", no_treatment_right=" + no_treatment_right + ", left_laser=" + left_laser + ", left_laser_date="
				+ left_laser_date + ", left_laser_comment=" + left_laser_comment + ", right_laser=" + right_laser
				+ ", right_laser_date=" + right_laser_date + ", right_laser_comment=" + right_laser_comment
				+ ", left_avastin=" + left_avastin + ", left_avastin_date=" + left_avastin_date + ", left_avastin_dose="
				+ left_avastin_dose + ", right_avastin=" + right_avastin + ", right_avastin_date=" + right_avastin_date
				+ ", right_avastin_dose=" + right_avastin_dose + ", left_surgery=" + left_surgery
				+ ", left_surgery_date=" + left_surgery_date + ", left_surgery_comment=" + left_surgery_comment
				+ ", right_surgery=" + right_surgery + ", right_surgery_date=" + right_surgery_date
				+ ", right_surgery_comment=" + right_surgery_comment + ", other_treatment=" + other_treatment
				+ ", reassess_time=" + reassess_time + ", reassess_timetype=" + reassess_timetype + ", reassess_date="
				+ reassess_date + ", reassess_comments=" + reassess_comments + ", progress_note=" + progress_note
				+ ", ophthalmologist=" + ophthalmologist + "]";
	}

}
