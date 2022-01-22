package com.inicu.cassandra.hapi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.Reader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.Parser;
import ca.uhn.hl7v2.parser.PipeParser;
import ca.uhn.hl7v2.protocol.ApplicationRouter;
import ca.uhn.hl7v2.protocol.Transportable;
import ca.uhn.hl7v2.protocol.impl.TransportableImpl;

/**
 * <p>
 * Performs the responding role in a message exchange (i.e receiver of the first
 * message, sender of the response; analagous to the server in a client-server
 * interaction), according to HL7's original mode processing rules.
 * </p>
 * <p>
 * At the time of writing, enhanced mode, two-phase reply, continuation
 * messages, and batch processing are unsupported.
 * </p>
 * 
 * @author Bryan Tripp
 */
class NihonKohdenResponder {

	private ApplicationRouter apps;
	private Socket inboundSocket;

	/**
	 * Creates a new instance of Responder
	 */
	public NihonKohdenResponder(Socket theInboundSocket) {
		inboundSocket = theInboundSocket;
	}


	/**
	 * Processes an incoming message string and returns the response message
	 * string. Message processing consists of parsing the message, finding an
	 * appropriate Application and processing the message with it, and encoding
	 * the response. Applications are chosen from among those registered using
	 * <code>registerApplication</code>. The Parser is obtained from the
	 * Connection associated with this Responder.
	 */
	protected String processMessage(String incomingMessageString)
			throws HL7Exception {
		System.out.println("---------data received from NK-----"+incomingMessageString);
//		Map<String, Object> metadata = new HashMap<String, Object>();
//		InetSocketAddress remoteSocketAddress = (InetSocketAddress) inboundSocket.getRemoteSocketAddress();
//		metadata.put(ApplicationRouter.METADATA_KEY_SENDING_IP, remoteSocketAddress.getAddress().getHostAddress());
//		metadata.put(ApplicationRouter.METADATA_KEY_SENDING_PORT, remoteSocketAddress.getPort());
//		
//		Transportable response = apps.processMessage(new TransportableImpl(incomingMessageString, metadata));
//		HL7v24Parser parser = new HL7v24Parser();
//		parser.parse(incomingMessageString);
		return incomingMessageString.replaceAll("\\r", "\n");
//		return incomingMessageString;
	}
	
//	/**
//	 * Logs the given exception and creates an error message to send to the
//	 * remote system.
//	 * 
//	 * @param encoding
//	 *            The encoding for the error message. If <code>null</code>, uses
//	 *            default encoding
//	 */
//	public static String logAndMakeErrorMessage(Exception e, Segment inHeader,
//			Parser p, String encoding) throws HL7Exception {
//		return ApplicationRouterImpl.logAndMakeErrorMessage(e, inHeader, p, encoding);
//	}

	/**
	 * Registers an Application with this Responder. The "Application", in this
	 * context, is the software that uses the information in the message. If
	 * multiple applications are registered, incoming Message objects will be
	 * passed to each one in turn (calling <code>canProcess()</code>) until one
	 * of them accepts responsibility for the message. If none of the registered
	 * applications can process the message, a DefaultApplication is used, which
	 * simply returns an Application Reject message.
	 */
	void setApplicationRouter(ApplicationRouter router) {
		this.apps = router;
	}

	/**
	 * Test code.
	 */
	@SuppressWarnings({ "unused", "resource" })
	public static void main3(String args[]) {
		if (args.length != 1) {
			System.err.println("Usage: DefaultApplication message_file");
			System.exit(1);
		}

		// read test message file ...
		try {
			File messageFile = new File(args[0]);
			Reader in = new BufferedReader(new FileReader(messageFile));
			int fileLength = (int) messageFile.length();
			char[] cbuf = new char[fileLength];
			in.read(cbuf, 0, fileLength);
			String messageString = new String(cbuf);

			// parse inbound message ...
			final Parser parser = new PipeParser();
			Message inMessage = null;
			try {
				inMessage = parser.parse(messageString);
			} catch (HL7Exception e) {
				e.printStackTrace();
			}

			// process with responder ...
			PipedInputStream initInbound = new PipedInputStream();
			PipedOutputStream initOutbound = new PipedOutputStream();
			PipedInputStream respInbound = new PipedInputStream(initOutbound);
			PipedOutputStream respOutbound = new PipedOutputStream(initInbound);

			/*
			 * This code won't work with new changes: final ActiveInitiator init = new
			 * ActiveInitiator(parser, new MinLowerLayerProtocol(), initInbound,
			 * initOutbound); Responder resp = new Responder(respInbound,
			 * respOutbound);
			 * 
			 * //run the initiator in a separate thread ... final Message
			 * inMessCopy = inMessage; Thread initThd = new Thread(new
			 * Runnable() { public void run() { try { Message response =
			 * init.sendAndReceive(inMessCopy);
			 * System.out.println("This is initiator writing response ...");
			 * System.out.println(parser.encode(response)); } catch (Exception
			 * ie) { if (HL7Exception.class.isAssignableFrom(ie.getClass())) {
			 * System.out.println("Error in segment " +
			 * ((HL7Exception)ie).getSegmentName() + " field " +
			 * ((HL7Exception)ie).getFieldPosition()); } ie.printStackTrace(); }
			 * } }); initThd.start();
			 * 
			 * //process the message we expect from the initiator thread ...
			 * System.out.println("Responder is going to respond now ...");
			 * resp.processOneMessage();
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


}
