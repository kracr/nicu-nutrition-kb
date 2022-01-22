package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the vw_sa_cns database table.
 * 
 */
@Entity
@Table(name="vw_sa_cns")
@NamedQuery(name="VwSaCn.findAll", query="SELECT v FROM VwSaCn v")
public class VwSaCn implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String ageofonsetseizuresdays;

	@Id
	private Long cnspid;

	
	private Timestamp creationtime;

	
	private String cry;

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

	private String mrihead;

	@Column(columnDefinition="bool")
	private Boolean neurexamdisc;

	@Column(columnDefinition="bool")
	private Boolean nnr;

	@Column(columnDefinition="bool")
	private Boolean posture;

	@Column(columnDefinition="bool")
	private Boolean seizures;

	@Column(name="seizures_medi")
	private String seizuresMediKey;
	
	@Transient
	private String seizuresMediValue;

	@Column(name="seizurescauseid")
	private String seizurescauseKey;
	
	@Transient
	private String seizurescauseValue;

	@Column(name="seizurestypeid")
	private String seizurestypeKey;
	
	@Transient
	private String seizurestypeValue;

	private Integer stagehie;

	private String tone;

	private String uhid;

	@Transient
	private boolean edit;
	
	private String seizures_type;
	
	private String seizures_cause;
	
	
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
		
	public boolean isEdit() {
		return edit;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}
	
	public String getSeizures_type() {
		return seizures_type;
	}

	public void setSeizures_type(String seizures_type) {
		this.seizures_type = seizures_type;
	}

	public String getSeizures_cause() {
		return seizures_cause;
	}

	public void setSeizures_cause(String seizures_cause) {
		this.seizures_cause = seizures_cause;
	}

	public VwSaCn() {
		this.ageofonsetseizuresdays = "";
		this.cry = "";
		this.cthead = null;
		this.edit = false;
		this.eeg = null;
		this.feeding = null;
		this.gradeivh = 0;
		this.hie = false;
		this.ivh = false;
		this.mrihead = "";
		this.neurexamdisc = null;
		this.nnr = null;
		this.posture = null;
		this.seizures = null;
		this.seizurescauseKey = "";
		this.seizuresMediKey = "";
		this.seizurestypeKey= "";
		this.seizures_cause="";
		this.seizures_type="";
		this.tone = "";
	}

	public String getAgeofonsetseizuresdays() {
		return this.ageofonsetseizuresdays;
	}

	public void setAgeofonsetseizuresdays(String ageofonsetseizuresdays) {
		this.ageofonsetseizuresdays = ageofonsetseizuresdays;
	}

	public Long getCnspid() {
		return this.cnspid;
	}

	public void setCnspid(Long cnspid) {
		this.cnspid = cnspid;
	}

	

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getCry() {
		return this.cry;
	}

	public void setCry(String cry) {
		this.cry = cry;
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

	public String getSeizuresMediKey() {
		return seizuresMediKey;
	}

	public void setSeizuresMediKey(String seizuresMediKey) {
		this.seizuresMediKey = seizuresMediKey;
	}

	public String getSeizuresMediValue() {
		return seizuresMediValue;
	}

	public void setSeizuresMediValue(String seizuresMediValue) {
		this.seizuresMediValue = seizuresMediValue;
	}

	public String getSeizurescauseKey() {
		return seizurescauseKey;
	}

	public void setSeizurescauseKey(String seizurescauseKey) {
		this.seizurescauseKey = seizurescauseKey;
	}

	public String getSeizurescauseValue() {
		return seizurescauseValue;
	}

	public void setSeizurescauseValue(String seizurescauseValue) {
		this.seizurescauseValue = seizurescauseValue;
	}

	public String getSeizurestypeKey() {
		return seizurestypeKey;
	}

	public void setSeizurestypeKey(String seizurestypeKey) {
		this.seizurestypeKey = seizurestypeKey;
	}

	public String getSeizurestypeValue() {
		return seizurestypeValue;
	}

	public void setSeizurestypeValue(String seizurestypeValue) {
		this.seizurestypeValue = seizurestypeValue;
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

	@Override
	public String toString() {
		String notes = "CNS [";
		
		if(ageofonsetseizuresdays!=null){
			notes = notes+", age of onset seizures days:"+ageofonsetseizuresdays;
		}
		
		//this needs to be handled separately. 
//		if(cry!=null){
//			notes = notes+", cry:"+cry;
//		}
		
		if(cthead!=null){
			notes = notes+", CT head:"+cthead;
		}
		if(eeg!=null){
			notes = notes+", EEG:"+eeg;
		}
		if(feeding!=null){
			notes = notes+", Feeding:"+feeding;
		}
		if(gradeivh!=null){
			notes = notes+", grade ivh:"+gradeivh;
		}
		if(mrihead!=null){
			notes = notes+", mri head:"+mrihead;
		}
		if(hie!=null){
			notes = notes+", hie:"+hie;
		}
		if(ivh!=null){
			notes = notes+", ivh:"+ivh;
		}
		if(neurexamdisc!=null){
			notes = notes+", neurexamdisc:"+neurexamdisc;
		}
		if(posture!=null){
			notes = notes+", posture:"+posture;
		}
		if(seizures!=null){
			notes = notes+", seizures:"+seizures;
		}
		if(seizuresMediValue!=null){
			notes = notes+", seizures Value:"+seizuresMediValue;
		}
		if(seizurescauseValue!=null){
			notes = notes+", seizures cause:"+seizurescauseValue;
		}
		if(seizurestypeValue!=null){
			notes = notes+", seizures type:"+seizurestypeValue;
		}
		if(stagehie!=null){
			notes = notes+", stage hie:"+stagehie;
		}
		if(tone!=null){
			notes = notes+", tone:"+tone;
		}
		if(seizures_cause!=null){
			notes = notes+", seizures cause:"+seizures_cause;
		}
		
		if(bera!=null){
			notes = notes+", bera:"+bera;
		}
		
		if(vep!=null){
			notes = notes+", vep:"+vep;
		}
		
		notes = notes+" ]";
		return notes;
//		return "VwSaCn [ageofonsetseizuresdays=" + ageofonsetseizuresdays
//				+ ", cnspid=" + cnspid + ", creationdate=" + creationdate
//				+ ", cry=" + cry + ", cthead=" + cthead + ", eeg=" + eeg
//				+ ", feeding=" + feeding + ", gradeivh=" + gradeivh + ", hie="
//				+ hie + ", ivh=" + ivh + ", mrihead=" + mrihead
//				+ ", neurexamdisc=" + neurexamdisc + ", nnr=" + nnr
//				+ ", posture=" + posture + ", seizures=" + seizures
//				+ ", seizuresMediKey=" + seizuresMediKey
//				+ ", seizuresMediValue=" + seizuresMediValue
//				+ ", seizurescauseKey=" + seizurescauseKey
//				+ ", seizurescauseValue=" + seizurescauseValue
//				+ ", seizurestypeKey=" + seizurestypeKey
//				+ ", seizurestypeValue=" + seizurestypeValue + ", stagehie="
//				+ stagehie + ", tone=" + tone + ", uhid=" + uhid + ", edit="
//				+ edit + ", seizures_type=" + seizures_type
//				+ ", seizures_cause=" + seizures_cause + "]";
	}
	
	
	
}