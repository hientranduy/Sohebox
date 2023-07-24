import { BaseSCO } from './baseSCO';
import { SearchNumber } from './core_sco/searchNumber';
import { SearchText } from './core_sco/searchText';

export class FoodSCO extends BaseSCO {
  id: SearchNumber;
  name: SearchText;
  typeId: SearchNumber;
  categoryId: SearchNumber;
  isFastFood: Boolean;
}
