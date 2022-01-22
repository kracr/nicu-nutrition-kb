package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name = "nursing_blood_gas_ventilator_final")
@NamedQuery(name = "NursingBloodGasVentilator.findAll", query = "SELECT v FROM NursingBloodGasVentilator v")
public class NursingBloodGasVentilator implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private String uniquekey;
	private String uhid;
	private Timestamp entrydate;
	private String ventmode;
    private String pip;
    private String peep_cpap;
    private String pressure_supply;
    private String map;
    private String freq_rate;
    private String tidal_volume;
    private String minute_volume;
    private String ti;
    private String fio2;
    private String flow_per_min;
    private String no_ppm;
    private String comments;
    private String amplitude;
    private String frequency;
    private String ph;
    private String pco2;
    private String hco2;
    private String po2;
    private String be;
    private String lactate;
    private String spo2;
    private String na;
    private String k;
    private String cl;
    private String glucose;
    private String ionized_calcium;
    private String regular_hco3;
    private String be_ecf;
    private String hct;
    private String sample_type;
    private String thbc;
    private String osmolarity;
    private String anion_gap;
    private String sample_method;
    private String dco2;
    private String volume_guarantee;
    private String cpap_type;
    private String delivery_type;
    private String pressure_support_type;
    private String control_type;
	private String vent_desc;

	@Column(name = "intubated_humidification_temp")
	private String intubatedHumidificationTemp;
    
    public NursingBloodGasVentilator() {
		super();
	}

	public String getIntubatedHumidificationTemp() {
		return intubatedHumidificationTemp;
	}

	public void setIntubatedHumidificationTemp(String intubatedHumidificationTemp) {
		this.intubatedHumidificationTemp = intubatedHumidificationTemp;
	}

	public String getUniquekey() {
		return uniquekey;
	}

	public void setUniquekey(String uniquekey) {
		this.uniquekey = uniquekey;
	}

	public String getUhid() {
		return uhid;
	}
	public Timestamp getEntrydate() {
		return entrydate;
	}
	public String getVentmode() {
		return ventmode;
	}
	public String getPip() {
		return pip;
	}
	public String getPeep_cpap() {
		return peep_cpap;
	}
	public String getPressure_supply() {
		return pressure_supply;
	}
	public String getMap() {
		return map;
	}
	public String getFreq_rate() {
		return freq_rate;
	}
	public String getTidal_volume() {
		return tidal_volume;
	}
	public String getMinute_volume() {
		return minute_volume;
	}
	public String getTi() {
		return ti;
	}
	public String getFio2() {
		return fio2;
	}
	public String getFlow_per_min() {
		return flow_per_min;
	}
	public String getNo_ppm() {
		return no_ppm;
	}
	public String getComments() {
		return comments;
	}
	public String getAmplitude() {
		return amplitude;
	}
	public String getFrequency() {
		return frequency;
	}
	public String getPh() {
		return ph;
	}
	public String getPco2() {
		return pco2;
	}
	public String getHco2() {
		return hco2;
	}
	public String getPo2() {
		return po2;
	}
	public String getBe() {
		return be;
	}
	public String getLactate() {
		return lactate;
	}
	public String getSpo2() {
		return spo2;
	}
	public String getNa() {
		return na;
	}
	public String getK() {
		return k;
	}
	public String getCl() {
		return cl;
	}
	public String getGlucose() {
		return glucose;
	}
	public String getIonized_calcium() {
		return ionized_calcium;
	}
	public String getRegular_hco3() {
		return regular_hco3;
	}
	public String getBe_ecf() {
		return be_ecf;
	}
	public String getHct() {
		return hct;
	}
	public String getSample_type() {
		return sample_type;
	}
	public String getThbc() {
		return thbc;
	}
	public String getOsmolarity() {
		return osmolarity;
	}
	public String getAnion_gap() {
		return anion_gap;
	}
	public String getSample_method() {
		return sample_method;
	}
	public void setUhid(String uhid) {
		this.uhid = uhid;
	}
	public void setEntrydate(Timestamp entrydate) {
		this.entrydate = entrydate;
	}
	public void setVentmode(String ventmode) {
		this.ventmode = ventmode;
	}
	public void setPip(String pip) {
		this.pip = pip;
	}
	public void setPeep_cpap(String peep_cpap) {
		this.peep_cpap = peep_cpap;
	}
	public void setPressure_supply(String pressure_supply) {
		this.pressure_supply = pressure_supply;
	}
	public void setMap(String map) {
		this.map = map;
	}
	public void setFreq_rate(String freq_rate) {
		this.freq_rate = freq_rate;
	}
	public void setTidal_volume(String tidal_volume) {
		this.tidal_volume = tidal_volume;
	}
	public void setMinute_volume(String minute_volume) {
		this.minute_volume = minute_volume;
	}
	public void setTi(String ti) {
		this.ti = ti;
	}
	public void setFio2(String fio2) {
		this.fio2 = fio2;
	}
	public void setFlow_per_min(String flow_per_min) {
		this.flow_per_min = flow_per_min;
	}
	public void setNo_ppm(String no_ppm) {
		this.no_ppm = no_ppm;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public void setAmplitude(String amplitude) {
		this.amplitude = amplitude;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public void setPh(String ph) {
		this.ph = ph;
	}
	public void setPco2(String pco2) {
		this.pco2 = pco2;
	}
	public void setHco2(String hco2) {
		this.hco2 = hco2;
	}
	public void setPo2(String po2) {
		this.po2 = po2;
	}
	public void setBe(String be) {
		this.be = be;
	}
	public void setLactate(String lactate) {
		this.lactate = lactate;
	}
	public void setSpo2(String spo2) {
		this.spo2 = spo2;
	}
	public void setNa(String na) {
		this.na = na;
	}
	public void setK(String k) {
		this.k = k;
	}
	public void setCl(String cl) {
		this.cl = cl;
	}
	public void setGlucose(String glucose) {
		this.glucose = glucose;
	}
	public void setIonized_calcium(String ionized_calcium) {
		this.ionized_calcium = ionized_calcium;
	}
	public void setRegular_hco3(String regular_hco3) {
		this.regular_hco3 = regular_hco3;
	}
	public void setBe_ecf(String be_ecf) {
		this.be_ecf = be_ecf;
	}
	public void setHct(String hct) {
		this.hct = hct;
	}
	public void setSample_type(String sample_type) {
		this.sample_type = sample_type;
	}
	public void setThbc(String thbc) {
		this.thbc = thbc;
	}
	public void setOsmolarity(String osmolarity) {
		this.osmolarity = osmolarity;
	}
	public void setAnion_gap(String anion_gap) {
		this.anion_gap = anion_gap;
	}
	public void setSample_method(String sample_method) {
		this.sample_method = sample_method;
	}
	public String getDco2() {
		return dco2;
	}

	public void setDco2(String dco2) {
		this.dco2 = dco2;
	}

	public String getVolume_guarantee() {
		return volume_guarantee;
	}

	public void setVolume_guarantee(String volume_guarantee) {
		this.volume_guarantee = volume_guarantee;
	}

	public String getCpap_type() {
		return cpap_type;
	}

	public void setCpap_type(String cpap_type) {
		this.cpap_type = cpap_type;
	}

	public String getDelivery_type() {
		return delivery_type;
	}

	public void setDelivery_type(String delivery_type) {
		this.delivery_type = delivery_type;
	}

	public String getPressure_support_type() {
		return pressure_support_type;
	}

	public void setPressure_support_type(String pressure_support_type) {
		this.pressure_support_type = pressure_support_type;
	}

	public String getControl_type() {
		return control_type;
	}

	public void setControl_type(String control_type) {
		this.control_type = control_type;
	}

	public String getVent_desc() {
		return vent_desc;
	}

	public void setVent_desc(String vent_desc) {
		this.vent_desc = vent_desc;
	}

	@Override
	public String toString() {
		return "NursingBloodGasVentilator [uniquekey=" + uniquekey + ", uhid=" + uhid + ", entrydate=" + entrydate
				+ ", ventmode=" + ventmode + ", pip=" + pip + ", peep_cpap=" + peep_cpap + ", pressure_supply="
				+ pressure_supply + ", map=" + map + ", freq_rate=" + freq_rate + ", tidal_volume=" + tidal_volume
				+ ", minute_volume=" + minute_volume + ", ti=" + ti + ", fio2=" + fio2 + ", flow_per_min="
				+ flow_per_min + ", no_ppm=" + no_ppm + ", comments=" + comments + ", amplitude=" + amplitude
				+ ", frequency=" + frequency + ", ph=" + ph + ", pco2=" + pco2 + ", hco2=" + hco2 + ", po2=" + po2
				+ ", be=" + be + ", lactate=" + lactate + ", spo2=" + spo2 + ", na=" + na + ", k=" + k + ", cl=" + cl
				+ ", glucose=" + glucose + ", ionized_calcium=" + ionized_calcium + ", regular_hco3=" + regular_hco3
				+ ", be_ecf=" + be_ecf + ", hct=" + hct + ", sample_type=" + sample_type + ", thbc=" + thbc
				+ ", osmolarity=" + osmolarity + ", anion_gap=" + anion_gap + ", sample_method=" + sample_method
				+ ", dco2=" + dco2 + ", volume_guarantee=" + volume_guarantee + ", cpap_type=" + cpap_type
				+ ", delivery_type=" + delivery_type + ", pressure_support_type=" + pressure_support_type
				+ ", control_type=" + control_type + "]";
	}

}
