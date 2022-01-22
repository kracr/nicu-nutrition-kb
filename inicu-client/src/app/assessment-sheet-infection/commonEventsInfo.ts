import { PastPrescriptionListObj } from './pastPrescriptionList';
import { DowneScoreObj } from './downeScoreObj';
export class CommonEventsInfo
    {
        pastPrescriptionList : Array<PastPrescriptionListObj>;
		babyPrescriptionEmptyObj : PastPrescriptionListObj;
		medicationBabyPrescriptionList : any;
		antibioticsBabyPrescriptionList : any;
		pastInfectionHistory : any;
		downeFlag : boolean;
		downeScoreObj : DowneScoreObj;
    }