package com.inicu.postgres.utility;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import com.inicu.his.data.acquisition.HISConstants;
import com.inicu.his.data.acquisition.HISReceiver;
import com.inicu.his.data.acquisition.LabReceiver;
import com.inicu.postgres.utility.BasicConstants;
import java.sql.BatchUpdateException;
import java.sql.Connection;

/*
 *  * Class for connecting to postgres remote database
 * For fetching patient, lab data details
 */
public class RemotedbConnector {
	
//	private Connection connection = null;
//	private final String url = "jdbc:postgresql://";
//	private final String serverName = "119.81.121.189";
//	private final String portNumber = "5432";
//	private final String databaseName = "inicudb";
//	private final String userName = "postgres";
//	private final String password = "postgres";
//	private final String schemaName = "kdah";

	private Connection connection = null;
	private final String url = "jdbc:postgresql://";
	private final String serverName = BasicConstants.REMOTE_IP_ADDRESS;
	private final String portNumber = BasicConstants.REMOTE_PORT;
	private final String databaseName = BasicConstants.REMOTE_DATABASE_NAME;
	private final String userName = BasicConstants.REMOTE_USER;
	private final String password = BasicConstants.REMOTE_PASSWORD;
	private final String schemaName = BasicConstants.REMOTE_SCHEMA;
	
	private final String serverNamePostgres = BasicConstants.REMOTE_IP_ADDRESS_POSTGRES;
	private final String portNumberPostgres = BasicConstants.REMOTE_PORT_POSTGRES;
	private final String databaseNamePostgres = BasicConstants.REMOTE_DATABASE_NAME_POSTGRES;
	private final String userNamePostgres = BasicConstants.REMOTE_USER_POSTGRES;
	private final String passwordPostgres = BasicConstants.REMOTE_PASSWORD_POSTGRES;
	private final String schemaNamePostgres = BasicConstants.REMOTE_SCHEMA_POSTGRES;

	public RemotedbConnector(Boolean isLab) throws Exception {
	 	System.out.println("Connecting to "+serverName+schemaName);
//		String connectionUrl = getConnectionUrl();
	 	
//		this.connection = makeConnection();  //making connection based on database
	 	if(BasicConstants.SCHEMA_NAME.equalsIgnoreCase("apollo")){
	 	
			if (isLab) {
				this.connection = getMySqlserverConnection();
			}
	
			if (!isLab) {
				this.connection = getPostgresConnection();
			}
			
	 	}else if(BasicConstants.SCHEMA_NAME.equalsIgnoreCase("rainbow")){
	 		this.connection = getSqlserverConnection();
	 	}
	 	else if(BasicConstants.SCHEMA_NAME.equalsIgnoreCase("kalawati")){
	 		this.connection = getMySqlserverConnection();
	 	}
	 	else if(BasicConstants.SCHEMA_NAME.equalsIgnoreCase("gangaram")){
	 		this.connection = getODBCServerConnection();
	 	} /*else if(BasicConstants.SCHEMA_NAME.equalsIgnoreCase("kdah")){       // To connect for localhost
			this.connection = getPostgresConnection();
		}*/
		//executePostgresTestQuery(null);
		
		
	}
	
	public Connection getODBCServerConnection() throws Exception{
		try {
//			final String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
//
//			Class.forName(driver);

			//connection = DriverManager.getConnection(url,user,password);
			
			
			Class.forName("com.intersys.jdbc.CacheDriver");
			System.out.println("ppp");
			String connectionURL = "jdbc:Cache://"+serverName+":"+portNumber+"/"+databaseName;
			System.out.println(connectionURL);
			this.connection = DriverManager.getConnection(connectionURL,userName,password);
			System.out.println(this.connection);
			if (connection != null) {
				System.out.println("Connection to SqlServer "+serverName+" successful");
				String query = "select ARCIM_Code code, ARCIM_Desc Name,   ARCIM_PHCDF_DR->PHCDF_PHCD_ParRef->PHCD_Generic_DR->PHCGE_Name Generic, ARCIM_PHCDF_DR->PHCDF_CTUOM_DR->CTUOM_Desc UOM,  ARCIM_PHCDF_DR->PHCDF_PHCFR_DR->PHCFR_Desc1 Frequency , ARCIM_PHCDF_DR->PHCDF_PHCS_DR->PHCS_Desc Strength, ARCIM_PHCDF_DR->PHCDF_Route_DR->ROUTE_Desc Route,\n" + 
						"\n" + 
						"ARCIM_PHCDF_DR->PHCDF_PHCDU_DR->PHCDU_Desc1 Duration from arc_itmmast where ARCIM_EffDateTo IS NULL AND ARCIM_BillSub_DR->ARCSG_ARCBG_ParRef->ARCBG_Code IN('MED','MEDCON')";
				ResultSet rs = null;
				try {
					Statement stmt = this.connection.createStatement();
					System.out.println("Executing query "+query);
					rs = stmt.executeQuery(query);
					while(rs.next()) {
//					System.out.println("Patients count received from remote server " + rs.getString("code"));
//					System.out.println("Patients count received from remote server " + rs.getString("Name"));
//					System.out.println("Patients count received from remote server " + rs.getString("Generic"));
//					System.out.println("Patients count received from remote server " + rs.getString("UOM"));
//					System.out.println("Patients count received from remote server " + rs.getString("Frequency"));
//					System.out.println("Patients count received from remote server " + rs.getString("Strength"));
//					System.out.println("Patients count received from remote server " + rs.getString("Route"));
//					System.out.println("Patients count received from remote server " + rs.getString("Duration"));

					}

					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error connecting to SqlServer : " + e.getMessage());
		}
		return connection;
		
	}
	
	public Connection getPostgresConnection() throws Exception{
		try {
			
			Class.forName("org.postgresql.Driver");
			String connectionURL = "jdbc:postgresql://";
			connectionURL += ( serverNamePostgres + ":" + portNumberPostgres + "/"+ databaseNamePostgres +"?"+"currentSchema="+schemaNamePostgres);
			this.connection = DriverManager.getConnection(connectionURL, userNamePostgres, passwordPostgres);
			
			if (connection != null) {
				System.out.println("Connection to PostgresDB "+serverNamePostgres+" successful");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error connecting to PostgresDB : " + e.getMessage());
		}
		return connection;
		
	}
	
	public Connection getSqlserverConnection() throws Exception{
		try {
			
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String connectionURL = "jdbc:sqlserver://";
			connectionURL += ( serverName  + ":" + portNumber + ";"+"databaseName="+databaseName+";");
			this.connection = DriverManager.getConnection(connectionURL, userName, password);
			if (connection != null) {
				System.out.println("Connection to SqlServer "+serverName+" successful");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error connecting to SqlServer : " + e.getMessage());
		}
		return connection;
		
	}
	
	public Connection getMySqlserverConnection() throws Exception{
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			String connectionURL = "jdbc:mysql://";
			connectionURL += ( serverName  + ":" + portNumber + "/"+databaseName + "?user=" + userName + "&password=" + password);
			System.out.println(connectionURL);
			this.connection = DriverManager.getConnection(connectionURL);
			System.out.println(this.connection);
			if (connection != null) {
				System.out.println("Connection to SqlServer "+serverName+" successful");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error connecting to SqlServer : " + e.getMessage());
		}
		return connection;
		
	}
	
	
	public void executeTestQuery(String args[])
	{
		// change this query to appropriate table
		
		String query = "select count(*) from "+BasicConstants.REMOTE_PATIENTVIEW_NAME;
		ResultSet rs = null;
		try {
			Statement stmt = this.connection.createStatement();
			System.out.println("Executing query "+query);
			rs = stmt.executeQuery(query);
			while(rs.next())
			System.out.println("Patients count received from remote server " + rs.getInt(1));
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private String getConnectionUrl() {
		System.out.println("Connection URL "+url + serverName + ":" + portNumber + "/"+databaseName+"?"+"currentSchema="+schemaName);
		return url + serverName + ":" + portNumber + "/"+databaseName+"?"+"currentSchema="+schemaName;
	}

	private Connection makeConnection() {
		try {
//			LOGGER.log(Level.INFO, msg, thrown);
			
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(getConnectionUrl(), userName, password);
			if (connection != null) {
				System.out.println("Connection to PostgresDB "+serverName+"successful");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error connecting to PostgresDB : " + e.getMessage());
		}
		return connection;
	}
	public Connection getConnection(){
		return this.connection;
	}
	
	public void closeConnection() {
		try {						
			if (connection != null && (!connection.isClosed())) {
				connection.close();
			}
			connection = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void executePostgresTestQuery(String args[])
	{
		// change this query to appropriate table
//		String query = "select count(*) from patient_details";
		
		String query = "select count(*) from "+BasicConstants.REMOTE_PATIENTVIEW_NAME;
		ResultSet rs = null;
		try {
			Statement stmt = this.connection.createStatement();
			System.out.println("Executing query "+query);
			rs = stmt.executeQuery(query);
			while(rs.next())
			System.out.println("Patients count received from remote server " + rs.getInt(1));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public ResultSet executePostgresSelectQuery(String query) throws SQLException
	{
		ResultSet rs = null;
		if(this.connection==null){
			System.out.println("Connection is null, creating new connection ");
			this.connection = this.getConnection();
		}
			
		try {
			Statement stmt = this.connection.createStatement();
			System.out.println("Executing query "+query);
			rs = stmt.executeQuery(query);
			return rs;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
		
	}

	

}
