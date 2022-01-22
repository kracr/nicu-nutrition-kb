/**
 * 
 */
package com.inicu.deviceadapter.library.push;

/**
 * @author sanoob
 *
 */
public class KafkaProducerDataModel {



	private String hostName;
	private String port;
	private String topic;

	public KafkaProducerDataModel(String hostName, String port, String topic) {
		super();
		this.hostName = hostName;
		this.port = port;
		this.topic = topic;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}


	
}
