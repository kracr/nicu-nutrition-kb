package com.inicu.device.utility;

import java.util.HashMap;

/**
 * 
 * @author Dipin
 *
 *The following class contains all the mapping objects
 *required to identify the device features, in order 
 *to push it to the postgres database.
 */
public class DeviceObjectMapper {

	public static HashMap<String,String> deviceObjectNameMap = new HashMap<>();
	static{
		deviceObjectNameMap.put(DeviceConstants.DEVICE_VISMO,"com.inicu.device.nihonkohden.Vismo");
		deviceObjectNameMap.put(DeviceConstants.DEVICE_PHILIPS_INTELLIVUE, "com.inicu.device.philips.IntellivueSeries");
		deviceObjectNameMap.put(DeviceConstants.DEVICE_PHILIPS_INTELLIVUE_MX800, "com.inicu.device.philips.IntellivueMX800");
		deviceObjectNameMap.put(DeviceConstants.DEVICE_PHILIPS_SURESIGN, "com.inicu.device.philips.SureSign");
		deviceObjectNameMap.put(DeviceConstants.DEVICE_MASIMO_RADICAL, "com.inicu.device.masimo.RadicalSeries");
		deviceObjectNameMap.put(DeviceConstants.DEVICE_MEDTRONIC_INVOS5100C, "com.inicu.device.medtronic.Invos5100C");
		deviceObjectNameMap.put(DeviceConstants.DEVICE_DRAEGER_EVITA, "com.inicu.device.draeger.EvitaXl");
		deviceObjectNameMap.put(DeviceConstants.DEVICE_DRAEGER_SLE5000, "com.inicu.device.ge.Sle5000");
		deviceObjectNameMap.put(DeviceConstants.DEVICE_DRAEGER_BABYLOG8000, "com.inicu.device.draeger.BabyLog8000");
		deviceObjectNameMap.put(DeviceConstants.DEVICE_DRAEGER_VN500, "com.inicu.device.draeger.DraegerVN500");
		deviceObjectNameMap.put(DeviceConstants.DEVICE_DRAEGER_INFINITY_DELTA, "com.inicu.device.draeger.DraegerInfinityDelta");
		deviceObjectNameMap.put(DeviceConstants.DEVICE_DRAEGER_INFINITY_VISTA, "com.inicu.device.draeger.DraegerInfinityVista");
		deviceObjectNameMap.put(DeviceConstants.DEVICE_DRAEGER_INFINITY_VISTA_XL, "com.inicu.device.draeger.DraegerInfinityVistaXL");

		deviceObjectNameMap.put(DeviceConstants.DEVICE_DRAEGER_VISTA120, "com.inicu.device.draeger.DraegerVista120");
		deviceObjectNameMap.put(DeviceConstants.DEVICE_GE_B40, "com.inicu.device.ge.B40"); // added for apollo craddle
		deviceObjectNameMap.put(DeviceConstants.DEVICE_RADIOMETER_ABL800, "com.inicu.device.radiometer.ABLBasic800");
		deviceObjectNameMap.put(DeviceConstants.DEVICE_STEPHAN_SOPHIE, "com.inicu.device.stephan.Sophie"); // added after Pgi
		deviceObjectNameMap.put(DeviceConstants.DEVICE_MASIMO_ROOT, "com.inicu.device.masimo.RadicalSeries");

		deviceObjectNameMap.put(DeviceConstants.DEVICE_NELLCOR_N560, "com.inicu.device.nellcor.N560");
		deviceObjectNameMap.put(DeviceConstants.DEVICE_DRAEGER_ISOLETTE8000, "com.inicu.device.draeger.DraegerIsolette8000");
		deviceObjectNameMap.put(DeviceConstants.DEVICE_GE_GIRAFFE, "com.inicu.device.ge.Giraffe");
		deviceObjectNameMap.put(DeviceConstants.DEVICE_GE_GIRAFFE_WARMER, "com.inicu.device.ge.GiraffeWarmer");
	}
}
