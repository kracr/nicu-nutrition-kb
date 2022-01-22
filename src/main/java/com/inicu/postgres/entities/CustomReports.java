package com.inicu.postgres.entities;


import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the custom_reports database table.
 *
 */
@Entity
@Table(name = "custom_reports")
@NamedQuery(name = "CustomReports.findAll", query = "SELECT b FROM CustomReports b")
public class CustomReports implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long customreportsid;

    @Column(name = "cr_name")
    private String crName;

    @Column(name = "created_by")
    private String createdBy;

    private Timestamp creation_time;

    @Column(name = "input_filters",columnDefinition = "text")
    private String inputFilters;

    public Long getCrID() {
        return customreportsid;
    }

    public void setCrID(Long crID) {
        this.customreportsid = crID;
    }

    public String getCrName() {
        return crName;
    }

    public void setCrName(String crName) {
        this.crName = crName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreation_time() {
        return creation_time;
    }

    public void setCreation_time(Timestamp creation_time) {
        this.creation_time = creation_time;
    }

    public String getInputFilters() {
        return inputFilters;
    }

    public void setInputFilters(String inputFilters) {
        this.inputFilters = inputFilters;
    }

    public String getOutputFilters() {
        return outputFilters;
    }

    public void setOutputFilters(String outputFilters) {
        this.outputFilters = outputFilters;
    }

    @Column(name = "output_filters",columnDefinition = "text")
    private String outputFilters;

    public CustomReports() {
        super();
    }
}