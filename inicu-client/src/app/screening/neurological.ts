export class Neurological {

	postureOpisthotonous : boolean;
	postureArmFlexed : boolean;
	postureLegExtended : boolean;

	headControlRemainVertical : boolean;
	headControlWobbles : boolean;

	headFlexorRemainVertical : boolean;
	headFlexorWobbles : boolean;

	backCurved : boolean;
	handStraight : boolean;
	backStraight : boolean;

	cramped : boolean;
	athetoid : boolean;
	otherAbnormalMovement : boolean;

	tremulousAlways : boolean;
	manyStartles : boolean;

	noResponse : boolean;
	fullAbduction : boolean;
	extensionElbows : boolean;
	noAdduction : boolean;
	minimalAdduction : boolean;
	difficultElicit : boolean;

	brightens : boolean;
	stimulus : boolean;

	alertnessSustained: boolean;
	alertState : boolean;

	constructor() {
		this.postureOpisthotonous = false;
		this.postureArmFlexed = false;
		this.postureLegExtended = false;

		this.headControlRemainVertical = false;
		this.headControlWobbles = false;

		this.headFlexorRemainVertical = false;
		this.headFlexorWobbles = false;

		this.backCurved = false;
		this.handStraight = false;
		this.backStraight = false;

		this.cramped = false;
		this.athetoid = false;
		this.otherAbnormalMovement = false;

		this.tremulousAlways = false;
		this.manyStartles = false;

		this.noResponse = false;
		this.fullAbduction = false;
		this.extensionElbows = false;
		this.noAdduction = false;
		this.minimalAdduction = false;
		this.difficultElicit = false;

		this.brightens = false;
		this.stimulus = false;

		this.alertnessSustained = false;
		this.alertState = false;

	}
}