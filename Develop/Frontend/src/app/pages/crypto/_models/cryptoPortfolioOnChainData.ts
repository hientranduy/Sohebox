import { CryptoPortfolioValidatorAmount } from "./cryptoPortfolioOnChainDataDetail";

export class CryptoPortfolioOnChainData {
    amtAvailable: Number;
    amtTotalDelegated: Number;
    amtTotalReward: Number;
    amtTotalUnbonding: Number;
    validatorAmount: CryptoPortfolioValidatorAmount[];
}
