import { BaseSCO } from './baseSCO';
import { SearchNumber } from './core_sco/searchNumber';

export class YoutubeChannelVideoSCO extends BaseSCO {
  channelId: SearchNumber;
  videoId: SearchNumber;
}
