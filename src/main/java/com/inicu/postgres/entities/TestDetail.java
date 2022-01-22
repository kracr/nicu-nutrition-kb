package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.ColumnTransformer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Time;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the ref_testslist database table.
 * 
 */
@Entity
@Table(name="ref_testslist")
@NamedQuery(name="TestDetail.findAll", query="SELECT t FROM TestDetail t")
public class TestDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String testid;
	
	private String testcode;
	
	private String testname;
	
	public String getTestid() {
		return testid;
	}

	public void setTestid(String testid) {
		this.testid = testid;
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

	
	
	

}