package com.inicu.postgres.entities;

import kafka.utils.Time;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "baby_view_timings")
@NamedQuery(name = "BabyViewTimings.findAll", query = "SELECT b FROM BabyViewTimings b")
public class BabyViewTimings {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long baby_view_timings_id;

    private String uhid;

    @Transient
    private String babyName;

    @Column(name ="morning_from_time")
    private String morningFromTime;

    @Column(name ="morning_to_time")
    private String morningToTime;

    @Column(name ="evening_from_time")
    private String eveningFromTime;

    @Column(name ="evening_to_time")
    private String eveningToTime;

    private Timestamp creationtime;

    private Timestamp modificationtime;

    @Transient
    private Timestamp displayMorningFromTime;

    @Transient
    private Timestamp displayMorningToTime;

    @Transient
    private Timestamp displayEveningFromTime;

    @Transient
    private Timestamp displayEveningToTime;

    @Transient
    private Boolean valueEdit;

    @Column(name ="morning_enabled",columnDefinition = "bool")
    private Boolean morningTimeEnabled;

    @Column(name ="evening_enabled",columnDefinition = "bool")
    private Boolean eveningTimeEnabled;

    @Column(name ="nurse_confirmation")
    private String nurseConfirmation;

    @Column(name ="delay_time")
    private String delayTime;

    public BabyViewTimings(){
        this.valueEdit = false;
        this.morningTimeEnabled = true;
        this.eveningTimeEnabled = true;
    }

    public String getBabyName() {
        return babyName;
    }

    public void setBabyName(String babyName) {
        this.babyName = babyName;
    }

    public Boolean getValueEdit() {
        return valueEdit;
    }

    public void setValueEdit(Boolean valueEdit) {
        this.valueEdit = valueEdit;
    }

    public String getUhid() {
        return uhid;
    }

    public void setUhid(String uhid) {
        this.uhid = uhid;
    }

    public void setMorningFromTime(String morningFromTime) {
        this.morningFromTime = morningFromTime;
    }

    public void setMorningToTime(String morningToTime) {
        this.morningToTime = morningToTime;
    }

    public String getMorningFromTime() {
        return morningFromTime;
    }

    public String getMorningToTime() {
        return morningToTime;
    }


    public String getEveningFromTime() {
        return eveningFromTime;
    }

    public void setEveningFromTime(String eveningFromTime) {
        this.eveningFromTime = eveningFromTime;
    }

    public String getEveningToTime() {
        return eveningToTime;
    }

    public void setEveningToTime(String eveningToTime) {
        this.eveningToTime = eveningToTime;
    }

    public Timestamp getDisplayMorningFromTime() {
        return displayMorningFromTime;
    }

    public void setDisplayMorningFromTime(Timestamp displayMorningFromTime) {
        this.displayMorningFromTime = displayMorningFromTime;
    }

    public Timestamp getDisplayMorningToTime() {
        return displayMorningToTime;
    }

    public void setDisplayMorningToTime(Timestamp displayMorningToTime) {
        this.displayMorningToTime = displayMorningToTime;
    }

    public Timestamp getDisplayEveningFromTime() {
        return displayEveningFromTime;
    }

    public void setDisplayEveningFromTime(Timestamp displayEveningFromTime) {
        this.displayEveningFromTime = displayEveningFromTime;
    }

    public Timestamp getDisplayEveningToTime() {
        return displayEveningToTime;
    }

    public void setDisplayEveningToTime(Timestamp displayEveningToTime) {
        this.displayEveningToTime = displayEveningToTime;
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

    public Long getBaby_view_timings_id() {
        return baby_view_timings_id;
    }

    public void setBaby_view_timings_id(Long baby_view_timings_id) {
        this.baby_view_timings_id = baby_view_timings_id;
    }

    public Boolean getMorningTimeEnabled() {
        return morningTimeEnabled;
    }

    public void setMorningTimeEnabled(Boolean morningTimeEnabled) {
        this.morningTimeEnabled = morningTimeEnabled;
    }

    public Boolean getEveningTimeEnabled() {
        return eveningTimeEnabled;
    }

    public void setEveningTimeEnabled(Boolean eveningTimeEnabled) {
        this.eveningTimeEnabled = eveningTimeEnabled;
    }

    public String getNurseConfirmation() {
        return nurseConfirmation;
    }

    public void setNurseConfirmation(String nurseConfirmation) {
        this.nurseConfirmation = nurseConfirmation;
    }

    public String getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(String delayTime) {
        this.delayTime = delayTime;
    }
}
