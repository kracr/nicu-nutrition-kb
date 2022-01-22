package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "ref_intergrowth")
@NamedQuery(name = "RefIntergrowth.findAll", query = "SELECT r FROM RefIntergrowth r")
public class RefIntergrowth implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String gender;
	
	private String weeks;
	
	private String days;

	@Column(columnDefinition = "float4")
	private Float five;
	
	@Column(columnDefinition = "float4")
	private Float three;

	@Column(columnDefinition = "float4")
	private Float ten;
	
	@Column(columnDefinition = "float4")
	private Float fifty;
	
	@Column(columnDefinition = "float4")
	private Float ninety;
	
	@Column(columnDefinition = "float4")
	private Float ninetyfive;
	
	@Column(columnDefinition = "float4")
	private Float ninetyseven;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getWeeks() {
		return weeks;
	}

	public void setWeeks(String weeks) {
		this.weeks = weeks;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public Float getFive() {
		return five;
	}

	public void setFive(Float five) {
		this.five = five;
	}

	public Float getThree() {
		return three;
	}

	public void setThree(Float three) {
		this.three = three;
	}

	public Float getTen() {
		return ten;
	}

	public void setTen(Float ten) {
		this.ten = ten;
	}

	public Float getFifty() {
		return fifty;
	}

	public void setFifty(Float fifty) {
		this.fifty = fifty;
	}

	public Float getNinety() {
		return ninety;
	}

	public void setNinety(Float ninety) {
		this.ninety = ninety;
	}

	public Float getNinetyfive() {
		return ninetyfive;
	}

	public void setNinetyfive(Float ninetyfive) {
		this.ninetyfive = ninetyfive;
	}

	public Float getNinetyseven() {
		return ninetyseven;
	}

	public void setNinetyseven(Float ninetyseven) {
		this.ninetyseven = ninetyseven;
	}

	@Override
	public String toString() {
		return "RefIntergrowth [id=" + id + ", gender=" + gender + ", weeks=" + weeks + ", days=" + days + ", five="
				+ five + ", three=" + three + ", ten=" + ten + ", fifty=" + fifty + ", ninety=" + ninety
				+ ", ninetyfive=" + ninetyfive + ", ninetyseven=" + ninetyseven + "]";
	}

}
