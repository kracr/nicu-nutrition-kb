/**
 * 
 */
package com.inicu.deviceadapter.library.push;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;


/**
 * @author sanoob
 *
 */
public class KafkaProducerClass {

	private Producer<String, String> kafkaProducer;
	private Properties producerProps;
	private KafkaProducerDataModel producerDataModel;

	public Producer<String, String> getKafkaProducer() {
		return kafkaProducer;
	}

	public KafkaProducerClass(KafkaProducerDataModel producerDataModel) {
		this.producerDataModel = producerDataModel;
		try {
			initializeProducer();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * initializes the producer class
	 */
	public void initializeProducer() {

		producerProps = new Properties();
		producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
				producerDataModel.getHostName() + ":" + producerDataModel.getPort());
		producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.ByteArraySerializer");
		producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.StringSerializer");

		kafkaProducer = new KafkaProducer<String, String>(producerProps);

	}

	/*
	 * pushes data into kafka server
	 * 
	 * @param data : json data object
	 * 
	 */
	public void pushDatatoServer(String data) {
		try {

			ProducerRecord<String, String> dataRecord = new ProducerRecord<String, String>(producerDataModel.getTopic(),
					data);
			kafkaProducer.send(dataRecord);
			//System.out.println("data pushed: " + data.toString());

			// System.out.println(data.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
