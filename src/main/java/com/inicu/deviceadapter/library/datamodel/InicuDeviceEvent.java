/**
 * 
 */
package com.inicu.deviceadapter.library.datamodel;

/**
 * @author sanoob
 *
 */
public class InicuDeviceEvent {
	
	public static final int EVENT_TYPE_DATA = 1;
	
	private String data;
	private int eventType;
	
	public String getData() {
		return data;
	}
	public int getEventType() {
		return eventType;
	}
	public InicuDeviceEvent(String data, int eventType) {
		super();
		this.data = data;
		this.eventType = eventType;
	}
	
	

}
