package com.inicu.models;

import com.inicu.postgres.entities.ClinicalAlertSettings;

import java.util.ArrayList;
import java.util.List;

public class ClinicalSetting {

    List<ClinicalAlertSettings> clinicalAlertSettingsList;

    public ClinicalSetting(){
        this.clinicalAlertSettingsList=new ArrayList<ClinicalAlertSettings>();
    }

    public List<ClinicalAlertSettings> getClinicalAlertSettingsList() {
        return clinicalAlertSettingsList;
    }

    public void setClinicalAlertSettingsList(List<ClinicalAlertSettings> clinicalAlertSettingsList) {
        this.clinicalAlertSettingsList = clinicalAlertSettingsList;
    }
}
