package com.inicu.postgres.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "local_configurations")
@NamedQuery(name = "LocalConfigurations.findAll", query = "SELECT b FROM LocalConfigurations b")
public class LocalConfigurations implements Serializable {

    @Id
    @Column(name = "configurationsid")
    private String configurationsId;

    @Column(name = "country")
    private String country;

    @Column(name = "local_date")
    private String localDate;

    @Column(name = "local_datetime")
    private String localDatetime;

    @Column(name = "local_date_medium")
    private String localDateMedium;

    @Column(name = "local_datetime_medium")
    private String localDatetimeMdium;

    @Column(name = "local_datepicker")
    private String localDatepicker;

    @Column(name = "addLangs")
    private String addLangs;

    @Column(name = "time_format")
    private String timeFormat;


    @Column(name = "useLang")
    private String useLang;

    @Column(name="local_datepicker_withouttime")
    private String localDatepickerWithouttime;

    @Column(name = "datepicker_timeformat")
    private String datepickerTimeformat;

    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    public String getLocalDatepickerWithouttime() {
        return localDatepickerWithouttime;
    }

    public void setLocalDatepickerWithouttime(String localDatepickerWithouttime) {
        this.localDatepickerWithouttime = localDatepickerWithouttime;
    }

    public String getConfigurationsId() {
        return configurationsId;
    }

    public void setConfigurationsId(String configurationsId) {
        this.configurationsId = configurationsId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocalDate() {
        return localDate;
    }

    public void setLocalDate(String localDate) {
        this.localDate = localDate;
    }

    public String getLocalDatetime() {
        return localDatetime;
    }

    public void setLocalDatetime(String localDatetime) {
        this.localDatetime = localDatetime;
    }

    public String getLocalDateMedium() {
        return localDateMedium;
    }

    public void setLocalDateMedium(String localDateMedium) {
        this.localDateMedium = localDateMedium;
    }

    public String getLocalDatetimeMdium() {
        return localDatetimeMdium;
    }

    public void setLocalDatetimeMdium(String localDatetimeMdium) {
        this.localDatetimeMdium = localDatetimeMdium;
    }

    public String getLocalDatepicker() {
        return localDatepicker;
    }

    public void setLocalDatepicker(String localDatepicker) {
        this.localDatepicker = localDatepicker;
    }

    public String getAddLangs() {
        return addLangs;
    }

    public void setAddLangs(String addLangs) {
        this.addLangs = addLangs;
    }

    public String getUseLang() {
        return useLang;
    }

    public void setUseLang(String useLang) {
        this.useLang = useLang;
    }

    public String getDatepickerTimeformat() {
        return datepickerTimeformat;
    }

    public void setDatepickerTimeformat(String datepickerTimeformat) {
        this.datepickerTimeformat = datepickerTimeformat;
    }
}
