import {Medicine} from './medicine';
import {FreqListMedcine} from './freqMedicine';
import { KeyValueObj } from '../common-model/keyValueObj';
export class DropDowns {
    orders: any;
    medicine: Medicine[];
    freqListMedcines: FreqListMedcine[];
    hours: string[];
    minutes: string[];
    treatmentActionSeizures: KeyValueObj[];
    causeOfSeizures: KeyValueObj[];
    treatmentActionIvh: KeyValueObj[];
    causeOfIvh: KeyValueObj[];
    treatmentActionAsphyxia: KeyValueObj[];
    causeOfAsphyxia: KeyValueObj[];
    associatedEvents: string[][];
    riskFactorAshphysia: KeyValueObj[];
}
