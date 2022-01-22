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
* The persistent class for the sa_feedgrowth database table.
* 
*/
@Entity
@Table(name="sa_feedgrowth")
@NamedQuery(name="SaFeedgrowth.findAll", query="SELECT s FROM SaFeedgrowth s")
public class SaFeedgrowth implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long feedgrowthid;

	private String ageatfullfeed;

	private String centileatbirth;

	private String centileatdischarge;

	private Timestamp creationtime;
	
	@Transient
	private String creationDate;

	@Column(columnDefinition="bool")
	private Boolean feedingintolerance;

	private String feedstartdays;

	private String feedstoday;

	private String minimumweight;

	private Timestamp modificationtime;

	@Column(columnDefinition="bool")
	private Boolean tpn;

	@Column(columnDefinition="bool")
	private Boolean tropicfeeds;

	private String uhid;
	private String loggeduser;
	
	@Transient
	private boolean edit;
	
	private String comment;
	
	
	
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

	public SaFeedgrowth() {
		this.ageatfullfeed="";
		this.centileatbirth="";
		this.centileatdischarge="";
		this.creationDate="";
		this.feedgrowthid=null;
		this.feedingintolerance=null;
		this.feedstartdays="";
		this.feedstoday="";
		this.minimumweight="";
		this.tpn= null;
		this.tropicfeeds=null;
	}

	public Long getFeedgrowthid() {
		return this.feedgrowthid;
	}

	public void setFeedgrowthid(Long feedgrowthid) {
		this.feedgrowthid = feedgrowthid;
	}

	public String getAgeatfullfeed() {
		return this.ageatfullfeed;
	}

	public void setAgeatfullfeed(String ageatfullfeed) {
		this.ageatfullfeed = ageatfullfeed;
	}

	public String getCentileatbirth() {
		return this.centileatbirth;
	}

	public void setCentileatbirth(String centileatbirth) {
		this.centileatbirth = centileatbirth;
	}

	public String getCentileatdischarge() {
		return this.centileatdischarge;
	}

	public void setCentileatdischarge(String centileatdischarge) {
		this.centileatdischarge = centileatdischarge;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public Boolean getFeedingintolerance() {
		return this.feedingintolerance;
	}

	public void setFeedingintolerance(Boolean feedingintolerance) {
		this.feedingintolerance = feedingintolerance;
	}

	public String getFeedstartdays() {
		return this.feedstartdays;
	}

	public void setFeedstartdays(String feedstartdays) {
		this.feedstartdays = feedstartdays;
	}

	public String getFeedstoday() {
		return this.feedstoday;
	}

	public void setFeedstoday(String feedstoday) {
		this.feedstoday = feedstoday;
	}

	public String getMinimumweight() {
		return this.minimumweight;
	}

	public void setMinimumweight(String minimumweight) {
		this.minimumweight = minimumweight;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public Boolean getTpn() {
		return this.tpn;
	}

	public void setTpn(Boolean tpn) {
		this.tpn = tpn;
	}

	public Boolean getTropicfeeds() {
		return this.tropicfeeds;
	}

	public void setTropicfeeds(Boolean tropicfeeds) {
		this.tropicfeeds = tropicfeeds;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getLoggedUser() {
		return loggeduser;
	}

	public void setLoggedUser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	@Override
	public String toString() {
		
		String notes = "Feed Growth [";
		
		if(ageatfullfeed!=null){
			notes = notes+", age at full feed:"+ageatfullfeed;
		}
		
//		if(centileatbirth!=null){
//			notes = notes+", centile at birth:"+centileatbirth;
//		}
//		
//		if(centileatdischarge!=null){
//			notes = notes+", centile at discharge:"+centileatdischarge;
//		}
//		
//		if(feedingintolerance!=null){
//			notes = notes+", feeding intolerance:"+feedingintolerance;
//		}
		
		if(feedstartdays!=null){
			notes = notes+", feed start days:"+feedstartdays;
		}
		

		if(feedstoday!=null){
			notes = notes+", feeds today:"+feedstoday;
		}
		
//		if(minimumweight!=null){
//			notes = notes+", minimum weight:"+minimumweight;
//		}
//		
//		if(tpn!=null){
//			notes = notes+", TPN:"+tpn;
//		}
//		
//		if(tropicfeeds!=null){
//			notes = notes+", tropic feeds:"+tropicfeeds;
//		}
//		
		notes = notes+" ]";
		return notes;
//		return "SaFeedgrowth [feedgrowthid=" + feedgrowthid
//				+ ", ageatfullfeed=" + ageatfullfeed + ", centileatbirth="
//				+ centileatbirth + ", centileatdischarge=" + centileatdischarge
//				+ ", creationtime=" + creationtime + ", creationDate="
//				+ creationDate + ", feedingintolerance=" + feedingintolerance
//				+ ", feedstartdays=" + feedstartdays + ", feedstoday="
//				+ feedstoday + ", minimumweight=" + minimumweight
//				+ ", modificationtime=" + modificationtime + ", tpn=" + tpn
//				+ ", tropicfeeds=" + tropicfeeds + ", uhid=" + uhid
//				+ ", loggeduser=" + loggeduser + ", edit=" + edit + "]";
	}
	
	
}
