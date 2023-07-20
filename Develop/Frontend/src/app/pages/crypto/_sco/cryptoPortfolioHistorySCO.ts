import { BaseSCO } from "../../../_common/_sco/baseSCO";
import { SearchNumber, SearchText } from "../../../_common/_sco/core_sco";

export class CryptoPortfolioHistorySCO extends BaseSCO {
  user: SearchNumber;
  token: SearchNumber;
}
