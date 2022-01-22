package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ref_pd_indication database table.
 *
 */

@Entity
@Table(name="ref_causeofnpo")
@NamedQuery(name="RefCauseOfNpo.findAll", query="SELECT r FROM RefCauseOfNpo r")
public class RefCauseOfNpo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="cause_id")
    private String causeId;

    @Column(name="description")
    private String description;

    @Column(name="cause_name")
    private String casueName;


    public String getCauseId() {
        return causeId;
    }

    public void setCauseId(String causeId) {
        this.causeId = causeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCasueName() {
        return casueName;
    }

    public void setCasueName(String casueName) {
        this.casueName = casueName;
    }
}