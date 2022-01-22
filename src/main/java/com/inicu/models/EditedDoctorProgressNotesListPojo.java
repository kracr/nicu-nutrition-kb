package com.inicu.models;

import com.google.gson.JsonObject;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

public class EditedDoctorProgressNotesListPojo implements Serializable {

//    private ArrayList<JSONObject> noteslist;

    private Date entryDate;
    private String loggedUser;
    private Timestamp creationtime;

    public EditedDoctorProgressNotesListPojo(){
        this.entryDate=null;
        this.loggedUser="";
    }

    public Timestamp getCreationtime() {
        return creationtime;
    }

    public void setCreationtime(Timestamp creationtime) {
        this.creationtime = creationtime;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public String getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(String loggedUser) {
        this.loggedUser = loggedUser;
    }
}
