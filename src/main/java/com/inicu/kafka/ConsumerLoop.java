package com.inicu.kafka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.json.JSONObject;
import org.slf4j.*;

import com.inicu.cassandra.service.DeviceDataService;
import com.inicu.kafkacassandra.connector.cassandra.CassandraConnector;
import com.inicu.kafkacassandra.connector.constants.Constants;
import com.inicu.postgres.utility.BasicConstants;
import com.inicu.postgres.utility.BasicUtils;

public class ConsumerLoop implements Runnable{

	private final Logger logger = LoggerFactory.getLogger(ConsumerLoop.class);
//	Logger.getLogger("org").setLevel(Level.WARNING);
//	Logger.getLogger("akka").setLevel(Level.WARNING);       
//	Logger.getLogger("kafka").setLevel(Level.WARNING);
	private final KafkaConsumer<String, String> consumer;
	private final List<String> topics;
	private final int id;

	public ConsumerLoop(int id,
			String groupId, 
			List<String> topics) {
		
		this.id = id;
		this.topics = topics;
		String bootstrapServer = BasicConstants.props.getProperty(BasicConstants.PROP_KAFKA_HOST)+":"+
				BasicConstants.props.getProperty(BasicConstants.PROP_KAFKA_PORT);
		Properties props = new Properties();
		props.put("bootstrap.servers", bootstrapServer);
		props.put("group.id", groupId);
		props.put("key.deserializer", StringDeserializer.class.getName());
		props.put("value.deserializer", StringDeserializer.class.getName());
		props.put("enable.auto.commit", "true");
		props.put("auto.offset.reset", "latest");
		this.consumer = new KafkaConsumer<>(props);
	}

	@Override
	public void run() {
		try {
			consumer.subscribe(topics);

			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
				for (ConsumerRecord<String, String> record : records) {
					Map<String, Object> data = new HashMap<>();
					data.put("partition", record.partition());
					data.put("offset", record.offset());
					data.put("value", record.value());
					System.out.println("data received " + record.value());
					try{
						//compute data and push data to postgres
						DeviceDataService service = BasicConstants.applicatonContext.getBean(DeviceDataService.class);
						if(!BasicUtils.isEmpty(service)){
							JSONObject pushObject = new JSONObject(record.value());
							String devId = pushObject.get(Constants.DEVICE_ID_JSON_KEY).toString();
							if(!BasicUtils.isEmpty(devId)){
								String uhid = service.getUhidFromBoxName(devId);
//								uhid = "521198";
								if(!BasicUtils.isEmpty(uhid)){
									String deviceTime = pushObject.getString(Constants.DEVICE_TIME_JSON_KEY);
									String deviceType = pushObject.get(Constants.DEVICE_NAME_JSON_KEY).toString();
									service.pushKafkaDataToCassandra(uhid,pushObject,deviceTime,deviceType);
								}
							}
						}
						
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		} catch (WakeupException e) {
			// ignore for shutdown 
		} finally {
			consumer.close();
		}
	}

	public void shutdown() {
		consumer.wakeup();
	}
}
