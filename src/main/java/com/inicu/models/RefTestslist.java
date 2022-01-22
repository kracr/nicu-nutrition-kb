package com.inicu.models;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ref_testslist database table.
 * 
 */
@Entity
@Table(name="ref_testslist")
@NamedQuery(name="RefTestslist.findAll", query="SELECT r FROM RefTestslist r")
public class RefTestslist implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String testid;
	
	@Column(name="assesment_category")
	private String assesmentCategory;

	@Column(columnDefinition="bool")
	private Boolean isactive;

	private String testcode;

	private String testname;

	@Column(name ="order_category")
	private String orderCategory;
	
	@Transient
	private Boolean isSelected;

	public RefTestslist() {
	}

	public String getTestid() {
		return this.testid;
	}

	public void setTestid(String testid) {
		this.testid = testid;
	}

	public String getAssesmentCategory() {
		return this.assesmentCategory;
	}

	public void setAssesmentCategory(String assesmentCategory) {
		this.assesmentCategory = assesmentCategory;
	}

	public Boolean getIsactive() {
		return this.isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	public String getTestcode() {
		return this.testcode;
	}

	public void setTestcode(String testcode) {
		this.testcode = testcode;
	}

	public String getTestname() {
		return this.testname;
	}

	public void setTestname(String testname) {
		this.testname = testname;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}


	public String getOrderCategory() {
		return orderCategory;
	}

	public void setOrderCategory(String orderCategory) {
		this.orderCategory = orderCategory;
	}
}