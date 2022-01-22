package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the oralfeed_detail database table.
 * 
 */
@Entity
@Table(name="oralfeed_detail")
@NamedQuery(name="OralfeedDetail.findAll", query="SELECT o FROM OralfeedDetail o")
public class OralfeedDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long oralfeedid;

	private String babyfeedid;

	private Timestamp creationtime;

	@Column(columnDefinition="float4")
	private Float feedvolume;

	@Column(columnDefinition="float4")
	private Float totalFeedVolume;
	
	@Column(name="feedtype_id")
	private String feedtypeId;

	private Timestamp modificationtime;

	private String uhid;
	
	private Timestamp entrydatetime;

	public OralfeedDetail() {
	}

	public Long getOralfeedid() {
		return this.oralfeedid;
	}

	public void setOralfeedid(Long oralfeedid) {
		this.oralfeedid = oralfeedid;
	}

	public String getBabyfeedid() {
		return this.babyfeedid;
	}

	public void setBabyfeedid(String babyfeedid) {
		this.babyfeedid = babyfeedid;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	
	

	public Float getFeedvolume() {
		return feedvolume;
	}

	public void setFeedvolume(Float feedvolume) {
		this.feedvolume = feedvolume;
	}

	public String getFeedtypeId() {
		return this.feedtypeId;
	}

	public void setFeedtypeId(String feedtypeId) {
		this.feedtypeId = feedtypeId;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public Float getTotalFeedVolume() {
		return totalFeedVolume;
	}

	public void setTotalFeedVolume(Float totalFeedVolume) {
		this.totalFeedVolume = totalFeedVolume;
	}

	public Timestamp getEntrydatetime() {
		return entrydatetime;
	}

	public void setEntrydatetime(Timestamp entrydatetime) {
		this.entrydatetime = entrydatetime;
	}
	
	

}