package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the device_monitor_detail database table.
 *
 */
@Entity
@Table(name="device_monitor_detail_dump")
@NamedQuery(name="DeviceMonitorDetailDump.findAll", query="SELECT d FROM DeviceMonitorDetailDump d")
public class DeviceMonitorDetailDump implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long devicemoniterid;

    private String beddeviceid;

    @Column(name="co2_resprate")
    private String co2Resprate;

    private Timestamp creationtime;

    @Column(name="dia_bp")
    private String diaBp;

    @Column(name="ecg_resprate")
    private String ecgResprate;

    private String etco2;

    private String heartrate;

    @Column(name="mean_bp")
    private String meanBp;

    private Timestamp modificationtime;

    private String pulserate;

    private String spo2;

    @Column(name="sys_bp")
    private String sysBp;

    private String uhid;

    private String nbp_s;

    private String nbp_m;

    private String nbp_d;

    private String ibp_s;

    private String ibp_m;

    private String ibp_d;

    private String pi;

    private String o3rSO2_1;

    private String o3rSO2_2;

    private Timestamp starttime;

    public Timestamp getStarttime() {
        return starttime;
    }

    public void setStarttime(Timestamp starttime) {
        this.starttime = starttime;
    }

    public String getNbp_s() {
        return nbp_s;
    }

    public void setNbp_s(String nbp_s) {
        this.nbp_s = nbp_s;
    }

    public String getNbp_m() {
        return nbp_m;
    }

    public void setNbp_m(String nbp_m) {
        this.nbp_m = nbp_m;
    }

    public String getNbp_d() {
        return nbp_d;
    }

    public void setNbp_d(String nbp_d) {
        this.nbp_d = nbp_d;
    }

    public String getIbp_s() {
        return ibp_s;
    }

    public void setIbp_s(String ibp_s) {
        this.ibp_s = ibp_s;
    }

    public String getIbp_m() {
        return ibp_m;
    }

    public void setIbp_m(String ibp_m) {
        this.ibp_m = ibp_m;
    }

    public String getIbp_d() {
        return ibp_d;
    }

    public void setIbp_d(String ibp_d) {
        this.ibp_d = ibp_d;
    }

    public Long getDevicemoniterid() {
        return this.devicemoniterid;
    }

    public void setDevicemoniterid(Long devicemoniterid) {
        this.devicemoniterid = devicemoniterid;
    }

    public String getBeddeviceid() {
        return this.beddeviceid;
    }

    public void setBeddeviceid(String beddeviceid) {
        this.beddeviceid = beddeviceid;
    }

    public String getCo2Resprate() {
        return this.co2Resprate;
    }

    public void setCo2Resprate(String co2Resprate) {
        this.co2Resprate = co2Resprate;
    }

    public Timestamp getCreationtime() {
        return this.creationtime;
    }

    public void setCreationtime(Timestamp creationtime) {
        this.creationtime = creationtime;
    }

    public String getDiaBp() {
        return this.diaBp;
    }

    public void setDiaBp(String diaBp) {
        this.diaBp = diaBp;
    }

    public String getEcgResprate() {
        return this.ecgResprate;
    }

    public void setEcgResprate(String ecgResprate) {
        this.ecgResprate = ecgResprate;
    }

    public String getEtco2() {
        return this.etco2;
    }

    public void setEtco2(String etco2) {
        this.etco2 = etco2;
    }

    public String getHeartrate() {
        return this.heartrate;
    }

    public void setHeartrate(String heartrate) {
        this.heartrate = heartrate;
    }

    public String getMeanBp() {
        return this.meanBp;
    }

    public void setMeanBp(String meanBp) {
        this.meanBp = meanBp;
    }

    public Timestamp getModificationtime() {
        return this.modificationtime;
    }

    public void setModificationtime(Timestamp modificationtime) {
        this.modificationtime = modificationtime;
    }

    public String getPulserate() {
        return this.pulserate;
    }

    public void setPulserate(String pulserate) {
        this.pulserate = pulserate;
    }

    public String getSpo2() {
        return this.spo2;
    }

    public void setSpo2(String spo2) {
        this.spo2 = spo2;
    }

    public String getSysBp() {
        return this.sysBp;
    }

    public void setSysBp(String sysBp) {
        this.sysBp = sysBp;
    }

    public String getUhid() {
        return this.uhid;
    }

    public void setUhid(String uhid) {
        this.uhid = uhid;
    }

    public String getPi() {
        return pi;
    }

    public void setPi(String pi) {
        this.pi = pi;
    }

    public String getO3rSO2_1() {
        return o3rSO2_1;
    }

    public void setO3rSO2_1(String o3rSO2_1) {
        this.o3rSO2_1 = o3rSO2_1;
    }

    public String getO3rSO2_2() {
        return o3rSO2_2;
    }

    public void setO3rSO2_2(String o3rSO2_2) {
        this.o3rSO2_2 = o3rSO2_2;
    }

}
