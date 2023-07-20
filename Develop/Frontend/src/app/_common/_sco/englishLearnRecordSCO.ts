import { BaseSCO } from './baseSCO';
import { SearchNumber, SearchDate } from './core_sco';

export class EnglishLearnRecordSCO extends BaseSCO {
  id: SearchNumber;
  userId: SearchNumber;
  englishId: SearchNumber;
  recordTimes: SearchNumber;
  updatedDate: SearchDate;
}
