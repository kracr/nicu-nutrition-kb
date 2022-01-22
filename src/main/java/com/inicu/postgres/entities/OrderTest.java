package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;

/**
Especially for Gangaram
 */
@Entity
@Table(name="order_test")
@NamedQuery(name="OrderTest.findAll", query="SELECT t FROM OrderTest t")
public class OrderTest implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String uhid;
	
	private String ipnumber;
	
	private String testid;
		
	private Timestamp creationtime;
		
	private String departmentname;
	
	private String sampleid;
	
	@Column(columnDefinition = "bool")
	private Boolean reportReceived;
		
	//Order
	@Column(name="lab_created_date")
	private Timestamp labCreatedDate;
	
	//Collection
	@Column(name="lab_collection_date")
	private Date labCollectionDate;
	
	//Receiving
	@Column(name="lab_report_date")
	private Timestamp labReportDate;
	private Date resultdate;
	
	//Entry
	@Column(name="lab_updated_date")
	private Timestamp labUpdatedDate;
	
	//Authorisation
	@Column(name="lab_approved_date")
	private Timestamp labApprovedDate;
	
	// adding for apollo's data
	@Column(name="lab_result_status")
	private String labResultStatus;
	
	@Column(name="lab_testname")
	private String labTestName;
	
	private String babyname;
	
	private String gender;
	
	private String age;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getIpnumber() {
		return ipnumber;
	}

	public void setIpnumber(String ipnumber) {
		this.ipnumber = ipnumber;
	}

	public String getTestid() {
		return testid;
	}

	public void setTestid(String testid) {
		this.testid = testid;
	}

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getDepartmentname() {
		return departmentname;
	}

	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}

	public Timestamp getLabCreatedDate() {
		return labCreatedDate;
	}

	public void setLabCreatedDate(Timestamp labCreatedDate) {
		this.labCreatedDate = labCreatedDate;
	}

	public Date getLabCollectionDate() {
		return labCollectionDate;
	}

	public void setLabCollectionDate(Date labCollectionDate) {
		this.labCollectionDate = labCollectionDate;
	}

	public Timestamp getLabReportDate() {
		return labReportDate;
	}

	public void setLabReportDate(Timestamp labReportDate) {
		this.labReportDate = labReportDate;
	}

	public Date getResultdate() {
		return resultdate;
	}

	public void setResultdate(Date resultdate) {
		this.resultdate = resultdate;
	}

	public Timestamp getLabUpdatedDate() {
		return labUpdatedDate;
	}

	public void setLabUpdatedDate(Timestamp labUpdatedDate) {
		this.labUpdatedDate = labUpdatedDate;
	}

	public Timestamp getLabApprovedDate() {
		return labApprovedDate;
	}

	public void setLabApprovedDate(Timestamp labApprovedDate) {
		this.labApprovedDate = labApprovedDate;
	}

	public String getLabResultStatus() {
		return labResultStatus;
	}

	public void setLabResultStatus(String labResultStatus) {
		this.labResultStatus = labResultStatus;
	}

	public String getLabTestName() {
		return labTestName;
	}

	public void setLabTestName(String labTestName) {
		this.labTestName = labTestName;
	}

	public String getBabyname() {
		return babyname;
	}

	public void setBabyname(String babyname) {
		this.babyname = babyname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public Boolean getReportReceived() {
		return reportReceived;
	}

	public void setReportReceived(Boolean reportReceived) {
		this.reportReceived = reportReceived;
	}

	public String getSampleid() {
		return sampleid;
	}

	public void setSampleid(String sampleid) {
		this.sampleid = sampleid;
	}

	@Override
	public String toString() {
		return "OrderTest [id=" + id + ", uhid=" + uhid + ", ipnumber=" + ipnumber + ", testid=" + testid
				+ ", creationtime=" + creationtime + ", departmentname=" + departmentname + ", sampleid=" + sampleid
				+ ", reportReceived=" + reportReceived + ", labCreatedDate=" + labCreatedDate + ", labCollectionDate="
				+ labCollectionDate + ", labReportDate=" + labReportDate + ", resultdate=" + resultdate
				+ ", labUpdatedDate=" + labUpdatedDate + ", labApprovedDate=" + labApprovedDate + ", labResultStatus="
				+ labResultStatus + ", labTestName=" + labTestName + ", babyname=" + babyname + ", gender=" + gender
				+ ", age=" + age + "]";
	}
				
}