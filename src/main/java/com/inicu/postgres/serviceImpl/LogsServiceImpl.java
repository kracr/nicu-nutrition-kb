package com.inicu.postgres.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inicu.postgres.dao.LogDAO;
import com.inicu.postgres.entities.Logs;
import com.inicu.postgres.service.LogsService;
import com.inicu.postgres.utility.InicuPostgresUtililty;

@Repository
public class LogsServiceImpl implements LogsService {
	
	@Autowired
	LogDAO logDao;
	
	@Override
	public void saveLog(String desc, String action, String loggeduser,
			String uhid, String pageName) throws Exception {
		
		Logs logs = new Logs();
		logs.setAction(action);
		logs.setDescription(desc);
		logs.setLoggeduser(loggeduser);
		logs.setPagename(pageName);
		logs.setUhid(uhid);
		logDao.saveObject(logs);
		
	}

	
	
}
