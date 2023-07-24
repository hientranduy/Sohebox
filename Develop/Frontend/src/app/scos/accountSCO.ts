import { BaseSCO } from './baseSCO';
import { SearchNumber } from './core_sco/searchNumber';
import { SearchText } from './core_sco/searchText';

export class AccountSCO extends BaseSCO {
  id: SearchNumber;
  user: SearchNumber;
  accountType: SearchNumber;
  accountName: SearchText;
  note: SearchText;
  userName: SearchText;
  accountTypeName: SearchText;
}
