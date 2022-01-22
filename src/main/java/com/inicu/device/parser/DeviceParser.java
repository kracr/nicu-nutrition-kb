package com.inicu.device.parser;

import java.util.HashMap;
import java.util.List;

public interface DeviceParser {

	void parse();
	
	HashMap<String, Float> parameterMapper(HashMap<String, Float> mean);
	
	HashMap<String, Float> calculateMean(List<String> listdata);
	
	void pushDataToPostgress(HashMap<String,Float> mappedMean, String uhid,String beddeviceid, Long deviceTime);
}
