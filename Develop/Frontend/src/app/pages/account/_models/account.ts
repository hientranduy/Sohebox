import { User } from '@app/_common/_models';
import { Type } from '@app/_common/_models';

export class Account {
  id: number;
  user: User;
  accountType: Type;
  accountName: string;
  mdp: string;
  note: string;
}
