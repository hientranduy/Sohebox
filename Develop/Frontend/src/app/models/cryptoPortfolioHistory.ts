import { CryptoTokenConfig } from './cryptoTokenConfig';
import { User } from './user';

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
