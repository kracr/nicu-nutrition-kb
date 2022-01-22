/**
 * 
 */
package com.inicu.hl7.data.acquisition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * @author sanoob
 * iNICU
 * 24-Feb-2017
 */

@Repository
public class HL7Constants {
	
	public static int HL7_SERVER_PORT = 8686;
	public static String SEG_OBX = "OBX";
	public static String TYPE_ST = "ST";
	public static String TYPE_SN = "SN";
	
	public static String PCICU_KEY = "PCICU";
	public static String SICU_KEY = "SICU";
	public static String PICU_KEY = "PICU";
	public static long oldTime = 0;
	
	public static HashMap<String, List<String>> bedMap = new HashMap<String, List<String>>();
	public static HashMap<String, String> paramMap = new HashMap<String, String>();
	public static HashMap<String, HashMap<String, List<Float>>> meanMap = new HashMap<String, HashMap<String, List<Float>>>();
	
	static{
		
		paramMap.put("HR", "heartrate");
		paramMap.put("PLS", "pulserate");
		paramMap.put("SpO2", "spo2");
		paramMap.put("ART D", "dia_bp");
		paramMap.put("ART S", "sys_bp");
		paramMap.put("ART M", "mean_bp");
		paramMap.put("STV", "tidalvol");
		
	}
	
//	public static List<String> bedList = new ArrayList<>();
//	static{
//		bedList.add("abc");
//		bedList.add("def");
//		bedMap.put("NICU", bedList);
//	}

}
