package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the vw_sa_respsystem database table.
 * 
 */
@Entity
@Table(name="vw_sa_respsystem")
@NamedQuery(name="VwSaRespsystem.findAll", query="SELECT v FROM VwSaRespsystem v")
public class VwSaRespsystem implements Serializable {
	private static final long serialVersionUID = 1L;

	private String ageatsurfactant;

	private String ageatvent;

	private String airvo;

	@Column(columnDefinition="bool")
	private Boolean apnea;

	@Column(columnDefinition="bool")
	private Boolean cld;

	private String cldstageid;

	private String cpap;

	
	private Timestamp creationtime;

	private String daysofcpap;

	private String lowestspo2;

	private String maxbili;

	private String maxfio2;

	private String maxrr;

	@Column(columnDefinition="bool")
	private Boolean mechvent;

	private String mvreasonid;

	private String niv;

	private String noofdoses;

	@Column(columnDefinition="bool")
	private Boolean pphn;

	@Column(columnDefinition="bool")
	private Boolean pulmhaem;

	private String rdscauseid;

	@Id
	private Long respid;

	private String spo2;

	@Column(columnDefinition="bool")
	private Boolean surfactant;

	private String uhid;

	private String ventduration;

	private String ventmodeid;

	private String rdscause;
	
	private String reasonofmv;
	
	private String ventilationmode;
	
	//changes on 09 march 2017
	@Column(name="ventilation_type")
	private String ventilationType;
	
	@Column(name="caffeine_startage")
	private String caffeineStartage;

	@Column(name="caffeine_stopage")
	private String caffeineStopage;
	
	public String getRdscause() {
		return rdscause;
	}

	public void setRdscause(String rdscause) {
		this.rdscause = rdscause;
	}

	public String getReasonofmv() {
		return reasonofmv;
	}

	public void setReasonofmv(String reasonofmv) {
		this.reasonofmv = reasonofmv;
	}

	public String getVentilationmode() {
		return ventilationmode;
	}

	public void setVentilationmode(String ventilationmode) {
		this.ventilationmode = ventilationmode;
	}

	public VwSaRespsystem() {
	}

	public String getAgeatsurfactant() {
		return this.ageatsurfactant;
	}

	public void setAgeatsurfactant(String ageatsurfactant) {
		this.ageatsurfactant = ageatsurfactant;
	}

	public String getAgeatvent() {
		return this.ageatvent;
	}

	public void setAgeatvent(String ageatvent) {
		this.ageatvent = ageatvent;
	}

	public String getAirvo() {
		return this.airvo;
	}

	public void setAirvo(String airvo) {
		this.airvo = airvo;
	}

	public Boolean getApnea() {
		return this.apnea;
	}

	public void setApnea(Boolean apnea) {
		this.apnea = apnea;
	}

	public Boolean getCld() {
		return this.cld;
	}

	public void setCld(Boolean cld) {
		this.cld = cld;
	}

	public String getCldstageid() {
		return this.cldstageid;
	}

	public void setCldstageid(String cldstageid) {
		this.cldstageid = cldstageid;
	}

	public String getCpap() {
		return this.cpap;
	}

	public void setCpap(String cpap) {
		this.cpap = cpap;
	}

	

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getVentilationType() {
		return ventilationType;
	}

	public void setVentilationType(String ventilationType) {
		this.ventilationType = ventilationType;
	}

	public String getCaffeineStartage() {
		return caffeineStartage;
	}

	public void setCaffeineStartage(String caffeineStartage) {
		this.caffeineStartage = caffeineStartage;
	}

	public String getCaffeineStopage() {
		return caffeineStopage;
	}

	public void setCaffeineStopage(String caffeineStopage) {
		this.caffeineStopage = caffeineStopage;
	}

	public String getDaysofcpap() {
		return this.daysofcpap;
	}

	public void setDaysofcpap(String daysofcpap) {
		this.daysofcpap = daysofcpap;
	}

	public String getLowestspo2() {
		return this.lowestspo2;
	}

	public void setLowestspo2(String lowestspo2) {
		this.lowestspo2 = lowestspo2;
	}

	public String getMaxbili() {
		return this.maxbili;
	}

	public void setMaxbili(String maxbili) {
		this.maxbili = maxbili;
	}

	public String getMaxfio2() {
		return this.maxfio2;
	}

	public void setMaxfio2(String maxfio2) {
		this.maxfio2 = maxfio2;
	}

	public String getMaxrr() {
		return this.maxrr;
	}

	public void setMaxrr(String maxrr) {
		this.maxrr = maxrr;
	}

	public Boolean getMechvent() {
		return this.mechvent;
	}

	public void setMechvent(Boolean mechvent) {
		this.mechvent = mechvent;
	}

	public String getMvreasonid() {
		return this.mvreasonid;
	}

	public void setMvreasonid(String mvreasonid) {
		this.mvreasonid = mvreasonid;
	}

	public String getNiv() {
		return this.niv;
	}

	public void setNiv(String niv) {
		this.niv = niv;
	}

	public String getNoofdoses() {
		return this.noofdoses;
	}

	public void setNoofdoses(String noofdoses) {
		this.noofdoses = noofdoses;
	}

	public Boolean getPphn() {
		return this.pphn;
	}

	public void setPphn(Boolean pphn) {
		this.pphn = pphn;
	}

	public Boolean getPulmhaem() {
		return this.pulmhaem;
	}

	public void setPulmhaem(Boolean pulmhaem) {
		this.pulmhaem = pulmhaem;
	}

	public String getRdscauseid() {
		return this.rdscauseid;
	}

	public void setRdscauseid(String rdscauseid) {
		this.rdscauseid = rdscauseid;
	}

	public Long getRespid() {
		return this.respid;
	}

	public void setRespid(Long respid) {
		this.respid = respid;
	}

	public String getSpo2() {
		return this.spo2;
	}

	public void setSpo2(String spo2) {
		this.spo2 = spo2;
	}

	public Boolean getSurfactant() {
		return this.surfactant;
	}

	public void setSurfactant(Boolean surfactant) {
		this.surfactant = surfactant;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getVentduration() {
		return this.ventduration;
	}

	public void setVentduration(String ventduration) {
		this.ventduration = ventduration;
	}

	public String getVentmodeid() {
		return this.ventmodeid;
	}

	public void setVentmodeid(String ventmodeid) {
		this.ventmodeid = ventmodeid;
	}

	@Override
	public String toString() {
		String notes = "Resp System [";
//		
//		if(ageatsurfactant!=null){
//			notes = notes+", age at surfactant:"+ageatsurfactant;
//		}
//		
//		if(ageatvent!=null){
//			notes = notes+", Age at vent:"+ageatvent;
//		}
//		
//		if(airvo!=null){
//			notes = notes+", Airvo:"+airvo;
//		}
//		
//		if(apnea!=null){
//			notes = notes+", Apnea:"+apnea;
//		}
//		
//		if(cld!=null){
//			notes = notes+", Cld:"+cld;
//		}
//		
//		if(cpap!=null){
//			notes = notes+", cpap:"+cpap;
//		}
//		
//		
//		if(daysofcpap!=null){
//			notes = notes+", Days of cpap:"+daysofcpap;
//		}
		
		if(lowestspo2!=null){
			notes = notes+", Lowest Spo2:"+lowestspo2;
		}
//		
//		if(maxbili!=null){
//			notes = notes+", Max. Bili:"+maxbili;
//		}
		
		if(maxrr!=null){
			notes = notes+", Max RR:"+maxrr;
		}
		
//		if(mechvent!=null){
//			notes = notes+", Mechvent:"+mechvent;
//		}
//		
//		if(niv!=null){
//			notes = notes+", Niv:"+niv;
//		}
//		
//		if(noofdoses!=null){
//			notes = notes+", No of doses:"+noofdoses;
//		}
//		
//		if(pphn!=null){
//			notes = notes+", PPHN:"+pphn;
//		}
//		
//		if(pulmhaem!=null){
//			notes = notes+", Pulm Haem:"+pulmhaem;
//		}
//		
//		if(spo2!=null){
//			notes = notes+", Spo2:"+spo2;
//		}
//		
//		if(surfactant!=null){
//			notes = notes+", Surfactant:"+surfactant;
//		}
//		
//		if(ventduration!=null){
//			notes = notes+", Vent Duration:"+ventduration;
//		}
		
		if(rdscause!=null){
			notes = notes+", RDS Cause:"+rdscause;
		}
		
//		if(reasonofmv!=null){
//			notes = notes+", Reason of MV:"+reasonofmv;
//		}
//		
//		if(ventilationmode!=null){
//			notes = notes+", Ventilation Mode:"+ventilationmode;
//		}
//		
//		if(ventilationType!=null){
//			notes = notes+", Ventilation Type:"+ventilationType;
//		}
//		
//		if(caffeineStartage!=null){
//			notes = notes+", caffeine Start age:"+caffeineStartage;
//		}
//		
//		if(caffeineStopage!=null){
//			notes = notes+", caffeine Stop age:"+caffeineStopage;
//		}
		
		notes = notes+" ]";
//		return "VwSaRespsystem [ageatsurfactant=" + ageatsurfactant
//				+ ", ageatvent=" + ageatvent + ", airvo=" + airvo + ", apnea="
//				+ apnea + ", cld=" + cld + ", cpap=" + cpap + ", creationdate="
//				+ creationdate + ", daysofcpap=" + daysofcpap + ", lowestspo2="
//				+ lowestspo2 + ", maxbili=" + maxbili + ", maxfio2=" + maxfio2
//				+ ", maxrr=" + maxrr + ", mechvent=" + mechvent + ", niv="
//				+ niv + ", noofdoses=" + noofdoses + ", pphn=" + pphn
//				+ ", pulmhaem=" + pulmhaem + ", respid=" + respid + ", spo2="
//				+ spo2 + ", surfactant=" + surfactant + ", uhid=" + uhid
//				+ ", ventduration=" + ventduration + ", ventmodeid="
//				+ ventmodeid + ", rdscause=" + rdscause + ", reasonofmv="
//				+ reasonofmv + ", ventilationmode=" + ventilationmode + "]";
		
		return notes;
	}

	
}