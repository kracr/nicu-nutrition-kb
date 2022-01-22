import { StopTime } from './stopTime'; 
export class PastPrescriptionListObj
    {
        babyPresid : number;
		medid : number;
		comments : string;
		creationtime : Date;
		dose : number;
		enddate : Date;
		frequency : string;
		medicationtype : string;
		medicinename : string;
		modificationtime : Date;
		route : string;
		startdate : Date;
		starttime : string;
		startTimeObject : any;
		uhid : string;
		loggeduser : string;
		isactive : boolean;
		calculateddose : string;
		dilusion : string;
		frequencyInt : number;
		medicationTypeStr : string;
		medicationFreqStr : string;
		eventid : string;
		eventname : string;
		isInEditMode : boolean;
		stopTime : StopTime;
		bolus : boolean;
		freq_type : string;
		dilution_type : string;
		dilution_volume : string;
		rate : string;
    }