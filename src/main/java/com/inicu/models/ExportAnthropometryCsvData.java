package com.inicu.models;

import java.sql.Time;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public class ExportAnthropometryCsvData {	
	
	private String UHID;
	private String BABY_NAME;
	private String GA_AT_BIRTH;
	private String ENTRY_DATE;
	private Time ENTRY_TIME;
	private String NICU_DAY;
	private String DOL;
	private String CORRECTED_GA;
	private String WEIGHT;
	private String HEIGHT;
	private String HEAD_CIRCUMFERRENCE;
	private String WORKING_WEIGHT;
	private String WEIGHT_FOR_CAL;
	
	public ExportAnthropometryCsvData() {
		super();
		UHID = "";
		BABY_NAME = "";
		GA_AT_BIRTH = "";
		WORKING_WEIGHT = "";
		WEIGHT_FOR_CAL = "";
		NICU_DAY = "";
		DOL = "";
		CORRECTED_GA = "";
		WEIGHT = "";
		HEAD_CIRCUMFERRENCE = "";
		HEIGHT = "";
	}

	public String getUHID() {
		return UHID;
	}

	public void setUHID(String uHID) {
		UHID = uHID;
	}

	public String getBABY_NAME() {
		return BABY_NAME;
	}

	public void setBABY_NAME(String bABY_NAME) {
		BABY_NAME = bABY_NAME;
	}

	public String getGA_AT_BIRTH() {
		return GA_AT_BIRTH;
	}

	public void setGA_AT_BIRTH(String gA_AT_BIRTH) {
		GA_AT_BIRTH = gA_AT_BIRTH;
	}

	public String getENTRY_DATE() {
		return ENTRY_DATE;
	}

	public void setENTRY_DATE(String eNTRY_DATE) {
		ENTRY_DATE = eNTRY_DATE;
	}

	public Time getENTRY_TIME() {
		return ENTRY_TIME;
	}

	public void setENTRY_TIME(Time eNTRY_TIME) {
		ENTRY_TIME = eNTRY_TIME;
	}

	public String getNICU_DAY() {
		return NICU_DAY;
	}

	public void setNICU_DAY(String nICU_DAY) {
		NICU_DAY = nICU_DAY;
	}

	public String getDOL() {
		return DOL;
	}

	public void setDOL(String dOL) {
		DOL = dOL;
	}

	public String getCORRECTED_GA() {
		return CORRECTED_GA;
	}

	public void setCORRECTED_GA(String cORRECTED_GA) {
		CORRECTED_GA = cORRECTED_GA;
	}

	public String getWEIGHT() {
		return WEIGHT;
	}

	public void setWEIGHT(String wEIGHT) {
		WEIGHT = wEIGHT;
	}

	public String getHEIGHT() {
		return HEIGHT;
	}

	public void setHEIGHT(String hEIGHT) {
		HEIGHT = hEIGHT;
	}

	public String getHEAD_CIRCUMFERRENCE() {
		return HEAD_CIRCUMFERRENCE;
	}

	public void setHEAD_CIRCUMFERRENCE(String hEAD_CIRCUMFERRENCE) {
		HEAD_CIRCUMFERRENCE = hEAD_CIRCUMFERRENCE;
	}

	public String getWORKING_WEIGHT() {
		return WORKING_WEIGHT;
	}

	public void setWORKING_WEIGHT(String wORKING_WEIGHT) {
		WORKING_WEIGHT = wORKING_WEIGHT;
	}

	public String getWEIGHT_FOR_CAL() {
		return WEIGHT_FOR_CAL;
	}

	public void setWEIGHT_FOR_CAL(String wEIGHT_FOR_CAL) {
		WEIGHT_FOR_CAL = wEIGHT_FOR_CAL;
	}
	
	
	
}
