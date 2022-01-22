package com.inicu.postgres.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "increment_feed")
@NamedQuery(name = "IncrementFeed.findAll", query = "SELECT s FROM IncrementFeed s")
public class IncrementFeed implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long increment_feed_id;

	private Timestamp creationtime;
	
	private Timestamp modificationtime;
	
	@Column(columnDefinition = "Float4", name = "gir_value")
	private Float girValue;
	
	private String uhid;
	
	@Column(name = "baby_feed_id")
	private String babyfeedId;
	
	@Column(columnDefinition = "Float4", name = "en_feed")
	private Float enFeed;
	
	@Column(columnDefinition = "Float4", name = "pn_feed_rate")
	private Float pnFeedRate;
	
	@Column(name = "feed_date")
	private Timestamp feedDate;

	String loggeduser;
	
	public Float getEnFeed() {
		return enFeed;
	}
	public void setEnFeed(Float enFeed) {
		this.enFeed = enFeed;
	}
	public Float getPnFeedRate() {
		return pnFeedRate;
	}
	public void setPnFeedRate(Float pnFeedRate) {
		this.pnFeedRate = pnFeedRate;
	}
	public Timestamp getFeedDate() {
		return feedDate;
	}
	public void setFeedDate(Timestamp feedDate) {
		this.feedDate = feedDate;
	}
	public Long getIncrement_feed_id() {
		return increment_feed_id;
	}
	public void setIncrement_feed_id(Long increment_feed_id) {
		this.increment_feed_id = increment_feed_id;
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
	public Float getGirValue() {
		return girValue;
	}
	public void setGirValue(Float girValue) {
		this.girValue = girValue;
	}
	public String getUhid() {
		return uhid;
	}
	public void setUhid(String uhid) {
		this.uhid = uhid;
	}
	public String getBabyfeedId() {
		return babyfeedId;
	}
	public void setBabyfeedId(String babyfeedId) {
		this.babyfeedId = babyfeedId;
	}
	public String getLoggeduser() {
		return loggeduser;
	}
	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	
}