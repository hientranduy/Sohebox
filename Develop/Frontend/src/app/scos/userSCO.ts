import { BaseSCO } from './baseSCO';
import { SearchNumber } from './core_sco/searchNumber';
import { SearchText } from './core_sco/searchText';

export class UserSCO extends BaseSCO {
  id: SearchNumber;
  userName: SearchText;
  firstName: SearchText;
  lastName: SearchText;
}
