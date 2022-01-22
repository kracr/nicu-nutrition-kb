package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.inicu.models.KeyValueObj;

/**
 * The persistent class for the lumbar_puncture database table.
 * 
 * @author Sourabh Verma
 */
@Entity
@Table(name = "lumbar_puncture")
@NamedQuery(name = "LumbarPuncture.findAll", query = "SELECT s FROM LumbarPuncture s")
public class LumbarPuncture implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long lumbar_puncture_id;

	@Column(columnDefinition = "timestamp with time zone")
	private Timestamp creationtime;

	private String uhid;

	private String lumbar_space;

	private String lumbar_size;

	private String lumbar_csf;

	@Transient
	private Object lumbar_date;

	@Transient
	private Integer lumbar_hrs;

	@Transient
	private Integer lumbar_mins;

	@Transient
	private String lumbar_meridian;

	@Column(columnDefinition = "timestamp with time zone")
	private Timestamp lumbar_timestamp;

	private String loggeduser;

	private String progressnotes;
	
	private String comments;
	
	@Column(columnDefinition = "float4")
	private Float sugar;
	
	@Column(columnDefinition = "float4")
	private Float protein;
	
	@Column(columnDefinition = "float4")
	private Float tnc;
	
	private String csfCultureReport;

	private String csfCultureReportType;

	private String csfCultureReportOrganism;

	private String csfCultureReportSensitivity;
	
	@Column(name="pre_procedure_care")
	private String preProcedureCare;

	@Transient
	private List<KeyValueObj> procedureCareList;

	public LumbarPuncture() {
		super();
	}

	public Long getLumbar_puncture_id() {
		return lumbar_puncture_id;
	}

	public void setLumbar_puncture_id(Long lumbar_puncture_id) {
		this.lumbar_puncture_id = lumbar_puncture_id;
	}

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getLumbar_space() {
		return lumbar_space;
	}

	public void setLumbar_space(String lumbar_space) {
		this.lumbar_space = lumbar_space;
	}

	public String getLumbar_size() {
		return lumbar_size;
	}

	public void setLumbar_size(String lumbar_size) {
		this.lumbar_size = lumbar_size;
	}

	public String getLumbar_csf() {
		return lumbar_csf;
	}

	public void setLumbar_csf(String lumbar_csf) {
		this.lumbar_csf = lumbar_csf;
	}

	public Object getLumbar_date() {
		return lumbar_date;
	}

	public void setLumbar_date(Object lumbar_date) {
		this.lumbar_date = lumbar_date;
	}

	public Integer getLumbar_hrs() {
		return lumbar_hrs;
	}

	public void setLumbar_hrs(Integer lumbar_hrs) {
		this.lumbar_hrs = lumbar_hrs;
	}

	public Integer getLumbar_mins() {
		return lumbar_mins;
	}

	public void setLumbar_mins(Integer lumbar_mins) {
		this.lumbar_mins = lumbar_mins;
	}

	public String getLumbar_meridian() {
		return lumbar_meridian;
	}

	public void setLumbar_meridian(String lumbar_meridian) {
		this.lumbar_meridian = lumbar_meridian;
	}

	public Timestamp getLumbar_timestamp() {
		return lumbar_timestamp;
	}

	public void setLumbar_timestamp(Timestamp lumbar_timestamp) {
		this.lumbar_timestamp = lumbar_timestamp;
	}

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public String getProgressnotes() {
		return progressnotes;
	}

	public void setProgressnotes(String progressnotes) {
		this.progressnotes = progressnotes;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Float getSugar() {
		return sugar;
	}

	public void setSugar(Float sugar) {
		this.sugar = sugar;
	}

	public Float getProtein() {
		return protein;
	}

	public void setProtein(Float protein) {
		this.protein = protein;
	}

	public Float getTnc() {
		return tnc;
	}

	public void setTnc(Float tnc) {
		this.tnc = tnc;
	}

	public String getCsfCultureReport() {
		return csfCultureReport;
	}

	public void setCsfCultureReport(String csfCultureReport) {
		this.csfCultureReport = csfCultureReport;
	}

	public String getCsfCultureReportType() {
		return csfCultureReportType;
	}

	public void setCsfCultureReportType(String csfCultureReportType) {
		this.csfCultureReportType = csfCultureReportType;
	}

	public String getCsfCultureReportOrganism() {
		return csfCultureReportOrganism;
	}

	public void setCsfCultureReportOrganism(String csfCultureReportOrganism) {
		this.csfCultureReportOrganism = csfCultureReportOrganism;
	}

	public String getCsfCultureReportSensitivity() {
		return csfCultureReportSensitivity;
	}

	public void setCsfCultureReportSensitivity(String csfCultureReportSensitivity) {
		this.csfCultureReportSensitivity = csfCultureReportSensitivity;
	}
	
	public String getPreProcedureCare() {
		return preProcedureCare;
	}

	public void setPreProcedureCare(String preProcedureCare) {
		this.preProcedureCare = preProcedureCare;
	}

	public List<KeyValueObj> getProcedureCareList() {
		return procedureCareList;
	}

	public void setProcedureCareList(List<KeyValueObj> procedureCareList) {
		this.procedureCareList = procedureCareList;
	}

	@Override
	public String toString() {
		return "LumbarPuncture [lumbar_puncture_id=" + lumbar_puncture_id + ", creationtime=" + creationtime + ", uhid="
				+ uhid + ", lumbar_space=" + lumbar_space + ", lumbar_size=" + lumbar_size + ", lumbar_csf="
				+ lumbar_csf + ", lumbar_date=" + lumbar_date + ", lumbar_hrs=" + lumbar_hrs + ", lumbar_mins="
				+ lumbar_mins + ", lumbar_meridian=" + lumbar_meridian + ", lumbar_timestamp=" + lumbar_timestamp
				+ ", loggeduser=" + loggeduser + ", progressnotes=" + progressnotes + ", comments=" + comments
				+ ", sugar=" + sugar + ", protein=" + protein + ", tnc=" + tnc + ", csfCultureReport="
				+ csfCultureReport + ", csfCultureReportType=" + csfCultureReportType + ", csfCultureReportOrganism="
				+ csfCultureReportOrganism + ", csfCultureReportSensitivity=" + csfCultureReportSensitivity
				+ ", preProcedureCare=" + preProcedureCare + ", procedureCareList=" + procedureCareList + "]";
	}

}
