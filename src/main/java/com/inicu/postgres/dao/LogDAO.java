package com.inicu.postgres.dao;

import org.springframework.stereotype.Repository;

import com.inicu.postgres.entities.Logs;

@Repository
public interface LogDAO {

	void saveObject(Logs logs) throws Exception;

}
