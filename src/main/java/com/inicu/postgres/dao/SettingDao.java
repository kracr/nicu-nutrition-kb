package com.inicu.postgres.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
@Repository
public interface SettingDao {
	public void executeInsertQuery(String query);
	public void saveObject(Object obj) throws Exception;
	public List getListFromMappedObjNativeQuery(String queryStr);
	public List getListFromNativeQuery(String queryStr);
	void  delObject(String query)throws Exception;
	public void delObjectNative(String query) throws Exception;
	public void updateNativeQuery(Object query)throws Exception;
}
