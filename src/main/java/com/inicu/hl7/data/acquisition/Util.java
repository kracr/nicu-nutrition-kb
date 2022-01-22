/**
 * 
 */
package com.inicu.hl7.data.acquisition;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.inicu.cassandra.serviceImpl.DeviceDataServiceImpl;

/**
 * @author sanoob iNICU 06-Mar-2017
 */
public class Util {

	public static String[] insertIntoHashMap(String bedId) {

		String splitString[] = bedId.split("\\^\\^");
		if (HL7Constants.bedMap.containsKey(splitString[0])) {

			List<String> bedList = HL7Constants.bedMap.get(splitString[0]);
			if (!bedList.contains(splitString[1])) {
				bedList.add(splitString[1]);
				HL7Constants.bedMap.put(splitString[0], bedList);
			}

		} else {

			List<String> newList = new ArrayList<String>();
			newList.add(splitString[1]);
			HL7Constants.bedMap.put(splitString[0], newList);

		}
		return splitString;
	}

	public static void updateMeanMap(String uhid, HashMap<String, Float> paramMap) {

		if (HL7Constants.meanMap.containsKey(uhid)) {

			HashMap<String, List<Float>> map = HL7Constants.meanMap.get(uhid);

			for (String param : paramMap.keySet()) {

				if (map.containsKey(param)) {

					List<Float> value = map.get(param);
					value.add(paramMap.get(param));

					map.remove(param);
					map.put(param, value);

				} else {
					List<Float> value = new ArrayList<>();
					value.add(paramMap.get(param));
					map.put(param, value);

				}

			}

		} else {

			HashMap<String, List<Float>> map = new HashMap<>();
			for (String param : paramMap.keySet()) {

				List<Float> value = new ArrayList<>();
				value.add(paramMap.get(param));
				map.put(param, value);

			}
			HL7Constants.meanMap.put(uhid, map);

		}

	}

	public static HashMap<String, Float> calculateMean(String uhid) {

		if (HL7Constants.meanMap.containsKey(uhid)) {

			HashMap<String, List<Float>> map = HL7Constants.meanMap.get(uhid);
			HashMap<String, Float> meanValueMap = DeviceDataServiceImpl.calculateMean("", map);
			HL7Constants.meanMap.remove(uhid); // remove entry after calculating
												// mean
			return meanValueMap;
		} else {

			return null;

		}

	}

	public static String cleanValue(String value) {

		String temp = value.replace("=^", "");
		temp.trim();
		return temp;

	}

	public static Float cleanAndConvert(String value) {

		Float retValue = Float.parseFloat(cleanValue(value));
		return retValue;
	}

	public static Date convertToDate(String date) {

		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
			Date parsedDate = dateFormat.parse(date);
			
			return parsedDate;
			
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

	}

}
