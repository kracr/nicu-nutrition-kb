import {Symptomatic} from './symptomatic';
import {SeizuresType} from './seizuresType';

export class SeizuresTempObj{
  pastSelectedOrderInvestigationStr : string;
  pastSelectedCauseStr : string;
  seizuresTreatmentStatus : string;
  meridian : boolean;
  hours : any;
  symptomatic : Symptomatic;
  seizuresType : SeizuresType;
  symptomaticEvent : any;
  symptomaticEventUrl : any;
  selectedCauseStr : string;
  assessmentDate : any;
}
