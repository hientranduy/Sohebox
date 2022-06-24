import { BaseSCO } from './baseSCO';
import { SearchNumber, SearchDate } from './core_sco';

export class EnglishLearnReportSCO extends BaseSCO {
    id: SearchNumber;
    userId: SearchNumber;
    learnedDate: SearchDate;
}
