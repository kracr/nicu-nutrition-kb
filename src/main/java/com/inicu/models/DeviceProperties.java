package com.inicu.models;

import java.util.List;

public class DeviceProperties {

	List<String> listofDevices;
	String kafka_host;
	String kafka_port;
	String kafka_topic;
	
	public List<String> getListofDevices() {
		return listofDevices;
	}
	public void setListofDevices(List<String> listofDevices) {
		this.listofDevices = listofDevices;
	}
	public String getKafka_host() {
		return kafka_host;
	}
	public void setKafka_host(String kafka_host) {
		this.kafka_host = kafka_host;
	}
	public String getKafka_port() {
		return kafka_port;
	}
	public void setKafka_port(String kafka_port) {
		this.kafka_port = kafka_port;
	}
	public String getKafka_topic() {
		return kafka_topic;
	}
	public void setKafka_topic(String kafka_topic) {
		this.kafka_topic = kafka_topic;
	}
}
