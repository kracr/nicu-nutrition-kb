package com.inicu.postgres.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "clinical_alert_settings")
@NamedQuery(name = "ClinicalAlertSettings.findAll", query = "SELECT c FROM ClinicalAlertSettings c")
public class ClinicalAlertSettings{

    @Id
    @Column(name = "alert_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long alertId;

    @Column(name = "parameter_name")
    private String parameterName;

    @Column(name = "min_value")
    private String minValue;

    @Column(name = "max_value")
    private String maxValue;

    @Column(name = "dependency_list")
    private String dependency;

    @Column(name = "branchname")
    private String branchname;

    @Transient
    private List<String> dependencyList;

    public ClinicalAlertSettings() {
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public String getDependency() {
        return dependency;
    }

    public void setDependency(String dependency) {
        this.dependency = dependency;
    }

    public List<String> getDependencyList() {
        return dependencyList;
    }

    public void setDependencyList(List<String> dependencyList) {
        this.dependencyList = dependencyList;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    public Long getAlertId() {
        return alertId;
    }

    public void setAlertId(Long alertId) {
        this.alertId = alertId;
    }
}
