import { TestObj } from './testObj';
//import { InicuList } from './inicuList';
import { KeyValueObj } from '../common-model/keyValueObj';
import { InvestigationPannelList } from './investigationPenelList';
export class InvestigationData {
	vendorLists : Array<TestObj>;
	inicuList : KeyValueObj[];
	message : string;
	investigationPannelList : Array<InvestigationPannelList>;
	type : string;

}