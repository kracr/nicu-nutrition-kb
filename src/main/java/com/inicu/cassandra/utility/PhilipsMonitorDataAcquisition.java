package com.inicu.cassandra.utility;


public class PhilipsMonitorDataAcquisition {

//	String abc = "old";
//
//	@Autowired
//	PatientDeviceService patientDeviceService;
//
//	private Configuration runConf;
//
//	private IceApplication app;
//
//	private final static File[] searchPath = new File [] {
//		new File(".JumpStartSettings"),
//		new File(System.getProperty("user.home"), ".JumpStartSettings")
//	};
//
//
//	public boolean startMonitoring(String address,Integer domainId) throws Exception{
//
//		boolean configSuccessfull =  true;
//
//		Thread t = new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//
//				// uncomment if configuration need to be done from .jumpstartSettins file on home directory
//				//runConf = Configuration.searchAndLoadSettings(searchPath);
//
//				//uncomment if configuration need to be done only for intellivue series via networking.
//				runConf = fetchIntellivueConfiguration(address,domainId);
//				// for testing simulated data 
//				//		runConf = fetchTestConfiguration(address);
//
//				boolean configSuccessfull = false;
//
//				if(null == runConf) {
//					configSuccessfull = false;
//				} else {
//					try {
//						Configuration.searchAndSaveSettings(runConf, searchPath);
//						Configuration.HeadlessCommand cmd = runConf.getCommand();
//						int retCode = cmd.execute(runConf);
//						System.out.println("RETURN CODE:"+retCode);
//						System.exit(retCode);
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//
//			}
//		}){
//
//		};
//
//		t.start();
//
//		return configSuccessfull;
//	}
//
//
//	/**
//	 * creates configuration for philips intellivue series 
//	 * @param address
//	 * @return
//	 */
//	private Configuration fetchIntellivueConfiguration(String address,Integer domainId) {
//		Application app = null;
//		app = Application.valueOf(MappingConstants.INTELLIVUE_APPLICATION);
//
//		DeviceDriverProvider deviceType = null;
//		deviceType = PhilipsMonitorDataAcquisition.getDeviceDriverProvider(MappingConstants.INTELLIVUE_DEVICE_TYPE);
//
//		return new Configuration(false,app, domainId, deviceType, address, "");
//	}
//
//	/**
//	 * Fetch test configuration for simulated data.
//	 * @param address
//	 * @return
//	 */
//	private Configuration fetchTestConfiguration(String address) {
//
//		return null;
//	}
//
//	static DeviceDriverProvider getDeviceDriverProvider(String alias) {
//		ServiceLoader<DeviceDriverProvider> l = ServiceLoader.load(DeviceDriverProvider.class);
//		final Iterator<DeviceDriverProvider> iter = l.iterator();
//		while (iter.hasNext()) {
//			DeviceDriverProvider ddp = iter.next();
//			if(alias.equals(ddp.getDeviceType().getAlias()))
//				return ddp;
//		}
//		throw new IllegalArgumentException("Cannot resolve '" + alias + " to a known device");
//	}
//
//	public void test(){
//		abc="str";
//		System.out.println("STR->"+abc);
//	}
//
//	public void test2(){
//		System.out.println("STR2->"+abc);
//	}
}
