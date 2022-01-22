package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The persistent class for the screen_usg database table.
 * 
 */
@Entity
@Table(name = "screen_usg")
@NamedQuery(name = "ScreenUSG.findAll", query = "SELECT s FROM ScreenUSG s")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScreenUSG implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long screen_usg_id;

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

	private String brain_parenchyma;

	private String parenchyma_description;

	private String lateral_ventricle;

	private Integer left_ventricle;

	private Integer right_ventricle;

	private Integer third_ventricle;

	private String ivh_type;

	private String ivh_side;

	private String ivh_description;

	private String ventriculomegaly;

	private String pvl_type;

	private String pvl_description;

	private String finding_comments;

	private Integer reassess_time;

	private String reassess_timetype;

	private Date reassess_date;

	private String reassess_comments;

	private String progress_note;
	
	private String current_findings;
	@Transient
	private String screening_message;

	public ScreenUSG() {
		super();
		this.reassess_timetype = "Days";
	}

	public Long getScreen_usg_id() {
		return screen_usg_id;
	}

	public void setScreen_usg_id(Long screen_usg_id) {
		this.screen_usg_id = screen_usg_id;
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

	public String getBrain_parenchyma() {
		return brain_parenchyma;
	}

	public void setBrain_parenchyma(String brain_parenchyma) {
		this.brain_parenchyma = brain_parenchyma;
	}

	public String getParenchyma_description() {
		return parenchyma_description;
	}

	public void setParenchyma_description(String parenchyma_description) {
		this.parenchyma_description = parenchyma_description;
	}

	public String getLateral_ventricle() {
		return lateral_ventricle;
	}

	public void setLateral_ventricle(String lateral_ventricle) {
		this.lateral_ventricle = lateral_ventricle;
	}

	public Integer getLeft_ventricle() {
		return left_ventricle;
	}

	public void setLeft_ventricle(Integer left_ventricle) {
		this.left_ventricle = left_ventricle;
	}

	public Integer getRight_ventricle() {
		return right_ventricle;
	}

	public void setRight_ventricle(Integer right_ventricle) {
		this.right_ventricle = right_ventricle;
	}

	public Integer getThird_ventricle() {
		return third_ventricle;
	}

	public void setThird_ventricle(Integer third_ventricle) {
		this.third_ventricle = third_ventricle;
	}

	public String getIvh_type() {
		return ivh_type;
	}

	public void setIvh_type(String ivh_type) {
		this.ivh_type = ivh_type;
	}

	public String getIvh_side() {
		return ivh_side;
	}

	public void setIvh_side(String ivh_side) {
		this.ivh_side = ivh_side;
	}

	public String getIvh_description() {
		return ivh_description;
	}

	public void setIvh_description(String ivh_description) {
		this.ivh_description = ivh_description;
	}

	public String getVentriculomegaly() {
		return ventriculomegaly;
	}

	public void setVentriculomegaly(String ventriculomegaly) {
		this.ventriculomegaly = ventriculomegaly;
	}

	public String getPvl_type() {
		return pvl_type;
	}

	public void setPvl_type(String pvl_type) {
		this.pvl_type = pvl_type;
	}

	public String getPvl_description() {
		return pvl_description;
	}

	public void setPvl_description(String pvl_description) {
		this.pvl_description = pvl_description;
	}

	public String getFinding_comments() {
		return finding_comments;
	}

	public void setFinding_comments(String finding_comments) {
		this.finding_comments = finding_comments;
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

	@Override
	public String toString() {
		return "ScreenUSG [screen_usg_id=" + screen_usg_id + ", creationtime=" + creationtime + ", modificationtime="
				+ modificationtime + ", uhid=" + uhid + ", episodeid=" + episodeid + ", loggeduser=" + loggeduser
				+ ", screening_time=" + screening_time + ", cga_weeks=" + cga_weeks + ", cga_days=" + cga_days
				+ ", pna_weeks=" + pna_weeks + ", pna_days=" + pna_days + ", brain_parenchyma=" + brain_parenchyma
				+ ", parenchyma_description=" + parenchyma_description + ", lateral_ventricle=" + lateral_ventricle
				+ ", left_ventricle=" + left_ventricle + ", right_ventricle=" + right_ventricle + ", third_ventricle="
				+ third_ventricle + ", ivh_type=" + ivh_type + ", ivh_side=" + ivh_side + ", ivh_description="
				+ ivh_description + ", ventriculomegaly=" + ventriculomegaly + ", pvl_type=" + pvl_type
				+ ", pvl_description=" + pvl_description + ", finding_comments=" + finding_comments + ", reassess_time="
				+ reassess_time + ", reassess_timetype=" + reassess_timetype + ", reassess_date=" + reassess_date
				+ ", reassess_comments=" + reassess_comments + ", progress_note=" + progress_note + "]";
	}

}
