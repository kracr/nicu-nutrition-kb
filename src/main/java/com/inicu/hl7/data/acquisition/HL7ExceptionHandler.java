/**
 * 
 */
package com.inicu.hl7.data.acquisition;

import java.util.Map;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.protocol.ReceivingApplicationExceptionHandler;

/**
 * @author sanoob 
 * iNICU 
 * 24-Feb-2017
 */
public class HL7ExceptionHandler implements ReceivingApplicationExceptionHandler {

	@Override
	public String processException(String inComingMessage, Map<String, Object> metadataMap, String outgoingMessage, Exception excp)
			throws HL7Exception {

		/*
		 * Here you can do any processing you like. If you want to change the
		 * response (NAK) message which will be returned you may do so, or just
		 * return the default NAK (outgoingMessage)
		 */

		return outgoingMessage;
	}

}
