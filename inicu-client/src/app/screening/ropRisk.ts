export class RopRisk {

	gestation : boolean;
	oxygen : boolean;
	mv : boolean;
	sepsis : boolean;
	blood : boolean;
	poor_weight : boolean;
	othersType : boolean;
	othersValue : string;

	constructor() {
		this.gestation = false;
		this.oxygen = false;
		this.mv = false;
		this.sepsis = false;
		this.blood = false;
		this.poor_weight = false;
		this.othersType = false;
		this.othersValue = "";
	}
}