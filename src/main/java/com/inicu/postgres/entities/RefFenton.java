package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the ref_fenton database table.
 * 
 * @author Sourabh Verma
 */
@Entity
@Table(name = "ref_fenton")
@NamedQuery(name = "RefFenton.findAll", query = "SELECT r FROM RefFenton r")
public class RefFenton implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String gender;

	private String age;

	@Column(columnDefinition = "varchar")
	private Integer percentile;

	@Column(columnDefinition = "float4")
	private Float weight;

	@Column(columnDefinition = "float4")
	private Float height;

	@Column(columnDefinition = "float4")
	private Float headcircum;

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

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public Integer getPercentile() {
		return percentile;
	}

	public void setPercentile(Integer percentile) {
		this.percentile = percentile;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public Float getHeight() {
		return height;
	}

	public void setHeight(Float height) {
		this.height = height;
	}

	public Float getHeadcircum() {
		return headcircum;
	}

	public void setHeadcircum(Float headcircum) {
		this.headcircum = headcircum;
	}

	@Override
	public String toString() {
		return "RefFenton [id=" + id + ", gender=" + gender + ", age=" + age + ", percentile=" + percentile
				+ ", weight=" + weight + ", height=" + height + ", headcircum=" + headcircum + "]";
	}

}
