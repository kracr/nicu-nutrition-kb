import { KeyValueObj } from './keyValueObj';

export class FeedDropDown {

	feedMethod : Array<KeyValueObj>;
	feedType : Array<KeyValueObj>;
	feedBo : Array<KeyValueObj>;
	pupilReact : Array<KeyValueObj>;
	hours : Array<string>;
	minutes : Array<string>;
	seconds : Array<string>;
	catheterLine : Array<KeyValueObj>;
	ventilatorMode : Array<KeyValueObj>;
	frequencyMed : Array<KeyValueObj>;
	libBreastFeeding : Array<KeyValueObj>;
	bolusMethod : Array<KeyValueObj>;
	bolusType : Array<KeyValueObj>;
	medicineFrequency : Map<string, Array<string>>;
	feedTypeSecondary : any;
	fluidType : Array<KeyValueObj>;

}
