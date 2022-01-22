package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


/**
 * The persistent class for the discharge_ventilation database table.
 * 
 */
@Entity
@Table(name="discharge_ventilation")
@NamedQuery(name="DischargeVentilation.findAll", query="SELECT d FROM DischargeVentilation d")
public class DischargeVentilation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private Long nicuventillationid;

	private String ageatvent;

	@Column(name="complication_extubated")
	private String complicationExtubated;

	private String complications;

	@Column(columnDefinition="bool")
	private Boolean cpap;

	@Temporal(TemporalType.DATE)
	@Column(name="cpap_enddate")
	private Date cpapEnddate;

	@Column(name="cpap_fio2")
	private String cpapFio2;

	@Column(name="cpap_litero2")
	private String cpapLitero2;

	@Temporal(TemporalType.DATE)
	@Column(name="cpap_startdate")
	private Date cpapStartdate;

	

	@Column(columnDefinition="bool")
	private Boolean hoodoxygen;
	
	@Temporal(TemporalType.DATE)
	@Column(name="hoodoxygen_enddate")
	private Date hoodoxygenEnddate;

	@Column(name="hoodoxygen_fio2")
	private String hoodoxygenFio2;

	@Column(name="hoodoxygen_litero2")
	private String hoodoxygenLitero2;

	@Temporal(TemporalType.DATE)
	@Column(name="hoodoxygen_startdate")
	private Date hoodoxygenStartdate;

	private String indication;

	private String inotropes;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	@Temporal(TemporalType.DATE)
	@Column(name="nasal_enddate")
	private Date nasalEnddate;

	@Column(name="nasal_fio2")
	private String nasalFio2;

	@Column(name="nasal_litero2")
	private String nasalLitero2;

	@Column(name="nasal_prongs_oxygen",columnDefinition="bool")
	private Boolean nasalProngsOxygen;

	@Temporal(TemporalType.DATE)
	@Column(name="nasal_startdate")
	private Date nasalStartdate;

	@Column(columnDefinition="bool")
	private Boolean reventilation;

	@Column(name="reventilation_detail")
	private String reventilationDetail;
	
	private String sedation;

	private String setting;

	@Column(name="surfactant_doses")
	private String surfactantDoses;

	private String surfactantname;

	@Column(name="surfacttant_age")
	private String surfacttantAge;

	@Column(name="total_vent_duration")
	private String totalVentDuration;

	private String uhid;

	private String ventcourseid;
	
@Transient
	private Map<String, Boolean> inotropesObj = new HashMap<String, Boolean>(){
		{
			put("Dopamine",false);
			put("Dobutamine",false);
			put("Adrenaline",false);
			put("Noradrenaline",false);
			put("Milrinone",false);
		}
	};

	@Transient
	private HashMap<String, Boolean> anagesiaSdationObj = new HashMap<String, Boolean>(){
		{
			put("Midozolam",false);
			put("Morphine",false);
			put("Fentanyl",false);
			put("Other",false);
		}
	};
	
	public DischargeVentilation() {
	}

	public Long getNicuventillationid() {
		return nicuventillationid;
	}

	public void setNicuventillationid(Long nicuventillationid) {
		this.nicuventillationid = nicuventillationid;
	}

	public String getAgeatvent() {
		return ageatvent;
	}

	public void setAgeatvent(String ageatvent) {
		this.ageatvent = ageatvent;
	}

	public String getComplicationExtubated() {
		return complicationExtubated;
	}

	public void setComplicationExtubated(String complicationExtubated) {
		this.complicationExtubated = complicationExtubated;
	}

	public String getComplications() {
		return complications;
	}

	public void setComplications(String complications) {
		this.complications = complications;
	}

	public Boolean getCpap() {
		return cpap;
	}

	public void setCpap(Boolean cpap) {
		this.cpap = cpap;
	}

	public Date getCpapEnddate() {
		return cpapEnddate;
	}

	public void setCpapEnddate(Date cpapEnddate) {
		this.cpapEnddate = cpapEnddate;
	}

	public String getCpapFio2() {
		return cpapFio2;
	}

	public void setCpapFio2(String cpapFio2) {
		this.cpapFio2 = cpapFio2;
	}

	public String getCpapLitero2() {
		return cpapLitero2;
	}

	public void setCpapLitero2(String cpapLitero2) {
		this.cpapLitero2 = cpapLitero2;
	}

	public Date getCpapStartdate() {
		return cpapStartdate;
	}

	public void setCpapStartdate(Date cpapStartdate) {
		this.cpapStartdate = cpapStartdate;
	}

	public Boolean getHoodoxygen() {
		return hoodoxygen;
	}

	public void setHoodoxygen(Boolean hoodoxygen) {
		this.hoodoxygen = hoodoxygen;
	}

	public Date getHoodoxygenEnddate() {
		return hoodoxygenEnddate;
	}

	public void setHoodoxygenEnddate(Date hoodoxygenEnddate) {
		this.hoodoxygenEnddate = hoodoxygenEnddate;
	}

	public String getHoodoxygenFio2() {
		return hoodoxygenFio2;
	}

	public void setHoodoxygenFio2(String hoodoxygenFio2) {
		this.hoodoxygenFio2 = hoodoxygenFio2;
	}

	public String getHoodoxygenLitero2() {
		return hoodoxygenLitero2;
	}

	public void setHoodoxygenLitero2(String hoodoxygenLitero2) {
		this.hoodoxygenLitero2 = hoodoxygenLitero2;
	}

	public Date getHoodoxygenStartdate() {
		return hoodoxygenStartdate;
	}

	public void setHoodoxygenStartdate(Date hoodoxygenStartdate) {
		this.hoodoxygenStartdate = hoodoxygenStartdate;
	}

	public String getIndication() {
		return indication;
	}

	public void setIndication(String indication) {
		this.indication = indication;
	}

	public String getInotropes() {
		return inotropes;
	}

	public void setInotropes(String inotropes) {
		this.inotropes = inotropes;
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

	public Date getNasalEnddate() {
		return nasalEnddate;
	}

	public void setNasalEnddate(Date nasalEnddate) {
		this.nasalEnddate = nasalEnddate;
	}

	public String getNasalFio2() {
		return nasalFio2;
	}

	public void setNasalFio2(String nasalFio2) {
		this.nasalFio2 = nasalFio2;
	}

	public String getNasalLitero2() {
		return nasalLitero2;
	}

	public void setNasalLitero2(String nasalLitero2) {
		this.nasalLitero2 = nasalLitero2;
	}

	public Boolean getNasalProngsOxygen() {
		return nasalProngsOxygen;
	}

	public void setNasalProngsOxygen(Boolean nasalProngsOxygen) {
		this.nasalProngsOxygen = nasalProngsOxygen;
	}

	public Date getNasalStartdate() {
		return nasalStartdate;
	}

	public void setNasalStartdate(Date nasalStartdate) {
		this.nasalStartdate = nasalStartdate;
	}

	

	public Boolean getReventilation() {
		return reventilation;
	}

	public void setReventilation(Boolean reventilation) {
		this.reventilation = reventilation;
	}

	public String getSedation() {
		return sedation;
	}

	public void setSedation(String sedation) {
		this.sedation = sedation;
	}

	public String getSetting() {
		return setting;
	}

	public void setSetting(String setting) {
		this.setting = setting;
	}

	public String getSurfactantDoses() {
		return surfactantDoses;
	}

	public void setSurfactantDoses(String surfactantDoses) {
		this.surfactantDoses = surfactantDoses;
	}

	public String getSurfactantname() {
		return surfactantname;
	}

	public void setSurfactantname(String surfactantname) {
		this.surfactantname = surfactantname;
	}

	public String getSurfacttantAge() {
		return surfacttantAge;
	}

	public void setSurfacttantAge(String surfacttantAge) {
		this.surfacttantAge = surfacttantAge;
	}

	public String getTotalVentDuration() {
		return totalVentDuration;
	}

	public void setTotalVentDuration(String totalVentDuration) {
		this.totalVentDuration = totalVentDuration;
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getVentcourseid() {
		return ventcourseid;
	}

	public void setVentcourseid(String ventcourseid) {
		this.ventcourseid = ventcourseid;
	}

	public String getReventilationDetail() {
		return reventilationDetail;
	}

	public void setReventilationDetail(String reventilationDetail) {
		this.reventilationDetail = reventilationDetail;
	}

	

	
	public Map<String, Boolean> getInotropesObj() {
		return inotropesObj;
	}

	public void setInotropesObj(Map<String, Boolean> inotropesObj) {
		this.inotropesObj = inotropesObj;
	}

	public HashMap<String, Boolean> getAnagesiaSdationObj() {
		return anagesiaSdationObj;
	}

	public void setAnagesiaSdationObj(HashMap<String, Boolean> anagesiaSdationObj) {
		this.anagesiaSdationObj = anagesiaSdationObj;
	}

	
	
}