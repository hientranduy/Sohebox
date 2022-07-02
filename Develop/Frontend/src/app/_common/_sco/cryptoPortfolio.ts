import { BaseSCO } from './baseSCO';
import { SearchNumber, SearchText } from './core_sco';

export class CryptoPortfolioSCO extends BaseSCO {
    id: SearchNumber;
    user: SearchNumber;
    token: SearchNumber;
    wallet: SearchText;
    starname: SearchText;
}
