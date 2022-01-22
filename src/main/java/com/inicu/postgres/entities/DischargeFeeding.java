package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the discharge_feeding database table.
 * 
 */
@Entity
@Table(name="discharge_feeding")
@NamedQuery(name="DischargeFeeding.findAll", query="SELECT d FROM DischargeFeeding d")
public class DischargeFeeding implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private Long feedingid;

	@Column(name="breastfeeds_days")
	private String breastfeedsDays;

	

	private Timestamp creationtime;

	private Timestamp modificationtime;

	@Column(name="ppn_days")
	private String ppnDays;

	@Column(name="tpn_days")
	private String tpnDays;

	@Column(name="tubefeeds_days")
	private String tubefeedsDays;

	private String uhid;

	@Column(name="watispoonfeeds_days")
	private String watispoonfeedsDays;

	public DischargeFeeding() {
	}

	public Long getFeedingid() {
		return this.feedingid;
	}

	public void setFeedingid(Long feedingid) {
		this.feedingid = feedingid;
	}

	public String getBreastfeedsDays() {
		return this.breastfeedsDays;
	}

	public void setBreastfeedsDays(String breastfeedsDays) {
		this.breastfeedsDays = breastfeedsDays;
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

	public String getPpnDays() {
		return this.ppnDays;
	}

	public void setPpnDays(String ppnDays) {
		this.ppnDays = ppnDays;
	}

	public String getTpnDays() {
		return this.tpnDays;
	}

	public void setTpnDays(String tpnDays) {
		this.tpnDays = tpnDays;
	}

	public String getTubefeedsDays() {
		return this.tubefeedsDays;
	}

	public void setTubefeedsDays(String tubefeedsDays) {
		this.tubefeedsDays = tubefeedsDays;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getWatispoonfeedsDays() {
		return this.watispoonfeedsDays;
	}

	public void setWatispoonfeedsDays(String watispoonfeedsDays) {
		this.watispoonfeedsDays = watispoonfeedsDays;
	}

}