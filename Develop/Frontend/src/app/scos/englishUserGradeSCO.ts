import { BaseSCO } from './baseSCO';
import { SearchNumber } from './core_sco/searchNumber';

export class EnglishUserGradeSCO extends BaseSCO {
  id: SearchNumber;
  userId: SearchNumber;
  vusGradeId: SearchNumber;
  learnDayId: SearchNumber;
}
