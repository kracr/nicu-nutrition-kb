package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the dashboard_device_ventilator_detail database
 * table.
 * 
 */
@Entity
@Table(name = "dashboard_device_ventilator_detail")
@NamedQuery(name = "DashboardDeviceVentilatorDetail.findAll", query = "SELECT d FROM DashboardDeviceVentilatorDetail d")
public class DashboardDeviceVentilatorDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String uhid;
	private String beddeviceid;
	private String freqrate;
	private String tidalvol;
	private String minvol;
	private String flowpermin;
	private String noppm;
	private String meanbp;
	private String occpressure;
	private String peakbp;
	private String platpressure;
	private Timestamp start_time;
	private Timestamp creationtime;
	private Timestamp modificationtime;
	
	//All the Parameter Coming From Draeger
	private String c;
	private String r;
	private String tc;
	private String c20;
	private String trigger;
	private String rvr;
	private String ti;
	private String te;
	private String map;
	private String peep;
	private String pip;
	private String dco2;
	private String vtim;
	private String vthf;
	private String vt;
	private String leak;
	private String spont;
	private String mv;
	private String mvim;
	private String rate;
	private String spo2;
	private String fio2;
	
	// sle5000 parameters
	
	private String set_cpap;
	private String set_insp_time;
	private String set_hfo_delta;
	private String set_hfo_mean;
	private String set_hfo_rate;
	private String ventilation_mode;
	private String set_termination_sensitivity;
	private String set_breath_trig_threshold;
	private String set_waveshape;
	private String set_patient_leak_alarm;
	private String set_apnoea_alarm;
	private String set_low_pressure_alarm;
	private String set_cycle_fail_alarm;
	private String set_high_pressure_alarm;
	private String set_low_tidal_vol_alarm;
	private String set_high_tidal_vol_alarm;
	private String set_low_minute_vol_alarm;
	private String set_high_minute_vol_alarm;
	private String measured_total_bpm;
	private String measured_cpap;
	private String measured_insp_volume;
	private String measured_exp_volume;
	private String measured_pip;
	private String measured_fio2;
	private String measured_hfo_delta_p;
	private String measured_hfo_mean;
	private String trigger_count;
	private String measure_leak;
	private String measured_resistance;
	private String measured_compliance;
	private String measured_c20 ;
	private String current_alarm;

	public DashboardDeviceVentilatorDetail() {
		super();
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getBeddeviceid() {
		return beddeviceid;
	}

	public void setBeddeviceid(String beddeviceid) {
		this.beddeviceid = beddeviceid;
	}

	public String getFreqrate() {
		return freqrate;
	}

	public void setFreqrate(String freqrate) {
		this.freqrate = freqrate;
	}

	public String getTidalvol() {
		return tidalvol;
	}

	public void setTidalvol(String tidalvol) {
		this.tidalvol = tidalvol;
	}

	public String getMinvol() {
		return minvol;
	}

	public void setMinvol(String minvol) {
		this.minvol = minvol;
	}

	public String getFlowpermin() {
		return flowpermin;
	}

	public void setFlowpermin(String flowpermin) {
		this.flowpermin = flowpermin;
	}

	public String getNoppm() {
		return noppm;
	}

	public void setNoppm(String noppm) {
		this.noppm = noppm;
	}

	public String getMeanbp() {
		return meanbp;
	}

	public void setMeanbp(String meanbp) {
		this.meanbp = meanbp;
	}

	public String getOccpressure() {
		return occpressure;
	}

	public void setOccpressure(String occpressure) {
		this.occpressure = occpressure;
	}

	public String getPeakbp() {
		return peakbp;
	}

	public void setPeakbp(String peakbp) {
		this.peakbp = peakbp;
	}

	public String getPlatpressure() {
		return platpressure;
	}

	public void setPlatpressure(String platpressure) {
		this.platpressure = platpressure;
	}

	public Timestamp getStart_time() {
		return start_time;
	}

	public void setStart_time(Timestamp start_time) {
		this.start_time = start_time;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public String getR() {
		return r;
	}

	public void setR(String r) {
		this.r = r;
	}

	public String getTc() {
		return tc;
	}

	public void setTc(String tc) {
		this.tc = tc;
	}

	public String getC20() {
		return c20;
	}

	public void setC20(String c20) {
		this.c20 = c20;
	}

	public String getTrigger() {
		return trigger;
	}

	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}

	public String getRvr() {
		return rvr;
	}

	public void setRvr(String rvr) {
		this.rvr = rvr;
	}

	public String getTi() {
		return ti;
	}

	public void setTi(String ti) {
		this.ti = ti;
	}

	public String getTe() {
		return te;
	}

	public void setTe(String te) {
		this.te = te;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public String getPeep() {
		return peep;
	}

	public void setPeep(String peep) {
		this.peep = peep;
	}

	public String getPip() {
		return pip;
	}

	public void setPip(String pip) {
		this.pip = pip;
	}

	public String getDco2() {
		return dco2;
	}

	public void setDco2(String dco2) {
		this.dco2 = dco2;
	}

	public String getVtim() {
		return vtim;
	}

	public void setVtim(String vtim) {
		this.vtim = vtim;
	}

	public String getVthf() {
		return vthf;
	}

	public void setVthf(String vthf) {
		this.vthf = vthf;
	}

	public String getVt() {
		return vt;
	}

	public void setVt(String vt) {
		this.vt = vt;
	}

	public String getLeak() {
		return leak;
	}

	public void setLeak(String leak) {
		this.leak = leak;
	}

	public String getSpont() {
		return spont;
	}

	public void setSpont(String spont) {
		this.spont = spont;
	}

	public String getMv() {
		return mv;
	}

	public void setMv(String mv) {
		this.mv = mv;
	}

	public String getMvim() {
		return mvim;
	}

	public void setMvim(String mvim) {
		this.mvim = mvim;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getSpo2() {
		return spo2;
	}

	public void setSpo2(String spo2) {
		this.spo2 = spo2;
	}

	public String getFio2() {
		return fio2;
	}

	public void setFio2(String fio2) {
		this.fio2 = fio2;
	}

	public String getSet_cpap() {
		return set_cpap;
	}

	public void setSet_cpap(String set_cpap) {
		this.set_cpap = set_cpap;
	}

	public String getSet_insp_time() {
		return set_insp_time;
	}

	public void setSet_insp_time(String set_insp_time) {
		this.set_insp_time = set_insp_time;
	}

	public String getSet_hfo_delta() {
		return set_hfo_delta;
	}

	public void setSet_hfo_delta(String set_hfo_delta) {
		this.set_hfo_delta = set_hfo_delta;
	}

	public String getSet_hfo_mean() {
		return set_hfo_mean;
	}

	public void setSet_hfo_mean(String set_hfo_mean) {
		this.set_hfo_mean = set_hfo_mean;
	}

	public String getSet_hfo_rate() {
		return set_hfo_rate;
	}

	public void setSet_hfo_rate(String set_hfo_rate) {
		this.set_hfo_rate = set_hfo_rate;
	}

	public String getVentilation_mode() {
		return ventilation_mode;
	}

	public void setVentilation_mode(String ventilation_mode) {
		this.ventilation_mode = ventilation_mode;
	}

	public String getSet_termination_sensitivity() {
		return set_termination_sensitivity;
	}

	public void setSet_termination_sensitivity(String set_termination_sensitivity) {
		this.set_termination_sensitivity = set_termination_sensitivity;
	}

	public String getSet_breath_trig_threshold() {
		return set_breath_trig_threshold;
	}

	public void setSet_breath_trig_threshold(String set_breath_trig_threshold) {
		this.set_breath_trig_threshold = set_breath_trig_threshold;
	}

	public String getSet_waveshape() {
		return set_waveshape;
	}

	public void setSet_waveshape(String set_waveshape) {
		this.set_waveshape = set_waveshape;
	}

	public String getSet_patient_leak_alarm() {
		return set_patient_leak_alarm;
	}

	public void setSet_patient_leak_alarm(String set_patient_leak_alarm) {
		this.set_patient_leak_alarm = set_patient_leak_alarm;
	}

	public String getSet_apnoea_alarm() {
		return set_apnoea_alarm;
	}

	public void setSet_apnoea_alarm(String set_apnoea_alarm) {
		this.set_apnoea_alarm = set_apnoea_alarm;
	}

	public String getSet_low_pressure_alarm() {
		return set_low_pressure_alarm;
	}

	public void setSet_low_pressure_alarm(String set_low_pressure_alarm) {
		this.set_low_pressure_alarm = set_low_pressure_alarm;
	}

	public String getSet_cycle_fail_alarm() {
		return set_cycle_fail_alarm;
	}

	public void setSet_cycle_fail_alarm(String set_cycle_fail_alarm) {
		this.set_cycle_fail_alarm = set_cycle_fail_alarm;
	}

	public String getSet_high_pressure_alarm() {
		return set_high_pressure_alarm;
	}

	public void setSet_high_pressure_alarm(String set_high_pressure_alarm) {
		this.set_high_pressure_alarm = set_high_pressure_alarm;
	}

	public String getSet_low_tidal_vol_alarm() {
		return set_low_tidal_vol_alarm;
	}

	public void setSet_low_tidal_vol_alarm(String set_low_tidal_vol_alarm) {
		this.set_low_tidal_vol_alarm = set_low_tidal_vol_alarm;
	}

	public String getSet_high_tidal_vol_alarm() {
		return set_high_tidal_vol_alarm;
	}

	public void setSet_high_tidal_vol_alarm(String set_high_tidal_vol_alarm) {
		this.set_high_tidal_vol_alarm = set_high_tidal_vol_alarm;
	}

	public String getSet_low_minute_vol_alarm() {
		return set_low_minute_vol_alarm;
	}

	public void setSet_low_minute_vol_alarm(String set_low_minute_vol_alarm) {
		this.set_low_minute_vol_alarm = set_low_minute_vol_alarm;
	}

	public String getSet_high_minute_vol_alarm() {
		return set_high_minute_vol_alarm;
	}

	public void setSet_high_minute_vol_alarm(String set_high_minute_vol_alarm) {
		this.set_high_minute_vol_alarm = set_high_minute_vol_alarm;
	}

	public String getMeasured_total_bpm() {
		return measured_total_bpm;
	}

	public void setMeasured_total_bpm(String measured_total_bpm) {
		this.measured_total_bpm = measured_total_bpm;
	}

	public String getMeasured_cpap() {
		return measured_cpap;
	}

	public void setMeasured_cpap(String measured_cpap) {
		this.measured_cpap = measured_cpap;
	}

	public String getMeasured_insp_volume() {
		return measured_insp_volume;
	}

	public void setMeasured_insp_volume(String measured_insp_volume) {
		this.measured_insp_volume = measured_insp_volume;
	}

	public String getMeasured_exp_volume() {
		return measured_exp_volume;
	}

	public void setMeasured_exp_volume(String measured_exp_volume) {
		this.measured_exp_volume = measured_exp_volume;
	}

	public String getMeasured_pip() {
		return measured_pip;
	}

	public void setMeasured_pip(String measured_pip) {
		this.measured_pip = measured_pip;
	}

	public String getMeasured_fio2() {
		return measured_fio2;
	}

	public void setMeasured_fio2(String measured_fio2) {
		this.measured_fio2 = measured_fio2;
	}

	public String getMeasured_hfo_delta_p() {
		return measured_hfo_delta_p;
	}

	public void setMeasured_hfo_delta_p(String measured_hfo_delta_p) {
		this.measured_hfo_delta_p = measured_hfo_delta_p;
	}

	public String getMeasured_hfo_mean() {
		return measured_hfo_mean;
	}

	public void setMeasured_hfo_mean(String measured_hfo_mean) {
		this.measured_hfo_mean = measured_hfo_mean;
	}

	public String getTrigger_count() {
		return trigger_count;
	}

	public void setTrigger_count(String trigger_count) {
		this.trigger_count = trigger_count;
	}

	public String getMeasure_leak() {
		return measure_leak;
	}

	public void setMeasure_leak(String measure_leak) {
		this.measure_leak = measure_leak;
	}

	public String getMeasured_resistance() {
		return measured_resistance;
	}

	public void setMeasured_resistance(String measured_resistance) {
		this.measured_resistance = measured_resistance;
	}

	public String getMeasured_compliance() {
		return measured_compliance;
	}

	public void setMeasured_compliance(String measured_compliance) {
		this.measured_compliance = measured_compliance;
	}

	public String getMeasured_c20() {
		return measured_c20;
	}

	public void setMeasured_c20(String measured_c20) {
		this.measured_c20 = measured_c20;
	}

	public String getCurrent_alarm() {
		return current_alarm;
	}

	public void setCurrent_alarm(String current_alarm) {
		this.current_alarm = current_alarm;
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

}
