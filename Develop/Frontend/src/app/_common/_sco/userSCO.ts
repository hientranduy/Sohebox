import { BaseSCO } from "./baseSCO";
import { SearchNumber, SearchText } from "./core_sco";

export class UserSCO extends BaseSCO {
  id: SearchNumber;
  userName: SearchText;
  firstName: SearchText;
  lastName: SearchText;
}
