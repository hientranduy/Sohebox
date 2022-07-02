import { BaseSCO } from './baseSCO';
import { SearchNumber, SearchText } from './core_sco';

export class CryptoTokenSCO extends BaseSCO {
    id: SearchNumber;
    tokenCode: SearchText;
    tokenName: SearchText;
    iconUrl: SearchText;
    nodeUrl: SearchText;
    denom: SearchText;
    addressPrefix: SearchText;
}
