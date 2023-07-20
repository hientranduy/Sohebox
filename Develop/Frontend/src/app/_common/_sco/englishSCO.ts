import { BaseSCO } from "./baseSCO";
import { SearchNumber, SearchText } from "./core_sco";

export class EnglishSCO extends BaseSCO {
  id: SearchNumber;
  keyWord: SearchText;
  wordLevelId: SearchNumber;
  categoryId: SearchNumber;
  learnDayId: SearchNumber;
  userId: SearchNumber;
  vusGradeId: SearchNumber;
}
