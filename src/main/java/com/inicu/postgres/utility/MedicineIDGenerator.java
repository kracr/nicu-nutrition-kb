package com.inicu.postgres.utility;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.mapping.Property;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

public class MedicineIDGenerator implements IdentifierGenerator, Configurable {

	private String prefix;
	private String tableName;
	private String name;
	private String columnName;
 	
	public MedicineIDGenerator() {
		super();
	}



	@Override
	public Serializable generate(SessionImplementor session, Object object)
			throws HibernateException {
		
		Connection connection = session.connection();

		try{

			Statement statement=connection.createStatement();
			ResultSet rs=statement.executeQuery("select max("+columnName+") as Id from "+BasicConstants.SCHEMA_NAME+"."+tableName);
			if(rs.next())
			{
				String generatedId = prefix+"0000000001"; // default generated id
				String medidStr = rs.getString(1);
				
				if(!BasicUtils.isEmpty(medidStr)){
					String[] numArr = medidStr.split(prefix);
					int num = Integer.parseInt(numArr[1]);
					num = num+1;
					generatedId = prefix+String.format("%010d",num);
					System.out.println("Generated Id: " + generatedId);					
				}
				return generatedId;
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
   public void setPrefix(String name)
   {
	   this.name=name;
	   String a[]=this.name.split("-");
	   
	   this.prefix=a[0];
	   this.tableName=a[1];
	   this.columnName=a[2];
	 
   }
   public void setTableName(String tableName)
   {
	   this.tableName=tableName;
   }
	

	@Override
	public void configure(Type type, Properties params, Dialect d) throws MappingException {
		// TODO Auto-generated method stub
		setPrefix(params.getProperty("name"));
	//	setTableName(params.getProperty("tableName"));
		
	}

}
