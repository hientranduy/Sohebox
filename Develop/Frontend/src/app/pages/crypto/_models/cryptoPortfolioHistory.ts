import { User } from '@app/user/_models';
import { CryptoTokenConfig } from './cryptoTokenConfig';

export class CryptoPortfolioHistory {
  id: number;
  timeStamp: Date;
  user: User;
  token: CryptoTokenConfig;
  totalAvailable: Number;
  totalDelegated: Number;
  totalReward: Number;
  totalUnbonding: Number;
  totalIncrease: Number;
  lastSyncDate: Date;
}
