package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "nutritional_compliance")
@NamedQuery(name = "NutritionalCompliance.findAll", query = "SELECT b FROM NutritionalCompliance b")
public class NutritionalCompliance implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long nutritionalcompianceid;
	
	private Timestamp creationtime;
	
	private Timestamp modificationtime;
	
	private String uhid;
	
	private Integer dol;
	
	private String details;
	
	@Column(columnDefinition = "Float4")
	private Float feed_volume;
	
	@Column(columnDefinition = "Float4")
	private Float feed_advancement;
	
	@Column(columnDefinition = "Float4")
	private Float upper_feed_volume;
	
	@Column(columnDefinition = "Float4")
	private Float upper_feed_advancement;
	
	@Column(columnDefinition = "Float4")
	private Float given_feed;
	
	@Transient
	private String feed_volume_range;
	
	@Transient
	private String feed_advancement_range;
	
	@Column(columnDefinition = "Float4")
	private Float cho_lower_pn_feed_volume;
	
	@Column(columnDefinition = "Float4")
	private Float cho_upper_pn_feed_volume;
	
	@Column(columnDefinition = "Float4")
	private Float cho_lower_pn_feed_advancement;
	
	@Column(columnDefinition = "Float4")
	private Float cho_upper_pn_feed_advancement;
	
	@Transient
	private String given_cho;
	
	@Transient
	private String cho_volume_range;
	
	@Transient
	private String cho_advancement_range;
	
	@Column(columnDefinition = "Float4")
	private Float protein_lower_pn_feed_volume;
	
	@Column(columnDefinition = "Float4")
	private Float protein_upper_pn_feed_volume;
	
	@Column(columnDefinition = "Float4")
	private Float protein_lower_pn_feed_advancement;
	
	@Column(columnDefinition = "Float4")
	private Float protein_upper_pn_feed_advancement;
	
	@Transient
	private String given_protein;
	
	@Transient
	private String protein_volume_range;
	
	@Transient
	private String protein_advancement_range;
	
	@Column(columnDefinition = "Float4")
	private Float sodium_lower_pn_feed_volume;
	
	@Column(columnDefinition = "Float4")
	private Float sodium_upper_pn_feed_volume;
	
	@Transient
	private String given_sodium;
	
	@Transient
	private String sodium_volume_range;
	
	@Column(columnDefinition = "Float4")
	private Float calcium_pn_feed_volume;
	
	@Transient
	private String given_calcium;
	
	@Column(columnDefinition = "Float4")
	private Float potassium_pn_feed_volume;
	
	@Transient
	private String given_potassium;
	
	@Column(columnDefinition = "Float4")
	private Float initial_energy;
	
	@Column(columnDefinition = "Float4")
	private Float final_energy;
	
	@Column(columnDefinition = "Float4")
	private Float given_energy;
	
	@Transient
	private String energy_volume_range;

	public Long getNutritionalcompianceid() {
		return nutritionalcompianceid;
	}

	public void setNutritionalcompianceid(Long nutritionalcompianceid) {
		this.nutritionalcompianceid = nutritionalcompianceid;
	}

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public Timestamp getModificationtime() {
		return modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public Integer getDol() {
		return dol;
	}

	public void setDol(Integer dol) {
		this.dol = dol;
	}

	public Float getGiven_feed() {
		return given_feed;
	}

	public void setGiven_feed(Float given_feed) {
		this.given_feed = given_feed;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getFeed_volume_range() {
		return feed_volume_range;
	}

	public void setFeed_volume_range(String feed_volume_range) {
		this.feed_volume_range = feed_volume_range;
	}

	public String getFeed_advancement_range() {
		return feed_advancement_range;
	}

	public void setFeed_advancement_range(String feed_advancement_range) {
		this.feed_advancement_range = feed_advancement_range;
	}

	public String getGiven_cho() {
		return given_cho;
	}

	public void setGiven_cho(String given_cho) {
		this.given_cho = given_cho;
	}

	public String getCho_volume_range() {
		return cho_volume_range;
	}

	public void setCho_volume_range(String cho_volume_range) {
		this.cho_volume_range = cho_volume_range;
	}

	public String getCho_advancement_range() {
		return cho_advancement_range;
	}

	public void setCho_advancement_range(String cho_advancement_range) {
		this.cho_advancement_range = cho_advancement_range;
	}

	public Float getCho_lower_pn_feed_volume() {
		return cho_lower_pn_feed_volume;
	}

	public void setCho_lower_pn_feed_volume(Float cho_lower_pn_feed_volume) {
		this.cho_lower_pn_feed_volume = cho_lower_pn_feed_volume;
	}

	public Float getCho_upper_pn_feed_volume() {
		return cho_upper_pn_feed_volume;
	}

	public void setCho_upper_pn_feed_volume(Float cho_upper_pn_feed_volume) {
		this.cho_upper_pn_feed_volume = cho_upper_pn_feed_volume;
	}

	public Float getCho_lower_pn_feed_advancement() {
		return cho_lower_pn_feed_advancement;
	}

	public void setCho_lower_pn_feed_advancement(Float cho_lower_pn_feed_advancement) {
		this.cho_lower_pn_feed_advancement = cho_lower_pn_feed_advancement;
	}

	public Float getCho_upper_pn_feed_advancement() {
		return cho_upper_pn_feed_advancement;
	}

	public void setCho_upper_pn_feed_advancement(Float cho_upper_pn_feed_advancement) {
		this.cho_upper_pn_feed_advancement = cho_upper_pn_feed_advancement;
	}

	public Float getProtein_lower_pn_feed_volume() {
		return protein_lower_pn_feed_volume;
	}

	public void setProtein_lower_pn_feed_volume(Float protein_lower_pn_feed_volume) {
		this.protein_lower_pn_feed_volume = protein_lower_pn_feed_volume;
	}

	public Float getProtein_upper_pn_feed_volume() {
		return protein_upper_pn_feed_volume;
	}

	public void setProtein_upper_pn_feed_volume(Float protein_upper_pn_feed_volume) {
		this.protein_upper_pn_feed_volume = protein_upper_pn_feed_volume;
	}

	public Float getProtein_lower_pn_feed_advancement() {
		return protein_lower_pn_feed_advancement;
	}

	public void setProtein_lower_pn_feed_advancement(Float protein_lower_pn_feed_advancement) {
		this.protein_lower_pn_feed_advancement = protein_lower_pn_feed_advancement;
	}

	public Float getProtein_upper_pn_feed_advancement() {
		return protein_upper_pn_feed_advancement;
	}

	public void setProtein_upper_pn_feed_advancement(Float protein_upper_pn_feed_advancement) {
		this.protein_upper_pn_feed_advancement = protein_upper_pn_feed_advancement;
	}

	public String getGiven_protein() {
		return given_protein;
	}

	public void setGiven_protein(String given_protein) {
		this.given_protein = given_protein;
	}

	public String getProtein_volume_range() {
		return protein_volume_range;
	}

	public void setProtein_volume_range(String protein_volume_range) {
		this.protein_volume_range = protein_volume_range;
	}

	public String getProtein_advancement_range() {
		return protein_advancement_range;
	}

	public void setProtein_advancement_range(String protein_advancement_range) {
		this.protein_advancement_range = protein_advancement_range;
	}

	public Float getFeed_volume() {
		return feed_volume;
	}

	public void setFeed_volume(Float feed_volume) {
		this.feed_volume = feed_volume;
	}

	public Float getFeed_advancement() {
		return feed_advancement;
	}

	public void setFeed_advancement(Float feed_advancement) {
		this.feed_advancement = feed_advancement;
	}

	public Float getUpper_feed_volume() {
		return upper_feed_volume;
	}

	public void setUpper_feed_volume(Float upper_feed_volume) {
		this.upper_feed_volume = upper_feed_volume;
	}

	public Float getUpper_feed_advancement() {
		return upper_feed_advancement;
	}

	public void setUpper_feed_advancement(Float upper_feed_advancement) {
		this.upper_feed_advancement = upper_feed_advancement;
	}

	public Float getSodium_lower_pn_feed_volume() {
		return sodium_lower_pn_feed_volume;
	}

	public void setSodium_lower_pn_feed_volume(Float sodium_lower_pn_feed_volume) {
		this.sodium_lower_pn_feed_volume = sodium_lower_pn_feed_volume;
	}

	public Float getSodium_upper_pn_feed_volume() {
		return sodium_upper_pn_feed_volume;
	}

	public void setSodium_upper_pn_feed_volume(Float sodium_upper_pn_feed_volume) {
		this.sodium_upper_pn_feed_volume = sodium_upper_pn_feed_volume;
	}

	public String getGiven_sodium() {
		return given_sodium;
	}

	public void setGiven_sodium(String given_sodium) {
		this.given_sodium = given_sodium;
	}

	public String getSodium_volume_range() {
		return sodium_volume_range;
	}

	public void setSodium_volume_range(String sodium_volume_range) {
		this.sodium_volume_range = sodium_volume_range;
	}

	public Float getCalcium_pn_feed_volume() {
		return calcium_pn_feed_volume;
	}

	public void setCalcium_pn_feed_volume(Float calcium_pn_feed_volume) {
		this.calcium_pn_feed_volume = calcium_pn_feed_volume;
	}

	public String getGiven_calcium() {
		return given_calcium;
	}

	public void setGiven_calcium(String given_calcium) {
		this.given_calcium = given_calcium;
	}

	public Float getPotassium_pn_feed_volume() {
		return potassium_pn_feed_volume;
	}

	public void setPotassium_pn_feed_volume(Float potassium_pn_feed_volume) {
		this.potassium_pn_feed_volume = potassium_pn_feed_volume;
	}

	public String getGiven_potassium() {
		return given_potassium;
	}

	public void setGiven_potassium(String given_potassium) {
		this.given_potassium = given_potassium;
	}

	public Float getInitial_energy() {
		return initial_energy;
	}

	public void setInitial_energy(Float initial_energy) {
		this.initial_energy = initial_energy;
	}

	public Float getFinal_energy() {
		return final_energy;
	}

	public void setFinal_energy(Float final_energy) {
		this.final_energy = final_energy;
	}

	public Float getGiven_energy() {
		return given_energy;
	}

	public void setGiven_energy(Float given_energy) {
		this.given_energy = given_energy;
	}

	public String getEnergy_volume_range() {
		return energy_volume_range;
	}

	public void setEnergy_volume_range(String energy_volume_range) {
		this.energy_volume_range = energy_volume_range;
	}

}
