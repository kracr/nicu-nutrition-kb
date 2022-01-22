/**
 * 
 */
package com.inicu.hl7.data.acquisition;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inicu.cassandra.entities.DeviceDataInfinity;
import com.inicu.cassandra.service.DeviceDataService;
import com.inicu.cassandra.utility.InicuCassandraTemplate;
import com.inicu.postgres.dao.PatientDao;
import com.inicu.postgres.entities.DeviceMonitorDetail;
import com.inicu.postgres.utility.BasicConstants;
import com.inicu.postgres.utility.BasicUtils;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v23.group.ORU_R01_ORDER_OBSERVATION;
import ca.uhn.hl7v2.model.v23.message.ORU_R01;
import ca.uhn.hl7v2.model.v23.segment.OBX;
import ca.uhn.hl7v2.parser.Parser;

/**
 * @author sanoob iNICU 03-Mar-2017
 */

@Repository
public class HL7Parser {

	@Autowired
	PatientDao patientDao;

	
	@SuppressWarnings("resource")
	public void parse(String hl7Message) {
		
		JSONObject dataJson = new JSONObject();
		HapiContext context = null;
		Parser parser = null;
		Message hl7Msg;
		ORU_R01 oruMsg;
		ORU_R01_ORDER_OBSERVATION orderObservation;
		StringBuffer strBuffer = new StringBuffer();
		//String PID = "";
		String timestamp = "";
		String bedId = "";
		HashMap<String, Float> paramMap = new HashMap<>();

		try {
			System.out.println("Inside HL7Parser");
			context = new DefaultHapiContext();
			parser = context.getGenericParser();
			hl7Msg = parser.parse(hl7Message);
			oruMsg = (ORU_R01) hl7Msg;
			orderObservation = oruMsg.getRESPONSE().getORDER_OBSERVATION();

			//PID = oruMsg.getRESPONSE().getPATIENT().getPID().encode();
			timestamp = oruMsg.getMSH().getDateTimeOfMessage().encode();
			bedId = oruMsg.getRESPONSE().getPATIENT().getVISIT().getPV1().getPv13_AssignedPatientLocation().encode();

			String [] splitBed = Util.insertIntoHashMap(bedId);
			// System.out.println(bedId);

			/*
			 * parse the OBX segment
			 */
			int count = countSubstring(hl7Message, HL7Constants.SEG_OBX);
			for (int index = 0; index < count; index++) {

				OBX obx = orderObservation.getOBSERVATION(index).getOBX();
				String temp = obx.encode();
				if (temp.contains(HL7Constants.TYPE_ST)) {

				} else if (temp.contains(HL7Constants.TYPE_SN)) {

					String obx5encoded = obx.getObx5_ObservationValue(0).encode();
					String obxId = obx.getObx3_ObservationIdentifier().encode();
					strBuffer.append(obxId + "" + obx5encoded + "\n");
					String split[] = obxId.split("\\^\\^");
					dataJson.put(split[0], Util.cleanValue(obx5encoded));
					paramMap.put(split[0], Util.cleanAndConvert(obx5encoded));
				}

			}

//			System.out.println("parsed output \n" + strBuffer.toString());
//			System.out.println("JSON: " + dataJson.toString());
			System.out.println("time: "+timestamp);
			Date date = Util.convertToDate(timestamp);

//			updatedatabase(splitBed[1], paramMap);
//			if(!BasicUtils.isEmpty(splitBed)){
//				String bedName  = splitBed[1];
//				if(bedName.equalsIgnoreCase("BED8025")){
//					System.out.println("****** HL7 Bed:BED8025 caught! ******");
//				}
//			}
			updateCassandra(date, dataJson.toString(), splitBed[1]);
			
			/*
			 * update cassandra here
			 */
			System.out.println("hl7 parser finished");

		} catch (HL7Exception | JSONException e) {
			e.printStackTrace();
		}
		

	}
	
	
	private void updateCassandra(Date date, String data, String bedId){		
		DeviceDataService deviceService = BasicConstants.applicatonContext.getBean(DeviceDataService.class);
		deviceService.saveInifinityDataCassandra(date,data,bedId);
	}
	
	
	@SuppressWarnings("unchecked")
	private void updatedatabase(String bedId, HashMap<String, Float> paramMap) {
		try {
			if(HL7Constants.oldTime == 0){
				HL7Constants.oldTime = System.currentTimeMillis();
			}
			String getUhid = "SELECT uhid FROM dashboard_finalview WHERE uhid!='null'";
			PatientDao patientDao = (PatientDao)BasicConstants.applicatonContext.getBean(PatientDao.class);
			List<String> uhidList = patientDao.getListFromNativeQuery(getUhid);

//			String query = "select uhid from "+BasicConstants.SCHEMA_NAME+".baby_detail where nicubedno = (Select inicu_bedid from "+BasicConstants.SCHEMA_NAME+".hl7_device_mapping where hl7_bedid='"
//					+ bedId + "')";
			List<String> uhid = getUhidFromBed(bedId);//patientDao.getListFromNativeQuery(query);
			if(!BasicUtils.isEmpty(uhid)){
				for(String uid: uhidList){

					
					if(uid.equalsIgnoreCase(uhid.get(0))){
						
						if((HL7Constants.oldTime + 60000) > System.currentTimeMillis()  ){
							
							Util.updateMeanMap(uhid.get(0), paramMap);
							
						}else{
							
							//calculate mean
							
							
							HashMap< String, Float> meanValueMap = Util.calculateMean(uhid.get(0));
							//push meanvaluemap into db
							if(!BasicUtils.isEmpty(meanValueMap)){
								 DeviceMonitorDetail dev = new DeviceMonitorDetail();
								 if(!BasicUtils.isEmpty(meanValueMap.get("HR"))){
									 dev.setHeartrate(String.valueOf(meanValueMap.get("HR")));
								 }
								 
//								 if(!BasicUtils.isEmpty(meanValueMap.get(""))){
//									 dev.setBeddeviceid(String.valueOf(meanValueMap.get("")));
//								 }
								 
//								 if(!BasicUtils.isEmpty(meanValueMap.get(""))){
//									 dev.setCo2Resprate(String.valueOf(meanValueMap.get("")));
//								 }
								 
								 if(!BasicUtils.isEmpty(meanValueMap.get("ART D"))){
									 dev.setDiaBp(String.valueOf(meanValueMap.get("ART D")));
								 }
								 
//								 if(!BasicUtils.isEmpty(meanValueMap.get(""))){
//									 dev.setEcgResprate(String.valueOf(meanValueMap.get("")));
//								 }
								 
//								 if(!BasicUtils.isEmpty(meanValueMap.get(""))){
//									 dev.setEtco2(String.valueOf(meanValueMap.get("")));
//								 }
								 
								 if(!BasicUtils.isEmpty(meanValueMap.get("ART M"))){
									 dev.setMeanBp(String.valueOf(meanValueMap.get("ART M")));
								 }
								 
								 if(!BasicUtils.isEmpty(meanValueMap.get("PLS"))){
									 dev.setPulserate(String.valueOf(meanValueMap.get("PLS")));
								 }
								 
								 if(!BasicUtils.isEmpty(meanValueMap.get("SpO2"))){
									 dev.setSpo2(String.valueOf(meanValueMap.get("SpO2")));
								 }
								 
								 if(!BasicUtils.isEmpty(meanValueMap.get("ART S"))){
									 dev.setSysBp(String.valueOf(meanValueMap.get("ART S")));
								 }
								 
								 if(!BasicUtils.isEmpty(uid)){
									 dev.setUhid(uid);
								 }
								 
								 patientDao.saveObject(dev);
							}
							HL7Constants.oldTime = System.currentTimeMillis();
								
						}
						
					}
					
				}				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	private List<String> getUhidFromBed(String bedId){
		
		PatientDao patientDao = (PatientDao)BasicConstants.applicatonContext.getBean(PatientDao.class);
		String query = "select uhid from "+BasicConstants.SCHEMA_NAME+".baby_detail where nicubedno = (Select inicu_bedid from "+BasicConstants.SCHEMA_NAME+".hl7_device_mapping where hl7_bedid='"
				+ bedId + "')";
		List<String> uhid_ = patientDao.getListFromNativeQuery(query);
		
		return uhid_;
		
		
	}
	
	private int countSubstring(String main, String sub) {

		int count = 0;
		String str = main;
		String findStr = sub;
		int lastIndex = 0;

		while (lastIndex != -1) {

			lastIndex = str.indexOf(findStr, lastIndex);

			if (lastIndex != -1) {
				count++;
				lastIndex += findStr.length();
			}
		}

		return count;

	}

//	public static void main(String args[]) {
//
//		String msg = "MSH|^~\\&|Infinity||NUR||201703011906||ORU^R01|17030119062613320632|P|2.3\r"
//				+ "PID|||000000^^^^AN||UNKNOWN|||||||||||||000000\r" + "PV1|||PCICU^^BED3034\r"
//				+ "OBR|||||||20170301190249\r"
//				+ "OBX||SN|HR^^local^8867-4&1^^LOINC||=^119|/min^^ISO+|||||R|||20170301190249\r"
//				+ "OBX||ST|%PACED^^local^&1^^MIB/CEN||   |%^^ISO+|||||R|||20170301190249\r"
//				+ "OBX||ST|ARR^^local^03266&1^^MIB/CEN||   |/min^^ISO+|||||R|||20170301190249\r"
//				+ "OBX||ST|PVC/min^^local^&1^^MIB/CEN||   |/min^^ISO+|||||R|||20170301190249\r"
//				+ "OBX||ST|STdV2^^local^10125-3&2^^LOINC||   |mm^^ISO+|||||R|||20170301190249\r"
//				+ "OBX||ST|STdV5^^local^10128-7&2^^LOINC||   |mm^^ISO+|||||R|||20170301190249\r"
//				+ "OBX||ST|PPR^^local^8893-0&1^^LOINC||   |mm(hg)^^ISO+|||||R|||20170301190249\r"
//				+ "OBX||SN|ART D^^local^8462-4&1^^LOINC||=^56|mm(hg)^^ISO+|||||R|||20170301190249\r"
//				+ "OBX||SN|ART S^^local^8480-6&1^^LOINC||=^125|mm(hg)^^ISO+|||||R|||20170301190249\r"
//				+ "OBX||SN|ART M^^local^8478-0&1^^LOINC||=^87|mm(hg)^^ISO+|||||R|||20170301190249\r"
//				+ "OBX||ST|CVP^^local^8591-0&1^^LOINC||   |mm(hg)^^ISO+|||||R|||20170301190249\r"
//				+ "OBX||SN|PLS^^local^8889-8&1^^LOINC||=^123|/min^^ISO+|||||R|||20170301190249\r"
//				+ "OBX||SN|SpO2^^local^2710-2&1^^LOINC||=^100|%^^ISO+|||||R|||20170301190249\r"
//				+ "OBX||SN|Ta^^local^8332-9&4^^LOINC||=^22.8|cel^^ISO+|||||R|||20170301190249\r"
//				+ "OBX||ST|Tb^^local^8310-5&4^^LOINC||   |cel^^ISO+|||||R|||20170301190249\r"
//				+ "OBX||ST|dT^^local^57368&4^^MIB/CEN||   |cel^^ISO+|||||R|||20170301190249\r"
//				+ "OBX||ST|NBP D^^local^8496-2&1^^LOINC||   |mm(hg)^^ISO+|||||R|||20170301174512\r"
//				+ "OBX||ST|NBP S^^local^8508-4&1^^LOINC||   |mm(hg)^^ISO+|||||R|||20170301174512\r"
//				+ "OBX||ST|NBP M^^local^8502-7&1^^LOINC||   |mm(hg)^^ISO+|||||R|||20170301174512\r";
//
//		new HL7Parser().parse(msg);
//	}

}
