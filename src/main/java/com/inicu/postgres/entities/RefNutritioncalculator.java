package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ref_nutritioncalculator database table.
 * 
 */
@Entity
@Table(name="ref_nutritioncalculator")
@NamedQuery(name="RefNutritioncalculator.findAll", query="SELECT r FROM RefNutritioncalculator r")
public class RefNutritioncalculator implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long nutritionid;

	@Column(columnDefinition="float4")
	private Float calcium;
	
	@Column(columnDefinition="float4")
	private Float energy;
	
	@Column(columnDefinition="float4")
	private Float fat;

	@Column(name="feedtype_id")
	private String feedtypeId;

	@Column(columnDefinition="float4")
	private Float iron;

	@Column(columnDefinition="float4")
	private Float phosphorus;

	@Column(columnDefinition="float4")
	private Float protein;

	@Column(columnDefinition="float4")
	private Float vitamina;

	@Column(columnDefinition="float4")
	private Float vitamind;
	
	@Column(columnDefinition="float4")
	private Float carbohydrates;

	public RefNutritioncalculator() {
	}

	public Long getNutritionid() {
		return nutritionid;
	}

	public void setNutritionid(Long nutritionid) {
		this.nutritionid = nutritionid;
	}

	public Float getCalcium() {
		return this.calcium;
	}

	public void setCalcium(Float calcium) {
		this.calcium = calcium;
	}

	public Float getEnergy() {
		return this.energy;
	}

	public void setEnergy(Float energy) {
		this.energy = energy;
	}

	public Float getFat() {
		return this.fat;
	}

	public void setFat(Float fat) {
		this.fat = fat;
	}

	public String getFeedtypeId() {
		return this.feedtypeId;
	}

	public void setFeedtypeId(String feedtypeId) {
		this.feedtypeId = feedtypeId;
	}

	public Float getIron() {
		return this.iron;
	}

	public void setIron(Float iron) {
		this.iron = iron;
	}

	public Float getPhosphorus() {
		return this.phosphorus;
	}

	public void setPhosphorus(Float phosphorus) {
		this.phosphorus = phosphorus;
	}

	public Float getProtein() {
		return this.protein;
	}

	public void setProtein(Float protein) {
		this.protein = protein;
	}

	public Float getVitamina() {
		return this.vitamina;
	}

	public void setVitamina(Float vitamina) {
		this.vitamina = vitamina;
	}

	public Float getVitamind() {
		return this.vitamind;
	}

	public void setVitamind(Float vitamind) {
		this.vitamind = vitamind;
	}

	public Float getCarbohydrates() {
		return carbohydrates;
	}

	public void setCarbohydrates(Float carbohydrates) {
		this.carbohydrates = carbohydrates;
	}

}