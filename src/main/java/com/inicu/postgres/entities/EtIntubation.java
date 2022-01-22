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
 * The persistent class for the et_intubation database table.
 * 
 * @author Sourabh Verma
 */
@Entity
@Table(name = "et_intubation")
@NamedQuery(name = "EtIntubation.findAll", query = "SELECT c FROM EtIntubation c")
public class EtIntubation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long et_intubation_id;

	@Column(columnDefinition = "timestamp with time zone")
	private Timestamp creationtime;

	@Column(columnDefinition = "timestamp with time zone")
	private Timestamp modificationtime;

	private String uhid;

	private String site;

	private String size;

	private String fixation;

	@Column(name = "confirm_xray", columnDefinition = "bool")
	private Boolean confirmXray;

	private String reposition;

	@Transient
	private Object insertion_date;

	@Transient
	private Integer insertion_hrs;

	@Transient
	private Integer insertion_mins;

	@Transient
	private String insertion_meridian;

	@Column(columnDefinition = "timestamp with time zone")
	private Timestamp insertion_timestamp;
	
	@Column(name = "isreintubation", columnDefinition = "bool")
	private Boolean isReintubation;
	
	@Column(name = "reasonintubation")
	private String reasonIntubation;

	@Column(name = "reasonreintubation")
	private String reasonReintubation;

	@Column(name = "PCO2_value")
	private Integer PCO2Value;

	@Column(name = "FiO2_value")
	private Integer FiO2Value;
	
	@Column(name = "isextubation", columnDefinition = "bool")
	private Boolean isextubation;

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

	@Column(name = "removal_reason")
	private String removalReason;

	@Column(columnDefinition = "timestamp with time zone")
	private Timestamp removal_timestamp;

	private String loggeduser;

	private String progressnotes;
	
	private String comments;

	public EtIntubation() {
		super();
	}

	public Long getEt_intubation_id() {
		return et_intubation_id;
	}

	public void setEt_intubation_id(Long et_intubation_id) {
		this.et_intubation_id = et_intubation_id;
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

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getFixation() {
		return fixation;
	}

	public void setFixation(String fixation) {
		this.fixation = fixation;
	}

	public Boolean getConfirmXray() {
		return confirmXray;
	}

	public void setConfirmXray(Boolean confirmXray) {
		this.confirmXray = confirmXray;
	}

	public String getReposition() {
		return reposition;
	}

	public void setReposition(String reposition) {
		this.reposition = reposition;
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

	public Timestamp getInsertion_timestamp() {
		return insertion_timestamp;
	}

	public void setInsertion_timestamp(Timestamp insertion_timestamp) {
		this.insertion_timestamp = insertion_timestamp;
	}

	public Boolean getIsReintubation() {
		return isReintubation;
	}

	public void setIsReintubation(Boolean isReintubation) {
		this.isReintubation = isReintubation;
	}

	public String getReasonIntubation() {
		return reasonIntubation;
	}

	public void setReasonIntubation(String reasonIntubation) {
		this.reasonIntubation = reasonIntubation;
	}

	public String getReasonReintubation() {
		return reasonReintubation;
	}

	public void setReasonReintubation(String reasonReintubation) {
		this.reasonReintubation = reasonReintubation;
	}

	public Integer getPCO2Value() {
		return PCO2Value;
	}

	public void setPCO2Value(Integer pCO2Value) {
		PCO2Value = pCO2Value;
	}

	public Integer getFiO2Value() {
		return FiO2Value;
	}

	public void setFiO2Value(Integer fiO2Value) {
		FiO2Value = fiO2Value;
	}

	public Boolean getIsextubation() {
		return isextubation;
	}

	public void setIsextubation(Boolean isextubation) {
		this.isextubation = isextubation;
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

	public String getRemovalReason() {
		return removalReason;
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

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public String getProgressnotes() {
		return progressnotes;
	}

	public void setProgressnotes(String progressnotes) {
		this.progressnotes = progressnotes;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "EtIntubation [et_intubation_id=" + et_intubation_id
				+ ", creationtime=" + creationtime + ", modificationtime="
				+ modificationtime + ", uhid=" + uhid + ", site=" + site
				+ ", size=" + size + ", fixation=" + fixation
				+ ", confirmXray=" + confirmXray + ", reposition=" + reposition
				+ ", insertion_date=" + insertion_date + ", insertion_hrs="
				+ insertion_hrs + ", insertion_mins=" + insertion_mins
				+ ", insertion_meridian=" + insertion_meridian
				+ ", insertion_timestamp=" + insertion_timestamp + ", remove="
				+ remove + ", removal_date=" + removal_date + ", removal_hrs="
				+ removal_hrs + ", removal_mins=" + removal_mins
				+ ", removal_meridian=" + removal_meridian + ", removalReason="
				+ removalReason + ", removal_timestamp=" + removal_timestamp
				+ ", loggeduser=" + loggeduser + ", progressnotes="
				+ progressnotes + ", comments=" + comments + "]";
	}

}
