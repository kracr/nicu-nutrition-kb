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

/**
 * The persistent class for the sa_misc database table.
 * 
 */
@Entity
@Table(name="sa_misc")
@NamedQuery(name="SaMisc.findAll", query="SELECT s FROM SaMisc s")
public class SaMisc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long miscid;

	private String beraadvised;

	private Timestamp creationtime;

	@Column(columnDefinition="bool")
	private Boolean metabolicscreensent;

	private Timestamp modificationtime;

	private String oaeleft;

	private String oaeright;

	private String other;

	private String report;

	@Column(columnDefinition="bool")
	private Boolean rop;

	@Column(name="ropstage")
	private String ropstageKey;

	@Transient
	private String ropstageValue;

	private String tsh;

	private String uhid;

	@Column(name="vaccination_discharge", columnDefinition="bool")
	private Boolean vaccinationDischarge;

	@Transient
	private boolean edit;

	private String loggeduser;

	@Transient
	private String createDate;
	
	//changes 10 march 2017
	@Column(columnDefinition="bool")
	private Boolean cchd;
	
	@Column(columnDefinition="bool")
	private Boolean cdh;
	
	private String comments;
	
	public SaMisc() {
		this.beraadvised="";
		this.createDate="";
		this.metabolicscreensent=null;
		this.miscid=null;
		this.oaeleft="";
		this.oaeright="";
		this.other="";
		this.report="";
		this.rop=null;
		this.ropstageKey="";
		this.tsh="";
		this.vaccinationDischarge=null;
		this.cchd = null;
		this.cdh = null;
	}


	public boolean isEdit() {
		return edit;
	}


	public void setEdit(boolean edit) {
		this.edit = edit;
	}


	public String getCreateDate() {
		return createDate;
	}


	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}


	public Long getMiscid() {
		return this.miscid;
	}

	public void setMiscid(Long miscid) {
		this.miscid = miscid;
	}

	public String getBeraadvised() {
		return this.beraadvised;
	}

	public void setBeraadvised(String beraadvised) {
		this.beraadvised = beraadvised;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public Boolean getMetabolicscreensent() {
		return this.metabolicscreensent;
	}

	public void setMetabolicscreensent(Boolean metabolicscreensent) {
		this.metabolicscreensent = metabolicscreensent;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getOaeleft() {
		return this.oaeleft;
	}

	public void setOaeleft(String oaeleft) {
		this.oaeleft = oaeleft;
	}

	public String getOaeright() {
		return this.oaeright;
	}

	public void setOaeright(String oaeright) {
		this.oaeright = oaeright;
	}

	public String getOther() {
		return this.other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getReport() {
		return this.report;
	}

	public void setReport(String report) {
		this.report = report;
	}

	public Boolean getRop() {
		return this.rop;
	}

	public void setRop(Boolean rop) {
		this.rop = rop;
	}

	public String getTsh() {
		return this.tsh;
	}

	public void setTsh(String tsh) {
		this.tsh = tsh;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public Boolean getVaccinationDischarge() {
		return this.vaccinationDischarge;
	}

	public void setVaccinationDischarge(Boolean vaccinationDischarge) {
		this.vaccinationDischarge = vaccinationDischarge;
	}


	public String getRopstageKey() {
		return ropstageKey;
	}


	public void setRopstageKey(String ropstageKey) {
		this.ropstageKey = ropstageKey;
	}


	public String getRopstageValue() {
		return ropstageValue;
	}


	public void setRopstageValue(String ropstageValue) {
		this.ropstageValue = ropstageValue;
	}
	public String getLoggedUser() {
		return loggeduser;
	}

	public void setLoggedUser(String loggeduser) {
		this.loggeduser = loggeduser;
	}


	@Override
	public String toString() {
		String notes = "Misc [";

//		if(beraadvised!=null){
//			notes = notes+", been advised:"+beraadvised;
//		}
//
//		if(metabolicscreensent!=null){
//			notes = notes+", metabolic screensent:"+metabolicscreensent;
//		}
//
//		if(oaeleft!=null){
//			notes = notes+", oaeleft:"+oaeleft;
//		}
//
//		if(oaeright!=null){
//			notes = notes+", oaeright:"+oaeright;
//		}
//
//		if(other!=null){
//			notes = notes+", other:"+other;
//		}
//
//		if(report!=null){
//			notes = notes+", report:"+report;
//		}

		if(rop!=null){
			notes = notes+", rop:"+rop;
		}

//		if(ropstageKey!=null){
//			notes = notes+", ropstageKey:"+ropstageKey;
//		}

		if(ropstageValue!=null){
			notes = notes+", ropstageValue:"+ropstageValue;
		}

//		if(tsh!=null){
//			notes = notes+", tsh:"+tsh;
//		}

		if(vaccinationDischarge!=null){
			notes = notes+", vaccination Discharge:"+vaccinationDischarge;
		}
		
//		if(cchd!=null){
//			notes = notes+", CCHD:"+cchd;
//		}
//		
//		if(cdh!=null){
//			notes = notes+", CDH:"+cdh;
//		}
//
//		if(cchd!=null){
//			notes = notes+", CCHD:"+cchd;
//		}
//	
//		if(cdh!=null){
//			notes = notes+", cdh:"+cdh;
//		}
//		if(comments!=null){
//			notes = notes+", comments:"+comments;
//		}
		
		notes = notes+" ]";
		return notes;
		//		return "SaMisc [miscid=" + miscid + ", beraadvised=" + beraadvised
		//				+ ", creationtime=" + creationtime + ", metabolicscreensent="
		//				+ metabolicscreensent + ", modificationtime="
		//				+ modificationtime + ", oaeleft=" + oaeleft + ", oaeright="
		//				+ oaeright + ", other=" + other + ", report=" + report
		//				+ ", rop=" + rop + ", ropstageKey=" + ropstageKey
		//				+ ", ropstageValue=" + ropstageValue + ", tsh=" + tsh
		//				+ ", uhid=" + uhid + ", vaccinationDischarge="
		//				+ vaccinationDischarge + ", edit=" + edit + ", loggeduser="
		//				+ loggeduser + ", createDate=" + createDate + "]";
	}


	public String getLoggeduser() {
		return loggeduser;
	}


	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}


	public Boolean getCchd() {
		return cchd;
	}


	public void setCchd(Boolean cchd) {
		this.cchd = cchd;
	}


	public Boolean getCdh() {
		return cdh;
	}


	public void setCdh(Boolean cdh) {
		this.cdh = cdh;
	}


	public String getComments() {
		return comments;
	}


	public void setComments(String comments) {
		this.comments = comments;
	}

	
	

}