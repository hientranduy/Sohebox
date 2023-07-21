import { User } from '@app/_common/_models';

export class EnglishLearnReport {
  id: number;
  user: User;
  learnedDate: Date;
  learnedTotal: number;
}
