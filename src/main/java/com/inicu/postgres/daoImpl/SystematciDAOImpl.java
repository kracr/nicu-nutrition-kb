package com.inicu.postgres.daoImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inicu.postgres.dao.SystematicDAO;
import com.inicu.postgres.entities.SaJaundice;
import com.inicu.postgres.entities.SaMetabolic;
import com.inicu.postgres.entities.User;
import com.inicu.postgres.utility.InicuPostgresUtililty;

@Repository
public class SystematciDAOImpl implements SystematicDAO{
	
	@Autowired
	InicuPostgresUtililty inicuPostgresUtililty;
	
	@Override
	public List executeObjectQuery(String query)throws Exception {
		return inicuPostgresUtililty.executeMappedObjectCustomizedQuery(query);	
	}

	@Override
	public List executeNativeQuery(String query) throws Exception {
		return inicuPostgresUtililty.executePsqlDirectQuery(query);
	}

	@Override
	public Object saveObject(Object saMetabolic)throws Exception{
		// TODO Auto-generated method stub
		return inicuPostgresUtililty.saveObject(saMetabolic);
	}

	@Override
	public void insertnativeQuery(String query) {
		// TODO Auto-generated method stub
		 inicuPostgresUtililty.executeInsertQuery(query);
	}	
	
}
