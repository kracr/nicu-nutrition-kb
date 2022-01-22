import { GenStatus } from './genStatus';
import { KeyValueObj } from '../common-model/keyValueObj';
import { TestsList } from './testList';
export class DropDowns {
  causeOfJaundice: KeyValueObj[];
  orderInvestigation: KeyValueObj[];
  treatmentAction: KeyValueObj[];
  hours: string[];
  minutes: string[];
  riskFactor: KeyValueObj[];
  testsList: any;
}//Map<string, Array<GenStatus>>
