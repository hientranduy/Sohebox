import { BaseSCO } from "@app/_common/_sco/baseSCO";
import { SearchNumber, SearchText } from "@app/_common/_sco/core_sco";

export class CryptoTokenConfigSCO extends BaseSCO {
  id: SearchNumber;
  tokenCode: SearchText;
  tokenName: SearchText;
  iconUrl: SearchText;
  nodeUrl: SearchText;
  denom: SearchText;
  addressPrefix: SearchText;
}
