package com.inicu.cassandra.hapi;

import java.net.Socket;

import org.springframework.stereotype.Repository;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.validation.builder.support.NoValidationBuilder;

import com.inicu.postgres.utility.BasicConstants;


public class TestNihonKohdenMonitor {

	
	boolean keepRunning = true;
	public static void startMonitoring(String[] args) {
		// TODO Auto-generated method stub
		int port = 2221; // The port to listen on
		boolean useTls = false; // Should we use TLS/SSL?
		try
		{
			HapiContext context = new DefaultHapiContext();
			context.setValidationRuleBuilder(new NoValidationBuilder());
//			context.setExecutorService(Executors.newCachedThreadPool());
	 		
//			HL7Service server = context.newServer(port, useTls);
//			ReceivingApplication<Message> recieverApplication = new HL7MessageReceiver();
//			server.registerApplication("ORU", "R01", recieverApplication);
//			server.startAndWait();

			//		      String msg = "MSH|^~\\&|NIHON KOHDEN|NIHON KOHDEN|CLIENT APP|CLIENT FACILITY|20170525022200||ORU^R01^ORU_R01|20170525000019|P|2.4|||NE|AL||ASCII||ASCII";
			//		      Parser p = context.getPipeParser();
			//		      Message oruMessage = p.parse(msg);		    
			Socket socket = new Socket("192.168.1.2",7998);
			//Connection connection = context.newClient("192.168.1.2",7998, useTls);
			NihonKohdenActiveConnection active = new NihonKohdenActiveConnection(context.getXMLParser(), context.getLowerLayerProtocol(),socket);
//			Receiver recev = new NihonKohdenReceiver(active,context.getLowerLayerProtocol().getReader(sock.getInputStream()));
			
			active.activate();

//			Initiator initiator = connection.getInitiator();

//			ActiveConnection activeconn = new ActiveConnection(null, null, null);
			//		      Receiver receiver = new Receiver(connection,);
//			ORU_R01 message = new ORU_R01();
//
//			message.initQuickstart("ORU", "R01", "P");


//			Message response = initiator.sendAndReceive(message);

//			System.out.println("------Received response-----"+response);
//			Message response = initiator.sendAndReceive(adt);

		}
//		catch(LLPException exp)
//		{
//			exp.printStackTrace();
//		}	 	      
//		catch(IOException exp)
//		{
//			exp.printStackTrace();
//		}	 	      
//		catch(InterruptedException exp)
//		{
//			exp.printStackTrace();
//		}	      
//		catch(HL7Exception exp)
//		{
//			exp.printStackTrace();
//		}
		catch(Exception e)
		{
			if(e instanceof NullPointerException)
			{
				System.out.println("Inside unsolicit application is null exception");
				
			}
			e.printStackTrace();
		}



	}

}
