package com.inicu.postgres.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface NotesDAO {
	
	//  workspace
	
	public List getListFromMappedObjNativeQuery(String queryStr);

	List getListFromNativeQuery(String queryStr);

	void executeInsertQuery(String query);

	void saveObject(Object obj) throws Exception;

	void updateOrDeleteQuery(String query);

	void updateOrDeleteNativeQuery(String query);

	Object saveObjectReturn(Object obj) throws Exception;
	// end
	//---------------------------------------------------------------------------------
	//priya workspace
	
	
	
	
	//end

}
