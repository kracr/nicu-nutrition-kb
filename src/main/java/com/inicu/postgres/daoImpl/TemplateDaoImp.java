package com.inicu.postgres.daoImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inicu.postgres.dao.TemplateDao;
import com.inicu.postgres.utility.InicuPostgresUtililty;

@Repository
public class TemplateDaoImp implements TemplateDao{
	@Autowired
	InicuPostgresUtililty inicuPostgresUtililty;

	@Override
	public Object saveObject(Object notetemplate) throws Exception {
		// TODO Auto-generated method stub
		
		return inicuPostgresUtililty.saveObject(notetemplate);
	}

	@Override
	public void delObject(String query) throws Exception {
		// TODO Auto-generated method stub
		inicuPostgresUtililty.updateOrDelQuery(query);
		
	}
	
	@Override
	public List executeObjectQuery(String query)throws Exception {
		return inicuPostgresUtililty.executeMappedObjectCustomizedQuery(query);	
	}
	
	
}
