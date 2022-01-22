package com.inicu.postgres.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the procedure_chesttube database table.
 * 
 */
@Entity
@Table(name="procedure_chesttube")
@NamedQuery(name="ProcedureChesttube.findAll", query="SELECT p FROM ProcedureChesttube p")
public class ProcedureChesttube implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String uhid;

	private String episodeid;

	private Timestamp inserteddate;
	private Timestamp modificationtime;
	private Timestamp creationtime;
	private Timestamp entrytime;
	private Timestamp removaldate;


	private String insertedfor;
	
	private String otherindication;

	private String size;

	private String sizeatfixation;

	private String tubeposition;

	private String comments;

	@Column(columnDefinition="bool")
	Boolean isTubeRemoved;

	@Column(columnDefinition="bool")
	private Boolean confirmedbyxray;

	@Column(name="ischesttube_left",columnDefinition="bool")
	private Boolean ischesttubeLeft;

	@Column(name="ischesttube_right",columnDefinition="bool")
	private Boolean ischesttubeRight;


	@Column(columnDefinition="float4")
	private Float repositionat;

	private String reasonofremoval;

	private String loggeduser;

	// Columns Added by me

	private String respneumothoraxid;

	@Column(name = "chesttube_value")
	private String chesttubeValue;

	@Column(name = "chesttube_adjusted_value")
	String chestTubeAdjustedValue;

	@Column(columnDefinition="bool")
	Boolean isTubeReadyToAdjust;

	@Column(columnDefinition="bool")
	Boolean isActive;

	private String clampStatus;

	@Transient
	private Boolean isClampReadyToAdjust;

	// Column added
	@Column(columnDefinition = "bool", name = "chesttube_insertion")
	private Boolean chesttubeInsertion;

	@Column(name = "chesttube_plan_time")
	private String chesttubePlanTime;

	@Column(name = "chesttube_minhrsdays")
	private String chesttubeMinhrsdays;

	@Column(name = "chesttube_date")
	private Date chesttubeDate;

	@Column(name = "chesttube_time")
	private String chesttubeTime;
	// End

	// end of the section

	public ProcedureChesttube() {
		this.isTubeReadyToAdjust = false;
		this.isActive = true;
		this.isTubeRemoved=false;
	}

	public String getResppneumothoraxid() {
		return respneumothoraxid;
	}

	public void setResppneumothoraxid(String resppneumothoraxid) {
		this.respneumothoraxid = resppneumothoraxid;
	}

	public String getChesttubeValue() {
		return chesttubeValue;
	}

	public void setChesttubeValue(String chesttubeValue) {
		this.chesttubeValue = chesttubeValue;
	}

	public String getChestTubeAdjustedValue() {
		return chestTubeAdjustedValue;
	}

	public void setChestTubeAdjustedValue(String chestTubeAdjustedValue) {
		this.chestTubeAdjustedValue = chestTubeAdjustedValue;
	}

	public Boolean getIsTubeReadyToAdjust() {
		return isTubeReadyToAdjust;
	}

	public void setIsTubeReadyToAdjust(Boolean tubeReadyToAdjust) {
		isTubeReadyToAdjust = tubeReadyToAdjust;
	}

	public Boolean getIsTubeRemoved() {
		return isTubeRemoved;
	}

	public void setIsTubeRemoved(Boolean tubeRemoved) {
		isTubeRemoved = tubeRemoved;
	}

	public Boolean getActive() {
		return this.isActive;
	}

	public void setActive(Boolean active) {
		this.isActive = active;
	}

	public String getClampStatus() {
		return clampStatus;
	}

	public void setClampStatus(String clampStatus) {
		this.clampStatus = clampStatus;
	}

	public Boolean getIsClampReadyToAdjust() {
		return isClampReadyToAdjust;
	}

	public void setIsClampReadyToAdjust(Boolean clampReadyToAdjust) {
		isClampReadyToAdjust = clampReadyToAdjust;
	}

	public Boolean getIsChesttubeInsertion() {
		return chesttubeInsertion;
	}

	public void setIsChesttubeInsertion(Boolean chesttubeInsertion) {
		this.chesttubeInsertion = chesttubeInsertion;
	}

	public String getChesttubePlanTime() {
		return chesttubePlanTime;
	}

	public void setChesttubePlanTime(String chesttubePlanTime) {
		this.chesttubePlanTime = chesttubePlanTime;
	}

	public String getChesttubeMinhrsdays() {
		return chesttubeMinhrsdays;
	}

	public void setChesttubeMinhrsdays(String chesttubeMinhrsdays) {
		this.chesttubeMinhrsdays = chesttubeMinhrsdays;
	}

	public Date getChesttubeDate() {
		return chesttubeDate;
	}

	public void setChesttubeDate(Date chesttubeDate) {
		this.chesttubeDate = chesttubeDate;
	}

	public String getChesttubeTime() {
		return chesttubeTime;
	}

	public void setChesttubeTime(String chesttubeTime) {
		this.chesttubeTime = chesttubeTime;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getConfirmedbyxray() {
		return this.confirmedbyxray;
	}

	public void setConfirmedbyxray(Boolean confirmedbyxray) {
		this.confirmedbyxray = confirmedbyxray;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public Timestamp getEntrytime() {
		return this.entrytime;
	}

	public void setEntrytime(Timestamp entrytime) {
		this.entrytime = entrytime;
	}

	public String getEpisodeid() {
		return this.episodeid;
	}

	public void setEpisodeid(String episodeid) {
		this.episodeid = episodeid;
	}

	public Timestamp getInserteddate() {
		return this.inserteddate;
	}

	public void setInserteddate(Timestamp inserteddate) {
		this.inserteddate = inserteddate;
	}

	public String getInsertedfor() {
		return this.insertedfor;
	}

	public void setInsertedfor(String insertedfor) {
		this.insertedfor = insertedfor;
	}

	public Boolean getIschesttubeLeft() {
		return this.ischesttubeLeft;
	}

	public void setIschesttubeLeft(Boolean ischesttubeLeft) {
		this.ischesttubeLeft = ischesttubeLeft;
	}

	public Boolean getIschesttubeRight() {
		return this.ischesttubeRight;
	}

	public void setIschesttubeRight(Boolean ischesttubeRight) {
		this.ischesttubeRight = ischesttubeRight;
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

	public String getReasonofremoval() {
		return this.reasonofremoval;
	}

	public void setReasonofremoval(String reasonofremoval) {
		this.reasonofremoval = reasonofremoval;
	}

	public Timestamp getRemovaldate() {
		return this.removaldate;
	}

	public void setRemovaldate(Timestamp removaldate) {
		this.removaldate = removaldate;
	}

	public Float getRepositionat() {
		return this.repositionat;
	}

	public void setRepositionat(Float repositionat) {
		this.repositionat = repositionat;
	}

	public String getSize() {
		return this.size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getSizeatfixation() {
		return this.sizeatfixation;
	}

	public void setSizeatfixation(String sizeatfixation) {
		this.sizeatfixation = sizeatfixation;
	}

	public String getTubeposition() {
		return this.tubeposition;
	}

	public void setTubeposition(String tubeposition) {
		this.tubeposition = tubeposition;
	}

	public String getUhid() {
		return this.uhid;
	}
	
	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getOtherindication() {
		return otherindication;
	}

	public void setOtherindication(String otherindication) {
		this.otherindication = otherindication;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	

}