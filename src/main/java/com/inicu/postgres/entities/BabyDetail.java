package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The persistent class for the baby_detail database table.
 * 
 */
@Entity
@Table(name = "baby_detail")
@NamedQuery(name = "BabyDetail.findAll", query = "SELECT b FROM BabyDetail b")
@JsonIgnoreProperties(ignoreUnknown = true)
public class BabyDetail<currentdateweight> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long babydetailid;

	@Column(columnDefinition = "bool")
	private Boolean activestatus;

	@Column(columnDefinition = "bool")
	private Boolean admissionstatus;

	@Column(name = "same_anthropometry_details",columnDefinition="bool")
	private Boolean sameAnthropometryDetails;
	
	private String typeofadmission;

	@Column(name = "inout_patient_status")
	private String inoutPatientStatus;

	@Column(name = "admission_head_circumference",columnDefinition = "float4")
	private Float admissionHeadCircumference;
	
	@Column(name = "admission_length",columnDefinition = "float4")
	private Float admissionLength;
	
	@Column(name = "admission_weight_centile",columnDefinition = "float4")
	private Float admissionWeightCentile;
	
	@Column(name = "admission_length_centile",columnDefinition = "float4")
	private Float admissionLengthCentile;
	
	@Column(name = "admission_hc_centile",columnDefinition = "float4")
	private Float admissionHCcentile;
	
	@Column(name = "admission_weight_galevel")
	private String admissionWeightGAlevel;

	@Column(name = "admission_hc_galevel")
	private String admissionHCgaLevel;
	
	@Column(name = "admission_length_galevel")
	private String admissionLengthGaLevel;

	private Integer actualgestationdays;

	private Integer actualgestationweek;

	@Column(name = "admission_bp")
	private String admissionBp;

	@Column(name = "admission_pulserate")
	private String admissionPulserate;

	@Column(name = "admission_rr")
	private String admissionRr;

	@Column(name = "admission_spo2")
	private String admissionSpo2;

	@Column(name = "admission_temp")
	private String admissionTemp;

	private String admittingdoctor;

	private String iptagno;

	private String babyname;

	private String cry;

	private String timeofcry;

	@Column(columnDefinition = "float4")
	private Float birthheadcircumference;

	@Column(columnDefinition = "bool")
	private Boolean birthinjuries;

	@Column(name = "birthinjuries_comments")
	private String birthinjuriesComments;

	@Column(columnDefinition = "float4")
	private Float birthlength;

	@Column(columnDefinition = "bool")
	private Boolean birthmarks;

	@Column(name = "birthmarks_comments")
	private String birthmarksComments;

	@Column(columnDefinition = "float4")
	private Float birthweight;

	@Column(columnDefinition = "float4")
	private Float admissionWeight;

	private String bloodgroup;

	private String comments;

	private Timestamp creationtime;
	
	@Column(name = "recommended_status")
	private String recommendedStatus;

	@Transient
	private Object doaObj;

	@Transient
	private String doaStr;

	@Temporal(TemporalType.DATE)
	private Date dateofadmission;

	private String timeofadmission;

	@Transient
	private Object dobObj;

	@Transient
	private String dobStr;

	@Temporal(TemporalType.DATE)
	private Date dateofbirth;

	private String dayoflife;

	@Column(columnDefinition = "bool")
	private Boolean dayoflifetype;

	@Column(name = "is_respiratory_support",columnDefinition = "bool")
	private Boolean isRespiratorySupport;
	
	private String timeofbirth;

	private Timestamp dischargeddate;

	private String dischargestatus;

	private String examinationid;

	private String gender;

	private Integer gestationdaysbylmp;

	private Integer gestationweekbylmp;

	private String loggeduser;

	private Timestamp modificationtime;

	private String nicuroomno;

	private String nicubedno;

	private String niculevelno;

	private String criticalitylevel;

	private String refferedfrom;

	private String refferedby;

	@Column(name = "reffered_number")
	private String refferedNumber;

	private String modeoftransportation;

	private String sourceoftransportation;

	private String reasonofreference;

	private String courseinrefferinghospital;

	private String transportationalongwith;

	private String uhid;
	
	private String ipnumber;

	private String episodeid;

	// status at admission
	private String consciousness;

	private String weight_galevel;

	private String length_galevel;

	private String hc_galevel;

	@Column(columnDefinition = "float4")
	private Float weight_centile;

	@Column(columnDefinition = "float4")
	private Float length_centile;

	@Column(columnDefinition = "float4")
	private Float hc_centile;

	private Integer ponderal_index;

	@Column(columnDefinition = "float4")
	private Float central_temp;

	@Column(columnDefinition = "float4")
	private Float peripheral_temp;

	private Integer hr;

	private Integer rr;

	private Integer spo2;

	// new Columns added
	private String bp_type;

	private Integer bp_systolic;

	private Integer bp_diastolic;

	private Integer bp_mean;

	private Integer bp_lll_systolic;
	private Integer bp_lll_diastolic;
	private Integer bp_lll_mean;

	private Integer bp_rll_systolic;
	private Integer bp_rll_diastolic;
	private Integer bp_rll_mean;

	private Integer bp_lul_systolic;
	private Integer bp_lul_diastolic;
	private Integer bp_lul_mean;

	private Integer bp_rul_systolic;
	private Integer bp_rul_diastolic;
	private Integer bp_rul_mean;

	private Integer bp_rul;
	private Integer bp_lul;
	private Integer bp_rll;
	private Integer bp_lll;

	private Integer crt;


	// End of BP section

	private String downesscoreid;

	@Column(columnDefinition = "bool")
	private Boolean isassessmentsubmit;

	@Column(name = "baby_type")
	private String babyType;

	@Column(name = "baby_number")
	private String babyNumber;

	@Column(columnDefinition = "bool")
	private Boolean isreadmitted;

	@Column(name = "four_limb")
	private String fourLimb;
	
	private String branchname;
	
	@Transient
	private String timeofreadmission;

	private Timestamp hisdischargedate;

	private String hisdischargestatus;

	@Temporal(TemporalType.DATE)
	private Date receivingdate;

	private String receivingtime;
	
	@Column(name = "gestation_quartile", columnDefinition = "bool")
	private Boolean gestationQuartile;
	
	@Column(name = "energy_deviation", columnDefinition = "bool")
	private Boolean energyDeviation;
	
	@Column(name = "protein_deviation", columnDefinition = "bool")
	private Boolean proteinDeviation;
	
	@Column(name = "medication_deviation")
	private Integer medicationDeviation;
	
	@Column(columnDefinition = "bool")
	private Boolean monoamniotic;

	@Column(name  = "audio_enabled", columnDefinition = "bool")
	private Boolean audioEnabled;

	@Transient private String bedNumber;
	@Transient private String roomNumber;
	@Transient private String condition;
	@Transient private Float currentdateweight;
	@Transient private Float prevDateWeight;

	@Transient private Timestamp dateTimeOfBirth;
	@Transient private Timestamp dateTimeOfAdmission;
	
	@Column(columnDefinition = "float4")
	private Float energy_deviation_value;
	
	@Column(columnDefinition = "float4")
	private Float protein_deviation_value;
	
	@Column(columnDefinition = "float4")
	private Float gestation_quartile_value;
	
	public BabyDetail() {
		super();
		this.isreadmitted = false;
		this.dayoflifetype = true;
	}

	public void setSameAnthropometryDetails(Boolean same_anthropometry_details)
	{
	  this.sameAnthropometryDetails=same_anthropometry_details;
	}

	public Integer getBp_lll_systolic() {
		return bp_lll_systolic;
	}

	public void setBp_lll_systolic(Integer bp_lll_systolic) {
		this.bp_lll_systolic = bp_lll_systolic;
	}

	public Integer getBp_lll_diastolic() {
		return bp_lll_diastolic;
	}

	public void setBp_lll_diastolic(Integer bp_lll_diastolic) {
		this.bp_lll_diastolic = bp_lll_diastolic;
	}

	public Integer getBp_lll_mean() {
		return bp_lll_mean;
	}

	public void setBp_lll_mean(Integer bp_lll_mean) {
		this.bp_lll_mean = bp_lll_mean;
	}

	public Integer getBp_rll_systolic() {
		return bp_rll_systolic;
	}

	public void setBp_rll_systolic(Integer bp_rll_systolic) {
		this.bp_rll_systolic = bp_rll_systolic;
	}

	public Integer getBp_rll_diastolic() {
		return bp_rll_diastolic;
	}

	public void setBp_rll_diastolic(Integer bp_rll_diastolic) {
		this.bp_rll_diastolic = bp_rll_diastolic;
	}

	public Integer getBp_rll_mean() {
		return bp_rll_mean;
	}

	public void setBp_rll_mean(Integer bp_rll_mean) {
		this.bp_rll_mean = bp_rll_mean;
	}

	public Integer getBp_lul_systolic() {
		return bp_lul_systolic;
	}

	public void setBp_lul_systolic(Integer bp_lul_systolic) {
		this.bp_lul_systolic = bp_lul_systolic;
	}

	public Integer getBp_lul_diastolic() {
		return bp_lul_diastolic;
	}

	public void setBp_lul_diastolic(Integer bp_lul_diastolic) {
		this.bp_lul_diastolic = bp_lul_diastolic;
	}

	public Integer getBp_lul_mean() {
		return bp_lul_mean;
	}

	public void setBp_lul_mean(Integer bp_lul_mean) {
		this.bp_lul_mean = bp_lul_mean;
	}

	public Integer getBp_rul_systolic() {
		return bp_rul_systolic;
	}

	public void setBp_rul_systolic(Integer bp_rul_systolic) {
		this.bp_rul_systolic = bp_rul_systolic;
	}

	public Integer getBp_rul_diastolic() {
		return bp_rul_diastolic;
	}

	public void setBp_rul_diastolic(Integer bp_rul_diastolic) {
		this.bp_rul_diastolic = bp_rul_diastolic;
	}

	public Integer getBp_rul_mean() {
		return bp_rul_mean;
	}

	public void setBp_rul_mean(Integer bp_rul_mean) {
		this.bp_rul_mean = bp_rul_mean;
	}

	public Boolean getSameAnthropometryDetails()
	{
	  return this.sameAnthropometryDetails;
	}

	public Long getBabydetailid() {
		return this.babydetailid;
	}

	public void setBabydetailid(Long babydetailid) {
		this.babydetailid = babydetailid;
	}

	public Boolean getActivestatus() {
		return this.activestatus;
	}

	public void setActivestatus(Boolean activestatus) {
		this.activestatus = activestatus;
	}

	public Integer getActualgestationdays() {
		return this.actualgestationdays;
	}

	public void setActualgestationdays(Integer actualgestationdays) {
		this.actualgestationdays = actualgestationdays;
	}



	public Integer getActualgestationweek() {
		return this.actualgestationweek;
	}

	public void setActualgestationweek(Integer actualgestationweek) {
		this.actualgestationweek = actualgestationweek;
	}

	public String getAdmissionBp() {
		return this.admissionBp;
	}

	public void setAdmissionBp(String admissionBp) {
		this.admissionBp = admissionBp;
	}

	public String getAdmissionPulserate() {
		return this.admissionPulserate;
	}

	public void setAdmissionPulserate(String admissionPulserate) {
		this.admissionPulserate = admissionPulserate;
	}

	public String getAdmissionRr() {
		return this.admissionRr;
	}

	public void setAdmissionRr(String admissionRr) {
		this.admissionRr = admissionRr;
	}

	public String getAdmissionSpo2() {
		return this.admissionSpo2;
	}

	public void setAdmissionSpo2(String admissionSpo2) {
		this.admissionSpo2 = admissionSpo2;
	}

	public String getAdmissionTemp() {
		return this.admissionTemp;
	}

	public void setAdmissionTemp(String admissionTemp) {
		this.admissionTemp = admissionTemp;
	}

	public Boolean getAdmissionstatus() {
		return this.admissionstatus;
	}

	public void setAdmissionstatus(Boolean admissionstatus) {
		this.admissionstatus = admissionstatus;
	}

	public String getAdmittingdoctor() {
		return this.admittingdoctor;
	}

	public void setAdmittingdoctor(String admittingdoctor) {
		this.admittingdoctor = admittingdoctor;
	}

	public String getBabyname() {
		return this.babyname;
	}

	public void setBabyname(String babyname) {
		this.babyname = babyname;
	}

	public Float getBirthheadcircumference() {
		return this.birthheadcircumference;
	}

	public void setBirthheadcircumference(Float birthheadcircumference) {
		this.birthheadcircumference = birthheadcircumference;
	}

	public Boolean getBirthinjuries() {
		return this.birthinjuries;
	}

	public void setBirthinjuries(Boolean birthinjuries) {
		this.birthinjuries = birthinjuries;
	}

	public String getBirthinjuriesComments() {
		return this.birthinjuriesComments;
	}

	public void setBirthinjuriesComments(String birthinjuriesComments) {
		this.birthinjuriesComments = birthinjuriesComments;
	}

	public Float getBirthlength() {
		return this.birthlength;
	}

	public void setBirthlength(Float birthlength) {
		this.birthlength = birthlength;
	}

	public Boolean getBirthmarks() {
		return this.birthmarks;
	}

	public void setBirthmarks(Boolean birthmarks) {
		this.birthmarks = birthmarks;
	}

	public String getBirthmarksComments() {
		return this.birthmarksComments;
	}

	public void setBirthmarksComments(String birthmarksComments) {
		this.birthmarksComments = birthmarksComments;
	}

	public Float getBirthweight() {
		return this.birthweight;
	}

	public void setBirthweight(Float birthweight) {
		this.birthweight = birthweight;
	}

	public String getBloodgroup() {
		return this.bloodgroup;
	}

	public void setBloodgroup(String bloodgroup) {
		this.bloodgroup = bloodgroup;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getCourseinrefferinghospital() {
		return this.courseinrefferinghospital;
	}

	public void setCourseinrefferinghospital(String courseinrefferinghospital) {
		this.courseinrefferinghospital = courseinrefferinghospital;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getCriticalitylevel() {
		return this.criticalitylevel;
	}

	public void setCriticalitylevel(String criticalitylevel) {
		this.criticalitylevel = criticalitylevel;
	}

	public String getCry() {
		return this.cry;
	}

	public void setCry(String cry) {
		this.cry = cry;
	}

	public Date getDateofadmission() {
		return this.dateofadmission;
	}

	public void setDateofadmission(Date dateofadmission) {
		this.dateofadmission = dateofadmission;
	}

	public String getTimeofadmission() {
		return timeofadmission;
	}

	public void setTimeofadmission(String timeofadmission) {
		this.timeofadmission = timeofadmission;
	}

	public Date getDateofbirth() {
		return this.dateofbirth;
	}

	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public String getDayoflife() {
		return this.dayoflife;
	}

	public void setDayoflife(String dayoflife) {
		this.dayoflife = dayoflife;
	}

	public Boolean getDayoflifetype() {
		return dayoflifetype;
	}

	public void setDayoflifetype(Boolean dayoflifetype) {
		this.dayoflifetype = dayoflifetype;
	}

	public Timestamp getDischargeddate() {
		return dischargeddate;
	}

	public void setDischargeddate(Timestamp dischargeddate) {
		this.dischargeddate = dischargeddate;
	}

	public String getDischargestatus() {
		return this.dischargestatus;
	}

	public void setDischargestatus(String dischargestatus) {
		this.dischargestatus = dischargestatus;
	}

	public String getExaminationid() {
		return this.examinationid;
	}

	public void setExaminationid(String examinationid) {
		this.examinationid = examinationid;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getGestationdaysbylmp() {
		return this.gestationdaysbylmp;
	}

	public void setGestationdaysbylmp(Integer gestationdaysbylmp) {
		this.gestationdaysbylmp = gestationdaysbylmp;
	}

	public Integer getGestationweekbylmp() {
		return this.gestationweekbylmp;
	}

	public void setGestationweekbylmp(Integer gestationweekbylmp) {
		this.gestationweekbylmp = gestationweekbylmp;
	}

	public String getInoutPatientStatus() {
		return this.inoutPatientStatus;
	}

	public void setInoutPatientStatus(String inoutPatientStatus) {
		this.inoutPatientStatus = inoutPatientStatus;
	}

	public String getLoggeduser() {
		return this.loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public String getModeoftransportation() {
		return modeoftransportation;
	}

	public void setModeoftransportation(String modeoftransportation) {
		this.modeoftransportation = modeoftransportation;
	}

	public String getRefferedby() {
		return refferedby;
	}

	public void setRefferedby(String refferedby) {
		this.refferedby = refferedby;
	}

	public String getRefferedNumber() {
		return refferedNumber;
	}

	public void setRefferedNumber(String refferedNumber) {
		this.refferedNumber = refferedNumber;
	}

	public String getSourceoftransportation() {
		return sourceoftransportation;
	}

	public void setSourceoftransportation(String sourceoftransportation) {
		this.sourceoftransportation = sourceoftransportation;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getNicubedno() {
		return this.nicubedno;
	}

	public void setNicubedno(String nicubedno) {
		this.nicubedno = nicubedno;
	}

	public String getNiculevelno() {
		return this.niculevelno;
	}

	public void setNiculevelno(String niculevelno) {
		this.niculevelno = niculevelno;
	}

	public String getNicuroomno() {
		return this.nicuroomno;
	}

	public void setNicuroomno(String nicuroomno) {
		this.nicuroomno = nicuroomno;
	}

	public String getReasonofreference() {
		return this.reasonofreference;
	}

	public void setReasonofreference(String reasonofreference) {
		this.reasonofreference = reasonofreference;
	}

	public String getRefferedfrom() {
		return this.refferedfrom;
	}

	public void setRefferedfrom(String refferedfrom) {
		this.refferedfrom = refferedfrom;
	}

	public String getTimeofbirth() {
		return this.timeofbirth;
	}

	public void setTimeofbirth(String timeofbirth) {
		this.timeofbirth = timeofbirth;
	}

	public String getTimeofcry() {
		return this.timeofcry;
	}

	public void setTimeofcry(String timeofcry) {
		this.timeofcry = timeofcry;
	}

	public String getTransportationalongwith() {
		return this.transportationalongwith;
	}

	public void setTransportationalongwith(String transportationalongwith) {
		this.transportationalongwith = transportationalongwith;
	}

	public String getTypeofadmission() {
		return this.typeofadmission;
	}

	public void setTypeofadmission(String typeofadmission) {
		this.typeofadmission = typeofadmission;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public Object getDoaObj() {
		return doaObj;
	}

	public void setDoaObj(Object doaObj) {
		this.doaObj = doaObj;
	}

	public Object getDobObj() {
		return dobObj;
	}

	public void setDobObj(Object dobObj) {
		this.dobObj = dobObj;
	}

	public String getDoaStr() {
		return doaStr;
	}

	public void setDoaStr(String doaStr) {
		this.doaStr = doaStr;
	}

	public String getDobStr() {
		return dobStr;
	}

	public void setDobStr(String dobStr) {
		this.dobStr = dobStr;
	}

	public String getEpisodeid() {
		return episodeid;
	}

	public void setEpisodeid(String episodeid) {
		this.episodeid = episodeid;
	}

	public String getConsciousness() {
		return consciousness;
	}

	public void setConsciousness(String consciousness) {
		this.consciousness = consciousness;
	}

	public String getWeight_galevel() {
		return weight_galevel;
	}

	public void setWeight_galevel(String weight_galevel) {
		this.weight_galevel = weight_galevel;
	}

	public String getLength_galevel() {
		return length_galevel;
	}

	public void setLength_galevel(String length_galevel) {
		this.length_galevel = length_galevel;
	}

	public String getHc_galevel() {
		return hc_galevel;
	}

	public void setHc_galevel(String hc_galevel) {
		this.hc_galevel = hc_galevel;
	}

	public Float getWeight_centile() {
		return weight_centile;
	}

	public void setWeight_centile(Float weight_centile) {
		this.weight_centile = weight_centile;
	}

	public Float getLength_centile() {
		return length_centile;
	}

	public void setLength_centile(Float length_centile) {
		this.length_centile = length_centile;
	}

	public Float getHc_centile() {
		return hc_centile;
	}

	public void setHc_centile(Float hc_centile) {
		this.hc_centile = hc_centile;
	}

	public Integer getPonderal_index() {
		return ponderal_index;
	}

	public void setPonderal_index(Integer ponderal_index) {
		this.ponderal_index = ponderal_index;
	}

	public Float getCentral_temp() {
		return central_temp;
	}

	public void setCentral_temp(Float central_temp) {
		this.central_temp = central_temp;
	}

	public Float getPeripheral_temp() {
		return peripheral_temp;
	}

	public void setPeripheral_temp(Float peripheral_temp) {
		this.peripheral_temp = peripheral_temp;
	}

	public Integer getHr() {
		return hr;
	}

	public void setHr(Integer hr) {
		this.hr = hr;
	}

	public Integer getRr() {
		return rr;
	}

	public void setRr(Integer rr) {
		this.rr = rr;
	}

	public Integer getSpo2() {
		return spo2;
	}

	public void setSpo2(Integer spo2) {
		this.spo2 = spo2;
	}

	public String getBp_type() {
		return bp_type;
	}

	public void setBp_type(String bp_type) {
		this.bp_type = bp_type;
	}

	public Integer getBp_systolic() {
		return bp_systolic;
	}

	public void setBp_systolic(Integer bp_systolic) {
		this.bp_systolic = bp_systolic;
	}

	public Integer getBp_diastolic() {
		return bp_diastolic;
	}

	public void setBp_diastolic(Integer bp_diastolic) {
		this.bp_diastolic = bp_diastolic;
	}

	public Integer getBp_mean() {
		return bp_mean;
	}

	public void setBp_mean(Integer bp_mean) {
		this.bp_mean = bp_mean;
	}

	public Integer getBp_lll() {
		return bp_lll;
	}

	public void setBp_lll(Integer bp_lll) {
		this.bp_lll = bp_lll;
	}

	public Integer getBp_rll() {
		return bp_rll;
	}

	public void setBp_rll(Integer bp_rll) {
		this.bp_rll = bp_rll;
	}

	public Integer getBp_lul() {
		return bp_lul;
	}

	public void setBp_lul(Integer bp_lul) {
		this.bp_lul = bp_lul;
	}

	public Integer getBp_rul() {
		return bp_rul;
	}

	public void setBp_rul(Integer bp_rul) {
		this.bp_rul = bp_rul;
	}

	public Integer getCrt() {
		return crt;
	}

	public void setCrt(Integer crt) {
		this.crt = crt;
	}

	public String getDownesscoreid() {
		return downesscoreid;
	}

	public void setDownesscoreid(String downesscoreid) {
		this.downesscoreid = downesscoreid;
	}

	public Boolean getIsassessmentsubmit() {
		return isassessmentsubmit;
	}

	public void setIsassessmentsubmit(Boolean isassessmentsubmit) {
		this.isassessmentsubmit = isassessmentsubmit;
	}

	public String getBabyType() {
		return babyType;
	}

	public void setBabyType(String babyType) {
		this.babyType = babyType;
	}

	public String getBabyNumber() {
		return babyNumber;
	}

	public void setBabyNumber(String babyNumber) {
		this.babyNumber = babyNumber;
	}

	public Float getAdmissionWeight() {
		return admissionWeight;
	}

	public void setAdmissionWeight(Float admissionWeight) {
		this.admissionWeight = admissionWeight;
	}

	public Boolean getIsreadmitted() {
		return isreadmitted;
	}

	public void setIsreadmitted(Boolean isreadmitted) {
		this.isreadmitted = isreadmitted;
	}

	public String getFourLimb() {
		return fourLimb;
	}

	public void setFourLimb(String fourLimb) {
		this.fourLimb = fourLimb;
	}

	public String getRecommendedStatus() {
		return recommendedStatus;
	}

	public void setRecommendedStatus(String recommendedStatus) {
		this.recommendedStatus = recommendedStatus;
	}

	public Float getAdmissionLength() {
		return admissionLength;
	}

	public void setAdmissionLength(Float admissionLength) {
		this.admissionLength = admissionLength;
	}
	public String getAdmissionLengthGaLevel() {
		return admissionLengthGaLevel;
	}

	public void setAdmissionLengthGaLevel(String admissionLengthGaLevel) {
		this.admissionLengthGaLevel = admissionLengthGaLevel;
	}

	public String getAdmissionHCgaLevel() {
		return admissionHCgaLevel;
	}

	public void setAdmissionHCgaLevel(String admissionHCgaLevel) {
		this.admissionHCgaLevel = admissionHCgaLevel;
	}

	public Float getAdmissionWeightCentile() {
		return admissionWeightCentile;
	}

	public void setAdmissionWeightCentile(Float admissionWeightCentile) {
		this.admissionWeightCentile = admissionWeightCentile;
	}

	public String getAdmissionWeightGAlevel() {
		return admissionWeightGAlevel;
	}

	public void setAdmissionWeightGAlevel(String admissionWeightGAlevel) {
		this.admissionWeightGAlevel = admissionWeightGAlevel;
	}

	public Float getAdmissionHCcentile() {
		return admissionHCcentile;
	}

	public void setAdmissionHCcentile(Float admissionHCcentile) {
		this.admissionHCcentile = admissionHCcentile;
	}

	public Float getAdmissionLengthCentile() {
		return admissionLengthCentile;
	}

	public void setAdmissionLengthCentile(Float admissionLengthCentile) {
		this.admissionLengthCentile = admissionLengthCentile;
	}

	public Float getAdmissionHeadCircumference() {
		return admissionHeadCircumference;
	}

	public void setAdmissionHeadCircumference(Float admissionHeadCircumference) {
		this.admissionHeadCircumference = admissionHeadCircumference;
	}

	public String getBranchname() {
		return branchname;
	}

	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}

	public String getIpnumber() {
		return ipnumber;
	}

	public void setIpnumber(String ipnumber) {
		this.ipnumber = ipnumber;
	}

	public Boolean getIsRespiratorySupport() {
		return isRespiratorySupport;
	}

	public void setIsRespiratorySupport(Boolean isRespiratorySupport) {
		this.isRespiratorySupport = isRespiratorySupport;
	}

	public String getTimeofreadmission() {
		return timeofreadmission;
	}

	public void setTimeofreadmission(String timeofreadmission) {
		this.timeofreadmission = timeofreadmission;
	}

	public Timestamp getHisdischargedate() {
		return hisdischargedate;
	}

	public void setHisdischargedate(Timestamp hisdischargedate) {
		this.hisdischargedate = hisdischargedate;
	}

	public String getHisdischargestatus() {
		return hisdischargestatus;
	}

	public void setHisdischargestatus(String hisdischargestatus) {
		this.hisdischargestatus = hisdischargestatus;
	}

	public String getIptagno() {
		return iptagno;
	}

	public void setIptagno(String iptagno) {
		this.iptagno = iptagno;
	}

	public Date getReceivingdate() {
		return receivingdate;
	}

	public void setReceivingdate(Date receivingdate) {
		this.receivingdate = receivingdate;
	}

	public String getReceivingtime() {
		return receivingtime;
	}

	public void setReceivingtime(String receivingtime) {
		this.receivingtime = receivingtime;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getBedNumber() {
		return bedNumber;
	}

	public void setBedNumber(String bedNumber) {
		this.bedNumber = bedNumber;
	}

	public Float getCurrentdateweight() {
		return currentdateweight;
	}

	public void setCurrentdateweight(Float currentdateweight) {
		this.currentdateweight = currentdateweight;
	}

	public Float getPrevDateWeight() {
		return prevDateWeight;
	}

	public void setPrevDateWeight(Float prevDateWeight) {
		this.prevDateWeight = prevDateWeight;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}


	public Timestamp getDateTimeOfBirth() {
		return dateTimeOfBirth;
	}

	public void setDateTimeOfBirth(Timestamp dateTimeOfBirth) {
		this.dateTimeOfBirth = dateTimeOfBirth;
	}

	public Timestamp getDateTimeOfAdmission() {
		return dateTimeOfAdmission;
	}

	public void setDateTimeOfAdmission(Timestamp dateTimeOfAdmission) {
		this.dateTimeOfAdmission = dateTimeOfAdmission;
	}

	public Boolean getGestationQuartile() {
		return gestationQuartile;
	}

	public void setGestationQuartile(Boolean gestationQuartile) {
		this.gestationQuartile = gestationQuartile;
	}

	public Boolean getEnergyDeviation() {
		return energyDeviation;
	}

	public void setEnergyDeviation(Boolean energyDeviation) {
		this.energyDeviation = energyDeviation;
	}

	public Boolean getProteinDeviation() {
		return proteinDeviation;
	}

	public void setProteinDeviation(Boolean proteinDeviation) {
		this.proteinDeviation = proteinDeviation;
	}

	public Integer getMedicationDeviation() {
		return medicationDeviation;
	}

	public void setMedicationDeviation(Integer medicationDeviation) {
		this.medicationDeviation = medicationDeviation;
	}

	public Boolean getMonoamniotic() {
		return monoamniotic;
	}

	public void setMonoamniotic(Boolean monoamniotic) {
		this.monoamniotic = monoamniotic;
	}


	public Boolean getAudioEnabled() {
		return audioEnabled;
	}

	public void setAudioEnabled(Boolean audioEnabled) {
		this.audioEnabled = audioEnabled;
	}

	public Float getEnergy_deviation_value() {
		return energy_deviation_value;
	}

	public void setEnergy_deviation_value(Float energy_deviation_value) {
		this.energy_deviation_value = energy_deviation_value;
	}

	public Float getProtein_deviation_value() {
		return protein_deviation_value;
	}

	public void setProtein_deviation_value(Float protein_deviation_value) {
		this.protein_deviation_value = protein_deviation_value;
	}

	public Float getGestation_quartile_value() {
		return gestation_quartile_value;
	}

	public void setGestation_quartile_value(Float gestation_quartile_value) {
		this.gestation_quartile_value = gestation_quartile_value;
	}

	@Override
	public String toString() {
		return "BabyDetail{" +
				"babydetailid=" + babydetailid +
				", activestatus=" + activestatus +
				", admissionstatus=" + admissionstatus +
				", sameAnthropometryDetails=" + sameAnthropometryDetails +
				", typeofadmission='" + typeofadmission + '\'' +
				", inoutPatientStatus='" + inoutPatientStatus + '\'' +
				", admissionHeadCircumference=" + admissionHeadCircumference +
				", admissionLength=" + admissionLength +
				", admissionWeightCentile=" + admissionWeightCentile +
				", admissionLengthCentile=" + admissionLengthCentile +
				", admissionHCcentile=" + admissionHCcentile +
				", admissionWeightGAlevel='" + admissionWeightGAlevel + '\'' +
				", admissionHCgaLevel='" + admissionHCgaLevel + '\'' +
				", admissionLengthGaLevel='" + admissionLengthGaLevel + '\'' +
				", actualgestationdays=" + actualgestationdays +
				", actualgestationweek=" + actualgestationweek +
				", admissionBp='" + admissionBp + '\'' +
				", admissionPulserate='" + admissionPulserate + '\'' +
				", admissionRr='" + admissionRr + '\'' +
				", admissionSpo2='" + admissionSpo2 + '\'' +
				", admissionTemp='" + admissionTemp + '\'' +
				", admittingdoctor='" + admittingdoctor + '\'' +
				", iptagno='" + iptagno + '\'' +
				", babyname='" + babyname + '\'' +
				", cry='" + cry + '\'' +
				", timeofcry='" + timeofcry + '\'' +
				", birthheadcircumference=" + birthheadcircumference +
				", birthinjuries=" + birthinjuries +
				", birthinjuriesComments='" + birthinjuriesComments + '\'' +
				", birthlength=" + birthlength +
				", birthmarks=" + birthmarks +
				", birthmarksComments='" + birthmarksComments + '\'' +
				", birthweight=" + birthweight +
				", admissionWeight=" + admissionWeight +
				", bloodgroup='" + bloodgroup + '\'' +
				", comments='" + comments + '\'' +
				", creationtime=" + creationtime +
				", recommendedStatus='" + recommendedStatus + '\'' +
				", doaObj=" + doaObj +
				", doaStr='" + doaStr + '\'' +
				", dateofadmission=" + dateofadmission +
				", timeofadmission='" + timeofadmission + '\'' +
				", dobObj=" + dobObj +
				", dobStr='" + dobStr + '\'' +
				", dateofbirth=" + dateofbirth +
				", dayoflife='" + dayoflife + '\'' +
				", dayoflifetype=" + dayoflifetype +
				", isRespiratorySupport=" + isRespiratorySupport +
				", timeofbirth='" + timeofbirth + '\'' +
				", dischargeddate=" + dischargeddate +
				", dischargestatus='" + dischargestatus + '\'' +
				", examinationid='" + examinationid + '\'' +
				", gender='" + gender + '\'' +
				", gestationdaysbylmp=" + gestationdaysbylmp +
				", gestationweekbylmp=" + gestationweekbylmp +
				", loggeduser='" + loggeduser + '\'' +
				", modificationtime=" + modificationtime +
				", nicuroomno='" + nicuroomno + '\'' +
				", nicubedno='" + nicubedno + '\'' +
				", niculevelno='" + niculevelno + '\'' +
				", criticalitylevel='" + criticalitylevel + '\'' +
				", refferedfrom='" + refferedfrom + '\'' +
				", refferedby='" + refferedby + '\'' +
				", refferedNumber='" + refferedNumber + '\'' +
				", modeoftransportation='" + modeoftransportation + '\'' +
				", sourceoftransportation='" + sourceoftransportation + '\'' +
				", reasonofreference='" + reasonofreference + '\'' +
				", courseinrefferinghospital='" + courseinrefferinghospital + '\'' +
				", transportationalongwith='" + transportationalongwith + '\'' +
				", uhid='" + uhid + '\'' +
				", ipnumber='" + ipnumber + '\'' +
				", episodeid='" + episodeid + '\'' +
				", consciousness='" + consciousness + '\'' +
				", weight_galevel='" + weight_galevel + '\'' +
				", length_galevel='" + length_galevel + '\'' +
				", hc_galevel='" + hc_galevel + '\'' +
				", weight_centile=" + weight_centile +
				", length_centile=" + length_centile +
				", hc_centile=" + hc_centile +
				", ponderal_index=" + ponderal_index +
				", central_temp=" + central_temp +
				", peripheral_temp=" + peripheral_temp +
				", hr=" + hr +
				", rr=" + rr +
				", spo2=" + spo2 +
				", bp_type='" + bp_type + '\'' +
				", bp_systolic=" + bp_systolic +
				", bp_diastolic=" + bp_diastolic +
				", bp_mean=" + bp_mean +
				", bp_lll_systolic=" + bp_lll_systolic +
				", bp_lll_diastolic=" + bp_lll_diastolic +
				", bp_lll_mean=" + bp_lll_mean +
				", bp_rll_systolic=" + bp_rll_systolic +
				", bp_rll_diastolic=" + bp_rll_diastolic +
				", bp_rll_mean=" + bp_rll_mean +
				", bp_lul_systolic=" + bp_lul_systolic +
				", bp_lul_diastolic=" + bp_lul_diastolic +
				", bp_lul_mean=" + bp_lul_mean +
				", bp_rul_systolic=" + bp_rul_systolic +
				", bp_rul_diastolic=" + bp_rul_diastolic +
				", bp_rul_mean=" + bp_rul_mean +
				", bp_rul=" + bp_rul +
				", bp_lul=" + bp_lul +
				", bp_rll=" + bp_rll +
				", bp_lll=" + bp_lll +
				", crt=" + crt +
				", downesscoreid='" + downesscoreid + '\'' +
				", isassessmentsubmit=" + isassessmentsubmit +
				", babyType='" + babyType + '\'' +
				", babyNumber='" + babyNumber + '\'' +
				", isreadmitted=" + isreadmitted +
				", fourLimb='" + fourLimb + '\'' +
				", branchname='" + branchname + '\'' +
				", timeofreadmission='" + timeofreadmission + '\'' +
				", hisdischargedate=" + hisdischargedate +
				", hisdischargestatus='" + hisdischargestatus + '\'' +
				", receivingdate=" + receivingdate +
				", receivingtime='" + receivingtime + '\'' +
				", bedNumber='" + bedNumber + '\'' +
				", roomNumber='" + roomNumber + '\'' +
				", condition='" + condition + '\'' +
				", currentdateweight=" + currentdateweight +
				", prevDateWeight=" + prevDateWeight +
				'}';
	}
}
