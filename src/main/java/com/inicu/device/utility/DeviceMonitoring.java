package com.inicu.device.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inicu.cassandra.service.DeviceDataService;
import com.inicu.postgres.entities.VwVitalDetail;
import com.inicu.postgres.utility.BasicConstants;

/*
 * This class creates a thread to check data coming from every new device added to a patient/bed. 
 * If data is not received for 5 mins, alarm is set
 */

public class DeviceMonitoring {

	public VwVitalDetail vwVitalObj;
	public boolean alarm;

	public VwVitalDetail getVwVitalObj() {
		return vwVitalObj;
	}

	public void setVwVitalObj(VwVitalDetail vwVitalObj) {
		this.vwVitalObj = vwVitalObj;
	}

	public boolean isAlarm() {
		return alarm;
	}

	public void setAlarm(boolean alarm) {
		this.alarm = alarm;
	}

}
