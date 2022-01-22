package com.inicu.postgres.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * The persistent class for the sa_cardiac database table.
 * 
 */

@Entity
@Table(name="sa_cardiac")
@NamedQuery(name="SaCardiac.findAll", query="SELECT s FROM SaCardiac s")
public class SaCardiac implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long cardiacid;

	private String cardiactype;

	private String chd;

	private Timestamp creationtime;
	
	@Transient
	private String creationDate;

	private String durationofinotropes;

	private Timestamp modificationtime;

	@Column(columnDefinition="bool")
	private Boolean needforinotropes;

	private String othercardiacdysfn;

	private String otherchd;

	@Column(columnDefinition="bool")
	private Boolean shock;

	private String uhid;
	
	@Transient
	private boolean edit;
	
	private String loggeduser;
	
	private String typeofinotropes1;
	
	private String typeofinotropes2;
	
	@Transient
	private String chdValue;
	
	@Transient
	private String typeInotropeValue;
	
	private String comment;
	
	
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	public String getChdValue() {
		return chdValue;
	}

	public void setChdValue(String chdValue) {
		this.chdValue = chdValue;
	}

	public String getTypeInotropeValue() {
		return typeInotropeValue;
	}

	public void setTypeInotropeValue(String typeInotropeValue) {
		this.typeInotropeValue = typeInotropeValue;
	}

	public String getTypeofinotropes1() {
		return typeofinotropes1;
	}

	public void setTypeofinotropes1(String typeofinotropes1) {
		this.typeofinotropes1 = typeofinotropes1;
	}

	public String getTypeofinotropes2() {
		return typeofinotropes2;
	}

	public void setTypeofinotropes2(String typeofinotropes2) {
		this.typeofinotropes2 = typeofinotropes2;
	}

	public boolean isEdit() {
		return edit;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public SaCardiac() {
		this.cardiacid = null;
		this.cardiactype = "";
		this.chd = "";
		this.durationofinotropes = "";
		this.edit = false;
		this.needforinotropes = null;
		this.othercardiacdysfn = "";
		this.otherchd = "";
		this.shock = null;
	}

	public Long getCardiacid() {
		return this.cardiacid;
	}

	public void setCardiacid(Long cardiacid) {
		this.cardiacid = cardiacid;
	}

	public String getCardiactype() {
		return this.cardiactype;
	}

	public void setCardiactype(String cardiactype) {
		this.cardiactype = cardiactype;
	}

	public String getChd() {
		return this.chd;
	}

	public void setChd(String chd) {
		this.chd = chd;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getDurationofinotropes() {
		return this.durationofinotropes;
	}

	public void setDurationofinotropes(String durationofinotropes) {
		this.durationofinotropes = durationofinotropes;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public Boolean getNeedforinotropes() {
		return this.needforinotropes;
	}

	public void setNeedforinotropes(Boolean needforinotropes) {
		this.needforinotropes = needforinotropes;
	}

	public String getOthercardiacdysfn() {
		return this.othercardiacdysfn;
	}

	public void setOthercardiacdysfn(String othercardiacdysfn) {
		this.othercardiacdysfn = othercardiacdysfn;
	}

	public String getOtherchd() {
		return this.otherchd;
	}

	public void setOtherchd(String otherchd) {
		this.otherchd = otherchd;
	}

	public Boolean getShock() {
		return this.shock;
	}

	public void setShock(Boolean shock) {
		this.shock = shock;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}
	public String getLoggedUser() {
		return loggeduser;
	}

	public void setLoggedUser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	@Override
	public String toString() {
		
		String notes = "Cardiac [";
//		if(cardiacid!=null){
//			notes = notes = notes+", bloodculture:"+cardiacid;
//		}
//		
		if(cardiactype!=null){ 
			notes = notes = notes+", cardiactype:"+cardiactype;
		}
		
//		if(durationofinotropes!=null){    
//			notes = notes = notes+", durationofinotropes:"+durationofinotropes;
//		}
//		
//		if(needforinotropes!=null){  
//			 
//			notes = notes = notes+", needforinotropes:"+needforinotropes;
//		}
//		
//		if(othercardiacdysfn!=null){
//			notes = notes = notes+", othercardiacdysfn:"+othercardiacdysfn;
//		}
		
		if(otherchd!=null){
			notes = notes = notes+", otherchd:"+otherchd;
		}
		
		
//		if(shock!=null){   
//			notes = notes = notes+", shock:"+shock;
//		}
//		
//		
//		if(typeofinotropes1!=null){
//			notes = notes = notes+", typeofinotropes1:"+typeofinotropes1;
//		}
//		
//		
//		if(typeofinotropes2!=null){
//			notes = notes = notes+", typeofinotropes2:"+typeofinotropes2;
//		}
//		
		if(chdValue!=null){
			notes = notes = notes+", chdValue:"+chdValue;
		}
//		if(typeInotropeValue!=null){
//			notes = notes = notes+", typeInotropeValue:"+typeInotropeValue;
//		}
//		
		
		notes = notes+" ]";
		
		return notes;
		
		/*return "SaCardiac [cardiacid=" + cardiacid + ", cardiactype="
				+ cardiactype + ", chd=" + chd + ", creationtime="
				+ cardiactype creationtime creationDate durationofinotropes modificationtime needforinotropes othercardiacdysfn othercardiacdysfn
				otherchd shock typeofinotropes1 typeofinotropes2 chdValue typeInotropeValue+ ", creationDate=" + creationDate
				+ ", durationofinotropes=" + durationofinotropes
				+ ", modificationtime=" + modificationtime
				+ ", needforinotropes=" + needforinotropes
				+ ", othercardiacdysfn=" + othercardiacdysfn + ", otherchd="
				+ otherchd + ", shock=" + shock + ", uhid=" + uhid + ", edit="
				+ edit + ", loggeduser=" + loggeduser + ", typeofinotropes1="
				+ typeofinotropes1 + ", typeofinotropes2=" + typeofinotropes2
				+ ", chdValue=" + chdValue + ", typeInotropeValue="
				+ typeInotropeValue + "]";*/
	}

	
	
	
}