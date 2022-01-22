/**
 * 
 */
package com.inicu.deviceadapter.library.push;

/**
 * @author sanoob
 *
 */
public class PushData {
	private KafkaProducerClass producer;

	public PushData(KafkaProducerDataModel dataModel) {
		producer = new KafkaProducerClass(dataModel);
	}

	public boolean push(String data) {
		try {

			producer.pushDatatoServer(data);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

}
