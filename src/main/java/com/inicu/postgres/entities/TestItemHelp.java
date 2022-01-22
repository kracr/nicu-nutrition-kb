package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.ColumnTransformer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Time;
import java.util.Date;
import java.sql.Timestamp;




@Entity
@Table(name="ref_testitemhelp")
@NamedQuery(name="TestItemHelp.findAll", query="SELECT t FROM TestItemHelp t")
public class TestItemHelp implements Serializable {
	
	@Id
	private String itemid;
	
	@Column(name="testid")
	private String testid;
	
	private String testcode;
	
	private String itemname;
	
	private String testvalue;
	
	@Column(name="unit")
	private String itemunit;
	
	private String normalrange;
	
	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

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

	public String getItemname() {
		return itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public String getTestvalue() {
		return testvalue;
	}

	public void setTestvalue(String testvalue) {
		this.testvalue = testvalue;
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

	
	
	
}