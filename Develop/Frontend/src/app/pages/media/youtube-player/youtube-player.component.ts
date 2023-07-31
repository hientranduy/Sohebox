import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { AppSettings } from '@app/app.settings';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-youtube-player',
  templateUrl: './youtube-player.component.html',
})
export class YoutubePlayerComponent implements OnInit {
  videoId: String;
  videoHeight;
  videoWidth;
  player;

  constructor(
    private route: ActivatedRoute,
    private toastr: ToastrService,
  ) {
    // Get video id from parameter
    this.route.params.subscribe(
      (params: Params) => (this.videoId = params['videoId']),
    );

    this.videoWidth = window.innerWidth;
    this.videoHeight = window.innerHeight;
  }

  ngOnInit(): void {
    // Initial video
    const tag = document.createElement('script');
    tag.src = AppSettings.GOOGLE_YOUTUBE_IFRAME;
    document.body.appendChild(tag);
  }

  /**
   * https://developers.google.com/youtube/iframe_api_reference#onStateChange
   *
   * -1 (unstarted)
   * 0 (ended)
   * 1 (playing)
   * 2 (paused)
   * 3 (buffering)
   * 5 (video cued).
   */
  public onStateChangeEvent(event) {
    // Close the windown when video is ended
    if (event.data === 0) {
      window.close();
    }
  }

  /**
   * Auto play when ready
   *
   */
  public onStateReady(event) {
    // Get player
    this.player = event.target;

    // Play video auto
    this.player.playVideo();
  }

  /**
   * https://developers.google.com/youtube/iframe_api_reference#onError
   *
   */
  public onErrorEvent(event) {
    this.toastr.error('[Youtube Player] Error:' + event.data);
  }
}
