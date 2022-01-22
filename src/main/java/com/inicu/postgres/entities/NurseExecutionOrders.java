package com.inicu.postgres.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "nurse_execution_orders")
@NamedQuery(name = "NurseExecutionOrders.findAll", query = "SELECT b FROM NurseExecutionOrders b")
@JsonIgnoreProperties(ignoreUnknown = true)
public class NurseExecutionOrders {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long nurse_execution_orders_id;
	
	private Timestamp creationtime;

	private String loggeduser;

	private Timestamp modificationtime;

	private String uhid;
	
	private String eventname;
	
	private Timestamp assessmentdate;
	
	@Column(name="order_text")
	private String orderText;
	
	private Timestamp executiontime;
	
	@Column(name="is_execution",columnDefinition = "bool")
	private Boolean isExecution;
	
	@Transient
	@Column(columnDefinition = "bool")
	private Boolean isExecutionFinal;

	public Long getNurse_execution_orders_id() {
		return nurse_execution_orders_id;
	}

	public void setNurse_execution_orders_id(Long nurse_execution_orders_id) {
		this.nurse_execution_orders_id = nurse_execution_orders_id;
	}

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
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

	public String getEventname() {
		return eventname;
	}

	public void setEventname(String eventname) {
		this.eventname = eventname;
	}

	public Timestamp getAssessmentdate() {
		return assessmentdate;
	}

	public void setAssessmentdate(Timestamp assessmentdate) {
		this.assessmentdate = assessmentdate;
	}

	public String getOrderText() {
		return orderText;
	}

	public void setOrderText(String orderText) {
		this.orderText = orderText;
	}

	public Timestamp getExecutiontime() {
		return executiontime;
	}

	public void setExecutiontime(Timestamp executiontime) {
		this.executiontime = executiontime;
	}

	public Boolean getIsExecution() {
		return isExecution;
	}

	public void setIsExecution(Boolean isExecution) {
		this.isExecution = isExecution;
	}

	public Boolean getIsExecutionFinal() {
		return isExecutionFinal;
	}

	public void setIsExecutionFinal(Boolean isExecutionFinal) {
		this.isExecutionFinal = isExecutionFinal;
	}

}
