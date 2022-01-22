package com.inicu.postgres.entities;


import javax.persistence.*;

@Entity
@Table(name="ref_systematic_symptoms")
@NamedQuery(name="RefSystematicSymptoms.findAll", query="SELECT r FROM RefSystematicSymptoms r")
public class RefSystematicSymptoms {

    @Id
    @Column(name="systematic_symptoms_id")
    private String systematicSymptomsId;

    private String description;

    @Column(name="systematic_symptoms_cause")
    private String systematicSymptomsCause;

    public String getSystematicSymptomsId() {
        return systematicSymptomsId;
    }

    public void setSystematicSymptomsId(String systematicSymptomsId) {
        this.systematicSymptomsId = systematicSymptomsId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSystematicSymptomsCause() {
        return systematicSymptomsCause;
    }

    public void setSystematicSymptomsCause(String systematicSymptomsCause) {
        this.systematicSymptomsCause = systematicSymptomsCause;
    }
}
