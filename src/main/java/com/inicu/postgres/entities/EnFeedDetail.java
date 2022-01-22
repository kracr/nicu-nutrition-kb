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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The persistent class for the en_feed_detail database table.
 * 
 */
@Entity
@Table(name = "en_feed_detail")
@NamedQuery(name = "EnFeedDetail.findAll", query = "SELECT b FROM EnFeedDetail b")
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnFeedDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long en_feed_detail_id;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private Long babyfeedid;

	@Column(columnDefinition = "float4")
	private Float feed_volume;

	private Integer no_of_feed;

	private String uhid;

	public EnFeedDetail() {
		super();
	}

	public Long getEn_feed_detail_id() {
		return en_feed_detail_id;
	}

	public void setEn_feed_detail_id(Long en_feed_detail_id) {
		this.en_feed_detail_id = en_feed_detail_id;
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

	public Long getBabyfeedid() {
		return babyfeedid;
	}

	public void setBabyfeedid(Long babyfeedid) {
		this.babyfeedid = babyfeedid;
	}

	public Float getFeed_volume() {
		return feed_volume;
	}

	public void setFeed_volume(Float feed_volume) {
		this.feed_volume = feed_volume;
	}

	public Integer getNo_of_feed() {
		return no_of_feed;
	}

	public void setNo_of_feed(Integer no_of_feed) {
		this.no_of_feed = no_of_feed;
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	@Override
	public String toString() {
		return "EnFeedDetail [en_feed_detail_id=" + en_feed_detail_id + ", creationtime=" + creationtime
				+ ", modificationtime=" + modificationtime + ", babyfeedid=" + babyfeedid + ", feed_volume="
				+ feed_volume + ", no_of_feed=" + no_of_feed + "]";
	}

}
