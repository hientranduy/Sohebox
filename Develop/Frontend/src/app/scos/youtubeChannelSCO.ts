import { BaseSCO } from './baseSCO';
import { SearchNumber } from './core_sco/searchNumber';
import { SearchText } from './core_sco/searchText';

export class YoutubeChannelSCO extends BaseSCO {
  id: SearchNumber;
  channelId: SearchText;
  name: SearchText;
  description: SearchText;
  categoryId: SearchNumber;
  userId: SearchNumber;
}
