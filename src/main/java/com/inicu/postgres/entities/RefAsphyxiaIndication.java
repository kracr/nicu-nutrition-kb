package com.inicu.postgres.entities;

import javax.persistence.*;

@Entity
@Table(name="ref_asphyxia_indication")
@NamedQuery(name="RefAsphyxiaIndication.findAll", query="SELECT r FROM RefAsphyxiaIndication r")
public class RefAsphyxiaIndication {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="indication_id")
    private String indicationId;

    @Column(name="description")
    private String description;

    @Column(name="indication_name")
    private String indicationName;


    public String getIndicationId() {
        return indicationId;
    }

    public void setIndicationId(String indicationId) {
        this.indicationId = indicationId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIndicationName() {
        return indicationName;
    }

    public void setIndicationName(String indicationName) {
        this.indicationName = indicationName;
    }
}
