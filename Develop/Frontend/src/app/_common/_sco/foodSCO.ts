import { BaseSCO } from './baseSCO';
import { SearchNumber, SearchText } from './core_sco';

export class FoodSCO extends BaseSCO {
  id: SearchNumber;
  name: SearchText;
  typeId: SearchNumber;
  categoryId: SearchNumber;
  isFastFood: Boolean;
}
