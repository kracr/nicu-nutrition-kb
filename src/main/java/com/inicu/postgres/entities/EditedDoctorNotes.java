package com.inicu.postgres.entities;


import com.inicu.postgres.utility.BasicUtils;
import scala.Array;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Table(name = "edited_doctor_notes")
@NamedQuery(name = "EditedDoctorNotes.findAll", query = "SELECT n FROM EditedDoctorNotes n")
public class EditedDoctorNotes implements Serializable {

    @Id
    @Column(name = "edited_note_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long editedNoteId;

    private String uhid;

    @Column(name = "progress_notes")
    private String progressNotes;

    @Column(name = "logged_user")
    private String loggedUser;

    @Column(name = "branchname")
    private String branchName;
    private Date entrydate;
    private Timestamp creationtime;
    private Timestamp modificationtime;
    private String fromwhere;

    public EditedDoctorNotes(){
    }

    public EditedDoctorNotes(Object[] editNote){
        this.editedNoteId=BasicUtils.toLongSafe(editNote[0]);;
        this.uhid= BasicUtils.toStringSafe(editNote[1]);
        this.progressNotes=BasicUtils.toStringSafe(editNote[2]);
        this.loggedUser=BasicUtils.toStringSafe(editNote[3]);
        this.branchName=BasicUtils.toStringSafe(editNote[4]);
        this.entrydate= BasicUtils.stringToDate(BasicUtils.toStringSafe(editNote[5]));
        this.creationtime=BasicUtils.toTimestampSafe(editNote[6]);
        this.modificationtime=BasicUtils.toTimestampSafe(editNote[7]);
        this.fromwhere = BasicUtils.toStringSafe(editNote[8]);
    }

    public String getUhid() {
        return uhid;
    }

    public void setUhid(String uhid) {
        this.uhid = uhid;
    }

    public String getProgressNotes() {
        return progressNotes;
    }

    public void setProgressNotes(String progressNotes) {
        this.progressNotes = progressNotes;
    }

    public String getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(String loggedUser) {
        this.loggedUser = loggedUser;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Date getEntrydate() {
        return entrydate;
    }

    public void setEntrydate(Date entrydate) {
        this.entrydate = entrydate;
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

    public Long getEditedNoteId() {
        return editedNoteId;
    }

    public void setEditedNoteId(Long editedNoteId) {
        this.editedNoteId = editedNoteId;
    }

    public String getFromwhere() {
        return fromwhere;
    }

    public void setFromwhere(String fromwhere) {
        this.fromwhere = fromwhere;
    }
}
