package com.inicu.device.radiometer;

import java.util.HashMap;
import java.util.List;

import com.inicu.device.parser.DeviceParser;

public class ABLBasic800 implements DeviceParser{

	private List<String> listdata;
	private String uhid;
	private String beddeviceid;
	private Long deviceTime;
	private List<String> serverTimeList;

	
	public ABLBasic800(List<String> listdata,List<String> serverTimeList,String uhid,String beddeviceid) {
		super();
		this.listdata = listdata;
		this.uhid = uhid;
		this.beddeviceid = beddeviceid;
		this.serverTimeList = serverTimeList;
	}
	
	
	@Override
	public void parse() {
		
	}

	@Override
	public HashMap<String, Float> parameterMapper(HashMap<String, Float> mean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<String, Float> calculateMean(List<String> listdata) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void pushDataToPostgress(HashMap<String, Float> mappedMean,
			String uhid, String beddeviceid, Long deviceTime) {
		// TODO Auto-generated method stub
		
	}

}
