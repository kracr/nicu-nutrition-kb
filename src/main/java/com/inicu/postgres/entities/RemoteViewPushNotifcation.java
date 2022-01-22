package com.inicu.postgres.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name ="remote_view_push_notifcation")
@NamedQuery(name = "RemoteViewPushNotifcation.findAll", query = "SELECT r FROM RemoteViewPushNotifcation r")
public class RemoteViewPushNotifcation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long notification_id;

    private String uhid;

    private String message;

    private String response;

    @Column(name = "time_type")
    private String timeType;

    @Column(name ="delay_message")
    private String delayMessage;

    @Column(columnDefinition = "bool")
    private boolean delay;

    @Column(name = "is_active",columnDefinition = "bool")
    private boolean isActive;

    @Column(name ="response_time")
    private Timestamp responseTime;

    @Column(name="branchname")
    private String branchname;

    private Timestamp creationtime;
    private Timestamp modificationtime;

    @Transient
    private boolean morningEnabled;

    @Transient
    private boolean eveningEnabled;

    public RemoteViewPushNotifcation(){
        this.morningEnabled = false;
        this.eveningEnabled = false;
        this.delay = false;
        this.isActive = true;
    }

    public Long getNotification_id() {
        return notification_id;
    }

    public void setNotification_id(Long notification_id) {
        this.notification_id = notification_id;
    }

    public String getUhid() {
        return uhid;
    }

    public void setUhid(String uhid) {
        this.uhid = uhid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public String getDelayMessage() {
        return delayMessage;
    }

    public void setDelayMessage(String delayMessage) {
        this.delayMessage = delayMessage;
    }

    public boolean isDelay() {
        return delay;
    }

    public void setDelay(boolean delay) {
        this.delay = delay;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
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

    public Timestamp getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Timestamp responseTime) {
        this.responseTime = responseTime;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    public boolean isMorningEnabled() {
        return morningEnabled;
    }

    public void setMorningEnabled(boolean morningEnabled) {
        this.morningEnabled = morningEnabled;
    }

    public boolean isEveningEnabled() {
        return eveningEnabled;
    }

    public void setEveningEnabled(boolean eveningEnabled) {
        this.eveningEnabled = eveningEnabled;
    }
}
