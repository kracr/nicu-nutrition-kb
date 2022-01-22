/**
 * 
 */
package com.inicu.hl7.data.acquisition;

import ca.uhn.hl7v2.app.Connection;
import ca.uhn.hl7v2.app.ConnectionListener;

/**
 * @author sanoob
 * iNICU
 * 24-Feb-2017
 */
public class HL7ConnectionListener implements ConnectionListener{

	@Override
	public void connectionDiscarded(Connection connection) {
		
		System.out.println("Connection closed "+connection.getRemoteAddress());
		
	}

	@Override
	public void connectionReceived(Connection connection) {
		
		System.out.println("Connection received "+connection.getRemoteAddress());
	}
	
	

}
