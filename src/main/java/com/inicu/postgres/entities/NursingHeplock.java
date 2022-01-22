package com.inicu.postgres.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "nursing_heplock")
@NamedQuery(name = "NursingHeplock.findAll", query = "SELECT n FROM NursingHeplock n")
public class NursingHeplock {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long nursing_heplock_id;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String uhid;

	private String loggeduser;

	private Long central_line_id;

	private Timestamp execution_time;

	@Column(columnDefinition = "Float4", name = "delivered_volume")
	private Float deliveredVolume;

	@Column(name = "order_date")
	private Timestamp orderDate;

	private String comments;
	
	@Column(name = "solution_type")
	private String solutionType;
	
	@Column(name = "anti_coagulent_type")
	private String antiCoagulentType;
	
	@Column(name = "anti_coagulent_brand")
	private String antiCoagulentBrand;
	
	@Column(columnDefinition = "Float4", name = "heparin_total_volume")
	private Float heparinTotalVolume;
	
	@Column(columnDefinition = "Float4", name = "heparin_rate")
	private Float heparinRate;
	 
	@Column(columnDefinition = "Float4", name = "remaining_volume")
	private Float remainingVolume;
	
	@Column(columnDefinition = "bool")
	private Boolean flag;

	public Long getNursing_heplock_id() {
		return nursing_heplock_id;
	}

	public void setNursing_heplock_id(Long nursing_heplock_id) {
		this.nursing_heplock_id = nursing_heplock_id;
	}

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public Timestamp getModificationtime() {
		return modificationtime;
	}

	public String getUhid() {
		return uhid;
	}

	public String getLoggeduser() {
		return loggeduser;
	}

	public Long getCentral_line_id() {
		return central_line_id;
	}

	public Timestamp getExecution_time() {
		return execution_time;
	}

	public Float getDeliveredVolume() {
		return deliveredVolume;
	}

	public Timestamp getOrderDate() {
		return orderDate;
	}

	public String getComments() {
		return comments;
	}

	public String getSolutionType() {
		return solutionType;
	}

	public String getAntiCoagulentType() {
		return antiCoagulentType;
	}

	public String getAntiCoagulentBrand() {
		return antiCoagulentBrand;
	}

	public Float getHeparinTotalVolume() {
		return heparinTotalVolume;
	}

	public Float getHeparinRate() {
		return heparinRate;
	}

	public Float getRemainingVolume() {
		return remainingVolume;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public void setCentral_line_id(Long central_line_id) {
		this.central_line_id = central_line_id;
	}

	public void setExecution_time(Timestamp execution_time) {
		this.execution_time = execution_time;
	}

	public void setDeliveredVolume(Float deliveredVolume) {
		this.deliveredVolume = deliveredVolume;
	}

	public void setOrderDate(Timestamp orderDate) {
		this.orderDate = orderDate;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setSolutionType(String solutionType) {
		this.solutionType = solutionType;
	}

	public void setAntiCoagulentType(String antiCoagulentType) {
		this.antiCoagulentType = antiCoagulentType;
	}

	public void setAntiCoagulentBrand(String antiCoagulentBrand) {
		this.antiCoagulentBrand = antiCoagulentBrand;
	}

	public void setHeparinTotalVolume(Float heparinTotalVolume) {
		this.heparinTotalVolume = heparinTotalVolume;
	}

	public void setHeparinRate(Float heparinRate) {
		this.heparinRate = heparinRate;
	}

	public void setRemainingVolume(Float remainingVolume) {
		this.remainingVolume = remainingVolume;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

}
