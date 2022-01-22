import { IntakeCalcDetails } from './intakeCalcDetails';

export class CaclulatorDeficitPOJO {
	
	eshphaganIntake : IntakeCalcDetails;
	eshphaganParenteralIntake : IntakeCalcDetails;
	enteralIntake : IntakeCalcDetails;
	parentalIntake : IntakeCalcDetails;
	parenteralIntake ?: IntakeCalcDetails;
	total : any;
	totalPerKg : any;
	deficit : any;

	constructor() {
		this.eshphaganIntake = new IntakeCalcDetails();
		this.eshphaganParenteralIntake = new IntakeCalcDetails();
		this.enteralIntake = new IntakeCalcDetails();
		this.parentalIntake = new IntakeCalcDetails();
		this.parenteralIntake = new IntakeCalcDetails();
	}
}