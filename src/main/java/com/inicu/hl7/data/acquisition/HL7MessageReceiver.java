/**
 * 
 */
package com.inicu.hl7.data.acquisition;

import java.io.IOException;
import java.util.Map;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.protocol.ReceivingApplication;
import ca.uhn.hl7v2.protocol.ReceivingApplicationException;

/**
 * @author sanoob 
 * iNICU
 * 24-Feb-2017
 */
public class HL7MessageReceiver implements ReceivingApplication {

	@Override
	public boolean canProcess(Message message) {

		/*
		 * call method to process the received message
		 * 
		 */
		System.out.println("Message Received: " + message);
		return true;

	}

	@Override
	public Message processMessage(Message message, Map<String, Object> map)
			throws ReceivingApplicationException, HL7Exception {

		String encodedMessage = "";
		DefaultHapiContext context = new DefaultHapiContext();
		encodedMessage = context.getPipeParser().encode(message);

		/*
		 * encoded message : encoded hl7 message for further
		 * processing
		 */

		System.out.println("encoded message: " + encodedMessage);

		try {
			
			/*
			 * automatic acknowledgement generation 
			 */
			Message acknowledgement = message.generateACK();
			System.out.println("acknowledgement: "+acknowledgement);
			return (acknowledgement);

		} catch (IOException | HL7Exception e) {

			e.printStackTrace();
			return null;

		} finally {

			try {

				if (context != null)
					context.close();

			} catch (IOException e) {

				e.printStackTrace();
			}
		}

	}


}
