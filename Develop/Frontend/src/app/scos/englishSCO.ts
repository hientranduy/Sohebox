import { BaseSCO } from './baseSCO';
import { SearchNumber } from './core_sco/searchNumber';
import { SearchText } from './core_sco/searchText';

export class EnglishSCO extends BaseSCO {
  id: SearchNumber;
  keyWord: SearchText;
  wordLevelId: SearchNumber;
  categoryId: SearchNumber;
  learnDayId: SearchNumber;
  userId: SearchNumber;
  vusGradeId: SearchNumber;
}
