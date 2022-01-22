package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.*;



/**
 * The persistent class for the vw_discharged_patients_final database table.
 * 
 */
@Entity
@Table(name="vw_discharged_patients_final")
@NamedQuery(name="VwDischargedPatientsFinal.findAll", query="SELECT v FROM VwDischargedPatientsFinal v")
public class VwDischargedPatientsFinal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long babydetailid;
	
	@Column(columnDefinition="bool")
	private Boolean isbabyininicu;

	@Column(columnDefinition = "bool")
	private Boolean activestatus;

	@Column(columnDefinition = "bool")
	private Boolean admissionstatus;

	private String typeofadmission;

	@Column(name = "inout_patient_status")
	private String inoutPatientStatus;

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

	private String bp_type;

	private Integer bp_systolic;

	private Integer bp_diastolic;

	private Integer bp_mean;

	private Integer bp_lll;

	private Integer bp_rll;

	private Integer bp_lul;

	private Integer bp_rul;

	private Integer crt;

	private String downesscoreid;

	@Column(columnDefinition = "bool")
	private Boolean isassessmentsubmit;

	@Column(name = "baby_type")
	private String babyType;

	@Column(name = "baby_number")
	private String babyNumber;
	
	private String branchname;
	
	private String assessmentname;
	
	private Integer respSupportDays;
	
	private String lengthOfStay;

	@Transient
	private Timestamp dateTimeOfBirth;

	@Transient Timestamp dateTimeOfAdmission;


	public VwDischargedPatientsFinal() {
		super();
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

	public Boolean getIsbabyininicu() {
		return isbabyininicu;
	}

	public void setIsbabyininicu(Boolean isbabyininicu) {
		this.isbabyininicu = isbabyininicu;
	}

	public String getBranchname() {
		return branchname;
	}

	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}

	public String getAssessmentname() {
		return assessmentname;
	}

	public void setAssessmentname(String assessmentname) {
		this.assessmentname = assessmentname;
	}

	public Integer getRespSupportDays() {
		return respSupportDays;
	}

	public void setRespSupportDays(Integer respSupportDays) {
		this.respSupportDays = respSupportDays;
	}

	public String getLengthOfStay() {
		return lengthOfStay;
	}

	public void setLengthOfStay(String lengthOfStay) {
		this.lengthOfStay = lengthOfStay;
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
}