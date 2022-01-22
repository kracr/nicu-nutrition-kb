import { Symptomatic } from './symptomatic';
import { BellStageObj } from './bellStageObj';

export class SepsisTempObj
    {
        symptomatic : Symptomatic;
        bellStageObj : BellStageObj;
        pastSelectedSymptomaticStr : string;
        pastSelectedRiskStr : string;
        selectedRiskStr : string;
        symptomaticEvent : string;
        symptomaticEventUrl : string;
        pastSelectedTreatmentStr : string;
        pastSelectedCauseStr : string;
        pastSelectedOrderInvestigationStr : string;

        constructor() {
		    this.symptomatic = new Symptomatic();
		    this.bellStageObj = new BellStageObj();
		}
	}
