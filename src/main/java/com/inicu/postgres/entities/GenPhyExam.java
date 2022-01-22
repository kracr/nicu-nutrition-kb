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

/**
 * The persistent class for the gen_phy_exam database table.
 * 
 * @author Sourabh Verma
 */
@Entity
@Table(name = "gen_phy_exam")
@NamedQuery(name = "GenPhyExam.findAll", query = "SELECT s FROM GenPhyExam s")
public class GenPhyExam implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "gen_phy_exam_id")
	private Long genPhyExamId;

	@Column(columnDefinition = "timestamp with time zone")
	private Timestamp creationtime;

	@Column(columnDefinition = "timestamp with time zone")
	private Timestamp modificationtime;

	private String uhid;

	private String episodeid;

	private String loggeduser;

	private String apearance;

	private String skin;

	private String skin_other;

	private String head_neck;

	private String head_neck_other;

	private String eyes;

	private String eyes_other;

	private String palate;

	private String lip;

	@Column(name = "lip_cleft_side", columnDefinition = "bool")
	private Boolean lipCleftSide;

	private String anal;
	
	private String anal_other;

	private String genitals;

	private String genitals_other;

	private String reflexes;

	private String reflexes_other;

	private String chest;

	private String chest_other;

	private String abdomen;

	private String abdomen_other;

	private String cong_malform;

	private String cong_malform_other;

	private String other;

	public Long getGenPhyExamId() {
		return genPhyExamId;
	}

	public void setGenPhyExamId(Long genPhyExamId) {
		this.genPhyExamId = genPhyExamId;
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

	public String getApearance() {
		return apearance;
	}

	public void setApearance(String apearance) {
		this.apearance = apearance;
	}

	public String getSkin() {
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}

	public String getSkin_other() {
		return skin_other;
	}

	public void setSkin_other(String skin_other) {
		this.skin_other = skin_other;
	}

	public String getHead_neck() {
		return head_neck;
	}

	public void setHead_neck(String head_neck) {
		this.head_neck = head_neck;
	}

	public String getHead_neck_other() {
		return head_neck_other;
	}

	public void setHead_neck_other(String head_neck_other) {
		this.head_neck_other = head_neck_other;
	}

	public String getEyes() {
		return eyes;
	}

	public void setEyes(String eyes) {
		this.eyes = eyes;
	}

	public String getEyes_other() {
		return eyes_other;
	}

	public void setEyes_other(String eyes_other) {
		this.eyes_other = eyes_other;
	}

	public String getPalate() {
		return palate;
	}

	public void setPalate(String palate) {
		this.palate = palate;
	}

	public String getLip() {
		return lip;
	}

	public void setLip(String lip) {
		this.lip = lip;
	}

	public Boolean getLipCleftSide() {
		return lipCleftSide;
	}

	public void setLipCleftSide(Boolean lipCleftSide) {
		this.lipCleftSide = lipCleftSide;
	}

	public String getAnal() {
		return anal;
	}

	public void setAnal(String anal) {
		this.anal = anal;
	}

	public String getGenitals() {
		return genitals;
	}

	public void setGenitals(String genitals) {
		this.genitals = genitals;
	}

	public String getGenitals_other() {
		return genitals_other;
	}

	public void setGenitals_other(String genitals_other) {
		this.genitals_other = genitals_other;
	}

	public String getReflexes() {
		return reflexes;
	}

	public void setReflexes(String reflexes) {
		this.reflexes = reflexes;
	}

	public String getReflexes_other() {
		return reflexes_other;
	}

	public void setReflexes_other(String reflexes_other) {
		this.reflexes_other = reflexes_other;
	}

	public String getChest() {
		return chest;
	}

	public void setChest(String chest) {
		this.chest = chest;
	}

	public String getChest_other() {
		return chest_other;
	}

	public void setChest_other(String chest_other) {
		this.chest_other = chest_other;
	}

	public String getAbdomen() {
		return abdomen;
	}

	public void setAbdomen(String abdomen) {
		this.abdomen = abdomen;
	}

	public String getAbdomen_other() {
		return abdomen_other;
	}

	public void setAbdomen_other(String abdomen_other) {
		this.abdomen_other = abdomen_other;
	}

	public String getCong_malform() {
		return cong_malform;
	}

	public void setCong_malform(String cong_malform) {
		this.cong_malform = cong_malform;
	}

	public String getCong_malform_other() {
		return cong_malform_other;
	}

	public void setCong_malform_other(String cong_malform_other) {
		this.cong_malform_other = cong_malform_other;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getAnal_other() {
		return anal_other;
	}

	public void setAnal_other(String anal_other) {
		this.anal_other = anal_other;
	}
	

}
