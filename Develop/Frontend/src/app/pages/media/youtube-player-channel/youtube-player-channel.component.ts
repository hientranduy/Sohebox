import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { AppSettings } from '@app/appSettings';
import { User } from '@app/user/_models';
import { AuthenticationService } from '@app/user/_service';
import { ApiReponse } from '@app/_common/_models';
import { YoutubeChannelVideoSCO } from '@app/_common/_sco';
import { SearchNumber } from '@app/_common/_sco/core_sco';
import { SEOService, SpinnerService } from '@app/_common/_services';
import { ToastrService } from 'ngx-toastr';
import { YoutubeVideo } from '../_models';
import { YoutubeService } from '../_services';
import { AlertService } from '@app/_common/alert/alert.service';

@Component({
  selector: 'app-youtube-player-channel',
  templateUrl: './youtube-player-channel.component.html',
  styleUrls: ['./youtube-player-channel.component.css'],
})
export class YoutubePlayerChannelComponent implements OnInit {
  currentUser: User;
  channelId: number;
  videoId: String;
  videoHeight;
  videoWidth;
  player;

  // Video list
  videos: Array<YoutubeVideo>;

  constructor(
    private seoService: SEOService,
    private spinner: SpinnerService,
    private route: ActivatedRoute,
    private alertService: AlertService,
    private youtubeService: YoutubeService,
    private authenticationService: AuthenticationService,
    private toastr: ToastrService,
  ) {
    // Get logged user
    this.authenticationService.currentUser.subscribe(
      (x) => (this.currentUser = x),
    );

    // Get channel id from parameter
    this.route.params.subscribe(
      (params: Params) => (this.channelId = params['channelId']),
    );

    this.videoWidth = window.innerWidth;
    this.videoHeight = window.innerHeight;
  }

  ngOnInit(): void {
    // CEO
    this.seoService.updateCEO(this.route);

    // Load video list - must use ==
    if (this.currentUser && this.channelId == 0) {
      this.getPrivateVideos();
    } else {
      this.loadVideoList();
    }

    // Initial video
    const tag = document.createElement('script');
    tag.src = AppSettings.GOOGLE_YOUTUBE_IFRAME;
    document.body.appendChild(tag);
  }

  /**
   * Load video list
   */
  public loadVideoList() {
    // Prepare search condition
    const channelSearch = new SearchNumber();
    channelSearch.eq = this.channelId;

    const sco = new YoutubeChannelVideoSCO();
    sco.channelId = channelSearch;
    sco.maxRecordPerPage = 20;

    // Show loading
    this.spinner.show();

    // Get list
    this.youtubeService.searchChannelVideo(sco).subscribe(
      (data) => {
        // Get data
        const responseAPi: any = data;
        const typeResponse: ApiReponse<YoutubeVideo> = responseAPi;
        if (typeResponse.data != null) {
          this.videos = typeResponse.data.elements;

          // Set first video
          this.videoId = this.getRandomVideoId();
        } else {
          this.videos = new Array<YoutubeVideo>();
        }

        // Hide loading
        this.spinner.hide();
      },
      (error) => {
        this.processError(error);
      },
    );
  }

  /**
   * Load private video
   */
  public getPrivateVideos() {
    // Show loading
    this.spinner.show();

    // Get list
    this.youtubeService.getPrivateVideo().subscribe(
      (data) => {
        // Get data
        const responseAPi: any = data;
        const typeResponse: ApiReponse<YoutubeVideo> = responseAPi;
        if (typeResponse.data != null) {
          this.videos = typeResponse.data.elements;

          // Set first video
          this.videoId = this.getRandomVideoId();
        } else {
          this.videos = new Array<YoutubeVideo>();
        }

        // Hide loading
        this.spinner.hide();
      },
      (error) => {
        this.processError(error);
      },
    );
  }

  public getRandomVideoId() {
    let result = null;

    if (this.videos.length > 0) {
      const itemRandom =
        this.videos[Math.floor(Math.random() * this.videos.length)];
      result = itemRandom.videoId;

      // Remove item
      const index: number = this.videos.indexOf(itemRandom);
      if (index !== -1) {
        this.videos.splice(index, 1);
      }
    }
    return result;
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
    // Play other video until end
    if (event.data === 0) {
      const nextVideoId = this.getRandomVideoId();
      if (nextVideoId) {
        this.player.loadVideoById(nextVideoId);
      } else {
        window.close();
      }
    }
  }

  /**
   * Auto play when ready
   *
   */
  public onStateReady(event) {
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

  /**
   * Process error in case have error call API
   */
  public processError(error: any) {
    // Hide loading
    this.spinner.hide();

    this.alertService.error(error);
  }
}
