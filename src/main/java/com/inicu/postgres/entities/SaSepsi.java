package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the sa_sepsis database table.
 * 
 */
@Entity
@Table(name="sa_sepsis")
@NamedQuery(name="SaSepsi.findAll", query="SELECT s FROM SaSepsi s")
public class SaSepsi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	private Boolean isEdit;
	
	
	
	
	
	public Boolean getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(Boolean isEdit) {
		this.isEdit = isEdit;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long sepsisid;

	private String ageatonset;

	private String symptoms;
	
	@Column(name="blood_antibiotics")
	private String bloodAntibiotics;

	@Column(columnDefinition="bool")
	private Boolean bloodculture;

	@Column(columnDefinition="bool")
	private Boolean boneinfection;

	private Timestamp creationtime;

	@Column(columnDefinition="bool")
	private Boolean crp;

	@Column(name="csf_antibiotics")
	private String csfAntibiotics;

	private String durationoftreatment;

	private String loggeduser;

	private String maxcrp;

	@Column(columnDefinition="bool")
	private Boolean meningtis;

	private Timestamp modificationtime;

	private String onsetsepsis;

	@Column(name="other_foci")
	private String otherFoci;

	@Column(columnDefinition="bool")
	private Boolean pneumonia;

	private String riskfactor;

	@Column(columnDefinition="bool")
	private Boolean sepsisscreen;

	private String tlc;

	private String uhid;

	@Column(name="urine_antibiotics")
	private String urineAntibiotics;

	@Column(columnDefinition="bool")
	private Boolean uti;

	@Column(name="blood_organisms")
	private String bloodOrganisms;

	@Column(name="csf_organisms")
	private String csfOrganisms;

	@Column(columnDefinition="bool")
	private Boolean csfculture;

	private String presentationeos;

	@Column(name="urine_organisms")
	private String urineOrganisms;

	@Column(columnDefinition="bool")
	private Boolean urineculture;

	@Transient
	private String otherUrineOrganisms;

	@Transient
	private String otherBloodOrganisms;

	@Transient
	private String otherCSFOrganisms;
	
	private String comment;
	
	
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public SaSepsi() {
		this.symtoms = new ArrayList<String>();
		this.urineAntibiotic = new ArrayList<String>();
		this.bloodAntibiotic = new ArrayList<String>();
		this.csfAntibiotic = new ArrayList<String>();
	}

	public Long getSepsisid() {
		return this.sepsisid;
	}

	public void setSepsisid(Long sepsisid) {
		this.sepsisid = sepsisid;
	}

	public String getAgeatonset() {
		return this.ageatonset;
	}

	public void setAgeatonset(String ageatonset) {
		this.ageatonset = ageatonset;
	}

	

	
	public Boolean getBloodculture() {
		return this.bloodculture;
	}

	public void setBloodculture(Boolean bloodculture) {
		this.bloodculture = bloodculture;
	}

	public Boolean getBoneinfection() {
		return this.boneinfection;
	}

	public void setBoneinfection(Boolean boneinfection) {
		this.boneinfection = boneinfection;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public Boolean getCrp() {
		return this.crp;
	}

	public void setCrp(Boolean crp) {
		this.crp = crp;
	}

	
	public String getDurationoftreatment() {
		return this.durationoftreatment;
	}

	public void setDurationoftreatment(String durationoftreatment) {
		this.durationoftreatment = durationoftreatment;
	}

	public String getLoggeduser() {
		return this.loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public String getMaxcrp() {
		return this.maxcrp;
	}

	public void setMaxcrp(String maxcrp) {
		this.maxcrp = maxcrp;
	}

	public Boolean getMeningtis() {
		return this.meningtis;
	}

	public void setMeningtis(Boolean meningtis) {
		this.meningtis = meningtis;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getOnsetsepsis() {
		return this.onsetsepsis;
	}

	public void setOnsetsepsis(String onsetsepsis) {
		this.onsetsepsis = onsetsepsis;
	}

	public String getOtherFoci() {
		return this.otherFoci;
	}

	public void setOtherFoci(String otherFoci) {
		this.otherFoci = otherFoci;
	}

	

	public Boolean getPneumonia() {
		return this.pneumonia;
	}

	public void setPneumonia(Boolean pneumonia) {
		this.pneumonia = pneumonia;
	}

	public String getRiskfactor() {
		return this.riskfactor;
	}

	public void setRiskfactor(String riskfactor) {
		this.riskfactor = riskfactor;
	}

	public Boolean getSepsisscreen() {
		return this.sepsisscreen;
	}

	public void setSepsisscreen(Boolean sepsisscreen) {
		this.sepsisscreen = sepsisscreen;
	}

	public String getTlc() {
		return this.tlc;
	}

	public void setTlc(String tlc) {
		this.tlc = tlc;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	

	public Boolean getUti() {
		return this.uti;
	}

	public void setUti(Boolean uti) {
		this.uti = uti;
	}

	
	public String getBloodOrganisms() {
		return bloodOrganisms;
	}

	public void setBloodOrganisms(String bloodOrganisms) {
		this.bloodOrganisms = bloodOrganisms;
	}

	public String getCsfOrganisms() {
		return csfOrganisms;
	}

	public void setCsfOrganisms(String csfOrganisms) {
		this.csfOrganisms = csfOrganisms;
	}

	public Boolean getCsfculture() {
		return csfculture;
	}

	public void setCsfculture(Boolean csfculture) {
		this.csfculture = csfculture;
	}

	public String getPresentationeos() {
		return presentationeos;
	}

	public void setPresentationeos(String presentationeos) {
		this.presentationeos = presentationeos;
	}

	public String getUrineOrganisms() {
		return urineOrganisms;
	}

	public void setUrineOrganisms(String urineOrganisms) {
		this.urineOrganisms = urineOrganisms;
	}

	public Boolean getUrineculture() {
		return urineculture;
	}

	public void setUrineculture(Boolean urineculture) {
		this.urineculture = urineculture;
	}
	
	public String getOtherUrineOrganisms() {
		return otherUrineOrganisms;
	}

	public void setOtherUrineOrganisms(String otherUrineOrganisms) {
		this.otherUrineOrganisms = otherUrineOrganisms;
	}

	public String getOtherBloodOrganisms() {
		return otherBloodOrganisms;
	}

	public void setOtherBloodOrganisms(String otherBloodOrganisms) {
		this.otherBloodOrganisms = otherBloodOrganisms;
	}

	public String getOtherCSFOrganisms() {
		return otherCSFOrganisms;
	}

	public void setOtherCSFOrganisms(String otherCSFOrganisms) {
		this.otherCSFOrganisms = otherCSFOrganisms;
	}

	@Override
	  public String toString() {
		  String notes = "Sepsis [";

		  if(bloodculture!=null){
			  notes = notes+", bloodculture:"+bloodculture;
		  }
//
//		  if(boneinfection!=null){
//			  notes = notes+", boneinfection:"+boneinfection;
//		  }
//
//		  if(crp!=null){
//			  notes = notes+", crp:"+crp;
//		  }
//
//
//
//		  if(durationoftreatment!=null){
//			  notes = notes+", duration of treatment:"+durationoftreatment;
//		  }
//
//		  if(maxcrp!=null){
//			  notes = notes+", max crp:"+maxcrp;
//		  }
//
//		  if(meningtis!=null){
//			  notes = notes+", meningtis:"+meningtis;
//		  }
//
//		  if(onsetsepsis!=null){
//			  notes = notes+", onset sepsis:"+onsetsepsis;
//		  }
//
//
//		  
//
//		  if(pneumonia!=null){
//			  notes = notes+", pneumonia:"+pneumonia;
//		  }



//		  if(riskfactor!=null){
//			  notes = notes+", riskfactor:"+riskfactor;
//		  }
//
//		  if(sepsisscreen!=null){
//			  notes = notes+", sepsisscreen:"+sepsisscreen;
//		  }
//
//
//
//		  if(uti!=null){
//			  notes = notes+", uti:"+uti;
//		  }

		  if(symptoms!=null){
			  notes = notes+", symptoms:"+symptoms;
		  }

//
//		  if(bloodOrganisms!=null){
//			  notes = notes+", bloodOrganisms:"+bloodOrganisms;
//		  }
//
//
//		  if(csfOrganisms!=null){
//			  notes = notes+", csfOrganisms:"+csfOrganisms;
//		  }


		  if(csfculture!=null){
			  notes = notes+", csfculture:"+csfculture;
		  }


//		  if(presentationeos!=null){
//			  notes = notes+", presentationeos:"+presentationeos;
//		  }
//
//		  if(urineculture!=null){
//			  notes = notes+", urineOrganisms:"+urineOrganisms;
//		  }

		  if(urineculture!=null){
			  notes = notes+", urineculture:"+urineculture;
		  }



		  notes = notes+" ]";
		  return notes;
	  }


	@Transient
	private List symtoms;

	@Transient
	private List urineAntibiotic;
	
	@Transient
	private List bloodAntibiotic;
	
	@Transient
	private List csfAntibiotic;



	
	public List getUrineAntibiotic() {
		return urineAntibiotic;
	}

	public void setUrineAntibiotic(List urineAntibiotic) {
		this.urineAntibiotic = urineAntibiotic;
	}

	public List getBloodAntibiotic() {
		return bloodAntibiotic;
	}

	public void setBloodAntibiotic(List bloodAntibiotic) {
		this.bloodAntibiotic = bloodAntibiotic;
	}

	public List getCsfAntibiotic() {
		return csfAntibiotic;
	}

	public void setCsfAntibiotic(List csfAntibiotic) {
		this.csfAntibiotic = csfAntibiotic;
	}

	public List getSymtoms() {
		return symtoms;
	}

	public void setSymtoms(List symtoms) {
		this.symtoms = symtoms;
	}

	public String getBloodAntibiotics() {
		return bloodAntibiotics;
	}

	public void setBloodAntibiotics(String bloodAntibiotics) {
		this.bloodAntibiotics = bloodAntibiotics;
	}

	public String getCsfAntibiotics() {
		return csfAntibiotics;
	}

	public void setCsfAntibiotics(String csfAntibiotics) {
		this.csfAntibiotics = csfAntibiotics;
	}

	public String getUrineAntibiotics() {
		return urineAntibiotics;
	}

	public void setUrineAntibiotics(String urineAntibiotics) {
		this.urineAntibiotics = urineAntibiotics;
	}

	public String getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(String symptoms) {
		this.symptoms = symptoms;
	}	
	
	
	
}