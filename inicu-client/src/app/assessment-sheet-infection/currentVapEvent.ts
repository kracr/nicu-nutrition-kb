export class CurrentVapEvent
    {
        savapid : number;
		creationtime : Date;
		modificationtime : Date;
		uhid : string;
		loggeduser : string;
		infectionSystemStatus : string;
		eventstatus : string;
		ageatonset : string;
		ageinhoursdays : boolean;
		ageatassesment : any;
		isageofassesmentinhours : boolean;
		timeofassessment : string;
		isvapavailable : string;
		treatmentaction : string;
		treatmentactionList : Array<string>;
		treatmentOther : string;
		treatmentplan : string;
		treatmentplanList : Array<string>;
		reassestime : Date;
		reassestimeType : string;
		otherplanComments : string;
		progressnotes : string;    
    }