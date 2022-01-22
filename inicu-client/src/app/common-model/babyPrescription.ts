import { StopTime } from './stopTime';

export class BabyPrescription {
        babyPresid : number;
        medid : any;
        comments : any;
        creationtime : any;
        dose : number;
        enddate : any;
        frequency : string;
        medicationtype : string;
        medicinename : string;
        modificationtime : any;
        route: string;
        startdate : any;
        starttime : string;
        startTimeObject : any;
        uhid : string;
        loggeduser : string;
        isactive : boolean;
        calculateddose : any;
        dilusion : any;
        frequencyInt : any;
        medicationTypeStr : any;
        medicationFreqStr : any;
        eventid : any;
        eventname : any;
        isInEditMode: boolean;
        stopTime : StopTime;
        bolus: boolean;
        freq_type : any;
        dilution_type : any;
        dilution_volume : any;
        rate : any;
}