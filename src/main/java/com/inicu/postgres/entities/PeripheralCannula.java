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
 * The persistent class for the peripheral_cannula database table.
 * 
 * @author Sourabh Verma
 */
@Entity
@Table(name = "peripheral_cannula")
@NamedQuery(name = "PeripheralCannula.findAll", query = "SELECT s FROM PeripheralCannula s")
public class PeripheralCannula implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long peripheral_cannula_id;

	@Column(columnDefinition = "timestamp with time zone")
	private Timestamp creationtime;

	private String uhid;

	private String site;

	private String limb_site;

	private String limb_site_other;

	private String limb_side;

	private String size;

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

	@Column(columnDefinition = "timestamp with time zone")
	private Timestamp removal_timestamp;

	private String removal_reason;

	private String loggeduser;

	private String progressnotes;
	
	private String comments;


	public PeripheralCannula() {
		super();
	}

	public Long getPeripheral_cannula_id() {
		return peripheral_cannula_id;
	}

	public void setPeripheral_cannula_id(Long peripheral_cannula_id) {
		this.peripheral_cannula_id = peripheral_cannula_id;
	}

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
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

	public String getLimb_site() {
		return limb_site;
	}

	public void setLimb_site(String limb_site) {
		this.limb_site = limb_site;
	}

	public String getLimb_site_other() {
		return limb_site_other;
	}

	public void setLimb_site_other(String limb_site_other) {
		this.limb_site_other = limb_site_other;
	}

	public String getLimb_side() {
		return limb_side;
	}

	public void setLimb_side(String limb_side) {
		this.limb_side = limb_side;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
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
		return "PeripheralCannula [peripheral_cannula_id="
				+ peripheral_cannula_id + ", creationtime=" + creationtime
				+ ", uhid=" + uhid + ", site=" + site + ", limb_site="
				+ limb_site + ", limb_site_other=" + limb_site_other
				+ ", limb_side=" + limb_side + ", size=" + size
				+ ", insertion_date=" + insertion_date + ", insertion_hrs="
				+ insertion_hrs + ", insertion_mins=" + insertion_mins
				+ ", insertion_meridian=" + insertion_meridian
				+ ", insertion_timestamp=" + insertion_timestamp + ", remove="
				+ remove + ", removal_date=" + removal_date + ", removal_hrs="
				+ removal_hrs + ", removal_mins=" + removal_mins
				+ ", removal_meridian=" + removal_meridian
				+ ", removal_timestamp=" + removal_timestamp
				+ ", removal_reason=" + removal_reason + ", loggeduser="
				+ loggeduser + ", progressnotes=" + progressnotes
				+ ", comments=" + comments + "]";
	}

}
