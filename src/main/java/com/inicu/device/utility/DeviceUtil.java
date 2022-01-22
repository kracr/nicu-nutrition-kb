package com.inicu.device.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import com.inicu.postgres.utility.BasicConstants;
import com.inicu.postgres.utility.BasicUtils;

public class DeviceUtil {

	public HashMap<String,Float> calculationMean(List<String> listdata){
		HashMap<String,List<Float>> meanMap = null;
		HashMap<String,Float> mean = null;
		try{
			if(!BasicUtils.isEmpty(listdata)){
				meanMap = new HashMap<>();
				mean = new HashMap<>();
				for(String s:listdata){
					JSONObject obj = new JSONObject(s);
					Iterator<?> keys = obj.keys();
					while( keys.hasNext() ) {
						String key = (String)keys.next();

						if(meanMap.containsKey(key)){
							if(!obj.get(key).equals("")){
								List<Float> listfloat = meanMap.get(key);
								if(obj.get(key).toString().matches(BasicConstants.REG_EX_NUMBER_PATTERN)){
									listfloat.add(Float.parseFloat(obj.get(key).toString()));
									meanMap.put(key, listfloat);					    											
								}
							}
						}else{
							if(!(obj.get(key).equals(""))){
								List<Float> listfloat = new ArrayList<Float>();
								if(obj.get(key).toString().trim().matches(BasicConstants.REG_EX_NUMBER_PATTERN)){
									listfloat.add(Float.parseFloat(obj.get(key).toString()));
									meanMap.put(key, listfloat);					    											
								}
							}
						}					    	
					}
				}

				if(!BasicUtils.isEmpty(meanMap)){
					// iterate over the mean map and calculate mean.
					for(String key:meanMap.keySet()){
						List<Float> paramVal = meanMap.get(key);
						Float val = Float.parseFloat("0");
						for(Float f:paramVal){
							val = val+f;
						}
						if(val!=0.0){
							val = val/paramVal.size();
							val = (float) val;
							mean.put(key, val);							
						}
					}
				}
			}	
		}catch(Exception e){
			e.printStackTrace();
		}

		return mean;
	}

	/**
	 * can be used for future implementation 
	 * where max is required instead of mean.
	 * @return
	 */
	public HashMap<String,Float> calculateMax(){
		return null;
	}

	/**
	 * can be used for future implementation
	 * where min is required instead of mean.
	 */
	public HashMap<String,Float> calculateMin(){
		return null;
	}
}
