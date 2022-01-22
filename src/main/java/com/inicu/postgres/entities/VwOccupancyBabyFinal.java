package com.inicu.postgres.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name="vw_occupancy_baby_final")
@NamedQuery(name="VwOccupancyBabyFinal.findAll", query="SELECT v FROM VwOccupancyBabyFinal v")
public class VwOccupancyBabyFinal {

    @Column(columnDefinition = "bool")
    private Boolean activestatus;

    @Column(columnDefinition = "bool")
    private Boolean admissionstatus;

    private String babyname;

    @Column(name = "inout_patient_status")
    private String inoutPatientStatus;

    @Column(columnDefinition = "float4")
    private Float birthweight;

    @Column(columnDefinition = "float4")
    private Float admissionWeight;

    private String timeofadmission;

    private String timeofbirth;

    private Timestamp dischargeddate;

    private String dischargestatus;

    private Integer gestationdaysbylmp;

    private Integer gestationweekbylmp;

    private String branchname;

    private Timestamp hisdischargedate;

    private String hisdischargestatus;

    private Integer actualgestationdays;

    private Integer actualgestationweek;

    @Temporal(TemporalType.DATE)
    private Date dateofadmission;

    @Temporal(TemporalType.DATE)
    private Date dateofbirth;

    private String uhid;

    @Column(columnDefinition = "bool")
    private Boolean isreadmitted;

    public String getUhid() {
        return uhid;
    }

    public void setUhid(String uhid) {
        this.uhid = uhid;
    }

    public Boolean getActivestatus() {
        return activestatus;
    }

    public void setActivestatus(Boolean activestatus) {
        this.activestatus = activestatus;
    }

    public Boolean getAdmissionstatus() {
        return admissionstatus;
    }

    public void setAdmissionstatus(Boolean admissionstatus) {
        this.admissionstatus = admissionstatus;
    }

    public String getBabyname() {
        return babyname;
    }

    public void setBabyname(String babyname) {
        this.babyname = babyname;
    }

    public String getInoutPatientStatus() {
        return inoutPatientStatus;
    }

    public void setInoutPatientStatus(String inoutPatientStatus) {
        this.inoutPatientStatus = inoutPatientStatus;
    }

    public Float getBirthweight() {
        return birthweight;
    }

    public void setBirthweight(Float birthweight) {
        this.birthweight = birthweight;
    }

    public Float getAdmissionWeight() {
        return admissionWeight;
    }

    public void setAdmissionWeight(Float admissionWeight) {
        this.admissionWeight = admissionWeight;
    }

    public String getTimeofadmission() {
        return timeofadmission;
    }

    public void setTimeofadmission(String timeofadmission) {
        this.timeofadmission = timeofadmission;
    }

    public String getTimeofbirth() {
        return timeofbirth;
    }

    public void setTimeofbirth(String timeofbirth) {
        this.timeofbirth = timeofbirth;
    }

    public Timestamp getDischargeddate() {
        return dischargeddate;
    }

    public void setDischargeddate(Timestamp dischargeddate) {
        this.dischargeddate = dischargeddate;
    }

    public String getDischargestatus() {
        return dischargestatus;
    }

    public void setDischargestatus(String dischargestatus) {
        this.dischargestatus = dischargestatus;
    }

    public Integer getGestationdaysbylmp() {
        return gestationdaysbylmp;
    }

    public void setGestationdaysbylmp(Integer gestationdaysbylmp) {
        this.gestationdaysbylmp = gestationdaysbylmp;
    }

    public Integer getGestationweekbylmp() {
        return gestationweekbylmp;
    }

    public void setGestationweekbylmp(Integer gestationweekbylmp) {
        this.gestationweekbylmp = gestationweekbylmp;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    public Timestamp getHisdischargedate() {
        return hisdischargedate;
    }

    public void setHisdischargedate(Timestamp hisdischargedate) {
        this.hisdischargedate = hisdischargedate;
    }

    public String getHisdischargestatus() {
        return hisdischargestatus;
    }

    public void setHisdischargestatus(String hisdischargestatus) {
        this.hisdischargestatus = hisdischargestatus;
    }

    public Integer getActualgestationdays() {
        return actualgestationdays;
    }

    public void setActualgestationdays(Integer actualgestationdays) {
        this.actualgestationdays = actualgestationdays;
    }

    public Integer getActualgestationweek() {
        return actualgestationweek;
    }

    public void setActualgestationweek(Integer actualgestationweek) {
        this.actualgestationweek = actualgestationweek;
    }

    public Date getDateofadmission() {
        return dateofadmission;
    }

    public void setDateofadmission(Date dateofadmission) {
        this.dateofadmission = dateofadmission;
    }

    public Date getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(Date dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    @Id
    private Long id;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public VwOccupancyBabyFinal() {
        super();
        this.isreadmitted = false;
    }
}
