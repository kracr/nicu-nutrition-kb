package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "et_suction")
@NamedQuery(name = "EtSuction.findAll", query = "SELECT c FROM EtIntubation c")
public class EtSuction implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long et_suction_id;
	
	@Column(columnDefinition = "timestamp with time zone")
	private Timestamp creationtime;

	@Column(columnDefinition = "timestamp with time zone")
	private Timestamp modificationtime;

	private String uhid;
	
	private Integer size;
	
	private Integer length;
	
	@Column(name = "is_saline", columnDefinition = "bool")
	private boolean salineStatus;
	
	@Column(name = "saline_volume")
	private Integer salineVolume;
	
	@Column(name = "attempts_count")
	private Integer attemptsCount;
	
	@Column(name = "is_desaturation", columnDefinition = "bool")
	private boolean desaturationStatus;
	
	@Column(name = "min_spo2")
	private Integer minSpo2;
	
	@Column(name = "is_secretions", columnDefinition = "bool")
	private boolean secretionsStatus;
	
	@Column(name = "secretion_color")
	private String secretionColor;
	
	@Column(name = "secretion_volume")
	private Integer secretionVolume;
	
	@Column(name = "is_sent", columnDefinition = "bool")
	private boolean sentStatus;
	
	@Column(name = "lab_orders")
	private String labOrders;
	
	private String loggeduser;
	
	@Column(name = "progressnotes")
	private String progressnotes;
	
	@Column(name = "removal_timestamp")
	private Timestamp removal_timestamp;
	
	@Column(name = "removal_reason")
	private String removal_reason;
	
	@Transient
	private boolean remove;
	
	private String comments;
	
	private String site;
	
	private String consistency;
	
	@Column(name = "order_time", columnDefinition = "timestamp with time zone")
	private Timestamp orderTime;

	public EtSuction() {
		super();
	}
	
	public Long getEt_suction_id() {
		return et_suction_id;
	}

	public void setEt_suction_id(Long et_suction_id) {
		this.et_suction_id = et_suction_id;
	}

	public String getProgressnotes() {
		return progressnotes;
	}

	public void setProgressnotes(String progressnotes) {
		this.progressnotes = progressnotes;
	}

	public Timestamp getRemoval_timestamp() {
		return removal_timestamp;
	}

	public void setRemoval_timestamp(Timestamp removal_timestamp) {
		this.removal_timestamp = removal_timestamp;
	}

	public String getRemoval_reason() {
		return removal_reason;
	}

	public void setRemoval_reason(String removal_reason) {
		this.removal_reason = removal_reason;
	}

	public boolean isRemove() {
		return remove;
	}

	public void setRemove(boolean remove) {
		this.remove = remove;
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

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getSalineVolume() {
		return salineVolume;
	}

	public void setSalineVolume(Integer salineVolume) {
		this.salineVolume = salineVolume;
	}

	public String getConsistency() {
		return consistency;
	}

	public void setConsistency(String consistency) {
		this.consistency = consistency;
	}

	public Integer getAttemptsCount() {
		return attemptsCount;
	}

	public void setAttemptsCount(Integer attemptsCount) {
		this.attemptsCount = attemptsCount;
	}

	public Integer getMinSpo2() {
		return minSpo2;
	}

	public void setMinSpo2(Integer minSpo2) {
		this.minSpo2 = minSpo2;
	}

	public String getSecretionColor() {
		return secretionColor;
	}

	public void setSecretionColor(String secretionColor) {
		this.secretionColor = secretionColor;
	}

	public Integer getSecretionVolume() {
		return secretionVolume;
	}

	public void setSecretionVolume(Integer secretionVolume) {
		this.secretionVolume = secretionVolume;
	}

	public String getLabOrders() {
		return labOrders;
	}

	public void setLabOrders(String labOrders) {
		this.labOrders = labOrders;
	}

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public Timestamp getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public boolean isSalineStatus() {
		return salineStatus;
	}

	public void setSalineStatus(boolean salineStatus) {
		this.salineStatus = salineStatus;
	}

	public boolean isDesaturationStatus() {
		return desaturationStatus;
	}

	public void setDesaturationStatus(boolean desaturationStatus) {
		this.desaturationStatus = desaturationStatus;
	}

	public boolean isSecretionsStatus() {
		return secretionsStatus;
	}

	public void setSecretionsStatus(boolean secretionsStatus) {
		this.secretionsStatus = secretionsStatus;
	}

	public boolean isSentStatus() {
		return sentStatus;
	}

	public void setSentStatus(boolean sentStatus) {
		this.sentStatus = sentStatus;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}
	
}
