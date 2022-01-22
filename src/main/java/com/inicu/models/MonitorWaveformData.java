package com.inicu.models;

import java.util.List;

public class MonitorWaveformData {
	List<String> waveformdata;
	String timestamp;
	public List<String> getWaveformdata() {
		return waveformdata;
	}
	public void setWaveformdata(List<String> waveformdata) {
		this.waveformdata = waveformdata;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}
