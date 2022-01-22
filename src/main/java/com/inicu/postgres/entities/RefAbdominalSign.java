package com.inicu.postgres.entities;

import javax.persistence.*;

@Entity
@Table(name="ref_abdominal_sign")
@NamedQuery(name="RefAbdominalSign.findAll", query="SELECT r FROM RefAbdominalSign r")
public class RefAbdominalSign {

    @Id
    @Column(name="abdominal_sign_id")
    private String abdominalSignId;

    private String description;

    @Column(name="abdominal_sign_cause")
    private String abdominalSignCause;

    public String getAbdominalSignId() {
        return abdominalSignId;
    }

    public void setAbdominalSignId(String abdominalSignId) {
        this.abdominalSignId = abdominalSignId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAbdominalSignCause() {
        return abdominalSignCause;
    }

    public void setAbdominalSignCause(String abdominalSignCause) {
        this.abdominalSignCause = abdominalSignCause;
    }
}
