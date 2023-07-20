import { User } from "@app/user/_models";
import { EnglishType } from "@app/_common/_models";

export class EnglishUserGrade {
  id: number;
  user: User;
  vusGrade: EnglishType;
  learnDay: EnglishType;
}
