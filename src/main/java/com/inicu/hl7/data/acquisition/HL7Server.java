
package com.inicu.hl7.data.acquisition;


import java.io.IOException;

import ca.uhn.hl7v2.hoh.api.DecodeException;
import ca.uhn.hl7v2.hoh.api.EncodeException;
import ca.uhn.hl7v2.hoh.api.IAuthorizationClientCallback;
import ca.uhn.hl7v2.hoh.api.IReceivable;
import ca.uhn.hl7v2.hoh.api.ISendable;
import ca.uhn.hl7v2.hoh.api.MessageMetadataKeys;
import ca.uhn.hl7v2.hoh.auth.SingleCredentialClientCallback;
import ca.uhn.hl7v2.hoh.hapi.api.MessageSendable;
import ca.uhn.hl7v2.hoh.hapi.client.HohClientSimple;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v22.message.ADT_A01;
import ca.uhn.hl7v2.model.v22.message.ORU_R01;
import ca.uhn.hl7v2.parser.Parser;
import ca.uhn.hl7v2.parser.PipeParser;


/**
 * @author sanoob
 * iNICU
 * 24-Feb-2017
 */


public class HL7Server implements Runnable {


	/*
	 * HL7Server runs in background and listens to a port for hl7 data
	 * 
	 * usage: new Thread(new HL7Server()).start();
	 * 
	 */


	private int PORT;

	public HL7Server(int port) {
		super();
		PORT = port;
	}

	@Override
	public void run() {
		startServer();
	}

	public void startServer() {


		//		/*
		//		 * Sending a message with HAPI and HL7 over HTTP. First
		//		 * an LLP instance is created. Note that you must tell
		//		 * the LLP class whether it will be used in a client
		//		 * or a server.
		//		 */
		//		LowerLayerProtocol llp;
		//		llp = new Hl7OverHttpLowerLayerProtocol(ServerRoleEnum.SERVER);
		//		/* 
		//		 * Create the server, and pass in the HoH LLP instance
		//		 * 
		//		 * Note that the HoH LLP implementation will not
		//		 * work in two-socket servers
		//		 */
		//		PipeParser parser = PipeParser.getInstanceWithNoValidation();
		//		
		//		SimpleServer server = new SimpleServer(PORT, llp, parser);
		//		
		//		// Set custom exception handler
		//		server.setExceptionHandler(new HL7ExceptionHandler());
		//		
		//		// Register custom connection listener
		//		server.registerConnectionListener(new HL7ConnectionListener());
		//		
		//		// Register an application to the server, and start it
		//		server.registerApplication("*", "*", new HL7MessageReceiver());
		//		server.start();
		
		
		
		
		
		

//		try{
//
//			/*
//			 * http://localhost:8080/AppContext
//			 */
//			String host = "192.168.1.2";
//			int port = 7998;
////			String uri = "/AppContext";
//			String uri = "/";
//
//			// Create a parser
//			Parser parse = PipeParser.getInstanceWithNoValidation();
//
//			// Create a client
//			HohClientSimple client = new HohClientSimple(host, port, uri, parse);
//
//			// Optionally, if credentials should be sent, they 
//			// can be provided using a credential callback
////			IAuthorizationClientCallback authCalback = new SingleCredentialClientCallback("ausername", "somepassword");
////			client.setAuthorizationCallback(authCalback);
//
//			// The ISendable defines the object that provides the actual
//			// message to send
////			ADT_A01 adt = new ADT_A01();
//			ORU_R01 oru = new ORU_R01();
//			oru.initQuickstart("ORU", "R01", "T");
//			// .. set other values on the message ..
//
//			// The MessageSendable provides the message to send 
//			ISendable sendable = new MessageSendable(oru);
//
//			// sendAndReceive actually sends the message
//			IReceivable<Message> receivable = client.sendAndReceiveMessage(sendable);
//
//			// receivavle.getRawMessage() provides the response
//			Message message = receivable.getMessage();
//			System.out.println("Response was:\n" + message.encode());
//
//			// IReceivable also stores metadata about the message
//			String remoteHostIp = (String) receivable.getMetadata().get(MessageMetadataKeys.REMOTE_HOST_ADDRESS);
//			System.out.println("From:\n" + remoteHostIp);
//
//			/*
//			 * Note that the client may be reused as many times as you like,
//			 * by calling sendAndReceiveMessage repeatedly
//			 */
//
//		} catch (DecodeException e) {
//			// Thrown if the response can't be read
//			e.printStackTrace();
//		} catch (IOException e) {
//			// Thrown if communication fails
//			e.printStackTrace();
//		} catch (EncodeException e) {
//			// Thrown if the message can't be encoded (generally a programming bug)
//			e.printStackTrace();
//		}catch(Exception e){
//			e.printStackTrace();
//		}

	}



}
