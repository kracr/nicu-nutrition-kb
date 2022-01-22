package com.inicu.postgres.entities;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.*;

import org.hibernate.annotations.ColumnTransformer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Time;
import java.sql.Date;
import java.sql.Timestamp;

/*
 CREATE TABLE kdah.test_result_inicu
(
  testresultid bigint NOT NULL DEFAULT nextval('kdah.testresults_id_seq'::regclass),
  creationtime timestamp with time zone,
  uhid character varying(50),
  testid character varying(6),
  itemid character varying(10),
  itemname character varying(100),
  itemvalue character varying(50),
  itemunit character varying(50),
  normalrange character varying(50),
  resultdate date NOT NULL,
  loggeduser character varying(50),
  CONSTRAINT testresult_pk PRIMARY KEY (testresultid)
)
 */


/**
 * The persistent class for the test_result database table.
 * 
 */
@Entity
@Table(name="test_result_inicu")
@NamedQuery(name="TestItemResultInicu.findAll", query="SELECT t FROM TestItemResultInicu t")
public class TestItemResultInicu implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long testresultid;
	private Timestamp creationtime;
	
	private String uhid;
	
	private String testid;
	
	private String itemid;
	
	private String itemname;
	
	private String itemvalue;
	
	private String itemunit;

	private String normalrange;
	
	private Date resultdate;
	
	private String loggeduser;

	

	public Long getTestresultid() {
		return testresultid;
	}

	public void setTestresultid(Long testresultid) {
		this.testresultid = testresultid;
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

	public Date getResultdate() {
		return resultdate;
	}

	public void setResultdate(Date resultdate) {
		this.resultdate = resultdate;
	}

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}
	
		
}