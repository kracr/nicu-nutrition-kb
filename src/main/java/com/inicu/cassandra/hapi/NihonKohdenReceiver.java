package com.inicu.cassandra.hapi;

import ca.uhn.hl7v2.app.ActiveConnection;
import ca.uhn.hl7v2.app.Receiver;
import ca.uhn.hl7v2.llp.HL7Reader;

public class NihonKohdenReceiver extends Receiver{

	public NihonKohdenReceiver(ActiveConnection c, HL7Reader in) {
		super(c, in);
		// TODO Auto-generated constructor stub
	}

	public void processMessage(String message) {
		System.out.println("-------Nihon Kohden message------"+message);
	}
}
