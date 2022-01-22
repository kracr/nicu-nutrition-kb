package com.inicu.his.data.acquisition;


import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import javax.persistence.Basic;

import com.inicu.postgres.service.SettingsService;
import com.inicu.postgres.utility.*;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.inicu.models.AdvanceAdmitPatientPojo;
import com.inicu.models.KeyValueObj;
import com.inicu.models.PatientInfoAddChildObj;
import com.inicu.models.PatientInfoAdmissonFormObj;
import com.inicu.models.PatientInfoChildDetailsObj;
import com.inicu.models.RefTestslist;
import com.inicu.postgres.dao.PatientDao;
import com.inicu.postgres.entities.BabyDetail;
import com.inicu.postgres.entities.ParentDetail;
import com.inicu.postgres.entities.RefBed;
import com.inicu.postgres.entities.RefRoom;
import com.inicu.postgres.entities.TestItemResult;
import com.inicu.postgres.service.PatientService;
import com.inicu.postgres.service.TestsService;
import com.inicu.sqlserver.utility.SqlserverConnector;

@Repository
public class HISReceiver extends Thread {

	@Autowired
	PatientDao patientDao;

	@Autowired
	PatientService patientService;

	@Autowired
	TestsService testsService;

	@Autowired
	SettingsService settingsService;

	private static final Logger logger = LoggerFactory.getLogger(HISReceiver.class);

	@Override
	public void run(){
		boolean running = true;
		while (running) {
				RemotedbConnector connector = null;
				try {
					connector = new RemotedbConnector(false);
				} catch (Exception e1) {
					System.out.println("Unable to create database connector ");
					e1.printStackTrace();
				}

				try {
					System.out.println("[" + BasicConstants.timeStampFormat.format(new Timestamp(System.currentTimeMillis())) + "] :: PATIENT DATA FETCH STARTS");
					List<String> currentUhidsList = fetchBabiesUHID();

					String queryNewBabiesCount = "";
					//fetch count of new baby uhids from remote DB
					if (BasicConstants.SCHEMA_NAME.equalsIgnoreCase("gangaram")) {
						queryNewBabiesCount = "Select count(distinct(f.papmi_no)) as RegNo From PA_Adm a, ct_loc b, rbc_departmentgroup c, ct_careprov d, pa_person e, pa_patmas f where  a.Paadm_depcode_dr=b.ctloc_rowid and b.ctloc_dep_dr=c.dep_rowid And  a.paadm_admdoccodedr=d.ctpcp_rowid1 and a.Paadm_Papmi_dr=e.paper_rowid and a. paadm_papmi_dr=f.papmi_rowid1 and a.paadm_currentward_dr->ward_code='WD-SWB-NURSERY' and paadm_visitstatus='A' and "
								+ "f.papmi_no"
								+ " not in "
								+ " ( '" + String.join("' , '", currentUhidsList) + "' ) ";
					} else {
						queryNewBabiesCount = "SELECT count(distinct "
								+ HISConstants.HISParametersMap.get("uhid") + " ) FROM " + BasicConstants.REMOTE_PATIENTVIEW_NAME + " where " + HISConstants.HISParametersMap.get("uhid")
								+ " not in "
								+ " ( '" + String.join("' , '", currentUhidsList) + "' ) ";
					}
					try {
						ResultSet newBabiesCountRS = connector.executePostgresSelectQuery(queryNewBabiesCount);
						if (newBabiesCountRS.next()) {
							int newBabiesCount = newBabiesCountRS.getInt(1);
							newBabiesCountRS.close();
							if (newBabiesCount > 0) {
								//fetch new baby data from remote DB
								String fieldsList = "";
								String queryNewBabies = "";
								if (BasicConstants.SCHEMA_NAME.equalsIgnoreCase("gangaram")) {

									queryNewBabies = "Select f.papmi_no as RegNo,PAPER_StName Address, PAADM_ADMNO as EpisodeNumber, e.paper_name2||' '||e.paper_name as Name, e.paper_sex_dr->ctsex_desc as Sex, e.PAPER_Dob, PAPER_AgeYr Age, e.paper_mobphone as Mobile,PAPER_name4 Father_name,PAPER_occupation MotherName, Paadm_depcode_dr->ctloc_desc as UnitName, d.ctpcp_desc as TreatingDoctor, c.dep_desc as Speciality, a.paadm_currentward_dr->ward_desc as Ward, a. paadm_currentroom_dr->Room_desc as Room, a.paadm_currentbed_dr->bed_code as Bed, paadm_admDate as AdmissionDate, Paadm_admTime as AdmissionTime, paadm_visitstatus as  EpisodeStatus From PA_Adm a, ct_loc b, rbc_departmentgroup c, ct_careprov d, pa_person e, pa_patmas f where  a.Paadm_depcode_dr=b.ctloc_rowid and b.ctloc_dep_dr=c.dep_rowid And  a.paadm_admdoccodedr=d.ctpcp_rowid1 and a.Paadm_Papmi_dr=e.paper_rowid and a. paadm_papmi_dr=f.papmi_rowid1 and a.paadm_currentward_dr->ward_code='WD-SWB-NURSERY' and paadm_visitstatus='A' and f.papmi_no " + " not in "
											+ " ( '" + String.join("' , '", currentUhidsList) + "' ) ";
								} else {
									queryNewBabies = "SELECT distinct " + String.join(" , ", HISConstants.HIS_FIELDS) + " FROM " + BasicConstants.REMOTE_PATIENTVIEW_NAME + " where " + HISConstants.HISParametersMap.get("uhid") + " not in "
											+ " ( '" + String.join("' , '", currentUhidsList) + "' ) ";

								}
								ResultSet currentBabiesData = connector.executePostgresSelectQuery(queryNewBabies);
								synchronized (currentBabiesData) {
									insertPatients(currentBabiesData, newBabiesCount);
								}
								currentBabiesData.close();
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					// To get babies discharge data from HIS
					if (BasicConstants.SCHEMA_NAME.equalsIgnoreCase("apollo") || BasicConstants.SCHEMA_NAME.equalsIgnoreCase("rainbow")
							|| BasicConstants.SCHEMA_NAME.equalsIgnoreCase("gangaram")) {
						List<String> currentUhidsListWithBlankHISDischargeDate = fetchBabiesUHIDWithBlankHISDischargeDate();
						String dischargeStatusQuery = "";
						if (BasicConstants.SCHEMA_NAME.equalsIgnoreCase("gangaram")) {
							dischargeStatusQuery = "Select f.papmi_no as RegNo,PAPER_StName Address, PAADM_ADMNO as EpisodeNumber, e.paper_name2||' '||e.paper_name as Name, e.paper_sex_dr->ctsex_desc as Sex, e.PAPER_Dob, PAPER_AgeYr Age, e.paper_mobphone as Mobile,PAPER_name4 Father_name,PAPER_occupation MotherName, Paadm_depcode_dr->ctloc_desc as UnitName, d.ctpcp_desc as TreatingDoctor, c.dep_desc as Speciality, a.paadm_currentward_dr->ward_desc as Ward, a. paadm_currentroom_dr->Room_desc as Room, a.paadm_currentbed_dr->bed_code as Bed, paadm_admDate as AdmissionDate, Paadm_admTime as AdmissionTime,PAADM_EstimDischargeDate Discharge_Date,PAADM_EstimDischargeTime Discharge_Time, paadm_visitstatus as  EpisodeStatus From PA_Adm a, ct_loc b, rbc_departmentgroup c, ct_careprov d, pa_person e, pa_patmas f where  a.Paadm_depcode_dr=b.ctloc_rowid and b.ctloc_dep_dr=c.dep_rowid And  a.paadm_admdoccodedr=d.ctpcp_rowid1 and a.Paadm_Papmi_dr=e.paper_rowid and a. paadm_papmi_dr=f.papmi_rowid1 and a.paadm_currentward_dr->ward_code='WD-SWB-NURSERY' and f.papmi_no in "
									+ " ( '" + String.join("' , '", currentUhidsListWithBlankHISDischargeDate) + "' ) ";
						} else if (BasicConstants.SCHEMA_NAME.equalsIgnoreCase("rainbow")) {
							dischargeStatusQuery = "select " + String.join(" , ", HISConstants.HIS_DISCHARGE_VIEW_FIELDS) + " , rn from (select " + String.join(" , ", HISConstants.HIS_DISCHARGE_VIEW_FIELDS) + " , ROW_NUMBER() " +
									"OVER (PARTITION BY " + HISConstants.HISDischargeParametersMap.get("uhid") + " order by " + HISConstants.HISDischargeParametersMap.get("uhid") + " ) as rn from " + BasicConstants.HIS_DISCHARGE_VIEW +
									" where " + HISConstants.HISDischargeParametersMap.get("uhid") + " in ( '" + String.join("' , '", currentUhidsListWithBlankHISDischargeDate) + "' ) ) As t " +
									"where rn=1 and " + HISConstants.HISDischargeParametersMap.get("hisdischargedate") + " IS NOT NULL ORDER BY " + HISConstants.HISDischargeParametersMap.get("hisdischargedate") + " DESC";
						} else {
							dischargeStatusQuery = "select " + String.join(" , ", HISConstants.HIS_DISCHARGE_VIEW_FIELDS) + " , rn from (select " + String.join(" , ", HISConstants.HIS_DISCHARGE_VIEW_FIELDS) + " , ROW_NUMBER() " +
									"OVER (PARTITION BY " + HISConstants.HISDischargeParametersMap.get("uhid") + " ORDER BY " + HISConstants.HISDischargeParametersMap.get("hisdischargedate") + " desc) as rn from " + BasicConstants.HIS_DISCHARGE_VIEW +
									" where " + HISConstants.HISDischargeParametersMap.get("uhid") + " in ( '" + String.join("' , '", currentUhidsListWithBlankHISDischargeDate) + "' ) order by " + HISConstants.HISDischargeParametersMap.get("hisdischargedate") + ") As t " +
									"where rn=1 and " + HISConstants.HISDischargeParametersMap.get("hisdischargedate") + " is not null";
						}
						try {
							ResultSet babiesDischargeStatusResultSet = connector.executePostgresSelectQuery(dischargeStatusQuery);
							if (BasicConstants.SCHEMA_NAME.equalsIgnoreCase("apollo") || BasicConstants.SCHEMA_NAME.equalsIgnoreCase("rainbow")
									|| BasicConstants.SCHEMA_NAME.equalsIgnoreCase("gangaram")) {
								insertDischargeData(babiesDischargeStatusResultSet);
							}
							babiesDischargeStatusResultSet.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					List<String> uhidsListWithBlankIPNumber = fetchBabiesUHIDWithBlankIPNumnber();
					String fetchIpNumberQuery = "";
					if (BasicConstants.SCHEMA_NAME.equalsIgnoreCase("gangaram")) {

						fetchIpNumberQuery = "Select f.papmi_no as RegNo, PAADM_ADMNO as EpisodeNumber From PA_Adm a, ct_loc b, rbc_departmentgroup c, ct_careprov d, pa_person e, pa_patmas f where  a.Paadm_depcode_dr=b.ctloc_rowid and b.ctloc_dep_dr=c.dep_rowid And  a.paadm_admdoccodedr=d.ctpcp_rowid1 and a.Paadm_Papmi_dr=e.paper_rowid and a. paadm_papmi_dr=f.papmi_rowid1 and a.paadm_currentward_dr->ward_code='WD-SWB-NURSERY' and f.papmi_no in "
								+ " ( '" + String.join("' , '", uhidsListWithBlankIPNumber) + "' ) ";
					} else {
						fetchIpNumberQuery = "SELECT " + HISConstants.HISParametersMap.get("uhid") + ", " + HISConstants.HISParametersMap.get("ipnumber") + " FROM " + BasicConstants.REMOTE_PATIENTVIEW_NAME + " where " + HISConstants.HISParametersMap.get("uhid") + " in "
								+ " ( '" + String.join("' , '", uhidsListWithBlankIPNumber) + "' ) ";
					}
					try {
						ResultSet fetchIpNumberResultSet = connector.executePostgresSelectQuery(fetchIpNumberQuery);
						updateIpNumber(fetchIpNumberResultSet);
						fetchIpNumberResultSet.close();
					} catch (Exception e) {
						e.printStackTrace();
					}

					Thread.sleep(BasicConstants.REMOTE_THREAD_FREQUENCY_POSTGRES);

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (connector != null)
						try {
							connector.closeConnection();
						} catch (Exception e2) {
							e2.printStackTrace();
						}
				}
			} //while end
	}

    private void insertDischargeData(ResultSet babiesDischargeStatusResultSet) throws SQLException {
	    if (babiesDischargeStatusResultSet != null) {
			Map<String, Timestamp> dischargeTimestampMap = new HashMap<>();
	        String hisDischargeStatus = "";
			Timestamp sqlPresentDate = null;
			String uhid = "";
			String updateQuery = "";
			boolean isDisconnectDevice = false;
            while(babiesDischargeStatusResultSet.next()) {
                // Save discharge data in baby details
                BabyDetail babyDetailObject = new BabyDetail();

                if(HISConstants.HISDischargeParametersMap.containsKey("hisdischargestatus")) {
                    hisDischargeStatus = babiesDischargeStatusResultSet.getString(HISConstants.HISDischargeParametersMap.get("hisdischargestatus"));
                    System.out.println("hisDischargeStatus --------> " + hisDischargeStatus);
                }

                try {
                    if(BasicConstants.SCHEMA_NAME.equalsIgnoreCase("apollo") || BasicConstants.SCHEMA_NAME.equalsIgnoreCase("gangaram")) {
                        if(HISConstants.HISDischargeParametersMap.containsKey("hisdischargedate") && HISConstants.HISDischargeParametersMap.containsKey("hisdischargetime")) {
                            String hisDischargeDate = babiesDischargeStatusResultSet.getString(HISConstants.HISDischargeParametersMap.get("hisdischargedate"));
                            String hisDischargeTime = babiesDischargeStatusResultSet.getString(HISConstants.HISDischargeParametersMap.get("hisdischargetime"));
							System.out.println("hisDischargeDate --------> " + hisDischargeDate);
							System.out.println("hisDischargeTime --------> " + hisDischargeTime);

                            if(BasicConstants.SCHEMA_NAME.equalsIgnoreCase("gangaram")) {
                                if(!BasicUtils.isEmpty(hisDischargeDate)) {
                                    if (BasicUtils.isEmpty(hisDischargeTime)) {
                                        hisDischargeTime = "00:00:00";
                                    }
                                }
                            }
                           

                            if(!BasicUtils.isEmpty(hisDischargeDate) && !BasicUtils.isEmpty(hisDischargeTime)) {
                                DateFormat readFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                java.util.Date presentDate = readFormat.parse(hisDischargeDate + " " + hisDischargeTime);
                                sqlPresentDate = new Timestamp(presentDate.getTime());
                            }
                        }
                    } else if(BasicConstants.SCHEMA_NAME.equalsIgnoreCase("rainbow")) {
                        if(HISConstants.HISDischargeParametersMap.containsKey("hisdischargedate")) {
                            String hisDischargeDate = babiesDischargeStatusResultSet.getString(HISConstants.HISDischargeParametersMap.get("hisdischargedate"));
                            if(!BasicUtils.isEmpty(hisDischargeDate)) {
                                DateFormat readFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                java.util.Date presentDate = readFormat.parse(hisDischargeDate);
                                sqlPresentDate = new Timestamp(presentDate.getTime());
                            }
                        }
                    }

					if(HISConstants.HISDischargeParametersMap.containsKey("uhid")) {
						uhid = babiesDischargeStatusResultSet.getString(HISConstants.HISDischargeParametersMap.get("uhid"));
						System.out.println("uhid --------> " + uhid);
					}

					if(!BasicUtils.isEmpty(uhid) && !BasicUtils.isEmpty(sqlPresentDate) && !BasicUtils.isEmpty(hisDischargeStatus)) {
						 if(BasicConstants.SCHEMA_NAME.equalsIgnoreCase("apollo")) {
                         	if(hisDischargeStatus.equalsIgnoreCase("Normal")) {
                         		hisDischargeStatus = "Discharge";
                         	}
                         	if(hisDischargeStatus.equalsIgnoreCase("DAMA")) {
                         		hisDischargeStatus = "LAMA";
                         	}
                         }
						updateQuery = "update baby_detail set hisdischargedate = '"+sqlPresentDate+"', hisdischargestatus = '"+hisDischargeStatus+"' where uhid ='"+ uhid +"'";
						patientDao.updateOrDeleteNativeQuery(updateQuery);
						dischargeTimestampMap.put(uhid, sqlPresentDate);
						isDisconnectDevice = true;
					}

                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (Exception e) {
					e.printStackTrace();
				}
			}
            if(isDisconnectDevice){
				disconnectAllDevice(dischargeTimestampMap);
			}
        }
    }

    private void disconnectAllDevice(Map<String, Timestamp> dischargeTimestampMap) {
        try {
			dischargeTimestampMap.forEach((k,v)->{
				String updateQuery = "update bed_device_detail set status = 'false', time_to = '" + v + "' where status='true' and uhid ='"
						+ k + "'";
				patientDao.updateOrDeleteNativeQuery(updateQuery);
			});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public List fetchBabiesUHIDWithBlankHISDischargeDate() throws Exception{
		String queryStr = "select obj.uhid from " + BasicConstants.SCHEMA_NAME +""+".baby_detail obj where hisdischargedate IS NULL ";
		List<String> babyUhidsList = patientDao.getListFromNativeQuery(queryStr);
		return babyUhidsList;
	}

    private List<RefBed> fetchEmptyBedsList(String branchName) {
		String queryStr = "select obj from  RefBed as obj "
				+ " WHERE "
				+ " status='t' and isactive='true' and branchname= '" + branchName
				+ "' order by bedid asc ";
		List<RefBed>emptyBedObjList = null;

		emptyBedObjList = patientDao.getListFromMappedObjNativeQuery(queryStr);

		return emptyBedObjList;	

	}

	private void insertPatients(ResultSet currentBabiesData, int newBabiesCount) throws Exception {
		System.out.println("Insert patients initiated,  No. of new babies to be inserted" + newBabiesCount);

		int index = 0;
		while(currentBabiesData.next())
		{
			// Save baby details
			BabyDetail babyDetailObject = new BabyDetail();

			String babyBranchName = "";
			String branchName = "";
			if(HISConstants.HISParametersMap.containsKey("branchname")){
				babyBranchName = currentBabiesData.getString(HISConstants.HISParametersMap.get("branchname"));
				if(!BasicUtils.isEmpty(babyBranchName)){
					if(babyBranchName.equalsIgnoreCase("Cradle Koramangala")) {
						babyDetailObject.setBranchname("Koramangala, Bangalore");
						branchName = "Koramangala, Bangalore";
					}
					else if(babyBranchName.equalsIgnoreCase("Cradle Kondapur")) {
						babyDetailObject.setBranchname("Kondapur, Hyderabad");
						branchName = "Kondapur, Hyderabad";
					}
					else if(babyBranchName.equalsIgnoreCase("Cradle Marathahalli")) {
						babyDetailObject.setBranchname("Marathahalli, Bangalore");
						branchName = "Marathahalli, Bangalore";
					}
					else if(babyBranchName.equalsIgnoreCase("Cradle Nehru Enclave")) {
						babyDetailObject.setBranchname("Nehru Enclave, Delhi");
						branchName = "Nehru Enclave, Delhi";
					}
					else if(babyBranchName.equalsIgnoreCase("Cradle Shivaji Marg")) {
						babyDetailObject.setBranchname("Moti Nagar, Delhi");
						branchName = "Moti Nagar, Delhi";
					}
					else if(babyBranchName.equalsIgnoreCase("Cradle Jayanagar")) {
						babyDetailObject.setBranchname("Jayanagar, Bangalore");	
						branchName = "Jayanagar, Bangalore";
					}
					else if(babyBranchName.equalsIgnoreCase("Cradle Amritsar")) {
						babyDetailObject.setBranchname("Amritsar, Amritsar");	
						branchName = "Amritsar, Amritsar";
					}
					else if(babyBranchName.equalsIgnoreCase("Cradle Jubilee Hills")) {
						babyDetailObject.setBranchname("Jubilee Hills, Hyderabad");	
						branchName = "Jubilee Hills, Hyderabad";
					}
				}
			}
			
			if(BasicConstants.SCHEMA_NAME.equalsIgnoreCase("gangaram")) {
				babyDetailObject.setBranchname("Gangaram, Delhi");	
				branchName = "Gangaram, Delhi";
			}
			if(BasicConstants.SCHEMA_NAME.equalsIgnoreCase("kalawati")) {
				babyDetailObject.setBranchname("Kalawati, Rewari");	
				branchName = "Kalawati, Rewari";
			}
			if(BasicConstants.SCHEMA_NAME.equalsIgnoreCase("rainbow")) {
				babyDetailObject.setBranchname("Rainbow, Banjara Hills, Hyderabad");	
				branchName = "Rainbow, Banjara Hills, Hyderabad";
			}
			
			//create new beds, as reqd from current available free beds
			List<RefBed> currentEmptyBedsObjectsList = fetchEmptyBedsList(branchName);

			if(currentEmptyBedsObjectsList==null || currentEmptyBedsObjectsList.isEmpty()){
				synchronized (branchName){
					createNewBeds(1, branchName);
				}
			}		
			
			List<RefBed>emptyBedObjectsList = fetchEmptyBedsList(branchName);
			
			if(emptyBedObjectsList!=null && emptyBedObjectsList.size()>0) {

				String babyUhid = currentBabiesData.getString(HISConstants.HISParametersMap.get("uhid"));
				babyDetailObject.setUhid(babyUhid);
				String babyName = "";
				if(HISConstants.HISParametersMap.containsKey("babyname")){
					babyName = currentBabiesData.getString(HISConstants.HISParametersMap.get("babyname"));
					if(!(babyName.contains("B/O") || babyName.contains("Baby Of")))
						babyDetailObject.setBabyname("B/O " + babyName);
					else
						babyDetailObject.setBabyname(babyName);
	
				}
				String babygender = "";
				if(HISConstants.HISParametersMap.containsKey("babygender")){
					babygender = currentBabiesData.getString(HISConstants.HISParametersMap.get("babygender"));
					if(babygender.equalsIgnoreCase("m") || babygender.equalsIgnoreCase("male"))
						babyDetailObject.setGender("Male");
					else if(babygender.equalsIgnoreCase("f") || babygender.equalsIgnoreCase("female"))
						babyDetailObject.setGender("Female");
					else {
						babyDetailObject.setGender("Unknown");
					}
				}
	
				String birthDate = "";
				String birthTime = "";
				String admissionDate = "";
				String admissionTime = "";
				if(HISConstants.HISParametersMap.containsKey("babydob")) {
					int columnIndex = currentBabiesData.findColumn(HISConstants.HISParametersMap.get("babydob"));
					String columnType = currentBabiesData.getMetaData().getColumnTypeName(columnIndex);
					if(columnType.equalsIgnoreCase("datetime")) {
						String dateTime = currentBabiesData.getString(columnIndex);
						String arrayValues[] = dateTime.split(" ");
	
						birthDate = dateTime.split(" ")[0];
						if(arrayValues.length>1) {
							birthTime = getTimeAmPmFormat(dateTime.split(" ")[1]);
						}				
					} else if(columnType.equalsIgnoreCase("date")) {
						birthDate = currentBabiesData.getString(HISConstants.HISParametersMap.get("babydob"));					
					} else {
						birthDate = currentBabiesData.getString(HISConstants.HISParametersMap.get("babydob"));
					}
					if(!BasicUtils.isEmpty(birthDate)) {
						System.out.println(birthDate + "Initial DOB");
						babyDetailObject.setDobStr(birthDate);
						System.out.println(birthDate + "Second DOB");
						java.util.Date dobDate = CalendarUtility.dateFormatDB.parse(babyDetailObject.getDobStr());
						System.out.println(dobDate + "Third DOB");
						Date dateofbirth = new Date(dobDate.getTime() + 19800000);
						System.out.println(dateofbirth + "Fourth DOB");

						babyDetailObject.setDateofbirth(dateofbirth);
					}
					if(!BasicUtils.isEmpty(birthTime)) {
						babyDetailObject.setTimeofbirth(birthTime);
					}					
				}
	
				if(HISConstants.HISParametersMap.containsKey("babydoa")) {
					int columnIndex = currentBabiesData.findColumn(HISConstants.HISParametersMap.get("babydoa"));
					String columnType = currentBabiesData.getMetaData().getColumnTypeName(columnIndex);
					if(columnType.equalsIgnoreCase("datetime")) {
						String dateTime = currentBabiesData.getString(columnIndex);
						String arrayValues[] = dateTime.split(" ");					
						admissionDate = arrayValues[0];
						if(arrayValues.length>1) {
							admissionTime = getTimeAmPmFormat(arrayValues[1]);
						}				
					} else if(columnType.equalsIgnoreCase("date")) {
						admissionDate = currentBabiesData.getString(HISConstants.HISParametersMap.get("babydoa"));					
					} else {
						admissionDate = currentBabiesData.getString(HISConstants.HISParametersMap.get("babydoa"));
					}		
					if(!BasicUtils.isEmpty(admissionDate)) {
						System.out.println(admissionDate + "Initial DOA");

						babyDetailObject.setDoaStr(admissionDate);
						System.out.println(admissionDate + "Second DOA");

						java.util.Date doaDate = CalendarUtility.dateFormatDB.parse(admissionDate);
						System.out.println(doaDate + "Third DOA");
						Date dateofadmission =  new Date(doaDate.getTime() + 19800000);

						System.out.println(dateofadmission + "Fourth Date");

						babyDetailObject.setDateofadmission(dateofadmission);
						
					}
					if(!BasicUtils.isEmpty(admissionTime)) {
						babyDetailObject.setTimeofadmission(admissionTime);
					}
				}
	
				String babyAdmissionType = "";
				if(HISConstants.HISParametersMap.containsKey("admissionType")){
					babyAdmissionType = currentBabiesData.getString(HISConstants.HISParametersMap.get("admissionType"));
					if(!BasicUtils.isEmpty(babyAdmissionType)){
						if(babyAdmissionType.equalsIgnoreCase("inborn"))
							babyDetailObject.setInoutPatientStatus("In Born");
						else if(babyAdmissionType.equalsIgnoreCase("outborn"))
							babyDetailObject.setInoutPatientStatus("Out Born");				
					}
				}
				
				if(HISConstants.HISParametersMap.containsKey("ipnumber")){
					String epiNumber = currentBabiesData.getString(HISConstants.HISParametersMap.get("ipnumber"));
					babyDetailObject.setIpnumber(epiNumber);
				}
	
				if(BasicConstants.SCHEMA_NAME.equalsIgnoreCase("apollo")) {
					if(HISConstants.HISParametersMap.containsKey("visitno")){
						String visitno = currentBabiesData.getString(HISConstants.HISParametersMap.get("visitno"));
						babyDetailObject.setIpnumber(visitno);
					}
				}
				
				
				// setting logged user as test
				babyDetailObject.setLoggeduser("test");
				babyDetailObject.setTimeofbirth("06,00,AM");
				babyDetailObject.setTimeofadmission("09,00,AM");
				
				if(HISConstants.HISParametersMap.containsKey("babytoa")){
					String timeofadmission = currentBabiesData.getString(HISConstants.HISParametersMap.get("babytoa"));
					if(!BasicUtils.isEmpty(timeofadmission)){
						String finalTimeOfAdmission = getTimeAmPmFormat(timeofadmission);
						if(!BasicUtils.isEmpty(finalTimeOfAdmission)) {
							babyDetailObject.setTimeofadmission(finalTimeOfAdmission);
						}	
					}
				}
				
				if(HISConstants.HISParametersMap.containsKey("babytob")){
					String timeofbirth = currentBabiesData.getString(HISConstants.HISParametersMap.get("babytob"));
					if(!BasicUtils.isEmpty(timeofbirth)){
						String finalTimeOfBirth = getTimeAmPmFormat(timeofbirth);
						if(!BasicUtils.isEmpty(finalTimeOfBirth)) {
							babyDetailObject.setTimeofbirth(finalTimeOfBirth);
						}	
					}
				}
				
				ParentDetail personalDetailObj = new ParentDetail();
				personalDetailObj.setUhid(babyDetailObject.getUhid());
	
				if(HISConstants.HISParametersMap.containsKey("emailid")) {
					String emailid = currentBabiesData.getString(HISConstants.HISParametersMap.get("emailid"));
					if(!BasicUtils.isEmpty(emailid))
						personalDetailObj.setEmailid(emailid);
				}
				if(HISConstants.HISParametersMap.containsKey("primaryno")) {
					String primaryNo = currentBabiesData.getString(HISConstants.HISParametersMap.get("primaryno"));
					if(!BasicUtils.isEmpty(primaryNo)) {
						primaryNo.replaceAll("-", "");
						int len = primaryNo.length();
						if (len > 10 && primaryNo.contains("+")) {
							primaryNo = primaryNo.substring(3, len);
						}
						personalDetailObj.setPrimaryphonenumber(primaryNo);
						personalDetailObj.setMother_phone(primaryNo);
					}
	
				}
				if(HISConstants.HISParametersMap.containsKey("secondaryno")) {
					String secondaryNo = currentBabiesData.getString(HISConstants.HISParametersMap.get("secondaryno"));
					if(!BasicUtils.isEmpty(secondaryNo)) {
						secondaryNo.replaceAll("-", "");
						personalDetailObj.setSecondaryphonenumber(secondaryNo);
					}
	
				}
	
				if(HISConstants.HISParametersMap.containsKey("address")) {
					String address = currentBabiesData.getString(HISConstants.HISParametersMap.get("address"));
					personalDetailObj.setAddress(address);
				}
				if(HISConstants.HISParametersMap.containsKey("city")) {
					String city = currentBabiesData.getString(HISConstants.HISParametersMap.get("city"));
					personalDetailObj.setAdd_city(city);
				}
				if(HISConstants.HISParametersMap.containsKey("state")) {
					String state = HISConstants.HISParametersMap.get("state");
					personalDetailObj.setAdd_state(state);
				}

				RefBed emptyBed = emptyBedObjectsList.get(0);
				String nicuBedNo = emptyBed.getBedid();
				String nicuRoomNo = emptyBed.getRoomid();
				babyDetailObject.setNicubedno(nicuBedNo);
				babyDetailObject.setNicuroomno(nicuRoomNo);
				
				babyDetailObject.setActivestatus(true);
				babyDetailObject.setAdmissionstatus(true);

				try {
					System.out.println("[" + BasicConstants.timeStampFormat.format(new Timestamp(System.currentTimeMillis())) + "] :: Patient Admitting from HIS "+babyDetailObject.toString());
	
					babyDetailObject = (BabyDetail) patientDao.saveObject(babyDetailObject);

					String mailContent = "<p>New Baby "+ babyDetailObject.getBabyname() +" is registered(From HIS) with UHID: " + babyDetailObject.getUhid()
							+ "<br><br>\n "
							+ "Regards<br>"
							+ "\n iNICU Team</p>";

					settingsService.sendCustomEmail("New Baby Admission",mailContent,babyDetailObject.getBranchname(),BasicConstants.BABY_ADMIT);

					// save  the same patient in ICHR database also if BranchName is Moti Nagar
					if(babyDetailObject.getBranchname().equalsIgnoreCase("Moti Nagar, Delhi")) {
						System.out.println("<---------  registering the baby with ICHR Application ------------->");
						saveBabyInICHRDatabase(babyDetailObject, personalDetailObj);
						System.out.println("<---------  Registration done ------------->");
					}

					if(babyDetailObject.getBabydetailid()!=null) {
						personalDetailObj.setEpisodeid(babyDetailObject.getEpisodeid());
						personalDetailObj.setMothername(babyName);
						personalDetailObj = (ParentDetail) patientDao.saveObject(personalDetailObj);
						emptyBed.setStatus(false);
						emptyBed = (RefBed)patientDao.saveObject(emptyBed);
						index++;					
					}
	
				} catch (ParseException | InicuDatabaseExeption e) {
					e.printStackTrace();
				}
			}
		}

	}

	private void saveBabyInICHRDatabase(BabyDetail babyDetail,ParentDetail parentDetail){
		// now add the same baby to the ICHR database
		try{
			String companyId = BasicConstants.ICHR_SCHEMA;
			if(companyId!=null&&!companyId.isEmpty()){
				// for patient table
				String Uhid= babyDetail.getUhid().trim();
				String fname = babyDetail.getBabyname().trim();

				if(!BasicUtils.isEmpty(fname)){
					fname = fname.replaceAll(" ","%20");
				}

				String gender = babyDetail.getGender().trim();
				String DOB = babyDetail.getDateofbirth().toString().trim();
				String lname = "";

				String phoneNumber = "";
				if(!BasicUtils.isEmpty(parentDetail.getPrimaryphonenumber())){
					phoneNumber = parentDetail.getPrimaryphonenumber().trim();
				}else if(!BasicUtils.isEmpty(parentDetail.getSecondaryphonenumber())){
					phoneNumber = parentDetail.getSecondaryphonenumber().trim();
				}

				String email = "";
				if(!BasicUtils.isEmpty(parentDetail.getEmailid())){
					email = parentDetail.getEmailid().trim();
				}

				String parameters ="uid="+Uhid+"&companyId="+companyId+"&fname="+fname+"&lname="+lname+"&gender="+gender+"&DOB="+DOB+"&phonenumber="+phoneNumber+"&email="+email;
				System.out.println("Parameters passed :"+ parameters);
				String response = SimpleHttpClient.executeHttpGet(BasicConstants.ADMIT_PATIENT+parameters);
				System.out.println("Post Result string returned"+response);
			}

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private void createNewBeds(int newBedsReqd, String branchName) {
		// create newBedsReqd new beds
		System.out.println("No. of new beds to be created " + newBedsReqd);
		String queryLastBed = "SELECT obj from RefBed as obj where branchname= '" + branchName + "' order by bedid desc ";
		List<RefBed>listLastOccupedBed = patientDao.getListFromMappedObjNativeQuery(queryLastBed);
		RefBed lastOccupiedBed = listLastOccupedBed.get(0);
		
		Integer lastBedIdVal = Integer.parseInt(lastOccupiedBed.getBedid().substring(2));
		String lastBedName= lastOccupiedBed.getBedname();
		Integer lastRoomNo  = Integer.parseInt(lastOccupiedBed.getRoomid().substring(2));
		Integer lastBedVal = Integer.parseInt(lastBedName.substring(4));
		
		for (int i = 1; i <=newBedsReqd; i++) {
			String newBedId = String.format("BD%010d", lastBedIdVal+i);
			String newBedName = String.format("BED %d", lastBedVal+i);
			
			// keeping room no same
			String newBedDescription = String.format("NICU ROOM NO %d - BED NO %d", lastRoomNo,lastBedVal+i);
			String newRoomid = String.format("RM%010d", lastRoomNo);
			
			RefBed newBed = new RefBed();
			newBed.setBedid(newBedId);
			newBed.setBedname(newBedName);
			newBed.setRoomid(newRoomid);
			newBed.setStatus(true);
			newBed.setDescription(newBedDescription);
			newBed.setIsactive(true);
			newBed.setLoggedUser("test");
			newBed.setBranchname(branchName);
			System.out.println("Bed to be created " + newBed.toString());

			try {
				patientDao.saveObject(newBed);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// List of uhids currently in iNICU application
	public List fetchBabiesUHID() throws Exception{
		String queryStr = "select obj.uhid from " + BasicConstants.SCHEMA_NAME +""+".baby_detail obj where activestatus='t' ";
		List<String>babyUhidsList = null;
		babyUhidsList = patientDao.getListFromNativeQuery(queryStr);
		if(babyUhidsList!=null && !babyUhidsList.isEmpty()){
			System.out.println("Current Uhids in iNICU Not Null");
		}
		return babyUhidsList;
	}

	public String getTimeAmPmFormat(String inputString) {
		/*
		 * inputString: 12:15:00 (24 hour format)
		 * outputString: 12,15,PM 
		 */
		String formattedString = "";
		String charArray[] = inputString.split(":");
		if(charArray.length>=1) {
			int hours = Integer.parseInt(charArray[0]);
			int mins = Integer.parseInt(charArray[1]);

			String meridiem = "";
			if(hours<12) {
				if(hours<10)
					formattedString += "0"+String.valueOf(hours);
				else
					formattedString += String.valueOf(hours);
				meridiem = "AM";
			} else if(hours==12) {
				formattedString += String.valueOf(hours);
				meridiem = "PM";
			} else {
				hours -= 12;
				if(hours<10)
					formattedString += "0"+String.valueOf(hours);
				else
					formattedString += String.valueOf(hours);
				meridiem = "PM";
			}
			formattedString += ",";
			if(mins<10) {
				formattedString += "0" + String.valueOf(mins);				
			} else {
				formattedString += String.valueOf(mins);
			}
			formattedString += ","+meridiem;			
		}


		return formattedString;
	}

    public List fetchBabiesUHIDWithBlankIPNumnber() throws Exception{
        String queryStr = "select obj.uhid from " + BasicConstants.SCHEMA_NAME +".baby_detail obj where ipnumber is null or ipnumber = '' ";
        List<String> babyUhidsList = patientDao.getListFromNativeQuery(queryStr);
        return babyUhidsList;
    }

    private void updateIpNumber(ResultSet fetchIpNumberResultSet) throws SQLException {
	    if (!BasicUtils.isEmpty(fetchIpNumberResultSet)) {
			String updateQuery = "";
			String babyUhid = "";
			String ipNumber = "";
			while (fetchIpNumberResultSet.next()) {

				babyUhid = fetchIpNumberResultSet.getString(HISConstants.HISParametersMap.get("uhid"));
				if(HISConstants.HISParametersMap.containsKey("ipnumber")){
					ipNumber = fetchIpNumberResultSet.getString(HISConstants.HISParametersMap.get("ipnumber"));
					updateQuery = "update baby_detail set ipnumber = '" + ipNumber + "' where uhid ='" + babyUhid + "'";
					patientDao.updateOrDeleteNativeQuery(updateQuery);
				}
            }

        }
	}

}
