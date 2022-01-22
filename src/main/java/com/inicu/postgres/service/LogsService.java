package com.inicu.postgres.service;

import org.springframework.stereotype.Repository;

@Repository
public interface LogsService {

	void saveLog(String desc, String action, String loggeduser, String uhid,
			String pageName)throws Exception;

	
}
