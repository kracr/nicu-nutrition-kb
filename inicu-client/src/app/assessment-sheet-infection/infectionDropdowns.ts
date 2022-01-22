//import { DropdownsOrder } from './dropdownsOrder';
import { MedicineObj } from './medicineObj';
import { FreqListMedcines } from './freqListMedcines';
import { KeyValue } from './keyValue';
import { OrderObj } from './orderObj';
export class InfectionDropdowns
    {
        orders : Map<any, Array<OrderObj>>;
		medicine : Array<MedicineObj>;
		freqListMedcines : Array<FreqListMedcines>;
		hours : Array<string>;
		minutes : Array<string>;
		treatmentActionSepsis : Array<KeyValue>;
		causeOfSepsis : Array<KeyValue>;
		associatedEvents : string[][];
		riskFactorSepsis : Array<KeyValue>;
    }