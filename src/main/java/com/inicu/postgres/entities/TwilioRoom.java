package com.inicu.postgres.entities;

import com.inicu.postgres.service.TwilioService;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "twilio_room")
@NamedQuery(name = "TwilioRoom.findAll", query = "SELECT s FROM TwilioRoom s")
public class TwilioRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roomid;

    @Column(name ="roomname")
    private String roomName;

    @Column(name ="username")
    private String userName;

    @Column(name = "caller_name")
    private String callerName;

    @Column(name ="participant_name")
    private String participantName;

    @Column(name = "branchname")
    private String branchName;

    @Column(name = "isactive",columnDefinition = "bool")
    private Boolean isActive;

    @Column(name ="created_at")
    private Timestamp createdAt;

    @Column(name ="expire_time")
    private Timestamp expireTime;

    @Column(name ="creationtime")
    private Timestamp creationtime;

    @Column(name ="modificationtime")
    private Timestamp modificationtime;

    @Column(name = "call_accepted",columnDefinition = "bool")
    private Boolean callAccepted;

    @Column(name = "accepted_timestamp")
    private Timestamp acceptedTimestamp;

    public TwilioRoom(){ }

    public Long getRoomid() {
        return roomid;
    }

    public void setRoomid(Long roomid) {
        this.roomid = roomid;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCallerName() {
        return callerName;
    }

    public void setCallerName(String callerName) {
        this.callerName = callerName;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Timestamp expireTime) {
        this.expireTime = expireTime;
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

    public Boolean getCallAccepted() {
        return callAccepted;
    }

    public void setCallAccepted(Boolean callAccepted) {
        this.callAccepted = callAccepted;
    }

    public Timestamp getAcceptedTimestamp() {
        return acceptedTimestamp;
    }

    public void setAcceptedTimestamp(Timestamp acceptedTimestamp) {
        this.acceptedTimestamp = acceptedTimestamp;
    }
}
