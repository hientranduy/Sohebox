import { User } from '@app/user/_models';
import { CryptoPortfolioOnChainData } from './cryptoPortfolioOnChainData';
import { CryptoTokenConfig } from './cryptoTokenConfig';

export class CryptoPortfolio {
    id: number;
    user: User;
    token: CryptoTokenConfig;
    wallet: string;
    starname: string;
    onChainData: CryptoPortfolioOnChainData;
}
