export class HearingRisk {

	family_history : boolean;
	nicu_stay : boolean;
	ototoxic : boolean;
	hyperbilirubinemia : boolean;
	infection : boolean;
	craniofacial : boolean;
	forelock : boolean;
	sepsis : boolean;
	meningitis : boolean;
	herpes : boolean;
	varicella : boolean;
	othersType : boolean;
	othersValue : string;

	constructor() {
		this.family_history = false;
		this.nicu_stay = false;
		this.ototoxic = false;
		this.hyperbilirubinemia = false;
		this.infection = false;
		this.craniofacial = false;
		this.forelock = false;
		this.sepsis = false;
		this.meningitis = false;
		this.herpes = false;
		this.varicella = false;
		this.othersType = false;
		this.othersValue = "";
	}
}