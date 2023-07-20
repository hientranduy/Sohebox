import { User } from "@app/user/_models";
import { CryptoTokenConfig } from "./cryptoTokenConfig";
import { CryptoValidator } from "./cryptoValidator";

export class CryptoPortfolio {
  id: number;
  updatedDate: Date;
  user: User;
  token: CryptoTokenConfig;
  wallet: string;
  starname: string;
  amtAvailable: Number;
  amtTotalDelegated: Number;
  amtTotalReward: Number;
  amtTotalUnbonding: Number;
  validator: CryptoValidator;
}
