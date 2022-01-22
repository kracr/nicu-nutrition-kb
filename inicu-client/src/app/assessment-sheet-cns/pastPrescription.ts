import {StopTime} from './stopTime';
import {Prescription} from './prescription';

export class PastPrescriptionList {
  babyPresid: number;
  medid: string;
  comments: string;
  creationtime: any;
  dose: number;
  enddate?: any;
  frequency: string;
  medicationtype: string;
  medicinename: string;
  modificationtime: any;
  route: string;
  startdate: any;
  starttime: string;
  startTimeObject?: Prescription;
  uhid: string;
  loggeduser: string;
  isactive: boolean;
  calculateddose: string;
  dilusion?: any;
  frequencyInt?: any;
  medicationTypeStr?: any;
  medicationFreqStr?: any;
  eventid: string;
  eventname: string;
  isInEditMode: boolean;
  stopTime: StopTime;
  bolus: boolean;
  freq_type: string;
  dilution_type: string;
  dilution_volume?: number;
  rate?: number;
}
