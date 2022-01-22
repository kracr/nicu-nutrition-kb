import { Keyvalue } from '../userpanel/Keyvalue';
import { Medicine } from '../common-model/medicine';
import { VentMode } from './ventMode';
import { MedFrequency } from './medFrequency';
export class DropDowns{
  testsList: any;
  treatmentAction: Keyvalue[];
  medicine: Medicine[];
  allMedicine: Medicine[];
  causeOfRds: Keyvalue[];
  causeOfPphn: Keyvalue[];
  causeOfApnea: Keyvalue[];
  ventMode: VentMode[];
  medicineFrequency: MedFrequency[];
  respiratoryPlan: Keyvalue[];
  apneaPlan: Keyvalue[];
  hours: any;
  orderInvestigation:  Keyvalue[];
  causeOfPneumo:  Keyvalue[];
  associatedEvents: any;
  riskFactorRds:  Keyvalue[];
}
