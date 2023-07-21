import { MediaType } from '@app/_common/_models';

export class YoutubeChannel {
  id: number;
  channelId: string;
  name: string;
  description: string;
  category: MediaType;
}
