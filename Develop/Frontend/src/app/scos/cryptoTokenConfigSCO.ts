import { BaseSCO } from '@app/scos/baseSCO';
import { SearchNumber } from './core_sco/searchNumber';
import { SearchText } from './core_sco/searchText';

export class CryptoTokenConfigSCO extends BaseSCO {
  id: SearchNumber;
  tokenCode: SearchText;
  tokenName: SearchText;
  iconUrl: SearchText;
  nodeUrl: SearchText;
  denom: SearchText;
  addressPrefix: SearchText;
}
