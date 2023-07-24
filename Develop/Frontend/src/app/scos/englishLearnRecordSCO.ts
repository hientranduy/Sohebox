import { BaseSCO } from './baseSCO';
import { SearchDate } from './core_sco/searchDate';
import { SearchNumber } from './core_sco/searchNumber';

export class EnglishLearnRecordSCO extends BaseSCO {
  id: SearchNumber;
  userId: SearchNumber;
  englishId: SearchNumber;
  recordTimes: SearchNumber;
  updatedDate: SearchDate;
}
