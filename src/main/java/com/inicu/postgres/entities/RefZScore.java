package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "ref_zscore")
@NamedQuery(name = "RefZScore.findAll", query = "SELECT r FROM RefZScore r")
public class RefZScore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String gender;
	
	private String weeks;
	
	@Column(columnDefinition = "float4")
	private Float minusthree;
	
	@Column(columnDefinition = "float4")
	private Float minustwo;

	@Column(columnDefinition = "float4")
	private Float minusone;
	
	@Column(columnDefinition = "float4")
	private Float zero;
	
	@Column(columnDefinition = "float4")
	private Float one;
	
	@Column(columnDefinition = "float4")
	private Float two;
	
	@Column(columnDefinition = "float4")
	private Float three;

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

	public Float getMinusthree() {
		return minusthree;
	}

	public void setMinusthree(Float minusthree) {
		this.minusthree = minusthree;
	}

	public Float getMinustwo() {
		return minustwo;
	}

	public void setMinustwo(Float minustwo) {
		this.minustwo = minustwo;
	}

	public Float getMinusone() {
		return minusone;
	}

	public void setMinusone(Float minusone) {
		this.minusone = minusone;
	}

	public Float getZero() {
		return zero;
	}

	public void setZero(Float zero) {
		this.zero = zero;
	}

	public Float getOne() {
		return one;
	}

	public void setOne(Float one) {
		this.one = one;
	}

	public Float getTwo() {
		return two;
	}

	public void setTwo(Float two) {
		this.two = two;
	}

	public Float getThree() {
		return three;
	}

	public void setThree(Float three) {
		this.three = three;
	}

	@Override
	public String toString() {
		return "RefZScore [id=" + id + ", gender=" + gender + ", weeks=" + weeks + ", minusthree=" + minusthree
				+ ", minustwo=" + minustwo + ", minusone=" + minusone + ", zero=" + zero + ", one=" + one + ", two="
				+ two + ", three=" + three + "]";
	}

}
