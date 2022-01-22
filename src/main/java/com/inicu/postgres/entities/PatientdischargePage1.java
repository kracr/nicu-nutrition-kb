package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the patientdischarge_page1 database table.
 * 
 */
@Entity
@Table(name="patientdischarge_page1")
@NamedQuery(name="PatientdischargePage1.findAll", query="SELECT p FROM PatientdischargePage1 p")
public class PatientdischargePage1 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="a_score")
	private String aScore;

	private String aadharcard;

	@Column(columnDefinition="bool")
	private Boolean activestatus;

	private String actualgestation;

	@Column(name="additional_doctor_notes")
	private String additionalDoctorNotes;

	private String address;

	@Column(columnDefinition="bool")
	private Boolean admissionstatus;

	private String admittingdoctor;

	@Transient
	@Column(columnDefinition="bool")
	private Boolean anc;

	@Column(name="antenatal_steroids")
	private String antenatalSteroids;

	@Column(name="apgar_score_1min")
	private Integer apgarScore1min;

	@Column(name="apgar_score_5min")
	private Integer apgarScore5min;

	@Column(name="baby_discharge_status")
	private String babyDischargeStatus;

	private String babyname;

	@Column(columnDefinition="float4")
	private Float birthheadcircumference;

	@Column(columnDefinition="float4")
	private Float birthlength;

	@Column(columnDefinition="float4")
	private Float birthweight;

	private String bloodgroup;

	private String comments;

	private String criticalitylevel;

	private String cry;

	@Column(columnDefinition="float4")
	private Float currentdateheadcircum;

	@Column(columnDefinition="float4")
	private Float currentdateheight;

	@Column(columnDefinition="float4")
	private Float currentdateweight;

	@Temporal(TemporalType.DATE)
	private Date dateofadmission;

	@Temporal(TemporalType.DATE)
	private Date dateofbirth;

	private String deliverymode;

	@Column(name="discharge_print_flag")
	private String dischargePrintFlag;

	private Timestamp dischargedate;

	
	private Timestamp dischargeddate;

	private Long dischargepatientid;

	private String dischargestatus;

	@Temporal(TemporalType.DATE)
	private Date edd;

	private String emailid;

	private Integer fatherage;

	@Temporal(TemporalType.DATE)
	private Date fatherdob;

	private String fathername;

	@Transient
	@Column(name="flatus_tube",columnDefinition="bool")
	private Boolean flatusTube;

	@Column(name="g_score")
	private String gScore;

	private String gender;

	private String gestationbylmp;

	private String headtotoeid;

	@Column(columnDefinition="bool")
	private Boolean immunized;

	@Column(name="inout_patient_status")
	private String inoutPatientStatus;

	private String iptagno;

	@Column(name="l_score")
	private String lScore;

	@Temporal(TemporalType.DATE)
	private Date lmp;

	private Integer motherage;

	@Temporal(TemporalType.DATE)
	private Date motherdob;

	private String mothername;

	private String nicubedno;

	private String niculevelno;

	private String nicuroomno;

	private String obstetrician;

	@Column(name="p_score")
	private String pScore;

	private String primaryphonenumber;

	@Transient
	@Column(columnDefinition="bool")
	private Boolean resuscitation;

	private String secondaryphonenumber;

	private String significantmaterialid;

	private String timeofadmission;

	private String timeofbirth;

	private String timeofcry;

	@Id
	private String uhid;

	private String villagename;

	@Transient
	@Column(name="vitamink_status",columnDefinition="bool")
	private Boolean vitaminkStatus;

	public PatientdischargePage1() {
	}

	public String getAScore() {
		return this.aScore;
	}

	public void setAScore(String aScore) {
		this.aScore = aScore;
	}

	public String getAadharcard() {
		return this.aadharcard;
	}

	public void setAadharcard(String aadharcard) {
		this.aadharcard = aadharcard;
	}

	public Boolean getActivestatus() {
		return this.activestatus;
	}

	public void setActivestatus(Boolean activestatus) {
		this.activestatus = activestatus;
	}

	public String getActualgestation() {
		return this.actualgestation;
	}

	public void setActualgestation(String actualgestation) {
		this.actualgestation = actualgestation;
	}

	public String getAdditionalDoctorNotes() {
		return this.additionalDoctorNotes;
	}

	public void setAdditionalDoctorNotes(String additionalDoctorNotes) {
		this.additionalDoctorNotes = additionalDoctorNotes;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public Boolean getAnc() {
		return this.anc;
	}

	public void setAnc(Boolean anc) {
		this.anc = anc;
	}

	public String getAntenatalSteroids() {
		return this.antenatalSteroids;
	}

	public void setAntenatalSteroids(String antenatalSteroids) {
		this.antenatalSteroids = antenatalSteroids;
	}

	public Integer getApgarScore1min() {
		return this.apgarScore1min;
	}

	public void setApgarScore1min(Integer apgarScore1min) {
		this.apgarScore1min = apgarScore1min;
	}

	public Integer getApgarScore5min() {
		return this.apgarScore5min;
	}

	public void setApgarScore5min(Integer apgarScore5min) {
		this.apgarScore5min = apgarScore5min;
	}

	public String getBabyDischargeStatus() {
		return this.babyDischargeStatus;
	}

	public void setBabyDischargeStatus(String babyDischargeStatus) {
		this.babyDischargeStatus = babyDischargeStatus;
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

	public Float getBirthlength() {
		return this.birthlength;
	}

	public void setBirthlength(Float birthlength) {
		this.birthlength = birthlength;
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

	public Float getCurrentdateheadcircum() {
		return this.currentdateheadcircum;
	}

	public void setCurrentdateheadcircum(Float currentdateheadcircum) {
		this.currentdateheadcircum = currentdateheadcircum;
	}

	public Float getCurrentdateheight() {
		return this.currentdateheight;
	}

	public void setCurrentdateheight(Float currentdateheight) {
		this.currentdateheight = currentdateheight;
	}

	public Float getCurrentdateweight() {
		return this.currentdateweight;
	}

	public void setCurrentdateweight(Float currentdateweight) {
		this.currentdateweight = currentdateweight;
	}

	public Date getDateofadmission() {
		return this.dateofadmission;
	}

	public void setDateofadmission(Date dateofadmission) {
		this.dateofadmission = dateofadmission;
	}

	public Date getDateofbirth() {
		return this.dateofbirth;
	}

	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public String getDeliverymode() {
		return this.deliverymode;
	}

	public void setDeliverymode(String deliverymode) {
		this.deliverymode = deliverymode;
	}

	public String getDischargePrintFlag() {
		return this.dischargePrintFlag;
	}

	public void setDischargePrintFlag(String dischargePrintFlag) {
		this.dischargePrintFlag = dischargePrintFlag;
	}

	public Timestamp getDischargedate() {
		return this.dischargedate;
	}

	public void setDischargedate(Timestamp dischargedate) {
		this.dischargedate = dischargedate;
	}



	public Timestamp getDischargeddate() {
		return dischargeddate;
	}

	public void setDischargeddate(Timestamp dischargeddate) {
		this.dischargeddate = dischargeddate;
	}

	public Long getDischargepatientid() {
		return this.dischargepatientid;
	}

	public void setDischargepatientid(Long dischargepatientid) {
		this.dischargepatientid = dischargepatientid;
	}

	public String getDischargestatus() {
		return this.dischargestatus;
	}

	public void setDischargestatus(String dischargestatus) {
		this.dischargestatus = dischargestatus;
	}

	public Date getEdd() {
		return this.edd;
	}

	public void setEdd(Date edd) {
		this.edd = edd;
	}

	public String getEmailid() {
		return this.emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public Integer getFatherage() {
		return this.fatherage;
	}

	public void setFatherage(Integer fatherage) {
		this.fatherage = fatherage;
	}

	public Date getFatherdob() {
		return this.fatherdob;
	}

	public void setFatherdob(Date fatherdob) {
		this.fatherdob = fatherdob;
	}

	public String getFathername() {
		return this.fathername;
	}

	public void setFathername(String fathername) {
		this.fathername = fathername;
	}

	public Boolean getFlatusTube() {
		return this.flatusTube;
	}

	public void setFlatusTube(Boolean flatusTube) {
		this.flatusTube = flatusTube;
	}

	public String getGScore() {
		return this.gScore;
	}

	public void setGScore(String gScore) {
		this.gScore = gScore;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getGestationbylmp() {
		return this.gestationbylmp;
	}

	public void setGestationbylmp(String gestationbylmp) {
		this.gestationbylmp = gestationbylmp;
	}

	public String getHeadtotoeid() {
		return this.headtotoeid;
	}

	public void setHeadtotoeid(String headtotoeid) {
		this.headtotoeid = headtotoeid;
	}

	public Boolean getImmunized() {
		return this.immunized;
	}

	public void setImmunized(Boolean immunized) {
		this.immunized = immunized;
	}

	public String getInoutPatientStatus() {
		return this.inoutPatientStatus;
	}

	public void setInoutPatientStatus(String inoutPatientStatus) {
		this.inoutPatientStatus = inoutPatientStatus;
	}

	public String getIptagno() {
		return this.iptagno;
	}

	public void setIptagno(String iptagno) {
		this.iptagno = iptagno;
	}

	public String getLScore() {
		return this.lScore;
	}

	public void setLScore(String lScore) {
		this.lScore = lScore;
	}

	public Date getLmp() {
		return this.lmp;
	}

	public void setLmp(Date lmp) {
		this.lmp = lmp;
	}

	public Integer getMotherage() {
		return this.motherage;
	}

	public void setMotherage(Integer motherage) {
		this.motherage = motherage;
	}

	public Date getMotherdob() {
		return this.motherdob;
	}

	public void setMotherdob(Date motherdob) {
		this.motherdob = motherdob;
	}

	public String getMothername() {
		return this.mothername;
	}

	public void setMothername(String mothername) {
		this.mothername = mothername;
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

	public String getObstetrician() {
		return this.obstetrician;
	}

	public void setObstetrician(String obstetrician) {
		this.obstetrician = obstetrician;
	}

	public String getPScore() {
		return this.pScore;
	}

	public void setPScore(String pScore) {
		this.pScore = pScore;
	}

	public String getPrimaryphonenumber() {
		return this.primaryphonenumber;
	}

	public void setPrimaryphonenumber(String primaryphonenumber) {
		this.primaryphonenumber = primaryphonenumber;
	}

	public Boolean getResuscitation() {
		return this.resuscitation;
	}

	public void setResuscitation(Boolean resuscitation) {
		this.resuscitation = resuscitation;
	}

	public String getSecondaryphonenumber() {
		return this.secondaryphonenumber;
	}

	public void setSecondaryphonenumber(String secondaryphonenumber) {
		this.secondaryphonenumber = secondaryphonenumber;
	}

	public String getSignificantmaterialid() {
		return this.significantmaterialid;
	}

	public void setSignificantmaterialid(String significantmaterialid) {
		this.significantmaterialid = significantmaterialid;
	}

	public String getTimeofadmission() {
		return this.timeofadmission;
	}

	public void setTimeofadmission(String timeofadmission) {
		this.timeofadmission = timeofadmission;
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

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getVillagename() {
		return this.villagename;
	}

	public void setVillagename(String villagename) {
		this.villagename = villagename;
	}

	public Boolean getVitaminkStatus() {
		return this.vitaminkStatus;
	}

	public void setVitaminkStatus(Boolean vitaminkStatus) {
		this.vitaminkStatus = vitaminkStatus;
	}

}