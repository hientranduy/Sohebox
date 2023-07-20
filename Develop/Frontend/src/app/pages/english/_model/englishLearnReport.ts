import { User } from "@app/user/_models";
import { English } from "./english";

export class EnglishLearnReport {
  id: number;
  user: User;
  learnedDate: Date;
  learnedTotal: number;
}
