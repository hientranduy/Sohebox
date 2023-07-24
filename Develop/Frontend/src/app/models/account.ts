import { Type } from './type';
import { User } from './user';

export class Account {
  id: number;
  user: User;
  accountType: Type;
  accountName: string;
  mdp: string;
  note: string;
}
