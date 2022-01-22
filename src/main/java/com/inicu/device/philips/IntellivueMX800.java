package com.inicu.device.philips;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.inicu.cassandra.service.DeviceDataService;
import com.inicu.device.parser.DeviceParser;
import com.inicu.device.utility.DeviceConstants;
import com.inicu.device.utility.DeviceUtil;
import com.inicu.postgres.utility.BasicConstants;
import com.inicu.postgres.utility.BasicUtils;

public class IntellivueMX800 implements DeviceParser{

	private List<String> listdata;
	private String uhid;
	private String beddeviceid;
	private List<String> serverTimeList;


	public IntellivueMX800(List<String> listdata,List<String> serverTimeList,String uhid,String beddeviceid) {
		super();
		this.listdata = listdata;
		this.serverTimeList = serverTimeList;
		this.uhid = uhid;
		this.beddeviceid = beddeviceid;
	}


	@Override
	public void parse() {		
		if(!BasicUtils.isEmpty(listdata) && !BasicUtils.isEmpty(serverTimeList)){			
			//iterate over the listdata to calculate mean values.			
			for(int i=0;i<listdata.size();i++) {
				String obj = listdata.get(i); 
				List<String> finalList = new ArrayList<>();
				finalList.add(obj);				
				HashMap<String,Float> mean = calculateMean(finalList);				
				if(!BasicUtils.isEmpty(mean)){					
					HashMap<String,Float> mappedMean = parameterMapper(mean);					
					if(!BasicUtils.isEmpty(mappedMean)){						
						// push the values to the database.			
						try {
							SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
							Date serverTimeInDate = dateFormat.parse(serverTimeList.get(i));
							System.out.println("Server Time For Postgres In Date = " + serverTimeInDate);
							Long serverTime = serverTimeInDate.getTime();
							System.out.println("Server Time For Postgres In Long = " + serverTime);
							pushDataToPostgress(mappedMean,uhid,beddeviceid,serverTime);
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}					
					}
				}
			}
		}
	}
	
	@Override
	public HashMap<String, Float> parameterMapper(HashMap<String, Float> mean) {
		//convert the data to generic map 
		//philips parameter are converted into generic parameter
		// generic parameters are then pushed into the database.

		HashMap<String,Float> mappedMean = new HashMap<>();
		if(!BasicUtils.isEmpty(mean)){
			for(String key:mean.keySet()){
				if(DeviceConstants.philipsIntellivueMx800ParamMapper.containsKey(key)){
					mappedMean.put(DeviceConstants.philipsIntellivueMx800ParamMapper.get(key), mean.get(key));					
				}
			}
		}
		return mappedMean;
	}

	@Override
	public HashMap<String, Float> calculateMean(List<String> listdata) {
		DeviceUtil util = new DeviceUtil();
		return util.calculationMean(listdata);
	}

	@Override
	public void pushDataToPostgress(HashMap<String, Float> mappedMean,
			String uhid, String beddeviceid, Long deviceTime) {
		DeviceDataService deviceService = BasicConstants.applicatonContext.getBean(DeviceDataService.class);
		if(!BasicUtils.isEmpty(deviceService)){
			deviceService.saveDeviceMonitorDetail(mappedMean,uhid,beddeviceid,deviceTime);
			deviceService.saveApneaEventDetail(mappedMean,uhid,deviceTime);
		}
	}

}
