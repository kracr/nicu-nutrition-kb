package com.inicu.postgres.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "clinical_alert_item_dropdown")
@NamedQuery(name = "ClinicalAlertItemDropdown.findAll", query = "SELECT c FROM ClinicalAlertItemDropdown c")
public class ClinicalAlertItemDropdown implements Serializable {

    @Id
    @Column(name = "clinical_item_id")
    private Long clinicalItemId;

    @Column(name = "clinical_item_name")
    private String clinicalItemName;

    @Column(name = "clinical_item_code")
    private String clinicalItemCode;

    @Column( name = "clinical_item_description")
    private String clinicalItemDescription;

    public ClinicalAlertItemDropdown(){

    }

    public Long getClinicalItemId() {
        return clinicalItemId;
    }

    public void setClinicalItemId(Long clinicalItemId) {
        this.clinicalItemId = clinicalItemId;
    }

    public String getClinicalItemName() {
        return clinicalItemName;
    }

    public void setClinicalItemName(String clinicalItemName) {
        this.clinicalItemName = clinicalItemName;
    }

    public String getClinicalItemCode() {
        return clinicalItemCode;
    }

    public void setClinicalItemCode(String clinicalItemCode) {
        this.clinicalItemCode = clinicalItemCode;
    }

    public String getClinicalItemDescription() {
        return clinicalItemDescription;
    }

    public void setClinicalItemDescription(String clinicalItemDescription) {
        this.clinicalItemDescription = clinicalItemDescription;
    }
}
