import { RegisteredDevicesBoxObj } from './registeredDevicesBoxObj';
import { RegisterDeviceDropDowns } from './registerDeviceDropDowns';
export class RegisterDevice {
	registeredDevices : Array<RegisteredDevicesBoxObj>;
	dropDowns : RegisterDeviceDropDowns;
	response : string;
	board1 : string;
	board2 : string;
	box_name : string;
}
