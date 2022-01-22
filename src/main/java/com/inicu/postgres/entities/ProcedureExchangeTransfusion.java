package com.inicu.postgres.entities;
import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;

/**
 * The persistent class for the procedure_exchange_transfusion database table.
 * 
 */
@Entity
@Table(name="procedure_exchange_transfusion")
@NamedQuery(name="ProcedureExchangeTransfusion.findAll", query="SELECT p FROM ProcedureExchangeTransfusion p")
public class ProcedureExchangeTransfusion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="exchange_transfusion_id")
	private Long exchangeTransfusionId;

	private Timestamp creationtime;

	private Timestamp modificationtime;
	
	private String uhid;

	private String loggeduser;

	@Column(name="sajaundice_id")
	private Long sajaundiceId;

	@Column(name="working_weight",columnDefinition = "float4")
	private Float workingWeight;
	
	@Column(name="episode_number")
	private Integer episodeNumber;
	
	@Column(name="exchange_type")
	private String exchangeType;
	
	@Column(name="totalvolume",columnDefinition = "float4")
	private Float totalVolume;
	
	@Column(columnDefinition = "float4")
	private Float volume;
	
	private String route;
	
	@Column(name="route_central_type")
	private String routeCentralType;
	
	@Column(columnDefinition = "float4")
	private Float aliquot;

	private Integer cycles;
	
	@Column(name="volumein",columnDefinition = "float4")
	private Float volumeIn;
	
	@Column(name="volumeout",columnDefinition = "float4")
	private Float volumeOut;

	@Column(name="exchange_blood_group")
	private String exchangeBloodGroup;

	@Column(name="blood_product")
	private String bloodProduct;
	
	@Column(name="blood_product_1",columnDefinition = "float4")
	private Float bloodProduct1;

	@Column(name="blood_product_2",columnDefinition = "float4")
	private Float bloodProduct2;

	@Column(name="bag_no")
	private String bagNo;

	@Column(name="checkedby")
	private String checkedBy;
	
	@Column(name="collection_date")
	private Timestamp collectionDate;

	@Column(name="expiryDate")
	private Timestamp expiryDate;
	
	@Column(name="blood_bag_image")
	private String bloodBagImage;
	
	@Column(name="done_date")
	private String doneDate;
	
	@Column(name="time_in")
	private Timestamp timeIn;
	
	@Column(name="time_out")
	private Timestamp timeOut;
	
	private Integer spo2;
	
	private Integer hr;

	private String comment;
	
	@Column(name="baby_blood_group")
	private String babyBloodGroup;

	@Column(name="mother_blood_group")
	private String motherBloodGroup;
	
	public Long getExchangeTransfusionId() {
		return exchangeTransfusionId;
	}

	public void setExchangeTransfusionId(Long exchangeTransfusionId) {
		this.exchangeTransfusionId = exchangeTransfusionId;
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

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public Long getSajaundiceId() {
		return sajaundiceId;
	}

	public void setSajaundiceId(Long sajaundiceId) {
		this.sajaundiceId = sajaundiceId;
	}

	public Float getWorkingWeight() {
		return workingWeight;
	}

	public void setWorkingWeight(Float workingWeight) {
		this.workingWeight = workingWeight;
	}

	public Integer getEpisodeNumber() {
		return episodeNumber;
	}

	public void setEpisodeNumber(Integer episodeNumber) {
		this.episodeNumber = episodeNumber;
	}
	
	public String getExchangeType() {
		return exchangeType;
	}

	public void setExchangeType(String exchangeType) {
		this.exchangeType = exchangeType;
	}

	public Float getTotalVolume() {
		return totalVolume;
	}

	public void setTotalVolume(Float totalVolume) {
		this.totalVolume = totalVolume;
	}

	public Float getVolume() {
		return volume;
	}

	public void setVolume(Float volume) {
		this.volume = volume;
	}
	
	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getRouteCentralType() {
		return routeCentralType;
	}

	public void setRouteCentralType(String routeCentralType) {
		this.routeCentralType = routeCentralType;
	}

	public Float getAliquot() {
		return aliquot;
	}

	public Integer getCycles() {
		return cycles;
	}

	public void setCycles(Integer cycles) {
		this.cycles = cycles;
	}

	public void setAliquot(Float aliquot) {
		this.aliquot = aliquot;
	}

	public Float getVolumeIn() {
		return volumeIn;
	}

	public void setVolumeIn(Float volumeIn) {
		this.volumeIn = volumeIn;
	}

	public Float getVolumeOut() {
		return volumeOut;
	}

	public void setVolumeOut(Float volumeOut) {
		this.volumeOut = volumeOut;
	}

	public String getExchangeBloodGroup() {
		return exchangeBloodGroup;
	}

	public void setExchangeBloodGroup(String exchangeBloodGroup) {
		this.exchangeBloodGroup = exchangeBloodGroup;
	}

	public String getBloodProduct() {
		return bloodProduct;
	}

	public void setBloodProduct(String bloodProduct) {
		this.bloodProduct = bloodProduct;
	}

	public Float getBloodProduct1() {
		return bloodProduct1;
	}

	public void setBloodProduct1(Float bloodProduct1) {
		this.bloodProduct1 = bloodProduct1;
	}

	public Float getBloodProduct2() {
		return bloodProduct2;
	}

	public void setBloodProduct2(Float bloodProduct2) {
		this.bloodProduct2 = bloodProduct2;
	}

	public String getBagNo() {
		return bagNo;
	}

	public void setBagNo(String bagNo) {
		this.bagNo = bagNo;
	}

	public Timestamp getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Timestamp expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Timestamp getCollectionDate() {
		return collectionDate;
	}

	public void setCollectionDate(Timestamp collectionDate) {
		this.collectionDate = collectionDate;
	}

	public String getCheckedBy() {
		return checkedBy;
	}

	public void setCheckedBy(String checkedBy) {
		this.checkedBy = checkedBy;
	}

	public String getBloodBagImage() {
		return bloodBagImage;
	}

	public void setBloodBagImage(String bloodBagImage) {
		this.bloodBagImage = bloodBagImage;
	}

	public String getDoneDate() {
		return doneDate;
	}

	public void setDoneDate(String doneDate) {
		this.doneDate = doneDate;
	}

	public Timestamp getTimeIn() {
		return timeIn;
	}

	public void setTimeIn(Timestamp timeIn) {
		this.timeIn = timeIn;
	}
	
	public Timestamp getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(Timestamp timeOut) {
		this.timeOut = timeOut;
	}

	public Integer getSpo2() {
		return spo2;
	}

	public void setSpo2(Integer spo2) {
		this.spo2 = spo2;
	}

	public Integer getHr() {
		return hr;
	}

	public void setHr(Integer hr) {
		this.hr = hr;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getBabyBloodGroup() {
		return babyBloodGroup;
	}

	public void setBabyBloodGroup(String babyBloodGroup) {
		this.babyBloodGroup = babyBloodGroup;
	}

	public String getMotherBloodGroup() {
		return motherBloodGroup;
	}

	public void setMotherBloodGroup(String motherBloodGroup) {
		this.motherBloodGroup = motherBloodGroup;
	}
}
