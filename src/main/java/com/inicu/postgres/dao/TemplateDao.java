package com.inicu.postgres.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

//import com.oxyent.inicu.models.AddTemplate;

@Repository
public interface TemplateDao {
	List executeObjectQuery(String query) throws Exception;

	Object saveObject(Object notetemplate)throws Exception;
	void  delObject(String query)throws Exception;
	
}
