import { User } from '@app/user/_models';
import { English } from './english';

export class EnglishLearnRecord {
  id: number;
  user: User;
  english: English;
  recordTimes: number;
  note: string;
  learnedToday: string;
}
