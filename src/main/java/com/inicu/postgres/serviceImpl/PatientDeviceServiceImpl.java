package com.inicu.postgres.serviceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inicu.postgres.dao.PatientDao;
import com.inicu.postgres.entities.DeviceDetail;
import com.inicu.postgres.service.PatientDeviceService;
import com.inicu.postgres.utility.BasicConstants;
import com.inicu.postgres.utility.BasicUtils;

@Repository
public class PatientDeviceServiceImpl implements PatientDeviceService {

	@Autowired
	PatientDao patientDao;
	/**
	 * returns unique domainId to configure with intellivue monitor
	 */
	@Override
	public Integer getUniqueDomainId() {
		Integer uniqDomId = null;
		List<String> setOfDomainIds = patientDao.getListFromNativeQuery("SELECT distinct(domainid) FROM device_detail WHERE domainid!='null'");
		List<String> maxIdStr = patientDao.getListFromNativeQuery("SELECT MAX(domainid) FROM device_detail");
		if(!BasicUtils.isEmpty(setOfDomainIds)){
			// create a set till the maximum id.
			Set<String> fullSet = new TreeSet<>();
			int maxId = Integer.parseInt(maxIdStr.get(0).toString());
			for(int i=1;i<=maxId;i++){
				fullSet.add(String.valueOf(i));
			}
			
			//compare fullset with subset;
			Set<String> subSet = new HashSet<>(setOfDomainIds);
			
			Set<String> resultSet = BasicUtils.setDifference(fullSet, subSet);
			if(!BasicUtils.isEmpty(resultSet)){
				uniqDomId = Integer.parseInt(resultSet.iterator().next());
			}else{
				uniqDomId = maxId+1;
			}
			
		}else{
			uniqDomId = 1;
		}
		return uniqDomId;
	}
	@Override
	public boolean checkifIpExists(String ipAddress) {
		boolean alreadyExist = false;
		String query = "SELECT * FROM device_detail WHERE ipaddress='"+ipAddress+"'";
		List resultSet = patientDao.getListFromNativeQuery(query);
		if(!BasicUtils.isEmpty(resultSet)&&resultSet.size()>0){
			alreadyExist = true;
		}else{
			alreadyExist = false;
		}
		return alreadyExist;
	}
	
}
