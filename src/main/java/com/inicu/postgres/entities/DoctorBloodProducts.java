package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The persistent class for the doctor_blood_products database table.
 * 
 */
@Entity
@Table(name = "doctor_blood_products")
@NamedQuery(name = "DoctorBloodProducts.findAll", query = "SELECT b FROM DoctorBloodProducts b")
public class DoctorBloodProducts implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long doctor_blood_products_id;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String uhid;

	private String status;

	private Timestamp assessment_time;

	private String blood_product;

	@Column(columnDefinition = "Float4")
	private Float indication_hb;

	private String indication_resp;

	@Column(columnDefinition = "bool")
	private Boolean apneic_spell;

	private Integer apnea_count;

	@Column(columnDefinition = "Float4")
	private Float platelet_count;

	private String bleeding;

	@Column(columnDefinition = "bool")
	private Boolean surgery;

	@Column(columnDefinition = "Float4")
	private Float ptt_value;

	@Column(columnDefinition = "Float4")
	private Float aptt_value;

	private Timestamp collection_date;

	private Timestamp expiry_date;

	private String bag_number;

	private String blood_group;

	private Integer bag_volume;

	private String checked_by;

	@Column(columnDefinition = "Float4")
	private Float blood_volume_kg;

	@Column(columnDefinition = "Float4")
	private Float total_volume;

	@Column(columnDefinition = "Float4")
	private Float infusion_time;

	@Column(columnDefinition = "Float4")
	private Float infusion_rate;

	private String venous_access;

	private String plan_test;

	@Column(columnDefinition = "Float4")
	private Float test_time;

	private String test_time_type;

	@Column(columnDefinition = "Float4")
	private Float vital_time;

	private String vital_time_type;

	private String cause;

	private String progress_notes;

	private String loggeduser;
	
	private Integer hematocrit;
	
	@Column(name = "blood_product_type")
	private String bloodProductType;
	
	@Column(columnDefinition = "bool", name = "shock_checked")
	private Boolean ShockChecked;
	
	@Column(columnDefinition = "bool", name = "ccf_checked")
	private Boolean CCFChecked;
	
	@Column(name = "procedure")
	private String Procedure;

	@Column(name = "hspda")
	private String HSPDA;
	
	@Column(name = "immune_thrombocytopenia")
	private String immuneThrombocytopenia;
	
	private Integer pti_value;
	
	private Integer inr_value;
		
	@Column(columnDefinition="bool")
	 private Boolean isIncludeInPN;
	
	@Transient
	List<BabyPrescription> prescriptionList;

	@Column(columnDefinition = "float4")
	private Integer ageatassesment;

	@Column(columnDefinition = "bool")
	private Boolean isageofassesmentinhours;
	
	@Column(name = "episode_number")
	private Integer episodeNumber;
	
	private Timestamp order_date;
	
	@Column(columnDefinition = "bool")
	private Boolean invasiveProcedures;
	
	@Column(columnDefinition = "bool")
	private Boolean ongoingBleeding;
	

	public DoctorBloodProducts() {
		super();
		this.apneic_spell = false;
		this.surgery = false;
		this.indication_resp = "";
		this.bleeding = "";
		this.blood_group = "";
		this.checked_by = "";
		this.venous_access = "peripheral";
		this.plan_test = "";
		this.test_time_type = "hour";
		this.vital_time_type = "hour";
		this.prescriptionList = new ArrayList<BabyPrescription>();
		this.isageofassesmentinhours = true;
	}
	
	
	public List<BabyPrescription> getPrescriptionList() {
		return prescriptionList;
	}


	public void setPrescriptionList(List<BabyPrescription> prescriptionList) {
		this.prescriptionList = prescriptionList;
	}


	public Long getDoctor_blood_products_id() {
		return doctor_blood_products_id;
	}

	public void setDoctor_blood_products_id(Long doctor_blood_products_id) {
		this.doctor_blood_products_id = doctor_blood_products_id;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getAssessment_time() {
		return assessment_time;
	}

	public void setAssessment_time(Timestamp assessment_time) {
		this.assessment_time = assessment_time;
	}

	public String getBlood_product() {
		return blood_product;
	}

	public void setBlood_product(String blood_product) {
		this.blood_product = blood_product;
	}

	public Float getIndication_hb() {
		return indication_hb;
	}

	public void setIndication_hb(Float indication_hb) {
		this.indication_hb = indication_hb;
	}

	public String getIndication_resp() {
		return indication_resp;
	}

	public void setIndication_resp(String indication_resp) {
		this.indication_resp = indication_resp;
	}

	public Boolean getApneic_spell() {
		return apneic_spell;
	}

	public void setApneic_spell(Boolean apneic_spell) {
		this.apneic_spell = apneic_spell;
	}

	public Integer getApnea_count() {
		return apnea_count;
	}

	public void setApnea_count(Integer apnea_count) {
		this.apnea_count = apnea_count;
	}

	public Float getPlatelet_count() {
		return platelet_count;
	}

	public void setPlatelet_count(Float platelet_count) {
		this.platelet_count = platelet_count;
	}

	public String getBleeding() {
		return bleeding;
	}

	public void setBleeding(String bleeding) {
		this.bleeding = bleeding;
	}

	public Boolean getSurgery() {
		return surgery;
	}

	public void setSurgery(Boolean surgery) {
		this.surgery = surgery;
	}

	public Float getPtt_value() {
		return ptt_value;
	}

	public void setPtt_value(Float ptt_value) {
		this.ptt_value = ptt_value;
	}

	public Float getAptt_value() {
		return aptt_value;
	}

	public void setAptt_value(Float aptt_value) {
		this.aptt_value = aptt_value;
	}

	public Timestamp getCollection_date() {
		return collection_date;
	}

	public void setCollection_date(Timestamp collection_date) {
		this.collection_date = collection_date;
	}

	public Timestamp getExpiry_date() {
		return expiry_date;
	}

	public void setExpiry_date(Timestamp expiry_date) {
		this.expiry_date = expiry_date;
	}

	public String getBag_number() {
		return bag_number;
	}

	public void setBag_number(String bag_number) {
		this.bag_number = bag_number;
	}

	public String getBlood_group() {
		return blood_group;
	}

	public void setBlood_group(String blood_group) {
		this.blood_group = blood_group;
	}

	public Integer getBag_volume() {
		return bag_volume;
	}

	public void setBag_volume(Integer bag_volume) {
		this.bag_volume = bag_volume;
	}

	public String getChecked_by() {
		return checked_by;
	}

	public void setChecked_by(String checked_by) {
		this.checked_by = checked_by;
	}

	public Float getBlood_volume_kg() {
		return blood_volume_kg;
	}

	public void setBlood_volume_kg(Float blood_volume_kg) {
		this.blood_volume_kg = blood_volume_kg;
	}

	public Float getTotal_volume() {
		return total_volume;
	}

	public void setTotal_volume(Float total_volume) {
		this.total_volume = total_volume;
	}

	public Float getInfusion_time() {
		return infusion_time;
	}

	public void setInfusion_time(Float infusion_time) {
		this.infusion_time = infusion_time;
	}

	public Float getInfusion_rate() {
		return infusion_rate;
	}

	public void setInfusion_rate(Float infusion_rate) {
		this.infusion_rate = infusion_rate;
	}

	public String getVenous_access() {
		return venous_access;
	}

	public void setVenous_access(String venous_access) {
		this.venous_access = venous_access;
	}

	public String getPlan_test() {
		return plan_test;
	}

	public void setPlan_test(String plan_test) {
		this.plan_test = plan_test;
	}

	public Float getTest_time() {
		return test_time;
	}

	public void setTest_time(Float test_time) {
		this.test_time = test_time;
	}

	public String getTest_time_type() {
		return test_time_type;
	}

	public void setTest_time_type(String test_time_type) {
		this.test_time_type = test_time_type;
	}

	public Float getVital_time() {
		return vital_time;
	}

	public void setVital_time(Float vital_time) {
		this.vital_time = vital_time;
	}

	public String getVital_time_type() {
		return vital_time_type;
	}

	public void setVital_time_type(String vital_time_type) {
		this.vital_time_type = vital_time_type;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getProgress_notes() {
		return progress_notes;
	}

	public void setProgress_notes(String progress_notes) {
		this.progress_notes = progress_notes;
	}

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public Integer getHematocrit() {
		return hematocrit;
	}

	public String getBloodProductType() {
		return bloodProductType;
	}

	public Boolean getShockChecked() {
		return ShockChecked;
	}

	public Boolean getCCFChecked() {
		return CCFChecked;
	}

	public String getProcedure() {
		return Procedure;
	}

	public String getHSPDA() {
		return HSPDA;
	}

	public String getImmuneThrombocytopenia() {
		return immuneThrombocytopenia;
	}

	public Integer getPti_value() {
		return pti_value;
	}

	public Integer getInr_value() {
		return inr_value;
	}

	public void setHematocrit(Integer hematocrit) {
		this.hematocrit = hematocrit;
	}

	public void setBloodProductType(String bloodProductType) {
		this.bloodProductType = bloodProductType;
	}

	public void setShockChecked(Boolean shockChecked) {
		ShockChecked = shockChecked;
	}

	public void setCCFChecked(Boolean cCFChecked) {
		CCFChecked = cCFChecked;
	}

	public void setProcedure(String procedure) {
		Procedure = procedure;
	}

	public void setHSPDA(String hSPDA) {
		HSPDA = hSPDA;
	}

	public void setImmuneThrombocytopenia(String immuneThrombocytopenia) {
		this.immuneThrombocytopenia = immuneThrombocytopenia;
	}

	public void setPti_value(Integer pti_value) {
		this.pti_value = pti_value;
	}

	public void setInr_value(Integer inr_value) {
		this.inr_value = inr_value;
	}
	
	public Boolean getIsIncludeInPN() {
		return isIncludeInPN;
	}

	public void setIsIncludeInPN(Boolean isIncludeInPN) {
		this.isIncludeInPN = isIncludeInPN;
	}
	

	public Integer getAgeatassesment() {
		return ageatassesment;
	}


	public void setAgeatassesment(Integer ageatassesment) {
		this.ageatassesment = ageatassesment;
	}


	public Boolean getIsageofassesmentinhours() {
		return isageofassesmentinhours;
	}


	public void setIsageofassesmentinhours(Boolean isageofassesmentinhours) {
		this.isageofassesmentinhours = isageofassesmentinhours;
	}


	public Integer getEpisodeNumber() {
		return episodeNumber;
	}


	public void setEpisodeNumber(Integer episodeNumber) {
		this.episodeNumber = episodeNumber;
	}


	public Timestamp getOrder_date() {
		return order_date;
	}


	public void setOrder_date(Timestamp order_date) {
		this.order_date = order_date;
	}

	public Boolean getInvasiveProcedures() {
		return invasiveProcedures;
	}


	public void setInvasiveProcedures(Boolean invasiveProcedures) {
		this.invasiveProcedures = invasiveProcedures;
	}


	public Boolean getOngoingBleeding() {
		return ongoingBleeding;
	}


	public void setOngoingBleeding(Boolean ongoingBleeding) {
		this.ongoingBleeding = ongoingBleeding;
	}


	@Override
	public String toString() {
		return "DoctorBloodProducts [doctor_blood_products_id=" + doctor_blood_products_id + ", creationtime="
				+ creationtime + ", modificationtime=" + modificationtime + ", uhid=" + uhid + ", status=" + status
				+ ", assessment_time=" + assessment_time + ", blood_product=" + blood_product + ", indication_hb="
				+ indication_hb + ", indication_resp=" + indication_resp + ", apneic_spell=" + apneic_spell
				+ ", apnea_count=" + apnea_count + ", platelet_count=" + platelet_count + ", bleeding=" + bleeding
				+ ", surgery=" + surgery + ", ptt_value=" + ptt_value + ", aptt_value=" + aptt_value
				+ ", collection_date=" + collection_date + ", expiry_date=" + expiry_date + ", bag_number=" + bag_number
				+ ", blood_group=" + blood_group + ", bag_volume=" + bag_volume + ", checked_by=" + checked_by
				+ ", blood_volume_kg=" + blood_volume_kg + ", total_volume=" + total_volume + ", infusion_time="
				+ infusion_time + ", infusion_rate=" + infusion_rate + ", venous_access=" + venous_access
				+ ", plan_test=" + plan_test + ", test_time=" + test_time + ", test_time_type=" + test_time_type
				+ ", vital_time=" + vital_time + ", vital_time_type=" + vital_time_type + ", cause=" + cause
				+ ", progress_notes=" + progress_notes + ", loggeduser=" + loggeduser + ", hematocrit=" + hematocrit
				+ ", bloodProductType=" + bloodProductType + ", ShockChecked=" + ShockChecked + ", CCFChecked="
				+ CCFChecked + ", Procedure=" + Procedure + ", HSPDA=" + HSPDA + ", immuneThrombocytopenia="
				+ immuneThrombocytopenia + ", pti_value=" + pti_value + ", inr_value=" + inr_value + ", isIncludeInPN="
				+ isIncludeInPN + ", prescriptionList=" + prescriptionList + ", ageatassesment=" + ageatassesment
				+ ", isageofassesmentinhours=" + isageofassesmentinhours + ", episodeNumber=" + episodeNumber
				+ ", order_date=" + order_date + ", invasiveProcedures=" + invasiveProcedures + ", ongoingBleeding="
				+ ongoingBleeding + "]";
	}

}
