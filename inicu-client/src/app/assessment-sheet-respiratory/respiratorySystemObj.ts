import { RespSystemObject } from './respSystemObject'
import { DropDowns } from './dropDowns';
export class RespiratorySystemObj{
  uhid: string;
  loggedUser: string;
  systemStatus: string;
  ageAtOnset: any;
  gestation: any;
  workingWeight: any;
  respSupportDuration: string;
  lfDuration: string;
  hfDuration: string;
  cpapDuration: string;
  mvDuration: string;
  hfoDuration: string;
  dropDowns: DropDowns;
  respSystemObject: RespSystemObject;
}
