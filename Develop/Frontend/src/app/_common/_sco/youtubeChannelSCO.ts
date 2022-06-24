import { BaseSCO } from './baseSCO';
import { SearchNumber, SearchText } from './core_sco';

export class YoutubeChannelSCO extends BaseSCO {
    id: SearchNumber;
    channelId: SearchText;
    name: SearchText;
    description: SearchText;
    categoryId: SearchNumber;
    userId: SearchNumber;
}
