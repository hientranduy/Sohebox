import { BaseSCO } from './baseSCO';
import { SearchNumber, SearchText } from './core_sco';

export class CryptoPortfolioHistorySCO extends BaseSCO {
  user: SearchNumber;
  token: SearchNumber;
}
