/**
 * 
 */
package com.inicu.deviceadapter.library;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import com.inicu.deviceadapter.library.datamodel.InicuConsumerModel;

/**
 * @author sanoob
 *
 */
public class KafkaConsumerClass {

	private InicuConsumerModel consumerDataModel;
	private KafkaConsumer<String, String> kafkaConsumer;
	private InicuDeviceAdapter deviceAdapter;

	public KafkaConsumer<String, String> getKafkaConsumer() {
		return kafkaConsumer;
	}

	public KafkaConsumerClass(InicuConsumerModel consumerDataModel, InicuDeviceAdapter deviceAdapter) {
		super();
		this.consumerDataModel = consumerDataModel;
		this.deviceAdapter = deviceAdapter;
		try {
			initializeConsumer();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void initializeConsumer() {
		Properties props = new Properties();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
				consumerDataModel.getHostName() + ":" + consumerDataModel.getPort());
		props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerDataModel.getGroupId());
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		kafkaConsumer = new KafkaConsumer<>(props);
	}

	public void startConsumer() {
		kafkaConsumer.subscribe(Arrays.asList(consumerDataModel.getTopic()));
		try {
			while (true) {
				ConsumerRecords<String, String> records = kafkaConsumer.poll(1000);
				for (ConsumerRecord<String, String> record : records) {
					//System.out.println("Consumer **** " + record.offset() + ": " + record.value());
					deviceAdapter.fireDataReceivedEvent(record.value());
				}
			}
		} finally {
			kafkaConsumer.close();
		}

		// Thread consumerThread = new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		//
		// }
		// });
		// consumerThread.start();

	}

}
