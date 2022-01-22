package com.inicu.postgres.utility;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import com.inicu.postgres.entities.SaJaundice;
//import org.bouncycastle.util.Times;

public class HqlSqlQueryConstants {

	public final static String AntenatalSteroidDetail = "select obj from AntenatalSteroidDetail as obj ";

	public final static String ChestTubeProcedure = "select obj from ProcedureChesttube as obj ";

	public final static String ExchangeTransfusionProcedure = "select obj from ProcedureExchangeTransfusion as obj ";

	public final static String PeritonealDialysisProcedure = "select obj from PeritonealDialysis as obj ";

	public final static String LumbarPunctureProcedure = "select obj from LumbarPuncture as obj ";

	public final static String EtIntubationReason = "select obj from EtIntubationReason as obj ";

	public final static String ENAdditiveBrandName = "select obj from RefEnAddtivesBrand as obj ";

	// Jaundice
	public static final String getJaundiceList(String uhid, int episodeNumber) {
		return "select obj from SaJaundice as obj where uhid='" + uhid + "' and episode_number =" + episodeNumber;
	}
	
	public static final String getShockList(String uhid, int episodeNumber) {
		return "select obj from SaShock as obj where uhid='" + uhid + "' and episode_number =" + episodeNumber;
	}


	public static final String getOldInactiveJaundiceList(String uhid) {
		return "select obj from SaJaundice as obj where uhid='" + uhid + "' and jaundicestatus ='Inactive'";
	}
	
	public static final String getOldInactiveShockList(String uhid) {
		return "select obj from SaShock as obj where uhid='" + uhid + "' and shockStatus ='Inactive'";
	}

	// Feed Intolerance
	public static final String getFeedIntoleranceList(String uhid, int episodeNumber) {
		return "select obj from SaFeedIntolerance as obj where uhid='" + uhid + "' and episode_number ="
				+ episodeNumber;
	}

	public static final String getOldInactiveFeedIntoleranceList(String uhid) {
		return "select obj from SaFeedIntolerance as obj where uhid='" + uhid
				+ "' and feed_intolerance_status ='Inactive'";
	}

	// Hypoglycemia
	public static final String getHypoglycemiaList(String uhid, int episodeNumber) {
		return "select obj from SaHypoglycemia as obj where uhid='" + uhid + "' and episode_number =" + episodeNumber;
	}

	public static final String getOldInactiveHypoglycemiaList(String uhid) {
		return "select obj from SaHypoglycemia as obj where uhid='" + uhid
				+ "' and hypoglycemiaEventStatus ='inactive'";
	}

	// Renal
	public static final String getOldInactiveRenalList(String uhid) {
		return " Select obj from SaRenalfailure as obj where uhid='" + uhid + "' and renalstatus = 'Inactive'";
	}

	public static final String getRenalList(String uhid, int episodeNumber) {
		return "select obj from SaRenalfailure as obj where uhid='" + uhid + "' and episode_number =" + episodeNumber
				+ " order by creationtime desc";
	}

	// Sepsis
	public static final String getSepsisList(String uhid, int episodeNumber) {
		return "select obj from SaSepsis as obj where uhid='" + uhid + "' and episode_number =" + episodeNumber
				+ " order by assessmentTime";
	}
	
	// Sepsis
		public static final String getNecList(String uhid, int episodeNumber) {
			return "select obj from SaNec as obj where uhid='" + uhid + "' and episode_number =" + episodeNumber
					+ " order by assessmentTime";
		}

	public static final String getOldInactiveSepsisList(String uhid) {
		return "select obj from SaSepsis as obj where uhid='" + uhid + "' and eventstatus ='inactive' "
				+ " order by assessmentTime";
	}

	// nec
	public static final String getOldInactiveNecList(String uhid) {
		return "select obj from SaNec as obj where uhid='" + uhid + "' and eventstatus ='inactive' ";
	}

	// Rds
	public static final String getRdsList(String uhid, int episodeNumber) {
		return "select obj from SaRespRds as obj where uhid='" + uhid + "' and episode_number =" + episodeNumber;
	}

	public static final String getOldInactiveRdsList(String uhid) {
		return "select obj from SaRespRds as obj where uhid='" + uhid + "' and eventstatus ='Inactive' ";
	}

	// Apnea
	public static final String getApneaList(String uhid, int episodeNumber) {
		return "select obj from SaRespApnea as obj where uhid='" + uhid + "' and episode_number =" + episodeNumber;
	}

	public static final String getOldInactiveApneaList(String uhid) {
		return "select obj from SaRespApnea as obj where uhid='" + uhid + "' and eventstatus ='Inactive' ";
	}

	// PPHN
	public static final String getPPHNList(String uhid, int episodeNumber) {
		return "select obj from SaRespPphn as obj where uhid='" + uhid + "' and episode_number =" + episodeNumber;
	}

	public static final String getOldInactivePPHNList(String uhid) {
		return "select obj from SaRespPphn as obj where uhid='" + uhid + "' and eventstatus ='Inactive' ";
	}

	// Pneumothorax
	public static final String getPneumothoraxList(String uhid, int episodeNumber) {
		return "select obj from SaRespPneumo as obj where uhid='" + uhid + "' and episode_number =" + episodeNumber;
	}

	public static final String getOldInactivePneumothoraxList(String uhid) {
		return "select obj from SaRespPneumo as obj where uhid='" + uhid + "' and eventstatus ='Inactive' ";
	}

	// Asphyxia
	public static final String getInitialDetailAsphyxiaList(String uhid) {
		return "select obj from BirthToNicu as obj where uhid='" + uhid + "'";
	}

	public static final String getAsphyxiaList(String uhid, int episodeNumber) {
		return "select obj from SaCnsAsphyxia as obj where uhid='" + uhid + "' and episode_number =" + episodeNumber;
	}

	public static final String getOldInactiveAsphyxiaList(String uhid) {
		return "select obj from SaCnsAsphyxia as obj where uhid='" + uhid + "' and eventstatus ='inactive' ";
	}

	// Seizures
	public static final String getSeizuresList(String uhid, int episodeNumber) {
		return "select obj from SaCnsSeizures as obj where uhid='" + uhid + "' and episode_number =" + episodeNumber;
	}

	public static final String getOldInactiveSeizuresList(String uhid) {
		return "select obj from SaCnsSeizures as obj where uhid='" + uhid + "' and eventstatus ='inactive' ";
	}

	// vap //use this for inactive notes
	public static final String getOldInactiveVapList(String uhid) {
		return "select obj from SaInfectVap as obj where uhid='" + uhid + "' and eventstatus ='inactive' ";
	}
	
	public static final String getVapList(String uhid, int episodeNumber) {
		return "select obj from SaInfectVap as obj where uhid='" + uhid + "' and episode_number =" + episodeNumber;
	}

	// clabsi //use this for inactive notes
	public static final String getOldInactiveClabsiList(String uhid) {
		return "select obj from SaInfectClabsi as obj where uhid='" + uhid + "' and eventstatus ='inactive' ";
	}
	
	public static final String getClabsiList(String uhid, int episodeNumber) {
		return "select obj from SaInfectClabsi as obj where uhid='" + uhid + "' and episode_number =" + episodeNumber;
	}

	// intrauterine //use this for inactive notes
	public static final String getOldInactiveIntrauterineList(String uhid) {
		return "select obj from SaInfectIntrauterine as obj where uhid='" + uhid + "' and eventstatus ='inactive' ";
	}
	
	public static final String getIntrauterineList(String uhid, int episodeNumber) {
		return "select obj from SaInfectIntrauterine as obj where uhid='" + uhid + "' and episode_number =" + episodeNumber;
	}

	// SaCnsEncephalopathy 
	public static final String getOldInactiveEncephalopathyList(String uhid) {
		return "select obj from SaCnsEncephalopathy as obj where uhid='" + uhid + "' and eventstatus ='inactive' ";
	}
	public static final String getEncephalopathyList(String uhid, int episodeNumber) {
		return "select obj from SaCnsEncephalopathy as obj where uhid='" + uhid + "' and episode_number =" + episodeNumber;
	}


	// Neuromuscular Disorder
	public static final String getOldInactiveNeuromuscularDisorderList(String uhid) {
		return "select obj from SaCnsNeuromuscularDisorders as obj where uhid='" + uhid + "' and eventstatus ='inactive' ";
	}
	public static final String getNeuromuscularDisorderList(String uhid, int episodeNumber) {
		return "select obj from SaCnsNeuromuscularDisorders as obj where uhid='" + uhid + "' and episode_number =" + episodeNumber;
	}

	// ivh
	public static final String getOldInactiveIVHList(String uhid) {
		return "select obj from SaCnsIvh as obj where uhid='" + uhid + "' and eventstatus ='inactive' ";
	}
	
	public static final String getIVHList(String uhid, int episodeNumber) {
		return "select obj from SaCnsIvh as obj where uhid='" + uhid + "' and episode_number =" + episodeNumber;
	}
	// Hydrocephalus

	public static final String getOldInactiveHydrocephalusList(String uhid) {
		return "select obj from SaCnsHydrocephalus as obj where uhid='" + uhid + "' and eventstatus ='inactive' ";
	}
	
	public static final String getHydrocephalusList(String uhid, int episodeNumber) {
		return "select obj from SaCnsHydrocephalus as obj where uhid='" + uhid + "' and episode_number =" + episodeNumber;
	}
	
	/////////////////////////////////////

	public static final String getVitalDetailHelper(String uhid, Timestamp time) {
		return "select obj from NursingEpisode as obj where uhid='" + uhid + "' and creationtime >='" + time + "'";
	}

	// Miscellaneous
	public static final String getMiscellaneousList(String uhid) {
		return "select obj from SaMiscellaneous as obj where uhid='" + uhid + "' and miscellaneousstatus != 'inactive'";
	}

	// Miscellaneous2
	public static final String getMiscellaneous2List(String uhid) {
		return "select obj from SaMiscellaneous2 as obj where uhid='" + uhid
				+ "' and miscellaneousstatus != 'inactive'";
	}

	public static final String getOldMiscellaneousList(String uhid) {
		return "select obj from SaMiscellaneous as obj where uhid='" + uhid + "' and miscellaneousstatus = 'inactive'";
	}

	// Medication List for Discharge Summary
	public static final String getMedicationListForDischargeSummary(String uhid) {
		return "select obj from BabyPrescription as obj where uhid='" + uhid
				+ "' and isactive = 'true' and eventname != 'Stable Notes'";
	}

	// Medication List for Advice on Discharge Summary
	public static final String getMedicationListForAdviceOnDischargeSummary(String uhid) {
		return "select obj from BabyPrescription as obj where uhid='" + uhid
				+ "' and isactive = 'true' and eventname != 'Stable Notes' and is_continue != 'true'";
	}

	public static final String getPeripheralList(String uhid) {
		return "select obj from PeripheralCannula as obj where uhid='" + uhid + "'";
	}

	public static final String getCurrentPeripheralList(String uhid) {
		return "select obj from PeripheralCannula as obj where uhid='" + uhid + "' and removal_timestamp is null";
	}

	public static final String getCentralLineList(String uhid) {
		return "select obj from CentralLine as obj where uhid='" + uhid + "'";
	}

	public static final String getCurrentCentralLineList(String uhid) {
		return "select obj from CentralLine as obj where uhid='" + uhid + "' and removal_timestamp is null";
	}

	public static final String getPeritonealDialysisList(String uhid) {
		return "select obj from PeritonealDialysis as obj where uhid='" + uhid + "'";
	}

	/*
	 * public static final String getCurrentPeritonealDialysisList(String uhid) {
	 * return "select obj from PeritonealDialysis as obj where uhid='" + uhid +
	 * "' and removal_timestamp is null"; }
	 */

	public static final String getLumbarPunctureList(String uhid) {
		return "select obj from LumbarPuncture as obj where uhid='" + uhid + "'";
	}

	public static final String getVtapList(String uhid) {
		return "select obj from Vtap as obj where uhid='" + uhid + "'";
	}

	public static final String getEtIntubationList(String uhid) {
		return "select obj from EtIntubation as obj where uhid='" + uhid + "' order by creationtime desc";
	}

	public static final String getCurrentEtIntubationList(String uhid) {
		return "select obj from EtIntubation as obj where uhid='" + uhid + "' order by creationtime desc";
	}

	public static final String getEtSuctionList(String uhid) {
		return "select obj from EtSuction as obj where uhid='" + uhid + "'";
	}

	public static final String getProcedureOtherList(String uhid) {
		return "select obj from ProcedureOther as obj where uhid='" + uhid + "'";
	}

	public static final String getProcedureOrderList(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "select obj from ProcedureOther as obj where uhid='" + uhid + "' and entrytime <= '" + toDate
				+ "' and entrytime >= '" + fromDate + "' order by entrytime desc";
	}

	public static final String getRefTestList() {
		return "SELECT testid, testname FROM ref_testslist";
	}

	public static final String getRefTestListByCategory() {
		return "select obj from RefTestslist as obj where not (obj.assesmentCategory like ('%None%')) order by assesmentCategory";
	}

	public static final String getSINBabyUHIDList(Timestamp fromDate, Timestamp endDate, String branchName) {
		return "SELECT distinct uhid FROM vw_baby_admission_discharge_detail where admissiontime <= '" + endDate
				+ "' and ((dischargeddate is null and admissionstatus is true) or dischargeddate > '" + fromDate + "') "
				+ " and branchname = '" + branchName + "'";
	}

	public static final String getSINBabyDataList(Timestamp fromDate, Timestamp endDate, String branchName) {
		return "SELECT distinct uhid, babyname FROM vw_baby_admission_discharge_detail where admissiontime <= '"
				+ endDate + "' and ((dischargeddate is null and admissionstatus is true) or dischargeddate > '"
				+ fromDate + "') " + " and branchname = '" + branchName + "'";
	}

	public static final String getSINantibiticsIVList(String uhidStr, Timestamp fromDate, Timestamp endDate) {
		return "SELECT distinct b.uhid,b.babyname FROM baby_prescription p INNER JOIN baby_detail b on p.uhid=b.uhid" +
				" where p.route ='IV' and p.medicationtype='TYPE0001' and p.startdate < '" + endDate + "' and (p.enddate is null or p.enddate >= '" + fromDate +"') " +
				"and p.uhid in (" + uhidStr + ")";
	}

	public static final String getSINnonInvasiveVentilatorList(String uhidStr, Timestamp fromDate, Timestamp endDate) {
		return "SELECT distinct b.uhid,b.babyname FROM vw_respiratory_usage_final p INNER JOIN baby_detail b on p.uhid=b.uhid" +
				" where p.isactive is true and p.rs_vent_type not in ('HFO','Mechanical Ventilation') and p.creationtime < '" + endDate + "' " +
				"and (p.endtime is null or p.endtime >= '" + fromDate + "') and p.uhid in ("+uhidStr+")";
	}

	public static final String getSINAllVentilatorList(String uhidStr, Timestamp fromDate, Timestamp endDate) {
		return "SELECT distinct b.uhid,b.babyname FROM vw_respiratory_usage_final p INNER JOIN baby_detail b on p.uhid=b.uhid" +
				" where p.isactive is true and p.creationtime < '" + endDate + "' and (p.endtime is null or p.endtime >= '" + fromDate + "')" +
				"and p.uhid in (" + uhidStr + ")";
	}

	public static final String getSINCentralLineList(String uhidStr, Timestamp fromDate, Timestamp endDate) {
		return "SELECT distinct b.uhid,b.babyname FROM central_line p INNER JOIN baby_detail b on p.uhid=b.uhid" +
				" where p.insertion_timestamp < '" + endDate + "' and (p.removal_timestamp is null or p.removal_timestamp >= '" + fromDate+"') " +
				"and p.uhid in (" + uhidStr + ")";
	}

	public static final String getSINCannulaList(String uhidStr, Timestamp fromDate, Timestamp endDate) {
		return "SELECT distinct b.uhid,b.babyname FROM peripheral_cannula p INNER JOIN baby_detail b on p.uhid=b.uhid" +
				" where p.insertion_timestamp < '" + endDate + "' and (p.removal_timestamp is null or p.removal_timestamp >= '" + fromDate + "')" +
				" and p.uhid in (" + uhidStr + ")";
	}

	public static final String getSINPnFeedList(String uhidStr, Timestamp fromDate, Timestamp endDate) {
		return "SELECT distinct b.uhid,b.babyname FROM babyfeed_detail p INNER JOIN baby_detail b on p.uhid=b.uhid" +
				" where p.isparentalgiven is TRUE and p.entrydatetime between '" + fromDate + "' and '" + endDate + "' " +
				"and p.uhid in (" + uhidStr + ")";
	}

	public static final String getSINIVFeedList(String uhidStr, Timestamp fromDate, Timestamp endDate) {
		return "SELECT distinct b.uhid,b.babyname FROM baby_prescription p INNER JOIN baby_detail b on p.uhid=b.uhid where p.route ='IV' and p.startdate < '"
				+ endDate + "' and (p.enddate is null or p.enddate >= '" + fromDate + "') and p.uhid in (" + uhidStr + ")"
				+ "UNION " +
				"SELECT distinct b.uhid,b.babyname FROM babyfeed_detail p INNER JOIN baby_detail b on p.uhid=b.uhid where p.isparentalgiven is TRUE and p.entrydatetime between '"+ fromDate + "' and '" + endDate +
				"' and p.uhid in (" + uhidStr + ")"
				+ "UNION " +
				"SELECT distinct b.uhid,b.babyname FROM doctor_blood_products bp INNER JOIN baby_detail b on bp.uhid=b.uhid where bp.isincludeinpn is TRUE and bp.assessment_time between '"
				+ fromDate + "' and '" + endDate + "' and bp.uhid in (" + uhidStr + ")"
				+ "UNION " + 
				"SELECT distinct b.uhid,b.babyname FROM central_line c INNER JOIN baby_detail b on c.uhid=b.uhid where c.isincludeinpn is TRUE and c.insertion_timestamp < '"
				+ endDate + "' and (c.removal_timestamp is null or c.removal_timestamp >= '" + fromDate + "') and c.uhid in (" + uhidStr + ")";
	}

	public static final String getSINDataList(String uhidStr) {
		return "SELECT obj from BabyDetail as obj where uhid in (" + uhidStr + ")";
	}



	public static final String getQIDrNurseCountList(Timestamp endDate, String branchName) {
		return "SELECT r.rolename, count(u.username) FROM users_roles u JOIN role r on r.roleid = u.roleid where u.creationtime < '"
				+ endDate + "' and u.username IN (select username from inicuuser where active = 1 and branchname = '"
				+ branchName + "' ) group by r.rolename";
	}

	public static final String getNurseTasksComments(Timestamp fromDate, Timestamp toDate, String uhid) {
		return "SELECT obj FROM NurseTasks obj where creationtime >= '" + fromDate +"' and creationtime <= '"+  toDate +
				"' and nurse_comments is not null and nurse_comments !='' and uhid = '" +uhid+ "' order by creationtime";
	}

	public static final String getQIBabyUHIDList(Timestamp fromDate, Timestamp toDate, String branchName) {
		return "SELECT distinct uhid FROM baby_detail where dateofadmission <= '" + toDate
				+ "' and ((dischargeddate is null and admissionstatus is true) or dischargeddate >= '" + fromDate
				+ "') and activestatus = true and branchname = '" + branchName + "'";
	}

	public static final String getQIBabyIPOPCountList(String uhidStr) {
		return "SELECT count(distinct uhid), inout_patient_status FROM baby_detail where uhid in (" + uhidStr
				+ ") group by inout_patient_status";
	}

	public static final String getQIMeanGestationWeightList(String uhidStr, Timestamp fromDate, Timestamp endDate) {
		return "Select round((avg(gestationweekbylmp * 7 + gestationdaysbylmp))/7, 2) as mean_gestweek, avg(birthweight)"
				+ " as mean_weight FROM baby_detail where uhid in (" + uhidStr + ")";
	}

	public static final String getPatientDurationList(String uhidStr, Timestamp fromDate, Timestamp endDate) {
		return "SELECT avg(v.stay_duration) FROM vw_baby_stay_duration v JOIN (SELECT uhid,"
				+ " episodeid FROM baby_detail where dateofadmission <= '" + endDate
				+ "' and ((dischargeddate is null and admissionstatus is true) or dischargeddate >= '" + fromDate
				+ "') and activestatus = true) b on b.uhid = v.uhid and b.episodeid = v.episodeid where b.uhid in ("
				+ uhidStr + ")";
	}

	public static final String getQIReadmissionRateList(String uhidStr, Timestamp fromDate, Timestamp endDate) {
		return "SELECT round((100 * CAST ((count(uhid) - count(distinct uhid)) AS numeric))/count(distinct uhid), 2) FROM baby_detail where uhid in ("
				+ uhidStr + ")";
	}

	public static final String getQIBreastCountList(String uhidStr, Timestamp fromDate, Timestamp endDate) {
		return "SELECT count(distinct uhid) FROM babyfeed_detail where creationtime between '" + fromDate + "' and '"
				+ endDate + "' and feedmethod like '%METHOD03%' and uhid in (" + uhidStr + ")";
	}

	public static final String getQISepsisCountList(String uhidStr) {
		return "SELECT distinct uhid FROM vw_sepsis_earlyonset_final where uhid in (" + uhidStr + ")";
	}

	public static final String getQISepsisList(String uhidStr) {
		return "select obj from SaSepsis obj where uhid in (" + uhidStr + ") order by uhid, assessmentTime";
	}

	public static final String getQIClabsiCountList(String uhidStr) {
		return "SELECT count(distinct uhid) from sa_infection_clabsi where lower(eventstatus) = 'yes' and uhid in ("
				+ uhidStr + ")";
	}

	public static final String getQICentralLineDurationList(String uhidStr, Timestamp fromDate, Timestamp endDate) {
		return "SELECT SUM(CEIL((CASE WHEN removal_timestamp is NULL THEN CASE WHEN insertion_timestamp < '" + fromDate
				+ "' THEN EXTRACT(EPOCH FROM (to_timestamp('" + endDate
				+ "', 'yyyy-MM-dd HH24:MI:SS.s') - to_timestamp('" + fromDate
				+ "', 'yyyy-MM-dd HH24:MI:SS.s'))) ELSE EXTRACT(EPOCH FROM ('" + endDate
				+ "' - insertion_timestamp)) END ELSE CASE WHEN insertion_timestamp < '" + fromDate
				+ "' THEN EXTRACT(EPOCH FROM (removal_timestamp - '" + fromDate
				+ "')) ELSE EXTRACT(EPOCH FROM (removal_timestamp - insertion_timestamp)) END END) / 86400))"
				+ " FROM central_line where uhid in (" + uhidStr + ")";
	}

	public static final String getQIVentilatorUsageList(String uhidStr, Timestamp fromDate, Timestamp endDate) {
		return "SELECT sum(differenceinhrs) FROM vw_respiratory_usage_final where isactive is true and eventname is not null and creationtime < '"
				+ endDate + "' and (endtime is null or endtime >= '" + fromDate + "') and uhid in (" + uhidStr + ")";
	}

	public static final String getQIAntibioticsUsageList(String uhidStr, Timestamp fromDate, Timestamp endDate) {
		return "SELECT sum(antibiotic_duration) FROM vw_antibiotic_duration where medicationtype='TYPE0001' and startdate < '"
				+ endDate + "' and (enddate is null or enddate >= '" + fromDate + "') and uhid in (" + uhidStr + ")";
	}

	public static final String getQIMortalUhidList(Timestamp fromDate, Timestamp toDate, String branchName) {
		return "SELECT distinct b.uhid FROM baby_detail b JOIN discharge_outcome d on b.uhid = d.uhid where b.dateofadmission <= '"
				+ toDate + "' and b.dischargeddate >= '" + fromDate
				+ "' and b.activestatus = true and d.outcome_type = 'Death' and b.branchname = '" + branchName + "'";
	}

	public static final String getQIMortalIPOPCountList(String mortalUhidStr) {
		return "SELECT count(distinct uhid), inout_patient_status FROM baby_detail where uhid in (" + mortalUhidStr
				+ ") group by inout_patient_status";
	}

	public static final String getQIMortalSepsisList(String mortalUhidStr) {
		return "SELECT count(distinct uhid) from sa_infection_sepsis where uhid in (" + mortalUhidStr
				+ ") and lower(eventstatus) = 'yes'";
	}

	public static final String getQIMortalBPDList(String mortalUhidStr) {
		return "SELECT count(distinct uhid) from sa_resp_bpd where uhid in (" + mortalUhidStr
				+ ") and lower(eventstatus) = 'yes'";
	}

	public static final String getQIMortalIVHList(String mortalUhidStr) {
		return "SELECT count(distinct uhid) from sa_cns_ivh where uhid in (" + mortalUhidStr
				+ ") and lower(eventstatus) = 'yes'";
	}

	public static final String getQIBabyUHIDCountList(String uhidListStr, Timestamp fromDate, Timestamp toDate) {
		return "SELECT count(distinct uhid) FROM baby_detail where dateofadmission <= '" + toDate
				+ "' and ((dischargeddate is null and admissionstatus is true) or dischargeddate >= '" + fromDate
				+ "') and activestatus = true and uhid in (" + uhidListStr + ")";
	}

	public static final String getQIBabyBedCountList(Timestamp fromDate, Timestamp toDate, String branchName) {
		return "SELECT count(bedid) FROM ref_bed where creationtimestamp <= '" + toDate
				+ "' and (isactive = true or removaltimestamp >= '" + fromDate + "') and branchname = '" + branchName
				+ "'";
	}

	public static final String getDischargeBabyAssessmentStatus(String uhid) {
		
		
		return "SELECT 'Jaundice' as event, jaundicestatus FROM sa_jaundice where lower(jaundicestatus) != 'inactive'"
		        + " and assessment_time = (SELECT max(assessment_time) as max_time FROM sa_jaundice where uhid='" + uhid
		        + "') and sajaundiceid = (select max(sajaundiceid) from sa_jaundice where uhid ='" + uhid
		        + "') and uhid='" + uhid
		        + "' UNION SELECT a.event, a.eventstatus FROM vw_assesment_respsystem_final a JOIN "
		        + "(SELECT max(creationtime) as max_time, event, max(id) as id FROM vw_assesment_respsystem_final where uhid='"
		        + uhid
		        + "' group by event) b on a.creationtime = b.max_time and a.event = b.event and a.id = b.id where "
		        + "lower(a.eventstatus) != 'inactive' and uhid='" + uhid
		        + "' UNION SELECT a.event, a.eventstatus FROM vw_assesment_cns_final a"
		        + " JOIN (SELECT max(creationtime) as max_time, event, max(id) as id FROM vw_assesment_cns_final where uhid='"
		        + uhid
		        + "' group by event) b on a.creationtime = b.max_time and a.event = b.event and a.id = b.id where "
		        + "lower(a.eventstatus) != 'inactive' and uhid='" + uhid
		        + "' UNION SELECT a.event, a.eventstatus FROM vw_assesment_infection_final a  "
		        + "JOIN (SELECT max(creationtime) as max_time, event,max(id) as id FROM vw_assesment_infection_final where uhid='"
		        + uhid
		        + "' group by event) b on a.creationtime = b.max_time and a.event = b.event and a.id=b.id where lower(a.eventstatus) != 'inactive' and uhid='"
		        + uhid
		        + "' UNION SELECT  'Renal' as event, renal_status FROM sa_renalfailure where lower(renal_status) != 'inactive' and assessment_time = (SELECT max(assessment_time) as max_time FROM"
		        + " sa_renalfailure where uhid='" + uhid
		        + "')  and renalid = ( select max(renalid) from sa_renalfailure where uhid ='" + uhid + "') and uhid='"
		        + uhid
		        + "' UNION SELECT  'FeedIntolerance' as event, feed_intolerance_status FROM sa_feed_intolerance where lower(feed_intolerance_status) != 'inactive' and"
		        + " assessment_time = (SELECT max(assessment_time) as max_time FROM"
		        + " sa_feed_intolerance where uhid='" + uhid
		        + "')  and feedintoleranceid = ( select max(feedintoleranceid) from sa_feed_intolerance where uhid ='"
		        + uhid + "') and uhid='" + uhid
		        + "' UNION SELECT a.event, a.eventstatus FROM vw_assesment_metabolic_final a"
		        + " JOIN (SELECT max(creationtime) as max_time, event, max(id) as id FROM vw_assesment_metabolic_final where uhid='"
		        + uhid 
		        + "' group by event) b on a.creationtime = b.max_time and a.event = b.event and a.id = b.id where "
		        + "lower(a.eventstatus) != 'inactive' and uhid='" + uhid
		        + "'UNION SELECT 'Miscellaneous' as event, miscellaneousstatus FROM sa_miscellaneous where lower(miscellaneousstatus) != 'inactive'" + 
		        " and assessment_time = (SELECT max(assessment_time) as max_time FROM sa_miscellaneous where uhid='" + uhid +  
		        "') and sa_miscellaneous_id = (select max(sa_miscellaneous_id) from sa_miscellaneous where uhid ='" + uhid  + 
		        "') and uhid='" + uhid
		        + "'UNION SELECT 'Miscellaneous' as event, miscellaneousstatus FROM sa_miscellaneous_2 where lower(miscellaneousstatus) != 'inactive'" + 
		        " and assessment_time = (SELECT max(assessment_time) as max_time FROM sa_miscellaneous_2 where uhid='" + uhid +  
		        "') and sa_miscellaneous_2_id = (select max(sa_miscellaneous_2_id) from sa_miscellaneous_2 where uhid ='" + uhid  + 
		        "') and uhid='" + uhid + "' "
		        + " UNION SELECT 'Pain' as event, pain_status FROM sa_pain where lower(pain_status) != 'inactive'" + 
		        " and assessment_time = (SELECT max(assessment_time) as max_time FROM sa_pain where uhid='" + uhid +  
		        "') and painid = (select max(painid) from sa_pain where uhid ='" + uhid + "') and uhid='" + uhid + "'"
		        + " UNION SELECT 'Shock' as event, shockstatus FROM sa_shock where lower(shockstatus) != 'inactive'" + 
		        " and assessment_time = (SELECT max(assessment_time) as max_time FROM sa_shock where uhid='" + uhid +  
		        "') and sashockid = (select max(sashockid) from sa_shock where uhid ='" + uhid + "') and uhid='" + uhid + "'";
		
		
	
	}



	public static final String getActiveAssessmentStatus(String uhid) {


		return "SELECT 'Jaundice' as event, jaundicestatus FROM sa_jaundice where lower(jaundicestatus) = 'active'"
				+ " and assessment_time = (SELECT max(assessment_time) as max_time FROM sa_jaundice where uhid='" + uhid
				+ "') and sajaundiceid = (select max(sajaundiceid) from sa_jaundice where uhid ='" + uhid
				+ "') and uhid='" + uhid
				+ "' UNION SELECT a.event, a.eventstatus FROM vw_assesment_respsystem_final a JOIN "
				+ "(SELECT max(creationtime) as max_time, event, max(id) as id FROM vw_assesment_respsystem_final where uhid='"
				+ uhid
				+ "' group by event) b on a.creationtime = b.max_time and a.event = b.event and a.id = b.id where "
				+ "lower(a.eventstatus) = 'active' and uhid='" + uhid
				+ "' UNION SELECT a.event, a.eventstatus FROM vw_assesment_cns_final a"
				+ " JOIN (SELECT max(creationtime) as max_time, event, max(id) as id FROM vw_assesment_cns_final where uhid='"
				+ uhid
				+ "' group by event) b on a.creationtime = b.max_time and a.event = b.event and a.id = b.id where "
				+ "lower(a.eventstatus) = 'active' and uhid='" + uhid
				+ "' UNION SELECT a.event, a.eventstatus FROM vw_assesment_infection_final a  "
				+ "JOIN (SELECT max(creationtime) as max_time, event,max(id) as id FROM vw_assesment_infection_final where uhid='"
				+ uhid
				+ "' group by event) b on a.creationtime = b.max_time and a.event = b.event and a.id=b.id where lower(a.eventstatus) = 'active' and uhid='"
				+ uhid
				+ "' UNION SELECT  'Renal' as event, renal_status FROM sa_renalfailure where lower(renal_status) = 'active' and assessment_time = (SELECT max(assessment_time) as max_time FROM"
				+ " sa_renalfailure where uhid='" + uhid
				+ "')  and renalid = ( select max(renalid) from sa_renalfailure where uhid ='" + uhid + "') and uhid='"
				+ uhid
				+ "' UNION SELECT  'FeedIntolerance' as event, feed_intolerance_status FROM sa_feed_intolerance where lower(feed_intolerance_status) = 'yes' and"
				+ " assessment_time = (SELECT max(assessment_time) as max_time FROM"
				+ " sa_feed_intolerance where uhid='" + uhid
				+ "')  and feedintoleranceid = ( select max(feedintoleranceid) from sa_feed_intolerance where uhid ='"
				+ uhid + "') and uhid='" + uhid
				+ "' UNION SELECT a.event, a.eventstatus FROM vw_assesment_metabolic_final a"
				+ " JOIN (SELECT max(creationtime) as max_time, event, max(id) as id FROM vw_assesment_metabolic_final where uhid='"
				+ uhid
				+ "' group by event) b on a.creationtime = b.max_time and a.event = b.event and a.id = b.id where "
				+ "lower(a.eventstatus) = 'active' and uhid='" + uhid
				+ "'UNION SELECT 'Miscellaneous' as event, miscellaneousstatus FROM sa_miscellaneous where lower(miscellaneousstatus) = 'yes'" +
				" and assessment_time = (SELECT max(assessment_time) as max_time FROM sa_miscellaneous where uhid='" + uhid +
				"') and sa_miscellaneous_id = (select max(sa_miscellaneous_id) from sa_miscellaneous where uhid ='" + uhid  +
				"') and uhid='" + uhid
				+ "'UNION SELECT 'Miscellaneous' as event, miscellaneousstatus FROM sa_miscellaneous_2 where lower(miscellaneousstatus) = 'yes'" +
				" and assessment_time = (SELECT max(assessment_time) as max_time FROM sa_miscellaneous_2 where uhid='" + uhid +
				"') and sa_miscellaneous_2_id = (select max(sa_miscellaneous_2_id) from sa_miscellaneous_2 where uhid ='" + uhid  +
				"') and uhid='" + uhid + "' "
				+ " UNION SELECT 'Pain' as event, pain_status FROM sa_pain where lower(pain_status) = 'active'" +
				" and assessment_time = (SELECT max(assessment_time) as max_time FROM sa_pain where uhid='" + uhid +
				"') and painid = (select max(painid) from sa_pain where uhid ='" + uhid + "') and uhid='" + uhid + "'"
				+ " UNION SELECT 'Shock' as event, shockstatus FROM sa_shock where lower(shockstatus) = 'active'" + 
		        " and assessment_time = (SELECT max(assessment_time) as max_time FROM sa_shock where uhid='" + uhid +  
		        "') and sashockid = (select max(sashockid) from sa_shock where uhid ='" + uhid + "') and uhid='" + uhid + "'";
	



	}



	public static final String getPassiveAssessmentStatus(String uhid) {


		return "SELECT 'Jaundice' as event, jaundicestatus FROM sa_jaundice where lower(jaundicestatus) = 'no'"
				+ " and assessment_time = (SELECT max(assessment_time) as max_time FROM sa_jaundice where uhid='" + uhid
				+ "') and sajaundiceid = (select max(sajaundiceid) from sa_jaundice where uhid ='" + uhid
				+ "') and uhid='" + uhid
				+ "' UNION SELECT a.event, a.eventstatus FROM vw_assesment_respsystem_final a JOIN "
				+ "(SELECT max(creationtime) as max_time, event, max(id) as id FROM vw_assesment_respsystem_final where uhid='"
				+ uhid
				+ "' group by event) b on a.creationtime = b.max_time and a.event = b.event and a.id = b.id where "
				+ "lower(a.eventstatus) = 'passive' and uhid='" + uhid
				+ "' UNION SELECT a.event, a.eventstatus FROM vw_assesment_cns_final a"
				+ " JOIN (SELECT max(creationtime) as max_time, event, max(id) as id FROM vw_assesment_cns_final where uhid='"
				+ uhid
				+ "' group by event) b on a.creationtime = b.max_time and a.event = b.event and a.id = b.id where "
				+ "lower(a.eventstatus) = 'passive' and uhid='" + uhid
				+ "' UNION SELECT a.event, a.eventstatus FROM vw_assesment_infection_final a  "
				+ "JOIN (SELECT max(creationtime) as max_time, event,max(id) as id FROM vw_assesment_infection_final where uhid='"
				+ uhid
				+ "' group by event) b on a.creationtime = b.max_time and a.event = b.event and a.id=b.id where lower(a.eventstatus) = 'passive' and uhid='"
				+ uhid
				+ "' UNION SELECT  'Renal' as event, renal_status FROM sa_renalfailure where lower(renal_status) = 'passive' and assessment_time = (SELECT max(assessment_time) as max_time FROM"
				+ " sa_renalfailure where uhid='" + uhid
				+ "')  and renalid = ( select max(renalid) from sa_renalfailure where uhid ='" + uhid + "') and uhid='"
				+ uhid
				+ "' UNION SELECT  'FeedIntolerance' as event, feed_intolerance_status FROM sa_feed_intolerance where lower(feed_intolerance_status) = 'no' and"
				+ " assessment_time = (SELECT max(assessment_time) as max_time FROM"
				+ " sa_feed_intolerance where uhid='" + uhid
				+ "')  and feedintoleranceid = ( select max(feedintoleranceid) from sa_feed_intolerance where uhid ='"
				+ uhid + "') and uhid='" + uhid
				+ "' UNION SELECT a.event, a.eventstatus FROM vw_assesment_metabolic_final a"
				+ " JOIN (SELECT max(creationtime) as max_time, event, max(id) as id FROM vw_assesment_metabolic_final where uhid='"
				+ uhid
				+ "' group by event) b on a.creationtime = b.max_time and a.event = b.event and a.id = b.id where "
				+ "lower(a.eventstatus) = 'passive' and uhid='" + uhid
				+ "'UNION SELECT 'Miscellaneous' as event, miscellaneousstatus FROM sa_miscellaneous where lower(miscellaneousstatus) = 'passive'" +
				" and assessment_time = (SELECT max(assessment_time) as max_time FROM sa_miscellaneous where uhid='" + uhid +
				"') and sa_miscellaneous_id = (select max(sa_miscellaneous_id) from sa_miscellaneous where uhid ='" + uhid  +
				"') and uhid='" + uhid
				+ "'UNION SELECT 'Miscellaneous' as event, miscellaneousstatus FROM sa_miscellaneous_2 where lower(miscellaneousstatus) = 'passive'" +
				" and assessment_time = (SELECT max(assessment_time) as max_time FROM sa_miscellaneous_2 where uhid='" + uhid +
				"') and sa_miscellaneous_2_id = (select max(sa_miscellaneous_2_id) from sa_miscellaneous_2 where uhid ='" + uhid  +
				"') and uhid='" + uhid + "' "
				+ " UNION SELECT 'Pain' as event, pain_status FROM sa_pain where lower(pain_status) = 'passive'" +
				" and assessment_time = (SELECT max(assessment_time) as max_time FROM sa_pain where uhid='" + uhid +
				"') and painid = (select max(painid) from sa_pain where uhid ='" + uhid + "') and uhid='" + uhid + "'";
	}

	public static final String getNursingVitalList(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT obj FROM NursingVitalparameter obj where uhid = '" + uhid + "' and entryDate >= '" + fromDate
				+ "' and entryDate <= '" + toDate + "' order by entryDate";
	}

	public static final String getAnthropometryList(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT obj FROM BabyVisit obj where uhid = '" + uhid + "' and creationtime >= '" + fromDate
				+ "' and creationtime <= '" + toDate + "' order by creationtime desc";
	}

	public static final String getNursingEventList(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT obj FROM NursingEpisode obj where uhid = '" + uhid + "' and creationtime >= '" + fromDate
				+ "' and creationtime <= '" + toDate + "' order by creationtime desc";
	}

	public static final String getNursingBloodGasVentilatorList(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT v FROM NursingBloodGasVentilator v where uhid='" + uhid + "' and entrydate >= '" + fromDate
				+ "' and entrydate <= '" + toDate + "' order by entrydate desc";
	}

	public static final String getNursingVentilatorList(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT obj FROM NursingVentilaor obj where ventmode is not null and uhid = '" + uhid
				+ "' and entryDate >= '" + fromDate + "' and entryDate <= '" + toDate + "' order by entryDate desc";
	}

	public static final String getNursingVentilatorPrintList(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT obj FROM NursingVentilaor obj where uhid = '" + uhid + "' and entryDate >= '" + fromDate
				+ "' and entryDate <= '" + toDate + "' order by entryDate DESC";
	}

	public static final String getNursingBGList(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT obj FROM NursingBloodGas obj where uhid = '" + uhid + "' and entryDate >= '" + fromDate
				+ "' and entryDate <= '" + toDate + "' order by entryDate desc";
	}

	public static final String getNursingBloodGasVentList(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT obj FROM NursingBloodGas obj where uhid = '" + uhid + "' and entryDate >= '" + fromDate
				+ "' and entryDate <= '" + toDate + "' UNION SELECT obj FROM NursingVentilaor obj where uhid = '" + uhid
				+ "' and entryDate >= '" + fromDate + "' and entryDate <= '" + toDate + "' order by entryDate";
	}

	public static final String getNursingIntakeOutputList(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT obj FROM NursingIntakeOutput obj where uhid = '" + uhid + "' and entry_timestamp >= '" + fromDate
				+ "' and entry_timestamp <= '" + toDate + "' order by entry_timestamp desc, creationtime desc";
	}

	public static final String getBabyFeedList(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT obj FROM BabyfeedDetail obj where uhid = '" + uhid + "' and entrydatetime >= '" + fromDate
				+ "' and entrydatetime <= '" + toDate + "' order by entrydatetime desc, creationtime desc";
	}

	public static final String getNursingIntakeOutputListFromId(String uhid, String id) {
		return "SELECT obj FROM NursingIntakeOutput obj where uhid = '" + uhid + "' and babyfeedid = '" + id
				+ "' order by entry_timestamp desc, creationtime desc";
	}

	public static final String getNursingHeplockList(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT obj FROM NursingHeplock obj where uhid = '" + uhid + "' and execution_time >= '" + fromDate
				+ "' and execution_time <= '" + toDate + "' and flag is null order by execution_time desc";
	}

	public static final String getNursingBloodProductList(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT obj FROM NursingBloodproduct obj where uhid = '" + uhid + "' and flag is null and execution_time >= '" + fromDate
				+ "' and execution_time <= '" + toDate + "' order by execution_time desc";
	}


	public static final String getNursingBloodProductPendingList(String uhid) {
		return "SELECT obj FROM NursingBloodproduct obj where uhid = '" + uhid + "' and flag is null and execution_time is null order by execution_time desc";
	}

	public static final String getNursingMedicationList(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT obj FROM NursingMedication obj where uhid = '" + uhid + "' and flag is null and given_time >= '" + fromDate
				+ "' and given_time <= '" + toDate + "' order by given_time";
	}

	public static final String getGlycemiaEventsCountList(String uhid) {
		return "SELECT count(hypoglycemia) as hypo, count(hyperglycemia) as hyper FROM nursing_vitalparameters where uhid='"
				+ uhid + "'";
	}

	public static final String getNursingEventsCountList(String uhid) {
		return "SELECT count(apnea) as apnea, count(bradycardia) as bradycardia, count(disaturation) as disaturation,"
				+ " count(seizures) as seizures, count(tachycardia) as tachycardia FROM nursing_episode where uhid='"
				+ uhid + "'";
	}

	public static final String getqueryAlarmsValueList(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT max(t.hr_alarm_hi) as hr_alarm_hi,max(t.spo2_alarm_hi) as spo2_alarm_hi,min(t.hr_alarm_lo) as hr_alarm_lo,min(t.spo2_alarm_lo) as spo2_alarm_lo from "
				+ BasicConstants.SCHEMA_NAME + ".stable_notes as t where t.uhid = '" + uhid + "' and t.entrytime >= '"
				+ fromDate + "' and t.entrytime <= '" + toDate + "'";
	}

	public static final String getotherParametersList(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT obj FROM StableNote obj where uhid = '" + uhid + "' and entrytime >= '" + fromDate
				+ "' and entrytime <= '" + toDate + "' order by entrytime desc";
	}

	public static final String getUsageBabyListWithdoa(Timestamp fromDate, Timestamp toDate, String branchName) {
		return "SELECT distinct uhid,episodeid,dateofadmission FROM baby_detail b where dateofadmission <= '" + toDate
				+ "' and ((dischargeddate is null and admissionstatus = 'true') or dischargeddate >= '" + fromDate
				+ "')and branchname = '" + branchName + "' order by dateofadmission desc";
	}


	public static final String getUsageBabyList(Timestamp fromDate, Timestamp toDate, String branchName) {
		return "SELECT obj FROM BabyDetail obj where dateofadmission <= '" + toDate
				+ "' and ((dischargeddate is null and admissionstatus = 'true') or dischargeddate >= '" + fromDate
				+ "')and branchname = '" + branchName + "' order by dateofadmission desc";
	}

//	public static final String getUsageBasicDetail(String uhid, String episodeid) {
//		return "SELECT b.uhid, b.babyname, rl.levelname AS niculevel, CASE WHEN b.admissionstatus=true THEN CASE WHEN b.isreadmitted=true THEN 'Readmit' ELSE 'Admit' END WHEN b.dischargestatus is null or "
//				+ "b.dischargestatus = '' THEN 'Discharge' ELSE b.dischargestatus END as admissionstatus,"
//				+ " v.stay_duration as lengthofstay, rc.levelname AS criticality, CASE WHEN b.isassessmentsubmit=true THEN 1 ELSE 0 END as initialassessment,"
//				+ " b.admittingdoctor as neonatologist, b.episodeid, b.dateofbirth, b.dateofadmission, b.birthweight, b.gestationweekbylmp, b.gestationdaysbylmp, b.gender, b.dischargeddate FROM baby_detail b left join vw_baby_stay_duration v on b.uhid = v.uhid and b.episodeid = v.episodeid"
//				+ " LEFT JOIN ref_level rl on b.niculevelno = rl.levelid LEFT JOIN ref_criticallevel rc on b.criticalitylevel = rc.crlevelid where b.uhid='"
//				+ uhid + "' and b.episodeid = '" + episodeid + "' order by creationtime desc";
//	}


	// This is my version
	public static final String getUsageBasicDetail(String uhid,String episodeid){
		return "SELECT b.uhid, b.babyname, rl.levelname AS niculevel, CASE WHEN b.admissionstatus=true THEN CASE WHEN b.isreadmitted=true THEN 'Readmit' ELSE 'Admit' END WHEN b.dischargestatus is null or "
				+ "b.dischargestatus = '' THEN 'Discharge' ELSE b.dischargestatus END as admissionstatus,"
				+ " v.stay_duration as lengthofstay, rc.levelname AS criticality, CASE WHEN b.isassessmentsubmit=true THEN 1 ELSE 0 END as initialassessment,"
				+ " b.admittingdoctor as neonatologist, b.episodeid, b.dateofbirth, b.dateofadmission, b.birthweight, b.gestationweekbylmp, b.gestationdaysbylmp, b.gender, b.dischargeddate, b.hisdischargestatus, b.hisdischargedate, b.admissionweight , b.timeofadmission , b.inout_patient_status FROM baby_detail b LEFT JOIN vw_baby_stay_duration v on b.uhid = v.uhid and b.episodeid = v.episodeid"
				+ " LEFT JOIN ref_level rl on b.niculevelno = rl.levelid LEFT JOIN ref_criticallevel rc on b.criticalitylevel = rc.crlevelid where b.uhid = '"
				+ uhid + "' and b.episodeid  = '" + episodeid + "' order by creationtime desc";
	}

//	public static final String getUsageAssessment(String uhid, Timestamp fromDate, Timestamp toDate) {
//		return "SELECT obj FROM VwDoctornotesListFinal obj where uhid='" + uhid + "' and creationtime >= '"
//				+ fromDate + "' and creationtime <= '" + toDate + "'";
//	}

	// My Version
	public static final String getUsageAssessment(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT obj FROM VwDoctornotesListFinal obj where uhid in (" + uhid + ") and creationtime >= '"
				+ fromDate + "' and creationtime <= '" + toDate + "'";
	}

//	public static final String getUsageAssessment(String uhid, Timestamp fromDate, Timestamp toDate) {
//		return "SELECT count(*) FROM vw_doctornotes_list_final where uhid='" + uhid + "' and creationtime >= '"
//				+ fromDate + "' and creationtime <= '" + toDate + "' and sa_event != 'Stable Notes'";
//	}

	public static final String getUsageStableAssessment(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT count(*) FROM vw_doctornotes_list_final where uhid='" + uhid + "' and creationtime >= '"
				+ fromDate + "' and creationtime <= '" + toDate + "' and sa_event = 'Stable Notes'";
	}

//	public static final String getUsageMedication(String uhid, Timestamp fromDate, Timestamp toDate) {
//		return "SELECT count(*) FROM baby_prescription where uhid in (" + uhid + ") and (enddate is null or enddate >= '"
//				+ fromDate + "') and startdate <= '" + toDate + "'";
//	}

//	My Version
public static final String getUsageMedication(String uhid, Timestamp fromDate, Timestamp toDate) {
	//return "SELECT count(*) as count,uhid FROM baby_prescription where uhid in (" + uhid + ") and (enddate is null or enddate >= '"
		//	+ fromDate + "') and startdate <= '" + toDate + "' group by uhid";

	return "SELECT count(*) as count,uhid FROM nursing_medication where uhid in (" + uhid + ") and given_time >= '"
			+ fromDate + "' and given_time <= '" + toDate + "' group by uhid";

}


//	public static final String getUsageNutrition(String uhid, Timestamp fromDate, Timestamp toDate) {
//		return "SELECT count(*) FROM babyfeed_detail where uhid='" + uhid + "' and entryDateTime >= '" + fromDate
//				+ "' and entryDateTime <= '" + toDate + "'";
//	}

	// my verison
	public static final String getUsageNutrition(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT count(*) as count,uhid FROM babyfeed_detail where uhid in (" + uhid + ") and entryDateTime >= '" + fromDate
				+ "' and entryDateTime <= '" + toDate + "' group by uhid";
	}

	public static final String getUsageLabOrder(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT count(*) as count,uhid FROM investigation_ordered where uhid in (" + uhid + ") and investigationorder_time >= '"
				+ fromDate + "' and investigationorder_time <= '" + toDate + "' group by uhid";
	}

	public static final String getUsageProcedure(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT count(*) as count,uhid FROM vw_procedures_usage where uhid in (" + uhid + ") and (endtime is null or endtime >= '"
				+ fromDate + "') and starttime <= '" + toDate + "' group by uhid";
	}

	public static final String getDeviceName(String uhidList, Timestamp fromDate,Timestamp toDate){
		return "SELECT upper(b.bboxname),a.uhid,a.deviceid FROM bed_device_detail a " +
				"INNER JOIN ref_inicu_bbox as b " +
				"ON a.bbox_device_id=b.bbox_id " +
				"WHERE a.uhid in ("+uhidList+") AND ((a.creationtime between '"+fromDate+"' and '"+toDate+"') OR a.status = 'true')";
	}


	public static final String getUsageLabReports(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT count(distinct testid) as count,prn FROM test_result where prn in (" + uhid + ") and lab_report_date >= '" + fromDate
				+ "' and lab_report_date <= '" + toDate + "' group by prn";
	}

//	public static final String getUsageAnthropometry(String uhid, Timestamp fromDate, Timestamp toDate) {
//		return "SELECT count(*) FROM baby_visit where uhid='" + uhid + "' and visitdate >= '" + fromDate
//				+ "' and visitdate <= '" + toDate + "'";
//	}


//	public static final String getUsageAnthropometry(String uhid, Timestamp fromDate, Timestamp toDate) {
//		return "SELECT obj FROM BabyVisit obj where uhid in (" + uhid + ") and visitdate >= '" + fromDate
//				+ "' and visitdate <= '" + toDate + "'";
//	}

	public static final String getUsageAnthropometry(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT currentdateheadcircum,currentdateheight,uhid FROM baby_visit  where uhid in (" + uhid + ") and visitdate >= '" + fromDate
				+ "' and visitdate <= '" + toDate + "'";
	}

	public static final String getLastAnthropometry(String uhid) {
			return getLastAnthropometry( uhid, null);
	}

	public static final String getLastAnthropometry(String uhid, String beforeDate) {

		if(beforeDate!= null)  return "SELECT obj FROM BabyVisit obj where  uhid = '" + uhid + "' and visitdate <= '" + beforeDate + "' order by visitdate desc";

		return "SELECT obj FROM BabyVisit obj where uhid = '" + uhid + "' order by creationtime desc";
	}

//	public static final String getLastAnthropometry(String uhid) {
//		return "SELECT obj FROM baby_visit obj where uhid = '" + uhid + "' order by creationtime desc";
//	}

	public static final String getUsageHeight(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT count(*) FROM baby_visit where uhid='" + uhid + "' and visitdate >= '" + fromDate
				+ "' and visitdate <= '" + toDate + "' and currentdateheight != 0";
	}

	public static final String getUsageHead(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT count(*) FROM baby_visit where uhid='" + uhid + "' and visitdate >= '" + fromDate
				+ "' and visitdate <= '" + toDate + "' and currentdateheadcircum != 0";
	}

	public static final String getUsageVitals(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT count(*) as count,uhid FROM nursing_vitalparameters where uhid in (" + uhid + ") and entrydate >= '" + fromDate
				+ "' and entrydate <= '" + toDate + "' group by uhid";
	}

	public static final String getUsageIntakeOutput(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT count(*) as count,uhid FROM nursing_intake_output where uhid in (" + uhid + ") and creationtime >= '" + fromDate
				+ "' and creationtime <= '" + toDate + "' group by uhid";
	}

	public static final String getUsageEvents(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT count(*) as count,uhid FROM nursing_episode where uhid in (" + uhid + ") and creationtime >= '" + fromDate
				+ "' and creationtime <= '" + toDate + "' group by uhid";
	}

	public static final String getUsageLabSampleSent(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT count(*) as count,uhid FROM investigation_ordered where uhid in (" + uhid + ") and senttolab_time >= '" + fromDate
				+ "' and senttolab_time <= '" + toDate + "' group by uhid";
	}
	
	public static final String getUsageNursingNotes(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT count(*) as count,uhid FROM nursing_notes where uhid in (" + uhid + ") and creationtime >= '" + fromDate
				+ "' and creationtime <= '" + toDate + "' group by uhid";
	}

	public static final String getBabyUHIDList() {
		return "SELECT uhid, episodeid FROM baby_detail";
	}

	public static final String getNursingNotesList(String uhid) {
		return "SELECT obj FROM NursingNote obj where uhid = '" + uhid + "'";
	}

	public static final String getActiveNursingNotesList(String uhid) {
		return "SELECT obj FROM NursingNote obj where uhid = '" + uhid + "' and flag = 'true' order by creationtime desc";
	}

	public static final String getBabyDetailList(String uhid) {
		return "SELECT obj FROM BabyDetail obj where uhid = '" + uhid + "'";
	}

	public static final String getBabyAnthropometryList(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT obj FROM BabyVisit obj where uhid = '" + uhid + "' and creationtime >= '" + fromDate
				+ "' and creationtime <= '" + toDate + "' order by creationtime";
	}


	public static final String  getBabyAnthropometryNewList(String uhid, Timestamp fromDate, Timestamp toDate){
		return "SELECT * FROM baby_visit where uhid = '" + uhid + "' and cast(concat(visitdate,' ',visittime) as timestamp) >= '" + fromDate
				+ "' and cast(concat(visitdate,' ',visittime) as timestamp) <= '" + toDate + "' order by cast(concat(visitdate,' ',visittime) as timestamp)";
	}

	public static final String getBabyAnthropometryListLast(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT obj FROM BabyVisit obj where uhid = '" + uhid + "' and creationtime >= '" + fromDate
				+ "' and creationtime <= '" + toDate + "' order by creationtime desc";
	}

	public static final String getEtSuctionList(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT obj FROM EtSuction obj where uhid = '" + uhid + "' and order_time >= '" + fromDate
				+ "' and order_time <= '" + toDate + "' order by order_time";
	}

	public static final String getBabyVisitList(String uhid, String fromDate, String toDate) {
		if (fromDate!=null){
		return "SELECT obj FROM BabyVisit obj where uhid = '" + uhid + "' and visitdate >= '" + fromDate
				+ "' and visitdate <= '" + toDate + "' order by visitdate desc";
		}else {
			return "SELECT obj FROM BabyVisit obj where uhid = '" + uhid
					+ "' and visitdate <= '" + toDate + "' order by visitdate desc";
		}
	}

	public static final String getAdmissionNotesList(String uhid) {
		return "select obj from AdmissionNotes obj where uhid='" + uhid + "' order by creationtime desc";
	}

	public static final String getDashboardViewList(String uhid) {
		return "select obj from DashboardFinalview obj where uhid='" + uhid + "'";
	}

	public static final String getRespSupportList(String uhid, Timestamp toDate) {
		return "select obj from RespSupport obj where uhid='" + uhid + "' and creationtime <= '" + toDate
				+ "' order by creationtime desc";
	}

	public static final String getNursingEpisodeList(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "select obj from NursingEpisode obj where uhid='" + uhid + "' and creationtime >= '" + fromDate
				+ "' and creationtime <= '" + toDate + "' order by creationtime asc";
	}

	public static final String getBabyfeedDetailList(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "select obj from BabyfeedDetail obj where uhid='" + uhid + "' and entrydatetime >= '" + fromDate
				+ "' and entrydatetime <= '" + toDate + "' order by entrydatetime desc";
	}
	
	public static final String getShockList(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "select obj from SaShock obj where uhid='" + uhid + "' and assessment_time >= '" + fromDate
				+ "' and assessment_time <= '" + toDate + "' order by assessment_time desc ";
	}

	public static final String getJaundiceList(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "select obj from SaJaundice obj where uhid='" + uhid + "' and assessment_time >= '" + fromDate
				+ "' and assessment_time <= '" + toDate + "' order by assessment_time desc ";
	}


	public static final String getSaHypoglycemiaListbyTime(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "select obj from SaHypoglycemia obj where uhid='" + uhid + "' and assessment_time >= '" + fromDate
				+ "' and assessment_time <= '" + toDate + "' order by assessment_time desc ";
	}


	public static final String getCurrentNursingMedicationList(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT medicinename, count(medicinename) FROM vw_nursing_medication where uhid='" + uhid
				+ "' and given_time >= '" + fromDate + "' and given_time <= '" + toDate + "' group by medicinename";
	}

	public static final String getCurrentMedicationList(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "select obj from BabyPrescription obj where uhid='" + uhid + "' and startdate < '" + toDate
				+ "' and (enddate is null or enddate >= '" + fromDate + "')";
	}

	public static final String getCurrentScreening(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT distinct screen_type FROM vw_screening_usage where uhid='" + uhid + "' and screening_time >= '"
				+ fromDate + "' and screening_time <= '" + toDate + "'";
	}

	public static final String getCurrentProcedureUsage(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT distinct procedure_type FROM vw_procedures_usage where uhid='" + uhid + "' and starttime >= '"
				+ fromDate + "' and starttime <= '" + toDate + "'";
	}

	public static final String getCurrentProcedureUsageDays(String uhid, Timestamp fromDate, Timestamp toDate, String procedureDetailstr) {
		String query =  "select   procedure_type, date_part('day', ('"+fromDate+"' - starttime))+1 from vw_procedures_usage " +
				"where  (endtime is null or endtime >='"+fromDate+"') and uhid='" + uhid + "' and  procedure_type!='Other'  and starttime <= '" + toDate + "' ";

		if (!BasicUtils.isEmpty(procedureDetailstr))  query +=  " and procedure_type not in ("+procedureDetailstr +") ";

 		//query +=  " group by procedure_type";

		return query;
		//				+" UNION "+
//				"select   distinct procedurename, date_part('day', ('"+fromDate+"' - max(creationtime)))+1 from procedure_other " +
//				"where  ( creationtime >='"+fromDate+"') and uhid='" + uhid + "' and creationtime <= '" + toDate +"' group by procedurename";
	}

	public static final String getRemovedProcedures(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "select   distinct procedure_type from vw_procedures_usage " +
				"where   endtime >='"+fromDate+"'  and uhid='" + uhid + "' and endtime <= '" + toDate + "'  and  procedure_type not in ('ET Suction', 'Lumbar Puncture', 'VTap' ) group by procedure_type";
	}


	public static final String getCurrentProcedure(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT distinct procedure_type FROM vw_procedures_usage where uhid='" + uhid
				+ "' and (endtime is null or endtime >= '" + fromDate + "') and starttime <= '" + toDate + "'";
	}

	public static final String getCurrentProcedureCount(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT procedure_type,count(procedure_type) FROM vw_procedures_usage where uhid='" + uhid
				+ "' and (endtime is null or endtime >= '" + fromDate + "') and starttime <= '" + toDate
				+ "' group by procedure_type";
	}

	public static final String getCurrentEtIntubationCount(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "select progressnotes from vw_procedures_usage where uhid='"+uhid+"' and ((endtime is null and starttime >= '"+fromDate+"') or (endtime>='" + fromDate + "'  and starttime <= '"+toDate+"')) and procedure_type='ET Intubation' and progressnotes is not null and progressnotes !='' ";
	}

	public static final String getNursingOrderList(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "select obj from NursingOrderAssesment obj where uhid='" + uhid + "' and actiontakenTime > '" + fromDate
				+ "' and actiontakenTime < '" + toDate + "'";
	}

	public static final String getInvestSampleSentList(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "select obj from InvestigationOrdered obj where uhid='" + uhid + "' and senttolab_time > '" + fromDate
				+ "' and senttolab_time < '" + toDate + "'";
	}

	public static final String getExchangeTransfusionList(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "select obj from SaJaundice obj where uhid='" + uhid
				+ "' and exchangetrans != null and assessment_time > '" + fromDate + "' and assessment_time < '"
				+ toDate + "' order by assessment_time desc";
	}

	public static final String getInvestigationList(String uhid) {
		return "select obj from InvestigationOrdered obj where uhid='" + uhid + "'";
	}

	public static final String getInvestReportList(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "select obj from InvestigationOrdered obj where uhid='" + uhid + "' and reportreceived_time > '"
				+ fromDate + "' and reportreceived_time < '" + toDate + "'";
	}

	public static final String getRespPphnList(String uhid) {
		return "select obj from SaRespPphn obj where uhid='" + uhid + "'";
	}

	public static final String getBabyPrescriptionList(String uhid) {
		return "select obj from BabyPrescription obj where uhid='" + uhid + "'";
	}

	public static final String getSepsisList(String uhid) {
		return "select obj from SaSepsis obj where uhid='" + uhid + "'";
	}

	// get nec
	public static final String getNecList(String uhid) {
		return "select obj from SaNec obj where uhid='" + uhid + "'";
	}

	public static final String getSeizuresList(String uhid) {
		return "select obj from SaCnsSeizures obj where uhid='" + uhid + "'";
	}

	public static final String getBabyfeedList(String uhid) {
		return "select obj from BabyfeedDetail obj where uhid='" + uhid + "'";
	}
	
	public static final String getBabyfeedListOrder(String uhid) {
		return "select obj from BabyfeedDetail obj where uhid='" + uhid + "' order by entrydatetime desc";
	}

	public static final String getNotificationEmailList() {
		return "select obj from NotificationEmail obj";
	}

	public static final String getAdmitBabyUHIDList(String branchName) {
		return "select uhid from baby_detail where admissionstatus is true and branchname = '" + branchName + "'";
	}

	public static final String getProcedureCannulaEmailList(String uhidStr, Timestamp insertionLimit) {
		return "select obj from PeripheralCannula obj where uhid in (" + uhidStr + ") and insertion_timestamp <'"
				+ insertionLimit + "' and removal_timestamp is null order by insertion_timestamp";
	}

	public static final String getProcedureCentralLineEmailList(String uhidStr, Timestamp insertionLimit) {
		return "select obj from CentralLine obj where uhid in (" + uhidStr + ") and insertion_timestamp <'"
				+ insertionLimit + "' and removal_timestamp is null order by insertion_timestamp";
	}
	public static final String getScreeningNeurologicalList(String uhid) {
		return "select obj from ScreenNeurological obj where uhid = '" + uhid + "'";
	}
	
	public static final String getScreeningNeurologicalList1(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "select obj from ScreenNeurological obj where uhid = '" + uhid + "'and screening_time >= '" + 
				toDate + "' and screening_time <= '" + fromDate + "'";
	}

	public static final String getScreeningMiscellaneousList(String uhid) {
		return "select obj from ScreenMiscellaneous obj where uhid = '" + uhid + "'";
	}
	
	public static final String getScreeningMiscellaneousList1(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "select obj from ScreenMiscellaneous obj where uhid = '" + uhid + "'and screening_time >= '" + 
				toDate + "' and screening_time <= '" + fromDate + "'";
	}
	public static final String getScreeningHearingList1(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "select obj from ScreenHearing obj where uhid = '" + uhid + "'and screening_time >= '" + 
				toDate + "' and screening_time <= '" + fromDate + "'";
	}

	public static final String getScreeningHearingList(String uhid) {
		return "select obj from ScreenHearing obj where uhid = '" + uhid + "'";
	}

	public static final String getScreeningRopList(String uhid) {
		return "select obj from ScreenRop obj where uhid = '" + uhid + "'";
	}
	
	public static final String getScreeningRopList1(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "select obj from ScreenRop obj where uhid = '" + uhid + "'and screening_time >= '" + 
				toDate + "' and screening_time <= '" + fromDate + "'";
	}

	public static final String getScreeningUSGList(String uhid) {
		return "select obj from ScreenUSG obj where uhid = '" + uhid + "'";
	}
	
	public static final String getScreeningUSGList1(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "select obj from ScreenUSG obj where uhid = '" + uhid + "'and screening_time >= '" + 
				toDate + "' and screening_time <= '" + fromDate + "'";
	}

	public static final String getScreeningMetabolicList(String uhid) {
		return "select obj from ScreenMetabolic obj where uhid = '" + uhid + "'";
	}
	
	public static final String getScreeningMetabolicList1(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "select obj from ScreenMetabolic obj where uhid = '" + uhid + "'and screening_time >= '" + 
				toDate + "' and screening_time <= '" + fromDate + "'";
	}

	public static final String getTreatmentList() {
		return "SELECT assesmenttreatmentid, treatment FROM ref_assesment_treatment";
	}

	public static final String getDailyProgressNotes(String uhid, String dateStr) {
		return "select obj from DailyProgressNotes obj where uhid = '" + uhid + "' and note_date = '" + dateStr + "'";
	}

	public static final String getRespiratorySupport(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "select obj from VwRespiratoryUsageFinal obj where uhid='" + uhid
				+ "' and (endtime is null or endtime > '" + fromDate + "') and creationtime <= '" + toDate + "'";
	}

	public static final String getDoctorAssessmentNotes(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "select obj from VwDoctornotesListFinal as obj where uhid='" + uhid + "' and " + "creationtime>='"
				+ fromDate + "' and " + "creationtime<='" + toDate + "' order by creationtime, sa_event";
	}

	public static final String getDoctorBloodProductList(String uhid) {
		return "select obj from DoctorBloodProducts obj where uhid = '" + uhid + "'";
	}

	public static final String getCurrentBloodProductList(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "select obj from DoctorBloodProducts obj where uhid = '" + uhid + "' and assessment_time >= '" + fromDate
				+ "' and assessment_time <= '" + toDate + "'";
	}

	public static final String getDoctorCentralLineList(String uhid) {
		return "select obj from CentralLine obj where uhid = '" + uhid + "'";
	}

	public static final String getNursingBloodProductList(String uhid) {
		return "select obj from NursingBloodproduct obj where uhid = '" + uhid + "' and flag is null order by execution_time asc";
	}

	public static final String getNursingHeplockList(String uhid) {
		return "select obj from NursingHeplock obj where uhid = '" + uhid + "' and flag is null order by execution_time desc";
	}

	public static final String getUserList(String branchName) {
		return "select obj from User obj where username not in ('test') and active=1 and branchname = '" + branchName
				+ "'";
	}

	public static final String getProcedureData(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "select obj from ProcedureOther as obj where uhid='" + uhid + "' and entrytime <= '" + toDate
				+ "' and entrytime >= '" + fromDate + "' order by entrytime desc";
	}

	public static final String getBloodProductData(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "select obj from DoctorBloodProducts obj where uhid='" + uhid + "' and " + "assessment_time <= '"
				+ toDate + "' and assessment_time>='" + fromDate + "' order by assessment_time desc";
	}

	public static final String getHeplockData(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "select obj from CentralLine obj where uhid='" + uhid + "' and " + "insertion_timestamp <= '" + toDate
				+ "' and insertion_timestamp>='" + fromDate + "' order by insertion_timestamp desc";
	}

	public static final String getIVMedData(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "select obj from BabyPrescription obj where uhid='" + uhid
				+ "' and route='IV' and isactive='true' and startdate<='" + toDate + "' and  startdate>='" + fromDate
				+ "' order by startdate desc";
	}

	public static final String getTodayMedicationList(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "select obj from BabyPrescription obj where uhid='" + uhid + "' and startdate < '" + toDate
				+ "' and startdate >= '" + fromDate + "' and (enddate is null or enddate >= '" + fromDate + "')";
	}

	public static final String getUsageVital(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT count(*) as count,uhid FROM device_monitor_detail where uhid in (" + uhid + ") and starttime >= '" + fromDate
				+ "' and starttime <= '" + toDate + "' group by uhid";
	}

	public static final String getUsageVentilator(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT count(*) as count,uhid FROM device_ventilator_detail where uhid in (" + uhid + ") and start_time >= '" + fromDate
				+ "' and start_time <= '" + toDate + "' group by uhid";
	}


	public static final String getNursingVentilator(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT count(*) as count,uhid FROM nursing_ventilaor where uhid in (" + uhid + ") and creationtime >= '" + fromDate
				+ "' and creationtime <= '" + toDate + "' group by uhid";
	}

	public static final String getUsageFio2(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT count(*) as count,uhid FROM device_ventilator_detail where uhid in (" + uhid + ") and start_time >= '" + fromDate
				+ "' and start_time <= '" + toDate + "' and fio2 is not null group by uhid";
	}

	public static final String getUsagePip(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT count(*) as count,uhid FROM device_ventilator_detail where uhid in (" + uhid + ") and start_time >= '" + fromDate
				+ "' and start_time <= '" + toDate + "' and pip is not null group by uhid";
	}

	public static final String getUsagePeep(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT count(*) as count,uhid FROM device_ventilator_detail where uhid in (" + uhid + ") and start_time >= '" + fromDate
				+ "' and start_time <= '" + toDate + "' and peep is not null group by uhid";
	}

	public static final String getUsageHr(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT count(*) as count,uhid FROM device_monitor_detail where uhid in (" + uhid + ") and starttime >= '" + fromDate
				+ "' and starttime <= '" + toDate + "' and heartrate is not null group by uhid";
	}

	public static final String getUsagePr(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT count(*) as count, uhid FROM device_monitor_detail where uhid in (" + uhid + ") and starttime >= '" + fromDate
				+ "' and starttime <= '" + toDate + "' and pulserate is not null group by uhid";
	}

	public static final String getUsageSpo2(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT count(*) as count,uhid FROM device_monitor_detail where uhid in (" + uhid + ") and starttime >= '" + fromDate
				+ "' and starttime <= '" + toDate + "' and spo2 is not null group by uhid";
	}

	public static final String getUsageRr(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT count(*) as count,uhid FROM device_monitor_detail where uhid in (" + uhid + ") and starttime >= '" + fromDate
				+ "' and starttime <= '" + toDate + "' and ecg_resprate is not null group by uhid";
	}

	public static final String getBranchList() {
		return "Select branchname from RefHospitalbranchname order by branchname";
	}

	public static final String getActiveMedications(String eventname, String uhid) {
		return "Select calculateddose from baby_prescription" + " where eventname='" + eventname
				+ "' and isactive=true and uhid='" + uhid + "'";
	}

	public static final String getTreatmentList(String tableName, String uhid) {
		return "Select action_type from " + tableName + " where uhid='" + uhid + "' and action_type is not null"
				+ "order by creationtime desc";
	}

	public static final String getMaxEpisodeOfAssessment(String tablename, String uhid) {
		return "SELECT MAX(episode_number) FROM " + tablename + " WHERE uhid='" + uhid + "'";
	}

	public static final String getFirstActiveOfGivenEpisode(String tablename, String uhid, Integer episode) {
		return "SELECT obj from " + tablename + " as obj where uhid ='" + uhid + "' and episodeNumber=" + episode + "";
	}

	public static String getRefHospitalDetails(String branchName) {
		return "Select obj from RefHospitalbranchname as obj where branchname ='" + branchName + "'";
	}
	
	public static String getAllActiveDoctors(String branchName) {
		return "Select concat(activeUsers.firstname, ' ',activeUsers.lastname) as Doctor " + 
				"from inicuuser activeUsers join users_roles roles on activeUsers.username = roles.username " + 
				"where roles.roleid IN('2') and activeUsers.branchname = '"+branchName+"' order by Doctor";
	}


	// Queries Added by Umesh
	public static String getRefBed(String bedIds, String branchName){
		return "Select obj.bedid, obj.bedname from ref_bed obj where branchname = '" + branchName
				+ "' and obj.bedid in (" + bedIds + ")";
	}

	public static String  getRefRoom(String roomIds,String branchName){
		return "Select obj.roomid, obj.roomname from ref_room obj where branchname = '"
				+ branchName + "' and obj.roomid in (" + roomIds + ")";
	}

	public static String getActiveBabies(String branchName){
		return "Select obj from BabyDetail obj where admissionstatus = 'true' and branchname = '"
				+ branchName + "' order by uhid";
	}

	public static String getBabyVisitAnthropometryList(String uhidList){
		return "Select obj from BabyVisit obj where uhid  in ("
				+ uhidList + ") order by creationtime desc";
	}

	public static String getBabiesWeightForCal(String uhidList){
		return "select weight_for_cal,uhid from baby_visit obj where uhid in ("
				+ uhidList + ") order by weight_for_cal desc";
	}

	public static  String getLabReportList(String uhidList,Timestamp today,Timestamp yesterday){
		return "select obj from InvestigationOrdered as obj where uhid in ("
				+ uhidList + ") and ((investigationorder_time <= '" + today + "' and "
				+ "investigationorder_time >= '" + yesterday
				+ "') or (issamplesent is null or issamplesent = '" + false
				+ "'  )) order by investigationorder_time";
	}

	public static String queryParentConnect(String uhidList){
		return "select distinct uhid,mother_gynaecologist,creationtime from parent_detail where uhid in (" + uhidList + ") order by creationtime desc";
	}

	public static String queryBabyConnect(String uhidList){
		return "select distinct uhid,admittingdoctor,creationtime from baby_detail where uhid in (" + uhidList + ") order by creationtime desc";
	}

	public static String getDoctoreNoteEntry(String uhidList){
		return "select distinct uhid,doctornotes,noteentrytime from doctor_notes where uhid in (" + uhidList + ") and noteentrytime>='"
				+ new java.sql.Date(new java.util.Date().getTime()) + "' order by noteentrytime desc";
	}

	public static String getROPList(String uhidList){
		return "select distinct uhid,reassess_date,screening_time from screen_rop where uhid in (" + uhidList + ") order by screening_time desc";
	}

	public static String getChildMaxWeightList(String uhidList){
		return "select distinct uhid,Max(currentdateweight) from baby_visit where uhid in (" + uhidList + ") group by uhid";
	}


	public static String getCurrentBabyVisit(String uhidList){
		return "select uhid,currentdateweight from baby_visit where uhid in (" + uhidList + ") and visitdate='"
				+ new java.sql.Date(new java.util.Date().getTime()) + "'";
	}

	public static String getBedDeviceHistory(String uhidList){
		return  "select uhid,devicetype from bed_device_history where uhid in (" + uhidList
				+ ")";
	}
	public static String getDeviceDetailName(String uhidList){
		return "SELECT a.bboxname,b.uhid,a.tinyboxname FROM ref_inicu_bbox a " +
				"INNER JOIN bed_device_detail b on a.bbox_id=b.bbox_device_id" +
				" WHERE b.uhid in (" + uhidList + ") and status='"+
				true + "'";
	 }

	 public static String getNursingNote(String uhidList,Timestamp yesterday,Timestamp today){
		return "Select obj from NursingNote obj where uhid in (" + uhidList
				+ ") and from_time >= '" + yesterday + "' and from_time <= '" + today + "' and to_time >= '"
				+ yesterday + "' and to_time <= '" + today + "'";
	 }
	 public static String getTcbValue(String uhidList, Timestamp yesterday, Timestamp today){
		return
				"SELECT uhid, tcb,creationtime,rn FROM"+
				 "( SELECT uhid, tcb,creationtime,ROW_NUMBER() OVER (PARTITION BY uhid ORDER BY MAX(creationtime) DESC ) AS rn"+
						" FROM nursing_vitalparameters"+
						" WHERE  uhid in (" + uhidList + ") and tcb is not null and creationtime >= '" + yesterday + "' and creationtime <= '" + today +"' GROUP BY  uhid, tcb,creationtime ) AS t WHERE" +
						" rn <= 1 ORDER BY uhid, rn, creationtime desc";
	 }
	 public static  String getRbsValue(String uhidList, Timestamp yesterday, Timestamp today){
		 return "SELECT uhid, rbs,creationtime,rn FROM"+
						 " ( SELECT uhid, rbs,creationtime,ROW_NUMBER() OVER (PARTITION BY uhid ORDER BY MAX(creationtime) DESC ) AS rn"+
						 " FROM nursing_vitalparameters"+
						 " WHERE  uhid in (" + uhidList + ") and rbs is not null and creationtime >= '" + yesterday + "' and creationtime <= '" + today +"' GROUP BY  uhid, rbs,creationtime ) AS t WHERE" +
						 " rn <= 1 ORDER BY uhid, rn, creationtime desc";
	 }

	 public static String getLastVitalParametersList(String uhidList,Timestamp yesterday, Timestamp today){
		return "SELECT uhid, abdomen_girth,creationtime,rn FROM"+
				" ( SELECT uhid, abdomen_girth,creationtime,ROW_NUMBER() OVER (PARTITION BY uhid ORDER BY MAX(creationtime) DESC ) AS rn"+
				" FROM nursing_intake_output"+
				" WHERE  uhid in (" + uhidList + ") and creationtime >= '" + yesterday + "' and creationtime <= '" + today +"' GROUP BY  uhid, abdomen_girth,creationtime ) AS t WHERE" +
				" rn <= 1 ORDER BY uhid, rn, creationtime desc";
	 }

	public static String getVitalParametersList(String uhidList,Timestamp yesterday, Timestamp today){
		return "Select obj from NursingIntakeOutput obj where uhid in (" + uhidList
				+ ") and entry_timestamp >= '" + yesterday + "' and entry_timestamp <= '" + today
				+ "' order by entry_timestamp desc";
	}
	public static String getLastRespSupport(String uhidList,Timestamp yesterday, Timestamp today){
			return "SELECT uhid, hco3,be,co2,ph,rs_pco2,creationtime,rn FROM"+
					" ( SELECT uhid, hco3,be,co2,ph,rs_pco2,creationtime,ROW_NUMBER() OVER (PARTITION BY uhid ORDER BY MAX(creationtime) DESC ) AS rn"+
					" FROM respsupport"+
					" WHERE  uhid in (" + uhidList + ") and creationtime >= '" + yesterday + "' and creationtime <= '" + today +"' GROUP BY  uhid,hco3,be,co2,ph,rs_pco2,creationtime ) AS t WHERE" +
					" rn <= 1 ORDER BY uhid, rn, creationtime desc";
	}

	public static String getLastDiagnosisNote(String uhidList,Timestamp yesterday, Timestamp today){
		return "SELECT uhid, diagnosis,creationtime,rn FROM"+
				" ( SELECT uhid, diagnosis,creationtime,ROW_NUMBER() OVER (PARTITION BY uhid ORDER BY MAX(creationtime) DESC ) AS rn"+
				" FROM admission_notes"+
				" WHERE  uhid ='" + uhidList + "' and creationtime >= '" + yesterday + "' and creationtime <= '" + today +"' GROUP BY  uhid,diagnosis,creationtime ) AS t WHERE" +
				" rn <= 1 ORDER BY uhid, rn, creationtime desc";
	}

	public static String getLastRenalFailure(String uhidList,Timestamp yesterday, Timestamp today){
		return "SELECT uhid, renalfailure,urine_output_feature,creationtime,rn FROM"+
				" ( SELECT uhid, renalfailure,urine_output_feature,creationtime,ROW_NUMBER() OVER (PARTITION BY uhid ORDER BY MAX(creationtime) DESC ) AS rn"+
				" FROM sa_renalfailure"+
				" WHERE  uhid in (" + uhidList + ") and creationtime >= '" + yesterday + "' and creationtime <= '" + today +"' GROUP BY  uhid,renalfailure,urine_output_feature,creationtime ) AS t WHERE" +
				" rn <= 1 ORDER BY uhid, rn, creationtime desc";
	}

	public static String getLastNursingVentMode(String uhidList,Timestamp yesterday, Timestamp today){
		return "SELECT uhid, ventmode,entrydate,rn FROM"+
				" ( SELECT uhid, ventmode,entrydate,ROW_NUMBER() OVER (PARTITION BY uhid ORDER BY MAX(creationtime) DESC ) AS rn"+
				" FROM nursing_ventilaor"+
				" WHERE  uhid in (" + uhidList + ") and entrydate >= '" + yesterday + "' and entrydate <= '" + today +"' GROUP BY  uhid,ventmode,entrydate ) AS t WHERE" +
				" rn <= 1 ORDER BY uhid, rn, entrydate desc";
	}

	public static String getLastVitalEntry(String uhidList,Timestamp yesterday, Timestamp today){
		return "SELECT uhid,starttime,rn FROM"+
				" ( SELECT uhid,starttime,ROW_NUMBER() OVER (PARTITION BY uhid ORDER BY MAX(entrydate) DESC ) AS rn"+
				" FROM vw_vital_detail"+
				" WHERE  uhid in (" + uhidList + ") and starttime >= '" + yesterday + "' and starttime <= '" + today +"' GROUP BY  uhid,starttime ) AS t WHERE" +
				" rn <= 1 ORDER BY uhid, rn, starttime desc";
	}

	public static String getAllVentilatorMode(){
		return "Select obj from RefVentilationmode obj";
	}

	public static String getDeviceBrand(String deviceIdStr){
		return "select devicetype,devicebrand,ipaddress,inicu_deviceid,devicename from device_detail where inicu_deviceid in ("+deviceIdStr+")";
	}

	// Queries for the patient Output Screen -> Nursing Panel
	public static final String getVitalsParametersForCharts(String uhid,Timestamp fromDate, Timestamp toDate) {
		return "SELECT obj FROM NursingVitalparameter obj where entrydate <= '" + toDate
				+ "' and entrydate >= '" + fromDate
				+ "' and uhid='"+uhid +"' order by entrydate asc";
	}

	public static String getNursingEpisodeListForChart(String uhid,Timestamp fromDate,Timestamp toDate){
		return "SELECT obj FROM NursingEpisode obj where creationtime < '" + toDate
				+ "' and creationtime >= '" + fromDate
				+ "' and uhid='"+uhid +"' order by creationtime asc";
	}

	public static String getBloodGasDetails(String uhid,Timestamp fromDate,Timestamp toDate){
		return "SELECT obj FROM NursingBloodGas obj where entrydate < '" + toDate
				+ "' and entrydate >= '" + fromDate
				+ "' and uhid='"+uhid +"' order by entrydate asc";
	}

	public static String getChartBloodGasDetails(String uhid,Timestamp fromDate,Timestamp toDate) {
		return "select  * from ( select b.entrydate,b.ph,b.pco2,n.etco2,po2,b.regular_hco3,b.be,b.spo2 as so2,b.lactate,b.hct,b.thbc,b.osmolarity," +
				"n.spo2,v.map,row_number() over (partition by date_trunc('hour', v. entrydate) order by  v. entrydate desc) as rn from " +
				"nursing_bloodgas b inner join nursing_vitalparameters n ON b.uhid=n.uhid and date_trunc('hour',  b.entrydate) = date_trunc('hour',  n. entrydate)" +
				"inner join nursing_ventilaor v ON b.uhid=v.uhid and date_trunc('hour',  b. entrydate) = date_trunc('hour',  v. entrydate)" +
				"where b.uhid='"+uhid+"' and b.entrydate <'"+toDate+"' and b.entrydate>='"+fromDate+"' order by v.entrydate desc) as dt where rn = 1 order by entrydate";
	}


	public static String getChartVitalsData(String uhid,Timestamp fromDate,Timestamp toDate){
		return "select entrydate,vp_position,baby_color,baby_color_other,consciousness,left_pupil_size,right_pupil_size,cft,spo2,etco2"+
		" from ( select entrydate,vp_position,baby_color,baby_color_other,consciousness,left_pupil_size,right_pupil_size,cft,spo2,etco2, row_number() over (partition by date_trunc('hour', entrydate) " +
				"order by entrydate desc) as r from nursing_vitalparameters t where " +
				"t.entrydate<'" + toDate
				+"' and t.entrydate>='"+fromDate
				+"' and t.uhid='"+uhid+"') as dt where r = 1 order by entrydate";
	}

	public static String getChartVentilatorData(String uhid,Timestamp fromDate,Timestamp toDate){
		return "select  * from ( select v.entrydate,v.ventmode,v.pressure_supply,v.peep_cpap,v.ti,v.te,v.map,v.fio2,v.flow_per_min,v.freq_rate," +
				"v.tidal_volume,v.minute_volume,v.frequency,v.dco2,v.humidity,v.control_type,v.pressure_support_type,v.volume_guarantee," +
				"v.cpap_type,v.delivery_type,row_number() over (partition by date_trunc('hour', v. entrydate)" +
				"    order by  v.entrydate desc) as rn from nursing_ventilaor v " +
				"    where v.uhid='"+uhid+"' and v.entrydate <'"+toDate+"' and v.entrydate>='"+fromDate+"' order by v.entrydate) as dt" +
				"    where rn = 1 order by entrydate";
	}

//	public static String getPatientGraphData(String uhid,Timestamp fromDate,Timestamp toDate){
//		return "select  * from ( select m.starttime,n.centraltemp,n.peripheraltemp,m.heartrate,n.cvp,m.ecg_resprate," +
//				"m.sys_bp,m.dia_bp,m.mean_bp,m.ibp_s,m.ibp_d,m.ibp_m,m.pulserate,row_number() over (partition by date_trunc('hour', m.starttime) order by m.starttime) as rn from " +
//				"nursing_vitalparameters n inner join device_monitor_detail m ON n.uhid=m.uhid and date_trunc('hour',  n. entrydate) = date_trunc('hour', m.starttime) " +
//				"where m.uhid='"+uhid+"' and m.starttime <'"+toDate+"' and m.starttime>='"+fromDate+"' order by m.starttime) as dt where rn = 1";
//	}

	public static final String getPatientGraphData(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT obj FROM DeviceMonitorDetail obj where uhid = '" + uhid + "' and starttime >= '" + fromDate
				+ "' and starttime < '" + toDate + "' order by starttime asc";
	}

	public static final String getNursingVitalGraphList(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT obj FROM NursingVitalparameter obj where uhid = '" + uhid + "' and entryDate >= '" + fromDate
				+ "' and entryDate < '" + toDate + "' order by entryDate";
	}


	public static String getVentilationMode(){
		return "select obj from RefVentilationmode obj";
	}

	public static String getTcbAndRbcValues(String uhid, Timestamp fromDate,Timestamp toDate){
		return "SELECT obj FROM NursingVitalparameter obj where uhid='"+ uhid +"'" +
				" and entrydate <'" + toDate +"'" +
				" and entrydate>='"+ fromDate +"' order by entrydate asc";
	}

	public static String getBloodGasObjectForInvestigation(String uhid,Timestamp fromDate,Timestamp toDate){
		return "SELECT obj FROM NursingBloodGas obj where uhid='"+ uhid +"'" +
				" and entrydate <'" + toDate +"'" +
				" and entrydate>='"+ fromDate +"' order by entrydate asc";
	}

	public static String getFeedTypeRefList(){
		return "select obj from RefMasterfeedtype obj";
	}

    public static final String getPatientMedicationList(String uhid, Timestamp fromDate, Timestamp toDate,String babyPresIdList) {
        return "select obj from NursingMedication obj where uhid='"+uhid+"'and given_time >= '"+ fromDate+ "'" +
                " and given_time <'" + toDate +"' and baby_presid in ( "+ babyPresIdList +
                " ) order by given_time asc";
    }

    public static String getCurrentNutrition(String uhid,Timestamp fromDate,Timestamp toDate){
		return "SELECT obj FROM BabyfeedDetail obj where uhid='"+uhid+"'and entrydatetime >= '"+ fromDate+ "'" +
				" and entrydatetime <'" + toDate + "'"+
				" order by entrydatetime desc";
	}

	public static final String getNursingExecution(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT obj FROM NursingIntakeOutput obj where uhid = '" + uhid + "' and entry_timestamp >= '" + fromDate
				+ "' and entry_timestamp <'" + toDate + "' order by entry_timestamp asc";
	}

	public static final String getBloodProductExecution(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT obj FROM NursingBloodproduct obj where uhid = '" + uhid + "' and execution_time >= '" + fromDate
				+ "' and execution_time <'" + toDate + "' order by execution_time asc";
	}

	public static final String getHeparinExecution(String uhid, Timestamp fromDate, Timestamp toDate) {
		return "SELECT obj FROM NursingHeplock obj where uhid = '" + uhid + "' and execution_time >= '" + fromDate
				+ "' and execution_time <'" + toDate + "' order by execution_time asc";
	}

	// get the baby prescrition id for the baby
	public static final String getBabyPrescription(String uhid, Timestamp fromDate, Timestamp toDate){
		return "SELECT obj FROM BabyPrescription obj where uhid = '" + uhid + "' and startdate >= '" + fromDate
				+ "' and startdate <'" + toDate + "' order by startdate asc";
	}

	// vital list
	public static final String getBP(String uhid, Timestamp fromDate, Timestamp toDate){
			return "select  entrydate,syst_bp,diast_bp,mean_bp,cvp"+
					" from ( select syst_bp,diast_bp,mean_bp,entrydate,cvp,row_number() over (partition by date_trunc('hour', entrydate) " +
					"order by entrydate) as r from nursing_vitalparameters t where " +
					"t.entrydate<'" + toDate
					+"' and t.entrydate>='"+fromDate
					+"' and t.uhid='"+uhid+"') as dt where r = 1";
	}

	public static String getPreviousBabyVisit(String uhid, String yesterdayDateStr){
		return "Select obj from BabyVisit obj where uhid  ='"
				+ uhid + "' and currentdateweight > 0  and visitdate = '" + yesterdayDateStr + "' order by creationtime desc";
	}
	public static String getExecutedMedication(String uhid, Timestamp fromDate, Timestamp toDate){
		return "select p.baby_presid, p.medicinename,n.given_time, p.inf_volume,p.uhid from nursing_medication n " +
				"inner join baby_prescription p on n.uhid = p.uhid and  CAST (n.baby_presid AS INTEGER)= CAST (p.baby_presid AS INTEGER) " +
				"where p.uhid = '" + uhid + "' and n.given_time >= '" + fromDate + "' and n.given_time <'" + toDate + "' order by n.given_time asc";
	}

	public static String getGievnMedicationbyID(Long presId, Timestamp fromDate, Timestamp toDate){

		return "SELECT baby_presid FROM nursing_medication where baby_presid = '" + presId + "' and given_time >= '"
				+ fromDate + "' and given_time <= '" + toDate + "'";

	}

	public static final String getDischargeBabyProcedureStatus(String uhid){
		return "SELECT 'Peripheral Cannula' as event FROM "+ BasicConstants.SCHEMA_NAME +".peripheral_cannula where uhid='" + uhid + "' and removal_timestamp is null " +
				"UNION SELECT 'Central Line' as event FROM "+ BasicConstants.SCHEMA_NAME +".central_line where uhid='" + uhid + "' and removal_timestamp is null " +
				"UNION SELECT 'ET Intubation' as event FROM "+ BasicConstants.SCHEMA_NAME +".et_intubation where uhid='" + uhid + "' and removal_timestamp is null " +
				"UNION SELECT 'Chest Tube' as event FROM "+ BasicConstants.SCHEMA_NAME +".procedure_chesttube where uhid='" + uhid + "' and isactive='true'";
	}


	public static final String getProcedureSites(String uhid){
		return "SELECT 'Peripheral Cannula' as event, site FROM peripheral_cannula where uhid='" + uhid + "' and removal_timestamp is null   " +
				"UNION SELECT 'Central Line' as event, site FROM central_line where uhid='" + uhid + "' and removal_timestamp is null   " +
				"UNION SELECT 'VTap' as event,vtap_site FROM vtap where uhid='" + uhid + "'  " +
				"UNION SELECT 'ET Suction' as event,site FROM et_suction where uhid='" + uhid + "' and removal_timestamp is null   " +
				"UNION SELECT 'ET Intubation' as event,site FROM et_intubation where uhid='" + uhid + "' and removal_timestamp is null   " ;
			//	"UNION SELECT 'Chest Tube' as event FROM procedure_chesttube where uhid='" + uhid + "' and isactive='true'   " ;
	}

	public static final String getJaundiceHypoglycemiaAssessmentStatus(String uhid) {

		return "SELECT 'Jaundice' as event, jaundicestatus FROM sa_jaundice where lower(jaundicestatus) != 'inactive' and lower(jaundicestatus) != 'no' "
				+ " and assessment_time = (SELECT max(assessment_time) as max_time FROM sa_jaundice where uhid='" + uhid
				+ "') and sajaundiceid = (select max(sajaundiceid) from sa_jaundice where uhid ='" + uhid
				+ "') and uhid='" + uhid
				+ "' UNION SELECT a.event, a.eventstatus FROM vw_assesment_metabolic_final a"
				+ " JOIN (SELECT max(creationtime) as max_time, event, max(id) as id FROM vw_assesment_metabolic_final where uhid='"
				+ uhid
				+ "' group by event) b on a.creationtime = b.max_time and a.event = b.event and a.id = b.id where "
				+ "lower(a.eventstatus) != 'inactive' and lower(a.eventstatus) != 'no' and uhid='" + uhid + "'";
	}

}
