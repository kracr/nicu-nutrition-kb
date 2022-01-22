package com.inicu.kafkacassandra.connector.datamodel;

/**
 * @author sanoob
 *
 */
public class InicuConsumerModel {
	private String hostName;
	private String port;
	private String topic;
	private String groupId;

	public InicuConsumerModel(String hostName, String port, String topic, String groupId) {
		super();
		this.hostName = hostName;
		this.port = port;
		this.topic = topic;
		this.groupId = groupId;
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

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

}
