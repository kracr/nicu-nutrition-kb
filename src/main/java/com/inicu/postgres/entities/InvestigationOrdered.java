package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the investigation_ordered database table.
 * 
 * @author Sourabh Verma
 */
@Entity
@Table(name = "investigation_ordered")
@NamedQuery(name = "InvestigationOrdered.findAll", query = "SELECT b FROM InvestigationOrdered b")
public class InvestigationOrdered implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long investigationorderid;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String assesment_type;

	private String assesmentid;

	private String uhid;

	private String category;

	private String testcode;

	private String testname;

	private Timestamp investigationorder_time;

	private String investigationorder_user;

	private Timestamp senttolab_time;

	private String senttolab_user;

	private Timestamp reportreceived_time;

	private String reportreceived_user;

	private String testslistid;

	private String order_status;
	
	private String sampleid;


	@Column(columnDefinition = "bool")
	private Boolean issamplevisible;
	
	@Column(columnDefinition = "bool")
	private Boolean issamplesent;
	
	@Transient
	private Boolean issampleSelected;

	public InvestigationOrdered() {
		super();
	}

	public Long getInvestigationorderid() {
		return investigationorderid;
	}

	public void setInvestigationorderid(Long investigationorderid) {
		this.investigationorderid = investigationorderid;
	}

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public boolean issamplevisible() {
		return issamplevisible;
	}

	public void setsamplevisible(boolean samplevisible) {
		issamplevisible = samplevisible;
	}

	public Timestamp getModificationtime() {
		return modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getAssesment_type() {
		return assesment_type;
	}

	public void setAssesment_type(String assesment_type) {
		this.assesment_type = assesment_type;
	}

	public String getAssesmentid() {
		return assesmentid;
	}

	public void setAssesmentid(String assesmentid) {
		this.assesmentid = assesmentid;
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTestcode() {
		return testcode;
	}

	public void setTestcode(String testcode) {
		this.testcode = testcode;
	}

	public String getTestname() {
		return testname;
	}

	public void setTestname(String testname) {
		this.testname = testname;
	}

	public Timestamp getInvestigationorder_time() {
		return investigationorder_time;
	}

	public void setInvestigationorder_time(Timestamp investigationorder_time) {
		this.investigationorder_time = investigationorder_time;
	}

	public String getInvestigationorder_user() {
		return investigationorder_user;
	}

	public void setInvestigationorder_user(String investigationorder_user) {
		this.investigationorder_user = investigationorder_user;
	}

	public Timestamp getSenttolab_time() {
		return senttolab_time;
	}

	public void setSenttolab_time(Timestamp senttolab_time) {
		this.senttolab_time = senttolab_time;
	}

	public String getSenttolab_user() {
		return senttolab_user;
	}

	public void setSenttolab_user(String senttolab_user) {
		this.senttolab_user = senttolab_user;
	}

	public Timestamp getReportreceived_time() {
		return reportreceived_time;
	}

	public void setReportreceived_time(Timestamp reportreceived_time) {
		this.reportreceived_time = reportreceived_time;
	}

	public String getReportreceived_user() {
		return reportreceived_user;
	}

	public void setReportreceived_user(String reportreceived_user) {
		this.reportreceived_user = reportreceived_user;
	}

	public String getOrder_status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public String getTestslistid() {
		return testslistid;
	}

	public void setTestslistid(String testslistid) {
		this.testslistid = testslistid;
	}

	public String getSampleid() {
		return sampleid;
	}

	public void setSampleid(String sampleid) {
		this.sampleid = sampleid;
	}

	public Boolean getIssamplesent() {
		return issamplesent;
	}

	public void setIssamplesent(Boolean issamplesent) {
		this.issamplesent = issamplesent;
	}

	public Boolean getIssampleSelected() {
		return issampleSelected;
	}

	public void setIssampleSelected(Boolean issampleSelected) {
		this.issampleSelected = issampleSelected;
	}

	@Override
	public String toString() {
		return "InvestigationOrdered [investigationorderid="
				+ investigationorderid + ", creationtime=" + creationtime
				+ ", modificationtime=" + modificationtime
				+ ", assesment_type=" + assesment_type + ", assesmentid="
				+ assesmentid + ", uhid=" + uhid + ", category=" + category
				+ ", testcode=" + testcode + ", testname=" + testname
				+ ", investigationorder_time=" + investigationorder_time
				+ ", investigationorder_user=" + investigationorder_user
				+ ", senttolab_time=" + senttolab_time + ", senttolab_user="
				+ senttolab_user + ", reportreceived_time="
				+ reportreceived_time + ", reportreceived_user="
				+ reportreceived_user + ", testslistid=" + testslistid
				+ ", order_status=" + order_status + ", sampleid=" + sampleid
				+ ", issamplesent=" + issamplesent + "]";
	}

}
