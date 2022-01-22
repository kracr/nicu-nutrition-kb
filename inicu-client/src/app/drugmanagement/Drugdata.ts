import { Drug } from './Durgdetails';
import { Keyvalue } from './Keyvalue';
import { Message } from './Message';

export class DrugData {
  drug : Drug;
  drugsList : Array<Drug>;
  freqDropDown : Array<Keyvalue>;
  typeDropDown : Array<Keyvalue>;
  unitDropDown : Array<Keyvalue>;
  message : Message;
  brand : any;
  brandList : any;
}
