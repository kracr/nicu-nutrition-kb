package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the device_monitor_detail database table.
 *
 */
@Entity
@Table(name="device_ventilator_detail_dump")
@NamedQuery(name="DeviceVentilatorDataDump.findAll", query="SELECT d FROM DeviceVentilatorDataDump d")
public class DeviceVentilatorDataDump implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long deviceventilatorid;

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
    private String amplitude;
    private String frequency;

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


    private Timestamp start_time;

    public Timestamp getStart_time() {
        return start_time;
    }
    public void setStart_time(Timestamp start_time) {
        this.start_time = start_time;
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
    public Long getDeviceventilatorid() {
        return deviceventilatorid;
    }
    public void setDeviceventilatorid(Long deviceventilatorid) {
        this.deviceventilatorid = deviceventilatorid;
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
    public String getAmplitude() {
        return amplitude;
    }
    public void setAmplitude(String amplitude) {
        this.amplitude = amplitude;
    }
    public String getFrequency() {
        return frequency;
    }
    public void setFrequency(String frequency) {
        this.frequency = frequency;
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
}
