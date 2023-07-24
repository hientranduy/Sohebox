import { EnglishType } from './englishType';
import { User } from './user';

export class EnglishUserGrade {
  id: number;
  user: User;
  vusGrade: EnglishType;
  learnDay: EnglishType;
}
