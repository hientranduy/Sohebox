import { BaseSCO } from './baseSCO';
import { SearchNumber } from './core_sco/searchNumber';
import { SearchText } from './core_sco/searchText';

export class CryptoPortfolioSCO extends BaseSCO {
  id: SearchNumber;
  user: SearchNumber;
  token: SearchNumber;
  tokenCode: SearchText;
  wallet: SearchText;
  starname: SearchText;
}
