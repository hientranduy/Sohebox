import { User } from '@app/_common/_models';
import { EnglishType } from '@app/_common/_models';

export class EnglishUserGrade {
  id: number;
  user: User;
  vusGrade: EnglishType;
  learnDay: EnglishType;
}
