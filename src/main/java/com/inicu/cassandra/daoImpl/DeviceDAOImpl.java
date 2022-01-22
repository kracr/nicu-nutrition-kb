package com.inicu.cassandra.daoImpl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inicu.cassandra.dao.DeviceDAO;
import com.inicu.cassandra.entities.DeviceDataCarescapeACK;
import com.inicu.cassandra.entities.DeviceDataInfinity;
import com.inicu.cassandra.entities.PatientDeviceDataInicu;
import com.inicu.cassandra.entities.PatientMonitorData;
import com.inicu.cassandra.models.MonitorJSON;
import com.inicu.cassandra.utility.InicuCassandraTemplate;
import com.inicu.cassandra.utility.MappingConstants;
import com.inicu.models.PatientDataJsonObject;
import com.inicu.postgres.entities.CameraFeed;
import com.inicu.postgres.entities.PatientData;
import com.inicu.postgres.entities.VentilatorData;
import com.inicu.postgres.utility.BasicUtils;
import com.inicu.postgres.utility.InicuPostgresUtililty;

/**
 * DAOImpl class to perform CRUD operation
 * @author Dipin
 * @version 1.0
 */

@Repository
public class DeviceDAOImpl implements DeviceDAO{

	@Autowired
	public InicuCassandraTemplate cassandraTemplate;
	
	@Autowired
	InicuPostgresUtililty inicuPostgresUtililty;

	@Override
	public void startService(PatientData pd) {
		if(pd!=null){
			cassandraTemplate.create(pd);
		}
	}

	@Override
	public List<PatientDataJsonObject> getData(String id) {
		List<PatientDataJsonObject> listOfPObj = null;
		Timestamp timeStamp = new Timestamp(new Date().getTime());
		if(timeStamp!=null){
			//			String cQuery = "select * from inicu.patientdata where patient_id = '"+id.trim()+"'"
			//					+ " AND event_time >= '"+timeStamp+"'  and event_time <= '"+timeStamp+"'";   
			PatientDataJsonObject pObj = new PatientDataJsonObject();
			String cQuery = "select * from inicu.patientdata where patient_id = '"+id.trim()+"'"
					+ " AND event_time >= '2017-01-18 12:34:00'  and event_time <= '2017-01-18 12:34:56'";
			List<PatientData> resultSet = cassandraTemplate.select(cQuery, PatientData.class);
			if(resultSet!=null&&resultSet.size()>0){
				listOfPObj = new ArrayList<>();
				for(PatientData pd : resultSet){

					String metricId = pd.getMetricId();
					if(metricId!=null&&!metricId.isEmpty()){
						String parameter = MappingConstants.metricMap.get(metricId);
						if(parameter!=null&&!parameter.isEmpty()){
							metricId = parameter;
						}
						pObj.setParameter(metricId);
						//						System.out.println(pObj.getParameter());
					}
					String value = pd.getValue();
					if(value!=null&&!value.isEmpty())
					{
						pObj.setValue(value);
					}
					java.util.Iterator<PatientDataJsonObject> itr = listOfPObj.iterator();
					while(itr.hasNext())
					{
						if(pObj.getParameter().equals(itr.next().getParameter()))
						{



							if(value!=null&&!value.isEmpty()){
								pObj.setValue(pObj.getValue() + itr.next().getValue());
								break;
							}

						}
					}
					listOfPObj.add(pObj);
				}
			}
		}
		return listOfPObj;
	}


	@Override
	public MonitorJSON getECGData(String id) {
		PatientData pData= new PatientData();
		MonitorJSON ecg =new MonitorJSON();
		//		System.out.println( new Date());

		//Timestamp timeStamp = new Timestamp((new Date().getTime()));
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		String date = dateFormat.format(new Date().getTime()-1000);
		String dateAfter= dateFormat.format(new Date().getTime());
		//		System.out.println("Date: **** "+date);
		//		System.out.println(dateAfter);
		if(date!=null){
			PatientDataJsonObject pObj = new PatientDataJsonObject();
			String cQuery = "select * from inicu.patientdata where patient_id = '"+ id.trim() +"'"
					+" AND event_time > '"  + date  + "' and event_time < '" + dateAfter + "'"; 
			List<PatientData> resultSet = cassandraTemplate.select(cQuery, PatientData.class);
			//			System.out.println(resultSet.size());
			java.util.Iterator itr = resultSet.iterator();
			if(resultSet!=null&&resultSet.size()>0)
			{
				while(itr.hasNext())
				{
					pData =  (PatientData) itr.next();
					if(pData.getMetricId().equals("MDC_ECG_LEAD_II"))
					{

						String values = pData.getValue();
						if(!BasicUtils.isEmpty(values)){
							List<String> truncatedValues = new ArrayList<>();
							List<String> listValues = new ArrayList<String>(Arrays.asList(values.split(",")));
							for(int i=0;i<listValues.size();i++){
								if(i%2==0){
									truncatedValues.add(listValues.get(i));
								}
							}

							ecg.setValue(String.join(",",truncatedValues));
						}
						//						System.out.println(pData.getValue());
						//						ecg.setValue(pData.getValue());
						//						System.out.println("time event -----"+pData.getEventTime().toString());
						ecg.setTime(pData.getEventTime().toString());
					}
				}
			}
		}
		return ecg;
	}

	@Override
	public MonitorJSON getBPData(String id) {
		PatientData pData= new PatientData();
		MonitorJSON bp =new MonitorJSON();
		//		System.out.println( new Date());

		//Timestamp timeStamp = new Timestamp((new Date().getTime()));
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		String date = dateFormat.format(new Date().getTime()-1000);
		String dateAfter= dateFormat.format(new Date().getTime());
		//		System.out.println("Date: **** "+date);
		//		System.out.println(dateAfter);
		if(date!=null){
			PatientDataJsonObject pObj = new PatientDataJsonObject();
			String cQuery = "select * from inicu.patientdata where patient_id = '"+ id.trim() +"'"
					+" AND event_time > '"  + date  + "' and event_time < '" + dateAfter + "'"; 
			List<PatientData> resultSet = cassandraTemplate.select(cQuery, PatientData.class);
			//			System.out.println(resultSet.size());
			java.util.Iterator itr = resultSet.iterator();
			if(resultSet!=null&&resultSet.size()>0)
			{
				while(itr.hasNext())
				{
					pData =  (PatientData) itr.next();
					if(pData.getMetricId().equals("MDC_PRESS_BLD_ART_ABP"))
					{
						//						System.out.println(pData.getValue());
						String values = pData.getValue();
						if(!BasicUtils.isEmpty(values)){
							List<String> truncatedValues = new ArrayList<>();
							List<String> listValues = new ArrayList<String>(Arrays.asList(values.split(",")));
							for(int i=0;i<listValues.size();i++){
								if(i%2==0){
									truncatedValues.add(listValues.get(i));
								}
							}

							bp.setValue(String.join(",",truncatedValues));
						}

						bp.setTime(pData.getEventTime().toString());
					}
				}
			}
		}
		return bp;
	}
	@Override
	public MonitorJSON getPulseData(String id) {
		PatientData pData= new PatientData();
		MonitorJSON pulse =new MonitorJSON();
		//		System.out.println( new Date());

		//Timestamp timeStamp = new Timestamp((new Date().getTime()));
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		String date = dateFormat.format(new Date().getTime()-1000);
		String dateAfter= dateFormat.format(new Date().getTime());
		//		System.out.println("Date: **** "+date);
		//		System.out.println(dateAfter);
		if(date!=null){
			PatientDataJsonObject pObj = new PatientDataJsonObject();
			String cQuery = "select * from inicu.patientdata where patient_id = '"+ id.trim() +"'"
					+" AND event_time > '"  + date  + "' and event_time < '" + dateAfter + "'"; 
			List<PatientData> resultSet = cassandraTemplate.select(cQuery, PatientData.class);
			//			System.out.println(resultSet.size());
			java.util.Iterator itr = resultSet.iterator();
			if(resultSet!=null&&resultSet.size()>0)
			{
				while(itr.hasNext())
				{
					pData =  (PatientData) itr.next();
					if(pData.getMetricId().equals("MDC_PULS_OXIM_PLETH"))
					{
						//						System.out.println(pData.getValue());
						String values = pData.getValue();
						if(!BasicUtils.isEmpty(values)){
							List<String> truncatedValues = new ArrayList<>();
							List<String> listValues = new ArrayList<String>(Arrays.asList(values.split(",")));
							for(int i=0;i<listValues.size();i++){
								if(i%2==0){
									truncatedValues.add(listValues.get(i));
								}
							}

							pulse.setValue(String.join(",",truncatedValues));
						}
						//						System.out.println("time event -----"+pData.getEventTime().toString());
						pulse.setTime(pData.getEventTime().toString());
					}
				}
			}
		}
		return pulse;
	}

	@Override
	public List<PatientDataJsonObject> getVariableData(String id) {
		List<PatientDataJsonObject> listOfPObj = null;

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		String date = dateFormat.format(new Date().getTime()-1000);
		String dateAfter= dateFormat.format(new Date().getTime());
		//		System.out.println("Date: **** "+date);
		//		System.out.println(dateAfter);


		String cQuery = "select * from inicu.patientdata where patient_id = '"+ id.trim() +"'"
				+" AND event_time > '"  + date  + "' and event_time < '" + dateAfter + "'"; 
		List<PatientData> resultSet = cassandraTemplate.select(cQuery, PatientData.class);
		//		Set<PatientData> resultSet = Sets.newHashSet(result);
		//		System.out.println(resultSet.size());
		if(resultSet!=null&&resultSet.size()>0){
			listOfPObj = new ArrayList<>();
			Set<String> metricSet = new java.util.HashSet<>();
			for(PatientData pd : resultSet){
				PatientDataJsonObject pObj = new PatientDataJsonObject();
				String metricId = pd.getMetricId();

				if(!BasicUtils.isEmpty(metricId)&&containsNumericData(metricId,listOfPObj)){
					//compare with existing metric id's
					if(!metricSet.contains(metricId)){
						metricSet.add(metricId);						
					}else{
						continue;
					}
					String parameter = MappingConstants.metricMap.get(metricId);
					if(parameter!=null&&!parameter.isEmpty()){
						metricId = parameter;
					}
					pObj.setParameter(metricId);
					//					System.out.println(pObj.getParameter());

					String value = pd.getValue();
					if(value!= null && !value.isEmpty())
					{
						pObj.setValue(value);
					}	
					listOfPObj.add(pObj);					
				}
			}
		}

		//parse list and add empty parameters

		Set<String> allKey = MappingConstants.numericMetricMap.keySet();
		Set<String> allParam = new TreeSet<>();

		return listOfPObj;
	}

	private boolean containsNumericData(String metricId, List<PatientDataJsonObject> listOfPObj) {
		boolean isSuccessful = false;
		Set<String> listMetricId = MappingConstants.numericMetricMap.keySet(); // make this set as static, being called again and again

		if(listMetricId.contains(metricId)){
			isSuccessful = true;
		}else{
			isSuccessful = false;
		}
		return isSuccessful;
	}

	//accept parameter which data needs to be shown.
	@Override
	public MonitorJSON getDummyData(String param) {
		MonitorJSON json = null;
		while(BasicUtils.isEmpty(json)){
			json = getPatientDummyData(param);
		}
		return json;
	}

	public MonitorJSON getPatientDummyData(String param){
		return getPatientDummyData(param, null, null);
	}

	/**
	 * returns back dummy variable data by quering from cassandra.
	 */
	@Override
	public List<PatientDataJsonObject> getDummyVariableData() {
		return getDummyVariableData(null,null);
	}
	
	@Override
	  public void create(DeviceDataCarescapeACK dev) {
	    if(dev!=null){
	      cassandraTemplate.create(dev);
	      System.out.println("inside create method");
	    }
	 }

	@Override
	public List<PatientDataJsonObject> getDummyVentilatorData() {

		String cQuery = "SELECT device_time,data FROM inicu.device_data limit 1000";
		List<VentilatorData> resultSet = cassandraTemplate.select(cQuery, VentilatorData.class);
		if(!BasicUtils.isEmpty(resultSet)){
			// iterate ventilator data and save in a static field.
			for(VentilatorData v:resultSet){
				MappingConstants.patientVentilatorTempData.add(v);
			}
		}
		return null;
	}

	@Override
	public List<PatientDataJsonObject> getDummyVariableData(Integer randomMinute,
			Integer randomSecond) {

		List<PatientDataJsonObject> listOfPObj = null;

		Random r = new Random();
		int lowerBound = 01;
		int upperBound = 59;
		if(BasicUtils.isEmpty(randomMinute)||BasicUtils.isEmpty(randomSecond)){
			randomMinute = r.nextInt(upperBound-lowerBound) + lowerBound;
			randomSecond = r.nextInt(upperBound-lowerBound) + lowerBound;
		}
		String dateFrom = "2017-02-01 08:"+randomMinute+":"+randomSecond+"";
		String dateto = "2017-02-01 08:"+randomMinute+":"+(randomSecond+1)+"";


		String cQuery = "select * from inicu.patientdata where patient_id = '161203970'"
				+" AND event_time > '"  + dateFrom  + "' and event_time < '" + dateto + "'"; 
		List<PatientData> resultSet = cassandraTemplate.select(cQuery, PatientData.class);
		//		Set<PatientData> resultSet = Sets.newHashSet(result);
		//		System.out.println(resultSet.size());
		if(resultSet!=null&&resultSet.size()>0){
			listOfPObj = new ArrayList<>();
			Set<String> metricSet = new java.util.HashSet<>();
			HashMap<String,String> vitalValMap = new HashMap<>();
			for(PatientData pd : resultSet){
				PatientDataJsonObject pObj = new PatientDataJsonObject();
				String metricId = pd.getMetricId();

				if(!BasicUtils.isEmpty(metricId)&&containsNumericData(metricId,listOfPObj)){
					//compare with existing metric id's
					if(!metricSet.contains(metricId)){
						metricSet.add(metricId);						
					}else{
						continue;
					}
					String parameter = MappingConstants.metricMap.get(metricId);
					if(parameter!=null&&!parameter.isEmpty()){
						metricId = parameter;						
					}
					pObj.setParameter(metricId);
					String value = pd.getValue();
					if(metricId.equalsIgnoreCase("BP MEAN")){
						MappingConstants.staticBpMEAN = value;
					}else if(metricId.equalsIgnoreCase("SYS")){
						MappingConstants.staticBpSYS = value;
					}else if(metricId.equalsIgnoreCase("DIA")){
						MappingConstants.staticBpDIA = value;
					}
					vitalValMap.put(metricId, value);
				}
			}
			
			if(!vitalValMap.containsKey("BP MEAN")){
				vitalValMap.put("BP MEAN", MappingConstants.staticBpMEAN);
			}
			
			if(!vitalValMap.containsKey("SYS")){
				vitalValMap.put("SYS", MappingConstants.staticBpSYS);
			}
			
			if(!vitalValMap.containsKey("DIA")){
				vitalValMap.put("DIA", MappingConstants.staticBpDIA);
			}
			
			// iterate map to populate list of patient data json object
			Set<String> keySet = vitalValMap.keySet();
			if(!BasicUtils.isEmpty(keySet)){
				for(String key: keySet){
					PatientDataJsonObject pb = new PatientDataJsonObject();
					pb.setParameter(key);
					pb.setValue(vitalValMap.get(key));
					
					listOfPObj.add(pb);
				}
			}
		}		
		
		return listOfPObj;

	}

	@Override
	public MonitorJSON getPatientDummyData(String param, Integer randomMinute,
			Integer randomSecond) {

		PatientData pData= new PatientData();
		MonitorJSON json = null;
		Random r = new Random();
		int lowerBound = 01;
		int upperBound = 59;
		if(BasicUtils.isEmpty(randomMinute)||BasicUtils.isEmpty(randomSecond)){
			randomMinute = r.nextInt(upperBound-lowerBound) + lowerBound;
			randomSecond = r.nextInt(upperBound-lowerBound) + lowerBound;			
		}
		String dateFrom = "2017-02-01 08:"+randomMinute+":"+randomSecond+"";
		String dateto = "2017-02-01 08:"+randomMinute+":"+(randomSecond+1)+"";
		String cQuery = "select * from inicu.patientdata where patient_id = '161203970' "
				+ "AND event_time > '"+dateFrom+"' "
				+ "AND event_time < '"+dateto+"'";
		List<PatientData> resultSet = cassandraTemplate.select(cQuery, PatientData.class);
		java.util.Iterator itr = resultSet.iterator();
		if(resultSet!=null&&resultSet.size()>0)
		{
			while(itr.hasNext())
			{	
				pData =  (PatientData) itr.next();
				if(pData.getMetricId().equals(param))
				{

					json = new MonitorJSON();
					String values = pData.getValue();
					if(!BasicUtils.isEmpty(values)){
						List<String> truncatedValues = new ArrayList<>();
						List<String> listValues = new ArrayList<String>(Arrays.asList(values.split(",")));
						for(int i=0;i<listValues.size();i++){
							if(i%2==0){
								truncatedValues.add(listValues.get(i));
							}
						}

						json.setValue(String.join(",",truncatedValues));
					}
					json.setTime(pData.getEventTime().toString());
				}
			}
		}
		return json;
	}

	@Override
	public List executeNativeQuery(String query) {
		return inicuPostgresUtililty.executePsqlDirectQuery(query);
	}

	@Override
	public void createPatientMonitorData(PatientMonitorData pd) {
		if(pd!=null){
			cassandraTemplate.create(pd);
		}
	}

	@Override
	public void create(DeviceDataInfinity dev) {
		if(dev!=null){
			cassandraTemplate.create(dev);
		}
	}

	@Override
	public void inseriNICURecord(PatientDeviceDataInicu pd) {
		if(!BasicUtils.isEmpty(pd)){
			cassandraTemplate.create(pd);
		}
	}



}
