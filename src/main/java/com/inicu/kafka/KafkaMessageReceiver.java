package com.inicu.kafka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.inicu.postgres.utility.BasicConstants;

public class KafkaMessageReceiver {

	public static void startKafkaMessageReceiver() {
		int numConsumers = 1;
		String groupId = BasicConstants.props
				.getProperty(BasicConstants.PROP_KAFKA_GROUP_ID);
		List<String> topics = Arrays.asList(BasicConstants.props
				.getProperty(BasicConstants.PROP_KAFKA_CONNECTOR_TOPIC));
		ExecutorService executor = Executors.newFixedThreadPool(numConsumers);

		final List<ConsumerLoop> consumers = new ArrayList<>();
		for (int i = 0; i < numConsumers; i++) {
			ConsumerLoop consumer = new ConsumerLoop(i, groupId, topics);
			consumers.add(consumer);
			executor.submit(consumer);
		}
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
		    @Override
		    public void run() {
		      for (ConsumerLoop consumer : consumers) {
		        consumer.shutdown();
		      } 
		      executor.shutdown();
		      try {
		        executor.awaitTermination(5000, TimeUnit.MILLISECONDS);
		      } catch (InterruptedException e) {
		        e.printStackTrace();
		      }
		    }
		  });
	}

}
