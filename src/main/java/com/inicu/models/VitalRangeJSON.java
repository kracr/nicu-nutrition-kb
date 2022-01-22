package com.inicu.models;

public class VitalRangeJSON {
	
	MinMaxRange hr;
	MinMaxRange rr;
	MinMaxRange spo2;
	MinMaxRange pip;
	MinMaxRange peep;
	MinMaxRange fio2;
	MinMaxRange rate;
	MinMaxRange map;
	MinMaxRange nbp_s;
	MinMaxRange nbp_d;
	MinMaxRange nbp_m;

	MinMaxRange ibp_s;
	MinMaxRange ibp_d;
	MinMaxRange ibp_m;

	MinMaxRange central_temperature;
	MinMaxRange peripheral_temperature;
	MinMaxRange humidity;

	public MinMaxRange getCentral_temperature() {
		return central_temperature;
	}

	public void setCentral_temperature(MinMaxRange central_temperature) {
		this.central_temperature = central_temperature;
	}

	public MinMaxRange getPeripheral_temperature() {
		return peripheral_temperature;
	}

	public void setPeripheral_temperature(MinMaxRange peripheral_temperature) {
		this.peripheral_temperature = peripheral_temperature;
	}

	public MinMaxRange getHumidity() {
		return humidity;
	}

	public void setHumidity(MinMaxRange humidity) {
		this.humidity = humidity;
	}

	public MinMaxRange getNbp_s() {
		return nbp_s;
	}

	public void setNbp_s(MinMaxRange nbp_s) {
		this.nbp_s = nbp_s;
	}

	public MinMaxRange getNbp_d() {
		return nbp_d;
	}

	public void setNbp_d(MinMaxRange nbp_d) {
		this.nbp_d = nbp_d;
	}

	public MinMaxRange getNbp_m() {
		return nbp_m;
	}

	public void setNbp_m(MinMaxRange nbp_m) {
		this.nbp_m = nbp_m;
	}

	public MinMaxRange getIbp_s() {
		return ibp_s;
	}

	public void setIbp_s(MinMaxRange ibp_s) {
		this.ibp_s = ibp_s;
	}

	public MinMaxRange getIbp_d() {
		return ibp_d;
	}

	public void setIbp_d(MinMaxRange ibp_d) {
		this.ibp_d = ibp_d;
	}

	public MinMaxRange getIbp_m() {
		return ibp_m;
	}

	public void setIbp_m(MinMaxRange ibp_m) {
		this.ibp_m = ibp_m;
	}

	public MinMaxRange getHr() {
		return hr;
	}
	public void setHr(MinMaxRange hr) {
		this.hr = hr;
	}
	public MinMaxRange getRr() {
		return rr;
	}
	public void setRr(MinMaxRange rr) {
		this.rr = rr;
	}
	public MinMaxRange getSpo2() {
		return spo2;
	}
	public void setSpo2(MinMaxRange spo2) {
		this.spo2 = spo2;
	}
	public MinMaxRange getPip() {
		return pip;
	}
	public void setPip(MinMaxRange pip) {
		this.pip = pip;
	}
	public MinMaxRange getPeep() {
		return peep;
	}
	public void setPeep(MinMaxRange peep) {
		this.peep = peep;
	}
	public MinMaxRange getFio2() {
		return fio2;
	}
	public void setFio2(MinMaxRange fio2) {
		this.fio2 = fio2;
	}
	public MinMaxRange getRate() {
		return rate;
	}
	public void setRate(MinMaxRange rate) {
		this.rate = rate;
	}
	public MinMaxRange getMap() {
		return map;
	}
	public void setMap(MinMaxRange map) {
		this.map = map;
	}
	
}
