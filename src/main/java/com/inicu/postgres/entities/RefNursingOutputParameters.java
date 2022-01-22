package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="ref_nursing_output_parameters")
@NamedQuery(name="RefNursingOutputParameters.findAll", query="SELECT r FROM RefNursingOutputParameters r")
public class RefNursingOutputParameters implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	String branchid;
	String branchname;

	public String getdashboard_configurations() {
		return dashboard_configurations;
	}

	public void setdashboard_configurations(String dashboard_configurations) {
		this.dashboard_configurations = dashboard_configurations;
	}

	@Column(columnDefinition = "text")
	String dashboard_configurations;

	@Column(columnDefinition = "bool")
	Boolean spo2;
	@Column(columnDefinition = "bool")
	Boolean hr;
	@Column(columnDefinition = "bool")
	Boolean pr;
	@Column(columnDefinition = "bool")
	Boolean baby_color;
	@Column(columnDefinition = "bool")
	Boolean central_temp;
	@Column(columnDefinition = "bool")
	Boolean peripheral_temp;
	@Column(columnDefinition = "bool")
	Boolean downes;
	@Column(columnDefinition = "bool")
	Boolean humidification;
	@Column(columnDefinition = "bool")
	Boolean rr;
	@Column(columnDefinition = "bool")
	Boolean rbs;

	public Boolean getRbsstatus() {
		return rbsstatus;
	}

	public void setRbsstatus(Boolean rbsstatus) {
		this.rbsstatus = rbsstatus;
	}

	public Boolean isRbsstatus() {
		return rbsstatus;
	}

	@Column(columnDefinition = "bool")
	Boolean rbsstatus;
	@Column(columnDefinition = "bool")
	Boolean tcb;
	@Column(columnDefinition = "bool")
	Boolean consciousness;
	@Column(columnDefinition = "bool")
	Boolean etco2;
	@Column(columnDefinition = "bool")
	Boolean cft;
	@Column(columnDefinition = "bool")
	Boolean cvp;
	@Column(columnDefinition = "bool")
	Boolean nibp_sys;
	@Column(columnDefinition = "bool")
	Boolean nibp_dia;
	@Column(columnDefinition = "bool")
	Boolean nibp_mean;
	@Column(columnDefinition = "bool")
	Boolean ibp_sys;
	@Column(columnDefinition = "bool")
	Boolean ibp_dia;
	@Column(columnDefinition = "bool")
	Boolean ibp_mean;
	@Column(columnDefinition = "bool")
	Boolean left_probe_site;
	@Column(columnDefinition = "bool")
	Boolean right_probe_site;
	@Column(columnDefinition = "bool")
	Boolean  diff_temp;
	@Column(columnDefinition = "bool")
	Boolean  left_pupil_size;
	@Column(columnDefinition = "bool")
	Boolean  left_pupil_reaction;
	@Column(columnDefinition = "bool")
	Boolean  right_pupil_size;
	@Column(columnDefinition = "bool")
	Boolean  right_pupil_reaction;
	@Column(columnDefinition = "bool")
	Boolean  pupil_equal;
	@Column(columnDefinition = "bool")
	Boolean  position;
	public String getBranchid() {
		return branchid;
	}
	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}
	public String getBranchname() {
		return branchname;
	}
	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}
	public Boolean isSpo2() {
		return spo2;
	}
	public void setSpo2(Boolean spo2) {
		this.spo2 = spo2;
	}
	public Boolean isHr() {
		return hr;
	}
	public void setHr(Boolean hr) {
		this.hr = hr;
	}
	public Boolean isPr() {
		return pr;
	}
	public void setPr(Boolean pr) {
		this.pr = pr;
	}
	public Boolean isBaby_color() {
		return baby_color;
	}
	public void setBaby_color(Boolean baby_color) {
		this.baby_color = baby_color;
	}
	public Boolean isCentral_temp() {
		return central_temp;
	}
	public void setCentral_temp(Boolean central_temp) {
		this.central_temp = central_temp;
	}
	public Boolean isPeripheral_temp() {
		return peripheral_temp;
	}
	public void setPeripheral_temp(Boolean peripheral_temp) {
		this.peripheral_temp = peripheral_temp;
	}
	public Boolean isDownes() {
		return downes;
	}
	public void setDownes(Boolean downes) {
		this.downes = downes;
	}
	public Boolean isHumidification() {
		return humidification;
	}
	public void setHumidification(Boolean humidification) {
		this.humidification = humidification;
	}
	public Boolean isRr() {
		return rr;
	}
	public void setRr(Boolean rr) {
		this.rr = rr;
	}
	public Boolean isRbs() {
		return rbs;
	}
	public void setRbs(Boolean rbs) {
		this.rbs = rbs;
	}
	public Boolean isTcb() {
		return tcb;
	}
	public void setTcb(Boolean tcb) {
		this.tcb = tcb;
	}
	public Boolean isConsciousness() {
		return consciousness;
	}
	public void setConsciousness(Boolean consciousness) {
		this.consciousness = consciousness;
	}
	public Boolean isEtco2() {
		return etco2;
	}
	public void setEtco2(Boolean etco2) {
		this.etco2 = etco2;
	}
	public Boolean isCft() {
		return cft;
	}
	public void setCft(Boolean cft) {
		this.cft = cft;
	}
	public Boolean isCvp() {
		return cvp;
	}
	public void setCvp(Boolean cvp) {
		this.cvp = cvp;
	}
	public Boolean isNibp_sys() {
		return nibp_sys;
	}
	public void setNibp_sys(Boolean nibp_sys) {
		this.nibp_sys = nibp_sys;
	}
	public Boolean isNibp_dia() {
		return nibp_dia;
	}
	public void setNibp_dia(Boolean nibp_dia) {
		this.nibp_dia = nibp_dia;
	}
	public Boolean isNibp_mean() {
		return nibp_mean;
	}
	public void setNibp_mean(Boolean nibp_mean) {
		this.nibp_mean = nibp_mean;
	}
	public Boolean isIbp_sys() {
		return ibp_sys;
	}
	public void setIbp_sys(Boolean ibp_sys) {
		this.ibp_sys = ibp_sys;
	}
	public Boolean isIbp_dia() {
		return ibp_dia;
	}
	public void setIbp_dia(Boolean ibp_dia) {
		this.ibp_dia = ibp_dia;
	}
	public Boolean isIbp_mean() {
		return ibp_mean;
	}
	public void setIbp_mean(Boolean ibp_mean) {
		this.ibp_mean = ibp_mean;
	}
	public Boolean isLeft_probe_site() {
		return left_probe_site;
	}
	public void setLeft_probe_site(Boolean left_probe_site) {
		this.left_probe_site = left_probe_site;
	}
	public Boolean isRight_probe_site() {
		return right_probe_site;
	}
	public void setRight_probe_site(Boolean right_probe_site) {
		this.right_probe_site = right_probe_site;
	}
	public Boolean isDiff_temp() {
		return diff_temp;
	}
	public void setDiff_temp(Boolean diff_temp) {
		this.diff_temp = diff_temp;
	}
	public Boolean isLeft_pupil_size() {
		return left_pupil_size;
	}
	public void setLeft_pupil_size(Boolean left_pupil_size) {
		this.left_pupil_size = left_pupil_size;
	}
	public Boolean isLeft_pupil_reaction() {
		return left_pupil_reaction;
	}
	public void setLeft_pupil_reaction(Boolean left_pupil_reaction) {
		this.left_pupil_reaction = left_pupil_reaction;
	}
	public Boolean isRight_pupil_size() {
		return right_pupil_size;
	}
	public void setRight_pupil_size(Boolean right_pupil_size) {
		this.right_pupil_size = right_pupil_size;
	}
	public Boolean isRight_pupil_reaction() {
		return right_pupil_reaction;
	}
	public void setRight_pupil_reaction(Boolean right_pupil_reaction) {
		this.right_pupil_reaction = right_pupil_reaction;
	}
	public Boolean isPupil_equal() {
		return pupil_equal;
	}
	public void setPupil_equal(Boolean pupil_equal) {
		this.pupil_equal = pupil_equal;
	}
	public Boolean getPosition() {
		return position;
	}
	public void setPosition(Boolean position) {
		this.position = position;
	}
	public Boolean getSpo2() {
		return spo2;
	}
	public Boolean getHr() {
		return hr;
	}
	public Boolean getPr() {
		return pr;
	}
	public Boolean getBaby_color() {
		return baby_color;
	}
	public Boolean getCentral_temp() {
		return central_temp;
	}
	public Boolean getPeripheral_temp() {
		return peripheral_temp;
	}
	public Boolean getDownes() {
		return downes;
	}
	public Boolean getHumidification() {
		return humidification;
	}
	public Boolean getRr() {
		return rr;
	}
	public Boolean getRbs() {
		return rbs;
	}
	public Boolean getTcb() {
		return tcb;
	}
	public Boolean getConsciousness() {
		return consciousness;
	}
	public Boolean getEtco2() {
		return etco2;
	}
	public Boolean getCft() {
		return cft;
	}
	public Boolean getCvp() {
		return cvp;
	}
	public Boolean getNibp_sys() {
		return nibp_sys;
	}
	public Boolean getNibp_dia() {
		return nibp_dia;
	}
	public Boolean getNibp_mean() {
		return nibp_mean;
	}
	public Boolean getIbp_sys() {
		return ibp_sys;
	}
	public Boolean getIbp_dia() {
		return ibp_dia;
	}
	public Boolean getIbp_mean() {
		return ibp_mean;
	}
	public Boolean getLeft_probe_site() {
		return left_probe_site;
	}
	public Boolean getRight_probe_site() {
		return right_probe_site;
	}
	public Boolean getDiff_temp() {
		return diff_temp;
	}
	public Boolean getLeft_pupil_size() {
		return left_pupil_size;
	}
	public Boolean getLeft_pupil_reaction() {
		return left_pupil_reaction;
	}
	public Boolean getRight_pupil_size() {
		return right_pupil_size;
	}
	public Boolean getRight_pupil_reaction() {
		return right_pupil_reaction;
	}
	public Boolean getPupil_equal() {
		return pupil_equal;
	}
	
	
}
