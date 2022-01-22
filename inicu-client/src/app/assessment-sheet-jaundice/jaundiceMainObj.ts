import {Response} from './response';
import {DropDowns} from './dropDowns';
import {ListJaundice} from './jaundiceList';
import {JaundicePhototherapyGraph} from './jaundicePhototherapyGraph';
import {JaundiceGraphData} from './jaundiceGraphData';
import {Jaundice} from './jaundiceObj';

export class RootObject {
  response: Response;
  dropDowns: DropDowns;
  jaundice: Jaundice;
  listJaundice: ListJaundice[];
  userId: string;
  maxTcB: number;
  jaundicePhototherapyGraph: JaundicePhototherapyGraph;
  jaundiceGraphData: JaundiceGraphData;
  associatedEvents: string[][];
  orderSelectedText: string;
  motherBloodGroup : string;
  prescriptionList : any[];
}
