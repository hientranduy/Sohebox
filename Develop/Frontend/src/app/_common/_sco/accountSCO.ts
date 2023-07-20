import { BaseSCO } from "./baseSCO";
import { SearchNumber, SearchText } from "./core_sco";

export class AccountSCO extends BaseSCO {
  id: SearchNumber;
  user: SearchNumber;
  accountType: SearchNumber;
  accountName: SearchText;
  note: SearchText;
  userName: SearchText;
  accountTypeName: SearchText;
}
