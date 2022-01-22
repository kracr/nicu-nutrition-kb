/**
 * 
 */
package com.inicu.deviceadapter.library;

import java.util.ArrayList;
import java.util.List;

import com.inicu.deviceadapter.library.datamodel.InicuConsumerModel;
import com.inicu.deviceadapter.library.datamodel.InicuDeviceEvent;
import com.inicu.deviceadapter.library.inicudevicelistener.InicuDeviceListener;

/**
 * @author sanoob
 *
 */
public class InicuDeviceAdapter {

	private InicuConsumerModel consumerModel;
	private KafkaConsumerClass kafkaConsumer;
	private List<InicuDeviceListener> listeners = new ArrayList<InicuDeviceListener>();

	public InicuDeviceAdapter(InicuConsumerModel consumerModel) {
		this.consumerModel = consumerModel;
		kafkaConsumer = new KafkaConsumerClass(this.consumerModel, InicuDeviceAdapter.this);
		Thread consumerThread = new Thread(new Runnable() {

			@Override
			public void run() {
				kafkaConsumer.startConsumer();

			}
		});
		consumerThread.start();
	}

	public void addListener(InicuDeviceListener deviceListener) {
		listeners.add(deviceListener);
	}

	public void fireDataReceivedEvent(String data) {
		InicuDeviceEvent event = new InicuDeviceEvent(data, InicuDeviceEvent.EVENT_TYPE_DATA);
		for (InicuDeviceListener inicuListener : listeners)
			inicuListener.onDataAvailable(event);
	}

}
