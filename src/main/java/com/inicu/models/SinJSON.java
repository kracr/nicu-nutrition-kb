package com.inicu.models;

import java.sql.Timestamp;

public class SinJSON {

	Timestamp dataDate;
	SinComponentPOJO noOfBabies;
	SinComponentPOJO ivAntibiotics;
	SinComponentPOJO nonInvasiveVentilator;
	SinComponentPOJO totalVentilator;
	SinComponentPOJO centralLine;
	SinComponentPOJO peripheralCannula;
	SinComponentPOJO pnFeed;
	SinComponentPOJO ivFeed;
	
	public SinJSON() {
		super();
		this.noOfBabies = new SinComponentPOJO();
		this.ivAntibiotics = new SinComponentPOJO();
		this.nonInvasiveVentilator = new SinComponentPOJO();
		this.totalVentilator = new SinComponentPOJO();
		this.centralLine = new SinComponentPOJO();
		this.peripheralCannula = new SinComponentPOJO();
		this.ivFeed = new SinComponentPOJO();
		this.pnFeed = new SinComponentPOJO();
	}
	

	public Timestamp getDataDate() {
		return dataDate;
	}

	public void setDataDate(Timestamp dataDate) {
		this.dataDate = dataDate;
	}

	public SinComponentPOJO getNoOfBabies() {
		return noOfBabies;
	}

	public void setNoOfBabies(SinComponentPOJO noOfBabies) {
		this.noOfBabies = noOfBabies;
	}

	public SinComponentPOJO getIvAntibiotics() {
		return ivAntibiotics;
	}

	public void setIvAntibiotics(SinComponentPOJO ivAntibiotics) {
		this.ivAntibiotics = ivAntibiotics;
	}

	public SinComponentPOJO getNonInvasiveVentilator() {
		return nonInvasiveVentilator;
	}

	public void setNonInvasiveVentilator(SinComponentPOJO nonInvasiveVentilator) {
		this.nonInvasiveVentilator = nonInvasiveVentilator;
	}

	public SinComponentPOJO getTotalVentilator() {
		return totalVentilator;
	}

	public void setTotalVentilator(SinComponentPOJO totalVentilator) {
		this.totalVentilator = totalVentilator;
	}

	public SinComponentPOJO getCentralLine() {
		return centralLine;
	}

	public void setCentralLine(SinComponentPOJO centralLine) {
		this.centralLine = centralLine;
	}

	public SinComponentPOJO getPeripheralCannula() {
		return peripheralCannula;
	}

	public void setPeripheralCannula(SinComponentPOJO peripheralCannula) {
		this.peripheralCannula = peripheralCannula;
	}

	public SinComponentPOJO getPnFeed() {
		return pnFeed;
	}

	public void setPnFeed(SinComponentPOJO pnFeed) {
		this.pnFeed = pnFeed;
	}

	public SinComponentPOJO getIvFeed() {
		return ivFeed;
	}

	public void setIvFeed(SinComponentPOJO ivFeed) {
		this.ivFeed = ivFeed;
	}

	@Override
	public String toString() {
		return "SinJSON [dataDate=" + dataDate + ", noOfBabies=" + noOfBabies + ", ivAntibiotics=" + ivAntibiotics
				+ ", nonInvasiveVentilator=" + nonInvasiveVentilator + ", totalVentilator=" + totalVentilator
				+ ", centralLine=" + centralLine + ", peripheralCannula=" + peripheralCannula + ", pnFeed=" + pnFeed
				+ ", ivFeed=" + ivFeed + "]";
	}

}
