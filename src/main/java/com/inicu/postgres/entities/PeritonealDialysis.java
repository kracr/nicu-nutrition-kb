package com.inicu.postgres.entities;

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

import com.inicu.models.KeyValueObj;

@Entity
@Table(name="peritoneal_dialysis")
@NamedQuery(name="PeritonealDialysis.findAll", query="SELECT s FROM PeritonealDialysis s")

public class PeritonealDialysis {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="peritoneal_dialysis_id")
	private Long peritonealDialysisId;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String uhid;

	private String indications;

	@Transient
	private List<KeyValueObj> indicationList;
	
	@Column(name="pre_procedure_care")
	private String preProcedureCare;

	@Transient
	private List<KeyValueObj> procedureCareList;
	
	@Column(name="catheter_type")
	private String catheterType;

	@Column(name="pd_fluid")
	private String pdFluid;

	@Column(name="pd_date_time")
	private Timestamp pdDateTime;

	@Column(name="pd_additives")
	private String pdAdditives;

	@Column(name="pd_volume_in",columnDefinition="float4")
	private Float pdVolumeIn;

	@Column(name="pd_volume_out",columnDefinition="float4")
	private Float pdVolumeOut;

	private String loggeduser;

	@Column(name="pd_volume_in_start_time")
	private Timestamp pdVolumeInStartTime;

	@Column(name="pd_volume_out_start_time")
	private Timestamp pdVolumeOutStartTime;

	@Column(name="pd_volume_in_finish_time")
	private Timestamp pdVolumeInFinishTime;

	@Column(name="pd_volume_out_finish_time")
	private Timestamp pdVolumeOutFinishTime;

	@Column(name="pd_dwell_time")
	private Integer pdDwellTime;

	@Column(name="pd_balance",columnDefinition="float4")
	private Float pdBalance;

	@Column(name="pd_ns",columnDefinition="float4")
	private Float pdNs;

	@Column(name="pd_d5_value",columnDefinition="float4")
	private Float pdD5Value;

	@Column(name="pd_nahco3_value",columnDefinition="float4")
	private Float pdNahco3Value;

	@Column(name="pd_total_volume",columnDefinition="float4")
	private Float pdTotalVolume;

	@Column(name="pd_additive_date_time")
	private Timestamp pdAdditiveDateTime;

	@Column(name="pd_heparin",columnDefinition="float4")
	private Float pdHeparin;

	@Column(name="pd_kcl",columnDefinition="float4")
	private Float pdKcl;

	@Column(name="pd_insulin",columnDefinition="float4")
	private Float pdInsulin;

	@Column(name="pd_dextrose",columnDefinition="float4")
	private Float pdDextrose;

	@Column(name="pd_others")
	private String pdOthers;
		
	private String brandName;
	
	@Column(columnDefinition="float4")
	private Float brandValue;
	
	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Float getBrandValue() {
		return brandValue;
	}

	public void setBrandValue(Float brandValue) {
		this.brandValue = brandValue;
	}

	public void setIndicationList(List<KeyValueObj> indicationList) {
		this.indicationList = indicationList;
	}

	public void setProcedureCareList(List<KeyValueObj> procedureCareList) {
		this.procedureCareList = procedureCareList;
	}

	public Long getPeritonealDialysisId() {
		return peritonealDialysisId;
	}

	public void setPeritonealDialysisId(Long peritonealDialysisId) {
		this.peritonealDialysisId = peritonealDialysisId;
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

	public String getIndications() {
		return indications;
	}

	public void setIndications(String indications) {
		this.indications = indications;
	}

	public String getPreProcedureCare() {
		return preProcedureCare;
	}

	public void setPreProcedureCare(String preProcedureCare) {
		this.preProcedureCare = preProcedureCare;
	}

	public String getCatheterType() {
		return catheterType;
	}

	public void setCatheterType(String catheterType) {
		this.catheterType = catheterType;
	}

	public String getPdFluid() {
		return pdFluid;
	}

	public void setPdFluid(String pdFluid) {
		this.pdFluid = pdFluid;
	}

	public Timestamp getPdDateTime() {
		return pdDateTime;
	}

	public void setPdDateTime(Timestamp pdDateTime) {
		this.pdDateTime = pdDateTime;
	}

	

	public String getPdAdditives() {
		return pdAdditives;
	}

	public void setPdAdditives(String pdAdditives) {
		this.pdAdditives = pdAdditives;
	}

	public Float getPdVolumeIn() {
		return pdVolumeIn;
	}

	public void setPdVolumeIn(Float pdVolumeIn) {
		this.pdVolumeIn = pdVolumeIn;
	}

	public Float getPdVolumeOut() {
		return pdVolumeOut;
	}

	public void setPdVolumeOut(Float pdVolumeOut) {
		this.pdVolumeOut = pdVolumeOut;
	}

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public Timestamp getPdVolumeInStartTime() {
		return pdVolumeInStartTime;
	}

	public void setPdVolumeInStartTime(Timestamp pdVolumeInStartTime) {
		this.pdVolumeInStartTime = pdVolumeInStartTime;
	}

	public Timestamp getPdVolumeOutStartTime() {
		return pdVolumeOutStartTime;
	}

	public void setPdVolumeOutStartTime(Timestamp pdVolumeOutStartTime) {
		this.pdVolumeOutStartTime = pdVolumeOutStartTime;
	}

	public Timestamp getPdVolumeInFinishTime() {
		return pdVolumeInFinishTime;
	}

	public void setPdVolumeInFinishTime(Timestamp pdVolumeInFinishTime) {
		this.pdVolumeInFinishTime = pdVolumeInFinishTime;
	}

	public Timestamp getPdVolumeOutFinishTime() {
		return pdVolumeOutFinishTime;
	}

	public void setPdVolumeOutFinishTime(Timestamp pdVolumeOutFinishTime) {
		this.pdVolumeOutFinishTime = pdVolumeOutFinishTime;
	}

	public Integer getPdDwellTime() {
		return pdDwellTime;
	}

	public void setPdDwellTime(Integer pdDwellTime) {
		this.pdDwellTime = pdDwellTime;
	}

	public Float getPdBalance() {
		return pdBalance;
	}

	public void setPdBalance(Float pdBalance) {
		this.pdBalance = pdBalance;
	}

	public Float getPdNs() {
		return pdNs;
	}

	public void setPdNs(Float pdNs) {
		this.pdNs = pdNs;
	}

	public Float getPdD5Value() {
		return pdD5Value;
	}

	public void setPdD5Value(Float pdD5Value) {
		this.pdD5Value = pdD5Value;
	}

	public Float getPdNahco3Value() {
		return pdNahco3Value;
	}

	public void setPdNahco3Value(Float pdNahco3Value) {
		this.pdNahco3Value = pdNahco3Value;
	}

	public Float getPdTotalVolume() {
		return pdTotalVolume;
	}

	public void setPdTotalVolume(Float pdTotalVolume) {
		this.pdTotalVolume = pdTotalVolume;
	}

	public Timestamp getPdAdditiveDateTime() {
		return pdAdditiveDateTime;
	}

	public void setPdAdditiveDateTime(Timestamp pdAdditiveDateTime) {
		this.pdAdditiveDateTime = pdAdditiveDateTime;
	}

	public Float getPdHeparin() {
		return pdHeparin;
	}

	public void setPdHeparin(Float pdHeparin) {
		this.pdHeparin = pdHeparin;
	}

	public Float getPdKcl() {
		return pdKcl;
	}

	public void setPdKcl(Float pdKcl) {
		this.pdKcl = pdKcl;
	}

	public Float getPdInsulin() {
		return pdInsulin;
	}

	public void setPdInsulin(Float pdInsulin) {
		this.pdInsulin = pdInsulin;
	}

	public Float getPdDextrose() {
		return pdDextrose;
	}

	public void setPdDextrose(Float pdDextrose) {
		this.pdDextrose = pdDextrose;
	}

	public String getPdOthers() {
		return pdOthers;
	}

	public void setPdOthers(String pdOthers) {
		this.pdOthers = pdOthers;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List getIndicationList() {
		return indicationList;
	}

	
	public List getProcedureCareList() {
		return procedureCareList;
	}

	
}
