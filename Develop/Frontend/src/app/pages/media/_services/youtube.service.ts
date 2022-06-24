import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { YoutubeChannelSCO, YoutubeChannelVideoSCO } from '@app/_common/_sco';
import { environment } from '@environments/environment';
import { YoutubeChannel, YoutubeVideo } from '../_models';

@Injectable({ providedIn: 'root' })
export class YoutubeService {

    constructor(private http: HttpClient) {
    }

    // Search channel
    searchChannel(sco: YoutubeChannelSCO) {
        return this.http.post(`${environment.soheboxUrl}/api/youtubeChannel/search`, sco);
    }

    // Search my own channel
    searchMyChannel(sco: YoutubeChannelSCO) {
        return this.http.post(`${environment.soheboxUrl}/api/youtubeChannel/searchMyOwner`, sco);
    }

    // Add channel
    addChannel(item: YoutubeChannel) {
        return this.http.post(`${environment.soheboxUrl}/api/youtubeChannel`, item);
    }

    // Update channel
    updateChannel(item: YoutubeChannel) {
        return this.http.put(`${environment.soheboxUrl}/api/youtubeChannel`, item);
    }

    // Search video by channel
    searchChannelVideo(sco: YoutubeChannelVideoSCO) {
        return this.http.post(`${environment.soheboxUrl}/api/youtubeVideo/searchByChannel`, sco);
    }

    // Add private video
    addPrivateVideo(item: YoutubeVideo) {
        return this.http.post(`${environment.soheboxUrl}/api/youtubeVideo/privateVideo`, item);
    }

    // Get private video
    getPrivateVideo() {
        return this.http.get(`${environment.soheboxUrl}/api/youtubeVideo/privateVideo`);
    }

    // Delete account
    deletePrivateVideo(videoId: string) {
        return this.http.delete(`${environment.soheboxUrl}/api/youtubeVideo/privateVideo/${videoId}`);
    }
}
