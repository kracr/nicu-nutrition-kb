/**
 * 
 */
package com.inicu.deviceadapter.library.devicecontroller;

import com.inicu.deviceadapter.library.push.KafkaProducerDataModel;
import com.inicu.deviceadapter.library.push.PushData;

/**
 * @author sanoob
 *
 */
public class DeviceController {

	private String hostName;
	private PushData CMDProducer;

	public DeviceController(String hostName) {
		this.hostName = hostName;
		CMDProducer = new PushData(
				new KafkaProducerDataModel(this.hostName, DeviceCMD.CMD_PORT, DeviceCMD.CMD_CHANNEL));
	}

	public void sendCMD(String CMD) {
		CMDProducer.push(CMD);
	}

}
