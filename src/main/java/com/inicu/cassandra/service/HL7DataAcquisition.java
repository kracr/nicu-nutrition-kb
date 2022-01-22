package com.inicu.cassandra.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v22.datatype.PN;
import ca.uhn.hl7v2.model.v22.message.ADT_A01;
import ca.uhn.hl7v2.model.v22.segment.MSH;
import ca.uhn.hl7v2.parser.EncodingNotSupportedException;
import ca.uhn.hl7v2.parser.Parser;

import com.inicu.cassandra.dao.DeviceDAO;

@Repository
public class HL7DataAcquisition extends Thread{

	@Autowired
	DeviceDAO deviceDao;

	@Override
	public void run() {
		while(true){
			try{
				Thread.sleep(10000);	
				consumeHl7File();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	private void consumeHl7File() {
		// create a tcp ip connection.
		// read from specific file.
		
		
		 String msg = " MSH|^~\\&|Infinity||NUR||201703011906||ORU^R01|17030119062613320632|P|2.3"
		 		+ "PID|||000000^^^^AN||UNKNOWN|||||||||||||000000"
		 		+ "PV1|||PCICU^^BED3034"
		 		+ "OBR|||||||20170301190249"
		 		+ "OBX||SN|HR^^local^8867-4&1^^LOINC||=^119|/min^^ISO+|||||R|||20170301190249"
		 		+ "OBX||ST|%PACED^^local^&1^^MIB/CEN||   |%^^ISO+|||||R|||20170301190249"
		 		+ "OBX||ST|ARR^^local^03266&1^^MIB/CEN||   |/min^^ISO+|||||R|||20170301190249"
		 		+ "OBX||ST|PVC/min^^local^&1^^MIB/CEN||   |/min^^ISO+|||||R|||20170301190249"
		 		+ "OBX||ST|STdV2^^local^10125-3&2^^LOINC||   |mm^^ISO+|||||R|||20170301190249"
		 		+ "OBX||ST|STdV5^^local^10128-7&2^^LOINC||   |mm^^ISO+|||||R|||20170301190249"
		 		+ "OBX||ST|PPR^^local^8893-0&1^^LOINC||   |mm(hg)^^ISO+|||||R|||20170301190249"
		 		+ "OBX||SN|ART D^^local^8462-4&1^^LOINC||=^56|mm(hg)^^ISO+|||||R|||20170301190249"
		 		+ "OBX||SN|ART S^^local^8480-6&1^^LOINC||=^125|mm(hg)^^ISO+|||||R|||20170301190249"
		 		+ "OBX||SN|ART M^^local^8478-0&1^^LOINC||=^87|mm(hg)^^ISO+|||||R|||20170301190249"
		 		+ "OBX||ST|CVP^^local^8591-0&1^^LOINC||   |mm(hg)^^ISO+|||||R|||20170301190249"
		 		+ "OBX||SN|PLS^^local^8889-8&1^^LOINC||=^123|/min^^ISO+|||||R|||20170301190249"
		 		+ "OBX||SN|SpO2^^local^2710-2&1^^LOINC||=^100|%^^ISO+|||||R|||20170301190249"
		 		+ "OBX||SN|Ta^^local^8332-9&4^^LOINC||=^22.8|cel^^ISO+|||||R|||20170301190249"
		 		+ "OBX||ST|Tb^^local^8310-5&4^^LOINC||   |cel^^ISO+|||||R|||20170301190249"
		 		+ "OBX||ST|dT^^local^57368&4^^MIB/CEN||   |cel^^ISO+|||||R|||20170301190249"
		 		+ "OBX||ST|NBP D^^local^8496-2&1^^LOINC||   |mm(hg)^^ISO+|||||R|||20170301174512"
		 		+ "OBX||ST|NBP S^^local^8508-4&1^^LOINC||   |mm(hg)^^ISO+|||||R|||20170301174512"
		 		+ "OBX||ST|NBP M^^local^8502-7&1^^LOINC||   |mm(hg)^^ISO+|||||R|||20170301174512";
				   
				           /*
				            * The HapiContext holds all configuration and provides factory methods for obtaining
				            * all sorts of HAPI objects, e.g. parsers. 
				            */
				           HapiContext context = new DefaultHapiContext();
				           
				           /*
				            * A Parser is used to convert between string representations of messages and instances of
				            * HAPI's "Message" object. In this case, we are using a "GenericParser", which is able to
				            * handle both XML and ER7 (pipe & hat) encodings.
				            */
				           Parser p = context.getGenericParser();
				  
				           Message hapiMsg;
				           try {
				               // The parse method performs the actual parsing
				               hapiMsg = p.parse(msg);
				           } catch (EncodingNotSupportedException e) {
				               e.printStackTrace();
				               return;
				           } catch (HL7Exception e) {
				               e.printStackTrace();
				               return;
				           }
				   
				           /*
				            * This message was an ADT^A01 is an HL7 data type consisting of several components1, so we
				            * will cast it as such. The ADT_A01 class extends from Message, providing specialized
				            * accessors for ADT^A01's segments.
				            * 
				            * HAPI provides several versions of the ADT_A01 class, each in a different package (note
				            * the import statement above) corresponding to the HL7 version for the message.
				            */
				           ADT_A01 adtMsg = (ADT_A01)hapiMsg;
				   
				           MSH msh = adtMsg.getMSH();
				   
				           // Retrieve some data from the MSH segment
				           String msgType = msh.getMessageType().getMessageType().getValue();
				          String msgTrigger = msh.getMessageType().getTriggerEvent().getValue();
				  
				          // Prints "ADT A01"
				         System.out.println(msgType + " " + msgTrigger);
				  
				          /* 
				           * Now let's retrieve the patient's name from the parsed message. 
				           * 
				           * PN is an HL7 data type consisting of several components, such as 
				           * family name, given name, etc. 
				           */
				          PN patientName = adtMsg.getPID().getPatientName();
				  
				          // Prints "SMITH"
				          String familyName = patientName.getFamilyName().getValue();
				          System.out.println(familyName);
				 
	}
	
}
