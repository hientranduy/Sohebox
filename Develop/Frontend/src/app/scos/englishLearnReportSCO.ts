import { BaseSCO } from './baseSCO';
import { SearchDate } from './core_sco/searchDate';
import { SearchNumber } from './core_sco/searchNumber';

export class EnglishLearnReportSCO extends BaseSCO {
  id: SearchNumber;
  userId: SearchNumber;
  learnedDate: SearchDate;
}
