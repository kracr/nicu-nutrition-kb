package com.inicu.cassandra.utility;

import com.inicu.deviceadapter.library.push.KafkaProducerDataModel;
import com.inicu.deviceadapter.library.push.PushData;

public class PushDataKafka{

	PushData dataPush = new PushData(new KafkaProducerDataModel("", "", ""));
	
	public void pushData(){
		dataPush.push("");
	}

}
