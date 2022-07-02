import { BaseSCO } from '../../../_common/_sco/baseSCO';
import { SearchNumber, SearchText } from '../../../_common/_sco/core_sco';

export class CryptoPortfolioSCO extends BaseSCO {
    id: SearchNumber;
    user: SearchNumber;
    token: SearchNumber;
    wallet: SearchText;
    starname: SearchText;
}
