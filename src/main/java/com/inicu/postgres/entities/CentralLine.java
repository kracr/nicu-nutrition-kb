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
 * The persistent class for the central_line database table.
 * 
 * @author Sourabh Verma
 */
@Entity
@Table(name = "central_line")
@NamedQuery(name = "CentralLine.findAll", query = "SELECT c FROM CentralLine c")
public class CentralLine implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "central_line_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long centralLineId;

	@Column(name = "adjust_comment")
	private String adjustComment;

	@Column(columnDefinition = "bool")
	private Boolean adjusted;

	private String brand;

	@Column(name = "central_line_position")
	private String centralLinePosition;

	@Column(name = "central_line_type")
	private String centralLineType;

	@Column(name = "confirm_xray", columnDefinition = "bool")
	private Boolean confirmXray;

	@Column(columnDefinition = "timestamp with time zone")
	private Timestamp creationtime;

	@Column(columnDefinition = "timestamp with time zone")
	private Timestamp insertion_timestamp;

	@Column(name = "limb_side")
	private String limbSide;

	@Column(name = "limb_site")
	private String limbSite;

	@Column(name = "limb_site_other")
	private String limbSiteOther;

	private String loggeduser;

	@Column(columnDefinition = "timestamp with time zone")
	private Timestamp modificationtime;

	private String progressnotes;

	@Column(name = "removal_reason")
	private String removalReason;

	@Column(columnDefinition = "timestamp with time zone")
	private Timestamp removal_timestamp;

	private String side;

	private String site;

	private String size;

	private String uhid;
	
	@Transient
	private String orderString;

	@Transient
	private Object insertion_date;

	@Transient
	private Integer insertion_hrs;

	@Transient
	private Integer insertion_mins;

	@Transient
	private String insertion_meridian;

	@Transient
	private boolean remove;

	@Transient
	private Object removal_date;

	@Transient
	private Integer removal_hrs;

	@Transient
	private Integer removal_mins;

	@Transient
	private String removal_meridian;
	
	private String comments;
	
	@Column(columnDefinition = "float4", name = "solution_type")
	private String solutionType;
	
	@Column(columnDefinition = "float4", name = "anti_coagulent_type")
	private String antiCoagulentType;
	
	@Column(columnDefinition = "float4", name = "anti_coagulent_brand")
	private String antiCoagulentBrand;
	
	@Column(columnDefinition = "float4", name = "heparin_strength")
	private Float heparinStrength;
	
	@Column(columnDefinition = "float4", name = "heparin_dose")
	private Float heparinDose;
	
	@Column(columnDefinition = "float4", name = "heparin_total_volume")
	private Float heparinTotalVolume;
	
	@Column(columnDefinition = "float4", name = "heparin_volume")
	private Float heparinVolume;
	
	@Column(columnDefinition = "float4", name = "heparin_rate")
	private Float heparinRate;
	
	@Column(columnDefinition = "float4", name = "heparin_overfill_volume")
	private Float heparinOverfillVolume;
	
	@Column(columnDefinition="bool")
	 private Boolean isIncludeInPN;
	
	@Column(name = "tip_culture_status")
	private String tipculture;
	
	public CentralLine() {
		super();
	}

	public Long getCentralLineId() {
		return this.centralLineId;
	}

	public void setCentralLineId(Long centralLineId) {
		this.centralLineId = centralLineId;
	}

	public String getAdjustComment() {
		return this.adjustComment;
	}

	public void setAdjustComment(String adjustComment) {
		this.adjustComment = adjustComment;
	}

	public Boolean getAdjusted() {
		return this.adjusted;
	}

	public void setAdjusted(Boolean adjusted) {
		this.adjusted = adjusted;
	}

	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCentralLinePosition() {
		return this.centralLinePosition;
	}

	public void setCentralLinePosition(String centralLinePosition) {
		this.centralLinePosition = centralLinePosition;
	}

	public String getCentralLineType() {
		return this.centralLineType;
	}

	public void setCentralLineType(String centralLineType) {
		this.centralLineType = centralLineType;
	}

	public Boolean getConfirmXray() {
		return this.confirmXray;
	}

	public void setConfirmXray(Boolean confirmXray) {
		this.confirmXray = confirmXray;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public Timestamp getInsertion_timestamp() {
		return insertion_timestamp;
	}

	public void setInsertion_timestamp(Timestamp insertion_timestamp) {
		this.insertion_timestamp = insertion_timestamp;
	}

	public String getLimbSide() {
		return this.limbSide;
	}

	public void setLimbSide(String limbSide) {
		this.limbSide = limbSide;
	}

	public String getLimbSite() {
		return this.limbSite;
	}

	public void setLimbSite(String limbSite) {
		this.limbSite = limbSite;
	}

	public String getLimbSiteOther() {
		return this.limbSiteOther;
	}

	public void setLimbSiteOther(String limbSiteOther) {
		this.limbSiteOther = limbSiteOther;
	}

	public String getLoggeduser() {
		return this.loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getProgressnotes() {
		return this.progressnotes;
	}

	public void setProgressnotes(String progressnotes) {
		this.progressnotes = progressnotes;
	}

	public String getRemovalReason() {
		return this.removalReason;
	}

	public void setRemovalReason(String removalReason) {
		this.removalReason = removalReason;
	}

	public Timestamp getRemoval_timestamp() {
		return removal_timestamp;
	}

	public void setRemoval_timestamp(Timestamp removal_timestamp) {
		this.removal_timestamp = removal_timestamp;
	}

	public String getSide() {
		return this.side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public String getSite() {
		return this.site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getSize() {
		return this.size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public Object getInsertion_date() {
		return insertion_date;
	}

	public void setInsertion_date(Object insertion_date) {
		this.insertion_date = insertion_date;
	}

	public Integer getInsertion_hrs() {
		return insertion_hrs;
	}

	public void setInsertion_hrs(Integer insertion_hrs) {
		this.insertion_hrs = insertion_hrs;
	}

	public Integer getInsertion_mins() {
		return insertion_mins;
	}

	public void setInsertion_mins(Integer insertion_mins) {
		this.insertion_mins = insertion_mins;
	}

	public String getInsertion_meridian() {
		return insertion_meridian;
	}

	public void setInsertion_meridian(String insertion_meridian) {
		this.insertion_meridian = insertion_meridian;
	}

	public boolean isRemove() {
		return remove;
	}

	public void setRemove(boolean remove) {
		this.remove = remove;
	}

	public Object getRemoval_date() {
		return removal_date;
	}

	public void setRemoval_date(Object removal_date) {
		this.removal_date = removal_date;
	}

	public Integer getRemoval_hrs() {
		return removal_hrs;
	}

	public void setRemoval_hrs(Integer removal_hrs) {
		this.removal_hrs = removal_hrs;
	}

	public Integer getRemoval_mins() {
		return removal_mins;
	}

	public void setRemoval_mins(Integer removal_mins) {
		this.removal_mins = removal_mins;
	}

	public String getRemoval_meridian() {
		return removal_meridian;
	}

	public void setRemoval_meridian(String removal_meridian) {
		this.removal_meridian = removal_meridian;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getSolutionType() {
		return solutionType;
	}

	public String getAntiCoagulentType() {
		return antiCoagulentType;
	}

	public String getAntiCoagulentBrand() {
		return antiCoagulentBrand;
	}

	public Float getHeparinStrength() {
		return heparinStrength;
	}

	public Float getHeparinDose() {
		return heparinDose;
	}

	public Float getHeparinTotalVolume() {
		return heparinTotalVolume;
	}

	public Float getHeparinVolume() {
		return heparinVolume;
	}

	public Float getHeparinRate() {
		return heparinRate;
	}

	public Float getHeparinOverfillVolume() {
		return heparinOverfillVolume;
	}

	public void setSolutionType(String solutionType) {
		this.solutionType = solutionType;
	}

	public void setAntiCoagulentType(String antiCoagulentType) {
		this.antiCoagulentType = antiCoagulentType;
	}

	public void setAntiCoagulentBrand(String antiCoagulentBrand) {
		this.antiCoagulentBrand = antiCoagulentBrand;
	}

	public void setHeparinStrength(Float heparinStrength) {
		this.heparinStrength = heparinStrength;
	}

	public void setHeparinDose(Float heparinDose) {
		this.heparinDose = heparinDose;
	}

	public void setHeparinTotalVolume(Float heparinTotalVolume) {
		this.heparinTotalVolume = heparinTotalVolume;
	}

	public void setHeparinVolume(Float heparinVolume) {
		this.heparinVolume = heparinVolume;
	}

	public void setHeparinRate(Float heparinRate) {
		this.heparinRate = heparinRate;
	}

	public void setHeparinOverfillVolume(Float heparinOverfillVolume) {
		this.heparinOverfillVolume = heparinOverfillVolume;
	}

	public String getOrderString() {
		return orderString;
	}

	public void setOrderString(String orderString) {
		this.orderString = orderString;
	}

	public Boolean getIsIncludeInPN() {
		return isIncludeInPN;
	}

	public void setIsIncludeInPN(Boolean isIncludeInPN) {
		this.isIncludeInPN = isIncludeInPN;
	}

	public String getTipculture() {
		return tipculture;
	}

	public void setTipculture(String tipculture) {
		this.tipculture = tipculture;
	}

	@Override
	public String toString() {
		return "CentralLine [centralLineId=" + centralLineId + ", adjustComment=" + adjustComment + ", adjusted="
				+ adjusted + ", brand=" + brand + ", centralLinePosition=" + centralLinePosition + ", centralLineType="
				+ centralLineType + ", confirmXray=" + confirmXray + ", creationtime=" + creationtime
				+ ", insertion_timestamp=" + insertion_timestamp + ", limbSide=" + limbSide + ", limbSite=" + limbSite
				+ ", limbSiteOther=" + limbSiteOther + ", loggeduser=" + loggeduser + ", modificationtime="
				+ modificationtime + ", progressnotes=" + progressnotes + ", removalReason=" + removalReason
				+ ", removal_timestamp=" + removal_timestamp + ", side=" + side + ", site=" + site + ", size=" + size
				+ ", uhid=" + uhid + ", orderString=" + orderString + ", insertion_date=" + insertion_date
				+ ", insertion_hrs=" + insertion_hrs + ", insertion_mins=" + insertion_mins + ", insertion_meridian="
				+ insertion_meridian + ", remove=" + remove + ", removal_date=" + removal_date + ", removal_hrs="
				+ removal_hrs + ", removal_mins=" + removal_mins + ", removal_meridian=" + removal_meridian
				+ ", comments=" + comments + ", solutionType=" + solutionType + ", antiCoagulentType="
				+ antiCoagulentType + ", antiCoagulentBrand=" + antiCoagulentBrand + ", heparinStrength="
				+ heparinStrength + ", heparinDose=" + heparinDose + ", heparinTotalVolume=" + heparinTotalVolume
				+ ", heparinVolume=" + heparinVolume + ", heparinRate=" + heparinRate + ", heparinOverfillVolume="
				+ heparinOverfillVolume + ", isIncludeInPN=" + isIncludeInPN + "]";
	}

}