package com.inicu.models;
/*--
 * Author:- iNICU 
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.inicu.postgres.entities.DischargeAminophylline;
import com.inicu.postgres.entities.DischargeBirthdetail;
import com.inicu.postgres.entities.DischargeCaffeine;
import com.inicu.postgres.entities.DischargeCpap;
import com.inicu.postgres.entities.DischargeFeeding;
import com.inicu.postgres.entities.DischargeInfection;
import com.inicu.postgres.entities.DischargeInvestigation;
import com.inicu.postgres.entities.DischargeMetabolic;
import com.inicu.postgres.entities.DischargeNicucourse;
import com.inicu.postgres.entities.DischargePhototherapy;
import com.inicu.postgres.entities.DischargeSummary;
import com.inicu.postgres.entities.DischargeTreatment;
import com.inicu.postgres.entities.DischargeVentcourse;
import com.inicu.postgres.entities.DischargeVentilation;
import com.inicu.postgres.entities.VwVaccinationsBrand;
import com.inicu.postgres.entities.VwVaccinationsPatient;

public class DischargeSummaryAdvancedMasterObj {

	DischargeSummary dischargeSummary;

	DischargeBirthdetail dischargeBirthDetails;

	DischargeNicucourse dischargeNicuCource;

	DischargeAminophylline dischargeAminophyllineObj;
	List<DischargeAminophylline> dischargeAminophyllineList;

	DischargeCaffeine dischargeCaffeineObj;
	List<DischargeCaffeine> dischargeCaffeineList;

	DischargeCpap dischargeCpapObj;
	List<DischargeCpap> dischargeCpapList;

	DischargeFeeding dischargeFeeding;

	DischargeInfection dischargeInfectionObj;
	List<DischargeInfection> dischargeInfectionList;

	DischargeInvestigation dischargeInvestigation;

	DischargeMetabolic dischargeMetabolic;

	DischargeVentcourse dischargeVentcourseObj;
	List<DischargeVentcourse> dischargeVentcourseList;

	DischargePhototherapy dischargePhototherapyObj;
	List<DischargePhototherapy> dischargePhototherapyList;

	DischargeVentilation dischargeVentilation;

	DischargeTreatment dischargeTreatment;

	HashMap<Object, Object> hospitalInfo;

	List<VwVaccinationsBrand> vaccinationBrandList;

	LinkedHashMap<String, List<VwVaccinationsPatient>> patientVaccinationDueageMap;

	@SuppressWarnings("serial")
	public DischargeSummaryAdvancedMasterObj() {
		super();
		this.dischargeSummary = new DischargeSummary();
		this.dischargeBirthDetails = new DischargeBirthdetail();
		this.dischargeNicuCource = new DischargeNicucourse();

		this.dischargeAminophyllineObj = new DischargeAminophylline();
		this.dischargeAminophyllineList = new ArrayList<DischargeAminophylline>();
		this.dischargeCaffeineObj = new DischargeCaffeine();
		this.dischargeCaffeineList = new ArrayList<DischargeCaffeine>();
		this.dischargeCpapObj = new DischargeCpap();
		this.dischargeCpapList = new ArrayList<DischargeCpap>();
		this.dischargeFeeding = new DischargeFeeding();
		this.dischargeInfectionObj = new DischargeInfection();
		this.dischargeInfectionList = new ArrayList<DischargeInfection>();
		this.dischargeInvestigation = new DischargeInvestigation();
		this.dischargeMetabolic = new DischargeMetabolic();
		this.dischargeVentcourseObj = new DischargeVentcourse();
		this.dischargeVentcourseList = new ArrayList<DischargeVentcourse>();
		this.dischargePhototherapyObj = new DischargePhototherapy();
		this.dischargePhototherapyList = new ArrayList<DischargePhototherapy>();
		this.dischargeVentilation = new DischargeVentilation();
		this.dischargeTreatment = new DischargeTreatment();

		this.hospitalInfo = new HashMap<Object, Object>() {
			{
				put("doctorName", " DR VINAY JOSHI / DR PREETHA JOSHI / DR SAJEEV VENGALATH");
				put("hospitalData", new ArrayList<String>() {
					{
						add("HIGH RISK (Neurodevelopmental) FOLLOW UP PACKAGE");
						add("6 Weeks (from expected date of delivery) follow UP");
						add("One Time Package Involving 5 doctors and 2 tests.");
						add("APPOINTMENT FOR THE FOLLOWING DOCTORS - CALL 30696969");
					}
				});

				put("dayData", new HashMap<Object, Object>() {
					{
						put("monday", new ArrayList<String>() {
							{
								add("1. Neonatologist: Dr. Vinay Joshi / Dr. Preetha Joshi");
								add("2. Developmental Physiotherapy: Dr. Vidhi (6th Floor)");
								add("3. Ophthamologist: Dr. Nireb Dongre (2nd Floor) (Ph.No. 30937292)");
							}
						});
						put("tuesday", new ArrayList<String>() {
							{
								add("1. Neonatologist: Dr. Vinay Joshi / Dr. Preetha Joshi");
								add("2. Developmental Physiotherapy: Dr. Vidhi (6th Floor)");
								add("3. Ophthamologist: Dr. Nireb Dongre (2nd Floor) (Ph.No. 30937292)");
							}
						});
						put("wednesday", new ArrayList<String>() {
							{
								add("1. Neonatologist: Dr. Vinay Joshi / Dr. Preetha Joshi");
								add("2. Developmental Physiotherapy: Dr. Vidhi (6th Floor)");
								add("3. Ophthamologist: Dr. Nireb Dongre (2nd Floor) (Ph.No. 30937292)");
							}
						});
						put("thursday", new ArrayList<String>() {
							{
								add("1. Neonatologist: Dr. Vinay Joshi / Dr. Preetha Joshi");
								add("2. Developmental Physiotherapy: Dr. Vidhi (6th Floor)");
								add("3. Ophthamologist: Dr. Nireb Dongre (2nd Floor) (Ph.No. 30937292)");
							}
						});
						put("friday", new ArrayList<String>() {
							{
								add("1. Neonatologist: Dr. Vinay Joshi / Dr. Preetha Joshi");
								add("2. Developmental Physiotherapy: Dr. Vidhi (6th Floor)");
								add("3. Ophthamologist: Dr. Nireb Dongre (2nd Floor) (Ph.No. 30937292)");
							}
						});
						put("saturday", new ArrayList<String>() {
							{
								add("1. Neonatologist: Dr. Vinay Joshi / Dr. Preetha Joshi");
								add("2. Developmental Physiotherapy: Dr. Vidhi (6th Floor)");
								add("3. Ophthamologist: Dr. Nireb Dongre (2nd Floor) (Ph.No. 30937292)");
							}
						});
						put("sunday", new ArrayList<String>() {
							{
								add("1. Neonatologist: Dr. Vinay Joshi / Dr. Preetha Joshi");
								add("2. Developmental Physiotherapy: Dr. Vidhi (6th Floor)");
								add("3. Ophthamologist: Dr. Nireb Dongre (2nd Floor) (Ph.No. 30937292)");
							}
						});
					}
				});
			};
		};
	}

	public DischargeSummary getDischargeSummary() {
		return dischargeSummary;
	}

	public void setDischargeSummary(DischargeSummary dischargeSummary) {
		this.dischargeSummary = dischargeSummary;
	}

	public DischargeBirthdetail getDischargeBirthDetails() {
		return dischargeBirthDetails;
	}

	public void setDischargeBirthDetails(DischargeBirthdetail dischargeBirthDetails) {
		this.dischargeBirthDetails = dischargeBirthDetails;
	}

	public DischargeNicucourse getDischargeNicuCource() {
		return dischargeNicuCource;
	}

	public void setDischargeNicuCource(DischargeNicucourse dischargeNicuCource) {
		this.dischargeNicuCource = dischargeNicuCource;
	}

	public DischargeAminophylline getDischargeAminophyllineObj() {
		return dischargeAminophyllineObj;
	}

	public void setDischargeAminophyllineObj(DischargeAminophylline dischargeAminophyllineObj) {
		this.dischargeAminophyllineObj = dischargeAminophyllineObj;
	}

	public List<DischargeAminophylline> getDischargeAminophyllineList() {
		return dischargeAminophyllineList;
	}

	public void setDischargeAminophyllineList(List<DischargeAminophylline> dischargeAminophyllineList) {
		this.dischargeAminophyllineList = dischargeAminophyllineList;
	}

	public DischargeCaffeine getDischargeCaffeineObj() {
		return dischargeCaffeineObj;
	}

	public void setDischargeCaffeineObj(DischargeCaffeine dischargeCaffeineObj) {
		this.dischargeCaffeineObj = dischargeCaffeineObj;
	}

	public List<DischargeCaffeine> getDischargeCaffeineList() {
		return dischargeCaffeineList;
	}

	public void setDischargeCaffeineList(List<DischargeCaffeine> dischargeCaffeineList) {
		this.dischargeCaffeineList = dischargeCaffeineList;
	}

	public DischargeCpap getDischargeCpapObj() {
		return dischargeCpapObj;
	}

	public void setDischargeCpapObj(DischargeCpap dischargeCpapObj) {
		this.dischargeCpapObj = dischargeCpapObj;
	}

	public List<DischargeCpap> getDischargeCpapList() {
		return dischargeCpapList;
	}

	public void setDischargeCpapList(List<DischargeCpap> dischargeCpapList) {
		this.dischargeCpapList = dischargeCpapList;
	}

	public DischargeFeeding getDischargeFeeding() {
		return dischargeFeeding;
	}

	public void setDischargeFeeding(DischargeFeeding dischargeFeeding) {
		this.dischargeFeeding = dischargeFeeding;
	}

	public DischargeInfection getDischargeInfectionObj() {
		return dischargeInfectionObj;
	}

	public void setDischargeInfectionObj(DischargeInfection dischargeInfectionObj) {
		this.dischargeInfectionObj = dischargeInfectionObj;
	}

	public List<DischargeInfection> getDischargeInfectionList() {
		return dischargeInfectionList;
	}

	public void setDischargeInfectionList(List<DischargeInfection> dischargeInfectionList) {
		this.dischargeInfectionList = dischargeInfectionList;
	}

	public DischargeInvestigation getDischargeInvestigation() {
		return dischargeInvestigation;
	}

	public void setDischargeInvestigation(DischargeInvestigation dischargeInvestigation) {
		this.dischargeInvestigation = dischargeInvestigation;
	}

	public DischargeMetabolic getDischargeMetabolic() {
		return dischargeMetabolic;
	}

	public void setDischargeMetabolic(DischargeMetabolic dischargeMetabolic) {
		this.dischargeMetabolic = dischargeMetabolic;
	}

	public DischargeVentcourse getDischargeVentcourseObj() {
		return dischargeVentcourseObj;
	}

	public void setDischargeVentcourseObj(DischargeVentcourse dischargeVentcourseObj) {
		this.dischargeVentcourseObj = dischargeVentcourseObj;
	}

	public List<DischargeVentcourse> getDischargeVentcourseList() {
		return dischargeVentcourseList;
	}

	public void setDischargeVentcourseList(List<DischargeVentcourse> dischargeVentcourseList) {
		this.dischargeVentcourseList = dischargeVentcourseList;
	}

	public DischargePhototherapy getDischargePhototherapyObj() {
		return dischargePhototherapyObj;
	}

	public void setDischargePhototherapyObj(DischargePhototherapy dischargePhototherapyObj) {
		this.dischargePhototherapyObj = dischargePhototherapyObj;
	}

	public List<DischargePhototherapy> getDischargePhototherapyList() {
		return dischargePhototherapyList;
	}

	public void setDischargePhototherapyList(List<DischargePhototherapy> dischargePhototherapyList) {
		this.dischargePhototherapyList = dischargePhototherapyList;
	}

	public DischargeVentilation getDischargeVentilation() {
		return dischargeVentilation;
	}

	public void setDischargeVentilation(DischargeVentilation dischargeVentilation) {
		this.dischargeVentilation = dischargeVentilation;
	}

	public DischargeTreatment getDischargeTreatment() {
		return dischargeTreatment;
	}

	public void setDischargeTreatment(DischargeTreatment dischargeTreatment) {
		this.dischargeTreatment = dischargeTreatment;
	}

	public HashMap<Object, Object> getHospitalInfo() {
		return hospitalInfo;
	}

	public void setHospitalInfo(HashMap<Object, Object> hospitalInfo) {
		this.hospitalInfo = hospitalInfo;
	}

	public List<VwVaccinationsBrand> getVaccinationBrandList() {
		return vaccinationBrandList;
	}

	public void setVaccinationBrandList(List<VwVaccinationsBrand> vaccinationBrandList) {
		this.vaccinationBrandList = vaccinationBrandList;
	}

	public LinkedHashMap<String, List<VwVaccinationsPatient>> getPatientVaccinationDueageMap() {
		return patientVaccinationDueageMap;
	}

	public void setPatientVaccinationDueageMap(
			LinkedHashMap<String, List<VwVaccinationsPatient>> patientVaccinationDueageMap) {
		this.patientVaccinationDueageMap = patientVaccinationDueageMap;
	}

}
