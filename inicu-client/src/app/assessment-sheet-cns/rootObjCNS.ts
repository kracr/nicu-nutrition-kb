import {DropDowns} from './dropdowns';
import {CnseventObject} from './cnsEventObj';
export class RootObject {
  uhid?: any;
  loggedUser?: any;
  systemStatus?: any;
  ageAtOnset: string;
  dropDowns: DropDowns;
  workingWeight: string;
  cnseventObject: CnseventObject;
  orderSelectedText : string;
}
