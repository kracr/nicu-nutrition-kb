package com.inicu.postgres.serviceImpl;

import java.math.BigInteger;
import java.sql.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.mail.Flags;
import javax.mail.Message.RecipientType;
import javax.persistence.Basic;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.SqlResultSetMapping;
import javax.servlet.http.HttpSession;
import java.text.DecimalFormat;

//import com.inicu.postgres.entities.AdmissionMedication;
import ca.uhn.hl7v2.model.v21.datatype.CM;
import ch.qos.logback.core.net.SyslogOutputStream;
import com.google.gson.JsonObject;
import com.inicu.models.*;
import com.inicu.postgres.entities.*;
import com.inicu.postgres.service.UserPanelService;
import com.sun.mail.handlers.message_rfc822;
import com.sun.org.apache.xpath.internal.axes.HasPositionalPredChecker;
import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.cookie.DateParseException;
import org.hibernate.annotations.NamedNativeQuery;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inicu.postgres.dao.InicuDao;
import com.inicu.postgres.dao.NotesDAO;
import com.inicu.postgres.dao.UserServiceDAO;
import com.inicu.postgres.service.LogsService;
import com.inicu.postgres.service.NotesService;
import com.inicu.postgres.service.PatientService;
import com.inicu.postgres.utility.BasicConstants;
import com.inicu.postgres.utility.BasicUtils;
import com.inicu.postgres.utility.CalendarUtility;
import com.inicu.postgres.utility.HqlSqlQueryConstants;
import com.inicu.postgres.utility.InicuDatabaseExeption;
import com.inicu.postgres.utility.InicuPostgresUtililty;
import scala.Function;
import scala.collection.mutable.StringBuilder;

import static java.lang.Float.parseFloat;
import java.text.DecimalFormat;
import java.util.stream.Stream;

@Repository
public class NotesServiceImp implements NotesService {
	ObjectMapper mapper = new ObjectMapper();

	@Autowired
	InicuPostgresUtililty inicuPostgersUtil;

	@Autowired
	NotesDAO notesDoa;

	@Autowired
	LogsService logService;

	@Autowired
	private InicuDatabaseExeption databaseException;

	@Autowired
	HttpSession httpSession;

	@Autowired
	InicuDao inicuDoa;

//	@Autowired
//	PatientService patientService;
	// Vikash workspace

	@Autowired
	UserServiceDAO userServiceDao;

	@Autowired
	SystematicServiceImpl systematicService;

	@Autowired
	UserPanelService userPanel;

	@Autowired
	TestsServiceImpl testsService;

	private String uhid = "";
	private String entryDate = "";
	private String loggedInUser = "";
	static String htmlLine = System.getProperty("line.separator");// "&#13;&#10;";


	private boolean checkListContainsNullOnly(List<Float> mylist){
		for(int i=0;i<mylist.size();i++){
			if(mylist.get(i)!=null){
				return true;
			}
		}
		return false;
	}

	private PatientGraphData getGraphData(String myUhid,LinkedList<Integer> timeList,Timestamp fromDate,Timestamp toDate,
										  Timestamp fromDateWithoutOffset,Timestamp toDateWithoutOffset,List<NursingVitalparameter> vitalsParametersList){

		int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
				- TimeZone.getDefault().getRawOffset();

		// Graph Object
		PatientGraphData patientGraphData=new PatientGraphData();

		// For Central Temperature
		List<Float> CMap=new ArrayList<>();
		// For Peripheral Temperature
		List<Float> PMap=new ArrayList<>();
		// For HR
		List<Float> HRMap=new ArrayList<>();
		// For CVP
		List<Float> CVPMap=new ArrayList<>();
		// For RR
		List<Float> RRMap=new ArrayList<>();

		// For NIBP
		List<Float> nbp_s=new ArrayList<>();
		List<Float> nbp_d=new ArrayList<>();
		List<Float> nbp_m=new ArrayList<>();

		// For IBP
		List<Float> ibp_s=new ArrayList<>();
		List<Float> ibp_d=new ArrayList<>();
		List<Float> ibp_m=new ArrayList<>();

		List<Float> ibpDiaGraph=new ArrayList<>();
		List<Float> ibpMeanGraph=new ArrayList<>();
		List<Float> ibpSysGraph=new ArrayList<>();

		List<Float> nbpDiaGraph=new ArrayList<>();
		List<Float> nbpMeanGraph=new ArrayList<>();
		List<Float> nbpSysGraph=new ArrayList<>();

		int cmapFlag=0;
		int pmapFlag=0;

		int hrFlag=0;
		int cvpFlag=0;
		int rrFlag=0;

		int Nbp_sFlag=0;
		int Nbp_dFlag=0;
		int Nbp_mFlag=0;

		int Ibp_sFlag=0;
		int Ibp_dFlag=0;
		int Ibp_mFlag=0;

		System.out.println("Graph data");

		List<NursingVitalparameter> nursingVitalparameterList=vitalsParametersList;

		for(int k=0;k<24;k++){
			CMap.add(null);
			PMap.add(null);
			CVPMap.add(null);
		}

		if(!BasicUtils.isEmpty(nursingVitalparameterList) && nursingVitalparameterList.size()>0){

			for(int i=0;i<nursingVitalparameterList.size();i++) {

				NursingVitalparameter myVitalObject = nursingVitalparameterList.get(i);
				int hour=myVitalObject.getEntryDate().getHours();
				int index=timeList.indexOf(hour);
				if(hour<=23 && hour>=8){
					index=hour-8;
				}else if(hour>=0 && hour<=7){
					index=hour+16;
				}

				// Central Temperature
				if(CMap.size()!=0) {
					if (!BasicUtils.isEmpty(myVitalObject.getCentraltemp())) {
						if(myVitalObject.getCentralTempUnit().equalsIgnoreCase("F")){
							CMap.set(index,(myVitalObject.getCentraltemp()-32)*5/9);
						}else{
							CMap.set(index,myVitalObject.getCentraltemp());
						}
						cmapFlag = 1;
					}
				}

				// Peripheral Temperature
				if(PMap.size()!=0){
					if (!BasicUtils.isEmpty(myVitalObject.getPeripheraltemp())) {
						if(myVitalObject.getPeripheralTempUnit().equalsIgnoreCase("F")){
							PMap.set(index,(myVitalObject.getPeripheraltemp()-32)*5/9);
						}else{
							PMap.set(index,myVitalObject.getPeripheraltemp());
						}
						pmapFlag=1;
					}
				}

				// CVP
				if(CVPMap.size()!=0){
					if (!BasicUtils.isEmpty(myVitalObject.getCvp())) {
						CVPMap.set(index,Float.parseFloat(myVitalObject.getCvp()));
						cvpFlag=1;
					}
				}
			}
		}

		// CMAP
		if(cmapFlag==1) {
			patientGraphData.setCentralTemperature(CMap);
			if(BasicUtils.maxValueFromList(CMap)!=null) {
				patientGraphData.setMaxCentralTemperature(BasicUtils.maxValueFromList(CMap));
			}
			if(BasicUtils.minValueFromList(CMap)!=null) {
				patientGraphData.setMinCentralTemperature(BasicUtils.minValueFromList(CMap));
			}
		}else{
			patientGraphData.setCentralTemperature(new ArrayList<>());
		}

		// PMAP
		if(pmapFlag==1) {
			patientGraphData.setPeripheralTemperature(PMap);
			if(BasicUtils.maxValueFromList(PMap)!=null) {
				patientGraphData.setMaxPeripheralTemperature(BasicUtils.maxValueFromList(PMap));
			}
			if(BasicUtils.minValueFromList(PMap)!=null) {
				patientGraphData.setMinPeripheralTemperature(BasicUtils.minValueFromList(PMap));
			}
		}else{
			patientGraphData.setPeripheralTemperature(new ArrayList<>());
		}

		// CVP
		if(cvpFlag==1) {
			patientGraphData.setCvp(CVPMap);
			if(BasicUtils.maxValueFromList(CVPMap)!=null) {
				patientGraphData.setMaxCvp(BasicUtils.maxValueFromList(CVPMap));
			}
			if(BasicUtils.minValueFromList(CVPMap)!=null) {
				patientGraphData.setMinCvp(BasicUtils.minValueFromList(CVPMap));
			}
		}else{
			patientGraphData.setCvp(new ArrayList<>());
		}

		// for Graph
		// Minus half hour from the from Date to fetch the details of vital parameters as per the condtions
		Timestamp NewSubtractedFromDate=new Timestamp(fromDate.getTime() - 1800000);

		String getGraphData=HqlSqlQueryConstants.getPatientGraphData(myUhid,NewSubtractedFromDate,toDate);
		List<DeviceMonitorDetail> vitalsGraphList=inicuDoa.getListFromMappedObjQuery(getGraphData);

		if(!BasicUtils.isEmpty(vitalsGraphList) && vitalsGraphList.size()>0) {


            for(int k=0;k<24;k++){
                HRMap.add(null);
                RRMap.add(null);
                ibp_s.add(null);
                ibp_d.add(null);
                ibp_m.add(null);
                nbp_s.add(null);
                nbp_d.add(null);
                nbp_m.add(null);
            }

			IBPDataObject ibpDataObject=new IBPDataObject();
            NIBPDataObject nibpDataObject=new NIBPDataObject();

			for(int timeIndex=0;timeIndex<timeList.size();timeIndex++) {

					int startTime=7+timeIndex;
					int endTime=startTime+1;

					if(startTime > 23 ){
						startTime= startTime-23;
						endTime=startTime+1;
					}


					int distanceValue = -1 ;

					for(int k=0;k<vitalsGraphList.size();k++) {

						DeviceMonitorDetail myGraphObject = vitalsGraphList.get(k);
						Timestamp entryTimeWithOffset = myGraphObject.getStarttime();
						int hour = entryTimeWithOffset.getHours();
						int minutes =entryTimeWithOffset.getMinutes();

						int tempDistance = -1;
						boolean continueFlag = false;

						// check the condition
						if((hour == startTime && minutes >= 30) || (hour == endTime && minutes <= 30)){

							if(hour == startTime){
								tempDistance = 60 - minutes;
							}else if (hour == endTime){
								tempDistance = minutes;
							}

							if( distanceValue == -1){
								distanceValue = tempDistance;
								continueFlag = true;
							}else if( tempDistance <= distanceValue){
								distanceValue = tempDistance;
								continueFlag = true;
							}

							if(continueFlag) {
								// HR
								if (HRMap.size() != 0) {
									if (!BasicUtils.isEmpty(myGraphObject.getHeartrate())) {
										HRMap.set(timeIndex, Float.parseFloat(myGraphObject.getHeartrate()));
										hrFlag = 1;
									}
								}

								//RRMap
								if (RRMap.size() != 0) {
									if (!BasicUtils.isEmpty(myGraphObject.getEcgResprate())) {
										RRMap.set(timeIndex, Float.parseFloat(myGraphObject.getEcgResprate()));
										rrFlag = 1;
									}
								}

								//IBPMap

								// systolic
								if (ibp_s.size() != 0) {
									if (!BasicUtils.isEmpty(myGraphObject.getIbp_s())) {
										ibp_s.set(timeIndex, Float.parseFloat(myGraphObject.getIbp_s()));
										Ibp_sFlag = 1;
									}
								}

								// diatolic
								if (ibp_d.size() != 0) {
									if (!BasicUtils.isEmpty(myGraphObject.getIbp_d())) {
										ibp_d.set(timeIndex, Float.parseFloat(myGraphObject.getIbp_d()));
										Ibp_dFlag = 1;
									}
								}

								// mean
								if (ibp_m.size() != 0) {
									if (!BasicUtils.isEmpty(myGraphObject.getIbp_m())) {
										ibp_m.set(timeIndex, Float.parseFloat(myGraphObject.getIbp_m()));
										Ibp_mFlag = 1;
									}
								}


								// NIBP

								// systolic
								if (nbp_s.size() != 0) {
									if (!BasicUtils.isEmpty(myGraphObject.getNbp_s())) {
										nbp_s.set(timeIndex, Float.parseFloat(myGraphObject.getNbp_s()));
										Nbp_sFlag = 1;
									}
								}

								// diatolic
								if (nbp_d.size() != 0) {
									if (!BasicUtils.isEmpty(myGraphObject.getNbp_d())) {
										nbp_d.set(timeIndex, Float.parseFloat(myGraphObject.getNbp_d()));
										Nbp_dFlag = 1;
									}
								}

								// mean
								if (nbp_m.size() != 0) {
									if (!BasicUtils.isEmpty(myGraphObject.getNbp_m())) {
										nbp_m.set(timeIndex, Float.parseFloat(myGraphObject.getNbp_m()));
										Nbp_mFlag = 1;
									}
								}
							}
						}
					}
				}

			// Graph data for BP with the difference value
			for(int i=0;i<timeList.size();i++){

				// Difference between the mean and dia for inbp
				Float diaInbpValue=null;

				if(ibp_d!=null && i< ibp_d.size() && ibp_d.get(i)!=null){
					diaInbpValue=ibp_d.get(i);
				}

				ibpDiaGraph.add(diaInbpValue);

				Float meanInbpValue=null;

				if(ibp_m!=null && i< ibp_m.size() && ibp_m.get(i)!=null){

					if(ibp_d!=null && i< ibp_d.size() && ibp_d.get(i)!=null){
						meanInbpValue=ibp_m.get(i)-ibp_d.get(i);

						if(meanInbpValue<0){
							meanInbpValue=meanInbpValue*(-1);
						}
					}else{
						meanInbpValue=ibp_m.get(i);
					}
				}

				if(meanInbpValue==null){
					ibpMeanGraph.add(meanInbpValue);
				}else{
					ibpMeanGraph.add(meanInbpValue);
				}

				Float sysInbpValue=null;

				if(ibp_s!=null && i< ibp_d.size() && ibp_s.get(i)!=null){

					if(ibp_m!=null && i< ibp_m.size() && ibp_m.get(i)!=null){
						sysInbpValue=ibp_s.get(i)-ibp_m.get(i);

						if(sysInbpValue<0){
							sysInbpValue=sysInbpValue*(-1);
						}

					}else{
						sysInbpValue=ibp_s.get(i);
					}
				}

				if(sysInbpValue==null){
					ibpSysGraph.add(sysInbpValue);
				}else{
					ibpSysGraph.add(sysInbpValue);
				}
			}

            // Graph data for BP with the difference value
            for(int i=0;i<timeList.size();i++){

                // Difference between the mean and dia for nbp
                Float diaValue=null;
                if(nbp_d!=null  && i<nbp_d.size() && nbp_d.get(i)!=null){
                    diaValue=nbp_d.get(i);
                }

                nbpDiaGraph.add(diaValue);

                Float meanValue=null;

                if(nbp_m!=null && i<nbp_m.size() && nbp_m.get(i)!=null){

                    if(nbp_d!=null && i<nbp_d.size() && nbp_d.get(i)!=null){
                        meanValue=nbp_m.get(i)-nbp_d.get(i);
                        if(meanValue<0){
                            meanValue=meanValue*(-1);
                        }
                    }else{
                        meanValue=nbp_m.get(i);
                    }
                }

                if(meanValue==null){
                    nbpMeanGraph.add(meanValue);
                }else{
                    nbpMeanGraph.add(meanValue);
                }

                Float sysValue=null;

                if(nbp_s!=null && i<nbp_s.size() && nbp_s.get(i)!=null){

                    if(nbp_m!=null && i<nbp_m.size() && nbp_m.get(i)!=null){
                        sysValue=nbp_s.get(i)-nbp_m.get(i);
                        if(sysValue<0){
                            sysValue=sysValue*(-1);
                        }
                    }else{
                        sysValue=nbp_s.get(i);
                    }
                }

                if(sysValue==null){
                    nbpSysGraph.add(sysValue);
                }else{
                    nbpSysGraph.add(sysValue);
                }
            }

			// Check condition for not null value in array then insert it

			// HR
			if(hrFlag==1) {
				patientGraphData.setHr(HRMap);
				if(BasicUtils.maxValueFromList(HRMap)!=null) {
					patientGraphData.setMaxHr(BasicUtils.maxValueFromList(HRMap));
				}
				if(BasicUtils.maxValueFromList(HRMap)!=null) {
					patientGraphData.setMinHr(BasicUtils.minValueFromList(HRMap));
				}
			}else{
				patientGraphData.setHr(new ArrayList<>());
			}

			// RR
			if(rrFlag==1) {
				patientGraphData.setRr(RRMap);
				if(BasicUtils.maxValueFromList(RRMap)!=null) {
					patientGraphData.setMaxRr(BasicUtils.maxValueFromList(RRMap));
				}
				if(BasicUtils.maxValueFromList(RRMap)!=null) {
					patientGraphData.setMinRr(BasicUtils.minValueFromList(RRMap));
				}
			}else{
				patientGraphData.setRr(new ArrayList<>());
			}

			// ibp_s
			if(Ibp_sFlag==1) {
				ibpDataObject.setIbp_s(ibp_s);
				ibpDataObject.setIbpSysGraph(ibpSysGraph);
				if(BasicUtils.maxValueFromList(ibp_s)!=null) {
					ibpDataObject.setMaxIbp_s(BasicUtils.maxValueFromList(ibp_s));
				}
				if(BasicUtils.maxValueFromList(ibp_s)!=null) {
					ibpDataObject.setMinIbp_s(BasicUtils.minValueFromList(ibp_s));
				}
			}else{
				ibpDataObject.setIbp_s(new ArrayList<>());
				ibpDataObject.setIbpSysGraph(new ArrayList<>());
			}

			// ibp_d
			if(Ibp_dFlag==1) {
				ibpDataObject.setIbp_d(ibp_d);
				ibpDataObject.setIbpDiaGraph(ibpDiaGraph);
				if(BasicUtils.maxValueFromList(ibp_d)!=null) {
					ibpDataObject.setMaxIbp_d(BasicUtils.maxValueFromList(ibp_d));
				}
				if(BasicUtils.maxValueFromList(ibp_d)!=null) {
					ibpDataObject.setMinIbp_d(BasicUtils.minValueFromList(ibp_d));
				}
			}else{
				ibpDataObject.setIbp_d(new ArrayList<>());
				ibpDataObject.setIbpDiaGraph(new ArrayList<>());
			}

			// ibp_m
			if(Ibp_mFlag==1) {
				ibpDataObject.setIbp_m(ibp_m);
				ibpDataObject.setIbpMeanGraph(ibpMeanGraph);

				if(BasicUtils.maxValueFromList(ibp_m)!=null) {
					ibpDataObject.setMaxIbp_m(BasicUtils.maxValueFromList(ibp_m));
				}
				if(BasicUtils.maxValueFromList(ibp_m)!=null) {
					ibpDataObject.setMinIbp_m(BasicUtils.minValueFromList(ibp_m));
				}
			}else{
				ibpDataObject.setIbp_m(new ArrayList<>());
				ibpDataObject.setIbpMeanGraph(new ArrayList<>());
			}

            // nbp_s
            if(Nbp_sFlag==1) {
                nibpDataObject.setNbp_s(nbp_s);
                nibpDataObject.setNbpSysGraph(nbpSysGraph);
				if(BasicUtils.maxValueFromList(nbp_s)!=null) {
					nibpDataObject.setMaxNbp_s(BasicUtils.maxValueFromList(nbp_s));
				}
				if(BasicUtils.maxValueFromList(nbp_s)!=null) {
					nibpDataObject.setMinNbp_s(BasicUtils.minValueFromList(nbp_s));
				}
            }else{
                nibpDataObject.setNbp_s(new ArrayList<>());
                nibpDataObject.setNbpSysGraph(new ArrayList<>());
            }

            // nbp_d
            if(Nbp_sFlag==1) {
                nibpDataObject.setNbp_d(nbp_d);
                nibpDataObject.setNbpDiaGraph(nbpDiaGraph);
				if(BasicUtils.maxValueFromList(nbp_d)!=null) {
					nibpDataObject.setMaxNbp_d(BasicUtils.maxValueFromList(nbp_d));
				}
				if(BasicUtils.maxValueFromList(nbp_d)!=null) {
					nibpDataObject.setMinNbp_d(BasicUtils.minValueFromList(nbp_d));
				}
            }else{
                nibpDataObject.setNbp_d(new ArrayList<>());
                nibpDataObject.setNbpDiaGraph(new ArrayList<>());
            }

            //  nbp_m
            if(Nbp_sFlag==1) {
                nibpDataObject.setNbp_m(nbp_m);
                nibpDataObject.setNbpMeanGraph(nbpMeanGraph);

				if(BasicUtils.maxValueFromList(nbp_m)!=null) {
					nibpDataObject.setMaxNbp_m(BasicUtils.maxValueFromList(nbp_m));
				}
				if(BasicUtils.maxValueFromList(nbp_m)!=null) {
					nibpDataObject.setMinNbp_m(BasicUtils.minValueFromList(nbp_m));
				}
            }else{
                nibpDataObject.setNbp_m(new ArrayList<>());
                nibpDataObject.setNbpMeanGraph(new ArrayList<>());
            }

            patientGraphData.setIbp(ibpDataObject);
            patientGraphData.setNibp(nibpDataObject);
		}

		return patientGraphData;
	}

	private PatientChartVentilatorObject getVentilatorData(String myUhid,LinkedList<Integer> timeList,Timestamp fromDate,
														   Timestamp toDate,List<Object[]> vitalsList,List<Object[]> VentilatorDataList){

		// variables
		List<Float> spo2=new ArrayList<>();
		List<Float> cft=new ArrayList<>();
		List<Float> sfRatio=new ArrayList<>();
		List<String> modeOfVentilation=new ArrayList<>();

		List<Float> pressureSupport=new ArrayList<>();

		List<Float> peep=new ArrayList<>();
		List<Float> ti=new ArrayList<>();
		List<Float> te=new ArrayList<>();
		List<Float> map=new ArrayList<>();
		List<Float> fio2=new ArrayList<>();
		List<Float> flow=new ArrayList<>();

		List<Float> frequency=new ArrayList<>();
		List<Float> minVolumne=new ArrayList<>();
		List<Float> tidalVolumne=new ArrayList<>();
		List<Float> dco2=new ArrayList<>();
		List<Float> deliveredTv=new ArrayList<>();
		List<Float> rpm=new ArrayList<>();
		List<Float> humidification=new ArrayList<>();

		// set the list
		PatientChartVentilatorObject patientChartVentilatorObject=new PatientChartVentilatorObject();

		List<Object[]> nursingVitalparameters=vitalsList;
		for (int i = 0; i < 24; i++) {
			cft.add(null);
			spo2.add(null);
		}

		if(!BasicUtils.isEmpty(nursingVitalparameters) && nursingVitalparameters.size()>0) {

			int j = 0;
			for (int k = 0; k < nursingVitalparameters.size(); k++) {

				Object[] tempVentilatorObject = nursingVitalparameters.get(k);

				Timestamp ventilatoryEntryDate = (Timestamp) tempVentilatorObject[0];
				int hour = ventilatoryEntryDate.getHours();

				int index = timeList.indexOf(ventilatoryEntryDate.getHours());

				if (hour <= 23 && hour >= 8) {
					index = hour - 8;
				} else if (hour >= 0 && hour <= 7) {
					index = hour + 16;
				}

				// CFT
				if (cft.size() != 0) {
					// CFT
					if (tempVentilatorObject[7] != null && !BasicUtils.isEmpty(tempVentilatorObject[7].toString())) {
						cft.set(index, parseFloat(tempVentilatorObject[7].toString()));
					}
				}

				// SPO2
				if (spo2.size() != 0) {

					// SPO2
					if (tempVentilatorObject[8] != null && !BasicUtils.isEmpty(tempVentilatorObject[8].toString())) {
						spo2.set(index, parseFloat(tempVentilatorObject[8].toString()));
					}
				}
			}
		}

		if(checkListContainsNullOnly(spo2)) {
			patientChartVentilatorObject.setSpo2(spo2);
		}else{
			patientChartVentilatorObject.setSpo2(new ArrayList<>());
		}

		if(checkListContainsNullOnly(cft)) {
			patientChartVentilatorObject.setCft(cft);
		}else{
			patientChartVentilatorObject.setCft(new ArrayList<>());
		}

		List<Object[]> nursingVentilatorList=VentilatorDataList;

		for(int i=0;i<24;i++){
			pressureSupport.add(null);
			modeOfVentilation.add(null);
			peep.add(null);
			ti.add(null);
			te.add(null);
			map.add(null);
			fio2.add(null);
			flow.add(null);
			frequency.add(null);
			tidalVolumne.add(null);
			minVolumne.add(null);
			rpm.add(null);
			dco2.add(null);
			humidification.add(null);
			sfRatio.add(null);
		}

		if(!BasicUtils.isEmpty(nursingVentilatorList) && nursingVentilatorList.size()>0) {

			// get Ventilator Mode
			String ventilatorModeQuery=HqlSqlQueryConstants.getVentilationMode();
			List<RefVentilationmode> refVentilationmodes=inicuDoa.getListFromMappedObjQuery(ventilatorModeQuery);

			HashMap<String,String> modes=new HashMap<>();
			for (RefVentilationmode refObj: refVentilationmodes) {
				modes.put(refObj.getVentmodeid(),refObj.getVentilationmode());
			}

			int j=0;
			for(int k=0;k<nursingVentilatorList.size();k++){

				Object[] tempVentilatorObject = nursingVentilatorList.get(k);
				Timestamp ventilatoryEntryDate = (Timestamp) tempVentilatorObject[0];
				int hour = ventilatoryEntryDate.getHours();

				int index=timeList.indexOf(ventilatoryEntryDate.getHours());
				if(hour<=23 && hour>=8){
					index=hour-8;
				}else if(hour>=0 && hour<=7){
					index=hour+16;
				}

				if(modeOfVentilation.size()!=0){
					// Mode Of Ventilation
					if (tempVentilatorObject[1] != null && modes.get(tempVentilatorObject[1].toString()) != null) {
						String ventmodes = "";
						//modeOfVentilation.add(modes.get(tempVentilatorObject[2].toString()));
						ventmodes += modes.get(tempVentilatorObject[1].toString()) + " ";
						if(modes.get(tempVentilatorObject[1]).toString().equalsIgnoreCase("PSV") ||
								modes.get(tempVentilatorObject[1]).toString().equalsIgnoreCase("IMV") ||
								modes.get(tempVentilatorObject[1]).toString().equalsIgnoreCase("SIPPV") ||
								modes.get(tempVentilatorObject[1]).toString().equalsIgnoreCase("SIMV")) {
							if(tempVentilatorObject[17] != null) {
								ventmodes += "+VG ";
							}
							if(tempVentilatorObject[16] != null && tempVentilatorObject[16].toString().equalsIgnoreCase("Yes")) {
								ventmodes += "+PS ";
							}
							if(tempVentilatorObject[15] != null) {
								ventmodes += "+ " + tempVentilatorObject[15].toString();
							}
						}
						if(modes.get(tempVentilatorObject[1].toString()).equalsIgnoreCase("CPAP")) {
							if(tempVentilatorObject[18]!=null && tempVentilatorObject[18].toString()!=null) {
								ventmodes += "- " + tempVentilatorObject[18].toString();
							}
							if(tempVentilatorObject[19]!=null && tempVentilatorObject[19].toString()!=null) {
								ventmodes += "- " + tempVentilatorObject[19].toString();
							}
						}
						modeOfVentilation.set(index,ventmodes);
					}

					if(modeOfVentilation.size()>24){
						while (modeOfVentilation.size()>24) {
							int length = modeOfVentilation.size();
							modeOfVentilation.remove(length-1);
						}
					 }
					}

				if(pressureSupport.size()!=0){
					// Pressure Supply
					if (tempVentilatorObject[2] != null && !BasicUtils.isEmpty(tempVentilatorObject[2].toString())) {
						pressureSupport.set(index,parseFloat(tempVentilatorObject[2].toString()));
					}
					if(pressureSupport.size()>24){
						while (pressureSupport.size()>24) {
							int length = pressureSupport.size();
							pressureSupport.remove(length-1);
						}
					}

				}

				if(peep.size()!=0){
					// PEEP
					if (tempVentilatorObject[3] != null && !BasicUtils.isEmpty(tempVentilatorObject[3].toString())) {
						peep.set(index,parseFloat(tempVentilatorObject[3].toString()));
					}
					if(peep.size()>24){
						while (peep.size()>24) {
							int length = peep.size();
							peep.remove(length-1);
						}
					}
				}

				if(ti.size()!=0){

					// TI
					if (tempVentilatorObject[4] != null && !BasicUtils.isEmpty(tempVentilatorObject[4].toString())) {
						ti.set(index,parseFloat(tempVentilatorObject[4].toString()));
					}

					if(ti.size()>24){
						while (ti.size()>24) {
							int length = ti.size();
							ti.remove(length-1);
						}
					}

				}

				if(te.size()!=0){
					// TE
					if (tempVentilatorObject[5] != null && !BasicUtils.isEmpty(tempVentilatorObject[5].toString())) {
						te.set(index,parseFloat(tempVentilatorObject[5].toString()));
					}

					if(te.size()>24){
						while (te.size()>24) {
							int length = te.size();
							te.remove(length-1);
						}
					}

				}

				if(map.size()!=0){
					// MAP
					if (tempVentilatorObject[6] != null && !BasicUtils.isEmpty(tempVentilatorObject[6].toString())) {
						map.set(index,parseFloat(tempVentilatorObject[6].toString()));
					}

					if(map.size()>24){
						while (map.size()>24) {
							int length = map.size();
							map.remove(length-1);
						}
					}

				}

				if(fio2.size()!=0){
					// FIO2
					if (tempVentilatorObject[7] != null && !BasicUtils.isEmpty(tempVentilatorObject[7].toString())) {
						fio2.set(index,parseFloat(tempVentilatorObject[7].toString()));
					}
					if(fio2.size()>24){
						while (fio2.size()>24) {
							int length = fio2.size();
							fio2.remove(length-1);
						}
					}

				}

				if(flow.size()!=0){
					if (tempVentilatorObject[8] != null && !BasicUtils.isEmpty(tempVentilatorObject[8].toString())) {
						flow.set(index,parseFloat(tempVentilatorObject[8].toString()));
					}

					if(flow.size()>24){
						while (flow.size()>24) {
							int length = flow.size();
							flow.remove(length-1);
						}
					}

				}

				if(frequency.size()!=0){
					// RATE PER Frequency
					if (tempVentilatorObject[9] != null && !BasicUtils.isEmpty(tempVentilatorObject[9].toString())) {
						frequency.set(index,parseFloat(tempVentilatorObject[9].toString()));
					}
					if(frequency.size()>24){
						while (frequency.size()>24) {
							int length = frequency.size();
							frequency.remove(length-1);
						}
					}

				}

				if(tidalVolumne.size()!=0){

					// TIDAL VOLUME
					if (tempVentilatorObject[10] != null && !BasicUtils.isEmpty(tempVentilatorObject[10].toString())) {
						tidalVolumne.set(index,parseFloat(tempVentilatorObject[10].toString()));
					}

					if(tidalVolumne.size()>24){
						while (tidalVolumne.size()>24) {
							int length = tidalVolumne.size();
							tidalVolumne.remove(length-1);
						}
					}

				}
				if(minVolumne.size()!=0){
					// MIN VOLUME
					if (tempVentilatorObject[11] != null && !BasicUtils.isEmpty(tempVentilatorObject[11].toString())) {
						minVolumne.set(index,parseFloat(tempVentilatorObject[11].toString()));
					}

					if(minVolumne.size()>24){
						while (minVolumne.size()>24) {
							int length = minVolumne.size();
							minVolumne.remove(length-1);
						}
					}

				}
				if(rpm.size()!=0){
					// RPM
					if (tempVentilatorObject[12] != null && !BasicUtils.isEmpty(tempVentilatorObject[12].toString())) {
						rpm.set(index,parseFloat(tempVentilatorObject[12].toString()));
					}
					if(rpm.size()>24){
						while (rpm.size()>24) {
							int length = rpm.size();
							rpm.remove(length-1);
						}
					}
				}
				if(dco2.size()!=0){
					// DCO2
					if (tempVentilatorObject[13] != null && !BasicUtils.isEmpty(tempVentilatorObject[13].toString())) {
						dco2.set(index,parseFloat(tempVentilatorObject[13].toString()));
					}

					if(dco2.size()>24){
						while (dco2.size()>24) {
							int length = dco2.size();
							dco2.remove(length-1);
						}
					}

				}
				if(humidification.size()!=0){
					// HUMIDIFICATION
					if (tempVentilatorObject[14] != null && !BasicUtils.isEmpty(tempVentilatorObject[14].toString())) {
						humidification.set(index,parseFloat(tempVentilatorObject[14].toString()));
					}

					if(humidification.size()>24){
						while (humidification.size()>24) {
							int length = humidification.size();
							humidification.remove(length-1);
						}
					}
				}
			}

			if(checkListContainsNullOnly(pressureSupport)) {
				patientChartVentilatorObject.setPressureSupport(pressureSupport);
			}else{
				patientChartVentilatorObject.setPressureSupport(new ArrayList<>());
			}

			if(checkListContainsNullOnly(peep)) {
				patientChartVentilatorObject.setPeep(peep);
			}else{
				patientChartVentilatorObject.setPeep(new ArrayList<>());
			}

			if(checkListContainsNullOnly(ti)) {
				patientChartVentilatorObject.setTi(ti);
			}else{
				patientChartVentilatorObject.setTi(new ArrayList<>());
			}

			if(checkListContainsNullOnly(te)) {
				patientChartVentilatorObject.setTe(te);
			}else{
				patientChartVentilatorObject.setTe(new ArrayList<>());
			}

			if(checkListContainsNullOnly(te)) {
				patientChartVentilatorObject.setTe(te);
			}else{
				patientChartVentilatorObject.setTe(new ArrayList<>());
			}

			if(checkListContainsNullOnly(map)) {
				patientChartVentilatorObject.setMap(map);
			}else{
				patientChartVentilatorObject.setMap(new ArrayList<>());
			}

			if(checkListContainsNullOnly(fio2)) {
				patientChartVentilatorObject.setFio2(fio2);
			}else{
				patientChartVentilatorObject.setFio2(new ArrayList<>());
			}

			// Flow
			if(checkListContainsNullOnly(flow)) {
				patientChartVentilatorObject.setFlow(flow);
			}else{
				patientChartVentilatorObject.setFlow(new ArrayList<>());
			}

			// frequency
			if(checkListContainsNullOnly(frequency)) {
				patientChartVentilatorObject.setFrequency(frequency);
			}else{
				patientChartVentilatorObject.setFrequency(new ArrayList<>());
			}

			// setMinVolumne
			if(checkListContainsNullOnly(minVolumne)) {
				patientChartVentilatorObject.setMinVolumne(minVolumne);
			}else{
				patientChartVentilatorObject.setMinVolumne(new ArrayList<>());
			}


			// setTidalVolumne
			if(checkListContainsNullOnly(tidalVolumne)) {
				patientChartVentilatorObject.setTidalVolumne(tidalVolumne);
			}else{
				patientChartVentilatorObject.setTidalVolumne(new ArrayList<>());
			}

			// dco2
			if(checkListContainsNullOnly(dco2)) {
				patientChartVentilatorObject.setDco2(dco2);
			}else{
				patientChartVentilatorObject.setDco2(new ArrayList<>());
			}

			// deliveredTv
			if(checkListContainsNullOnly(deliveredTv)) {
				patientChartVentilatorObject.setDeliveredTv(deliveredTv);
			}else{
				patientChartVentilatorObject.setDeliveredTv(new ArrayList<>());
			}

			// rpm
			if(checkListContainsNullOnly(rpm)) {
				patientChartVentilatorObject.setRpm(rpm);
			}else{
				patientChartVentilatorObject.setRpm(new ArrayList<>());
			}

			// rpm
			if(checkListContainsNullOnly(humidification)) {
				patientChartVentilatorObject.setHumidification(humidification);
			}else{
				patientChartVentilatorObject.setHumidification(new ArrayList<>());
			}

			patientChartVentilatorObject.setModeOfVentilation(modeOfVentilation);
		}

		for(int i=0;i<timeList.size();i++){

				int index=i;
				if(sfRatio.size()!=0){

					// SF Ratio
					if (!BasicUtils.isEmpty(spo2.get(i)) && !BasicUtils.isEmpty(fio2.get(i))) {
						float spo2Value = parseFloat(spo2.get(i).toString());
						float fio2Value = parseFloat(fio2.get(i).toString());
						if (spo2Value > 0 && fio2Value > 0) {
							double sfRatioValue = spo2Value / fio2Value;
							sfRatio.set(index,BasicUtils.getRoundedValue(sfRatioValue));
						}
					}
				}
		}

		if(checkListContainsNullOnly(sfRatio)) {
			patientChartVentilatorObject.setSfRatio(sfRatio);
		}else{
			patientChartVentilatorObject.setSfRatio(new ArrayList<>());
		}

		return patientChartVentilatorObject;
	}

	private PatientChartBloodGasObject getBloodGasData(String myUhid,LinkedList<Integer> timeList,Timestamp fromDate,Timestamp toDate,
													   List<Object[]> vitalsList){

		PatientChartBloodGasObject patientChartBloodGasObject=new PatientChartBloodGasObject();

		String bloodGasQuery=HqlSqlQueryConstants.getBloodGasDetails(myUhid,fromDate,toDate);
		List<NursingBloodGas> bloodGasList=inicuDoa.getListFromMappedObjQuery(bloodGasQuery);

		List<Object[]> vitalList=vitalsList;

		// This is vital
		List<Float> etco2=new ArrayList<>();

		List<Float> ph=new ArrayList<>();
		List<Float> pco2=new ArrayList<>();
		List<Float> po2=new ArrayList<>();
		List<Float> hco3=new ArrayList<>();
		List<Float> be=new ArrayList<>();
		List<Float> so2=new ArrayList<>();
		List<Float> lactose=new ArrayList<>();
		List<Float> hct=new ArrayList<>();
		List<Float> tHbc=new ArrayList<>();
		List<Float> osmolarity=new ArrayList<>();

		int phFlag=0;
		int pco2Flag=0;
		int po2Flag=0;
		int hco3Flag=0;
		int beFlag=0;
		int so2Flag=0;
		int lactoseFlag=0;
		int hctFlag=0;
		int tHbcFlag=0;
		int osmolarityFlag=0;

		// Vitals
		int etco2Flag=0;

		for(int i=0;i<timeList.size();i++){
			ph.add(null);
			pco2.add(null);
			po2.add(null);
			hco3.add(null);
			be.add(null);
			so2.add(null);
			lactose.add(null);
			hct.add(null);
			tHbc.add(null);
			osmolarity.add(null);
			etco2.add(null);
		}

		if(!BasicUtils.isEmpty(vitalList) && vitalList.size()>0){
			for (int k = 0; k < vitalList.size(); k++) {

				Object[] tempVentilatorObject = vitalList.get(k);

				Timestamp ventilatoryEntryDate = (Timestamp) tempVentilatorObject[0];
				int hour = ventilatoryEntryDate.getHours();

				int index = timeList.indexOf(ventilatoryEntryDate.getHours());

				if (hour <= 23 && hour >= 8) {
					index = hour - 8;
				} else if (hour >= 0 && hour <= 7) {
					index = hour + 16;
				}

				// ETCO2
				if (etco2.size() != 0) {
					// CFT
					if (tempVentilatorObject[9] != null && !BasicUtils.isEmpty(tempVentilatorObject[9].toString())) {
						etco2.set(index, parseFloat(tempVentilatorObject[9].toString()));
						etco2Flag=1;
					}
				}
			}
		}

		if(etco2Flag==1) {
			patientChartBloodGasObject.setEtco2(etco2);
		}else{
			patientChartBloodGasObject.setEtco2(new ArrayList<>());
		}

		if(!BasicUtils.isEmpty(bloodGasList) && bloodGasList.size()>0) {
			int j = 0;
			for (int i = 0; i < bloodGasList.size(); i++) {

				NursingBloodGas tempBloodGasObject = bloodGasList.get(i);
				Timestamp bloodGasEntryDate = tempBloodGasObject.getEntryDate();

				int hour = bloodGasEntryDate.getHours();

				int index = timeList.indexOf(hour);

				if (hour <= 23 && hour >= 8) {
					index = hour - 8;
				} else if (hour >= 0 && hour <= 7) {
					index = hour + 16;
				}

				// PH
				if (ph.size() != 0) {
					if (!BasicUtils.isEmpty(tempBloodGasObject.getPh())) {
						ph.set(index, Float.parseFloat(tempBloodGasObject.getPh().toString()));
						phFlag = 1;
					}
				}

				// PCO2
				if (pco2.size() != 0) {
					if (!BasicUtils.isEmpty(tempBloodGasObject.getPco2())) {
						pco2.set(index, Float.parseFloat(tempBloodGasObject.getPco2().toString()));
						pco2Flag = 1;
					}
				}

				// PO2
				if (po2.size() != 0) {
					if (!BasicUtils.isEmpty(tempBloodGasObject.getPo2())) {
						po2.set(index, Float.parseFloat(tempBloodGasObject.getPo2().toString()));
						po2Flag = 1;
					}
				}

				// HCO3
				if (hco3.size() != 0) {
					if (!BasicUtils.isEmpty(tempBloodGasObject.getHco2())) {
						hco3.set(index, Float.parseFloat(tempBloodGasObject.getHco2().toString()));
						hco3Flag = 1;
					}
				}

				// BE
				if (be.size() != 0) {
					if (!BasicUtils.isEmpty(tempBloodGasObject.getBe())) {
						be.set(index, Float.parseFloat(tempBloodGasObject.getBe().toString()));
						beFlag = 1;
					}
				}

				// SO2
				if (so2.size() != 0) {
					if (!BasicUtils.isEmpty(tempBloodGasObject.getSpo2())) {
						so2.set(index, Float.parseFloat(tempBloodGasObject.getSpo2().toString()));
						so2Flag = 1;
					}
				}

				// Lactose
				if (lactose.size() != 0) {
					if (!BasicUtils.isEmpty(tempBloodGasObject.getLactate())) {
						lactose.set(index, Float.parseFloat(tempBloodGasObject.getLactate().toString()));
						lactoseFlag = 1;
					}
				}

				// HCT
				if (hct.size() != 0) {
					if (!BasicUtils.isEmpty(tempBloodGasObject.getHct())) {
						hct.set(index, Float.parseFloat(tempBloodGasObject.getHct().toString()));
						hctFlag = 1;
					}
				}

				// tHBC
				if (tHbc.size() != 0) {
					if (!BasicUtils.isEmpty(tempBloodGasObject.getThbc())) {
						tHbc.set(index, Float.parseFloat(tempBloodGasObject.getThbc().toString()));
						tHbcFlag = 1;
					}
				}

				// Osmolarity
				if (osmolarity.size() != 0) {
					if (!BasicUtils.isEmpty(tempBloodGasObject.getOsmolarity())) {
						osmolarity.set(index, Float.parseFloat(tempBloodGasObject.getOsmolarity().toString()));
						osmolarityFlag = 1;
					}
				}
			}
		}

		if(phFlag==1) {
			patientChartBloodGasObject.setPh(ph);
		}else{
			patientChartBloodGasObject.setPh(new ArrayList<>());
		}

		if(pco2Flag==1) {
			patientChartBloodGasObject.setPco2(pco2);
		}else{
			patientChartBloodGasObject.setPco2(new ArrayList<>());
		}

		if(po2Flag==1) {
			patientChartBloodGasObject.setPo2(po2);
		}else{
			patientChartBloodGasObject.setPo2(new ArrayList<>());
		}

		if(hco3Flag==1) {
			patientChartBloodGasObject.setHco3(hco3);
		}else{
			patientChartBloodGasObject.setHco3(new ArrayList<>());
		}

		if(beFlag==1) {
			patientChartBloodGasObject.setBe(be);
		}else{
			patientChartBloodGasObject.setBe(new ArrayList<>());
		}

		if(so2Flag==1) {
			patientChartBloodGasObject.setSo2(so2);
		}else{
			patientChartBloodGasObject.setSo2(new ArrayList<>());
		}

		if(lactoseFlag==1) {
			patientChartBloodGasObject.setLactose(lactose);
		}else{
			patientChartBloodGasObject.setLactose(new ArrayList<>());
		}

		if(hctFlag==1) {
			patientChartBloodGasObject.setHct(hct);
		}else{
			patientChartBloodGasObject.setHct(new ArrayList<>());
		}

		if(tHbcFlag==1) {
			patientChartBloodGasObject.settHbc(tHbc);
		}else{
			patientChartBloodGasObject.settHbc(new ArrayList<>());
		}

		if(osmolarityFlag==1) {
			patientChartBloodGasObject.setOsmolarity(osmolarity);
		}else{
			patientChartBloodGasObject.setOsmolarity(new ArrayList<>());
		}

		return patientChartBloodGasObject;
	}

	private HashMap<String, List<String>> getMedicationData(String myUhid,LinkedList<Integer> timeList,Timestamp fromDate,Timestamp toDate) {

		HashMap<String, List<String>> medicationsMap = new LinkedHashMap<>();
		List<Object[]> currentMedicationList = null;

		int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
				- TimeZone.getDefault().getRawOffset();

		// get the all current on going medication names and pres_id
		System.out.println("Medication Query Start->>>>>>>>>>>>>>>");

		System.out.println("Offset :"+offset);

		String babyCurrentMedication = HqlSqlQueryConstants.getExecutedMedication(myUhid, fromDate, toDate);
		currentMedicationList = inicuPostgersUtil.executePsqlDirectQuery(babyCurrentMedication);

		System.out.println("Medication Query End->>>>>>>>>>>>>>>");

		List<String> medicationNameList = new ArrayList<>();
		for (int k = 0; k < currentMedicationList.size(); k++) {
			medicationNameList.add(currentMedicationList.get(k)[1].toString());
			if (currentMedicationList != null) {

				String medicationname = currentMedicationList.get(k)[1].toString();
				Timestamp entryTime = (Timestamp) currentMedicationList.get(k)[2];

				BigInteger babyPresId=(BigInteger) currentMedicationList.get(k)[0];

				System.out.println("Hour value before :"+entryTime.getHours());
				Timestamp entryTimeWithOffset=new Timestamp(entryTime.getTime()+offset);
				int hour = entryTimeWithOffset.getHours();
				System.out.println("Hour value after :"+ entryTimeWithOffset.getHours());

				int index=timeList.indexOf(((Timestamp) currentMedicationList.get(k)[2]).getHours());

				if(hour<=23 && hour>=8){
					index=hour-8;
				}else if(hour>=0 && hour<=7){
					index=hour+16;
				}

				String volume = null;
				if(currentMedicationList.get(k)[3]!=null && currentMedicationList.get(k)[3].toString()!=null){
					volume=currentMedicationList.get(k)[3].toString();
				}else{
					// get the medication volume from the medication preparation
					String medicationPrepQuery="select p.baby_presid,p.cal_dose_volume,p.overfill_factor,p.dose_unit,n.med_strength,n.final_strength from medication_preparation n " +
							"inner join baby_prescription p on n.baby_presid = p.baby_presid where p.uhid = '"+myUhid+"' and n.baby_presid='"+babyPresId+"' order by n.creationtime asc";
					List<Object[]> medicationPreparations=inicuPostgersUtil.executePsqlDirectQuery(medicationPrepQuery);
					if(!BasicUtils.isEmpty(medicationPreparations) && medicationPreparations.size()>0){
						Object[] medicationPreparationItem=medicationPreparations.get(0);

						float calculated_cal_dose_volume=0;
						double medVol=0;
						float overflowFactor=0;

						float calMedStrength=0;
						float calFinalStrength=0;
						float cal_dose_volume=0;

						if(!BasicUtils.isEmpty(medicationPreparationItem[2]) && medicationPreparationItem[2]!="" && medicationPreparationItem[2]!=null) {
							overflowFactor= (float)medicationPreparationItem[2];
						}

						if(!BasicUtils.isEmpty(medicationPreparationItem[1]) && medicationPreparationItem[1]!="") {
							cal_dose_volume= (float)medicationPreparationItem[1];
						}

						if(!BasicUtils.isEmpty(medicationPreparationItem[4]) && medicationPreparationItem[4]!="") {
							calMedStrength= (float) medicationPreparationItem[4];
						}

						if(!BasicUtils.isEmpty(medicationPreparationItem[5]) && medicationPreparationItem[1]!="") {
							calFinalStrength= (float) medicationPreparationItem[5];
						}

						String[] unitarray=null;
						if(!BasicUtils.isEmpty(medicationPreparationItem[3]) && medicationPreparationItem[3].toString()!=""){
							unitarray=medicationPreparationItem[3].toString().split("/",2);
						}

						if (unitarray!=null && unitarray[0].equalsIgnoreCase("μg")) {
							calculated_cal_dose_volume = cal_dose_volume / 1000;
						} else {
							calculated_cal_dose_volume = cal_dose_volume;
						}

						if (calFinalStrength != 0) {
							medVol = Math.round((calculated_cal_dose_volume / calFinalStrength)*100.0)/100.0;
							if(overflowFactor!=0){
								medVol=medVol*overflowFactor;
							}
						}else if (calMedStrength != 0) {
							medVol =Math.round((calculated_cal_dose_volume / calMedStrength)*100.0)/100.0;
							if(overflowFactor!=0){
								medVol=medVol*overflowFactor;
							}
						}

						if(medVol!=0){
							if(overflowFactor!=0){
								volume=String.valueOf(Math.round(medVol*100.0)/100.0);
							}else {
								volume = String.valueOf(Math.round(medVol*100.0)/100.0);
							}
						}
					}
				}

				if (medicationsMap.containsKey(medicationname)) {
					List<String> TempList = medicationsMap.get(medicationname);
					TempList.set(index,volume);
					if(TempList.size()>24){
						while (TempList.size()>24) {
							int length = TempList.size();
							TempList.remove(length-1);
						}
					}
					medicationsMap.put(medicationname, TempList);
				} else {
					List<String> TempList = new ArrayList<>();
					for(int i=0;i<24;i++){
						TempList.add(null);
					}
					TempList.set(index,volume);
					if(TempList.size()>24){
						while (TempList.size()>24) {
							int length = TempList.size();
							TempList.remove(length-1);
						}
					}
					medicationsMap.put(medicationname, TempList);
				}
			}
		}
		return medicationsMap;
	}

	private PatientChartInvestigationObject getInvestigationData (String myUhid,LinkedList<Integer> timeList, Timestamp fromDate,Timestamp toDate,
																  List<NursingVitalparameter> vitalsParametersList){
		PatientChartInvestigationObject patientChartInvestigationObject=new PatientChartInvestigationObject();

		List<Float> tcb=new ArrayList<>();
		List<Float> rbc=new ArrayList<>();

		List<Float> naPlus=new ArrayList<>();
		List<Float> kPlus=new ArrayList<>();
		List<Float> clMinus=new ArrayList<>();
		List<Float> caPlusPlus=new ArrayList<>();
		List<Float> anionGap=new ArrayList<>();

		for(int i=0;i<timeList.size();i++){
			tcb.add(null);
			rbc.add(null);

			naPlus.add(null);
			kPlus.add(null);
			clMinus.add(null);
			caPlusPlus.add(null);
			anionGap.add(null);
		}

		int tcbFlag=0;
		int rbcFlag=0;

		int naPlusFlag=0;
		int kPlusFlag=0;
		int clMinusFlag=0;
		int caPlusPlusFlag=0;
		int anionGapFlag=0;

		// this can have more than one entry

		List<NursingVitalparameter> nursingVitalparametersForTcb=vitalsParametersList;

		// TCB and RBS
		if(!BasicUtils.isEmpty(nursingVitalparametersForTcb) && nursingVitalparametersForTcb.size()>0) {

			for (int i = 0; i < nursingVitalparametersForTcb.size(); i++) {
					NursingVitalparameter myVitalObject = nursingVitalparametersForTcb.get(i);
					int hour = myVitalObject.getEntryDate().getHours();
					int index = timeList.indexOf(hour);

					if (hour <= 23 && hour >= 8) {
						index = hour - 8;
					}else if (hour >= 0 && hour <= 7) {
						index = hour + 16;
					}

					// RBS
					if (rbc.size() != 0) {
						if (!BasicUtils.isEmpty(myVitalObject.getRbs())) {
							rbc.set(index, myVitalObject.getRbs());
							rbcFlag = 1;
						}
					}

					// TCB
					if (tcb.size() != 0) {
						if (!BasicUtils.isEmpty(myVitalObject.getTcb())) {
							tcb.set(index, Float.parseFloat(myVitalObject.getTcb()));
							tcbFlag = 1;
						}
					}
			}
		}

		if(rbcFlag==1) {
			patientChartInvestigationObject.setRbs(rbc);
		}else{
			patientChartInvestigationObject.setRbs(new ArrayList<>());
		}

		if(tcbFlag==1) {
			patientChartInvestigationObject.setTcb(tcb);
		}else{
			patientChartInvestigationObject.setTcb(new ArrayList<>());
		}

		// Electrolytes
		String getElectrolytes=HqlSqlQueryConstants.getBloodGasObjectForInvestigation(myUhid,fromDate,toDate);
		List<NursingBloodGas> nursingBloodGases=inicuDoa.getListFromMappedObjQuery(getElectrolytes);

		if(!BasicUtils.isEmpty(nursingBloodGases)) {

			for (int i = 0; i < nursingBloodGases.size(); i++) {

						NursingBloodGas nursingBloodGasObject = nursingBloodGases.get(i);
						Timestamp nursingEntryTime = nursingBloodGasObject.getEntryDate();
						int hour = nursingEntryTime.getHours();

						int index = timeList.indexOf(hour);

						if (hour <= 23 && hour >= 8) {
							index = hour - 8;
						}else if (hour >= 0 && hour <= 7) {
							index = hour + 16;
						}

						// naPlus
						if (nursingBloodGasObject != null && !BasicUtils.isEmpty(nursingBloodGasObject.getNa())) {
							naPlus.set(index,parseFloat(nursingBloodGasObject.getNa()));
							naPlusFlag=1;
						}

						// kPlus
						if (nursingBloodGasObject != null && !BasicUtils.isEmpty(nursingBloodGasObject.getK())) {
							kPlus.set(index,parseFloat(nursingBloodGasObject.getK()));
							kPlusFlag=1;
						}

						// ClMinus
						if (nursingBloodGasObject != null && !BasicUtils.isEmpty(nursingBloodGasObject.getCl())) {
							clMinus.set(index,parseFloat(nursingBloodGasObject.getCl()));
							clMinusFlag=1;
						}
						// CaPlusPlus
						if (nursingBloodGasObject != null && !BasicUtils.isEmpty(nursingBloodGasObject.getIonized_calcium())) {
							caPlusPlus.set(index,parseFloat(nursingBloodGasObject.getIonized_calcium()));
							caPlusPlusFlag=1;
						}

						// anionGap
						if (nursingBloodGasObject != null && !BasicUtils.isEmpty(nursingBloodGasObject.getAnionGap())) {
							anionGap.set(index,parseFloat(nursingBloodGasObject.getAnionGap()));
							anionGapFlag=1;
						}
					}
			}

		if(naPlusFlag==1) {
			patientChartInvestigationObject.setNaPlus(naPlus);
		}else{
			patientChartInvestigationObject.setNaPlus(new ArrayList<>());
		}

		if(kPlusFlag==1) {
			patientChartInvestigationObject.setkPlus(kPlus);
		}else{
			patientChartInvestigationObject.setkPlus(new ArrayList<>());
		}

		if(clMinusFlag==1) {
			patientChartInvestigationObject.setClMinus(clMinus);
		}else{
			patientChartInvestigationObject.setClMinus(new ArrayList<>());
		}

		if(caPlusPlusFlag==1) {
			patientChartInvestigationObject.setCaPlusPlus(caPlusPlus);
		}else{
			patientChartInvestigationObject.setCaPlusPlus(new ArrayList<>());
		}

		if(anionGapFlag==1) {
			patientChartInvestigationObject.setAnionGap(anionGap);
		}else{
			patientChartInvestigationObject.setAnionGap(new ArrayList<>());
		}

		return patientChartInvestigationObject;
	}

	private PatientChartEventsObject getEpisodeData(String myUhid,LinkedList<Integer> timeList,Timestamp fromDate,Timestamp toDate){

		int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
				- TimeZone.getDefault().getRawOffset();

		PatientChartEventsObject eventsObject=new PatientChartEventsObject();

		String nursingEpisodeQuery=HqlSqlQueryConstants.getNursingEpisodeListForChart(myUhid,fromDate,toDate);
		List<NursingEpisode> nursingEpisodeList=inicuDoa.getListFromMappedObjQuery(nursingEpisodeQuery);

		if(!BasicUtils.isEmpty(nursingEpisodeList) && nursingEpisodeList.size()>0) {

			List<String> seizures = new ArrayList<>();
			List<String> apnea = new ArrayList<>();
			List<String> desaturation = new ArrayList<>();

			int apneaFlag=0;
			int seizuresFlag=0;
			int desaturationFlag=0;

			for(int i=0;i<timeList.size();i++){

				int apneaCount=0;
				String apneaStr="";

				int seizuresCount=0;
				String seizuresStr="";

				int desaturationCount=0;
				String desaturationStr="";

				for(int j=0;j<nursingEpisodeList.size();j++) {

					NursingEpisode nursingEpisodeObject=nursingEpisodeList.get(j);
					Timestamp creationtime=nursingEpisodeObject.getCreationtime();

//					System.out.println("Hour value before :"+creationtime.getHours());
//					Timestamp entryTimeWithOffset=new Timestamp(creationtime.getTime());
					int hour = creationtime.getHours();
//					System.out.println("Hour value After :"+hour);

					if(hour==timeList.get(i)) {
						// check for seizure
						if (!BasicUtils.isEmpty(nursingEpisodeObject.getSeizures()) && nursingEpisodeObject.getSeizures()==true) {
							if (nursingEpisodeObject.getSeizureDuration() != null && nursingEpisodeObject.getDuration_unit_seizure() != null) {
								seizuresCount++;
								if(seizuresStr.equals("")) {
									seizuresStr += nursingEpisodeObject.getSeizureDuration() + " " + nursingEpisodeObject.getDuration_unit_seizure();
								}else{
									seizuresStr +=", "+nursingEpisodeObject.getSeizureDuration() + " " + nursingEpisodeObject.getDuration_unit_seizure();
								}
							}
						}else if(!BasicUtils.isEmpty(nursingEpisodeObject.getSeizures()) && nursingEpisodeObject.getSeizures()==false){
							seizuresCount--;
						}


						// Check for Apnea
						if (!BasicUtils.isEmpty(nursingEpisodeObject.getApnea()) && nursingEpisodeObject.getApnea()==true) {
							if (nursingEpisodeObject.getApneaDuration() != null && nursingEpisodeObject.getDuration_unit_apnea() != null) {
								apneaCount++;
								if(apneaStr.equals("")) {
									apneaStr +=nursingEpisodeObject.getApneaDuration() + " " + nursingEpisodeObject.getDuration_unit_apnea();
								}else{
									apneaStr +=", "+nursingEpisodeObject.getApneaDuration() + " " + nursingEpisodeObject.getDuration_unit_apnea();
								}
							}
						}else if(!BasicUtils.isEmpty(nursingEpisodeObject.getApnea()) && nursingEpisodeObject.getApnea()==false){
							apneaCount--;
						}
						// check for desaturation
						if (!BasicUtils.isEmpty(nursingEpisodeObject.getDesaturation()) && nursingEpisodeObject.getDesaturation()==true) {
							if (nursingEpisodeObject.getDesaturationSpo2() != null) {
								desaturationCount++;
								desaturationStr="Found";
							}
						}else if (!BasicUtils.isEmpty(nursingEpisodeObject.getDesaturation()) && nursingEpisodeObject.getDesaturation()==false){
							desaturationCount--;

						}
					}
				}// Loop Ends here

				if(apneaCount>=0 && apneaStr!="") {
					String apneafinalStr="";
					if(apneaCount==1){
						apneafinalStr=apneaCount+" Event ("+apneaStr+")";
					}else if(apneaCount>1) {
						apneafinalStr=apneaCount+" Events ("+apneaStr+")";
					}
					apnea.add(apneafinalStr);
					apneaFlag=1;
				}else if(apneaCount<0 && apneaStr==""){
					apnea.add("NO");
					apneaFlag=1;
				}else{
					apnea.add(null);
				}

				if(seizuresCount>=0 && seizuresStr!="") {
					String seizuresfinalStr="";
					if(seizuresCount==1){
						seizuresfinalStr=seizuresCount+" Event ("+seizuresStr+")";
					}else if(seizuresCount>1) {
						seizuresfinalStr=seizuresCount+" Events ("+seizuresStr+")";
					}
					seizures.add(seizuresfinalStr);
					seizuresFlag=1;
				}else if(seizuresCount<0 && seizuresStr==""){
					seizures.add("NO");
					seizuresFlag=1;
				}else{
					seizures.add(null);
				}

				if(desaturationCount>=0 && desaturationStr!="") {
					desaturation.add("YES");
					desaturationFlag=1;
				}else if(desaturationCount<0 && desaturationStr==""){
					desaturation.add("NO");
					desaturationFlag=1;
				}else{
					desaturation.add(null);
				}
			}

			// Apnea
			if(apneaFlag==1) {
				eventsObject.setApnea(apnea);
			}else{
				eventsObject.setApnea(new ArrayList<>());
			}

			// Seizures
			if(seizuresFlag==1) {
				eventsObject.setSeizures(seizures);
			}else{
				eventsObject.setSeizures(new ArrayList<>());
			}

			// Desaturation
			if(desaturationFlag==1) {
				eventsObject.setDesaturation(desaturation);
			}else{
				eventsObject.setDesaturation(new ArrayList<>());
			}
		}
		return eventsObject;
	}

	// Vital Parameters method implementation
	@Override
	public VitalParamtersResponse getPatientChartByUhid(String uhid,String fromDateStr,String toDateStr){
		// received the uhid of the baby
		System.out.println("Uhid of the baby: "+uhid);

		VitalParamtersResponse returnObj=new VitalParamtersResponse();

		LinkedList<Integer> timeList=new LinkedList<>();
		timeList.add(8);
		timeList.add(9);
		timeList.add(10);
		timeList.add(11);
		timeList.add(12);
		timeList.add(13);
		timeList.add(14);
		timeList.add(15);
		timeList.add(16);
		timeList.add(17);
		timeList.add(18);
		timeList.add(19);
		timeList.add(20);
		timeList.add(21);
		timeList.add(22);
		timeList.add(23);
		timeList.add(0);
		timeList.add(1);
		timeList.add(2);
		timeList.add(3);
		timeList.add(4);
		timeList.add(5);
		timeList.add(6);
		timeList.add(7);

		PatientChartData patientChartData=new PatientChartData();

		int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
				- TimeZone.getDefault().getRawOffset();
		System.out.println("TimeStamp -- From date "+offset);

		System.out.println("TimeStamp -- From date "+fromDateStr);
		System.out.println("TimeStamp -- To date "+toDateStr);

		Timestamp fromDate = new Timestamp(Long.parseLong(fromDateStr)+offset);
		Timestamp toDate = new Timestamp(Long.parseLong(toDateStr)+offset);

		System.out.println("TimeStamp With Offset-- From date "+fromDate);
		System.out.println("TimeStamp With Offset-- To date "+toDate);

		Timestamp fromDateWithoutOffset = new Timestamp(Long.parseLong(fromDateStr));
		Timestamp toDateWithoutOffset = new Timestamp(Long.parseLong(toDateStr));

		System.out.println("TimeStamp Without Offset-- From date "+fromDateWithoutOffset);
		System.out.println("TimeStamp Without Offset-- To date "+toDateWithoutOffset);

		// Normal Values
		List<String> positionList=new ArrayList<>();
		List<String> babyColorList=new ArrayList<>();
		List<String> consciousnessList=new ArrayList<>();
		List<String> leftPupil=new ArrayList<>();
		List<String> rightPupil=new ArrayList<>();

		// Get the Vital Details
		String getVitalsQuery=HqlSqlQueryConstants.getChartVitalsData(uhid,fromDate,toDate);
		List<Object[]> nursingVitalparameters=inicuPostgersUtil.executePsqlDirectQuery(getVitalsQuery);

		if(!BasicUtils.isEmpty(nursingVitalparameters) && nursingVitalparameters.size()>0) {
			 int j=0;
			 int positionFlag=0;
			 int babyColorFlag=0;
			 int consciousnessFlag=0;
			 int leftPupilFlag=0;
			 int rightPupilFlag=0;

			 for(int i=0;i<timeList.size();i++){
			 	positionList.add(null);
			 	babyColorList.add(null);
			 	consciousnessList.add(null);
			 	leftPupil.add(null);
			 	rightPupil.add(null);
			 }

			 for(int i=0;i<nursingVitalparameters.size();i++){

			 	Object[] vitalParameterObject = nursingVitalparameters.get(i);
				 Timestamp entrydate = (Timestamp) vitalParameterObject[0];
				 int hour = entrydate.getHours();
				 int index=timeList.indexOf(hour);

				 if(hour<=23 && hour>=8){
					 index=hour-8;
				 }else if(hour>=0 && hour<=7){
					 index=hour+16;
				 }

				     // Position
					 if(positionList.size()!=0){
						 // SPO2
						 if (!BasicUtils.isEmpty(vitalParameterObject[1])) {
							 positionList.set(index, vitalParameterObject[1].toString());
							 positionFlag = 1;
						 }
					 }

					 // Skin or Color
				     if(babyColorList.size()!=0){

						 if (!BasicUtils.isEmpty(vitalParameterObject[2])) {

							 if (!BasicUtils.isEmpty(vitalParameterObject[3])) {
								 babyColorList.set(index,vitalParameterObject[3].toString());
								 babyColorFlag=1;
							 }else{
								 babyColorList.set(index,vitalParameterObject[2].toString());
								 babyColorFlag=1;
							 }

							 babyColorList.set(index,vitalParameterObject[2].toString());
							 babyColorFlag=1;
						 }
					 }

				      // Consciousness
				      if(consciousnessList.size()!=0){
							 if (!BasicUtils.isEmpty(vitalParameterObject[4])) {
								 consciousnessList.set(index,vitalParameterObject[4].toString());
								 consciousnessFlag=1;
							 }
				 		}

				      // Left Pupil
				      if(leftPupil.size()!=0){
							 if (!BasicUtils.isEmpty(vitalParameterObject[5])) {
								 leftPupil.set(index,vitalParameterObject[5].toString());
								 leftPupilFlag=1;
							 }

				         }
				 	   // right Pupil
				      if(rightPupil.size()!=0){
							 if (!BasicUtils.isEmpty(vitalParameterObject[6])) {
								 rightPupil.set(index, vitalParameterObject[6].toString());
								 leftPupilFlag = 1;
							 }
				      }
			 }

			PatientChartVitalObject vitalObject=new PatientChartVitalObject();

			// position
			if(positionFlag==1) {
				vitalObject.setPositionList(positionList);
			}else{
				vitalObject.setPositionList(new ArrayList<>());
			}

			// Baby Color
			if(babyColorFlag==1) {
				vitalObject.setBabyColorList(babyColorList);
			}else{
				vitalObject.setBabyColorList(new ArrayList<>());
			}

			// consciousness
			if(consciousnessFlag==1) {
				vitalObject.setConsciousnessList(consciousnessList);
			}else{
				vitalObject.setConsciousnessList(new ArrayList<>());
			}

			// Left Pupil
			if(leftPupilFlag==1) {
				vitalObject.setLeftPupil(leftPupil);
			}else{
				vitalObject.setLeftPupil(new ArrayList<>());
			}

			// Right Pupil
			if(rightPupilFlag==1) {
				vitalObject.setRightPupil(rightPupil);
			}else{
				vitalObject.setLeftPupil(new ArrayList<>());
			}


			patientChartData.setPatientChartVitalObject(vitalObject);
		}

		// get events Details
		PatientChartEventsObject eventsObject=getEpisodeData(uhid,timeList,fromDateWithoutOffset,toDateWithoutOffset);
		patientChartData.setPatientChartEventsObject(eventsObject);

     	// get the Graph data
		String vitalSql = HqlSqlQueryConstants.getNursingVitalGraphList(uhid, fromDate, toDate);
		List<NursingVitalparameter> nursingVitalparameterList=inicuDoa.getListFromMappedObjQuery(vitalSql);

		PatientGraphData patientGraphData=getGraphData(uhid,timeList,fromDate,toDate,fromDateWithoutOffset,toDateWithoutOffset,nursingVitalparameterList);
		returnObj.setPatientGraphData(patientGraphData);

		// get Ventilator Details
		String nursingVentilatorQuery=HqlSqlQueryConstants.getChartVentilatorData(uhid,fromDate,toDate);
		List<Object[]> nursingVentilatorList=inicuPostgersUtil.executePsqlDirectQuery(nursingVentilatorQuery);

		// Get Ventilator data
		PatientChartVentilatorObject patientChartVentilatorObject=getVentilatorData(uhid,timeList,fromDate,toDate,nursingVitalparameters,nursingVentilatorList);
		patientChartData.setPatientChartVentilatorObject(patientChartVentilatorObject);

		// get Blood Gas Details
		PatientChartBloodGasObject patientChartBloodGasObject=getBloodGasData(uhid,timeList,fromDate,toDate,nursingVitalparameters);
		patientChartData.setPatientChartBloodGasObject(patientChartBloodGasObject);

		// Intake And output Section
		PatientChartEnteralObject patientChartEnteralObject=new PatientChartEnteralObject();
		PatientChartParentralObject patientChartParentralObject=new PatientChartParentralObject();
		PatientChartOutputObject patientChartOutputObject=new PatientChartOutputObject();

		// previous EN and PN
		Timestamp toDatePreviosIntakle = fromDateWithoutOffset;
		Timestamp fromDatePreviousIntake = new Timestamp(
				fromDateWithoutOffset.getTime() - (1000 * 24 * 60 * 60));

		// Get the Previous Day EN and PN
		double totalPreviousIntake=0;
		double totalPreviousOutput=0;

		double totalCurrentEN=0;
		double totalCurrentPN=0;
		double totalCurrentIntake=0;

		// Cummulative of  Intake and ouput
		List<Float> intakeOutputVolume=new ArrayList<>();

		// Enteral
		List<String> feedMethod=new ArrayList<>();   //  route
		List<String> feedType=new ArrayList<>();    // method
		List<Float> feedVolume=new ArrayList<>();   // actual


		boolean feedVolumeFlag=false;   // actual
		boolean feedMethodFlag=false;   //  route
		boolean feedTypeFlag=false;    // method



		// Parentral
		List<Float> administeredFeedVolume=new ArrayList<>();
		List<Float> intraLipid=new ArrayList<>();
		List<Float> tpnDelivered=new ArrayList<>();
		List<Float> calcium=new ArrayList<>();
		List<Float> bloodProductVolume =new ArrayList<>();
		List<Float> heparinVolume=new ArrayList<>();

		boolean administeredFeedVolumeFlag=false;
		boolean intraLipidFlag=false;
		boolean tpnDeliveredFlag=false;
		boolean calciumFlag=false;
		boolean bloodProductFlag=false;
		boolean heparinFlag=false;

		// Ouput
		List<Float> totalOutputVolumne=new ArrayList<>();
		List<Float> AbdGirth=new ArrayList<>();
		List<Float> urine=new ArrayList<>();
		List<String> stool=new ArrayList<>();
		List<String> bloodInStool=new ArrayList<>();
		List<Float> gastricAspirate=new ArrayList<>();
		List<Float> stomaList=new ArrayList<>();
		List<String> aspirateColor=new ArrayList<>();

		boolean totalOutputVolumneFlag=false;
		boolean AbdGirthFlag=false;
		boolean urineFlag=false;
		boolean stoolFlag=false;
		boolean bloodInStoolFlag=false;
		boolean gastricAspirateFlag=false;
		boolean aspirateColorFlag=false;
		boolean stomaFlag=false;

		//		 get medication list
		HashMap<String, List<String>> medicationList=getMedicationData(uhid,timeList,fromDateWithoutOffset,toDateWithoutOffset);
		patientChartData.setPatientChartMedicationObject(medicationList);

		ArrayList<Double> medicationFinalList=new ArrayList<>();


		// current EN and PN
		String getCurrentOrder=HqlSqlQueryConstants.getCurrentNutrition(uhid,fromDateWithoutOffset,toDateWithoutOffset);
		List<BabyfeedDetail> babyDetailList=inicuDoa.getListFromMappedObjQuery(getCurrentOrder);

		// current day
		String getNurseOrder=HqlSqlQueryConstants.getNursingExecution(uhid,fromDateWithoutOffset,toDateWithoutOffset);
		List<NursingIntakeOutput> CurrrentIntakeOutputs=inicuDoa.getListFromMappedObjQuery(getNurseOrder);

		// Previous day values
		String getNurseOrderQuery=HqlSqlQueryConstants.getNursingExecution(uhid,fromDatePreviousIntake,toDatePreviosIntakle);
		List<NursingIntakeOutput> nursingIntakeOutputs=inicuDoa.getListFromMappedObjQuery(getNurseOrderQuery);

		// Change start

		// Blood Product List -current day
		String getBloodProduct=HqlSqlQueryConstants.getBloodProductExecution(uhid,fromDateWithoutOffset,toDateWithoutOffset);
		List<NursingBloodproduct> bloodProductList=inicuDoa.getListFromMappedObjQuery(getBloodProduct);

		// Heparin List - current day
		String getHeparinQuery=HqlSqlQueryConstants.getHeparinExecution(uhid,fromDateWithoutOffset,toDateWithoutOffset);
		List<NursingHeplock> getHeparinList=inicuDoa.getListFromMappedObjQuery(getHeparinQuery);


		// Blood Product List -Previous day
		String getPrevBloodProduct=HqlSqlQueryConstants.getBloodProductExecution(uhid,fromDatePreviousIntake,toDatePreviosIntakle);
		List<NursingBloodproduct> bloodPrevProductList=inicuDoa.getListFromMappedObjQuery(getPrevBloodProduct);

		// Heparin List - Previous day
		String getPrevHeparinQuery=HqlSqlQueryConstants.getHeparinExecution(uhid,fromDatePreviousIntake,toDatePreviosIntakle);
		List<NursingHeplock> getPrevHeparinList=inicuDoa.getListFromMappedObjQuery(getPrevHeparinQuery);

		// Change end
		String getRefFeedType=HqlSqlQueryConstants.getFeedTypeRefList();
		List<RefMasterfeedtype> refMasterfeedtypeList=inicuDoa.getListFromMappedObjQuery(getRefFeedType);

		HashMap<String ,String> feedMap=new HashMap<>();
		for (RefMasterfeedtype refObject:refMasterfeedtypeList) {
			feedMap.put(refObject.getFeedtypeid(),refObject.getFeedtypename());
		}

		if(babyDetailList!=null && !BasicUtils.isEmpty(babyDetailList)){

			BabyfeedDetail babyfeedDetail=babyDetailList.get(0);
				if(!BasicUtils.isEmpty(babyfeedDetail.getTotalenteralvolume())){
					totalCurrentEN+=babyfeedDetail.getTotalenteralvolume();
				}

				if(!BasicUtils.isEmpty(babyfeedDetail.getTotalparenteralvolume())){
					totalCurrentPN+=babyfeedDetail.getTotalparenteralvolume();
				}

			if(!BasicUtils.isEmpty(babyfeedDetail.getTotalIntake())){
				totalCurrentIntake=Float.parseFloat(babyfeedDetail.getTotalIntake());
			}

			patientChartData.setCurrentDayEn(BasicUtils.getRoundedValue(totalCurrentEN));
			patientChartData.setCurrentDayPn(BasicUtils.getRoundedValue(totalCurrentPN));
			patientChartData.setTotalCurrentIntake(BasicUtils.getRoundedValue(totalCurrentIntake));
		}

		if(nursingIntakeOutputs!=null && !BasicUtils.isEmpty(nursingIntakeOutputs)) {

			for (NursingIntakeOutput nursingIntakeOutputObject:nursingIntakeOutputs) {
				//  Total Enteral and Parenteral Value
				if (nursingIntakeOutputObject.getActualFeed() != null) {
					totalPreviousIntake += nursingIntakeOutputObject.getActualFeed();
					totalPreviousIntake = Math.round(totalPreviousIntake * 100.0) / 100.0;
				}

				if (nursingIntakeOutputObject.getLipid_delivered() != null) {
					totalPreviousIntake += (nursingIntakeOutputObject.getLipid_delivered());
				}

				if (nursingIntakeOutputObject.getTpn_delivered() != null) {
					totalPreviousIntake += (nursingIntakeOutputObject.getTpn_delivered());
				}

				if (nursingIntakeOutputObject.getReadymadeDeliveredFeed() != null) {
					totalPreviousIntake += (nursingIntakeOutputObject.getReadymadeDeliveredFeed());
				}

				if (!BasicUtils.isEmpty(totalPreviousIntake)) {
					totalPreviousIntake = Math.round(totalPreviousIntake * 100.0) / 100.0;
				}

				// urine
				String urineValue = null;
				if (!BasicUtils.isEmpty(nursingIntakeOutputObject.getUrinePassed()) && nursingIntakeOutputObject.getUrinePassed()) {
					if (!BasicUtils.isEmpty(nursingIntakeOutputObject.getUrine())) {
						urineValue = nursingIntakeOutputObject.getUrine();
						if(urineValue!=null) {
							totalPreviousOutput+=Float.parseFloat(urineValue);
						}
					}
				}

				// gastric aspirate
				String gastricAspirateValue = null;
				if (!BasicUtils.isEmpty(nursingIntakeOutputObject.getGastricAspirate())) {
					gastricAspirateValue = nursingIntakeOutputObject.getGastricAspirate();
					totalPreviousOutput+=Float.parseFloat(gastricAspirateValue);
				}

				// Stoma
				String stomaValue = null;
				if (!BasicUtils.isEmpty(nursingIntakeOutputObject.getStoma())) {
					stomaValue = nursingIntakeOutputObject.getStoma();
					totalPreviousOutput+=Float.parseFloat(stomaValue);
				}
			}

			// Blood Product
			if(bloodPrevProductList!=null && bloodPrevProductList.size()>0){
				for(NursingBloodproduct bloodproduct: bloodPrevProductList){
					if (bloodproduct.getDelivered_volume() != null) {
						totalPreviousIntake += (bloodproduct.getDelivered_volume());
					}
				}
			}

			// Heparin
			if(getPrevHeparinList!=null && getPrevHeparinList.size()>0){
				for(NursingHeplock nursingHeplock: getPrevHeparinList){
					if (nursingHeplock.getDeliveredVolume() != null) {
						totalPreviousIntake += (nursingHeplock.getDeliveredVolume());
					}
				}
			}

			if (!BasicUtils.isEmpty(totalPreviousIntake)) {
				totalPreviousIntake = Math.round(totalPreviousIntake * 100.0) / 100.0;
			}

			// add the values in the array now
			patientChartData.setPreviousDayEn(parseFloat(String.valueOf(totalPreviousIntake)));
			patientChartData.setPreviousDayPn(parseFloat(String.valueOf(totalPreviousOutput)));
		}

		for(int i=0;i<timeList.size();i++) {
			feedVolume.add(null);
			feedType.add(null);
			feedMethod.add(null);

			administeredFeedVolume.add(null);
			intraLipid.add(null);
			tpnDelivered.add(null);

			calcium.add(null);
			AbdGirth.add(null);
			urine.add(null);
			bloodInStool.add(null);
			gastricAspirate.add(null);
			stomaList.add(null);
			heparinVolume.add(null);
			bloodProductVolume.add(null);
			totalOutputVolumne.add(null);
			aspirateColor.add(null);
			stool.add(null);
			medicationFinalList.add(0.0);
		}

		if(CurrrentIntakeOutputs!=null && !BasicUtils.isEmpty(CurrrentIntakeOutputs) || (bloodProductList!=null && bloodProductList.size()>0)
				|| (heparinVolume!=null && heparinVolume.size()>0)) {
			// Add the medication volume to the list
			medicationList.forEach((k,v) -> {
				List<String> tempList = medicationList.get(k);
				for(int i=0;i<timeList.size();i++){
					if(tempList.get(i)!=null && tempList.get(i)!="0") {
						float medicationValue = Float.parseFloat(tempList.get(i));
						double valueFromFinalList=medicationFinalList.get(i);
						valueFromFinalList=valueFromFinalList+medicationValue;
						medicationFinalList.set(i,valueFromFinalList);
					}
			    }
			});

			for (int k=0;k<CurrrentIntakeOutputs.size();k++) {

				NursingIntakeOutput nursingIntakeOutput=CurrrentIntakeOutputs.get(k);
				Timestamp entryTimeWithOffset=new Timestamp(nursingIntakeOutput.getEntry_timestamp().getTime()+offset);
				int hour=entryTimeWithOffset.getHours();

				int index=timeList.indexOf(nursingIntakeOutput.getEntry_timestamp().getHours());

				if(hour<=23 && hour>=8){
					index=hour-8;
				}else if(hour>=0 && hour<=7){
					index=hour+16;
				}

				int feedflagPrimary=0;
				int feedflagFormula=0;

				if(feedVolume.size()!=0) {

					double enteralActualVolumneActualFeed=0;
					double enteralActualVolumneFormulaFeed=0;

					if (nursingIntakeOutput.getPrimaryFeedValue() != null) {
						enteralActualVolumneActualFeed = nursingIntakeOutput.getPrimaryFeedValue();
						enteralActualVolumneActualFeed = Math.round(enteralActualVolumneActualFeed * 100.0) / 100.0;
						feedflagPrimary=1;
					}

					if (!BasicUtils.isEmpty(nursingIntakeOutput.getFormulaValue())) {
						enteralActualVolumneFormulaFeed = nursingIntakeOutput.getFormulaValue();
						enteralActualVolumneFormulaFeed = Math.round(enteralActualVolumneFormulaFeed * 100.0) / 100.0;
						feedflagFormula=1;
					}

					if(feedflagPrimary==1 && feedflagFormula==1){
						double totalFeed= enteralActualVolumneActualFeed + enteralActualVolumneFormulaFeed;
						feedVolume.set(index,parseFloat(String.valueOf(totalFeed)));
						feedVolumeFlag=true;
					}else if(feedflagPrimary==1){
						feedVolume.set(index,parseFloat(String.valueOf(enteralActualVolumneActualFeed)));
						feedVolumeFlag=true;
					}else if (feedflagFormula==1){
						feedVolume.set(index,parseFloat(String.valueOf(enteralActualVolumneFormulaFeed)));
						feedVolumeFlag=true;
					}
				}

				// Feed type
				if(feedType.size()!=0) {

					if (!BasicUtils.isEmpty(nursingIntakeOutput.getRoute())) {
						String route = nursingIntakeOutput.getRoute();
						feedType.set(index,String.valueOf(route));
						feedTypeFlag = true;
					}
				}

				// Feed Method
				if(feedMethod.size()!=0) {

					if(feedflagPrimary==1 && feedflagFormula==1){
						String primaryFeedType=null;
						String formulaFeedType=null;
						String feedTypeStr="";

						if (nursingIntakeOutput.getPrimaryFeedType() != null) {
							primaryFeedType= nursingIntakeOutput.getPrimaryFeedType();
						}

						if (nursingIntakeOutput.getFormulaType() != null) {
							formulaFeedType= nursingIntakeOutput.getFormulaType();
						}

						if(primaryFeedType!=null) {
							feedTypeStr =feedMap.get(primaryFeedType).toString();
						}
						if(formulaFeedType!=null) {
							if(feedTypeStr!="") {
								feedTypeStr = feedTypeStr+","+feedMap.get(formulaFeedType).toString();
							}else {
								feedTypeStr = feedMap.get(formulaFeedType).toString();
							}
						}
						feedMethod.set(index, feedTypeStr);
						feedMethodFlag = true;
					}else if(feedflagPrimary==1){
						if (nursingIntakeOutput.getPrimaryFeedType() != null) {
							String primaryFeedType= nursingIntakeOutput.getPrimaryFeedType();
							String feedTypeStr =feedMap.get(primaryFeedType).toString();
							feedMethod.set(index, feedTypeStr);
						}
						feedMethodFlag = true;
					}else if (feedflagFormula==1){
						if (nursingIntakeOutput.getFormulaType() != null) {
							String formulaFeedType= nursingIntakeOutput.getFormulaType();
							String feedTypeStr =feedMap.get(formulaFeedType).toString();
							feedMethod.set(index, feedTypeStr);
						}
						feedMethodFlag = true;
					}
				}

				// Parentral
				// Administered Volumne
				if(administeredFeedVolume.size()!=0){
					if (!BasicUtils.isEmpty(nursingIntakeOutput.getReadymadeDeliveredFeed())) {
						double readymadeDeliveredFeedValue = nursingIntakeOutput.getReadymadeDeliveredFeed();
						administeredFeedVolume.set(index,parseFloat(String.valueOf(readymadeDeliveredFeedValue)));
						administeredFeedVolumeFlag=true;
					}
				}

				if(intraLipid.size()!=0){
					// Lipid Volume
					if (!BasicUtils.isEmpty(nursingIntakeOutput.getLipid_delivered())) {
						Float lipidVolumneValue = nursingIntakeOutput.getLipid_delivered();
						intraLipid.set(index,lipidVolumneValue);
						intraLipidFlag=true;
					}
				}

				if(tpnDelivered.size()!=0){

					// TPN delivered
					if (!BasicUtils.isEmpty(nursingIntakeOutput.getTpn_delivered())) {
						Float tpn_deliveredValue = nursingIntakeOutput.getTpn_delivered();
						tpnDelivered.set(index,tpn_deliveredValue);
						tpnDeliveredFlag=true;
					}
				}

				if(calcium.size()!=0){

					// calcium value
					if (!BasicUtils.isEmpty(nursingIntakeOutput.getCalciumVolume())) {
						Float calciumValue = nursingIntakeOutput.getCalciumVolume();
						calcium.set(index,calciumValue);
						calciumFlag=true;
					}
				}

				// Output
				// adb girth
				if(AbdGirth.size()!=0){
					if (!BasicUtils.isEmpty(nursingIntakeOutput.getAbdomenGirth())) {
						String adbValue = nursingIntakeOutput.getAbdomenGirth();
						if (!BasicUtils.isEmpty(adbValue)) {
								float previousValue=0;
								previousValue+=parseFloat(adbValue);
								AbdGirth.set(index,previousValue);
								AbdGirthFlag=true;
						}
					}
				}

				// urine
				String urineValue = null;
				if(urine.size()!=0){
					if (!BasicUtils.isEmpty(nursingIntakeOutput.getUrinePassed()) && nursingIntakeOutput.getUrinePassed()) {
						if (!BasicUtils.isEmpty(nursingIntakeOutput.getUrine())) {
							urineValue = nursingIntakeOutput.getUrine();
							if(urineValue!=null) {
								float previousValue=0;
								if(urine.get(index)!=null){
									previousValue+=urine.get(index);
								}
								previousValue+=parseFloat(urineValue);
								urine.set(index,previousValue);
								urineFlag=true;
							}
						}
					}
				}

				// stoma
				String stomaValue = null;
				if(stomaList.size()!=0){
					if (!BasicUtils.isEmpty(nursingIntakeOutput.getStoma()) && nursingIntakeOutput.getStoma()!=null) {
						if (!BasicUtils.isEmpty(nursingIntakeOutput.getStoma())) {
							stomaValue = nursingIntakeOutput.getStoma();
							if(stomaValue!=null) {
								float previousValue=0;
								if(stomaList.get(index)!=null){
									previousValue+=stomaList.get(index);
								}
								previousValue+=parseFloat(stomaValue);
								stomaList.set(index,previousValue);
								stomaFlag=true;
							}
						}
					}
				}

				if(bloodInStool.size()!=0){
					// stool
					if (!BasicUtils.isEmpty(nursingIntakeOutput.getStoolPassed()) && nursingIntakeOutput.getStoolPassed()) {
						if (!BasicUtils.isEmpty(nursingIntakeOutput.getStool())) {
							String stoolValue = nursingIntakeOutput.getStool();
							// blood in stool
							if (stoolValue.equalsIgnoreCase("blood")) {
								bloodInStool.set(index,"YES");
								bloodInStoolFlag=true;
							}

							if (stoolValue.equalsIgnoreCase("other")) {
								if (!BasicUtils.isEmpty(nursingIntakeOutput.getStoolOther())) {
									stool.set(index,nursingIntakeOutput.getStoolOther());
								}
							} else {
								stool.set(index,stoolValue);
								stoolFlag=true;
							}
						}
					}
				}
				// gastric aspirate
				String gastricAspirateValue = null;
				if(gastricAspirate.size()!=0){
					if (!BasicUtils.isEmpty(nursingIntakeOutput.getGastricAspirate())) {
						gastricAspirateValue = nursingIntakeOutput.getGastricAspirate();

						float previousValue=0;
						if(gastricAspirate.get(index)!=null){
							previousValue+=gastricAspirate.get(index);
						}
						previousValue+=parseFloat(gastricAspirateValue);
						gastricAspirate.set(index,previousValue);
						gastricAspirateFlag=true;
					}
				}

				// aspirate color
				if(aspirateColor.size()!=0){
					if (!BasicUtils.isEmpty(nursingIntakeOutput.getAspirateColor())) {
						String aspirateValue = nursingIntakeOutput.getAspirateColor();
						if (aspirateValue.equalsIgnoreCase("other")) {
							if (BasicUtils.isEmpty(nursingIntakeOutput.getAspirateColorOther())) {
								aspirateColor.set(index,nursingIntakeOutput.getAspirateColorOther());
								aspirateColorFlag=true;
							}else {
								aspirateColor.set(index,aspirateValue);
								aspirateColorFlag=true;
							}
						}else{
							aspirateColor.set(index,aspirateValue);
							aspirateColorFlag=true;
						}
					}
				}
			}

			// total output
			for(int i=0;i<24;i++){
				if(totalOutputVolumne.size()!=0){

					float totalOutput=0;
					if(gastricAspirate.get(i)!=null && gastricAspirate.get(i)!=0){
						totalOutput+=gastricAspirate.get(i);
					}

					if(urine.get(i)!=null && urine.get(i)!=0){
						totalOutput+=urine.get(i);
					}

					if(stomaList.get(i)!=null){
						totalOutput+=stomaList.get(i);
					}

					if(totalOutput!=0){
						totalOutputVolumne.set(i,BasicUtils.round(totalOutput,2));
						totalOutputVolumneFlag=true;
					}
				}
			}

			// adding the blood product and heplock value here
			for(int i=0;i<bloodProductList.size();i++){
				NursingBloodproduct nursingBloodproductObject=bloodProductList.get(i);
				Timestamp entryTimeWithOffset=new Timestamp(nursingBloodproductObject.getExecution_time().getTime()+offset);
				int hour=entryTimeWithOffset.getHours();

				int index=timeList.indexOf(nursingBloodproductObject.getExecution_time().getHours());

				if(hour<=23 && hour>=8){
					index=hour-8;
				}else if(hour>=0 && hour<=7){
					index=hour+16;
				}

				if(nursingBloodproductObject.getDelivered_volume()!=null && !BasicUtils.isEmpty(nursingBloodproductObject.getDelivered_volume())){
					float previousValue=0;
					if(bloodProductVolume.get(index)!=null){
						previousValue+=bloodProductVolume.get(index);
					}
					previousValue+=nursingBloodproductObject.getDelivered_volume();
					bloodProductVolume.set(index,previousValue);
					bloodProductFlag=true;
				}
			}

			// Heparin
			if(getHeparinList.size()>0) {
				for (NursingHeplock nursingHeplock : getHeparinList) {
					Timestamp entryTimeWithOffset = new Timestamp(nursingHeplock.getExecution_time().getTime() + offset);
					int hour = entryTimeWithOffset.getHours();

					int index = timeList.indexOf(nursingHeplock.getExecution_time().getHours());

					if (hour <= 23 && hour >= 8) {
						index = hour - 8;
					} else if (hour >= 0 && hour <= 7) {
						index = hour + 16;
					}

					if (nursingHeplock.getDeliveredVolume() != null && !BasicUtils.isEmpty(nursingHeplock.getDeliveredVolume())) {
						float previousValue=0;
						if(heparinVolume.get(index)!=null){
							previousValue+=heparinVolume.get(index);
						}
						previousValue+=nursingHeplock.getDeliveredVolume();
						heparinVolume.set(index, previousValue);
						heparinFlag = true;
					}
				}
			}

			for(int i=0;i<timeList.size();i++) {

				double cummulativeValue = 0.0;

				// Total Intake
				double totalEn = 0.0;
				Float enValue = null;
				Float administeredValue = null;
				Float intraLipidValue = null;
				Float tpnDeliveredValue = null;
				Float bloodProduct=null;
				Float heparinVol=null;


				if(i<feedVolume.size()) {
					if (!BasicUtils.isEmpty(feedVolume.get(i))) {
						enValue = feedVolume.get(i);
						totalEn += enValue;
					}
				}


				if(i<administeredFeedVolume.size()) {
					if (!BasicUtils.isEmpty(administeredFeedVolume.get(i))) {
						administeredValue = administeredFeedVolume.get(i);
						totalEn += administeredValue;
					}
				}

				if(i<intraLipid.size()) {
					if (!BasicUtils.isEmpty(intraLipid.get(i))) {
						intraLipidValue = intraLipid.get(i);
						totalEn += intraLipidValue;
					}
				}

				if(i<tpnDelivered.size()) {
					if (!BasicUtils.isEmpty(tpnDelivered.get(i))) {
						tpnDeliveredValue = tpnDelivered.get(i);
						totalEn += tpnDeliveredValue;
					}
				}

				// adding the medication value in total EN
				if(i<medicationFinalList.size()){
					if (!BasicUtils.isEmpty(medicationFinalList.get(i))) {
						double medicationValue = medicationFinalList.get(i);
						totalEn += medicationValue;
					}
				}

				// Blood Product
				if(i<bloodProductVolume.size()){
					if (!BasicUtils.isEmpty(bloodProductVolume.get(i))) {
						bloodProduct = bloodProductVolume.get(i);
						totalEn += bloodProduct;
					}
				}

				// Heparin
				if(i<heparinVolume.size()){
					if (!BasicUtils.isEmpty(heparinVolume.get(i))) {
						heparinVol = heparinVolume.get(i);
						totalEn += heparinVol;
					}
				}

				// Total Output
				double outputValue = 0.0;

				if (i<totalOutputVolumne.size() && !BasicUtils.isEmpty(totalOutputVolumne.get(i))) {
					outputValue = totalOutputVolumne.get(i);
				}

				if (totalEn != 0.0 && outputValue != 0.0) {
					cummulativeValue = totalEn - outputValue;
				} else if (totalEn != 0.0 && outputValue == 0.0) {
					cummulativeValue = totalEn;
				} else if (totalEn == 0.0 && outputValue != 0.0) {
					cummulativeValue = (-1) * outputValue;
				}

				if (!BasicUtils.isEmpty(cummulativeValue)) {
					cummulativeValue = Math.round(cummulativeValue * 100.0) / 100.0;
				}

				int previousIndex=i-1;
				if (i!=0 && !BasicUtils.isEmpty(intakeOutputVolume.get(previousIndex))){
					Float previousValue=intakeOutputVolume.get(previousIndex);
					cummulativeValue=cummulativeValue+previousValue;
					if (!BasicUtils.isEmpty(cummulativeValue)) {
						cummulativeValue = Math.round(cummulativeValue * 100.0) / 100.0;
					}
					intakeOutputVolume.add(parseFloat(String.valueOf(cummulativeValue)));
				}else{
					intakeOutputVolume.add(parseFloat(String.valueOf(cummulativeValue)));
				}
			}

			patientChartData.setCummumlativeIntakeOutputBalance(intakeOutputVolume);

			// Enteral
			if(feedMethodFlag==true) {
				patientChartEnteralObject.setFeedMethod(feedMethod);
			}else{
				patientChartEnteralObject.setFeedMethod(new ArrayList<>());
			}

			if(feedTypeFlag==true) {
				patientChartEnteralObject.setFeedType(feedType);
			}else{
				patientChartEnteralObject.setFeedType(new ArrayList<>());
			}

			if(feedVolumeFlag==true) {
				patientChartEnteralObject.setFeedVolume(feedVolume);
			}else{
				patientChartEnteralObject.setFeedVolume(new ArrayList<>());
			}

			patientChartData.setPatientChartEnteralObject(patientChartEnteralObject);

			// Parentral

			if(administeredFeedVolumeFlag==true) {
				patientChartParentralObject.setAdministeredFeedVolume(administeredFeedVolume);
			}else{
				patientChartParentralObject.setAdministeredFeedVolume(new ArrayList<>());
			}

			if(intraLipidFlag==true) {
				patientChartParentralObject.setIntraLipid(intraLipid);
			}else{
				patientChartParentralObject.setIntraLipid(new ArrayList<>());
			}

			if(calciumFlag==true) {
				patientChartParentralObject.setCalcium(calcium);
			}else{
				patientChartParentralObject.setCalcium(new ArrayList<>());
			}

			// Blood Product
			if(bloodProductFlag==true){
				patientChartParentralObject.setBloodProduct(bloodProductVolume);
			}else{
				patientChartParentralObject.setBloodProduct(new ArrayList<>());
			}


			// Heparin
			if(heparinFlag==true){
				patientChartParentralObject.setHeparin(heparinVolume);
			}else{
				patientChartParentralObject.setHeparin(new ArrayList<>());
			}

			patientChartData.setPatientChartParentralObject(patientChartParentralObject);

			// output
			if(totalOutputVolumneFlag==true) {
				patientChartOutputObject.setTotalOutputVolumne(totalOutputVolumne);
			}else{
				patientChartOutputObject.setTotalOutputVolumne(new ArrayList<>());
			}

			if(AbdGirthFlag==true){
				patientChartOutputObject.setAbdGirth(AbdGirth);
			}else{
				patientChartOutputObject.setAbdGirth(new ArrayList<>());
			}
			if(urineFlag==true) {
				patientChartOutputObject.setUrine(urine);
			}else{
				patientChartOutputObject.setUrine(new ArrayList<>());
			}
			if(stoolFlag==true) {
				patientChartOutputObject.setStool(stool);
			}else{
				patientChartOutputObject.setStool(new ArrayList<>());
			}

			// Stoma
			if(stomaFlag==true){
				patientChartOutputObject.setStoma(stomaList);
			}else{
				patientChartOutputObject.setStool(new ArrayList<>());
			}

			if(bloodInStoolFlag==true) {
				patientChartOutputObject.setBloodInStool(bloodInStool);
			}else{
				patientChartOutputObject.setBloodInStool(new ArrayList<>());
			}

			if(gastricAspirateFlag==true) {
				patientChartOutputObject.setGastricAspirate(gastricAspirate);
			}else{
				patientChartOutputObject.setGastricAspirate(new ArrayList<>());
			}
			if(aspirateColorFlag==true){
				patientChartOutputObject.setAspirateColor(aspirateColor);
			}else{
				patientChartOutputObject.setAspirateColor(new ArrayList<>());
			}

			patientChartData.setPatientChartOutputObject(patientChartOutputObject);
		}


		PatientChartInvestigationObject patientChartInvestigationObject=getInvestigationData(uhid,timeList,fromDate,toDate,nursingVitalparameterList);
		patientChartData.setPatientChartInvestigationObject(patientChartInvestigationObject);


		// get the clinical alter setting
		String ClinicalAlertSettingQuery = "select obj from ClinicalAlertSettings as obj";
		List<ClinicalAlertSettings> ClinicalAlertSettingList = inicuDoa.getListFromMappedObjQuery(ClinicalAlertSettingQuery);
		if(ClinicalAlertSettingList.size()>0){
			HashMap<String,AlertMinMax> myAlertMap=new HashMap<>();
			for (ClinicalAlertSettings object: ClinicalAlertSettingList) {
				AlertMinMax alertMinMax=new AlertMinMax();
				if(object.getMinValue()!=null) {
					alertMinMax.setMinValue(Integer.parseInt(object.getMinValue()));
				}
				if(object.getMaxValue()!=null) {
					alertMinMax.setMaxValue(Integer.parseInt(object.getMaxValue()));
				}
				alertMinMax.setDependencies(object.getDependency());
				myAlertMap.put(object.getParameterName(),alertMinMax);
			}
			returnObj.setClinicalAlertSettingsList(myAlertMap);
		}

		returnObj.setPatientChartData(patientChartData);
		returnObj.setMessage("Patient chart");
		returnObj.setStatusCode(200);
		returnObj.setStatus(true);

		return returnObj;
	}

	@SuppressWarnings("unchecked")
	@Override
	public NursingObservationPojo getNursingObservationInfo(String tabType, String entryDate, String uhid,
			String loggedInUser, NursingObservationPojo nursingInfoPojo) {

		java.sql.Date sqlPresentDate = null;
		java.sql.Date sqlPresentDateGMT = null;
		String entrydateGMT = "";

		try {
			entryDate = entryDate.substring(0, 24);
			DateFormat readFormat = new SimpleDateFormat("EEE MMM dd yyyy hh:mm:ss");
			java.util.Date presentDate = readFormat.parse(entryDate);
			sqlPresentDate = new java.sql.Date(presentDate.getTime());

			int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
					- TimeZone.getDefault().getRawOffset();

			sqlPresentDateGMT = new java.sql.Date(presentDate.getTime() - offset);

			entrydateGMT = "" + sqlPresentDateGMT + "";
		} catch (ParseException e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUser,
					uhid, "GET_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}

		this.entryDate = "" + sqlPresentDate + "";
		this.uhid = uhid;
		this.loggedInUser = loggedInUser;

		NursingObservationPojo nursingObserVationSheetInfo = null;
		if (nursingInfoPojo == null) {
			nursingObserVationSheetInfo = new NursingObservationPojo();
		} else {
			nursingObserVationSheetInfo = nursingInfoPojo;
		}

		// getting baby visits data...............
		BabyVisitsObj babyVisit = getBabyVisits(entryDate, this.uhid);
		if (babyVisit.getBirthGestationweeks() == null) {
			babyVisit.setBirthGestationweeks("");
		}
		if (babyVisit.getBirthGestationdays() == null) {
			babyVisit.setBirthGestationdays("");
		}
		if (BasicUtils.isEmpty(babyVisit.getCorrectedGestationweeks())
				|| babyVisit.getCorrectedGestationweeks().toString().equalsIgnoreCase("null")) {
			babyVisit.setCorrectedGestationweeks("");
		}
		if (BasicUtils.isEmpty(babyVisit.getCorrectedGestationdays())
				|| babyVisit.getCorrectedGestationdays().toString().equalsIgnoreCase("null")) {
			babyVisit.setCorrectedGestationdays("");
		}

		nursingObserVationSheetInfo.setBabyVisit(babyVisit);

		if (tabType.equalsIgnoreCase(BasicConstants.VITAL_PARAM)) {
			//Setting Parameter (Apnea Duration)
			String babyDetailListQuery = "select obj from BabyDetail as obj where uhid='" + uhid + "'";
			List<BabyDetail> babyDetailList = notesDoa.getListFromMappedObjNativeQuery(babyDetailListQuery);
			if(babyDetailList != null && babyDetailList.size() > 0) {
				String branchName = babyDetailList.get(0).getBranchname();
				if(branchName != null && branchName != "") {
					String refHospitalbranchnameQuery = "select obj from RefHospitalbranchname as obj where branchname='" + branchName + "'";
					List<RefHospitalbranchname> refHospitalbranchnameList = inicuDoa.getListFromMappedObjQuery(refHospitalbranchnameQuery);
					if(refHospitalbranchnameList != null && refHospitalbranchnameList.size() > 0) {
						Integer apneaDuration = refHospitalbranchnameList.get(0).getDuration();
						if(apneaDuration != null) {
							nursingObserVationSheetInfo.setApneaDuration(apneaDuration);
						}
					}
				}
			}

			//Setting Apnea Event List
			String apneaEventQuery = "select obj from ApneaEvent obj where uhid='" + uhid
					+ "' and marked is null order by server_time";
			List<ApneaEvent> apneaEventList = notesDoa.getListFromMappedObjNativeQuery(apneaEventQuery);
			if(!BasicUtils.isEmpty(apneaEventList)) {
				nursingObserVationSheetInfo.setApneaEventList(apneaEventList);
			}
			
			//Setting Declined Events
			apneaEventQuery = "select obj from DeclinedApneaEvent obj where uhid='" + uhid
					+ "'  order by start_time";
			List<DeclinedApneaEvent> declinedApneaEventList = notesDoa.getListFromMappedObjNativeQuery(apneaEventQuery);
			nursingObserVationSheetInfo.setDeclinedApneaEventList(declinedApneaEventList);
			
			
			// set current data and previous list data for vital parameters

			// get Previous filled data ..
			List<NursingEpisode> previousEpisodeData = getPreviousEpisode();
			List<NursingVitalparameter> previousVitalParamData = getPreviousVitalInformation(false);
			List<NursingEpisodeVitalHistoryPojo> pastHistoryList = getNursingChartPastHistory(previousEpisodeData,
					previousVitalParamData);

			nursingObserVationSheetInfo.getVitalParametersInfo().put(BasicConstants.PREVIOUS_DATA_STR, pastHistoryList);

			nursingObserVationSheetInfo.getVitalParametersInfo().put(BasicConstants.NURSING_EPISODE_EMPTY_OBJ_STR,
					new NursingEpisode());

			if (!BasicUtils.isEmpty(previousVitalParamData) && !previousVitalParamData.isEmpty())
			// populating last saved intake to easy nurses entry.
			{
				previousVitalParamData.get(0).setNnVitalparameterid(null);
				NursingVitalparameter vitalTemp = previousVitalParamData.get(0);
				NursingVitalparameter currentVitalParameters = getCurrentVitalInformation(vitalTemp, entrydateGMT);
				nursingObserVationSheetInfo.getVitalParametersInfo().put(BasicConstants.EMPTY_OBJ_STR,
						currentVitalParameters);

				previousEpisodeData = getPreviousEpisode();
				previousVitalParamData = getPreviousVitalInformation(false);
				pastHistoryList = getNursingChartPastHistory(previousEpisodeData, previousVitalParamData);

				nursingObserVationSheetInfo.getVitalParametersInfo().put(BasicConstants.PREVIOUS_DATA_STR,
						pastHistoryList);

				System.out.println(nursingObserVationSheetInfo);
			} else {
				nursingObserVationSheetInfo.getVitalParametersInfo().put(BasicConstants.EMPTY_OBJ_STR,
						getCurrentVitalInformation(null, entrydateGMT));
			}
		}
		else if (tabType.equalsIgnoreCase(BasicConstants.INTAKE)) {

			// nursingObserVationSheetInfo.getIntakeInfo().put(BasicConstants.EMPTY_OBJ_STR,
			// new VwNursingnotesIntakeFinal2());

			List<VwNursingnotesIntakeFinal2> previousIntake = getPreviousIntakeInfo();

			nursingObserVationSheetInfo.getIntakeInfo().put(BasicConstants.PREVIOUS_DATA_STR, previousIntake);

			// get blood product prescribed by doctor...
			BloodProduct bloodProduct = getBloodProductsInfo(this.uhid);

			nursingObserVationSheetInfo.getIntakeInfo().put(BasicConstants.CURRENT_BLOOD_PRODUCT, bloodProduct);

			VwNursingnotesIntakeFinal2 intakeData = new VwNursingnotesIntakeFinal2();
			if (!BasicUtils.isEmpty(previousIntake)) // populating last saved
			// intake to easy nurses
			// entry .
			{
				previousIntake.get(0).setNnIntakeid(null);
				intakeData = previousIntake.get(0);
			} else {
				intakeData.setBolustype("Bolus");
			}

			/*
			 * intakeData.setBloodproduct(bloodProduct.getBloodproduct());
			 * intakeData.setBloodDose(bloodProduct.getDose());
			 * intakeData.setBloodDuration(bloodProduct.getDuration());
			 */

			// get baby feed & IV info entered by doctor ....
			String feedQuery = "select obj from BabyfeedDetail obj where uhid='" + uhid
					+ "' and to_char(creationtime,'YYYY-MM-DD')='" + sqlPresentDate + "' order by entrydatetime desc";
			List<BabyfeedDetail> babyFeedList = notesDoa.getListFromMappedObjNativeQuery(feedQuery);
			BabyfeedDetail babyTodayFeed = new BabyfeedDetail();
			if (!BasicUtils.isEmpty(babyFeedList)) {
				babyTodayFeed = babyFeedList.get(0);
				// here we are populating list feedMethodList
				if (!BasicUtils.isEmpty(babyTodayFeed.getFeedmethod())) {
					String feedMethod = babyTodayFeed.getFeedmethod().replace("[", "").replace("]", "").replace(" ",
							"");
					ArrayList<String> listFeedMethod;
					if (!BasicUtils.isEmpty(feedMethod)) {
						listFeedMethod = new ArrayList<String>(Arrays.asList(feedMethod.split(",")));
						babyTodayFeed.setFeedmethodList(listFeedMethod);
					}
				}
			} else {
				babyTodayFeed.setIsNormalTpn(true);
			}

			nursingObserVationSheetInfo.getIntakeInfo().put(BasicConstants.BABY_FEED_BY_DOCTOR, babyTodayFeed);

			nursingObserVationSheetInfo.getIntakeInfo().put(BasicConstants.EMPTY_OBJ_STR, intakeData);

			// check box status for each section.
			nursingObserVationSheetInfo.getIntakeInfo().put("isFeedGiven", false);
			nursingObserVationSheetInfo.getIntakeInfo().put("isIVGiven", false);
			nursingObserVationSheetInfo.getIntakeInfo().put("isBloodGiven", false);
			nursingObserVationSheetInfo.getIntakeInfo().put("isBolusGiven", false);

		}
		else if (tabType.equalsIgnoreCase(BasicConstants.OUTPUT)) {

			// nursingObserVationSheetInfo.getOutputInfo().put(BasicConstants.EMPTY_OBJ_STR,
			// new VwNursingnotesOutputFinal());

			List<VwNursingnotesOutputFinal> previousOutput = getPreviousOutputInfo(false);

			nursingObserVationSheetInfo.getOutputInfo().put(BasicConstants.PREVIOUS_DATA_STR, previousOutput);

			VwNursingnotesOutputFinal emptyObj = new VwNursingnotesOutputFinal();
			if (!BasicUtils.isEmpty(previousOutput)) {
				VwNursingnotesOutputFinal prevObj = previousOutput.get(0);
				emptyObj.setOutputid(null);
				emptyObj.setTotalUo(prevObj.getTotalUo());
			}
			nursingObserVationSheetInfo.getOutputInfo().put(BasicConstants.EMPTY_OBJ_STR, emptyObj);

		}
		else if (tabType.equalsIgnoreCase(BasicConstants.CATHERS)) {

			// nursingObserVationSheetInfo.getCathetersInfo().put(BasicConstants.EMPTY_OBJ_STR,
			// new NursingCatheter());

			List<NursingCatheter> previousCathers = getPreviousCathersInfo(false);

			nursingObserVationSheetInfo.getCathetersInfo().put(BasicConstants.PREVIOUS_DATA_STR, previousCathers);

			if (!BasicUtils.isEmpty(previousCathers)) {
				previousCathers.get(0).setNnCathetersid(null);
				nursingObserVationSheetInfo.getCathetersInfo().put(BasicConstants.EMPTY_OBJ_STR,
						previousCathers.get(0));

			} else
				nursingObserVationSheetInfo.getCathetersInfo().put(BasicConstants.EMPTY_OBJ_STR, new NursingCatheter());

		}
		else if (tabType.equalsIgnoreCase(BasicConstants.BLOOD_GAS)) {

			List<NursingBloodGas> previousBloodGasData = getPreviousBloodGasInfo(false);
			nursingObserVationSheetInfo.getBloodGasInfo().put(BasicConstants.PREVIOUS_DATA_STR, previousBloodGasData);

			if (!BasicUtils.isEmpty(previousBloodGasData)) {
				previousBloodGasData.get(0).setNnBloodgasid(null);
				nursingObserVationSheetInfo.getBloodGasInfo().put(BasicConstants.EMPTY_OBJ_STR,
						previousBloodGasData.get(0));

			} else
				nursingObserVationSheetInfo.getBloodGasInfo().put(BasicConstants.EMPTY_OBJ_STR, new NursingBloodGas());
		}
		else if (tabType.equalsIgnoreCase(BasicConstants.NURO_VITALS)) {

			// VwPupilReactivity currentNeuroVitals =
			// getCurrentNeuroVitalsInfo();
			// nursingObserVationSheetInfo.getNeuroVitalsInfo().put(BasicConstants.EMPTY_OBJ_STR,
			// currentNeuroVitals);

			List<VwPupilReactivity> previousNeuroVitalsData = getPreviousNeuroVitalsInfo(false);
			nursingObserVationSheetInfo.getNeuroVitalsInfo().put(BasicConstants.PREVIOUS_DATA_STR,
					previousNeuroVitalsData);
			if (!BasicUtils.isEmpty(previousNeuroVitalsData)) {
				previousNeuroVitalsData.get(0).setNnNeorovitalsid(null);

				nursingObserVationSheetInfo.getNeuroVitalsInfo().put(BasicConstants.EMPTY_OBJ_STR,
						previousNeuroVitalsData.get(0));

			} else
				nursingObserVationSheetInfo.getNeuroVitalsInfo().put(BasicConstants.EMPTY_OBJ_STR,
						new VwPupilReactivity());
		}
		else if (tabType.equalsIgnoreCase(BasicConstants.VENTILATOR)) {
			// NursingVentilaor currentVentilator = getCurrentventilatorInfo();
			// nursingObserVationSheetInfo.getVentilatorInfo().put(BasicConstants.EMPTY_OBJ_STR,
			// currentVentilator);

			String pphnQuery = "select obj from SaRespPphn obj where uhid='" + uhid
					+ "' order by assessment_time desc";
			List<SaRespPphn> pphnList = notesDoa.getListFromMappedObjNativeQuery(pphnQuery);
			if(!BasicUtils.isEmpty(pphnList)) {
				nursingObserVationSheetInfo.setInoObject(pphnList.get(0));
			}else {
				SaRespPphn obj = new SaRespPphn();
				nursingObserVationSheetInfo.setInoObject(obj);
			}

			List<NursingVentilaor> previousVentilatorData = getPreviousVentilaorInfo(false);
			nursingObserVationSheetInfo.getVentilatorInfo().put(BasicConstants.PREVIOUS_DATA_STR,
					previousVentilatorData);
			if (!BasicUtils.isEmpty(previousVentilatorData)) {
				previousVentilatorData.get(0).setNnVentilaorid(null);
				NursingVentilaor prevVentilator = previousVentilatorData.get(0);
				nursingObserVationSheetInfo.getVentilatorInfo().put(BasicConstants.EMPTY_OBJ_STR,
						getCurrentVentilatorInformation(prevVentilator,entrydateGMT));
			} else {
				nursingObserVationSheetInfo.getVentilatorInfo().put(BasicConstants.EMPTY_OBJ_STR,
						getCurrentVentilatorInformation(new NursingVentilaor(), entrydateGMT));
			}
			previousVentilatorData = getPreviousVentilaorInfo(false);
			nursingObserVationSheetInfo.getVentilatorInfo().put(BasicConstants.PREVIOUS_DATA_STR,
					previousVentilatorData);
		}
		else if (tabType.equalsIgnoreCase(BasicConstants.DAILY_ASSESSMENT)) {
			NursingDailyassesment currentDailyAssessment = getCurrentDailyAssessmentInfo();
			nursingObserVationSheetInfo.getDailyAssessmentInfo().put(BasicConstants.EMPTY_OBJ_STR,
					currentDailyAssessment);

			NursingDailyAssesmentJSON previousDailyAssessment = getPreviousDailyAssessmentInfo();
			nursingObserVationSheetInfo.getDailyAssessmentInfo().put(BasicConstants.PREVIOUS_DATA_STR,
					previousDailyAssessment);
		}
		else if (tabType.equalsIgnoreCase(BasicConstants.MISC)) {
			// NursingMisc currentMisc = getCurrentMiscInfo();
			// nursingObserVationSheetInfo.getMiscInfo().put(BasicConstants.EMPTY_OBJ_STR,currentMisc);

			List<NursingMisc> previousMisc = getPreviousMiscInfo();
			nursingObserVationSheetInfo.getMiscInfo().put(BasicConstants.PREVIOUS_DATA_STR, previousMisc);
			if (!BasicUtils.isEmpty(previousMisc)) {
				previousMisc.get(0).setNnMiscid(null);
				nursingObserVationSheetInfo.getMiscInfo().put(BasicConstants.EMPTY_OBJ_STR, previousMisc.get(0));
				if (!BasicUtils.isEmpty(previousMisc.get(0).getPhototherapyStartDate())) {
					nursingObserVationSheetInfo.setPhototTheropy(true);
				}
			} else
				nursingObserVationSheetInfo.getMiscInfo().put(BasicConstants.EMPTY_OBJ_STR, new NursingMisc());
		}

		return nursingObserVationSheetInfo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public NursingVentilaor getVentilatorInfoByDate(String uhid, String entryDate) {

		Timestamp today = null;
		Timestamp previousHour = null;
		NursingVentilaor nursingVentilaor = new NursingVentilaor();
		entryDate = entryDate.substring(0, 24);
		DateFormat readFormat = new SimpleDateFormat("EEE MMM dd yyyy hh:mm:ss");
		java.util.Date presentDate;
		try {
			presentDate = readFormat.parse(entryDate);
			int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
					- TimeZone.getDefault().getRawOffset();
			if (offset == 0) {
				today = new Timestamp(presentDate.getTime());
				previousHour = new Timestamp(today.getTime() - (60 * 60 * 1000));
			} else {
				today = new Timestamp(presentDate.getTime() - offset);
				previousHour = new Timestamp(presentDate.getTime() - offset - (60 * 60 * 1000));
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}

		String query = "select obj from DeviceVentilatorData as obj where uhid='" + uhid
				+ "' and start_time <='" + today + "' and start_time >='" + previousHour + "' order by start_time desc";
		List<DeviceVentilatorData> listDeviceVentilator = notesDoa.getListFromMappedObjNativeQuery(query);
		if (!BasicUtils.isEmpty(listDeviceVentilator)) {
			DeviceVentilatorData deviceVentData = listDeviceVentilator.get(0);

			if (!BasicUtils.isEmpty(deviceVentData.getC())) {
				nursingVentilaor.setC(deviceVentData.getC());
			}else {
				nursingVentilaor.setC(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getR())) {
				nursingVentilaor.setR(deviceVentData.getR());
			}else {
				nursingVentilaor.setR(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getTc())) {
				nursingVentilaor.setTc(deviceVentData.getTc());
			}else {
				nursingVentilaor.setTc(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getC20())) {
				nursingVentilaor.setC20(deviceVentData.getC20());
			}else {
				nursingVentilaor.setC20(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getTrigger())) {
				nursingVentilaor.setTrigger(deviceVentData.getTrigger());
			}else {
				nursingVentilaor.setTrigger(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getRvr())) {
				nursingVentilaor.setRvr(deviceVentData.getRvr());
			}else {
				nursingVentilaor.setRvr(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getTi())) {
				nursingVentilaor.setTi(deviceVentData.getTi());
			}else {
				nursingVentilaor.setTi(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getTe())) {
				nursingVentilaor.setTe(deviceVentData.getTe());
			}else {
				nursingVentilaor.setTe(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getMap())) {
				nursingVentilaor.setMap(deviceVentData.getMap());
			}else {
				nursingVentilaor.setMap(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getPeep())) {
				nursingVentilaor.setPeepCpap(deviceVentData.getPeep());
			}else {
				nursingVentilaor.setPeepCpap(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getPip())) {
				nursingVentilaor.setPip(deviceVentData.getPip());
			}else {
				nursingVentilaor.setPip(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getDco2())) {
				nursingVentilaor.setDco2(deviceVentData.getDco2());
			}else {
				nursingVentilaor.setDco2(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getVtim())) {
				nursingVentilaor.setVtim(deviceVentData.getVtim());
			}else {
				nursingVentilaor.setVtim(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getVthf())) {
				nursingVentilaor.setVthf(deviceVentData.getVthf());
			}else {
				nursingVentilaor.setVthf(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getVt())) {
				nursingVentilaor.setTidalVolume(deviceVentData.getVt());
			}else {
				nursingVentilaor.setTidalVolume(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getLeak())) {
				nursingVentilaor.setLeak(deviceVentData.getLeak());
			}else {
				nursingVentilaor.setLeak(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getSpont())) {
				nursingVentilaor.setSpont(deviceVentData.getSpont());
			}else {
				nursingVentilaor.setSpont(null);
			}

			if(!BasicUtils.isEmpty(deviceVentData.getMv())){
				nursingVentilaor.setMinuteVolume(deviceVentData.getMv());
			}else {
				nursingVentilaor.setMinuteVolume(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getMvim())) {
				nursingVentilaor.setMvim(deviceVentData.getMvim());
			}else {
				nursingVentilaor.setMvim(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getFlowpermin())) {
				nursingVentilaor.setFlowPerMin(deviceVentData.getFlowpermin());
			}else {
				nursingVentilaor.setFlowPerMin(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getSpo2())) {
				nursingVentilaor.setSpo2(deviceVentData.getSpo2());
			}else {
				nursingVentilaor.setSpo2(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getFio2())) {
				nursingVentilaor.setFio2(deviceVentData.getFio2());
			}else {
				nursingVentilaor.setFio2(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getFreqrate())) {
				nursingVentilaor.setFreqRate(deviceVentData.getFreqrate());
			}else {
				nursingVentilaor.setFreqRate(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getNoppm())) {
				nursingVentilaor.setNoPpm(deviceVentData.getNoppm());
			}else {
				nursingVentilaor.setNoPpm(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getAmplitude())) {
				nursingVentilaor.setAmplitude(deviceVentData.getAmplitude());
			}else {
				nursingVentilaor.setAmplitude(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getRate())) {
				nursingVentilaor.setFrequency(deviceVentData.getRate());
			}else {
				nursingVentilaor.setFrequency(null);
			}

			nursingVentilaor.setVentName(null);
			String deviceIdGetQuery = "SELECT obj FROM BedDeviceDetail as obj " + "WHERE status ='true' AND time_to is null "
					+ "AND uhid='" + uhid + "'";
			List<BedDeviceDetail> deviceIdList = notesDoa.getListFromMappedObjNativeQuery(deviceIdGetQuery);
			if (!BasicUtils.isEmpty(deviceIdList)) {
				for(int i=0;i<deviceIdList.size();i++) {
					BedDeviceDetail deviceIdObj = deviceIdList.get(i);
					String deviceDetailGetQuery = "SELECT obj FROM DeviceDetail as obj " + "WHERE deviceid ='"
							+ deviceIdObj.getDeviceid() + "'";
					List<DeviceDetail> deviceDetailList = notesDoa.getListFromMappedObjNativeQuery(deviceDetailGetQuery);
					if (!BasicUtils.isEmpty(deviceDetailList)) {
						DeviceDetail deviceDetailObj = deviceDetailList.get(0);
						if(deviceDetailObj.getDevicetype().equalsIgnoreCase("Ventilator")) {
							nursingVentilaor.setVentName(deviceDetailObj.getDevicename());
						}
					}
				}
			}
		}

		String queryRespSupportToFindLastActive = "Select obj from RespSupport obj where uhid='" + uhid
				+ "' and isactive='true' and creationtime <= '" + today + "' order by creationtime desc";

		List<RespSupport> respSupportActiveList = inicuDoa.getListFromMappedObjQuery(queryRespSupportToFindLastActive);

		if (!BasicUtils.isEmpty(respSupportActiveList)) {
			String controlType = null;
			nursingVentilaor.setDoctorventName(null);
			String deviceIdGetQuery = "SELECT obj FROM BedDeviceDetail as obj " + "WHERE status ='true' AND time_to is null "
					+ "AND uhid='" + uhid + "'";
			List<BedDeviceDetail> deviceIdList = notesDoa.getListFromMappedObjNativeQuery(deviceIdGetQuery);
			if (!BasicUtils.isEmpty(deviceIdList)) {
				for(int i=0;i<deviceIdList.size();i++) {
					BedDeviceDetail deviceIdObj = deviceIdList.get(i);
					String deviceDetailGetQuery = "SELECT obj FROM DeviceDetail as obj " + "WHERE deviceid ='"
							+ deviceIdObj.getDeviceid() + "'";
					List<DeviceDetail> deviceDetailList = notesDoa.getListFromMappedObjNativeQuery(deviceDetailGetQuery);
					if (!BasicUtils.isEmpty(deviceDetailList)) {
						DeviceDetail deviceDetailObj = deviceDetailList.get(0);
						if(deviceDetailObj.getDevicetype().equalsIgnoreCase("Ventilator")) {
							nursingVentilaor.setDoctorventName(deviceDetailObj.getDevicename());
						}
					}
				}
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getC())) {
				nursingVentilaor.setDoctorc(respSupportActiveList.get(0).getC());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getR())) {
				nursingVentilaor.setDoctorr(respSupportActiveList.get(0).getR());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getTc())) {
				nursingVentilaor.setDoctortc(respSupportActiveList.get(0).getTc());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getC20())) {
				nursingVentilaor.setDoctorc20(respSupportActiveList.get(0).getC20());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getTrigger())) {
				nursingVentilaor.setDoctortrigger(respSupportActiveList.get(0).getTrigger());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getRvr())) {
				nursingVentilaor.setDoctorrvr(respSupportActiveList.get(0).getRvr());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getRsIt())) {
				nursingVentilaor.setDoctorti(respSupportActiveList.get(0).getRsIt());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getRsEt())) {
				nursingVentilaor.setDoctorte(respSupportActiveList.get(0).getRsEt());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getRsMap())) {
				nursingVentilaor.setDoctormap(respSupportActiveList.get(0).getRsMap());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getRsPeep())) {
				nursingVentilaor.setDoctorpeepCpap(respSupportActiveList.get(0).getRsPeep());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getRsPip())) {
				nursingVentilaor.setDoctorpip(respSupportActiveList.get(0).getRsPip());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getDco2())) {
				nursingVentilaor.setDoctordco2(respSupportActiveList.get(0).getDco2());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getVtim())) {
				nursingVentilaor.setDoctorvtim(respSupportActiveList.get(0).getVtim());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getVthf())) {
				nursingVentilaor.setDoctorvthf(respSupportActiveList.get(0).getVthf());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getRsTv())) {
				nursingVentilaor.setDoctortidalVolume(respSupportActiveList.get(0).getRsTv());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getRsTvInMl())) {
				nursingVentilaor.setDoctortidalVolumeml(respSupportActiveList.get(0).getRsTvInMl().toString());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getLeak())) {
				nursingVentilaor.setDoctorleak(respSupportActiveList.get(0).getLeak());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getSpont())) {
				nursingVentilaor.setDoctorspont(respSupportActiveList.get(0).getSpont());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getRsMv())) {
				nursingVentilaor.setDoctorminuteVolume(respSupportActiveList.get(0).getRsMv());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getMvim())) {
				nursingVentilaor.setDoctormvim(respSupportActiveList.get(0).getMvim());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getRsFlowRate())) {
				nursingVentilaor.setDoctorflowPerMin(respSupportActiveList.get(0).getRsFlowRate());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getRsSpo2())) {
				nursingVentilaor.setDoctorspo2(respSupportActiveList.get(0).getRsSpo2());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getRsFio2())) {
				nursingVentilaor.setDoctorfio2(respSupportActiveList.get(0).getRsFio2());
			}

			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getRsControlType())) {
				controlType = (respSupportActiveList.get(0).getRsControlType());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getRsVentType())) {
				String ventMode = respSupportActiveList.get(0).getRsVentType();
				if (ventMode.equals("Mechanical Ventilation")) {
					String mvType = respSupportActiveList.get(0).getRsMechVentType();
					if (!BasicUtils.isEmpty(controlType)) {
						ventMode = mvType + "-" + controlType;
						nursingVentilaor.setDoctorventmode(ventMode);
					} else {
						nursingVentilaor.setDoctorventmode(mvType);
					}
				} else {
					nursingVentilaor.setDoctorventmode(ventMode);
				}

			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getRsAmplitude())) {
				nursingVentilaor.setDoctoramplitude(respSupportActiveList.get(0).getRsAmplitude());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getRsFrequency())) {
				nursingVentilaor.setDoctorfrequency(respSupportActiveList.get(0).getRsFrequency());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getRsFrequency())) {
				nursingVentilaor.setDoctorfreqRate(respSupportActiveList.get(0).getRsFrequency());
			}
		}
		return nursingVentilaor;
	}

	@SuppressWarnings("unchecked")
	private Object getCurrentVentilatorInformation(NursingVentilaor nursingVentilaor, String entrydateGMT) {

		String query = "select obj from DeviceVentilatorData as obj where uhid='" + uhid
				+ "' and to_char(creationtime,'YYYY-MM-DD')='" + entrydateGMT + "' order by creationtime desc";
		List<DeviceVentilatorData> listDeviceVentilator = notesDoa.getListFromMappedObjNativeQuery(query);
		if (!BasicUtils.isEmpty(listDeviceVentilator)) {
			DeviceVentilatorData deviceVentData = listDeviceVentilator.get(0);

			if (!BasicUtils.isEmpty(deviceVentData.getC())) {
				nursingVentilaor.setC(deviceVentData.getC());
			}else {
				nursingVentilaor.setC(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getR())) {
				nursingVentilaor.setR(deviceVentData.getR());
			}else {
				nursingVentilaor.setR(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getTc())) {
				nursingVentilaor.setTc(deviceVentData.getTc());
			}else {
				nursingVentilaor.setTc(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getC20())) {
				nursingVentilaor.setC20(deviceVentData.getC20());
			}else {
				nursingVentilaor.setC20(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getTrigger())) {
				nursingVentilaor.setTrigger(deviceVentData.getTrigger());
			}else {
				nursingVentilaor.setTrigger(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getRvr())) {
				nursingVentilaor.setRvr(deviceVentData.getRvr());
			}else {
				nursingVentilaor.setRvr(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getTi())) {
				nursingVentilaor.setTi(deviceVentData.getTi());
			}else {
				nursingVentilaor.setTi(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getTe())) {
				nursingVentilaor.setTe(deviceVentData.getTe());
			}else {
				nursingVentilaor.setTe(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getMap())) {
				nursingVentilaor.setMap(deviceVentData.getMap());
			}else {
				nursingVentilaor.setMap(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getPeep())) {
				nursingVentilaor.setPeepCpap(deviceVentData.getPeep());
			}else {
				nursingVentilaor.setPeepCpap(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getPip())) {
				nursingVentilaor.setPip(deviceVentData.getPip());
			}else {
				nursingVentilaor.setPip(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getDco2())) {
				nursingVentilaor.setDco2(deviceVentData.getDco2());
			}else {
				nursingVentilaor.setDco2(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getVtim())) {
				nursingVentilaor.setVtim(deviceVentData.getVtim());
			}else {
				nursingVentilaor.setVtim(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getVthf())) {
				nursingVentilaor.setVthf(deviceVentData.getVthf());
			}else {
				nursingVentilaor.setVthf(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getVt())) {
				nursingVentilaor.setTidalVolume(deviceVentData.getVt());
			}else {
				nursingVentilaor.setTidalVolume(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getLeak())) {
				nursingVentilaor.setLeak(deviceVentData.getLeak());
			}else {
				nursingVentilaor.setLeak(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getSpont())) {
				nursingVentilaor.setSpont(deviceVentData.getSpont());
			}else {
				nursingVentilaor.setSpont(null);
			}

			if(!BasicUtils.isEmpty(deviceVentData.getMv())){
				nursingVentilaor.setMinuteVolume(deviceVentData.getMv());
			}else {
				nursingVentilaor.setMinuteVolume(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getMvim())) {
				nursingVentilaor.setMvim(deviceVentData.getMvim());
			}else {
				nursingVentilaor.setMvim(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getFlowpermin())) {
				nursingVentilaor.setFlowPerMin(deviceVentData.getFlowpermin());
			}else {
				nursingVentilaor.setFlowPerMin(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getSpo2())) {
				nursingVentilaor.setSpo2(deviceVentData.getSpo2());
			}else {
				nursingVentilaor.setSpo2(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getFio2())) {
				nursingVentilaor.setFio2(deviceVentData.getFio2());
			}else {
				nursingVentilaor.setFio2(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getFreqrate())) {
				nursingVentilaor.setFreqRate(deviceVentData.getFreqrate());
			}else {
				nursingVentilaor.setFreqRate(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getNoppm())) {
				nursingVentilaor.setNoPpm(deviceVentData.getNoppm());
			}else {
				nursingVentilaor.setNoPpm(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getAmplitude())) {
				nursingVentilaor.setAmplitude(deviceVentData.getAmplitude());
			}else {
				nursingVentilaor.setAmplitude(null);
			}

			if (!BasicUtils.isEmpty(deviceVentData.getRate())) {
				nursingVentilaor.setFrequency(deviceVentData.getRate());
			}else {
				nursingVentilaor.setFrequency(null);
			}

			nursingVentilaor.setVentName(null);
			String deviceIdGetQuery = "SELECT obj FROM BedDeviceDetail as obj " + "WHERE status ='true' AND time_to is null "
					+ "AND uhid='" + uhid + "'";
			List<BedDeviceDetail> deviceIdList = notesDoa.getListFromMappedObjNativeQuery(deviceIdGetQuery);
			if (!BasicUtils.isEmpty(deviceIdList)) {
				for(int i=0;i<deviceIdList.size();i++) {
					BedDeviceDetail deviceIdObj = deviceIdList.get(i);
					String deviceDetailGetQuery = "SELECT obj FROM DeviceDetail as obj " + "WHERE deviceid ='"
							+ deviceIdObj.getDeviceid() + "'";
					List<DeviceDetail> deviceDetailList = notesDoa.getListFromMappedObjNativeQuery(deviceDetailGetQuery);
					if (!BasicUtils.isEmpty(deviceDetailList)) {
						DeviceDetail deviceDetailObj = deviceDetailList.get(0);
						if(deviceDetailObj.getDevicetype().equalsIgnoreCase("Ventilator")) {
							nursingVentilaor.setVentName(deviceDetailObj.getDevicename());
						}
					}
				}
			}
		}

		String queryRespSupportToFindLastActive = "Select obj from RespSupport obj where uhid='" + uhid
				+ "' order by creationtime desc";

		List<RespSupport> respSupportActiveList = inicuDoa.getListFromMappedObjQuery(queryRespSupportToFindLastActive);

		if (!BasicUtils.isEmpty(respSupportActiveList)) {
			String controlType = null;
			boolean isActive = false;
			nursingVentilaor.setDoctorventName(null);
			String deviceIdGetQuery = "SELECT obj FROM BedDeviceDetail as obj " + "WHERE status ='true' AND time_to is null "
					+ "AND uhid='" + uhid + "'";
			List<BedDeviceDetail> deviceIdList = notesDoa.getListFromMappedObjNativeQuery(deviceIdGetQuery);
			if (!BasicUtils.isEmpty(deviceIdList)) {
				for(int i=0;i<deviceIdList.size();i++) {
					BedDeviceDetail deviceIdObj = deviceIdList.get(i);
					String deviceDetailGetQuery = "SELECT obj FROM DeviceDetail as obj " + "WHERE deviceid ='"
							+ deviceIdObj.getDeviceid() + "'";
					List<DeviceDetail> deviceDetailList = notesDoa.getListFromMappedObjNativeQuery(deviceDetailGetQuery);
					if (!BasicUtils.isEmpty(deviceDetailList)) {
						DeviceDetail deviceDetailObj = deviceDetailList.get(0);
						if(deviceDetailObj.getDevicetype().equalsIgnoreCase("Ventilator")) {
							nursingVentilaor.setDoctorventName(deviceDetailObj.getDevicename());
						}
					}
				}
			}

			if(!BasicUtils.isEmpty(respSupportActiveList.get(0).getIsactive())){
				isActive = respSupportActiveList.get(0).getIsactive();
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getC())) {
				nursingVentilaor.setDoctorc(respSupportActiveList.get(0).getC());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getR())) {
				nursingVentilaor.setDoctorr(respSupportActiveList.get(0).getR());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getTc())) {
				nursingVentilaor.setDoctortc(respSupportActiveList.get(0).getTc());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getC20())) {
				nursingVentilaor.setDoctorc20(respSupportActiveList.get(0).getC20());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getTrigger())) {
				nursingVentilaor.setDoctortrigger(respSupportActiveList.get(0).getTrigger());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getRvr())) {
				nursingVentilaor.setDoctorrvr(respSupportActiveList.get(0).getRvr());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getRsIt())) {
				nursingVentilaor.setDoctorti(respSupportActiveList.get(0).getRsIt());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getRsEt())) {
				nursingVentilaor.setDoctorte(respSupportActiveList.get(0).getRsEt());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getRsMap())) {
				nursingVentilaor.setDoctormap(respSupportActiveList.get(0).getRsMap());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getRsPeep())) {
				nursingVentilaor.setDoctorpeepCpap(respSupportActiveList.get(0).getRsPeep());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getRsPip())) {
				nursingVentilaor.setDoctorpip(respSupportActiveList.get(0).getRsPip());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getDco2())) {
				if(isActive){
					nursingVentilaor.setDoctordco2(respSupportActiveList.get(0).getDco2());
				}
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getVtim())) {
				nursingVentilaor.setDoctorvtim(respSupportActiveList.get(0).getVtim());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getVthf())) {
				nursingVentilaor.setDoctorvthf(respSupportActiveList.get(0).getVthf());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getRsTv())) {
				nursingVentilaor.setDoctortidalVolume(respSupportActiveList.get(0).getRsTv());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getRsTvInMl())) {
				nursingVentilaor.setDoctortidalVolumeml(respSupportActiveList.get(0).getRsTvInMl().toString());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getLeak())) {
				nursingVentilaor.setDoctorleak(respSupportActiveList.get(0).getLeak());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getSpont())) {
				nursingVentilaor.setDoctorspont(respSupportActiveList.get(0).getSpont());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getRsMv())) {
				nursingVentilaor.setDoctorminuteVolume(respSupportActiveList.get(0).getRsMv());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getMvim())) {
				nursingVentilaor.setDoctormvim(respSupportActiveList.get(0).getMvim());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getRsFlowRate())) {
				nursingVentilaor.setDoctorflowPerMin(respSupportActiveList.get(0).getRsFlowRate());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getRsSpo2())) {
				nursingVentilaor.setDoctorspo2(respSupportActiveList.get(0).getRsSpo2());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getRsFio2())) {
				nursingVentilaor.setDoctorfio2(respSupportActiveList.get(0).getRsFio2());
			}

			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getRsControlType())) {
				controlType = (respSupportActiveList.get(0).getRsControlType());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getRsVentType())) {
				String ventMode = respSupportActiveList.get(0).getRsVentType();
				if (ventMode.equals("Mechanical Ventilation")) {
					String mvType = respSupportActiveList.get(0).getRsMechVentType();
//					if (!BasicUtils.isEmpty(controlType)) {
//						ventMode = mvType + "-" + controlType;
//						nursingVentilaor.setDoctorventmode(ventMode);
//					} else {
//						nursingVentilaor.setDoctorventmode(mvType);
//					}
					if (!BasicUtils.isEmpty(mvType)) {
						if(mvType.equalsIgnoreCase("PTV")) {
							nursingVentilaor.setDoctorventmode(respSupportActiveList.get(0).getRsPtvMode());
						}
						else {
							nursingVentilaor.setDoctorventmode(mvType);
						}
					}

				} else {
					nursingVentilaor.setDoctorventmode(ventMode);
				}

			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getRsAmplitude())) {
				nursingVentilaor.setDoctoramplitude(respSupportActiveList.get(0).getRsAmplitude());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getRsFrequency())) {
				nursingVentilaor.setDoctorfrequency(respSupportActiveList.get(0).getRsFrequency());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getRsFrequency())) {
				nursingVentilaor.setDoctorfreqRate(respSupportActiveList.get(0).getRsFrequency());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getVolumeGaurantee())) {
				nursingVentilaor.setDoctorvolguarantee(respSupportActiveList.get(0).getVolumeGaurantee());
			}

			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getVolumeGauranteeml())) {
				nursingVentilaor.setDoctorvolguaranteeml(respSupportActiveList.get(0).getVolumeGauranteeml());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getRsCpapType())) {
				nursingVentilaor.setDoctorcpaptype(respSupportActiveList.get(0).getRsCpapType());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getRsSupportType())) {
				nursingVentilaor.setDoctordeliverytype(respSupportActiveList.get(0).getRsSupportType());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getRsControlType())) {
				nursingVentilaor.setDoctorcontroltype(respSupportActiveList.get(0).getRsControlType());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getRsPressureSupport())) {
				nursingVentilaor.setDoctorpressuresupporttype(respSupportActiveList.get(0).getRsPressureSupport());
			}
			if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getRsVolumeGuarantee())) {
				if(respSupportActiveList.get(0).getRsVolumeGuarantee().equalsIgnoreCase("yes"))
					nursingVentilaor.setDoctorVolumeGuarantee("Yes");
				if(respSupportActiveList.get(0).getRsVolumeGuarantee().equalsIgnoreCase("no"))
					nursingVentilaor.setDoctorVolumeGuarantee("No");
			}
		}
		return nursingVentilaor;
	}

	public CaclulatorDeficitPOJO getDeficitFeedCalculatorInput(String uhid, List<BabyfeedDetail> feedList,
 			List<RefNutritioncalculator> nutritionList, String currentWeight, Timestamp fromDateOffsetTwentFourHour, Timestamp toDateOffsetTwentFourHour) {

 		List<NursingIntakeOutput> nursingIntakeOutputList = inicuDoa.getListFromMappedObjQuery(
 				HqlSqlQueryConstants.getNursingIntakeOutputList(uhid, fromDateOffsetTwentFourHour, toDateOffsetTwentFourHour));

 		CaclulatorDeficitPOJO calculator = new CaclulatorDeficitPOJO();
 		String oralFeed = "select obj from OralfeedDetail obj where uhid = '"  + uhid  + "' and entrydatetime >= '"  + fromDateOffsetTwentFourHour  +
 				"' and entrydatetime <= '"  + toDateOffsetTwentFourHour  + "' order by entrydatetime desc, creationtime desc";
 		List<OralfeedDetail> oralFeedList = notesDoa.getListFromMappedObjNativeQuery(oralFeed);

 		String babyDetail = "Select obj from BabyDetail obj where uhid = '"  + uhid  + "' order by creationtime";
 		List<BabyDetail> babyDetailList = notesDoa.getListFromMappedObjNativeQuery(babyDetail);

 		if (!BasicUtils.isEmpty(nursingIntakeOutputList)) {
 			HashMap<String, Float> enteral = new HashMap<String, Float>();
 			for (NursingIntakeOutput oral : nursingIntakeOutputList) {
 				for (RefNutritioncalculator nutrition : nutritionList) {

 					if (!BasicUtils.isEmpty(oral.getPrimaryFeedType()) && oral.getPrimaryFeedType().equalsIgnoreCase(nutrition.getFeedtypeId())) {

 						if (!BasicUtils.isEmpty(oral.getPrimaryFeedValue())) {
 							if (enteral.get(BasicConstants.ENERGY) != null) {
 								enteral.put(BasicConstants.ENERGY, enteral.get(BasicConstants.ENERGY)
 										 + ((oral.getPrimaryFeedValue() * nutrition.getEnergy()) / 100));
 							} else {
 								enteral.put(BasicConstants.ENERGY,
 										oral.getPrimaryFeedValue() * nutrition.getEnergy() / 100);
 							}

 							if (enteral.get(BasicConstants.PROTEIN) != null) {
 								enteral.put(BasicConstants.PROTEIN, enteral.get(BasicConstants.PROTEIN)
 										+  ((oral.getPrimaryFeedValue() * nutrition.getProtein()) / 100));
 							} else {
 								enteral.put(BasicConstants.PROTEIN,
 										oral.getPrimaryFeedValue() * nutrition.getProtein() / 100);
 							}

 							if (enteral.get(BasicConstants.FAT) != null) {
 								enteral.put(BasicConstants.FAT, enteral.get(BasicConstants.FAT)
 										+  ((oral.getPrimaryFeedValue() * nutrition.getFat()) / 100));
 							} else {
 								enteral.put(BasicConstants.FAT, oral.getPrimaryFeedValue() * nutrition.getFat() / 100);
 							}
 							if (enteral.get(BasicConstants.VITAMINa) != null) {
 								enteral.put(BasicConstants.VITAMINa, enteral.get(BasicConstants.VITAMINa)
 										+  ((oral.getPrimaryFeedValue() * nutrition.getVitamina()) / 100));
 							} else {
 								enteral.put(BasicConstants.VITAMINa,
 										oral.getPrimaryFeedValue() * nutrition.getVitamina() / 100);
 							}
 							if (enteral.get(BasicConstants.VITAMINd) != null) {
 								enteral.put(BasicConstants.VITAMINd, enteral.get(BasicConstants.VITAMINd)
 										+  ((oral.getPrimaryFeedValue() * nutrition.getVitamind()) / 100));
 							} else {
 								enteral.put(BasicConstants.VITAMINd,
 										oral.getPrimaryFeedValue() * nutrition.getVitamind() / 100);
 							}
 							if (enteral.get(BasicConstants.CALCIUM) != null) {
 								enteral.put(BasicConstants.CALCIUM, enteral.get(BasicConstants.CALCIUM)
 										 + ((oral.getPrimaryFeedValue() * nutrition.getCalcium()) / 100));
 							} else {
 								enteral.put(BasicConstants.CALCIUM,
 										oral.getPrimaryFeedValue() * nutrition.getCalcium() / 100);
 							}
 							if (enteral.get(BasicConstants.PHOSPHORUS) != null) {
 								enteral.put(BasicConstants.PHOSPHORUS, enteral.get(BasicConstants.PHOSPHORUS)
 										+  ((oral.getPrimaryFeedValue() * nutrition.getPhosphorus()) / 100));
 							} else {
 								enteral.put(BasicConstants.PHOSPHORUS,
 										oral.getPrimaryFeedValue() * nutrition.getPhosphorus() / 100);
 							}
 							if (enteral.get(BasicConstants.IRON) != null) {
 								enteral.put(BasicConstants.IRON, enteral.get(BasicConstants.IRON)
 										+  ((oral.getPrimaryFeedValue() * nutrition.getIron()) / 100));
 							} else {
 								enteral.put(BasicConstants.IRON,
 										oral.getPrimaryFeedValue() * nutrition.getIron() / 100);
 							}
 							if (enteral.get(BasicConstants.CARBOHYDRATES) != null && (!BasicUtils.isEmpty(nutrition.getCarbohydrates()))) {
	                           enteral.put(BasicConstants.CARBOHYDRATES, enteral.get(BasicConstants.CARBOHYDRATES) +
	                                            ((oral.getPrimaryFeedValue() * nutrition.getCarbohydrates()) / 100));
			                } else if((!BasicUtils.isEmpty(nutrition.getCarbohydrates()))){
			                           enteral.put(BasicConstants.CARBOHYDRATES,
			                                           oral.getPrimaryFeedValue() * nutrition.getCarbohydrates() / 100);
			                }

 						}
 					}
 					if (!BasicUtils.isEmpty(oral.getFormulaType()) && oral.getFormulaType().equalsIgnoreCase(nutrition.getFeedtypeId())) {

 						if (!BasicUtils.isEmpty(oral.getFormulaValue())) {
 							if (enteral.get(BasicConstants.ENERGY) != null) {
 								enteral.put(BasicConstants.ENERGY, enteral.get(BasicConstants.ENERGY)
 										 + ((oral.getFormulaValue() * nutrition.getEnergy()) / 100));
 							} else {
 								enteral.put(BasicConstants.ENERGY,
 										oral.getFormulaValue() * nutrition.getEnergy() / 100);
 							}

 							if (enteral.get(BasicConstants.PROTEIN) != null) {
 								enteral.put(BasicConstants.PROTEIN, enteral.get(BasicConstants.PROTEIN)
 										+  ((oral.getFormulaValue() * nutrition.getProtein()) / 100));
 							} else {
 								enteral.put(BasicConstants.PROTEIN,
 										oral.getFormulaValue() * nutrition.getProtein() / 100);
 							}

 							if (enteral.get(BasicConstants.FAT) != null) {
 								enteral.put(BasicConstants.FAT, enteral.get(BasicConstants.FAT)
 										+  ((oral.getFormulaValue() * nutrition.getFat()) / 100));
 							} else {
 								enteral.put(BasicConstants.FAT, oral.getFormulaValue() * nutrition.getFat() / 100);
 							}
 							if (enteral.get(BasicConstants.VITAMINa) != null) {
 								enteral.put(BasicConstants.VITAMINa, enteral.get(BasicConstants.VITAMINa)
 										+  ((oral.getFormulaValue() * nutrition.getVitamina()) / 100));
 							} else {
 								enteral.put(BasicConstants.VITAMINa,
 										oral.getFormulaValue() * nutrition.getVitamina() / 100);
 							}
 							if (enteral.get(BasicConstants.VITAMINd) != null) {
 								enteral.put(BasicConstants.VITAMINd, enteral.get(BasicConstants.VITAMINd)
 										+  ((oral.getFormulaValue() * nutrition.getVitamind()) / 100));
 							} else {
 								enteral.put(BasicConstants.VITAMINd,
 										oral.getFormulaValue() * nutrition.getVitamind() / 100);
 							}
 							if (enteral.get(BasicConstants.CALCIUM) != null) {
 								enteral.put(BasicConstants.CALCIUM, enteral.get(BasicConstants.CALCIUM)
 										 + ((oral.getFormulaValue() * nutrition.getCalcium()) / 100));
 							} else {
 								enteral.put(BasicConstants.CALCIUM,
 										oral.getFormulaValue() * nutrition.getCalcium() / 100);
 							}
 							if (enteral.get(BasicConstants.PHOSPHORUS) != null) {
 								enteral.put(BasicConstants.PHOSPHORUS, enteral.get(BasicConstants.PHOSPHORUS)
 										+  ((oral.getFormulaValue() * nutrition.getPhosphorus()) / 100));
 							} else {
 								enteral.put(BasicConstants.PHOSPHORUS,
 										oral.getFormulaValue() * nutrition.getPhosphorus() / 100);
 							}
 							if (enteral.get(BasicConstants.IRON) != null) {
 								enteral.put(BasicConstants.IRON, enteral.get(BasicConstants.IRON)
 										+  ((oral.getFormulaValue() * nutrition.getIron()) / 100));
 							} else {
 								enteral.put(BasicConstants.IRON,
 										oral.getFormulaValue() * nutrition.getIron() / 100);
 							}
 							if (enteral.get(BasicConstants.CARBOHYDRATES) != null && (!BasicUtils.isEmpty(nutrition.getCarbohydrates()))) {
	                           enteral.put(BasicConstants.CARBOHYDRATES, enteral.get(BasicConstants.CARBOHYDRATES) +
	                                            ((oral.getFormulaValue() * nutrition.getCarbohydrates()) / 100));
			                } else if((!BasicUtils.isEmpty(nutrition.getCarbohydrates()))){
			                           enteral.put(BasicConstants.CARBOHYDRATES,
			                                           oral.getFormulaValue() * nutrition.getCarbohydrates() / 100);
			                }

 						}
 					}
 				}
 			}

 			if (!BasicUtils.isEmpty(nursingIntakeOutputList)) {
 				for (NursingIntakeOutput addtive : nursingIntakeOutputList) {
 					BabyfeedDetail currentFeed = new BabyfeedDetail();
 					String feedQuery = "select obj from BabyfeedDetail obj where uhid='" + uhid
 							+ "' and babyfeedid='" + addtive.getBabyfeedid() + "' order by entrydatetime desc";
 					List<BabyfeedDetail> babyFeedList = notesDoa.getListFromMappedObjNativeQuery(feedQuery);
 					if(!BasicUtils.isEmpty(babyFeedList)) {
 						currentFeed = babyFeedList.get(0);
 					}
 					for (RefNutritioncalculator nutrition : nutritionList) {
 						
 						if(!BasicUtils.isEmpty(addtive.getCalciumVolume()) && !BasicUtils.isEmpty(babyFeedList)) {
 							
 							if(nutrition.getFeedtypeId().equalsIgnoreCase("TYPE20") && !BasicUtils.isEmpty(currentFeed.getCalBrand()) && currentFeed.getCalBrand().equalsIgnoreCase("EN001")){
 					              
					              if (enteral.get(BasicConstants.VITAMINd) != null) {
					                enteral.put(BasicConstants.VITAMINd, enteral.get(BasicConstants.VITAMINd)
					                    +  ((addtive.getCalciumVolume() * nutrition.getVitamind())));
					              } else {
					                enteral.put(BasicConstants.VITAMINd,
					                		addtive.getCalciumVolume() * nutrition.getVitamind());
					              }
					              if (enteral.get(BasicConstants.CALCIUM) != null) {
					                enteral.put(BasicConstants.CALCIUM, enteral.get(BasicConstants.CALCIUM)
					                    +  ((addtive.getCalciumVolume() * nutrition.getCalcium())));
					              } else {
					                enteral.put(BasicConstants.CALCIUM,
					                		addtive.getCalciumVolume() * nutrition.getCalcium());
					              }
					              if (enteral.get(BasicConstants.PHOSPHORUS) != null) {
					                enteral.put(BasicConstants.PHOSPHORUS, enteral.get(BasicConstants.PHOSPHORUS)
					                    +  ((addtive.getCalciumVolume() * nutrition.getPhosphorus())));
					              } else {
					                enteral.put(BasicConstants.PHOSPHORUS,
					                		addtive.getCalciumVolume() * nutrition.getPhosphorus());
					              }  
 					         }
 							
 							if(nutrition.getFeedtypeId().equalsIgnoreCase("TYPE28") && !BasicUtils.isEmpty(currentFeed.getCalBrand()) && currentFeed.getCalBrand().equalsIgnoreCase("EN006")){
					              
					              if (enteral.get(BasicConstants.VITAMINd) != null) {
					                enteral.put(BasicConstants.VITAMINd, enteral.get(BasicConstants.VITAMINd)
					                    +  ((addtive.getCalciumVolume() * nutrition.getVitamind())));
					              } else {
					                enteral.put(BasicConstants.VITAMINd,
					                		addtive.getCalciumVolume() * nutrition.getVitamind());
					              }
					              if (enteral.get(BasicConstants.CALCIUM) != null) {
					                enteral.put(BasicConstants.CALCIUM, enteral.get(BasicConstants.CALCIUM)
					                    +  ((addtive.getCalciumVolume() * nutrition.getCalcium())));
					              } else {
					                enteral.put(BasicConstants.CALCIUM,
					                		addtive.getCalciumVolume() * nutrition.getCalcium());
					              }
					              if (enteral.get(BasicConstants.PHOSPHORUS) != null) {
					                enteral.put(BasicConstants.PHOSPHORUS, enteral.get(BasicConstants.PHOSPHORUS)
					                    +  ((addtive.getCalciumVolume() * nutrition.getPhosphorus())));
					              } else {
					                enteral.put(BasicConstants.PHOSPHORUS,
					                		addtive.getCalciumVolume() * nutrition.getPhosphorus());
					              }  
					         }
 						}
 						
 						if(!BasicUtils.isEmpty(addtive.getVitamindVolume()) && !BasicUtils.isEmpty(babyFeedList)) {
 							
 							if(nutrition.getFeedtypeId().equalsIgnoreCase("TYPE32") && !BasicUtils.isEmpty(currentFeed.getVitaminDBrand()) && currentFeed.getVitaminDBrand().equalsIgnoreCase("EN016")){
 					              
					              if (enteral.get(BasicConstants.VITAMINd) != null) {
					                enteral.put(BasicConstants.VITAMINd, enteral.get(BasicConstants.VITAMINd)
					                    +  ((addtive.getVitamindVolume() * nutrition.getVitamind())));
					              } else {
					                enteral.put(BasicConstants.VITAMINd,
					                		addtive.getVitamindVolume() * nutrition.getVitamind());
					              }
					             
 					         }
 							
 							if(nutrition.getFeedtypeId().equalsIgnoreCase("TYPE19") && !BasicUtils.isEmpty(currentFeed.getVitaminDBrand()) && (currentFeed.getVitaminDBrand().equalsIgnoreCase("EN004") || 
 									currentFeed.getVitaminDBrand().equalsIgnoreCase("EN005") || currentFeed.getVitaminDBrand().equalsIgnoreCase("EN007") || currentFeed.getVitaminDBrand().equalsIgnoreCase("EN013") || currentFeed.getVitaminDBrand().equalsIgnoreCase("EN015"))){
					              
					              if (enteral.get(BasicConstants.VITAMINd) != null) {
					                enteral.put(BasicConstants.VITAMINd, enteral.get(BasicConstants.VITAMINd)
					                    +  ((addtive.getVitamindVolume() * nutrition.getVitamind())));
					              } else {
					                enteral.put(BasicConstants.VITAMINd,
					                		addtive.getVitamindVolume() * nutrition.getVitamind());
					              }
					         }
 						}
 						
 						if(!BasicUtils.isEmpty(addtive.getIronVolume()) && !BasicUtils.isEmpty(babyFeedList)) {
 							
 							if(nutrition.getFeedtypeId().equalsIgnoreCase("TYPE09") && !BasicUtils.isEmpty(currentFeed.getIronBrand()) && currentFeed.getIronBrand().equalsIgnoreCase("EN002")){
 					              
 								if (enteral.get(BasicConstants.IRON) != null) {
 	 								enteral.put(BasicConstants.IRON, enteral.get(BasicConstants.IRON)
 	 										 + ((addtive.getIronVolume() * nutrition.getIron())));
 	 							} else {
 	 								enteral.put(BasicConstants.IRON,
 	 										addtive.getIronVolume() * nutrition.getIron());
 	 							} 
 					         }
 							
 							if(nutrition.getFeedtypeId().equalsIgnoreCase("TYPE29") && !BasicUtils.isEmpty(currentFeed.getIronBrand()) && currentFeed.getIronBrand().equalsIgnoreCase("EN008")){
					              
 								if (enteral.get(BasicConstants.IRON) != null) {
 	 								enteral.put(BasicConstants.IRON, enteral.get(BasicConstants.IRON)
 	 										 + ((addtive.getIronVolume() * nutrition.getIron())));
 	 							} else {
 	 								enteral.put(BasicConstants.IRON,
 	 										addtive.getIronVolume() * nutrition.getIron());
 	 							} 
					         }
 							
 							if(nutrition.getFeedtypeId().equalsIgnoreCase("TYPE30") && !BasicUtils.isEmpty(currentFeed.getIronBrand()) && currentFeed.getIronBrand().equalsIgnoreCase("EN012")){
					              
 								if (enteral.get(BasicConstants.IRON) != null) {
 	 								enteral.put(BasicConstants.IRON, enteral.get(BasicConstants.IRON)
 	 										 + ((addtive.getIronVolume() * nutrition.getIron())));
 	 							} else {
 	 								enteral.put(BasicConstants.IRON,
 	 										addtive.getIronVolume() * nutrition.getIron());
 	 							} 
					         }
 							
 							if(nutrition.getFeedtypeId().equalsIgnoreCase("TYPE31") && !BasicUtils.isEmpty(currentFeed.getIronBrand()) && currentFeed.getIronBrand().equalsIgnoreCase("EN014")){
					              
 								if (enteral.get(BasicConstants.IRON) != null) {
 	 								enteral.put(BasicConstants.IRON, enteral.get(BasicConstants.IRON)
 	 										 + ((addtive.getIronVolume() * nutrition.getIron())));
 	 							} else {
 	 								enteral.put(BasicConstants.IRON,
 	 										addtive.getIronVolume() * nutrition.getIron());
 	 							}
					         }
 						}
 						
 						if(!BasicUtils.isEmpty(addtive.getMvVolume()) && !BasicUtils.isEmpty(babyFeedList)) {
 							
 							if(nutrition.getFeedtypeId().equalsIgnoreCase("TYPE33") && !BasicUtils.isEmpty(currentFeed.getMultiVitaminBrand()) && currentFeed.getMultiVitaminBrand().equalsIgnoreCase("EN017")){
 					              
 								if (enteral.get(BasicConstants.VITAMINa) != null) {
 	 								enteral.put(BasicConstants.VITAMINa, enteral.get(BasicConstants.VITAMINa)
 	 										+  ((addtive.getMvVolume() * nutrition.getVitamina())));
 	 							} else {
 	 								enteral.put(BasicConstants.VITAMINa,
 	 										addtive.getMvVolume() * nutrition.getVitamina());
 	 							}
 								 
					              if (enteral.get(BasicConstants.VITAMINd) != null) {
					                enteral.put(BasicConstants.VITAMINd, enteral.get(BasicConstants.VITAMINd)
					                    +  ((addtive.getMvVolume() * nutrition.getVitamind())));
					              } else {
					                enteral.put(BasicConstants.VITAMINd,
					                		addtive.getMvVolume() * nutrition.getVitamind());
					              }
					             
 					         }
 						}
 					}
 				}
 			}

 			calculator.setEnteralIntake(enteral);
 		}

 		if (!BasicUtils.isEmpty(babyDetailList)) {
 			HashMap<String, Float> eshphaganIntake = new HashMap<String, Float>();

 					if(babyDetailList.get(0).getBirthweight() < 1000) {

 						eshphaganIntake.put("Energy", (float) 130);
 						eshphaganIntake.put("Protein", (float) 3.8);
 						eshphaganIntake.put("Fat",(float) 8);
 						eshphaganIntake.put("Vitamina", (float)700);
 						eshphaganIntake.put("Vitamind", (float)800);
 						eshphaganIntake.put("Calcium", (float)2.5);
 						eshphaganIntake.put("Phosphorus", (float)90);
 						eshphaganIntake.put("Iron", (float)35.8);

 						}

 					else
 					{
 						eshphaganIntake.put("Energy", (float) 110);
 						eshphaganIntake.put("Protein", (float) 3.4);
 						eshphaganIntake.put("Fat",(float) 8);
 						eshphaganIntake.put("Vitamina", (float)700);
 						eshphaganIntake.put("Vitamind", (float)800);
 						eshphaganIntake.put("Calcium", (float)2.5);
 						eshphaganIntake.put("Phosphorus", (float)90);
 						eshphaganIntake.put("Iron", (float)35.8);
 					}

 			calculator.setEshphaganIntake(eshphaganIntake);
 		}

 		if (!BasicUtils.isEmpty(babyDetailList)) {
 			HashMap<String, Float> eshphaganParenteralIntake = new HashMap<String, Float>();

 			if(babyDetailList.get(0).getBirthweight() < 1000) { //ELBW

 				eshphaganParenteralIntake.put("Energy", (float) 75);
 				eshphaganParenteralIntake.put("Protein", (float) 3.5);
 				eshphaganParenteralIntake.put("Fat",(float) 4);
 				eshphaganParenteralIntake.put("Vitamina", (float)700);
 				eshphaganParenteralIntake.put("Vitamind", (float)40);
 				eshphaganParenteralIntake.put("Calcium", (float)1.5);
 				eshphaganParenteralIntake.put("Phosphorus", (float)80);
 				eshphaganParenteralIntake.put("Iron", (float)0);



 				}

 			else //VLBW
 			{
 				eshphaganParenteralIntake.put("Energy", (float) 60);
 				eshphaganParenteralIntake.put("Protein", (float) 3.5);
 				eshphaganParenteralIntake.put("Fat",(float) 4);
 				eshphaganParenteralIntake.put("Vitamina", (float)700);
 				eshphaganParenteralIntake.put("Vitamind", (float)40);
 				eshphaganParenteralIntake.put("Calcium", (float)1.5);
 				eshphaganParenteralIntake.put("Phosphorus", (float)80);
 				eshphaganParenteralIntake.put("Iron", (float)0);
 			}

 			calculator.setEshphaganParenteralIntake(eshphaganParenteralIntake);
 		}

 		// parental....
 		HashMap<String, Float> parental = new HashMap<String, Float>();


 		if (!BasicUtils.isEmpty(feedList)) {
 			BabyfeedDetail FeedParental = feedList.get(0);

 			if (currentWeight != null && !currentWeight.isEmpty()) {
 				Float workingWeight = Float.valueOf(currentWeight);
 				Float energyParenteral = null;
 				if (FeedParental.getAminoacidConc() != null) {
 					energyParenteral = FeedParental.getAminoacidConc() * 4 * workingWeight;
 					calculator.setAminoEnergy(energyParenteral);
 				}
 				if (FeedParental.getAminoacidConc() != null)
 					parental.put(BasicConstants.PROTEIN, FeedParental.getAminoacidConc() * workingWeight);
 				if (FeedParental.getLipidConc() != null) {
 					parental.put(BasicConstants.FAT, FeedParental.getLipidConc() * workingWeight);
 					if (energyParenteral != null)
 						energyParenteral = energyParenteral  + FeedParental.getLipidConc() * 10 * workingWeight;
 					else
 						energyParenteral = FeedParental.getLipidConc() * 10 * workingWeight;
 					calculator.setLipidEnergy(FeedParental.getLipidConc() * 10 * workingWeight);
 				}

 				if (FeedParental.getGirvalue() != null && !FeedParental.getGirvalue().trim().isEmpty()) {
 					Float gir = Float.valueOf(FeedParental.getGirvalue());
 					if (energyParenteral != null)
 						energyParenteral = Float.valueOf(energyParenteral  + gir * 4.9 * workingWeight  + "");
 					else
 						energyParenteral = Float.valueOf(gir * 4.9 * workingWeight  + "");
 					calculator.setGirEnergy((float) (gir * 4.9 * workingWeight));
 				}
 				parental.put(BasicConstants.ENERGY, energyParenteral);

 				if (FeedParental.getCalciumVolume() != null && FeedParental.getCalciumVolume() != 0) {
 					parental.put(BasicConstants.CALCIUM, FeedParental.getCalciumVolume() * 9 * workingWeight);
 				}

 				calculator.setParentalIntake(parental);
 			}
 		}

 		return calculator;
 	}
	@SuppressWarnings("unchecked")
	private BabyVisitsObj getBabyVisits(String entryDate2, String uhid2) {

		BabyVisitsObj babyVisit = new BabyVisitsObj();
		this.uhid = uhid2;
		java.util.Date presentDate = null;
		java.util.Date dobDate = null;
		java.sql.Date sqlPresentDate = null;
		try {
			entryDate2 = entryDate2.substring(0, 24);
			DateFormat readFormat = new SimpleDateFormat("EEE MMM dd yyyy hh:mm:ss");
			presentDate = readFormat.parse(entryDate2);
			sqlPresentDate = new java.sql.Date(presentDate.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// other than feed data setting here...
		// setting birth data....
		Float birthWeight = null;
		Float presentWeight = null;
		Float previousWeight = null;

		// setting present datat.....

		// ***********************************
		Calendar c1 = Calendar.getInstance();
		c1.setTime(sqlPresentDate);
		c1.add(c1.DAY_OF_MONTH, -1);
		System.out.println("decremented date " + c1.getTime());
		java.util.Date yesterday = c1.getTime();
		java.sql.Date sqlYesterDayDate = new java.sql.Date(yesterday.getTime());

		String queryVwDoctorNoteDOB = "select obj from BabyDetail obj where uhid='" + uhid2 + "'";
		List<BabyDetail> listDoctorNotesDOB = notesDoa.getListFromMappedObjNativeQuery(queryVwDoctorNoteDOB);

		if (listDoctorNotesDOB != null && listDoctorNotesDOB.size() > 0) {
			BabyDetail babyDetails = listDoctorNotesDOB.get(0);
			babyVisit.setBirthWeight(babyDetails.getBirthweight());
			babyVisit.setBirthHeight(babyDetails.getBirthlength());
			babyVisit.setBirthHeadcircum(babyDetails.getBirthheadcircumference());
			babyVisit.setDob(babyDetails.getDateofbirth());
			babyVisit.setBirthGestationweeks(babyDetails.getGestationweekbylmp());
			babyVisit.setBirthGestationdays(babyDetails.getGestationdaysbylmp());
			// day of life calculating here....

			long diff = presentDate.getTime() - babyDetails.getDateofbirth().getTime();
			long ageDays = (diff / (24 * 60 * 60 * 1000)) + 1;// days..
			long ageMonth = ageDays / (30);
			// long ageYear =diff/(365*30*24*60*60*1000);
			if (ageMonth > 0)
				babyVisit.setDaysAfterBirth(ageMonth + "months " + (ageDays - (ageMonth * 30)) + "days");
			else
				babyVisit.setDaysAfterBirth(ageDays + "days");

			long dayAfterAdmissionDiff = presentDate.getTime() - babyDetails.getDateofadmission().getTime();
			long dayAfterAdmissionDays = (dayAfterAdmissionDiff / (24 * 60 * 60 * 1000)) + 1;// days..
			long dayAfterAdmissionMonth = dayAfterAdmissionDays / (30);
			// long ageYear =diff/(365*30*24*60*60*1000);
			if (dayAfterAdmissionMonth > 0)
				babyVisit.setDaysAfterAdmission(dayAfterAdmissionMonth + "months "
						+ (dayAfterAdmissionDays - (dayAfterAdmissionMonth * 30)) + "days");
			else
				babyVisit.setDaysAfterAdmission(dayAfterAdmissionDays + "days");

			// get visit record for last two entries....
			String queryCurrentBabyVisit = "select obj from BabyVisit as obj where uhid='" + uhid2 + "' and visitdate='"
					+ sqlPresentDate + "'";
			List<BabyVisit> currentBabyVisitList = notesDoa.getListFromMappedObjNativeQuery(queryCurrentBabyVisit);
			if (!BasicUtils.isEmpty(currentBabyVisitList)) {
				BabyVisit currentBabyVisit = currentBabyVisitList.get(0);
				babyVisit.setPresentWeight(currentBabyVisit.getCurrentdateweight());
				babyVisit.setPresentHeight(currentBabyVisit.getCurrentdateheight());
				babyVisit.setPresentHeadcircum(currentBabyVisit.getCurrentdateheadcircum());
				babyVisit.setWorkingWeight(currentBabyVisit.getWorkingweight());
				if ((null == babyVisit.getCorrectedGestationweeks() || null == babyVisit.getCorrectedGestationdays())
						&& !BasicUtils.isEmpty(currentBabyVisit.getCorrectedGa())) {
					String[] correctedGAArr = currentBabyVisit.getCorrectedGa().split(",");
					if (correctedGAArr.length >= 2) {
						System.out.println("array week: " + correctedGAArr[0] + " days: " + correctedGAArr[1]
								+ " age days: " + ageDays);
						if (Integer.parseInt(correctedGAArr[1]) + (ageDays % 7) > 6) {
							babyVisit.setCorrectedGestationweeks(
									Integer.parseInt(correctedGAArr[0]) + (ageDays / 7) + 1);
							babyVisit
									.setCorrectedGestationdays(Integer.parseInt(correctedGAArr[1]) + (ageDays % 7) - 7);
						} else {
							babyVisit.setCorrectedGestationweeks(Integer.parseInt(correctedGAArr[0]) + (ageDays / 7));
							babyVisit.setCorrectedGestationdays(Integer.parseInt(correctedGAArr[1]) + (ageDays % 7));
						}
					} else if (correctedGAArr.length == 1) {
						System.out.println("array week: " + correctedGAArr[0] + " current age days: " + ageDays);
						babyVisit.setCorrectedGestationweeks(Integer.parseInt(correctedGAArr[0]) + (ageDays / 7));
						babyVisit.setCorrectedGestationdays(ageDays % 7);
					}
				}
				// getting surgery information
				if (currentBabyVisit.getSurgery() != null && !currentBabyVisit.getSurgery().equalsIgnoreCase("null"))
					babyVisit.setSurgery(currentBabyVisit.getSurgery());
				if (currentBabyVisit.getSurgeon() != null && !currentBabyVisit.getSurgeon().equalsIgnoreCase("null"))
					babyVisit.setSurgeon(currentBabyVisit.getSurgeon());
				if (currentBabyVisit.getNeonatologist() != null
						&& !currentBabyVisit.getNeonatologist().equalsIgnoreCase("null"))
					babyVisit.setNeonatologist(currentBabyVisit.getNeonatologist());
			}

			String queryPreviousBabyVisit = "select obj from BabyVisit as obj where uhid='" + uhid2
					+ "' and visitdate <'" + sqlPresentDate + "' order by creationtime desc";
			List<BabyVisit> previousBabyVisitList = notesDoa.getListFromMappedObjNativeQuery(queryPreviousBabyVisit);
			if (!BasicUtils.isEmpty(previousBabyVisitList)) {
				int i = 0;
				while ((null == babyVisit.getPreviousHeadcircum() || null == babyVisit.getPreviousHeight()
						|| null == babyVisit.getPreviousWeight()) && i < previousBabyVisitList.size()) {

					BabyVisit previousBabyVisit = previousBabyVisitList.get(i);

					if (null == babyVisit.getPreviousWeight()) {
						babyVisit.setPreviousWeight(previousBabyVisit.getCurrentdateweight());
					}

					if (null == babyVisit.getPreviousHeight()) {
						babyVisit.setPreviousHeight(previousBabyVisit.getCurrentdateheight());
					}

					if (null == babyVisit.getPreviousHeadcircum()) {
						babyVisit.setPreviousHeadcircum(previousBabyVisit.getCurrentdateheadcircum());
					}

					if (babyVisit.getWorkingWeight() == null || babyVisit.getWorkingWeight().toString().isEmpty()) {
						babyVisit.setWorkingWeight(previousBabyVisit.getWorkingweight());
					}

					if ((null == babyVisit.getCorrectedGestationweeks()
							|| null == babyVisit.getCorrectedGestationdays())
							&& !BasicUtils.isEmpty(previousBabyVisit.getCorrectedGa())) {
						String[] correctedGAArr = previousBabyVisit.getCorrectedGa().split(",");
						if (correctedGAArr.length >= 2) {
							System.out.println("array week: " + correctedGAArr[0] + " days: " + correctedGAArr[1]
									+ " age days: " + ageDays);
							if (Integer.parseInt(correctedGAArr[1]) + (ageDays % 7) > 6) {
								babyVisit.setCorrectedGestationweeks(
										Integer.parseInt(correctedGAArr[0]) + (ageDays / 7) + 1);
								babyVisit.setCorrectedGestationdays(
										Integer.parseInt(correctedGAArr[1]) + (ageDays % 7) - 7);
							} else {
								babyVisit.setCorrectedGestationweeks(
										Integer.parseInt(correctedGAArr[0]) + (ageDays / 7));
								babyVisit
										.setCorrectedGestationdays(Integer.parseInt(correctedGAArr[1]) + (ageDays % 7));
							}
						} else if (correctedGAArr.length == 1) {
							System.out.println("array week: " + correctedGAArr[0] + " current age days: " + ageDays);
							babyVisit.setCorrectedGestationweeks(Integer.parseInt(correctedGAArr[0]) + (ageDays / 7));
							babyVisit.setCorrectedGestationdays(ageDays % 7);
						}
					}
					i++;
				}
			}

			// calculating weight gain and loss....

			if (babyVisit.getPresentWeight() != null && !babyVisit.getPresentWeight().toString().isEmpty()) {

				if (babyVisit.getPreviousWeight() != null && !babyVisit.getPreviousWeight().toString().isEmpty()) {
					babyVisit.setWeightChangeFromPrevious(Float.valueOf(babyVisit.getPresentWeight().toString())
							- Float.valueOf(babyVisit.getPreviousWeight().toString()));
				} /*
					 * else { babyVisit.setWeightChangeFromPrevious(babyVisit. getPresentWeight());
					 * }
					 */
			} /*
				 * else // to understand this is the first record of the da if
				 * (babyVisit.getPreviousWeight() != null &&
				 * !babyVisit.getPreviousWeight().toString().isEmpty()) {
				 * babyVisit.setWeightChangeFromPrevious(babyVisit. getPreviousWeight()); } else
				 * { babyVisit.setWeightChangeFromPrevious(0); }
				 */
		}

		return babyVisit;
	}

	@SuppressWarnings("unchecked")
	private List<VwNursingnotesIntakeFinal2> getPreviousIntakeInfo() {
		List<VwNursingnotesIntakeFinal2> prevIntakeData = null;
		String query = "select obj from VwNursingnotesIntakeFinal2 as obj where uhid='" + uhid
				+ "' and to_char(creationtime,'YYYY-MM-DD')='" + entryDate + "' order by creationtime desc";
		List<VwNursingnotesIntakeFinal2> listIntakeInfo = notesDoa.getListFromMappedObjNativeQuery(query);
		if (listIntakeInfo != null) {
			prevIntakeData = listIntakeInfo;
		} else {
			prevIntakeData = new ArrayList<VwNursingnotesIntakeFinal2>();
		}

		// create fibnocci series....
		for (int k = 0; k < listIntakeInfo.size() - 1; k++) {
			VwNursingnotesIntakeFinal2 nextIntake = listIntakeInfo.get(k + 1);
			VwNursingnotesIntakeFinal2 prevIntake = listIntakeInfo.get(k);
			if (prevIntake.getTotalIntake() != null && !prevIntake.getTotalIntake().isEmpty()) {

				if (nextIntake.getHourlyIntake() != null && !nextIntake.getHourlyIntake().isEmpty()) {
					listIntakeInfo.get(k + 1).setTotalIntake((Integer.valueOf(listIntakeInfo.get(k).getTotalIntake())
							+ Integer.valueOf(listIntakeInfo.get(k + 1).getHourlyIntake())) + "");
				}
			}

			if (!BasicUtils.isEmpty(prevIntake.getFeedmethod())) {
				String[] arrayFeedMethod = prevIntake.getFeedmethod().replace(" ", "").trim().replace("[", "")
						.replace("]", "").split(",");
				ArrayList<String> listFeedMethod = new ArrayList<String>(Arrays.asList(arrayFeedMethod));
				prevIntake.setFeedmethodList(listFeedMethod);
			}

		}
		return prevIntakeData;
	}

	@SuppressWarnings("unchecked")
	private List<NursingCatheter> getPreviousCathersInfo(boolean isAllType) {

		List<NursingCatheter> prevCathersData = null;
		String query = "";

		query = "select obj from NursingCatheter as obj where uhid='" + uhid
				+ "' and to_char(creationtime,'YYYY-MM-DD')='" + entryDate + "' order by creationtime desc";

		List<NursingCatheter> listCathersInfo = notesDoa.getListFromMappedObjNativeQuery(query);
		if (listCathersInfo != null) {
			prevCathersData = listCathersInfo;
		} else {
			prevCathersData = new ArrayList<NursingCatheter>();
		}
		return prevCathersData;

	}

	@SuppressWarnings("unchecked")
	private List<VwNursingnotesOutputFinal> getPreviousOutputInfo(Boolean isAllNotes) {
		List<VwNursingnotesOutputFinal> prevOutputData = null;

		String query = "";

		query = "select obj from VwNursingnotesOutputFinal as obj where uhid='" + uhid
				+ "' and to_char(creationtime,'YYYY-MM-DD')='" + entryDate + "' order by creationtime desc";

		List<VwNursingnotesOutputFinal> listOutputInfo = notesDoa.getListFromMappedObjNativeQuery(query);
		if (listOutputInfo != null) {
			prevOutputData = listOutputInfo;
		} else {
			prevOutputData = new ArrayList<VwNursingnotesOutputFinal>();
		}

		/*
		 * for(int k=0;k<listOutputInfo.size()-1;k++){ VwNursingnotesOutputFinal
		 * nextIntake = listOutputInfo.get(k+1); VwNursingnotesOutputFinal prevIntake =
		 * listOutputInfo.get(k); if(prevIntake.getTotalOutput()!=null &&
		 * !prevIntake.getTotalOutput().is){
		 *
		 * if(nextIntake.getHourlyIntake()!=null &&
		 * !nextIntake.getHourlyIntake().isEmpty()){
		 * listOutputInfo.get(k+1).setTotalIntake((Integer.valueOf(
		 * listOutputInfo.get(k).listOutputInfo())+Integer.valueOf(
		 * listOutputInfo.get(k+1).getHourlyIntake()))+""); } } }
		 */
		return prevOutputData;
	}

	@SuppressWarnings("unchecked")
	private List<NursingIntake> getPreviousIntakeEnternalAndIv() {
		List<NursingIntake> prevIntakeData = null;
		String query = "select obj from NursingIntake as obj where uhid='" + uhid
				+ "' and to_char(creationtime,'YYYY-MM-DD')='" + entryDate + "' order by creationtime desc";
		List<NursingIntake> listIntakeInfo = notesDoa.getListFromMappedObjNativeQuery(query);
		if (listIntakeInfo != null) {
			prevIntakeData = listIntakeInfo;
		} else {
			prevIntakeData = new ArrayList<NursingIntake>();
		}
		return prevIntakeData;
	}

	@SuppressWarnings("unchecked")
	private List<NursingBolus> getPreviousIntakeBolus() {
		List<NursingBolus> prevBolus = null;
		String query = "select obj from NursingBolus as obj where uhid='" + uhid
				+ "' and to_char(creationtime,'YYYY-MM-DD')='" + entryDate + "' order by creationtime desc";
		List<NursingBolus> listBolusInfo = notesDoa.getListFromMappedObjNativeQuery(query);
		if (listBolusInfo != null) {
			prevBolus = listBolusInfo;
		} else {
			prevBolus = new ArrayList<NursingBolus>();
		}
		return prevBolus;
	}

	@SuppressWarnings("unchecked")
	private List<BloodProduct> getPreviousIntakeBlood() {
		List<BloodProduct> prevBloodProduct = null;
		String query = "select obj from BloodProduct as obj where uhid='" + uhid
				+ "' and to_char(creationtime,'YYYY-MM-DD')='" + entryDate + "' order by creationtime desc";
		List<BloodProduct> listBloodProduct = notesDoa.getListFromMappedObjNativeQuery(query);
		if (listBloodProduct != null) {
			prevBloodProduct = listBloodProduct;
		} else {
			prevBloodProduct = new ArrayList<BloodProduct>();
		}
		return prevBloodProduct;
	}

	@SuppressWarnings("unchecked")
	private List<NursingEpisode> getPreviousEpisode() {

		List<NursingEpisode> prevEpisodeData = null;
		String query = "";

		int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
				- TimeZone.getDefault().getRawOffset();

		query = "select obj from NursingEpisode as obj where uhid='" + uhid
				+ "' and to_char(creationtime,'YYYY-MM-DD')='" + entryDate + "' order by nn_vitalparameter_time desc,episodeid desc";
		System.out.println(query + "in previous episode all episodes not today..");

		List<NursingEpisode> listVitalInfo = notesDoa.getListFromMappedObjNativeQuery(query);
		System.out.println(offset + "offset Episode");

		if (listVitalInfo != null) {
			prevEpisodeData = listVitalInfo;
			for (NursingEpisode obj : prevEpisodeData) {
				System.out.println(obj.getCreationtime().getTime() + "offset Episode");
				obj.setCreationtime(new Timestamp(obj.getCreationtime().getTime() - offset));
			}
		} else {
			prevEpisodeData = new ArrayList<NursingEpisode>();
		}
		return prevEpisodeData;
	}

	@SuppressWarnings("unchecked")
	private List<NursingVitalparameter> getPreviousVitalInformation(Boolean isAllType) {

		int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
				- TimeZone.getDefault().getRawOffset();

		List<NursingVitalparameter> prevVitalData = null;
		String query = "";

		query = "select obj from NursingVitalparameter as obj where uhid='" + uhid
				+ "' and to_char(entrydate,'YYYY-MM-DD')='" + entryDate + "' ORDER BY entrydate desc";
		System.out.println(query + "in previous vital");

		List<NursingVitalparameter> listVitalInfo = notesDoa.getListFromMappedObjNativeQuery(query);
		if (listVitalInfo != null) {
			prevVitalData = listVitalInfo;
			for (NursingVitalparameter obj : prevVitalData) {
				System.out.println(obj.getEntryDate() + "offset Episode");
				obj.setEntryDate(new Timestamp(obj.getEntryDate().getTime() - offset));
			}
		} else {
			prevVitalData = new ArrayList<NursingVitalparameter>();
		}
		return prevVitalData;
	}

	private List<NursingEpisodeVitalHistoryPojo> getNursingChartPastHistory(List<NursingEpisode> previousEpisodeData,
			List<NursingVitalparameter> previousVitalParamData) {
		List<NursingEpisodeVitalHistoryPojo> pastHistoryList = new ArrayList<NursingEpisodeVitalHistoryPojo>();
		if (null == previousEpisodeData || previousEpisodeData.isEmpty()) {
			if (!(null == previousVitalParamData || previousVitalParamData.isEmpty())) {
				Iterator<NursingVitalparameter> itr = previousVitalParamData.iterator();
				while (itr.hasNext()) {
					NursingEpisodeVitalHistoryPojo obj = new NursingEpisodeVitalHistoryPojo();
					NursingVitalparameter vitalObj = itr.next();
					obj.setNnVitalparameterTime(vitalObj.getNnVitalparameterTime());
					obj.setCreationTime(vitalObj.getCreationtime());
					obj.setEventType("N");
					obj.setPreviousVitalParamData(vitalObj);
					pastHistoryList.add(obj);
				}
			}
		} else {
			if (null == previousVitalParamData || previousVitalParamData.isEmpty()) {
				Iterator<NursingEpisode> itr = previousEpisodeData.iterator();
				while (itr.hasNext()) {
					NursingEpisode episodeObj = itr.next();
					pastHistoryList.add(getNursingEpisodePastObj(episodeObj));
				}
			} else {// both have data
				int episodeIndex = 0;
				boolean listFlag = true;
				for (int i = 0; i <= previousVitalParamData.size(); i++) {
					NursingVitalparameter vitalObj = null;
					if (i < previousVitalParamData.size()) {
						vitalObj = previousVitalParamData.get(i);
					} else {
						listFlag = false;
					}
					while (episodeIndex < previousEpisodeData.size()) {
						NursingEpisode episodeObj = previousEpisodeData.get(episodeIndex);
						if (listFlag) {
							if (episodeObj.getCreationtime().getTime() > vitalObj.getCreationtime().getTime()) {
								pastHistoryList.add(getNursingEpisodePastObj(episodeObj));
								episodeIndex++;
							} else {
								break;
							}
						} else {
							pastHistoryList.add(getNursingEpisodePastObj(episodeObj));
							episodeIndex++;
						}
					}
					if (listFlag) {
						NursingEpisodeVitalHistoryPojo obj = new NursingEpisodeVitalHistoryPojo();
						obj.setNnVitalparameterTime(vitalObj.getNnVitalparameterTime());
						obj.setCreationTime(vitalObj.getCreationtime());
						obj.setEventType("N");
						obj.setPreviousVitalParamData(vitalObj);
						pastHistoryList.add(obj);
					}
				}
			}
		}
		return pastHistoryList;
	}

	private NursingEpisodeVitalHistoryPojo getNursingEpisodePastObj(NursingEpisode episodeObj) {
		NursingEpisodeVitalHistoryPojo obj = new NursingEpisodeVitalHistoryPojo();
		obj.setCreationTime(episodeObj.getCreationtime());

		String eventType = "";
		if (episodeObj.getApnea() != null && episodeObj.getApnea()) {
			eventType = "A";
		}

		if (episodeObj.getBradycardia() != null && episodeObj.getBradycardia()) {
			if (eventType.isEmpty()) {
				eventType = "B";
			} else {
				eventType += ", B";
			}
		}

		if (episodeObj.getDisaturation() != null && episodeObj.getDisaturation()) {
			if (eventType.isEmpty()) {
				eventType = "D";
			} else {
				eventType += ", D";
			}
		}
		obj.setNnVitalparameterTime(episodeObj.getNnVitalparameterTime());
		obj.setEventType(eventType);
		obj.setPreviousEpisodeData(episodeObj);
		return obj;
	}

	@SuppressWarnings("unchecked")
	private NursingVitalparameter getCurrentVitalInformation(NursingVitalparameter vitalInfoInput,
			String entrydateGMT) {
		// get device monitor details...
		NursingVitalparameter vitalInfo = null;
		if (vitalInfoInput == null) {
			vitalInfo = new NursingVitalparameter();
		} else {
			vitalInfo = vitalInfoInput;
			vitalInfo.setSystBp(null);
			vitalInfo.setDiastBp(null);
			vitalInfo.setMeanBp(null);
			// vitalInfo.setNnVitalparameterid(null);
		}
		
		String query = "select obj from DashboardDeviceMonitorDetail as obj where uhid='" + uhid
				+ "' and to_char(starttime,'YYYY-MM-DD')='" + entrydateGMT + "'";
		List<DashboardDeviceMonitorDetail> listDeviceMonitor = notesDoa.getListFromMappedObjNativeQuery(query);
		if (listDeviceMonitor != null && listDeviceMonitor.size() > 0) {
			DashboardDeviceMonitorDetail monitorData = listDeviceMonitor.get(0);

			if (!BasicUtils.isEmpty(monitorData.getHeartrate())) {
				vitalInfo.setHrRate(Float.valueOf(monitorData.getHeartrate()));
			}
			else {
				vitalInfo.setHrRate(null);
			}

			if (!BasicUtils.isEmpty(monitorData.getSpo2())) {
				vitalInfo.setSpo2(monitorData.getSpo2());
			}

			if (!BasicUtils.isEmpty(monitorData.getEtco2())) {
				vitalInfo.setEtco2(monitorData.getEtco2());
			}

			if (!BasicUtils.isEmpty(monitorData.getEcgResprate())) {
				vitalInfo.setRrRate(Float.valueOf(monitorData.getEcgResprate()));
			}
			else {
				vitalInfo.setRrRate(null);
			}

			if (!BasicUtils.isEmpty(monitorData.getEtco2())) {
				vitalInfo.setEtco2(monitorData.getEtco2());
			}

			if (!BasicUtils.isEmpty(monitorData.getPulserate())) {
				vitalInfo.setPulserate(monitorData.getPulserate());
			}

			// flag to check for is all bp fields are empty..
			Boolean isNormalBpAllEmpty = true;
			if (!BasicUtils.isEmpty(monitorData.getSysBp())) {
				vitalInfo.setSystBp(monitorData.getSysBp());
				isNormalBpAllEmpty = false;
			}

			if (!BasicUtils.isEmpty(monitorData.getDiaBp())) {
				vitalInfo.setDiastBp(monitorData.getDiaBp());
				isNormalBpAllEmpty = false;
			}

			if (!BasicUtils.isEmpty(monitorData.getMeanBp())) {
				vitalInfo.setMeanBp(monitorData.getMeanBp());
				isNormalBpAllEmpty = false;
			}

			if (isNormalBpAllEmpty) {
				if (!BasicUtils.isEmpty(monitorData.getNbpS())) {
					vitalInfo.setSystBp(monitorData.getNbpS());
				}

				if (!BasicUtils.isEmpty(monitorData.getNbpD())) {
					vitalInfo.setDiastBp(monitorData.getNbpD());
				}

				if (!BasicUtils.isEmpty(monitorData.getNbpM())) {
					vitalInfo.setMeanBp(monitorData.getNbpM());
				}
			}
            if (!BasicUtils.isEmpty(monitorData.getIbpS())) {
                vitalInfo.setSystiBp(Float.parseFloat(monitorData.getIbpS()));
            }

            if (!BasicUtils.isEmpty(monitorData.getIbpD())) {
                vitalInfo.setDiastiBp(Float.parseFloat(monitorData.getIbpD()));
            }

            if (!BasicUtils.isEmpty(monitorData.getIbpM())) {
                vitalInfo.setMeaniBp(Float.parseFloat(monitorData.getIbpM()));
            }

		}

		// As requirement from apollo. These parameters should not show up on the screen
		vitalInfo.setComments(null);
		vitalInfo.setCft(null);
		vitalInfo.setRbs(null);
		vitalInfo.setConsciousness(null);
		vitalInfo.setTcb(null);
		vitalInfo.setHumidification(null);
		vitalInfo.setRoundConsultants(null);
		vitalInfo.setRoundResidents(null);
		vitalInfo.setRoundSpecialists(null);

		vitalInfo.setAssessmentsConsultants(null);
		vitalInfo.setAssessmentsResidents(null);
		vitalInfo.setAssessmentsSpecialists(null);
		
		vitalInfo.setPastDownesScore(getDownesScore(uhid));
		vitalInfo.setPastDownesTime(lastDownesTime(uhid));
		vitalInfo.setDownesscore(null);
		vitalInfo.setLeftProbeSite(null);
		vitalInfo.setRightProbeSite(null);
		vitalInfo.setVpPosition(null);
		vitalInfo.setBaby_color(null);

		return vitalInfo;
	}

	private Integer getDownesScore(String uhid) {
		Integer downesScore = null;

		String fetchDownesScore = "SELECT obj FROM ScoreDownes as obj where uhid='" + uhid
				+ "' order by creationtime desc";
		List<ScoreDownes> downesScoreList = notesDoa.getListFromMappedObjNativeQuery(fetchDownesScore);
		if (!BasicUtils.isEmpty(downesScoreList)) {
			if (downesScoreList.get(0).getDownesscore() != null) {
				downesScore = downesScoreList.get(0).getDownesscore();
			}
		}

		return downesScore;
	}


	private Timestamp lastDownesTime(String uhid) {

		int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
				- TimeZone.getDefault().getRawOffset();
		Timestamp downesTime = null;

		String fetchDownesScore = "SELECT obj FROM ScoreDownes as obj where uhid='" + uhid
				+ "' order by creationtime desc";
		List<ScoreDownes> downesScoreList = notesDoa.getListFromMappedObjNativeQuery(fetchDownesScore);
		if (!BasicUtils.isEmpty(downesScoreList)) {
			if (downesScoreList.get(0).getDownesscore() != null) {
				downesTime = downesScoreList.get(0).getEntrydate();
			}
		}
		return downesTime;
	}

	@SuppressWarnings("unchecked")
	public ResponseMessageWithResponseObject saveBabyVisit(NursingDailyProgressPojo babyVisitObj, String uhid,
			String entryDate, String userName) {
		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
		// saving baby visits here..............
		BabyVisit babyVisit = babyVisitObj.getBabyVisit();
		try {
			this.uhid = uhid;
			java.sql.Date sqlPresentDate = null;
			java.sql.Time sqlPresentTime = null;
			try {
				entryDate = entryDate.substring(0, 24);
				String entryTime = entryDate.substring(16, 22);
				entryTime = entryTime + "00";
				DateFormat readFormat = new SimpleDateFormat("EEE MMM dd yyyy hh:mm:ss");
				java.util.Date presentDate = readFormat.parse(entryDate);
				sqlPresentDate = new java.sql.Date(presentDate.getTime());

				DateFormat readFormatTime = new SimpleDateFormat("HH:mm:ss");
				java.util.Date presentTime = readFormatTime.parse(entryTime);
				sqlPresentTime = new java.sql.Time(presentTime.getTime());

				babyVisit.setVisitdate(sqlPresentDate);
				babyVisit.setVisittime(sqlPresentTime);

			} catch (ParseException e) {
				e.printStackTrace();
			}

			String queryBabyVisit = "select obj from BabyVisit obj where uhid='" + uhid + "'  and visitdate='"
					+ sqlPresentDate + "' and visittime='" + sqlPresentTime + "'";
			List<BabyVisit> listBabyVisit = notesDoa.getListFromMappedObjNativeQuery(queryBabyVisit);

			if (!BasicUtils.isEmpty(listBabyVisit)) {
				babyVisit = listBabyVisit.get(0);
				BabyVisit babyVisitCurrent = babyVisitObj.getBabyVisit();
				if (!BasicUtils.isEmpty(babyVisitCurrent.getCurrentdateweight())) {
					babyVisit.setCurrentdateweight(babyVisitCurrent.getCurrentdateweight());
				}
				if (!BasicUtils.isEmpty(babyVisitCurrent.getCurrentdateheadcircum())) {
					babyVisit.setCurrentdateheadcircum(babyVisitCurrent.getCurrentdateheadcircum());
				}
				if (!BasicUtils.isEmpty(babyVisitCurrent.getCurrentdateheight())) {
					babyVisit.setCurrentdateheight(babyVisitCurrent.getCurrentdateheight());
				}
			}
			else {
				if (!BasicUtils.isEmpty(babyVisitObj.getBabyVisit().getCurrentdateweight())) {
					babyVisit.setCurrentdateweight(babyVisitObj.getBabyVisit().getCurrentdateweight());
				}
				else {
					//Set Weight
					String queryChildWeight = "select currentdateweight from baby_visit obj where uhid ='" + uhid
					+ "' and currentdateweight IS NOT NULL order by creationtime desc";
					List<Float> resultWeight = userServiceDao.executeNativeQuery(queryChildWeight);
					Float prevWeight = null;
					if (!BasicUtils.isEmpty(resultWeight)) {
						prevWeight = resultWeight.get(0);
					}
					babyVisit.setCurrentdateweight(prevWeight);
				}
			}

			System.out.println(babyVisit.getCreationtime() + "creation time date");
			if (babyVisit.getCreationtime() != null) {
				babyVisit.setModificationtime(babyVisit.getCreationtime());
				babyVisit.setCreationtime(babyVisit.getCreationtime());
			} else {

				babyVisit.setCreationtime(new Timestamp(System.currentTimeMillis()));
				babyVisit.setModificationtime(new Timestamp(System.currentTimeMillis()));
			}

			String queryChildMaxWeight = "select Max(currentdateweight) from baby_visit obj where uhid ='" + uhid + "'";
			List<Float> resultSet = userServiceDao.executeNativeQuery(queryChildMaxWeight);
			Float workingWeight = null;
			if (!BasicUtils.isEmpty(resultSet)) {
				workingWeight = resultSet.get(0);
				if (!BasicUtils.isEmpty(workingWeight)
						&& !BasicUtils.isEmpty(babyVisitObj.getBabyVisit().getCurrentdateweight())) {
					if (workingWeight.floatValue() < babyVisitObj.getBabyVisit().getCurrentdateweight().floatValue()) {
						workingWeight = babyVisitObj.getBabyVisit().getCurrentdateweight().floatValue();
					}
				}
				babyVisit.setWorkingweight(workingWeight);
			}

			//Weight for Calculation
			String queryChildWeightForCal = "select weight_for_cal from baby_visit obj where uhid ='" + uhid
					+ "' order by creationtime desc";
			List<Float> resultWeightForCalSet = userServiceDao.executeNativeQuery(queryChildWeightForCal);
			if (!BasicUtils.isEmpty(resultWeightForCalSet)) {
				Float prevWeightForCal = resultWeightForCalSet.get(0);
				Float currWeightForCal = babyVisitObj.getBabyVisit().getWeightForCal();
				Float weight = null;
				if(!BasicUtils.isEmpty(babyVisitObj.getBabyVisit().getCurrentdateweight())){
					weight = babyVisitObj.getBabyVisit().getCurrentdateweight().floatValue();
				}

				if (prevWeightForCal != null && Float.compare(currWeightForCal, prevWeightForCal) != 0) {
					babyVisit.setWeightForCal(currWeightForCal);
				}
				else {
					if(prevWeightForCal != null) {
						if(weight == null) {
							babyVisit.setWeightForCal(prevWeightForCal);
						}
						else {
							if(Float.compare(prevWeightForCal, weight) < 0) {
								babyVisit.setWeightForCal(weight);
							}
							else {
								babyVisit.setWeightForCal(prevWeightForCal);
							}
						}
					}
				}
			}

			babyVisit.setUhid(uhid);
			babyVisit.setLoggeduser(userName);

			String gestStr = ",";
			if (!(BasicUtils.isEmpty(babyVisit.getGestationWeek())
					|| BasicUtils.isEmpty(babyVisit.getGestationDays()))) {
				gestStr = babyVisit.getGestationWeek() + "," + babyVisit.getGestationDays();
			}

			babyVisit.setCorrectedGa(gestStr);

			String queryChildWeight = "select ga_at_birth, episodeid from baby_visit obj where uhid ='" + uhid
					+ "' and ga_at_birth IS NOT NULL and episodeid IS NOT NULL order by creationtime desc";
			List<Object[]> resultGaAtBirth = userServiceDao.executeNativeQuery(queryChildWeight);

			if(resultGaAtBirth != null && resultGaAtBirth.size() > 0) {
				if(resultGaAtBirth.get(0) != null) {
					babyVisit.setGaAtBirth(String.valueOf(resultGaAtBirth.get(0)[0]));
					babyVisit.setEpisodeid(String.valueOf(resultGaAtBirth.get(0)[1]));
				}
			}

			notesDoa.saveObject(babyVisit);

			// get the anthropometry progress rates
			AnthropometryProgressRate anthropometryProgressRate=userPanel.getAnthropometryGrowthRate(uhid);
			if(anthropometryProgressRate!=null){
				babyVisitObj.setAnthropometryProgressRate(anthropometryProgressRate);
			}

			response.setType(BasicConstants.MESSAGE_SUCCESS);
			response.setMessage("Baby Visits Saved Successfully..!!");

			// @Sourabh Verma 2-5-17, unnecessary call.
			// BabyVisitsObj babyVisitNew = getBabyVisits(entryDate, uhid);
			// response.setReturnedObject(babyVisitNew);
			response.setReturnedObject(babyVisitObj);

			// ****************BABY visit ends here********************
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return response;
	}

	// end
	// --------------------------------------------------------------------------------------------------------
	// Priya workspace
	// Get bloodGas Data
	@SuppressWarnings("unchecked")
	private List<NursingBloodGas> getPreviousBloodGasInfo(Boolean isAllType) {

		List<NursingBloodGas> prevBloodGasInfo = new ArrayList<NursingBloodGas>();
		String query = "";

		query = "select n from NursingBloodGas as n where uhid = '" + uhid + "' order by entrydate desc";
		System.out.println(query + "in blood gas");

		try {

			 int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
			 		- TimeZone.getDefault().getRawOffset();
			List<NursingBloodGas> bloodGasList = inicuPostgersUtil.executeMappedObjectCustomizedQuery(query);

			 for (NursingBloodGas item:bloodGasList) {
			 	System.out.println(offset + "offset");
			 	item.setEntryDate((new Timestamp(item.getEntryDate().getTime() - offset)));
			 }

			if (!BasicUtils.isEmpty(bloodGasList)) {
				prevBloodGasInfo = bloodGasList;
			} else {
				prevBloodGasInfo = new ArrayList<NursingBloodGas>();
			}

		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUser,
					uhid, "GET_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		return prevBloodGasInfo;
	}

	private NursingBloodGas getCurrentBloodGasInfo() {

		return new NursingBloodGas();
	}

	// Get NeuroVitals Data
	@SuppressWarnings("unchecked")
	private List<VwPupilReactivity> getPreviousNeuroVitalsInfo(Boolean isAllType) {

		List<VwPupilReactivity> prevNeuroVitalsInfo = new ArrayList<VwPupilReactivity>();
		String query = "";

		query = "select n from VwPupilReactivity as n where uhid = '" + uhid
				+ "' and to_char(creationtime,'YYYY-MM-DD')='" + entryDate + "' order by creationtime desc";
		/*
		 * String pupilQuery = "select p from PupilReactivity as p where uhid =
		 * '" + uhid + "' and creationtime >= '" + selectedDate+"' order by creationtime
		 * desc"
		 */ ;

		try {
			List<VwPupilReactivity> neuroVitalsList = inicuPostgersUtil.executeMappedObjectCustomizedQuery(query);
			// List<PupilReactivity> pupilList =
			// inicuPostgersUtil.executeMappedObjectCustomizedQuery(pupilQuery
			// );
			if (neuroVitalsList != null) {
				prevNeuroVitalsInfo = neuroVitalsList;
			}

		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUser,
					uhid, "GET_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		return prevNeuroVitalsInfo;

	}

	private VwPupilReactivity getCurrentNeuroVitalsInfo() {

		return new VwPupilReactivity();
	}

	// Ventilator get data
	@SuppressWarnings("unchecked")
	private List<NursingVentilaor> getPreviousVentilaorInfo(Boolean isAllType) {

		int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
				- TimeZone.getDefault().getRawOffset();

		List<NursingVentilaor> prevVentilaorInfo = new ArrayList<NursingVentilaor>();
		String query = "";

		query = "select n from NursingVentilaor as n where uhid = '" + uhid + "' and to_char(entrydate,'YYYY-MM-DD')='"
				+ entryDate + "' order by entrydate desc";

		try {
			List<NursingVentilaor> ventilaorList = inicuPostgersUtil.executeMappedObjectCustomizedQuery(query);
			if (!BasicUtils.isEmpty(ventilaorList)) {
				prevVentilaorInfo = ventilaorList;
				for (NursingVentilaor obj : prevVentilaorInfo) {
					System.out.println(obj.getEntryDate() + "offset Episode");
					obj.setEntryDate(new Timestamp(obj.getEntryDate().getTime() - offset));
					//obj.setVent_desc(obj.getVent_desc());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUser,
					uhid, "GET_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		return prevVentilaorInfo;
	}

	private NursingVentilaor getCurrentventilatorInfo() {

		return new NursingVentilaor();
	}

	// daily assessment
	@SuppressWarnings("unchecked")
	private NursingDailyAssesmentJSON getPreviousDailyAssessmentInfo() {
		// SimpleDateFormat s;
		NursingDailyAssesmentJSON dailyAssesmentJson = new NursingDailyAssesmentJSON();
		List<NursingDailyassesment> dailyAssessmentList = new ArrayList<NursingDailyassesment>();
		String query = "";
		query = "select obj from NursingDailyassesment as obj where uhid='" + uhid
				+ "' and to_char(creationtime,'YYYY-MM-DD')='" + entryDate + "' order by creationtime desc";

		try {
			List<NursingDailyassesment> dailyList = inicuPostgersUtil.executeMappedObjectCustomizedQuery(query);
			NursingDailyassesment dailyAssessment = new NursingDailyassesment();
			Iterator<NursingDailyassesment> iterator = null;
			if (!BasicUtils.isEmpty(dailyList))

				iterator = dailyList.iterator();

			if (iterator != null) {
				while (iterator.hasNext()) {
					NursingDailyassesment n = null;
					n = iterator.next();
					if (n.getDailyassesmentTime().equalsIgnoreCase("morning")) {
						dailyAssesmentJson.setMorningData(n);
					} else if (n.getDailyassesmentTime().trim().equalsIgnoreCase("afternoon")) {
						System.out.println("me");
						dailyAssesmentJson.setAfternoonData(n);
					} else if (n.getDailyassesmentTime().trim().equalsIgnoreCase("night")) {
						dailyAssesmentJson.setNightData(n);
					} else {
						dailyAssesmentJson.setEveningData(n);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUser,
					uhid, "GET_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}

		return dailyAssesmentJson;
	}

	private NursingDailyassesment getCurrentDailyAssessmentInfo() {
		return new NursingDailyassesment();
	}

	@SuppressWarnings("unchecked")
	private NursingMisc getCurrentMiscInfo() {
		NursingMisc misc = new NursingMisc();
		String query = "select obj from NursingMisc as obj where uhid='" + uhid
				+ "' and to_char(creationtime,'YYYY-MM-DD')='" + entryDate + "' order by creationtime desc";
		try {
			List<NursingMisc> miscList = inicuPostgersUtil.executeMappedObjectCustomizedQuery(query);
			if (!BasicUtils.isEmpty(miscList)) {

			}
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUser,
					uhid, "GET_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		return misc;
	}

	@SuppressWarnings("unchecked")
	private List<NursingMisc> getPreviousMiscInfo() {

		List<NursingMisc> prevMiscInfo = new ArrayList<NursingMisc>();
		String query = "select obj from NursingMisc as obj where uhid='" + uhid
				+ "' and to_char(creationtime,'YYYY-MM-DD')='" + entryDate + "' order by creationtime desc";
		try {
			List<NursingMisc> miscList = inicuPostgersUtil.executeMappedObjectCustomizedQuery(query);
			if (!BasicUtils.isEmpty(miscList)) {
				prevMiscInfo = miscList;

			} else {
				prevMiscInfo = new ArrayList<NursingMisc>();
			}

		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUser,
					uhid, "GET_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		return prevMiscInfo;
	}

	// for set or on submit call
	@SuppressWarnings("unchecked")
	@Override
	public ResponseMessageWithResponseObject setNursingObservationInfo(String tabType,
			NursingObservationPojo nursingInfo, String uhid, String entryDate) {
		ResponseMessageWithResponseObject obj = new ResponseMessageWithResponseObject();

		// System.out.println(nursingInfo.isEdited());
		try {

			uhid = nursingInfo.getUhid();


			this.uhid = uhid;
			if (tabType.equalsIgnoreCase(BasicConstants.VITAL_PARAM)) {

				Object vitalInfo = nursingInfo.getVitalParametersInfo().get(BasicConstants.EMPTY_OBJ_STR);
				NursingVitalparameter vital = mapper.readValue(BasicUtils.getObjAsJson(vitalInfo),
						NursingVitalparameter.class);

				vital.setNnVitalparameterTime(nursingInfo.getNnEntryTime().getHours() + BasicConstants.TIME_SPLITTER
						+ nursingInfo.getNnEntryTime().getMinutes() + BasicConstants.TIME_SPLITTER
						+ nursingInfo.getNnEntryTime().getMeridium());
				vital.setUhid(uhid);
				vital.setLoggeduser(nursingInfo.getLoggedUser());
				String userDate = nursingInfo.getUserDate();
				vital.setUserDate(userDate);

				System.out.println(BasicConstants.CLIENT_TIME_ZONE);

				int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
						- TimeZone.getDefault().getRawOffset();

				System.out.println(offset + "offset");

				vital.setEntryDate((new Timestamp(nursingInfo.getEntryDate().getTime() + offset)));

				if(!BasicUtils.isEmpty(vital.getDownesscore())) {
					ScoreDownes downesScore = new ScoreDownes();
					Timestamp timestamp = new Timestamp(nursingInfo.getEntryDate().getTime() + offset);

					downesScore.setEntrydate(timestamp);
					downesScore.setDownesscore(vital.getAirentry());
					downesScore.setDownesscore(vital.getRetractions());
					downesScore.setDownesscore(vital.getCynosis());
					downesScore.setDownesscore(vital.getGrunting());
					downesScore.setRespiratoryrate(vital.getRespiratoryrate());

					downesScore.setDownesscore(vital.getDownesscore());
					downesScore.setAdmission_entry(false);
					downesScore.setUhid(vital.getUhid());
					notesDoa.saveObject(downesScore);
				}


				// vital.setEntryDate(nursingInfo.getEntryDate());

				System.out.println(nursingInfo.getCreationtime() + "creation time date");
				if (nursingInfo.getCreationtime() != null) {
					vital.setCreationtime(nursingInfo.getCreationtime());
					vital.setModificationtime(nursingInfo.getCreationtime());
				} else {
					vital.setCreationtime(new Timestamp(System.currentTimeMillis()));
					vital.setModificationtime(new Timestamp(System.currentTimeMillis()));
				}

				String entryTime = nursingInfo.getNnEntryTime().getHours() + BasicConstants.TIME_SPLITTER
						+ nursingInfo.getNnEntryTime().getMinutes() + BasicConstants.TIME_SPLITTER
						+ nursingInfo.getNnEntryTime().getMeridium();
				java.util.Date tdate = new SimpleDateFormat("MMM dd yyyy").parse(entryDate.substring(4, 15));
				// get date object for date Mar 05 2017
				String sqlDate = new SimpleDateFormat("yyyy-MM-dd").format(tdate);

				// converting date to sql format
				String prevOutputEntry = "select obj from NursingVitalparameter as obj where uhid='" + uhid
						+ "' and to_char(creationtime,'YYYY-MM-DD')='" + sqlDate + "'" + " and nnVitalparameterTime='"
						+ entryTime + "' order by creationtime desc";
				List<NursingVitalparameter> listVitalParameter = notesDoa
						.getListFromMappedObjNativeQuery(prevOutputEntry);
				if (listVitalParameter != null && listVitalParameter.size() > 0) {
					Long nnVitalID = listVitalParameter.get(0).getNnVitalparameterid();
					vital.setNnVitalparameterid(nnVitalID);
				}

				DashboardNursingVitalparameters dashboardNV = new DashboardNursingVitalparameters();

				//Added as nursing charts is showing wrong downes time
				dashboardNV.setEntrydate(new Timestamp(vital.getEntryDate().getTime() - offset));
				dashboardNV.setHr_rate(vital.getHrRate());
				dashboardNV.setSkintemp(vital.getSkintemp());
				dashboardNV.setSpo21(vital.getSpo2());
				dashboardNV.setMean_bp1(vital.getMeanBp());
				dashboardNV.setUhid(vital.getUhid());

				notesDoa.saveObject(vital);
				notesDoa.saveObject(dashboardNV);

			} else if (tabType.equalsIgnoreCase(BasicConstants.INTAKE)) {

				saveIntakeData(nursingInfo, uhid, entryDate);

			} else if (tabType.equalsIgnoreCase(BasicConstants.OUTPUT)) {
				Object outputObj = nursingInfo.getOutputInfo().get(BasicConstants.EMPTY_OBJ_STR);
				VwNursingnotesOutputFinal vwNursingOutput = mapper.readValue(BasicUtils.getObjAsJson(outputObj),
						VwNursingnotesOutputFinal.class);

				NursingOutput nursingOutput = new NursingOutput();
				nursingOutput.setNnOutputTime(nursingInfo.getNnEntryTime().getHours() + BasicConstants.TIME_SPLITTER
						+ nursingInfo.getNnEntryTime().getMinutes() + BasicConstants.TIME_SPLITTER
						+ nursingInfo.getNnEntryTime().getMeridium());
				nursingOutput.setAspirateQuantity(vwNursingOutput.getAspirateQuantity());
				nursingOutput.setAspirateType(vwNursingOutput.getAspirateType());
				nursingOutput.setUrineMls(vwNursingOutput.getUrineMls());
				nursingOutput.setUrineMlkg(vwNursingOutput.getUrineMlkg());
				nursingOutput.setTotalUo(vwNursingOutput.getTotalUo());
				nursingOutput.setBloodLetting(vwNursingOutput.getBloodLetting());
				nursingOutput.setBowelStatus(vwNursingOutput.getBowelStatus());
				nursingOutput.setBowelType(vwNursingOutput.getBowelType());
				nursingOutput.setBowelColor(vwNursingOutput.getBowelColor());

				nursingOutput.setUhid(uhid);
				nursingOutput.setLoggeduser(nursingInfo.getLoggedUser());

				/* Vedaant */
				String entryTime = nursingInfo.getNnEntryTime().getHours() + BasicConstants.TIME_SPLITTER
						+ nursingInfo.getNnEntryTime().getMinutes() + BasicConstants.TIME_SPLITTER
						+ nursingInfo.getNnEntryTime().getMeridium();
				java.util.Date tdate = new SimpleDateFormat("MMM dd yyyy").parse(entryDate.substring(4, 15));
				/*
				 * get date object for date Mar 05 2017
				 */
				String sqlDate = new SimpleDateFormat("yyyy-MM-dd").format(tdate);
				/*
				 * converting date to sql format
				 */
				String prevOutputEntry = "select obj from NursingOutput as obj where uhid='" + uhid
						+ "' and to_char(creationtime,'YYYY-MM-DD')='" + sqlDate + "'" + " and nnOutputTime='"
						+ entryTime + "' order by creationtime desc";
				List<NursingOutput> listNursingOutput = notesDoa.getListFromMappedObjNativeQuery(prevOutputEntry);
				if (listNursingOutput != null && listNursingOutput.size() > 0) {
					Long nnOutputID = listNursingOutput.get(0).getNnOutputid();
					nursingOutput.setNnOutputid(nnOutputID);
					nursingOutput.setCreationtime(listNursingOutput.get(0).getCreationtime());
				}

				/* Vedaant */
				notesDoa.saveObject(nursingOutput);

				// saving output drains...

				NursingOutputdrain nursingOutputDrains = new NursingOutputdrain();

				nursingOutputDrains.setNnDrainTime(nursingInfo.getNnEntryTime().getHours()
						+ BasicConstants.TIME_SPLITTER + nursingInfo.getNnEntryTime().getMinutes()
						+ BasicConstants.TIME_SPLITTER + nursingInfo.getNnEntryTime().getMeridium());
				nursingOutputDrains.setDrain1Input(vwNursingOutput.getDrain1Input());
				nursingOutputDrains.setDrain1Output(vwNursingOutput.getDrain1Output());
				nursingOutputDrains.setDrain2Input(vwNursingOutput.getDrain2Input());
				nursingOutputDrains.setDrain2Output(vwNursingOutput.getDrain2Output());
				nursingOutputDrains.setDrain3Input(vwNursingOutput.getDrain3Input());
				nursingOutputDrains.setDrain3Output(vwNursingOutput.getDrain3Output());

				nursingOutputDrains.setUhid(uhid);
				nursingOutputDrains.setLoggeduser(nursingInfo.getLoggedUser());
				/*
				 * will change later to live user.
				 */
				/* Vedaant */
				String prevOutputDrainEntry = "select obj from NursingOutputdrain as obj where uhid='" + uhid
						+ "' and to_char(creationtime,'YYYY-MM-DD')='" + sqlDate + "'" + " and nnDrainTime='"
						+ entryTime + "' order by creationtime desc";
				List<NursingOutputdrain> listNursingOutputDrain = notesDoa
						.getListFromMappedObjNativeQuery(prevOutputDrainEntry);

				if (listNursingOutputDrain != null && listNursingOutputDrain.size() > 0) {
					Long nnOutputDrainID = listNursingOutputDrain.get(0).getNnDrainid();
					nursingOutputDrains.setNnDrainid(nnOutputDrainID);
					nursingOutputDrains.setCreationtime(listNursingOutputDrain.get(0).getCreationtime());
				}

				/* Vedaant */
				notesDoa.saveObject(nursingOutputDrains);

			} else if (tabType.equalsIgnoreCase(BasicConstants.CATHERS)) {
				obj = saveCatheters(nursingInfo, uhid);

			} else if (tabType.equalsIgnoreCase(BasicConstants.NURO_VITALS)) {
				obj = saveNeuroVitals(nursingInfo, uhid);
			} else if (tabType.equalsIgnoreCase(BasicConstants.BLOOD_GAS)) {

				obj = saveBloodGas(nursingInfo, uhid);
			} else if (tabType.equalsIgnoreCase(BasicConstants.VENTILATOR)) {

				obj = saveVentilator(nursingInfo, uhid);
			} else if (tabType.equalsIgnoreCase(BasicConstants.DAILY_ASSESSMENT)) {

				obj = saveDailyAssessment(nursingInfo, uhid);
			} else if (tabType.equalsIgnoreCase(BasicConstants.MISC)) {

				obj = saveMisc(nursingInfo, uhid);
			}
			obj.setType("message_success");
			obj.setMessage("save successfully");
		} catch (Exception ex) {// will handle her exception mgmt
			ex.printStackTrace();
			return obj;
		}
		return obj;
	}

	@SuppressWarnings("unchecked")
	private void saveIntakeData(NursingObservationPojo nursingInfo, String uhid, String date) throws Exception {

		/* Vedaant */
		String entryTime = nursingInfo.getNnEntryTime().getHours() + BasicConstants.TIME_SPLITTER
				+ nursingInfo.getNnEntryTime().getMinutes() + BasicConstants.TIME_SPLITTER
				+ nursingInfo.getNnEntryTime().getMeridium();

		/* Check if the record already exists in table */
		String prevEntry = "select obj from NursingIntake as obj where uhid='" + uhid
				+ "' and to_char(creationtime,'YYYY-MM-DD')='" + entryDate + "'" + " and nnIntakeTime='" + entryTime
				+ "' order by creationtime desc";
		List<NursingIntake> listIntakeInfo = notesDoa.getListFromMappedObjNativeQuery(prevEntry);

		/* Vedaant */

		Object intakeInfo = nursingInfo.getIntakeInfo().get(BasicConstants.EMPTY_OBJ_STR);
		Object feedOnlyInfo = nursingInfo.getIntakeInfo().get(BasicConstants.BABY_FEED_BY_DOCTOR);

		VwNursingnotesIntakeFinal2 intakeObj = mapper.readValue(BasicUtils.getObjAsJson(intakeInfo),
				VwNursingnotesIntakeFinal2.class);
		BabyfeedDetail feedOnlyObj = mapper.readValue(BasicUtils.getObjAsJson(feedOnlyInfo), BabyfeedDetail.class);
		// set nursing intake table...
		NursingIntake feedAndIVData = new NursingIntake();
		// saving iv only if the iv is checked
		boolean isFeedORIvGiven = false;

		if (nursingInfo.getIntakeInfo().get("isFeedGiven") != null
				&& Boolean.valueOf(nursingInfo.getIntakeInfo().get("isFeedGiven") + "")) {
			isFeedORIvGiven = true;
			if (feedOnlyObj.getFeedmethod() != null)
				feedAndIVData.setFeedmethod(feedOnlyObj.getFeedmethod());

			/*
			 * if (feedOnlyObj.getHmfperdayml() != null)
			 * feedAndIVData.setHmfvalue(feedOnlyObj.getHmfperdayml() + "");
			 */

			if (intakeObj.getHmfvalue() != null)
				feedAndIVData.setHmfvalue(intakeObj.getHmfvalue() + "");

			if (feedOnlyObj.getFeedtype() != null)
				feedAndIVData.setFeedtype(feedOnlyObj.getFeedtype());

			if (feedOnlyObj.getFeedvolume() != null && !feedOnlyObj.getFeedvolume().toString().isEmpty())
				feedAndIVData.setFeedvolume(feedOnlyObj.getFeedvolume());
		} else {
			feedAndIVData.setFeedvolume(null);
		}

		// saving iv only if the iv is checked
		if (nursingInfo.getIntakeInfo().get("isIVGiven") != null
				&& Boolean.valueOf(nursingInfo.getIntakeInfo().get("isIVGiven") + "")) {
			isFeedORIvGiven = true;

			if (!BasicUtils.isEmpty(feedOnlyObj.getIvfluidtype()))
				feedAndIVData.setIvtype(feedOnlyObj.getIvfluidtype());

			if (!BasicUtils.isEmpty(feedOnlyObj.getIvfluidrate()))
				feedAndIVData.setIvperhr(Float.valueOf(feedOnlyObj.getIvfluidrate()));

			if (!BasicUtils.isEmpty(intakeObj.getIvtotal())) {
				feedAndIVData.setIvtotal(intakeObj.getIvtotal());
			}

		} else {
			feedAndIVData.setIvtotal(null);
			feedAndIVData.setIvperhr(null);
		}

		feedAndIVData.setUhid(uhid);
		feedAndIVData.setNnIntakeTime(nursingInfo.getNnEntryTime().getHours() + BasicConstants.TIME_SPLITTER
				+ nursingInfo.getNnEntryTime().getMinutes() + BasicConstants.TIME_SPLITTER
				+ nursingInfo.getNnEntryTime().getMeridium());
		feedAndIVData.setLoggeduser(nursingInfo.getLoggedUser());

		notesDoa.saveObject(feedAndIVData); // for use case of view problem
		// ...separate blood bolus etc
		// .save..
		/*
		 * if(isFeedORIvGiven) { Vedaant if(listIntakeInfo!=null &&
		 * listIntakeInfo.size()>0) { Long nnIntakeID =
		 * listIntakeInfo.get(0).getNnIntakeid();
		 * feedAndIVData.setNnIntakeid(nnIntakeID);
		 * feedAndIVData.setCreationtime(listIntakeInfo.get(0).getCreationtime() ); }
		 * Vedaant notesDoa.saveObject(feedAndIVData); }
		 */

		// if(intakeObj.getBolustype()!=null &&
		// intakeObj.getBolustype().equalsIgnoreCase("Blood"))
		// saving iv only if the iv is checked
		if (nursingInfo.getIntakeInfo().get("isBloodGiven") != null
				&& Boolean.valueOf(nursingInfo.getIntakeInfo().get("isBloodGiven") + "")) {
			/*
			 * save to nursing blood product
			 *
			 * NursingBloodproduct blood = new NursingBloodproduct(); Object bloodProduct =
			 * nursingInfo.getIntakeInfo().get(BasicConstants.CURRENT_BLOOD_PRODUCT);
			 * BloodProduct bloodObj =
			 * mapper.readValue(BasicUtils.getObjAsJson(bloodProduct), BloodProduct.class);
			 *
			 * if (bloodObj.getDose() != null && !bloodObj.getDose().isEmpty())
			 * blood.setDose(bloodObj.getDose());
			 *
			 * blood.setDuration(bloodObj.getDuration());
			 * blood.setBloodgroup(bloodObj.getBloodgroup());
			 * blood.setStarttime(intakeObj.getBloodStarttime());
			 * blood.setBloodproduct(bloodObj.getBloodproduct()); blood.setUhid(uhid);
			 * blood.setLoggeduser(nursingInfo.getLoggedUser());
			 * blood.setNnBloodTime(nursingInfo.getNnEntryTime().getHours() +
			 * BasicConstants.TIME_SPLITTER + nursingInfo.getNnEntryTime().getMinutes() +
			 * BasicConstants.TIME_SPLITTER + nursingInfo.getNnEntryTime().getMeridium());
			 *
			 * Vedaant String prevBloodEntry =
			 * "select obj from NursingBloodproduct as obj where uhid='" + uhid +
			 * "' and to_char(creationtime,'YYYY-MM-DD')='" + entryDate + "'" +
			 * " and nnBloodTime='" + entryTime + "' order by creationtime desc";
			 * List<NursingBloodproduct> listBloodInfo =
			 * notesDoa.getListFromMappedObjNativeQuery(prevBloodEntry); if (listBloodInfo
			 * != null & listBloodInfo.size() > 0) { Long bgID =
			 * listBloodInfo.get(0).getNnBloodproductid(); blood.setNnBloodproductid(bgID);
			 * blood.setCreationtime(listBloodInfo.get(0).getCreationtime()); } Vedaant
			 * notesDoa.saveObject(blood);
			 */
		}
		// else if(intakeObj.getBolustype()!=null)
		// saving iv only if the iv is checked
		if (nursingInfo.getIntakeInfo().get("isBolusGiven") != null
				&& Boolean.valueOf(nursingInfo.getIntakeInfo().get("isBolusGiven") + "")) {
			// save to bolus
			NursingBolus bolus = new NursingBolus();
			bolus.setBolustype(intakeObj.getBolustype());
			if (intakeObj.getBolusDose() != null)
				bolus.setDose(intakeObj.getBolusDose());
			bolus.setDuration(intakeObj.getBolusDuration());
			bolus.setUhid(uhid);
			bolus.setLoggeduser(nursingInfo.getLoggedUser());
			bolus.setNnBolusTime(nursingInfo.getNnEntryTime().getHours() + BasicConstants.TIME_SPLITTER
					+ nursingInfo.getNnEntryTime().getMinutes() + BasicConstants.TIME_SPLITTER
					+ nursingInfo.getNnEntryTime().getMeridium());
			bolus.setStarttime(intakeObj.getBolusStarttime());
			/* Vedaant */
			String prevBolusEntry = "select obj from NursingBolus as obj where uhid='" + uhid
					+ "' and to_char(creationtime,'YYYY-MM-DD')='" + entryDate + "'" + " and nnBolusTime='" + entryTime
					+ "' order by creationtime desc";
			List<NursingBolus> listBolusInfo = notesDoa.getListFromMappedObjNativeQuery(prevBolusEntry);
			if (listBolusInfo != null && listBolusInfo.size() > 0) {
				Long blID = listBolusInfo.get(0).getNnBolusid();
				bolus.setNnBolusid(blID);
				bolus.setCreationtime(listBolusInfo.get(0).getCreationtime());
			}
			/* Vedaant */
			notesDoa.saveObject(bolus);
		}

	}

	// Save NeuroVitals Data
	@SuppressWarnings("unchecked")
	private ResponseMessageWithResponseObject saveNeuroVitals(NursingObservationPojo nursingInfo, String uhid) {
		ResponseMessageWithResponseObject obj = new ResponseMessageWithResponseObject();
		obj.setType(BasicConstants.MESSAGE_SUCCESS);
		obj.setMessage("save successfully");
		VwPupilReactivity vwPupil = new VwPupilReactivity();
		NursingNeuroVitals neuroVitals = new NursingNeuroVitals();
		PupilReactivity pupil = new PupilReactivity();
		Object vwPupilNew = nursingInfo.getNeuroVitalsInfo().get(BasicConstants.EMPTY_OBJ_STR);
		BasicUtils.getObjAsJson(vwPupilNew);
		try {
			vwPupil = mapper.readValue(BasicUtils.getObjAsJson(vwPupilNew), VwPupilReactivity.class);
			if (!BasicUtils.isEmpty(vwPupil)) {
				neuroVitals.setCcp(vwPupil.getCcp());
				neuroVitals.setGcs(vwPupil.getGcs());
				neuroVitals.setIcp(vwPupil.getIcp());
				neuroVitals.setSedationScore(vwPupil.getSedationScore());
				neuroVitals.setNnNeurovitalsTime(nursingInfo.getTime());
				neuroVitals.setLoggeduser(nursingInfo.getLoggedUser());
				neuroVitals.setUhid(uhid);

				/* Vedaant */
				String entryTime = nursingInfo.getTime();

				/* Check if the record already exists in table */
				String prevEntry = "select obj from NursingNeuroVitals as obj where uhid='" + uhid
						+ "' and to_char(creationtime,'YYYY-MM-DD')='" + entryDate + "'" + " and nnNeurovitalsTime='"
						+ entryTime + "' order by creationtime desc";
				List<NursingNeuroVitals> listNeuroVitalsInfo = notesDoa.getListFromMappedObjNativeQuery(prevEntry);
				if (listNeuroVitalsInfo != null && listNeuroVitalsInfo.size() > 0) {
					Long nnNeuroVitalsID = listNeuroVitalsInfo.get(0).getNnNeorovitalsid();
					neuroVitals.setNnNeorovitalsid(nnNeuroVitalsID);
					neuroVitals.setCreationtime(listNeuroVitalsInfo.get(0).getCreationtime());
				}
				/* Vedaant */

				inicuPostgersUtil.saveObject(neuroVitals);
				// save logs
				String desc = mapper.writeValueAsString(neuroVitals);
				String action = BasicConstants.INSERT;
				String loggeduser = null;
				if (!BasicUtils.isEmpty(nursingInfo.getLoggedUser())) {
					loggeduser = nursingInfo.getLoggedUser();
				} else {
					loggeduser = "1234";
				}

				String pageName = BasicConstants.NURSING_NOTES;

				logService.saveLog(desc, action, loggeduser, uhid, pageName);
				pupil.setLeftPupilsize(vwPupil.getLeftpupilsize());
				pupil.setLeftReactivity(vwPupil.getLeftreactivity());
				pupil.setRightPupilsize(vwPupil.getRightpupilsize());
				pupil.setRightReactivity(vwPupil.getRightreactivity());
				pupil.setUhid(uhid);
				pupil.setPupilTime(nursingInfo.getTime());

				/* Vedaant */

				/* Check if the record already exists in table */
				prevEntry = "select obj from PupilReactivity as obj where uhid='" + uhid
						+ "' and to_char(creationtime,'YYYY-MM-DD')='" + entryDate + "'" + " and pupilRectivityTime='"
						+ entryTime + "' order by creationtime desc";
				List<PupilReactivity> listPupilsInfo = notesDoa.getListFromMappedObjNativeQuery(prevEntry);
				if (listPupilsInfo != null && listPupilsInfo.size() > 0) {
					Long nnPupilID = listPupilsInfo.get(0).getPupilreactivityid();
					pupil.setPupilreactivityid(nnPupilID);
					pupil.setCreationtime(listPupilsInfo.get(0).getCreationtime());
				}
				/* Vedaant */

				inicuPostgersUtil.saveObject(pupil);

				// save logs
				desc = mapper.writeValueAsString(pupil);
				action = BasicConstants.INSERT;
				loggeduser = null;
				if (!BasicUtils.isEmpty(nursingInfo.getLoggedUser())) {
					loggeduser = nursingInfo.getLoggedUser();
				} else {
					loggeduser = "1234";
				}

				pageName = BasicConstants.NURSING_NOTES;

				logService.saveLog(desc, action, loggeduser, uhid, pageName);

			}
		} catch (Exception e) {
			obj.setType(BasicConstants.MESSAGE_FAILURE);
			obj.setMessage("operation failed");
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUser,
					uhid, "SAVE_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}

		return obj;
	}

	// save blood gas Data
	@SuppressWarnings("unchecked")
	private ResponseMessageWithResponseObject saveBloodGas(NursingObservationPojo nursingInfo, String uhid) {
		ResponseMessageWithResponseObject obj = new ResponseMessageWithResponseObject();
		obj.setType(BasicConstants.MESSAGE_SUCCESS);
		obj.setMessage("blood gas save successfully");
		NursingBloodGas bloodGas = new NursingBloodGas();
		Object bloodGasNew = nursingInfo.getBloodGasInfo().get(BasicConstants.EMPTY_OBJ_STR);
		BasicUtils.getObjAsJson(bloodGasNew);
		try {
			bloodGas = mapper.readValue(BasicUtils.getObjAsJson(bloodGasNew), NursingBloodGas.class);

			bloodGas.setNnBloodgasTime(nursingInfo.getNnEntryTime().getHours() + BasicConstants.TIME_SPLITTER
					+ nursingInfo.getNnEntryTime().getMinutes() + BasicConstants.TIME_SPLITTER
					+ nursingInfo.getNnEntryTime().getMeridium());
			bloodGas.setUhid(uhid);
			bloodGas.setLoggeduser(nursingInfo.getLoggedUser());
			String userDate = nursingInfo.getUserDate();
			bloodGas.setUserDate(userDate);
			System.out.println(BasicConstants.CLIENT_TIME_ZONE);

			int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
					- TimeZone.getDefault().getRawOffset();

			System.out.println(offset + "offset");

			bloodGas.setEntryDate((new Timestamp(nursingInfo.getEntryDate().getTime() + offset)));
			// bloodGas.setEntryDate(nursingInfo.getEntryDate());

			System.out.println(nursingInfo.getCreationtime() + "creation time date");
			if (nursingInfo.getCreationtime() != null) {
				bloodGas.setCreationtime(nursingInfo.getCreationtime());
				bloodGas.setModificationtime(nursingInfo.getCreationtime());
			} else {
				bloodGas.setCreationtime(new Timestamp(System.currentTimeMillis()));
				bloodGas.setModificationtime(new Timestamp(System.currentTimeMillis()));
			}

			/* Vedaant */
			/* Check if the record already exists in table */
			String prevEntry = "select obj from NursingBloodGas as obj where uhid='" + uhid
					+ "' and to_char(creationtime,'YYYY-MM-DD')='" + entryDate + "'" + " and nnBloodgasTime='"
					+ nursingInfo.getTime() + "' order by creationtime desc";
			List<NursingBloodGas> listBGInfo = notesDoa.getListFromMappedObjNativeQuery(prevEntry);
			if (listBGInfo != null && listBGInfo.size() > 0) {
				Long nngBGID = listBGInfo.get(0).getNnBloodgasid();
				bloodGas.setNnBloodgasid(nngBGID);
			}
			/* Vedaant */

			if (!BasicUtils.isEmpty(bloodGas)) {
				inicuPostgersUtil.saveObject(bloodGas);
			}

			// save logs
			String desc = mapper.writeValueAsString(bloodGas);
			String action = BasicConstants.INSERT;
			String loggeduser = null;
			if (!BasicUtils.isEmpty(nursingInfo.getLoggedUser())) {
				loggeduser = nursingInfo.getLoggedUser();
			} else {
				loggeduser = "1234";
			}

			String pageName = BasicConstants.NURSING_NOTES;

			logService.saveLog(desc, action, loggeduser, uhid, pageName);
		} catch (Exception e) {
			obj.setType(BasicConstants.MESSAGE_FAILURE);
			obj.setMessage("operation failed");
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUser,
					uhid, "SAVE_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		return obj;
	}

	@SuppressWarnings("unchecked")
	private ResponseMessageWithResponseObject saveVentilator(NursingObservationPojo nursingInfo, String uhid) {
		ResponseMessageWithResponseObject obj = new ResponseMessageWithResponseObject();
		obj.setType(BasicConstants.MESSAGE_SUCCESS);
		obj.setMessage("ventilator save successfully");
		NursingVentilaor ventiloar = new NursingVentilaor();
		Object ventiloarNew = nursingInfo.getVentilatorInfo().get(BasicConstants.EMPTY_OBJ_STR);
		BasicUtils.getObjAsJson(ventiloarNew);
		String ventmode = "";

		try {
			ventiloar = mapper.readValue(BasicUtils.getObjAsJson(ventiloarNew), NursingVentilaor.class);
			ventiloar.setLoggeduser(nursingInfo.getLoggedUser());

			if(ventiloar.getVentmode().toString().equalsIgnoreCase("VM0005") || ventiloar.getVentmode().toString().equalsIgnoreCase("VM0019") | ventiloar.getVentmode().toString().equalsIgnoreCase("VM0006") || ventiloar.getVentmode().toString().equalsIgnoreCase("VM0016")) {
				if(ventiloar.getVentmode().toString().equalsIgnoreCase("VM0005")) {
					ventmode += "PSV ";
				}
				if(ventiloar.getVentmode().toString().equalsIgnoreCase("VM0019")) {
					ventmode += "SIPPV ";
				}
				if(ventiloar.getVentmode().toString().equalsIgnoreCase("VM0006")) {
					ventmode += "SIMV ";
				}
				if(ventiloar.getVentmode().toString().equalsIgnoreCase("VM0016")) {
					ventmode += "IMV ";
				}
			if(ventiloar.getVolumeguarantee()!=null && ventiloar.getVolumeguarantee()!="") {
				ventmode += " + VG ";
			}
			if(ventiloar.getPressuresupporttype()!=null && ventiloar.getPressuresupporttype().toString().equalsIgnoreCase("Yes")) {
				ventmode += " + PS ";
			}
			if(ventiloar.getControltype()!=null) {
				ventmode += " + " + ventiloar.getControltype() + "";
			}
				//ventiloar.setVent_desc(ventmode);
			}

			if(ventiloar.getVentmode().toString().equalsIgnoreCase("VM0020")) {
				ventmode += "CPAP ";
				if(ventiloar.getCpaptype()!=null) {
					ventmode += " - " + ventiloar.getCpaptype() + "";
				}
				if(ventiloar.getDeliverytype()!=null) {
					ventmode += " - " + ventiloar.getDeliverytype() +"";
				}
			}
			ventiloar.setVent_desc(ventmode);

			if(ventmode.equals("")) {
				String query = "select ventmodeid,ventilationmode from ref_ventilationmode";
				List<Object[]> ventiList = inicuDoa.getListFromNativeQuery(query);

				for(int i=0;i<ventiList.size();i++) {
					Object[] vent = ventiList.get(i);
					String ventid = vent[0].toString();
					String ventname = vent[1].toString();
					//String ventid = vent.getVentmodeid();
					if(ventiloar.getVentmode().toString().equalsIgnoreCase(ventid)) {
						ventmode = ventname;
						ventiloar.setVent_desc(ventmode);
					}
				}
			}
			ventiloar.setNnVentilaorTime(nursingInfo.getNnEntryTime().getHours() + BasicConstants.TIME_SPLITTER
					+ nursingInfo.getNnEntryTime().getMinutes() + BasicConstants.TIME_SPLITTER
					+ nursingInfo.getNnEntryTime().getMeridium());
			ventiloar.setUhid(uhid);
			String userDate = nursingInfo.getUserDate();
			ventiloar.setUserDate(userDate);

			System.out.println(BasicConstants.CLIENT_TIME_ZONE);

			int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
					- TimeZone.getDefault().getRawOffset();

			System.out.println(offset + "offset");

			ventiloar.setEntryDate((new Timestamp(nursingInfo.getEntryDate().getTime() + offset)));

			// ventiloar.setEntryDate(nursingInfo.getEntryDate());

			System.out.println(nursingInfo.getCreationtime() + "creation time date");
			if (nursingInfo.getCreationtime() != null) {
				ventiloar.setCreationtime(nursingInfo.getCreationtime());
				ventiloar.setModificationtime(nursingInfo.getCreationtime());
			} else {
				ventiloar.setCreationtime(new Timestamp(System.currentTimeMillis()));
				ventiloar.setModificationtime(new Timestamp(System.currentTimeMillis()));
			}

			/* Vedaant */
			/* Check if the record already exists in table */
			String prevEntry = "select obj from NursingVentilaor as obj where uhid='" + uhid
					+ "' and to_char(creationtime,'YYYY-MM-DD')='" + entryDate + "'" + " and nnVentilaorTime='"
					+ nursingInfo.getTime() + "' order by creationtime desc";
			List<NursingVentilaor> listVentilatorInfo = notesDoa.getListFromMappedObjNativeQuery(prevEntry);
			if (listVentilatorInfo != null && listVentilatorInfo.size() > 0) {
				Long nnVentilaorID = listVentilatorInfo.get(0).getNnVentilaorid();
				ventiloar.setNnVentilaorid(nnVentilaorID);
			}
			/* Vedaant */

			if (!BasicUtils.isEmpty(ventiloar)) {
				inicuPostgersUtil.saveObject(ventiloar);
			}
			// save logs
			String desc = mapper.writeValueAsString(ventiloar);
			String action = BasicConstants.INSERT;
			String loggeduser = null;
			if (!BasicUtils.isEmpty(nursingInfo.getLoggedUser())) {
				loggeduser = nursingInfo.getLoggedUser();
			} else {
				loggeduser = "1234";
			}

			String pageName = BasicConstants.NURSING_NOTES;

			logService.saveLog(desc, action, loggeduser, uhid, pageName);

		} catch (Exception e) {
			obj.setType(BasicConstants.MESSAGE_FAILURE);
			obj.setMessage("operation failed");
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUser,
					uhid, "SAVE_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		return obj;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseMessageWithResponseObject setNursingDropDowns() {
		List<KeyValueObj> pupilDropDown = new ArrayList<KeyValueObj>();
		NursingNotesDropDownsObj dropDown = new NursingNotesDropDownsObj();
		ResponseMessageWithResponseObject obj = new ResponseMessageWithResponseObject();
		String query = "SELECT r FROM RefPupilreactivity r";
		try {
			List<RefPupilreactivity> pupilList = inicuPostgersUtil.executeMappedObjectCustomizedQuery(query);
			if (pupilList != null) {
				for (RefPupilreactivity p : pupilList) {
					KeyValueObj keyObj = new KeyValueObj();
					keyObj.setKey(p.getReactivityid());
					keyObj.setValue(p.getReactivity());
					System.out.println("hi");
					pupilDropDown.add(keyObj);
				}

			}
			dropDown.setPupilReact(pupilDropDown);
			obj.setReturnedObject(dropDown);
		} catch (Exception e) {
			obj.setType(BasicConstants.MESSAGE_FAILURE);
			obj.setMessage("operation failed");
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUser,
					uhid, "SAVE_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		return obj;
	}

	public ResponseMessageWithResponseObject saveDailyAssessment(NursingObservationPojo nursingInfo, String uhid) {
		ResponseMessageWithResponseObject obj = new ResponseMessageWithResponseObject();
		obj.setType(BasicConstants.MESSAGE_SUCCESS);
		obj.setMessage("save successfully");
		NursingDailyassesment daily = new NursingDailyassesment();
		Object dailyNew = nursingInfo.getDailyAssessmentInfo().get(BasicConstants.EMPTY_OBJ_STR);

		try {
			daily = mapper.readValue(BasicUtils.getObjAsJson(dailyNew), NursingDailyassesment.class);
			System.out.println(daily.getChestPt());
			daily.setLoggeduser(nursingInfo.getLoggedUser());

			daily.setUhid(uhid);
			System.out.println(daily);
			if (!BasicUtils.isEmpty(daily)) {
				inicuPostgersUtil.saveObject(daily);
			}
			// save logs
			String desc = mapper.writeValueAsString(daily);
			String action = BasicConstants.INSERT;
			String loggeduser = null;
			if (!BasicUtils.isEmpty(nursingInfo.getLoggedUser())) {
				loggeduser = nursingInfo.getLoggedUser();
			} else {
				loggeduser = "1234";
			}

			String pageName = BasicConstants.NURSING_NOTES;

			logService.saveLog(desc, action, loggeduser, uhid, pageName);
		} catch (Exception e) {
			obj.setType(BasicConstants.MESSAGE_FAILURE);
			obj.setMessage("operation failed");
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUser,
					uhid, "SAVE_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		return obj;

	}

	@SuppressWarnings("unchecked")
	public ResponseMessageWithResponseObject saveCatheters(NursingObservationPojo nursingInfo, String uhid) {
		ResponseMessageWithResponseObject obj = new ResponseMessageWithResponseObject();
		obj.setType(BasicConstants.MESSAGE_SUCCESS);
		obj.setMessage("save successfully");
		NursingCatheter catheter = new NursingCatheter();
		Object catheterNew = nursingInfo.getCathetersInfo().get(BasicConstants.EMPTY_OBJ_STR);
		BasicUtils.getObjAsJson(catheterNew);
		try {
			catheter = mapper.readValue(BasicUtils.getObjAsJson(catheterNew), NursingCatheter.class);
			catheter.setLoggeduser(nursingInfo.getLoggedUser());
			catheter.setNnCathetersTime(nursingInfo.getTime());
			catheter.setUhid(uhid);
			if (!BasicUtils.isEmpty(catheter)) {
				/* Vedaant */

				String entryTime = nursingInfo.getTime();

				/* Check if the record already exists in table */
				String prevEntry = "select obj from NursingCatheter as obj where uhid='" + uhid
						+ "' and to_char(creationtime,'YYYY-MM-DD')='" + entryDate + "'" + " and nnCathetersTime='"
						+ entryTime + "' order by creationtime desc";
				List<NursingCatheter> listCathInfo = notesDoa.getListFromMappedObjNativeQuery(prevEntry);
				if (listCathInfo != null && listCathInfo.size() > 0) {
					Long nnCathID = listCathInfo.get(0).getNnCathetersid();
					catheter.setNnCathetersid(nnCathID);
				}
				/* Vedaant */
				inicuPostgersUtil.saveObject(catheter);
			}
			// save logs
			String desc = mapper.writeValueAsString(catheter);
			String action = BasicConstants.INSERT;
			String loggeduser = null;
			if (!BasicUtils.isEmpty(nursingInfo.getLoggedUser())) {
				loggeduser = nursingInfo.getLoggedUser();
			} else {
				loggeduser = "1234";
			}

			String pageName = BasicConstants.NURSING_NOTES;

			logService.saveLog(desc, action, loggeduser, uhid, pageName);
		} catch (Exception e) {
			obj.setType(BasicConstants.MESSAGE_FAILURE);
			obj.setMessage("operation failed");
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUser,
					uhid, "SAVE_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		return obj;

	}

	@SuppressWarnings("unchecked")
	private ResponseMessageWithResponseObject saveMisc(NursingObservationPojo nursingInfo, String uhid) {
		ResponseMessageWithResponseObject obj = new ResponseMessageWithResponseObject();
		obj.setType(BasicConstants.MESSAGE_SUCCESS);
		obj.setMessage("save successfully");
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
		NursingMisc misc = new NursingMisc();
		Object miscNew = nursingInfo.getMiscInfo().get(BasicConstants.EMPTY_OBJ_STR);
		BasicUtils.getObjAsJson(miscNew);
		try {
			misc = mapper.readValue(BasicUtils.getObjAsJson(miscNew), NursingMisc.class);
			misc.setPhototherapyStartDate(null);
			misc.setPhototherapyEndDate(null);
			misc.setNnMiscTime(nursingInfo.getNnEntryTime().getHours() + BasicConstants.TIME_SPLITTER
					+ nursingInfo.getNnEntryTime().getMinutes() + BasicConstants.TIME_SPLITTER
					+ nursingInfo.getNnEntryTime().getMeridium());
			misc.setUhid(uhid);
			misc.setLoggeduser(nursingInfo.getLoggedUser());
			if (nursingInfo.getPhotoTherapy() != null) {
				if (nursingInfo.getPhotoTherapy() == true) {

					misc.setPhototherapyStartDate(currentTimestamp);
				} else if (nursingInfo.getPhotoTherapy() == false) {
					misc.setPhototherapyEndDate(currentTimestamp);
				}
			}
			if (!BasicUtils.isEmpty(misc)) {
				/* Vedaant */
				String entryTime = nursingInfo.getNnEntryTime().getHours() + BasicConstants.TIME_SPLITTER
						+ nursingInfo.getNnEntryTime().getMinutes() + BasicConstants.TIME_SPLITTER
						+ nursingInfo.getNnEntryTime().getMeridium();

				/* Check if the record already exists in table */
				String prevEntry = "select obj from NursingMisc as obj where uhid='" + uhid
						+ "' and to_char(creationtime,'YYYY-MM-DD')='" + entryDate + "'" + " and nnMiscTime='"
						+ entryTime + "' order by creationtime desc";
				List<NursingMisc> listMiscInfo = notesDoa.getListFromMappedObjNativeQuery(prevEntry);
				if (listMiscInfo != null && listMiscInfo.size() > 0) {
					Long nnMiscID = listMiscInfo.get(0).getNnMiscid();
					misc.setNnMiscid(nnMiscID);
				}
				/* Vedaant */

				inicuPostgersUtil.saveObject(misc);
			}
			// save logs
			String desc = mapper.writeValueAsString(misc);
			String action = BasicConstants.INSERT;
			String loggeduser = null;
			if (!BasicUtils.isEmpty(nursingInfo.getLoggedUser())) {
				loggeduser = nursingInfo.getLoggedUser();
			} else {
				loggeduser = "1234";
			}

			String pageName = BasicConstants.NURSING_NOTES;

			logService.saveLog(desc, action, loggeduser, uhid, pageName);

		} catch (Exception e) {
			obj.setType(BasicConstants.MESSAGE_FAILURE);
			obj.setMessage("operation failed");
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUser,
					uhid, "SAVE_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}

		return obj;
	}

	@SuppressWarnings("unchecked")
	public BloodProduct getBloodProductsInfo(String uhid) {
		List<BloodProduct> bloodList = null;

		try {
			java.sql.Date date = new Date(new java.util.Date().getTime());

			String query = "select obj from BloodProduct as obj where uhid='" + uhid
					+ "' and to_char(creationtime, 'yyyy-MM-dd')" + "='" + date + "' order by creationtime desc";
			bloodList = notesDoa.getListFromMappedObjNativeQuery(query);
			if (bloodList != null && bloodList.size() > 0) {
				return bloodList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUser,
					uhid, "GET_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		return new BloodProduct();
	}

	@Override
	public NursingObservationPojoAll getModuleNursingNotesAll(String date, String uhid) {

		NursingObservationPojoAll nursingInfo = new NursingObservationPojoAll();
		Iterator<String> iterator = BasicConstants.nursingNotesModules.iterator();
		try {
			while (iterator.hasNext()) {
				nursingInfo = getNursingObservationInfoAll(iterator.next(), date, uhid, "", nursingInfo);
			}

			BabyVisitsObj babyVisit = getBabyVisits(date, uhid);
			nursingInfo.setBabyVisit(babyVisit);

		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUser,
					uhid, "GET_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}

		return nursingInfo;
	}

	public NursingObservationPojoAll getNursingObservationInfoAll(String tabType, String entryDate, String uhid,
			String loggeUser, NursingObservationPojoAll nursingPojo) throws Exception {

		NursingObservationPojoAll allNotes = null;

		if (nursingPojo == null) {
			allNotes = new NursingObservationPojoAll();
		} else {
			allNotes = nursingPojo;
		}

		java.sql.Date sqlPresentDate = null;

		entryDate = entryDate.substring(0, 24);
		DateFormat readFormat = new SimpleDateFormat("EEE MMM dd yyyy hh:mm:ss");
		java.util.Date presentDate = readFormat.parse(entryDate);
		sqlPresentDate = new java.sql.Date(presentDate.getTime());

		this.entryDate = "" + sqlPresentDate + "";
		this.uhid = uhid;
		this.loggedInUser = loggedInUser;

		if (tabType.equalsIgnoreCase(BasicConstants.VITAL_PARAM)) {
			List<HashMap<String, Object>> vitalData = new ArrayList<HashMap<String, Object>>();
			List<NursingVitalparameter> vital = getPreviousVitalInformation(true);
			Iterator<NursingVitalparameter> iterator = vital.iterator();
			while (iterator.hasNext()) {
				NursingVitalparameter vitalItem = iterator.next();
				String vitalTime = vitalItem.getNnVitalparameterTime();
				HashMap<String, Object> vitalMapItem = new HashMap<String, Object>();
				String[] timeArr = vitalTime.split(":");

				Object time24Format = "";
				if (timeArr != null && timeArr.length == 3) {

					if (timeArr[2].equalsIgnoreCase("PM")) {
						if (timeArr[0].trim().equalsIgnoreCase("12")) {
							time24Format = Integer.valueOf("12");
						} else {
							time24Format = Integer.valueOf(timeArr[0].trim()) + 12;
						}
					} else {
						if (timeArr[0].trim().equalsIgnoreCase("12")) {
							time24Format = Integer.valueOf("0");
						} else {
							time24Format = Integer.valueOf(timeArr[0].trim());
						}
					}
				}
				vitalMapItem.put("TIME", time24Format);
				vitalMapItem.put("OBJECT", vitalItem);
				vitalData.add(vitalMapItem);
			}
			allNotes.setVitalParametersInfo(vitalData);

		} else if (tabType.equalsIgnoreCase(BasicConstants.INTAKE)) {

			List<HashMap<String, Object>> intakeData = new ArrayList<HashMap<String, Object>>();
			List<VwNursingnotesIntakeFinal2> intake = getPreviousIntakeInfo();

			Iterator<VwNursingnotesIntakeFinal2> iterator = intake.iterator();
			while (iterator.hasNext()) {
				VwNursingnotesIntakeFinal2 intakeItem = iterator.next();
				String intakeTime = intakeItem.getNnIntakeTime();
				HashMap<String, Object> intakeMapItem = new HashMap<String, Object>();
				String[] timeArr = intakeTime.split(":");

				Object time24Format = "";
				if (timeArr != null && timeArr.length == 3) {

					if (timeArr[2].equalsIgnoreCase("PM")) {
						if (timeArr[0].equalsIgnoreCase("12")) {
							time24Format = Integer.valueOf("12");
						} else {
							time24Format = Integer.valueOf(timeArr[0]) + 12;
						}
					} else {
						if (timeArr[0].equalsIgnoreCase("12")) {
							time24Format = Integer.valueOf("0");
						} else {
							time24Format = Integer.valueOf(timeArr[0]);
						}
					}

				}
				intakeMapItem.put("TIME", time24Format);
				intakeMapItem.put("OBJECT", intakeItem);
				intakeData.add(intakeMapItem);
			}
			allNotes.setIntakeInfo(intakeData);

		} else if (tabType.equalsIgnoreCase(BasicConstants.OUTPUT)) {

			List<HashMap<String, Object>> outputData = new ArrayList<HashMap<String, Object>>();
			List<VwNursingnotesOutputFinal> output = getPreviousOutputInfo(true);

			Iterator<VwNursingnotesOutputFinal> iterator = output.iterator();
			while (iterator.hasNext()) {
				VwNursingnotesOutputFinal outputItem = iterator.next();
				String outputTime = outputItem.getNnOutputTime();
				HashMap<String, Object> outputMapItem = new HashMap<String, Object>();
				String[] timeArr = outputTime.split(":");

				Object time24Format = "";
				if (timeArr != null && timeArr.length == 3) {

					if (timeArr[2].equalsIgnoreCase("PM")) {
						if (timeArr[0].equalsIgnoreCase("12")) {
							time24Format = Integer.valueOf("12");
						} else {
							time24Format = Integer.valueOf(timeArr[0]) + 12;
						}
					} else {
						if (timeArr[0].equalsIgnoreCase("12")) {
							time24Format = Integer.valueOf("0");
						} else {
							time24Format = Integer.valueOf(timeArr[0]);
						}
					}

				}
				outputMapItem.put("TIME", time24Format);
				outputMapItem.put("OBJECT", outputItem);
				outputData.add(outputMapItem);
			}
			allNotes.setOutputInfo(outputData);

		} else if (tabType.equalsIgnoreCase(BasicConstants.CATHERS)) {

			List<HashMap<String, Object>> cathersData = new ArrayList<HashMap<String, Object>>();
			List<NursingCatheter> cathers = getPreviousCathersInfo(true);

			Iterator<NursingCatheter> iterator = cathers.iterator();
			while (iterator.hasNext()) {
				NursingCatheter cathersItem = iterator.next();
				String cathersTime = cathersItem.getNnCathetersTime();
				HashMap<String, Object> cathersMapItem = new HashMap<String, Object>();
				String[] timeArr = cathersTime.split(":");

				Object time24Format = "";
				if (timeArr != null && timeArr.length == 3) {

					if (timeArr[2].equalsIgnoreCase("PM")) {
						if (timeArr[0].equalsIgnoreCase("12")) {
							time24Format = Integer.valueOf("12");
						} else {
							time24Format = Integer.valueOf(timeArr[0]) + 12;
						}
					} else {
						if (timeArr[0].equalsIgnoreCase("12")) {
							time24Format = Integer.valueOf("0");
						} else {
							time24Format = Integer.valueOf(timeArr[0]);
						}
					}

				}
				cathersMapItem.put("TIME", time24Format);
				cathersMapItem.put("OBJECT", cathersItem);
				cathersData.add(cathersMapItem);
			}
			allNotes.setCathetersInfo(cathersData);

		} else if (tabType.equalsIgnoreCase(BasicConstants.BLOOD_GAS)) {

			List<HashMap<String, Object>> bloodGasData = new ArrayList<HashMap<String, Object>>();
			List<NursingBloodGas> bloodGas = getPreviousBloodGasInfo(true);

			Iterator<NursingBloodGas> iterator = bloodGas.iterator();
			while (iterator.hasNext()) {
				NursingBloodGas bloodGasItem = iterator.next();
				String bloodGasTime = bloodGasItem.getNnBloodgasTime();
				HashMap<String, Object> bloodGasMapItem = new HashMap<String, Object>();
				String[] timeArr = bloodGasTime.split(":");

				Object time24Format = "";
				if (timeArr != null && timeArr.length == 3) {

					if (timeArr[2].equalsIgnoreCase("PM")) {
						if (timeArr[0].equalsIgnoreCase("12")) {
							time24Format = Integer.valueOf("12");
						} else {
							time24Format = Integer.valueOf(timeArr[0]) + 12;
						}
					} else {
						if (timeArr[0].equalsIgnoreCase("12")) {
							time24Format = Integer.valueOf("0");
						} else {
							time24Format = Integer.valueOf(timeArr[0]);
						}
					}

				}
				bloodGasMapItem.put("TIME", time24Format);
				bloodGasMapItem.put("OBJECT", bloodGasItem);
				bloodGasData.add(bloodGasMapItem);
			}
			allNotes.setBloodGasInfo(bloodGasData);

		} else if (tabType.equalsIgnoreCase(BasicConstants.NURO_VITALS)) {

			List<HashMap<String, Object>> nuroVitalsData = new ArrayList<HashMap<String, Object>>();
			List<VwPupilReactivity> nuroVitals = getPreviousNeuroVitalsInfo(true);

			Iterator<VwPupilReactivity> iterator = nuroVitals.iterator();
			while (iterator.hasNext()) {
				VwPupilReactivity nuroVitalsItem = iterator.next();
				String nuroVitalsTime = nuroVitalsItem.getNnNeurovitalsTime();
				HashMap<String, Object> nuroVitalsMapItem = new HashMap<String, Object>();
				String[] timeArr = nuroVitalsTime.split(":");

				Object time24Format = "";
				if (timeArr != null && timeArr.length == 3) {

					if (timeArr[2].equalsIgnoreCase("PM")) {
						if (timeArr[0].equalsIgnoreCase("12")) {
							time24Format = Integer.valueOf("12");
						} else {
							time24Format = Integer.valueOf(timeArr[0]) + 12;
						}
					} else {
						if (timeArr[0].equalsIgnoreCase("12")) {
							time24Format = Integer.valueOf("0");
						} else {
							time24Format = Integer.valueOf(timeArr[0]);
						}
					}

				}
				nuroVitalsMapItem.put("TIME", time24Format);
				nuroVitalsMapItem.put("OBJECT", nuroVitalsItem);
				nuroVitalsData.add(nuroVitalsMapItem);
			}
			allNotes.setNeuroVitalsInfo(nuroVitalsData);

		} else if (tabType.equalsIgnoreCase(BasicConstants.VENTILATOR)) {

			List<HashMap<String, Object>> ventilatorData = new ArrayList<HashMap<String, Object>>();
			List<NursingVentilaor> ventilator = getPreviousVentilaorInfo(true);

			Iterator<NursingVentilaor> iterator = ventilator.iterator();
			while (iterator.hasNext()) {
				NursingVentilaor ventilatorItem = iterator.next();
				String ventilatorTime = ventilatorItem.getNnVentilaorTime();
				HashMap<String, Object> ventilatorMapItem = new HashMap<String, Object>();
				String[] timeArr = ventilatorTime.split(":");

				Object time24Format = "";
				if (timeArr != null && timeArr.length == 3) {

					if (timeArr[2].equalsIgnoreCase("PM")) {
						if (timeArr[0].equalsIgnoreCase("12")) {
							time24Format = Integer.valueOf("12");
						} else {
							time24Format = Integer.valueOf(timeArr[0]) + 12;
						}
					} else {
						if (timeArr[0].equalsIgnoreCase("12")) {
							time24Format = Integer.valueOf("0");
						} else {
							time24Format = Integer.valueOf(timeArr[0]);
						}
					}

				}
				ventilatorMapItem.put("TIME", time24Format);
				ventilatorMapItem.put("OBJECT", ventilatorItem);
				ventilatorData.add(ventilatorMapItem);
			}
			allNotes.setVentilatorInfo(ventilatorData);

		} else if (tabType.equalsIgnoreCase(BasicConstants.DAILY_ASSESSMENT)) {

			NursingDailyAssesmentJSON dailyAssess = getPreviousDailyAssessmentInfo();
			allNotes.setDailyAssessmentInfo(dailyAssess);

		} else if (tabType.equalsIgnoreCase(BasicConstants.MISC)) {

			List<HashMap<String, Object>> miscData = new ArrayList<HashMap<String, Object>>();
			List<NursingMisc> misc = getPreviousMiscInfo();

			Iterator<NursingMisc> iterator = misc.iterator();
			while (iterator.hasNext()) {
				NursingMisc miscItem = iterator.next();
				String miscTime = miscItem.getNnMiscTime();
				HashMap<String, Object> miscMapItem = new HashMap<String, Object>();
				String[] timeArr = miscTime.split(":");

				Object time24Format = "";
				if (timeArr != null && timeArr.length == 3) {

					if (timeArr[2].equalsIgnoreCase("PM")) {
						if (timeArr[0].equalsIgnoreCase("12")) {
							time24Format = Integer.valueOf("12");
						} else {
							time24Format = Integer.valueOf(timeArr[0]) + 12;
						}
					} else {
						if (timeArr[0].equalsIgnoreCase("12")) {
							time24Format = Integer.valueOf("0");
						} else {
							time24Format = Integer.valueOf(timeArr[0]);
						}
					}

				}
				miscMapItem.put("TIME", time24Format);
				miscMapItem.put("OBJECT", miscItem);
				miscData.add(miscMapItem);
			}
			allNotes.setMiscInfo(miscData);
		}
		return allNotes;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List getPreviousDailyAssessmentInfoAll() {
		String query = "select obj from NursingDailyassesment as obj where uhid='" + uhid
				+ "' and to_char(creationtime,'YYYY-MM-DD')='" + entryDate + "' order by creationtime desc";
		List<NursingDailyassesment> dailyList;
		dailyList = notesDoa.getListFromMappedObjNativeQuery(query);
		return dailyList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public TpnFeedPojo getTpnFeedDetails(String uhid, String loggedUser) {

		TpnFeedPojo tpnObj = new TpnFeedPojo();
		java.sql.Date todayDate = new java.sql.Date(new java.util.Date().getTime());

		try {

			// Cycle runs from 8AM to 8AM.
			Timestamp today = new Timestamp((new java.util.Date().getTime()));
			Timestamp currentDate = new Timestamp((new java.util.Date().getTime()));
			Timestamp yesterday = new Timestamp((new java.util.Date().getTime()) - (24 * 60 * 60 * 1000));
			Timestamp tomorrow = new Timestamp((new java.util.Date().getTime()) + (24 * 60 * 60 * 1000));

			int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
					- TimeZone.getDefault().getRawOffset();

			today.setHours(8);
			today.setMinutes(0);
			today.setSeconds(0);

			yesterday.setHours(8);
			yesterday.setMinutes(0);
			yesterday.setSeconds(0);

			tomorrow.setHours(8);
			tomorrow.setMinutes(0);
			tomorrow.setSeconds(0);

			// get current baby visit ...
			TpnFeedCurrentObj currentFeedDetails = new TpnFeedCurrentObj();
			currentFeedDetails.setIsNewEntry(true);
			String queryCurrentBabyVisit = "select obj from BabyVisit as obj where uhid='" + uhid
					+ "' order by creationtime desc";
			List<BabyVisit> currentBabyVisitList = notesDoa.getListFromMappedObjNativeQuery(queryCurrentBabyVisit);

			if (currentBabyVisitList != null && currentBabyVisitList.size() > 0) {
				for (BabyVisit visit : currentBabyVisitList) {
					Float weight = visit.getWeightForCal();
					if (weight != null) {
						weight = weight / 1000;
						currentFeedDetails.setCurrentWeight(weight + "");
						currentFeedDetails.getBabyFeed().setWorkingWeight(weight);
						break;
					}
				}
			}

			currentDate = new Timestamp(currentDate.getTime() + offset);
			today = new Timestamp(today.getTime() - offset);
			yesterday = new Timestamp(yesterday.getTime() - offset);
			tomorrow = new Timestamp(tomorrow.getTime() - offset);
			String lastFeedQuery = "";
			// get feed details from last 24 hrs(8AM to 8AM)
			if(offset != 0) {
				if(currentDate.getHours() >= 8 || currentDate.getHours() <= 5){
					lastFeedQuery = "select obj from BabyfeedDetail obj where uhid='" + uhid + "' and entrydatetime<='"
							+ tomorrow + "' and  entrydatetime>='" + today + "' order by entrydatetime desc";
				}
				else{
					lastFeedQuery = "select obj from BabyfeedDetail obj where uhid='" + uhid + "' and entrydatetime<='"
							+ today + "' and  entrydatetime>='" + yesterday + "' order by entrydatetime desc";
				}
			}
			else {
				if(currentDate.getHours() >= 8){
					lastFeedQuery = "select obj from BabyfeedDetail obj where uhid='" + uhid + "' and entrydatetime<='"
							+ tomorrow + "' and  entrydatetime>='" + today + "' order by entrydatetime desc";
				}
				else{
					lastFeedQuery = "select obj from BabyfeedDetail obj where uhid='" + uhid + "' and entrydatetime<='"
							+ today + "' and  entrydatetime>='" + yesterday + "' order by entrydatetime desc";
				}
			}

			List<BabyfeedDetail> babyFeedAllList = notesDoa.getListFromMappedObjNativeQuery(lastFeedQuery);
			if (!BasicUtils.isEmpty(babyFeedAllList)) {
				tpnObj.setLastFeedInfo(babyFeedAllList.get(0));
			}

			String enAddtivesBrand = "select obj from RefEnAddtivesBrand obj";
			List<RefEnAddtivesBrand> enAddtivesList = notesDoa.getListFromMappedObjNativeQuery(enAddtivesBrand);
			if (!BasicUtils.isEmpty(enAddtivesList)) {
				tpnObj.setAddtivesbrandList(enAddtivesList);
			}

			// get normal feed details...
			String feedQuery = "select obj from BabyfeedDetail obj where uhid='" + uhid + "' order by entrydatetime desc";
			List<BabyfeedDetail> babyFeedList = notesDoa.getListFromMappedObjNativeQuery(feedQuery);
			BabyfeedDetail currentBabyFeed = currentFeedDetails.getBabyFeed();
			// calculating feed calculator....
			FeedCalculatorPOJO calculator = new FeedCalculatorPOJO();
			if (!BasicUtils.isEmpty(babyFeedList)) {
				String nutritionCalculator = "select obj from RefNutritioncalculator obj";
				List<RefNutritioncalculator> nutritionList = notesDoa.getListFromMappedObjNativeQuery(nutritionCalculator);
				calculator.setRefNutritionInfo(nutritionList);
				BabyfeedDetail feed = babyFeedList.get(0);
				currentBabyFeed.setPastBolusTotalFeed(feed.getPastBolusTotalFeed());

				BabyfeedDetail feedLast = babyFeedList.get(0);

				CaclulatorDeficitPOJO cuurentDeficitLast = getDeficitFeedCalculator(feedLast, nutritionList,
						currentFeedDetails.getCurrentWeight());
				calculator.setLastDeficitCal(cuurentDeficitLast);
				tpnObj.setFeedCalulator(calculator);
			}

			String medicineQuery = "select obj from BabyPrescription obj where uhid='" + uhid
					+ "' and route='IV' and isactive='true' order by startdate desc";
			List<BabyPrescription> babyPrescriptionList = notesDoa.getListFromMappedObjNativeQuery(medicineQuery);

			//Blood Product Volume only included in PN if the doctor agrees to it plus if the doctor orders within the duration
			String bloodQuery = "select obj from DoctorBloodProducts obj where uhid='" + uhid + "' and isIncludeInPN = 'true' order by assessment_time desc";
			List<DoctorBloodProducts> bloodList = notesDoa.getListFromMappedObjNativeQuery(bloodQuery);
			if(!BasicUtils.isEmpty(bloodList)) {
				Long duration = (currentDate.getTime() - (bloodList.get(0).getAssessment_time().getTime() + offset)) / (1000 * 60 * 60);
				System.out.println(currentDate.getTime() + " " + bloodList.get(0).getAssessment_time().getTime() + " " + offset + " Blood Prodcut List Time");
				if(bloodList.get(0).getInfusion_time() != null && duration < bloodList.get(0).getInfusion_time())
					tpnObj.setBabyBloodProductList(bloodList);
			}


			//Heplock Volume only included in PN if the doctor agrees to it
			String centralLineQuery = "select obj from CentralLine obj where uhid='" + uhid + "' and isIncludeInPN = 'true' and "
					+ "creationtime <= '" + tomorrow + "' and creationtime>='" + today + "' order by creationtime desc";
			List<CentralLine> centralLineList = notesDoa.getListFromMappedObjNativeQuery(centralLineQuery);

			String ivMedQuery = "select obj from BabyfeedivmedDetail obj where uhid='" + uhid
					+ "'  order by creationtime desc";

			List<HashMap<String, Object>> pastBolusList = new ArrayList<HashMap<String, Object>>();

			String additionalPNStatus = null;
			for (BabyfeedDetail babyFeed : babyFeedList) {
				if (!BasicUtils.isEmpty(babyFeed.getFeedmethod())) {
					String[] arrayFeedMethod = babyFeed.getFeedmethod().replace(" ", "").trim().replace("[", "")
							.replace("]", "").split(",");
					ArrayList<String> listFeedMethod = new ArrayList<String>(Arrays.asList(arrayFeedMethod));
					babyFeed.setFeedmethodList(listFeedMethod);
				}

				// calculate here...bolus of the baby......

				if (!BasicUtils.isEmpty(babyFeed.getIsBolusGiven()) && babyFeed.getIsBolusGiven() == true) {
					String date = CalendarUtility.getDateformatdb(CalendarUtility.CLIENT_CRUD_OPERATION)
							.format(babyFeed.getCreationtime().getTime());
					if (date.equalsIgnoreCase(todayDate + "")) {
						HashMap<String, Object> bolus = new HashMap<String, Object>();
						bolus.put("bolusType", babyFeed.getBolusType());
						bolus.put("bolusVolume", babyFeed.getBolusVolume());
						bolus.put("bolusTotalFeed", babyFeed.getBolusTotalFeed());
						pastBolusList.add(bolus);
					}
				}

				//calculate whether additional PN is going or not
				if(babyFeed.getStopAdditionalPN() != null && babyFeed.getStopAdditionalPN() == true) {
					additionalPNStatus = "Stopped";
				}
				if(BasicUtils.isEmpty(additionalPNStatus)) {
					if(!BasicUtils.isEmpty(babyFeed.getAdditionalRate())) {
						additionalPNStatus = "Stopped";
						tpnObj.setAdditionalPNStatus(additionalPNStatus);
					}
				}

			}

			currentBabyFeed.setPastBolusObject(pastBolusList);

			tpnObj.setBabyFeedList(babyFeedList);


			if(!BasicUtils.isEmpty(babyFeedList)) {
				String nutritionCalculator = "select obj from RefNutritioncalculator obj";
				List<RefNutritioncalculator> nutritionList = notesDoa.getListFromMappedObjNativeQuery(nutritionCalculator);

				List<FeedCalculatorPOJO> calculatorList = new ArrayList<FeedCalculatorPOJO>();
				for(BabyfeedDetail feed : babyFeedList) {
					FeedCalculatorPOJO feedCalculator = new FeedCalculatorPOJO();
					feedCalculator.setRefNutritionInfo(nutritionList);
					CaclulatorDeficitPOJO cuurentDeficitLast = getDeficitFeedCalculator(feed, nutritionList,
							feed.getWorkingWeight().toString());
					feedCalculator.setLastDeficitCal(cuurentDeficitLast);
					calculatorList.add(feedCalculator);
				}
				tpnObj.setCalculatorList(calculatorList);
			}

			if (!BasicUtils.isEmpty(babyPrescriptionList)) {
				for (int i = 0; i < babyPrescriptionList.size(); i++) {
					if (!BasicUtils.isEmpty(babyPrescriptionList.get(i).getFrequency())) {
						List<String> frequencyObj = BasicConstants.medicineFrequency
								.get(babyPrescriptionList.get(i).getFrequency());
						if (!BasicUtils.isEmpty(frequencyObj)) {
							if (!BasicUtils.isEmpty(frequencyObj.get(0))) {
								babyPrescriptionList.get(i).setFrequency(frequencyObj.get(0));
							}
							if (!BasicUtils.isEmpty(frequencyObj.get(1))) {
								babyPrescriptionList.get(i).setFrequencyInt(frequencyObj.get(1));
							}
						}
					}
				}
			}

			// past oral feed voluems....
			String oralFeed = "select obj from OralfeedDetail obj where uhid='" + uhid + "' order by creationtime desc";
			List<OralfeedDetail> oralFeedList = notesDoa.getListFromMappedObjNativeQuery(oralFeed);
			if (!BasicUtils.isEmpty(oralFeedList)) {
				tpnObj.setPastOralFeedList(oralFeedList);
			}

			// past EN feed details....
			String enFeedDetails = "select obj from EnFeedDetail obj where uhid='" + uhid + "' order by creationtime desc";
			List<EnFeedDetail> enFeedDetailList = notesDoa.getListFromMappedObjNativeQuery(enFeedDetails);
			if (!BasicUtils.isEmpty(enFeedDetailList)) {
				tpnObj.setPastEnFeedDetailList(enFeedDetailList);
			}

			tpnObj.setBabyPrescriptionList(babyPrescriptionList);
			tpnObj.setBabyCentralLineList(centralLineList);
			/* currentBabyFeed.setIsNormalTpn(true); */
			boolean isEntryAvailable = false;
			Float weightForCalOriginal = (float)-1;
			if(!BasicUtils.isEmpty(babyFeedList)) {
				currentBabyFeed = (BabyfeedDetail) babyFeedList.get(0);
				if(!BasicUtils.isEmpty(babyFeedList.get(0).getWorkingWeight())) {
					isEntryAvailable = true;
					weightForCalOriginal = babyFeedList.get(0).getWorkingWeight();	
				}
			}
			Float weightForCal = currentFeedDetails.getBabyFeed().getWorkingWeight();
			currentFeedDetails.setBabyFeed(currentBabyFeed);
			currentFeedDetails.getBabyFeed().setWorkingWeight(weightForCal);
			tpnObj.setCurrentFeedInfo(currentFeedDetails);
			tpnObj.setBabyPrescEmptyObj(new BabyPrescription());
			
			if(isEntryAvailable) {
				tpnObj.getBabyFeedList().get(0).setWorkingWeight(weightForCalOriginal);
			}

			
			String babyDetailListQuery = "select obj from BabyDetail as obj where uhid='" + uhid + "'";
			List<BabyDetail> babyDetailList = notesDoa.getListFromMappedObjNativeQuery(babyDetailListQuery);
			if(babyDetailList != null && babyDetailList.size() > 0) {
				String branchName = babyDetailList.get(0).getBranchname();
				if(branchName != null && branchName != "") {
					String refHospitalbranchnameQuery = "select obj from RefHospitalbranchname as obj where branchname='" + branchName + "'";
					List<RefHospitalbranchname> refHospitalbranchnameList = inicuDoa.getListFromMappedObjQuery(refHospitalbranchnameQuery);
					if(refHospitalbranchnameList != null && refHospitalbranchnameList.size() > 0) {
						if(!BasicUtils.isEmpty(refHospitalbranchnameList.get(0).getNutritionalType())){
							tpnObj.getCurrentFeedInfo().getBabyFeed().setNutritionalType(refHospitalbranchnameList.get(0).getNutritionalType());
						}
					}
				}
			}
			

		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUser,
					uhid, "GET_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		return tpnObj;

	}

	public CaclulatorDeficitPOJO getDeficitFeedCalculator(BabyfeedDetail feed,
			List<RefNutritioncalculator> nutritionList, String currentWeight) {

		CaclulatorDeficitPOJO calculator = new CaclulatorDeficitPOJO();
		String oralFeed = "select obj from OralfeedDetail obj where babyfeedid='" + feed.getBabyfeedid()
				+ "' order by creationtime desc";
		List<OralfeedDetail> oralFeedList = notesDoa.getListFromMappedObjNativeQuery(oralFeed);

		String babyDetail = "Select obj from BabyDetail obj where uhid = '" + feed.getUhid() + "' order by creationtime";
		List<BabyDetail> babyDetailList = notesDoa.getListFromMappedObjNativeQuery(babyDetail);

		String nursingIntakeOutput = "select obj from NursingIntakeOutput obj where babyfeedid='" + feed.getBabyfeedid()
		+ "' order by creationtime desc";
		List<NursingIntakeOutput> nursingIntakeOutputList = notesDoa.getListFromMappedObjNativeQuery(nursingIntakeOutput);

		if (!BasicUtils.isEmpty(oralFeedList)) {
			HashMap<String, Float> enteral = new HashMap<String, Float>();
			for (OralfeedDetail oral : oralFeedList) {
				for (RefNutritioncalculator nutrition : nutritionList) {

					if (oral.getFeedtypeId().equalsIgnoreCase(nutrition.getFeedtypeId())) {

						if (oral.getTotalFeedVolume() != null) {
							if (enteral.get(BasicConstants.ENERGY) != null) {
								enteral.put(BasicConstants.ENERGY, enteral.get(BasicConstants.ENERGY)
										+ ((oral.getTotalFeedVolume() * nutrition.getEnergy()) / 100));
							} else {
								enteral.put(BasicConstants.ENERGY,
										oral.getTotalFeedVolume() * nutrition.getEnergy() / 100);
							}

							if (enteral.get(BasicConstants.PROTEIN) != null) {
								enteral.put(BasicConstants.PROTEIN, enteral.get(BasicConstants.PROTEIN)
										+ ((oral.getTotalFeedVolume() * nutrition.getProtein()) / 100));
							} else {
								enteral.put(BasicConstants.PROTEIN,
										oral.getTotalFeedVolume() * nutrition.getProtein() / 100);
							}

							if (enteral.get(BasicConstants.FAT) != null) {
								enteral.put(BasicConstants.FAT, enteral.get(BasicConstants.FAT)
										+ ((oral.getTotalFeedVolume() * nutrition.getFat()) / 100));
							} else {
								enteral.put(BasicConstants.FAT, oral.getTotalFeedVolume() * nutrition.getFat() / 100);
							}
							if (enteral.get(BasicConstants.VITAMINa) != null) {
								enteral.put(BasicConstants.VITAMINa, enteral.get(BasicConstants.VITAMINa)
										+ ((oral.getTotalFeedVolume() * nutrition.getVitamina()) / 100));
							} else {
								enteral.put(BasicConstants.VITAMINa,
										oral.getTotalFeedVolume() * nutrition.getVitamina() / 100);
							}
							if (enteral.get(BasicConstants.VITAMINd) != null) {
								enteral.put(BasicConstants.VITAMINd, enteral.get(BasicConstants.VITAMINd)
										+ ((oral.getTotalFeedVolume() * nutrition.getVitamind()) / 100));
							} else {
								enteral.put(BasicConstants.VITAMINd,
										oral.getTotalFeedVolume() * nutrition.getVitamind() / 100);
							}
							if (enteral.get(BasicConstants.CALCIUM) != null) {
								enteral.put(BasicConstants.CALCIUM, enteral.get(BasicConstants.CALCIUM)
										+ ((oral.getTotalFeedVolume() * nutrition.getCalcium()) / 100));
							} else {
								enteral.put(BasicConstants.CALCIUM,
										oral.getTotalFeedVolume() * nutrition.getCalcium() / 100);
							}
							if (enteral.get(BasicConstants.PHOSPHORUS) != null) {
								enteral.put(BasicConstants.PHOSPHORUS, enteral.get(BasicConstants.PHOSPHORUS)
										+ ((oral.getTotalFeedVolume() * nutrition.getPhosphorus()) / 100));
							} else {
								enteral.put(BasicConstants.PHOSPHORUS,
										oral.getTotalFeedVolume() * nutrition.getPhosphorus() / 100);
							}
							if (enteral.get(BasicConstants.IRON) != null) {
								enteral.put(BasicConstants.IRON, enteral.get(BasicConstants.IRON)
										+ ((oral.getTotalFeedVolume() * nutrition.getIron()) / 100));
							} else {
								enteral.put(BasicConstants.IRON,
										oral.getTotalFeedVolume() * nutrition.getIron() / 100);
							}
							if (enteral.get(BasicConstants.CARBOHYDRATES) != null && (!BasicUtils.isEmpty(nutrition.getCarbohydrates()))) {
								enteral.put(BasicConstants.CARBOHYDRATES, enteral.get(BasicConstants.CARBOHYDRATES)
										+ ((oral.getTotalFeedVolume() * nutrition.getCarbohydrates()) / 100));
							} else if((!BasicUtils.isEmpty(nutrition.getCarbohydrates()))){
								enteral.put(BasicConstants.CARBOHYDRATES,
										oral.getTotalFeedVolume() * nutrition.getCarbohydrates() / 100);
							}

						}
					}
				}
			}

			if (!BasicUtils.isEmpty(nursingIntakeOutputList)) {
 				for (NursingIntakeOutput addtive : nursingIntakeOutputList) {
 					
 					for (RefNutritioncalculator nutrition : nutritionList) {
 						
 						if(!BasicUtils.isEmpty(addtive.getCalciumVolume())) {
 							
 							if(nutrition.getFeedtypeId().equalsIgnoreCase("TYPE20") && !BasicUtils.isEmpty(feed.getCalBrand()) && feed.getCalBrand().equalsIgnoreCase("EN001")){
 					              
					              if (enteral.get(BasicConstants.VITAMINd) != null) {
					                enteral.put(BasicConstants.VITAMINd, enteral.get(BasicConstants.VITAMINd)
					                    +  ((addtive.getCalciumVolume() * nutrition.getVitamind())));
					              } else {
					                enteral.put(BasicConstants.VITAMINd,
					                		addtive.getCalciumVolume() * nutrition.getVitamind());
					              }
					              if (enteral.get(BasicConstants.CALCIUM) != null) {
					                enteral.put(BasicConstants.CALCIUM, enteral.get(BasicConstants.CALCIUM)
					                    +  ((addtive.getCalciumVolume() * nutrition.getCalcium())));
					              } else {
					                enteral.put(BasicConstants.CALCIUM,
					                		addtive.getCalciumVolume() * nutrition.getCalcium());
					              }
					              if (enteral.get(BasicConstants.PHOSPHORUS) != null) {
					                enteral.put(BasicConstants.PHOSPHORUS, enteral.get(BasicConstants.PHOSPHORUS)
					                    +  ((addtive.getCalciumVolume() * nutrition.getPhosphorus())));
					              } else {
					                enteral.put(BasicConstants.PHOSPHORUS,
					                		addtive.getCalciumVolume() * nutrition.getPhosphorus());
					              }  
 					         }
 							
 							if(nutrition.getFeedtypeId().equalsIgnoreCase("TYPE28") && !BasicUtils.isEmpty(feed.getCalBrand()) && feed.getCalBrand().equalsIgnoreCase("EN006")){
					              
					              if (enteral.get(BasicConstants.VITAMINd) != null) {
					                enteral.put(BasicConstants.VITAMINd, enteral.get(BasicConstants.VITAMINd)
					                    +  ((addtive.getCalciumVolume() * nutrition.getVitamind())));
					              } else {
					                enteral.put(BasicConstants.VITAMINd,
					                		addtive.getCalciumVolume() * nutrition.getVitamind());
					              }
					              if (enteral.get(BasicConstants.CALCIUM) != null) {
					                enteral.put(BasicConstants.CALCIUM, enteral.get(BasicConstants.CALCIUM)
					                    +  ((addtive.getCalciumVolume() * nutrition.getCalcium())));
					              } else {
					                enteral.put(BasicConstants.CALCIUM,
					                		addtive.getCalciumVolume() * nutrition.getCalcium());
					              }
					              if (enteral.get(BasicConstants.PHOSPHORUS) != null) {
					                enteral.put(BasicConstants.PHOSPHORUS, enteral.get(BasicConstants.PHOSPHORUS)
					                    +  ((addtive.getCalciumVolume() * nutrition.getPhosphorus())));
					              } else {
					                enteral.put(BasicConstants.PHOSPHORUS,
					                		addtive.getCalciumVolume() * nutrition.getPhosphorus());
					              }  
					         }
 						}
 						
 						if(!BasicUtils.isEmpty(addtive.getVitamindVolume())) {
 							
 							if(nutrition.getFeedtypeId().equalsIgnoreCase("TYPE32") && !BasicUtils.isEmpty(feed.getVitaminDBrand()) && feed.getVitaminDBrand().equalsIgnoreCase("EN016")){
 					              
					              if (enteral.get(BasicConstants.VITAMINd) != null) {
					                enteral.put(BasicConstants.VITAMINd, enteral.get(BasicConstants.VITAMINd)
					                    +  ((addtive.getVitamindVolume() * nutrition.getVitamind())));
					              } else {
					                enteral.put(BasicConstants.VITAMINd,
					                		addtive.getVitamindVolume() * nutrition.getVitamind());
					              }
					             
 					         }
 							
 							if(nutrition.getFeedtypeId().equalsIgnoreCase("TYPE19") && !BasicUtils.isEmpty(feed.getVitaminDBrand()) && (feed.getVitaminDBrand().equalsIgnoreCase("EN004") || 
 									feed.getVitaminDBrand().equalsIgnoreCase("EN005") || feed.getVitaminDBrand().equalsIgnoreCase("EN007") || feed.getVitaminDBrand().equalsIgnoreCase("EN013") || feed.getVitaminDBrand().equalsIgnoreCase("EN015"))){
					              
					              if (enteral.get(BasicConstants.VITAMINd) != null) {
					                enteral.put(BasicConstants.VITAMINd, enteral.get(BasicConstants.VITAMINd)
					                    +  ((addtive.getVitamindVolume() * nutrition.getVitamind())));
					              } else {
					                enteral.put(BasicConstants.VITAMINd,
					                		addtive.getVitamindVolume() * nutrition.getVitamind());
					              }
					         }
 						}
 						
 						if(!BasicUtils.isEmpty(addtive.getIronVolume())) {
 							
 							if(nutrition.getFeedtypeId().equalsIgnoreCase("TYPE09") && !BasicUtils.isEmpty(feed.getIronBrand()) && feed.getIronBrand().equalsIgnoreCase("EN002")){
 					              
 								if (enteral.get(BasicConstants.IRON) != null) {
 	 								enteral.put(BasicConstants.IRON, enteral.get(BasicConstants.IRON)
 	 										 + ((addtive.getIronVolume() * nutrition.getIron())));
 	 							} else {
 	 								enteral.put(BasicConstants.IRON,
 	 										addtive.getIronVolume() * nutrition.getIron());
 	 							} 
 					         }
 							
 							if(nutrition.getFeedtypeId().equalsIgnoreCase("TYPE29") && !BasicUtils.isEmpty(feed.getIronBrand()) && feed.getIronBrand().equalsIgnoreCase("EN008")){
					              
 								if (enteral.get(BasicConstants.IRON) != null) {
 	 								enteral.put(BasicConstants.IRON, enteral.get(BasicConstants.IRON)
 	 										 + ((addtive.getIronVolume() * nutrition.getIron())));
 	 							} else {
 	 								enteral.put(BasicConstants.IRON,
 	 										addtive.getIronVolume() * nutrition.getIron());
 	 							} 
					         }
 							
 							if(nutrition.getFeedtypeId().equalsIgnoreCase("TYPE30") && !BasicUtils.isEmpty(feed.getIronBrand()) && feed.getIronBrand().equalsIgnoreCase("EN012")){
					              
 								if (enteral.get(BasicConstants.IRON) != null) {
 	 								enteral.put(BasicConstants.IRON, enteral.get(BasicConstants.IRON)
 	 										 + ((addtive.getIronVolume() * nutrition.getIron())));
 	 							} else {
 	 								enteral.put(BasicConstants.IRON,
 	 										addtive.getIronVolume() * nutrition.getIron());
 	 							} 
					         }
 							
 							if(nutrition.getFeedtypeId().equalsIgnoreCase("TYPE31") && !BasicUtils.isEmpty(feed.getIronBrand()) && feed.getIronBrand().equalsIgnoreCase("EN014")){
					              
 								if (enteral.get(BasicConstants.IRON) != null) {
 	 								enteral.put(BasicConstants.IRON, enteral.get(BasicConstants.IRON)
 	 										 + ((addtive.getIronVolume() * nutrition.getIron())));
 	 							} else {
 	 								enteral.put(BasicConstants.IRON,
 	 										addtive.getIronVolume() * nutrition.getIron());
 	 							}
					         }
 						}
 						
 						if(!BasicUtils.isEmpty(addtive.getMvVolume())) {
 							
 							if(nutrition.getFeedtypeId().equalsIgnoreCase("TYPE33") && !BasicUtils.isEmpty(feed.getMultiVitaminBrand()) && feed.getMultiVitaminBrand().equalsIgnoreCase("EN017")){
 					              
 								if (enteral.get(BasicConstants.VITAMINa) != null) {
 	 								enteral.put(BasicConstants.VITAMINa, enteral.get(BasicConstants.VITAMINa)
 	 										+  ((addtive.getMvVolume() * nutrition.getVitamina())));
 	 							} else {
 	 								enteral.put(BasicConstants.VITAMINa,
 	 										addtive.getMvVolume() * nutrition.getVitamina());
 	 							}
 								 
					              if (enteral.get(BasicConstants.VITAMINd) != null) {
					                enteral.put(BasicConstants.VITAMINd, enteral.get(BasicConstants.VITAMINd)
					                    +  ((addtive.getMvVolume() * nutrition.getVitamind())));
					              } else {
					                enteral.put(BasicConstants.VITAMINd,
					                		addtive.getMvVolume() * nutrition.getVitamind());
					              }
					              
					              if (enteral.get(BasicConstants.ENERGY) != null) {
						                enteral.put(BasicConstants.ENERGY, enteral.get(BasicConstants.ENERGY)
						                    +  ((addtive.getMvVolume() * nutrition.getEnergy())));
						              } else {
						                enteral.put(BasicConstants.ENERGY,
						                		addtive.getMvVolume() * nutrition.getEnergy());
						              }
					             
 					         }
 						}
 					}
 				}
 			}

			calculator.setEnteralIntake(enteral);
		}

		if (!BasicUtils.isEmpty(babyDetailList)) {
			HashMap<String, Float> eshphaganIntake = new HashMap<String, Float>();

					if(babyDetailList.get(0).getBirthweight() < 1000) {

						eshphaganIntake.put("Energy", (float) 130);
						eshphaganIntake.put("Protein", (float) 3.8);
						eshphaganIntake.put("Fat",(float) 8);
						eshphaganIntake.put("Vitamina", (float)700);
						eshphaganIntake.put("Vitamind", (float)800);
						eshphaganIntake.put("Calcium", (float)2.5);
						eshphaganIntake.put("Phosphorus", (float)90);
						eshphaganIntake.put("Iron", (float)35.8);



						}

					else
					{
						eshphaganIntake.put("Energy", (float) 110);
						eshphaganIntake.put("Protein", (float) 3.4);
						eshphaganIntake.put("Fat",(float) 8);
						eshphaganIntake.put("Vitamina", (float)700);
						eshphaganIntake.put("Vitamind", (float)800);
						eshphaganIntake.put("Calcium", (float)2.5);
						eshphaganIntake.put("Phosphorus", (float)90);
						eshphaganIntake.put("Iron", (float)35.8);
					}




			calculator.setEshphaganIntake(eshphaganIntake);
		}

		if (!BasicUtils.isEmpty(babyDetailList)) {
			HashMap<String, Float> eshphaganParenteralIntake = new HashMap<String, Float>();

					if(babyDetailList.get(0).getBirthweight() < 1000) { //ELBW

						eshphaganParenteralIntake.put("Energy", (float) 75);
						eshphaganParenteralIntake.put("Protein", (float) 3.5);
						eshphaganParenteralIntake.put("Fat",(float) 4);
						eshphaganParenteralIntake.put("Vitamina", (float)700);
						eshphaganParenteralIntake.put("Vitamind", (float)40);
						eshphaganParenteralIntake.put("Calcium", (float)1.5);
						eshphaganParenteralIntake.put("Phosphorus", (float)80);
						eshphaganParenteralIntake.put("Iron", (float)0);



						}

					else //VLBW
					{
						eshphaganParenteralIntake.put("Energy", (float) 60);
						eshphaganParenteralIntake.put("Protein", (float) 3.5);
						eshphaganParenteralIntake.put("Fat",(float) 4);
						eshphaganParenteralIntake.put("Vitamina", (float)700);
						eshphaganParenteralIntake.put("Vitamind", (float)40);
						eshphaganParenteralIntake.put("Calcium", (float)1.5);
						eshphaganParenteralIntake.put("Phosphorus", (float)80);
						eshphaganParenteralIntake.put("Iron", (float)0);
					}




			calculator.setEshphaganParenteralIntake(eshphaganParenteralIntake);
		}

		// parental....
		HashMap<String, Float> parental = new HashMap<String, Float>();
		BabyfeedDetail FeedParental = feed;

		if (currentWeight != null && !currentWeight.isEmpty()) {
			Float workingWeight = Float.valueOf(currentWeight);
			Float energyParenteral = null;
			if (FeedParental.getAminoacidConc() != null) {
				energyParenteral = FeedParental.getAminoacidConc() * 4 * workingWeight;
				calculator.setAminoEnergy(energyParenteral);
			}
			if (FeedParental.getAminoacidConc() != null) {
				parental.put(BasicConstants.PROTEIN, FeedParental.getAminoacidConc() * workingWeight);
			}
			if (FeedParental.getLipidConc() != null) {
				parental.put(BasicConstants.FAT, FeedParental.getLipidConc() * workingWeight);
				if (energyParenteral != null) {
					energyParenteral = energyParenteral + FeedParental.getLipidConc() * 10 * workingWeight;
				}
				else {
					energyParenteral = FeedParental.getLipidConc() * 10 * workingWeight;
				}
				calculator.setLipidEnergy(FeedParental.getLipidConc() * 10 * workingWeight);
			}

			if (FeedParental.getGirvalue() != null && !FeedParental.getGirvalue().trim().isEmpty()) {
				Float gir = Float.valueOf(FeedParental.getGirvalue());
				if (energyParenteral != null)
					energyParenteral = Float.valueOf(energyParenteral + gir * 4.9 * workingWeight + "");
				else
					energyParenteral = Float.valueOf(gir * 4.9 * workingWeight + "");
				calculator.setGirEnergy((float) (gir * 4.9 * workingWeight));
			}
			parental.put(BasicConstants.ENERGY, energyParenteral);

			if (FeedParental.getCalciumVolume() != null && FeedParental.getCalciumVolume() != 0) {
				parental.put(BasicConstants.CALCIUM, FeedParental.getCalciumVolume() * 9 * workingWeight);
			}

			calculator.setParentalIntake(parental);
		}
//		parental.put(BasicConstants.PROTEIN, (float) 0);
//		parental.put(BasicConstants.ENERGY, (float) 0);
//		parental.put(BasicConstants.FAT, (float) 0);
//
//
//		if (!BasicUtils.isEmpty(nursingIntakeOutputList) && currentWeight != null && !currentWeight.isEmpty()) {
//			Float aminoFinalConc = (float) 0;
//			Float lipidFinalConc = (float) 0;
//			Float aminoTotal = (float) 0;
//			Float pnTotal = (float) 0;
//			if(!BasicUtils.isEmpty(feed.getAminoacidTotal())) {
//				pnTotal += feed.getAminoacidTotal();
//				aminoTotal = feed.getAminoacidTotal();
//			}
//			if(!BasicUtils.isEmpty(feed.getDextroseVolumemlperday())) {
//				pnTotal += feed.getDextroseVolumemlperday();
//			}
//			if(!BasicUtils.isEmpty(feed.getTotalparenteralAdditivevolume())) {
//				pnTotal += feed.getTotalparenteralAdditivevolume();
//			}
//			Float pnDeliveredTotal = (float) 0;
//			for (NursingIntakeOutput parenteral : nursingIntakeOutputList) {
//				if(!BasicUtils.isEmpty(parenteral.getTpn_delivered()))
//					pnDeliveredTotal += parenteral.getTpn_delivered();
//				if(!BasicUtils.isEmpty(parenteral.getLipid_delivered())) {
//					lipidFinalConc += parenteral.getLipid_delivered();
//					pnDeliveredTotal += parenteral.getLipid_delivered();
//					lipidFinalConc = lipidFinalConc / 5;
//				}
//			}
//			if(pnDeliveredTotal != 0.0 && pnTotal != 0.0 && aminoTotal != 0.0) {
//				aminoFinalConc = (aminoTotal / pnTotal) * pnDeliveredTotal;
//				aminoFinalConc = aminoFinalConc / 10;
//			}
//			Float workingWeight = Float.valueOf(currentWeight);
//			Float energyParenteral = null;
//			if (aminoFinalConc != 0.0) {
//				energyParenteral = (aminoFinalConc) * 4;
//			}
//			if (aminoFinalConc != 0.0)
//				parental.put(BasicConstants.PROTEIN, aminoFinalConc);
//			if (lipidFinalConc != 0.0) {
//				parental.put(BasicConstants.FAT, lipidFinalConc);
//				if (energyParenteral != null)
//					energyParenteral = energyParenteral + lipidFinalConc * 10;
//				else
//					energyParenteral = lipidFinalConc * 10;
//			}
//
//			if (FeedParental.getGirvalue() != null && !FeedParental.getGirvalue().trim().isEmpty()) {
//				Float gir = Float.valueOf(FeedParental.getGirvalue());
//				if (energyParenteral != null)
//					energyParenteral = Float.valueOf(energyParenteral + gir * 4.9 * workingWeight + "");
//				else
//					energyParenteral = Float.valueOf(gir * 4.9 * workingWeight + "");
//			}
//			parental.put(BasicConstants.ENERGY, energyParenteral);
//			calculator.setParentalIntake(parental);
//
//		}

		return calculator;
	}

	@Override
	public ResponseMessageWithResponseObject saveTpnFeedDetails(TpnFeedPojo tpnFeedObj, String uhid,
			String loggedUser) {

		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
		BabyfeedDetail babyFeed = tpnFeedObj.getCurrentFeedInfo().getBabyFeed();
		//OralfeedDetail oralFeed = new OralfeedDetail();

		List<BabyPrescription> medicine = tpnFeedObj.getBabyPrescriptionList();
		BloodProduct blood = tpnFeedObj.getCurrentFeedInfo().getBabyBloodProduct();

		try {
			
			if(!BasicUtils.isEmpty(babyFeed.getSkipFeed()) && babyFeed.getSkipFeed()==true){
				NurseExecutionOrders order = new NurseExecutionOrders();
				order.setEventname("Feeding");
				order.setIsExecution(false);
				order.setOrderText("Skip one feed");
				order.setAssessmentdate(babyFeed.getEntryDateTime());
				order.setUhid(uhid);
				order.setLoggeduser(loggedUser);
				notesDoa.saveObject(order);	
			}

			babyFeed.setUhid(uhid);
			if (BasicUtils.isEmpty(babyFeed.getEpisodeid()) && !BasicUtils.isEmpty(babyFeed.getUhid())) {
				String queryAssociatedEvents = "select episodeid from babyfeed_detail where uhid='"
						+ babyFeed.getUhid() + "' order by creationtime desc";
				List<Object[]> listAssociatedEvents = notesDoa.getListFromNativeQuery(queryAssociatedEvents);

				if (!BasicUtils.isEmpty(listAssociatedEvents)) {
					babyFeed.setEpisodeid(String.valueOf(listAssociatedEvents.get(0)));
				}
			}
			/* tpnFeed.setUhid(uhid); */
			blood.setUhid(uhid);
			blood.setLoggeduser(loggedUser);
			babyFeed.setLoggeduser(loggedUser);
			if (!BasicUtils.isEmpty(tpnFeedObj.getCurrentFeedInfo().getCurrentWeight())
					&& BasicUtils.isEmpty(babyFeed.getWorkingWeight())) {
				babyFeed.setWorkingWeight(Float.valueOf(tpnFeedObj.getCurrentFeedInfo().getCurrentWeight()));
			}

			boolean isNewEntry = false;
			// setting all object id as null so every entry will be a new
			// entry....
			if (tpnFeedObj.getCurrentFeedInfo().getIsNewEntry()) {
				if(tpnFeedObj.getCurrentFeedInfo().getBabyFeed().getIsRevise()!=null) {
					isNewEntry = true;
					babyFeed.setBabyfeedid(null);
				}
				else {
					if(BasicUtils.isEmpty(tpnFeedObj.getCurrentFeedInfo().getBabyFeed().getAdditionalRate())) {
						isNewEntry = true;
						babyFeed.setBabyfeedid(null);
					}
				}
			}

			if(!BasicUtils.isEmpty(babyFeed.getCauseListOfNPO()))
			{
				babyFeed.setCauseofnpo(babyFeed.getCauseListOfNPO().toString());
				if(babyFeed.getCauseListOfNPO().contains("NPO004"))
				{
					babyFeed.setNpoDuetoFeedIntolerance(true);
				}
			}


			if(!BasicUtils.isEmpty(babyFeed.getStopPN()) && babyFeed.getStopPN()){
				String feedQuery = "select obj from BabyfeedDetail obj where uhid='" + uhid + "' order by entrydatetime desc";
				List<BabyfeedDetail> babyFeedList = notesDoa.getListFromMappedObjNativeQuery(feedQuery);
				if(!BasicUtils.isEmpty(babyFeedList) && (!BasicUtils.isEmpty(babyFeed.getStopAdditionalPN()) && babyFeed.getStopAdditionalPN() == false) || BasicUtils.isEmpty(babyFeed.getStopAdditionalPN())) {
					BabyfeedDetail babyfeedObj = babyFeedList.get(0);
					if(!BasicUtils.isEmpty(babyfeedObj.getAdditionalDuration()))
						babyFeed.setAdditionalDuration(babyfeedObj.getAdditionalDuration());
					if(!BasicUtils.isEmpty(babyfeedObj.getAdditionalKCL()))
						babyFeed.setAdditionalKCL(babyfeedObj.getAdditionalKCL());
					if(!BasicUtils.isEmpty(babyfeedObj.getAdditionalRate()))
						babyFeed.setAdditionalRate(babyfeedObj.getAdditionalRate());
						babyFeed.setAdditionalTotalVolume(babyfeedObj.getAdditionalTotalVolume());
					if(!BasicUtils.isEmpty(babyfeedObj.getAdditionalVolume()))
						babyFeed.setAdditionalVolume(babyfeedObj.getAdditionalVolume());
					if(!BasicUtils.isEmpty(babyfeedObj.getAdditionalPNType()))
						babyFeed.setAdditionalPNType(babyfeedObj.getAdditionalPNType());
				}
			}

			if (BasicUtils.isEmpty(babyFeed.getIsBolusGiven()) || !babyFeed.getIsBolusGiven()) {
				babyFeed.setBolusType(null);
				babyFeed.setBolusMethod(null);
				babyFeed.setBolusVolume(null);
				babyFeed.setBolusTotalFeed(null);
			} else {
				if (!BasicUtils.isEmpty(babyFeed.getBolusType()))
					babyFeed.setBolusMethod(babyFeed.getBolusType());
				if (!BasicUtils.isEmpty(babyFeed.getPastBolusTotalFeed()) && !BasicUtils.isEmpty(babyFeed.getBolusTotalFeed())) {
					babyFeed.setPastBolusTotalFeed(babyFeed.getPastBolusTotalFeed() + babyFeed.getBolusTotalFeed());
				} else if(!BasicUtils.isEmpty(babyFeed.getBolusTotalFeed())){
					babyFeed.setPastBolusTotalFeed(babyFeed.getBolusTotalFeed());
				}
			}

			if (!BasicUtils.isEmpty(babyFeed.getFluidTypeList()))
				babyFeed.setReadymadeFluidTypeList(babyFeed.getFluidTypeList().toString());

			if (!BasicUtils.isEmpty(babyFeed.getIsenternalgiven())) {

				if (!BasicUtils.isEmpty(babyFeed.getFeedTypeList()))
					babyFeed.setFeedtype(babyFeed.getFeedTypeList().toString());
				if (!BasicUtils.isEmpty(babyFeed.getFeedmethodList()))
					babyFeed.setFeedmethod(babyFeed.getFeedmethodList().toString());
				if (!BasicUtils.isEmpty(babyFeed.getLibBreastFeedList()))
					babyFeed.setLibBreastFeed(babyFeed.getLibBreastFeedList().toString());

				if (!BasicUtils.isEmpty(babyFeed.getFeedmethodList())
						&& babyFeed.getFeedmethodList().contains(BasicConstants.OTHERS)) {

					String feedTypeTemp = addNewFeedMethod(babyFeed.getFeedMethodOther());
					List<String> feedMethodList = babyFeed.getFeedmethodList();
					if (BasicUtils.isEmpty(feedMethodList))
						feedMethodList = new ArrayList<String>();
					feedMethodList.add(feedTypeTemp);
					babyFeed.setFeedmethod(feedMethodList.toString());
				}

				if (!BasicUtils.isEmpty(babyFeed.getFeedTypeSecondaryList())) {
					babyFeed.setFeedTypeSecondary(babyFeed.getFeedTypeSecondaryList().toString());
				}

				// before this creating text for the feed view in dashboard...
				String feedText = "";
				if (!BasicUtils.isEmpty(babyFeed.getTotalIntake()))
					feedText = feedText + "Total Feed: " + Math.ceil(Double.valueOf(babyFeed.getTotalIntake()));

				List<String> feedTypeList = babyFeed.getFeedTypeList();
				if (!BasicUtils.isEmpty(feedTypeList)) {
					List<KeyValueObj> masterType =
							getRefObj("select obj.feedtypeid,obj.feedtypename from ref_masterfeedtype obj");
					String feedTypeText = "";
					for (int j = 0; j < feedTypeList.size(); j++) {
						for (int i = 0; i < masterType.size(); i++) {
							if (feedTypeList.get(j).trim()
									.equalsIgnoreCase(masterType.get(i).getKey().toString().trim())) {
								if (feedTypeText.isEmpty()) {
									feedTypeText = masterType.get(i).getValue().toString();
								} else {
									feedTypeText = feedTypeText + ", " + masterType.get(i).getValue().toString();
								}
							}
						}
					}

					if (!feedTypeText.isEmpty()) {
						feedText = feedText + ", Feed Type: " + feedTypeText;
					}
				}

				List<String> feedMethodList = babyFeed.getFeedmethodList();
				if (!BasicUtils.isEmpty(feedMethodList)) {
					List<KeyValueObj> masterMethod =
							getRefObj("select obj.feedmethodid,obj.feedmethodname from ref_masterfeedmethod obj");
					String feedMethodText = "";
					for (int j = 0; j < feedMethodList.size(); j++) {
						for (int i = 0; i < masterMethod.size(); i++) {
							if (feedMethodList.get(j).trim()
									.equalsIgnoreCase(masterMethod.get(i).getKey().toString().trim())) {
								if (feedMethodText.isEmpty()) {
									feedMethodText = masterMethod.get(i).getValue().toString();
								} else {
									feedMethodText = feedMethodText + ", " + masterMethod.get(i).getValue().toString();
								}
							}
						}
					}

					if (!feedMethodText.isEmpty()) {
						feedText = feedText + ", Feed Method: " + feedMethodText;
					}
				}
				Float enteralFeedVolume = Float.valueOf("0");
				if (!BasicUtils.isEmpty(babyFeed.getTotalfeedMlDay())) {
					enteralFeedVolume = babyFeed.getTotalfeedMlDay();
				}

				if (!BasicUtils.isEmpty(babyFeed.getTotalenteraAdditivelvolume())) {
					enteralFeedVolume = enteralFeedVolume + babyFeed.getTotalenteraAdditivelvolume();
				}
				feedText = feedText + ", Total Enteral Volume: " + Math.ceil(enteralFeedVolume);

				Float totalParenteralVolume = new Float("0");
				if (!BasicUtils.isEmpty(babyFeed.getDextroseVolumemlperday())) {
					totalParenteralVolume = totalParenteralVolume + babyFeed.getDextroseVolumemlperday();
				}
				if (!BasicUtils.isEmpty(babyFeed.getTotalparenteralAdditivevolume())) {
					totalParenteralVolume = totalParenteralVolume + babyFeed.getTotalparenteralAdditivevolume();
				}
				if (!BasicUtils.isEmpty(babyFeed.getAminoacidTotal())) {
					totalParenteralVolume = totalParenteralVolume + babyFeed.getAminoacidTotal();
				}
				if (!BasicUtils.isEmpty(babyFeed.getLipidTotal())) {
					totalParenteralVolume = totalParenteralVolume + babyFeed.getLipidTotal();
				}
				if (!BasicUtils.isEmpty(babyFeed.getBolusTotalFeed())) {
					totalParenteralVolume = totalParenteralVolume + babyFeed.getBolusTotalFeed();
				}
				feedText = feedText + ", Total Parenteral Volume: " + Math.ceil(totalParenteralVolume);
				babyFeed.setFeedText(feedText);
				
				

			}
			BabyfeedDetail returnedBabyFeed = new BabyfeedDetail();
			babyFeed.setEntryDateTime(tpnFeedObj.getCurrentFeedInfo().getFeedGivenDateTime());
			returnedBabyFeed = (BabyfeedDetail) inicuDoa.saveObject(babyFeed);
//			oralFeed.setBabyfeedid(returnedBabyFeed.getBabyfeedid().toString());
//			oralFeed.setUhid(uhid);
			
			 //Increment Feeds^M
			if(!BasicUtils.isEmpty(babyFeed.getIncrementFeedGiven()) && babyFeed.getIncrementFeedGiven() && !BasicUtils.isEmpty(tpnFeedObj.getIncrementFeed())){
				List<IncrementFeed> incrementList = tpnFeedObj.getIncrementFeed();
				if(isNewEntry == false) {
					String query = "DELETE FROM increment_feed WHERE baby_feed_id = '" + returnedBabyFeed.getBabyfeedid() + "'";
					inicuDoa.updateOrDeleteNativeQuery(query);
				}
				for(IncrementFeed feed : incrementList) {
					feed.setBabyfeedId(returnedBabyFeed.getBabyfeedid() + "");
					feed.setLoggeduser(loggedUser);
					inicuDoa.saveObject(feed);
				}
			}

			blood.setBabyfeedid(returnedBabyFeed.getBabyfeedid() + "");
			blood.setBloodproductsid(null);
			if (!BasicUtils.isEmpty(blood.getIsBloodGiven()) && blood.getIsBloodGiven()) {
				blood.setCreationtime(tpnFeedObj.getCurrentFeedInfo().getFeedGivenDateTime());
				notesDoa.saveObject(blood);
			}

//			if (!BasicUtils.isEmpty(oralFeed.getBabyfeedid())) {
//				oralFeed.setCreationtime(tpnFeedObj.getCurrentFeedInfo().getFeedGivenDateTime());
//				oralFeed.setFeedtypeId(returnedBabyFeed.getFeedtype().replace("[", "").replace("]", ""));
//				oralFeed.setFeedvolume(returnedBabyFeed.getFeedvolume());
//				if(returnedBabyFeed.getFeedduration().equalsIgnoreCase("Continuous")) {
//					oralFeed.setTotalFeedVolume((oralFeed.getFeedvolume() * 24) / 1);
//				}
//				else{
//					oralFeed.setTotalFeedVolume((oralFeed.getFeedvolume() * 24) / Integer.parseInt(returnedBabyFeed.getFeedduration()));
//				}
//
//				notesDoa.saveObject(oralFeed);
//			}

			// saving oral feed table.....for volumes of different type....
//			if (!BasicUtils.isEmpty(tpnFeedObj.getOralFeedList())) {
//				Long babyFeedId = returnedBabyFeed.getBabyfeedid();
//				if (babyFeedId != null) {
//					for (OralfeedDetail oral : tpnFeedObj.getOralFeedList()) {
//						oral.setUhid(uhid);
//						oral.setBabyfeedid(babyFeedId.toString());
//						if(returnedBabyFeed.getFeedduration().equalsIgnoreCase("Continuous")) {
//							oral.setTotalFeedVolume((oral.getFeedvolume() * 24) / 1);
//						}
//						else{
//							oral.setTotalFeedVolume((oral.getFeedvolume() * 24) / Integer.parseInt(returnedBabyFeed.getFeedduration()));
//						}
//						oral.setCreationtime(tpnFeedObj.getCurrentFeedInfo().getFeedGivenDateTime());
//						inicuDoa.saveObject(oral);
//					}
//				}
//			}

			// saving EN feed details table for different feed sequences....
			if (!BasicUtils.isEmpty(tpnFeedObj.getEnFeedDetailList())) {
				Long babyFeedId = returnedBabyFeed.getBabyfeedid();
				if (babyFeedId != null) {
					List<EnFeedDetail> enFeedDetailList = tpnFeedObj.getEnFeedDetailList();
					for (EnFeedDetail enFeed : enFeedDetailList) {
						if (!(BasicUtils.isEmpty(enFeed.getFeed_volume())
								|| BasicUtils.isEmpty(enFeed.getNo_of_feed()))) {
							enFeed.setUhid(uhid);
							enFeed.setBabyfeedid(babyFeedId);
							if ((null == tpnFeedObj.getCurrentFeedInfo().getIsNewEntry()
									|| tpnFeedObj.getCurrentFeedInfo().getIsNewEntry()) && (isNewEntry)) {
								enFeed.setEn_feed_detail_id(null);
								enFeed.setCreationtime(null);
							}
							inicuDoa.saveObject(enFeed);
						}
					}
				}
			}

			////Added for fetching nutrition id of above saved object
			String fetchNutritionIdQuery = "SELECT obj from BabyfeedDetail as obj where uhid ='"+uhid+"' "
					+ " order by creationtime desc";
			List<BabyfeedDetail> listBabyfeedDetail = inicuDoa.getListFromMappedObjQuery(fetchNutritionIdQuery);
			Long nutritionId = null;
			if(!BasicUtils.isEmpty(listBabyfeedDetail)) {
				if(!BasicUtils.isEmpty(listBabyfeedDetail.get(0).getBabyfeedid()))
					nutritionId =listBabyfeedDetail.get(0).getBabyfeedid();


			}

			response.setReturnedObject(nutritionId);



			response.setType(BasicConstants.MESSAGE_SUCCESS);
			response.setMessage("feeds and fluids saved successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	private String addNewFeedMethod(String feedMethodOther) {
		String fetchId = "Select feedmethodid FROM ref_masterfeedmethod order by feedmethodid desc limit 1";
		List<String> resultId = inicuDoa.getListFromNativeQuery(fetchId);
		if (!BasicUtils.isEmpty(resultId)) {
			String id = resultId.get(0).toString();
			if (!BasicUtils.isEmpty(id)) {
				// tokenise the string to get the val.
				id = id.replace("METHOD", "");
				Integer intVal = Integer.parseInt(id);
				if (!BasicUtils.isEmpty(intVal)) {
					intVal = intVal + 1;
					String intValStr = String.format("%02d", intVal);
					String insertType = "insert into ref_masterfeedmethod " + "values('" + "METHOD" + intValStr + "','"
							+ feedMethodOther + "','" + feedMethodOther + "')";
					inicuDoa.executeInsertQuery(insertType);
					return "METHOD" + intValStr;
				}
			}
		}
		return null;
	}

	private String addNewFeedType(String feetTypeOther) {
		String fetchId = "Select feedtypeid FROM ref_masterfeedtype order by feedtypeid desc limit 1";
		List<String> resultId = inicuDoa.getListFromNativeQuery(fetchId);
		if (!BasicUtils.isEmpty(resultId)) {
			String id = resultId.get(0).toString();
			if (!BasicUtils.isEmpty(id)) {
				// tokenise the string to get the val.
				id = id.replace("TYPE", "");
				Integer intVal = Integer.parseInt(id);
				if (!BasicUtils.isEmpty(intVal)) {
					intVal = intVal + 1;
					String intValStr = String.format("%02d", intVal);
					String insertType = "insert into ref_masterfeedtype " + "values('" + "TYPE" + intValStr + "','"
							+ feetTypeOther + "','" + feetTypeOther + "')";
					inicuDoa.executeInsertQuery(insertType);
					return "TYPE" + intValStr;
					// saSepsis.setUrineOrganisms("ORG"+intValStr);
				}
			}
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public BabyHistoryObj getBabyHistory(String uhid, String loggedUser) {

		BabyHistoryObj babyHsitory = new BabyHistoryObj();
		try {
			java.util.Date date = new java.util.Date();
			java.sql.Date dateSql = new Date(date.getTime());
			String presentDateStr = CalendarUtility.getDateformatdb(CalendarUtility.SERVER_CRUD_OPERATION)
					.format(dateSql);
			// getting baby medication....
			String queryPrescription = "SELECT obj FROM BabyPrescription obj where uhid='" + uhid
					+ "' and startdate <= '" + presentDateStr + "' order by startdate desc";
			List prescriptionList = inicuDoa.getListFromMappedObjQuery(queryPrescription);

			// getting baby feeds..
			String feedQuery = "select obj from BabyfeedDetail obj where uhid='" + uhid + "' and entrydatetime<= '"
					+ presentDateStr + "' order by entrydatetime desc";
			List<BabyfeedDetail> babyFeedList = inicuDoa.getListFromMappedObjQuery(feedQuery);

			// getting Doctor notes..
			String doctorNotesquery = "select obj from VwDoctornotesFinal obj where uhid='" + uhid
					+ "' and creationdate <= '" + presentDateStr + "' order by creationtime desc";
			List<VwDoctornotesFinal> doctorNotesList = inicuDoa.getListFromMappedObjQuery(doctorNotesquery);

			if (!BasicUtils.isEmpty(prescriptionList))
				babyHsitory.setMedication(prescriptionList);

			if (!BasicUtils.isEmpty(babyFeedList))
				babyHsitory.setFeed(babyFeedList);

			if (!BasicUtils.isEmpty(doctorNotesList))
				babyHsitory.setDoctorNotes(doctorNotesList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return babyHsitory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public NursingDailyProgressPojo getNursingDailyProgressInfo(String entryDate, String dob, String doa, String uhid,
			String loggedInUser) {
		NursingDailyProgressPojo dailyProgessObj = new NursingDailyProgressPojo();

		java.util.Date presentDate = null;
		java.util.Date dobDate = null;
		java.util.Date doaDate = null;
		this.uhid = uhid;

		try {
			dobDate = CalendarUtility.getDateformatdb(CalendarUtility.CLIENT_CRUD_OPERATION)
					.parse(dob.substring(0, 10));

			doaDate = CalendarUtility.getDateformatdb(CalendarUtility.CLIENT_CRUD_OPERATION)
					.parse(doa.substring(0, 10));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		BabyVisitsObj babyVisit = getBabyVisits(entryDate, uhid);
		if (babyVisit.getBirthGestationweeks() == null) {
			babyVisit.setBirthGestationweeks("");
		}
		if (babyVisit.getBirthGestationdays() == null) {
			babyVisit.setBirthGestationdays("");
		}
		if (BasicUtils.isEmpty(babyVisit.getCorrectedGestationweeks())
				|| babyVisit.getCorrectedGestationweeks().toString().equalsIgnoreCase("null")) {
			babyVisit.setCorrectedGestationweeks("");
		}
		if (BasicUtils.isEmpty(babyVisit.getCorrectedGestationdays())
				|| babyVisit.getCorrectedGestationdays().toString().equalsIgnoreCase("null")) {
			babyVisit.setCorrectedGestationdays("");
		}
		Date dateSql = null;
		try {
			entryDate = entryDate.substring(0, 24);
			DateFormat readFormat = new SimpleDateFormat("EEE MMM dd yyyy hh:mm:ss");
			presentDate = readFormat.parse(entryDate);
			dateSql = new Date(presentDate.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		long diff = presentDate.getTime() - dobDate.getTime();
		long ageDays = (diff / (24 * 60 * 60 * 1000)) + 1;
		long ageMonth = ageDays / (30);
		if (ageMonth > 0) {
			dailyProgessObj.setCurrentage(ageMonth + "months " + (ageDays - (ageMonth * 30)) + "days");
		} else {
			dailyProgessObj.setCurrentage(ageDays + "days");
		}

		long dayAfterAdmissionDiff = presentDate.getTime() - doaDate.getTime();
		long dayAfterAdmissionDays = (dayAfterAdmissionDiff / (24 * 60 * 60 * 1000)) + 1;// days..
		long dayAfterAdmissionMonth = dayAfterAdmissionDays / (30);
		if (dayAfterAdmissionMonth > 0) {
			dailyProgessObj.setDayAfterAdmission(dayAfterAdmissionMonth + "months "
					+ (dayAfterAdmissionDays - (dayAfterAdmissionMonth * 30)) + "days");
		} else {
			dailyProgessObj.setDayAfterAdmission(dayAfterAdmissionDays + "days");
		}

		String dateStr = CalendarUtility.getDateformatdb(CalendarUtility.SERVER_CRUD_OPERATION).format(dateSql);

		String doctorNotesquery = "select obj from VwDoctornotesFinal obj where uhid='" + uhid
				+ "' and creationdate = '" + dateStr + "' order by creationtime desc";
		List<VwDoctornotesFinal> doctorNotesList = inicuDoa.getListFromMappedObjQuery(doctorNotesquery);

		if (!BasicUtils.isEmpty(doctorNotesList)) {
			dailyProgessObj.setDoctorNotes(doctorNotesList);
		}
		/* dailyProgessObj.setBabyVisit(babyVisit); */
		return dailyProgessObj;
	}

	@SuppressWarnings("unchecked")
	@Override
	public NursingOrderJaundiceJSON getJaundiceNursingOrder(String uhid) {
		NursingOrderJaundiceJSON returnObj = new NursingOrderJaundiceJSON();
		try {
			String investigationOrderQuery = "select obj from InvestigationOrdered obj where assesment_type='Jaundice' and uhid='"
					+ uhid + "' order by creationtime desc";
			List<InvestigationOrdered> investigationList = inicuDoa.getListFromMappedObjQuery(investigationOrderQuery);
			if (null == investigationList || investigationList.isEmpty()) {
				returnObj.setTodayInvestigationList(null);
				returnObj.setPastInvestigationList(null);
			} else {
				List<InvestigationOrdered> pendingInvestigationList = new ArrayList<>();

				Iterator<InvestigationOrdered> itr = investigationList.iterator();
				while (itr.hasNext()) {
					InvestigationOrdered obj = itr.next();
					if (obj.getSenttolab_time() == null) {
						pendingInvestigationList.add(obj);
					}
				}
				returnObj.setTodayInvestigationList(pendingInvestigationList);
				returnObj.setPastInvestigationList(investigationList);
			}

			String treatmentQuery = "select obj from NursingOrderJaundice obj where uhid='" + uhid
					+ "' order by creationtime desc";
			List<NursingOrderJaundice> treatmentList = inicuDoa.getListFromMappedObjQuery(treatmentQuery);
			if (null == treatmentList || treatmentList.isEmpty()) {
				returnObj.setRecentNursingOrderJaundiceObj(null);
				returnObj.setPastJaundiceOrderList(null);
			} else {
				returnObj.setRecentNursingOrderJaundiceObj(treatmentList.get(0));
				returnObj.setPastJaundiceOrderList(treatmentList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUser,
					uhid, "GET_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		return returnObj;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseMessageWithResponseObject saveJaundiceNursingOrder(String uhid,
			NursingOrderJaundiceJSON nursingOrderObj) {

		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
		NursingOrderJaundice orderObj = nursingOrderObj.getRecentNursingOrderJaundiceObj();
		List<InvestigationOrdered> todayInvestigationList = nursingOrderObj.getTodayInvestigationList();

		try {
			if (orderObj != null) {
				orderObj = (NursingOrderJaundice) inicuDoa.saveObject(orderObj);
			}

			if (!(null == todayInvestigationList || todayInvestigationList.isEmpty())) {
				todayInvestigationList = (List<InvestigationOrdered>) inicuDoa
						.saveMultipleObject(todayInvestigationList);
			}
			response.setType(BasicConstants.MESSAGE_SUCCESS);
			response.setMessage("Nursing Order Jaundice Saved Successfully..!!");
			response.setReturnedObject(getJaundiceNursingOrder(uhid));
		} catch (Exception e) {
			e.printStackTrace();
			response.setType(BasicConstants.MESSAGE_FAILURE);
			response.setMessage(e.getMessage());
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUser,
					uhid, "SAVE_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		return response;
	}

	@Override
	public NursingOrderRdJSON getRdNursingOrder(String uhid) {
		NursingOrderRdJSON returnObj = new NursingOrderRdJSON();
		try {
			String fecthRespSystemLastStatus = "select obj from SaRespsystem obj where uhid='" + uhid
					+ "' order by creationtime desc ";
			List<SaRespsystem> respSystemListStatus = inicuDoa.getListFromMappedObjQuery(fecthRespSystemLastStatus);

			String indvestigationOrderQuery = "select obj from InvestigationOrdered obj where assesment_type='Respiratory' and uhid='"
					+ uhid + "' order by creationtime desc";
			List<InvestigationOrdered> investigationList = inicuDoa.getListFromMappedObjQuery(indvestigationOrderQuery);
			if (null == investigationList || investigationList.isEmpty()) {
				returnObj.setTodayInvestigationList(null);
				returnObj.setPastInvestigationList(null);
			} else {
				List<InvestigationOrdered> pendingInvestigationList = new ArrayList<>();

				Iterator<InvestigationOrdered> itr = investigationList.iterator();
				while (itr.hasNext()) {
					InvestigationOrdered obj = itr.next();
					if (obj.getSenttolab_time() == null) {
						pendingInvestigationList.add(obj);
					}
				}
				returnObj.setTodayInvestigationList(pendingInvestigationList);
				returnObj.setPastInvestigationList(investigationList);
			}

			String treatmentQuery = "select obj from NursingorderRd obj where uhid='" + uhid
					+ "' order by creationtime desc";
			List<NursingorderRd> treatmentList = inicuDoa.getListFromMappedObjQuery(treatmentQuery);

			NursingorderRd nursingOrderRd = treatmentList.get(0);
			returnObj.setNursingOrderObj(nursingOrderRd);

			String medFrequency = "select obj from RefMedfrequency obj";
			List<RefMedfrequency> refFrequencyList = inicuDoa.getListFromMappedObjQuery(medFrequency);

			if (treatmentList != null && treatmentList.size() > 0) {

				String fecthRespSystem = "select obj from SaRespsystem obj where uhid='" + uhid
						+ "' and eventstatus='Yes' order by creationtime desc ";
				List<SaRespsystem> respSystemList = inicuDoa.getListFromMappedObjQuery(fecthRespSystem);
				if (respSystemList.get(0).getRespid() == Long.parseLong(nursingOrderRd.getRdsid())) {
					returnObj.setRecentSaRespsystemObj(respSystemList.get(0));
				}
				List<RdsMedicineJSON> rdsMedicineListRecent = new ArrayList<>();

				for (int i = 0; i < respSystemList.size(); i++) {
					Iterator<NursingorderRd> nursingOrderListIterator = treatmentList.iterator();
					while (nursingOrderListIterator.hasNext()) {
						NursingorderRd nursingOrderRdObj = nursingOrderListIterator.next();
						if (Long.parseLong(nursingOrderRdObj.getRdsid()) == respSystemList.get(i).getRespid()) {
							respSystemList.get(i).setNursingComments(nursingOrderRdObj.getNursingComments());
							break;
						}
					}

					String fecthRespSystemMedicine = "select obj from NursingorderRdsMedicine obj where uhid='" + uhid
							+ "' and rdsid='" + respSystemList.get(i).getRespid() + "' order by creationtime desc";
					List<NursingorderRdsMedicine> respSystemMedicineList = inicuDoa
							.getListFromMappedObjQuery(fecthRespSystemMedicine);
					if (respSystemMedicineList != null && respSystemMedicineList.size() > 0) {
						String medicineString = "";
						Iterator<NursingorderRdsMedicine> nursingOrderMedListIterator = respSystemMedicineList
								.iterator();
						while (nursingOrderMedListIterator.hasNext()) {
							NursingorderRdsMedicine nursingOrderMedObj = nursingOrderMedListIterator.next();
							if (Long.parseLong(nursingOrderMedObj.getRdsid()) == respSystemList.get(i).getRespid()) {
								String medFreqString = "";
								String fetchBabyPrescription = "Select obj from BabyPrescription obj where baby_presid='"
										+ nursingOrderMedObj.getBabyprescId() + "'";
								List<BabyPrescription> babyPrescriptionList = inicuDoa
										.getListFromMappedObjQuery(fetchBabyPrescription);
								if (babyPrescriptionList != null && babyPrescriptionList.size() > 0) {
									medicineString = medicineString + babyPrescriptionList.get(0).getMedicinename()
											+ ":";
									for (int j = 0; j < refFrequencyList.size(); j++) {
										if (refFrequencyList.get(j).getFreqid()
												.equalsIgnoreCase(babyPrescriptionList.get(0).getFrequency())) {
											medicineString = medicineString + refFrequencyList.get(j).getFreqvalue()
													+ ",";
											medFreqString = refFrequencyList.get(j).getFreqvalue();
											break;
										}
									}
								}

								if (i == 0 && (Long.parseLong(nursingOrderMedObj.getRdsid()) == respSystemList.get(0)
										.getRespid()) && nursingOrderMedObj.getMedicineactiontime() == null) {
									RdsMedicineJSON rdsMedicine = new RdsMedicineJSON();
									rdsMedicine.setNursingRdsMedicine(nursingOrderMedObj);
									rdsMedicine.setMedicineName(babyPrescriptionList.get(0).getMedicinename());
									rdsMedicine.setDose(babyPrescriptionList.get(0).getDose());
									rdsMedicine.setFrequency(medFreqString);
									rdsMedicine.setMedicationType(babyPrescriptionList.get(0).getMedicationtype());
									rdsMedicine.setRoute(babyPrescriptionList.get(0).getRoute());
									rdsMedicine.setCalculateDose(babyPrescriptionList.get(0).getCalculateddose());

									rdsMedicineListRecent.add(rdsMedicine);
								}
							}
						}
						medicineString = medicineString.substring(0, medicineString.length() - 1);
						respSystemList.get(i).setMedicineString(medicineString);
					}
				}

				if (respSystemListStatus.get(0).getEventstatus().equalsIgnoreCase("yes")) {
					returnObj.setRdsMedicineList(rdsMedicineListRecent);
				} else {
					returnObj.setRdsMedicineList(null);
				}
				// returnObj.setRdsMedicineList(rdsMedicineListRecent);
				returnObj.setPastSaRespsystemList(respSystemList);
			} else {
				returnObj.setNursingOrderObj(null);
				returnObj.setPastSaRespsystemList(null);
				returnObj.setRecentSaRespsystemObj(null);
			}

		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUser,
					uhid, "GET_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		return returnObj;
	}

	@Override
	public ResponseMessageWithResponseObject saveRdNursingOrder(String uhid, NursingOrderRdJSON nursingOrderRdObj) {
		// TODO Auto-generated method stub
		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
		List<InvestigationOrdered> todayInvestigationList = nursingOrderRdObj.getTodayInvestigationList();

		NursingorderRd nursingOrderObj = nursingOrderRdObj.getNursingOrderObj();

		List<RdsMedicineJSON> rdsMedicineList = nursingOrderRdObj.getRdsMedicineList();
		List<NursingorderRdsMedicine> nursingOrderRdsMedicineList = new ArrayList<>();
		try {
			if (!(null == todayInvestigationList || todayInvestigationList.isEmpty())) {
				todayInvestigationList = (List<InvestigationOrdered>) inicuDoa
						.saveMultipleObject(todayInvestigationList);
			}

			if (nursingOrderObj != null) {
				nursingOrderObj = (NursingorderRd) inicuDoa.saveObject(nursingOrderObj);
			}

			if (rdsMedicineList != null && rdsMedicineList.size() > 0) {
				Iterator<RdsMedicineJSON> rdsMedicineListIterator = rdsMedicineList.iterator();
				while (rdsMedicineListIterator.hasNext()) {
					RdsMedicineJSON rdsMedicineJson = rdsMedicineListIterator.next();
					nursingOrderRdsMedicineList.add(rdsMedicineJson.getNursingRdsMedicine());
				}

				if (!(null == nursingOrderRdsMedicineList || nursingOrderRdsMedicineList.isEmpty())) {
					nursingOrderRdsMedicineList = (List<NursingorderRdsMedicine>) inicuDoa
							.saveMultipleObject(nursingOrderRdsMedicineList);
				}
			}

			response.setType(BasicConstants.MESSAGE_SUCCESS);
			response.setMessage("Nursing Order Respiratory Distress Saved Successfully..!!");
			response.setReturnedObject(getRdNursingOrder(uhid));
		} catch (Exception e) {
			e.printStackTrace();
			response.setType(BasicConstants.MESSAGE_FAILURE);
			response.setMessage(e.getMessage());
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUser,
					uhid, "SAVE_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		return response;
	}

	@Override
	public NursingOrderApneaJSON getApneaNursingOrder(String uhid) {
		// TODO Auto-generated method stub
		NursingOrderApneaJSON returnObj = new NursingOrderApneaJSON();
		try {
			String fetchSaRespApneaLastStatus = "select obj from SaRespApnea obj where uhid='" + uhid
					+ "' order by creationtime desc";
			List<SaRespApnea> saRespApneaListStatus = inicuDoa.getListFromMappedObjQuery(fetchSaRespApneaLastStatus);

			String indvestigationOrderQuery = "select obj from InvestigationOrdered obj where assesment_type='Apnea' and uhid='"
					+ uhid + "' order by creationtime desc";
			List<InvestigationOrdered> investigationList = inicuDoa.getListFromMappedObjQuery(indvestigationOrderQuery);
			if (null == investigationList || investigationList.isEmpty()) {
				returnObj.setTodayInvestigationList(null);
				returnObj.setPastInvestigationList(null);
			} else {
				List<InvestigationOrdered> pendingInvestigationList = new ArrayList<>();

				Iterator<InvestigationOrdered> itr = investigationList.iterator();
				while (itr.hasNext()) {
					InvestigationOrdered obj = itr.next();
					if (obj.getSenttolab_time() == null) {
						pendingInvestigationList.add(obj);
					}
				}

				returnObj.setTodayInvestigationList(pendingInvestigationList);
				returnObj.setPastInvestigationList(investigationList);
			}

			String fetchApneaList = "select obj from NursingorderApnea obj where uhid='" + uhid
					+ "' order by creationtime desc";
			List<NursingorderApnea> apneaList = inicuDoa.getListFromMappedObjQuery(fetchApneaList);

			if (apneaList != null && apneaList.size() > 0) {
				NursingorderApnea lastApneaObj = apneaList.get(0);
				returnObj.setNursingOrderApnea(lastApneaObj);

				String fetchSaRespApnea = "select obj from SaRespApnea obj where uhid='" + uhid
						+ "' and eventstatus='Yes' order by creationtime desc";
				List<SaRespApnea> saRespApneaList = inicuDoa.getListFromMappedObjQuery(fetchSaRespApnea);
				if (saRespApneaList != null && saRespApneaList.size() > 0) {
					if (saRespApneaListStatus.get(0).getApneaid() == saRespApneaList.get(0).getApneaid()) {
						returnObj.setRecentSaRespApneaObj(saRespApneaList.get(0));
					}

					for (int i = 0; i < saRespApneaList.size(); i++) {
						Iterator<NursingorderApnea> apneaListIterator = apneaList.iterator();
						while (apneaListIterator.hasNext()) {
							NursingorderApnea nursingOrderApnea = apneaListIterator.next();
							if (Long.parseLong(nursingOrderApnea.getApneaid()) == saRespApneaList.get(i).getApneaid()) {
								saRespApneaList.get(i).setNursingComments(nursingOrderApnea.getNursingComments());
								break;
							}
						}
					}

					returnObj.setPastSaRespApneaList(saRespApneaList);
				}
			} else {
				returnObj.setNursingOrderApnea(null);
				returnObj.setRecentSaRespApneaObj(null);
				returnObj.setPastSaRespApneaList(null);
			}

		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUser,
					uhid, "GET_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		return returnObj;
	}

	@Override
	public ResponseMessageWithResponseObject saveApneaNursingOrder(String uhid, NursingOrderApneaJSON nursingOrderObj) {
		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
		List<InvestigationOrdered> todayInvestigationList = nursingOrderObj.getTodayInvestigationList();

		NursingorderApnea nursingOrderApnea = nursingOrderObj.getNursingOrderApnea();

		try {
			if (!(null == todayInvestigationList || todayInvestigationList.isEmpty())) {
				todayInvestigationList = (List<InvestigationOrdered>) inicuDoa
						.saveMultipleObject(todayInvestigationList);
			}

			if (nursingOrderApnea != null) {
				nursingOrderApnea = (NursingorderApnea) inicuDoa.saveObject(nursingOrderApnea);
			}

			response.setType(BasicConstants.MESSAGE_SUCCESS);
			response.setMessage("Nursing Order Apnea Saved Successfully..!!");
			response.setReturnedObject(getApneaNursingOrder(uhid));
		} catch (Exception e) {
			e.printStackTrace();
			response.setType(BasicConstants.MESSAGE_FAILURE);
			response.setMessage(e.getMessage());
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUser,
					uhid, "SAVE_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		return response;
	}

	@Override
	public ResponseMessageWithResponseObject saveNursingEpisode(NursingEpisode nursingEpisodeObj) {
		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
		try {
			if (nursingEpisodeObj != null) {

				// setting modification to creationtime which has been manually
				// entered by user
				System.out.println(nursingEpisodeObj.getCreationtime() + "creation time date");

				System.out.println(BasicConstants.CLIENT_TIME_ZONE);

				int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
						- TimeZone.getDefault().getRawOffset();
				System.out.println(TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset());
				System.out.println(TimeZone.getDefault().getRawOffset());

				System.out.println(offset + "offset");

				nursingEpisodeObj
						.setCreationtime((new Timestamp(nursingEpisodeObj.getCreationtime().getTime() + offset)));

				if (nursingEpisodeObj.getCreationtime() != null) {
					nursingEpisodeObj.setModificationtime(nursingEpisodeObj.getCreationtime());
				} else {

					nursingEpisodeObj.setCreationtime(new Timestamp(System.currentTimeMillis()));
					nursingEpisodeObj.setModificationtime(new Timestamp(System.currentTimeMillis()));
				}

				inicuDoa.saveObject(nursingEpisodeObj);
				
				if(!BasicUtils.isEmpty(nursingEpisodeObj.getEventsDone())) {
					String[] indices = nursingEpisodeObj.getEventsDone().split(",");
					for(int i=0;i<indices.length;i++) {
						String indexFinal = indices[i].trim();
						if(!BasicUtils.isEmpty(indexFinal)) {
							Integer index = Integer.parseInt(indices[i].trim());
							String apneaEventQuery = "select obj from ApneaEvent obj where apnea_event_id='" + index
									+ "'";
							List<ApneaEvent> apneaList = inicuDoa.getListFromMappedObjQuery(apneaEventQuery);
							if(!BasicUtils.isEmpty(apneaList)) {
								ApneaEvent event = apneaList.get(0);
								event.setMarked("1");
								inicuDoa.saveObject(event);
							}
						}
					}
				}
				
				response.setType(BasicConstants.MESSAGE_SUCCESS);
				response.setMessage("Saved Successfully..!!");
				response.setReturnedObject(nursingEpisodeObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setType(BasicConstants.MESSAGE_FAILURE);
			response.setMessage(e.getMessage());
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUser,
					nursingEpisodeObj.getUhid(), "SAVE_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		return response;
	}

	@Override
	public ResponseMessageWithResponseObject saveDeclinedApneaEvent(DeclinedApneaEvent declinedApneaEvent) {
		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
		try {
			if (declinedApneaEvent != null) {
				System.out.println(declinedApneaEvent.getCreationTime() + "creation time date");
				System.out.println(BasicConstants.CLIENT_TIME_ZONE);
				int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
						- TimeZone.getDefault().getRawOffset();
				System.out.println(TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset());
				System.out.println(TimeZone.getDefault().getRawOffset());
				System.out.println(offset + "offset");
				declinedApneaEvent.setCreationTime((new Timestamp(declinedApneaEvent.getCreationTime().getTime() + offset)));

				if (declinedApneaEvent.getCreationTime() == null) {
					declinedApneaEvent.setCreationTime(new Timestamp(System.currentTimeMillis()));
				}

				inicuDoa.saveObject(declinedApneaEvent);
				response.setType(BasicConstants.MESSAGE_SUCCESS);
				response.setMessage("Saved Successfully..!!");
				
				if(!BasicUtils.isEmpty(declinedApneaEvent.getEventsDone())) {
					String[] indices = declinedApneaEvent.getEventsDone().split(",");
					for(int i=0;i<indices.length;i++) {
						String indexFinal = indices[i].trim();
						if(!BasicUtils.isEmpty(indexFinal)) {
							Integer index = Integer.parseInt(indices[i].trim());
							String apneaEventQuery = "select obj from ApneaEvent obj where apnea_event_id='" + index
									+ "'";
							List<ApneaEvent> apneaList = inicuDoa.getListFromMappedObjQuery(apneaEventQuery);
							if(!BasicUtils.isEmpty(apneaList)) {
								ApneaEvent event = apneaList.get(0);
								event.setMarked("1");
								inicuDoa.saveObject(event);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setType(BasicConstants.MESSAGE_FAILURE);
			response.setMessage(e.getMessage());
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUser,
					declinedApneaEvent.getUhid(), "SAVE_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public AssessmentNursingOrderPOJO getAssessmentNursingOrder(String uhid) {
		AssessmentNursingOrderPOJO returnObj = new AssessmentNursingOrderPOJO();
		try {
			String investigationOrderQuery = "select obj from InvestigationOrdered obj where uhid='" + uhid
					+ "' order by investigationorder_time desc";
			List<InvestigationOrdered> investigationList = inicuDoa.getListFromMappedObjQuery(investigationOrderQuery);
			if (null == investigationList || investigationList.isEmpty()) {
				returnObj.setTodayInvestigationList(null);
				returnObj.setPastInvestigationList(null);
			} else {
				List<InvestigationOrdered> pendingInvestigationList = new ArrayList<>();
				Iterator<InvestigationOrdered> itr = investigationList.iterator();
				while (itr.hasNext()) {
					InvestigationOrdered obj = itr.next();
					if (obj.getSenttolab_time() == null) {
						pendingInvestigationList.add(obj);
					}
				}
				returnObj.setTodayInvestigationList(pendingInvestigationList);
				returnObj.setPastInvestigationList(investigationList);
			}

			String treatmentQuery = "select obj from NursingOrderAssesment obj where uhid='" + uhid
					+ "' order by creationtime desc";
			List<NursingOrderAssesment> nursingOrderList = inicuDoa.getListFromMappedObjQuery(treatmentQuery);
			if (null == nursingOrderList || nursingOrderList.isEmpty()) {
				returnObj.setPendingNursingOrderList(null);
				returnObj.setPastNursingOrderList(null);
			} else {
				List<NursingOrderAssesment> pendingNursingOrderList = new ArrayList<>();
				Iterator<NursingOrderAssesment> itr = nursingOrderList.iterator();
				while (itr.hasNext()) {
					NursingOrderAssesment obj = itr.next();
					if (obj.getActiontakenTime() == null) {
						pendingNursingOrderList.add(obj);
					}
				}
				returnObj.setPendingNursingOrderList(pendingNursingOrderList);
				returnObj.setPastNursingOrderList(nursingOrderList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUser,
					uhid, "GET_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		return returnObj;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseMessageWithResponseObject saveAssessmentNursingOrder(String uhid,
			AssessmentNursingOrderPOJO nursingOrderObj) {

		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
		List<NursingOrderAssesment> orderList = nursingOrderObj.getPendingNursingOrderList();
		List<InvestigationOrdered> todayInvestigationList = nursingOrderObj.getTodayInvestigationList();

		try {
			if (!(BasicUtils.isEmpty(orderList))) {
				String nursingComments = nursingOrderObj.getNursingComments();
				Iterator<NursingOrderAssesment> itr = orderList.iterator();
				while (itr.hasNext()) {
					NursingOrderAssesment obj = itr.next();
					if (obj.getActiontakenTime() != null) {
						obj.setNursingComments(nursingComments);
						obj = (NursingOrderAssesment) inicuDoa.saveObject(obj);
					}
				}
			}

			if (!(BasicUtils.isEmpty(todayInvestigationList))) {
				todayInvestigationList = (List<InvestigationOrdered>) inicuDoa
						.saveMultipleObject(todayInvestigationList);
			}
			response.setType(BasicConstants.MESSAGE_SUCCESS);
			response.setMessage("Saved Successfully !!");
			response.setReturnedObject(getAssessmentNursingOrder(uhid));
		} catch (Exception e) {
			e.printStackTrace();
			response.setType(BasicConstants.MESSAGE_FAILURE);
			response.setMessage(e.getMessage());
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUser,
					uhid, "SAVE_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public NursingInputsPOJO getNursingInputs(String uhid, String fromTime, String toTime) {

		int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
				- TimeZone.getDefault().getRawOffset();

	    DecimalFormat df2 = new DecimalFormat("#.##");

		NursingInputsPOJO returnObj = new NursingInputsPOJO();
		List<String> hours = getTime(BasicConstants.HOURS);
		returnObj.setHours(hours);
		List<String> minutes = getTime(BasicConstants.MINUTES);
		returnObj.setMinutes(minutes);

		Float lipidValue = 0.0f;
		Float tpnValue = 0.0f;
		Float bolusVolumeValue = 0.0f;
		Float readymadeVolumeValue = 0.0f;
		Float calciumVolumeValue = 0.0f;
		Float additionalPNValue = 0.0f;
		Float aminoValue = 0.0f;

		String bolusTypeValue = null;
		String lastNursingFeedid = null;
		String readymadeTypeValue = null;

		String refFeedTypeSql = "SELECT obj FROM RefMasterfeedtype as obj";
		List<RefMasterfeedtype> refFeedTypeList = inicuDoa.getListFromMappedObjQuery(refFeedTypeSql);
		returnObj.setRefFeedTypeList(refFeedTypeList);

		String enAddtivesBrand = "select obj from RefEnAddtivesBrand obj";
		List<RefEnAddtivesBrand> enAddtivesList = notesDoa.getListFromMappedObjNativeQuery(enAddtivesBrand);
		if (!BasicUtils.isEmpty(enAddtivesList)) {
			returnObj.setAddtivesbrandList(enAddtivesList);
		}

		String prevGirthquery = "select abdomen_girth from nursing_intake_output where entry_timestamp in (select max(entry_timestamp) from nursing_intake_output where uhid='"+uhid+"' and abdomen_girth is not null) ;";
		List prevGirth = inicuDoa.getListFromNativeQuery(prevGirthquery);
		if(!BasicUtils.isEmpty(prevGirth))
		{
			returnObj.setPrevabdomenGirth(prevGirth.get(0).toString());
		}

		String babyFeedSql = "SELECT obj FROM BabyfeedDetail as obj WHERE uhid ='" + uhid
				+ "' order by entrydatetime desc";

		List<BabyfeedDetail> babyFeedList = (List<BabyfeedDetail>) inicuDoa
				.getListFromMappedObjRowLimitQuery(babyFeedSql, 1);

		// pastIntakeOutputList
		Timestamp fromDate = new Timestamp(60000+Long.parseLong(fromTime));
		Timestamp toDate = new Timestamp(59000+Long.parseLong(toTime));

		String queryBaselineGirth= "Select min(abdomen_girth) from nursing_intake_output where uhid='" + uhid
				+ "' and abdomen_girth is not null and creationtime between (now() - interval '1 day') and now()";
		List<String> baselineGirthList = inicuDoa.getListFromNativeQuery(queryBaselineGirth);

		if(!BasicUtils.isEmpty(baselineGirthList)){
			returnObj.setMinAbdomenGirth(baselineGirthList.get(0));
		}
		if (!BasicUtils.isEmpty(babyFeedList)) {
			BabyfeedDetail babyFeedObj = babyFeedList.get(0);

			returnObj.setBabyFeedObj(babyFeedObj);

			// get the Doctor order date
			Timestamp orderDateTime = new Timestamp(babyFeedObj.getEntryDateTime().getTime());

			// get the nurse order executed for that particular order id
			String nurseOrderString = "SELECT obj FROM NursingIntakeOutput obj where uhid = '" + uhid + "' "
					+ "and babyfeedid = '" + babyFeedObj.getBabyfeedid().toString() + "' and entry_timestamp >='" + orderDateTime + "' "
					+ "order by entry_timestamp desc";

			List<NursingIntakeOutput> pastNursingTPNList = (List<NursingIntakeOutput>) inicuDoa.getListFromMappedObjQuery(nurseOrderString);

//			List<NursingIntakeOutput> pastNursingIntakeList = inicuDoa
//					.getListFromMappedObjQuery(HqlSqlQueryConstants.getNursingIntakeOutputList(uhid, fromDate, toDate));
			returnObj.setPastNursingIntakeList(pastNursingTPNList);

//			List<NursingIntakeOutput> pastNursingTPNList = (List<NursingIntakeOutput>) inicuDoa
//					.getListFromMappedObjRowLimitQuery(HqlSqlQueryConstants.getNursingIntakeOutputListFromId(uhid,
//							babyFeedObj.getBabyfeedid().toString()), 1);

			if (!BasicUtils.isEmpty(pastNursingTPNList)) {

				if(!BasicUtils.isEmpty(pastNursingTPNList) && pastNursingTPNList.size()>0) {
					lastNursingFeedid = pastNursingTPNList.get(0).getBabyfeedid();
					readymadeTypeValue = pastNursingTPNList.get(0).getReadymadeType();
					bolusTypeValue = pastNursingTPNList.get(0).getBolusType();
				}

				for (NursingIntakeOutput nurseEntry : pastNursingTPNList) {

					if(!BasicUtils.isEmpty(nurseEntry.getLipid_delivered())){
						lipidValue += nurseEntry.getLipid_delivered();
					}

					if(!BasicUtils.isEmpty(nurseEntry.getTpn_delivered())){
						tpnValue += nurseEntry.getTpn_delivered();
					}

					if(!BasicUtils.isEmpty(nurseEntry.getBolusDeliveredFeed())){
						bolusVolumeValue += nurseEntry.getBolusDeliveredFeed();
					}

					if(!BasicUtils.isEmpty(nurseEntry.getReadymadeDeliveredFeed())){
						readymadeVolumeValue += nurseEntry.getReadymadeDeliveredFeed();
					}

					if(!BasicUtils.isEmpty(nurseEntry.getCalciumDeliveredFeed())){
						calciumVolumeValue += nurseEntry.getCalciumDeliveredFeed();
					}

					if(!BasicUtils.isEmpty(nurseEntry.getAdditionalPNDeliveredFeed())){
						additionalPNValue += nurseEntry.getAdditionalPNDeliveredFeed();
					}

					if(!BasicUtils.isEmpty(nurseEntry.getAminoDeliveredFeed())){
						aminoValue += nurseEntry.getAminoDeliveredFeed();
					}
				}
//				lipidValue = pastNursingTPNList.get(0).getLipid_remaining();
//				tpnValue = pastNursingTPNList.get(0).getTpn_remaining();
								//				readymadeVolumeValue = pastNursingTPNList.get(0).getReadymadeRemainingFeed();
				//				calciumVolumeValue = pastNursingTPNList.get(0).getCalciumRemainingFeed();
				//				additionalPNValue = pastNursingTPNList.get(0).getAdditionalPNRemainingFeed();
				//				aminoValue = pastNursingTPNList.get(0).getAminoRemainingFeed();

//				bolusVolumeValue = pastNursingTPNList.get(0).getBolusRemainingFeed();

			}

			Float totalTpn = 0f;
			if (!BasicUtils.isEmpty(babyFeedObj.getTotalparenteralAdditivevolume())) {
				totalTpn += babyFeedObj.getTotalparenteralAdditivevolume();
			}

			if (!BasicUtils.isEmpty(babyFeedObj.getAminoacidTotal())) {
				if(BasicUtils.isEmpty(babyFeedObj.getIsAminoRate()) || (!BasicUtils.isEmpty(babyFeedObj.getIsAminoRate()) && babyFeedObj.getIsAminoRate() == false)){
					totalTpn += babyFeedObj.getAminoacidTotal();
				}
			}

			if (!BasicUtils.isEmpty(babyFeedObj.getDextroseVolumemlperday())) {
				totalTpn += babyFeedObj.getDextroseVolumemlperday();
			}

			if (!BasicUtils.isEmpty(totalTpn)) 
				totalTpn = Float.parseFloat(df2.format(totalTpn));

			Float totalReadymade = 0f;

			if (babyFeedObj.getIsReadymadeSolutionGiven()
					&& !BasicUtils.isEmpty(babyFeedObj.getReadymadeTotalFluidVolume())) {
				totalReadymade += babyFeedObj.getReadymadeTotalFluidVolume();
			}
			if (!BasicUtils.isEmpty(babyFeedObj.getTotalparenteralAdditivevolume())) {
				totalReadymade += babyFeedObj.getTotalparenteralAdditivevolume();
			}
			if (!BasicUtils.isEmpty(babyFeedObj.getAminoacidTotal())) {
				if(BasicUtils.isEmpty(babyFeedObj.getIsAminoRate()) || (!BasicUtils.isEmpty(babyFeedObj.getIsAminoRate()) && babyFeedObj.getIsAminoRate() == false)){
					totalReadymade += babyFeedObj.getAminoacidTotal();
				}
			}
			
			if (!BasicUtils.isEmpty(totalReadymade)) 
				totalReadymade = Float.parseFloat(df2.format(totalReadymade));

			Float totaladditionalPN = 0f;

			if (!BasicUtils.isEmpty(babyFeedObj.getAdditionalVolume())) {
				totaladditionalPN += babyFeedObj.getAdditionalVolume();
			}

			if (!BasicUtils.isEmpty(babyFeedObj.getAdditionalKCL())) {
				totaladditionalPN += babyFeedObj.getAdditionalKCL();
			}
			
			if (!BasicUtils.isEmpty(totaladditionalPN)) 
				totaladditionalPN = Float.parseFloat(df2.format(totaladditionalPN));

			if(!BasicUtils.isEmpty(totaladditionalPN)){
				returnObj.getCurrentNursingObj().setAdditionalPNTotalFeed(totaladditionalPN);
				returnObj.getCurrentNursingObj().setAdditionalPNRemainingFeed(totaladditionalPN);
				returnObj.getCurrentNursingObj().setAdditionalPNDeliveredFeed((float) 0.0);
			}

			if(babyFeedObj.getIsReadymadeSolutionGiven() == false){
				returnObj.getCurrentNursingObj().setTpn_total_volume(totalTpn);
				returnObj.getCurrentNursingObj().setTpn_remaining(totalTpn);
				returnObj.getCurrentNursingObj().setTpn_delivered((float) 0.0);
			}

			if(babyFeedObj.getIsReadymadeSolutionGiven() == true){

				returnObj.getCurrentNursingObj().setReadymadeTotalFeed(totalReadymade);
				returnObj.getCurrentNursingObj().setReadymadeRemainingFeed(totalReadymade);
				returnObj.getCurrentNursingObj().setReadymadeDeliveredFeed((float) 0.0);
			}

			if (!BasicUtils.isEmpty(babyFeedObj.getOtherAdditive())) {
				returnObj.getCurrentNursingObj().setOtherAdditive(babyFeedObj.getOtherAdditive());
			}

			if (!BasicUtils.isEmpty(babyFeedObj.getFeedtype())) {
				String[] feedTypeArr = babyFeedObj.getFeedtype().replace("[", "").replace("]", "").split(",");
				for (int i = 0; i < refFeedTypeList.size(); i++) {
					if (feedTypeArr[0].equalsIgnoreCase(refFeedTypeList.get(i).getFeedtypeid())) {
						returnObj.getCurrentNursingObj().setPrimaryFeedType(refFeedTypeList.get(i).getFeedtypeid());
						returnObj.getCurrentNursingObj()
								.setSelectedPrimaryFeedType(refFeedTypeList.get(i).getFeedtypename());
						break;
					}
				}
			}

			if (!BasicUtils.isEmpty(babyFeedObj.getFeedTypeSecondary())) {
				String[] feedTypeArr = babyFeedObj.getFeedTypeSecondary().replace("[", "").replace("]", "").split(",");
				returnObj.getCurrentNursingObj().setFormulaType(feedTypeArr[0].toString());
			}

			if (!BasicUtils.isEmpty(babyFeedObj.getLipidTotal())) {
				float obj = Float.parseFloat(df2.format(babyFeedObj.getLipidTotal()));

				returnObj.getCurrentNursingObj().setLipid_total_volume(obj);
				returnObj.getCurrentNursingObj().setLipid_remaining(obj);
				returnObj.getCurrentNursingObj().setLipid_delivered((float) 0.0);
			}

			if (!BasicUtils.isEmpty(babyFeedObj.getBolusType())) {
				returnObj.getCurrentNursingObj().setBolusType(babyFeedObj.getBolusType());
			}

			if (!BasicUtils.isEmpty(babyFeedObj.getBolusTotalFeed())) {

				float obj = Float.parseFloat(df2.format(babyFeedObj.getBolusTotalFeed()));
				returnObj.getCurrentNursingObj().setBolusTotalFeed(obj);
				returnObj.getCurrentNursingObj().setBolusRemainingFeed(obj);
				returnObj.getCurrentNursingObj().setBolusDeliveredFeed((float) 0.0);

			}
			if (!BasicUtils.isEmpty(babyFeedObj.getReadymadeFluidType())) {
				returnObj.getCurrentNursingObj().setReadymadeType(babyFeedObj.getReadymadeFluidType());
			}

			if (!BasicUtils.isEmpty(babyFeedObj.getCalciumTotal())) {
				float obj = Float.parseFloat(df2.format(babyFeedObj.getCalciumTotal()));

				returnObj.getCurrentNursingObj().setCalciumTotalFeed(obj);
				returnObj.getCurrentNursingObj().setCalciumRemainingFeed(obj);
				returnObj.getCurrentNursingObj().setCalciumDeliveredFeed((float) 0.0);
			}

			if (!BasicUtils.isEmpty(babyFeedObj.getIsAminoRate()) && babyFeedObj.getIsAminoRate() == true && !BasicUtils.isEmpty(babyFeedObj.getAminoacidTotal())) {
				float obj = Float.parseFloat(df2.format(babyFeedObj.getAminoacidTotal()));

				returnObj.getCurrentNursingObj().setAminoTotalFeed(obj);
				returnObj.getCurrentNursingObj().setAminoRemainingFeed(obj);
				returnObj.getCurrentNursingObj().setAminoDeliveredFeed((float) 0.0);
			}

			if (!BasicUtils.isEmpty(lastNursingFeedid)
					&& babyFeedObj.getBabyfeedid().toString().equalsIgnoreCase(lastNursingFeedid)) {
				if (!BasicUtils.isEmpty(lipidValue)) {
					// nurse executed
					lipidValue = Float.parseFloat(df2.format(lipidValue));

					if(!BasicUtils.isEmpty(babyFeedObj.getLipidTotal())){
						// total ordered
						float obj = Float.parseFloat(df2.format(babyFeedObj.getLipidTotal()));

						// balance
						float balanceLipid = obj - lipidValue;
						if(balanceLipid<0){
							returnObj.getCurrentNursingObj().setLipid_total_volume((float) 0.0);
							returnObj.getCurrentNursingObj().setLipid_remaining((float) 0.0);
							returnObj.getCurrentNursingObj().setLipid_delivered((float) 0.0);
						}else{
							balanceLipid = BasicUtils.round(balanceLipid,2);
							returnObj.getCurrentNursingObj().setLipid_total_volume(balanceLipid);
							returnObj.getCurrentNursingObj().setLipid_remaining(balanceLipid);
							returnObj.getCurrentNursingObj().setLipid_delivered(null);
						}
					}
				}

				if (!BasicUtils.isEmpty(aminoValue) && !BasicUtils.isEmpty(babyFeedObj.getIsAminoRate()) && babyFeedObj.getIsAminoRate() == true && !BasicUtils.isEmpty(babyFeedObj.getAminoacidTotal())) {
					aminoValue = Float.parseFloat(df2.format(aminoValue));

					if(!BasicUtils.isEmpty(babyFeedObj.getAminoacidTotal())) {

						// total ordered
						float obj = Float.parseFloat(df2.format(babyFeedObj.getAminoacidTotal()));
						float balanceLipid = obj - aminoValue;

						if (balanceLipid < 0) {
							returnObj.getCurrentNursingObj().setAminoTotalFeed((float) 0.0);
							returnObj.getCurrentNursingObj().setAminoRemainingFeed((float) 0.0);
							returnObj.getCurrentNursingObj().setAminoDeliveredFeed((float) 0.0);
						} else {
							balanceLipid = BasicUtils.round(balanceLipid, 2);
							returnObj.getCurrentNursingObj().setAminoTotalFeed(balanceLipid);
							returnObj.getCurrentNursingObj().setAminoRemainingFeed(balanceLipid);
							returnObj.getCurrentNursingObj().setAminoDeliveredFeed(null);
						}
					}
				}

				if (!BasicUtils.isEmpty(tpnValue) && babyFeedObj.getIsReadymadeSolutionGiven() == false) {
					tpnValue = Float.parseFloat(df2.format(tpnValue));

					if(!BasicUtils.isEmpty(totalTpn)) {
						float balance = totalTpn - tpnValue;
						if (balance < 0) {
							returnObj.getCurrentNursingObj().setTpn_total_volume((float) 0.0);
							returnObj.getCurrentNursingObj().setTpn_remaining((float) 0.0);
							returnObj.getCurrentNursingObj().setTpn_delivered((float) 0.0);
						} else {
							balance = BasicUtils.round(balance, 2);
							returnObj.getCurrentNursingObj().setTpn_total_volume(balance);
							returnObj.getCurrentNursingObj().setTpn_remaining(balance);
							returnObj.getCurrentNursingObj().setTpn_delivered(null);
						}
					}
				}
				if (bolusTypeValue != null) {
					returnObj.getCurrentNursingObj().setBolusType(bolusTypeValue);
				}
				if (!BasicUtils.isEmpty(bolusVolumeValue)) {
					bolusVolumeValue = Float.parseFloat(df2.format(bolusVolumeValue));

					if(!BasicUtils.isEmpty(babyFeedObj.getBolusTotalFeed())) {
						float obj = Float.parseFloat(df2.format(babyFeedObj.getBolusTotalFeed()));

						float balance = obj - bolusVolumeValue;
						if (balance < 0) {
							returnObj.getCurrentNursingObj().setBolusTotalFeed((float) 0.0);
							returnObj.getCurrentNursingObj().setBolusRemainingFeed((float) 0.0);
							returnObj.getCurrentNursingObj().setBolusDeliveredFeed(null);
						} else {
							balance = BasicUtils.round(balance, 2);
							returnObj.getCurrentNursingObj().setBolusTotalFeed(balance);
							returnObj.getCurrentNursingObj().setBolusRemainingFeed(balance);
							returnObj.getCurrentNursingObj().setBolusDeliveredFeed(null);
						}
					}
				}

				if (readymadeTypeValue != null) {
					returnObj.getCurrentNursingObj().setReadymadeType(babyFeedObj.getReadymadeFluidType());
				}
				if (!BasicUtils.isEmpty(readymadeVolumeValue) && babyFeedObj.getIsReadymadeSolutionGiven() == true) {
					readymadeVolumeValue = Float.parseFloat(df2.format(readymadeVolumeValue));

					if(!BasicUtils.isEmpty(totalReadymade)) {
						float balance = totalReadymade - readymadeVolumeValue;

						if (balance < 0) {
							returnObj.getCurrentNursingObj().setReadymadeTotalFeed((float) 0.0);
							returnObj.getCurrentNursingObj().setReadymadeRemainingFeed((float) 0.0);
							returnObj.getCurrentNursingObj().setReadymadeDeliveredFeed((float) 0.0);
						} else {
							balance = BasicUtils.round(balance, 2);
							returnObj.getCurrentNursingObj().setReadymadeTotalFeed(balance);
							returnObj.getCurrentNursingObj().setReadymadeRemainingFeed(balance);
							returnObj.getCurrentNursingObj().setReadymadeDeliveredFeed(null);
						}
					}

				}
				if (!BasicUtils.isEmpty(calciumVolumeValue)) {
					calciumVolumeValue = Float.parseFloat(df2.format(calciumVolumeValue));

					if(!BasicUtils.isEmpty(babyFeedObj.getCalciumTotal())) {
						float obj = Float.parseFloat(df2.format(babyFeedObj.getCalciumTotal()));

						float balance = obj - calciumVolumeValue;
						if (balance < 0) {
							returnObj.getCurrentNursingObj().setCalciumTotalFeed((float) 0.0);
							returnObj.getCurrentNursingObj().setCalciumRemainingFeed((float) 0.0);
							returnObj.getCurrentNursingObj().setCalciumDeliveredFeed(null);
						} else {
							balance = BasicUtils.round(balance, 2);
							returnObj.getCurrentNursingObj().setCalciumTotalFeed(balance);
							returnObj.getCurrentNursingObj().setCalciumRemainingFeed(balance);
							returnObj.getCurrentNursingObj().setCalciumDeliveredFeed(null);
						}
					}
				}
				if(!BasicUtils.isEmpty(additionalPNValue)) {
					additionalPNValue = Float.parseFloat(df2.format(additionalPNValue));
					if(!BasicUtils.isEmpty(totaladditionalPN)) {
						float balance = totaladditionalPN - additionalPNValue;
						if (balance < 0) {
							returnObj.getCurrentNursingObj().setAdditionalPNTotalFeed((float) 0.0);
							returnObj.getCurrentNursingObj().setAdditionalPNRemainingFeed((float) 0.0);
							returnObj.getCurrentNursingObj().setAdditionalPNDeliveredFeed(null);
						} else {
							balance = BasicUtils.round(balance, 2);
							returnObj.getCurrentNursingObj().setAdditionalPNTotalFeed(balance);
							returnObj.getCurrentNursingObj().setAdditionalPNRemainingFeed(balance);
							returnObj.getCurrentNursingObj().setAdditionalPNDeliveredFeed(null);
						}
					}
				}
			}

			// OralfeedDetail
			if (!(babyFeedObj.getFeedtype() == null || babyFeedObj.getFeedtype().isEmpty())) {
				String feedType = babyFeedObj.getFeedtype().replace("[", "'").replace("]", "'").replace(", ", "','");

				String oralfeedDetailSql = "SELECT obj FROM OralfeedDetail as obj WHERE babyfeedid='"
						+ babyFeedObj.getBabyfeedid() + "' and feedtypeId in (" + feedType + ")";

				List<OralfeedDetail> oralfeedDetailList = inicuDoa.getListFromMappedObjQuery(oralfeedDetailSql);
				returnObj.setOralFeedList(oralfeedDetailList);

			}

			// Feed Method
			if (!(babyFeedObj.getFeedmethod() == null || babyFeedObj.getFeedmethod().isEmpty())) {
				String feedMethodStr = babyFeedObj.getFeedmethod().replace("[", "").replace("]", "").replace(", ", ",");

				String refFeedMethodSql = "SELECT obj FROM RefMasterfeedmethod as obj";
				List<RefMasterfeedmethod> refFeedMethodList = inicuDoa.getListFromMappedObjQuery(refFeedMethodSql);

				if (!BasicUtils.isEmpty(feedMethodStr)) {
					String[] feedMethodArr = feedMethodStr.split(",");

					for (int i = 0; i < feedMethodArr.length; i++) {
						feedMethodStr = "";
						Iterator<RefMasterfeedmethod> itr = refFeedMethodList.iterator();
						while (itr.hasNext()) {
							RefMasterfeedmethod obj = itr.next();
							if (feedMethodArr[i].trim().equalsIgnoreCase(obj.getFeedmethodid())) {
								if (feedMethodStr.isEmpty()) {
									feedMethodStr = obj.getFeedmethodname();
								} else {
									feedMethodStr += ", " + obj.getFeedmethodname();
								}
								break;
							}
						}
					}

					if (feedMethodArr.length > 1 && babyFeedObj.getFeedmethod_type() != null) {
						if (babyFeedObj.getFeedmethod_type()) {
							feedMethodStr += "(Per Shift)";
						} else {
							feedMethodStr += "(Alternatively)";
						}
					}
				}

				returnObj.setFeedMethodStr(feedMethodStr);
			}
			
			String incrementSql = "SELECT obj FROM IncrementFeed as obj WHERE uhid='" + uhid + "' and baby_feed_id='"
					+ babyFeedObj.getBabyfeedid() + "' order by feed_date asc";
			List<IncrementFeed> incrementList = inicuDoa.getListFromMappedObjQuery(incrementSql);
			returnObj.setIncrementFeed(incrementList);

			// EnfeedDetails
			String enfeedDetailsSql = "SELECT obj FROM EnFeedDetail as obj WHERE uhid='" + uhid + "' and babyfeedid="
					+ babyFeedObj.getBabyfeedid() + " order by en_feed_detail_id asc";
			List<EnFeedDetail> enfeedDetailsList = inicuDoa.getListFromMappedObjQuery(enfeedDetailsSql);
			returnObj.setEnFeedList(enfeedDetailsList);

			// Cycle runs from 8AM to 8AM
			Timestamp today = new Timestamp((new java.util.Date().getTime()));
			Timestamp currentDate = new Timestamp((new java.util.Date().getTime()));
			Timestamp yesterday = new Timestamp((new java.util.Date().getTime()) - (24 * 60 * 60 * 1000));
			Timestamp tomorrow = new Timestamp((new java.util.Date().getTime()) + (24 * 60 * 60 * 1000));

			today.setHours(8);
			today.setMinutes(1);
			today.setSeconds(0);

			yesterday.setHours(8);
			yesterday.setMinutes(1);
			yesterday.setSeconds(0);

			tomorrow.setHours(8);
			tomorrow.setMinutes(0);
			tomorrow.setSeconds(59);

			currentDate = new Timestamp(currentDate.getTime() + offset);
			today = new Timestamp(today.getTime() - offset);
			yesterday = new Timestamp(yesterday.getTime() - offset);
			tomorrow = new Timestamp(tomorrow.getTime() - offset);
			String lastFeedQuery = "";
			String currentIntakeQuery =  "select obj from NursingIntakeOutput obj where uhid='" + uhid + "' and entry_timestamp<='"
					+ tomorrow + "' and  entry_timestamp>='" + today + "' order by entry_timestamp desc";
			List<NursingIntakeOutput> currentIntakeList = notesDoa.getListFromMappedObjNativeQuery(currentIntakeQuery);
			if (!BasicUtils.isEmpty(currentIntakeList)) {
				returnObj.setCurrentNursingIntakeList(currentIntakeList);
			}
			// get feed details from last 24 hrs(8AM to 8AM)
			if(offset != 0) {
				if(currentDate.getHours() >= 8 || currentDate.getHours() <= 5){
					lastFeedQuery = "select obj from BabyfeedDetail obj where uhid='" + uhid + "' and entrydatetime<='"
							+ tomorrow + "' and  entrydatetime>='" + today + "' order by entrydatetime desc";
				}
				else{
					lastFeedQuery = "select obj from BabyfeedDetail obj where uhid='" + uhid + "' and entrydatetime<='"
							+ today + "' and  entrydatetime>='" + yesterday + "' order by entrydatetime desc";
				}
			}
			else {
				if(currentDate.getHours() >= 8){
					lastFeedQuery = "select obj from BabyfeedDetail obj where uhid='" + uhid + "' and entrydatetime<='"
							+ tomorrow + "' and  entrydatetime>='" + today + "' order by entrydatetime desc";
				}
				else{
					lastFeedQuery = "select obj from BabyfeedDetail obj where uhid='" + uhid + "' and entrydatetime<='"
							+ today + "' and  entrydatetime>='" + yesterday + "' order by entrydatetime desc";
				}
			}

			List<BabyfeedDetail> babyFeedListSet = new ArrayList<BabyfeedDetail>();
			List<BabyfeedDetail> babyFeedAllList = notesDoa.getListFromMappedObjNativeQuery(lastFeedQuery);

			if (!BasicUtils.isEmpty(babyFeedAllList)) {
				for(int i = 1; i < babyFeedAllList.size() ; i++) {
					babyFeedListSet.add(babyFeedAllList.get(i));
				}
			}
			returnObj.setBabyFeedList(babyFeedListSet);
		}
		return returnObj;
	}

	private List<String> getTime(String string) {
		List<String> hours = new ArrayList<String>();
		List<String> minutes = new ArrayList<String>();
		List<String> seconds = new ArrayList<String>();
		if (string.equalsIgnoreCase(BasicConstants.HOURS)) {
			for (int i = 0; i <= 12; i++) {
				if (i < 10) {
					hours.add("0" + i);
				} else {
					hours.add(String.valueOf(i));
				}
			}
			return hours;
		} else if (string.equalsIgnoreCase(BasicConstants.MINUTES)) {
			for (int i = 0; i <= 59; i++) {
				if (i < 10) {
					minutes.add("0" + i);
				} else {
					minutes.add(String.valueOf(i));
				}
			}
			return minutes;
		} else if (string.equalsIgnoreCase(BasicConstants.SECONDS)) {
			for (int i = 0; i <= 59; i++) {
				if (i < 10) {
					seconds.add("0" + i);
				} else {
					seconds.add(String.valueOf(i));
				}
			}
			return seconds;
		}
		return null;
	}

	@Override
	public NursingInputsPOJO saveNursingInputs(String uhid, String fromTime, String toTime, String loggedUser,
			NursingIntakeOutput obj) {
		try {
			obj.setCreationtime(obj.getEntry_timestamp());
			if (BasicUtils.isEmpty(obj.getBabyfeedid())) {
				obj.setBabyfeedid("0");
			} else if (obj.getBolus_executed() != null && obj.getBolus_executed()) {
				String babyFeedSql = "update babyfeed_detail set bolus_executed=true WHERE babyfeedid="
						+ obj.getBabyfeedid();
				inicuDoa.updateOrDeleteNativeQuery(babyFeedSql);
			}

			inicuDoa.saveObject(obj);
			Timestamp startingentryTime  = obj.getEntry_timestamp();
		
			String babyFeedSql = "SELECT obj FROM NursingIntakeOutput as obj WHERE uhid ='" + uhid
					+ "' and entry_timestamp > '" +  startingentryTime  + "' order by entry_timestamp asc";
			List<NursingIntakeOutput> listIntakeOutput = notesDoa.getListFromMappedObjNativeQuery(babyFeedSql);
			float lipidRemainingFeed = -1;
			int lipidCount = 0;
			float tpnRemainingFeed = -1;
			int tpnCount = 0;
			float aminoRemainingFeed = -1;
			int aminoCount = 0;
			float bolusRemainingFeed = -1;
			int bolusCount = 0;
			float readymadeRemainingFeed = -1;
			int readymadeCount = 0;
			float calciumRemainingFeed = -1;
			int calciumCount = 0;
			float additionalPNRemainingFeed = -1;
			int additionalPNCount = 0;
//			for(NursingIntakeOutput intakeObj : listIntakeOutput) {
//				if(!BasicUtils.isEmpty(obj.getReadymadeRemainingFeed()) && !BasicUtils.isEmpty(intakeObj.getReadymadeTotalFeed()) && !BasicUtils.isEmpty(intakeObj.getReadymadeDeliveredFeed())) {
//					if(readymadeCount == 0) {
//						readymadeRemainingFeed = obj.getReadymadeRemainingFeed();
//						readymadeCount++;
//					}
//
//					if(intakeObj.getReadymadeTotalFeed() != readymadeRemainingFeed) {
//						intakeObj.setReadymadeTotalFeed(readymadeRemainingFeed);
//						intakeObj.setReadymadeRemainingFeed(readymadeRemainingFeed - intakeObj.getReadymadeDeliveredFeed());
//					}
//					readymadeRemainingFeed = intakeObj.getReadymadeRemainingFeed();
//				}
//				else if(readymadeRemainingFeed != -1 && !BasicUtils.isEmpty(intakeObj.getReadymadeTotalFeed()) && !BasicUtils.isEmpty(intakeObj.getReadymadeDeliveredFeed())) {
//					if(readymadeCount == 0) {
//						readymadeRemainingFeed = obj.getReadymadeRemainingFeed();
//						readymadeCount++;
//					}
//
//					if(intakeObj.getReadymadeTotalFeed() != readymadeRemainingFeed) {
//						intakeObj.setReadymadeTotalFeed(readymadeRemainingFeed);
//						intakeObj.setReadymadeRemainingFeed(readymadeRemainingFeed - intakeObj.getReadymadeDeliveredFeed());
//					}
//					readymadeRemainingFeed = intakeObj.getReadymadeRemainingFeed();
//				}
//				else if(readymadeRemainingFeed == -1 && BasicUtils.isEmpty(intakeObj.getReadymadeTotalFeed()) && BasicUtils.isEmpty(intakeObj.getReadymadeDeliveredFeed())){
//					if(!BasicUtils.isEmpty(obj.getReadymadeRemainingFeed())) {
//						readymadeRemainingFeed = obj.getReadymadeRemainingFeed();
//						readymadeCount++;
//					}
//				}
//				else if(readymadeRemainingFeed != -1 && !BasicUtils.isEmpty(intakeObj.getReadymadeTotalFeed()) && BasicUtils.isEmpty(intakeObj.getReadymadeDeliveredFeed()) && !BasicUtils.isEmpty(intakeObj.getReadymadeRemainingFeed())) {
//					intakeObj.setReadymadeTotalFeed(readymadeRemainingFeed);
//					intakeObj.setReadymadeRemainingFeed(readymadeRemainingFeed);
//
//				}
//				else if(readymadeRemainingFeed != -1 && !BasicUtils.isEmpty(obj.getReadymadeRemainingFeed()) && !BasicUtils.isEmpty(intakeObj.getReadymadeTotalFeed()) && BasicUtils.isEmpty(intakeObj.getReadymadeDeliveredFeed()) && !BasicUtils.isEmpty(intakeObj.getReadymadeRemainingFeed())) {
//					intakeObj.setReadymadeTotalFeed(obj.getReadymadeRemainingFeed());
//					intakeObj.setReadymadeRemainingFeed(obj.getReadymadeRemainingFeed());
//				}
//
//				if(!BasicUtils.isEmpty(obj.getBolusRemainingFeed()) && !BasicUtils.isEmpty(intakeObj.getBolusTotalFeed()) && !BasicUtils.isEmpty(intakeObj.getBolusDeliveredFeed())) {
//					if(bolusCount == 0) {
//						bolusRemainingFeed = obj.getBolusRemainingFeed();
//						bolusCount++;
//					}
//
//					if(intakeObj.getBolusTotalFeed() != bolusRemainingFeed) {
//						intakeObj.setBolusTotalFeed(bolusRemainingFeed);
//						intakeObj.setBolusRemainingFeed(bolusRemainingFeed - intakeObj.getBolusDeliveredFeed());
//					}
//					bolusRemainingFeed = intakeObj.getBolusRemainingFeed();
//				}
//				else if(bolusRemainingFeed != -1 && !BasicUtils.isEmpty(intakeObj.getBolusTotalFeed()) && !BasicUtils.isEmpty(intakeObj.getBolusDeliveredFeed())) {
//					if(bolusCount == 0) {
//						bolusRemainingFeed = obj.getBolusRemainingFeed();
//						bolusCount++;
//					}
//
//					if(intakeObj.getBolusTotalFeed() != bolusRemainingFeed) {
//						intakeObj.setBolusTotalFeed(bolusRemainingFeed);
//						intakeObj.setBolusRemainingFeed(bolusRemainingFeed - intakeObj.getBolusDeliveredFeed());
//					}
//					bolusRemainingFeed = intakeObj.getBolusRemainingFeed();
//				}
//				else if(bolusRemainingFeed == -1 && BasicUtils.isEmpty(intakeObj.getBolusTotalFeed()) && BasicUtils.isEmpty(intakeObj.getBolusDeliveredFeed())){
//					if(!BasicUtils.isEmpty(obj.getBolusRemainingFeed())) {
//						bolusRemainingFeed = obj.getBolusRemainingFeed();
//						bolusCount++;
//					}
//				}else if(bolusRemainingFeed != -1 && !BasicUtils.isEmpty(intakeObj.getBolusTotalFeed()) && BasicUtils.isEmpty(intakeObj.getBolusDeliveredFeed()) && !BasicUtils.isEmpty(intakeObj.getBolusRemainingFeed())) {
//					intakeObj.setBolusTotalFeed(bolusRemainingFeed);
//					intakeObj.setBolusRemainingFeed(bolusRemainingFeed);
//
//				}else if(bolusRemainingFeed != -1 && !BasicUtils.isEmpty(obj.getBolusRemainingFeed()) && !BasicUtils.isEmpty(intakeObj.getBolusTotalFeed()) && BasicUtils.isEmpty(intakeObj.getBolusDeliveredFeed()) && !BasicUtils.isEmpty(intakeObj.getBolusRemainingFeed())) {
//					intakeObj.setBolusTotalFeed(obj.getBolusRemainingFeed());
//					intakeObj.setBolusRemainingFeed(obj.getBolusRemainingFeed());
//				}
//
//
//				if(!BasicUtils.isEmpty(obj.getCalciumRemainingFeed()) && !BasicUtils.isEmpty(intakeObj.getCalciumTotalFeed()) && !BasicUtils.isEmpty(intakeObj.getCalciumDeliveredFeed())) {
//					if(calciumCount == 0) {
//						calciumRemainingFeed = obj.getCalciumRemainingFeed();
//						calciumCount++;
//					}
//
//					if(intakeObj.getCalciumTotalFeed() != calciumRemainingFeed) {
//						intakeObj.setCalciumTotalFeed(calciumRemainingFeed);
//						intakeObj.setCalciumRemainingFeed(calciumRemainingFeed - intakeObj.getCalciumDeliveredFeed());
//					}
//					calciumRemainingFeed = intakeObj.getCalciumRemainingFeed();
//				}
//				else if(calciumRemainingFeed != -1 && !BasicUtils.isEmpty(intakeObj.getCalciumTotalFeed()) && !BasicUtils.isEmpty(intakeObj.getCalciumDeliveredFeed())) {
//					if(calciumCount == 0) {
//						calciumRemainingFeed = obj.getCalciumRemainingFeed();
//						calciumCount++;
//					}
//
//					if(intakeObj.getCalciumTotalFeed() != calciumRemainingFeed) {
//						intakeObj.setCalciumTotalFeed(calciumRemainingFeed);
//						intakeObj.setCalciumRemainingFeed(calciumRemainingFeed - intakeObj.getCalciumDeliveredFeed());
//					}
//					calciumRemainingFeed = intakeObj.getCalciumRemainingFeed();
//				}
//				else if(calciumRemainingFeed == -1 && BasicUtils.isEmpty(intakeObj.getCalciumTotalFeed()) && BasicUtils.isEmpty(intakeObj.getCalciumDeliveredFeed())){
//					if(!BasicUtils.isEmpty(obj.getCalciumRemainingFeed())) {
//						calciumRemainingFeed = obj.getCalciumRemainingFeed();
//						calciumCount++;
//					}
//				}
//				else if(calciumRemainingFeed != -1 && !BasicUtils.isEmpty(intakeObj.getCalciumTotalFeed()) && BasicUtils.isEmpty(intakeObj.getCalciumDeliveredFeed()) && !BasicUtils.isEmpty(intakeObj.getCalciumRemainingFeed())) {
//					intakeObj.setCalciumTotalFeed(calciumRemainingFeed);
//					intakeObj.setCalciumRemainingFeed(calciumRemainingFeed);
//
//				}
//				else if(calciumRemainingFeed != -1 && !BasicUtils.isEmpty(obj.getCalciumRemainingFeed()) && !BasicUtils.isEmpty(intakeObj.getCalciumTotalFeed()) && BasicUtils.isEmpty(intakeObj.getCalciumDeliveredFeed()) && !BasicUtils.isEmpty(intakeObj.getCalciumRemainingFeed())) {
//					intakeObj.setCalciumTotalFeed(obj.getCalciumRemainingFeed());
//					intakeObj.setCalciumRemainingFeed(obj.getCalciumRemainingFeed());
//				}
//
//				if(!BasicUtils.isEmpty(obj.getAdditionalPNRemainingFeed()) && !BasicUtils.isEmpty(intakeObj.getAdditionalPNTotalFeed()) && !BasicUtils.isEmpty(intakeObj.getAdditionalPNDeliveredFeed())) {
//					if(additionalPNCount == 0) {
//						additionalPNRemainingFeed = obj.getAdditionalPNRemainingFeed();
//						additionalPNCount++;
//					}
//
//					if(intakeObj.getAdditionalPNTotalFeed() != additionalPNRemainingFeed) {
//						intakeObj.setAdditionalPNTotalFeed(additionalPNRemainingFeed);
//						intakeObj.setAdditionalPNRemainingFeed(additionalPNRemainingFeed - intakeObj.getAdditionalPNDeliveredFeed());
//					}
//					additionalPNRemainingFeed = intakeObj.getAdditionalPNRemainingFeed();
//				}
//				else if(additionalPNRemainingFeed != -1 && !BasicUtils.isEmpty(intakeObj.getAdditionalPNTotalFeed()) && !BasicUtils.isEmpty(intakeObj.getAdditionalPNDeliveredFeed())) {
//					if(additionalPNCount == 0) {
//						additionalPNRemainingFeed = obj.getAdditionalPNRemainingFeed();
//						additionalPNCount++;
//					}
//
//					if(intakeObj.getAdditionalPNTotalFeed() != additionalPNRemainingFeed) {
//						intakeObj.setAdditionalPNTotalFeed(additionalPNRemainingFeed);
//						intakeObj.setAdditionalPNRemainingFeed(additionalPNRemainingFeed - intakeObj.getAdditionalPNDeliveredFeed());
//					}
//					additionalPNRemainingFeed = intakeObj.getAdditionalPNRemainingFeed();
//				}
//				else if(additionalPNRemainingFeed == -1 && BasicUtils.isEmpty(intakeObj.getAdditionalPNTotalFeed()) && BasicUtils.isEmpty(intakeObj.getAdditionalPNDeliveredFeed())){
//					if(!BasicUtils.isEmpty(obj.getAdditionalPNRemainingFeed())) {
//						additionalPNRemainingFeed = obj.getAdditionalPNRemainingFeed();
//						additionalPNCount++;
//					}
//				}
//				else if(additionalPNRemainingFeed != -1 && !BasicUtils.isEmpty(intakeObj.getAdditionalPNTotalFeed()) && BasicUtils.isEmpty(intakeObj.getAdditionalPNDeliveredFeed()) && !BasicUtils.isEmpty(intakeObj.getAdditionalPNRemainingFeed())) {
//					intakeObj.setAdditionalPNTotalFeed(additionalPNRemainingFeed);
//					intakeObj.setAdditionalPNRemainingFeed(additionalPNRemainingFeed);
//
//				}
//				else if(additionalPNRemainingFeed != -1 && !BasicUtils.isEmpty(obj.getAdditionalPNRemainingFeed()) && !BasicUtils.isEmpty(intakeObj.getAdditionalPNTotalFeed()) && BasicUtils.isEmpty(intakeObj.getAdditionalPNDeliveredFeed()) && !BasicUtils.isEmpty(intakeObj.getAdditionalPNRemainingFeed())) {
//					intakeObj.setAdditionalPNTotalFeed(obj.getAdditionalPNRemainingFeed());
//					intakeObj.setAdditionalPNRemainingFeed(obj.getAdditionalPNRemainingFeed());
//				}
//
//				if(!BasicUtils.isEmpty(obj.getAminoRemainingFeed()) && !BasicUtils.isEmpty(intakeObj.getAminoTotalFeed()) && !BasicUtils.isEmpty(intakeObj.getAminoDeliveredFeed())) {
//					if(aminoCount == 0) {
//						aminoRemainingFeed = obj.getAminoRemainingFeed();
//						aminoCount++;
//					}
//					if(intakeObj.getAminoTotalFeed() != aminoRemainingFeed) {
//						intakeObj.setAminoTotalFeed(aminoRemainingFeed);
//						intakeObj.setAminoRemainingFeed(aminoRemainingFeed - intakeObj.getAminoDeliveredFeed());
//					}
//					aminoRemainingFeed = intakeObj.getAminoRemainingFeed();
//				}
//				else if(aminoRemainingFeed != -1 && !BasicUtils.isEmpty(intakeObj.getAminoTotalFeed()) && !BasicUtils.isEmpty(intakeObj.getAminoDeliveredFeed())) {
//					if(aminoCount == 0) {
//						aminoRemainingFeed = obj.getAminoRemainingFeed();
//						aminoCount++;
//					}
//					if(intakeObj.getAminoTotalFeed() != aminoRemainingFeed) {
//						intakeObj.setAminoTotalFeed(aminoRemainingFeed);
//						intakeObj.setAminoRemainingFeed(aminoRemainingFeed - intakeObj.getAminoDeliveredFeed());
//					}
//					aminoRemainingFeed = intakeObj.getAminoRemainingFeed();
//				}
//				else if(aminoRemainingFeed == -1 && BasicUtils.isEmpty(intakeObj.getAminoTotalFeed()) && BasicUtils.isEmpty(intakeObj.getAminoDeliveredFeed())){
//					if(!BasicUtils.isEmpty(obj.getAminoRemainingFeed())) {
//						aminoRemainingFeed = obj.getAminoRemainingFeed();
//						aminoCount++;
//					}
//				}
//				else if(aminoRemainingFeed != -1 && !BasicUtils.isEmpty(intakeObj.getAminoTotalFeed()) && BasicUtils.isEmpty(intakeObj.getAminoDeliveredFeed()) && !BasicUtils.isEmpty(intakeObj.getAminoRemainingFeed())) {
//					intakeObj.setAminoTotalFeed(aminoRemainingFeed);
//					intakeObj.setAminoRemainingFeed(aminoRemainingFeed);
//
//				}
//				else if(aminoRemainingFeed != -1 && !BasicUtils.isEmpty(obj.getAminoRemainingFeed()) && !BasicUtils.isEmpty(intakeObj.getAminoTotalFeed()) && BasicUtils.isEmpty(intakeObj.getAminoDeliveredFeed()) && !BasicUtils.isEmpty(intakeObj.getAminoRemainingFeed())) {
//					intakeObj.setAminoTotalFeed(obj.getAminoRemainingFeed());
//					intakeObj.setAminoRemainingFeed(obj.getAminoRemainingFeed());
//				}
//
//				if(!BasicUtils.isEmpty(obj.getLipid_remaining()) && !BasicUtils.isEmpty(intakeObj.getLipid_total_volume()) && !BasicUtils.isEmpty(intakeObj.getLipid_delivered())) {
//					if(lipidCount == 0) {
//						lipidRemainingFeed = obj.getLipid_remaining();
//						lipidCount++;
//					}
//					if(intakeObj.getLipid_total_volume() != lipidRemainingFeed) {
//						intakeObj.setLipid_total_volume(lipidRemainingFeed);
//						intakeObj.setLipid_remaining(lipidRemainingFeed - intakeObj.getLipid_delivered());
//					}
//					lipidRemainingFeed = intakeObj.getLipid_remaining();
//				}
//				else if(lipidRemainingFeed != -1 && !BasicUtils.isEmpty(intakeObj.getLipid_total_volume()) && !BasicUtils.isEmpty(intakeObj.getLipid_delivered())) {
//					if(lipidCount == 0) {
//						lipidRemainingFeed = obj.getLipid_remaining();
//						lipidCount++;
//					}
//					if(intakeObj.getLipid_total_volume() != lipidRemainingFeed) {
//						intakeObj.setLipid_total_volume(lipidRemainingFeed);
//						intakeObj.setLipid_remaining(lipidRemainingFeed - intakeObj.getLipid_delivered());
//					}
//					lipidRemainingFeed = intakeObj.getLipid_remaining();
//				}
//				else if(lipidRemainingFeed == -1 && BasicUtils.isEmpty(intakeObj.getLipid_total_volume()) && BasicUtils.isEmpty(intakeObj.getLipid_delivered())){
//					if(!BasicUtils.isEmpty(obj.getLipid_remaining())) {
//						lipidRemainingFeed = obj.getLipid_remaining();
//						lipidCount++;
//					}
//				}
//				else if(lipidRemainingFeed != -1 && !BasicUtils.isEmpty(intakeObj.getLipid_total_volume()) && BasicUtils.isEmpty(intakeObj.getLipid_delivered()) && !BasicUtils.isEmpty(intakeObj.getLipid_remaining())) {
//					intakeObj.setLipid_total_volume(lipidRemainingFeed);
//					intakeObj.setLipid_remaining(lipidRemainingFeed);
//
//				}
//				else if(lipidRemainingFeed == -1 && !BasicUtils.isEmpty(obj.getLipid_remaining()) && !BasicUtils.isEmpty(intakeObj.getLipid_total_volume()) && BasicUtils.isEmpty(intakeObj.getLipid_delivered()) && !BasicUtils.isEmpty(intakeObj.getLipid_remaining())) {
//					intakeObj.setLipid_total_volume(obj.getLipid_remaining());
//					intakeObj.setLipid_remaining(obj.getLipid_remaining());
//
//				}
//
//				if(!BasicUtils.isEmpty(obj.getTpn_remaining()) && !BasicUtils.isEmpty(intakeObj.getTpn_total_volume()) && !BasicUtils.isEmpty(intakeObj.getTpn_delivered())) {
//					if(tpnCount == 0) {
//						tpnRemainingFeed = obj.getTpn_remaining();
//						tpnCount++;
//					}
//					if(intakeObj.getTpn_total_volume() != tpnRemainingFeed) {
//						intakeObj.setTpn_total_volume(tpnRemainingFeed);
//						intakeObj.setTpn_remaining(tpnRemainingFeed - intakeObj.getTpn_delivered());
//					}
//					tpnRemainingFeed = intakeObj.getTpn_remaining();
//				}else if(tpnRemainingFeed != -1 && !BasicUtils.isEmpty(intakeObj.getTpn_total_volume()) && !BasicUtils.isEmpty(intakeObj.getTpn_delivered())) {
//					if(tpnCount == 0) {
//						tpnRemainingFeed = obj.getTpn_remaining();
//						tpnCount++;
//					}
//					if(intakeObj.getTpn_total_volume() != tpnRemainingFeed) {
//						intakeObj.setTpn_total_volume(tpnRemainingFeed);
//						intakeObj.setTpn_remaining(tpnRemainingFeed - intakeObj.getTpn_delivered());
//					}
//					tpnRemainingFeed = intakeObj.getTpn_remaining();
//				}else if(tpnRemainingFeed == -1 && BasicUtils.isEmpty(intakeObj.getTpn_total_volume()) && BasicUtils.isEmpty(intakeObj.getTpn_delivered())){
//					if(!BasicUtils.isEmpty(obj.getTpn_remaining())) {
//						tpnRemainingFeed = obj.getTpn_remaining();
//						tpnCount++;
//					}
//				}else if(tpnRemainingFeed != -1 && !BasicUtils.isEmpty(intakeObj.getTpn_total_volume()) && BasicUtils.isEmpty(intakeObj.getTpn_delivered()) && !BasicUtils.isEmpty(intakeObj.getTpn_remaining())) {
//					intakeObj.setTpn_total_volume(tpnRemainingFeed);
//					intakeObj.setTpn_remaining(tpnRemainingFeed);
//
//				}else if(tpnRemainingFeed != -1 && !BasicUtils.isEmpty(obj.getTpn_remaining()) && !BasicUtils.isEmpty(intakeObj.getTpn_total_volume()) && BasicUtils.isEmpty(intakeObj.getTpn_delivered()) && !BasicUtils.isEmpty(intakeObj.getTpn_remaining())) {
//					intakeObj.setTpn_total_volume(obj.getTpn_remaining());
//					intakeObj.setTpn_remaining(obj.getTpn_remaining());
//
//				}
//
//				inicuDoa.saveObject(intakeObj);
//
//				obj = intakeObj;
//			}


			// save primary feed to oral feed table
			if (!BasicUtils.isEmpty(obj.getPrimaryFeedType())
					&& (null != obj.getPrimaryFeedValue() && obj.getPrimaryFeedValue() != 0)) {
				OralfeedDetail feedObj = new OralfeedDetail();
				feedObj.setUhid(uhid);
				feedObj.setEntrydatetime(obj.getEntry_timestamp());
				feedObj.setFeedtypeId(obj.getPrimaryFeedType());
				feedObj.setFeedvolume(obj.getPrimaryFeedValue());
				feedObj.setTotalFeedVolume(obj.getPrimaryFeedValue());
				feedObj.setBabyfeedid(obj.getBabyfeedid());
				inicuDoa.saveObject(feedObj);
			}

			// save formula feed to oral feed table
			if (!BasicUtils.isEmpty(obj.getFormulaType())
					&& (null != obj.getFormulaValue() && obj.getFormulaValue() != 0)) {
				OralfeedDetail feedObj = new OralfeedDetail();
				feedObj.setUhid(uhid);
				feedObj.setEntrydatetime(obj.getEntry_timestamp());
				feedObj.setFeedtypeId(obj.getFormulaType());
				feedObj.setFeedvolume(obj.getFormulaValue());
				feedObj.setTotalFeedVolume(obj.getFormulaValue());
				feedObj.setBabyfeedid(obj.getBabyfeedid());
				inicuDoa.saveObject(feedObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return getNursingInputs(uhid, fromTime, toTime);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DoctorNotesPOJO getDoctorNotes(DoctorNotesPrintInfoPOJO doctorNotesPrintInfo) {

		DoctorNotesPOJO doctorNotes = new DoctorNotesPOJO();
		try {

			// get baby details from admission formm.................

			String queryAdmissionNotes = "select obj from  AdmissionNotes obj where uhid='"
					+ doctorNotesPrintInfo.getUhid() + "' order by creationtime desc";
			List<AdmissionNotes> listAdmissionNotes = notesDoa.getListFromMappedObjNativeQuery(queryAdmissionNotes);
			String adminssionNotesDiagnosis = "";
			if (!BasicUtils.isEmpty(listAdmissionNotes)) {
				AdmissionNotes admNotes = listAdmissionNotes.get(0);

				if (!BasicUtils.isEmpty(admNotes.getDiagnosis())) {
					adminssionNotesDiagnosis = admNotes.getDiagnosis();
				}
			}
			String queryDiagnosisDetails = "select obj from  DashboardFinalview obj where uhid='"
					+ doctorNotesPrintInfo.getUhid() + "'";
			List<DashboardFinalview> listDiagnosisDetails = notesDoa
					.getListFromMappedObjNativeQuery(queryDiagnosisDetails);
			if (!BasicUtils.isEmpty(listDiagnosisDetails)) {
				if (!BasicUtils.isEmpty(listDiagnosisDetails.get(0).getDiagnosis())) {

					// diagnosis note
					String diagnosisStr = listDiagnosisDetails.get(0).getDiagnosis().replaceAll(",", "/");

					int flag = 0;
					if(adminssionNotesDiagnosis.contains("Sepsis")){
						adminssionNotesDiagnosis = getSepsisType(adminssionNotesDiagnosis,uhid);
						flag =1;
					}

					if (diagnosisStr.contains("Sepsis")) {
						if(flag == 0) {
							diagnosisStr = getSepsisType(diagnosisStr, uhid);
						}else if (flag == 1){

							// replace the Sepsis with empty string
							diagnosisStr = diagnosisStr.replace("Sepsis","");
						}
					}

					adminssionNotesDiagnosis = adminssionNotesDiagnosis + ", "
							+ diagnosisStr;
				}
			}

			String stableNotesSql = "select obj from StableNote obj where uhid='" + doctorNotesPrintInfo.getUhid()
					+ "' order by creationtime desc";
			List<StableNote> listStableNote = (List<StableNote>) inicuDoa
					.getListFromMappedObjRowLimitQuery(stableNotesSql, 1);
			if (!BasicUtils.isEmpty(listStableNote)) {
				doctorNotes.setStableNoteObj(listStableNote.get(0));
			}

			// for nursing events string
			String nursingEventStr = "";
			List<Object> listHypoHyperGlycemia = inicuDoa.getListFromNativeQuery(
					HqlSqlQueryConstants.getGlycemiaEventsCountList(doctorNotesPrintInfo.getUhid()));
			if (!BasicUtils.isEmpty(listHypoHyperGlycemia)) {
				Object[] obj = (Object[]) listHypoHyperGlycemia.get(0);
				if (obj[0] != null && ((BigInteger) obj[0]).intValue() > 0) {
					nursingEventStr += "hypoglycemia";
				}

				if (obj[1] != null && ((BigInteger) obj[1]).intValue() > 0) {
					if (BasicUtils.isEmpty(nursingEventStr)) {
						nursingEventStr += "hyperglycemia";
					} else {
						nursingEventStr += ", hyperglycemia";
					}
				}
			}

			List<Object> listNursingEpisode = inicuDoa.getListFromNativeQuery(
					HqlSqlQueryConstants.getNursingEventsCountList(doctorNotesPrintInfo.getUhid()));
			if (!BasicUtils.isEmpty(listNursingEpisode)) {
				Object[] obj = (Object[]) listNursingEpisode.get(0);
				if (obj[0] != null && ((BigInteger) obj[0]).intValue() > 0) {
					if (BasicUtils.isEmpty(nursingEventStr)) {
						nursingEventStr += "apnea";
					} else {
						nursingEventStr += ", apnea";
					}
				}

				if (obj[1] != null && ((BigInteger) obj[1]).intValue() > 0) {
					if (BasicUtils.isEmpty(nursingEventStr)) {
						nursingEventStr += "bradycardia";
					} else {
						nursingEventStr += ", bradycardia";
					}
				}

				if (obj[2] != null && ((BigInteger) obj[2]).intValue() > 0) {
					if (BasicUtils.isEmpty(nursingEventStr)) {
						nursingEventStr += "disaturation";
					} else {
						nursingEventStr += ", disaturation";
					}
				}

				if (obj[3] != null && ((BigInteger) obj[3]).intValue() > 0) {
					if (BasicUtils.isEmpty(nursingEventStr)) {
						nursingEventStr += "seizures";
					} else {
						nursingEventStr += ", seizures";
					}
				}

				if (obj[4] != null && ((BigInteger) obj[4]).intValue() > 0) {
					if (BasicUtils.isEmpty(nursingEventStr)) {
						nursingEventStr += "tachycardia";
					} else {
						nursingEventStr += ", tachycardia";
					}
				}
			}
			doctorNotes.setNursingEventStr(nursingEventStr);

			// handle unique in diagnosis...
			String finalDiagnosisUnique = "";
			if (!BasicUtils.isEmpty(adminssionNotesDiagnosis)) {
				String[] finalDiagArr = adminssionNotesDiagnosis.split(",");
				for (String diag : finalDiagArr) {
					if (finalDiagnosisUnique.isEmpty()) {
						finalDiagnosisUnique = diag.replace(" ", "");
					} else {
						if (!finalDiagnosisUnique.contains(diag.replace(" ", ""))) {
							finalDiagnosisUnique += ", " + diag.replace(" ", "");
						}
					}
				}
			}
			doctorNotes.setDiagnosis(finalDiagnosisUnique.replace(",", "/"));

			// getting assessment data....
			java.util.Date dateFrom = null;
			java.util.Date dateTo = null;
			String uhid = "";
			String hoursTo = "08";
			String hoursFrom = "08";
			if (BasicUtils.isEmpty(doctorNotesPrintInfo.getDateFrom())) {
				dateFrom = new java.util.Date();
			} else {
				dateFrom = CalendarUtility.dateFormatUTF.parse(doctorNotesPrintInfo.getDateFrom());
			}
			if (BasicUtils.isEmpty(doctorNotesPrintInfo.getDateTo())) {

				dateTo = new java.util.Date();
			} else {
				dateTo = CalendarUtility.dateFormatUTF.parse(doctorNotesPrintInfo.getDateTo());
			}
			if (!BasicUtils.isEmpty(doctorNotesPrintInfo.getTimeFrom())) {
				hoursFrom = doctorNotesPrintInfo.getTimeFrom();
			}

			if (!BasicUtils.isEmpty(doctorNotesPrintInfo.getTimeTo())) {
				hoursTo = doctorNotesPrintInfo.getTimeTo();
			}
			uhid = doctorNotesPrintInfo.getUhid();
			List<String> moduleName = doctorNotesPrintInfo.getModuleName();
			if (BasicUtils.isEmpty(moduleName)) {
				moduleName = new ArrayList<String>() {
					{
						add("All");
					}
				};
			}

			if (!BasicUtils.isEmpty(moduleName) && !BasicUtils.isEmpty(uhid)) {
				Calendar calFrom = Calendar.getInstance();
				calFrom.setTime(dateFrom);
				// calFrom.set(Calendar.DAY_OF_MONTH,
				// calFrom.get(Calendar.DAY_OF_MONTH)-1);
				Calendar calTo = Calendar.getInstance();
				calTo.setTime(dateTo);

				// if (!BasicUtils.isEmpty(hoursFrom)) {
				// calFrom.set(Calendar.HOUR_OF_DAY,
				// Integer.valueOf(hoursFrom));
				// calFrom.set(Calendar.MINUTE, 0);
				// }
				//
				// if (!BasicUtils.isEmpty(hoursTo)) {
				// calTo.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hoursTo));
				// calTo.set(Calendar.MINUTE, 0);
				// }

				String strDateFrom = CalendarUtility.getTimeStampFormat(CalendarUtility.CLIENT_CRUD_OPERATION)
						.format(calFrom.getTime());
				String strDateTo = CalendarUtility.getTimeStampFormat(CalendarUtility.CLIENT_CRUD_OPERATION)
						.format(calTo.getTime());

				String strDateFromVital = CalendarUtility.getTimeStampFormat(CalendarUtility.CLIENT_CRUD_OPERATION)
						.format(calFrom.getTime().getTime() + 19800000);
				String strDateToVital = CalendarUtility.getTimeStampFormat(CalendarUtility.CLIENT_CRUD_OPERATION)
						.format(calTo.getTime().getTime() + 19800000);

				// get vital parameter string from nursing table
				String queryCurrentBabyVitals = "select obj from NursingVitalparameter as obj where uhid='" + uhid
						+ "' and entryDate <= '" + strDateToVital + "' and entryDate >= '" + strDateFromVital
						+ "' order by entryDate desc";
				List<NursingVitalparameter> currentBabyVitalList = (List<NursingVitalparameter>) inicuDoa
						.getListFromMappedObjRowLimitQuery(queryCurrentBabyVitals, 1);
				if (!BasicUtils.isEmpty(currentBabyVitalList)) {
					String vitalInfoStr = "";
					NursingVitalparameter latestVitalInfo = currentBabyVitalList.get(0);
					if (!BasicUtils.isEmpty(latestVitalInfo.getHrRate())) {
						if (vitalInfoStr == "") {
							vitalInfoStr = "HR: " + Float.valueOf(latestVitalInfo.getHrRate());
						} else {
							vitalInfoStr += ", HR: " + Float.valueOf(latestVitalInfo.getHrRate());
						}
					}
					if (!BasicUtils.isEmpty(latestVitalInfo.getRrRate())) {
						if (vitalInfoStr == "") {
							vitalInfoStr = "Pulse rate: " + Float.valueOf(latestVitalInfo.getRrRate());
						} else {
							vitalInfoStr += ", Pulse rate: " + Float.valueOf(latestVitalInfo.getRrRate());
						}
					}
					if (!BasicUtils.isEmpty(latestVitalInfo.getSpo2())) {
						if (vitalInfoStr == "") {
							vitalInfoStr = "SpO2: " + Float.valueOf(latestVitalInfo.getSpo2());
						} else {
							vitalInfoStr += ", SpO2: " + Float.valueOf(latestVitalInfo.getSpo2());
						}
					}
					if (!BasicUtils.isEmpty(latestVitalInfo.getMeanBp())) {
						if (vitalInfoStr == "") {
							vitalInfoStr = "Mean BP: " + Float.valueOf(latestVitalInfo.getMeanBp());
						} else {
							vitalInfoStr += ", Mean BP: " + Float.valueOf(latestVitalInfo.getMeanBp());
						}
					}
					doctorNotes.setVitalDetails(vitalInfoStr);
					doctorNotes.setNursingVitalObj(latestVitalInfo);
					int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
							- TimeZone.getDefault().getRawOffset();
					if (offset == 0) {
						doctorNotes.setVital_time(latestVitalInfo.getEntryDate());
					} else {
						Long newTime = latestVitalInfo.getEntryDate().getTime() + offset;
						doctorNotes.setVital_time(new Timestamp(newTime));
					}
				} else {
					// get vital parameter string from monitor table
					String queryVitalInfo = "";
					int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
							- TimeZone.getDefault().getRawOffset();
					if (offset == 0) {
						queryVitalInfo = "select obj from DeviceMonitorDetail as obj where uhid='" + uhid
								+ "' and creationtime <= '" + strDateToVital + "' and creationtime >= '"
								+ strDateFromVital + "' order by creationtime desc";
					} else {
						queryVitalInfo = "select obj from DeviceMonitorDetail as obj where uhid='" + uhid
								+ "' and creationtime <= '" + strDateTo + "' and creationtime >= '" + strDateFrom
								+ "' order by creationtime desc";
					}
					List<DeviceMonitorDetail> vitalInfo = (List<DeviceMonitorDetail>) inicuDoa
							.getListFromMappedObjRowLimitQuery(queryVitalInfo, 1);
					if (!BasicUtils.isEmpty(vitalInfo)) {
						String vitalInfoStr = "";
						DeviceMonitorDetail latestVitalInfo = vitalInfo.get(0);
						if (!BasicUtils.isEmpty(latestVitalInfo.getHeartrate())) {
							if (vitalInfoStr == "") {
								vitalInfoStr = "HR: " + Float.valueOf(latestVitalInfo.getHeartrate());
							} else {
								vitalInfoStr += ", HR: " + Float.valueOf(latestVitalInfo.getHeartrate());
							}
						}
						if (!BasicUtils.isEmpty(latestVitalInfo.getPulserate())) {
							if (vitalInfoStr == "") {
								vitalInfoStr = "Pulse rate: " + Float.valueOf(latestVitalInfo.getPulserate());
							} else {
								vitalInfoStr += ", Pulse rate: " + Float.valueOf(latestVitalInfo.getPulserate());
							}
						}
						if (!BasicUtils.isEmpty(latestVitalInfo.getSpo2())) {
							if (vitalInfoStr == "") {
								vitalInfoStr = "SpO2: " + Float.valueOf(latestVitalInfo.getSpo2());
							} else {
								vitalInfoStr += ", SpO2: " + Float.valueOf(latestVitalInfo.getSpo2());
							}
						}
						if (!BasicUtils.isEmpty(latestVitalInfo.getMeanBp())) {
							if (vitalInfoStr == "") {
								vitalInfoStr = "Mean BP: " + Float.valueOf(latestVitalInfo.getMeanBp());
							} else {
								vitalInfoStr += ", Mean BP: " + Float.valueOf(latestVitalInfo.getMeanBp());
							}
						}
						doctorNotes.setVitalDetails(vitalInfoStr);
						doctorNotes.setDeviceVitalObj(latestVitalInfo);
						doctorNotes.setVital_time(latestVitalInfo.getCreationtime());
					}
				}

				// getting baby basic visit details.....
				String queryCurrentBabyVisit = "select obj from BabyVisit as obj where uhid='" + uhid
						+ "' and to_char(visitdate,'yyyy-MM-dd') <= '" + strDateTo.substring(0, 10)
						+ "' and to_char(visitdate,'yyyy-MM-dd') >= '" + strDateFrom.substring(0, 10)
						+ "' order by visitdate desc";
				List<BabyVisit> currentBabyVisitList = notesDoa.getListFromMappedObjNativeQuery(queryCurrentBabyVisit);
				if (!BasicUtils.isEmpty(currentBabyVisitList)) {
					BabyVisit babyVisit = currentBabyVisitList.get(0);
					if (currentBabyVisitList.size() > 1) {
						BabyVisit preVisit = currentBabyVisitList.get(1);
						Float prevWeight = preVisit.getCurrentdateweight();
						babyVisit.setPrevDateWeight(prevWeight);
					}
					doctorNotes.setBasicDetails(babyVisit);
				}

				// intake information....
				// output information...............
				String pastIntakeOutputSql = "SELECT obj FROM NursingIntakeOutput as obj WHERE uhid='" + uhid
						+ "' and to_char(entry_timestamp,'yyyy-MM-dd HH:mm:ss') <='" + strDateTo
						+ "'  and to_char(entry_timestamp,'yyyy-MM-dd HH:mm:ss') >='" + strDateFrom
						+ "' order by entry_timestamp desc";

				List<NursingIntakeOutput> pastNursingIntakeList = inicuDoa
						.getListFromMappedObjQuery(pastIntakeOutputSql);
				Float urineOutput = null;
				Float numberOfStool = null;
				Float drainOutput = null;
				Float enTotal = null;
				Float pnTotal = null;
				Float lipidTotal = null;
				Float tpnTotal = null;
				if (!BasicUtils.isEmpty(pastNursingIntakeList)) {

					for (NursingIntakeOutput nursingIntakeOutput : pastNursingIntakeList) {

						if (!BasicUtils.isEmpty(nursingIntakeOutput.getLipid_delivered())) {
							if (lipidTotal == null) {
								lipidTotal = nursingIntakeOutput.getLipid_delivered();
							}
						}
						if (!BasicUtils.isEmpty(nursingIntakeOutput.getTpn_delivered())) {
							if (tpnTotal == null) {
								tpnTotal = nursingIntakeOutput.getTpn_delivered();
							}
						}

						if (!BasicUtils.isEmpty(nursingIntakeOutput.getUrine())) {
							if (urineOutput == null) {
								urineOutput = Float.valueOf((nursingIntakeOutput.getUrine()));
							} else {
								urineOutput = urineOutput + Float.valueOf((nursingIntakeOutput.getUrine()));
							}
						}

						if (!BasicUtils.isEmpty(nursingIntakeOutput.getStool())) {
							if (numberOfStool == null) {
								numberOfStool = Float.valueOf("1");
							} else {
								numberOfStool += 1;
							}
						}
						if (!BasicUtils.isEmpty(nursingIntakeOutput.getDrain())) {
							if (drainOutput == null) {
								drainOutput = Float.valueOf(nursingIntakeOutput.getDrain());
							} else {
								drainOutput += Float.valueOf(nursingIntakeOutput.getDrain());
							}
						}

						// for en total ..
						if (!BasicUtils.isEmpty(nursingIntakeOutput.getPrimaryFeedValue())) {
							if (enTotal == null) {
								enTotal = nursingIntakeOutput.getPrimaryFeedValue();
							} else {
								enTotal += nursingIntakeOutput.getPrimaryFeedValue();
							}
						}
						if (!BasicUtils.isEmpty(nursingIntakeOutput.getFormulaValue())) {
							if (enTotal == null) {
								enTotal = nursingIntakeOutput.getFormulaValue();
							} else {
								enTotal += nursingIntakeOutput.getFormulaValue();
							}
						}
					}
					if (tpnTotal != null) {
						pnTotal = tpnTotal;
					}
					if (lipidTotal != null) {
						if (pnTotal == null) {
							pnTotal = lipidTotal;
						} else {
							pnTotal += lipidTotal;
						}
					}
				}

				HashMap<String, Object> inputDetails = new HashMap<String, Object>();
				inputDetails.put("enTotal", enTotal);
				inputDetails.put("pnTotal", pnTotal);
				doctorNotes.setIntakeDetails(inputDetails);
				HashMap<String, Object> outputDetails = new HashMap<String, Object>();
				outputDetails.put("urineOutput", urineOutput);
				outputDetails.put("numberOfStool", numberOfStool);
				outputDetails.put("drainOutput", drainOutput);

				doctorNotes.setOutputDetails(outputDetails);

				String queryAssessmentData = "select obj from VwDoctornotesListFinal as obj where uhid='" + uhid
						+ "' and " + "to_char(creationtime,'yyyy-MM-dd HH:mm:ss')>='" + strDateFrom + "' and "
						+ "to_char(creationtime,'yyyy-MM-dd HH:mm:ss')<='" + strDateTo + "' ";
				String innerQueryStr = "";
				if (!BasicUtils.isEmpty(moduleName)) {
					for (String module : moduleName) {
						if (!BasicUtils.isEmpty(module)) {
							if (module.equalsIgnoreCase("All")) {
								// no filter ...

							} else {
								if (innerQueryStr.isEmpty()) {
									innerQueryStr = doctorNotes.getSystemEventsMapping().get(module);
								} else {
									innerQueryStr += ", " + doctorNotes.getSystemEventsMapping().get(module);
								}
							}
						}
					}

					String queryAssessment = "SELECT assesmenttreatmentid, treatment FROM ref_assesment_treatment";
					List<KeyValueObj> assessmentDropList = getRefObj(queryAssessment);
					KeyValueObj otherTreatment = new KeyValueObj();
					otherTreatment.setKey("Other");
					otherTreatment.setValue("Other");
					assessmentDropList.add(otherTreatment);
					HashMap<Object, Object> treatmentMap = new HashMap<Object, Object>();
					for (KeyValueObj assessment : assessmentDropList) {
						treatmentMap.put(assessment.getKey(), assessment.getValue());
					}

					if (!BasicUtils.isEmpty(innerQueryStr.isEmpty()) && !innerQueryStr.equalsIgnoreCase("")) {
						queryAssessmentData += " and saEvent in (" + innerQueryStr + ")";

						queryAssessmentData += " order by creationtime desc";
						List<VwDoctornotesListFinal> assessmentDetails = inicuDoa
								.getListFromMappedObjQuery(queryAssessmentData);
						if (!BasicUtils.isEmpty(assessmentDetails)) {
							for (VwDoctornotesListFinal assessment : assessmentDetails) {
								if (assessment.getSaEvent().equalsIgnoreCase("Jaundice")) {
									assessment.setSystemName("Jaundice");
								} else if (assessment.getSaEvent().equalsIgnoreCase("RDS")
										|| assessment.getSaEvent().equalsIgnoreCase("Pneumothorax")
										|| assessment.getSaEvent().equalsIgnoreCase("Apnea")
										|| assessment.getSaEvent().equalsIgnoreCase("PPHN")) {
									assessment.setSystemName("RespSystems");
								} else if (assessment.getSaEvent().equalsIgnoreCase("Stable Notes")) {
									assessment.setSystemName("Stable Notes");
								} else if (assessment.getSaEvent().equalsIgnoreCase("Sepsis")
										|| assessment.getSaEvent().equalsIgnoreCase("VAP")
										|| assessment.getSaEvent().equalsIgnoreCase("CLABSI")) {
									assessment.setSystemName("Infection");
								} else if (assessment.getSaEvent().equalsIgnoreCase("Asphyxia")
										|| assessment.getSaEvent().equalsIgnoreCase("Seizures")) {
									assessment.setSystemName("CNS");
								} else if (assessment.getSaEvent().equalsIgnoreCase("Misc")) {
									assessment.setSystemName("Misc");
								}
								// calculate here treatment ....
								String treatments = assessment.getTreatment();
								if (!BasicUtils.isEmpty(treatments)) {
									String[] treatmentsList = treatments.replace("[", "").replace("]", "")
											.replace(" ", "").split(",");
									String treatmentString = "";
									for (String treatment : treatmentsList) {
										if (BasicUtils.isEmpty(treatmentMap.get(treatment))) {
											treatmentString += ", " + treatment;
										} else {
											treatmentString += ", " + treatmentMap.get(treatment).toString();
										}
									}
									assessment.setTreatment(treatmentString.substring(2));
								}
							}
							if (!BasicUtils.isEmpty(doctorNotesPrintInfo.getIsNotesBySystem())) {
								if (doctorNotesPrintInfo.getIsNotesBySystem()) {// system
																				// wise
																				// notes..
									HashMap<String, List<VwDoctornotesListFinal>> systemWise = new HashMap<String, List<VwDoctornotesListFinal>>();
									for (VwDoctornotesListFinal assessment : assessmentDetails) {
										String assessmentType = assessment.getSystemName();
										if (systemWise.get(assessmentType) == null) {
											systemWise.put(assessmentType, new ArrayList<VwDoctornotesListFinal>() {
												{
													add(assessment);
												}
											});
										} else {
											List<VwDoctornotesListFinal> arrayList = systemWise.get(assessmentType);
											boolean flagFound = false;
											for (VwDoctornotesListFinal item : arrayList) {
												String oldNoteTime = CalendarUtility.timeStampFormatUI
														.format(item.getCreationtime());
												String newNoteTime = CalendarUtility.timeStampFormatUI
														.format(assessment.getCreationtime());
												if (oldNoteTime.equalsIgnoreCase(newNoteTime) && item.getSaEvent()
														.trim().equalsIgnoreCase(assessment.getSaEvent())) {
													flagFound = true;
												}
											}
											if (!flagFound) {
												arrayList.add(assessment);
											}
											systemWise.put(assessmentType, arrayList);
										}
									}
									doctorNotes.setAssessmentDetails(systemWise);
								} else {// date wise notes...
									HashMap<String, List<VwDoctornotesListFinal>> datemWise = new HashMap<String, List<VwDoctornotesListFinal>>();
									for (VwDoctornotesListFinal assessment : assessmentDetails) {
										String assessmentTime = CalendarUtility.dateFormatDB
												.format(assessment.getCreationtime());
										if (datemWise.get(assessmentTime) == null) {
											datemWise.put(assessmentTime, new ArrayList<VwDoctornotesListFinal>() {
												{
													add(assessment);
												}
											});
										} else {
											List<VwDoctornotesListFinal> arrayList = datemWise.get(assessmentTime);
											boolean flagFound = false;
											for (VwDoctornotesListFinal item : arrayList) {
												String oldNoteTime = CalendarUtility.timeStampFormatUI
														.format(item.getCreationtime());
												String newNoteTime = CalendarUtility.timeStampFormatUI
														.format(assessment.getCreationtime());
												if (oldNoteTime.equalsIgnoreCase(newNoteTime) && item.getSaEvent()
														.trim().equalsIgnoreCase(assessment.getSaEvent())) {
													flagFound = true;
												}
											}
											if (!flagFound) {
												arrayList.add(assessment);
											}
											datemWise.put(assessmentTime, arrayList);
										}
									}
									doctorNotes.setAssessmentDetails(datemWise);
								}
							}
						}
					}
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return doctorNotes;
	}

	public List getRefObj(String query) {
		List<KeyValueObj> refKeyValueList = new ArrayList<KeyValueObj>();
		try {
			// String query = "select obj.bpid,obj.blood_product from
			// kalawati.ref_blood_product obj";
			List<Object[]> refList = inicuDoa.getListFromNativeQuery(query);
			KeyValueObj keyValue = null;
			if (refList != null && !refList.isEmpty()) {
				Iterator<Object[]> iterator = refList.iterator();
				while (iterator.hasNext()) {
					keyValue = new KeyValueObj();
					Object[] obj = iterator.next();
					if (obj != null && obj[0] != null)
						keyValue.setKey(obj[0]);
					if (obj != null && obj[1] != null)
						keyValue.setValue(obj[1]);
					refKeyValueList.add(keyValue);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return refKeyValueList;
	}


	@Override
	public NursingBloodGas getBabyBloodGasInfoBySampleId(String uhid, String orderID) {
		String query = "select n from DeviceBloodgasDetailDetail as n where sampleId='" + orderID
				+ "' order by creationtime desc";
		NursingBloodGas bloodgas = new NursingBloodGas();
		try {
			List<DeviceBloodgasDetailDetail> bloodGasList = inicuDoa.getListFromMappedObjQuery(query);
			if (!BasicUtils.isEmpty(bloodGasList)) {
				DeviceBloodgasDetailDetail obj = bloodGasList.get(0);
				if (!(BasicUtils.isEmpty(obj) || BasicUtils.isEmpty(obj.getHl7Message()))) {

					String dateStr = "";
					String dateStrTemp = "";

					String[] strArr = obj.getHl7Message().split("\\|");
					for (int index = 0; index < strArr.length; index++) {
						String str = strArr[index];
						// System.out.println(str + " there");
						if (strArr[index].contains("OBR") && strArr[index + 1].contains("1")) {
							System.out.println(strArr[index + 4] + " Method");
							bloodgas.setSampleMethod(strArr[index + 4]);
						}
						if (strArr[index].contains("pH^M")) {
							System.out.println(strArr[index + 2] + " pH");
							bloodgas.setPh(strArr[index + 2]);
						}
						if (strArr[index].contains("pCO2^M")) {
							System.out.println(strArr[index + 2] + " pCO2");
							bloodgas.setPco2(strArr[index + 2]);
						}
						if (strArr[index].contains("pO2^M")) {
							System.out.println(strArr[index + 2] + " pO2");
							bloodgas.setPo2(strArr[index + 2]);
						}
						if (strArr[index].contains("Na+")) {
							System.out.println(strArr[index + 2] + " Na+");
							bloodgas.setNa(strArr[index + 2]);
						}
						if (strArr[index].contains("Glu")) {
							System.out.println(strArr[index + 2] + " Glu");
							bloodgas.setGlucose(strArr[index + 2]);
						}
						if (strArr[index].contains("K+^M")) {
							System.out.println(strArr[index + 2] + " K+");
							bloodgas.setK(strArr[index + 2]);
						}
						if (strArr[index].contains("Cl-")) {
							System.out.println(strArr[index + 2] + " Cl-");
							bloodgas.setCl(strArr[index + 2]);
						}
						if (strArr[index].contains("Ca++")) {
							System.out.println(strArr[index + 2] + " Ca++");
							bloodgas.setIonized_calcium(strArr[index + 2]);
						}
						if (strArr[index].contains("Lac")) {
							System.out.println(strArr[index + 2] + " Lac");
							bloodgas.setLactate(strArr[index + 2]);
						}
						if (strArr[index].contains("gap^C")) {
							System.out.println(strArr[index + 2] + " gap^C");
							bloodgas.setAnionGap(strArr[index + 2]);
						}
						if (strArr[index].contains("SBE^C")) {
							System.out.println(strArr[index + 2] + " BE^C");
							bloodgas.setBe_ecf(strArr[index + 2]);
						}
						if (strArr[index].contains("SBC^C")) {
							System.out.println(strArr[index + 2] + " HCO-3");
							bloodgas.setHco2(strArr[index + 2]);
						}
						if (strArr[index].contains("Osm^C")) {
							System.out.println(strArr[index + 2] + " Osm^C");
							bloodgas.setOsmolarity(strArr[index + 2]);
						}
						if (strArr[index].contains("sO2^M")) {
							System.out.println(strArr[index + 2] + " sO2^M");
							bloodgas.setSpo2(strArr[index + 2]);
						}
						if (strArr[index].equals("L") && strArr[index + 3].equals("F") && dateStr != null
								&& dateStr.length() <= 0) {
							dateStr = strArr[index + 6];
						}
						if (strArr[index].equals("H") && strArr[index + 3].equals("F") && dateStr != null
								&& dateStr.length() <= 0) {
							dateStr = strArr[index + 6];
						}
						if (strArr[index].equals("N") && strArr[index + 3].equals("F") && dateStr != null
								&& dateStr.length() <= 0) {
							dateStr = strArr[index + 6];
						}
						if (strArr[index].contains("Cord Blood")) {
							System.out.println("Cord Blood Venous");
							bloodgas.setSampleType("Cord Blood Venous");
						} else if (strArr[index].contains("Arterial")) {
							System.out.println("Arterial");
							bloodgas.setSampleType("Arterial");
						} else if (strArr[index].contains("Venous")) {
							System.out.println("Venous");
							bloodgas.setSampleType("Venous");
						}
					}

					if (BasicUtils.isEmpty(dateStr)) {
						dateStr = dateStrTemp;
					}
					bloodgas.setLoggeduser("test");

					if (!BasicUtils.isEmpty(dateStr)) {
						System.out.println(dateStr + " date");

						Timestamp today = null;
						String year = dateStr.charAt(0) + "" + dateStr.charAt(1) + "" + dateStr.charAt(2) + ""
								+ dateStr.charAt(3);
						String month = dateStr.charAt(4) + "" + dateStr.charAt(5);
						String date = dateStr.charAt(6) + "" + dateStr.charAt(7);
						String hour = dateStr.charAt(8) + "" + dateStr.charAt(9);
						String min = dateStr.charAt(10) + "" + dateStr.charAt(11);
						String sec = dateStr.charAt(12) + "" + dateStr.charAt(13);
						String entryDate = year + "-" + month + "-" + date + " " + hour + ":" + min + ":" + sec;

						today = Timestamp.valueOf(entryDate);
						String dateString = year + "-" + month + "-" + date;

						System.out.println(today);
						System.out.println(dateString);

						bloodgas.setUserDate(dateString);
						String meridian = "AM";
						if (today.getHours() >= 12) {
							meridian = "PM";
						}
						String nn_blood_time = today.getHours() + ":" + today.getMinutes() + ":" + meridian;
						bloodgas.setNnBloodgasTime(nn_blood_time);

						String zone_time = "GMT+5:30";
						int offset = TimeZone.getTimeZone(zone_time).getRawOffset()
								- TimeZone.getDefault().getRawOffset();

						System.out.println(offset + "offset");

						bloodgas.setEntryDate((new Timestamp(today.getTime() + offset)));
						bloodgas.setCreationtime((new Timestamp(today.getTime() + offset)));
						bloodgas.setModificationtime((new Timestamp(today.getTime() + offset)));

					}

				}
			} else {
			}
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUser,
					uhid, "GET_OBJECT Blood Gas by Sample ID", BasicUtils.convertErrorStacktoString(e));
		}
		return bloodgas;
	}

	@Override
	public NursingDailyProgressPojo getAnthropometry(String uhid) {

		NursingDailyProgressPojo anthropometryObj = new NursingDailyProgressPojo();
		try {
			String queryVwDoctorNoteDOB = "select obj from BabyDetail obj where uhid='" + uhid + "'";
			List<BabyDetail> listDoctorNotesDOB = notesDoa.getListFromMappedObjNativeQuery(queryVwDoctorNoteDOB);

			if (listDoctorNotesDOB != null && listDoctorNotesDOB.size() > 0) {
				BabyVisit babyVisit = new BabyVisit();

				BabyDetail babyDetails = listDoctorNotesDOB.get(0);
				java.util.Date presentDate = new java.util.Date();
				Date sqlPresentDate = new java.sql.Date(presentDate.getTime());

				long diff = presentDate.getTime() - babyDetails.getDateofbirth().getTime();
				long ageDays = (diff / (24 * 60 * 60 * 1000)) + 1;// days..
				long ageMonth = ageDays / (30);
				// long ageYear =diff/(365*30*24*60*60*1000);
				if (ageMonth > 0)
					babyVisit.setCurrentage(ageMonth + "months " + (ageDays - (ageMonth * 30)) + "days");
				else
					babyVisit.setCurrentage(ageDays + "days");

				Integer gestationDay = babyDetails.getGestationdaysbylmp();
				Integer gestationWeek = babyDetails.getGestationweekbylmp();
				if (!BasicUtils.isEmpty(gestationDay) && !BasicUtils.isEmpty(gestationWeek)) {
					if (ageDays > 7) {
						long lifeWeeks = ageDays / 7;
						System.out.println("gestation" + lifeWeeks);
						long lifeDays = ageDays - lifeWeeks * 7;
						System.out.println("gestationing" + lifeDays);
						long totalDaysOfGestation = gestationDay + lifeDays;
						System.out.println("gestationing" + totalDaysOfGestation);
						gestationWeek += Integer.valueOf(lifeWeeks + "");
						if (totalDaysOfGestation > 6) {
							long weeksAfterDayAdd = totalDaysOfGestation / 7;
							gestationWeek += Integer.valueOf(weeksAfterDayAdd + "");
							gestationDay = Integer.valueOf((totalDaysOfGestation - weeksAfterDayAdd * 7) + "");
						} else {
							gestationDay = Integer.valueOf(totalDaysOfGestation + "");
						}
					} else {
						long totalDaysOfGestation = gestationDay + gestationDay;
						if (totalDaysOfGestation > 6) {
							long weeksAfterDayAdd = totalDaysOfGestation / 7;
							gestationWeek += Integer.valueOf(weeksAfterDayAdd + "");
							gestationDay = Integer.valueOf((totalDaysOfGestation - weeksAfterDayAdd * 7) + "");
						} else {
							gestationDay = Integer.valueOf(totalDaysOfGestation + "");
						}
					}
					babyVisit.setGestationWeek(gestationWeek);
					babyVisit.setGestationDays(gestationDay);

					//set Working For Calculation
					String queryChildWeightForCal = "select weight_for_cal from baby_visit obj where uhid ='" + uhid
					+ "' order by creationtime desc";
					List<Float> resultWeightForCalSet = userServiceDao.executeNativeQuery(queryChildWeightForCal);
					Float prevWeightForCal = null;
					if (!BasicUtils.isEmpty(resultWeightForCalSet)) {
						prevWeightForCal = resultWeightForCalSet.get(0);
					}
					babyVisit.setWeightForCal(prevWeightForCal);
				}

				anthropometryObj.setBabyVisit(babyVisit);

				int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
						- TimeZone.getDefault().getRawOffset();
				// get birth visit info....
                java.util.Date dateOfB = babyDetails.getDateofbirth();
				String timeOfBirth = babyDetails.getTimeofbirth();
				Calendar birthDateCal = getDateTimeFromDateAndTime(dateOfB, timeOfBirth);

				java.sql.Date sqlDob = new Date(dateOfB.getTime());
				String queryBirhtBabyVisit = "select obj from BabyVisit as obj where uhid='" + uhid
						+ "' and visitdate='" + sqlDob + "'";
				List<BabyVisit> birhtBabyVisitList = notesDoa.getListFromMappedObjNativeQuery(queryBirhtBabyVisit);
				if (!BasicUtils.isEmpty(birhtBabyVisitList)) {
					BabyVisit birthBabyVisit = birhtBabyVisitList.get(0);
                    birthBabyVisit.setDateOfBirth(String.valueOf(birthDateCal.getTimeInMillis()));
					birthBabyVisit.setEntryDateOfBabyVisit(new Timestamp(BasicUtils.getDateTimeFromTime(birthBabyVisit.getVisitdate(),birthBabyVisit.getVisittime()).getTime() - offset));
					anthropometryObj.setBirthBabyVisit(birthBabyVisit);
				} else {
					BabyVisit babyVisitEmpty = new BabyVisit();
					anthropometryObj.setBirthBabyVisit(babyVisitEmpty);
				}

				// get all baby visit details....
				String queryBabyVisitAll = "select obj from BabyVisit as obj where uhid='" + uhid + "' and visitdate>='"
						+ sqlDob + "' order by creationtime desc";
				List<BabyVisit> babyVisitListAll = notesDoa.getListFromMappedObjNativeQuery(queryBabyVisitAll);
				if (!BasicUtils.isEmpty(babyVisitListAll)) {

					for (BabyVisit babyObject:babyVisitListAll) {
						babyObject.setEntryDateOfBabyVisit(new Timestamp(BasicUtils.getDateTimeFromTime(babyObject.getVisitdate(),babyObject.getVisittime()).getTime() - offset));
					}
					anthropometryObj.setListBabyVisit(babyVisitListAll);
				}

				String csvData = "select obj from VwBabyVisitCSVData as obj where uhid = '" + uhid + "' and entrydate >= '" + sqlDob + "' order by entrydate,entrytime desc";
				List<VwBabyVisitCSVData> csvListData = notesDoa.getListFromMappedObjNativeQuery(csvData);
				if (!BasicUtils.isEmpty(csvListData)) {
					anthropometryObj.setListcsvData(csvListData);
				}

				// get the anthropometry progress rates
				AnthropometryProgressRate anthropometryProgressRate=userPanel.getAnthropometryGrowthRate(uhid);
				if(anthropometryProgressRate!=null){
					anthropometryObj.setAnthropometryProgressRate(anthropometryProgressRate);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return anthropometryObj;
	}

	public Calendar getDateTimeFromDateAndTime(java.util.Date date, String time) {

		Calendar cal = Calendar.getInstance();
		String[] timeArr = new String[3];
		if (!BasicUtils.isEmpty(time)) {
			timeArr = time.split(",");
		}
		cal.setTime(date);
		//System.out.println(birthDateCal.getTime());
		if (timeArr.length >= 3 && !BasicUtils.isEmpty(timeArr[1])) {

			if (!BasicUtils.isEmpty(timeArr[2]) && timeArr[2].equalsIgnoreCase("AM")) {
				if (!BasicUtils.isEmpty(timeArr[0])) {
					if (Integer.valueOf(timeArr[0]) == 12) {
						cal.set(Calendar.HOUR, Integer.valueOf(timeArr[0]) - 12);
					} else {
						cal.set(Calendar.HOUR, Integer.valueOf(timeArr[0]));
					}
				}
			} else if (!BasicUtils.isEmpty(timeArr[2]) && timeArr[2].equalsIgnoreCase("PM")) {
				if (!BasicUtils.isEmpty(timeArr[0])) {
					if (Integer.valueOf(timeArr[0]) == 12) {
						cal.set(Calendar.HOUR, Integer.valueOf(timeArr[0]));
					} else {
						cal.set(Calendar.HOUR, Integer.valueOf(timeArr[0]) + 12);
					}
				}
			}
			if (!BasicUtils.isEmpty(timeArr[1]))
				cal.set(Calendar.MINUTE, Integer.valueOf(timeArr[1]));
		} else {
			cal.set(Calendar.HOUR, Integer.valueOf("0"));
			cal.set(Calendar.MINUTE, Integer.valueOf("0"));
			cal.set(Calendar.SECOND, Integer.valueOf("0"));
		}
		return cal;
	}



	@Override
	public List<ExportAnthropometryCsvData> getAnthropometryCsvData(String uhid) {
		List<VwBabyVisitCSVData>  babyVisitList = new ArrayList<>();
		List<ExportAnthropometryCsvData> csvBabyVisitList = new ArrayList<>();
		String queryeDOB = "select obj from BabyDetail obj where uhid='" + uhid + "'";
		List<BabyDetail> listDoctorNotesDOB = notesDoa.getListFromMappedObjNativeQuery(queryeDOB);
		BabyDetail babyDetails = listDoctorNotesDOB.get(0);
		java.util.Date dob = babyDetails.getDateofbirth();
		java.sql.Date sqlDob = new Date(dob.getTime());

		String fetchBedData = "SELECT obj FROM VwBabyVisitCSVData as obj WHERE uhid = '" + uhid + "' and entrydate >= '" + sqlDob + "' order by entrydate,entrytime desc";
		List<VwBabyVisitCSVData> resultSet = userServiceDao.executeQuery(fetchBedData);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		String query = "select obj from AntenatalHistoryDetail as obj where uhid = '" + uhid +
				 "'";
		List<AntenatalHistoryDetail> resSet = inicuDoa.getListFromMappedObjQuery(query);

		if (!BasicUtils.isEmpty(resultSet)) {
			for (VwBabyVisitCSVData d : resultSet) {
				ExportAnthropometryCsvData obj = new ExportAnthropometryCsvData();


				String babyUhid = d.getUhid();
				if(!BasicUtils.isEmpty(babyUhid))
					obj.setUHID(babyUhid);

				String babyName = d.getBabyname();
				if(!BasicUtils.isEmpty(babyName))
					obj.setBABY_NAME(babyName);

//				String gaAtBirth = d.getGa_at_birth();
//				if(!BasicUtils.isEmpty(gaAtBirth))
//					obj.setGA_AT_BIRTH(gaAtBirth);

				String gestWeeks = d.getGestationweek().toString();
				String gestDays = d.getGestationdays().toString();
				if(!BasicUtils.isEmpty(gestWeeks))
					obj.setCORRECTED_GA(gestWeeks
							+ ";" + gestDays );


				java.util.Date entryDate = d.getEntrydate();
				String visitdate = dateFormat.format(entryDate);
				if(!BasicUtils.isEmpty(visitdate))
					obj.setENTRY_DATE(visitdate);

				Time entryTime = d.getEntrytime();
				if(!BasicUtils.isEmpty(entryTime))
					obj.setENTRY_TIME(entryTime);

				String nicudays = d.getNicuday();
				if(!BasicUtils.isEmpty(nicudays)) {
					nicudays = nicudays + " days";
					obj.setNICU_DAY(nicudays);
				}
				else {
					java.sql.Date today = new Date(new java.util.Date().getTime());
					Long nicuday = ((today.getTime() - d.getDateofadmission().getTime())/(1000*60*60*24));
					String ndays = nicuday.toString() + " days";
					obj.setNICU_DAY(ndays);
				}

				String age = d.getAge();
				if(!BasicUtils.isEmpty(age))
					obj.setDOL(age);

				Float weight = d.getWeight();
				if(!BasicUtils.isEmpty(weight)) {
					String wt = weight.toString() + " grams";
					obj.setWEIGHT(wt);
				}

				Float height = d.getHeight();
				if(!BasicUtils.isEmpty(height)) {
					String ht = height.toString() + " cms";
					obj.setHEIGHT(ht);
				}

				Float headcircumferrence = d.getHeadcircumferrence();
				if(!BasicUtils.isEmpty(headcircumferrence)) {
					String hc = headcircumferrence.toString() + " cms";
					obj.setHEAD_CIRCUMFERRENCE(hc);
				}

				Float workingweight = d.getWorkingweight();
				if(!BasicUtils.isEmpty(workingweight)) {
					String ww = workingweight.toString() + " grams";
					obj.setWORKING_WEIGHT(ww);
				}

				Float weightforcal = d.getWeightforcal();
				if(!BasicUtils.isEmpty(weightforcal)) {
					String wc = weightforcal.toString()
							+ " grams";
					obj.setWEIGHT_FOR_CAL(wc);
				}

				if(!BasicUtils.isEmpty(resSet)) {
					obj.setGA_AT_BIRTH(resSet.get(0).getGestationbyLmpWeeks().toString() + ";" + resSet.get(0).getGestationbyLmpDays().toString());
				}

				csvBabyVisitList.add(obj);


			}
		}



		return csvBabyVisitList;



	}

	@Override
	@SuppressWarnings("unchecked")
	public NursingVitalPrintJSON getNursingVitalPrint(String uhid, String fromTime, String toTime) {
		NursingVitalPrintJSON returnObj = new NursingVitalPrintJSON();
		try {

			returnObj.setFromTime(Long.parseLong(fromTime));
			returnObj.setToTime(Long.parseLong(toTime));

			int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
					- TimeZone.getDefault().getRawOffset();

			System.out.println(offset + "offset");

			Timestamp fromDate = new Timestamp(Long.parseLong(fromTime) + offset);
			Timestamp toDate = new Timestamp(Long.parseLong(toTime) + offset);
			toDate.setMinutes(toDate.getMinutes()+3);
			fromDate.setMinutes(fromDate.getMinutes()+2);

			System.out.println("aa");
			String vitalSql = HqlSqlQueryConstants.getNursingVitalList(uhid, fromDate, toDate);
			List<NursingVitalparameter> vitalList = inicuDoa.getListFromMappedObjQuery(vitalSql);
			for (NursingVitalparameter obj : vitalList) {
				System.out.println(obj.getEntryDate() + "offset Episode");
				obj.setEntryDate(new Timestamp(obj.getEntryDate().getTime() - offset));
			}
			returnObj.setVitalList(vitalList);

			String babySql = "select dateofadmission from baby_detail where uhid='" + uhid + "' order by creationtime";
			List<Date> babyDetail = inicuDoa.getListFromNativeQuery(babySql);
			if (!BasicUtils.isEmpty(babyDetail)) {
				returnObj.setDateofAdmission(babyDetail.get(0).getTime());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnObj;
	}

	@Override
	@SuppressWarnings("unchecked")
	public AnthropometryPrintJSON getAnthropometryPrint(String uhid, String fromTime, String toTime) {
		AnthropometryPrintJSON returnObj = new AnthropometryPrintJSON();
		try {
			returnObj.setFromTime(Long.parseLong(fromTime));
			returnObj.setToTime(Long.parseLong(toTime));

			Timestamp fromDate = new Timestamp(Long.parseLong(fromTime));
			Timestamp toDate = new Timestamp(Long.parseLong(toTime));

			System.out.println("aa");
			String visitSql = HqlSqlQueryConstants.getAnthropometryList(uhid, fromDate, toDate);
			List<BabyVisit> visitList = inicuDoa.getListFromMappedObjQuery(visitSql);
			returnObj.setVisitList(visitList);

			String babySql = "select dateofadmission from baby_detail where uhid='" + uhid + "' order by creationtime";
			List<Date> babyDetail = inicuDoa.getListFromNativeQuery(babySql);
			if (!BasicUtils.isEmpty(babyDetail)) {
				returnObj.setDateofAdmission(babyDetail.get(0).getTime());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnObj;
	}

	@Override
	@SuppressWarnings("unchecked")
	public NursingEventPrintJSON getNursingEventPrint(String uhid, String fromTime, String toTime) {
		NursingEventPrintJSON returnObj = new NursingEventPrintJSON();
		try {

			returnObj.setFromTime(Long.parseLong(fromTime));
			returnObj.setToTime(Long.parseLong(toTime));

			int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
					- TimeZone.getDefault().getRawOffset();

			Timestamp fromDate = new Timestamp(Long.parseLong(fromTime) + offset);
			Timestamp toDate = new Timestamp(Long.parseLong(toTime) + offset);

			toDate.setMinutes(toDate.getMinutes()+3);
			fromDate.setMinutes(fromDate.getMinutes()+2);

			String eventSql = HqlSqlQueryConstants.getNursingEventList(uhid, fromDate, toDate);
			List<NursingEpisode> eventList = inicuDoa.getListFromMappedObjQuery(eventSql);

			for (NursingEpisode obj : eventList) {
				System.out.println(obj.getCreationtime().getTime() + "offset Episode");
				obj.setCreationtime(new Timestamp(obj.getCreationtime().getTime() - offset));
			}
			returnObj.setEpisodeList(eventList);

			String babySql = "select dateofadmission from baby_detail where uhid='" + uhid + "' order by creationtime";
			List<Date> babyDetail = inicuDoa.getListFromNativeQuery(babySql);
			if (!BasicUtils.isEmpty(babyDetail)) {
				returnObj.setDateofAdmission(babyDetail.get(0).getTime());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnObj;
	}

	@Override
	@SuppressWarnings("unchecked")
	public NursingNotesPojo getNursingNotes(String uhid, String fromTime, String toTime) {
		NursingNotesPojo returnObj = new NursingNotesPojo();
		NursingNote currentNotes = returnObj.getCurrentNotes();

		List<NursingNote> notesList = inicuDoa
				.getListFromMappedObjQuery(HqlSqlQueryConstants.getActiveNursingNotesList(uhid));
//		List<NursingNote> allNotesList = new ArrayList<NursingNote>();
//		for (NursingNote item : notesList) {
////			item.setFrom_time(new Timestamp(item.getFrom_time().getTime() - offset));
////			item.setTo_time(new Timestamp(item.getTo_time().getTime() - offset));
//			if(item.getFlag() == true) {
//				allNotesList.add(item);
//			}
//		}
//		returnObj.setPastNotesList(allNotesList);
		returnObj.setPastNotesList(notesList);

		if (!(BasicUtils.isEmpty(fromTime) || BasicUtils.isEmpty(toTime))) {
			SimpleDateFormat sdfDB = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
			int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
					- TimeZone.getDefault().getRawOffset();

			try {
				Timestamp yestDate = new Timestamp((Long.parseLong(fromTime) - offset) - (24 * 60 * 60 * 1000));
				Timestamp fromDate = new Timestamp(Long.parseLong(fromTime) - offset);
				Timestamp toDate = new Timestamp(Long.parseLong(toTime) - offset);

				String yestStr = sdfDB.format(yestDate);
				String todayStr = sdfDB.format(fromDate);

				currentNotes.setUhid(uhid);
				currentNotes.setFrom_time(fromDate);
				currentNotes.setTo_time(toDate);

				String notesText = "";
				String cgaStr = "";
				Integer gestationWeeks = 40;
				List<BabyDetail> babyDetailList = inicuDoa
						.getListFromMappedObjQuery(HqlSqlQueryConstants.getBabyDetailList(uhid));
				if (!BasicUtils.isEmpty(babyDetailList)) {
					BabyDetail babyDetails = babyDetailList.get(0);
					if (!BasicUtils.isEmpty(babyDetails.getBirthweight())) {
						notesText += "Birth Weight: " + babyDetailList.get(0).getBirthweight() + " gms \t";
					}

					if (!BasicUtils.isEmpty(babyDetails.getActualgestationweek())) {
						gestationWeeks = babyDetails.getActualgestationweek();
						java.util.Date dateOfBirth = babyDetails.getDateofbirth();
						long gestationDaysAfterBirth = (fromDate.getTime() - dateOfBirth.getTime())
								/ (24 * 60 * 60 * 1000);

						int dischargeGetationDays = 0;
						if (!BasicUtils.isEmpty(babyDetails.getActualgestationweek())) {
							dischargeGetationDays = babyDetails.getActualgestationweek() * 7;

						}
						if (!BasicUtils.isEmpty(babyDetails.getActualgestationdays())) {
							dischargeGetationDays += babyDetails.getActualgestationdays();
						}

						long currentGestaionTotalDays = gestationDaysAfterBirth + dischargeGetationDays;
						if (currentGestaionTotalDays % 7 == 0) {
							cgaStr = Integer.valueOf(currentGestaionTotalDays / 7 + "") + " Weeks";
						} else {
							long actualDays = currentGestaionTotalDays % 7;
							cgaStr = Integer.valueOf((currentGestaionTotalDays - actualDays) / 7 + "") + " Weeks "
									+ Integer.valueOf(actualDays + "") + " Days";
						}
					}
				}

				List<BabyVisit> babyVisitList = inicuDoa
						.getListFromMappedObjQuery(HqlSqlQueryConstants.getBabyVisitList(uhid, yestStr, todayStr));
				if (!BasicUtils.isEmpty(babyVisitList)) {
					Iterator<BabyVisit> itr = babyVisitList.iterator();
					Float todayWeight = null;
					Float yestWeight = null;

					while (itr.hasNext()) {
						BabyVisit obj = itr.next();
						if (null == todayWeight && obj.getVisitdate().toString().equalsIgnoreCase(todayStr)) {
							todayWeight = obj.getCurrentdateweight();
						} else if (null == yestWeight && obj.getVisitdate().toString().equalsIgnoreCase(yestStr)) {
							yestWeight = obj.getCurrentdateweight();
						}
					}
					if (todayWeight != null) {
						notesText += "Today Weight: " + todayWeight + " gms";

						if (yestWeight != null) {
							notesText += "" + "Weight Diff: " + (todayWeight - yestWeight) + " gms \t";
						} else {
							notesText += "";
						}
					}
				}

				if (!BasicUtils.isEmpty(cgaStr)) {
					notesText += "CGA: " + cgaStr;
				}

				try {
					// diagnosis as in discharge
					List<AdmissionNotes> listAdmissionNotes = inicuDoa
							.getListFromMappedObjQuery(HqlSqlQueryConstants.getAdmissionNotesList(uhid));
					String diagnosisText = "";
					if (!BasicUtils.isEmpty(listAdmissionNotes)
							&& !BasicUtils.isEmpty(listAdmissionNotes.get(0).getDiagnosis())) {
						diagnosisText = listAdmissionNotes.get(0).getDiagnosis();
					}

					List<DashboardFinalview> listDiagnosisDetails = inicuDoa
							.getListFromMappedObjQuery(HqlSqlQueryConstants.getDashboardViewList(uhid));

					if (!BasicUtils.isEmpty(listDiagnosisDetails)) {
						if (!BasicUtils.isEmpty(listDiagnosisDetails.get(0).getDiagnosis())) {

							String diagnosisStr = diagnosisText.replace(",", "/") + "/"
									+ listDiagnosisDetails.get(0).getDiagnosis().replaceAll(",", "/");
							String[] daignosisArr = diagnosisStr.split("/");
							diagnosisStr = "";
							for (String daignosis : daignosisArr) {
								if (diagnosisStr.isEmpty()) {
									diagnosisStr = daignosis;
								} else if (!diagnosisStr.contains(daignosis.trim())) {
									diagnosisStr += "/ " + daignosis.trim();
								}
							}
							diagnosisText = diagnosisStr;
						}
					}

					// handle unique in diagnosis.
					String finalDiagnosisUnique = "";
					if (!BasicUtils.isEmpty(diagnosisText)) {
						String[] finalDiagArr = diagnosisText.split("/");
						for (String diag : finalDiagArr) {
							if (finalDiagnosisUnique.isEmpty()) {
								finalDiagnosisUnique = diag.replace(" ", "");
							} else {
								if (!finalDiagnosisUnique.contains(diag.replace(" ", ""))) {
									finalDiagnosisUnique += "/ " + diag.replace(" ", "");
								}
							}
						}
						finalDiagnosisUnique = finalDiagnosisUnique.replace("Jaundice", "NNH");
					}
					notesText += "" + "Diagnosis: " + finalDiagnosisUnique.replace(",", "/") + ".";
				} catch (Exception e) {
					e.printStackTrace();
					String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
					databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID,
							loggedInUser, uhid, "getNursingNotes Diagnosis", BasicUtils.convertErrorStacktoString(e));
				}

				// if stable notes then description
				List<NursingVitalparameter> listVitalDetails = (List<NursingVitalparameter>) inicuDoa
						.getListFromMappedObjRowLimitQuery(
								HqlSqlQueryConstants.getNursingVitalList(uhid, fromDate, toDate) + " desc", 1);
				if (!BasicUtils.isEmpty(listVitalDetails)) {
					String stableStr = "";
					NursingVitalparameter vitalObj = listVitalDetails.get(0);
					if ((null == vitalObj.getHrRate() || vitalObj.getHrRate() >= 1 && vitalObj.getHrRate() <= 200)
							&& (BasicUtils.isEmpty(vitalObj.getMeanBp())
							|| Integer.parseInt(vitalObj.getMeanBp().trim()) > gestationWeeks)) {
						if (stableStr.isEmpty()) {
							stableStr += "Baby is haemodynamically stable. ";
						} else {
							stableStr += "and haemodynamically stable. ";
						}
					} else {
						if (stableStr.isEmpty()) {
							stableStr += "Baby is haemodynamically not stable. ";
						} else {
							stableStr += "and haemodynamically not stable. ";
						}
					}
					notesText += "" + stableStr;
				}

				// resp support detail if any,
				List<RespSupport> listRespSupport = (List<RespSupport>) inicuDoa
						.getListFromMappedObjRowLimitQuery(HqlSqlQueryConstants.getRespSupportList(uhid, toDate), 1);
				if (!BasicUtils.isEmpty(listRespSupport) && listRespSupport.get(0).getIsactive()) {
					RespSupport respSupportObj = listRespSupport.get(0);
					String respStr = "";
					if (!BasicUtils.isEmpty(respSupportObj.getRsMechVentType())) {
						respStr += "Mechanical Vent Type: " + respSupportObj.getRsMechVentType() + ", ";
					}

					if (!BasicUtils.isEmpty(respSupportObj.getRsMap())) {
						respStr += "MAP: " + respSupportObj.getRsMap() + ", ";
					}

					if (!BasicUtils.isEmpty(respSupportObj.getRsFrequency())) {
						respStr += "Frequency: " + respSupportObj.getRsFrequency() + ", ";
					}

					if (!BasicUtils.isEmpty(respSupportObj.getRsTv())) {
						respStr += "TV: " + respSupportObj.getRsTv() + ", ";
					}

					if (!BasicUtils.isEmpty(respSupportObj.getRsAmplitude())) {
						respStr += "Amplitude: " + respSupportObj.getRsAmplitude() + ", ";
					}

					if (!BasicUtils.isEmpty(respSupportObj.getRsFio2())) {
						respStr += "FiO2: " + respSupportObj.getRsFio2() + " %, ";
					}

					if (!BasicUtils.isEmpty(respSupportObj.getRsFlowRate())) {
						respStr += "Flow Rate: " + respSupportObj.getRsFlowRate() + " Liters/Min, ";
					}

					if (!BasicUtils.isEmpty(respSupportObj.getRsPip())) {
						respStr += "PIP: " + respSupportObj.getRsPip() + " cm H2O, ";
					}

					if (!BasicUtils.isEmpty(respSupportObj.getRsPeep())) {
						respStr += "PEEP: " + respSupportObj.getRsPeep() + " cm H2O, ";
					}

					if (!BasicUtils.isEmpty(respSupportObj.getRsIt())) {
						respStr += "IT: " + respSupportObj.getRsIt() + " secs, ";
					}

					if (!BasicUtils.isEmpty(respSupportObj.getRsEt())) {
						respStr += "ET: " + respSupportObj.getRsEt() + " secs, ";
					}

					if (!BasicUtils.isEmpty(respSupportObj.getRsMv())) {
						respStr += "MV: " + respSupportObj.getRsMv() + ", ";
					}

					if (!respStr.isEmpty()) {
						Timestamp entryTime = new Timestamp(respSupportObj.getCreationtime().getTime() + offset);
						notesText += "" + "Baby is on " + respSupportObj.getRsVentType() + " mode ("
								+ respStr.substring(0, (respStr.length() - 2)) + "), last parameters recorded at "
								+ sdf.format(entryTime) + ". ";
					}
				}

				// nursing event details
				List<NursingEpisode> listNursingEpisode = (List<NursingEpisode>) inicuDoa
						.getListFromMappedObjQuery(HqlSqlQueryConstants.getNursingEpisodeList(uhid, fromDate, toDate));
				if (!BasicUtils.isEmpty(listNursingEpisode)) {
					int apnea = 0;
					int bradycardia = 0;
					int disaturation = 0;
					String recoveryStr = "";
					String nursingEpisodeStr = "";

					Iterator<NursingEpisode> itr = listNursingEpisode.iterator();
					while (itr.hasNext()) {
						NursingEpisode obj = itr.next();
						if (obj.getApnea() != null && obj.getApnea()) {
							apnea++;
							if (recoveryStr.isEmpty() && !BasicUtils.isEmpty(obj.getRecovery())) {
								recoveryStr = obj.getRecovery();
							}
						}

						if (obj.getBradycardia() != null && obj.getBradycardia()) {
							bradycardia++;
						}

						if (obj.getDisaturation() != null && obj.getDisaturation()) {
							disaturation++;
						}
					}

					if (apnea > 0) {
						if (apnea == 1) {
							nursingEpisodeStr = "Baby had " + apnea + " event of apnea";
						} else {
							nursingEpisodeStr = "Baby had " + apnea + " events of apnea";
						}
						if (bradycardia > 0 && disaturation > 0) {
							nursingEpisodeStr += " associated with bradycardia and disaturation";
						} else if (bradycardia > 0) {
							nursingEpisodeStr += " associated with bradycardia";
						} else if (disaturation > 0) {
							nursingEpisodeStr += " associated with disaturation";
						}
						if (recoveryStr.contains("spontaneous")) {
							nursingEpisodeStr += ", recovered " + recoveryStr + ". ";
						} else {
							nursingEpisodeStr += ", required " + recoveryStr + ". ";
						}
					} else {
						if (bradycardia > 0) {
							nursingEpisodeStr = "Baby had " + bradycardia + " events of bradycardia. ";
						}

						if (disaturation > 0) {
							nursingEpisodeStr = "Baby had " + disaturation + " events of disaturation. ";
						}
					}
					notesText += "" + nursingEpisodeStr;
				}

				// doctor - feed if NPO then not accepting feed,
				List<BabyfeedDetail> listFeedDetail = (List<BabyfeedDetail>) inicuDoa.getListFromMappedObjRowLimitQuery(
						HqlSqlQueryConstants.getBabyfeedDetailList(uhid, fromDate, toDate), 1);
				BabyfeedDetail feedObj;
				if (!BasicUtils.isEmpty(listFeedDetail)) {
					feedObj = listFeedDetail.get(0);
					String feedStr = "";
					if (feedObj.getIsenternalgiven() != null && feedObj.getIsenternalgiven()) {
						feedStr = "Accepting feeds well, " + (Math.round(feedObj.getFeedvolume()*100)/100) + " (ml/feed) every "
								+ feedObj.getFeedduration() + " hours.";
					} else {
						feedStr = "Not accepting feeds";
					}
					notesText += "" + feedStr;
				}

				// nursing intake output details
				float totalFeed = 0;
				float urineVolume = 0;
				float lipidTotal = 0;
				float tpnTotal = 0;

				List<String> urineDetails = (List<String>) inicuDoa.getListFromNativeQuery(
						"SELECT urine FROM vw_nursing_intake_output_final where urine is not null and urine !='' and uhid ='"
								+ uhid + "' and entry_datetime >= '" + fromDate + "' and entry_datetime <= '" + toDate
								+ "'");
				if (!BasicUtils.isEmpty(urineDetails)) {
					Iterator<String> iterator = urineDetails.iterator();
					while (iterator.hasNext()) {
						String urine = iterator.next();
						try {
							urineVolume += parseFloat(urine);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

				List<Object> intakeOutputDetail = (List<Object>) inicuDoa.getListFromNativeQuery(
						"SELECT sum(primary_feed_value) as primary, sum(formula_value) as secondary"
								+ ", sum(lipid_delivered) as lipid, sum(tpn_delivered) as tpn FROM vw_nursing_intake_output_final"
								+ " where uhid ='" + uhid + "' and entry_datetime >= '" + fromDate
								+ "' and entry_datetime <= '" + toDate + "'");
				if (!BasicUtils.isEmpty(intakeOutputDetail)) {
					Object[] obj = (Object[]) intakeOutputDetail.get(0);
					if (obj[0] != null) {
						totalFeed += (Float) obj[0];
					}

					if (obj[1] != null) {
						totalFeed += (Float) obj[1];
					}

					if (obj[2] != null) {
						lipidTotal = (Float) obj[2];
					}

					if (obj[3] != null) {
						tpnTotal = (Float) obj[3];
					}
				}

				int stoolCount = 0;
				List<Object> stoolDetail = (List<Object>) inicuDoa.getListFromNativeQuery(
						"SELECT count(stool) as stool FROM vw_nursing_intake_output_final" + " where uhid ='" + uhid
								+ "' and (stool_passed is true or (stool !='' and stool !='none')) and entry_datetime >= '"
								+ fromDate + "' and entry_datetime <= '" + toDate + "'");
				if (!BasicUtils.isEmpty(stoolDetail)) {
					stoolCount += ((BigInteger) stoolDetail.get(0)).intValue();
				}

				if (totalFeed > 0) {
					notesText += "" + "Total Feed given is " + (Math.round(totalFeed*100)/100) + " ml.";
				}

				if (lipidTotal > 0) {
					notesText += "" + "Total Lipid given is " + (Math.round(lipidTotal*100)/100) + " ml.";
				}

				if (tpnTotal > 0) {
					notesText += "" + "Total TPN Fluid given is " + (Math.round(tpnTotal*100)/100) + " ml.";
				}

				// feedObj - PN details if
				// (!BasicUtils.isEmpty(feedObj.getLipidTotal())) {}

				if (urineVolume > 0 && stoolCount > 0) {
					notesText += "" + "Baby has passed " + (Math.round(urineVolume*100)/100)  + " ml urine and stool ";
					if (stoolCount == 1) {
						notesText += stoolCount + " time.";
					} else {
						notesText += stoolCount + " times.";
					}
				} else if (urineVolume > 0) {
					notesText += "" + "Baby has passed " + (Math.round(urineVolume*100)/100) + " ml urine.";
				} else if (stoolCount > 0) {
					notesText += "" + "Baby has passed stool ";
					if (stoolCount == 1) {
						notesText += stoolCount + " time.";
					} else {
						notesText += stoolCount + " times.";
					}
				}

				// medication
				List<RefMedType> medTypeList = inicuDoa.getListFromMappedObjQuery("select obj from  RefMedType obj");
				List<RefMedfrequency> medFreqList = inicuDoa
						.getListFromMappedObjQuery("select obj from  RefMedfrequency obj");
				List<BabyPrescription> listMedication = (List<BabyPrescription>) inicuDoa.getListFromMappedObjQuery(
						HqlSqlQueryConstants.getCurrentMedicationList(uhid, fromDate, toDate));
				if (!BasicUtils.isEmpty(listMedication)) {
					Iterator<BabyPrescription> itr = listMedication.iterator();
					while (itr.hasNext()) {
						String medStr = "";
						BabyPrescription obj = itr.next();
						if (!BasicUtils.isEmpty(obj.getMedicationtype()) && !BasicUtils.isEmpty(medTypeList)) {
							for (int i = 0; i < medTypeList.size(); i++) {
								if (medTypeList.get(i).getTypeid().equalsIgnoreCase(obj.getMedicationtype())) {
									medStr += medTypeList.get(i).getTypevalue() + " ";
									break;
								}
							}
						}

						medStr += obj.getMedicinename() + " given " + obj.getDose() + " mg/kg/dose";
						if (!BasicUtils.isEmpty(obj.getFrequency()) && !BasicUtils.isEmpty(medFreqList)) {
							for (int i = 0; i < medFreqList.size(); i++) {
								if (medFreqList.get(i).getFreqid().equalsIgnoreCase(obj.getFrequency())) {
									medStr += " every " + medFreqList.get(i).getFreqvalue();
									break;
								}
							}
						}
						notesText += "" + medStr + ".";
					}
				}

				// procedure
				List<String> procedureDetail = (List<String>) inicuDoa
						.getListFromNativeQuery(HqlSqlQueryConstants.getCurrentProcedure(uhid, fromDate, toDate));
				if (!BasicUtils.isEmpty(procedureDetail)) {
					String procedureStr = "";
					for (String procedureName : procedureDetail) {
						if (procedureStr.isEmpty()) {
							procedureStr = "" + "Baby is on " + procedureName;
						} else {
							procedureStr += ", " + procedureName;
						}
					}
					notesText += procedureStr + ".";
				}

				// nursing order - assessments
				List<NursingOrderAssesment> nursingOrderList = (List<NursingOrderAssesment>) inicuDoa
						.getListFromMappedObjQuery(HqlSqlQueryConstants.getNursingOrderList(uhid, fromDate, toDate));
				if (!BasicUtils.isEmpty(nursingOrderList)) {
					for (NursingOrderAssesment order : nursingOrderList) {
						if (order.getActionvalue() != null) {
							notesText += "" + order.getActionvalue() + ".";
						}
					}
				}

				// nursing order - investigation sample
				List<InvestigationOrdered> investSampleList = (List<InvestigationOrdered>) inicuDoa
						.getListFromMappedObjQuery(
								HqlSqlQueryConstants.getInvestSampleSentList(uhid, fromDate, toDate));
				if (!BasicUtils.isEmpty(investSampleList)) {
					for (InvestigationOrdered sample : investSampleList) {
						notesText += "" + sample.getTestname() + " sample sent at "
								+ sdf.format(new Timestamp(sample.getSenttolab_time().getTime() + offset)) + ".";
					}
				}

				currentNotes.setNursing_notes("" + notesText + "" + "Routine care was given.");
			} catch (Exception e) {
				e.printStackTrace();
				String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
				databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUser,
						uhid, "getNursingNotes", BasicUtils.convertErrorStacktoString(e));
			}
		}
		return returnObj;
	}



	public List<BabyVisit> getMappedBabyVisitObject(List<Object[]> anthropometryObjectList){
		List<BabyVisit> babyVisitList = new ArrayList<>();
		for (Object[] anthropometryObject:anthropometryObjectList) {
			try {
				BabyVisit babyVisit = new BabyVisit();
				babyVisit.setVisitid(BasicUtils.toLongSafe(anthropometryObject[0]));
				babyVisit.setCreationtime(BasicUtils.toTimestampSafe(anthropometryObject[1]));
				babyVisit.setModificationtime(BasicUtils.toTimestampSafe(anthropometryObject[2]));
				babyVisit.setUhid(BasicUtils.toStringSafe(anthropometryObject[3]));
				babyVisit.setVisitdate(BasicUtils.toDateSafe(anthropometryObject[4]));
				babyVisit.setCurrentage(BasicUtils.toStringSafe(anthropometryObject[5]));
				babyVisit.setNicuday(BasicUtils.toStringSafe(anthropometryObject[6]));
				babyVisit.setGaAtBirth(BasicUtils.toStringSafe(anthropometryObject[7]));
				babyVisit.setCorrectedGa(BasicUtils.toStringSafe(anthropometryObject[8]));
				babyVisit.setCurrentdateweight(BasicUtils.toFloatSafe(anthropometryObject[9]));
				babyVisit.setCurrentdateheight(BasicUtils.toFloatSafe(anthropometryObject[10]));
				babyVisit.setCurrentdateheadcircum(BasicUtils.toFloatSafe(anthropometryObject[11]));
				babyVisit.setWorkingweight(BasicUtils.toFloatSafe(anthropometryObject[12]));
				babyVisit.setLoggeduser(BasicUtils.toStringSafe(anthropometryObject[13]));
				babyVisit.setSurgeon(BasicUtils.toStringSafe(anthropometryObject[14]));
				babyVisit.setSurgery(BasicUtils.toStringSafe(anthropometryObject[15]));
				babyVisit.setNeonatologist(BasicUtils.toStringSafe(anthropometryObject[16]));
				babyVisit.setEpisodeid(BasicUtils.toStringSafe(anthropometryObject[17]));
				babyVisit.setAdmission_entry(BasicUtils.toBooleanSafe(anthropometryObject[18]));
				babyVisit.setDaysAfterBirth(BasicUtils.toStringSafe(anthropometryObject[19]));
				babyVisit.setGestationWeek(BasicUtils.toIntegerSafe(anthropometryObject[20]));
				babyVisit.setGestationDays(BasicUtils.toIntegerSafe(anthropometryObject[21]));
				babyVisit.setComments(BasicUtils.toStringSafe(anthropometryObject[22]));
				babyVisit.setVisittime(BasicUtils.toTimeSafe(anthropometryObject[23]));
				babyVisit.setAdmissionHeadCircumference(BasicUtils.toFloatSafe(anthropometryObject[24]));
				babyVisit.setAdmissionLength(BasicUtils.toFloatSafe(anthropometryObject[25]));
				babyVisit.setAdmissionWeight(BasicUtils.toFloatSafe(anthropometryObject[26]));
				babyVisit.setWeightForCal(BasicUtils.toFloatSafe(anthropometryObject[27]));
				babyVisitList.add(babyVisit);
			}catch (NullPointerException e){
				e.printStackTrace();
			}
		}
		return babyVisitList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public NursingAllDataPOJO getAllNursingData(String uhid, String fromTime, String toTime,String branchName) {
		NursingAllDataPOJO returnObj = new NursingAllDataPOJO();

		if (!(BasicUtils.isEmpty(fromTime) || BasicUtils.isEmpty(toTime))) {

			int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset() - TimeZone.getDefault().getRawOffset();
			System.out.println("[" + BasicConstants.timeStampFormat.format(new Timestamp(System.currentTimeMillis())) + "] :: NURSING OUTPUT WITH OFFSET:"+offset);

			try {
				Timestamp fromDate = new Timestamp(60000 + Long.parseLong(fromTime));
				Timestamp toDate = new Timestamp(59000 + Long.parseLong(toTime));

				Timestamp fromDateOffset = new Timestamp(fromDate.getTime() + offset);
				Timestamp toDateOffset = new Timestamp(toDate.getTime() + offset);

				Timestamp fromDateOffset1 = new Timestamp(fromDateOffset.getTime() - offset);
				Timestamp toDateOffset1 = new Timestamp(toDateOffset.getTime() - offset);

				System.out.println(fromDateOffset.getHours() + "********HOURS******");

				Timestamp fromDateOffsetTwentFourHour = new Timestamp(fromDateOffset.getTime() - offset);
				Timestamp toDateOffsetTwentFourHour = new Timestamp(toDateOffset.getTime() - offset);

				String babyFeedSql = "SELECT obj FROM BabyfeedDetail as obj WHERE uhid ='" + uhid
						+ "' order by entrydatetime desc";

				List<BabyfeedDetail> babyFeedList = (List<BabyfeedDetail>) inicuDoa
						.getListFromMappedObjRowLimitQuery(babyFeedSql, 1);

				if (!BasicUtils.isEmpty(babyFeedList)) {
					BabyfeedDetail babyFeedObj = babyFeedList.get(0);
					// Feed Method
					if (!(babyFeedObj.getFeedmethod() == null || babyFeedObj.getFeedmethod().isEmpty())) {
						String feedMethodStr = babyFeedObj.getFeedmethod().replace("[", "").replace("]", "").replace(", ", ",");

						String refFeedMethodSql = "SELECT obj FROM RefMasterfeedmethod as obj";
						List<RefMasterfeedmethod> refFeedMethodList = inicuDoa.getListFromMappedObjQuery(refFeedMethodSql);

						if (!BasicUtils.isEmpty(feedMethodStr)) {
							String[] feedMethodArr = feedMethodStr.split(",");

							for (int i = 0; i < feedMethodArr.length; i++) {
								feedMethodStr = "";
								Iterator<RefMasterfeedmethod> itr = refFeedMethodList.iterator();
								while (itr.hasNext()) {
									RefMasterfeedmethod obj = itr.next();
									if (feedMethodArr[i].trim().equalsIgnoreCase(obj.getFeedmethodid())) {
										if (feedMethodStr.isEmpty()) {
											feedMethodStr = obj.getFeedmethodname();
										} else {
											feedMethodStr += ", " + obj.getFeedmethodname();
										}
										break;
									}
								}
							}

							if (feedMethodArr.length > 1 && babyFeedObj.getFeedmethod_type() != null) {
								if (babyFeedObj.getFeedmethod_type()) {
									feedMethodStr += "(Per Shift)";
								} else {
									feedMethodStr += "(Alternatively)";
								}
							}
						}

						returnObj.setFeedMethodStr(feedMethodStr);
					}

					// EnfeedDetails
					String enfeedDetailsSql = "SELECT obj FROM EnFeedDetail as obj WHERE uhid='" + uhid + "' and babyfeedid="
							+ babyFeedObj.getBabyfeedid() + " order by en_feed_detail_id asc";
					List<EnFeedDetail> enfeedDetailsList = inicuDoa.getListFromMappedObjQuery(enfeedDetailsSql);
					returnObj.setEnFeedList(enfeedDetailsList);
				}

				//Getting the Print Format
				List<BabyDetail> babyList = inicuDoa.getListFromMappedObjQuery(
						HqlSqlQueryConstants.getBabyDetailList(uhid));
				if (!BasicUtils.isEmpty(babyList)) {
					String queryhospitalName = "select obj from RefHospitalbranchname obj where branchname='" + babyList.get(0).getBranchname() + "'";
					List<RefHospitalbranchname> refHospitalBranchNameList = inicuDoa.getListFromMappedObjQuery(queryhospitalName);
					if (!BasicUtils.isEmpty(refHospitalBranchNameList) && !BasicUtils.isEmpty(refHospitalBranchNameList.get(0).getNursingPrintFormat())) {
						returnObj.setPrintFormatType(refHospitalBranchNameList.get(0).getNursingPrintFormat());
					}

					queryhospitalName = "select obj from RefNursingOutputParameters obj where branchname='" + babyList.get(0).getBranchname() + "'";
					List<RefNursingOutputParameters> refNursingOutputParametersList = inicuDoa.getListFromMappedObjQuery(queryhospitalName);
					if (!BasicUtils.isEmpty(refNursingOutputParametersList)) {
						returnObj.setNursingOutputParameter(refNursingOutputParametersList.get(0));
					}
				}

				//Get only Intake Output, Medications, Blood Product and Heplock for 24 hrs irrespective of any condition
				List<NursingIntakeOutput> pastIntakeOutputList = inicuDoa.getListFromMappedObjQuery(
						HqlSqlQueryConstants.getNursingIntakeOutputList(uhid, fromDateOffsetTwentFourHour, toDateOffsetTwentFourHour));
				returnObj.setPastIntakeOutputList(pastIntakeOutputList);

				List<NursingHeplock> pastHeplockList = inicuDoa.getListFromMappedObjQuery(
						HqlSqlQueryConstants.getNursingHeplockList(uhid, fromDateOffsetTwentFourHour, toDateOffsetTwentFourHour));
				returnObj.setPastHeplockList(pastHeplockList);

				List<NursingBloodproduct> pastBloodProductList = inicuDoa.getListFromMappedObjQuery(
						HqlSqlQueryConstants.getNursingBloodProductList(uhid, fromDateOffsetTwentFourHour, toDateOffsetTwentFourHour));
				returnObj.setAllPastNursingBloodProductList(pastBloodProductList);

				List<NursingMedication> pastMedicationList = inicuDoa.getListFromMappedObjQuery(
						HqlSqlQueryConstants.getNursingMedicationList(uhid, fromDateOffsetTwentFourHour, toDateOffsetTwentFourHour));
				returnObj.setPastMedicationList(pastMedicationList);

				List<BabyfeedDetail> feedList = (List<BabyfeedDetail>) inicuDoa.getListFromMappedObjQuery(
						HqlSqlQueryConstants.getBabyfeedDetailList(uhid, fromDateOffset1, toDateOffset1));
				returnObj.setDoctorNutritionOrder(feedList);

				String procedureSql = "select obj from NurseExecutionOrders as obj where uhid='" + uhid + "' and executiontime <= '" + toDateOffsetTwentFourHour
						+ "' and executiontime >= '" + fromDateOffsetTwentFourHour + "' and is_execution is 'true' order by executiontime desc";
				List<NurseExecutionOrders> nurseData = inicuDoa.getListFromMappedObjQuery(procedureSql);
				returnObj.setNursingExecutionList(nurseData);

				List<NurseTasks> nurseTasksCommentsList = inicuDoa.getListFromMappedObjQuery(
						HqlSqlQueryConstants.getNurseTasksComments(fromDateOffsetTwentFourHour, toDateOffsetTwentFourHour, uhid));

				returnObj.setNurseTasksCommentsList(nurseTasksCommentsList);

				/**
				 * Get the values from different screens according to uhid and date and time
				 * Anthropometry, Vitals, Events, BloodGas, Ventilator, BabyFeed, Intake Output,
				 *  Medications, Nursing Notes, ET Suction
 				 */

//				List<BabyVisit> pastAnthropometryList = inicuDoa.getListFromMappedObjQuery(
//						HqlSqlQueryConstants.getBabyAnthropometryList(uhid, fromDateOffsetTwentFourHour, toDateOffsetTwentFourHour));
//				returnObj.setAnthropometryList(pastAnthropometryList);

				/**
				* Get the Baby visit list from 8:00AM to 8:00 AM
				* */
				List<Object[]> anthropometryObjectList = inicuDoa.getListFromNativeQuery(
						HqlSqlQueryConstants.getBabyAnthropometryNewList(uhid, fromDateOffset, toDateOffset));

				List<BabyVisit> anthropometryList = getMappedBabyVisitObject(anthropometryObjectList);

				if (!BasicUtils.isEmpty(anthropometryList)) {
					returnObj.setAnthropometryList(anthropometryList);
					returnObj.setToday_weight(anthropometryList.get(anthropometryList.size() - 1).getCurrentdateweight().toString());

					// Get the Yesterday's Weight i.e last day weight
					Timestamp fromDateYesterdayOffset = new Timestamp(fromDateOffset.getTime() - (24 * 60 * 60 * 1000));
					Timestamp toDateYesterdayOffset = new Timestamp(toDateOffset.getTime() - (24 * 60 * 60 * 1000));

					float lastdayweight = 0;
					List<Object[]> lastAnthropometryObjectList = inicuDoa.getListFromNativeQuery(
							HqlSqlQueryConstants.getBabyAnthropometryNewList(uhid, fromDateYesterdayOffset, toDateYesterdayOffset));

					List<BabyVisit> lastAnthropometryList = getMappedBabyVisitObject(lastAnthropometryObjectList);

					if (!BasicUtils.isEmpty(lastAnthropometryList)) {
						lastdayweight = lastAnthropometryList.get(0).getCurrentdateweight();
					}

					for (BabyVisit obj : anthropometryList) {
						obj.setPrevDateWeight(lastdayweight);
						obj.setEntryDateOfBabyVisit(new Timestamp(BasicUtils.getDateTimeFromTime(obj.getVisitdate(),obj.getVisittime()).getTime() -offset));
					}
					returnObj.setAnthropometryList(anthropometryList);
				}

				List<EtSuction> etSuctionList = inicuDoa.getListFromMappedObjQuery(
						HqlSqlQueryConstants.getEtSuctionList(uhid, fromDate, toDate));
				returnObj.setEtSuctionList(etSuctionList);

				List<NursingVitalparameter> vitalList = inicuDoa.getListFromMappedObjQuery(
						HqlSqlQueryConstants.getNursingVitalList(uhid, fromDateOffset, toDateOffset));
				for (NursingVitalparameter obj : vitalList) {
					obj.setEntryDate(new Timestamp(obj.getEntryDate().getTime() -offset));
				}
				returnObj.setVitalList(vitalList);

				List<NursingEpisode> episodeList = inicuDoa
						.getListFromMappedObjQuery(HqlSqlQueryConstants.getNursingEventList(uhid, fromDateOffset, toDateOffset));
				returnObj.setEpisodeList(episodeList);

				for (NursingEpisode obj : episodeList) {
					obj.setCreationtime(new Timestamp(obj.getCreationtime().getTime() - offset));
				}

				List<NursingBloodGasVentilator> dataList = inicuDoa.getListFromMappedObjQuery(
						HqlSqlQueryConstants.getNursingBloodGasVentilatorList(uhid, fromDateOffset, toDateOffset));
				if (!BasicUtils.isEmpty(dataList)) {
					for (NursingBloodGasVentilator obj : dataList) {
						Timestamp p = obj.getEntrydate();
						obj.setEntrydate(new Timestamp(p.getTime() - offset));
					}
				}
				String ventmode = "";
				for (int index = 0; index < dataList.size(); index++) {
					ventmode = "";
					if (dataList.get(index).getVentmode() != null && dataList.get(index).getVentmode().toString() != "") {
						String var = dataList.get(index).getVentmode().toString();
						if (var.equalsIgnoreCase("VM0006") || dataList.get(index).getVentmode().toString().equalsIgnoreCase("VM0007") || dataList.get(index).getVentmode().toString().equalsIgnoreCase("VM0008") || dataList.get(index).getVentmode().toString().equalsIgnoreCase("VM0004")) {

							if (dataList.get(index).getVentmode().toString().equalsIgnoreCase("VM0006")) {
								ventmode += "PSV ";
							}
							if (dataList.get(index).getVentmode().toString().equalsIgnoreCase("VM0007")) {
								ventmode += "SIPPV ";
							}
							if (dataList.get(index).getVentmode().toString().equalsIgnoreCase("VM0008")) {
								ventmode += "SIMV ";
							}
							if (dataList.get(index).getVentmode().toString().equalsIgnoreCase("VM0004")) {
								ventmode += "IMV ";
							}
							if (dataList.get(index).getVolume_guarantee() != null && dataList.get(index).getVolume_guarantee() != "") {
								ventmode += "+ VG ";
							}
							if (dataList.get(index).getPressure_support_type() != null && dataList.get(index).getPressure_support_type().equalsIgnoreCase("Yes")) {
								ventmode += "+ PS ";
							}
							if (dataList.get(index).getControl_type() != null) {
								ventmode += "+ " + dataList.get(index).getControl_type() + " ";
							}

							dataList.get(index).setVent_desc(ventmode);
						}
						if (dataList.get(index).getVentmode().toString().equalsIgnoreCase("VM0003")) {
							ventmode += "CPAP ";
							if (dataList.get(index).getCpap_type() != null)
								ventmode += " - " + dataList.get(index).getCpap_type();
							if (dataList.get(index).getDelivery_type() != null)
								ventmode += " - " + dataList.get(index).getDelivery_type() + "";

							dataList.get(index).setVent_desc(ventmode);
						}
					}

				}


				if (!BasicUtils.isEmpty(dataList)) {
					returnObj.setBgVentilatorList(dataList);
				}

				List<KeyValueObj> ventilatorModeDropDown = getRefObj(
						"select obj.ventmodeid,obj.ventilationmode from  ref_ventilationmode obj");
				returnObj.setVentilatorModeDropDown(ventilatorModeDropDown);

				String refFeedTypeSql = "SELECT obj FROM RefMasterfeedtype as obj";
				List<RefMasterfeedtype> refFeedTypeList = inicuDoa.getListFromMappedObjQuery(refFeedTypeSql);
				returnObj.setRefFeedTypeList(refFeedTypeList);

				// calculating feed calculator....
				FeedCalculatorPOJO calculator = new FeedCalculatorPOJO();

				if (!BasicUtils.isEmpty(pastIntakeOutputList)) {
					String nutritionCalculator = "select obj from RefNutritioncalculator obj";
					List<RefNutritioncalculator> nutritionList = notesDoa.getListFromMappedObjNativeQuery(nutritionCalculator);
					calculator.setRefNutritionInfo(nutritionList);

					if (anthropometryList != null && anthropometryList.size() > 0) {
						if (!BasicUtils.isEmpty(anthropometryList.get(0).getCurrentdateweight())) {
							String weight = (anthropometryList.get(0).getCurrentdateweight() / 1000) + "";
							CaclulatorDeficitPOJO cuurentDeficitLast = getDeficitFeedCalculatorInput(uhid, feedList, nutritionList,
									weight, fromDateOffsetTwentFourHour, toDateOffsetTwentFourHour);
							calculator.setLastDeficitCal(cuurentDeficitLast);
						}
						returnObj.setFeedCalulator(calculator);
					} else {
						String queryPreviousBabyVisit = "select obj from BabyVisit as obj where uhid='" + uhid + "' order by creationtime desc";
						List<BabyVisit> visitList = notesDoa.getListFromMappedObjNativeQuery(queryPreviousBabyVisit);
						if (visitList != null && visitList.size() > 0) {
							if (!BasicUtils.isEmpty(visitList.get(0).getWorkingweight())) {
								String weight = (visitList.get(0).getWorkingweight() / 1000) + "";
								CaclulatorDeficitPOJO cuurentDeficitLast = getDeficitFeedCalculatorInput(uhid, feedList, nutritionList,
										weight, fromDateOffsetTwentFourHour, toDateOffsetTwentFourHour);
								calculator.setLastDeficitCal(cuurentDeficitLast);
							}
							returnObj.setFeedCalulator(calculator);
						}

					}
				}
				List<NursingEpisode> nursingEpisodeList = inicuDoa.getListFromMappedObjQuery(HqlSqlQueryConstants.getNursingEpisodeList(uhid, fromDate, toDate));
				Collections.reverse(nursingEpisodeList);
				returnObj.setNursingEpisodeList(nursingEpisodeList);

				List<NursingIntakeOutput> intakeOutputList = inicuDoa.getListFromMappedObjQuery(
						HqlSqlQueryConstants.getNursingIntakeOutputList(uhid, fromDate, toDate));
				Collections.reverse(intakeOutputList);
				returnObj.setIntakeOutputList(intakeOutputList);

				List<NursingHeplock> heplockList = inicuDoa.getListFromMappedObjQuery(
						HqlSqlQueryConstants.getNursingHeplockList(uhid, fromDate, toDate));
				returnObj.setHeplockList(heplockList);

				List<NursingBloodproduct> bloodProductList = inicuDoa.getListFromMappedObjQuery(
						HqlSqlQueryConstants.getNursingBloodProductList(uhid, fromDate, toDate));
				returnObj.setPastNursingBloodProductList(bloodProductList);

				List<DoctorBloodProducts> doctorBloodProductList = inicuDoa
						.getListFromMappedObjQuery(HqlSqlQueryConstants.getDoctorBloodProductList(uhid)
								+ " and status='Active' and assessment_time >= '" + fromDate
								+ "' and assessment_time <= '" + toDate + "' order by assessment_time desc");
				returnObj.setBloodProductList(doctorBloodProductList);

				List<NursingMedication> medicationList = inicuDoa.getListFromMappedObjQuery(
						HqlSqlQueryConstants.getNursingMedicationList(uhid, fromDate, toDate));
				returnObj.setMedicationList(medicationList);

				String queryGetFrequencyRef = "select obj from RefMedfrequency obj";
				List<RefMedfrequency> freqList = inicuDoa.getListFromMappedObjQuery(queryGetFrequencyRef);
				returnObj.setFreqList(freqList);

				List<BabyPrescription> alltMedicationList = inicuDoa
						.getListFromMappedObjQuery("select obj from BabyPrescription obj where uhid='" + uhid
								+ "' order by enddate desc, startdate desc");
				returnObj.setAllMedicationList(alltMedicationList);

				String querygetActiveMeds = "select obj from BabyPrescription obj where uhid='" + uhid + "' and isactive ='true' and startdate <= '" + toDateOffset1 + "' order by startdate desc";
				String querygetActiveMeds1 = "select obj from BabyPrescription as obj where isactive = 'false' and startdate <= '" + toDateOffset1 + "' and uhid = '" + uhid + "' and '" + fromDateOffset1 + "' <= enddate and isedit is null and iscontinuemedication is null";
				List<BabyPrescription> activeMeds = inicuDoa.getListFromMappedObjQuery(querygetActiveMeds);
				List<BabyPrescription> inactiveMeds = inicuDoa.getListFromMappedObjQuery(querygetActiveMeds1);
				List<BabyPrescription> allMeds = new ArrayList<BabyPrescription>();
				allMeds.addAll(activeMeds);
				allMeds.addAll(inactiveMeds);
				returnObj.setCurrentMedicationList(allMeds);

				List<Object> procedureDetail = (List<Object>) inicuDoa
						.getListFromNativeQuery("SELECT procedure_type,starttime,endtime FROM vw_procedures_usage where uhid='" + uhid
								+ "' and (endtime is null or endtime >= '" + fromDateOffsetTwentFourHour + "') and starttime <= '" + toDateOffsetTwentFourHour + "' and procedure_type != 'Therapeutic Hypothermia'");
				returnObj.setProcedureList(procedureDetail);

				// get investigations for "uhid" which are in sent state
				List<InvestigationOrdered> invOrderedList = null;
				String queryinvOrderedList = "select t from InvestigationOrdered t "
						+ " WHERE uhid='" + uhid + "' and investigationorder_time >= '" + fromDateOffsetTwentFourHour + "' and investigationorder_time <= '" + toDateOffsetTwentFourHour + "'";
				invOrderedList = notesDoa.getListFromMappedObjNativeQuery(queryinvOrderedList);
				returnObj.setInvOrderedList(invOrderedList);


				List<AssessmentMedication> pastAssessmentMedicationList = inicuDoa
						.getListFromMappedObjQuery("select obj from AssessmentMedication obj where uhid='" + uhid
								+ "' and nursing_action='true' order by assessmenttime desc");
				returnObj.setPastAssessmentMedicineList(pastAssessmentMedicationList);

				List<NursingNote> notesList = inicuDoa.getListFromMappedObjQuery(
						HqlSqlQueryConstants.getNursingNotesList(uhid) + " and to_time <= '" + toDateOffsetTwentFourHour
								+ "' and from_time >= '" + fromDateOffsetTwentFourHour + "' order by from_time, to_time");
				List<NursingNote> list = new ArrayList<NursingNote>();
				for (NursingNote obj : notesList) {
					Timestamp p = obj.getFrom_time();
					Timestamp q = obj.getTo_time();

					obj.setFrom_time(new Timestamp(p.getTime()));
					obj.setTo_time(new Timestamp(q.getTime()));

					if (obj.getFlag() != null && obj.getFlag() == true) {
						list.add(obj);
					}
				}
				returnObj.setNotesList(list);

				StableNote stableNoteObj = new StableNote();

				// Setting the alarms of SpO2 and HR(low and high) from stable notes
				List<Object[]> queryAlarmsValueList = inicuDoa
						.getListFromNativeQuery(HqlSqlQueryConstants.getqueryAlarmsValueList(uhid, fromDate, toDate));

				Object[] queryAlarmsValueObject = queryAlarmsValueList.get(0);

				if (!BasicUtils.isEmpty(queryAlarmsValueObject[0])) {
					stableNoteObj.setHrAlarmHi(parseFloat(queryAlarmsValueObject[0].toString()));
				}
				if (!BasicUtils.isEmpty(queryAlarmsValueObject[1])) {
					stableNoteObj.setHrAlarmLo(parseFloat(queryAlarmsValueObject[1].toString()));
				}
				if (!BasicUtils.isEmpty(queryAlarmsValueObject[2])) {
					stableNoteObj.setSpo2AlarmHi(parseFloat(queryAlarmsValueObject[2].toString()));
				}
				if (!BasicUtils.isEmpty(queryAlarmsValueObject[3])) {
					stableNoteObj.setSpo2AlarmLo(parseFloat(queryAlarmsValueObject[3].toString()));
				}

				// Setting the values from stable notes for Dutta chart(condition - last updated
				// and value should not be empty)
				List<StableNote> otherParametersList = inicuDoa
						.getListFromMappedObjQuery(HqlSqlQueryConstants.getotherParametersList(uhid, fromDate, toDate));
				boolean isAirSkinModeTemp = true;
				boolean isEttFixed = true;
				boolean isEttSuction = true;
				boolean isIncubatorHumidity = true;

				for (StableNote obj : otherParametersList) {

					if (!BasicUtils.isEmpty(obj.getAirSkinModeTemp()) && isAirSkinModeTemp) {
						stableNoteObj.setAirSkinModeTemp(obj.getAirSkinModeTemp());
						isAirSkinModeTemp = false;
					}
					if (!BasicUtils.isEmpty(obj.getEttFixed()) && isEttFixed) {
						stableNoteObj.setEttFixed(obj.getEttFixed());
						isEttFixed = false;
					}
					if (!BasicUtils.isEmpty(obj.getEttSuction()) && isEttSuction) {
						stableNoteObj.setEttSuction(obj.getEttSuction());
						isEttSuction = false;
					}
					if (!BasicUtils.isEmpty(obj.getIncubatorHumidity()) && isIncubatorHumidity) {
						stableNoteObj.setIncubatorHumidity(obj.getIncubatorHumidity());
						isIncubatorHumidity = false;
					}
				}

				returnObj.setStableNoteObj(stableNoteObj);

				// get the clinical alter setting
				String ClinicalAlertSettingQuery = "select obj from ClinicalAlertSettings as obj";
				List<ClinicalAlertSettings> ClinicalAlertSettingList = inicuDoa.getListFromMappedObjQuery(ClinicalAlertSettingQuery);
				if (ClinicalAlertSettingList.size() > 0) {
					HashMap<String, AlertMinMax> myAlertMap = new HashMap<>();
					for (ClinicalAlertSettings object : ClinicalAlertSettingList) {
						AlertMinMax alertMinMax = new AlertMinMax();
						if (object.getMinValue() != null) {
							alertMinMax.setMinValue(Integer.parseInt(object.getMinValue()));
						}
						if (object.getMaxValue() != null) {
							alertMinMax.setMaxValue(Integer.parseInt(object.getMaxValue()));
						}
						alertMinMax.setDependencies(object.getDependency());
						myAlertMap.put(object.getParameterName(), alertMinMax);
					}
					returnObj.setClinicalAlertSettingsList(myAlertMap);
				}
			

 			Float todayWeight = null;
			List<BabyVisit> babyVisitList = inicuDoa
					.getListFromMappedObjQuery(HqlSqlQueryConstants.getBabyVisitList(uhid, null, toDate.toString()));
			if (!BasicUtils.isEmpty(babyVisitList)) {
				Iterator<BabyVisit> itr = babyVisitList.iterator();

				while (itr.hasNext()) {
					BabyVisit obj = itr.next();
					if (!BasicUtils.isEmpty(obj.getWorkingweight())) {
						todayWeight = obj.getWorkingweight();
						break;
					}
				}

			}

 			if (todayWeight != null) returnObj.setNursingWeight(todayWeight);


			} catch (Exception e) {
				e.printStackTrace();
				String[] receiverArray = {BasicConstants.MAIL_ID_RECIEVER};
				databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUser,
						uhid, "getAllNursingData", BasicUtils.convertErrorStacktoString(e));
			}


		}
		return returnObj;
	}




	@Override
	@SuppressWarnings("unchecked")
	public NursingNotesPojo getAdvNursingNotes(String uhid, String fromTime, String toTime) {
		NursingNotesPojo returnObj = new NursingNotesPojo();
		NursingNote currentNotes = returnObj.getCurrentNotes();
//		int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
//				- TimeZone.getDefault().getRawOffset();

		int offset = BasicUtils.getFixedOffsetValue();

		String notesText = "";
		String currentRespSupport  = "";
		String currentVitals = "";

		// get previous nursing notes
		List<NursingNote> notesList = inicuDoa
				.getListFromMappedObjQuery(HqlSqlQueryConstants.getActiveNursingNotesList(uhid));

		List<NursingNote> allNotesList = new ArrayList<NursingNote>();
		boolean flagtest = true;
		for (NursingNote item : notesList) {
			if(flagtest) {
				Timestamp updatedFromTimeValue = new Timestamp(item.getFrom_time().getTime());
				Timestamp updatedWithOffsetTimeValue = new Timestamp(item.getFrom_time().getTime() - offset);
				flagtest = false;
			}

			item.setFrom_time(new Timestamp(item.getFrom_time().getTime()));
			item.setTo_time(new Timestamp(item.getTo_time().getTime()));
		}
		returnObj.setPastNotesList(notesList);


		if (!(BasicUtils.isEmpty(fromTime) || BasicUtils.isEmpty(toTime))) {
			SimpleDateFormat sdfDB = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
			long hrsMillis = 60 * 60 * 1000;

			try {
				Timestamp yestDate = new Timestamp((Long.parseLong(fromTime) + offset) - (24 * hrsMillis));
				Timestamp fromDateOffset = new Timestamp(60000+Long.parseLong(fromTime));

				// removed the one minute extra from the from time field
//				Timestamp fromDateOffset = new Timestamp(Long.parseLong(fromTime));

				Timestamp toDateOffset = new Timestamp( 59000+Long.parseLong(toTime));

				Timestamp fromDate = new Timestamp(60000+Long.parseLong(fromTime) + offset);
				Timestamp toDate = new Timestamp( 59000+Long.parseLong(toTime) + offset);

				String yestStr = sdfDB.format(yestDate);
				String todayStr = sdfDB.format(fromDate);

				currentNotes.setUhid(uhid);
				currentNotes.setFrom_time(fromDateOffset);
				currentNotes.setTo_time(toDateOffset);

				String cgaStr = "";

				List<BabyDetail> babyDetailList = inicuDoa
						.getListFromMappedObjQuery(HqlSqlQueryConstants.getBabyDetailList(uhid));
				if (!BasicUtils.isEmpty(babyDetailList)) {
					BabyDetail babyDetails = babyDetailList.get(0);
					if (!BasicUtils.isEmpty(babyDetails.getBirthweight())) {
						String birthWeightStr = babyDetailList.get(0).getBirthweight().toString();

						//	notesText += "Birth Weight: " + birthWeightStr.substring(0, birthWeightStr.indexOf("."))
						//	+ " gms \n";
					}

					if (!BasicUtils.isEmpty(babyDetails.getActualgestationweek())) {
						java.util.Date dateOfBirth = babyDetails.getDateofbirth();
						long gestationDaysAfterBirth = (fromDate.getTime() - dateOfBirth.getTime()) / (24 * hrsMillis);

						int dischargeGetationDays = 0;
						if (!BasicUtils.isEmpty(babyDetails.getActualgestationweek())) {
							dischargeGetationDays = babyDetails.getActualgestationweek() * 7;

						}
						if (!BasicUtils.isEmpty(babyDetails.getActualgestationdays())) {
							dischargeGetationDays += babyDetails.getActualgestationdays();
						}

						long currentGestaionTotalDays = gestationDaysAfterBirth + dischargeGetationDays;
						if (currentGestaionTotalDays % 7 == 0) {
							cgaStr = Integer.valueOf(currentGestaionTotalDays / 7 + "") + " Weeks";
						} else {
							long actualDays = currentGestaionTotalDays % 7;
							cgaStr = Integer.valueOf((currentGestaionTotalDays - actualDays) / 7 + "") + " Weeks "
									+ Integer.valueOf(actualDays + "") + " Days";
						}
					}
				}

				Float todayWeight = null;
				List<BabyVisit> babyVisitList = inicuDoa
						.getListFromMappedObjQuery(HqlSqlQueryConstants.getBabyVisitList(uhid, yestStr, todayStr));
				if (!BasicUtils.isEmpty(babyVisitList)) {
					Iterator<BabyVisit> itr = babyVisitList.iterator();
					Float yestWeight = null;

					while (itr.hasNext()) {
						BabyVisit obj = itr.next();
						if (null == todayWeight && obj.getVisitdate().toString().equalsIgnoreCase(todayStr)) {
							todayWeight = obj.getCurrentdateweight();
						} else if (null == yestWeight && obj.getVisitdate().toString().equalsIgnoreCase(yestStr)) {
							yestWeight = obj.getCurrentdateweight();
						}
					}
					if (todayWeight != null) {
						String todayWeightStr = todayWeight.toString();
						//	notesText += "Today Weight: " + todayWeightStr.substring(0, todayWeightStr.indexOf("."))
						//			+ " gms \n";

						if (yestWeight != null) {
							String weightDiffStr = "" + (todayWeight - yestWeight);
							//	notesText += "Weight Diff: " + weightDiffStr.substring(0, weightDiffStr.indexOf("."))
							//			+ " gms \n";
						} else {
							//	notesText += "";
						}
					}
				}

				String finalDiagnosisUnique = "";

				try {
					// diagnosis as in discharge
					List<AdmissionNotes> listAdmissionNotes = inicuDoa
							.getListFromMappedObjQuery(HqlSqlQueryConstants.getAdmissionNotesList(uhid));
					String diagnosisText = "";
					if (!BasicUtils.isEmpty(listAdmissionNotes)
							&& !BasicUtils.isEmpty(listAdmissionNotes.get(0).getDiagnosis())) {
						diagnosisText = listAdmissionNotes.get(0).getDiagnosis();
					}

					List<DashboardFinalview> listDiagnosisDetails = inicuDoa
							.getListFromMappedObjQuery(HqlSqlQueryConstants.getDashboardViewList(uhid));

					if (!BasicUtils.isEmpty(listDiagnosisDetails)) {
						if (!BasicUtils.isEmpty(listDiagnosisDetails.get(0).getDiagnosis())) {
							String diagnosis = listDiagnosisDetails.get(0).getDiagnosis();

							int flag = 0;
							if(diagnosisText.contains("Sepsis")){
								diagnosisText = getSepsisType(diagnosisText,uhid);
								flag =1;
							}

							if (diagnosis.contains("Sepsis")) {
								if(flag == 0) {
									diagnosis = getSepsisType(diagnosis, uhid);
								}else if (flag == 1){

									// replace the Sepsis with empty string
									diagnosis = diagnosis.replace("Sepsis","");
								}
							}

							String diagnosisStr = diagnosisText.replace(",", "/") + "/"
									+ diagnosis.replaceAll(",", "/");


							String[] daignosisArr = diagnosisStr.split("/");
							diagnosisStr = "";
							for (String daignosis : daignosisArr) {
								if (diagnosisStr.isEmpty()) {
									diagnosisStr = daignosis;
								} else if (!diagnosisStr.contains(daignosis.trim())) {
									diagnosisStr += "/ " + daignosis.trim();
								}
							}
							diagnosisText = diagnosisStr;
						}
					}

					String queryRop = "select obj from ScreenRop as obj where uhid = '" + uhid + "'";
					List<ScreenRop> ropLists = inicuDoa.getListFromMappedObjQuery(queryRop);
					boolean leftRop = false;
					boolean rightRop = false;
					String ropDiagnosis = "";
					if(!BasicUtils.isEmpty(ropLists)) {
						for(ScreenRop ropObject: ropLists) {
							if(ropObject.getIs_rop()!=null && ropObject.getIs_rop()==true) {
								ropDiagnosis = "ROP";
								if(ropObject.getIs_rop_left()!=null && ropObject.getIs_rop_left() == true) {
									leftRop = true;
								}
								if(ropObject.getIs_rop_right()!=null && ropObject.getIs_rop_right() == true) {
									rightRop = true;
								}
							}
						}


						if(leftRop == true && rightRop == false) {
							ropDiagnosis = "ROP Left eye";
							diagnosisText += "/ " + ropDiagnosis;
						}
						else if(rightRop == true && leftRop == false) {
							ropDiagnosis = "/ " + "ROP Right eye";
							diagnosisText += ropDiagnosis;
						}
						else if(rightRop == true && leftRop == true) {
							ropDiagnosis = "/ " + "ROP Both eyes";
							diagnosisText += ropDiagnosis;
						}
						else {
							diagnosisText += "/ " + ropDiagnosis;
						}


					}

					// handle unique in diagnosis.
					if (!BasicUtils.isEmpty(diagnosisText)) {
						String[] finalDiagArr = diagnosisText.split("/");
						for (String diag : finalDiagArr) {
							if (finalDiagnosisUnique.isEmpty()) {
								finalDiagnosisUnique = diag.replace(" ", "");
							} else {
								if (!finalDiagnosisUnique.contains(diag.replace(" ", ""))) {
									finalDiagnosisUnique += "/ " + diag.replace(" ", "");
								}
							}
						}
					}

					finalDiagnosisUnique = finalDiagnosisUnique.replace("RespiratoryDistress", "Respiratory Distress").replace("FeedIntolerance", "Feed Intolerance").replace("CongenitalHeartDisease", "Congenital Heart Disease").replace("ROPBotheyes", "ROP Both eyes").replace("ROPLefteye", "ROP Left eye").replace("ROPRighteye", "ROP Right eye").replace("Jaundice", "NNH").replace("cultureproven", "culture proven").replace("ClinicalSepsis", "Clinical Sepsis");


					//	notesText += " Diagnosis: " + finalDiagnosisUnique.replace(",", "/") + ". \n";
				} catch (Exception e) {
					e.printStackTrace();
					String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
					databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID,
							loggedInUser, uhid, "getNursingNotes Diagnosis", BasicUtils.convertErrorStacktoString(e));
				}

				if(BasicUtils.isEmpty(notesList) && notesList.size()==0)
				{
					String query = "Select status_at_admission from AdmissionNotes where uhid='"+uhid+"'";
					List result = inicuDoa.getListFromMappedObjQuery(query);
					if(!BasicUtils.isEmpty(result))
					{
						String status = result.get(0).toString();
						//	notesText+= "Status at admission : "+status+"\n";
					}
				}

				//Code according to NABH guidelines
				//1. Doctor's Orders
				String doctorOrdersStr = "Doctor's orders - \n";

				String respSupportSql = "select obj from RespSupport obj where uhid='" + uhid + "' and creationtime <= '" + toDate + "' order by creationtime desc";
				List<RespSupport> respSupportList = inicuDoa.getListFromMappedObjQuery(respSupportSql);
				if (!BasicUtils.isEmpty(respSupportList) && !BasicUtils.isEmpty(respSupportList.get(0).getIsactive()) && respSupportList.get(0).getIsactive() && respSupportList.get(0).getRsVentType() !=null) {

						currentRespSupport = "Baby is kept on " + respSupportList.get(0).getRsVentType() + " mode.";

				}else {
					currentRespSupport = "Baby is in room air.";
				}
				doctorOrdersStr += currentRespSupport+" \n";

				doctorOrdersStr += "Current Diagnosis of the baby is " + finalDiagnosisUnique.replace(",", "/") + ". \n";



				String currentNutritionOrder = "";

				List<BabyfeedDetail> listBabyFeedDetails = (List<BabyfeedDetail>) inicuDoa
						.getListFromMappedObjQuery(HqlSqlQueryConstants.getBabyFeedList(uhid, fromDate, toDate));


				if(!BasicUtils.isEmpty(listBabyFeedDetails) && !BasicUtils.isEmpty(listBabyFeedDetails.get(0).getWorkingWeight())) {

					todayWeight = listBabyFeedDetails.get(0).getWorkingWeight() * 1000;

				}


 				if(todayWeight ==null) {


					List<BabyVisit> visitList = (List<BabyVisit>) inicuDoa
							.getListFromMappedObjRowLimitQuery(HqlSqlQueryConstants.getLastAnthropometry(uhid,todayStr), 1);
					if (!BasicUtils.isEmpty(visitList)) {
						BabyVisit visitObj = visitList.get(0);
						todayWeight = visitObj.getWorkingweight();
					}

				}

 				if(todayWeight!=null) {
					String doctorText = getNursingNotesDoctorOrders(uhid,fromDateOffset, toDateOffset, todayWeight / 1000);
					if (!BasicUtils.isEmpty(doctorText)) {
						currentNutritionOrder = "Nutrition Plan - " + doctorText;
						doctorOrdersStr += currentNutritionOrder + "\n";
					}
				}



				//Surfactant
				boolean isSurfactant = false;
				String respRdsQuery = "SELECT obj FROM SaRespRds as obj where uhid='" + uhid+"' and assessment_time >= '" + fromDateOffset + "' and assessment_time <= '" + toDateOffset +"'";

				List<SaRespRds> rdsQList = notesDoa.getListFromMappedObjNativeQuery(respRdsQuery);
				for(SaRespRds obj : rdsQList) {
					if(!BasicUtils.isEmpty(obj.getSufactantname())) {
						isSurfactant = true;
						break;
					}
				}


				if (isSurfactant) {
					doctorOrdersStr += "Surfactant given. ";
				}


				// Sufactant end

				long oneDay = 24 * 60 * 60 * 1000;
				String currentMedicine = "";
				List<BabyPrescription> medicationList = inicuDoa.getListFromMappedObjQuery(
						HqlSqlQueryConstants.getCurrentMedicationList(uhid, fromDate, toDate));
				String contMedicine = "";
				String newMedicine = "";

				for (BabyPrescription item : medicationList) {

					if(BasicUtils.isEmpty(item.getEnddate())) {
						if (!BasicUtils.isEmpty(item.getMedicineOrderDate()) && item.getMedicineOrderDate().compareTo(fromDate) < 0) {
							Double day = Math.ceil((fromDate.getTime() - item.getMedicineOrderDate().getTime()) / oneDay) + 1;

							if(BasicUtils.isEmpty(contMedicine)) {
								contMedicine += "Continue " + item.getMedicinename() + " for day " + day.intValue();
							} else {
								contMedicine += ", " + item.getMedicinename() + " for day " + day.intValue();
							}
						} else {
							newMedicine += ", " + item.getMedicinename();
						}
					}
				}

				if (!contMedicine.isEmpty()) {
					doctorOrdersStr += contMedicine;
				}

				if (!newMedicine.isEmpty()) {
					currentMedicine += "Start " + newMedicine.substring(2) + ". ";
					doctorOrdersStr += currentMedicine;
				}

				String bloodProductStr = "";
				List<DoctorBloodProducts> bloodProductDetailList = (List<DoctorBloodProducts>)inicuDoa.
						getListFromMappedObjQuery(HqlSqlQueryConstants.getCurrentBloodProductList(uhid, fromDateOffset, toDateOffset));
				if (!BasicUtils.isEmpty(bloodProductDetailList)) {
					for(int i=0;i<bloodProductDetailList.size();i++) {
						if(!BasicUtils.isEmpty(bloodProductDetailList.get(i).getBlood_product())) {
							if(BasicUtils.isEmpty(bloodProductStr))
								bloodProductStr = bloodProductDetailList.get(i).getBlood_product();
							else
								bloodProductStr += ", " + bloodProductDetailList.get(i).getBlood_product();
						}
					}
					doctorOrdersStr += "\n"+bloodProductStr + " ordered. ";
				}

				String currentPhototherapy  = "";
				String phototherapySql = "select obj from SaJaundice obj where uhid='" + uhid + "' and assessment_time >= '" + fromDateOffset + "' and assessment_time <= '" + toDateOffset + "' order by assessment_time desc";
				List<SaJaundice> phototherapyList = inicuDoa.getListFromMappedObjQuery(phototherapySql);
				for(SaJaundice jaund : phototherapyList) {
					if(!BasicUtils.isEmpty(jaund.getPhototherapyvalue())) {
						if(jaund.getPhototherapyvalue().equalsIgnoreCase("Start")) {
							if(BasicUtils.isEmpty(currentPhototherapy)) {
								currentPhototherapy = "Started Phototherapy";
							}else {
								currentPhototherapy += ", Started Phototherapy";
							}
						}
						else if(jaund.getPhototherapyvalue().equalsIgnoreCase("Stop")) {
							if(BasicUtils.isEmpty(currentPhototherapy)) {
								currentPhototherapy = "Stopped Phototherapy";
							}else {
								currentPhototherapy += ", Stopped Phototherapy";
							}
						}
						else if(jaund.getPhototherapyvalue().equalsIgnoreCase("Continue")) {
							if(BasicUtils.isEmpty(currentPhototherapy)) {
								currentPhototherapy = "Continued Phototherapy";
							}else {
								currentPhototherapy += ", Continued Phototherapy";
							}
						}
					}
				}
				if(!BasicUtils.isEmpty(currentPhototherapy)) {
					currentPhototherapy += ". ";
					doctorOrdersStr += "\n"+currentPhototherapy;
				}

				String screeningText = getDailyProgressNotesScreening(uhid, fromDateOffset, toDateOffset);
				if(!BasicUtils.isEmpty(screeningText))
					doctorOrdersStr += "\n"+screeningText;


				String procedureStrFinal = "";
				List<String> procedureDetailFinal = (List<String>) inicuDoa
						.getListFromNativeQuery(HqlSqlQueryConstants.getCurrentProcedureUsage(uhid, fromDate, toDate));
				if (!BasicUtils.isEmpty(procedureDetailFinal)) {
					for (String procedureName : procedureDetailFinal) {
						if (procedureStrFinal.isEmpty()) {
							procedureStrFinal = "" + procedureName;
						} else {
							procedureStrFinal += ", " + procedureName;
						}
					}
					procedureStrFinal +=  " done. ";
					doctorOrdersStr += "\n"+procedureStrFinal;
				}

				String investigationStr = "";
				String investigationStrFinal = "";
				String investigationQuery = "select obj from InvestigationOrdered obj where uhid='" + uhid + "' and investigationorder_time >= '" + fromDateOffset + "' and investigationorder_time <= '" + toDateOffset + "' order by investigationorder_time desc";
				List<InvestigationOrdered> investigationList = inicuDoa.getListFromMappedObjQuery(investigationQuery);

				for (InvestigationOrdered investigationOrdered : investigationList) {
					if (!BasicUtils.isEmpty(investigationOrdered.getTestname())) {
						if (investigationStr.isEmpty()) {
							investigationStr = "" + investigationOrdered.getTestname();
						} else {
							investigationStr += ", " + investigationOrdered.getTestname();
						}
					}

				}
				if(!BasicUtils.isEmpty(investigationStr)) {
					investigationStrFinal +=  "\nInvestigation ordered - " + investigationStr;
					doctorOrdersStr += investigationStrFinal;
				}

				notesText += doctorOrdersStr + "\n";


				// Monitoring start

				notesText += "\nMonitoring - " + "\n";


				if (!BasicUtils.isEmpty(cgaStr)) {
					notesText += "PMA: " + cgaStr + " \n";
				}



					SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm a");



				List<BabyVisit> lastAnthropometryList = inicuDoa.getListFromMappedObjQuery(
						//	HqlSqlQueryConstants.getBabyAnthropometryListLast(uhid, fromDateOffset, toDateOffset));
						//	HqlSqlQueryConstants.getBabyVisitList(uhid, sdf1.format(fromDate.getTime())+"", sdf1.format(toDate.getTime())+""));
						"SELECT obj FROM BabyVisit obj where uhid = '" + uhid + "' and visitdate >= '" + sdf1.format(fromDate.getTime())
								+ "' and visitdate <= '" + sdf1.format(toDate.getTime()) + "'  and visittime >= '" + sdf2.format(fromDate.getTime()) +
								"' and visittime <= '" + sdf2.format(toDate.getTime()) + "'  order by visitdate, visittime desc");

				if(!BasicUtils.isEmpty(lastAnthropometryList)) {

					String anthroStr = "";
					//Float	wt  = lastAnthropometryList.get(0).getWorkingweight();

					if(!BasicUtils.isEmpty(todayWeight)) {
						anthroStr+=	" Today's Weight - "+todayWeight.intValue() + " gms";
					}

					Float height = lastAnthropometryList.get(0).getCurrentdateheight();
					if(!BasicUtils.isEmpty(height)) {
						String ht = "Length - "+height.intValue()  + " cms";
						if (!anthroStr.isEmpty()) anthroStr += ", ";
						anthroStr+=		ht;
					}

					Float headcircumferrence = lastAnthropometryList.get(0).getCurrentdateheadcircum();


					if(!BasicUtils.isEmpty(headcircumferrence)) {
						String hc = "Head Circumference - "+headcircumferrence.intValue()  + " cms";
						if (!anthroStr.isEmpty()) anthroStr += ", ";

						anthroStr+=		headcircumferrence;
					}

					notesText += "Anthropometry : " + anthroStr + " \n";


				}



				long diffHours = (toDate.getTime()- fromDate.getTime())/(1000*60*60);


				System.out.println("diffHours-------"+diffHours);
				// new code starts from here
				List<NursingVitalparameter> listVitalDetails = (List<NursingVitalparameter>) inicuDoa
						.getListFromMappedObjQuery(HqlSqlQueryConstants.getNursingVitalList(uhid, fromDate, toDate) + " desc");

				long spo2Count = 0;

				if (!BasicUtils.isEmpty(listVitalDetails)) {



					long countOfVitals = listVitalDetails.size();



					long freqOfVitals = 0;

					if(countOfVitals>diffHours)
					{freqOfVitals = 1;}
					else
					{freqOfVitals =	Math.round(diffHours/countOfVitals);}



					notesText += "Vitals were monitored and recorded every "+ freqOfVitals+ " hourly.\n";


 					HashMap<String, List<NursingVitalparameter>> vitalMap = new HashMap<String, List<NursingVitalparameter>>() {
						private static final long serialVersionUID = 1L;
						{
							put("consciousness", new ArrayList<>());
							put("position", new ArrayList<>());
							put("rbs", new ArrayList<>());
							put("tcb", new ArrayList<>());
						}
					};

					String consciousness = "";
					String position = "";


					NursingVitalparameter vitalObj = listVitalDetails.get(0);

					if(!BasicUtils.isEmpty(vitalObj.getHrRate())) currentVitals+="HR : "+vitalObj.getHrRate().intValue()+", ";

					if(!BasicUtils.isEmpty(vitalObj.getSpo2())) currentVitals+="SpO2 : "+vitalObj.getSpo2()+", ";

					if(!BasicUtils.isEmpty(vitalObj.getRrRate())) currentVitals+="RR : "+vitalObj.getRrRate().intValue()+", ";

					if(!BasicUtils.isEmpty(vitalObj.getSystBp()) &&!BasicUtils.isEmpty(vitalObj.getDiastBp())) currentVitals+="BP - Syst/Diast : "+vitalObj.getSystBp()+"/"+vitalObj.getDiastBp()+", ";

					if(!BasicUtils.isEmpty(vitalObj.getPulserate())) currentVitals+="PR : "+vitalObj.getPulserate()+", ";

					if(!BasicUtils.isEmpty(vitalObj.getCentraltemp())) currentVitals+="Central Temp : "+vitalObj.getCentraltemp()+", ";

					if(!BasicUtils.isEmpty(vitalObj.getPeripheraltemp())) currentVitals+="Peripheral Temp : "+vitalObj.getPeripheraltemp()+", ";

					if(!BasicUtils.isEmpty(vitalObj.getCft())) currentVitals+="CFT : "+vitalObj.getCft()+", ";

					if(currentVitals.endsWith(", "))  currentVitals = currentVitals.substring(0, currentVitals.length() - 2);

					for (NursingVitalparameter item : listVitalDetails) {

 						if (!BasicUtils.isEmpty(item.getSpo2())) spo2Count++;


						if (!(BasicUtils.isEmpty(item.getConsciousness())
								|| consciousness.equalsIgnoreCase(item.getConsciousness()))) {
							consciousness = item.getConsciousness();
							vitalMap.get("consciousness").add(item);
						}

						if (!(BasicUtils.isEmpty(item.getVpPosition())
								|| position.equalsIgnoreCase(item.getVpPosition()))) {
							position = item.getVpPosition();
							vitalMap.get("position").add(item);
						}

						if (!BasicUtils.isEmpty(item.getRbs())) {
							vitalMap.get("rbs").add(item);
						}

						if (!BasicUtils.isEmpty(item.getTcb())) {
							vitalMap.get("tcb").add(item);
						}
					}

					if (!BasicUtils.isEmpty(vitalMap.get("consciousness"))) {
						String consciousnessStr = "";
						List<NursingVitalparameter> vitalList = vitalMap.get("consciousness");
						if (vitalList.size() == 1) {
							consciousnessStr = "The baby remained " + vitalList.get(0).getConsciousness();
						} else {
							NursingVitalparameter item1 = vitalList.get(0);
							NursingVitalparameter item2 = null;
							for (int i = 1; i < vitalList.size(); i++) {
								item2 = vitalList.get(i);

								long timeDiff = item2.getEntryDate().getTime() - item1.getEntryDate().getTime();
								if (consciousnessStr.isEmpty()) {
									consciousnessStr = "The baby remained " + item1.getConsciousness() + " for "
											+ (timeDiff < hrsMillis
											? (Math.round((timeDiff * 60) / hrsMillis)) + " mins, "
											: (Math.round((timeDiff) / hrsMillis)) + " hrs, ");
								} else {
									consciousnessStr += "remained " + item1.getConsciousness() + " for next "
											+ (timeDiff < hrsMillis
											? (Math.round((timeDiff * 60) / hrsMillis)) + " mins, "
											: (Math.round((timeDiff) / hrsMillis)) + " hrs, ");
								}

								item1 = item2;
								if ((i + 1) == vitalList.size()) {
									timeDiff = toDate.getTime() - item1.getEntryDate().getTime();
									consciousnessStr += "remained " + item1.getConsciousness() + " for next "
											+ (timeDiff < hrsMillis
											? (Math.round((timeDiff * 60) / hrsMillis)) + " mins"
											: (Math.round((timeDiff) / hrsMillis)) + " hrs");
								}
							}
						}
						//	notesText += consciousnessStr + ". ";
					}

					if (!BasicUtils.isEmpty(vitalMap.get("position"))) {
						String positionStr = "";
						List<NursingVitalparameter> vitalList = vitalMap.get("position");
						if (vitalList.size() == 1) {
							positionStr = "The baby remained in " + vitalList.get(0).getVpPosition() + " position";
						} else {
							NursingVitalparameter item1 = vitalList.get(0);
							NursingVitalparameter item2 = null;
							for (int i = 1; i < vitalList.size(); i++) {
								item2 = vitalList.get(i);

								long timeDiff = item2.getEntryDate().getTime() - item1.getEntryDate().getTime();
								if (positionStr.isEmpty()) {
									positionStr = "The baby remained in " + item1.getVpPosition() + " position for "
											+ (timeDiff < hrsMillis
											? (Math.round((timeDiff * 60) / hrsMillis)) + " mins, "
											: (Math.round((timeDiff) / hrsMillis)) + " hrs, ");
								} else {
									positionStr += "remained in " + item1.getVpPosition() + " position for next "
											+ (timeDiff < hrsMillis
											? (Math.round((timeDiff * 60) / hrsMillis)) + " mins, "
											: (Math.round((timeDiff) / hrsMillis)) + " hrs, ");
								}

								item1 = item2;
								if ((i + 1) == vitalList.size()) {
									timeDiff = toDate.getTime() - item1.getEntryDate().getTime();
									positionStr += "remained in " + item1.getVpPosition() + " position for next "
											+ (timeDiff < hrsMillis
											? (Math.round((timeDiff * 60) / hrsMillis)) + " mins"
											: (Math.round((timeDiff) / hrsMillis)) + " hrs");
								}
							}
						}
						//position removed
						//notesText += positionStr + ". ";
					}

					//rbs and tcb removed
//					if (!BasicUtils.isEmpty(vitalMap.get("rbs"))) {
//						List<NursingVitalparameter> vitalList = vitalMap.get("rbs");
//						if (vitalList.size() == 1) {
//							NursingVitalparameter item = vitalList.get(0);
//							if (item.getHypoglycemia() != null && item.getHypoglycemia()) {
//								notesText += "The baby had one episode of "
//										+ ((item.getSymptomaticStatus() != null && item.getSymptomaticStatus())
//												? "Symptomatic (with symptoms of " + item.getSymptomaticValue() + ")"
//												: "Asymptomatic")
//										+ " Hypoglycemia with blood sugar level of " + item.getRbs() + " mg/dL at "
//										+ sdf.format(new Timestamp(item.getEntryDate().getTime()));
//							} else if (item.getHyperglycemia() != null && item.getHyperglycemia()) {
//								notesText += "The baby had one episode of "
//										+ ((item.getSymptomaticStatus() != null && item.getSymptomaticStatus())
//												? "Symptomatic (with symptoms of " + item.getSymptomaticValue() + ")"
//												: "Asymptomatic")
//										+ " Hyperglycemia with blood sugar level of " + item.getRbs() + " mg/dL at "
//										+ sdf.format(new Timestamp(item.getEntryDate().getTime()));
//							} else {
//								notesText += "The blood sugar level was normal. ";
//							}
//						} else {
//							boolean normalFlag = true;
//							String rbsStr = "";
//							for (NursingVitalparameter item : vitalList) {
//
//								if (item.getHypoglycemia() != null && item.getHypoglycemia()) {
//									normalFlag = false;
//									if (rbsStr.isEmpty()) {
//										rbsStr += "The baby had ";
//									} else {
//										rbsStr += ", ";
//									}
//
//									rbsStr += ((item.getSymptomaticStatus() != null && item.getSymptomaticStatus())
//											? "Symptomatic (with symptoms of " + item.getSymptomaticValue() + ")"
//											: "Asymptomatic") + " Hypoglycemia with blood sugar level of "
//											+ item.getRbs() + " mg/dL at "
//											+ sdf.format(new Timestamp(item.getEntryDate().getTime()));
//								} else if (item.getHyperglycemia() != null && item.getHyperglycemia()) {
//									normalFlag = false;
//									if (rbsStr.isEmpty()) {
//										rbsStr += "The baby had ";
//									} else {
//										rbsStr += ", ";
//									}
//
//									rbsStr += ((item.getSymptomaticStatus() != null && item.getSymptomaticStatus())
//											? "Symptomatic (with symptoms of " + item.getSymptomaticValue() + ")"
//											: "Asymptomatic") + " Hyperglycemia with blood sugar level of "
//											+ item.getRbs() + " mg/dL at "
//											+ sdf.format(new Timestamp(item.getEntryDate().getTime()));
//								}
//							}
//
//							if (normalFlag) {
//								notesText += "The blood sugar levels were normal. ";
//							} else {
//								notesText += rbsStr + ". ";
//							}
//						}
//					}
//
//					if (!BasicUtils.isEmpty(vitalMap.get("tcb"))) {
//						List<NursingVitalparameter> vitalList = vitalMap.get("tcb");
//						if (vitalList.size() == 1) {
//							notesText += "TCB was recorded once during the shift. The value of TCB was "
//									+ vitalList.get(0).getTcb() + ". ";
//						} else {
//							notesText += "TCB were recorded " + vitalList.size()
//									+ " times during the shift. The value of TCB were ";
//							String tcbValues = "";
//							for (int i = 0; i < vitalList.size(); i++) {
//								tcbValues += ", " + vitalList.get(i).getTcb();
//							}
//							notesText += tcbValues.substring(2) + ". ";
//						}
//					}
//				//	notesText += "Vitals were monitored continuously and recorded on hourly basis. ";
				}


				if (spo2Count != 0) {

					long freqOfSpo2 = 1 ;
					if(spo2Count>diffHours)
					{freqOfSpo2 = 1;}
					else
					{freqOfSpo2 =	Math.round(diffHours/spo2Count);}


					notesText += "SPO2 was monitored and recorded every " + freqOfSpo2 + " hourly.\n";

				}


				String seizuresText = "";
				String apneaText = "";
				int apneaCount =0;
				int seizuresCount =0;
				int otherEventsCount =0;
				int desatCount =0;


				List<NursingEpisode> listEventDetails = (List<NursingEpisode>) inicuDoa
						.getListFromMappedObjQuery(HqlSqlQueryConstants.getNursingEventList(uhid, fromDate, toDate));
				if (!BasicUtils.isEmpty(listEventDetails)) {
					HashMap<String, List<NursingEpisode>> eventMap = new HashMap<String, List<NursingEpisode>>() {
						private static final long serialVersionUID = 1L;

						{
							put("apnea", new ArrayList<>());
							put("seizures", new ArrayList<>());
						}
					};

					for (NursingEpisode item : listEventDetails) {



						if (item.getApnea() != null && item.getApnea()) {
							eventMap.get("apnea").add(item);
							apneaCount++;
						}

						if (item.getSeizures() != null && item.getSeizures()) {
							eventMap.get("seizures").add(item);
							seizuresCount++;
						}


						if (item.getDesaturation() != null && item.getDesaturation()) {
							//	eventMap.get("seizures").add(item);
							desatCount++;
						}


						if (item.getOthers() != null) {
							//eventMap.get("seizures").add(item);
							otherEventsCount++;
						}
					}


					if (BasicUtils.isEmpty(eventMap.get("apnea")) && BasicUtils.isEmpty(eventMap.get("apnea"))) {
						//	notesText += "No significant events of apnea and seizures were observed. ";
					} else {
						if (!BasicUtils.isEmpty(eventMap.get("seizures"))) {
							List<NursingEpisode> eventList = eventMap.get("seizures");
							String seizureStr = "The baby had ";
							for (NursingEpisode item : eventList) {
								seizureStr += "episode of "
										+ (item.getSeizureType() == null ? "" : item.getSeizureType()) + " Seizure"
										+ ((item.getSymptomaticStatus() != null && item.getSymptomaticStatus())
										? " with symptoms of " + item.getSymptomaticValue()
										: "")
										+ ((BasicUtils.isEmpty(item.getSeizureDuration())) ? ""
										: " with the duration of " + item.getSeizureDuration() + " "
										+ item.getDuration_unit_seizure())
										+ " at " + sdf.format(new Timestamp(item.getCreationtime().getTime() + offset))
										+ ", ";
							}
							seizuresText += seizureStr.substring(0, seizureStr.length() - 2) + ". ";
						}

						if (!BasicUtils.isEmpty(eventMap.get("apnea"))) {
							List<NursingEpisode> eventList = eventMap.get("apnea");
							String apneaStr = "The baby had ";
							for (NursingEpisode item : eventList) {
								apneaStr += "episode of apnea"
										+ ((BasicUtils.isEmpty(item.getApneaDuration())) ? ""
										: " with the duration of " + item.getApneaDuration() + " "
										+ item.getDuration_unit_apnea())
										+ " at " + sdf.format(new Timestamp(item.getCreationtime().getTime() + offset))
										+ ((BasicUtils.isEmpty(item.getSpo2())) ? ""
										: ", minimum spO2 recorded was " + item.getSpo2() + "%")
										+ ((item.getBradycardia() != null && item.getBradycardia())
										? ", was accompanied with Bradycardia"
										: "")
										+ ((BasicUtils.isEmpty(item.getHr())) ? ""
										: ", minimum heart rate recorded was " + item.getHr() + " bpm")
										+ ((BasicUtils.isEmpty(item.getRecovery())) ? ""
										: ", baby recovered "
										+ ((item.getRecovery().equalsIgnoreCase("Spontaneous"))
										? "spontaneously"
										: "with " + item.getRecovery()))
										+ ", ";
							}
							apneaText += apneaStr.substring(0, apneaStr.length() - 2) + ". ";
						}
					}
				} else {
					//notesText += "No significant events of apnea and seizures were observed. ";
				}

				String ventilatorStr = "";
				List<NursingVentilaor> listVentilatorDetails = (List<NursingVentilaor>) inicuDoa
						.getListFromMappedObjQuery(
								HqlSqlQueryConstants.getNursingVentilatorList(uhid, fromDate, toDate));
				if (!BasicUtils.isEmpty(listVentilatorDetails)) {
					String ventMode = "";
					String fio2 = "";
					String flowRate = "";

					List<RefVentilationmode> listVentilatorModes = (List<RefVentilationmode>) inicuDoa
							.getListFromMappedObjQuery("SELECT obj FROM RefVentilationmode obj");
					HashMap<String, String> ventMap = new HashMap<String, String>();
					for (RefVentilationmode ventObj : listVentilatorModes) {
						ventMap.put(ventObj.getVentmodeid(), ventObj.getVentilationmode());
					}

					long ventCount = 0;

					for (NursingVentilaor obj : listVentilatorDetails) {
						ventCount++;
						if (ventilatorStr.isEmpty()) {
							ventMode = obj.getVentmode();
							ventilatorStr = "Baby was on " + ventMap.get(obj.getVentmode()) ;
							//	ventilatorStr += " at " + sdf.format(new Timestamp(obj.getEntryDate().getTime())) + ". ";
						} /*else if (ventMode.equalsIgnoreCase(obj.getVentmode()) && fio2.equalsIgnoreCase(obj.getFio2())
								&& flowRate.equalsIgnoreCase(obj.getFlowPerMin())) {
							continue;
						}*/
						else if (!(ventMode.equalsIgnoreCase(obj.getVentmode()))) {
							ventilatorStr += "and shifted to " + ventMap.get(obj.getVentmode());
							if (!BasicUtils.isEmpty(obj.getFio2())) {
								fio2 = obj.getFio2();
								ventilatorStr += " with FiO2 " + obj.getFio2() + "%";
							}

							if (!BasicUtils.isEmpty(obj.getFlowPerMin())) {
								flowRate = obj.getFlowPerMin();
								ventilatorStr += " @ " + obj.getFlowPerMin() + " ltr/min air flow";
							}

							//ventilatorStr += " at " + sdf.format(new Timestamp(obj.getEntryDate().getTime())) + ". ";

						}

						/*else {
							ventilatorStr += "The parameter of ventilation was changed to "
									+ ventMap.get(obj.getVentmode());
						}*/


						ventMode = obj.getVentmode();
					}
					notesText += ventilatorStr;

					if (ventCount != 0) {


						long freqOfVent = 1 ;
						if(ventCount>diffHours)
						{freqOfVent = 1;}
						else
						{freqOfVent =	Math.round(diffHours/ventCount);}

						notesText += " and its parameters were recorded every " + freqOfVent + " hourly.\n";

					}

				}



				List<Object> listMedication = (List<Object>) inicuDoa.getListFromNativeQuery(
						HqlSqlQueryConstants.getCurrentNursingMedicationList(uhid, fromDateOffset, toDateOffset));
				if (!BasicUtils.isEmpty(listMedication)) {
					Iterator<Object> itr = listMedication.iterator();
					String medicationStr = "";
					while (itr.hasNext()) {
						Object[] medicationObj = (Object[]) itr.next();
						if (((BigInteger) medicationObj[1]).intValue() == 1) {
							medicationStr += ", " + medicationObj[1] + " dose of " + medicationObj[0];
						} else {
							medicationStr += ", " + medicationObj[1] + " doses of " + medicationObj[0];
						}
					}
					notesText += "Drug " + medicationStr.substring(2) + " were administered.\n";
				}


				//	Drug A, B, C were administered. <Event Name> X number were recorded (Collective medication executions from the current nursing shift)
// above line Event doubt

				String monitoringBloodProductStr = "";


				List<NursingBloodproduct> bloodProductList = inicuDoa.getListFromMappedObjQuery(
						HqlSqlQueryConstants.getNursingBloodProductList(uhid, fromDateOffset, toDateOffset));

				if (!BasicUtils.isEmpty(bloodProductList)) {
					for (NursingBloodproduct item : bloodProductList) {
						if (!BasicUtils.isEmpty(item.getDelivered_volume())) {


							//get Blood Product NAme from doctor order
							List<DoctorBloodProducts> doctorList = inicuDoa.getListFromMappedObjQuery("select obj from DoctorBloodProducts obj where doctor_blood_products_id= '" + item.getDoctor_blood_products_id() + "'");
							if(!BasicUtils.isEmpty(doctorList)) {
								if (BasicUtils.isEmpty(monitoringBloodProductStr))
									monitoringBloodProductStr = doctorList.get(0).getBlood_product();
								else
									monitoringBloodProductStr += ", " + doctorList.get(0).getBlood_product();
							}


 						}
					}
				}


				if (!BasicUtils.isEmpty(bloodProductList)) {
					monitoringBloodProductStr +=  " was verified and administered ";

					notesText += monitoringBloodProductStr;


					//				Vitals were recorded and monitored during the transfusion,

					//above doubt

					//				Adverse reactions observed were <Name of the selected adverse reaction> ,
//				<If no adverse reaction recorded add- No adverse reactions were recorded>

					String adverse = "";
					for (NursingBloodproduct item : bloodProductList) {
						if (!BasicUtils.isEmpty(item.getAdverseReactions())) {
							if (adverse.isEmpty()) adverse += item.getAdverseReactions();
							else adverse += ", " + item.getAdverseReactions();
						}
					}

					if (adverse.isEmpty()) notesText += "No adverse reactions were recorded"+"\n";
					else notesText += adverse+"\n";

				}
				List<InvestigationOrdered> listInvSampleDetails = (List<InvestigationOrdered>) inicuDoa
						.getListFromMappedObjQuery(
								HqlSqlQueryConstants.getInvestSampleSentList(uhid, fromDate, toDate));
				if (!BasicUtils.isEmpty(listInvSampleDetails)) {
					String sentStr = "";
					for (InvestigationOrdered obj : listInvSampleDetails) {
						sentStr += ", " + obj.getTestname();
					}
					sentStr = sentStr.substring(2);
					if (sentStr.contains(",")) {
						notesText += "Investigations - " + sentStr + " were executed ";
					} else {
						notesText += "Investigation - " + sentStr + " was executed ";
					}
				}
				// Monitoring end



				//Modification of Care:  Start

				//correction in events

				notesText += "\n\nModification of Care - \n";

//				notesText += apneaText;
//
//				notesText += seizuresText;
//
//				System.out.println("apneaText-------"+apneaText);
//				System.out.println("seizuresText-------"+seizuresText);

				if(apneaCount==1)  notesText += "1 event of apnea was recorded.\n";
				if(apneaCount>1)  notesText += apneaCount+" events of apnea were recorded\n";

				if(seizuresCount==1)  notesText += "1 event of seizures was recorded.\n";
				if(seizuresCount>1)  notesText += seizuresCount+" events of seizures were recorded\n";

				if(desatCount==1)  notesText += "1 event of desaturation was recorded.\n";
				if(desatCount>1)  notesText += desatCount+" events of desaturation were recorded\n";

				if(otherEventsCount==1)  notesText += "1 event was recorded.\n";
				if(otherEventsCount>1)  notesText += otherEventsCount+" events were recorded\n";


				// any change in the orders prescribed are noted, that should be mentioned here.

				//Change in order missing

				if(apneaText.isEmpty() && seizuresText.isEmpty())  notesText += "No specific modification, Same care plan was continued.";

				// Modification of Care:  End



				//Outcome:  Start

/** Baby is on room air/ventilator<ventilator mode name> and tolerating feed well,
 * Abdominal girth is Y cm < The most recent recorded Abdominal girth>

 Current Vitals (HR, RR, BP –Systolic/Diastolic, Spo2 at the time of submitting the note)

 I/O – TFL # ml/kg, (EN # ml/kg, PN # ml/kg + Bolus if any), Urine passed, Stool passed, Vomit (TFL, EN and PN the total volume given to the baby in the particular time period selected should be calculated and displayed. In case of Urine, Stool and Vomit if entry is recorded and the values if entered.)

 */

				notesText += "\n\nOutcome - \n";

				boolean noVomit = true;


				String abdominalGirth = "";

				String ioText = "";
				List<NursingIntakeOutput> listIntakeOutputDetails = (List<NursingIntakeOutput>) inicuDoa
						.getListFromMappedObjQuery(
								HqlSqlQueryConstants.getNursingIntakeOutputList(uhid, fromDateOffset, toDateOffset));
				if (!BasicUtils.isEmpty(listIntakeOutputDetails)) {
					HashMap<String, List<NursingIntakeOutput>> intakeOutputMap = new HashMap<String, List<NursingIntakeOutput>>() {
						private static final long serialVersionUID = 1L;

						{
							put("enteral", new ArrayList<>());
							put("parenteral", new ArrayList<>());
							put("output", new ArrayList<>());
						}
					};

					Collections.reverse(listIntakeOutputDetails);
					for (NursingIntakeOutput item : listIntakeOutputDetails) {
						if ((!BasicUtils.isEmpty(item.getRoute()) && item.getRoute().equalsIgnoreCase("Breast Feed")) || item.getPrimaryFeedValue() != null
								|| item.getFormulaValue() != null || item.getActualFeed() != null) {
							intakeOutputMap.get("enteral").add(item);
						}

						if ((item.getBolus_executed() != null && item.getBolus_executed())
								|| item.getTpn_delivered() != null || item.getLipid_delivered() != null) {
							intakeOutputMap.get("parenteral").add(item);
						}

						if (item.getGastricAspirate() != null
								|| (item.getUrinePassed() != null && item.getUrinePassed())
								|| (item.getStoolPassed() != null && item.getStoolPassed())
								|| (item.getVomitPassed() != null && item.getVomitPassed())) {
							intakeOutputMap.get("output").add(item);
						}
					}

					if (BasicUtils.isEmpty(intakeOutputMap.get("enteral"))) {
						ioText += "The baby is on NPO. ";
					} else {
						float primaryFeed = 0;
						float formulaFeed = 0;
						boolean breastMilk = false;
						String routeStr = "";
						for (NursingIntakeOutput item : intakeOutputMap.get("enteral")) {
							if (!BasicUtils.isEmpty(item.getRoute()) && item.getRoute().equalsIgnoreCase("Breast Feed")) {
								breastMilk = true;
							} else {
								if (!BasicUtils.isEmpty(item.getRoute()) && !routeStr.contains(item.getRoute())) {
									routeStr += ", " + item.getRoute();
								}

								if (!(item.getPrimaryFeedValue() == null || item.getPrimaryFeedValue() == 0)) {
									primaryFeed += item.getPrimaryFeedValue();
								}

								if (!(item.getFormulaValue() == null || item.getFormulaValue() == 0)) {
									formulaFeed += item.getFormulaValue();
								}
							}
						}

						if (breastMilk) {
							ioText += "The baby was on direct Breast Feed. ";
						}

						if (!routeStr.isEmpty() && (primaryFeed != 0 || formulaFeed != 0)) {
							ioText += "The baby was given " + (Math.round((primaryFeed + formulaFeed) * 100) / 100) + " ml of feed through "
									+ routeStr.substring(2);
							if (primaryFeed != 0 && formulaFeed != 0) {
								ioText += ", received " + (Math.round(primaryFeed * 100) / 100) + " ml of fortified human milk and "
										+ formulaFeed + " ml of formula milk. ";
							} else if (primaryFeed != 0) {
								ioText += ", received " + (Math.round(primaryFeed * 100) / 100) + " ml of fortified human milk. ";
							} else if (formulaFeed != 0) {
								ioText += ", received " + (Math.round(primaryFeed * 100) / 100) + " ml of formula milk. ";
							}

						}
					}

					if (!BasicUtils.isEmpty(intakeOutputMap.get("parenteral"))) {
						List<NursingIntakeOutput> pnList = intakeOutputMap.get("parenteral");
						float lipidValue = 0;
						float tpnValue = 0;
						Float lipidRemainingValue = null;
						Float tpnRemainingValue = null;

						for (NursingIntakeOutput item : pnList) {
							if (item.getBolus_executed() != null && item.getBolus_executed()
									&& !BasicUtils.isEmpty(item.getParenteralComment())) {
								ioText += item.getParenteralComment() + " ";
							}

							if (null != item.getLipid_delivered()) {
								lipidValue += item.getLipid_delivered();
							}

							if (null != item.getTpn_delivered()) {
								tpnValue += item.getTpn_delivered();
							}

							if (null != item.getLipid_remaining()) {
								lipidRemainingValue = item.getLipid_remaining();
							}

							if (null != item.getTpn_remaining()) {
								tpnRemainingValue = item.getTpn_remaining();
							}
						}

						if (lipidValue > 0) {
							ioText += "The baby had received " + (Math.round(lipidValue * 100) / 100)
									+ " ml of Lipid IV and at the end of the shift, " + (Math.round(lipidRemainingValue * 100) / 100)
									+ " ml of lipid is remaining in the syringe. ";
						}

						if (tpnValue > 0) {
							ioText += "The baby had received " + (Math.round(tpnValue * 100) / 100)
									+ " ml of TPN solution and at the end of the shift, " + (Math.round(tpnRemainingValue * 100) / 100)
									+ " ml of TPN solution is remaining. ";
						}
					}

					if (!BasicUtils.isEmpty(intakeOutputMap.get("output"))) {
						float urineTotal = 0;
						int stoolCount = 0;
						String gaStr = "";
						String stoolStr = "";
						String vomitStr = "";
						String smallTimestampStr = "";
						String mediumTimestampStr = "";
						String largeTimestampStr = "";
						String bloodTimestampStr = "";
						int smallStoolCount = 0;
						int mediumStoolCount = 0;
						int largeStoolCount = 0;
						int bloodStoolCount = 0;

						List<NursingIntakeOutput> outputList = intakeOutputMap.get("output");
						for (NursingIntakeOutput item : outputList) {
							if (item.getUrinePassed() != null && item.getUrinePassed()) {
								try {
									urineTotal += parseFloat(item.getUrine());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							if (abdominalGirth.isEmpty() &&  item.getAbdomenGirth() != null ) {
								abdominalGirth = item.getAbdomenGirth();

							}

							if (item.getStoolPassed() != null && item.getStoolPassed()) {
								stoolCount++;
								if (!(item.getStool() == null || item.getStool().isEmpty()
										|| stoolStr.contains(item.getStool()))) {
									stoolStr += ", " + item.getStool();
								}
							}

							if (item.getStoolPassed() != null && item.getStoolPassed()) {
								if (!BasicUtils.isEmpty(item.getStool())) {
									if (item.getStool().equals("Small")) {
										smallStoolCount++;
										if (smallTimestampStr.isEmpty()) {
											smallTimestampStr = sdf.format(new Timestamp(item.getEntry_timestamp().getTime() + offset));
										} else {
											smallTimestampStr += ", " + sdf.format(new Timestamp(item.getEntry_timestamp().getTime() + offset));
										}
									}
									if (item.getStool().equals("Medium")) {
										mediumStoolCount++;
										if (mediumTimestampStr.isEmpty()) {
											mediumTimestampStr = sdf.format(new Timestamp(item.getEntry_timestamp().getTime() + offset));
										} else {
											mediumTimestampStr += ", " + sdf.format(new Timestamp(item.getEntry_timestamp().getTime() + offset));
										}

									}
									if (item.getStool().equals("Large")) {
										largeStoolCount++;
										if (largeTimestampStr.isEmpty()) {
											largeTimestampStr = sdf.format(new Timestamp(item.getEntry_timestamp().getTime() + offset));
										} else {
											largeTimestampStr += ", " + sdf.format(new Timestamp(item.getEntry_timestamp().getTime() + offset));
										}
									}
									if (item.getStool().equals("Blood")) {
										bloodStoolCount++;
										if (bloodTimestampStr.isEmpty()) {
											bloodTimestampStr = sdf.format(new Timestamp(item.getEntry_timestamp().getTime() + offset));
										} else {
											bloodTimestampStr += ", " + sdf.format(new Timestamp(item.getEntry_timestamp().getTime() + offset));
										}
									}
								}
							}


							if (!(item.getGastricAspirate() == null || item.getGastricAspirate().isEmpty())) {
								try {
									int gaValue = Integer.parseInt(item.getGastricAspirate());
									if (gaStr.isEmpty()) {
										gaStr = "Baby had gastric aspirate of ";
									} else {
										gaStr += ", ";
									}

									gaStr += gaValue + " ml at "
											+ sdf.format(new Timestamp(item.getEntry_timestamp().getTime() + offset));

								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							if (item.getVomitPassed() != null && item.getVomitPassed()) {

								noVomit = false;
								if (vomitStr.isEmpty()) {
									vomitStr = "The baby had an episode of ";
								} else {
									vomitStr += ", episode of ";
								}

								vomitStr += (BasicUtils.isEmpty(item.getVomitColor()) ? ""
										: (item.getVomitColor().equalsIgnoreCase("other") ? item.getVomitColorOther()
										: item.getVomitColor()) + " ")
										+ "vomiting "
										+ (BasicUtils.isEmpty(item.getVomit()) ? ""
										: "of " + item.getVomit() + " size ")
										+ "at " + sdf.format(new Timestamp(item.getEntry_timestamp().getTime() + offset));
							}
						}

						if (urineTotal > 0) {
							if (todayWeight == null) {
								String sql = "SELECT currentdateweight FROM baby_visit where uhid='" + uhid
										+ "' and currentdateweight is not null and visitdate <= '"
										+ sdfDB.format(toDate) + "' order by visitdate desc, visittime desc limit 1";
								List<Object> babyWeight = (List<Object>) inicuDoa.getListFromNativeQuery(sql);
								if (!BasicUtils.isEmpty(babyWeight)) {
									todayWeight = (Float) babyWeight.get(0);
								}

							}

							if (!BasicUtils.isEmpty(todayWeight)) {
								float weight = todayWeight / 1000;
								float hrs = (toDate.getTime() - fromDate.getTime()) / (60 * 60 * 1000);
								ioText += "Urine output as recorded was "
										+ String.format("%.02f", (urineTotal / (weight * hrs))) + " ml/kg/hr. ";
							}
						}

						String stoolDurationStr = "";
						if (smallStoolCount != 0) {
							stoolDurationStr = smallStoolCount + " Small at " + smallTimestampStr;
						}
						if (mediumStoolCount != 0) {
							if (!stoolDurationStr.isEmpty()) {
								stoolDurationStr += ", ";
							}
							stoolDurationStr += mediumStoolCount + " Medium at " + mediumTimestampStr;
						}
						if (largeStoolCount != 0) {
							if (!stoolDurationStr.isEmpty()) {
								stoolDurationStr += ", ";
							}
							stoolDurationStr += largeStoolCount + " Large at " + largeTimestampStr;
						}
						if (bloodStoolCount != 0) {
							if (!stoolDurationStr.isEmpty()) {
								stoolDurationStr += ", ";
							}
							stoolDurationStr += bloodStoolCount + " Blood at " + bloodTimestampStr;
						}

						if (stoolCount == 1) {
							ioText += "Passed one stool";
							if (stoolStr.isEmpty()) {
								ioText += ". ";
							} else {
								ioText += " of " + stoolStr.substring(2) + " size at " + smallTimestampStr + mediumTimestampStr +
										largeTimestampStr + bloodTimestampStr + ". ";
							}
						} else if (stoolCount > 1) {
							ioText += "Passed " + stoolCount + " stools";
							if (stoolStr.isEmpty()) {
								ioText += ". ";
							} else {
								ioText += " (" + stoolDurationStr + " ). ";
							}
						} else {
							ioText += "Baby didn't passed stool. ";
						}

						if (!vomitStr.isEmpty()) {
							ioText += vomitStr + ". ";
						}

						if (!gaStr.isEmpty()) {
							ioText += gaStr + ". ";
						}
					}
				}


				notesText +=currentRespSupport;

				if(noVomit) notesText +=" and tolerating feed well";

				notesText +=".\n";




				if (!abdominalGirth.isEmpty())	notesText += "Abdominal girth is "+abdominalGirth+" cm.\n";


				if(!currentVitals.isEmpty())notesText += "Current Vitals: "+currentVitals;

 				//Input IO correction
				if(todayWeight!=null) {

					String ioTextOutcome = getOutcomeNotesInputOutputPast(uhid, fromDate, toDate, fromDateOffset,toDateOffset,todayWeight/1000);

					//notesText += "\nIntake: "+ioText;

					if (!BasicUtils.isEmpty(ioTextOutcome))  notesText += "\nIntake : " + ioTextOutcome;
				}

				// Outcome:  End

//planning start
//

				notesText += "\n\nPlanning and follow up - \n";

//			A,B,C Medication execution is pending (All the pending medication not executed from the current shift)

				//Medication pending start


				// Medications
				String queryPrescription = "SELECT obj FROM BabyPrescription obj where uhid in ('" + uhid
						+ "') and (isactive ='true') and startdate <= '"+fromDateOffset  +"' order by startdate desc";
				List<BabyPrescription> activePrescription = inicuDoa.getListFromMappedObjQuery(queryPrescription);

				if (!BasicUtils.isEmpty(activePrescription)) {
					for (BabyPrescription med : activePrescription) {

						// Local variables
						String finalMedString = "";
						String finalPreparationMedString = "";

						List<String> preparationMedicationStringList = new ArrayList<>();

//						String tempUhid = med.getUhid();
//						NurseTasks currentObject = NurseTasksMap.get(tempUhid);
//						finalMedString = currentObject.getMedications();
//						finalPreparationMedString = currentObject.getPreparationMedication();

						String medString = "";
						medString = med.getMedicinename();
						Long presId = med.getBabyPresid();


					if (!BasicUtils.isEmpty(med.getFrequency())) {

							List<RefMedfrequency> freqResult = inicuPostgersUtil.executePsqlDirectQuery(HqlSqlQueryConstants.getGievnMedicationbyID(presId,fromDateOffset,toDateOffset));
							if (!BasicUtils.isEmpty(freqResult)) {
								continue;
							}
						}
						if (finalMedString == "" || finalMedString == null) {
							finalMedString = medString;
						} else {
							finalMedString += ", " + medString;
						}

						if (!BasicUtils.isEmpty(med.getInstruction())) {
							preparationMedicationStringList.add(med.getInstruction());
						}

						if (!BasicUtils.isEmpty(preparationMedicationStringList)) {
							finalPreparationMedString = preparationMedicationStringList.toString();
							finalPreparationMedString = finalPreparationMedString.replace("[", "");
							finalPreparationMedString = finalPreparationMedString.replace("]", "") + ". ";
						}


						notesText += finalMedString + " execution is pending.\n";
						// finalPreparationMedString

					}
				}

				// medication pending end


//			X, Y,Z Investigation are to be sent (All the pending Investigation not executed from the current shift/time period selected)


				List<InvestigationOrdered> listInvPendingDetails = (List<InvestigationOrdered>) inicuDoa
						.getListFromMappedObjQuery(
								HqlSqlQueryConstants.getInvestigationList(uhid) + " and issamplesent is null");
				if (!BasicUtils.isEmpty(listInvPendingDetails)) {
					String pendingStr = "";
					for (InvestigationOrdered obj : listInvPendingDetails) {
						pendingStr += ", " + obj.getTestname();
					}
					pendingStr = pendingStr.substring(2);
					if (pendingStr.contains(",")) {
						notesText +=  "Pending Investigations - "+pendingStr+".\n";
					} else {
						notesText +=  "Pending Investigation - "+pendingStr+".\n";
					}
				}

//
//					<Blood product name> verification and execution is pending. (Only if Blood Product verification and execution is not done)
//			AND
//					<Blood product name> execution is pending (If only execution is pending then this sentence should appear)


				String bloodProductPending = "";

				bloodProductList = inicuDoa.getListFromMappedObjQuery(
						HqlSqlQueryConstants.getNursingBloodProductPendingList(uhid));
				System.out.println("bloodProductList issue---------------------"+bloodProductList);

				if (!BasicUtils.isEmpty(bloodProductList)) {
					for (NursingBloodproduct item : bloodProductList) {


							//get Blood Product NAme from doctor order
							List<DoctorBloodProducts> doctorList = inicuDoa.getListFromMappedObjQuery("select obj from DoctorBloodProducts obj where doctor_blood_products_id= '" + item.getDoctor_blood_products_id() + "'");
							if(!BasicUtils.isEmpty(doctorList)) {
								if (BasicUtils.isEmpty(bloodProductPending))
									bloodProductPending = doctorList.get(0).getBlood_product();
								else
									bloodProductPending += ", " + doctorList.get(0).getBlood_product();
							}


 					}

				}

//
//				String bloodProductPending = "";
//				List<DoctorBloodProducts> bloodProductPendinglList = (List<DoctorBloodProducts>) inicuDoa.
//						getListFromMappedObjQuery(HqlSqlQueryConstants.getCurrentBloodProductList(uhid, fromDateOffset, toDateOffset)
//								+ " and status='Active' order by assessment_time desc");
//				if (!BasicUtils.isEmpty(bloodProductPendinglList)) {
//					for (int i = 0; i < bloodProductDetailList.size(); i++) {
//						if (!BasicUtils.isEmpty(bloodProductDetailList.get(i).getBlood_product())) {
//							if (BasicUtils.isEmpty(bloodProductPending))
//								bloodProductPending = bloodProductDetailList.get(i).getBlood_product();
//							else
//								bloodProductPending += ", " + bloodProductDetailList.get(i).getBlood_product();
//						}
//					}
//				}

				if(!bloodProductPending.isEmpty()) notesText += bloodProductPending + " execution is pending.\n";


//
//			Handover- <Items from To Do list to be added, only if present >


				List<NurseTasks> nurseTasksList = null;

				String handOver = "";

				try {
					nurseTasksList = userPanel.getNurseTasks(null, uhid);

					if (!BasicUtils.isEmpty(nurseTasksList)) {

						NurseTasks nurseObj = nurseTasksList.get(0);

						if (nurseObj.getInvestigations() != null) {
							handOver += nurseObj.getInvestigations();
// handover todo is duplicate
							//	notesText += handOver;
						}

						//						nurseObj.setMedications(null);
//						nurseObj.setPreparationMedication(null);
//						nurseObj.setIntake(null);
//						nurseObj.setPreparationNutrition(null);
//


					}
				} catch (Exception e) {
					e.printStackTrace();
				}


				// planning end

//					// Exchange Transfusion checking
//					String exchangeTransfusionQuery = HqlSqlQueryConstants.getExchangeTransfusionList(uhid, fromDate, toDate);
//					List<SaJaundice> exchangeTransfusionList = inicuDoa.getListFromMappedObjQuery(exchangeTransfusionQuery);
//					if (!BasicUtils.isEmpty(exchangeTransfusionList)) {
//						SaJaundice exchangeTransfusionObj = exchangeTransfusionList.get(0);
//						if (exchangeTransfusionObj.getExchangetrans() == true) {
//							notesText += "Exchange Transfusion was planned on "
//									+ getDateFromTimestamp(exchangeTransfusionObj.getAssessmentTime())
//									+ ". ";
//						} else {
//							long diffTime = (toDate.getTime() - exchangeTransfusionObj.getAssessmentTime().getTime());
//							if (diffTime <= 86400000) {
//								notesText += " Exchange Transfusion was done on "
//										+ getDateFromTimestamp(exchangeTransfusionObj.getAssessmentTime())
//										+ ". ";
//							}
//						}
//					}
//
//
//					List<NursingBloodGas> listBloodGasDetails = (List<NursingBloodGas>) inicuDoa
//							.getListFromMappedObjQuery(HqlSqlQueryConstants.getNursingBGList(uhid, fromDate, toDate));
//					if (!BasicUtils.isEmpty(listBloodGasDetails)) {
//						int size = listBloodGasDetails.size();
//						if (size == 1) {
//							notesText += "One blood gas was done and the doctors were informed. ";
//						} else {
//							notesText += size + " blood gases were done and the doctors were informed. ";
//						}
//					}
//
//
//					List<InvestigationOrdered> listInvReportDetails = (List<InvestigationOrdered>) inicuDoa
//							.getListFromMappedObjQuery(HqlSqlQueryConstants.getInvestReportList(uhid, fromDate, toDate));
//					if (!BasicUtils.isEmpty(listInvReportDetails)) {
//						String reportStr = "";
//						for (InvestigationOrdered obj : listInvReportDetails) {
//							reportStr += ", " + obj.getTestname();
//						}
//						reportStr = reportStr.substring(2);
//						if (reportStr.contains(",")) {
//							notesText += "Reports of " + reportStr + " are available, doctors are informed. ";
//						} else {
//							notesText += "Report of " + reportStr + " is available, doctors are informed. ";
//						}
//					}
//
//
//					List<Object> procedureDetail = (List<Object>) inicuDoa
//							.getListFromNativeQuery(HqlSqlQueryConstants.getCurrentProcedureCount(uhid, fromDateOffset, toDateOffset));
//					boolean EtFlag = true;
//					if (BasicUtils.isEmpty(procedureDetail)) {
//						notesText += "No Procedures were done during the shift. ";
//
//					} else {
//						String procedureStr = "";
//						Integer count = 0;
//						Iterator<Object> itr = procedureDetail.iterator();
//						while (itr.hasNext()) {
//							Object[] procedureObj = (Object[]) itr.next();
//							if (procedureObj[0].equals("ET Intubation")) {
//								continue;
//							}
//							if (procedureObj[0].equals("ET Suction")) {
//								EtFlag = false;
//								count += ((BigInteger) procedureObj[1]).intValue();
//								procedureStr += " " + procedureObj[1] + " " + "Oronasal/ET Suction" + ",";
//							} else {
//								EtFlag = false;
//								count += ((BigInteger) procedureObj[1]).intValue();
//								procedureStr += " " + procedureObj[1] + " " + procedureObj[0] + ",";
//							}
//						}
//						if (!EtFlag) {
//							procedureStr = procedureStr.substring(0, procedureStr.length() - 1);
//						}
//						if (count == 1) {
//							notesText += procedureStr + " was done. ";
//						} else {
//							notesText += procedureStr + " were done. ";
//						}

//						List<Object> EtEntubationDetail = (List<Object>) inicuDoa
//								.getListFromNativeQuery(HqlSqlQueryConstants.getCurrentEtIntubationCount(uhid, fromDateOffset, toDateOffset));
//						if (!BasicUtils.isEmpty(EtEntubationDetail)) {
//							Iterator<Object> it = EtEntubationDetail.iterator();
//							while (it.hasNext()) {
//								String IntubationObj = it.next().toString();
//								notesText += " " + IntubationObj;
//							}
//						}


//					}

				//currentNotes.setNursing_notes(notesText + "Overall Patient remained stable, mother counseled.");
				currentNotes.setNursing_notes(notesText );


			} catch (Exception e) {
				e.printStackTrace();
				String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
				databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUser,
						uhid, "getNursingNotes", BasicUtils.convertErrorStacktoString(e));
			}
		}
		return returnObj;
	}


	@Override
	public NursingNotesPojo setNursingNotes(NursingNotesPojo nursingNotesObj) {
		NursingNote currentNotes = nursingNotesObj.getCurrentNotes();
		try {
			/*Edited nursing notes will get updated in the table*/
			currentNotes.setFlag(true);
			System.out.println("Nursing Notes:: Note Before saving"+currentNotes.toString());
			currentNotes = (NursingNote) inicuDoa.saveObject(currentNotes);
			System.out.println("Nursing Notes:: Note After saving"+currentNotes.toString());

			nursingNotesObj = getNursingNotes(currentNotes.getUhid(), null, null);
			nursingNotesObj.setCurrentNotes(currentNotes);
			saveNursingNotesHistory(nursingNotesObj);

		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUser,
					uhid, "setNursingNotes", BasicUtils.convertErrorStacktoString(e));
		}
		return nursingNotesObj;
	}

	public NursingNotesHistory saveNursingNotesHistory(NursingNotesPojo nursingNotesObj) {

		/*Function written so as to track edit history of nursing notes data*/
		NursingNotesHistory note = new NursingNotesHistory();

		try {

			if(nursingNotesObj!=null && nursingNotesObj.getCurrentNotes()!=null) {
				note.setNursing_notes_id(nursingNotesObj.getCurrentNotes().getNursing_notes_id());
				note.setLoggeduser(nursingNotesObj.getCurrentNotes().getLoggeduser());
				note.setCreationtime(nursingNotesObj.getCurrentNotes().getModificationtime());
				note.setNursing_notes(nursingNotesObj.getCurrentNotes().getNursing_notes());
				note.setFrom_time(nursingNotesObj.getCurrentNotes().getFrom_time());
				note.setTo_time(nursingNotesObj.getCurrentNotes().getTo_time());
				note.setUhid(nursingNotesObj.getCurrentNotes().getUhid());

				note = (NursingNotesHistory)inicuDoa.saveObject(note);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}


		return note;
	}

	@Override
	@SuppressWarnings("unchecked")
	public NursingVentilatorPrintJSON getNursingVentilatorPrint(String uhid, String fromTime, String toTime) {
		NursingVentilatorPrintJSON returnObj = new NursingVentilatorPrintJSON();
		try {
			returnObj.setFromTime(Long.parseLong(fromTime));
			returnObj.setToTime(Long.parseLong(toTime));

			int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
					- TimeZone.getDefault().getRawOffset();

			Timestamp fromDate = null;
			Timestamp toDate = null;
			if (offset == 0) {
				fromDate = new Timestamp(Long.parseLong(fromTime));
				toDate = new Timestamp(Long.parseLong(toTime));
			} else {
				fromDate = new Timestamp(Long.parseLong(fromTime) + 19800000);
				toDate = new Timestamp(Long.parseLong(toTime) + 19800000);
			}

			toDate.setMinutes(toDate.getMinutes()+3);
			fromDate.setMinutes(fromDate.getMinutes()+2);


			String ventilatorSql = HqlSqlQueryConstants.getNursingVentilatorPrintList(uhid, fromDate, toDate);
			List<NursingVentilaor> ventilatorList = inicuDoa.getListFromMappedObjQuery(ventilatorSql);
            for (NursingVentilaor obj : ventilatorList) {
                System.out.println(obj.getEntryDate() + "offset Episode");
                obj.setEntryDate(new Timestamp(obj.getEntryDate().getTime() - offset));
                //obj.setVent_desc(obj.getVent_desc());
            }
			returnObj.setVentilatorList(ventilatorList);

			String babySql = "select dateofadmission from baby_detail where uhid='" + uhid + "' order by creationtime";
			List<Date> babyDetail = inicuDoa.getListFromNativeQuery(babySql);
			if (!BasicUtils.isEmpty(babyDetail)) {
				returnObj.setDateofAdmission(babyDetail.get(0).getTime());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnObj;
	}

	@Override
	@SuppressWarnings("unchecked")
	public NursingBGPrintJSON getNursingBGPrint(String uhid, String fromTime, String toTime) {
		NursingBGPrintJSON returnObj = new NursingBGPrintJSON();
		try {
			returnObj.setFromTime(Long.parseLong(fromTime));
			returnObj.setToTime(Long.parseLong(toTime));

			Timestamp fromDate = null;
			Timestamp toDate = null;

			int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
					- TimeZone.getDefault().getRawOffset();

			if (offset == 0) {
				fromDate = new Timestamp(Long.parseLong(fromTime));
				toDate = new Timestamp(Long.parseLong(toTime));
			} else {
				fromDate = new Timestamp(Long.parseLong(fromTime) + 19800000);
				toDate = new Timestamp(Long.parseLong(toTime) + 19800000);
			}

			toDate.setMinutes(toDate.getMinutes()+3);
			fromDate.setMinutes(fromDate.getMinutes()+2);

			String bloodGasSql = HqlSqlQueryConstants.getNursingBGList(uhid, fromDate, toDate);
			List<NursingBloodGas> bloodGasList = inicuDoa.getListFromMappedObjQuery(bloodGasSql);

			for (NursingBloodGas obj: bloodGasList) {
				obj.setEntryDate(new Timestamp(obj.getEntryDate().getTime() - offset));
			}
			returnObj.setBloodGasList(bloodGasList);

			String babySql = "select dateofadmission from baby_detail where uhid='" + uhid + "' order by creationtime";
			List<Date> babyDetail = inicuDoa.getListFromNativeQuery(babySql);
			if (!BasicUtils.isEmpty(babyDetail)) {
				returnObj.setDateofAdmission(babyDetail.get(0).getTime());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnObj;
	}

	@Override
	@SuppressWarnings("unchecked")
	public NursingIntakeOutputPrintJSON getNursingIntakeOutputPrint(String uhid, String fromTime, String toTime) {
		NursingIntakeOutputPrintJSON returnObj = new NursingIntakeOutputPrintJSON();
		try {
			returnObj.setFromTime(Long.parseLong(fromTime));
			returnObj.setToTime(Long.parseLong(toTime));

//			int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
//					- TimeZone.getDefault().getRawOffset();

			Timestamp fromDate = new Timestamp(60000+Long.parseLong(fromTime));
			Timestamp toDate = new Timestamp(59000+Long.parseLong(toTime));

			String intakeOutputSql = HqlSqlQueryConstants.getNursingIntakeOutputList(uhid, fromDate, toDate);
			List<NursingIntakeOutput> intakeOutputList = inicuDoa.getListFromMappedObjQuery(intakeOutputSql);
			returnObj.setIntakeOutputList(intakeOutputList);

			String babySql = "select dateofadmission from baby_detail where uhid='" + uhid + "' order by creationtime";
			List<Date> babyDetail = inicuDoa.getListFromNativeQuery(babySql);
			if (!BasicUtils.isEmpty(babyDetail)) {
				returnObj.setDateofAdmission(babyDetail.get(0).getTime());
			}



			Float todayWeight = null;
			List<BabyVisit> babyVisitList = inicuDoa
					.getListFromMappedObjQuery(HqlSqlQueryConstants.getBabyVisitList(uhid,null, toDate.toString()));
			if (!BasicUtils.isEmpty(babyVisitList)) {
				Iterator<BabyVisit> itr = babyVisitList.iterator();

				while (itr.hasNext()) {
					BabyVisit obj = itr.next();
					if (!BasicUtils.isEmpty(obj.getWorkingweight())) {
 						todayWeight = obj.getWorkingweight();
 						break;
					}
				}

			}

			if(todayWeight!= null) returnObj.setNursingWeight(todayWeight);


		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnObj;
	}

	public String calculateDate24HoursAgo(java.util.Date currentDate) {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// convert date to calendar
		Calendar c = Calendar.getInstance();
		c.setTime(currentDate);

		c.add(Calendar.DATE, -1);
		java.util.Date currentDatePlusTwo = c.getTime();
		String calculatedDate = dateFormat.format(currentDatePlusTwo);
		return calculatedDate;
	}

	public List<BabyPrescription> getPastPrescriptionList(String uhid, String calDate, String currDate) {

		List<BabyPrescription> pastPrescriptionList = new ArrayList<BabyPrescription>();
		String queryPastPrescriptionList = "select obj from BabyPrescription as obj where uhid='"
				+ uhid + "' and startdate >= '" + calDate + "' and startdate <= '" + currDate
				+ "' order by startdate desc";
		List<BabyPrescription> pastPrescriptionList1 = inicuDoa.getListFromMappedObjQuery(queryPastPrescriptionList);
		if (!BasicUtils.isEmpty(pastPrescriptionList1)) {
			pastPrescriptionList.addAll(pastPrescriptionList1);
		}
		return pastPrescriptionList;
	}

	public List<InvestigationOrdered> getInvestigationOrdersList(String uhid, String calDate, String currDate) {

		List<InvestigationOrdered> investigationOrdersList = new ArrayList<InvestigationOrdered>();
		String queryInvestigationOrder = "Select obj from InvestigationOrdered obj where uhid='" + uhid
				+ "' and investigationorder_time >= '" + calDate + "' and investigationorder_time <= '" + currDate
				+ "' order by creationtime desc";
		List<InvestigationOrdered> investigationOrderResultSet = inicuDoa.getListFromMappedObjQuery(queryInvestigationOrder);
		if (!BasicUtils.isEmpty(investigationOrderResultSet)) {
			investigationOrdersList.addAll(investigationOrderResultSet);
		}
		return investigationOrdersList;
	}

	public List<DoctorBloodProducts> getDoctorBloodProductsList(String uhid, String calDate, String currDate) {
		List<DoctorBloodProducts> bloodProductsList = new ArrayList<DoctorBloodProducts>();
		String queryBloodProducts = "Select obj from DoctorBloodProducts obj where uhid='" + uhid
				+ "' and assessment_time >= '" + calDate + "' and assessment_time <= '" + currDate
				+ "' order by assessment_time desc";
		List<DoctorBloodProducts> bloodProductsResultSet = inicuDoa.getListFromMappedObjQuery(queryBloodProducts);
		if (!BasicUtils.isEmpty(bloodProductsResultSet)) {
			bloodProductsList.addAll(bloodProductsResultSet);
		}
		return bloodProductsList;
	}


	public List<RespSupport> getRespSupportList(String uhid, String calDate, String currDate) {
		List<RespSupport> respSupportList = new ArrayList<RespSupport>();
		String queryRespSupportToFindLastActive = "Select obj from RespSupport obj where uhid='" + uhid
				+ "' and creationtime >= '" + calDate + "' and creationtime <= '" + currDate
				+ "' and isactive='true' order by creationtime desc";

		List<RespSupport> respSupportActiveList = inicuDoa.getListFromMappedObjQuery(queryRespSupportToFindLastActive);
		if (!BasicUtils.isEmpty(respSupportActiveList)) {
			respSupportList.addAll(respSupportActiveList);
		}
		return respSupportList;
	}

	public List<SaJaundice> getPhototherapyList(String uhid, String calDate, String currDate) {
		List<SaJaundice> saJaundiceList = new ArrayList<SaJaundice>();
		String querySaJaundice = "select obj from SaJaundice as obj where phototherapyvalue is not null and phototherapyvalue !='' and uhid = '" + uhid +
			"' and assessment_time >='" + calDate + "' and assessment_time <= '" + currDate + "' order by creationtime desc";

		List<SaJaundice> saJaundiceListResult = inicuDoa.getListFromMappedObjQuery(querySaJaundice);
		if (!BasicUtils.isEmpty(saJaundiceListResult)) {
			saJaundiceList.addAll(saJaundiceListResult);
		}
		return saJaundiceList;
	}
	/**
	*
	*
	*Purpose: Adding all assessments and other procedures to doctor order in nursing panel

	*@Updated on: June 27 2019
	*@author: Shweta Nichani Mohanani
	*/
	@Override
	@SuppressWarnings("unchecked")
	public List<NurseExecutionOrders> getDoctorOrder(String uhid, String fromDateLong) {

		NurseExecutionOrders returnObj = new NurseExecutionOrders();
		// working with 8.30 to 8.30 slot

/*		Long startTime = Long.parseLong(fromDateLong);
		Long endTime = startTime + (24 * 60 * 60 * 1000);

		Timestamp startDateObj = new Timestamp(startTime);
		Timestamp endDateObj = new Timestamp(endTime);*/

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date currentDate = new java.util.Date();

		String currentDateFinal = dateFormat.format(currentDate);
		String calDate = calculateDate24HoursAgo(currentDate);


		String procedureSql = "select obj from NurseExecutionOrders as obj where uhid='" + uhid + "' and assessmentdate <= '" + currentDateFinal
				+ "' and assessmentdate >= '" + calDate + "' order by assessmentdate desc";
		List<NurseExecutionOrders> nurseData = inicuDoa.getListFromMappedObjQuery(procedureSql);
		
		List<NurseExecutionOrders> nurseDataFinal = new ArrayList<NurseExecutionOrders>();
		for(NurseExecutionOrders order : nurseData) {
			if(!BasicUtils.isEmpty(order.getIsExecution()) && order.getIsExecution() == true)
				order.setIsExecutionFinal(true);
			else
				order.setIsExecutionFinal(false);
			nurseDataFinal.add(order);
		}
		

//		returnObj.setPrescriptionList(getPastPrescriptionList(uhid, calDate, currentDateFinal));
//
//		returnObj.setInvestigationOrdersList(getInvestigationOrdersList(uhid, calDate, currentDateFinal));
//
//		returnObj.setBloodProductsList(getDoctorBloodProductsList(uhid, calDate, currentDateFinal));
//
//		returnObj.setRespSupportList(getRespSupportList(uhid, calDate, currentDateFinal));
//
//		returnObj.setPhotoTherapyList(getPhototherapyList(uhid, calDate, currentDateFinal));
//



		return nurseDataFinal;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<NurseExecutionOrders> saveNursingExecution(String uhid, String fromDateLong, List<NurseExecutionOrders> doctorNotesObj) {

		try {
			inicuDoa.saveMultipleObject(doctorNotesObj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<NurseExecutionOrders> nurseData = getDoctorOrder(uhid,fromDateLong);
		return nurseData;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<NursingVitalparameter> getRoundAndAssessmentData(String uhid, String entryDate) {

		Timestamp today = null;
		Timestamp nextHour = null;
		List<NursingVitalparameter> vitalInfo = new ArrayList<NursingVitalparameter>();

		entryDate = entryDate.substring(0, 24);
		DateFormat readFormat = new SimpleDateFormat("EEE MMM dd yyyy hh:mm:ss");
		java.util.Date presentDate;
		
		try {
			presentDate = readFormat.parse(entryDate);
			int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
					- TimeZone.getDefault().getRawOffset();
			presentDate.setHours(0);
			presentDate.setMinutes(0);
			presentDate.setSeconds(0);

			if (offset == 0) {
				
				today = new Timestamp(presentDate.getTime());
				nextHour = new Timestamp(today.getTime() + (24 * 60 * 60 * 1000));
			} else {
				today = new Timestamp(presentDate.getTime() - offset);
				nextHour = new Timestamp(presentDate.getTime() - offset + (24 * 60 * 60 * 1000));
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		String query = "select obj from NursingVitalparameter as obj where uhid='" + uhid
				+ "' and entryDate <= '" + nextHour + "' and entryDate >= '" + today
				+ "' order by entryDate desc";
		List<NursingVitalparameter> listDeviceMonitor = notesDoa.getListFromMappedObjNativeQuery(query);
		
		return listDeviceMonitor;

	}

	@SuppressWarnings("unchecked")
	@Override
	public NursingVitalparameter getVitalByDate(String uhid, String entryDate) {

		Timestamp today = null;
		Timestamp previousHour = null;
		NursingVitalparameter vitalInfo = new NursingVitalparameter();

		entryDate = entryDate.substring(0, 24);
		DateFormat readFormat = new SimpleDateFormat("EEE MMM dd yyyy hh:mm:ss");
		java.util.Date presentDate;
		try {
			presentDate = readFormat.parse(entryDate);
			int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
					- TimeZone.getDefault().getRawOffset();
			if (offset == 0) {
				today = new Timestamp(presentDate.getTime());
				previousHour = new Timestamp(today.getTime() - (60 * 60 * 1000));
			} else {
				today = new Timestamp(presentDate.getTime() - offset);
				previousHour = new Timestamp(presentDate.getTime() - offset - (60 * 60 * 1000));
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		String query = "select obj from DeviceMonitorDetail as obj where uhid='" + uhid + "' and starttime <='" + today
				+ "' and starttime >='" + previousHour + "' order by starttime desc";
		List<DeviceMonitorDetail> listDeviceMonitor = notesDoa.getListFromMappedObjNativeQuery(query);
		if (listDeviceMonitor != null && listDeviceMonitor.size() > 0) {
			DeviceMonitorDetail monitorData = listDeviceMonitor.get(0);

			if (!BasicUtils.isEmpty(monitorData.getHeartrate())) {
				vitalInfo.setHrRate(Float.valueOf(monitorData.getHeartrate()));
			}

			if (!BasicUtils.isEmpty(monitorData.getSpo2())) {
				vitalInfo.setSpo2(monitorData.getSpo2());
			}

			if (!BasicUtils.isEmpty(monitorData.getEtco2())) {
				vitalInfo.setEtco2(monitorData.getEtco2());
			}

			if (!BasicUtils.isEmpty(monitorData.getEcgResprate())) {
				vitalInfo.setRrRate(Float.valueOf(monitorData.getEcgResprate()));
			}
			if (!BasicUtils.isEmpty(monitorData.getStarttime())) {
				vitalInfo.setEntryDate(monitorData.getStarttime());
			}

			if (!BasicUtils.isEmpty(monitorData.getEtco2())) {
				vitalInfo.setEtco2(monitorData.getEtco2());
			}

			if (!BasicUtils.isEmpty(monitorData.getPulserate())) {
				vitalInfo.setPulserate(monitorData.getPulserate());
			}

			// flag to check for is all bp fields are empty..
			Boolean isNormalBpAllEmpty = true;
			if (!BasicUtils.isEmpty(monitorData.getSysBp())) {
				vitalInfo.setSystBp(monitorData.getSysBp());
				isNormalBpAllEmpty = false;
			}

			if (!BasicUtils.isEmpty(monitorData.getDiaBp())) {
				vitalInfo.setDiastBp(monitorData.getDiaBp());
				isNormalBpAllEmpty = false;
			}

			if (!BasicUtils.isEmpty(monitorData.getMeanBp())) {
				vitalInfo.setMeanBp(monitorData.getMeanBp());
				isNormalBpAllEmpty = false;
			}

			if (isNormalBpAllEmpty) {
				if (!BasicUtils.isEmpty(monitorData.getNbp_s())) {
					vitalInfo.setSystBp(monitorData.getNbp_s());
				}

				if (!BasicUtils.isEmpty(monitorData.getNbp_d())) {
					vitalInfo.setDiastBp(monitorData.getNbp_d());
				}

				if (!BasicUtils.isEmpty(monitorData.getNbp_m())) {
					vitalInfo.setMeanBp(monitorData.getNbp_m());
				}
			}
            if (!BasicUtils.isEmpty(monitorData.getIbp_s())) {
                vitalInfo.setSystiBp(Float.parseFloat(monitorData.getIbp_s()));
            }

            if (!BasicUtils.isEmpty(monitorData.getIbp_d())) {
                vitalInfo.setDiastiBp(Float.parseFloat(monitorData.getIbp_d()));
            }

            if (!BasicUtils.isEmpty(monitorData.getIbp_m())) {
                vitalInfo.setMeaniBp(Float.parseFloat(monitorData.getIbp_m()));
            }
		}
		return vitalInfo;

	}

	public String respSupportUpdatedStr(RespSupport respSupportObj) throws InicuDatabaseExeption {
		String respSupportStr = "";
		List<String> apneaIdList = new ArrayList<String>();
		try {
			String rsVentType = respSupportStrOnlyType(respSupportObj);
			respSupportStr = " " + rsVentType;
			respSupportStr += " mode (";
			if(!BasicUtils.isEmpty(respSupportObj.getDco2())) {
				respSupportStr += " DCO2 : " + respSupportObj.getDco2();
			}
			if(!BasicUtils.isEmpty(respSupportObj.getiTime())) {
				respSupportStr += " iTime : " + respSupportObj.getiTime();
			}
			if(!BasicUtils.isEmpty(respSupportObj.getRsAmplitude())) {
				respSupportStr += " Amplitude : " + respSupportObj.getRsAmplitude() + " m";
			}
			if(!BasicUtils.isEmpty(respSupportObj.getRsBackuprate())) {
				respSupportStr += "Backup Rate : " + respSupportObj.getRsBackuprate() + " Breaths/min";
			}
			if(!BasicUtils.isEmpty(respSupportObj.getRsEt())) {
				respSupportStr += " ET : " + respSupportObj.getRsEt() + " secs";
			}
			if(!BasicUtils.isEmpty(respSupportObj.getRsFio2())) {
				respSupportStr += " Fio2 : " + respSupportObj.getRsFio2()+ " %";
			}
			if(!BasicUtils.isEmpty(respSupportObj.getRsPeep())) {
				respSupportStr += " PEEP : " + respSupportObj.getRsPeep() + " cm H20";
			}
			if(!BasicUtils.isEmpty(respSupportObj.getRsPip())) {
				respSupportStr += " PIP : " + respSupportObj.getRsPip() + " cm H20";
			}
			if(!BasicUtils.isEmpty(respSupportObj.getRsFlowRate())) {
				respSupportStr += " Flow Rate : " + respSupportObj.getRsFlowRate() + " Litres/min";
			}
			if(!BasicUtils.isEmpty(respSupportObj.getRsFrequency())) {
				respSupportStr += " Frequency : " + respSupportObj.getRsFrequency() + " Hz";
			}
			if(!BasicUtils.isEmpty(respSupportObj.getRsIt())) {
				respSupportStr += " IT : " + respSupportObj.getRsIt() + " secs";
			}
			if(!BasicUtils.isEmpty(respSupportObj.getRsMap())) {
				respSupportStr += " MAP : " + respSupportObj.getRsMap() + " cmH20";
			}
			if(!BasicUtils.isEmpty(respSupportObj.getRsMv())) {
				respSupportStr += " MV : " + respSupportObj.getRsMv();
			}
			if(!BasicUtils.isEmpty(respSupportObj.getRsTv())) {
				respSupportStr += " TV : " + respSupportObj.getRsTv() + " mL/kg";
			}
			respSupportStr += ")";

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return respSupportStr;
	}

	public String respSupportStr(RespSupport respSupportObj) throws InicuDatabaseExeption {
		String respSupportStr = "";
		List<String> apneaIdList = new ArrayList<String>();
		try {
			String rsVentType = respSupportObj.getRsVentType();
			if(rsVentType!= null) {
				if(rsVentType.equals("CPAP")) {
					rsVentType = "";
					if(respSupportObj.getRsCpapType()!=null){
						if(respSupportObj.getRsCpapType().equalsIgnoreCase("Ventilator CPAP"))  rsVentType = "Ventilator ";
						if(respSupportObj.getRsCpapType().equalsIgnoreCase("Bubble CPAP"))  rsVentType = "Bubble ";
						if(respSupportObj.getRsCpapType().equalsIgnoreCase("Infant Flow Driver"))  rsVentType = "Infant Flow Driver ";

					}
					rsVentType += "cPAP";
				}
			
				respSupportStr = " " + rsVentType;
				respSupportStr = respSupportStr.replace("Mechanical Ventilation", "").replace("  ", " ");
			}
			if(!BasicUtils.isEmpty(respSupportObj.getRsMechVentType()))
				respSupportStr += " " + respSupportObj.getRsMechVentType();
			respSupportStr += " mode (";
			if(!BasicUtils.isEmpty(respSupportObj.getDco2())) {
				respSupportStr += " DCO2 : " + respSupportObj.getDco2();
			}
			if(!BasicUtils.isEmpty(respSupportObj.getiTime())) {
				respSupportStr += " iTime : " + respSupportObj.getiTime();
			}
			if(!BasicUtils.isEmpty(respSupportObj.getRsAmplitude())) {
				respSupportStr += " Amplitude : " + respSupportObj.getRsAmplitude() + " m";
			}
			if(!BasicUtils.isEmpty(respSupportObj.getRsBackuprate())) {
				respSupportStr += "Backup Rate : " + respSupportObj.getRsBackuprate() + " Breaths/min";
			}
			if(!BasicUtils.isEmpty(respSupportObj.getRsEt())) {
				respSupportStr += " ET : " + respSupportObj.getRsEt() + " secs";
			}
			if(!BasicUtils.isEmpty(respSupportObj.getRsFio2())) {
				respSupportStr += " Fio2 : " + respSupportObj.getRsFio2()+ " %";
			}
			if(!BasicUtils.isEmpty(respSupportObj.getRsPeep())) {
				respSupportStr += " PEEP : " + respSupportObj.getRsPeep() + " cm H20";
			}
			if(!BasicUtils.isEmpty(respSupportObj.getRsPip())) {
				respSupportStr += " PIP : " + respSupportObj.getRsPip() + " cm H20";
			}
			if(!BasicUtils.isEmpty(respSupportObj.getRsFlowRate())) {
				respSupportStr += " Flow Rate : " + respSupportObj.getRsFlowRate() + " Litres/min";
			}
			if(!BasicUtils.isEmpty(respSupportObj.getRsFrequency())) {
				respSupportStr += " Frequency : " + respSupportObj.getRsFrequency() + " Hz";
			}
			if(!BasicUtils.isEmpty(respSupportObj.getRsIt())) {
				respSupportStr += " IT : " + respSupportObj.getRsIt() + " secs";
			}
			if(!BasicUtils.isEmpty(respSupportObj.getRsMap())) {
				respSupportStr += " MAP : " + respSupportObj.getRsMap() + " cmH20";
			}
			if(!BasicUtils.isEmpty(respSupportObj.getRsMv())) {
				respSupportStr += " MV : " + respSupportObj.getRsMv();
			}
			if(!BasicUtils.isEmpty(respSupportObj.getRsTv())) {
				respSupportStr += " TV : " + respSupportObj.getRsTv() + " mL/kg";
			}
			respSupportStr += ")";

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return respSupportStr;
	}

	public String respSupportStrOnlyType(RespSupport respSupportObj) throws InicuDatabaseExeption {
		String respSupportStr = "";
		List<String> apneaIdList = new ArrayList<String>();
		try {
			String rsVentType = respSupportObj.getRsVentType();
			if(rsVentType!= null) {
				if(rsVentType.equals("CPAP")) {
					rsVentType = "";
					if(respSupportObj.getRsCpapType()!=null){
						if(respSupportObj.getRsCpapType().equalsIgnoreCase("Ventilator CPAP"))  rsVentType = "Ventilator ";
						if(respSupportObj.getRsCpapType().equalsIgnoreCase("Bubble CPAP"))  rsVentType = "Bubble ";
						if(respSupportObj.getRsCpapType().equalsIgnoreCase("Infant Flow Driver"))  rsVentType = "Infant Flow Driver ";

					}
					rsVentType += "CPAP";
				}
				if(rsVentType.equals("SIPPV")) {
					rsVentType = "SIPPV";
				}
				if(rsVentType.equals("SIMV")) {
					rsVentType = "SIMV ";
				}
				if(rsVentType.equals("NIMV")) {
					rsVentType = "NIMV";
				}
				if(rsVentType.equals("IMV")) {
					rsVentType = "IMV";
				}
				if(rsVentType.equals("PSV")) {
					rsVentType = "PSV";
				}
				if(rsVentType.equals("HFO")) {
					rsVentType = "HFO";
				}
				respSupportStr = " " + rsVentType;
			}
			if(!BasicUtils.isEmpty(respSupportObj.getRsMechVentType()))
				respSupportStr += " " + respSupportObj.getRsMechVentType();
			
			respSupportStr = respSupportStr.replace("Mechanical Ventilation", "").replace("  ", " ");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return respSupportStr;
	}

	@Override
	public GeneralResponseObject getDoctorEditedNotesList(String uhid, String branchname, String fromWhere) {
		GeneralResponseObject returnObject=new GeneralResponseObject();
		try{
			ArrayList<EditedDoctorProgressNotesListPojo> userList=new ArrayList<>();
			String getNoteQuery="SELECT obj from EditedDoctorNotes obj where uhid='"+uhid+"' and branchname='"+branchname
					+ "' and fromwhere='" +fromWhere +"' order by entrydate asc,creationtime asc";
			List<EditedDoctorNotes> editedDoctorNotesList=inicuDoa.getListFromMappedObjQuery(getNoteQuery);
			if(!BasicUtils.isEmpty(editedDoctorNotesList) && editedDoctorNotesList.size()>0){
				for (EditedDoctorNotes editObject: editedDoctorNotesList) {
					JSONObject myObject=new JSONObject();
					EditedDoctorProgressNotesListPojo tempObject=new EditedDoctorProgressNotesListPojo();
					tempObject.setEntryDate(editObject.getEntrydate());
					tempObject.setCreationtime(editObject.getCreationtime());
					tempObject.setLoggedUser(editObject.getLoggedUser());
					userList.add(tempObject);
				}
				returnObject=BasicUtils.getResonseObject(true,200,"success",userList);
			}else{
				returnObject=BasicUtils.getResonseObject(true,302,"No entry found",userList);
			}
		}catch (Exception e){
			returnObject=BasicUtils.getResonseObject(false,500,"Internal Server Error",null);
			e.printStackTrace();
		}
		return returnObject;
	}

	@Override
	public GeneralResponseObject getEditedDoctorNotes(String uhid, Timestamp dateString, String branchName, String loggedUser, String fromwhere) {
		GeneralResponseObject returnObject=new GeneralResponseObject();
		try{

			String getNoteQuery="SELECT * FROM edited_doctor_notes WHERE uhid='"+uhid+"' AND '"+dateString+"'>= date_trunc('milliseconds', creationtime) - INTERVAL '1 milliseconds' AND '"+
					dateString+"' < date_trunc('milliseconds', creationtime) + INTERVAL '1 milliseconds' "+
					"AND branchname='"+branchName+"' AND logged_user='"+loggedUser + "' AND fromwhere='" + fromwhere +"' order by entrydate desc";

			List<Object[]> editedDoctorNotesList=inicuDoa.getListFromNativeQuery(getNoteQuery);
			if(!BasicUtils.isEmpty(editedDoctorNotesList) && editedDoctorNotesList.size()>0){
				EditedDoctorNotes editedDoctorNotes=new EditedDoctorNotes(editedDoctorNotesList.get(0));
				returnObject=BasicUtils.getResonseObject(true,200,"success",editedDoctorNotes);
			}else{
				returnObject=BasicUtils.getResonseObject(true,302,"No entry found",null);
			}

		}catch (Exception e){
			returnObject=BasicUtils.getResonseObject(false,500,"Internal Server Error",null);
			e.printStackTrace();
		}
		return returnObject;
	}

	@Override
	public GeneralResponseObject saveEditedDoctorNotes(EditedDoctorNotes editedNotes) {
		GeneralResponseObject returnObject=new GeneralResponseObject();
		try {
			EditedDoctorNotes editedDoctorNoteObject=(EditedDoctorNotes)inicuDoa.saveObject(editedNotes);
			if(!BasicUtils.isEmpty(editedDoctorNoteObject)){
				returnObject=BasicUtils.getResonseObject(true,200,"success",editedDoctorNoteObject);
			}else{
				returnObject=BasicUtils.getResonseObject(true,302,"No entry found",null);
			}
		}catch (Exception e){
			returnObject=BasicUtils.getResonseObject(false,500,"Internal Server Error",null);
			e.printStackTrace();
		}
		return returnObject;
	}

	public long	getAssessmentHours( Timestamp anaStarttime, Timestamp anaEndTime,List<Object[]> activeAssessmentList  ){

		Timestamp  calStarttime = anaStarttime;

		Timestamp calEndtime = anaEndTime;

		Timestamp asessmentTime = null;

		long aHours = 0;

		if (!BasicUtils.isEmpty(activeAssessmentList)) {
			Iterator<Object[]> itr = activeAssessmentList.iterator();
			while (itr.hasNext()) {

				Object[] obj = itr.next();

				try {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
					java.util.Date parsedDate = dateFormat.parse(obj[1].toString());
					asessmentTime = new java.sql.Timestamp(parsedDate.getTime());


				} catch(Exception e) { //this generic but you can control another types of exception
					// look the origin of excption
				}

				if("yes".equalsIgnoreCase((String)(obj[0]))){

					if(asessmentTime.getTime() < anaStarttime.getTime())
						calStarttime=anaStarttime;
					else 
						calStarttime=asessmentTime;

				} else if("no".equalsIgnoreCase((String)(obj[0]))){
					calEndtime = asessmentTime;

				}else if("inactive".equalsIgnoreCase((String)(obj[0]))){
					if(asessmentTime.getTime() <= anaStarttime.getTime()) 
						break;
				}
				if(calEndtime.getTime() <= anaStarttime.getTime()) break;
				aHours += ((calEndtime.getTime() - calStarttime.getTime()) / (1000 * 60 * 60));
				calEndtime = calStarttime;
			}
		}
		return aHours;
	}

	public  String getActiveAssessmentInaPeriod(Timestamp anaStarttime, Timestamp anaEndTime,String uhid ){

		long totalAssessmentCount = 0;
		long aHours = 0;

		java.lang.StringBuilder diagnonis = new java.lang.StringBuilder();
		
		try {
			StringBuilder query = new StringBuilder("SELECT  eventstatus , assessment_time FROM sa_resp_rds where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and assessment_time <='").append(anaEndTime).append("'")
					.append(" order by  assessment_time desc");

			List<Object[]> activeAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
			aHours = getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
 			
			if(aHours>0){
				if(diagnonis.length()>0) 
					diagnonis.append("/");
				diagnonis.append("Respiratory Distress");
			}

			aHours = 0;

			query = new StringBuilder("SELECT  eventstatus , assessment_time FROM sa_resp_apnea where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and assessment_time <='").append(anaEndTime).append("'")
					.append(" order by  assessment_time desc");

			activeAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
			aHours = getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
 			
			if(aHours>0){
				if(diagnonis.length()>0) diagnonis.append("/");
				diagnonis.append("Apnea");
			}

			aHours = 0;

			query = new StringBuilder("SELECT  eventstatus , assessment_time FROM sa_resp_pphn where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and assessment_time <='").append(anaEndTime).append("'")
					.append(" order by  assessment_time desc");


			activeAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
			aHours = getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
			

			if(aHours>0){
				if(diagnonis.length()>0) diagnonis.append("/");
				diagnonis.append("PPHN");
			}

			aHours = 0;
			
			query = new StringBuilder("SELECT  eventstatus , assessment_time FROM sa_resp_pneumothorax where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and assessment_time <='").append(anaEndTime).append("'")
					.append(" order by  assessment_time desc");

			activeAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
			aHours = getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );

			if(aHours>0){
				if(diagnonis.length()>0) diagnonis.append("/");
				diagnonis.append("Pneumothorax");
			}

			aHours = 0;
			
			query = new StringBuilder("SELECT  eventstatus , assessment_time FROM sa_infection_sepsis where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and assessment_time <='").append(anaEndTime).append("'")
					.append(" order by  assessment_time desc");


			activeAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
			aHours = getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
			

			if(aHours>0){
				if(diagnonis.length()>0) diagnonis.append("/");
				diagnonis.append("Sepsis");
			}

			aHours = 0;

			query = new StringBuilder("SELECT  eventstatus , assessment_time FROM sa_infection_vap where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and assessment_time <='").append(anaEndTime).append("'")
					.append(" order by  assessment_time desc");


			activeAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
			aHours = getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
			
			
			
			if(aHours>0){
				if(diagnonis.length()>0) diagnonis.append("/");
				diagnonis.append("VAP");
			}

			aHours = 0;

			query = new StringBuilder("SELECT  eventstatus , assessment_time FROM sa_infection_nec where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and assessment_time <='").append(anaEndTime).append("'")
					.append(" order by  assessment_time desc");


			activeAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
			aHours = getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
			
			if(aHours>0){
				if(diagnonis.length()>0) diagnonis.append("/");
				diagnonis.append("NEC");
			}

			aHours = 0;

			query = new StringBuilder("SELECT  eventstatus , assessment_time FROM sa_infection_clabsi where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and assessment_time <='").append(anaEndTime).append("'")
					.append(" order by  assessment_time desc");


			activeAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
			aHours = getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
			
			if(aHours>0){
				if(diagnonis.length()>0) diagnonis.append("/");
				diagnonis.append("CLABSI");
			}

			aHours = 0;

			query = new StringBuilder("SELECT  eventstatus , timeofassessment FROM sa_infection_intrauterine where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and timeofassessment <='").append(anaEndTime).append("'")
					.append(" order by  timeofassessment desc");


			activeAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
			aHours = getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
			
			if(aHours>0){
				if(diagnonis.length()>0) diagnonis.append("/");
				diagnonis.append("Intrauterine Infection");
			}

			aHours = 0;

			query = new StringBuilder("SELECT  eventstatus , assessment_time FROM sa_cns_asphyxia where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and assessment_time <='").append(anaEndTime).append("'")
					.append(" order by  assessment_time desc");


			activeAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
			aHours = getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
			
			if(aHours>0){
				if(diagnonis.length()>0) diagnonis.append("/");
				diagnonis.append("Asphyxia");
			}

			aHours = 0;

			query = new StringBuilder("SELECT  eventstatus , assessment_time FROM sa_cns_encephalopathy where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and assessment_time <='").append(anaEndTime).append("'")
					.append(" order by  assessment_time desc");


			activeAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
			aHours = getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
			
			if(aHours>0){
				if(diagnonis.length()>0) diagnonis.append("/");
				diagnonis.append("Encephalopathy");
			}

			aHours = 0;

			query = new StringBuilder("SELECT  eventstatus , assessment_date FROM sa_cns_neuromuscular_disorder where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and assessment_date <='").append(anaEndTime).append("'")
					.append(" order by  assessment_date desc");


			activeAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
			aHours = getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );

			if(aHours>0){
				if(diagnonis.length()>0) diagnonis.append("/");
				diagnonis.append("Neuromuscular Disorder");
			}

			aHours = 0;

			query = new StringBuilder("SELECT  eventstatus , assessment_date FROM sa_cns_ivh where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and assessment_date <='").append(anaEndTime).append("'")
					.append(" order by  assessment_date desc");


			activeAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
			aHours = getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );

			if(aHours>0){
				if(diagnonis.length()>0) diagnonis.append("/");
				diagnonis.append("IVH");
			}


			aHours = 0;

			query = new StringBuilder("SELECT  eventstatus , assessment_date FROM sa_cns_hydrocephalus where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and assessment_date <='").append(anaEndTime).append("'")
					.append(" order by  assessment_date desc");


			activeAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
			aHours = getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
			
			if(aHours>0){
				if(diagnonis.length()>0) diagnonis.append("/");
				diagnonis.append("Hydrocephalus");
			}

			aHours = 0;

			query = new StringBuilder("SELECT  eventstatus , assessment_time FROM sa_cns_seizures where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and assessment_time <='").append(anaEndTime).append("'")
					.append(" order by  assessment_time desc");


			activeAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
			aHours = getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
			
			if(aHours>0){
				if(diagnonis.length()>0) diagnonis.append("/");
				diagnonis.append("Seizures");
			}

			aHours = 0;

			query = new StringBuilder("SELECT jaundicestatus,assessment_time FROM sa_jaundice where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and assessment_time <='").append(anaEndTime).append("'")
					.append(" order by  assessment_time desc");


			activeAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
			aHours = getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );

			if(aHours>0){
				if(diagnonis.length()>0) diagnonis.append("/");
				diagnonis.append("Jaundice");
			}

			aHours = 0;

			query = new StringBuilder("SELECT  hypoglycemia_event , assessment_time FROM sa_hypoglycemia where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and assessment_time <='").append(anaEndTime).append("'")
					.append(" order by  assessment_time desc");


			activeAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
			aHours = getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
			
			if(aHours>0){
				if(diagnonis.length()>0) diagnonis.append("/");
				diagnonis.append("Hypoglycemia");
			}

			aHours = 0;

			query = new StringBuilder("SELECT  renal_status , assessment_time FROM sa_renalfailure where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and assessment_time <='").append(anaEndTime).append("'")
					.append(" order by  assessment_time desc");


			activeAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
			aHours = getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
			
			if(aHours>0){
				if(diagnonis.length()>0) diagnonis.append("/");
				diagnonis.append("Renal");
			}

			aHours = 0;

			query = new StringBuilder("SELECT  miscellaneousstatus , assessment_time,disease,otherDisease FROM sa_miscellaneous where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and assessment_time <='").append(anaEndTime).append("'")
					.append(" order by  assessment_time desc");


			activeAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
			aHours = getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );

			if(aHours>0){

				if (!BasicUtils.isEmpty(activeAssessmentList)) {
					Iterator<Object[]> itr = activeAssessmentList.iterator();
					Object[] obj = itr.next();
					if (diagnonis.length() > 0) diagnonis.append("/");
					if (!BasicUtils.isEmpty(obj[2]) && obj[2].toString().equalsIgnoreCase("Others")) {
						diagnonis.append(obj[3]);
					}else {
						diagnonis.append(obj[2]);
					}
				}
			}

			aHours = 0;
			query = new StringBuilder("SELECT  miscellaneousstatus , assessment_time,disease,otherDisease FROM sa_miscellaneous_2 where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and assessment_time <='").append(anaEndTime).append("'")
					.append(" order by  assessment_time desc");

			activeAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
			aHours = getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );


			if(aHours>0){
				if (!BasicUtils.isEmpty(activeAssessmentList)) {
					Iterator<Object[]> itr = activeAssessmentList.iterator();
					Object[] obj = itr.next();
					if (diagnonis.length() > 0) diagnonis.append("/");
					if (!BasicUtils.isEmpty(obj[2]) && obj[2].toString().equalsIgnoreCase("Others")) {
						diagnonis.append(obj[3]);
					}else {
						diagnonis.append(obj[2]);
					}
				}
			}

			aHours = 0;

			query = new StringBuilder("SELECT  feed_intolerance_status , assessment_time FROM sa_feed_intolerance where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and assessment_time <='").append(anaEndTime).append("'")
					.append(" order by  assessment_time desc");


			activeAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
			aHours = getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
			
			if(aHours>0){
				if(diagnonis.length()>0) diagnonis.append("/");
				diagnonis.append("Feed intolerance\n");
			}

			aHours = 0;

			query = new StringBuilder("SELECT  shockstatus , assessment_time FROM sa_shock where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and assessment_time <='").append(anaEndTime).append("'")
					.append(" order by  assessment_time desc");

			activeAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
			aHours = getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
			
			if(aHours>0){
				if(diagnonis.length()>0) diagnonis.append("/");
				diagnonis.append("Shock");
			}
			aHours = 0;


		}catch(Exception e){
			e.printStackTrace();
		}

		return diagnonis.toString();

	}

	public boolean isDifferentRespObj(RespSupport obj1,RespSupport obj2) {
		boolean isDifferentResp = false;

		if( obj1 == null) {
			return true;
		}

		if( !(obj1.toComparableString().equalsIgnoreCase(obj2.toComparableString()))) 
			isDifferentResp = true;

//		if( !(obj1.getRsVentType().equalsIgnoreCase(obj2.getRsVentType())) ) isDifferentResp = true;
//
//		if( obj1.getRsFio2()!= null &&   obj2.getRsFio2()!= null && !(obj1.getRsFio2().equalsIgnoreCase(obj2.getRsFio2())) ) isDifferentResp = true;
//
//		if( obj1.getRsCpapType()!= null &&   obj2.getRsCpapType()!= null && !(obj1.getRsCpapType().equalsIgnoreCase(obj2.getRsCpapType())) ) isDifferentResp = true;

		return isDifferentResp;
	}

	public String changedParameterRespObj(RespSupport obj1,RespSupport obj2) {

		String a = obj1.toComparableString();
		String b = obj2.toComparableString();
 		String diff = "";

 		int at = indexOfDifference(a, b);
		while(at!=-1) {
			 String diff1 = findDiff(a, b, at);
			System.out.println(diff1);
			if(diff.length()>1) diff+=", ";
			diff+= diff1;

			int aStart = a.indexOf(',', at);
			int bStart = b.indexOf(',', at);

			a = a.substring(aStart+1);
			b = b.substring(bStart+1);
			at = indexOfDifference(a,b);
		}

	return diff;
	}


	public static String findDiff(String a, String b, int at) {

		String ret = null;
		System.out.println("a---------------"+a);
		System.out.println("b---------------"+b);
		System.out.println("at===========   "+at);


		String str1 = findBetweenComa(a,at);
		String str2 = findBetweenComa(b,at);

		System.out.println("a diff---------------" + str1);
		System.out.println("b diff---------------" + str2);

		if(str1 !=null) {
			try {
				String change = " decreased ";
				String[] arr1 = str1.split("=");
				String[] arr2 = str2.split("=");
				if (arr1[1].equals("null")) arr1[1] = "0";
				if (arr2[1].equals("null")) arr2[1] = "0";
					try {
						if ((Integer.parseInt(arr1[1]) > (Integer.parseInt(arr2[1])))) change = " increased ";
					}catch(Exception e){
						change = " changed ";
					}
				ret = arr1[0] + " was"+change+"from " + arr2[1] + " to " + arr1[1];
				}catch(Exception e){
					e.printStackTrace();

				}
		}
		return ret;

	}

	public static String findBetweenComa(String line, int index ) {

		int end = line.indexOf(',', index);
		String sub = line.substring(0,end);
		int start = sub.lastIndexOf(',');
		String result = sub.substring(start+1);
		return result;
	}



	public static int indexOfDifference(CharSequence cs1, CharSequence cs2) {
		if (cs1 == cs2) {
			return -1;
		}
		if (cs1 == null || cs2 == null) {
			return -1;
		}
		int i;
		for (i = 0; i < cs1.length() && i < cs2.length(); ++i) {
			if (cs1.charAt(i) != cs2.charAt(i)) {
				break;
			}
		}
		if (i < cs2.length() || i < cs1.length()) {
			return i;
		}
		return -1;
	}



	public String formatString(String str) {

		int index = str.lastIndexOf(",");
	   	if(index!=-1)	str = new java.lang.StringBuilder(str).replace(index, index+1, " and ").toString();

		return str;

	}

	// Get Daily Progress Notes
	@Override
	@SuppressWarnings({ "unchecked", "deprecation" })
	public DailyProgressNotes getDailyProgressNotes(String uhid, String dateStr, String fromTimestamps, String toTimestamps,
													String branchName, String loggedInUser) {
		// pastIntakeOutputList
		DailyProgressNotes returnObj = new DailyProgressNotes();
		String htmlNextLine = System.getProperty("line.separator");// "&#13;&#10;";

		long oneDay = 24 * 60 * 60 * 1000;
		long twoDay = 48 * 60 * 60 * 1000;

		SimpleDateFormat sdfTimestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
		SimpleDateFormat onlyTimesdf = new SimpleDateFormat("hh:mm a");

		int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
				- TimeZone.getDefault().getRawOffset();

		boolean preterm = false;
		try {

			int fromTime = 8;
			String progressNoteFormatType = "";

			String babyDetailList = "select obj from BabyDetail as obj where uhid='" + uhid + "' and admissionstatus ='true' order by creationtime desc";
			List<BabyDetail> babyObj = notesDoa.getListFromMappedObjNativeQuery(babyDetailList);
			String branchname = babyObj.get(0).getBranchname();
			String consultantName = babyObj.get(0).getAdmittingdoctor();

			long doa = babyObj.get(0).getDateofadmission().getTime();

			String queryhospitalName = "select obj from RefHospitalbranchname obj where branchname='" + branchname + "'";
			List<RefHospitalbranchname> refHospitalBranchNameList = notesDoa.getListFromMappedObjNativeQuery(queryhospitalName);
			if (!BasicUtils.isEmpty(refHospitalBranchNameList)) {
				if (!BasicUtils.isEmpty(refHospitalBranchNameList.get(0).getFromTime())) {
					fromTime = refHospitalBranchNameList.get(0).getFromTime();
					progressNoteFormatType = refHospitalBranchNameList.get(0).getProgressNotesFormat();

				}
			}
			returnObj.setProgressNoteFormatType(progressNoteFormatType);

			if (!BasicUtils.isEmpty(consultantName)) {
				String[] nameArr = consultantName.split(" ");
				StringBuilder userQuery = new StringBuilder("select obj from User as obj where firstname='" + nameArr[0] + "'");
				if (nameArr.length > 1 && !BasicUtils.isEmpty(nameArr[1])) {
					userQuery.append(" and lastname = '" + nameArr[1] + "'");
				}
				List<User> userList = notesDoa.getListFromMappedObjNativeQuery(userQuery.toString());
				if (!BasicUtils.isEmpty(userList)) {
					String consultantUsername = userList.get(0).getUserName();
					if (!BasicUtils.isEmpty(consultantUsername)) {
						List<KeyValueObj> consultantSignatureImageData = userPanel.getImageData(consultantUsername, branchname);
						returnObj.setConsultantSignatureImage(consultantSignatureImageData);
					}
				}
			}

			java.util.Date dateObj = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);

			Timestamp originalTodayDate = new Timestamp(dateObj.getTime());
			Timestamp t1 = new Timestamp(System.currentTimeMillis());

			originalTodayDate.setHours(t1.getHours());
			originalTodayDate.setMinutes(t1.getMinutes());

			dateObj.setHours(fromTime);
			dateObj.setMinutes(0);
			dateObj.setSeconds(0);

			// from date and to date
			Timestamp givenDate = new Timestamp((new SimpleDateFormat("yyyy-MM-dd").parse(dateStr)).getTime());
			Timestamp fromDate = new Timestamp(givenDate.getTime() - offset);
			Timestamp toDate = new Timestamp(givenDate.getTime() - offset + (24 * 60 * 60 * 1000) + (1 * 59 * 1000));

			Timestamp initialTodayObj = new Timestamp(dateObj.getTime());
			Timestamp initialTomObj = new Timestamp(dateObj.getTime() + oneDay);
			Timestamp initialPrevObj = new Timestamp(dateObj.getTime() - oneDay);

			Date dateOnlyObj = new Date(initialTomObj.getTime());

			String getNoteSql = HqlSqlQueryConstants.getDailyProgressNotes(uhid, dateStr);
			List<DailyProgressNotes> notesList = inicuDoa.getListFromMappedObjQuery(getNoteSql);

			Timestamp todayObj = new Timestamp(dateObj.getTime());
			Timestamp yesObj = new Timestamp(todayObj.getTime() - oneDay);
			Timestamp tomorrowObj = new Timestamp(todayObj.getTime() + oneDay);

			String strDateTom = sdfTimestamp.format(tomorrowObj);
			String strDateYester = sdfTimestamp.format(yesObj);
			String strDateToday = sdfTimestamp.format(todayObj);

			String strDateTodayPrint = sdf.format(todayObj);
			String strDateYesterPrint = sdf.format(yesObj);
			String strDateTomPrint = sdf.format(tomorrowObj);

			if (progressNoteFormatType.equalsIgnoreCase("Configurable")) {
				String currentTime = "" + strDateTodayPrint + " to " + strDateTomPrint + "";
				returnObj.setCurrentTime(currentTime);
			}
			else {
				int year = todayObj.getYear() + 1900;
				String currentTime = todayObj.getDate() + " " + BasicConstants.monthMapping.get(todayObj.getMonth()) + " " + year + "";
				returnObj.setCurrentTime(currentTime);
			}

			String previousTime = "" + strDateYesterPrint + " to " + strDateTodayPrint + "";
			returnObj.setPreviousTime(previousTime);

			todayObj = new Timestamp(dateObj.getTime() - offset + 59000);
			yesObj = new Timestamp(todayObj.getTime() - oneDay + 60000);
			tomorrowObj = new Timestamp(todayObj.getTime() + oneDay);

			strDateYester = sdfTimestamp.format(yesObj);
			strDateToday = sdfTimestamp.format(todayObj);
			strDateTom = sdfTimestamp.format(tomorrowObj);

			long hoursAfterAdmissionDays = ((todayObj.getTime() - doa) / (60 * 60 * 1000));// days..

			Timestamp prevToyesObj = new Timestamp(todayObj.getTime() - twoDay);

			String strDateYesterToPrev = sdfTimestamp.format(prevToyesObj);

			// getting baby visit weight details

			String entryTime = "08:00:00";
			java.sql.Time sqlPresentTime = null;
			DateFormat readFormatTime = new SimpleDateFormat("hh:mm:ss");
			java.util.Date presentTime = readFormatTime.parse(entryTime);
			sqlPresentTime = new java.sql.Time(presentTime.getTime());
			sqlPresentTime.setHours(fromTime);
			sqlPresentTime.setMinutes(0);
			sqlPresentTime.setSeconds(0);

			// Not Touched
			if (progressNoteFormatType.equalsIgnoreCase("Configurable")) {
				String queryCurrentBabyVisit = "select obj from BabyVisit as obj where uhid='" + uhid
						+ "' and ((visitdate = '" + tomorrowObj + "' and visittime <= '" + sqlPresentTime
						+ "') or (visitdate = '" + todayObj + "' and visittime >= '" + sqlPresentTime
						+ "')) order by visitdate desc";
				List<BabyVisit> currentBabyVisitList = notesDoa.getListFromMappedObjNativeQuery(queryCurrentBabyVisit);
				if (!BasicUtils.isEmpty(currentBabyVisitList)) {
					BabyVisit babyVisit = currentBabyVisitList.get(0);
//					if (currentBabyVisitList.size() > 1) {
//						BabyVisit preVisit = currentBabyVisitList.get(1);
//						Float prevWeight = preVisit.getCurrentdateweight();
//						babyVisit.setPrevDateWeight(prevWeight);
//					}
					returnObj.setBasicDetails(babyVisit);
				}
				queryCurrentBabyVisit = "select obj from BabyVisit as obj where uhid='" + uhid
						+ "' and ((visitdate = '" + todayObj + "' and visittime <= '" + sqlPresentTime
						+ "') or (visitdate = '" + yesObj + "' and visittime >= '" + sqlPresentTime
						+ "')) order by visitdate desc";
				List<BabyVisit> currentBabyVisitListPrev = notesDoa.getListFromMappedObjNativeQuery(queryCurrentBabyVisit);
				if (!BasicUtils.isEmpty(currentBabyVisitListPrev)) {
					BabyVisit babyVisit = currentBabyVisitListPrev.get(0);
					Float prevWeight = babyVisit.getCurrentdateweight();
					if (BasicUtils.isEmpty(currentBabyVisitList)) {
						babyVisit.setPrevDateWeight(prevWeight);
						babyVisit.setCurrentdateweight(null);
						returnObj.setBasicDetails(babyVisit);
					} else {
						returnObj.getBasicDetails().setPrevDateWeight(prevWeight);
					}
				}


			}
			else {
				String queryCurrentBabyVisit = "select obj from BabyVisit as obj where uhid='" + uhid
						+ "' and ((visitdate = '" + tomorrowObj + "' and visittime <= '" + sqlPresentTime
						+ "') or (visitdate = '" + todayObj + "' and visittime >= '" + sqlPresentTime
						+ "')) order by visitdate desc, visittime desc";
				List<BabyVisit> currentBabyVisitList = notesDoa.getListFromMappedObjNativeQuery(queryCurrentBabyVisit);
				if (!BasicUtils.isEmpty(currentBabyVisitList)) {
					BabyVisit babyVisit = currentBabyVisitList.get(0);
					returnObj.setBasicDetails(babyVisit);
				}
				queryCurrentBabyVisit = "select obj from BabyVisit as obj where uhid='" + uhid
						+ "' and ((visitdate = '" + todayObj + "' and visittime <= '" + sqlPresentTime
						+ "') or (visitdate = '" + yesObj + "' and visittime >= '" + sqlPresentTime
						+ "')) order by visitdate desc, visittime desc";
				List<BabyVisit> currentBabyVisitListPrev = notesDoa.getListFromMappedObjNativeQuery(queryCurrentBabyVisit);
				if (!BasicUtils.isEmpty(currentBabyVisitListPrev)) {
					BabyVisit babyVisit = currentBabyVisitListPrev.get(0);
					Float prevWeight = babyVisit.getCurrentdateweight();
					if (BasicUtils.isEmpty(currentBabyVisitList)) {
						babyVisit.setPrevDateWeight(prevWeight);
						babyVisit.setCurrentdateweight(null);
						returnObj.setBasicDetails(babyVisit);
					} else {
						returnObj.getBasicDetails().setPrevDateWeight(prevWeight);
					}
				}
			}

			int gestDay;
			int gestWeek;
			int gestWeekFinal;
			int lifeWeeks;
			int lifeDays;
			int totalDaysOfGestation;
			int weeksAfterDayAdd;

			Calendar cal1 = Calendar.getInstance();

			java.util.Date dateOfBirthTemp = babyObj.get(0).getDateofbirth();

			String timeOfBirth = babyObj.get(0).getTimeofbirth();

			// Not Touched
			if (dateOfBirthTemp != null) {
				Timestamp dateOfBirth = new Timestamp(dateOfBirthTemp.getTime());

				String[] tobStr = timeOfBirth.split(",");
				if (tobStr[2] == "PM" && Integer.parseInt(tobStr[0]) != 12) {
					dateOfBirth.setHours(12 + Integer.parseInt(tobStr[0]));
				} else if (tobStr[2] == "AM" && Integer.parseInt(tobStr[0]) == 12) {
					dateOfBirth.setHours(0);
				} else {
					dateOfBirth.setHours(Integer.parseInt(tobStr[0]));
				}
				dateOfBirth.setMinutes(Integer.parseInt(tobStr[1]));
 				System.out.println("*****Today Date" + originalTodayDate.getTime());


				double difference = ((originalTodayDate.getTime() - dateOfBirth.getTime()) / 86400000) + 1;
				System.out.println("*****First DOL" + difference);

				int ageOfDays = (int) java.lang.Math.ceil(difference);
				System.out.println("*****Second DOL" + ageOfDays);

				if (babyObj.get(0).getGestationdaysbylmp() != null && babyObj.get(0).getGestationweekbylmp() != null) {
					gestDay = babyObj.get(0).getGestationdaysbylmp();
					gestWeek = babyObj.get(0).getGestationweekbylmp();
					gestWeekFinal = babyObj.get(0).getGestationweekbylmp();
				} else {
					gestDay = 0;
					gestWeek = 0;
					gestWeekFinal = 0;
				}

				if (ageOfDays > 7) {
					lifeWeeks = ageOfDays / 7;
					lifeDays = ageOfDays - lifeWeeks * 7;
					gestWeek += lifeWeeks;
					totalDaysOfGestation = lifeDays + gestDay;
					if (totalDaysOfGestation > 6) {

						weeksAfterDayAdd = (totalDaysOfGestation / 7);
						gestWeek += weeksAfterDayAdd;
						gestDay = totalDaysOfGestation - weeksAfterDayAdd * 7;

					} else {
						gestDay = (int) totalDaysOfGestation;
					}
				} else {
					totalDaysOfGestation = ageOfDays + gestDay;
					if (totalDaysOfGestation > 6) {
						weeksAfterDayAdd = (totalDaysOfGestation / 7);

						gestWeek += weeksAfterDayAdd;
						gestDay = (int) (totalDaysOfGestation - weeksAfterDayAdd * 7);
					} else {
						gestDay = (int) totalDaysOfGestation;
					}
				}

				if (gestDay == 0) {
					gestDay = 6;
					gestWeek = gestWeek - 1;
				} else {
					gestDay = gestDay - 1;
				}

				if (progressNoteFormatType.equalsIgnoreCase("Configurable")) {
					ageOfDays = ageOfDays + 1;
					if (gestDay == 6) {
						gestDay = 0;
						gestWeek++;
					} else {
						gestDay++;
					}
				}
				returnObj.setCurrentDol(ageOfDays);
				returnObj.setCurrentGestationDays(gestDay);
				returnObj.setCurrentGestationWeeks(gestWeek);

				if (gestWeekFinal < 37) preterm = true;
			}


			Float todayWeight = null;
			if (returnObj.getBasicDetails() == null
					|| returnObj.getBasicDetails().getWorkingweight() == null
					|| returnObj.getBasicDetails().getWorkingweight() == 0) {
				String sql = "SELECT weight_for_cal FROM baby_visit where uhid='" + uhid
						+ "' and weight_for_cal is not null and visitdate <= '"
						+ strDateToday.substring(0, 10)
						+ "' order by visitdate desc, visittime desc limit 1";
				List<Object> babyWeight = (List<Object>) inicuDoa.getListFromNativeQuery(sql);
				if (!BasicUtils.isEmpty(babyWeight)) {
					todayWeight = (Float) babyWeight.get(0) / 1000;
				}
			}
			else {
				todayWeight = returnObj.getBasicDetails().getWorkingweight() / 1000;
			}


			// diagnosis here
			boolean prematurity = false;
			String queryAdmissionNotes = "select obj from  AdmissionNotes obj where uhid='" + uhid
					+ "' order by creationtime desc";
			List<AdmissionNotes> listAdmissionNotes = notesDoa.getListFromMappedObjNativeQuery(queryAdmissionNotes);
			String adminssionNotesDiagnosis = "";
			String reasonAdmission = "";
			if (!BasicUtils.isEmpty(listAdmissionNotes)) {
				AdmissionNotes admNotes = listAdmissionNotes.get(0);

				if (!BasicUtils.isEmpty(admNotes.getDiagnosis())) {
					reasonAdmission = admNotes.getReason_admission();
					adminssionNotesDiagnosis = admNotes.getDiagnosis();
					adminssionNotesDiagnosis = adminssionNotesDiagnosis.replace("/Female", "");
					adminssionNotesDiagnosis = adminssionNotesDiagnosis.replace("/Male", "");
					adminssionNotesDiagnosis = adminssionNotesDiagnosis.replace("/Unknown", "");
				}

//				try {
//
//					// replace the Gestation as super script
//					String[] arr = adminssionNotesDiagnosis.split("/");
//
//					String[] secArr = arr[1].trim().split(" ");
//					System.out.println("Checking");
//
//					String gestation = " <var>"+ secArr[0].toString() +"</var> ";
//
//					if(secArr.length > 2 && secArr[2]!=null && !BasicUtils.isEmpty(secArr[2])){
//						gestation += "<sup> +" + secArr[2].toString() + "</sup>";
//					}else{
//						gestation += "<sup> +0 </sup>";
//					}
//					arr[1]= gestation;
//
//					adminssionNotesDiagnosis= "";
//					for(int i=0;i<arr.length;i++){
//						if(adminssionNotesDiagnosis == ""){
//							adminssionNotesDiagnosis +=arr[i];
//						}else{
//							adminssionNotesDiagnosis +="/"+arr[i];
//						}
//					}
//
//					System.out.println("Checking");
//				}catch(Exception e){
//					System.out.println(e.getMessage());
//				}
			}

			if (adminssionNotesDiagnosis != null && adminssionNotesDiagnosis.indexOf("Prematurity") != -1) {
				prematurity = true;
			}

			String queryDiagnosisDetails = "select obj from DashboardFinalview  obj where uhid='" + uhid + "'";
			List<DashboardFinalview> listDiagnosisDetails = notesDoa
					.getListFromMappedObjNativeQuery(queryDiagnosisDetails);
			if (!BasicUtils.isEmpty(listDiagnosisDetails)) {
				if (!BasicUtils.isEmpty(listDiagnosisDetails.get(0).getDiagnosis())) {
					// diagnosis note
					String diagnosisStr = listDiagnosisDetails.get(0).getDiagnosis();

					int flag = 0;
					if(adminssionNotesDiagnosis.contains("Sepsis")){
						adminssionNotesDiagnosis = getSepsisType(adminssionNotesDiagnosis,uhid);
						flag =1;
					}

					if (diagnosisStr.contains("Sepsis")) {
						if(flag == 0) {
						diagnosisStr = getSepsisType(diagnosisStr, uhid);
						}else if (flag == 1){
							// replace the Sepsis with empty string
							diagnosisStr = diagnosisStr.replace("Sepsis","");
						}
					}

					adminssionNotesDiagnosis = adminssionNotesDiagnosis.replace(",", "/") + "," + diagnosisStr;
				}
			}

			//misc disease list
			String queryMiscDisease = "select obj from  SaMiscellaneous obj where uhid='" + uhid
					+ "' order by creationtime desc";
			List<SaMiscellaneous> listMiscDisease = notesDoa.getListFromMappedObjNativeQuery(queryMiscDisease);
			String miscDiseaseDiagnosis = "";
			if (!BasicUtils.isEmpty(listMiscDisease)) {
				for (SaMiscellaneous miscObj : listMiscDisease) {
					if (!BasicUtils.isEmpty(miscObj.getDisease())) {
						if(miscObj.getDisease().equalsIgnoreCase("Others")){
							miscDiseaseDiagnosis += miscObj.getOtherDisease() + ",";
						}else {
							miscDiseaseDiagnosis += miscObj.getDisease() + ",";
						}
					} else {
						miscDiseaseDiagnosis += "Miscellaneous" + ",";
					}
				}
				if (!BasicUtils.isEmpty(miscDiseaseDiagnosis)) {
					if (BasicUtils.isEmpty(adminssionNotesDiagnosis)) {
						adminssionNotesDiagnosis += miscDiseaseDiagnosis;
					} else {
						adminssionNotesDiagnosis += "," + miscDiseaseDiagnosis;
					}
				}
			}

			//misc2 disease list
			String queryMisc2Disease = "select obj from  SaMiscellaneous2 obj where uhid='" + uhid
					+ "' order by creationtime desc";
			List<SaMiscellaneous2> listMisc2Disease = notesDoa.getListFromMappedObjNativeQuery(queryMisc2Disease);
			String misc2DiseaseDiagnosis = "";
			if (!BasicUtils.isEmpty(listMisc2Disease)) {
				for (SaMiscellaneous2 misc2Obj : listMisc2Disease) {
					if (!BasicUtils.isEmpty(misc2Obj.getDisease())) {
						if(misc2Obj.getDisease().equalsIgnoreCase("Others")){
							misc2DiseaseDiagnosis += misc2Obj.getOtherDisease() + ",";
						}else {
							misc2DiseaseDiagnosis += misc2Obj.getDisease() + ",";
						}
					} else {
						misc2DiseaseDiagnosis += "Miscellaneous" + ",";
					}
				}
				if (!BasicUtils.isEmpty(misc2DiseaseDiagnosis)) {
					if (BasicUtils.isEmpty(adminssionNotesDiagnosis)) {
						adminssionNotesDiagnosis += misc2DiseaseDiagnosis;
					} else {
						adminssionNotesDiagnosis += "," + misc2DiseaseDiagnosis;
					}
				}
			}


			String queryRop = "select obj from ScreenRop as obj where uhid = '" + uhid + "'";
			List<ScreenRop> ropLists = inicuDoa.getListFromMappedObjQuery(queryRop);
			boolean leftRop = false;
			boolean rightRop = false;
			String ropDiagnosis = "";
			if (!BasicUtils.isEmpty(ropLists)) {
				for (ScreenRop ropObject : ropLists) {
					if (ropObject.getIs_rop() != null && ropObject.getIs_rop() == true) {
						ropDiagnosis = "ROP";
						if (ropObject.getIs_rop_left() != null && ropObject.getIs_rop_left() == true) {
							leftRop = true;
						}
						if (ropObject.getIs_rop_right() != null && ropObject.getIs_rop_right() == true) {
							rightRop = true;
						}
					}
				}


				if (leftRop == true && rightRop == false) {
					ropDiagnosis = "ROP Left eye";
					adminssionNotesDiagnosis += ropDiagnosis;
				} else if (rightRop == true && leftRop == false) {
					ropDiagnosis = "ROP Right eye";
					adminssionNotesDiagnosis += ropDiagnosis;
				} else if (rightRop == true && leftRop == true) {
					ropDiagnosis = "ROP Both eyes";
					adminssionNotesDiagnosis += ropDiagnosis;
				} else {
					adminssionNotesDiagnosis += ropDiagnosis;
				}

			}

 			String provisionalDiagnosis = "";
			if (!BasicUtils.isEmpty(adminssionNotesDiagnosis)) {
				String[] finalDiagArr = adminssionNotesDiagnosis.split(",");

				String flag = "";
				for (String diag : finalDiagArr) {
					// update provisional dignosis as per selected date - Start
					if (!diag.contains("Prematurity") && !diag.contains("Twin") && !diag.contains("ROP")) {
						flag = checkActiveOrPassiveAsssessmentByDate(fromDate, tomorrowObj, uhid, diag);
						if(flag.equalsIgnoreCase("inactive")){
							diag = diag+"(Inactive)";
						}else if (flag.equalsIgnoreCase("notpresent")){
//							continue;
							System.out.println("Do nothing");
						}
					}
					// update provisional dignosis as per selected date - End
					if (provisionalDiagnosis.isEmpty()) {
						provisionalDiagnosis = diag.replace(" ", "");
					} else {
						if (!provisionalDiagnosis.contains(diag.replace(" ", ""))) {
							provisionalDiagnosis += ", " + diag.replace(" ", "");
						}
					}
				}
			}
			provisionalDiagnosis = provisionalDiagnosis
					.replace("RespiratoryDistress", "Respiratory Distress")
					.replace("SuspectedSepsis", "Suspected Sepsis")
					.replace("ClinicalSepsis", "Clinical Sepsis")
					.replace("FeedIntolerance", "Feed Intolerance").replace("CongenitalHeartDisease", "Congenital Heart Disease")
					.replace("ROPBotheyes", "ROP Both eyes").replace("ROPLefteye", "ROP Left eye")
					.replace("ROPRighteye", "ROP Right eye");

			provisionalDiagnosis = provisionalDiagnosis.replace(",", "/");
			String arr[] = provisionalDiagnosis.split("/");
			String tmpProv = "";
			try {
				for (int i = 0; i < arr.length; i++) {

					if (arr[i].indexOf("AGA") != -1 || arr[i].indexOf("SGA") != -1 || arr[i].indexOf("LGA") != -1) {
						String arrTemp = arr[i].trim().substring(0,3);
						arr[i] = arrTemp;
					}
					if (tmpProv == "") {
						tmpProv += arr[i];
					} else {
						tmpProv += "/" + arr[i];
					}
				}
				returnObj.setProvisionalDiagnosis(tmpProv);
			}catch(Exception e){
				System.out.println(e.getMessage());
				returnObj.setProvisionalDiagnosis(provisionalDiagnosis);
			}



			// new assessment diagnonis start
			String diagnosis = getActiveAssessmentInaPeriod(fromDate, toDate, uhid);

			String finalDiagnosisUnique = "";

			if (diagnosis != null && diagnosis.length() > 0) {
				adminssionNotesDiagnosis = "Baby is being managed for ";
				//if(prematurity) adminssionNotesDiagnosis += "Prematurity/";
				adminssionNotesDiagnosis += diagnosis + ".";
				finalDiagnosisUnique = adminssionNotesDiagnosis;
			}
			else {

				if (!BasicUtils.isEmpty(adminssionNotesDiagnosis)) {
					String[] finalDiagArr = adminssionNotesDiagnosis.split(",");
					for (String diag : finalDiagArr) {
						if (finalDiagnosisUnique.isEmpty()) {
							finalDiagnosisUnique = diag.replace(" ", "");
						} else {
							if (!finalDiagnosisUnique.contains(diag.replace(" ", ""))) {
								finalDiagnosisUnique += ", " + diag.replace(" ", "");
							}
						}
					}
				}
				diagnosis = finalDiagnosisUnique;
				finalDiagnosisUnique = "Baby is being managed for " + reasonAdmission + " with no active concerns.";
			}

			// new assessment diagnonis end
			finalDiagnosisUnique = finalDiagnosisUnique.replace("RespiratoryDistress", "Respiratory Distress").replace("FeedIntolerance", "Feed Intolerance").replace("CongenitalHeartDisease", "Congenital Heart Disease").replace("ROPBotheyes", "ROP Both eyes").replace("ROPLefteye", "ROP Left eye").replace("ROPRighteye", "ROP Right eye");
			returnObj.setDiagnosis(finalDiagnosisUnique.replace(",", "/"));

			// vital details here
			String queryCurrentBabyVitals = "select obj from NursingVitalparameter as obj where uhid='" + uhid
					+ "' and entryDate <= '" + strDateToday + "' and entryDate >= '" + strDateYester
					+ "' order by entryDate desc";
			List<NursingVitalparameter> currentBabyVitalList = notesDoa
					.getListFromMappedObjNativeQuery(queryCurrentBabyVitals);
			String vitalInfoStr = "";
			boolean isTemp = true;
			float centralTemp = 0;
			double centralTemplow = 2;
			double centralTempHigh = 3;
			String centralTempAbnormalConditionName = "";

//
			boolean isHeartRate = true;
			int heartRate = 0;
			int heartRatelow = 90;
			int heartRateHigh = 150;
			String heartRateAbnormalConditionName = "";

			boolean ismeanbp = true;
			int meanbp = 0;
			int meanbplow = 58;
			int meanbpHigh = 73;
			String meanbpAbnormalConditionName = "";

			boolean isCRT = true;
			int crt = 0;
			//int crtlow = 59;
			int crtHigh = 4;
			//int precrtlow = 38;
			int precrthigh = 4;
			String crtAbnormalConditionName = "";

			if (preterm) {
				heartRatelow = 120;
				heartRateHigh = 170;
				meanbplow = 38;
				meanbpHigh = 57;
			}


			boolean vitalsGiven = false;

			List<String> tcbValueList = new ArrayList<String>();
			List<String> tcbValueTime = new ArrayList<String>();

			List<Float> rbsValueList = new ArrayList<Float>();
			List<String> rbsValueTime = new ArrayList<String>();

			try {

				for (NursingVitalparameter vitalObj : currentBabyVitalList) {

					if (!BasicUtils.isEmpty(vitalObj.getHrRate()) || !BasicUtils.isEmpty(vitalObj.getMeanBp()) || !BasicUtils
							.isEmpty(vitalObj.getCft()) || (!BasicUtils.isEmpty(vitalObj.getCentraltemp()) && !BasicUtils
							.isEmpty(vitalObj.getPeripheraltemp())) || !BasicUtils.isEmpty(vitalObj.getPulserate())) {
						vitalsGiven = true;
					}

					//				if(!BasicUtils.isEmpty(vitalObj.getCentraltemp()) && (vitalObj.getCentraltemp() < 36.5 || vitalObj.getCentraltemp() > 37.5)) {
					//					isTemp = false;
					//					float var = vitalObj.getCentraltemp();
					//					centralTemp = (int)var;
					//				}
					if (!BasicUtils.isEmpty(vitalObj.getHrRate()) && (vitalObj.getHrRate() < heartRatelow
							|| vitalObj.getHrRate() > heartRateHigh)) {
						isHeartRate = false;
						float var1 = vitalObj.getHrRate();
						heartRate = (int) var1;

						if (vitalObj.getHrRate() < heartRatelow)
							heartRateAbnormalConditionName = "bradycardia";
						else
							heartRateAbnormalConditionName = "tachycardia";
					}
					if (!BasicUtils.isEmpty(vitalObj.getPulserate()) && (
							Integer.parseInt(vitalObj.getPulserate()) < heartRatelow
									|| Integer.parseInt(vitalObj.getPulserate()) > heartRateHigh)) {
						isHeartRate = false;
						float var1 = Integer.parseInt(vitalObj.getPulserate());
						heartRate = (int) var1;

						if (Integer.parseInt(vitalObj.getPulserate()) < heartRatelow)
							heartRateAbnormalConditionName = "bradycardia";
						else
							heartRateAbnormalConditionName = "tachycardia";
					}
					if (!BasicUtils.isEmpty(vitalObj.getMeanBp()) && (
							Integer.parseInt(vitalObj.getMeanBp().trim()) < meanbplow
									|| Integer.parseInt(vitalObj.getMeanBp().trim()) > meanbpHigh)) {
						ismeanbp = false;
						int var1 = Integer.parseInt(vitalObj.getMeanBp().trim());
						meanbp = (int) var1;

						if (Integer.parseInt(vitalObj.getMeanBp().trim()) < meanbplow)
							meanbpAbnormalConditionName = "hypotensive";
						else
							meanbpAbnormalConditionName = "hypertensive";
					}

					//				if ((!BasicUtils.isEmpty(vitalObj.getCentraltemp()) && !BasicUtils.isEmpty(vitalObj.getPeripheraltemp()))){
					//
					//					float diffTemp = vitalObj.getCentraltemp() - vitalObj.getPeripheraltemp();
					//					if(diffTemp < centralTemplow || diffTemp > centralTempHigh) {
					//						isTemp = false;
					//						float var1 = vitalObj.getCentraltemp();
					//						centralTemp = var1;
					//
					//						if (diffTemp < centralTemplow) centralTempAbnormalConditionName = "hypothermic";
					//						else centralTempAbnormalConditionName = "hyperthermic";
					//					}
					//				}

					if ((!BasicUtils.isEmpty(vitalObj.getCentraltemp())) && !BasicUtils
							.isEmpty(vitalObj.getCentralTempUnit())) {
						float CentralTemperature = vitalObj.getCentraltemp();

						isTemp = false;

						float lowerTemperature = 36.5F;
						float higherTemperature = 37.5F;

						if (vitalObj.getCentralTempUnit().equalsIgnoreCase("F")) {
							lowerTemperature = 97.7F;
							higherTemperature = 99.5F;
						}

						if (CentralTemperature < lowerTemperature)
							centralTempAbnormalConditionName = "hypothermic";
						else if (CentralTemperature > higherTemperature)
							centralTempAbnormalConditionName = "hyperthermic";

					} else if (!BasicUtils.isEmpty(vitalObj.getPeripheraltemp()) && !BasicUtils
							.isEmpty(vitalObj.getPeripheralTempUnit())) {
						float PeripheralTemperature = vitalObj.getPeripheraltemp();
						isTemp = false;

						float lowerTemperature = 36.5F;
						float higherTemperature = 37.5F;

						if (vitalObj.getPeripheralTempUnit().equalsIgnoreCase("F")) {
							lowerTemperature = 97.7F;
							higherTemperature = 99.5F;
						}

						if (PeripheralTemperature < lowerTemperature)
							centralTempAbnormalConditionName = "hypothermic";
						else if (PeripheralTemperature > higherTemperature)
							centralTempAbnormalConditionName = "hyperthermic";
					}

					if (!BasicUtils.isEmpty(vitalObj.getCft())) {
						crt = Integer.parseInt(vitalObj.getCft());
						if (crt > 4) {
							isCRT = false;
							crtAbnormalConditionName = "prolonged CRT";
						}
					}

					if (!BasicUtils.isEmpty(vitalObj.getTcb())) {
						tcbValueList.add(vitalObj.getTcb());
						String tcbTime = vitalObj.getEntryDate().getHours() + ":00";
						tcbValueTime.add(tcbTime);
					}
					if (!BasicUtils.isEmpty(vitalObj.getRbs())) {

						rbsValueList.add((vitalObj.getRbs()));
						String rbsTime = vitalObj.getEntryDate().getHours() + ":00";
						rbsValueTime.add(rbsTime);
					}
				}
			}catch(Exception e){
				System.out.println("Vital Parameters Exception :" + e.getMessage());
			}

			vitalsGiven = false;
			if(vitalsGiven) {
				if (isHeartRate && ismeanbp  && isCRT && isTemp) {
					if (hoursAfterAdmissionDays > 22){
						vitalInfoStr = "In previous 24 hours vitals were normal, baby remained hemodynamically stable. ";
					}else{
						vitalInfoStr = "Baby’s vital remained normal and hemodynamically stable since admission. ";
					}
	
				} else {
					if (hoursAfterAdmissionDays > 22) {
						vitalInfoStr += "In previous 24 hours baby ";
					} else {
						vitalInfoStr += "Baby ";
					}
					String unstableParamsHad = "";
					String unstableParamsWas = "";
		
					if (!isHeartRate) {
						if (unstableParamsHad.length() > 1) unstableParamsHad += ", ";
						unstableParamsHad += heartRateAbnormalConditionName;
	 				}
	
					if (!ismeanbp) {
						if (unstableParamsWas.length() > 1) unstableParamsWas += ", ";
						unstableParamsWas += meanbpAbnormalConditionName;
	 				}
	
					if (!isCRT) {
						if (unstableParamsHad.length() > 1) unstableParamsHad += ", ";
						unstableParamsHad += crtAbnormalConditionName;
	 				}
	
					if (!isTemp) {
						if (unstableParamsWas.length() > 1) unstableParamsWas += ", ";
						unstableParamsWas += centralTempAbnormalConditionName;
	 				}
						
					if(unstableParamsHad.length()>1 )  vitalInfoStr+=" had "+unstableParamsHad;
	
					if(unstableParamsWas.length()>1 ) {
						if (unstableParamsHad.length() > 1) {
							vitalInfoStr += " and ";
						}
						vitalInfoStr+=" was "+unstableParamsWas;
					}
	
					vitalInfoStr+= ". ";
					String stableParams = "";
	
					if (isHeartRate) {
						if (stableParams.length() > 1) stableParams += ", ";
						stableParams += "Heart Rate";
					}
					if (ismeanbp) {
						if (stableParams.length() > 1) stableParams += ", ";
						stableParams += "Mean BP";
					}
					if (isTemp) {
						if (stableParams.length() > 1) stableParams += ", ";
						stableParams += "Temperature";
					}
					if (isCRT) {
						if (stableParams.length() > 1) stableParams += ", ";
						stableParams += "CRT";
					}
	
					stableParams = formatString(stableParams);
					if(isHeartRate || ismeanbp || isTemp || isCRT) {
						if(stableParams.indexOf(" and ")!= -1)	
							vitalInfoStr +=  stableParams+ " were normal.";
						else 
							vitalInfoStr +=  stableParams+ " was normal.";
					}
				}
			}

			String respSupport = "";
			String ventMode = "";

			String queryCurrentRespSupport = "select obj from RespSupport as obj where uhid='" + uhid
					+ "' and creationtime <= '" + yesObj
					+ "' order by creationtime desc ";
			List<RespSupport> respSupportLists = notesDoa.getListFromMappedObjNativeQuery(queryCurrentRespSupport);

			boolean oneStartEntry = false;
			boolean prevDayRespActive = false;


			RespSupport oldObj = new RespSupport();

			for(RespSupport obj : respSupportLists) {
 					prevDayRespActive =  obj.getIsactive();
 					oneStartEntry = true;
					if(prevDayRespActive) {
						oldObj = obj ;
						respSupport += "Was on " + respSupportStr(obj) + ". ";
					}

					break;
 			}

 			queryCurrentRespSupport = "select obj from RespSupport as obj where uhid='" + uhid
			+ "' and creationtime <= '" + todayObj
			+ "' and creationtime >= '" + yesObj
			+ "' order by creationtime asc";

			boolean showLastUpdateSettings = false;

			respSupportLists = notesDoa.getListFromMappedObjNativeQuery(queryCurrentRespSupport);
			for(RespSupport obj : respSupportLists) {

 				if (!obj.getIsactive()) {

					respSupport += "Respiratory support was stopped";
					Timestamp dateTimeObj = new Timestamp(obj.getCreationtime().getTime() + offset);
					respSupport += " at " + onlyTimesdf.format(dateTimeObj.getTime()) + ". ";
					oldObj = obj;
					oneStartEntry = false;
					prevDayRespActive = false;
					showLastUpdateSettings = false;
				} else if (!BasicUtils.isEmpty(obj.getRsVentType())) {

					if (!prevDayRespActive) {

						respSupport += "Baby was put on ";
						respSupport += respSupportStrOnlyType(obj) ;
						Timestamp dateTimeObj = new Timestamp(obj.getCreationtime().getTime() + offset);
						respSupport += " at " + onlyTimesdf.format(dateTimeObj.getTime()) + ". ";

						oneStartEntry = true;
						prevDayRespActive = true;
						showLastUpdateSettings = true;
					} else if (!isDifferentRespObj(oldObj,obj)){

						if (!oneStartEntry ){

							respSupport += "Continued on ";
							respSupport += respSupportStrOnlyType(obj)+ ". ";;
 							oneStartEntry = true;
							showLastUpdateSettings = true;

						}
					} else if (!BasicUtils.isEmpty(oldObj.getRsVentType()) && !oldObj.getRsVentType().equalsIgnoreCase(obj.getRsVentType())){

						respSupport += "Respiratory support was changed to ";
						respSupport += respSupportStrOnlyType(obj);
						Timestamp dateTimeObj = new Timestamp(obj.getCreationtime().getTime()+offset);
						respSupport += " at " + onlyTimesdf.format(dateTimeObj.getTime()) ;
						if(!BasicUtils.isEmpty(obj.getReason()))  
							respSupport += " in view of "+obj.getReason();
					    respSupport +=  ". ";;

						oneStartEntry = true;
						showLastUpdateSettings = true;

					}else if (isDifferentRespObj(oldObj,obj)){

						 Timestamp dateTimeObj = new Timestamp(obj.getCreationtime().getTime()+offset);
						 respSupport += changedParameterRespObj(obj,oldObj) + " at " + onlyTimesdf.format(dateTimeObj.getTime());
						 if(!BasicUtils.isEmpty(obj.getReason()))  
							 respSupport += " in view of "+obj.getReason().trim();
						 //respSupport += ", Present settings are "+ respSupportStr(obj);
						 respSupport +=  ". ";;
						 oneStartEntry = true;
						 showLastUpdateSettings = true;

					}
					oldObj = obj;
				}
			}

			if(showLastUpdateSettings )  respSupport += " Last updated settings were "+ respSupportStr(oldObj)+". ";

			int seizures = 0;
			// nursing event details
			List<NursingEpisode> listNursingEpisode = (List<NursingEpisode>) inicuDoa
					.getListFromMappedObjQuery(HqlSqlQueryConstants.getNursingEpisodeList(uhid, yesObj,todayObj));
			if (!BasicUtils.isEmpty(listNursingEpisode)) {
				int apnea = 0;
				int bradycardia = 0;
				int disaturation = 0;

				int apneaRecoverySpo = 0;
				int disaturationRecoverySpo = 0;
				int seizuresRecoverySpo = 0;
				int apneaRecoveryPhy = 0;
				int apneaRecoveryPpv = 0;

				int disaturationRecoveryPhy = 0;
				int disaturationRecoveryPpv = 0;
				int seizuresRecoveryPhy = 0;
				String nursingEpisodeStr = "";
				String recoverySpoStr = "";
				String recoveryPhyStr = "";

                String seizuresEpisodeStr = "";


				Iterator<NursingEpisode> itr = listNursingEpisode.iterator();
				while (itr.hasNext()) {
					NursingEpisode obj = itr.next();
					if (obj.getApnea() != null && obj.getApnea()) {
						apnea++;
						if(!BasicUtils.isEmpty(obj.getRecovery())) {
							if(obj.getRecovery().equalsIgnoreCase("Spontaneous") ) apneaRecoverySpo++;
							if(obj.getRecovery().equalsIgnoreCase("Physical stimulation") ) apneaRecoveryPhy++;
							if(obj.getRecovery().equalsIgnoreCase("PPV") ) apneaRecoveryPpv++;
                        }
					}

//					if (obj.getBradycardia() != null && obj.getBradycardia()) {
//						bradycardia++;
//					}

					if (obj.getDesaturation() != null && obj.getDesaturation()) {
						disaturation++;
						if(!BasicUtils.isEmpty(obj.getRecoveryDesaturation())) {
							if(obj.getRecoveryDesaturation().equalsIgnoreCase("Spontaneous") ) disaturationRecoverySpo++;
							if(obj.getRecoveryDesaturation().equalsIgnoreCase("Physical stimulation") ) disaturationRecoveryPhy++;
							if(obj.getRecoveryDesaturation().equalsIgnoreCase("PPV") ) disaturationRecoveryPpv++;

                        }
					}
					if(obj.getSeizures() != null && obj.getSeizures()) {
						seizures++;
//						if(!BasicUtils.isEmpty(obj.getRecovery())) {
//							if(obj.getRecovery().equalsIgnoreCase("Spontaneous") ) seizuresRecoverySpo++;
//							if(obj.getRecovery().equalsIgnoreCase("Physical stimulation") ) seizuresRecoveryPhy++;
//						}
					}
				}


				if(apnea > 0) {
					if(nursingEpisodeStr.length()>1)  nursingEpisodeStr+=", ";
					if (apnea == 1) {
						nursingEpisodeStr += "1 apnea";
					} else {
						nursingEpisodeStr += apnea + " apneas ";
					}
				}

				if(disaturation > 0) {
					if(nursingEpisodeStr.length()>1)  nursingEpisodeStr+=", ";
					if (disaturation == 1) {
						nursingEpisodeStr += "1 desaturation ";
					} else {
						nursingEpisodeStr += disaturation + " desaturations ";
					}
				}

				nursingEpisodeStr = formatString(nursingEpisodeStr);

				// if only 1 event in total
				if(apnea+disaturation==1) {
				    nursingEpisodeStr+=" event was recorded ";

                    if(apneaRecoverySpo+disaturationRecoverySpo == 1){
                        nursingEpisodeStr += "and was recovered spontaneously";
                    }else if(disaturationRecoveryPhy+apneaRecoveryPhy == 1){
                        nursingEpisodeStr += "and required physical stimulation";
                    }else if(disaturationRecoveryPpv+apneaRecoveryPpv == 1){
                        nursingEpisodeStr += "and required PPV";
                    }

                    nursingEpisodeStr+=".";

				} else if(apnea+disaturation > 1){ // if more than 1 event in total

					
				
                

                    // Recovery code for more than 1 events start
                    
                    if((apneaRecoverySpo > 0 || disaturationRecoverySpo > 0) && disaturationRecoveryPhy == 0 && apneaRecoveryPhy == 0 && disaturationRecoveryPpv == 0 && apneaRecoveryPpv == 0) {
                        nursingEpisodeStr += " events were recorded and recovered spontaneously.";
                    }
                    else if((apneaRecoveryPhy > 0 || disaturationRecoveryPhy > 0) && apneaRecoverySpo == 0 && disaturationRecoverySpo == 0 && disaturationRecoveryPpv == 0 && apneaRecoveryPpv == 0) {
                        nursingEpisodeStr += " events were recorded and required physical stimulation.";
                    }else if((disaturationRecoveryPpv > 0 || apneaRecoveryPpv > 0) && apneaRecoverySpo == 0 && disaturationRecoverySpo == 0 && disaturationRecoveryPhy == 0 && apneaRecoveryPhy == 0) {
                        nursingEpisodeStr += " events were recorded and required PPV.";
                    }else {
                    	
    					nursingEpisodeStr += " events were recorded. ";
                    	
                    	if((apneaRecoverySpo > 0) && apneaRecoveryPhy == 0 && apneaRecoveryPpv == 0) {
                    		if(apnea > 1)
                    			nursingEpisodeStr += "All apneas recovered spontaneously .";
                    		else
                    			nursingEpisodeStr += "Apnea recovered spontaneously. ";
                        }
                        else if((apneaRecoveryPhy > 0) && apneaRecoverySpo == 0 && apneaRecoveryPpv == 0) {
                    		if(apnea > 1)
                    			nursingEpisodeStr += "All apneas required physical stimulation. ";
                    		else
                    			nursingEpisodeStr += "Apnea required physical stimulation. ";
                        }else if((apneaRecoveryPpv > 0) && apneaRecoverySpo == 0 && apneaRecoveryPhy == 0) {
                        	if(apnea > 1)
                            	nursingEpisodeStr += "All apneas required PPV. ";
                    		else
                    			nursingEpisodeStr += "Apnea required PPV. ";
                        }else if(apneaRecoverySpo > 0 || apneaRecoveryPhy > 0 || apneaRecoveryPpv > 0){
                        	String recovStr = "";
                        	
                        	
                        	if(apneaRecoverySpo > 0) {
                        		if(disaturation == 0) {
                        			if(apneaRecoverySpo > 1)
	                            		recovStr = apneaRecoverySpo + " recovered spontaneously";
	                        		else
	                        			recovStr = apneaRecoverySpo + " recovered spontaneously";
                        		}else {
	                        		if(apneaRecoverySpo > 1)
	                            		recovStr = apneaRecoverySpo + " apneas recovered spontaneously";
	                        		else
	                        			recovStr = apneaRecoverySpo + " apnea recovered spontaneously";
                        		}
                        	}
                        	if(apneaRecoveryPhy > 0) {
                        		if(!recovStr.equalsIgnoreCase("") && apneaRecoveryPpv == 0) {
                        			recovStr += " and rest required physical stimulation";
                        		}else {
	                        		if(disaturation == 0) {
	                        			if(apneaRecoveryPhy > 1) {
	                        		         
			                        		if(recovStr.equalsIgnoreCase(""))
			                        			recovStr = apneaRecoveryPhy + " required physical stimulation";
			                        		else
			                        			recovStr += ", " + apneaRecoveryPhy + " required physical stimulation";
		                        		}else {
		                        			if(recovStr.equalsIgnoreCase(""))
			                        			recovStr = apneaRecoveryPhy + " required physical stimulation";
			                        		else
			                        			recovStr += ", " + apneaRecoveryPhy + " required physical stimulation";
		                        		}
	                        		
	                        		}else {
		                        		if(apneaRecoveryPhy > 1) {
		         
			                        		if(recovStr.equalsIgnoreCase(""))
			                        			recovStr = apneaRecoveryPhy + " apneas required physical stimulation";
			                        		else
			                        			recovStr += ", " + apneaRecoveryPhy + " apneas required physical stimulation";
		                        		}else {
		                        			if(recovStr.equalsIgnoreCase(""))
			                        			recovStr = apneaRecoveryPhy + " apnea required physical stimulation";
			                        		else
			                        			recovStr += ", " + apneaRecoveryPhy + " apnea required physical stimulation";
		                        		}
	                        		}
                        		}
                        	}
                        	if(apneaRecoveryPpv > 0) {
                        		if(!recovStr.equalsIgnoreCase("")) {
                        			recovStr += " and rest required PPV";
                        		}
                        		if(disaturation == 0) {
                        			if(apneaRecoveryPpv > 1) {
                        				
		                        		if(recovStr.equalsIgnoreCase(""))
		                        			recovStr = apneaRecoveryPpv + " required PPV";
		                        		else
		                        			recovStr += ", " + apneaRecoveryPpv + " required PPV";
	                        		}else {
	                        			if(recovStr.equalsIgnoreCase(""))
		                        			recovStr = apneaRecoveryPpv + " required PPV";
		                        		else
		                        			recovStr += ", " + apneaRecoveryPpv + " required PPV";
	                        		}
                        		}else {
	                        		if(apneaRecoveryPpv > 1) {
	
		                        		if(recovStr.equalsIgnoreCase(""))
		                        			recovStr = apneaRecoveryPpv + " apneas required PPV";
		                        		else
		                        			recovStr += ", " + apneaRecoveryPpv + " apneas required PPV";
	                        		}else {
	                        			if(recovStr.equalsIgnoreCase(""))
		                        			recovStr = apneaRecoveryPpv + " apnea required PPV";
		                        		else
		                        			recovStr += ", " + apneaRecoveryPpv + " apnea required PPV";
	                        		}
                        		}
                        	}
                        	nursingEpisodeStr += recovStr + ". ";
                        }
                    	
                    	if((disaturationRecoverySpo > 0) && disaturationRecoveryPhy == 0 && disaturationRecoveryPpv == 0) {
                    		if(apnea > 1)
                    			nursingEpisodeStr += "All desaturations recovered spontaneously.";
                    		else
                    			nursingEpisodeStr += "Desaturations recovered spontaneously.";
                        }
                        else if((disaturationRecoveryPhy > 0) && disaturationRecoverySpo == 0 && disaturationRecoveryPpv == 0) {
                    		if(apnea > 1)
                    			nursingEpisodeStr += "All desaturations required physical stimulation.";
                    		else
                    			nursingEpisodeStr += "Desaturations required physical stimulation.";
                        }else if((disaturationRecoveryPpv > 0) && disaturationRecoverySpo == 0 && disaturationRecoveryPhy == 0) {
                        	if(apnea > 1)
                            	nursingEpisodeStr += "All desaturations required PPV.";
                    		else
                    			nursingEpisodeStr += "Desaturations required PPV.";
                        }else {
                        	String recovStr = "";
                        	if(disaturationRecoverySpo > 0) {
                        		if(apnea == 0) {
                        			if(disaturationRecoverySpo > 1)
                                		recovStr = disaturationRecoverySpo + " recovered spontaneously";
                            		else
                            			recovStr = disaturationRecoverySpo + " recovered spontaneously";
                        		}else {
	                        		if(disaturationRecoverySpo > 1)
	                            		recovStr = disaturationRecoverySpo + " desaturations recovered spontaneously";
	                        		else
	                        			recovStr = disaturationRecoverySpo + " desaturation recovered spontaneously";
                        		}
                        	}
                        	if(disaturationRecoveryPhy > 0) {
                        		if(!recovStr.equalsIgnoreCase("") && disaturationRecoveryPpv == 0) {
                        			recovStr += " and rest required physical stimulation";
                        		}else {
	                        		if(apnea == 0) {
	
		                        		if(disaturationRecoveryPhy > 1) {
		                        			if(recovStr.equalsIgnoreCase(""))
			                        			recovStr = disaturationRecoveryPhy + " required physical stimulation";
			                        		else
			                        			recovStr += ", " + disaturationRecoveryPhy + " required physical stimulation";
		                        		}else {
			                        		if(recovStr.equalsIgnoreCase(""))
			                        			recovStr = disaturationRecoveryPhy + " required physical stimulation";
			                        		else
			                        			recovStr += ", " + disaturationRecoveryPhy + " required physical stimulation";
		                        		}
	                        		}else {
	                        			if(disaturationRecoveryPhy > 1) {
	                            			if(recovStr.equalsIgnoreCase(""))
	    	                        			recovStr = disaturationRecoveryPhy + " desaturations required physical stimulation";
	    	                        		else
	    	                        			recovStr += ", " + disaturationRecoveryPhy + " desaturations required physical stimulation";
	                            		}else {
	    	                        		if(recovStr.equalsIgnoreCase(""))
	    	                        			recovStr = disaturationRecoveryPhy + " desaturation required physical stimulation";
	    	                        		else
	    	                        			recovStr += ", " + disaturationRecoveryPhy + " desaturation required physical stimulation";
	                            		}
	                        		}
                        		}
                        	}
                        	if(disaturationRecoveryPpv > 0) {
                        		if(!recovStr.equalsIgnoreCase("")) {
                        			recovStr += " and rest required PPV";
                        		}else {
	                        		if(apnea == 0) {
	                        			if(disaturationRecoveryPpv > 1) {
	                            			if(recovStr.equalsIgnoreCase(""))
	    	                        			recovStr = disaturationRecoveryPpv + " required PPV";
	    	                        		else
	    	                        			recovStr += ", " + disaturationRecoveryPpv + " required PPV";
	                            		}else {
	    	                        		if(recovStr.equalsIgnoreCase(""))
	    	                        			recovStr = disaturationRecoveryPpv + " required PPV";
	    	                        		else
	    	                        			recovStr += ", " + disaturationRecoveryPpv + " required PPV";
	                            		}
	                        		}else {
		                        		if(disaturationRecoveryPpv > 1) {
		                        			if(recovStr.equalsIgnoreCase(""))
			                        			recovStr = disaturationRecoveryPpv + " desaturations required PPV";
			                        		else
			                        			recovStr += ", " + disaturationRecoveryPpv + " desaturations required PPV";
		                        		}else {
			                        		if(recovStr.equalsIgnoreCase(""))
			                        			recovStr = disaturationRecoveryPpv + " desaturation required PPV";
			                        		else
			                        			recovStr += ", " + disaturationRecoveryPpv + " desaturation required PPV";
		                        		}
	                        		}
                        		}
                        	}
                        	nursingEpisodeStr += recovStr + ". ";
	                        
                        }
                     }
                }
	            vitalInfoStr += " " + nursingEpisodeStr;
  			}

            // append resp support after events
            if(!progressNoteFormatType.equalsIgnoreCase("Configurable")) {
                vitalInfoStr += " " + respSupport;
            }

            // phototherapy
			String currentPhototherapy  = "";
			String phototherapySql = "select obj from SaJaundice obj where uhid='" + uhid + "' and assessment_time >= '" + yesObj + "' and assessment_time <= '" + todayObj + "' order by assessment_time asc";
			List<SaJaundice> phototherapyList = inicuDoa.getListFromMappedObjQuery(phototherapySql);
			
			
			int firstJaundice = 1;
			boolean prevStartStatus = false;
			float latestTCB = -1;
			String latestTCBStr = "";
		
			
			if(!BasicUtils.isEmpty(phototherapyList)) {
				phototherapySql = "select obj from SaJaundice obj where uhid='" + uhid + "' and assessment_time <= '" + yesObj + "' order by assessment_time desc";
				List<SaJaundice> phototherapyListPast = inicuDoa.getListFromMappedObjQuery(phototherapySql);
				if(!BasicUtils.isEmpty(phototherapyListPast) && !BasicUtils.isEmpty(phototherapyListPast.get(0).getPhototherapyvalue()) && (phototherapyListPast.get(0).getPhototherapyvalue().equalsIgnoreCase("Start") || phototherapyListPast.get(0).getPhototherapyvalue().equalsIgnoreCase("Continue") || phototherapyListPast.get(0).getPhototherapyvalue().equalsIgnoreCase("Change"))) {
					prevStartStatus = true;
				}
			}
			for(SaJaundice jaund : phototherapyList) {
				if(!BasicUtils.isEmpty(jaund.getTbcvalue())) {
					latestTCB = jaund.getTbcvalue();
					if(!BasicUtils.isEmpty(jaund.getTcbortsb()) && jaund.getTcbortsb())
						latestTCBStr = "TCB value was " + latestTCB + " mg/dL at " + onlyTimesdf.format(new Timestamp(jaund.getAssessmentTime().getTime()+offset)) + ". ";
					else
						latestTCBStr = "TSB value was " + latestTCB + " mg/dL at " + onlyTimesdf.format(new Timestamp(jaund.getAssessmentTime().getTime()+offset)) + ". ";
				}
				if(!BasicUtils.isEmpty(jaund.getPhototherapyvalue())) {
					if(jaund.getPhototherapyvalue().equalsIgnoreCase("Start")) {
						if(!prevStartStatus) {
							currentPhototherapy += "For Jaundice baby was started on" ;
							if(!BasicUtils.isEmpty(jaund.getPhototherapyType())) 
								currentPhototherapy += " ("+jaund.getPhototherapyType()+")";
							if(!BasicUtils.isEmpty(jaund.getAssessmentTime())) 
								currentPhototherapy += " phototherapy at "+onlyTimesdf.format(new Timestamp(jaund.getAssessmentTime().getTime()+offset));
							if(!BasicUtils.isEmpty(jaund.getReason())) {
								currentPhototherapy += " in view of " + jaund.getReason();
							}
							prevStartStatus = true;
							currentPhototherapy += ". ";
						}else {
							currentPhototherapy += "Started on" ;
							if(jaund.getPhototherapyType()!=null) 
								currentPhototherapy += " ("+jaund.getPhototherapyType()+")";
							if(jaund.getAssessmentTime()!=null) 
								currentPhototherapy += " phototherapy at "+onlyTimesdf.format(new Timestamp(jaund.getAssessmentTime().getTime()+offset));
							if(!BasicUtils.isEmpty(jaund.getReason())) {
								currentPhototherapy += " in view of " + jaund.getReason();
							}
							prevStartStatus = true;
							currentPhototherapy += ". ";
						}
						
					}
					else if(jaund.getPhototherapyvalue().equalsIgnoreCase("Stop")) {
						if(!prevStartStatus) {
							currentPhototherapy += "Stopped at ";
							currentPhototherapy += onlyTimesdf.format(new Timestamp(jaund.getAssessmentTime().getTime()+offset));
							if(!BasicUtils.isEmpty(jaund.getReason())) {
								currentPhototherapy += " in view of " + jaund.getReason();
							}
							prevStartStatus = true;
							currentPhototherapy += ". ";
						}else if(currentPhototherapy.equalsIgnoreCase("") && prevStartStatus){
							currentPhototherapy += "For Jaundice baby was on phototherapy, stopped at ";
							currentPhototherapy += onlyTimesdf.format(new Timestamp(jaund.getAssessmentTime().getTime()+offset));
							if(!BasicUtils.isEmpty(jaund.getReason())) {
								currentPhototherapy += " in view of " + jaund.getReason();
							}
							prevStartStatus = true;
							currentPhototherapy += ". ";
						}else {
							currentPhototherapy += "Stopped at ";
							currentPhototherapy += onlyTimesdf.format(new Timestamp(jaund.getAssessmentTime().getTime()+offset));
							if(!BasicUtils.isEmpty(jaund.getReason())) {
								currentPhototherapy += " in view of " + jaund.getReason();
							}
							prevStartStatus = true;
							currentPhototherapy += ". ";
						}
					}
					else if(jaund.getPhototherapyvalue().equalsIgnoreCase("Continue") && !BasicUtils.isEmpty(jaund.getReason())) {
						currentPhototherapy += "Baby remained on phototherapy in view of " + jaund.getReason();
						if(jaund.getAssessmentTime()!=null) 
							currentPhototherapy += " at "+onlyTimesdf.format(new Timestamp(jaund.getAssessmentTime().getTime()+offset));
						prevStartStatus = true;
						currentPhototherapy += ". ";
					} else if(jaund.getPhototherapyvalue().equalsIgnoreCase("Change")) {
						if(currentPhototherapy.equalsIgnoreCase("") && prevStartStatus){
							currentPhototherapy += "For Jaundice baby remained on phototherapy. ";
							if(jaund.getAssessmentTime()!=null) 
								currentPhototherapy += "At "+onlyTimesdf.format(new Timestamp(jaund.getAssessmentTime().getTime()+offset));
							currentPhototherapy += " was shifted on "+jaund.getPhototherapyType()+" phototherapy";
							if(jaund.getReason()!=null) 
								currentPhototherapy += " in view of "+jaund.getReason();
							prevStartStatus = true;
							currentPhototherapy += ". ";
						}else {
							if(jaund.getAssessmentTime()!=null) 
								currentPhototherapy += "At "+onlyTimesdf.format(new Timestamp(jaund.getAssessmentTime().getTime()+offset));
							currentPhototherapy += " was shifted on "+jaund.getPhototherapyType()+" phototherapy";
							if(jaund.getReason()!=null) 
								currentPhototherapy += " in view of "+jaund.getReason();
							prevStartStatus = true;
							currentPhototherapy += ". ";
						}
					}
				}
			}
			
			if(!BasicUtils.isEmpty(currentPhototherapy)) {
 				vitalInfoStr += "\n"+currentPhototherapy + latestTCBStr;
			}else if(!BasicUtils.isEmpty(phototherapyList) && prevStartStatus) {
				vitalInfoStr += "\n"+ "Baby remained on phototherapy. " + latestTCBStr;
			}

			if(seizures > 0) {
				if (seizures == 1) {
					vitalInfoStr += " 1 seizure event was recorded.";
				} else {
					vitalInfoStr += " "+seizures + " seizure events were recorded.";
				}
			}

			returnObj.setVital(vitalInfoStr);


			//  get Previous Day Screening Notes
			String screeningText = getDailyProgressNotesScreening(uhid, yesObj, todayObj);
			returnObj.setScreening(screeningText);

			try {
				// get Current Day Screening Notes
				returnObj = getScreeningNotes(returnObj, uhid, tomorrowObj, todayObj);
			}catch(Exception  e){
				System.out.println(e.getMessage());
			}

			// blood product here
			String bloodProductText = getDailyProgressNotesBloodProduct(uhid, yesObj, todayObj);
			returnObj.setBloodProduct(bloodProductText);

			// lab_details here
			String LabStr = "";
			List<InvestigationOrdered> listInvSampleDetails = (List<InvestigationOrdered>) inicuDoa
					.getListFromMappedObjQuery(HqlSqlQueryConstants.getInvestSampleSentList(uhid, yesObj, todayObj));
			if (!BasicUtils.isEmpty(listInvSampleDetails)) {
				String sentStr = "";
				for (InvestigationOrdered obj : listInvSampleDetails) {
					sentStr += ", " + obj.getTestname();
				}
				sentStr = sentStr.substring(2);
				if (sentStr.contains(",")) {
					LabStr += "The Lab Orders of " + sentStr + " were ordered and executed." + htmlNextLine;
				} else {
					LabStr += "The Lab Order of " + sentStr + " was ordered and executed." + htmlNextLine;
				}
			}

			List<InvestigationOrdered> listInvOrderedDetails = (List<InvestigationOrdered>) inicuDoa
					.getListFromMappedObjQuery(HqlSqlQueryConstants.getInvestigationList(uhid)
							+ " and senttolab_time is null  and investigationorder_time >= '" + yesObj
							+ "' and investigationorder_time < '" + todayObj + "'");
			if (!BasicUtils.isEmpty(listInvOrderedDetails)) {
				String pendingStr = "";
				for (InvestigationOrdered obj : listInvOrderedDetails) {
					pendingStr += ", " + obj.getTestname();
				}
				pendingStr = pendingStr.substring(2);
				if (pendingStr.contains(",")) {
					LabStr += pendingStr + " investigations are ordered." + htmlNextLine;
				} else {
					LabStr += pendingStr + " investigation is ordered." + htmlNextLine;
				}
			}

			List<InvestigationOrdered> listInvReportDetails = (List<InvestigationOrdered>) inicuDoa
					.getListFromMappedObjQuery(HqlSqlQueryConstants.getInvestReportList(uhid, yesObj, todayObj));
			if (!BasicUtils.isEmpty(listInvReportDetails)) {
				String reportStr = "";
				for (InvestigationOrdered obj : listInvReportDetails) {
					reportStr += ", " + obj.getTestname();
				}
				reportStr = reportStr.substring(2);
				if (reportStr.contains(",")) {
					LabStr += "Reports of " + reportStr + " are received.";
				} else {
					LabStr += "Report of " + reportStr + " is received.";
				}
			}
			returnObj.setLab_details(LabStr);


			String morningInvestigations = "";
			String morningPlan = "";
			Timestamp morningTime = null;

			List<VwDoctornotesListFinal> assessmentList = inicuDoa.getListFromMappedObjQuery(
					HqlSqlQueryConstants.getDoctorAssessmentNotes(uhid, todayObj, tomorrowObj));
			HashMap<String, List<EventDetailsPOJO>> testsListMap = new HashMap<String, List<EventDetailsPOJO>>();
			HashMap<String, String> investigationsOrderMap = new HashMap<String, String>();

			String prevNote = "";
			Timestamp prevTime = new Timestamp((new java.util.Date().getTime()));
			Timestamp startTime = new Timestamp(todayObj.getTime());
			Map planMap = new HashMap();
			HashSet<Timestamp> timeList = new HashSet<>();

			int index = -1;
			for (VwDoctornotesListFinal item : assessmentList) {
				Timestamp offsetDate = item.getCreationtime();
				offsetDate = new Timestamp(offsetDate.getTime() + offset);
				timeList.add(offsetDate);

				String formatedDate = sdf.format(offsetDate);
				int currentHour = offsetDate.getHours();
				String note = BasicConstants.hrFormatTo12hrFormatMapping.get(offsetDate.getHours());

				if (!BasicUtils.isEmpty(formatedDate)) {
					note = formatedDate.split(" ")[0] + " " + note;
				}

				List categoryList = new ArrayList<EventDetailsPOJO>();
				if (testsListMap.get(note) != null) {
					categoryList = testsListMap.get(note);
				} else {
					categoryList = new ArrayList<EventDetailsPOJO>();
				}

				if (BasicUtils.isEmpty(prevNote)) {
					prevNote = note;
					prevTime = new Timestamp(offsetDate.getTime());
					startTime = prevTime;
				}

				boolean isProgressNoteAvailable = false;
				Long assId = item.getAssesmentid();
				boolean inActive = false;
				inActive = checkInActiveAsssessment(item.getSaEvent(),assId);
				if (!inActive) {

					if (currentHour >= fromTime && currentHour < 14) {
						if (BasicUtils.isEmpty(returnObj.getMorningNote().getMorningNoteTime()) || returnObj.getMorningNote().getMorningNoteTime() == 0) {
							returnObj.getMorningNote().setMorningNoteTime(currentHour);
							morningTime = offsetDate;
						}
						if (!BasicUtils.isEmpty(returnObj.getMorningNote().getMorningNoteTime()) && returnObj.getMorningNote().getMorningNoteTime() > currentHour) {
							returnObj.getMorningNote().setMorningNoteTime(currentHour);
							morningTime = offsetDate;

						}
					}

					if(!BasicUtils.isEmpty(returnObj.getMorningNote()) &&  !BasicUtils.isEmpty(returnObj.getMorningNote().getMorningNoteTime()) && returnObj.getMorningNote().getMorningNoteTime()==currentHour) {

						System.out.println("in morning loop ------------");
						if (!BasicUtils.isEmpty(item.getProgressnotes()) && item.getProgressnotes().indexOf("has been done.") != -1) {
							int eend = item.getProgressnotes().indexOf("has been done.");
							int estart = item.getProgressnotes().lastIndexOf(".", eend);
							String einvestigation = item.getProgressnotes().substring(estart + 1, eend);
							item.setProgressnotes(item.getProgressnotes().replace(einvestigation + "has been done.", ""));

							if (einvestigation.indexOf("Investigation ordered is") !=-1 || einvestigation.indexOf("Investigation ordered are") !=-1){
								einvestigation = einvestigation.replace("Investigation ordered is","");
								einvestigation = einvestigation.replace("Investigation ordered are","");
								if(morningInvestigations.length()>0 && einvestigation.length()>0) morningInvestigations +=", ";
							}
							morningInvestigations += einvestigation;
						}


						if (!BasicUtils.isEmpty(item.getProgressnotes()) && item.getProgressnotes().indexOf("have been done.") != -1) {
							int eend = item.getProgressnotes().indexOf("have been done.");
							int estart = item.getProgressnotes().lastIndexOf(".", eend);
							String einvestigation = item.getProgressnotes().substring(estart + 1, eend);
							item.setProgressnotes(item.getProgressnotes().replace(einvestigation + "have been done.", ""));
							if (einvestigation.indexOf("Investigation ordered is") !=-1 || einvestigation.indexOf("Investigation ordered are") !=-1){
								einvestigation = einvestigation.replace("Investigation ordered is","");
								einvestigation = einvestigation.replace("Investigation ordered are","");
								if(morningInvestigations.length()>0 && einvestigation.length()>0) morningInvestigations +=", ";
							}
							morningInvestigations += einvestigation;
						}

						if (!BasicUtils.isEmpty(item.getProgressnotes()) && item.getProgressnotes().indexOf("Investigation ordered") != -1) {
							int estart = item.getProgressnotes().indexOf("Investigation ordered");
							int eend = item.getProgressnotes().indexOf(".", estart);
							String einvestigation = item.getProgressnotes().substring(estart, eend);

							item.setProgressnotes(item.getProgressnotes().replace(einvestigation + ".", ""));
							if (einvestigation.indexOf("Investigation ordered is") !=-1 || einvestigation.indexOf("Investigation ordered are") !=-1){
								einvestigation = einvestigation.replace("Investigation ordered is","");
								einvestigation = einvestigation.replace("Investigation ordered are","");
								if(morningInvestigations.length()>0 && einvestigation.length()>0) morningInvestigations +=", ";
							}
							morningInvestigations += einvestigation;
						}

						if (!BasicUtils.isEmpty(item.getProgressnotes()) && item.getProgressnotes().indexOf("Plan is to reassess") != -1) {
							int estart = item.getProgressnotes().indexOf("Plan is to reassess");
							int eend = item.getProgressnotes().indexOf(".", estart);
							String plan = item.getProgressnotes().substring(estart, eend);

							item.setProgressnotes(item.getProgressnotes().replace(plan + ".", ""));

							plan = plan.replace("Plan is to", "For " + item.getSaEvent());
							plan = plan.replace("the baby ", "");

							if (morningPlan.length() > 1)
								morningPlan += "\n";
							morningPlan += plan;
						}

						else if (!BasicUtils.isEmpty(item.getPlan()) && !BasicUtils.isEmpty(item.getProgressnotes()) && item.getProgressnotes().indexOf(item.getPlan()) != -1) {
							item.setProgressnotes(item.getProgressnotes().replace("Plan - " + item.getPlan(), ""));
							item.setProgressnotes(item.getProgressnotes().replace(item.getPlan(), ""));

							if (morningPlan.length() > 1) morningPlan += "\n";
							morningPlan += item.getPlan();
						}
					}
					else {

						String investigations = "";
						String plan = "";

						if(BasicUtils.isEmpty(item.getProgressnotes())){
							System.out.println("[:: Morning Note is Empty ::]");
						}

						if (!BasicUtils.isEmpty(item.getProgressnotes()) &&  item.getProgressnotes().indexOf("has been done.") != -1) {
							int eend = item.getProgressnotes().indexOf("has been done.");
							int estart = item.getProgressnotes().lastIndexOf(".", eend);
							String einvestigation = item.getProgressnotes().substring(estart + 1, eend);
							item.setProgressnotes(item.getProgressnotes().replace(einvestigation + "has been done.", ""));
							investigations += einvestigation;
						}

						if (!BasicUtils.isEmpty(item.getProgressnotes()) &&  item.getProgressnotes().indexOf("have been done.") != -1) {
							int eend = item.getProgressnotes().indexOf("have been done.");
							int estart = item.getProgressnotes().lastIndexOf(".", eend);
							String einvestigation = item.getProgressnotes().substring(estart + 1, eend);

							item.setProgressnotes(item.getProgressnotes().replace(einvestigation + "have been done.", ""));

							investigations += einvestigation;
						}

						if (!BasicUtils.isEmpty(item.getProgressnotes()) &&  item.getProgressnotes().indexOf("Investigation ordered") != -1) {
							int estart = item.getProgressnotes().indexOf("Investigation ordered");
							int eend = item.getProgressnotes().indexOf(".", estart);
							String einvestigation = item.getProgressnotes().substring(estart, eend);
							item.setProgressnotes(item.getProgressnotes().replace(einvestigation + ".", ""));
							investigations += einvestigation;
						}

						if (!BasicUtils.isEmpty(item.getProgressnotes()) &&  item.getProgressnotes().indexOf("Plan is to reassess") != -1) {
							int estart = item.getProgressnotes().indexOf("Plan is to reassess");
							int eend = item.getProgressnotes().indexOf(".", estart);
							String planStr1 = item.getProgressnotes().substring(estart, eend);

							item.setProgressnotes(item.getProgressnotes().replace(planStr1 + ".", ""));
							planStr1 = planStr1.replace("Plan is to", "For " + item.getSaEvent());
							planStr1 = planStr1.replace("the baby ", "");
							if (plan.length() > 1)
								plan += "\n";
							plan += planStr1;
						}


						else if (!BasicUtils.isEmpty(item.getPlan()) && !BasicUtils.isEmpty(item.getProgressnotes()) && item.getProgressnotes().indexOf(item.getPlan()) != -1) {
							item.setProgressnotes(item.getProgressnotes().replace("Plan - " + item.getPlan(), ""));
							item.setProgressnotes(item.getProgressnotes().replace(item.getPlan(), ""));
							if (plan.length() > 1) plan += "\n";
							plan += item.getPlan();
						}


						if(investigations.length()>1) {

							if(investigations.indexOf("Investigation ordered are") != -1 || investigations.indexOf("Investigation ordered is") !=-1 || investigations.indexOf("and")!=-1){
								investigations = investigations.replace("Investigation ordered are","");
								investigations = investigations.replace("Investigation ordered is","");
								investigations = investigations.replaceAll("and",",");
							}

							if(investigationsOrderMap.get(note)!=null){
								String investStr = investigationsOrderMap.get(note);
								if(investStr.length()>1) investStr+= ",";
								investStr +=investigations;
								investigationsOrderMap.put(note,investStr);
							}else{
								investigationsOrderMap.put(note,investigations);
							}

//							if(plan.length()>1) plan+= "\n";
//							if(investigations.indexOf("Investigation ordered are")!=-1) {
//								investigations.replace("Investigation ordered are", "");
//							}
//							plan+="Investigation ordered: "+investigations;
						}

						if(plan.length()>1) {
							if (planMap.get(note) != null) {
								planMap.put(note,planMap.get(note)+"\n"+plan);
							} else{
								planMap.put(note,plan);
							}
						}
					}
					System.out.println("Current Hour :"+currentHour);
					if (item.getSaEvent().equalsIgnoreCase("Stable Notes") && BasicUtils.isEmpty(returnObj.getGeneralStableNote())
							&& (currentHour >= fromTime && currentHour < 14)) {
						//  split the stable notes exclude vitals
						String newStr = item.getProgressnotes();
						// first exclude the vital parameters
						int vitalStartIndex = newStr.indexOf("Vital parameters are");
						int vitalEndIndex = newStr.indexOf(". ");

						if(vitalStartIndex!=-1) {
							System.out.println(item.getProgressnotes().substring(0, vitalStartIndex ));
							System.out.println(item.getProgressnotes()
									.substring(vitalEndIndex + 1, item.getProgressnotes().length() ));

							newStr = item.getProgressnotes().substring(0, vitalStartIndex ) + item.getProgressnotes()
											.substring(vitalEndIndex + 1, item.getProgressnotes().length() );
						}

						// now remove the No Significant Findings
						int startIndex = newStr.indexOf("No significant finding");
						int endIndex = newStr.indexOf(".",startIndex);

						String condition = newStr;

						if(startIndex!=-1) {
							System.out.println(newStr.substring(0, startIndex));
							System.out.println(newStr.substring(endIndex + 1, newStr.length()));

							condition = newStr.substring(0, startIndex - 1) + newStr
									.substring(endIndex + 1, newStr.length() - 1);
						}

						System.out.println("Final Condition is :"+condition);
						returnObj.setGeneralStableNote(condition);
					}
					else if (item.getSaEvent().equalsIgnoreCase("Stable Notes") && !BasicUtils.isEmpty(returnObj.getGeneralStableNote())
							&& (currentHour >= fromTime && currentHour < 14)) {
						String notes = item.getProgressnotes();

						//  split the stable notes exclude vitals
						String newStr = item.getProgressnotes();
						// first exclude the vital parameters
						int vitalStartIndex = newStr.indexOf("Vital parameters are");
						int vitalEndIndex = newStr.indexOf(". ");

						if(vitalStartIndex!=-1) {
							System.out.println(item.getProgressnotes().substring(0, vitalStartIndex ));
							System.out.println(item.getProgressnotes()
									.substring(vitalEndIndex + 1, item.getProgressnotes().length()));

							newStr = item.getProgressnotes().substring(0, vitalStartIndex ) + item.getProgressnotes()
									.substring(vitalEndIndex + 1, item.getProgressnotes().length() );
						}

						// now remove the No Significant Findings
						int startIndex = newStr.indexOf("No significant finding");
						int endIndex = newStr.indexOf(".",startIndex);

						String condition2 = newStr;

						if(startIndex!=-1) {
							System.out.println(newStr.substring(0, startIndex ));
							System.out.println(newStr.substring(endIndex + 1, newStr.length() ));

							condition2 = newStr.substring(0, startIndex) + newStr
									.substring(endIndex + 1, newStr.length() );
						}

						System.out.println("Final Condition is :"+condition2);
						returnObj.setGeneralStableNote(condition2);

//						if(notes!= null && notes.indexOf("Baby's condition is")!= -1){
//							int start = notes.indexOf("Baby's condition is");
//							int end = notes.indexOf(".", start);
//							String condition = notes.substring(start, end);
//
//							if(returnObj.getGeneralStableNote().indexOf("Baby's condition is")==-1) {
//								returnObj.setGeneralStableNote(condition+". "+returnObj.getGeneralStableNote() );
//							}
//						}
//
//						if(notes!= null && notes.indexOf("General Physical Examination is normal.")!= -1){
//
//							if(returnObj.getGeneralStableNote().indexOf("General Physical Examination is normal.")==-1){
//								returnObj.setGeneralStableNote(returnObj.getGeneralStableNote()+" General Physical Examination is normal. ");
//							}
//						}
					}
					else if (item.getSaEvent().equalsIgnoreCase("Stable Notes")) {
						EventDetailsPOJO event = new EventDetailsPOJO();
						event.setEventName("");

						//  split the stable notes exclude vitals
						String newStr = item.getProgressnotes();
						// first exclude the vital parameters
						int vitalStartIndex = newStr.indexOf("Vital parameters are");
						int vitalEndIndex = newStr.indexOf(". ");

						if(vitalStartIndex!=-1) {
							System.out.println(item.getProgressnotes().substring(0, vitalStartIndex ));
							System.out.println(item.getProgressnotes()
									.substring(vitalEndIndex + 1, item.getProgressnotes().length()));

							newStr = item.getProgressnotes().substring(0, vitalStartIndex ) + item.getProgressnotes()
									.substring(vitalEndIndex + 1, item.getProgressnotes().length() );
						}

						// now remove the No Significant Findings
						int startIndex = newStr.indexOf("No significant finding");
						int endIndex = newStr.indexOf(".",startIndex);

						String condition = newStr;

						if(startIndex!=-1) {
							System.out.println(newStr.substring(0, startIndex ));
							System.out.println(newStr.substring(endIndex + 1, newStr.length() ));

							condition = newStr.substring(0, startIndex) + newStr
									.substring(endIndex + 1, newStr.length() );
						}

						System.out.println("Final Condition is :"+condition);

						event.setProgressNote(condition);
						categoryList.add(event);
						testsListMap.put(note, categoryList);
						isProgressNoteAvailable = true;
					}

					if ((item.getSaEvent().equalsIgnoreCase("RDS") && BasicUtils.isEmpty(returnObj.getMorningNote().getRdspresentNote()) && (currentHour >= fromTime && currentHour < 14 && returnObj.getMorningNote().getMorningNoteTime()==currentHour)) || (returnObj.getMorningNote().getMorningNoteTime()==currentHour && (currentHour >= fromTime && currentHour < 14) && item.getSaEvent().equalsIgnoreCase("RDS"))) {
						if(!BasicUtils.isEmpty(returnObj.getMorningNote()) && !BasicUtils.isEmpty(returnObj.getMorningNote().getRdspresentNote())) {
							String noteDummy = returnObj.getMorningNote().getRdspresentNote();
							noteDummy = noteDummy + "\n" + item.getProgressnotes();
							returnObj.getMorningNote().setRdspresentNote(noteDummy);

						}else {
							returnObj.getMorningNote().setRdspresentNote(item.getProgressnotes());
						}

					}
					else if (item.getSaEvent().equalsIgnoreCase("RDS")) {
						EventDetailsPOJO event = new EventDetailsPOJO();
						event.setEventName(item.getSaEvent());
						event.setProgressNote(item.getProgressnotes());
						categoryList.add(event);
						testsListMap.put(note, categoryList);
						isProgressNoteAvailable = true;
					}
					if ((item.getSaEvent().equalsIgnoreCase("Apnea") && BasicUtils.isEmpty(returnObj.getMorningNote().getApneapresentNote()) && (currentHour >= fromTime && currentHour < 14 && returnObj.getMorningNote().getMorningNoteTime()==currentHour)) || (returnObj.getMorningNote().getMorningNoteTime()==currentHour && (currentHour >= fromTime && currentHour < 14) && item.getSaEvent().equalsIgnoreCase("Apnea"))) {
						if(!BasicUtils.isEmpty(returnObj.getMorningNote()) && !BasicUtils.isEmpty(returnObj.getMorningNote().getApneapresentNote())) {
							String noteDummy = returnObj.getMorningNote().getApneapresentNote();
							noteDummy = noteDummy + "\n" + item.getProgressnotes();
							returnObj.getMorningNote().setApneapresentNote(noteDummy);

						}else {
							returnObj.getMorningNote().setApneapresentNote(item.getProgressnotes());
						}
					} else if (item.getSaEvent().equalsIgnoreCase("Apnea")) {
						EventDetailsPOJO event = new EventDetailsPOJO();
						event.setEventName(item.getSaEvent());
						event.setProgressNote(item.getProgressnotes());
						categoryList.add(event);
						testsListMap.put(note, categoryList);
						isProgressNoteAvailable = true;
					}
					if ((item.getSaEvent().equalsIgnoreCase("PPHN") && BasicUtils.isEmpty(returnObj.getMorningNote().getPphnpresentNote()) && (currentHour >= fromTime && currentHour < 14 && returnObj.getMorningNote().getMorningNoteTime()==currentHour)) || (returnObj.getMorningNote().getMorningNoteTime()==currentHour && (currentHour >= fromTime && currentHour < 14) && item.getSaEvent().equalsIgnoreCase("PPHN"))) {
						if(!BasicUtils.isEmpty(returnObj.getMorningNote()) && !BasicUtils.isEmpty(returnObj.getMorningNote().getPphnpresentNote())) {
							String noteDummy = returnObj.getMorningNote().getPphnpresentNote();
							noteDummy = noteDummy + "\n" + item.getProgressnotes();
							returnObj.getMorningNote().setPphnpresentNote(noteDummy);

						}else {
							returnObj.getMorningNote().setPphnpresentNote(item.getProgressnotes());
						}
					} else if (item.getSaEvent().equalsIgnoreCase("PPHN")) {
						EventDetailsPOJO event = new EventDetailsPOJO();
						event.setEventName(item.getSaEvent());
						event.setProgressNote(item.getProgressnotes());
						categoryList.add(event);
						testsListMap.put(note, categoryList);
						isProgressNoteAvailable = true;
					}
					if ((item.getSaEvent().equalsIgnoreCase("Pneumothorax") && BasicUtils.isEmpty(returnObj.getMorningNote().getPneumothoraxpresentNote()) && (currentHour >= fromTime && currentHour < 14 && returnObj.getMorningNote().getMorningNoteTime()==currentHour)) || (returnObj.getMorningNote().getMorningNoteTime()==currentHour && (currentHour >= fromTime && currentHour < 14) && item.getSaEvent().equalsIgnoreCase("Pneumothorax"))) {
						if(!BasicUtils.isEmpty(returnObj.getMorningNote()) && !BasicUtils.isEmpty(returnObj.getMorningNote().getPneumothoraxpresentNote())) {
							String noteDummy = returnObj.getMorningNote().getPneumothoraxpresentNote();
							noteDummy = noteDummy + "\n" + item.getProgressnotes();
							returnObj.getMorningNote().setPneumothoraxpresentNote(noteDummy);

						}else {
							returnObj.getMorningNote().setPneumothoraxpresentNote(item.getProgressnotes());
						}
					} else if (item.getSaEvent().equalsIgnoreCase("Pneumothorax")) {
						EventDetailsPOJO event = new EventDetailsPOJO();
						event.setEventName(item.getSaEvent());
						event.setProgressNote(item.getProgressnotes());
						categoryList.add(event);
						testsListMap.put(note, categoryList);
						isProgressNoteAvailable = true;
					}
					if ((item.getSaEvent().equalsIgnoreCase("Sepsis") && BasicUtils.isEmpty(returnObj.getMorningNote().getSepsispresentNote()) && (currentHour >= fromTime && currentHour < 14 && returnObj.getMorningNote().getMorningNoteTime()==currentHour)) || (returnObj.getMorningNote().getMorningNoteTime()==currentHour && (currentHour >= fromTime && currentHour < 14) && item.getSaEvent().equalsIgnoreCase("Sepsis"))) {
						if(!BasicUtils.isEmpty(returnObj.getMorningNote()) && !BasicUtils.isEmpty(returnObj.getMorningNote().getSepsispresentNote())) {
							String noteDummy = returnObj.getMorningNote().getSepsispresentNote();
							noteDummy = noteDummy + "\n" + item.getProgressnotes();
							returnObj.getMorningNote().setSepsispresentNote(noteDummy);

						}else {
							returnObj.getMorningNote().setSepsispresentNote(item.getProgressnotes());
						}
					} else if (item.getSaEvent().equalsIgnoreCase("Sepsis")) {
						EventDetailsPOJO event = new EventDetailsPOJO();
						event.setEventName(item.getSaEvent());
						event.setProgressNote(item.getProgressnotes());
						categoryList.add(event);
						testsListMap.put(note, categoryList);
						isProgressNoteAvailable = true;
					}

					if ((item.getSaEvent().equalsIgnoreCase("NEC") && BasicUtils.isEmpty(returnObj.getMorningNote().getNecpresentNote()) && (currentHour >= fromTime && currentHour < 14 && returnObj.getMorningNote().getMorningNoteTime()==currentHour)) || (returnObj.getMorningNote().getMorningNoteTime()==currentHour && (currentHour >= fromTime && currentHour < 14) && item.getSaEvent().equalsIgnoreCase("NEC"))) {
						if(!BasicUtils.isEmpty(returnObj.getMorningNote()) && !BasicUtils.isEmpty(returnObj.getMorningNote().getNecpresentNote())) {
							String noteDummy = returnObj.getMorningNote().getNecpresentNote();
							noteDummy = noteDummy + "\n" + item.getProgressnotes();
							returnObj.getMorningNote().setNecpresentNote(noteDummy);

						}else {
							returnObj.getMorningNote().setNecpresentNote(item.getProgressnotes());
						}					}
					else if (item.getSaEvent().equalsIgnoreCase("NEC")) {
						EventDetailsPOJO event = new EventDetailsPOJO();
						event.setEventName(item.getSaEvent());
						event.setProgressNote(item.getProgressnotes());
						categoryList.add(event);
						testsListMap.put(note, categoryList);
						isProgressNoteAvailable = true;
					}

					if ((item.getSaEvent().equalsIgnoreCase("Jaundice") && BasicUtils.isEmpty(returnObj.getMorningNote().getJaundicepresentNote()) && (currentHour >= fromTime && currentHour < 14 && returnObj.getMorningNote().getMorningNoteTime()==currentHour)) || (returnObj.getMorningNote().getMorningNoteTime()==currentHour && (currentHour >= fromTime && currentHour < 14) && item.getSaEvent().equalsIgnoreCase("Jaundice"))) {
						if(!BasicUtils.isEmpty(returnObj.getMorningNote()) && !BasicUtils.isEmpty(returnObj.getMorningNote().getJaundicepresentNote())) {
							String noteDummy = returnObj.getMorningNote().getJaundicepresentNote();
							noteDummy = noteDummy + "\n" + item.getProgressnotes();
							returnObj.getMorningNote().setJaundicepresentNote(noteDummy);

						}else {
							returnObj.getMorningNote().setJaundicepresentNote(item.getProgressnotes());
						}
					} else if (item.getSaEvent().equalsIgnoreCase("Jaundice")) {
						EventDetailsPOJO event = new EventDetailsPOJO();
						event.setEventName(item.getSaEvent());
						event.setProgressNote(item.getProgressnotes());
						categoryList.add(event);
						testsListMap.put(note, categoryList);
						isProgressNoteAvailable = true;
					}
					if ((item.getSaEvent().equalsIgnoreCase("Seizures") && BasicUtils.isEmpty(returnObj.getMorningNote().getSeizurespresentNote()) && (currentHour >= fromTime && currentHour < 14 && returnObj.getMorningNote().getMorningNoteTime()==currentHour)) || (returnObj.getMorningNote().getMorningNoteTime()==currentHour && (currentHour >= fromTime && currentHour < 14) && item.getSaEvent().equalsIgnoreCase("Seizures"))) {
						if(!BasicUtils.isEmpty(returnObj.getMorningNote()) && !BasicUtils.isEmpty(returnObj.getMorningNote().getSeizurespresentNote())) {
							String noteDummy = returnObj.getMorningNote().getSeizurespresentNote();
							noteDummy = noteDummy + "\n" + item.getProgressnotes();
							returnObj.getMorningNote().setSeizurespresentNote(noteDummy);

						}else {
							returnObj.getMorningNote().setSeizurespresentNote(item.getProgressnotes());
						}
					} else if (item.getSaEvent().equalsIgnoreCase("Seizures")) {
						EventDetailsPOJO event = new EventDetailsPOJO();
						event.setEventName(item.getSaEvent());
						event.setProgressNote(item.getProgressnotes());
						categoryList.add(event);
						testsListMap.put(note, categoryList);
						isProgressNoteAvailable = true;
					}
					if ((item.getSaEvent().equalsIgnoreCase("Asphyxia") && BasicUtils.isEmpty(returnObj.getMorningNote().getAsphyxiapresentNote()) && (currentHour >= fromTime && currentHour < 14 && returnObj.getMorningNote().getMorningNoteTime()==currentHour)) || (returnObj.getMorningNote().getMorningNoteTime()==currentHour && (currentHour >= fromTime && currentHour < 14) && item.getSaEvent().equalsIgnoreCase("Asphyxia"))) {
						if(!BasicUtils.isEmpty(returnObj.getMorningNote()) && !BasicUtils.isEmpty(returnObj.getMorningNote().getAsphyxiapresentNote())) {
							String noteDummy = returnObj.getMorningNote().getAsphyxiapresentNote();
							noteDummy = noteDummy + "\n" + item.getProgressnotes();
							returnObj.getMorningNote().setAsphyxiapresentNote(noteDummy);

						}else {
							returnObj.getMorningNote().setAsphyxiapresentNote(item.getProgressnotes());
						}
					} else if (item.getSaEvent().equalsIgnoreCase("Asphyxia")) {
						EventDetailsPOJO event = new EventDetailsPOJO();
						event.setEventName(item.getSaEvent());
						event.setProgressNote(item.getProgressnotes());
						categoryList.add(event);
						testsListMap.put(note, categoryList);
						isProgressNoteAvailable = true;
					}
					if ((item.getSaEvent().equalsIgnoreCase("Renal") && BasicUtils.isEmpty(returnObj.getMorningNote().getRenalpresentNote()) && (currentHour >= fromTime && currentHour < 14 && returnObj.getMorningNote().getMorningNoteTime()==currentHour)) || (returnObj.getMorningNote().getMorningNoteTime()==currentHour && (currentHour >= fromTime && currentHour < 14) && item.getSaEvent().equalsIgnoreCase("Renal"))) {
						if(!BasicUtils.isEmpty(returnObj.getMorningNote()) && !BasicUtils.isEmpty(returnObj.getMorningNote().getRenalpresentNote())) {
							String noteDummy = returnObj.getMorningNote().getRenalpresentNote();
							noteDummy = noteDummy + "\n" + item.getProgressnotes();
							returnObj.getMorningNote().setRenalpresentNote(noteDummy);
						}else {
							returnObj.getMorningNote().setRenalpresentNote(item.getProgressnotes());
						}
					} else if (item.getSaEvent().equalsIgnoreCase("Renal")) {
						EventDetailsPOJO event = new EventDetailsPOJO();
						event.setEventName(item.getSaEvent());
						event.setProgressNote(item.getProgressnotes());
						categoryList.add(event);
						testsListMap.put(note, categoryList);
						isProgressNoteAvailable = true;
					}
					if ((item.getSaEvent().equalsIgnoreCase("Hypoglycemia") && BasicUtils.isEmpty(returnObj.getMorningNote().getHypoglycemiapresentNote()) && (currentHour >= fromTime && currentHour < 14 && returnObj.getMorningNote().getMorningNoteTime()==currentHour)) || (returnObj.getMorningNote().getMorningNoteTime()==currentHour && (currentHour >= fromTime && currentHour < 14) && item.getSaEvent().equalsIgnoreCase("Hypoglycemia"))) {
						if(!BasicUtils.isEmpty(returnObj.getMorningNote()) && !BasicUtils.isEmpty(returnObj.getMorningNote().getHypoglycemiapresentNote())) {
							String noteDummy = returnObj.getMorningNote().getHypoglycemiapresentNote();
							noteDummy = noteDummy + "\n" + item.getProgressnotes();
							returnObj.getMorningNote().setHypoglycemiapresentNote(noteDummy);

						}else {
							returnObj.getMorningNote().setHypoglycemiapresentNote(item.getProgressnotes());
						}
					} else if (item.getSaEvent().equalsIgnoreCase("Hypoglycemia")) {
						EventDetailsPOJO event = new EventDetailsPOJO();
						event.setEventName(item.getSaEvent());
						event.setProgressNote(item.getProgressnotes());
						categoryList.add(event);
						testsListMap.put(note, categoryList);
						isProgressNoteAvailable = true;
					}
					if ((item.getSaEvent().equalsIgnoreCase("Shock") && BasicUtils.isEmpty(returnObj.getMorningNote().getShockpresentNote()) && (currentHour >= fromTime && currentHour < 14 && returnObj.getMorningNote().getMorningNoteTime()==currentHour)) || (returnObj.getMorningNote().getMorningNoteTime()==currentHour && (currentHour >= fromTime && currentHour < 14) && item.getSaEvent().equalsIgnoreCase("Shock"))) {
						if(!BasicUtils.isEmpty(returnObj.getMorningNote()) && !BasicUtils.isEmpty(returnObj.getMorningNote().getShockpresentNote())) {
							String noteDummy = returnObj.getMorningNote().getShockpresentNote();
							noteDummy = noteDummy + "\n" + item.getProgressnotes();
							returnObj.getMorningNote().setShockpresentNote(noteDummy);
						}else {
							returnObj.getMorningNote().setShockpresentNote(item.getProgressnotes());
						}
					} else if (item.getSaEvent().equalsIgnoreCase("Shock")) {
						EventDetailsPOJO event = new EventDetailsPOJO();
						event.setEventName(item.getSaEvent());
						event.setProgressNote(item.getProgressnotes());
						categoryList.add(event);
						testsListMap.put(note, categoryList);
						isProgressNoteAvailable = true;
					}
					if ((item.getSaEvent().equalsIgnoreCase("Pain") && BasicUtils.isEmpty(returnObj.getMorningNote().getPainpresentNote()) && (currentHour >= fromTime && currentHour < 14 && returnObj.getMorningNote().getMorningNoteTime()==currentHour)) || (returnObj.getMorningNote().getMorningNoteTime()==currentHour && (currentHour >= fromTime && currentHour < 14) && item.getSaEvent().equalsIgnoreCase("Pain"))) {
						if(!BasicUtils.isEmpty(returnObj.getMorningNote()) && !BasicUtils.isEmpty(returnObj.getMorningNote().getPainpresentNote())) {
							String noteDummy = returnObj.getMorningNote().getPainpresentNote();
							noteDummy = noteDummy + "\n" + item.getProgressnotes();
							returnObj.getMorningNote().setPainpresentNote(noteDummy);

						}else {
							returnObj.getMorningNote().setPainpresentNote(item.getProgressnotes());
						}
					} else if (item.getSaEvent().equalsIgnoreCase("Pain")) {
						EventDetailsPOJO event = new EventDetailsPOJO();
						event.setEventName(item.getSaEvent());
						event.setProgressNote(item.getProgressnotes());
						categoryList.add(event);
						testsListMap.put(note, categoryList);
						isProgressNoteAvailable = true;
					}

					if ((item.getSaEvent().equalsIgnoreCase("Feed Intolerance") && BasicUtils.isEmpty(returnObj.getMorningNote().getFeedIntolerencepresentNote()) && (currentHour >= fromTime && currentHour < 14 && returnObj.getMorningNote().getMorningNoteTime()==currentHour)) || (returnObj.getMorningNote().getMorningNoteTime()==currentHour && (currentHour >= fromTime && currentHour < 14) && item.getSaEvent().equalsIgnoreCase("Feed Intolerance"))) {
						if(!BasicUtils.isEmpty(returnObj.getMorningNote()) && !BasicUtils.isEmpty(returnObj.getMorningNote().getFeedIntolerencepresentNote())) {
							String noteDummy = returnObj.getMorningNote().getFeedIntolerencepresentNote();
							noteDummy = noteDummy + "\n" + item.getProgressnotes();
							returnObj.getMorningNote().setFeedIntolerencepresentNote(noteDummy);

						}else {
							returnObj.getMorningNote().setFeedIntolerencepresentNote(item.getProgressnotes());
						}
					} else if (item.getSaEvent().equalsIgnoreCase("Feed Intolerance")) {
						EventDetailsPOJO event = new EventDetailsPOJO();
						event.setEventName(item.getSaEvent());
						event.setProgressNote(item.getProgressnotes());
						categoryList.add(event);
						testsListMap.put(note, categoryList);
						isProgressNoteAvailable = true;
					}
					if ((item.getSaEvent().equalsIgnoreCase("IVH") && BasicUtils.isEmpty(returnObj.getMorningNote().getIvhpresentNote()) && (currentHour >= fromTime && currentHour < 14 && returnObj.getMorningNote().getMorningNoteTime()==currentHour)) || (returnObj.getMorningNote().getMorningNoteTime()==currentHour && (currentHour >= fromTime && currentHour < 14) && item.getSaEvent().equalsIgnoreCase("IVH"))) {
						if(!BasicUtils.isEmpty(returnObj.getMorningNote()) && !BasicUtils.isEmpty(returnObj.getMorningNote().getIvhpresentNote())) {
							String noteDummy = returnObj.getMorningNote().getIvhpresentNote();
							noteDummy = noteDummy + "\n" + item.getProgressnotes();
							returnObj.getMorningNote().setIvhpresentNote(noteDummy);

						}else {
							returnObj.getMorningNote().setIvhpresentNote(item.getProgressnotes());
						}
					} else if (item.getSaEvent().equalsIgnoreCase("IVH")) {
						EventDetailsPOJO event = new EventDetailsPOJO();
						event.setEventName(item.getSaEvent());
						event.setProgressNote(item.getProgressnotes());
						categoryList.add(event);
						testsListMap.put(note, categoryList);
						isProgressNoteAvailable = true;
					}
					if ((item.getSaEvent().equalsIgnoreCase("Misc") &&
							BasicUtils.isEmpty(returnObj.getMorningNote().getMiscellaneouspresentNote()) &&
							(currentHour >= fromTime && currentHour < 14 && returnObj.getMorningNote().getMorningNoteTime()==currentHour)) || (returnObj.getMorningNote().getMorningNoteTime()==currentHour &&
							(currentHour >= fromTime && currentHour < 14) && item.getSaEvent().equalsIgnoreCase("Misc"))) {
						if(!BasicUtils.isEmpty(returnObj.getMorningNote()) && !BasicUtils.isEmpty(returnObj.getMorningNote().getMiscellaneouspresentNote())) {
							String noteDummy = returnObj.getMorningNote().getMiscellaneouspresentNote();
							noteDummy = noteDummy + "\n" + item.getProgressnotes();
							returnObj.getMorningNote().setMiscellaneouspresentNote(noteDummy);

						}else {
							returnObj.getMorningNote().setMiscellaneouspresentNote(item.getProgressnotes());
						}
					} else if (item.getSaEvent().equalsIgnoreCase("Misc")) {
						EventDetailsPOJO event = new EventDetailsPOJO();

						if(!BasicUtils.isEmpty(item.getDiseaseName())){
							event.setEventName(item.getDiseaseName());
						}else{
							event.setEventName(item.getSaEvent());
						}

						event.setProgressNote(item.getProgressnotes());
						categoryList.add(event);
						testsListMap.put(note, categoryList);
						isProgressNoteAvailable = true;
					}
					if ((item.getSaEvent().equalsIgnoreCase("Misc 2") && BasicUtils.isEmpty(returnObj.getMorningNote().getMiscellaneous2presentNote()) && (currentHour >= fromTime && currentHour < 14 && returnObj.getMorningNote().getMorningNoteTime()==currentHour)) || (returnObj.getMorningNote().getMorningNoteTime()==currentHour && (currentHour >= fromTime && currentHour < 14) && item.getSaEvent().equalsIgnoreCase("Misc 2"))) {
						if(!BasicUtils.isEmpty(returnObj.getMorningNote()) && !BasicUtils.isEmpty(returnObj.getMorningNote().getMiscellaneous2presentNote())) {
							String noteDummy = returnObj.getMorningNote().getMiscellaneous2presentNote();
							noteDummy = noteDummy + "\n" + item.getProgressnotes();
							returnObj.getMorningNote().setMiscellaneous2presentNote(noteDummy);

						}else {
							returnObj.getMorningNote().setMiscellaneous2presentNote(item.getProgressnotes());
						}
					} else if (item.getSaEvent().equalsIgnoreCase("Misc 2")) {
						EventDetailsPOJO event = new EventDetailsPOJO();

						if(!BasicUtils.isEmpty(item.getDiseaseName())){
							event.setEventName(item.getDiseaseName());
						}else{
							event.setEventName(item.getSaEvent());
						}

						event.setProgressNote(item.getProgressnotes());
						categoryList.add(event);
						testsListMap.put(note, categoryList);
						isProgressNoteAvailable = true;
					}
					if ((item.getSaEvent().equalsIgnoreCase("VAP") && BasicUtils.isEmpty(returnObj.getMorningNote().getVappresentNote()) && (currentHour >= fromTime && currentHour < 14 && returnObj.getMorningNote().getMorningNoteTime()==currentHour)) || (returnObj.getMorningNote().getMorningNoteTime()==currentHour && (currentHour >= fromTime && currentHour < 14) && item.getSaEvent().equalsIgnoreCase("VAP"))) {
						if(!BasicUtils.isEmpty(returnObj.getMorningNote()) && !BasicUtils.isEmpty(returnObj.getMorningNote().getVappresentNote())) {
							String noteDummy = returnObj.getMorningNote().getVappresentNote();
							noteDummy = noteDummy + "\n" + item.getProgressnotes();
							returnObj.getMorningNote().setVappresentNote(noteDummy);

						}else {
							returnObj.getMorningNote().setVappresentNote(item.getProgressnotes());
						}
					} else if (item.getSaEvent().equalsIgnoreCase("VAP")) {
						EventDetailsPOJO event = new EventDetailsPOJO();
						event.setEventName(item.getSaEvent());
						event.setProgressNote(item.getProgressnotes());
						categoryList.add(event);
						testsListMap.put(note, categoryList);
						isProgressNoteAvailable = true;
					}
					if ((item.getSaEvent().equalsIgnoreCase("CLABSI") && BasicUtils.isEmpty(returnObj.getMorningNote().getClabsipresentNote()) && (currentHour >= fromTime && currentHour < 14 && returnObj.getMorningNote().getMorningNoteTime()==currentHour)) || (returnObj.getMorningNote().getMorningNoteTime()==currentHour && (currentHour >= fromTime && currentHour < 14) && item.getSaEvent().equalsIgnoreCase("CLABSI"))) {
						if(!BasicUtils.isEmpty(returnObj.getMorningNote()) && !BasicUtils.isEmpty(returnObj.getMorningNote().getClabsipresentNote())) {
							String noteDummy = returnObj.getMorningNote().getClabsipresentNote();
							noteDummy = noteDummy + "\n" + item.getProgressnotes();
							returnObj.getMorningNote().setClabsipresentNote(noteDummy);

						}else {
							returnObj.getMorningNote().setClabsipresentNote(item.getProgressnotes());
						}
					} else if (item.getSaEvent().equalsIgnoreCase("CLABSI")) {
						EventDetailsPOJO event = new EventDetailsPOJO();
						event.setEventName(item.getSaEvent());
						event.setProgressNote(item.getProgressnotes());
						categoryList.add(event);
						testsListMap.put(note, categoryList);
						isProgressNoteAvailable = true;
					}
					if ((item.getSaEvent().equalsIgnoreCase("Intrauterine Infection") && BasicUtils.isEmpty(returnObj.getMorningNote().getIntrauterinepresentNote()) && (currentHour >= fromTime && currentHour < 14 && returnObj.getMorningNote().getMorningNoteTime()==currentHour)) || (returnObj.getMorningNote().getMorningNoteTime()==currentHour && (currentHour >= fromTime && currentHour < 14) && item.getSaEvent().equalsIgnoreCase("Intrauterine Infection"))) {
						if(!BasicUtils.isEmpty(returnObj.getMorningNote()) && !BasicUtils.isEmpty(returnObj.getMorningNote().getIntrauterinepresentNote())) {
							String noteDummy = returnObj.getMorningNote().getIntrauterinepresentNote();
							noteDummy = noteDummy + "\n" + item.getProgressnotes();
							returnObj.getMorningNote().setIntrauterinepresentNote(noteDummy);

						}else {
							returnObj.getMorningNote().setIntrauterinepresentNote(item.getProgressnotes());
						}
					} else if (item.getSaEvent().equalsIgnoreCase("Intrauterine Infection")) {
						EventDetailsPOJO event = new EventDetailsPOJO();
						event.setEventName(item.getSaEvent());
						event.setProgressNote(item.getProgressnotes());
						categoryList.add(event);
						testsListMap.put(note, categoryList);
						isProgressNoteAvailable = true;
					}
					if ((item.getSaEvent().equalsIgnoreCase("Encephalopathy") && BasicUtils.isEmpty(returnObj.getMorningNote().getEncephalopathypresentNote()) && (currentHour >= fromTime && currentHour < 14 && returnObj.getMorningNote().getMorningNoteTime()==currentHour)) || (returnObj.getMorningNote().getMorningNoteTime()==currentHour && (currentHour >= fromTime && currentHour < 14) && item.getSaEvent().equalsIgnoreCase("Encephalopathy"))) {
						if(!BasicUtils.isEmpty(returnObj.getMorningNote()) && !BasicUtils.isEmpty(returnObj.getMorningNote().getEncephalopathypresentNote())) {
							String noteDummy = returnObj.getMorningNote().getEncephalopathypresentNote();
							noteDummy = noteDummy + "\n" + item.getProgressnotes();
							returnObj.getMorningNote().setEncephalopathypresentNote(noteDummy);

						}else {
							returnObj.getMorningNote().setEncephalopathypresentNote(item.getProgressnotes());
						}
					} else if (item.getSaEvent().equalsIgnoreCase("Encephalopathy")) {
						EventDetailsPOJO event = new EventDetailsPOJO();
						event.setEventName(item.getSaEvent());
						event.setProgressNote(item.getProgressnotes());
						categoryList.add(event);
						testsListMap.put(note, categoryList);
						isProgressNoteAvailable = true;
					}
					if ((item.getSaEvent().equalsIgnoreCase("Neuromuscular Disorders") && BasicUtils.isEmpty(returnObj.getMorningNote().getNeuromuscularpresentNote()) && (currentHour >= fromTime && currentHour < 14 && returnObj.getMorningNote().getMorningNoteTime()==currentHour)) || (returnObj.getMorningNote().getMorningNoteTime()==currentHour && (currentHour >= fromTime && currentHour < 14) && item.getSaEvent().equalsIgnoreCase("Neuromuscular Disorders"))) {
						if(!BasicUtils.isEmpty(returnObj.getMorningNote()) && !BasicUtils.isEmpty(returnObj.getMorningNote().getNeuromuscularpresentNote())) {
							String noteDummy = returnObj.getMorningNote().getNeuromuscularpresentNote();
							noteDummy = noteDummy + "\n" + item.getProgressnotes();
							returnObj.getMorningNote().setNeuromuscularpresentNote(noteDummy);

						}else {
							returnObj.getMorningNote().setNeuromuscularpresentNote(item.getProgressnotes());
						}
					} else if (item.getSaEvent().equalsIgnoreCase("Neuromuscular Disorders")) {
						EventDetailsPOJO event = new EventDetailsPOJO();
						event.setEventName(item.getSaEvent());
						event.setProgressNote(item.getProgressnotes());
						categoryList.add(event);
						testsListMap.put(note, categoryList);
						isProgressNoteAvailable = true;
					}
					if ((item.getSaEvent().equalsIgnoreCase("Hydrocephalus") && BasicUtils.isEmpty(returnObj.getMorningNote().getHydrocephaluspresentNote()) && (currentHour >= fromTime && currentHour < 14 && returnObj.getMorningNote().getMorningNoteTime()==currentHour)) || (returnObj.getMorningNote().getMorningNoteTime()==currentHour && (currentHour >= fromTime && currentHour < 14) && item.getSaEvent().equalsIgnoreCase("Hydrocephalus"))) {
						if(!BasicUtils.isEmpty(returnObj.getMorningNote()) && !BasicUtils.isEmpty(returnObj.getMorningNote().getHydrocephaluspresentNote())) {
							String noteDummy = returnObj.getMorningNote().getHydrocephaluspresentNote();
							noteDummy = noteDummy + "\n" + item.getProgressnotes();
							returnObj.getMorningNote().setHydrocephaluspresentNote(noteDummy);

						}else {
							returnObj.getMorningNote().setHydrocephaluspresentNote(item.getProgressnotes());
						}
					} else if (item.getSaEvent().equalsIgnoreCase("Hydrocephalus")) {
						EventDetailsPOJO event = new EventDetailsPOJO();
						event.setEventName(item.getSaEvent());
						event.setProgressNote(item.getProgressnotes());
						categoryList.add(event);
						testsListMap.put(note, categoryList);
						isProgressNoteAvailable = true;
					}
				}

				if (prevNote.equalsIgnoreCase(note)) {
					prevNote = note;
					prevTime = new Timestamp(offsetDate.getTime());
				}
				else {
					if (testsListMap.get(prevNote) != null) {

						System.out.println("Prev Note is :"+prevNote);
						index++;
						String intakeOutputText = getDailyProgressNotesInputOutputPast(uhid,startTime,prevTime, todayWeight);
						List<EventDetailsPOJO> mediateList = testsListMap.get(prevNote);
						EventDetailsPOJO event = new EventDetailsPOJO();

						if(!intakeOutputText.isEmpty()) {
							event.setIndex(index);
							event.setEventName("Intake");
							String dummyNote = BasicConstants.hrFormatTo12hrFormatMapping.get(startTime.getHours());
							if (intakeOutputText.contains("TFL") || intakeOutputText.contains("EN") || intakeOutputText.contains("PN")) {
								//event.setProgressNote("Since " + dummyNote + " baby had tolerated: " + intakeOutputText);
								event.setProgressNote(" baby had tolerated: " + intakeOutputText);
							} else {
								//event.setProgressNote("Since " + dummyNote + " " + intakeOutputText);
								event.setProgressNote(intakeOutputText);
							}
							mediateList.add(event);
						}
						else {
							event.setIndex(index);
							event.setEventName("Intake");
							mediateList.add(event);
						}

						String outputString = getDailyProgressNotesOutputPast(uhid,startTime,prevTime, todayWeight);
						if(!outputString.isEmpty()) {
							event.setIndex(index);
							event.setEventName("Output");
							String dummyNote = BasicConstants.hrFormatTo12hrFormatMapping.get(startTime.getHours());

//							event.setProgressNote("Since " + dummyNote + ": " + outputString);
							event.setProgressNote(outputString);
							mediateList.add(event);
						}
						else {
							event.setIndex(index);
							event.setEventName("Output");
							mediateList.add(event);
						}

						NursingVitalparameter vitalsList = getDailyProgressNotesVitalsInGivenTime( uhid,  prevTime, startTime,    startTime,   offset) ;
						if(vitalsList!= null) {
							event = new EventDetailsPOJO();
							event.setIndex(index);
							event.setEventName("Vitals");
							String dummyNote1 = BasicConstants.hrFormatTo12hrFormatMapping.get(startTime.getHours());
							String vitalsMessage = getDailyProgressNotesVitalsMessage(vitalsList, preterm);
							//event.setProgressNote("Since " + dummyNote1 + " " + vitalsMessage);
							event.setProgressNote(vitalsMessage);
							mediateList.add(event);
						}
						testsListMap.put(prevNote, mediateList);
						startTime = new Timestamp(prevTime.getTime());
					}
					prevNote = note;
					prevTime = new Timestamp(offsetDate.getTime());
				}
			}

			/**
			 * ASSESSMENTS SECTION - CLUBBED UNDER 1 HOUR UMBREALLA
			 * STABLE NOTES, ALL SYSTEMIC ASSESSMENTS (NAME 1,NAME 2.. ETC)
			 * OTHER SYSTEM
			 * INTAKE IN LAST 24 HOURS
			 * OUTPUT IN LAST 24 HOURS
			 * PROCEDURE (TODAYS'S 8AM TO MORNING NOTE TIME)
			 * LAB REPORTS IN LAST 24 HOURS
			 * EXISTING TREAMENT -> ALL MEDICATION , RESPIRATORY SUPPORT, PHOTOTHERAPY, BLOOD PRODUCT ORDER
			 */

			// intake output here  ->  Last 24 Hour InTake
			yesObj.setSeconds(0);
			try {
				String notesText = getDailyProgressNotesInputOutputPast(uhid, yesObj, todayObj, todayWeight);
				returnObj.setIntake_output(notesText);
			}catch(Exception e){
				System.out.println(e.getMessage());
			}

			// Ouput Execution  -> Last 24 Hour Output
			try{
				String outputText = getDailyProgressNotesOutputPast(uhid, yesObj, todayObj, todayWeight);
				returnObj.setOutputNote(outputText);
			}catch(Exception e){
				System.out.println(e.getMessage());
			}

			// get the Lab Reports  (Last 24 Hours)
			HashMap<String,String> testsListMapped = new HashMap<>();
			String queryTestsList = "select distinct testid,lab_testname from test_result where prn = '" + uhid + "' and resultdate >='"+ yesObj+"' " +
					"and resultdate<='"+ initialTodayObj+"' order by testid";

			String selectedTestId= "";
			List<Object[]> testsList = inicuDoa.getListFromNativeQuery(queryTestsList);
			for (Object[] testItem : testsList) {
				testsListMapped.put(testItem[0].toString(), testItem[1].toString());
				if(selectedTestId.equalsIgnoreCase("")){
					selectedTestId += testItem[0].toString();
				}else{
					selectedTestId += ","+testItem[0].toString();
				}
			}
			returnObj.setLabReports(testsListMapped);

			List<TestResultObject> testResultObjectList = testsService.getTestResultsForProgressNotes(uhid,yesObj.toString(),todayObj.toString(),selectedTestId,"lab");
			returnObj.setTestResultObjectList(testResultObjectList);

			// Existing Treatment
			queryCurrentRespSupport = "select obj from RespSupport as obj where uhid='" + uhid
					+ "' and creationtime <= '" +  tomorrowObj
					+ "' and creationtime >= '" + todayObj
					+ "' order by creationtime asc";

			respSupportLists = notesDoa.getListFromMappedObjNativeQuery(queryCurrentRespSupport);

			respSupport = "";
			for(RespSupport obj : respSupportLists) {
				if(!BasicUtils.isEmpty(obj.getRsVentType())){
					if(ventMode == "") {
						respSupport = "Baby is on respiratory support of";
						respSupport += respSupportStr(obj);
						Timestamp dateTimeObj = new Timestamp(obj.getCreationtime().getTime()+offset);
						respSupport += " at " + sdf1.format(dateTimeObj.getTime()) + ". ";
//						respSupport += " at " + obj.getCreationtime() + ". ";
						ventMode = obj.getRsVentType();
					}else if(ventMode != "" && !ventMode.equalsIgnoreCase(obj.getRsVentType())) {
						respSupport += "Treatment changed to";
						respSupport += respSupportStr(obj);
						Timestamp dateTimeObj = new Timestamp(obj.getCreationtime().getTime()+offset);
						respSupport += " at " + sdf1.format(dateTimeObj.getTime()) + ". ";
					//	respSupport += " at " + obj.getCreationtime() + ". ";
						ventMode = obj.getRsVentType();
					}else if(respSupport != "" && !ventMode.equalsIgnoreCase(obj.getRsVentType())) {
						respSupport += "Treatment changed to";
						respSupport += respSupportStr(obj);
						Timestamp dateTimeObj = new Timestamp(obj.getCreationtime().getTime()+offset);
						respSupport += " at " + sdf1.format(dateTimeObj.getTime()) + ". ";
						//respSupport += " at " + obj.getCreationtime() + ". ";
						ventMode = obj.getRsVentType();
					}else if(ventMode != "" && ventMode.equalsIgnoreCase(obj.getRsVentType()) && respSupport == "") {
						respSupport += "Respiratory Support Treatment is continued.";
					}
				}
			}
			returnObj.setCurrentRespSupport(respSupport);

			System.out.println("Todays Date :"+todayObj);
			System.out.println("Todays Date + 6 Hours :"+ new Timestamp(todayObj.getTime()+(6 * 60 * 60 * 1000)));

			String currentMedicine = "";

			System.out.println("Morning Note :"+morningTime);

//			List<BabyPrescription> medicationList = inicuDoa.getListFromMappedObjQuery(
//													HqlSqlQueryConstants.getCurrentMedicationList(uhid, todayObj, new Timestamp(todayObj.getTime()+(6 * 60 * 60 * 1000))));

			String contMedicine = "";
			String newMedicine = "";
			String caffeineMedicine = "";
			String stopMedicine = "";

			int treatmentFound = 0;
			System.out.println("Todays Date: "+todayObj);

			if(!BasicUtils.isEmpty(morningTime)){
				// Fetch all the active (revised and Continued) medication till today's morning note
				List<BabyPrescription> medicationList = inicuDoa.getListFromMappedObjQuery(
						HqlSqlQueryConstants.getCurrentMedicationList(uhid, todayObj, morningTime));

				for (BabyPrescription item : medicationList) {
					String route = "";
					if(item.getRoute().equalsIgnoreCase("IV") || item.getRoute().equalsIgnoreCase("IM")) route = "Inj." ;
					if(item.getRoute().equalsIgnoreCase("PO") ) route = "Syrup" ;
					if(item.getMedicationtype().equalsIgnoreCase("TYPE0032") ) route = "Drops" ;

					if (!BasicUtils.isEmpty(item.getMedicineOrderDate()) && item.getMedicineOrderDate().compareTo(todayObj) < 0) {
						Double day = Math.ceil((todayObj.getTime() - item.getMedicineOrderDate().getTime()) / oneDay) + 1;

						if(item.getFrequency()!=null && (item.getFrequency().equals("FR72")  || item.getFrequency().equals("FR36") )){
							int f = Integer.parseInt(item.getFrequency().replaceAll("[\\D]", ""));
							double dosage  = (day*24)/f;
							if(dosage==Math.floor(dosage)) {
								String contMedicineName = "";

//								if(item.getBolus()){
//									// Don't Display again
////									contMedicineName +="A load of "+route+" " + item.getMedicinename();
////
////									if(!BasicUtils.isEmpty(item.getDose())){
////										contMedicineName+=" given " + item.getDose()+" mg/kg";
////									}
//
//								}else

								if(item.getIsEdit()!=null && item.getIsEdit()){
									contMedicineName += route+" " + item.getMedicinename();
									if(!BasicUtils.isEmpty(item.getDose())) {
										contMedicineName += " revised to " + item.getDose() + " "+item.getDose_unit();
									}
								}else{
									contMedicineName += route+" " + item.getMedicinename();
									if(!BasicUtils.isEmpty(item.getDose())) {
										contMedicineName += " " + item.getDose() + " "+item.getDose_unit();
									}
								}
								contMedicineName += " (D " + (int) (dosage +1) + ")";

								if(!BasicUtils.isEmpty(contMedicineName) && contMedicine.indexOf(contMedicineName) == -1) {
									if (!BasicUtils.isEmpty(contMedicine)) {
										contMedicine += ", ";
									}
									contMedicine += contMedicineName;
								}
							}
						}
						else {
							String contMedicineName = "";
							// handle revise medication case and loading dose caffeine
//							if(item.getBolus() && item.getIsEdit()!=null && item.getIsEdit() && (item.getStartdate().compareTo(todayObj) >=0) && (item.getStartdate().compareTo(morningTime) <=0){
//								// Don't Display again
////								contMedicineName +="A load of "+route+" " + item.getMedicinename();
////
////								if(!BasicUtils.isEmpty(item.getDose())){
////									contMedicineName+=" given " + item.getDose()+" mg/kg";
////								}
//							}else

							if(item.getIsEdit()!=null && item.getIsEdit()){
								contMedicineName += route+" " + item.getMedicinename();
								if(!BasicUtils.isEmpty(item.getDose())) {
									contMedicineName += " revised to " + item.getDose() + " "+item.getDose_unit();
								}
							}else{
								contMedicineName += route+" " + item.getMedicinename();
								if(!BasicUtils.isEmpty(item.getDose())) {
									contMedicineName += " " + item.getDose() + " "+item.getDose_unit();
								}
							}

							if(!BasicUtils.isEmpty(item.getBolus()) && !item.getBolus())
								contMedicineName +=  " (D" + day.intValue()+ ")";

							if(!BasicUtils.isEmpty(contMedicineName) && contMedicine.indexOf(contMedicineName) == -1) {
								if (!BasicUtils.isEmpty(contMedicine)) {
									contMedicine += ", ";
								}
								contMedicine += contMedicineName;
							}
						}
					} else if(!BasicUtils.isEmpty(item.getMedicineOrderDate()) && item.getMedicineOrderDate().compareTo(morningTime) <= 0
							&& item.getMedicineOrderDate().compareTo(todayObj) >= 0) {

						if(item.getBolus()){
							newMedicine +="A load of "+route+" " + item.getMedicinename();

							if(!BasicUtils.isEmpty(item.getDose())){
								newMedicine+=" given " + item.getDose()+ " "+item.getDose_unit();
							}
						} else{
							newMedicine += ", " +route+" "+ item.getMedicinename();
							if(!BasicUtils.isEmpty(item.getDose())) {
								newMedicine +=" " +item.getDose()+ " "+item.getDose_unit();
							}
						}
//						else if(item.getIsEdit()!=null && item.getIsEdit()){
//							newMedicine += route+" " + item.getMedicinename();
//							if(!BasicUtils.isEmpty(item.getDose())) {
//								newMedicine += " revised to " + item.getDose() + " mg/kg";
//							}
//						}
					}
			}
			}

			if (!contMedicine.isEmpty()) {
				currentMedicine += contMedicine + "." ;
				treatmentFound++;
			}

			if (!newMedicine.isEmpty()) {
				if (!currentMedicine.isEmpty())   currentMedicine +="\n";
//				currentMedicine += "Start " + newMedicine.substring(2) + "." ;
				currentMedicine += newMedicine + "." ;
				treatmentFound++;
			}


			String currentResp = "";
			String queryCurrentRespSupport1 = "select obj from RespSupport as obj where uhid='" + uhid
					+ "' and creationtime <= '" +  new Timestamp(todayObj.getTime()+(6 * 60 * 60 * 1000))
					+ "' order by creationtime desc ";
			List<RespSupport> respSupportLists1 = notesDoa.getListFromMappedObjNativeQuery(queryCurrentRespSupport1);

			if (!BasicUtils.isEmpty(respSupportLists1)) {
				if (respSupportLists1.get(0).getIsactive() != null && respSupportLists1.get(0).getIsactive() &&
							!BasicUtils.isEmpty(respSupportLists1.get(0).getRsVentType())) {
					currentResp += "Respiratory support : On " +  respSupportUpdatedStr(respSupportLists1.get(0));
				}
			}

			if(currentResp.length()>1){
				if(currentMedicine.length()>1) currentMedicine+="\n";
				currentMedicine+=currentResp;
				treatmentFound++;
			}

			 // phototherapy
			 String treatmentPhototherapy  = "";
			 phototherapySql = "select obj from SaJaundice obj where uhid='" + uhid + "' and assessment_time <= '" + new Timestamp(todayObj.getTime()+(6 * 60 * 60 * 1000) )+ "' " +
					 "and phototherapyvalue is not null and phototherapyvalue !=''  order by assessment_time desc ";
			 phototherapyList = inicuDoa.getListFromMappedObjQuery(phototherapySql);

			if(!BasicUtils.isEmpty(phototherapyList) &&
					!phototherapyList.get(0).getPhototherapyvalue().equalsIgnoreCase("Stop")) {
				Double day = Math.ceil((todayObj.getTime() - phototherapyList.get(0).getAssessmentTime().getTime()) / oneDay) + 1;

				if (!BasicUtils.isEmpty(phototherapyList.get(0).getPhototherapyType())) {
					treatmentPhototherapy = phototherapyList.get(0).getPhototherapyType();
				}

				if(treatmentPhototherapy.length()>0) treatmentPhototherapy +=" ";
				treatmentPhototherapy += "Phototherapy"+" (D" + day.intValue()+ ")";
			}

			if(treatmentPhototherapy.length()>1){
				if(currentMedicine.length()>1) currentMedicine+="\n";
				currentMedicine+=treatmentPhototherapy;
				treatmentFound++;
			}

			// Blood Product
			String bloodProductTextStr = getDailyProgressNotesBloodProduct(uhid, todayObj, new Timestamp(todayObj.getTime()+(6 * 60 * 60 * 1000)));
			if(bloodProductTextStr.length()>1){
				if(currentMedicine.length()>1) currentMedicine+="\n";
				currentMedicine+="Blood Product : "+bloodProductTextStr;
				treatmentFound++;
			}

			if(treatmentFound>1){
				currentMedicine = "";

				if (!contMedicine.isEmpty()) {
					currentMedicine += contMedicine ;
				}

				if (!newMedicine.isEmpty()) {
					if (!currentMedicine.isEmpty())   currentMedicine +=", ";
					currentMedicine += newMedicine + "." ;
				}

				if(currentResp.length()>1){
					if(currentMedicine.length()>1) currentMedicine+=", ";
					currentMedicine+=currentResp;
				}

				if(treatmentPhototherapy.length()>1){
					if(currentMedicine.length()>1) currentMedicine+=", ";
					currentMedicine+="Phototherapy";
				}

				if(bloodProductTextStr.length()>1){
					if(currentMedicine.length()>1) currentMedicine+=", ";
					currentMedicine+="Blood Product : "+bloodProductTextStr;
				}

				if (!currentMedicine.isEmpty()) {
					currentMedicine += "." ;
				}
			}
			returnObj.setCurrentMedications(currentMedicine);

			/**
			 * PLAN SECTION - CLUBBED UNDER 1 HOUR UMBREALLA
			 *  ALL ASSESSMENT DATA IN PLAN FIELDS
			 *  INVESTIGATIONS ORDERED
			 *  NUTRITION ORDER -> TODAY'S ORDER
			 *  PROVISIONAL DIAGONOSIS
			 */


			if(morningInvestigations.length()>1) {
				if(morningInvestigations.indexOf("and")!=-1){
					morningInvestigations = morningInvestigations.replaceAll("and",",");
				}
				if(morningPlan.length()>1) morningPlan+= "\n";
				morningPlan+="Investigation Ordered: "+morningInvestigations;
			}

			returnObj.getMorningNote().setPlan(morningPlan);

			String planStr = "";
			String enFeedStr = "";
			List<BabyfeedDetail> feedList = (List<BabyfeedDetail>) inicuDoa.getListFromMappedObjRowLimitQuery(
					HqlSqlQueryConstants.getBabyfeedDetailList(uhid, todayObj, tomorrowObj), 1);
			if (!BasicUtils.isEmpty(feedList)) {
				BabyfeedDetail babyFeedObj = feedList.get(0);
				if (babyFeedObj.getIsenternalgiven() != null && babyFeedObj.getIsenternalgiven()) {
					List<RefMasterfeedtype> refFeedTypeList = inicuDoa
							.getListFromMappedObjQuery("SELECT obj FROM RefMasterfeedtype as obj");
					String primaryFeed = "";
					String feedMethodStr = "";

					if (!(babyFeedObj.getFeedtype() == null || babyFeedObj.getFeedtype() == "")) {
						String[] feedTypeArr = babyFeedObj.getFeedtype().replace("[", "").replace("]", "").split(",");
						for (int i = 0; i < refFeedTypeList.size(); i++) {
							if (feedTypeArr[0].equalsIgnoreCase(refFeedTypeList.get(i).getFeedtypeid())) {
								primaryFeed = refFeedTypeList.get(i).getFeedtypename();
							}
						}
					}
					String enfeedDetailsSql = "SELECT obj FROM EnFeedDetail as obj WHERE uhid='" + uhid
							+ "' and babyfeedid=" + babyFeedObj.getBabyfeedid() + " order by en_feed_detail_id asc";
					List<EnFeedDetail> enfeedDetailsList = inicuDoa.getListFromMappedObjQuery(enfeedDetailsSql);

					if (!BasicUtils.isEmpty(enfeedDetailsList)) {
						for (int i = 0; i < enfeedDetailsList.size(); i++) {
							if (i == 0) {
								enFeedStr = enfeedDetailsList.get(i).getFeed_volume() + " (mL/feed) for "
										+ enfeedDetailsList.get(i).getNo_of_feed() + " feed";
							} else {
								enFeedStr += ", " + enfeedDetailsList.get(i).getFeed_volume() + " (mL/feed) for "
										+ enfeedDetailsList.get(i).getNo_of_feed() + " feed";
							}
						}
					} else {
						if (!(babyFeedObj.getFeedmethod() == null || babyFeedObj.getFeedmethod().isEmpty())) {
							feedMethodStr = babyFeedObj.getFeedmethod().replace("[", "").replace("]", "").replace(", ",
									",");

							String refFeedMethodSql = "SELECT obj FROM RefMasterfeedmethod as obj";
							List<RefMasterfeedmethod> refFeedMethodList = inicuDoa
									.getListFromMappedObjQuery(refFeedMethodSql);

							if (!BasicUtils.isEmpty(feedMethodStr)) {
								String[] feedMethodArr = feedMethodStr.split(",");

								for (int i = 0; i < feedMethodArr.length; i++) {
									feedMethodStr = "";
									Iterator<RefMasterfeedmethod> itr = refFeedMethodList.iterator();
									while (itr.hasNext()) {
										RefMasterfeedmethod obj = itr.next();
										if (feedMethodArr[i].trim().equalsIgnoreCase(obj.getFeedmethodid())) {
											if (feedMethodStr.isEmpty()) {
												feedMethodStr = obj.getFeedmethodname();
											} else {
												feedMethodStr += ", " + obj.getFeedmethodname();
											}
											break;
										}
									}
								}

								if (feedMethodArr.length > 1 && babyFeedObj.getFeedmethod_type() != null) {
									if (babyFeedObj.getFeedmethod_type()) {
										feedMethodStr += "(Per Shift)";
									} else {
										feedMethodStr += "(Alternatively)";
									}
								}
							}
							if (feedMethodStr == "Breast Feed") {
								enFeedStr = "Give EN";
							} else {
								enFeedStr = babyFeedObj.getFeedvolume() + " (mL/feed)";
							}
						}
					}
					if(feedMethodStr == "") {
						enFeedStr += " by " + primaryFeed;
					}
					else {
						enFeedStr += " by " + feedMethodStr + ", " + primaryFeed;
					}

					if (BasicUtils.isEmpty(babyFeedObj.getFeedduration())) {
						enFeedStr += " on demand";
					} else {
						enFeedStr += " every " + babyFeedObj.getFeedduration() + " hours";
					}

					String formulaType = "";
					if (babyFeedObj.getFeedTypeSecondary() != null) {
						String[] secondaryFeedArr = babyFeedObj.getFeedTypeSecondary().replace("[", "").replace("]", "")
								.split(",");
						for (index = 0; index < secondaryFeedArr.length; index++) {
							for (int i = 0; i < refFeedTypeList.size(); i++) {

								if (secondaryFeedArr[index].equalsIgnoreCase(refFeedTypeList.get(i).getFeedtypeid())) {
									if (formulaType == "")
										formulaType = refFeedTypeList.get(i).getFeedtypename();
									else {
										formulaType += ", " + refFeedTypeList.get(i).getFeedtypename();
									}
								}
							}
						}
					}

					if (!BasicUtils.isEmpty(formulaType)) {
						enFeedStr += "(Give " + formulaType + " if " + primaryFeed + " volume is not sufficient). ";
					} else {
						enFeedStr += ". ";
					}
				} else {
					enFeedStr = "NPO.";
				}
			}
			else {
				enFeedStr = "No Enteral Order.";
			}
			planStr += enFeedStr + htmlNextLine;
			returnObj.setPlan(planStr);


			/**
			 * SUBSEQUENT NOTES SECTION -
			 *  ALL ASSESSMENT
			 *  INTAKE
			 *  OUTPUT
			 *  PLAN
			 */

			if (testsListMap.get(prevNote) != null) {
				index++;
				System.out.println("Prev Note :"+prevNote);

				// Intake String
				String intakeOutputText = getDailyProgressNotesInputOutputPast(uhid, startTime, prevTime, todayWeight);
				if(intakeOutputText!=null && !intakeOutputText.equals("")) {
					List<EventDetailsPOJO> mediateList = testsListMap.get(prevNote);
					EventDetailsPOJO event = new EventDetailsPOJO();
					event.setIndex(index);
					event.setEventName("Intake");
					String dummyNote = BasicConstants.hrFormatTo12hrFormatMapping.get(startTime.getHours());
					if (intakeOutputText.contains("TFL") || intakeOutputText.contains("EN") || intakeOutputText.contains("PN")) {
						//event.setProgressNote("Since " + dummyNote + " baby had tolerated: " + intakeOutputText);
						event.setProgressNote(" baby had tolerated: " + intakeOutputText);
					} else {
						//event.setProgressNote("Since " + dummyNote + " " + intakeOutputText);
						event.setProgressNote(intakeOutputText);
					}

					mediateList.add(event);
					testsListMap.put(prevNote, mediateList);
				}
				else {
					List<EventDetailsPOJO> mediateList = testsListMap.get(prevNote);
					EventDetailsPOJO event = new EventDetailsPOJO();
					event.setIndex(index);
					event.setEventName("Intake");
					mediateList.add(event);
					testsListMap.put(prevNote, mediateList);
				}

				// Output String
				String outputString = getDailyProgressNotesOutputPast(uhid, startTime, prevTime, todayWeight);
				if(outputString!=null && !outputString.equals("")) {
					List<EventDetailsPOJO> mediateList = testsListMap.get(prevNote);
					EventDetailsPOJO event = new EventDetailsPOJO();
					event.setIndex(index);
					event.setEventName("Output");
					String dummyNote = BasicConstants.hrFormatTo12hrFormatMapping.get(startTime.getHours());

					//event.setProgressNote("Since " + dummyNote + ": " + outputString);
					event.setProgressNote(outputString);

					mediateList.add(event);
					testsListMap.put(prevNote, mediateList);
				}
				else {
					List<EventDetailsPOJO> mediateList = testsListMap.get(prevNote);
					EventDetailsPOJO event = new EventDetailsPOJO();
					event.setIndex(index);
					event.setEventName("Output");
					mediateList.add(event);
					testsListMap.put(prevNote, mediateList);
				}
			}

			HashMap<String, List<EventDetailsPOJO>> testsListMapFinal = new HashMap<String, List<EventDetailsPOJO>>();
			List<HashMap<String, List<EventDetailsPOJO>>> finalListProgressNote = new ArrayList<HashMap<String, List<EventDetailsPOJO>>>();
			List<String> uniqueKeys = new ArrayList<>();

			// Timelist for Nutrition Notes

			List<Timestamp> myCustomTimeList = new ArrayList<>();
			HashMap<String,Timestamp> customHashMap = new HashMap<>();
			ArrayList<ArrayList<Float>> EnPnValueList = new ArrayList<>();
			ArrayList<Float> nutritionList = new ArrayList<>();

			if(!BasicUtils.isEmpty(morningTime)) {
				myCustomTimeList.add(morningTime);
			}

			if(!BasicUtils.isEmpty(morningTime)) {
				nutritionList = checkChangeInNutritionOrder(uhid, todayObj, morningTime);
				EnPnValueList.add(nutritionList);
			}

			for (String keys : testsListMap.keySet()) {
				try {
					String[] arrVal = keys.split(" ");
					String timestr = "";
					if(arrVal[1].indexOf("pm")!=-1){
						int hour = Integer.parseInt(arrVal[1].split(":")[0]);
						if(hour == 12){
							hour+=0;
						}else{
							hour+=12;
						}
						timestr  = hour+":00:00";
					}else{
						int hour = Integer.parseInt(arrVal[1].split(":")[0]);
						if(hour == 12){
							hour = 0;
						}
						timestr  = hour+":00:00";
					}
					String updatedDateStr =arrVal[0]+" "+timestr;
					SimpleDateFormat myTimeformat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
					java.util.Date parsedDate =  myTimeformat.parse(updatedDateStr);
					Timestamp timeValue = new Timestamp(parsedDate.getTime());
					System.out.println("TimeStamp are :" + timeValue);
					myCustomTimeList.add(timeValue);
					customHashMap.put(keys,timeValue);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			customHashMap = sortHashMapByValues(customHashMap);

			if(myCustomTimeList.size()>0) {
				myCustomTimeList.sort((e1, e2) -> new Long(e1.getTime()).compareTo(new Long(e2.getTime())));
			}

			int j=0;
			for(int i = 0; i<= index; i++) {
				for(String keys : customHashMap.keySet()) {
					List<EventDetailsPOJO> currentList = testsListMap.get(keys);

					if(i==0 && planMap.get(keys)!=null) {

						EventDetailsPOJO plan = new EventDetailsPOJO();
						plan.setEventName("Plan");

						String planUpdatedNote = (String) planMap.get(keys);

						if(investigationsOrderMap!=null && investigationsOrderMap.get(keys)!=null){
							String str = investigationsOrderMap.get(keys);
							if(str.length()>0) {
								// Add Here the Investigation done in all the assessment
								if (planUpdatedNote.length() > 0) planUpdatedNote += "\n";
								planUpdatedNote += "Investigation Ordered: "+str;
							}
						}

						// get the Nutrition Order
						if(myCustomTimeList!=null && EnPnValueList!=null && myCustomTimeList.size()>j && EnPnValueList.size()>j) {
							ArrayList<Float> tempNutritonList = checkChangeInNutritionOrder(uhid, myCustomTimeList.get(j), myCustomTimeList.get(j + 1));

							ArrayList<Float> myCompareToList = EnPnValueList.get(j);

							String nutritionMessage = "";
							float floatValue = 0;
							ArrayList<Float> listTemp = new ArrayList<Float>() {
								{
									add(floatValue);
									add(floatValue);
									add(floatValue);
								}
							};

							if (tempNutritonList.equals(myCompareToList) || (tempNutritonList.equals(listTemp) && !myCompareToList.equals(listTemp))) {
								nutritionMessage = "Continued on same nutrition plan";
								tempNutritonList = myCompareToList;
							} else {
								nutritionMessage = getDailyProgressNotesDoctorOrders(uhid, myCustomTimeList.get(j), myCustomTimeList.get(j + 1), todayWeight);
							}

							EnPnValueList.add(tempNutritonList);
							if (nutritionMessage.length() > 0) {
								if (planUpdatedNote.length() > 0) planUpdatedNote += "\n";

								if(!nutritionMessage.equalsIgnoreCase("Continued on same nutrition plan")) {
									planUpdatedNote += nutritionMessage;
								}
							}
							plan.setProgressNote(planUpdatedNote);
						}


						currentList.add(plan);
					}
					else if (i==0) {
						EventDetailsPOJO plan = new EventDetailsPOJO();
						plan.setEventName("Plan");
						String planUpdatedNote = "";
						if(investigationsOrderMap!=null && investigationsOrderMap.get(keys)!=null){
							String str = investigationsOrderMap.get(keys);
							if(str.length()>0) {
								// Add Here the Investigation done in all the assessment
								if (planUpdatedNote.length() > 0) planUpdatedNote += "\n";
								planUpdatedNote += "Investigation Ordered: "+str;
							}
						}

						// get the Nutrition Order
						if(myCustomTimeList!=null && EnPnValueList!=null && myCustomTimeList.size()>j && EnPnValueList.size()>j) {
							ArrayList<Float> tempNutritonList = checkChangeInNutritionOrder(uhid, myCustomTimeList.get(j), myCustomTimeList.get(j + 1));
							ArrayList<Float> myCompareToList = EnPnValueList.get(j);

							String nutritionMessage = "";
							float floatValue = 0;
							ArrayList<Float> listTemp = new ArrayList<Float>() {
								{
									add(floatValue);
									add(floatValue);
									add(floatValue);
								}
							};

							if (tempNutritonList.equals(myCompareToList) || (tempNutritonList.equals(listTemp) && !myCompareToList.equals(listTemp))) {
								nutritionMessage = "Continued on same nutrition plan";
								tempNutritonList = myCompareToList;
							} else {
								nutritionMessage = getDailyProgressNotesDoctorOrders(uhid, myCustomTimeList.get(j), myCustomTimeList.get(j + 1), todayWeight);
							}

							EnPnValueList.add(tempNutritonList);

							if (nutritionMessage.length() > 0) {
								if (planUpdatedNote.length() > 0) planUpdatedNote += "\n";
//								planUpdatedNote += "Nutrition Order: " + nutritionMessage;

								if(!nutritionMessage.equalsIgnoreCase("Continued on same nutrition plan")) {
									planUpdatedNote += nutritionMessage;
								}
							}

							plan.setProgressNote(planUpdatedNote);
						}

						if(planUpdatedNote.length()>0) {
							currentList.add(plan);
						}
					}

					for(EventDetailsPOJO obj : currentList) {
						if((obj.getEventName().equalsIgnoreCase("Intake") || obj.getEventName().equalsIgnoreCase("Output"))
								&& obj.getIndex() == i) {
							List<EventDetailsPOJO> currentProvisionalList = testsListMap.get(keys);
							EventDetailsPOJO stable = new EventDetailsPOJO();
							EventDetailsPOJO IO = new EventDetailsPOJO();
							EventDetailsPOJO Output = new EventDetailsPOJO();
							EventDetailsPOJO Plan = new EventDetailsPOJO();
							List<EventDetailsPOJO> others = new ArrayList<EventDetailsPOJO>();

							for(EventDetailsPOJO obj1 : currentProvisionalList) {
								if(obj1.getEventName().equalsIgnoreCase("")) {
									stable = obj1;
								}else if(obj1.getEventName().equalsIgnoreCase("Intake")) {
									IO = obj1;
								}else if(obj1.getEventName().equalsIgnoreCase("Output")) {
									Output = obj1;
								}else if(obj1.getEventName().equalsIgnoreCase("Plan")) {
									Plan = obj1;
								}else {
									others.add(obj1);
								}
							}

							List<EventDetailsPOJO> finalList = new ArrayList<EventDetailsPOJO>();
							if(!BasicUtils.isEmpty(stable)) {
								if(!BasicUtils.isEmpty(stable.getProgressNote()))
									finalList.add(stable);
							}

							if(!BasicUtils.isEmpty(others))
								finalList.addAll(others);

							if(!BasicUtils.isEmpty(IO)) {
								if(!BasicUtils.isEmpty(IO.getProgressNote()))
									finalList.add(IO);
							}
							if(!BasicUtils.isEmpty(Output)) {
								if(!BasicUtils.isEmpty(Output.getProgressNote()))
									finalList.add(Output);
							}

							if(!BasicUtils.isEmpty(Plan)) {
								if(!BasicUtils.isEmpty(Plan.getProgressNote()))
									finalList.add(Plan);
							}

							if(!uniqueKeys.contains(keys)) {
								uniqueKeys.add(keys);
								testsListMapFinal.put(keys, finalList);
								finalListProgressNote.add(testsListMapFinal);
								testsListMapFinal = new HashMap<String, List<EventDetailsPOJO>>();
							}
						}
					}
					j++;
				}
			}

			returnObj.setFinalProgressNotesList(finalListProgressNote);
			returnObj.setProgressNotesList(testsListMapFinal);


			/**
			 * OBSERVATION SECTION
			 * vital details PR, TEMP, RR, SPO2, BP ,RBS, TCB
			 */
			NursingVitalparameter latestVitalInfo = new NursingVitalparameter();
			Timestamp latestDate = initialTodayObj;
			Timestamp latestRbsDate = initialTodayObj;
			Timestamp latestTCBDate = initialTodayObj;
			long vitalsNearMorningDate = 0;
			long vitalsNearMorningDateOld = 0;

			try {
				if (progressNoteFormatType.equalsIgnoreCase("Configurable")) {

					queryCurrentBabyVitals = "select obj from NursingVitalparameter as obj where uhid='" + uhid
							+ "' and entryDate <= '" + initialTomObj + "' and entryDate >= '" + initialTodayObj
							+ "' order by entryDate desc";
					currentBabyVitalList = (List<NursingVitalparameter>) inicuDoa
							.getListFromMappedObjRowLimitQuery(queryCurrentBabyVitals, 1);
					if (!BasicUtils.isEmpty(currentBabyVitalList)) {
						latestVitalInfo = currentBabyVitalList.get(0);
						vitalInfoStr = getDailyProgressNotesVitals(latestVitalInfo);
						returnObj.setCurrentVital(
								sdf.format(new Timestamp(latestVitalInfo.getEntryDate().getTime() + offset)) + ": "
										+ vitalInfoStr);
					}
				} else {
					if (!BasicUtils.isEmpty(morningTime)) {
						// Fetch the Nursing Vital Parameters from today's 8AM to First Morning Note Time
						queryCurrentBabyVitals = "select obj from NursingVitalparameter as obj where uhid='" + uhid
								+ "' and entryDate >= '" + initialTodayObj + "' and entryDate <= '" + morningTime
								+ "' order by entryDate desc";

						currentBabyVitalList = (List<NursingVitalparameter>) inicuDoa
								.getListFromMappedObjQuery(queryCurrentBabyVitals);

						if (currentBabyVitalList.size() > 0) {
							// update latestVitalInfo object with the latest entry
							latestVitalInfo = currentBabyVitalList.get(0);
						} else {
							// Fetch the Nursing Vital Parameters from today's 8AM to 2PM
							Timestamp twoPmTime = new Timestamp(todayObj.getTime());
							twoPmTime.setHours(14);
							twoPmTime.setMinutes(00);

							queryCurrentBabyVitals = "select obj from NursingVitalparameter as obj where uhid='" + uhid
									+ "' and entryDate >= '" + initialTodayObj + "' and entryDate <= '" + twoPmTime
									+ "' order by entryDate desc";

							currentBabyVitalList = (List<NursingVitalparameter>) inicuDoa
									.getListFromMappedObjQuery(queryCurrentBabyVitals);
							if (currentBabyVitalList.size() > 0) {
								// update latestVitalInfo object with the latest entry
								latestVitalInfo = currentBabyVitalList.get(0);
							}
						}

						//				currentBabyVitalList = (List<NursingVitalparameter>) inicuDoa
						//						.getListFromMappedObjQuery(queryCurrentBabyVitals, 12);

						//				if (!BasicUtils.isEmpty(currentBabyVitalList)) {
						//					for(NursingVitalparameter vitalparameter : currentBabyVitalList) {
						//						if (!BasicUtils.isEmpty(vitalparameter.getSystBp()) || !BasicUtils.isEmpty(vitalparameter.getDiastBp()) ||
						//								!BasicUtils.isEmpty(vitalparameter.getPulserate()) || !BasicUtils.isEmpty( vitalparameter.getRrRate())  ||
						//								!BasicUtils.isEmpty(vitalparameter.getSpo2() ) ||  !BasicUtils.isEmpty(vitalparameter.getHrRate() ) ) {
						//							if(morningTime!= null) {
						//								if(vitalsNearMorningDateOld==0)
						//									vitalsNearMorningDateOld = (vitalparameter.getEntryDate().getTime() - offset) - morningTime.getTime();
						//								vitalsNearMorningDate = (vitalparameter.getEntryDate().getTime() - offset) - morningTime.getTime();
						//
						//								if (Math.abs(vitalsNearMorningDate) <= Math.abs(vitalsNearMorningDateOld) ) {
						//									latestVitalInfo = vitalparameter;
						//									latestDate = new Timestamp(latestVitalInfo.getEntryDate().getTime() - offset);
						//									vitalsNearMorningDateOld = (vitalparameter.getEntryDate().getTime() - offset) - morningTime.getTime();
						//								}
						//							} else{
						//								latestVitalInfo = vitalparameter;
						//								latestDate = new Timestamp(latestVitalInfo.getEntryDate().getTime() - offset);
						//								break;
						//							}
						//						}
						//					}
						//					System.out.println(latestVitalInfo.toString());
						//				}

						for (NursingVitalparameter vitalparameter : currentBabyVitalList) {
							if (vitalparameter.getRbs() != null) {
								System.out.println("RBS Value From Nursing Vital Parameters :" + vitalparameter.getRbs());
								latestVitalInfo.setRbs(vitalparameter.getRbs());
								latestRbsDate = new Timestamp(vitalparameter.getEntryDate().getTime() - offset);
								// break;
							}
						}

						// latest RBS -  Stable Notes
						List<StableNote> stableNoteList = inicuDoa.getListFromMappedObjQuery(
								HqlSqlQueryConstants.getotherParametersList(uhid, todayObj, morningTime));

						if (!BasicUtils.isEmpty(stableNoteList)) {
							for (StableNote stableNote : stableNoteList) {
								if (stableNote.getRbs() != null) {
									Timestamp tempRbsDate = stableNote.getEntrytime();

									// if the stable is the lastest entry then update accordingly
									if (tempRbsDate.after(latestRbsDate) && latestRbsDate.before(tempRbsDate)) {
										System.out.println("RBS Value From Stable Notes :" + stableNote.getRbs()
												+ " and Timestamp value :" + tempRbsDate);
										latestRbsDate = tempRbsDate;
										latestVitalInfo.setRbs(stableNote.getRbs());
									}
								}
							}
						}

						// RBS - From Hypoglycemia
						List<SaHypoglycemia> hList = inicuDoa.getListFromMappedObjQuery(
								HqlSqlQueryConstants.getSaHypoglycemiaListbyTime(uhid, todayObj, morningTime));
						if (!BasicUtils.isEmpty(hList)) {
							for (SaHypoglycemia obj : hList) {
								if (obj.getBloodSugar() != null) {
									Timestamp tempRbsDate = obj.getAssessmentTime();
									// if the stable is the lastest entry then update accordingly
									if (tempRbsDate.after(latestRbsDate) && latestRbsDate.before(tempRbsDate)) {
										System.out.println("RBS Value From Hypoglycemia Notes :" + obj.getBloodSugar()
												+ " and Timestamp value :" + tempRbsDate);
										latestRbsDate = tempRbsDate;
										latestVitalInfo.setRbs(obj.getBloodSugar());
									}
								}
							}
						}

						for (NursingVitalparameter vitalparameter : currentBabyVitalList) {
							if (vitalparameter.getTcb() != null) {
								System.out.println("TCB Value From Nursing Vital Parameters :" + vitalparameter.getTcb());
								latestVitalInfo.setTcb(vitalparameter.getTcb() + "");
								latestTCBDate = new Timestamp(vitalparameter.getEntryDate().getTime() - offset);
							}
						}

						// latest TcB - From Stable Notes
						stableNoteList = inicuDoa.getListFromMappedObjQuery(
								HqlSqlQueryConstants.getotherParametersList(uhid, todayObj, morningTime));

						if (!BasicUtils.isEmpty(stableNoteList)) {
							for (StableNote stableNote : stableNoteList) {
								if (stableNote.getTcb() != null) {
									Timestamp tempTcbDate = stableNote.getEntrytime();

									// if the stable is the lastest entry then update accordingly
									if (tempTcbDate.after(latestTCBDate) && latestTCBDate.before(tempTcbDate)) {
										System.out.println("TCB Value From Stable Notes :" + stableNote.getTcb()
												+ " and Timestamp value :" + tempTcbDate);
										latestTCBDate = tempTcbDate;
										latestVitalInfo.setTcb(stableNote.getTcb());
									}
								}
							}
						}

						//  TCB -  From Jaundice
						List<SaJaundice> jList = inicuDoa.getListFromMappedObjQuery(
								HqlSqlQueryConstants.getJaundiceList(uhid, todayObj, morningTime));

						if (!BasicUtils.isEmpty(jList)) {
							for (SaJaundice obj : jList) {
								System.out.println("TCB Value From Jaundice Notes :" + obj.getTbcvalue());
								if (obj.getTbcvalue() != null) {
									Timestamp tempTcbDate = obj.getAssessmentTime();

									// if the stable is the lastest entry then update accordingly
									if (tempTcbDate.after(latestTCBDate) && latestTCBDate.before(tempTcbDate)) {
										System.out.println("TCB Value From Stable Notes :" + obj.getTbcvalue()
												+ " and Timestamp value :" + tempTcbDate);
										latestTCBDate = tempTcbDate;
										latestVitalInfo.setTcb(obj.getTbcvalue() + "");
									}
								}
							}
						}

						if (latestVitalInfo != null) {
							vitalInfoStr = getDailyProgressNotesVitals(latestVitalInfo);
							returnObj.setCurrentVital(vitalInfoStr);
						}
					}
				}
			}catch(Exception  e){
				System.out.println("Message is :"+e.getMessage());
			}
			// vitals end


			// TO BE DECIDED

			//Intake  Execution
			String intakeOutputText = getDailyProgressNotesInputOutput(uhid, todayObj, tomorrowObj, todayWeight);
			returnObj.setIntakeOutputNote(intakeOutputText);

			//Doctor Orders
			if(!BasicUtils.isEmpty(morningTime)) {
				String doctorText = getDailyProgressNotesDoctorOrders(uhid, todayObj, morningTime, todayWeight);
				returnObj.setDoctorOrders(doctorText);
			}

			//Procedure here
			if(!BasicUtils.isEmpty(todayObj)) {

				// fetch only last 24 hours procedures
				System.out.println("Fetching Procedure done in last 24 hours");
				System.out.println("Today : "+todayObj.toString());
				System.out.println("Yesterday : "+yesObj.toString());

				String procedureNote = getDailyProgressNotesProcedure(uhid, yesObj,todayObj);
				returnObj.setProcedureNote(procedureNote);
			}

			// screening here
			String screeningNote = getDailyProgressNotesScreening(uhid, todayObj, tomorrowObj);
			returnObj.setScreeningNote(screeningNote);

			// bloodProduct here
			String bloodProductNote = getDailyProgressNotesBloodProduct(uhid, todayObj, tomorrowObj);
			returnObj.setBloodProductNote(bloodProductNote);


			StringBuilder loggedUserFullName = null;
			String userListQuery = "select obj from User as obj where username='" + loggedInUser + "' and branchname = '"
					+ branchName + "'";
			List<User> userList = inicuDoa.getListFromMappedObjQuery(userListQuery);
			if(!BasicUtils.isEmpty(userList)) {
				loggedUserFullName = new StringBuilder(userList.get(0).getFirstName());
				if (!BasicUtils.isEmpty(userList.get(0).getLastName())) {
					loggedUserFullName.append(" " + userList.get(0).getLastName());
				}

				String roleId = "";
				String userObjQuery = "SELECT user FROM UserRolesTable as user WHERE userName='" + loggedInUser
						+ "' and branchname = '" + branchName + "'";
				List<UserRolesTable> userRoleList = inicuDoa.getListFromMappedObjQuery(userObjQuery);
				if (!BasicUtils.isEmpty(userRoleList)) {
					roleId = userRoleList.get(0).getRoleId();
				}

				if(!BasicUtils.isEmpty(roleId)) {
					if ("2".equalsIgnoreCase(roleId) || "3".equalsIgnoreCase(roleId)) {
						loggedUserFullName.insert(0, "Dr. ");
					}
				}

				returnObj.setLoggedUserFullName(loggedUserFullName.toString());
			}

			// Not Used Starts
			//tcb data here
//			String queryLastTcb = "select obj from NursingVitalparameter as obj where uhid='" + uhid
//					+ "' and entryDate <= '" + initialTomObj + "' and entryDate >= '" + initialTodayObj + "' and tcb is not null"
//					+ " order by entryDate desc";
//			List<NursingVitalparameter> nursingVitalsData = (List<NursingVitalparameter>) inicuDoa
//					.getListFromMappedObjRowLimitQuery(queryLastTcb, 1);
//
//			if (!BasicUtils.isEmpty(nursingVitalsData)) {
//				  latestVitalInfo = nursingVitalsData.get(0);
//				returnObj.setTcb(latestVitalInfo.getTcb() + " mg/dL (" + sdf.format(new Timestamp(latestVitalInfo.getCreationtime().getTime() + offset)) + ")");
//			}

			//rbs data here - Not Used
//			queryLastTcb = "select obj from NursingVitalparameter as obj where uhid='" + uhid
//					+ "' and entryDate <= '" + initialTomObj + "' and entryDate >= '" + initialTodayObj + "' and rbs is not null"
//					+ " order by entryDate desc";
//			nursingVitalsData = (List<NursingVitalparameter>) inicuDoa
//					.getListFromMappedObjRowLimitQuery(queryLastTcb, 1);
//
//			if (!BasicUtils.isEmpty(nursingVitalsData)) {
//				latestVitalInfo = nursingVitalsData.get(0);
//				returnObj.setRbs(latestVitalInfo.getRbs() + " mg/dL (" + sdf.format(new Timestamp(latestVitalInfo.getCreationtime().getTime() + offset)) + ")");
//			}
			// Not Used Ends

		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUser,
					uhid, "getDailyProgressNotes", BasicUtils.convertErrorStacktoString(e));
		}
		return returnObj;
	}



	public DailyProgressNotes getScreeningNotes(DailyProgressNotes returnObj,String uhid, Timestamp tomorrowObj, Timestamp todayObj){
		String htmlNextLine = System.getProperty("line.separator");// "&#13;&#10;";
		List<ScreenNeurological> neurologicalList = inicuDoa.getListFromMappedObjQuery(HqlSqlQueryConstants.getScreeningNeurologicalList1(uhid, tomorrowObj, todayObj));
		if(!BasicUtils.isEmpty(neurologicalList)) {

			for(int i = 0;i<neurologicalList.size();i++) {
				if(neurologicalList.get(i).getScreening_time()!=null) {

					Date date = new Date(neurologicalList.get(i).getScreening_time().getTime());
					SimpleDateFormat sdf_formatter = new SimpleDateFormat("dd-MM-yyyy");
					String date1 = sdf_formatter.format(date);
					neurologicalList.get(i).setScreening_message("Screening was done on " + date1);
				}

				if(neurologicalList.get(i).getReports()!=null)
					neurologicalList.get(i).setReports("Reports - " + neurologicalList.get(i).getReports());

				if(neurologicalList.get(i).getOther_comments()!=null)
					neurologicalList.get(i).setOther_comments("Comments - " + neurologicalList.get(i).getOther_comments());
			}
		}
		returnObj.setScreenNeurological(neurologicalList);

		List<ScreenHearing> hearingList = inicuDoa.getListFromMappedObjQuery(HqlSqlQueryConstants.getScreeningHearingList1(uhid, tomorrowObj, todayObj));
		if(!BasicUtils.isEmpty(hearingList)) {
			String oae = "";
			String abr = "";
			for(int i = 0; i <hearingList.size();i++) {

				if(hearingList.get(i).getScreening_time()!=null) {

					Date date = new Date(hearingList.get(i).getScreening_time().getTime());
					SimpleDateFormat sdf_formatter = new SimpleDateFormat("dd-MM-yyyy");
					String date1 = sdf_formatter.format(date);
					System.out.println(date1);
					hearingList.get(i).setScreening_message("Screening was done on " + date1);
				}

				if(hearingList.get(i).getOae_left()!=null && hearingList.get(i).getOae_left()!="") {
					oae = "Left Eye: " + hearingList.get(i).getOae_left() + htmlNextLine;
				}
				if(hearingList.get(i).getOae_right()!=null && hearingList.get(i).getOae_right()!="") {
					oae+= "Right Eye: " + hearingList.get(i).getOae_right();
				}
				hearingList.get(i).setOae(oae);

				if(hearingList.get(i).getAbr_left()!=null && hearingList.get(i).getAbr_left()!="") {
					abr = "Left Eye: " + hearingList.get(i).getAbr_left() +  htmlNextLine;
				}
				if(hearingList.get(i).getAbr_right()!=null && hearingList.get(i).getAbr_right()!="") {
					abr += "Right Eye: " +  hearingList.get(i).getAbr_right();
				}

				hearingList.get(i).setAbr(abr);

				if(hearingList.get(i).getTreatment()!=null && hearingList.get(i).getTreatment()!="") {
					hearingList.get(i).setTreatment("Treatment - " + hearingList.get(i).getTreatment());
				}
			}
		}
		returnObj.setScreenHearing(hearingList);

		List<ScreenRop> ropList = inicuDoa.getListFromMappedObjQuery(HqlSqlQueryConstants.getScreeningRopList1(uhid, tomorrowObj, todayObj));
		if(!BasicUtils.isEmpty(ropList)) {
			String findings = "";
			String laser = "";
			for(int i =0; i<ropList.size();i++) {

				if(ropList.get(i).getScreening_time()!=null) {

					Date date = new Date(ropList.get(i).getScreening_time().getTime());
					SimpleDateFormat sdf_formatter = new SimpleDateFormat("dd-MM-yyyy");
					String date1 = sdf_formatter.format(date);
					System.out.println(date1);
					ropList.get(i).setScreening_message("Screening was done on " + date1);
				}

				if(ropList.get(i).getIs_rop() == null || ropList.get(i).getIs_rop() == false) {
					findings = "Finding - No ROP";
				}
				if(ropList.get(i).getIs_rop()!=null && ropList.get(i).getIs_rop()==true) {
					findings = "Finding - " + htmlNextLine;
					if(ropList.get(i).getIs_rop_left()!=null && ropList.get(i).getIs_rop_left()==true) {
						findings += "Left Eye: ";
						if(ropList.get(i).getRop_left_zone()!=null)
							findings +=  ropList.get(i).getRop_left_zone() + " zone, ";
						if(ropList.get(i).getRop_left_stage()!=null)
							findings+= ropList.get(i).getRop_left_stage() + " stage, ";
					}
					if(ropList.get(i).getRop_left_plus()!=null && ropList.get(i).getRop_left_plus() == true) {
						findings+= "Plus disease: Present";
					}
					if(ropList.get(i).getRop_left_plus()!=null && ropList.get(i).getRop_left_plus() == false) {
						findings+= "Plus disease: Absent";
					}

					if(ropList.get(i).getIs_rop_right()!=null && ropList.get(i).getIs_rop_right()==true) {
						findings += "Right Eye: ";
						if(ropList.get(i).getRop_right_zone()!=null) {
							findings += ropList.get(i).getRop_right_zone() + " zone, " ;
						}

						if(ropList.get(i).getRop_right_stage()!=null) {
							findings += ropList.get(i).getRop_right_stage() + " stage, " ;
						}

					}
					if(ropList.get(i).getRop_right_plus()!=null && ropList.get(i).getRop_right_plus() == true) {

						findings+= "Plus disease: Present";
					}
					if(ropList.get(i).getRop_right_plus()!=null && ropList.get(i).getRop_right_plus() == false) {
						findings+= "Plus disease: Absent";
					}
					ropList.get(i).setFindings_message(findings);


				}

				if(ropList.get(i).getLeft_laser() != null && ropList.get(i).getLeft_laser() == true) {
					laser = " Left Eye: ";
					if(ropList.get(i).getLeft_laser_date()!=null) {
						//Timestamp ts = new Timestamp();
						Date date = new Date(ropList.get(i).getLeft_laser_date().getTime());
						SimpleDateFormat sdf_formatter = new SimpleDateFormat("dd-MM-yyyy");
						String date1 = sdf_formatter.format(date);
						System.out.println(date1);
						laser += date1 + " ";
					}
				}

				if(ropList.get(i).getLeft_laser_comment()!=null && ropList.get(i).getLeft_laser_comment()!="") {
					laser += ropList.get(i).getLeft_laser_comment()+ " ";
				}

				if(ropList.get(i).getRight_laser() != null && ropList.get(i).getRight_laser() == true) {
					laser = " Right Eye: ";
					if(ropList.get(i).getRight_laser_date()!=null) {
						//Timestamp ts = new Timestamp();
						Date date = new Date(ropList.get(i).getRight_laser_date().getTime());
						SimpleDateFormat sdf_formatter = new SimpleDateFormat("dd-MM-yyyy");
						String date1 = sdf_formatter.format(date);
						System.out.println(date1);
						laser += date1 + " ";
					}
				}
				if(ropList.get(i).getRight_laser_comment()!=null && ropList.get(i).getRight_laser_comment()!="") {
					laser += ropList.get(i).getRight_laser_comment()+ " ";
				}

				ropList.get(i).setLaser_message(laser);
			}
		}
		returnObj.setScreenRop(ropList);

		List<ScreenUSG> usgList = inicuDoa.getListFromMappedObjQuery(HqlSqlQueryConstants.getScreeningUSGList1(uhid, tomorrowObj, todayObj));
		if(!BasicUtils.isEmpty(usgList)) {
			String usgNotes = "";
			for(int i = 0; i<usgList.size(); i++) {
				if(usgList.get(i).getScreening_time()!=null) {

					Date date = new Date(usgList.get(i).getScreening_time().getTime());
					SimpleDateFormat sdf_formatter = new SimpleDateFormat("dd-MM-yyyy");
					String date1 = sdf_formatter.format(date);
					System.out.println(date1);
					usgList.get(i).setScreening_message("Screening was done on " + date1);
				}
				if(usgList.get(i).getBrain_parenchyma()!=null) {
//						usgNotes = "Brain Parenchyma - " + usgList.get(i).getBrain_parenchyma();
					usgList.get(i).setBrain_parenchyma("Brain Parenchyma - " + usgList.get(i).getBrain_parenchyma());
				}
				if(usgList.get(i).getIvh_type()!=null) {
//						usgNotes += " IVH Type - " +
					usgList.get(i).setIvh_type("IVH Type - " + usgList.get(i).getIvh_type());
				}

				if(usgList.get(i).getPvl_type()!=null) {
//						usgNotes += " IVH Type - " +
					usgList.get(i).setPvl_type("PVL Type - " + usgList.get(i).getPvl_type());
				}
			}
		}
		returnObj.setScreenUsg(usgList);

		List<ScreenMetabolic> metabolicList = inicuDoa.getListFromMappedObjQuery(HqlSqlQueryConstants.getScreeningMetabolicList1(uhid, tomorrowObj, todayObj));
		if(!BasicUtils.isEmpty(metabolicList)) {
			for(int i = 0; i<metabolicList.size();i++) {
				if(metabolicList.get(i).getScreening_time()!=null) {

					Date date = new Date(metabolicList.get(i).getScreening_time().getTime());
					SimpleDateFormat sdf_formatter = new SimpleDateFormat("dd-MM-yyyy");
					String date1 = sdf_formatter.format(date);
					System.out.println(date1);
					metabolicList.get(i).setScreening_message("Screening was done on " + date1);
				}
				if(metabolicList.get(i).getMetabolic_screening()!=null) {
					//String msg = "Metabolic screening of 6 tests";

					metabolicList.get(i).setMetabolic_screening("Metabolic screening of 6 tests - " + metabolicList.get(i).getMetabolic_screening() + htmlNextLine);
					//message.setContent(msg, "text/html; charset=utf-8");`
				}
				if(metabolicList.get(i).getScreening_panel()!=null && metabolicList.get(i).getScreening_panel() == true) {
					metabolicList.get(i).setScreening_panel_message("Metabolic Screening of 123 conditions");
				}
			}
		}
		returnObj.setScreenMetabolical(metabolicList);

		List<ScreenMiscellaneous> MiscList = inicuDoa.getListFromMappedObjQuery(HqlSqlQueryConstants.getScreeningMiscellaneousList1(uhid, tomorrowObj, todayObj));
		if(!BasicUtils.isEmpty(MiscList)) {
			for(int i = 0; i < MiscList.size(); i++) {
				if(MiscList.get(i).getScreening_time()!=null) {

					Date date = new Date(MiscList.get(i).getScreening_time().getTime());
					SimpleDateFormat sdf_formatter = new SimpleDateFormat("dd-MM-yyyy");
					String date1 = sdf_formatter.format(date);
					System.out.println(date1);
					MiscList.get(i).setScreening_message("Screening was done on " + date1);
				}
				if(MiscList.get(i).getScreeningType()!=null) {
					MiscList.get(i).setScreeningType("Screening investigation - " + MiscList.get(i).getScreeningType() + htmlNextLine);
				}
				if(MiscList.get(i).getSite()!=null) {
					MiscList.get(i).setSite("Site - " + MiscList.get(i).getSite() + htmlNextLine);
				}
				if(MiscList.get(i).getView()!=null) {
					MiscList.get(i).setView("View - " + MiscList.get(i).getView() + htmlNextLine);
				}

				if(MiscList.get(i).getCttype()!=null) {
					MiscList.get(i).setCttype("CT Type - " + MiscList.get(i).getCttype() + htmlNextLine);
				}

				if(MiscList.get(i).getFindings()!=null) {
					MiscList.get(i).setFindings("Findings - " + MiscList.get(i).getFindings() + htmlNextLine);
				}
			}
		}
		returnObj.setScreenMisc(MiscList);

		return returnObj;
	}


	public String checkActiveOrPassiveAsssessmentByDate(Timestamp startTime, Timestamp endTime,String uhid,String assessmentType){

		String inActive = "notpresent";

		try {
			assessmentType = assessmentType.trim();
			if (assessmentType.equalsIgnoreCase("RDS")  || assessmentType.equalsIgnoreCase("Respiratory Distress") ) {

				StringBuilder query = new StringBuilder("SELECT  uhid , eventstatus  FROM sa_resp_rds where ")
						.append(" uhid='").append(uhid).append("'")
						.append(" and assessment_time <='").append(endTime).append("' order by assessment_time desc");

				List<Object[]> inActiveAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
				if (inActiveAssessmentList != null && inActiveAssessmentList.size() > 0){
					if(!BasicUtils.isEmpty(inActiveAssessmentList.get(0))){
						String lastEventStatus = inActiveAssessmentList.get(0)[1].toString();
						if(lastEventStatus.equalsIgnoreCase("inactive")){
							inActive = "inactive";
						}else{
							inActive = "active";
						}
					}else {
						inActive = "active";
					}
				}
                return  inActive;
			}
			if (assessmentType.equalsIgnoreCase("Apnea")) {


				StringBuilder query = new StringBuilder("SELECT  uhid,eventstatus  FROM sa_resp_apnea where ")
						.append(" uhid='").append(uhid).append("'")
						.append(" and assessment_time <='").append(endTime).append("' order by assessment_time desc");

				List<Object[]> inActiveAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
				if (inActiveAssessmentList != null && inActiveAssessmentList.size() > 0){
					if(!BasicUtils.isEmpty(inActiveAssessmentList.get(0))){
						String lastEventStatus = inActiveAssessmentList.get(0)[1].toString();
						if(lastEventStatus.equalsIgnoreCase("inactive")){
							inActive = "inactive";
						}else{
							inActive = "active";
						}
					}else {
						inActive = "active";
					}
				}

                return  inActive;

			}
			if (assessmentType.equalsIgnoreCase("PPHN")) {


				StringBuilder query = new StringBuilder("SELECT  uhid,eventstatus FROM sa_resp_pphn where ")
						.append(" uhid='").append(uhid).append("'")
						.append(" and assessment_time <='").append(endTime).append("' order by assessment_time desc");

				List<Object[]> inActiveAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
				if (inActiveAssessmentList != null && inActiveAssessmentList.size() > 0){
					if(!BasicUtils.isEmpty(inActiveAssessmentList.get(0))){
						String lastEventStatus = inActiveAssessmentList.get(0)[1].toString();
						if(lastEventStatus.equalsIgnoreCase("inactive")){
							inActive = "inactive";
						}else{
							inActive = "active";
						}
					}else {
						inActive = "active";
					}
				}
                return  inActive;

			}
			if (assessmentType.equalsIgnoreCase("Pneumothorax")) {


				StringBuilder query = new StringBuilder("SELECT  uhid,eventstatus  FROM sa_resp_pneumothorax where ")
						.append(" uhid='").append(uhid).append("'")
						.append(" and assessment_time <='").append(endTime).append("' order by assessment_time desc");


				List<Object[]> inActiveAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
				if (inActiveAssessmentList != null && inActiveAssessmentList.size() > 0){
					if(!BasicUtils.isEmpty(inActiveAssessmentList.get(0))){
						String lastEventStatus = inActiveAssessmentList.get(0)[1].toString();
						if(lastEventStatus.equalsIgnoreCase("inactive")){
							inActive = "inactive";
						}else{
							inActive = "active";
						}
					}else {
						inActive = "active";
					}
				}
                return  inActive;

			}
			if (assessmentType.contains("Sepsis")) {

				StringBuilder query = new StringBuilder("SELECT  uhid,eventstatus  FROM sa_infection_sepsis where ")
						.append(" uhid='").append(uhid).append("'")
						.append(" and assessment_time <='").append(endTime).append("' order by assessment_time desc");


				List<Object[]> inActiveAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
				if (inActiveAssessmentList != null && inActiveAssessmentList.size() > 0) {
					if(!BasicUtils.isEmpty(inActiveAssessmentList.get(0))){
						String lastEventStatus = inActiveAssessmentList.get(0)[1].toString();
						if(lastEventStatus.equalsIgnoreCase("inactive")){
							inActive = "inactive";
						}else{
							inActive = "active";
						}
					}else {
						inActive = "active";
					}
				}

                return  inActive;

			}
			if (assessmentType.equalsIgnoreCase("NEC")) {


				StringBuilder query = new StringBuilder("SELECT  uhid,eventstatus  FROM sa_infection_nec where ")
						.append(" uhid='").append(uhid).append("'")
						.append(" and assessment_time <='").append(endTime).append("' order by assessment_time desc");


				List<Object[]> inActiveAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
				if (inActiveAssessmentList != null && inActiveAssessmentList.size() > 0) {
					if(!BasicUtils.isEmpty(inActiveAssessmentList.get(0))){
						String lastEventStatus = inActiveAssessmentList.get(0)[1].toString();
						if(lastEventStatus.equalsIgnoreCase("inactive")){
							inActive = "inactive";
						}else{
							inActive = "active";
						}
					}else {
						inActive = "active";
					}
				}

                return  inActive;

			}

			if (assessmentType.equalsIgnoreCase("VAP")) {


				StringBuilder query = new StringBuilder("SELECT  uhid,eventstatus  FROM sa_infection_vap where ")
						.append(" uhid='").append(uhid).append("'")
						.append(" and assessment_time <='").append(endTime).append("' order by assessment_time desc");


				List<Object[]> inActiveAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
				if (inActiveAssessmentList != null && inActiveAssessmentList.size() > 0) {
					if(!BasicUtils.isEmpty(inActiveAssessmentList.get(0))){
						String lastEventStatus = inActiveAssessmentList.get(0)[1].toString();
						if(lastEventStatus.equalsIgnoreCase("inactive")){
							inActive = "inactive";
						}else{
							inActive = "active";
						}
					}else {
						inActive = "active";
					}
				}

				return  inActive;

			}

			if (assessmentType.equalsIgnoreCase("clabsi")) {


				StringBuilder query = new StringBuilder("SELECT  uhid,eventstatus  FROM sa_infection_clabsi where ")
						.append(" uhid='").append(uhid).append("'")
						.append(" and assessment_time <='").append(endTime).append("' order by assessment_time desc");


				List<Object[]> inActiveAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
				if (inActiveAssessmentList != null && inActiveAssessmentList.size() > 0) {
					if(!BasicUtils.isEmpty(inActiveAssessmentList.get(0))){
						String lastEventStatus = inActiveAssessmentList.get(0)[1].toString();
						if(lastEventStatus.equalsIgnoreCase("inactive")){
							inActive = "inactive";
						}else{
							inActive = "active";
						}
					}else {
						inActive = "active";
					}
				}

				return  inActive;

			}


			if (assessmentType.equalsIgnoreCase("intrauterine")) {


				StringBuilder query = new StringBuilder("SELECT  uhid,eventstatus  FROM sa_infection_intrauterine  where " )
						.append(" uhid='").append(uhid).append("'")
						.append(" and assessment_time <='").append(endTime).append("' order by assessment_time desc");


				List<Object[]> inActiveAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
				if (inActiveAssessmentList != null && inActiveAssessmentList.size() > 0) {
					if(!BasicUtils.isEmpty(inActiveAssessmentList.get(0))){
						String lastEventStatus = inActiveAssessmentList.get(0)[1].toString();
						if(lastEventStatus.equalsIgnoreCase("inactive")){
							inActive = "inactive";
						}else{
							inActive = "active";
						}
					}else {
						inActive = "active";
					}
				}
				return  inActive;

			}


			if (assessmentType.equalsIgnoreCase("intrauterine")) {


				StringBuilder query = new StringBuilder("SELECT  uhid,eventstatus  FROM sa_infection_intrauterine  where " )
						.append(" uhid='").append(uhid).append("'")
						.append(" and assessment_time <='").append(endTime).append("' order by assessment_time desc");


				List<Object[]> inActiveAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
				if (inActiveAssessmentList != null && inActiveAssessmentList.size() > 0){
					if(!BasicUtils.isEmpty(inActiveAssessmentList.get(0))){
						String lastEventStatus = inActiveAssessmentList.get(0)[1].toString();
						if(lastEventStatus.equalsIgnoreCase("inactive")){
							inActive = "inactive";
						}else{
							inActive = "active";
						}
					}else {
						inActive = "active";
					}
				}

				return  inActive;

			}

			if (assessmentType.equalsIgnoreCase("Jaundice")) {


				StringBuilder query = new StringBuilder("SELECT  uhid,jaundicestatus FROM sa_jaundice where ")
						.append(" uhid='").append(uhid).append("'")
						.append(" and assessment_time <='").append(endTime).append("' order by assessment_time desc");


				List<Object[]> inActiveAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
				if (inActiveAssessmentList != null && inActiveAssessmentList.size() > 0) {
					if(!BasicUtils.isEmpty(inActiveAssessmentList.get(0))){
						String lastEventStatus = inActiveAssessmentList.get(0)[1].toString();
						if(lastEventStatus.equalsIgnoreCase("inactive")){
							inActive = "inactive";
						}else{
							inActive = "active";
						}
					}else {
						inActive = "active";
					}
				}

                return  inActive;

			}
			if (assessmentType.equalsIgnoreCase("Seizure")) {


				StringBuilder query = new StringBuilder("SELECT  uhid,eventstatus  FROM sa_cns_seizures where ")
						.append(" uhid='").append(uhid).append("'")
						.append(" and assessment_time <='").append(endTime).append("' order by assessment_time desc");

				List<Object[]> inActiveAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
				if (inActiveAssessmentList != null && inActiveAssessmentList.size() > 0) {
					if(!BasicUtils.isEmpty(inActiveAssessmentList.get(0))){
						String lastEventStatus = inActiveAssessmentList.get(0)[1].toString();
						if(lastEventStatus.equalsIgnoreCase("inactive")){
							inActive = "inactive";
						}else{
							inActive = "active";
						}
					}else {
						inActive = "active";
					}
				}

                return  inActive;

			}
			if (assessmentType.equalsIgnoreCase("Encephalopathy")) {


				StringBuilder query = new StringBuilder("SELECT  uhid,eventstatus  FROM sa_cns_encephalopathy where ")
						.append(" uhid='").append(uhid).append("'")
						.append(" and assessment_time <='").append(endTime).append("' order by assessment_time desc");


				List<Object[]> inActiveAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
				if (inActiveAssessmentList != null && inActiveAssessmentList.size() > 0) {
					if(!BasicUtils.isEmpty(inActiveAssessmentList.get(0))){
						String lastEventStatus = inActiveAssessmentList.get(0)[1].toString();
						if(lastEventStatus.equalsIgnoreCase("inactive")){
							inActive = "inactive";
						}else{
							inActive = "active";
						}
					}else {
						inActive = "active";
					}
				}

                return  inActive;

			}
			if (assessmentType.contains("Neuromuscular")) {


				StringBuilder query = new StringBuilder("SELECT  uhid,eventstatus  FROM sa_cns_neuromuscular_disorder where ")
						.append(" uhid='").append(uhid).append("'")
						.append(" and assessment_time <='").append(endTime).append("' order by assessment_time desc");


				List<Object[]> inActiveAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
				if (inActiveAssessmentList != null && inActiveAssessmentList.size() > 0) {
					if(!BasicUtils.isEmpty(inActiveAssessmentList.get(0))){
						String lastEventStatus = inActiveAssessmentList.get(0)[1].toString();
						if(lastEventStatus.equalsIgnoreCase("inactive")){
							inActive = "inactive";
						}else{
							inActive = "active";
						}
					}else {
						inActive = "active";
					}
				}

				return  inActive;

			}
			if (assessmentType.equalsIgnoreCase("ivh")) {


				StringBuilder query = new StringBuilder("SELECT  uhid,eventstatus  FROM sa_cns_ivh where ")
						.append(" uhid='").append(uhid).append("'")
						.append(" and assessment_time <='").append(endTime).append("' order by assessment_time desc");


				List<Object[]> inActiveAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
				if (inActiveAssessmentList != null && inActiveAssessmentList.size() > 0) {
					if(!BasicUtils.isEmpty(inActiveAssessmentList.get(0))){
						String lastEventStatus = inActiveAssessmentList.get(0)[1].toString();
						if(lastEventStatus.equalsIgnoreCase("inactive")){
							inActive = "inactive";
						}else{
							inActive = "active";
						}
					}else {
						inActive = "active";
					}
				}

				return  inActive;

			}
			if (assessmentType.equalsIgnoreCase("Hydrocephalus")) {


				StringBuilder query = new StringBuilder("SELECT  uhid,eventstatus FROM sa_cns_hydrocephalus where ")
						.append(" uhid='").append(uhid).append("'")
						.append(" and assessment_time <='").append(endTime).append("' order by assessment_time desc");


				List<Object[]> inActiveAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
				if (inActiveAssessmentList != null && inActiveAssessmentList.size() > 0) {
					if(!BasicUtils.isEmpty(inActiveAssessmentList.get(0))){
						String lastEventStatus = inActiveAssessmentList.get(0)[1].toString();
						if(lastEventStatus.equalsIgnoreCase("inactive")){
							inActive = "inactive";
						}else{
							inActive = "active";
						}
					}else {
						inActive = "active";
					}
				}

				return  inActive;

			}if (assessmentType.equalsIgnoreCase("Asphyxia")) {


				StringBuilder query = new StringBuilder("SELECT  uhid,eventstatus  FROM sa_cns_asphyxia where ")
						.append(" uhid='").append(uhid).append("'")
						.append(" and assessment_time <='").append(endTime).append("' order by assessment_time desc");


				List<Object[]> inActiveAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
				if (inActiveAssessmentList != null && inActiveAssessmentList.size() > 0) {
					if(!BasicUtils.isEmpty(inActiveAssessmentList.get(0))){
						String lastEventStatus = inActiveAssessmentList.get(0)[1].toString();
						if(lastEventStatus.equalsIgnoreCase("inactive")){
							inActive = "inactive";
						}else{
							inActive = "active";
						}
					}else {
						inActive = "active";
					}
				}

				return  inActive;

			}

			if (assessmentType.equalsIgnoreCase("Renal")) {


				StringBuilder query = new StringBuilder("SELECT  uhid, renal_status FROM sa_renalfailure where ")
						.append(" uhid='").append(uhid).append("'")
						.append(" and assessment_time <='").append(endTime).append("' order by assessment_time desc");


				List<Object[]> inActiveAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
				if (inActiveAssessmentList != null && inActiveAssessmentList.size() > 0) {
					if(!BasicUtils.isEmpty(inActiveAssessmentList.get(0))){
						String lastEventStatus = inActiveAssessmentList.get(0)[1].toString();
						if(lastEventStatus.equalsIgnoreCase("inactive")){
							inActive = "inactive";
						}else{
							inActive = "active";
						}
					}else {
						inActive = "active";
					}
				}

                return  inActive;

			}
			if (assessmentType.equalsIgnoreCase("Hypoglycemia")) {


				StringBuilder query = new StringBuilder("SELECT  uhid,hypoglycemia_event  FROM sa_hypoglycemia where ")
						.append(" uhid='").append(uhid).append("'")
						.append(" and assessment_time <='").append(endTime).append("' order by assessment_time desc");


				List<Object[]> inActiveAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
				if (inActiveAssessmentList != null && inActiveAssessmentList.size() > 0) {
					if(!BasicUtils.isEmpty(inActiveAssessmentList.get(0)) && !BasicUtils.isEmpty(inActiveAssessmentList.get(0)[1])){
						String lastEventStatus = inActiveAssessmentList.get(0)[1].toString();
						if(lastEventStatus.equalsIgnoreCase("inactive")){
							inActive = "inactive";
						}else{
							inActive = "active";
						}
					}else {
						inActive = "active";
					}
				}

                return  inActive;

			}
			if (assessmentType.equalsIgnoreCase("Shock")) {


				StringBuilder query = new StringBuilder("SELECT uhid,shockstatus FROM sa_shock where ")
						.append(" uhid='").append(uhid).append("'")
						.append(" and assessment_time <='").append(endTime).append("' order by assessment_time desc");


				List<Object[]> inActiveAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
				if (inActiveAssessmentList != null && inActiveAssessmentList.size() > 0) {
					if(!BasicUtils.isEmpty(inActiveAssessmentList.get(0))){
						String lastEventStatus = inActiveAssessmentList.get(0)[1].toString();
						if(lastEventStatus.equalsIgnoreCase("inactive")){
							inActive = "inactive";
						}else{
							inActive = "active";
						}
					}else {
						inActive = "active";
					}
				}

                return  inActive;

			}
			if (assessmentType.equalsIgnoreCase("Pain")) {


				StringBuilder query = new StringBuilder("SELECT  uhid,pain_status FROM sa_pain where ")
						.append(" uhid='").append(uhid).append("'")
						.append(" and assessment_time <='").append(endTime).append("' order by assessment_time desc");


				List<Object[]> inActiveAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
				if (inActiveAssessmentList != null && inActiveAssessmentList.size() > 0) {
					if(!BasicUtils.isEmpty(inActiveAssessmentList.get(0))){
						String lastEventStatus = inActiveAssessmentList.get(0)[1].toString();
						if(lastEventStatus.equalsIgnoreCase("inactive")){
							inActive = "inactive";
						}else{
							inActive = "active";
						}
					}else {
						inActive = "active";
					}
				}

                return  inActive;

			}
			if (assessmentType.contains("feed") || assessmentType.equalsIgnoreCase("FeedIntolerance")) {


				StringBuilder query = new StringBuilder("SELECT  uhid,feed_intolerance_status  FROM sa_feed_intolerance where ")
						.append(" uhid='").append(uhid).append("'")
						.append(" and assessment_time <='").append(endTime).append("' order by assessment_time desc");


				List<Object[]> inActiveAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
				if (inActiveAssessmentList != null && inActiveAssessmentList.size() > 0) {
					if(!BasicUtils.isEmpty(inActiveAssessmentList.get(0))){
						String lastEventStatus = inActiveAssessmentList.get(0)[1].toString();
						if(lastEventStatus.equalsIgnoreCase("inactive")){
							inActive = "inactive";
						}else{
							inActive = "active";
						}
					}else {
						inActive = "active";
					}
				}

                return  inActive;

			}


			String diseaseListQuery = "Select initcap(assessment_name) from ref_misc_dropdowns order by assessment_name";
			List<String> diseaseList = inicuDoa.getListFromNativeQuery(diseaseListQuery);

			if (diseaseList.contains(assessmentType)) {

				StringBuilder query = new StringBuilder("SELECT  uhid FROM sa_miscellaneous where ")
						.append(" uhid='").append(uhid).append("'")
						.append(" and assessment_time <='").append(endTime).append("'")
						.append(" and disease ='").append(assessmentType).append("'");



				List inActiveAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
				if (inActiveAssessmentList != null && inActiveAssessmentList.size() > 0) inActive = "true";

				query = new StringBuilder("SELECT  uhid FROM sa_miscellaneous_2 where ")
						.append(" uhid='").append(uhid).append("'")
						.append(" and assessment_time <='").append(endTime).append("'")
						.append(" and disease ='").append(assessmentType).append("'");



				 inActiveAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
				if (inActiveAssessmentList != null && inActiveAssessmentList.size() > 0) inActive = "true";


				return  inActive;

			}

		}catch(Exception e ){
			System.out.println(e);
		}


		return inActive;
	}

	public boolean checkInActiveAsssessment(String assessmentType,Long assId){

		boolean inActive = false ;

		try {
			if (assessmentType.equalsIgnoreCase("RDS")) {

				StringBuilder query = new StringBuilder("SELECT  eventstatus FROM sa_resp_rds where ")
						.append(" resprdsid='").append(assId).append("'")
						.append(" and ( eventstatus ='Inactive' or eventstatus ='inactive') ");


				List inActiveAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
				if (inActiveAssessmentList != null && inActiveAssessmentList.size() > 0) inActive = true;

			}
			if (assessmentType.equalsIgnoreCase("Apnea")) {


				StringBuilder query = new StringBuilder("SELECT  eventstatus FROM sa_resp_apnea where ")
						.append(" apneaid='").append(assId).append("'")
						.append(" and ( eventstatus ='Inactive' or eventstatus ='inactive') ");


				List inActiveAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
				if (inActiveAssessmentList != null && inActiveAssessmentList.size() > 0) inActive = true;

			}
			if (assessmentType.equalsIgnoreCase("PPHN")) {


				StringBuilder query = new StringBuilder("SELECT  eventstatus FROM sa_resp_pphn where ")
						.append(" resppphnid='").append(assId).append("'")
						.append(" and ( eventstatus ='Inactive' or eventstatus ='inactive') ");


				List inActiveAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
				if (inActiveAssessmentList != null && inActiveAssessmentList.size() > 0) inActive = true;

			}
			if (assessmentType.equalsIgnoreCase("Pneumothorax")) {


				StringBuilder query = new StringBuilder("SELECT  eventstatus FROM sa_resp_pneumothorax where ")
						.append(" resppneumothoraxid='").append(assId).append("'")
						.append(" and ( eventstatus ='Inactive' or eventstatus ='inactive') ");


				List inActiveAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
				if (inActiveAssessmentList != null && inActiveAssessmentList.size() > 0) inActive = true;

			}
			if (assessmentType.equalsIgnoreCase("Sepsis")) {


				StringBuilder query = new StringBuilder("SELECT  eventstatus FROM sa_infection_sepsis where ")
						.append(" sasepsisid='").append(assId).append("'")
						.append(" and ( eventstatus ='Inactive' or eventstatus ='inactive') ");


				List inActiveAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
				if (inActiveAssessmentList != null && inActiveAssessmentList.size() > 0) inActive = true;

			}
			if (assessmentType.equalsIgnoreCase("NEC")) {


				StringBuilder query = new StringBuilder("SELECT  eventstatus FROM sa_infection_nec where ")
						.append(" sanec_id='").append(assId).append("'")
						.append(" and ( eventstatus ='Inactive' or eventstatus ='inactive') ");


				List inActiveAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
				if (inActiveAssessmentList != null && inActiveAssessmentList.size() > 0) inActive = true;

			}
			if (assessmentType.equalsIgnoreCase("Jaundice")) {


				StringBuilder query = new StringBuilder("SELECT  jaundicestatus FROM sa_jaundice where ")
						.append(" sajaundiceid='").append(assId).append("'")
						.append(" and jaundicestatus ='Inactive'");


				List inActiveAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
				if (inActiveAssessmentList != null && inActiveAssessmentList.size() > 0) inActive = true;

			}
			if (assessmentType.equalsIgnoreCase("Seizures")) {


				StringBuilder query = new StringBuilder("SELECT  eventstatus FROM sa_cns_seizures where ")
						.append(" sacnsseizuresid='").append(assId).append("'")
						.append(" and ( eventstatus ='Inactive' or eventstatus ='inactive') ");


				List inActiveAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
				if (inActiveAssessmentList != null && inActiveAssessmentList.size() > 0) inActive = true;

			}
			if (assessmentType.equalsIgnoreCase("Asphyxia")) {


				StringBuilder query = new StringBuilder("SELECT  eventstatus FROM sa_cns_asphyxia where ")
						.append(" sacnsasphyxiaid='").append(assId).append("'")
						.append(" and ( eventstatus ='Inactive' or eventstatus ='inactive') ");


				List inActiveAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
				if (inActiveAssessmentList != null && inActiveAssessmentList.size() > 0) inActive = true;

			}
			if (assessmentType.equalsIgnoreCase("Renal")) {


				StringBuilder query = new StringBuilder("SELECT  renal_status FROM sa_renalfailure where ")
						.append(" renalid='").append(assId).append("'")
						.append(" and ( renal_status ='Inactive' or renal_status ='inactive') ");


				List inActiveAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
				if (inActiveAssessmentList != null && inActiveAssessmentList.size() > 0) inActive = true;

			}
			if (assessmentType.equalsIgnoreCase("Hypoglycemia")) {


				StringBuilder query = new StringBuilder("SELECT  eventstatus FROM sa_hypoglycemia where ")
						.append(" hypoglycemiaid='").append(assId).append("'")
						.append(" and ( eventstatus ='Inactive' or eventstatus ='inactive') ");


				List inActiveAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
				if (inActiveAssessmentList != null && inActiveAssessmentList.size() > 0) inActive = true;

			}
			if (assessmentType.equalsIgnoreCase("Shock")) {


				StringBuilder query = new StringBuilder("SELECT  shockstatus FROM sa_shock where ")
						.append(" sashockid='").append(assId).append("'")
						.append(" and shockStatus ='Inactive'");


				List inActiveAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
				if (inActiveAssessmentList != null && inActiveAssessmentList.size() > 0) inActive = true;

			}
			if (assessmentType.equalsIgnoreCase("Pain")) {


				StringBuilder query = new StringBuilder("SELECT  pain_status FROM sa_pain where ")
						.append(" painid='").append(assId).append("'")
						.append(" and ( eventstatus ='Inactive' or eventstatus ='inactive') ");


				List inActiveAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
				if (inActiveAssessmentList != null && inActiveAssessmentList.size() > 0) inActive = true;

			}
			if (assessmentType.equalsIgnoreCase("Enteral Feeding")) {


				StringBuilder query = new StringBuilder("SELECT  eventstatus FROM sa_feed_intolerance where ")
						.append(" feedintoleranceid='").append(assId).append("'")
						.append(" and feed_intolerance_status ='Inactive'");


				List inActiveAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
				if (inActiveAssessmentList != null && inActiveAssessmentList.size() > 0) inActive = true;

			}
			if (assessmentType.equalsIgnoreCase("Miscellaneous")) {


				StringBuilder query = new StringBuilder("SELECT  eventstatus FROM sa_miscellaneous where ")
						.append(" sa_miscellaneous_id='").append(assId).append("'")
						.append(" and ( eventstatus ='Inactive' or eventstatus ='inactive') ");


				List inActiveAssessmentList = inicuDoa.getListFromNativeQuery(query.toString());
				if (inActiveAssessmentList != null && inActiveAssessmentList.size() > 0) inActive = true;

			}
		}catch(Exception e ){
			System.out.println(e);
		}


		return inActive;
	}

	@Override
	public String getSepsisType(String diagnosis,String uhid) {
		double antibioticUsage = 0;
		
		Timestamp currentDate = null;
		Timestamp currentDateNew = new Timestamp(new java.util.Date().getTime());
		String medQuery = "select obj from BabyPrescription obj where uhid = '" + uhid + "' and medicationtype='TYPE0001' order by startdate asc";
		List<BabyPrescription> medList = inicuDoa.getListFromMappedObjQuery(medQuery);
		for(BabyPrescription obj : medList) {
			if(!BasicUtils.isEmpty(obj.getMedicineOrderDate())) {
				if(BasicUtils.isEmpty(obj.getEnddate())) {
					obj.setEnddate(currentDateNew);
				}
				if(currentDate == null || (!BasicUtils.isEmpty(obj.getStartdate()) && !BasicUtils.isEmpty(obj.getMedicineOrderDate()) && currentDate != null && obj.getStartdate().getTime() > currentDate.getTime())) {
					currentDate = obj.getEnddate();
					antibioticUsage += ((obj.getEnddate().getTime() - obj.getMedicineOrderDate().getTime()) / ( 60 * 60 * 1000));
				}else if(currentDate != null && obj.getEnddate().getTime() > currentDate.getTime()) {
					antibioticUsage += ((obj.getEnddate().getTime() - currentDate.getTime()) / (24 * 60 * 60 * 1000));
					currentDate = obj.getEnddate();
				}
			}
		}
		//returnObj.setAntibioticDaysSepsis(String.valueOf(antibioticUsage));

		if(antibioticUsage < 120 ) {
			diagnosis = diagnosis.replace("Sepsis", "Suspected Sepsis");
		}

		if(antibioticUsage >= 120) {
			diagnosis = diagnosis.replace("Sepsis", "Clinical Sepsis");
		}
			String query1 = "Select obj from SaSepsis as obj where uhid = '" + uhid + "' order by creationtime desc";
			List<SaSepsis> sepsisList = notesDoa.getListFromMappedObjNativeQuery(query1);
			if(!BasicUtils.isEmpty(sepsisList) && sepsisList!=null) {
				if(sepsisList.get(0).getBloodCultureStatus()!=null && sepsisList.get(0).getBloodCultureStatus().equalsIgnoreCase("positive")) {
					diagnosis = diagnosis.replace("Clinical Sepsis", "Confirmed Sepsis");
					diagnosis = diagnosis.replace("Suspected Sepsis", "Confirmed Sepsis");
					diagnosis = diagnosis.replace("Sepsis", "Confirmed Sepsis");
					diagnosis = diagnosis.replace("Confirmed Confirmed","Confirmed" );
				}
			}
		
		return diagnosis;
	}

	public String getDailyProgressNotesVitals(NursingVitalparameter latestVitalInfo) {
		String vitalInfoStr = "";
		if (!BasicUtils.isEmpty(latestVitalInfo.getHrRate())) {
			float hrRate = latestVitalInfo.getHrRate();
			if (vitalInfoStr == "") {
				vitalInfoStr = "HR: " + (int) hrRate + " bpm";
			} else {
				vitalInfoStr += ", HR: " + (int) hrRate + " bpm";
			}
		}
		if (!BasicUtils.isEmpty(latestVitalInfo.getCentraltemp())) {
			
			float tempChange = latestVitalInfo.getCentraltemp();
			if (!BasicUtils.isEmpty(latestVitalInfo.getCentralTempUnit())) {
				if(latestVitalInfo.getCentralTempUnit().equalsIgnoreCase("F")) {
					tempChange = ((latestVitalInfo.getCentraltemp() - 32) * 5) / 9;
				}
			}
			
			if (vitalInfoStr == "") {
				vitalInfoStr = "Temp: " + String.format("%.01f", tempChange) + " (C)";
			} else {
				vitalInfoStr += ", Temp: " + String.format("%.01f", tempChange) + " (C)";
			}
		}else if (!BasicUtils.isEmpty(latestVitalInfo.getPeripheraltemp())) {
			
			float tempChange = latestVitalInfo.getPeripheraltemp();
			if (!BasicUtils.isEmpty(latestVitalInfo.getCentralTempUnit())) {
				if(latestVitalInfo.getCentralTempUnit().equalsIgnoreCase("F")) {
					tempChange = ((latestVitalInfo.getPeripheraltemp() - 32) * 5) / 9;
				}
			}
			
			if (vitalInfoStr == "") {
				vitalInfoStr = "Temp: " + String.format("%.01f", tempChange) + " (C)";
			} else {
				vitalInfoStr += ", Temp: " + String.format("%.01f", tempChange) + " (C)";
			}
		}
		if (!BasicUtils.isEmpty(latestVitalInfo.getRrRate())) {
			float rrRate = latestVitalInfo.getRrRate();
			if (vitalInfoStr == "") {
				vitalInfoStr = "RR: " + (int) rrRate + " pm";
			} else  {
				vitalInfoStr += ", RR: " + (int) rrRate + " pm";
			}
		}
		if (!BasicUtils.isEmpty(latestVitalInfo.getSpo2())) {
			if (vitalInfoStr == "") {
				vitalInfoStr = "SpO2: " + (latestVitalInfo.getSpo2()) + " %";
			} else {
				vitalInfoStr += ", SpO2: " + (latestVitalInfo.getSpo2()) + " %";
			}
		}


		if (!BasicUtils.isEmpty(latestVitalInfo.getSystBp())) {
			if (vitalInfoStr != "")  vitalInfoStr +=", ";

			vitalInfoStr += "BP: " + latestVitalInfo.getSystBp();

			if ( !BasicUtils.isEmpty(latestVitalInfo.getDiastBp()))  vitalInfoStr += "/"+  latestVitalInfo.getDiastBp();

			vitalInfoStr +=  " mmHg";

		}else if (!BasicUtils.isEmpty(latestVitalInfo.getSystiBp())) {
			if (vitalInfoStr != "")  vitalInfoStr +=", ";

			vitalInfoStr += "BP: " + latestVitalInfo.getSystiBp();

			if ( !BasicUtils.isEmpty(latestVitalInfo.getDiastiBp()))  vitalInfoStr += "/"+  latestVitalInfo.getDiastiBp();

			vitalInfoStr +=  " mmHg";

		}

		if (!BasicUtils.isEmpty(latestVitalInfo.getRbs())) {
			if (vitalInfoStr == "") {
				vitalInfoStr = "RBS: " + (latestVitalInfo.getRbs()) + " mg/dl";
			} else {
				vitalInfoStr += ", RBS: " + (latestVitalInfo.getRbs()) + " mg/dl";
			}
		}


		if (!BasicUtils.isEmpty(latestVitalInfo.getTcb())) {
			if (vitalInfoStr == "") {
				vitalInfoStr = "TcB: " + (latestVitalInfo.getTcb()) + " mg/dl";
			} else {
				vitalInfoStr += ", TcB: " + (latestVitalInfo.getTcb()) + " mg/dl";
			}
		}


		return vitalInfoStr;
	}



	public NursingVitalparameter getDailyProgressNotesVitalsInGivenTime(String uhid, Timestamp fromDate, Timestamp toDate, Timestamp assessmentTime, long offset) {

		String queryCurrentBabyVitals = "select obj from NursingVitalparameter as obj where uhid='" + uhid
				+ "' and entryDate <= '" + fromDate + "' and entryDate >= '" + toDate
				+ "' order by entryDate asc";
		List<NursingVitalparameter> currentBabyVitalList = (List<NursingVitalparameter>) inicuDoa
				.getListFromMappedObjRowLimitQuery(queryCurrentBabyVitals, 12);
		long vitalsNearMorningDate = 0;
		long vitalsNearMorningDateOld = 0;

		NursingVitalparameter latestVitalInfo = null;
		if (!BasicUtils.isEmpty(currentBabyVitalList)) {

			for (NursingVitalparameter vitalparameter : currentBabyVitalList) {

				if (!BasicUtils.isEmpty(vitalparameter.getSystBp()) || !BasicUtils.isEmpty(vitalparameter.getDiastBp()) || !BasicUtils.isEmpty(vitalparameter.getPulserate()) || !BasicUtils.isEmpty(vitalparameter.getRrRate()) || !BasicUtils.isEmpty(vitalparameter.getSpo2()) || !BasicUtils.isEmpty(vitalparameter.getHrRate())) {
					System.out.println("assessmentTime-----------------------" + assessmentTime);

					if (assessmentTime != null) {
						System.out.println("assessmentTime-----------------------" + assessmentTime);

						if (vitalsNearMorningDateOld == 0)
							vitalsNearMorningDateOld = (vitalparameter.getEntryDate().getTime() - offset) - assessmentTime.getTime();
						vitalsNearMorningDate = (vitalparameter.getEntryDate().getTime() - offset) - assessmentTime.getTime();
						System.out.println("vitalsNearMorningDateOld-----------------------" + vitalsNearMorningDateOld);
						System.out.println("vitalsNearMorningDate-----------------------" + vitalsNearMorningDate);

						if (Math.abs(vitalsNearMorningDate) <= Math.abs(vitalsNearMorningDateOld)) {
							latestVitalInfo = vitalparameter;
							System.out.println("latestVitalInfo-----------------------" + latestVitalInfo);

							vitalsNearMorningDateOld = (vitalparameter.getEntryDate().getTime() - offset) - assessmentTime.getTime();
						}
					} else {
						latestVitalInfo = vitalparameter;
						break;
					}
				}

			}
		}
		return latestVitalInfo;
	}


	public String getDailyProgressNotesVitalsMessage(NursingVitalparameter vitalObj, boolean preterm) {


		String vitalInfoStr = "";
		boolean isTemp = true;
		float centralTemp = 0;
		double centralTemplow = 36.5;
		double centralTempHigh = 37.4;
		String centralTempAbnormalConditionName = "";

//
		boolean isHeartRate = true;
		int heartRate = 0;
		int heartRatelow = 90;
		int heartRateHigh = 150;
		String heartRateAbnormalConditionName = "";


		boolean ismeanbp = true;
		int meanbp = 0;
		int meanbplow = 59;
		int meanbpHigh = 73;
		String meanbpAbnormalConditionName = "";

		boolean isCRT = true;
		int crt = 0;
		//int crtlow = 59;
		int crtHigh = 4;
		//int precrtlow = 38;
		int precrthigh = 4;
		String crtAbnormalConditionName = "";

		if (preterm) {
			heartRatelow = 120;
			heartRateHigh = 170;
			meanbplow = 38;
			meanbpHigh = 57;
		}


		boolean vitalsGiven = false;

		List<String> tcbValueList = new ArrayList<String>();
		List<String> tcbValueTime = new ArrayList<String>();

		List<Float> rbsValueList = new ArrayList<Float>();
		List<String> rbsValueTime = new ArrayList<String>();


			if (!BasicUtils.isEmpty(vitalObj.getHrRate()) || !BasicUtils.isEmpty(vitalObj.getMeanBp()) || !BasicUtils.isEmpty(vitalObj.getCft()) || !BasicUtils.isEmpty(vitalObj.getCentraltemp())) {
				vitalsGiven = true;
			}

//				if(!BasicUtils.isEmpty(vitalObj.getCentraltemp()) && (vitalObj.getCentraltemp() < 36.5 || vitalObj.getCentraltemp() > 37.5)) {
//					isTemp = false;
//					float var = vitalObj.getCentraltemp();
//					centralTemp = (int)var;
//				}
			if (!BasicUtils.isEmpty(vitalObj.getHrRate()) && (vitalObj.getHrRate() < heartRatelow || vitalObj.getHrRate() > heartRateHigh)) {
				isHeartRate = false;
				float var1 = vitalObj.getHrRate();
				heartRate = (int) var1;

				if (vitalObj.getHrRate() < heartRatelow) heartRateAbnormalConditionName = "bradycardia";
				else heartRateAbnormalConditionName = "tachycardia";
			}
			if (!BasicUtils.isEmpty(vitalObj.getMeanBp()) && (Integer.parseInt(vitalObj.getMeanBp().trim()) < meanbplow || Integer.parseInt(vitalObj.getMeanBp().trim()) > meanbpHigh)) {
				ismeanbp = false;
				int var1 = Integer.parseInt(vitalObj.getMeanBp().trim());
				meanbp = (int) var1;

				if (Integer.parseInt(vitalObj.getMeanBp().trim()) < meanbplow) meanbpAbnormalConditionName = "hypotensive";
				else meanbpAbnormalConditionName = "hypertensive";
			}

			if (!BasicUtils.isEmpty(vitalObj.getCentraltemp()) && (vitalObj.getCentraltemp() < centralTemplow || vitalObj.getCentraltemp() > centralTempHigh)) {
				isTemp = false;
				float var1 = vitalObj.getCentraltemp();
				centralTemp = var1;

				if (vitalObj.getCentraltemp() < centralTemplow) centralTempAbnormalConditionName = "hypothermic";
				else centralTempAbnormalConditionName = "hyperthermic";
			}

			if (!BasicUtils.isEmpty(vitalObj.getCft())) {
				crt = Integer.parseInt(vitalObj.getCft());
				if (crt > 4) {
					isCRT = false;
					crtAbnormalConditionName = "hypertensive";
				}
			}

//				if(!BasicUtils.isEmpty(vitalObj.getSpo2())) {
//					int spo2Rate1 = (int) parseFloat(vitalObj.getSpo2());
//
//					if(spo2Rate1 < 90) {
//						isSpo2Rate = false;
//						spo2Rate = (int) parseFloat(vitalObj.getSpo2());
//
//					}
//				}
//				if(!BasicUtils.isEmpty(vitalObj.getRrRate()) && (vitalObj.getRrRate() < 40 || vitalObj.getRrRate() > 60)) {
//					isRRRate = false;
//					float var2 = vitalObj.getRrRate();
//					rrRate = (int)var2;
//				}
			if (!BasicUtils.isEmpty(vitalObj.getTcb())) {
				tcbValueList.add(vitalObj.getTcb());
				String tcbTime = vitalObj.getEntryDate().getHours() + ":00";
				tcbValueTime.add(tcbTime);
			}
			if (!BasicUtils.isEmpty(vitalObj.getRbs())) {

				rbsValueList.add((vitalObj.getRbs()));
				String rbsTime = vitalObj.getEntryDate().getHours() + ":00";
				rbsValueTime.add(rbsTime);
			}



		if(vitalsGiven) {
			if (isHeartRate && ismeanbp && isCRT && isTemp) {
// 					vitalInfoStr = " vitals were normal, baby remained hemodynamically stable. ";
				vitalInfoStr = " Baby's vitals were normal and hemodynamically stable. ";
			} else {
				vitalInfoStr += "Baby ";

				String unstableParamsHad = "";
				String unstableParamsWas = "";

				//if (!isTemp) unstableParams += "Temperature: " + centralTemp + " (C)";

				if (!isHeartRate) {
					if (unstableParamsHad.length() > 1) unstableParamsHad += ", ";
					unstableParamsHad += heartRateAbnormalConditionName;
				}

				if (!ismeanbp) {
					if (unstableParamsWas.length() > 1) unstableParamsWas += ", ";
					unstableParamsWas += meanbpAbnormalConditionName;
				}

				if (!isCRT) {
					if (unstableParamsHad.length() > 1) unstableParamsHad += ", ";
					unstableParamsHad += crtAbnormalConditionName;
				}

				if (!isTemp) {
					if (unstableParamsWas.length() > 1) unstableParamsWas += ", ";
					unstableParamsWas += centralTempAbnormalConditionName;
				}

				unstableParamsHad = formatString(unstableParamsHad);
				unstableParamsWas = formatString(unstableParamsWas);

				if (unstableParamsHad.length() > 1) vitalInfoStr += " had " + unstableParamsHad;

				if (unstableParamsWas.length() > 1) {
					if (unstableParamsHad.length() > 1) {
						vitalInfoStr += " and ";
					}
					vitalInfoStr += " was " + unstableParamsWas;
				}

				vitalInfoStr += ". ";
				String stableParams = "";

				if (isHeartRate) {
					if (stableParams.length() > 1) stableParams += ", ";
					stableParams += "Heart Rate";
				}
				if (ismeanbp) {
					if (stableParams.length() > 1) stableParams += ", ";
					stableParams += "Mean BP";
				}
				if (isTemp) {
					if (stableParams.length() > 1) stableParams += ", ";
					stableParams += "Temperature";
				}
				if (isCRT) {
					if (stableParams.length() > 1) stableParams += ", ";
					stableParams += "CRT";
				}

				stableParams = formatString(stableParams);

				if (stableParams.indexOf(" and ") != -1) vitalInfoStr += stableParams + " remained normal.";
				else vitalInfoStr += stableParams + " remained normal.";
			}
		}
		return vitalInfoStr;
	}


	public String getDailyProgressNotesProcedure(String uhid, Timestamp fromDate, Timestamp toDate) {
		List<String> procedureDetailstr  = (List<String>) inicuDoa.getListFromNativeQuery(HqlSqlQueryConstants.getRemovedProcedures(uhid, fromDate, toDate));

		String removedStr = "";
		String removedStrQ = "";

		if (!BasicUtils.isEmpty(procedureDetailstr)) {
			for (String procedure : procedureDetailstr) {
				if (procedure!=null && !procedure.equalsIgnoreCase("Therapeutic Hypothermia")) {
					if(removedStr.length()>1) removedStr += ", ";
					if(removedStrQ.length()>1) removedStrQ += ", ";

					removedStr += procedure;
					removedStrQ +=  "'"+procedure+"'";
				}
			}
		}

		String procedureStr = "";
		List<Object[]> procedureDetail = (List<Object[]>) inicuDoa
				.getListFromNativeQuery(HqlSqlQueryConstants.getCurrentProcedureUsageDays(uhid, fromDate, toDate, ""));
		if (!BasicUtils.isEmpty(procedureDetail)) {
			int i = 0;
			for (Object[] procedure : procedureDetail) {
				if (procedure[0]!=null) {
					if(procedure[0].toString().equalsIgnoreCase("Therapeutic Hypothermia") && i==0) {
						i++;
						//procedureStr +=  procedure[0]+" (D"+String.format("%.0f",procedure[1])+")."+htmlLine;

						if(procedureStr.indexOf(procedure[0].toString()) ==-1) {
							procedureStr += procedure[0] + htmlLine;
						}
					}else if(!procedure[0].toString().equalsIgnoreCase("Therapeutic Hypothermia")){
						//procedureStr +=  procedure[0]+" (D"+String.format("%.0f",procedure[1])+")."+htmlLine;
						//procedureStr +=  procedure[0]+htmlLine;
						if(procedureStr.indexOf(procedure[0].toString()) ==-1) {
							procedureStr += procedure[0] + htmlLine;
						}
					}
				}
			}
		}


		procedureDetail = (List<Object[]>) inicuDoa
				.getListFromNativeQuery(HqlSqlQueryConstants.getProcedureSites(uhid));
		if (!BasicUtils.isEmpty(procedureDetail)) {
			for (Object[] procedure : procedureDetail) {
				if (procedure[0]!=null) {
					if(procedureStr.indexOf(procedure[0].toString())!=-1 && !BasicUtils.isEmpty(procedure[1]) ) procedureStr = procedureStr.replaceFirst(procedure[0].toString()+" \\(",procedure[0].toString()+" \\("+procedure[1].toString()+", ");
				}
			}
		}

		if (!BasicUtils.isEmpty(procedureDetailstr)) {
			if(removedStr.length()>1) {
				if (procedureStr.length() > 1) procedureStr += htmlLine;
				procedureStr +=  removedStr+" removed.";
			}
		}
		return procedureStr;
	}

	public String getDailyProgressNotesProcedurePast(String uhid, Timestamp fromDate, Timestamp toDate) {
		String procedureStr = "";
		List<String> procedureDetail = (List<String>) inicuDoa.getListFromNativeQuery(HqlSqlQueryConstants.getCurrentProcedureUsage(uhid, fromDate, toDate));
		if (!BasicUtils.isEmpty(procedureDetail)) {
			for (String procedureName : procedureDetail) {
				if (procedureStr.isEmpty()) {
					procedureStr = "" + "Baby was on " + procedureName;
				} else {
					procedureStr += ", " + procedureName;
				}
			}
			procedureStr +=  ". ";
		}
		return procedureStr;
	}

	public String getDailyProgressNotesScreening(String uhid, Timestamp fromDate, Timestamp toDate) {
		String screeningStr = "";
		List<String> screeningDetail = (List<String>) inicuDoa
				.getListFromNativeQuery(HqlSqlQueryConstants.getCurrentScreening(uhid, fromDate, toDate));
		if (!BasicUtils.isEmpty(screeningDetail)) {
			for (String screeningName : screeningDetail) {
				if (screeningStr.isEmpty()) {
					screeningStr = screeningName;
				} else {
					screeningStr += ", " + screeningName;
				}
			}
			if(screeningStr.contains(","))
				screeningStr += " screening were done. ";
			else
				screeningStr += " screening was done. ";
		}
		return screeningStr;
	}

	//Blood Product Notes Fetching Function
	public String getDailyProgressNotesBloodProduct(String uhid, Timestamp fromDate, Timestamp toDate) {
		String htmlNextLine = System.getProperty("line.separator");
		String bloodProductStr = "";
		List<DoctorBloodProducts> bloodProductDetailList = (List<DoctorBloodProducts>)inicuDoa.
				getListFromMappedObjQuery(HqlSqlQueryConstants.getCurrentBloodProductList(uhid, fromDate, toDate));
		if (!BasicUtils.isEmpty(bloodProductDetailList)) {
			for(int i=0;i<bloodProductDetailList.size();i++) {
				if(!BasicUtils.isEmpty(bloodProductDetailList.get(i).getProgress_notes())) {
					bloodProductStr += getDateFromTimestamp(bloodProductDetailList.get(i).getAssessment_time()) + " - "
							+ bloodProductDetailList.get(i).getProgress_notes() + ". " + htmlNextLine;
				}
			}
		}
		return bloodProductStr;
	}

	public String getDailyProgressNotesInputOutput(String uhid, Timestamp fromDate, Timestamp toDate, float todayWeight) {
		fromDate = new Timestamp(fromDate.getTime());
		Long duration  = (toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60);
		String htmlNextLine = System.getProperty("line.separator");// "&#13;&#10;";
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm a");

		//Fetching blood product delivered volume to be shown in PN
		float totalBloodProductVolume = 0;
		List<NursingBloodproduct> bloodProductList = inicuDoa.getListFromMappedObjQuery(
				HqlSqlQueryConstants.getNursingBloodProductList(uhid, fromDate, toDate));
		if (!BasicUtils.isEmpty(bloodProductList)) {
			for (NursingBloodproduct item : bloodProductList) {
				if (!BasicUtils.isEmpty(item.getDelivered_volume())) {
					totalBloodProductVolume += item.getDelivered_volume();
				}
			}
		}

		//Fetching Heplock delivered volume to be shown in PN
		float totalHeplockVolume = 0;
		List<NursingHeplock> heplockList = inicuDoa.getListFromMappedObjQuery(
				HqlSqlQueryConstants.getNursingHeplockList(uhid, fromDate, toDate));
		if (!BasicUtils.isEmpty(heplockList)) {
			for (NursingHeplock item : heplockList) {
				if (!BasicUtils.isEmpty(item.getDeliveredVolume())) {
					totalHeplockVolume += item.getDeliveredVolume();
				}
			}
		}

		//Fetching Medication delivered volume to be shown in PN
		float totalMedicineVolume = 0;
		List<NursingMedication> medicationLists = inicuDoa.getListFromMappedObjQuery(
				HqlSqlQueryConstants.getNursingMedicationList(uhid, fromDate, toDate));
		if (!BasicUtils.isEmpty(medicationLists)) {
			for (NursingMedication item : medicationLists) {
				if (!BasicUtils.isEmpty(item.getDelivered_volume())) {
					totalMedicineVolume += item.getDelivered_volume();
				}
			}
		}

		String notesText = "";
		float totalEn = 0;
		float totalPn = 0;
		float ivValue = 0;
		//Planned Input
		List<BabyfeedDetail> listBabyFeedDetails = (List<BabyfeedDetail>) inicuDoa
				.getListFromMappedObjQuery(HqlSqlQueryConstants.getBabyFeedList(uhid, fromDate, toDate));
		// calculating feed calculator....
		FeedCalculatorPOJO calculator = new FeedCalculatorPOJO();
		String deficitsStr = "";

		if (!BasicUtils.isEmpty(listBabyFeedDetails)) {

			BabyfeedDetail babyFeedDetailsObj = listBabyFeedDetails.get(0);

			String nutritionCalculator = "select obj from RefNutritioncalculator obj";
			List<RefNutritioncalculator> nutritionList = notesDoa.getListFromMappedObjNativeQuery(nutritionCalculator);
			calculator.setRefNutritionInfo(nutritionList);

			if(!BasicUtils.isEmpty(todayWeight)) {
				float energy = 0;
				float protein = 0;
				String weight = (todayWeight / 1000) + "";
				List<BabyfeedDetail> dummyFeedList = new ArrayList<BabyfeedDetail>();
				dummyFeedList.add(babyFeedDetailsObj);
				CaclulatorDeficitPOJO cuurentDeficitLast = getDeficitFeedCalculatorInput(uhid, dummyFeedList, nutritionList,
						weight, fromDate, toDate);
				calculator.setLastDeficitCal(cuurentDeficitLast);
				if(!BasicUtils.isEmpty(calculator.getLastDeficitCal().getParentalIntake().get("Protein"))) {
					protein = calculator.getLastDeficitCal().getParentalIntake().get("Protein");
				}
				if(!BasicUtils.isEmpty(calculator.getLastDeficitCal().getEnteralIntake().get("Protein"))) {
					protein += calculator.getLastDeficitCal().getEnteralIntake().get("Protein");
				}
				if(!BasicUtils.isEmpty(calculator.getLastDeficitCal().getParentalIntake().get("Energy"))) {
					energy = calculator.getLastDeficitCal().getParentalIntake().get("Energy");
				}
				if(!BasicUtils.isEmpty(calculator.getLastDeficitCal().getEnteralIntake().get("Energy"))) {
					energy += calculator.getLastDeficitCal().getEnteralIntake().get("Energy");
				}
				if(energy != 0)
					deficitsStr = ", Energy : " + (Math.round(energy*100)/100) + " cal/kg/day";
				if(protein != 0)
					deficitsStr += ", Protein : " + (Math.round(protein*100)/100) + " gm/kg/day";
				if(energy != 0 || protein != 0)
					deficitsStr += ". ";

			}

			if(!BasicUtils.isEmpty(babyFeedDetailsObj.getTotalenteralvolume())) {
				totalEn += babyFeedDetailsObj.getTotalenteralvolume();
			}

			if(!BasicUtils.isEmpty(babyFeedDetailsObj.getTotalparenteralvolume())) {
				totalPn += babyFeedDetailsObj.getTotalparenteralvolume();
			}

			if(!BasicUtils.isEmpty(babyFeedDetailsObj.getTotalMedvolume())) {
				ivValue += babyFeedDetailsObj.getTotalMedvolume();
			}

			if(!BasicUtils.isEmpty(todayWeight)) {
				if(totalEn != 0 || totalPn != 0) {

					String intakeOutputText = "Planned Input : " + String.format("%.02f", ((totalPn + totalEn) / (todayWeight))) +
							" mL/kg/day (";

					if(totalEn != 0) {
						intakeOutputText +=  "EN - " + String.format("%.02f", (totalEn / (todayWeight))) +
								" mL/kg/day ";
					}
					if(totalPn != 0) {
						intakeOutputText +=  "PN - " + String.format("%.02f", (totalPn / (todayWeight))) +
								" mL/kg/day ";
					}
					if(ivValue != 0) {
						intakeOutputText +=  "IV - " + String.format("%.02f", (ivValue / (todayWeight))) +
								" mL/kg/day";
					}
					intakeOutputText += ").";
					notesText += intakeOutputText;

				}
				notesText += htmlNextLine;
			}
		}

		totalEn = 0;
		totalPn = 0;
		ivValue = 0;
		int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
				- TimeZone.getDefault().getRawOffset();

		//Given Feeds & Output
		String finalvolumeFinalStr = "";
		List<NursingIntakeOutput> listIntakeOutputDetails = (List<NursingIntakeOutput>) inicuDoa
				.getListFromMappedObjQuery(HqlSqlQueryConstants.getNursingIntakeOutputList(uhid, fromDate, toDate));
		if (!BasicUtils.isEmpty(listIntakeOutputDetails)) {
			HashMap<String, List<NursingIntakeOutput>> intakeOutputMap = new HashMap<String, List<NursingIntakeOutput>>() {
				private static final long serialVersionUID = 1L;
				{
					put("enteral", new ArrayList<>());
					put("parenteral", new ArrayList<>());
					put("output", new ArrayList<>());
					put("comment", new ArrayList<>());
				}
			};

			Collections.reverse(listIntakeOutputDetails);
			for (NursingIntakeOutput item : listIntakeOutputDetails) {
				//Enteral & Parenteral Comments
				if (!BasicUtils.isEmpty(item.getEnteralComment()) && !BasicUtils.isEmpty(item.getEnteralComment())) {
					intakeOutputMap.get("comment").add(item);
				}
				else {
					if (!BasicUtils.isEmpty(item.getEnteralComment())) {
						intakeOutputMap.get("comment").add(item);
					}
					else {
						if (!BasicUtils.isEmpty(item.getEnteralComment())) {
							intakeOutputMap.get("comment").add(item);
						}
					}
				}

				if ((!BasicUtils.isEmpty(item.getRoute()) && item.getRoute().equalsIgnoreCase("Breast Feed")) || item.getPrimaryFeedValue() != null
						|| item.getFormulaValue() != null || item.getActualFeed() != null) {
					intakeOutputMap.get("enteral").add(item);
					if(item.getActualFeed() != null) {
						totalEn = totalEn + item.getActualFeed();
					}
				}

				if ((item.getBolus_executed() != null && item.getBolus_executed())
						|| item.getTpn_delivered() != null || item.getLipid_delivered() != null || item.getReadymadeDeliveredFeed() != null
						|| totalMedicineVolume != 0 || totalHeplockVolume != 0 || totalBloodProductVolume != 0) {
					intakeOutputMap.get("parenteral").add(item);
					if(item.getTpn_delivered() != null) {
						totalPn = totalPn + item.getTpn_delivered();
					}
					if(item.getLipid_delivered() != null) {
						totalPn = totalPn + item.getLipid_delivered();
					}
					if(item.getReadymadeDeliveredFeed() != null) {
						totalPn = totalPn + item.getReadymadeDeliveredFeed();
					}
					if(item.getBolusDeliveredFeed() != null){
						totalPn = totalPn + item.getBolusDeliveredFeed();
					}
				}

				if (item.getGastricAspirate() != null || (item.getUrinePassed() != null && item.getUrinePassed())
						|| (item.getStoolPassed() != null && item.getStoolPassed())
						|| (item.getVomitPassed() != null && item.getVomitPassed()) || item.getDrain() != null) {
					intakeOutputMap.get("output").add(item);
				}
			}

			if (BasicUtils.isEmpty(intakeOutputMap.get("enteral"))) {
				//notesText += "The baby is on NPO. " + htmlNextLine;
			} else {
				float primaryFeed = 0;
				float formulaFeed = 0;
				boolean breastMilk = false;
				String routeStr = "";
				String volumeFrequencyStr = "";
				HashMap<String, Integer> differentVolumes = new HashMap<String, Integer>();
				for (NursingIntakeOutput item : intakeOutputMap.get("enteral")) {
					if (!BasicUtils.isEmpty(item.getRoute()) && item.getRoute().equalsIgnoreCase("Breast Feed")) {
						breastMilk = true;
					} else {
						if (!BasicUtils.isEmpty(item.getRoute()) && !routeStr.contains(item.getRoute())) {
							routeStr += ", " + item.getRoute();
						}

						if (!(item.getPrimaryFeedValue() == null || item.getPrimaryFeedValue() == 0)) {
							primaryFeed += item.getPrimaryFeedValue();
						}

						if (!(item.getFormulaValue() == null || item.getFormulaValue() == 0)) {
							formulaFeed += item.getFormulaValue();
						}

						if (!(item.getActualFeed() == null || item.getActualFeed() == 0)) {
							if (differentVolumes.containsKey("" + item.getActualFeed())) {
								differentVolumes.put("" + item.getActualFeed(), differentVolumes.get("" + item.getActualFeed()) + 1);
							} else {
								differentVolumes.put("" + item.getActualFeed(), 1);
							}
						}
					}
				}

		String frequencyString = "";
		if(!BasicUtils.isEmpty(listBabyFeedDetails)){
			for(BabyfeedDetail feed : listBabyFeedDetails) {
				if(!BasicUtils.isEmpty(feed.getFeedduration())) {
					frequencyString = " q " + feed.getFeedduration() + " hrly";
				}
			}
		}

		for (Map.Entry<String, Integer> entry : differentVolumes.entrySet()) {
			if(!BasicUtils.isEmpty(volumeFrequencyStr)) {
				volumeFrequencyStr += ", " + entry.getKey() + "(" + entry.getValue() + ")";
			}
			else {
				if (differentVolumes.size() > 1) {
					volumeFrequencyStr = " " + entry.getKey() + "(" + entry.getValue() + ")";
				} else {
					volumeFrequencyStr = " " + entry.getKey();
				}
			}
		}

		if(!BasicUtils.isEmpty(volumeFrequencyStr) && differentVolumes.size() > 1) {
			finalvolumeFinalStr = volumeFrequencyStr;
		} else if(!BasicUtils.isEmpty(volumeFrequencyStr) && differentVolumes.size() == 1  && !BasicUtils.isEmpty(frequencyString)) {
			finalvolumeFinalStr = volumeFrequencyStr + " mL" + frequencyString;
		} else if(!BasicUtils.isEmpty(volumeFrequencyStr)) {
			finalvolumeFinalStr = volumeFrequencyStr + " mL";
		}

				if (breastMilk) {
					//notesText += "The baby was on direct Breast Feed. ";
				}

				if (!routeStr.isEmpty() && (primaryFeed != 0 || formulaFeed != 0)) {
					//notesText += "The baby was given " + (primaryFeed + formulaFeed) + " mL of feed through " + routeStr.substring(2);
					if (primaryFeed != 0 && formulaFeed != 0) {
						//notesText += ", received " + primaryFeed + " mL of fortified human milk and " + formulaFeed + " mL of formula milk. ";
					} else if (primaryFeed != 0) {
						//notesText += ", received " + primaryFeed + " mL. ";
					} else if (formulaFeed != 0) {
						//notesText += ", received " + primaryFeed + " mL of formula milk. ";
					}
				}
				//notesText += htmlNextLine;
			}

			if(totalMedicineVolume != 0) {
				ivValue = ivValue + totalMedicineVolume;
			}
			if(totalHeplockVolume != 0) {
				ivValue = ivValue + totalHeplockVolume;
			}
			if(totalBloodProductVolume != 0){
				ivValue = ivValue + totalBloodProductVolume;
			}

			float lipidValue = 0;
			float tpnValue = 0;
			float bolusValue = 0;
			if (!BasicUtils.isEmpty(intakeOutputMap.get("parenteral"))) {
				List<NursingIntakeOutput> pnList = intakeOutputMap.get("parenteral");


				for (NursingIntakeOutput item : pnList) {
					if (item.getBolus_executed() != null && item.getBolus_executed()
							&& !BasicUtils.isEmpty(item.getParenteralComment())) {
						notesText += item.getParenteralComment() + " ";
					}

					if (null != item.getLipid_delivered()) {
						lipidValue += item.getLipid_delivered();
					}

					if (null != item.getTpn_delivered()) {
						tpnValue += item.getTpn_delivered();
					}

//					if (null != item.getReadymadeDeliveredFeed()) {
//						ivValue += item.getReadymadeDeliveredFeed();
//					}

					if (null != item.getBolusDeliveredFeed()) {
						bolusValue += item.getBolusDeliveredFeed();
					}
				}

				if(bolusValue > 0){
					//notesText += "Bolus given to the baby " + bolusValue + " mL. ";
				}
				if(ivValue > 0){
					//notesText += "The baby had received " + Math.round(ivValue * 100) / 100 + " mL of readymade solution. " + htmlNextLine;
				}
				else{
					if (lipidValue > 0 && tpnValue > 0) {
						//notesText += "The baby had received " + lipidValue + " mL of Lipid IV and " + tpnValue + " mL of TPN solution. " + htmlNextLine;
					} else if (lipidValue > 0) {
						//notesText += "The baby had received " + lipidValue + " mL of Lipid IV. " + htmlNextLine;
					} else if (tpnValue > 0) {
						//notesText += "The baby had received " + tpnValue + " mL of TPN solution. " + htmlNextLine;
					}
				}
			}

			if(!BasicUtils.isEmpty(todayWeight)) {
				String intakeOutputText = "";

				if(totalEn != 0 || totalPn != 0) {
//						intakeOutputText += "Given Feeds : " + String.format("%.02f", ((totalPn + totalEn + ivValue) / (todayWeight))) +
//								" mL/kg/day (";

					intakeOutputText += "Vol "+String.format("%.0f",(totalPn + totalEn + ivValue))+" ml ("+String.format("%.02f", ((totalPn + totalEn + ivValue) / (todayWeight))) +
							" ml/kg)";


					if(totalPn != 0) {
						intakeOutputText +=  ", PN " +String.format("%.0f",(totalPn)) +" ml ";
					}

					if(totalEn != 0) {
						intakeOutputText +=  ", EN "+String.format("%.0f",(totalEn))+" ml ";
						if(!BasicUtils.isEmpty(finalvolumeFinalStr)) {
							intakeOutputText +="( " +finalvolumeFinalStr;
						}
						intakeOutputText += " )";
					}

					if(ivValue != 0) {
						intakeOutputText +=  ", IV "  +String.format("%.0f",(ivValue)) +" ml ("+String.format("%.02f", (ivValue / (todayWeight))) +
								" ml/kg )";
					}
					//intakeOutputText += ").";
					notesText += intakeOutputText;

				}
				notesText += htmlNextLine;
			}

			//Comments added to the notes
			if (!BasicUtils.isEmpty(intakeOutputMap.get("comment"))) {
				String allComment = "";
				for (NursingIntakeOutput item : intakeOutputMap.get("comment")) {
					String comment = "";
					if (!BasicUtils.isEmpty(item.getEnteralComment())) {
						comment += getDateFromTimestamp(item.getEntry_timestamp()) + " - " + item.getEnteralComment() + "(EN)";
					}

					if (!BasicUtils.isEmpty(item.getParenteralComment())) {
						if(BasicUtils.isEmpty(comment)) {
							comment += getDateFromTimestamp(item.getEntry_timestamp()) + " - " + item.getParenteralComment() + "(PN). ";
						}
						else {
							comment += ", " + item.getParenteralComment() + "(PN). ";
						}
					}
					else {
						comment += ". ";
					}
					allComment += comment + htmlNextLine;
				}

				if(!BasicUtils.isEmpty(allComment)) {
					notesText += "Comment : " + htmlNextLine + allComment + htmlNextLine;
				}
			}
			notesText += deficitsStr;
		}
		return notesText;
	}

	public String getNursingNotesDoctorOrders(String uhid, Timestamp fromDate, Timestamp toDate, float todayWeight) {
		fromDate = new Timestamp(fromDate.getTime() - 60000);
		Long duration  = (toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60);
		String htmlNextLine = System.getProperty("line.separator");// "&#13;&#10;";
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm a");

		//Fetching blood product delivered volume to be shown in PN
		float totalBloodProductVolume = 0;
		List<NursingBloodproduct> bloodProductList = inicuDoa.getListFromMappedObjQuery(
				HqlSqlQueryConstants.getNursingBloodProductList(uhid, fromDate, toDate));
		if (!BasicUtils.isEmpty(bloodProductList)) {
			for (NursingBloodproduct item : bloodProductList) {
				if (!BasicUtils.isEmpty(item.getDelivered_volume())) {
					totalBloodProductVolume += item.getDelivered_volume();
				}
			}
		}

		//Fetching Heplock delivered volume to be shown in PN
		float totalHeplockVolume = 0;
		List<NursingHeplock> heplockList = inicuDoa.getListFromMappedObjQuery(
				HqlSqlQueryConstants.getNursingHeplockList(uhid, fromDate, toDate));
		if (!BasicUtils.isEmpty(heplockList)) {
			for (NursingHeplock item : heplockList) {
				if (!BasicUtils.isEmpty(item.getDeliveredVolume())) {
					totalHeplockVolume += item.getDeliveredVolume();
				}
			}
		}

		//Fetching Medication delivered volume to be shown in PN
		float totalMedicineVolume = 0;
		List<NursingMedication> medicationLists = inicuDoa.getListFromMappedObjQuery(
				HqlSqlQueryConstants.getNursingMedicationList(uhid, fromDate, toDate));
		if (!BasicUtils.isEmpty(medicationLists)) {
			for (NursingMedication item : medicationLists) {
				if (!BasicUtils.isEmpty(item.getDelivered_volume())) {
					totalMedicineVolume += item.getDelivered_volume();
				}
			}
		}

		String notesText = "";
		float totalEn = 0;
		float totalPn = 0;
		float ivValue = 0;

		float totalLipid = 0;
		//Planned Input
		List<BabyfeedDetail> listBabyFeedDetails = (List<BabyfeedDetail>) inicuDoa
				.getListFromMappedObjQuery(HqlSqlQueryConstants.getBabyFeedList(uhid, fromDate, toDate));
		// calculating feed calculator....
		FeedCalculatorPOJO calculator = new FeedCalculatorPOJO();
		String deficitsStr = "";
		String finalvolumeFinalStr = "";

		if (!BasicUtils.isEmpty(listBabyFeedDetails)) {

			BabyfeedDetail babyFeedDetailsObj = listBabyFeedDetails.get(0);

			String nutritionCalculator = "select obj from RefNutritioncalculator obj";
			List<RefNutritioncalculator> nutritionList = notesDoa.getListFromMappedObjNativeQuery(nutritionCalculator);
			calculator.setRefNutritionInfo(nutritionList);

			if(!BasicUtils.isEmpty(todayWeight)) {
				float energy = 0;
				float protein = 0;
				String weight = (todayWeight / 1000) + "";
				List<BabyfeedDetail> dummyFeedList = new ArrayList<BabyfeedDetail>();
				dummyFeedList.add(babyFeedDetailsObj);
				CaclulatorDeficitPOJO cuurentDeficitLast = getDeficitFeedCalculatorInput(uhid, dummyFeedList, nutritionList,
						weight, fromDate, toDate);
				calculator.setLastDeficitCal(cuurentDeficitLast);
				if(!BasicUtils.isEmpty(calculator.getLastDeficitCal().getParentalIntake().get("Protein"))) {
					protein = calculator.getLastDeficitCal().getParentalIntake().get("Protein");
				}
				if(!BasicUtils.isEmpty(calculator.getLastDeficitCal().getEnteralIntake().get("Protein"))) {
					protein += calculator.getLastDeficitCal().getEnteralIntake().get("Protein");
				}
				if(!BasicUtils.isEmpty(calculator.getLastDeficitCal().getParentalIntake().get("Energy"))) {
					energy = calculator.getLastDeficitCal().getParentalIntake().get("Energy");
				}
				if(!BasicUtils.isEmpty(calculator.getLastDeficitCal().getEnteralIntake().get("Energy"))) {
					energy += calculator.getLastDeficitCal().getEnteralIntake().get("Energy");
				}
				if(energy != 0)
					deficitsStr = " Energy : " + (Math.round(energy*100)/100) + " cal/kg/day";
				if(protein != 0)
					deficitsStr += " Protein : " + (Math.round(protein*100)/100) + " gm/kg/day";
				if(energy != 0 || protein != 0)
					deficitsStr += ". ";

				String frequencyString = "";
				if(!BasicUtils.isEmpty(listBabyFeedDetails)){
					if(!BasicUtils.isEmpty(babyFeedDetailsObj.getFeedduration())) {
						frequencyString = " q " + babyFeedDetailsObj.getFeedduration() + " hrly";
					}

				}
				String volumeFrequencyStr = "";

				if(!BasicUtils.isEmpty(babyFeedDetailsObj.getFeedvolume())){
					volumeFrequencyStr = " " + babyFeedDetailsObj.getFeedvolume();
				}

				if(!BasicUtils.isEmpty(volumeFrequencyStr) && !BasicUtils.isEmpty(frequencyString)) {
					finalvolumeFinalStr = volumeFrequencyStr + " mL" + frequencyString;
				}

				if(!BasicUtils.isEmpty(babyFeedDetailsObj.getLipidTotal())) {
					totalLipid = babyFeedDetailsObj.getLipidTotal();
				}

			}

			if(!BasicUtils.isEmpty(babyFeedDetailsObj.getTotalenteralvolume())) {
				totalEn += babyFeedDetailsObj.getTotalenteralvolume();
			}

			if(!BasicUtils.isEmpty(babyFeedDetailsObj.getTotalparenteralvolume())) {
				totalPn += babyFeedDetailsObj.getTotalparenteralvolume();
			}

			if(!BasicUtils.isEmpty(babyFeedDetailsObj.getTotalMedvolume())) {
				ivValue += babyFeedDetailsObj.getTotalMedvolume();
			}

			if(!BasicUtils.isEmpty(babyFeedDetailsObj.getTotalBloodProductVolume())) {
				ivValue += babyFeedDetailsObj.getTotalBloodProductVolume();
			}
			String intakeOutputText = "";

			if(!BasicUtils.isEmpty(todayWeight)) {
				if(totalEn != 0 || totalPn != 0) {

					if(!BasicUtils.isEmpty(todayWeight)) {

						if(totalEn != 0 || totalPn != 0) {
							int tflFinal = (int) ((totalPn + totalEn + ivValue) / (todayWeight));
							int tflFinalDay = (int) ((totalPn + totalEn + ivValue));
							intakeOutputText += "TFL: " + tflFinal + " mL/kg/day (";

							if(totalEn != 0) {
								int enFinal = (int) ((totalEn) / (todayWeight));
								intakeOutputText +=  "EN: " + enFinal + " mL/kg/day, ";
//								if(!BasicUtils.isEmpty(finalvolumeFinalStr)) {
//									intakeOutputText += finalvolumeFinalStr;
//								}
							}
							if(totalPn != 0) {
								int pnFinal = (int) ((totalPn) / (todayWeight));
								intakeOutputText +=  "PN: " + pnFinal + " mL/kg/day";
//								if(totalLipid > 0) {
//									int lipidRate = (int) (totalLipid / 24);
//									intakeOutputText += "Lipid(20%) " + totalLipid + "mL@ " + String.format("%.01f", (totalLipid / (24))) + "mL/hr";
//								}
//								float totalRestPNVolume = (totalPn - ivValue - totalLipid);
//								intakeOutputText += "AA + Dex: " + (int) totalRestPNVolume + "mL@ " + String.format("%.01f", (totalRestPNVolume / (24))) + "mL/hr";
							}
//							if(ivValue != 0) {
//								int ivFinal = (int) (ivValue / (todayWeight));
//								intakeOutputText +=  ", IV: " + ivFinal + " mL/kg/day";
//							}

							intakeOutputText += ")";
							intakeOutputText += ".";
							notesText += intakeOutputText;

						}
					}
				}
			}
		}
		return notesText;
	}

	public LinkedHashMap<String, Timestamp> sortHashMapByValues(
			HashMap<String, Timestamp> passedMap) {
		List<String> mapKeys = new ArrayList<>(passedMap.keySet());
		List<Timestamp> mapValues = new ArrayList<>(passedMap.values());
		Collections.sort(mapValues);
		Collections.sort(mapKeys);

		LinkedHashMap<String, Timestamp> sortedMap =
				new LinkedHashMap<>();

		Iterator<Timestamp> valueIt = mapValues.iterator();
		while (valueIt.hasNext()) {
			Timestamp val = valueIt.next();
			Iterator<String> keyIt = mapKeys.iterator();

			while (keyIt.hasNext()) {
				String key = keyIt.next();
				Timestamp comp1 = passedMap.get(key);
				Timestamp comp2 = val;

				if (comp1.equals(comp2)) {
					keyIt.remove();
					sortedMap.put(key, val);
					break;
				}
			}
		}
		return sortedMap;
	}

	// Check Change in Nutrition Order
	public ArrayList<Float> checkChangeInNutritionOrder(String uhid, Timestamp currentFromDate,Timestamp currentToDate) {
		ArrayList<Float> intakeArray = new ArrayList<>();
		currentFromDate = new Timestamp(currentFromDate.getTime() - 60000);

		float totalEn = 0;
		float totalPn = 0;
		float ivValue = 0;
		float tflValkue = 0;

		List<BabyfeedDetail> listBabyFeedDetails = (List<BabyfeedDetail>) inicuDoa
				.getListFromMappedObjQuery(HqlSqlQueryConstants.getBabyFeedList(uhid, currentFromDate, currentToDate));
		if (!BasicUtils.isEmpty(listBabyFeedDetails)) {
			BabyfeedDetail babyFeedDetailsObj = listBabyFeedDetails.get(0);

			if (!BasicUtils.isEmpty(babyFeedDetailsObj.getTotalenteralvolume())) {
				totalEn += babyFeedDetailsObj.getTotalenteralvolume();
			}

			if (!BasicUtils.isEmpty(babyFeedDetailsObj.getTotalparenteralvolume())) {
				totalPn += babyFeedDetailsObj.getTotalparenteralvolume();
			}

			if (!BasicUtils.isEmpty(babyFeedDetailsObj.getTotalMedvolume())) {
				ivValue += babyFeedDetailsObj.getTotalMedvolume();
			}

			if (!BasicUtils.isEmpty(babyFeedDetailsObj.getTotalBloodProductVolume())) {
				ivValue += babyFeedDetailsObj.getTotalBloodProductVolume();
			}
			tflValkue = totalEn + totalPn + ivValue;
		}
		intakeArray.add(tflValkue);
		intakeArray.add(totalEn);
		intakeArray.add(totalPn);
		return intakeArray;
	};

	/*
	 * Today's Nutrition Order i.e Doctor Order
	 * totalFeed(EN + PN+Bolus) + totalHeplockDelVolume + totalBloodDelVolume + totalMedicineVol
	 */
	public String getDailyProgressNotesDoctorOrders(String uhid, Timestamp fromDate, Timestamp toDate, float todayWeight) {
		fromDate = new Timestamp(fromDate.getTime() - 60000);
		Long duration  = (toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60);
		String htmlNextLine = System.getProperty("line.separator");// "&#13;&#10;";
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm a");

		//Fetching blood product delivered volume to be shown in PN

		// Updated -> Fetch the doctor order volume;
		float totalBloodProductVolume = 0;
		List<DoctorBloodProducts> bloodProductList = inicuDoa.getListFromMappedObjQuery(
				HqlSqlQueryConstants.getBloodProductData(uhid, fromDate, toDate));
		if (!BasicUtils.isEmpty(bloodProductList)) {
			for (DoctorBloodProducts item : bloodProductList) {
				if (!BasicUtils.isEmpty(item.getTotal_volume())) {
					totalBloodProductVolume += item.getTotal_volume();
				}
			}
		}

		//Fetching Heplock delivered volume to be shown in PN
		// Updated -> Fetch the doctor order volume;
		float totalHeplockVolume = 0;
		List<CentralLine> heplockList = inicuDoa.getListFromMappedObjQuery(
				HqlSqlQueryConstants.getHeplockData(uhid, fromDate, toDate));
		if (!BasicUtils.isEmpty(heplockList)) {
			for (CentralLine item : heplockList) {
				if (!BasicUtils.isEmpty(item.getHeparinTotalVolume())) {
					totalHeplockVolume += item.getHeparinTotalVolume();
				}
			}
		}

		//Fetching Medication delivered volume to be shown in PN
		float totalMedicineVolume = 0;
		List<BabyPrescription> medicationLists = inicuDoa.getListFromMappedObjQuery(
				HqlSqlQueryConstants.getBabyPrescription(uhid, fromDate, toDate));
		if (!BasicUtils.isEmpty(medicationLists)) {
			for (BabyPrescription item : medicationLists) {
				if (!BasicUtils.isEmpty(item.getInf_volume())) {
					totalMedicineVolume += item.getInf_volume();
				}
			}
		}


		String notesText = "";
		float TFL_Ml = 0;
		float TFLPerKg = 0;
		float TFLPerKgPerDay = 0;
		float totalEn = 0;
		float totalPn = 0;
		float ivValue = 0;
		boolean isNPOtrue = false;

		float totalLipid = 0;
		//Planned Input
		List<BabyfeedDetail> listBabyFeedDetails = (List<BabyfeedDetail>) inicuDoa
				.getListFromMappedObjQuery(HqlSqlQueryConstants.getBabyFeedList(uhid, fromDate, toDate));
		// calculating feed calculator....
		FeedCalculatorPOJO calculator = new FeedCalculatorPOJO();
		String deficitsStr = "";
		String finalvolumeFinalStr = "";

		if (!BasicUtils.isEmpty(listBabyFeedDetails)) {
			BabyfeedDetail babyFeedDetailsObj = listBabyFeedDetails.get(0);

			String nutritionCalculator = "select obj from RefNutritioncalculator obj";
			List<RefNutritioncalculator> nutritionList = notesDoa.getListFromMappedObjNativeQuery(nutritionCalculator);
			calculator.setRefNutritionInfo(nutritionList);


			if(!BasicUtils.isEmpty(babyFeedDetailsObj.getTotalfeedMlDay())){
				TFLPerKg = babyFeedDetailsObj.getTotalfeedMlDay();
			}else if (!BasicUtils.isEmpty(babyFeedDetailsObj.getIsenternalgiven()) && babyFeedDetailsObj.getIsenternalgiven() == false){
				isNPOtrue = true;
			}
			if(!BasicUtils.isEmpty(babyFeedDetailsObj.getTotalfluidMlDay())){
				TFLPerKgPerDay = babyFeedDetailsObj.getTotalfluidMlDay();
			}

			if(!BasicUtils.isEmpty(babyFeedDetailsObj.getTotalIntake())){
				TFL_Ml = Float.parseFloat(babyFeedDetailsObj.getTotalIntake());
			}

			if(!BasicUtils.isEmpty(babyFeedDetailsObj.getTotalenteralvolume())) {
				totalEn += babyFeedDetailsObj.getTotalenteralvolume();
			}

			if(!BasicUtils.isEmpty(babyFeedDetailsObj.getTotalparenteralvolume())) {
				totalPn += babyFeedDetailsObj.getTotalparenteralvolume();
			}

			if(!BasicUtils.isEmpty(babyFeedDetailsObj.getTotalMedvolume())) {
				ivValue += babyFeedDetailsObj.getTotalMedvolume();
			}

			if(!BasicUtils.isEmpty(babyFeedDetailsObj.getTotalBloodProductVolume())) {
				ivValue += babyFeedDetailsObj.getTotalBloodProductVolume();
			}

			if(!BasicUtils.isEmpty(todayWeight)) {
				float energy = 0;
				float protein = 0;
				String weight = (todayWeight / 1000) + "";
				List<BabyfeedDetail> dummyFeedList = new ArrayList<BabyfeedDetail>();
				dummyFeedList.add(babyFeedDetailsObj);
				CaclulatorDeficitPOJO cuurentDeficitLast = getDeficitFeedCalculatorInput(uhid, dummyFeedList, nutritionList,
						weight, fromDate, toDate);
				calculator.setLastDeficitCal(cuurentDeficitLast);
				if(!BasicUtils.isEmpty(calculator.getLastDeficitCal().getParentalIntake().get("Protein"))) {
					protein = calculator.getLastDeficitCal().getParentalIntake().get("Protein");
				}
				if(!BasicUtils.isEmpty(calculator.getLastDeficitCal().getEnteralIntake().get("Protein"))) {
					protein += calculator.getLastDeficitCal().getEnteralIntake().get("Protein");
				}
				if(!BasicUtils.isEmpty(calculator.getLastDeficitCal().getParentalIntake().get("Energy"))) {
					energy = calculator.getLastDeficitCal().getParentalIntake().get("Energy");
				}
				if(!BasicUtils.isEmpty(calculator.getLastDeficitCal().getEnteralIntake().get("Energy"))) {
					energy += calculator.getLastDeficitCal().getEnteralIntake().get("Energy");
				}
				if(energy != 0)
					deficitsStr = " Energy : " + (Math.round(energy*100)/100) + " cal/kg/day";
				if(protein != 0)
					deficitsStr += " Protein : " + (Math.round(protein*100)/100) + " gm/kg/day";
				if(energy != 0 || protein != 0)
					deficitsStr += ". ";

				String frequencyString = "";
				if(!BasicUtils.isEmpty(listBabyFeedDetails)){
					if(!BasicUtils.isEmpty(babyFeedDetailsObj.getFeedduration())) {
						frequencyString = " q " + babyFeedDetailsObj.getFeedduration() + " hrly";
					}
				}

				String volumeFrequencyStr = "";
				Long babyFeedId = babyFeedDetailsObj.getBabyfeedid();

				// get the Volume which has maximum feed from the en_feedDetail table
				String maxFeedVolume = "select feed_volume from en_feed_detail " +
						"where babyfeedid = '" + babyFeedId + "'" +
						"  and no_of_feed = (Select max(no_of_feed) from en_feed_detail where babyfeedid = '" + babyFeedId + "') order by feed_volume desc";
				List<Float> maxVolumeList = inicuDoa.getListFromNativeQuery(maxFeedVolume);

				if(!BasicUtils.isEmpty(maxVolumeList) && maxVolumeList.size()>0){
					volumeFrequencyStr = " " + maxVolumeList.get(0);
				}

				if(!BasicUtils.isEmpty(volumeFrequencyStr) && !BasicUtils.isEmpty(frequencyString)) {
					finalvolumeFinalStr = volumeFrequencyStr + " mL" + frequencyString;
				}

				if(!BasicUtils.isEmpty(babyFeedDetailsObj.getLipidTotal())) {
					totalLipid = babyFeedDetailsObj.getLipidTotal();
				}
			}

			String intakeOutputText = "";

			if(!BasicUtils.isEmpty(todayWeight)) {
				if(totalEn != 0 || totalPn != 0) {

					if(!BasicUtils.isEmpty(todayWeight)) {

						if(totalEn != 0 || totalPn != 0) {

//							int tflFinal = (int) ((totalPn + totalEn + ivValue) / (todayWeight));
//							int tflFinalDay = (int) ((totalPn + totalEn + ivValue));
//							intakeOutputText += "TFL: " + tflFinal + " mL/kg/day (";


							intakeOutputText += "TFL "+String.format("%.0f",TFL_Ml)+" ml ";

							if(!BasicUtils.isEmpty(TFLPerKgPerDay) && TFLPerKgPerDay!=0) {
								intakeOutputText += "("+String.format("%.01f", TFLPerKgPerDay) + " ml/kg/day)";
							}

							if(totalPn != 0) {
								intakeOutputText +=  ", PN " +String.format("%.0f",(totalPn)) +" ml ("+String.format("%.01f", (totalPn / (todayWeight))) +
										" ml/kg/day )";
							}

							if(totalEn != 0) {
								intakeOutputText +=  ", EN "+String.format("%.0f",(totalEn))+" ml";
								if(!BasicUtils.isEmpty(finalvolumeFinalStr)) {
									intakeOutputText += " ("+finalvolumeFinalStr;
									intakeOutputText += ")";
								}
							}

							if(totalMedicineVolume != 0) {
								intakeOutputText +=  ", IV Medication "  +String.format("%.0f",(totalMedicineVolume)) +" ml ("+String.format("%.01f", (totalMedicineVolume / (todayWeight))) +
										" ml/kg )";
							}

							// Heplock
							if(totalHeplockVolume != 0) {
								if(intakeOutputText.length()>0) intakeOutputText+=", ";
								intakeOutputText +=  "Heplock "  +String.format("%.0f",(totalHeplockVolume)) +" ml";
							}

							// Blood Product
							if(totalBloodProductVolume != 0) {
								if(intakeOutputText.length()>0) intakeOutputText+=", ";
								intakeOutputText +=  "Blood Product "  +String.format("%.0f",(totalBloodProductVolume)) +" ml";
							}
							notesText += intakeOutputText;
						}
					}
				}
			}
		}
		return notesText;
	}

	/*
	 * Last 24 Hour Intake and Output
	 * totalFeed(EN + PN+Bolus) + totalHeplockDelVolume + totalBloodDelVolume + totalMedicineVol
	 */
	public String getDailyProgressNotesInputOutputPast(String uhid, Timestamp fromDate, Timestamp toDate, float todayWeight) {
 		fromDate = new Timestamp(fromDate.getTime());
		String htmlNextLine = System.getProperty("line.separator");// "&#13;&#10;";
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm a");

		//Fetching blood product delivered volume to be shown in PN
		float totalBloodProductVolume = 0;
		List<NursingBloodproduct> bloodProductList = inicuDoa.getListFromMappedObjQuery(
				HqlSqlQueryConstants.getNursingBloodProductList(uhid, fromDate, toDate));
		if (!BasicUtils.isEmpty(bloodProductList)) {
			for (NursingBloodproduct item : bloodProductList) {
				if (!BasicUtils.isEmpty(item.getDelivered_volume())) {
					totalBloodProductVolume += item.getDelivered_volume();
				}
			}
		}

		//Fetching Heplock delivered volume to be shown in PN
		float totalHeplockVolume = 0;
		List<NursingHeplock> heplockList = inicuDoa.getListFromMappedObjQuery(
				HqlSqlQueryConstants.getNursingHeplockList(uhid, fromDate, toDate));
		if (!BasicUtils.isEmpty(heplockList)) {
			for (NursingHeplock item : heplockList) {
				if (!BasicUtils.isEmpty(item.getDeliveredVolume())) {
					totalHeplockVolume += item.getDeliveredVolume();
				}
			}
		}

		//Fetching Medication delivered volume to be shown in PN
		float totalMedicineVolume = 0;
		List<NursingMedication> medicationLists = inicuDoa.getListFromMappedObjQuery(
				HqlSqlQueryConstants.getNursingMedicationList(uhid, fromDate, toDate));
		if (!BasicUtils.isEmpty(medicationLists)) {
			for (NursingMedication item : medicationLists) {
				if (!BasicUtils.isEmpty(item.getDelivered_volume())) {
					totalMedicineVolume += item.getDelivered_volume();
				}
			}
		}

		String notesText = "";
		float totalEn = 0;
		float totalPn = 0;
		float totalBolus = 0;
		float totalPnSol = 0;
		float ivValue = 0;
		String finalvolumeFinalStr = "";

		//Planned Input
		List<BabyfeedDetail> listBabyFeedDetails = (List<BabyfeedDetail>) inicuDoa.getListFromMappedObjQuery(HqlSqlQueryConstants.getBabyFeedList(uhid, fromDate, toDate));

		//Given Feeds & Output
		List<NursingIntakeOutput> listIntakeOutputDetails = (List<NursingIntakeOutput>) inicuDoa
				.getListFromMappedObjQuery(HqlSqlQueryConstants.getNursingIntakeOutputList(uhid, fromDate, toDate));

		if (!BasicUtils.isEmpty(listIntakeOutputDetails)) {

			HashMap<String, List<NursingIntakeOutput>> intakeOutputMap = new HashMap<String, List<NursingIntakeOutput>>() {
				private static final long serialVersionUID = 1L;
				{
					put("enteral", new ArrayList<>());
					put("parenteral", new ArrayList<>());
					put("output", new ArrayList<>());
					put("comment", new ArrayList<>());
				}
			};

			Collections.reverse(listIntakeOutputDetails);
			for (NursingIntakeOutput item : listIntakeOutputDetails) {
				//Enteral & Parenteral Comments
				if (!BasicUtils.isEmpty(item.getEnteralComment()) && !BasicUtils.isEmpty(item.getEnteralComment())) {
					intakeOutputMap.get("comment").add(item);
				}
				else {
					if (!BasicUtils.isEmpty(item.getEnteralComment())) {
						intakeOutputMap.get("comment").add(item);
					}
					else {
						if (!BasicUtils.isEmpty(item.getEnteralComment())) {
							intakeOutputMap.get("comment").add(item);
						}
					}
				}

				if ((!BasicUtils.isEmpty(item.getRoute()) && item.getRoute().equalsIgnoreCase("Breast Feed")) || item.getPrimaryFeedValue() != null
						|| item.getFormulaValue() != null || item.getActualFeed() != null) {
					intakeOutputMap.get("enteral").add(item);

					// actuall Feed
					if(!BasicUtils.isEmpty(item.getActualFeed())) {
						totalEn += item.getActualFeed();
					}

					// Calcium Volume
					if(!BasicUtils.isEmpty(item.getCalciumVolume())) {
						totalEn += item.getCalciumVolume();
					}

					// Iron Volume
					if(!BasicUtils.isEmpty(item.getIronVolume())) {
						totalEn += item.getIronVolume();
					}

					// Mv Volume
					if(!BasicUtils.isEmpty(item.getMvVolume())) {
						totalEn += item.getMvVolume();
					}

					// Vitamin Volume
					if(!BasicUtils.isEmpty(item.getVitamindVolume())) {
						totalEn += item.getVitamindVolume();
					}

					// MCT Volume
					if(!BasicUtils.isEmpty(item.getMctVolume())) {
						totalEn += item.getMctVolume();
					}

					// Other Additives Volumne
					if(!BasicUtils.isEmpty(item.getOtherAdditiveVolume())) {
						totalEn += item.getOtherAdditiveVolume();
					}
				}

				if ((item.getBolus_executed() != null && item.getBolus_executed())
						|| item.getTpn_delivered() != null || item.getLipid_delivered() != null || item.getReadymadeDeliveredFeed() != null
						|| totalMedicineVolume != 0 || totalHeplockVolume != 0 || totalBloodProductVolume != 0) {
					intakeOutputMap.get("parenteral").add(item);

					// TPN Delivered
					if(item.getTpn_delivered() != null) {
						totalPn = totalPn + item.getTpn_delivered();
						totalPnSol = totalPnSol + item.getTpn_delivered();
					}

					// Lipid Delivered
					if(item.getLipid_delivered() != null) {
						totalPn = totalPn + item.getLipid_delivered();
					}

					// ready Made Delivered Feed
					if(item.getReadymadeDeliveredFeed() != null) {
						totalPn = totalPn + item.getReadymadeDeliveredFeed();
						totalPnSol = totalPnSol + item.getReadymadeDeliveredFeed();
					}

					// amino Delivered Feed
					if(item.getAminoDeliveredFeed() != null) {
						totalPn = totalPn + item.getAminoDeliveredFeed();
						totalPnSol = totalPnSol + item.getReadymadeDeliveredFeed();
					}

					if(item.getBolusDeliveredFeed() != null){
//						totalPn = totalPn + item.getBolusDeliveredFeed();
						totalBolus = totalBolus + item.getBolusDeliveredFeed();
					}
				}

				if (item.getGastricAspirate() != null || (item.getUrinePassed() != null && item.getUrinePassed())
						|| (item.getStoolPassed() != null && item.getStoolPassed())
						|| (item.getVomitPassed() != null && item.getVomitPassed()) || item.getDrain() != null) {
					intakeOutputMap.get("output").add(item);
				}
			}

			if (!BasicUtils.isEmpty(intakeOutputMap.get("enteral"))) {
				float primaryFeed = 0;
				float formulaFeed = 0;
				boolean breastMilk = false;
				String routeStr = "";
				HashMap<String, Integer> differentVolumes = new HashMap<String, Integer>();
				String volumeFrequencyStr = "";
				for (NursingIntakeOutput item : intakeOutputMap.get("enteral")) {
					if (!BasicUtils.isEmpty(item.getRoute()) && item.getRoute().equalsIgnoreCase("Breast Feed")) {
						breastMilk = true;
					} else {
						if (!BasicUtils.isEmpty(item.getRoute()) && !routeStr.contains(item.getRoute())) {
							routeStr += ", " + item.getRoute();
						}

						if (!(item.getPrimaryFeedValue() == null || item.getPrimaryFeedValue() == 0)) {
							primaryFeed += item.getPrimaryFeedValue();
						}

						if (!(item.getFormulaValue() == null || item.getFormulaValue() == 0)) {
							formulaFeed += item.getFormulaValue();
						}

						if (!(item.getActualFeed() == null || item.getActualFeed() == 0)) {
							if (differentVolumes.containsKey("" + item.getActualFeed())) {
								differentVolumes.put("" + item.getActualFeed(), differentVolumes.get("" + item.getActualFeed()) + 1);
							} else {
								differentVolumes.put("" + item.getActualFeed(), 1);
							}
						}
					}
				}

				String frequencyString = "";
				if(!BasicUtils.isEmpty(listBabyFeedDetails)){
					for(BabyfeedDetail feed : listBabyFeedDetails) {
						if(!BasicUtils.isEmpty(feed.getFeedduration())) {
							frequencyString = " q " + feed.getFeedduration() + " hrly";
						}
					}
				}

                for (Map.Entry<String, Integer> entry : differentVolumes.entrySet()) {
                    if(!BasicUtils.isEmpty(volumeFrequencyStr)) {
                        volumeFrequencyStr += ", " + entry.getKey() + "ml(" + entry.getValue() + ")";
                    }
                    else {
                        if (differentVolumes.size() > 1) {
                            volumeFrequencyStr = " " + entry.getKey() + "ml(" + entry.getValue() + ")";
                        } else {
                            volumeFrequencyStr = " " + entry.getKey();
                        }
                    }
                }

                if(!BasicUtils.isEmpty(volumeFrequencyStr) && differentVolumes.size() > 1) {
                    finalvolumeFinalStr = volumeFrequencyStr;
                } else if(!BasicUtils.isEmpty(volumeFrequencyStr) && differentVolumes.size() == 1 && !BasicUtils.isEmpty(frequencyString)) {
                    finalvolumeFinalStr = volumeFrequencyStr + " ml" + frequencyString;
                } else if(!BasicUtils.isEmpty(volumeFrequencyStr)) {
                    finalvolumeFinalStr = volumeFrequencyStr + " ml";
                }
			}

			if(totalMedicineVolume != 0) {
				ivValue = ivValue + totalMedicineVolume;
			}
			if(totalHeplockVolume != 0) {
				ivValue = ivValue + totalHeplockVolume;
			}
			if(totalBloodProductVolume != 0){
				ivValue = ivValue + totalBloodProductVolume;
			}
			if(totalBolus != 0){
				ivValue = ivValue + totalBolus;
			}

			float lipidValue = 0;
			float tpnValue = 0;
			float bolusValue = 0;

			if(!BasicUtils.isEmpty(todayWeight)) {
				String intakeOutputText = "";
				if(totalEn != 0 || totalPn != 0) {

					intakeOutputText += "TFL "+String.format("%.0f",(totalPn + totalEn + ivValue))+" ml ("+String.format("%.01f", ((totalPn + totalEn + ivValue) / (todayWeight))) +
							" ml/kg/day)";

					if(totalPn != 0) {
						intakeOutputText +=  ", PN " +String.format("%.0f",(totalPn)) +" ml";
					}

					if(totalEn != 0) {
						intakeOutputText +=  ", EN "+String.format("%.0f",(totalEn))+" ml";
						if(!BasicUtils.isEmpty(finalvolumeFinalStr)) {
							intakeOutputText += " ("+finalvolumeFinalStr;
							intakeOutputText += ")";
						}

					}

					if(totalMedicineVolume != 0) {
						intakeOutputText +=  ", IV Medication "  +String.format("%.0f",(totalMedicineVolume)) +" ml";
					}
					notesText += intakeOutputText;

					if(totalHeplockVolume!=0){
						if(!BasicUtils.isEmpty(notesText)){
							notesText += ", Heplock "+ String.format("%.0f",(totalHeplockVolume)) + " ml";
						}else{
							notesText += "Heplock "+ String.format("%.0f",(totalHeplockVolume)) + " ml";
						}
					}

					if(totalBloodProductVolume!=0){
						if(!BasicUtils.isEmpty(notesText)){
							notesText += ", Blood Product "+ String.format("%.0f",(totalBloodProductVolume)) + " ml";
						}else{
							notesText += "Blood Product "+ String.format("%.0f",(totalBloodProductVolume)) + " ml";
						}
					}
					notesText += htmlNextLine;
				}
			}
		}
		return notesText;
	}

	/*
	 * Last 24 Hour Output
	 * Included Urine and Stool
	 */
	public String getDailyProgressNotesOutputPast(String uhid, Timestamp fromDate, Timestamp toDate, float todayWeight) {
		fromDate = new Timestamp(fromDate.getTime());
		Long duration  = (toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60);
		String htmlNextLine = System.getProperty("line.separator");// "&#13;&#10;";
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm a");

		String notesText = "";
		//Given Feeds & Output
		List<NursingIntakeOutput> listIntakeOutputDetails = (List<NursingIntakeOutput>) inicuDoa
				.getListFromMappedObjQuery(HqlSqlQueryConstants.getNursingIntakeOutputList(uhid, fromDate, toDate));
		if (!BasicUtils.isEmpty(listIntakeOutputDetails)) {

			HashMap<String, List<NursingIntakeOutput>> intakeOutputMap = new HashMap<String, List<NursingIntakeOutput>>() {
				private static final long serialVersionUID = 1L;
				{
					put("output", new ArrayList<>());
					put("comment", new ArrayList<>());
				}
			};

			Collections.reverse(listIntakeOutputDetails);
			for (NursingIntakeOutput item : listIntakeOutputDetails) {

				//Enteral & Parenteral Comments
				if (!BasicUtils.isEmpty(item.getEnteralComment()) && !BasicUtils.isEmpty(item.getEnteralComment())) {
					intakeOutputMap.get("comment").add(item);
				}
				else {
					if (!BasicUtils.isEmpty(item.getEnteralComment())) {
						intakeOutputMap.get("comment").add(item);
					}
					else {
						if (!BasicUtils.isEmpty(item.getEnteralComment())) {
							intakeOutputMap.get("comment").add(item);
						}
					}
				}

				if (item.getGastricAspirate() != null || (item.getUrinePassed() != null && item.getUrinePassed())
						|| (item.getStoolPassed() != null && item.getStoolPassed())
						|| (item.getVomitPassed() != null && item.getVomitPassed()) || item.getDrain() != null) {
					intakeOutputMap.get("output").add(item);
				}
			}

			if (!BasicUtils.isEmpty(intakeOutputMap.get("output"))) {
				float urineTotal = 0;
				int stoolCount = 0;
				String stoolStr = "";

				List<NursingIntakeOutput> outputList = intakeOutputMap.get("output");
				for (NursingIntakeOutput item : outputList) {

					if (item.getUrinePassed() != null && item.getUrinePassed() && item.getUrine() != null) {
						try {
							urineTotal += parseFloat(item.getUrine());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					if (item.getStoolPassed() != null && item.getStoolPassed()) {
						stoolCount++;
						if (!(item.getStool() == null || item.getStool().isEmpty()
								|| stoolStr.contains(item.getStool()))) {
							stoolStr += ", " + item.getStool();
						}
					}
				}

					if (!BasicUtils.isEmpty(todayWeight)) {
						if (urineTotal != 0) {
							notesText += "Urine: " + String.format("%.01f", (urineTotal / (todayWeight)) / 24) + " ml/kg/hr";
						}

						if (stoolCount != 0) {
							if (notesText.length() > 1) notesText += ", ";
							if (stoolCount > 1) {
								notesText += "Stool: " + stoolCount + " times";
							} else {
								notesText += "Stool: " + stoolCount + " time";
							}

						} else {
							if (notesText.length() > 1) notesText += ", ";
							notesText += "No stool passed";
						}
					}
					if (notesText.length() > 1) notesText += ". ";
				}
			}
		return notesText;
	}

	public String getOutcomeNotesInputOutputPast(String uhid, Timestamp fromDate, Timestamp toDate,Timestamp fromDateOffset,Timestamp toDateOffset, float todayWeight) {
		fromDate = new Timestamp(fromDate.getTime());
		Long duration  = (toDate.getTime()+60000 - fromDate.getTime()) / (1000 * 60 * 60);
		String htmlNextLine = System.getProperty("line.separator");// "&#13;&#10;";
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm a");

		//Fetching blood product delivered volume to be shown in PN
		float totalBloodProductVolume = 0;
		List<NursingBloodproduct> bloodProductList = inicuDoa.getListFromMappedObjQuery(
				HqlSqlQueryConstants.getNursingBloodProductList(uhid, fromDate, toDate));
		if (!BasicUtils.isEmpty(bloodProductList)) {
			for (NursingBloodproduct item : bloodProductList) {
				if (!BasicUtils.isEmpty(item.getDelivered_volume())) {
					totalBloodProductVolume += item.getDelivered_volume();
				}
			}
		}

		//Fetching Heplock delivered volume to be shown in PN
		float totalHeplockVolume = 0;
		List<NursingHeplock> heplockList = inicuDoa.getListFromMappedObjQuery(
				HqlSqlQueryConstants.getNursingHeplockList(uhid, fromDate, toDate));
		if (!BasicUtils.isEmpty(heplockList)) {
			for (NursingHeplock item : heplockList) {
				if (!BasicUtils.isEmpty(item.getDeliveredVolume())) {
					totalHeplockVolume += item.getDeliveredVolume();
				}
			}
		}

		//Fetching Medication delivered volume to be shown in PN
		float totalMedicineVolume = 0;
		List<NursingMedication> medicationLists = inicuDoa.getListFromMappedObjQuery(
				HqlSqlQueryConstants.getNursingMedicationList(uhid, fromDate, toDate));
		if (!BasicUtils.isEmpty(medicationLists)) {
			for (NursingMedication item : medicationLists) {
				if (!BasicUtils.isEmpty(item.getDelivered_volume())) {
					totalMedicineVolume += item.getDelivered_volume();
				}
			}
		}

		String notesText = "";
		float totalEn = 0;
		float totalPn = 0;
		float totalBolus = 0;
		float totalPnSol = 0;
		float ivValue = 0;
		//Planned Input
		List<BabyfeedDetail> listBabyFeedDetails = (List<BabyfeedDetail>) inicuDoa
				.getListFromMappedObjQuery(HqlSqlQueryConstants.getBabyFeedList(uhid, fromDate, toDate));
		// calculating feed calculator....
		FeedCalculatorPOJO calculator = new FeedCalculatorPOJO();

		totalEn = 0;
		totalPn = 0;
		ivValue = 0;
		String finalvolumeFinalStr = "";

		//Given Feeds & Output
		List<NursingIntakeOutput> listIntakeOutputDetails = (List<NursingIntakeOutput>) inicuDoa
				.getListFromMappedObjQuery(HqlSqlQueryConstants.getNursingIntakeOutputList(uhid, fromDateOffset, toDateOffset));
		if (!BasicUtils.isEmpty(listIntakeOutputDetails)) {
			HashMap<String, List<NursingIntakeOutput>> intakeOutputMap = new HashMap<String, List<NursingIntakeOutput>>() {
				private static final long serialVersionUID = 1L;
				{
					put("enteral", new ArrayList<>());
					put("parenteral", new ArrayList<>());
					put("output", new ArrayList<>());
					put("comment", new ArrayList<>());
				}
			};

			Collections.reverse(listIntakeOutputDetails);
			for (NursingIntakeOutput item : listIntakeOutputDetails) {


				if ((!BasicUtils.isEmpty(item.getRoute()) && item.getRoute().equalsIgnoreCase("Breast Feed")) || item.getPrimaryFeedValue() != null
						|| item.getFormulaValue() != null || item.getActualFeed() != null) {


					intakeOutputMap.get("enteral").add(item);
					if(item.getActualFeed() != null) {
						totalEn = totalEn + item.getActualFeed();


					}
				}

				if ((item.getBolus_executed() != null && item.getBolus_executed())
						|| item.getTpn_delivered() != null || item.getLipid_delivered() != null || item.getReadymadeDeliveredFeed() != null
						|| totalMedicineVolume != 0 || totalHeplockVolume != 0 || totalBloodProductVolume != 0) {
					intakeOutputMap.get("parenteral").add(item);
					if(item.getTpn_delivered() != null) {
						totalPn = totalPn + item.getTpn_delivered();
						totalPnSol = totalPnSol + item.getTpn_delivered();
					}
					if(item.getLipid_delivered() != null) {
						totalPn = totalPn + item.getLipid_delivered();
					}
					if(item.getReadymadeDeliveredFeed() != null) {
						totalPn = totalPn + item.getReadymadeDeliveredFeed();
						totalPnSol = totalPnSol + item.getReadymadeDeliveredFeed();
					}
					if(item.getBolusDeliveredFeed() != null){
						totalPn = totalPn + item.getBolusDeliveredFeed();
						totalBolus = totalBolus + item.getBolusDeliveredFeed();
					}
				}

				if (item.getGastricAspirate() != null || (item.getUrinePassed() != null && item.getUrinePassed())
						|| (item.getStoolPassed() != null && item.getStoolPassed())
						|| (item.getVomitPassed() != null && item.getVomitPassed()) || item.getDrain() != null) {
					intakeOutputMap.get("output").add(item);
				}
			}

			if (BasicUtils.isEmpty(intakeOutputMap.get("enteral"))) {
				//notesText += "The baby is on NPO. " + htmlNextLine;
			} else {
				float primaryFeed = 0;
				float formulaFeed = 0;
				boolean breastMilk = false;
				String routeStr = "";
				HashMap<String, Integer> differentVolumes = new HashMap<String, Integer>();
				String volumeFrequencyStr = "";
				for (NursingIntakeOutput item : intakeOutputMap.get("enteral")) {
					if (!BasicUtils.isEmpty(item.getRoute()) && item.getRoute().equalsIgnoreCase("Breast Feed")) {
						breastMilk = true;
					} else {
						if (!BasicUtils.isEmpty(item.getRoute()) && !routeStr.contains(item.getRoute())) {
							routeStr += ", " + item.getRoute();
						}

						if (!(item.getPrimaryFeedValue() == null || item.getPrimaryFeedValue() == 0)) {
							primaryFeed += item.getPrimaryFeedValue();
						}

						if (!(item.getFormulaValue() == null || item.getFormulaValue() == 0)) {
							formulaFeed += item.getFormulaValue();
						}

						if (!(item.getActualFeed() == null || item.getActualFeed() == 0)) {
							if (differentVolumes.containsKey("" + item.getActualFeed())) {
								differentVolumes.put("" + item.getActualFeed(), differentVolumes.get("" + item.getActualFeed()) + 1);
							} else {
								differentVolumes.put("" + item.getActualFeed(), 1);
							}
						}
					}
				}
				String frequencyString = "";
				if(!BasicUtils.isEmpty(listBabyFeedDetails)){
					for(BabyfeedDetail feed : listBabyFeedDetails) {
						if(!BasicUtils.isEmpty(feed.getFeedduration())) {
							frequencyString = " q " + feed.getFeedduration() + " hrly";
						}
					}
				}

//				for(String keys: differentVolumes.keySet()) {
//					if(!BasicUtils.isEmpty(volumeFrequencyStr)) {
//						volumeFrequencyStr += ", " + keys;
//					}
//					else {
//						volumeFrequencyStr = " " + keys;
//					}
//				}

				for (Map.Entry<String, Integer> entry : differentVolumes.entrySet()) {
					if(!BasicUtils.isEmpty(volumeFrequencyStr)) {
						volumeFrequencyStr += ", " + entry.getKey() + "(" + entry.getValue() + ")";
					}
					else {
						if (differentVolumes.size() > 1) {
							volumeFrequencyStr = " " + entry.getKey() + "(" + entry.getValue() + ")";
						} else {
							volumeFrequencyStr = " " + entry.getKey();
						}
					}
				}

				if(!BasicUtils.isEmpty(volumeFrequencyStr) && differentVolumes.size() > 1) {
					finalvolumeFinalStr = volumeFrequencyStr;
				} else if(!BasicUtils.isEmpty(volumeFrequencyStr) && differentVolumes.size() == 1 && !BasicUtils.isEmpty(frequencyString)) {
					finalvolumeFinalStr = volumeFrequencyStr + " ml" + frequencyString;
				} else if(!BasicUtils.isEmpty(volumeFrequencyStr)) {
					finalvolumeFinalStr = volumeFrequencyStr + " ml";
				}

				if (breastMilk) {
					//notesText += "The baby was on direct Breast Feed. ";
				}

				if (!routeStr.isEmpty() && (primaryFeed != 0 || formulaFeed != 0)) {
					//notesText += "The baby was given " + (primaryFeed + formulaFeed) + " mL of feed through " + routeStr.substring(2);
					if (primaryFeed != 0 && formulaFeed != 0) {
						//notesText += ", received " + primaryFeed + " mL of fortified human milk and " + formulaFeed + " mL of formula milk. ";
					} else if (primaryFeed != 0) {
						//notesText += ", received " + primaryFeed + " mL. ";
					} else if (formulaFeed != 0) {
						//notesText += ", received " + primaryFeed + " mL of formula milk. ";
					}
				}
				//notesText += htmlNextLine;
			}

			if(totalMedicineVolume != 0) {
				ivValue = ivValue + totalMedicineVolume;
			}
			if(totalHeplockVolume != 0) {
				ivValue = ivValue + totalHeplockVolume;
			}
			if(totalBloodProductVolume != 0){
				ivValue = ivValue + totalBloodProductVolume;
			}

			float lipidValue = 0;
			float tpnValue = 0;
			float bolusValue = 0;
			if (!BasicUtils.isEmpty(intakeOutputMap.get("parenteral"))) {
				List<NursingIntakeOutput> pnList = intakeOutputMap.get("parenteral");


				for (NursingIntakeOutput item : pnList) {
					if (item.getBolus_executed() != null && item.getBolus_executed()
							&& !BasicUtils.isEmpty(item.getParenteralComment())) {
						notesText += item.getParenteralComment() + " ";
					}

					if (null != item.getLipid_delivered()) {
						lipidValue += item.getLipid_delivered();
					}

					if (null != item.getTpn_delivered()) {
						tpnValue += item.getTpn_delivered();
					}

//					if (null != item.getReadymadeDeliveredFeed()) {
//						ivValue += item.getReadymadeDeliveredFeed();
//					}

					if (null != item.getBolusDeliveredFeed()) {
						bolusValue += item.getBolusDeliveredFeed();
					}
				}

				if(bolusValue > 0){
					//notesText += "Bolus given to the baby " + bolusValue + " mL. ";
				}
				if(ivValue > 0){
					//notesText += "The baby had received " + Math.round(ivValue * 100) / 100 + " mL of readymade solution. " + htmlNextLine;
				}
				else{
					if (lipidValue > 0 && tpnValue > 0) {
						//notesText += "The baby had received " + lipidValue + " mL of Lipid IV and " + tpnValue + " mL of TPN solution. " + htmlNextLine;
					} else if (lipidValue > 0) {
						//notesText += "The baby had received " + lipidValue + " mL of Lipid IV. " + htmlNextLine;
					} else if (tpnValue > 0) {
						//notesText += "The baby had received " + tpnValue + " mL of TPN solution. " + htmlNextLine;
					}
				}
			}




			//Given Feeds
			if(!BasicUtils.isEmpty(todayWeight)) {
				String intakeOutputText = "";


				if(totalEn != 0 || totalPn != 0) {

					intakeOutputText += "Vol " + String.format("%.0f", (totalPn + totalEn + ivValue)) + " ml (" + String.format("%.01f", ((totalPn + totalEn + ivValue) / (todayWeight))) +
							" ml/kg)";


					if (totalPn != 0) {
						intakeOutputText += ", PN " + String.format("%.0f", (totalPn)) + " ml (" + String.format("%.01f", (totalPn / (todayWeight))) +
								" ml/kg )";
					}

					if (totalEn != 0) {
						intakeOutputText += ", EN " + String.format("%.0f", (totalEn)) + " ml (" + String.format("%.01f", (totalEn / (todayWeight))) +
								" ml/kg ";
						if (!BasicUtils.isEmpty(finalvolumeFinalStr)) {
							intakeOutputText += finalvolumeFinalStr;
						}
						intakeOutputText += ")";
					}

					if (ivValue != 0) {
						intakeOutputText += ", IV " + String.format("%.0f", (ivValue)) + " ml (" + String.format("%.01f", (ivValue / (todayWeight))) +
								" ml/kg )";
					}

					notesText += intakeOutputText;
				}


//					if(totalEn != 0 || totalPn != 0) {
//
//					System.out.println("totalEn------------"+totalEn);
//
//					System.out.println("totalPn------------"+totalPn);
//
//					System.out.println("ivValue------------"+ivValue);
//
//					System.out.println("todayWeight------------"+todayWeight);
//
//					float tflFinal = ((totalPn + totalEn + ivValue) / (todayWeight));
//					System.out.println("tflFinal------------"+tflFinal);
//
//					int tflFinalDay = (int) ((totalPn + totalEn + ivValue));
//
//					System.out.println("tflFinalDay------------"+tflFinalDay);
//
//					//	intakeOutputText += "TFL: " + tflFinal + " mL/kg/day (" + tflFinalDay + " ml/day). ";
//
//					intakeOutputText += "TFL: " +String.format("%.01f", tflFinal )+ " mL/kg, (";
//
//					System.out.println("intakeOutputText------------"+intakeOutputText);
//
//
//					if(totalEn != 0) {
//						float enFinal =  ((totalEn) / (todayWeight));
//						intakeOutputText +=  "EN: " + String.format("%.01f",enFinal )+ " mL/kg, ";
////						if(!BasicUtils.isEmpty(finalvolumeFinalStr)) {
////							intakeOutputText += finalvolumeFinalStr;
////						}
//						//intakeOutputText += ". ";
//
//					}
//
//					if(totalPn != 0) {
//						float pnFinal =  ((totalPn) / (todayWeight));
//
//						intakeOutputText +=  "PN: " + String.format("%.01f",pnFinal )+ " mL/kg ";
////
////						intakeOutputText +=  "PN: " + pnFinal + " mL/kg (";
////						if(lipidValue > 0) {
////							int lipidRate = (int) (lipidValue / 24);
////							intakeOutputText += "Lipid(20%) " + lipidValue + "mL@ " + String.format("%.01f", (lipidValue / (24))) + "mL/hr, ";
////						}
////						if(totalPnSol > 0) {
////							intakeOutputText += "AA + Dex: " + (int) totalPnSol + "mL@ " + String.format("%.01f", (totalPnSol / (24))) + "mL/hr";
////						}
////						if(totalBolus > 0) {
////							intakeOutputText += ", Bolus " + totalBolus + "mL ";
////						}
//					}
////					if(ivValue != 0) {
////						int ivFinal = (int) (ivValue / (todayWeight));
////						intakeOutputText +=  ", IV: " + ivFinal + " mL/kg/day";
////					}
//
//					intakeOutputText += ")";
//
//					intakeOutputText += ".";

//				}
				notesText += htmlNextLine;
			}


			if (!BasicUtils.isEmpty(intakeOutputMap.get("output"))) {
				float urineTotal = 0;
				float gastricTotal = 0;
				float drainTotal = 0;
				float stomaTotal = 0;
				int stoolCount = 0;
				int vomitCount = 0;
				String gaStr = "";
				String stoolStr = "";
				String vomitStr = "";

				List<NursingIntakeOutput> outputList = intakeOutputMap.get("output");
				for (NursingIntakeOutput item : outputList) {
					if (item.getUrinePassed() != null && item.getUrinePassed() && item.getUrine() != null) {
						try {
							urineTotal += parseFloat(item.getUrine());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					if (item.getGastricAspirate() != null) {
						try {
							gastricTotal += parseFloat(item.getGastricAspirate());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					if (item.getDrain() != null) {
						try {
							drainTotal += parseFloat(item.getDrain());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					if (item.getStoma() != null) {
						try {
							stomaTotal += parseFloat(item.getStoma());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					if (item.getStoolPassed() != null && item.getStoolPassed()) {
						stoolCount++;
						if (!(item.getStool() == null || item.getStool().isEmpty()
								|| stoolStr.contains(item.getStool()))) {
							stoolStr += ", " + item.getStool();
						}
					}

					if (!(item.getGastricAspirate() == null || item.getGastricAspirate().isEmpty())) {
						try {
							String gaValue = (item.getGastricAspirate());
							if (gaStr.isEmpty()) {
								gaStr = "Baby had gastric aspirate of ";
							} else {
								gaStr += ", ";
							}

							gaStr += gaValue + " ml at " + sdf.format(new Timestamp(item.getEntry_timestamp().getTime()));

						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					if (item.getVomitPassed() != null && item.getVomitPassed()) {
						if (vomitStr.isEmpty()) {
							vomitStr = "The baby had an episode of ";
						} else {
							vomitStr += ", episode of ";
						}
						vomitCount++;
						vomitStr += (BasicUtils.isEmpty(item.getVomitColor()) ? ""
								: (item.getVomitColor().equalsIgnoreCase("other") ? item.getVomitColorOther()
								: item.getVomitColor()) + " ")
								+ "vomiting "
								+ (BasicUtils.isEmpty(item.getVomit()) ? "" : "of " + item.getVomit() + " size ")
								+ "at " + sdf.format(new Timestamp(item.getEntry_timestamp().getTime()));
					}
				}

				if (urineTotal > 0) {
					if (!BasicUtils.isEmpty(todayWeight)) {
//						notesText += "Urine output as recorded was "
//								+ String.format("%.02f", (urineTotal / (todayWeight * 24))) + " mL/kg/hr. ";
					}
				}

				if (!vomitStr.isEmpty()) {
//					notesText += vomitStr + ". ";
				}

				if (!gaStr.isEmpty()) {
//					notesText += gaStr + ". ";
				}
//				notesText += htmlNextLine;

//				if(!BasicUtils.isEmpty(todayWeight)) {
//					notesText += "Total Output :" + String.format("%.02f", ((urineTotal + drainTotal + gastricTotal) / (todayWeight))) + " mL/kg/day. ";
//					if(!BasicUtils.isEmpty(urineTotal) || !BasicUtils.isEmpty(drainTotal) || !BasicUtils.isEmpty(gastricTotal)) {
//					notesText += "( ";
//					}
//					if(urineTotal != 0){
//						notesText += "Urine :" +  String.format("%.02f", (urineTotal / (todayWeight))) + " mL/kg/day. ";
//					}
//					if(gastricTotal != 0){
//						notesText += "Gastric Aspirate :" +  String.format("%.02f", (gastricTotal / (todayWeight))) + " mL/kg/day. ";
//					}
//					if(drainTotal != 0){
//						notesText += "Drain :" +  String.format("%.02f", (drainTotal / (todayWeight))) + " mL/kg/day. ";
//					}
//					if(!BasicUtils.isEmpty(urineTotal) || !BasicUtils.isEmpty(drainTotal) || !BasicUtils.isEmpty(gastricTotal)) {
//						notesText += ")";
//					}
//				}

				//Planned Input
//				if(!BasicUtils.isEmpty(todayWeight)) {
//
//					notesText += htmlNextLine;
//					if(totalEn != 0 || totalPn != 0) {
//						notesText += "Planned Input :" + String.format("%.02f", ((totalPn + totalEn) / (todayWeight))) +
//								" mL/kg/day (" + "EN - " + String.format("%.02f", (totalEn / (todayWeight))) +
//								" mL/kg/day, PN - " + String.format("%.02f", (totalPn / (todayWeight))) +
//								" mL/kg/day, IV - " + String.format("%.02f", (ivValue / (todayWeight))) + ").";
//					}
//
//					if(lipidValue != 0 || tpnValue != 0 || ivValue != 0 || bolusValue != 0 ||
//							totalMedicineVolume != 0 || totalHeplockVolume != 0 || totalBloodProductVolume != 0) {
//						notesText += "( ";
//					}
//					if(bolusValue != 0){
//						notesText += "Bolus :" +  String.format("%.02f", (bolusValue / (todayWeight))) + " mL/kg/day. ";
//					}
//					if(lipidValue != 0){
//						notesText += "Lipid :" +  String.format("%.02f", (lipidValue / (todayWeight))) + " mL/kg/day. ";
//					}
//					if(tpnValue != 0){
//						notesText += "TPN :" +  String.format("%.02f", (tpnValue / (todayWeight))) + " mL/kg/day. ";
//					}
//					if(ivValue != 0){
//						notesText += "Readymade :" +  String.format("%.02f", (ivValue / (todayWeight))) + " mL/kg/day. ";
//					}
//					if(totalMedicineVolume != 0){
//						notesText += "Medicine :" +  String.format("%.02f", (totalMedicineVolume / (todayWeight))) + " mL/kg/day. ";
//					}
//					if(totalHeplockVolume != 0){
//						notesText += "Heplock :" +  String.format("%.02f", (totalHeplockVolume / (todayWeight))) + " mL/kg/day. ";
//					}
//					if(totalBloodProductVolume != 0){
//						notesText += "Blood Product :" +  String.format("%.02f", (totalBloodProductVolume / (todayWeight))) + " mL/kg/day. ";
//					}
//					if(lipidValue != 0 || tpnValue != 0 || ivValue != 0 || bolusValue != 0 ||
//							totalMedicineVolume != 0 || totalHeplockVolume != 0 || totalBloodProductVolume != 0) {
//						notesText += ")";
//					}
//					notesText += ").";
//					notesText += htmlNextLine;
//				}


				//Output
				if(!BasicUtils.isEmpty(todayWeight)) {
					notesText += "Output : ";
					if(!BasicUtils.isEmpty(urineTotal)) {
						if(urineTotal != 0){
							notesText += "Urine :" +  String.format("%.01f", (urineTotal / (todayWeight * duration))) + " mL/kg/hr";
						}
					}

					if(!BasicUtils.isEmpty(drainTotal)) {
						if(drainTotal != 0){
							notesText += ", Stoma :" +  String.format("%.01f", drainTotal) + " ml";
						}
					}

					if(!BasicUtils.isEmpty(stomaTotal)) {
						if(stomaTotal != 0){
							notesText += ", Stoma :" +  String.format("%.01f", stomaTotal) + " gm";
						}
					}

					if (stoolCount == 1) {
						notesText += ", Stool : 1 time ";
					} else if (stoolCount > 1) {
						notesText += ", Stool : " + stoolCount + " times ";
					} else {
						notesText += ", No stool passed ";
					}

					if (vomitCount == 1) {
						notesText += ", Vomit : 1 time. ";
					} else if (vomitCount > 1) {
						notesText += ", Vomit : " + vomitCount + " times. ";
					}



					if (!gaStr.isEmpty()) {
						notesText += gaStr + ". ";
					}
				}
			}
		}

		return notesText;
	}

	private static final SimpleDateFormat monthDayYearformatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	// Get Date from Timestamp
	public static String getDateFromTimestamp(Timestamp time) {
		String date = monthDayYearformatter.format((java.util.Date) time);
		return date;
	}

	@Override
	@SuppressWarnings({ "unchecked", "deprecation" })
	public DoctorOrdersPOJO getDoctorOrders(String uhid, String dateStr) {
		DoctorOrdersPOJO returnObj = new DoctorOrdersPOJO();

		String htmlNextLine = System.getProperty("line.separator");// "&#13;&#10;";
		long oneDay = 24 * 60 * 60 * 1000;
		SimpleDateFormat sdfTimestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
		int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
				- TimeZone.getDefault().getRawOffset();

		try {

			java.util.Date dateObj = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
			dateObj.setHours(8);
			dateObj.setMinutes(0);
			dateObj.setSeconds(0);
			Timestamp todayObj = new Timestamp(dateObj.getTime() - offset);

			Timestamp tomObj = new Timestamp(todayObj.getTime() + oneDay);
			Timestamp yesObj = new Timestamp(todayObj.getTime() - oneDay);
			String strDateTom = sdfTimestamp.format(tomObj);
			String strDateToday = sdfTimestamp.format(todayObj);
			String strDateYes = sdfTimestamp.format(yesObj);

			String enAddtivesBrand = "select obj from RefEnAddtivesBrand obj";
			List<RefEnAddtivesBrand> enAddtivesList = notesDoa.getListFromMappedObjNativeQuery(enAddtivesBrand);
			if (!BasicUtils.isEmpty(enAddtivesList)) {
				returnObj.setAddtivesbrandList(enAddtivesList);
			}

			// getting baby visit weight details
			String queryCurrentBabyVisit = "select obj from BabyVisit as obj where uhid='" + uhid
					+ "' and concat(visitdate,' ',visittime) >= '" + strDateToday
					+ "' and concat(visitdate,' ',visittime) <= '" + strDateTom
					+ "' order by visitdate desc, visittime desc";
			List<BabyVisit> currentBabyVisitList = notesDoa.getListFromMappedObjNativeQuery(queryCurrentBabyVisit);
			BabyVisit babyVisit = null;
			if (!BasicUtils.isEmpty(currentBabyVisitList)) {
				babyVisit = currentBabyVisitList.get(0);
			}

			queryCurrentBabyVisit = "select obj from BabyVisit as obj where uhid='" + uhid
					+ "' and concat(visitdate,' ',visittime) >= '" + strDateYes
					+ "' and concat(visitdate,' ',visittime) <= '" + strDateToday
					+ "' order by visitdate desc, visittime desc";
			List<BabyVisit> prevBabyVisitList = notesDoa.getListFromMappedObjNativeQuery(queryCurrentBabyVisit);
			if (!BasicUtils.isEmpty(prevBabyVisitList)) {
				BabyVisit prevBabyVisit = prevBabyVisitList.get(0);
				Float prevWeight = prevBabyVisit.getCurrentdateweight();
				if (!BasicUtils.isEmpty(babyVisit)) {
					babyVisit.setPrevDateWeight(prevWeight);
				}
			}

			if (!BasicUtils.isEmpty(babyVisit)) {
				returnObj.setBasicDetails(babyVisit);
			}

			//Fetching Procedure Orders
			List<ProcedureOther> procedureData = inicuDoa
					.getListFromMappedObjQuery(HqlSqlQueryConstants.getProcedureData(uhid,todayObj,tomObj));
			if (!BasicUtils.isEmpty(procedureData)) {
				returnObj.setProcedureObj(procedureData);
			}

			//Fetching Feed Orders
			List<BabyfeedDetail> babyFeedAllList = inicuDoa.getListFromMappedObjQuery(
					HqlSqlQueryConstants.getBabyfeedDetailList(uhid, todayObj, tomObj));
			if (!BasicUtils.isEmpty(babyFeedAllList)) {
				returnObj.setBabyFeedObj(babyFeedAllList);
				for(BabyfeedDetail feedObj : babyFeedAllList) {
					processFeedMethod(feedObj);
					processFeedType(feedObj);
				}
			}

			//Blood Product Volume only included in PN if the doctor agrees to it
			List<DoctorBloodProducts> bloodList = notesDoa.getListFromMappedObjNativeQuery(
					HqlSqlQueryConstants.getBloodProductData(uhid, todayObj, tomObj));
			if (!BasicUtils.isEmpty(bloodList)) {
				returnObj.setBloodProductObj(bloodList);
			}

			//Heplock Volume only included in PN if the doctor agrees to it
			List<CentralLine> centralLineList = notesDoa.getListFromMappedObjNativeQuery(
					HqlSqlQueryConstants.getHeplockData(uhid, todayObj, tomObj));
			if (!BasicUtils.isEmpty(centralLineList)) {
				for(CentralLine obj : centralLineList){
					String orderString = "";
					orderString = calculateOrderString(obj);
					obj.setOrderString(orderString);
				}
				returnObj.setCentralLineObj(centralLineList);
			}

			//Fetching Only those Medicines which has IV route
			List<BabyPrescription> IVPrescriptionList = notesDoa.getListFromMappedObjNativeQuery(
					HqlSqlQueryConstants.getIVMedData(uhid, todayObj, tomObj));
			if (!BasicUtils.isEmpty(IVPrescriptionList)) {
				returnObj.setIVMedicineList(IVPrescriptionList);
			}

			//Fetching All Medicines
			List<BabyPrescription> babyPrescriptionList = notesDoa.getListFromMappedObjNativeQuery(
					HqlSqlQueryConstants.getTodayMedicationList(uhid, todayObj, tomObj));
			if (!BasicUtils.isEmpty(babyPrescriptionList)) {
				returnObj.setBabyPrescriptionList(babyPrescriptionList);
			}

			List<BabyVisit> pastAnthropometryList = inicuDoa.getListFromMappedObjQuery(
					HqlSqlQueryConstants.getBabyAnthropometryList(uhid, todayObj, tomObj));

			// calculating feed calculator....
			FeedCalculatorPOJO calculator = new FeedCalculatorPOJO();

			if (!BasicUtils.isEmpty(babyFeedAllList)) {
				String nutritionCalculator = "select obj from RefNutritioncalculator obj";
				List<RefNutritioncalculator> nutritionList = notesDoa.getListFromMappedObjNativeQuery(nutritionCalculator);
				calculator.setRefNutritionInfo(nutritionList);

				BabyfeedDetail feedLast = babyFeedAllList.get(0);

				if(pastAnthropometryList != null && pastAnthropometryList.size() > 0) {
					if(!BasicUtils.isEmpty(pastAnthropometryList.get(0).getCurrentdateweight())) {
						String weight = (pastAnthropometryList.get(0).getCurrentdateweight() / 1000) + "";
						CaclulatorDeficitPOJO cuurentDeficitLast = getDeficitFeedCalculator(feedLast, nutritionList,
								weight);
						calculator.setLastDeficitCal(cuurentDeficitLast);
					}
					returnObj.setFeedCalulator(calculator);
				}
			}

			// get investigations for "uhid" which are in sent state
			List<InvestigationOrdered> invOrderedList = null;
			String queryinvOrderedList = "select t from InvestigationOrdered t "
					+ " WHERE uhid='" + uhid + "' and investigationorder_time >= '" + todayObj + "' and investigationorder_time <= '" + tomObj + "'";
			invOrderedList = notesDoa.getListFromMappedObjNativeQuery(queryinvOrderedList);
			if (!BasicUtils.isEmpty(invOrderedList)) {
				returnObj.setInvOrderedList(invOrderedList);
			}


		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUser,
					uhid, "getDailyProgressNotes", BasicUtils.convertErrorStacktoString(e));
		}
		return returnObj;

	}

	void processFeedMethod(BabyfeedDetail feedObj) {

		if(!BasicUtils.isEmpty(feedObj.getFeedmethod()))
		{
			String finalFeedStr = "";
			String feedStr = "" + feedObj.getFeedmethod();
			feedStr = feedStr.replace("[", "").replace("]", "").replace(" ", "");
			if(feedStr.contains(",")) {
				String[] feedArry = feedStr.split(",");
				for(int i = 0; i < feedArry.length; i++) {
					String getFeedType = "SELECT feedmethodname, feedmethodid from " +
							BasicConstants.SCHEMA_NAME + ".ref_masterfeedmethod where feedmethodid = '" + feedArry[i] + "'";
					List<Object[]> getFeedTypeData = userServiceDao.executeNativeQuery(getFeedType);
					if(getFeedTypeData != null && getFeedTypeData.size() > 0) {
						if(getFeedTypeData.get(0)[0] != null) {
							if(finalFeedStr == "") {
								finalFeedStr += getFeedTypeData.get(0)[0];
							}
							else {
								finalFeedStr += ", " + getFeedTypeData.get(0)[0];
							}
						}
					}
				}
			}
			else {
				String getFeedType = "SELECT feedmethodname, feedmethodid from " +
						BasicConstants.SCHEMA_NAME + ".ref_masterfeedmethod where feedmethodid = '" + feedStr + "'";
				List<Object[]> getFeedTypeData = userServiceDao.executeNativeQuery(getFeedType);
				if(getFeedTypeData != null && getFeedTypeData.size() > 0) {
					if(getFeedTypeData.get(0)[0] != null) {
						finalFeedStr += getFeedTypeData.get(0)[0];
					}
				}
			}
			feedObj.setFeedmethod("" + finalFeedStr);
		}
	}

	void processFeedType(BabyfeedDetail feedObj) {

		String finalFeedStr = "";
		if(!BasicUtils.isEmpty(feedObj.getFeedtype()))
		{
			String feedStr = "" + feedObj.getFeedtype();
			feedStr = feedStr.replace("[", "").replace("]", "").replace(" ", "");
			if(feedStr.contains(",")) {
				String[] feedArry = feedStr.split(",");
				for(int i = 0; i < feedArry.length; i++) {
					String getFeedType = "SELECT feedtypename, feedtypeid from " +
							BasicConstants.SCHEMA_NAME + ".ref_masterfeedtype where feedtypeid = '" + feedArry[i] + "'";
					List<Object[]> getFeedTypeData = userServiceDao.executeNativeQuery(getFeedType);
					if(getFeedTypeData != null && getFeedTypeData.size() > 0) {
						if(getFeedTypeData.get(0)[0] != null) {
							if(finalFeedStr == "") {
								finalFeedStr += getFeedTypeData.get(0)[0];
							}
							else {
								finalFeedStr += ", " + getFeedTypeData.get(0)[0];
							}
						}
					}
				}
			}
			else {
				String getFeedType = "SELECT feedtypename, feedtypeid from " +
						BasicConstants.SCHEMA_NAME + ".ref_masterfeedtype where feedtypeid = '" + feedStr + "'";
				List<Object[]> getFeedTypeData = userServiceDao.executeNativeQuery(getFeedType);
				if(getFeedTypeData != null && getFeedTypeData.size() > 0) {
					if(getFeedTypeData.get(0)[0] != null) {
						finalFeedStr += getFeedTypeData.get(0)[0];
					}
				}
			}
		}
		if(!BasicUtils.isEmpty(feedObj.getFeedTypeSecondary()))
		{
			String feedStr = "" + feedObj.getFeedTypeSecondary();
			feedStr = feedStr.replace("[", "").replace("]", "").replace(" ", "");
			if(feedStr.contains(",")) {
				String[] feedArry = feedStr.split(",");
				for(int i = 0; i < feedArry.length; i++) {
					String getFeedType = "SELECT feedtypename, feedtypeid from " +
							BasicConstants.SCHEMA_NAME + ".ref_masterfeedtype where feedtypeid = '" + feedArry[i] + "'";
					List<Object[]> getFeedTypeData = userServiceDao.executeNativeQuery(getFeedType);
					if(getFeedTypeData != null && getFeedTypeData.size() > 0) {
						if(getFeedTypeData.get(0)[0] != null) {
							if(finalFeedStr == "") {
								finalFeedStr += getFeedTypeData.get(0)[0];
							}
							else {
								finalFeedStr += ", " + getFeedTypeData.get(0)[0];
							}
						}
					}
				}
			}
			else {
				String getFeedType = "SELECT feedtypename, feedtypeid from " +
						BasicConstants.SCHEMA_NAME + ".ref_masterfeedtype where feedtypeid = '" + feedStr + "'";
				List<Object[]> getFeedTypeData = userServiceDao.executeNativeQuery(getFeedType);
				if(getFeedTypeData != null && getFeedTypeData.size() > 0) {
					if(getFeedTypeData.get(0)[0] != null) {
						if(finalFeedStr == "") {
							finalFeedStr += getFeedTypeData.get(0)[0];
						}
						else {
							finalFeedStr += ", " + getFeedTypeData.get(0)[0];
						}
					}
				}
			}
		}
		feedObj.setFeedtype("" + finalFeedStr);
	}

	String calculateOrderString(CentralLine obj){
		String orderString = "";
		if(obj!= null){
		if(obj.getSolutionType().equals("Normal Saline")){
			Float remVolume = obj.getHeparinTotalVolume() - obj.getHeparinVolume();
			orderString = "Add " + remVolume +  " ml of NS with " + obj.getHeparinVolume() + " ml of Heplock to be given at the rate of " + obj.getHeparinRate() + " ml/hr";
		}
		if(obj.getSolutionType().equals("0.25 NS in DW")){
			Float remVolume = obj.getHeparinTotalVolume() - obj.getHeparinVolume();
			Float NSVolume = remVolume/4;
			Float DWVolume = remVolume - NSVolume;
			orderString = "Add " + NSVolume +  " ml of NS in " + DWVolume + "ml of DN to create " + obj.getHeparinTotalVolume() + " ml of solution " + remVolume + " ml of (0.25 NS in DW) prepared solution with " + obj.getHeparinVolume() +" ml of Heplock to be given at the rate of " + obj.getHeparinRate() + " ml/hr";
		}
		}
		return orderString;
	}

	@Override
	public DailyProgressNotes setDailyProgressNotes(DailyProgressNotes progressNote) {
		try {
			progressNote = (DailyProgressNotes) inicuDoa.saveObject(progressNote);
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUser,
					uhid, "setDailyProgressNotes", BasicUtils.convertErrorStacktoString(e));
		}
		return progressNote;
	}



	/**
	 * @param uhid
	 * @return DoctorBloodProductPojo Object for the UHID
	 */
	@Override
	@SuppressWarnings("unchecked")
	public DoctorBloodProductPojo getDoctorBloodProducts(String uhid, String branchName) {
		DoctorBloodProductPojo returnObj = new DoctorBloodProductPojo();
		DoctorBloodProducts prescriptionObj = returnObj.getCurrentObj();
		returnObj.setUserList(getUserList("doctor", branchName));
		List<DoctorBloodProducts> pastList = inicuDoa.getListFromMappedObjQuery(
				HqlSqlQueryConstants.getDoctorBloodProductList(uhid) + " order by assessment_time desc");
		returnObj.setPastList(pastList);
		if (!BasicUtils.isEmpty(pastList)) {
			returnObj.getCurrentObj().setStatus(pastList.get(0).getStatus());
		}

		prescriptionObj.setPrescriptionList(getPastPrescriptionList(uhid));
		returnObj.setCurrentObj(prescriptionObj);

		// get the last Nutrition Object between 8Am to 8Am
		String babyFeed=getLastFeedString(uhid);
		List<BabyfeedDetail> babyfeedDetails=inicuDoa.getListFromMappedObjQuery(babyFeed);
		if(!BasicUtils.isEmpty(babyfeedDetails)){
			// set the last nutrition object
			returnObj.setLastBabyFeedObject(babyfeedDetails.get(0));
		}

		return returnObj;
	}

	@SuppressWarnings("unchecked")
	private List<BabyPrescription> getPastPrescriptionList(String uhid) {
		String queryPastPrescriptionList = "select obj from BabyPrescription as obj where isactive='true' and uhid='"
				+ uhid + "' order by creationtime desc";
		List<BabyPrescription> pastPrescriptionList = inicuDoa.getListFromMappedObjQuery(queryPastPrescriptionList);
		return pastPrescriptionList;
	}

	/**
	 * @param DoctorBloodProducts
	 * @return save the current object and get updated DoctorBloodProductPojo Object
	 */

	@Override
	@SuppressWarnings("unchecked")
	public ResponseMessageWithResponseObject setDoctorBloodProducts(DoctorBloodProducts currentObj, String branchName) {
		ResponseMessageWithResponseObject returnObj = new ResponseMessageWithResponseObject();
		String uhid = currentObj.getUhid();
		try {
			List<BabyPrescription> prescriptionList = currentObj.getPrescriptionList();

			if (!BasicUtils.isEmpty(prescriptionList)) {
				for (int i = 0; i < prescriptionList.size(); i++) {
					BabyPrescription babyPrescription = (BabyPrescription) prescriptionList.get(i);
				}
				this.savePrescriptionList(null, prescriptionList, "BloodProducts", null,
						currentObj.getAssessment_time());
			}
			currentObj = (DoctorBloodProducts) inicuDoa.saveObject(currentObj);
			if (!BasicUtils.isEmpty(currentObj.getPlan_test())) {
				Long investigationOrderTime = currentObj.getAssessment_time().getTime();
				if (currentObj.getTest_time() != null) {
					if (currentObj.getTest_time_type().equalsIgnoreCase("hour")) {
						investigationOrderTime += ((Float) (currentObj.getTest_time() * 60 * 60 * 1000)).longValue();
					} else {
						investigationOrderTime += ((Float) (currentObj.getTest_time() * 60 * 1000)).longValue();
					}
				}

				// HB - 100002, APTT - 100056, PT - 100049, Platelet count - 100027
				String sql = "select obj from RefTestslist obj where testid in ";
				if (currentObj.getPlan_test().equalsIgnoreCase("Hb")) {
					sql += "('100002')";
				} else if (currentObj.getPlan_test().equalsIgnoreCase("Platelet Count")) {
					sql += "('100027')";
				} else if (currentObj.getPlan_test().equalsIgnoreCase("PT")) {
					sql += "('100049')";
				} else if (currentObj.getPlan_test().equalsIgnoreCase("APTT")) {
					sql += "('100056')";
				} else if (currentObj.getPlan_test().equalsIgnoreCase("PT & APTT")) {
					sql += "('100049', '100056')";
				}
				List<RefTestslist> testList = inicuDoa.getListFromMappedObjQuery(sql);
				saveOrderInvestigation(testList, currentObj.getDoctor_blood_products_id(), uhid,
						currentObj.getLoggeduser(), "Blood Product", new Timestamp(investigationOrderTime));
			}
			if(currentObj.getIsIncludeInPN()) {
				float currentRate=getCalculatedPNRate(uhid,1,currentObj);

			}

			returnObj.setType("success");
			returnObj.setReturnedObject(getDoctorBloodProducts(uhid, branchName));
		} catch (Exception e) {
			returnObj.setType("failure");
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID,
					currentObj.getLoggeduser(), uhid, "setDoctorBloodProducts",
					BasicUtils.convertErrorStacktoString(e));
		}
		return returnObj;
	}

	@Override
	public float getCalculatedPNRate(String uhid,int updateRate,DoctorBloodProducts currentObj){
		float calculatedRate=0;
		String lastFeedQuery=getLastFeedString(uhid);
		List<BabyfeedDetail> babyFeedAllList = notesDoa.getListFromMappedObjNativeQuery(lastFeedQuery);
		if (!BasicUtils.isEmpty(babyFeedAllList)) {
			BabyfeedDetail tpnObj = babyFeedAllList.get(0);
			int timeDiff=tpnObj.getEntryDateTime().getHours()-currentObj.getAssessment_time().getHours();
			int duration=tpnObj.getDuration();
			if(timeDiff<0){
				timeDiff=timeDiff*-(1);
			}

			int denominator=duration-timeDiff;

			if(denominator<0){
				denominator=denominator*-(1);
			}

			if(tpnObj.getIsparentalgiven()!=null && tpnObj.getIsparentalgiven()) {
				if(tpnObj.getIsReadymadeSolutionGiven()) {

					if(!BasicUtils.isEmpty(tpnObj.getReadymadeFluidRate())){
						float readyMadeRate=tpnObj.getReadymadeFluidRate();
						float totalVolume=currentObj.getTotal_volume();
						calculatedRate = ((readyMadeRate* duration) - (totalVolume+timeDiff*readyMadeRate)) / denominator;
					}

					if(updateRate==1) {
						tpnObj.setBabyfeedid(null);
						tpnObj.setBloodProductGiven(true);
						tpnObj.setEntryDateTime(currentObj.getAssessment_time());
						tpnObj.setReadymadeFluidRate(calculatedRate);
						tpnObj.setBloodProductMessage("Rate has been revised due to blood product");
						try {
							inicuDoa.saveObject(tpnObj);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				} else if(!tpnObj.getIsReadymadeSolutionGiven()) {

					if(!BasicUtils.isEmpty(tpnObj.getIvfluidrate())){
						float ivFluidsRate=parseFloat(tpnObj.getIvfluidrate());
						float totalVolume=currentObj.getTotal_volume();
						calculatedRate = ((ivFluidsRate* duration) - (totalVolume+timeDiff*ivFluidsRate)) / denominator;
					}

					if(updateRate==1) {
						tpnObj.setBabyfeedid(null);
						tpnObj.setBloodProductGiven(true);
						tpnObj.setEntryDateTime(currentObj.getAssessment_time());
						tpnObj.setIvfluidrate(String.valueOf(calculatedRate));
						tpnObj.setBloodProductMessage("Rate has been revised due to blood product");
						try {
							inicuDoa.saveObject(tpnObj);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return calculatedRate;
	}

	public String getLastFeedString(String uhid){
		String lastFeedQuery = "";
		int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
				- TimeZone.getDefault().getRawOffset();

		// Cycle runs from 8AM to 8AM.
		Timestamp today = new Timestamp((new java.util.Date().getTime()));
		Timestamp currentDate = new Timestamp((new java.util.Date().getTime()));
		Timestamp yesterday = new Timestamp((new java.util.Date().getTime()) - (24 * 60 * 60 * 1000));
		Timestamp tomorrow = new Timestamp((new java.util.Date().getTime()) + (24 * 60 * 60 * 1000));

		today.setHours(8);
		today.setMinutes(0);
		today.setSeconds(0);

		yesterday.setHours(8);
		yesterday.setMinutes(0);
		yesterday.setSeconds(0);

		tomorrow.setHours(8);
		tomorrow.setMinutes(0);
		tomorrow.setSeconds(0);

		currentDate = new Timestamp(currentDate.getTime() + offset);
		today = new Timestamp(today.getTime() - offset);
		yesterday = new Timestamp(yesterday.getTime() - offset);
		tomorrow = new Timestamp(tomorrow.getTime() - offset);


		// get feed details from last 24 hrs(8AM to 8AM)
		if(currentDate.getHours() >= 8){
			lastFeedQuery = "select obj from BabyfeedDetail obj where uhid='" + uhid + "' and entrydatetime<='"
					+ tomorrow + "' and  entrydatetime>='" + today + "' order by entrydatetime desc";
		} else{
			lastFeedQuery = "select obj from BabyfeedDetail obj where uhid='" + uhid + "' and entrydatetime<='"
					+ today + "' and  entrydatetime>='" + yesterday + "' order by entrydatetime desc";
		}

		return lastFeedQuery;
	}

	public void savePrescriptionList(InicuDao inicuDaoObj, List<BabyPrescription> prescriptionList,
			String assessmentName, Long assessmentId, Timestamp currentTime) {
		if (inicuDaoObj != null) {
			inicuDoa = inicuDaoObj;
		}
		try {
			for (BabyPrescription babyPrescription : prescriptionList) {
				if (babyPrescription.getBabyPresid() == null) {
					//babyPrescription.setEventid(assessmentId.toString());
					babyPrescription.setEventname(assessmentName);
				}
			}
			prescriptionList = (List<BabyPrescription>) inicuDoa.saveMultipleObject(prescriptionList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@SuppressWarnings("unchecked")
	private List<KeyValueObj> getUserList(String type, String branchName) {
		List<KeyValueObj> refKeyValueList = new ArrayList<KeyValueObj>();
		try {
			List<User> refList = inicuDoa.getListFromMappedObjQuery(HqlSqlQueryConstants.getUserList(branchName));
			if (refList != null) {
				KeyValueObj keyValue = null;
				Iterator<User> iterator = refList.iterator();
				while (iterator.hasNext()) {
					keyValue = new KeyValueObj();
					User obj = iterator.next();
					keyValue.setKey(obj.getUserName());
					keyValue.setValue(obj.getFirstName() + " " + obj.getLastName());
					String userObjQuery = "SELECT user FROM UserRolesTable as user WHERE userName='" + obj.getUserName()
							+ "' and branchname = '" + branchName + "'";
					List<UserRolesTable> result = inicuDoa.getListFromMappedObjQuery(userObjQuery);
					if (result != null && result.size() > 0) {
						UserRolesTable userRoles = (UserRolesTable) result.get(0);
						if (userRoles != null) {
							String roleId = userRoles.getRoleId();
							if (type.equalsIgnoreCase("doctor")) {
								if (roleId.equals("2") || roleId.equals("3")) {
									refKeyValueList.add(keyValue);
								}
							} else if (type.equalsIgnoreCase("nurse")) {
								if (roleId.equals("5")) {
									refKeyValueList.add(keyValue);
								}
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return refKeyValueList;
	}

	private void saveOrderInvestigation(List<RefTestslist> testList, Long assessmentdid, String uhid, String userId,
			String pageName, Timestamp investigationOrderTime) throws Exception {
		for (RefTestslist test : testList) {
			InvestigationOrdered investigationOrder = new InvestigationOrdered();
			investigationOrder.setAssesment_type(pageName);
			investigationOrder.setAssesmentid(String.valueOf(assessmentdid));
			investigationOrder.setUhid(uhid);
			investigationOrder.setCategory(test.getAssesmentCategory());
			investigationOrder.setTestcode(test.getTestcode());
			investigationOrder.setTestname(test.getTestname());
			investigationOrder.setTestslistid(test.getTestid());
			investigationOrder.setInvestigationorder_user(userId);
			investigationOrder.setOrder_status("ordered");
			investigationOrder.setInvestigationorder_time(investigationOrderTime);
			inicuDoa.saveObject(investigationOrder);
		}
	}

	public CgaAndDolPOPJO getCgaAndDol(String uhid, String dateString) {
		CgaAndDolPOPJO returnObj = new CgaAndDolPOPJO();
		try {

			int fromTime = 6;
			String progressNoteFormatType = "";
			String babyDetailList = "select obj from BabyDetail as obj where uhid='" + uhid + "'";
			List<BabyDetail> babyObj = notesDoa.getListFromMappedObjNativeQuery(babyDetailList);

			SimpleDateFormat sdfTimestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm a");



		  java.util.Date dateObj = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
		  Timestamp originalTodayDate = new Timestamp(dateObj.getTime());

			int gestDay;
			int gestWeek;
			int gestWeekFinal;

			int lifeWeeks;
			int lifeDays;
			int totalDaysOfGestation;
			int weeksAfterDayAdd;

			Calendar cal1 = Calendar.getInstance();

			java.util.Date dateOfBirthTemp=babyObj.get(0).getDateofbirth();
			System.out.println("*****First Date" + dateOfBirthTemp.getTime());

			String timeOfBirth=babyObj.get(0).getTimeofbirth();


			if(dateOfBirthTemp!=null) {
				Timestamp dateOfBirth = new Timestamp(dateOfBirthTemp.getTime());
				System.out.println("*****Second Date" + dateOfBirth.getTime());

				String[] tobStr = timeOfBirth.split(",");
				if (tobStr[2] == "PM" && Integer.parseInt(tobStr[0]) != 12) {
					dateOfBirth.setHours(12 + Integer.parseInt(tobStr[0]));
				} else if (tobStr[2] == "AM" && Integer.parseInt(tobStr[0]) == 12) {
					dateOfBirth.setHours(0);
				} else {
					dateOfBirth.setHours(Integer.parseInt(tobStr[0]));
				}
				dateOfBirth.setMinutes(Integer.parseInt(tobStr[1]));
				System.out.println("*****Third Date" + dateOfBirth.getTime());
				System.out.println("*****Today Date" + originalTodayDate.getTime());


				double difference = ((originalTodayDate.getTime()-dateOfBirth.getTime())/86400000)+1;
				System.out.println("*****First DOL" + difference);

				int ageOfDays = (int) java.lang.Math.ceil(difference);
				System.out.println("*****Second DOL" + ageOfDays);

				if (babyObj.get(0).getGestationdaysbylmp() != null && babyObj.get(0).getGestationweekbylmp() != null) {
					gestDay = babyObj.get(0).getGestationdaysbylmp();
					gestWeek = babyObj.get(0).getGestationweekbylmp();
					gestWeekFinal = babyObj.get(0).getGestationweekbylmp();

				} else {
					gestDay = 0;
					gestWeek = 0;
					gestWeekFinal = 0;
				}

				if (ageOfDays > 7) {
					lifeWeeks =ageOfDays / 7;
					lifeDays = ageOfDays - lifeWeeks * 7;
					gestWeek += lifeWeeks;
					totalDaysOfGestation = lifeDays + gestDay;
					if (totalDaysOfGestation > 6) {

						weeksAfterDayAdd = (totalDaysOfGestation / 7);
						gestWeek += weeksAfterDayAdd;
						gestDay = totalDaysOfGestation - weeksAfterDayAdd * 7;

					} else {
						gestDay = (int) totalDaysOfGestation;
					}
				} else {
					totalDaysOfGestation = ageOfDays + gestDay;
					if (totalDaysOfGestation > 6) {
						weeksAfterDayAdd = (totalDaysOfGestation / 7);

						gestWeek += weeksAfterDayAdd;
						gestDay = (int) (totalDaysOfGestation - weeksAfterDayAdd * 7);
					} else {
						gestDay = (int) totalDaysOfGestation;
					}
				}

//				if (gestDay == 0) {
//					gestDay = 6;
//					gestWeek = gestWeek - 1;
//				} else {
//					gestDay = gestDay - 1;
//				}

				if(progressNoteFormatType.equalsIgnoreCase("Configurable")) {
					ageOfDays = ageOfDays + 1;
					if(gestDay == 6) {
						gestDay = 0;
						gestWeek++;
					}else {
						gestDay++;
					}
				}
				returnObj.setCurrentDol(ageOfDays);
				returnObj.setCurrentGestationDays(gestDay);
				returnObj.setCurrentGestationWeeks(gestWeek);
			}

		}
		catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUser,
					uhid, "getDailyProgressNotes", BasicUtils.convertErrorStacktoString(e));
		}

		return returnObj;

	}

	@Override
	public ResponseMessageObject deleteNursingNotes(String uhid, Long nursingNotesId) {
		// TODO Auto-generated method stub
		ResponseMessageObject returnObj = new ResponseMessageObject();
		NursingNotesPojo nursingNotesObj = new NursingNotesPojo();
		String roleid = null;
		try {
			
//			String userRoleQuery = "select obj from UserRoles as obj where username = '" + loggedUser + "' and branchname = '" + branchName + "'";
//			List<UserRoles> userList = inicuDoa.getListFromMappedObjQuery(userRoleQuery);
//			if(!BasicUtils.isEmpty(userList)) {
//				 roleid = userList.get(0).getRoleId();
//			}
			if(!BasicUtils.isEmpty(nursingNotesId)) {
				String query = "update nursing_notes set flag = false where nursing_notes_id = '" + nursingNotesId + "'";
				inicuDoa.updateOrDeleteNativeQuery(query);
//				nursingNotesObj = getNursingNotes(uhid, null, null);
//				
				returnObj.setMessage(BasicConstants.MESSAGE_SUCCESS);
			}
			else {
				returnObj.setMessage("Please login with super user credentials to delete!");
				returnObj.setStatus_code(403);
			}
		}
		catch(Exception e) {
			returnObj.setType(BasicConstants.MESSAGE_FAILURE);
			returnObj.setMessage("Failure in discharging baby");
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "",
					uhid, "GET_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		return returnObj;
	}

}
