import { BaseSCO } from './baseSCO';
import { SearchText } from './core_sco';

export class FoodTypeSCO extends BaseSCO {
  typeClass: SearchText;
  typeCode: SearchText;
  typeName: SearchText;
  description: SearchText;
}
