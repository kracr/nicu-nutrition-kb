package com.inicu.postgres.daoImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inicu.postgres.dao.LogDAO;
import com.inicu.postgres.entities.Logs;
import com.inicu.postgres.utility.InicuPostgresUtililty;

@Repository
public class LogDAOImpl implements LogDAO{

	@Autowired
	InicuPostgresUtililty inicuPostgresUtililty;

	@Override
	public void saveObject(Logs logs) throws Exception{
		inicuPostgresUtililty.saveObject(logs);
	}
	
	
}
