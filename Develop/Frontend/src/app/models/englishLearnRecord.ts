import { English } from './english';
import { User } from './user';

export class EnglishLearnRecord {
  id: number;
  user: User;
  english: English;
  recordTimes: number;
  note: string;
  learnedToday: string;
}
