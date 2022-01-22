package com.inicu.sqlserver.utility;


import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;



import com.inicu.postgres.utility.BasicConstants;

import java.sql.BatchUpdateException;
import java.sql.Connection;

/*
 *  * Class for connecting to postgres remote database
 * For fetching patient, lab data details
 */
public class SqlserverConnector {
	
//	private Connection connection = null;
//	private final String url = "jdbc:postgresql://";
//	private final String serverName = "119.81.121.189";
//	private final String portNumber = "5432";
//	private final String databaseName = "inicudb";
//	private final String userName = "postgres";
//	private final String password = "postgres";
//	private final String schemaName = "kdah";

	private Connection connection = null;
	private final String url = "jdbc:sqlserver://";
	private final String serverName = BasicConstants.REMOTE_IP_ADDRESS;
	private final String portNumber = BasicConstants.REMOTE_PORT;
	private final String databaseName = BasicConstants.REMOTE_DATABASE_NAME;
	private final String userName = BasicConstants.REMOTE_USER;
	private final String password = BasicConstants.REMOTE_PASSWORD;
	

	public SqlserverConnector() {
	 	System.out.println("Connecting to "+serverName);
//		String connectionUrl = getConnectionUrl();
		this.connection = makeConnection();
		executeSqlserverTestQuery(null);
		
		
	}
	private String getConnectionUrl() {
		System.out.println("Connection URL "+url + serverName + ":" + portNumber + ";"+"databaseName="+databaseName+";");
		return url + serverName + ":" + this.portNumber + ";" + "databaseName=" + this.databaseName+";";
	}

	private Connection makeConnection() {
		try {
//			LOGGER.log(Level.INFO, msg, thrown);
			
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection(getConnectionUrl(), userName, password);
			if (connection != null) {
				System.out.println("Connection to SqlServer "+serverName+" successful");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error connecting to SqlServer : " + e.getMessage());
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
	
	public void executeSqlserverTestQuery(String args[])
	{
		// change this query to appropriate table
//		String query = "select count(*) from patient_details";
		
//		String query = "select count(*) from "+BasicConstants.REMOTE_PATIENTVIEW_NAME;
		String query = "select count(*) from "+BasicConstants.REMOTE_LABVIEW_NAME;
		
		ResultSet rs = null;
		try {
			Statement stmt = this.connection.createStatement();
			System.out.println("Executing query "+query);
			rs = stmt.executeQuery(query);
//			rs.first();
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

	
//	public ResultSet executeSelectQuery(String query)
//	{
//		ResultSet rs = null;
//		try {
//			Statement stmt = connection.createStatement();
//			rs = stmt.executeQuery(query);
//			
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return rs;
//		
//	}
//	
//	public ResultSet executeInsertQuery(String query)
//	{
//		ResultSet rs = null;
//		try {
//			Statement stmt = connection.createStatement();
//			rs = stmt.executeQuery(query);
//						
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return rs;
//		
//	}

	
	/*
	public List<String>retrieveTestsList()
	{
//		String query = "SELECT testid, testcode, testname from tests_list ";
		String query = "SELECT testid FROM ref_testslist ";
		ResultSet rs = executeCustomQuery(query);
		List<String> testsList = new ArrayList<String>();
 
		try {
			
			while(rs.next())
			{
				testsList.add(rs.getString(1));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return testsList;
			
	}
	*/
	
	/*
	public List<String>retrieveUhidsList()
	{
//		String query = "SELECT testid, testcode, testname from tests_list ";
		String query = "SELECT uhid FROM baby_detail where admissionstatus='t' and activestatus='t' ";
		ResultSet rs = executeCustomQuery(query);
		List<String> uhidsList = new ArrayList<String>();
 
		try {
			
			while(rs.next())
			{
				uhidsList.add(rs.getString(1));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			System.out.println("UHIDs received successfully..Count "+uhidsList.size());
		}
		return uhidsList;			
	}
	
	
//	public Boolean insertData(String dated, String prn, String testid, String itemid, String itemname, String itemvalue, String unit, String normalrange, String regno)
//	{
//		String query = "INSERT INTO test_result(resultdate, prn, testid, itemid, itemname, itemvalue, itemunit, normalrange, regno) VALUES ( "
//				+ "'"+dated.substring(0, 10)+"' , '"+prn+"' , '"+testid+"' , '"+itemid+"' , '"+itemname+"' , '"+itemvalue+"' , '"+unit+"' , '"+normalrange+"' , '"+regno+"' "
//						+ " )";
//		System.out.println(query);
//		ResultSet rs = executeCustomQuery(query);
//		
//		return true;
//	
//		
//	}
//	public Boolean insertData(ResultSet rs) throws SQLException
//	{
//		String insertSqlQuery = "insert into test_result(prn,testid,itemid,itemname,itemvalue,itemunit,normalrange,regno,resultdate) values (?, ?, ?, ?, ?, ?, ?, ?, ?) ";
//		PreparedStatement ps = connection.prepareStatement(insertSqlQuery);
//
//		while(rs.next())
//		{
//			String dated = rs.getString(1); ps.setString(9, dated);
//			String prn = rs.getString(2); ps.setString(1, prn);
//			String testid= rs.getString(3); ps.setString(2, testid);
//			String itemid = rs.getString(4); ps.setString(3, itemid);
//			String itemname= rs.getString(5); ps.setString(4, itemname);
//			String itemvalue = rs.getString(6); ps.setString(5, itemvalue);
//			String unit = rs.getString(7); ps.setString(6, unit);
//			String normalrange = rs.getString(8); ps.setString(7, normalrange);
//			String regno = rs.getString(9); ps.setString(8, regno);
//			System.out.printf("%s %s %s %s %s %s %s %s %s \n",prn,testid,itemid,itemname,itemvalue,unit,normalrange,regno,dated);
//			ps.addBatch();
//			
//			//Boolean out = destConnection.insertData(dated, prn, testid, itemid, itemname, itemvalue, unit, normalrange, regno);
//			//System.out.println(out);
//			
//		}
//		
//		try {
//			ps.executeBatch();
//			
//		} catch (BatchUpdateException e) {
//			throw e.getNextException(); 
//		}
//		finally {
//			ps.close(); 
//		}
//		
//		return true;
//	}
	
	*/

}
