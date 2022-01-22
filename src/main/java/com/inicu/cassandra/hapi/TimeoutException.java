package com.inicu.cassandra.hapi;

import ca.uhn.hl7v2.HL7Exception;

public class TimeoutException extends HL7Exception {

    public TimeoutException(String message) {
        super(message);
    }
}
