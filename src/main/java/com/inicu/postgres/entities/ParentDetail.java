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
 * The persistent class for the parent_detail database table.
 * 
 */
@Entity
@Table(name = "parent_detail")
@NamedQuery(name = "ParentDetail.findAll", query = "SELECT p FROM ParentDetail p")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParentDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long parentdetailid;

	private String aadharcard;

	private Timestamp creationtime;

	private String emailid;

	private Integer fatherage;

	@Temporal(TemporalType.DATE)
	private Date fatherdob;

	@Transient
	private String fatherdobStr;

	private String fathername;

	private Timestamp modificationtime;

	private Integer motherage;

	@Temporal(TemporalType.DATE)
	private Date motherdob;

	@Transient
	private String motherdobStr;

	private String motherid;

	private String mothername;

	private String primaryphonenumber;

	private String secondaryphonenumber;

	private String uhid;

	private String villagename;

	// in case of emergency details....

	private String emergency_contactname;

	private String emergency_contactno;

	private String relationship;

	private String mother_religion;
	
	private String mother_religion_other;
	
	private String father_religion_other;

	private String mother_uhid;

	private String mother_aadhar;

	private String mother_education;

	private String mother_profession;

	private String mother_income;

	private String mother_phone;

	private String mother_email;
	
	private String mother_obstetrician;
	
	private String mother_gynaecologist;

	private String father_religion;

	private String father_aadhar;

	private String father_education;

	private String father_profession;

	private String father_income;

	private String father_phone;

	private String father_email;

	private String address;

	private String add_city;

	private String add_district;

	private String add_state;

	private String add_pin;

	private String episodeid;

	@Column(name = "is_mother_kuppuswamy", columnDefinition = "bool")
	private Boolean isMotherKuppuswamy;

	private Integer kuppuswamy_score;

	private String kuppuswamy_class;

	public ParentDetail() {
		super();
		//this.isMotherKuppuswamy = false;
	}

	public Long getParentdetailid() {
		return this.parentdetailid;
	}

	public void setParentdetailid(Long parentdetailid) {
		this.parentdetailid = parentdetailid;
	}

	public String getAadharcard() {
		return this.aadharcard;
	}

	public void setAadharcard(String aadharcard) {
		this.aadharcard = aadharcard;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
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

	public String getFatherdobStr() {
		return fatherdobStr;
	}

	public void setFatherdobStr(String fatherdobStr) {
		this.fatherdobStr = fatherdobStr;
	}

	public String getFathername() {
		return this.fathername;
	}

	public void setFathername(String fathername) {
		this.fathername = fathername;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
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

	public String getMotherdobStr() {
		return motherdobStr;
	}

	public void setMotherdobStr(String motherdobStr) {
		this.motherdobStr = motherdobStr;
	}

	public String getMotherid() {
		return this.motherid;
	}

	public void setMotherid(String motherid) {
		this.motherid = motherid;
	}

	public String getMothername() {
		return this.mothername;
	}

	public void setMothername(String mothername) {
		this.mothername = mothername;
	}

	public String getPrimaryphonenumber() {
		return this.primaryphonenumber;
	}

	public void setPrimaryphonenumber(String primaryphonenumber) {
		this.primaryphonenumber = primaryphonenumber;
	}

	public String getSecondaryphonenumber() {
		return this.secondaryphonenumber;
	}

	public void setSecondaryphonenumber(String secondaryphonenumber) {
		this.secondaryphonenumber = secondaryphonenumber;
	}

	public String getUid() {
		return this.uhid;
	}

	public void setUid(String uid) {
		this.uhid = uid;
	}

	public String getVillagename() {
		return this.villagename;
	}

	public void setVillagename(String villagename) {
		this.villagename = villagename;
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getEmergency_contactname() {
		return emergency_contactname;
	}

	public void setEmergency_contactname(String emergency_contactname) {
		this.emergency_contactname = emergency_contactname;
	}

	public String getEmergency_contactno() {
		return emergency_contactno;
	}

	public void setEmergency_contactno(String emergency_contactno) {
		this.emergency_contactno = emergency_contactno;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getMother_religion() {
		return mother_religion;
	}

	public void setMother_religion(String mother_religion) {
		this.mother_religion = mother_religion;
	}

	public String getMother_uhid() {
		return mother_uhid;
	}

	public void setMother_uhid(String mother_uhid) {
		this.mother_uhid = mother_uhid;
	}

	public String getMother_aadhar() {
		return mother_aadhar;
	}

	public void setMother_aadhar(String mother_aadhar) {
		this.mother_aadhar = mother_aadhar;
	}

	public String getMother_education() {
		return mother_education;
	}

	public void setMother_education(String mother_education) {
		this.mother_education = mother_education;
	}

	public String getMother_profession() {
		return mother_profession;
	}

	public void setMother_profession(String mother_profession) {
		this.mother_profession = mother_profession;
	}

	public String getMother_income() {
		return mother_income;
	}

	public void setMother_income(String mother_income) {
		this.mother_income = mother_income;
	}

	public String getMother_phone() {
		return mother_phone;
	}

	public void setMother_phone(String mother_phone) {
		this.mother_phone = mother_phone;
	}

	public String getMother_email() {
		return mother_email;
	}

	public void setMother_email(String mother_email) {
		this.mother_email = mother_email;
	}

	public String getFather_religion() {
		return father_religion;
	}

	public void setFather_religion(String father_religion) {
		this.father_religion = father_religion;
	}

	public String getFather_aadhar() {
		return father_aadhar;
	}

	public void setFather_aadhar(String father_aadhar) {
		this.father_aadhar = father_aadhar;
	}

	public String getFather_education() {
		return father_education;
	}

	public void setFather_education(String father_education) {
		this.father_education = father_education;
	}

	public String getFather_profession() {
		return father_profession;
	}

	public void setFather_profession(String father_profession) {
		this.father_profession = father_profession;
	}

	public String getFather_income() {
		return father_income;
	}

	public void setFather_income(String father_income) {
		this.father_income = father_income;
	}

	public String getFather_phone() {
		return father_phone;
	}

	public void setFather_phone(String father_phone) {
		this.father_phone = father_phone;
	}

	public String getFather_email() {
		return father_email;
	}

	public void setFather_email(String father_email) {
		this.father_email = father_email;
	}

	public String getAdd_district() {
		return add_district;
	}

	public void setAdd_district(String add_district) {
		this.add_district = add_district;
	}

	public String getAdd_city() {
		return add_city;
	}

	public void setAdd_city(String add_city) {
		this.add_city = add_city;
	}

	public String getAdd_state() {
		return add_state;
	}

	public void setAdd_state(String add_state) {
		this.add_state = add_state;
	}

	public String getAdd_pin() {
		return add_pin;
	}

	public void setAdd_pin(String add_pin) {
		this.add_pin = add_pin;
	}

	public String getEpisodeid() {
		return episodeid;
	}

	public void setEpisodeid(String episodeid) {
		this.episodeid = episodeid;
	}

	public Boolean getIsMotherKuppuswamy() {
		return isMotherKuppuswamy;
	}

	public void setIsMotherKuppuswamy(Boolean isMotherKuppuswamy) {
		this.isMotherKuppuswamy = isMotherKuppuswamy;
	}

	public Integer getKuppuswamy_score() {
		return kuppuswamy_score;
	}

	public void setKuppuswamy_score(Integer kuppuswamy_score) {
		this.kuppuswamy_score = kuppuswamy_score;
	}

	public String getKuppuswamy_class() {
		return kuppuswamy_class;
	}

	public void setKuppuswamy_class(String kuppuswamy_class) {
		this.kuppuswamy_class = kuppuswamy_class;
	}

	public String getMother_religion_other() {
		return mother_religion_other;
	}

	public void setMother_religion_other(String mother_religion_other) {
		this.mother_religion_other = mother_religion_other;
	}

	public String getFather_religion_other() {
		return father_religion_other;
	}

	public void setFather_religion_other(String father_religion_other) {
		this.father_religion_other = father_religion_other;
	}

	public String getMother_obstetrician() {
		return mother_obstetrician;
	}

	public void setMother_obstetrician(String mother_obstetrician) {
		this.mother_obstetrician = mother_obstetrician;
	}

	public String getMother_gynaecologist() {
		return mother_gynaecologist;
	}

	public void setMother_gynaecologist(String mother_gynaecologist) {
		this.mother_gynaecologist = mother_gynaecologist;
	}
	
}