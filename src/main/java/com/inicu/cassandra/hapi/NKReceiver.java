package com.inicu.cassandra.hapi;


import java.io.IOException;
import java.net.SocketException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.hl7v2.concurrent.Service;
import ca.uhn.hl7v2.llp.HL7Reader;

/**
 * Listens for incoming messages on a certain input stream, and sends them to
 * the appropriate location.
 * 
 * @author Dipin
 */

public class NKReceiver extends Service {

	private static final Logger log = LoggerFactory.getLogger(NKReceiver.class);

	private NihonKohdenActiveConnection conn;
	private HL7Reader in;

	/** Creates a new instance of Receiver, associated with the given Connection */
	public NKReceiver(NihonKohdenActiveConnection c, HL7Reader in) {
		super("Receiver", c.getExecutorService());
		this.conn = c;
		this.in = in;
	}


	@Override
	protected void handle() {
		try {
			String message = in.getMessage();
			if (message == null) {
				log.debug("Failed to read a message");
			} else {
				processMessage(message);
			}
		} catch (SocketException e)  {
			// This probably means that the client closed the server connection normally
			conn.close();
			log.info("SocketException: closing Connection from " + describeRemoteConnection() + ", will no longer read messages with this Receiver: " + e.getMessage());
		} catch (IOException e) {
			conn.close();
			log.warn("IOException: closing Connection from " + describeRemoteConnection() + ", will no longer read messages with this Receiver. ", e);
		} catch (Exception e) {
			conn.close();
			log.error("Unexpected error, closing connection from " + describeRemoteConnection() + " - ", e);
		}

	}


	private String describeRemoteConnection() {
		return conn.getRemoteAddress().getHostAddress() + ":" + conn.getRemotePort();
	}


	/**
	 * Processes a single incoming message by sending it to the appropriate
	 * internal location. If an incoming message contains an MSA-2 field, it is
	 * assumed that this message is meant as a reply to a message that has been
	 * sent earlier. In this case an attempt is give the message to the object
	 * that sent the corresponding outbound message. If the message contains an
	 * MSA-2 but there are no objects that appear to be waiting for it, it is
	 * discarded and an exception is logged. If the message does not contain an
	 * MSA-2 field, it is concluded that the message has arrived unsolicited. In
	 * this case it is sent to the Responder (in a new Thread).
	 */
	protected void processMessage(String message) {
		String ackID = conn.getParser().getAckID(message);
		if (ackID == null) {
			log.debug("Unsolicited Message Received: {}", message);
			getExecutorService().submit(new Grunt(conn, message));
		} else {
			if (!conn.isRecipientWaiting(ackID, message)) {
				log.info("Unexpected Message Received. This message appears to be an acknowledgement (MSA-2 has a value) so it will be ignored: {}", message);
			} else {
				log.debug("Response Message Received: {}", message);
			}
		}
	}

	/** Independent thread for processing a single message */
	private class Grunt implements Runnable {

		private NihonKohdenActiveConnection conn;
		private String m;

		public Grunt(NihonKohdenActiveConnection conn, String message) {
			this.conn = conn;
			this.m = message;
		}

		public void run() {
			try {
				String response = conn.getResponder().processMessage(m);
				if (response != null) {
					conn.getAckWriter().writeMessage(response);
				} else {
					log.debug("Not responding to incoming message");
				}
			} catch (Exception e) {
				log.error("Error while processing message: ", e);
			}
		}
	}

}
