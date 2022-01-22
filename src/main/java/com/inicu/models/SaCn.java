package com.inicu.models;

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

/**
 * The persistent class for the sa_cns database table.
 * 
 */
@Entity
@Table(name="sa_cns")
@NamedQuery(name="SaCn.findAll", query="SELECT s FROM SaCn s")
public class SaCn implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long cnspid;

	private String ageofonsetseizuresdays;

	private Timestamp creationtime;

	@Column(columnDefinition="bool")
	private Boolean cthead;

	@Column(columnDefinition="bool")
	private Boolean eeg;

	@Column(columnDefinition="bool")
	private Boolean feeding;

	private Integer gradeivh;

	@Column(columnDefinition="bool")
	private Boolean hie;

	@Column(columnDefinition="bool")
	private Boolean ivh;

	private Timestamp modificationtime;

	private String mrihead;

	@Column(columnDefinition="bool")
	private Boolean neurexamdisc;

	@Column(columnDefinition="bool")
	private Boolean nnr;

	@Column(columnDefinition="bool")
	private Boolean posture;

	@Column(columnDefinition="bool")
	private Boolean seizures;

	@Column(name="seizures_cause")
	private String seizuresCause;

	@Column(name="seizures_medi")
	private String seizuresMedi;

	@Column(name="seizures_type")
	private String seizuresType;

	private Integer stagehie;

	private String tone;

	private String uhid;
	private String loggeduser;
	
//changes on 09 march 2017
	@Column(columnDefinition="bool")
	private Boolean bera;
	
	@Column(columnDefinition="bool")
	private Boolean vep;
	
	private String comment;
	
	@Transient
	private String otherSeizuresCause;
	
	@Transient
	private String otherSeizuresMedi;
	
	
	
	public String getOtherSeizuresCause() {
		return otherSeizuresCause;
	}

	public void setOtherSeizuresCause(String otherSeizuresCause) {
		this.otherSeizuresCause = otherSeizuresCause;
	}

	public String getOtherSeizuresMedi() {
		return otherSeizuresMedi;
	}

	public void setOtherSeizuresMedi(String otherSeizuresMedi) {
		this.otherSeizuresMedi = otherSeizuresMedi;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public SaCn() {
		this.cthead =null;
		this.eeg = null;
		this.feeding = null;
		this.hie = null;
		this.ivh  = null;
		this.neurexamdisc  = null;
		this.nnr  = null;
		this.posture  = null;this.seizures  = null;
	}

	public Long getCnspid() {
		return this.cnspid;
	}

	public void setCnspid(Long cnspid) {
		this.cnspid = cnspid;
	}

	public String getAgeofonsetseizuresdays() {
		return this.ageofonsetseizuresdays;
	}

	public void setAgeofonsetseizuresdays(String ageofonsetseizuresdays) {
		this.ageofonsetseizuresdays = ageofonsetseizuresdays;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public Boolean getCthead() {
		return this.cthead;
	}

	public void setCthead(Boolean cthead) {
		this.cthead = cthead;
	}

	public Boolean getEeg() {
		return this.eeg;
	}

	public void setEeg(Boolean eeg) {
		this.eeg = eeg;
	}

	public Boolean getFeeding() {
		return this.feeding;
	}

	public void setFeeding(Boolean feeding) {
		this.feeding = feeding;
	}

	public Integer getGradeivh() {
		return this.gradeivh;
	}

	public void setGradeivh(Integer gradeivh) {
		this.gradeivh = gradeivh;
	}

	public Boolean getHie() {
		return this.hie;
	}

	public void setHie(Boolean hie) {
		this.hie = hie;
	}

	public Boolean getIvh() {
		return this.ivh;
	}

	public void setIvh(Boolean ivh) {
		this.ivh = ivh;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getMrihead() {
		return this.mrihead;
	}

	public void setMrihead(String mrihead) {
		this.mrihead = mrihead;
	}

	public Boolean getNeurexamdisc() {
		return this.neurexamdisc;
	}

	public void setNeurexamdisc(Boolean neurexamdisc) {
		this.neurexamdisc = neurexamdisc;
	}

	public Boolean getNnr() {
		return this.nnr;
	}

	public void setNnr(Boolean nnr) {
		this.nnr = nnr;
	}

	public Boolean getPosture() {
		return this.posture;
	}

	public void setPosture(Boolean posture) {
		this.posture = posture;
	}

	public Boolean getSeizures() {
		return this.seizures;
	}

	public void setSeizures(Boolean seizures) {
		this.seizures = seizures;
	}

	public String getSeizuresCause() {
		return this.seizuresCause;
	}

	public void setSeizuresCause(String seizuresCause) {
		this.seizuresCause = seizuresCause;
	}

	public String getSeizuresMedi() {
		return this.seizuresMedi;
	}

	public void setSeizuresMedi(String seizuresMedi) {
		this.seizuresMedi = seizuresMedi;
	}

	public String getSeizuresType() {
		return this.seizuresType;
	}

	public void setSeizuresType(String seizuresType) {
		this.seizuresType = seizuresType;
	}

	public Integer getStagehie() {
		return this.stagehie;
	}

	public void setStagehie(Integer stagehie) {
		this.stagehie = stagehie;
	}

	public String getTone() {
		return this.tone;
	}

	public void setTone(String tone) {
		this.tone = tone;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}
	public  void setLoggeduser(String loggeduser)
	{
		this.loggeduser = loggeduser;
	}
	public String getLoggeduser()
	{
		return this.loggeduser;
	}

	public Boolean getBera() {
		return bera;
	}

	public void setBera(Boolean bera) {
		this.bera = bera;
	}

	public Boolean getVep() {
		return vep;
	}

	public void setVep(Boolean vep) {
		this.vep = vep;
	}

	
	
}