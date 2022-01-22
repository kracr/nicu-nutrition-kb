package com.inicu.models;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="vw_baby_visit_CSVData")
@NamedQuery(name="VwBabyVisitCSVData.findAll", query="SELECT v FROM VwBabyVisitCSVData v")
public class VwBabyVisitCSVData {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long visitid;
	
	private String uhid;
	
	private String babyname;
	
	private String ga_at_birth;
	
	private Date entrydate;
	
	private Time entrytime;
	
	private String nicuday;
	
	private String age;
	
	private String corrected_ga;
	
	private Integer gestationweek;
	
	private Integer gestationdays;
	
	private Date dateofadmission;
	
	@Column(columnDefinition = "float4")
	private Float weight;
	
	@Column(columnDefinition = "float4")
	private Float height;
	
	@Column(columnDefinition = "float4")
	private Float headcircumferrence;
	
	@Column(columnDefinition = "float4")
	private Float workingweight;
	
	@Column(columnDefinition = "float4")
	private Float weightforcal;


	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getGa_at_birth() {
		return ga_at_birth;
	}

	public void setGa_at_birth(String ga_at_birth) {
		this.ga_at_birth = ga_at_birth;
	}

	public String getCorrected_ga() {
		return corrected_ga;
	}

	public void setCorrected_ga(String corrected_ga) {
		this.corrected_ga = corrected_ga;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public Float getHeight() {
		return height;
	}

	public void setHeight(Float height) {
		this.height = height;
	}

	public Float getHeadcircumferrence() {
		return headcircumferrence;
	}

	public void setHeadcircumferrence(Float headcircumferrence) {
		this.headcircumferrence = headcircumferrence;
	}

	public Float getWorkingweight() {
		return workingweight;
	}

	public void setWorkingweight(Float workingweight) {
		this.workingweight = workingweight;
	}

	public String getBabyname() {
		return babyname;
	}

	public void setBabyname(String babyname) {
		this.babyname = babyname;
	}

	public Date getEntrydate() {
		return entrydate;
	}

	public void setEntrydate(Date entrydate) {
		this.entrydate = entrydate;
	}

	public Time getEntrytime() {
		return entrytime;
	}

	public void setEntrytime(Time entrytime) {
		this.entrytime = entrytime;
	}

	public String getNicuday() {
		return nicuday;
	}

	public void setNicuday(String nicuday) {
		this.nicuday = nicuday;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public Float getWeightforcal() {
		return weightforcal;
	}

	public void setWeightforcal(Float weightforcal) {
		this.weightforcal = weightforcal;
	}

	public Long getVisitid() {
		return visitid;
	}

	public void setVisitid(Long visitid) {
		this.visitid = visitid;
	}

	public Date getDateofadmission() {
		return dateofadmission;
	}

	public void setDateofadmission(Date dateofadmission) {
		this.dateofadmission = dateofadmission;
	}

	public Integer getGestationweek() {
		return gestationweek;
	}

	public void setGestationweek(Integer gestationweek) {
		this.gestationweek = gestationweek;
	}

	public Integer getGestationdays() {
		return gestationdays;
	}

	public void setGestationdays(Integer gestationdays) {
		this.gestationdays = gestationdays;
	}
}

