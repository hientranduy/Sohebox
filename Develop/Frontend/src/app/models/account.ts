import { Type } from './type';
import { User } from './user';

export class Account {
  id: number;
  user: User;
  type: Type;
  accountName: string;
  note: string;

  mdpPlain: string;
}
