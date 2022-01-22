package com.inicu.postgres.entities;

import javax.persistence.*;

@Entity
@Table(name="ref_necrisk_factor")
@NamedQuery(name="RefNecRiskFactor.findAll", query="SELECT r FROM RefNecRiskFactor r")
public class RefNecRiskFactor {

    @Id
    @Column(name="necrisk_factor_id")
    private String riskFactorId;

    private String description;

    @Column(name="riskfactor")
    private String riskfactor;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getRiskFactorId() {
        return riskFactorId;
    }

    public void setRiskFactorId(String riskFactorId) {
        this.riskFactorId = riskFactorId;
    }

    public String getRiskfactor() {
        return riskfactor;
    }

    public void setRiskfactor(String riskfactor) {
        this.riskfactor = riskfactor;
    }
}
