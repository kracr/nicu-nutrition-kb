package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;


/**
 * The persistent class for the ref_medicine database table.
 * 
 */
@Entity
@Table(name="ref_med_brand")
@NamedQuery(name="RefMedBrand.findAll", query="SELECT r FROM RefMedBrand r")
public class RefMedBrand implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ref_med_brand_id")
	private Long refmedbrandid;
	
	private Timestamp creationtime;

	private Timestamp modificationtime;
	
	private String medname;
	
	private String medid;

	@Column(name = "brand_name")
	private String brandName;
	
	@Column(name = "brand_type")
	private String brandType;

	@Column(name = "brand_form")
	private String brandForm;
	
	@Column(columnDefinition = "Float4", name = "brand_strength")
	private Float brandStrength;
	
	@Column(name = "brand_strength_unit")
	private String brandStrengthUnit;

	@Column(columnDefinition="bool")
	private Boolean isactive;
	
	@Column(columnDefinition = "Float4", name = "brand_content")
	private Float brandContent;
	
	@Column(columnDefinition = "Float4", name = "brand_reconstituent")
	private Float brandReconstituent;
	
	@Column(name = "brand_fluid")
	private String brandFluid;

	public RefMedBrand() {
	}

	public Long getRefmedbrandid() {
		return refmedbrandid;
	}

	public void setRefmedbrandid(Long refmedbrandid) {
		this.refmedbrandid = refmedbrandid;
	}

	public String getMedname() {
		return medname;
	}

	public void setMedname(String medname) {
		this.medname = medname;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getBrandType() {
		return brandType;
	}

	public void setBrandType(String brandType) {
		this.brandType = brandType;
	}

	public String getBrandForm() {
		return brandForm;
	}

	public void setBrandForm(String brandForm) {
		this.brandForm = brandForm;
	}

	public Float getBrandStrength() {
		return brandStrength;
	}

	public void setBrandStrength(Float brandStrength) {
		this.brandStrength = brandStrength;
	}

	public String getBrandStrengthUnit() {
		return brandStrengthUnit;
	}

	public void setBrandStrengthUnit(String brandStrengthUnit) {
		this.brandStrengthUnit = brandStrengthUnit;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
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

	public String getMedid() {
		return medid;
	}

	public void setMedid(String medid) {
		this.medid = medid;
	}

	public Float getBrandContent() {
		return brandContent;
	}

	public void setBrandContent(Float brandContent) {
		this.brandContent = brandContent;
	}

	public Float getBrandReconstituent() {
		return brandReconstituent;
	}

	public void setBrandReconstituent(Float brandReconstituent) {
		this.brandReconstituent = brandReconstituent;
	}

	public String getBrandFluid() {
		return brandFluid;
	}

	public void setBrandFluid(String brandFluid) {
		this.brandFluid = brandFluid;
	}
	
}