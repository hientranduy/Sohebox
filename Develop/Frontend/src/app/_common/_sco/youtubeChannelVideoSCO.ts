import { BaseSCO } from './baseSCO';
import { SearchNumber } from './core_sco';

export class YoutubeChannelVideoSCO extends BaseSCO {
    channelId: SearchNumber;
    videoId: SearchNumber;
}
