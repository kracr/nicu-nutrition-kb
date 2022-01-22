import { MedRefDropDown } from './medRefDropDown';
import { BabyPrescription } from './babyPrescription';
import { BabyfeedDetail } from '../nutrition/babyfeedDetail';
import { EnAddtivesBrandName } from './enAddtivesBrandNameList';

export class BabyPrescriptionModel {
	userId ?: string;
	dropDowns: MedRefDropDown;
	currentPrescription: BabyPrescription;
	currentPrescriptionList : BabyPrescription[];
	activePrescription: BabyPrescription[];
	pastPrescriptions: BabyPrescription[];
	babyfeedDetailList : BabyfeedDetail;
	enAddtivesBrandNameList : EnAddtivesBrandName[];
	weightForCal : any;
	constructor() {
		this.dropDowns = new MedRefDropDown();
		this.currentPrescription = new BabyPrescription();
	}
}
