package com.inicu.postgres.entities;


import scala.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
@Table(name="vw_icdcode_final")
@NamedQuery(name="VwIcdCause.findAll", query="SELECT v FROM VwIcdCause v")
public class VwIcdCause implements Serializable {

    @Id
    private String uniquekey;

    private Long id;

    private Timestamp creationtime;

    private String uhid;

    private String category;

    private String icdcause;

    private String event;

    private String eventstatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getCreationtime() {
        return creationtime;
    }

    public void setCreationtime(Timestamp creationtime) {
        this.creationtime = creationtime;
    }

    public String getUhid() {
        return uhid;
    }

    public void setUhid(String uhid) {
        this.uhid = uhid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIcdcause() {
        return icdcause;
    }

    public void setIcdcause(String icdcause) {
        this.icdcause = icdcause;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getEventstatus() {
        return eventstatus;
    }

    public void setEventstatus(String eventstatus) {
        this.eventstatus = eventstatus;
    }
}
