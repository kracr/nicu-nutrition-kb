import { InfectionDropdowns } from './infectionDropdowns';
import { InfectionEventObject } from './infectionEventObject';
export class InfectSystemObj
    {
        uhid : string;
        loggedUser : string;
        systemStatus : string;
        ageAtOnset : string;
        dropDowns : InfectionDropdowns;
        infectionEventObject: InfectionEventObject;    
    }