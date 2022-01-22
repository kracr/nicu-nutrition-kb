package com.inicu.models;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
public class SystemicReportJSONAsphyxia {

	private String uhid;
	private String name;
	private String hoursAtAdmission;
	private float gestation_weeks;
	private float gestation_days;
	private String IUGR;
	private String Chorioamniotis;
	private String sepsis;
	private String Mode_Of_Delivery;
	private float birth_weight;
	private Integer Onemin_APGAR;
	private Integer Fivemin_APGAR;
	private Integer Sarnat_Score;
	private Integer Age_At_Discharge;
	private String Ventilator_Support;
	private String MAS;
	private String sodium;
	private String calcium;
	private String potassium;
	private String bloodUrea;
	private String seremCreatinine;
	private String seremCreatinineTimeHours;
	private String PT;
	private String APTT;
	private String Liver_Enzymes;
	private String INR;
	private String TLC;
	private String PLT;
	private String PH;
	private String lactate;
	private String BE;
	private String BloodGasTimeHours;
	private String patient_type;
	private String cooling;
	private String seizures;
	private String seizuresHoursAtBirth;
	private String FFP;
	private String Inotropes;
	private String DOA;
	private String TOA;
	private String DOB;
	private String TOB;
	private String DOD;
	private String outcome;
	private String deviceDataAvailable;
}
