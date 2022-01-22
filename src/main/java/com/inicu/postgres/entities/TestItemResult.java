package com.inicu.postgres.entities;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.*;

import org.hibernate.annotations.ColumnTransformer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Time;
import java.util.Date;
import java.sql.Timestamp;

/*
 * prn character varying(50),
  testid character varying(6),
  itemid character varying(10),
  itemname character varying(100),
  itemvalue character varying(50),
  itemunit character varying(50),
  normalrange character varying(50),
  regno bigint,
  id bigint NOT NULL DEFAULT nextval('kdah.testresults_id_seq'::regclass),
  resultdate date NOT NULL,
  creationtime timestamp with time zone,
  CONSTRAINT testresults_pkey PRIMARY KEY (id)
 */


/**
 * The persistent class for the test_result database table.
 * 
 */
/**
 * @author Oxyent
 *
 */
@Entity
@Table(name="test_result")
@NamedQuery(name="TestItemResult.findAll", query="SELECT t FROM TestItemResult t")
public class TestItemResult implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String prn;
	
	private String testid;
	
	private String itemid;
	
	private String itemname;
	
	private String itemvalue;
	
	private String itemunit;
	
	private String normalrange;
	
	private String regno;
		
	private Timestamp creationtime;
	
	private String resulttype;
	
	private String reporturl;
	
	private String sampletype;
	
	//Order
	@Column(name="lab_created_date")
	private Timestamp labCreatedDate;
	
	@Column(name="lab_posted_date")
	private Timestamp labPostedDate;
	
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
	
	@Column(name="lab_updated_by")
	private String labUpdatedBy;
	
	@Column(name="lab_testresultid")
	private Long labTestResultId;
	
	@Column(name="lab_testname")
	private String labTestName;
	
	private String lab_center_code;
	//To know from which view the data is coming(Ex - Micro, Histo) for apollo
	@Column(name="view_type")
	private String viewType;
	
	private String gross;

	private String microscopic;

	private String impression;

	private String advice;

	private String clinicalHistory;

	private String detail;
	
	private String labObservationName;
	
	private String value;
	
	private String unit;
	
	private String labObservationComment;
	
	private String organismNameDisplayName;
	
	private String colonyCount;
	
	private String colonyCountComment;
	
	private String antibioticName;
	
	private String antibioticInterpreatation;
	
	private String mic;
	
	private String reportType;
	
	private String displayReading;
	
	private String babyname;
	
	private String gender;
	
	private String age;

	@Column(name ="template_xml")
	private String templateXml;

	@Column(name ="template_html")
	private String templateHtml;

	@Column(name ="order_no")
	private String orderNo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPrn() {
		return prn;
	}

	public void setPrn(String prn) {
		this.prn = prn;
	}

	public String getTestid() {
		return testid;
	}

	public void setTestid(String testid) {
		this.testid = testid;
	}

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public String getItemname() {
		return itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public String getItemvalue() {
		return itemvalue;
	}

	public void setItemvalue(String itemvalue) {
		this.itemvalue = itemvalue;
	}

	public String getItemunit() {
		return itemunit;
	}

	public void setItemunit(String itemunit) {
		this.itemunit = itemunit;
	}

	public String getNormalrange() {
		return normalrange;
	}

	public void setNormalrange(String normalrange) {
		this.normalrange = normalrange;
	}

	public String getRegno() {
		return regno;
	}

	public void setRegno(String regno) {
		this.regno = regno;
	}

	public Date getResultdate() {
		return resultdate;
	}

	public void setResultdate(Date resultdate) {
		this.resultdate = resultdate;
	}

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getLabResultStatus() {
		return labResultStatus;
	}

	public void setLabResultStatus(String labResultStatus) {
		this.labResultStatus = labResultStatus;
	}

	public Timestamp getLabCreatedDate() {
		return labCreatedDate;
	}

	public void setLabCreatedDate(Timestamp labCreatedDate) {
		this.labCreatedDate = labCreatedDate;
	}

	public Timestamp getLabUpdatedDate() {
		return labUpdatedDate;
	}

	public void setLabUpdatedDate(Timestamp labUpdatedDate) {
		this.labUpdatedDate = labUpdatedDate;
	}

	public Date getLabCollectionDate() {
		return labCollectionDate;
	}

	public void setLabCollectionDate(Date labCollectionDate) {
		this.labCollectionDate = labCollectionDate;
	}

	public Timestamp getLabPostedDate() {
		return labPostedDate;
	}

	public void setLabPostedDate(Timestamp labPostedDate) {
		this.labPostedDate = labPostedDate;
	}

	public Timestamp getLabReportDate() {
		return labReportDate;
	}

	public void setLabReportDate(Timestamp labReportDate) {
		this.labReportDate = labReportDate;
	}

	public String getLabUpdatedBy() {
		return labUpdatedBy;
	}

	public void setLabUpdatedBy(String labUpdatedBy) {
		this.labUpdatedBy = labUpdatedBy;
	}

	public Long getLabTestResultId() {
		return labTestResultId;
	}

	public void setLabTestResultId(Long labTestResultId) {
		this.labTestResultId = labTestResultId;
	}

	public String getLabTestName() {
		return labTestName;
	}

	public void setLabTestName(String labTestName) {
		this.labTestName = labTestName;
	}

	public String getLab_center_code() {
		return lab_center_code;
	}

	public void setLab_center_code(String lab_center_code) {
		this.lab_center_code = lab_center_code;
	}

	public Timestamp getLabApprovedDate() {
		return labApprovedDate;
	}

	public void setLabApprovedDate(Timestamp labApprovedDate) {
		this.labApprovedDate = labApprovedDate;
	}

	public String getResulttype() {
		return resulttype;
	}

	public void setResulttype(String resulttype) {
		this.resulttype = resulttype;
	}

	public String getReporturl() {
		return reporturl;
	}

	public void setReporturl(String reporturl) {
		this.reporturl = reporturl;
	}

	public String getSampletype() {
		return sampletype;
	}

	public void setSampletype(String sampletype) {
		this.sampletype = sampletype;
	}

	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}

	public String getGross() {
		return gross;
	}

	public void setGross(String gross) {
		this.gross = gross;
	}

	public String getMicroscopic() {
		return microscopic;
	}

	public void setMicroscopic(String microscopic) {
		this.microscopic = microscopic;
	}

	public String getImpression() {
		return impression;
	}

	public void setImpression(String impression) {
		this.impression = impression;
	}

	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

	public String getClinicalHistory() {
		return clinicalHistory;
	}

	public void setClinicalHistory(String clinicalHistory) {
		this.clinicalHistory = clinicalHistory;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getLabObservationName() {
		return labObservationName;
	}

	public void setLabObservationName(String labObservationName) {
		this.labObservationName = labObservationName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getLabObservationComment() {
		return labObservationComment;
	}

	public void setLabObservationComment(String labObservationComment) {
		this.labObservationComment = labObservationComment;
	}

	public String getOrganismNameDisplayName() {
		return organismNameDisplayName;
	}

	public void setOrganismNameDisplayName(String organismNameDisplayName) {
		this.organismNameDisplayName = organismNameDisplayName;
	}

	public String getColonyCount() {
		return colonyCount;
	}

	public void setColonyCount(String colonyCount) {
		this.colonyCount = colonyCount;
	}

	public String getColonyCountComment() {
		return colonyCountComment;
	}

	public void setColonyCountComment(String colonyCountComment) {
		this.colonyCountComment = colonyCountComment;
	}

	public String getAntibioticName() {
		return antibioticName;
	}

	public void setAntibioticName(String antibioticName) {
		this.antibioticName = antibioticName;
	}

	public String getAntibioticInterpreatation() {
		return antibioticInterpreatation;
	}

	public void setAntibioticInterpreatation(String antibioticInterpreatation) {
		this.antibioticInterpreatation = antibioticInterpreatation;
	}

	public String getMic() {
		return mic;
	}

	public void setMic(String mic) {
		this.mic = mic;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getDisplayReading() {
		return displayReading;
	}

	public void setDisplayReading(String displayReading) {
		this.displayReading = displayReading;
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

	public String getTemplateXml() {
		return templateXml;
	}

	public void setTemplateXml(String templateXml) {
		this.templateXml = templateXml;
	}

	public String getTemplateHtml() {
		return templateHtml;
	}

	public void setTemplateHtml(String templateHtml) {
		this.templateHtml = templateHtml;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Override
	public String toString() {
		return "TestItemResult{" +
				"id=" + id +
				", prn='" + prn + '\'' +
				", testid='" + testid + '\'' +
				", itemid='" + itemid + '\'' +
				", itemname='" + itemname + '\'' +
				", itemvalue='" + itemvalue + '\'' +
				", itemunit='" + itemunit + '\'' +
				", normalrange='" + normalrange + '\'' +
				", regno='" + regno + '\'' +
				", creationtime=" + creationtime +
				", resulttype='" + resulttype + '\'' +
				", reporturl='" + reporturl + '\'' +
				", sampletype='" + sampletype + '\'' +
				", labCreatedDate=" + labCreatedDate +
				", labPostedDate=" + labPostedDate +
				", labCollectionDate=" + labCollectionDate +
				", labReportDate=" + labReportDate +
				", resultdate=" + resultdate +
				", labUpdatedDate=" + labUpdatedDate +
				", labApprovedDate=" + labApprovedDate +
				", labResultStatus='" + labResultStatus + '\'' +
				", labUpdatedBy='" + labUpdatedBy + '\'' +
				", labTestResultId=" + labTestResultId +
				", labTestName='" + labTestName + '\'' +
				", lab_center_code='" + lab_center_code + '\'' +
				", viewType='" + viewType + '\'' +
				", gross='" + gross + '\'' +
				", microscopic='" + microscopic + '\'' +
				", impression='" + impression + '\'' +
				", advice='" + advice + '\'' +
				", clinicalHistory='" + clinicalHistory + '\'' +
				", detail='" + detail + '\'' +
				", labObservationName='" + labObservationName + '\'' +
				", value='" + value + '\'' +
				", unit='" + unit + '\'' +
				", labObservationComment='" + labObservationComment + '\'' +
				", organismNameDisplayName='" + organismNameDisplayName + '\'' +
				", colonyCount='" + colonyCount + '\'' +
				", colonyCountComment='" + colonyCountComment + '\'' +
				", antibioticName='" + antibioticName + '\'' +
				", antibioticInterpreatation='" + antibioticInterpreatation + '\'' +
				", mic='" + mic + '\'' +
				", reportType='" + reportType + '\'' +
				", displayReading='" + displayReading + '\'' +
				", babyname='" + babyname + '\'' +
				", gender='" + gender + '\'' +
				", age='" + age + '\'' +
				", templateXml='" + templateXml + '\'' +
				", templateHtml='" + templateHtml + '\'' +
				'}';
	}
}