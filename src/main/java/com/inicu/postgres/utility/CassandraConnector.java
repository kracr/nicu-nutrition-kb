package com.inicu.postgres.utility;




import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;

import static java.lang.System.out;

import java.util.List;
/**
 * Class used for connecting to Cassandra database.
 */
public class CassandraConnector
{
   /** Cassandra Cluster. */
   private Cluster cluster;
   /** Cassandra Session. */
   private Session session;
   /**
    * Connect to Cassandra Cluster specified by provided node IP
    * address and port number.
    *
    * @param node Cluster node IP address.
    * @param port Port of cluster host.
    */
   public static void main1(String[] args)
   {
	   final CassandraConnector client = new CassandraConnector();
	   args = new String[2];
	   args[0] = "10.244.181.105";
	   args[1] = "9042";
	   client.executeCassandraSelectQuery(args, client);
   }
   
   public void executeCassandraSelectQuery(String args[],CassandraConnector client){
	   
	   	
	      final String ipAddress = args.length > 0 ? args[0] : "localhost";
	      final int port = args.length > 1 ? Integer.parseInt(args[1]) : 9042;
	      out.println("Connecting to IP Address " + ipAddress + ":" + port + "...");
	      client.connect(ipAddress, port);
	      
	      final ResultSet sessionList = client.getSession().execute(
	              "SELECT * from inicu.patient_devicedata_infinity ");
	      final Row sessionRow = sessionList.one();
	      final List<Row> allRows = sessionList.all();
	      out.println("No. of rows "+allRows.size());
	      out.println(allRows.get(0));
	      out.println(sessionRow.getString("name"));
	      client.close();
   }
   
   
   public void connect(final String node, final int port)
   {
      this.cluster = Cluster.builder().addContactPoint(node).withPort(port).build();
      final Metadata metadata = cluster.getMetadata();
      out.printf("Connected to cluster: %s\n", metadata.getClusterName());
      for (final Host host : metadata.getAllHosts())
      {
         out.printf("Datacenter: %s; Host: %s; Rack: %s\n",
            host.getDatacenter(), host.getAddress(), host.getRack());
      }
      session = cluster.connect();
   }
   /**
    * Provide my Session.
    *
    * @return My session.
    */
   public Session getSession()
   {
      return this.session;
   }
   /** Close cluster. */
   public void close()
   {
      cluster.close();
   }
}