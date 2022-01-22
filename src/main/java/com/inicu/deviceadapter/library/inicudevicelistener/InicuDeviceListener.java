package com.inicu.deviceadapter.library.inicudevicelistener;

import com.inicu.deviceadapter.library.datamodel.InicuDeviceEvent;

/**
 * @author sanoob
 *
 */
public interface InicuDeviceListener {
	
	public void onDataAvailable(InicuDeviceEvent data);
	
}
