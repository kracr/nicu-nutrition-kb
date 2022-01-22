import { CaclulatorDeficitPOJO } from './caclulatorDeficitPOJO';
import { RefNutritioncalculator } from './refNutritioncalculator';

export class FeedCalculatorPOJO {
	
	currentDeficitCal : CaclulatorDeficitPOJO;
	lastDeficitCal : CaclulatorDeficitPOJO;
	deficitCalToShow : CaclulatorDeficitPOJO;
	refNutritionInfo : Array<RefNutritioncalculator>;

}