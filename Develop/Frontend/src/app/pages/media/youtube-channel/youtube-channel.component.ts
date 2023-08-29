import { Component, HostListener, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';

import { AlertService } from '@app/commons/alert/alert.service';
import { ApiReponse } from '@app/models/response/apiReponse';
import { User } from '@app/models/user';
import { YoutubeChannel } from '@app/models/youtubeChannel';
import { YoutubeVideo } from '@app/models/youtubeVideo';
import { SearchNumber } from '@app/scos/core_sco/searchNumber';
import { YoutubeChannelSCO } from '@app/scos/youtubeChannelSCO';
import { YoutubeChannelVideoSCO } from '@app/scos/youtubeChannelVideoSCO';
import { AuthenticationService } from '@app/services/authentication.service';
import { BackendService } from '@app/services/backend.service';
import { DialogService } from '@app/services/dialog.service';
import { RequireMatchForm } from '@app/services/requireMatchForm';
import { SpinnerService } from '@app/services/spinner.service';
import { UtilsService } from '@app/services/utils.service';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';

@Component({
  selector: 'app-youtube-channel',
  templateUrl: './youtube-channel.component.html',
  styleUrls: ['./youtube-channel.component.css'],
})
export class YoutubeChannelComponent implements OnInit {
  currentUser: User;

  /**
   * Constructor
   */
  constructor(
    private spinner: SpinnerService,
    private backendService: BackendService,
    private utilsService: UtilsService,
    private alertService: AlertService,
    private toastr: ToastrService,
    private authenticationService: AuthenticationService,
    private dialogService: DialogService,
  ) {
    // Get logged user
    this.authenticationService.currentUser.subscribe(
      (x) => (this.currentUser = x),
    );
  }
  // Loading
  isPrivateChannel: Boolean = false;
  isHavePrivateVideo: Boolean = false;

  // Video list
  videos: Array<YoutubeVideo>;

  // Field Channel
  channelSelect: YoutubeChannel;
  channelFormControl = new FormControl('', [RequireMatchForm]);
  filteredChannels: Observable<YoutubeChannel[]>;

  // Auto resize card styles
  videoCardWidth: String;
  @HostListener('window:resize', ['$event'])
  onResize(event) {
    this.videoCardWidth = this.utilsService.getAdjustCardWidth() + 'px';
  }
  @HostListener('window:orientationchange', ['$event'])
  onOrientationChange(event) {
    this.videoCardWidth = this.utilsService.getAdjustCardWidth() + 'px';
  }

  public displayChannel(channel: YoutubeChannel) {
    if (channel) {
      return channel.name;
    }
    return null;
  }

  ngOnInit(): void {
    // Get channel list
    this.getChannelList();

    // Get private video list
    if (this.currentUser) {
      this.getPrivateVideo();
    }

    // Set card styles
    this.videoCardWidth = this.utilsService.getAdjustCardWidth() + 'px';
  }

  ////////////////////
  // Button control //
  ////////////////////
  /**
   * Refresh movie list
   */
  public searchMovie() {
    if (
      this.isFormValid() &&
      this.channelSelect &&
      this.channelSelect.channelId
    ) {
      if (
        !this.currentUser ||
        this.channelSelect.channelId !== this.currentUser.username
      ) {
        this.loadVideoListByChannel(this.channelSelect);
      } else {
        this.getPrivateVideo();
      }
    } else {
      this.toastr.info('Let select a channel');
    }
  }

  /**
   * Search movide by channel id
   */
  public searchMovieByChannel(selectedChannel) {
    if (
      !this.currentUser ||
      selectedChannel.channelId !== this.currentUser.username
    ) {
      this.loadVideoListByChannel(selectedChannel);
    } else {
      this.getPrivateVideo();
    }
  }

  /**
   * Play channel
   */
  public playMovieButton() {
    if (this.currentUser) {
      if (
        this.channelSelect &&
        this.channelSelect.channelId &&
        this.channelSelect.channelId !== this.currentUser.username
      ) {
        window.open('/media/youtubeplayerchannel/' + this.channelSelect.id);
      } else {
        if (this.isHavePrivateVideo) {
          window.open('/media/youtubeplayerchannel/' + 0);
        } else {
          this.toastr.info(
            'Let login and add personal videos or select a channel to play',
          );
        }
      }
    } else {
      if (this.channelSelect && this.channelSelect.channelId) {
        window.open('/media/youtubeplayerchannel/' + this.channelSelect.id);
      } else {
        this.toastr.info(
          'Let login and add personal videos or select a channel to play',
        );
      }
    }
  }

  /**
   * Open youtube player
   */
  onClickVideo(video: YoutubeVideo) {
    window.open('/media/youtubeplayer/' + video.videoId);
  }

  /**
   * Remove video form list
   */
  onClickDelete(video: YoutubeVideo) {
    this.dialogService
      .deletePrivateVideo(
        'DELETION',
        'Are you sure remove video: ' +
          video.title +
          ' _ id:' +
          video.videoId +
          ' ?',
        video,
      )
      .then(
        (result) => {
          this.getPrivateVideo();
        },
        (reason) => {
          console.log('DELETE reason:' + reason);
        },
      );
  }

  /**
   * Add private video
   */
  public addPrivateVideo() {
    this.dialogService.addPrivateVideo('Add private video', '').then(
      (result) => {
        if (result) {
          this.getChannelList();
          this.getPrivateVideo();
        }
      },
      (reason) => {
        console.log('ADD video reason:' + reason);
      },
    );
  }

  ///////////////////////
  // API call          //
  ///////////////////////
  /**
   * Load channel list
   */
  public getChannelList() {
    // Prepare search condition
    const sco = new YoutubeChannelSCO();
    sco.deleteFlag = false;

    // Show loading
    this.spinner.show();

    // Get list
    this.backendService.searchMyChannel(sco).subscribe({
      next: async (res) => {
        // Get data
        const responseAPi: any = res;
        const typeResponse: ApiReponse<YoutubeChannel> = responseAPi;
        if (typeResponse.data != null) {
          const channels: YoutubeChannel[] = typeResponse.data.elements;
          this.filteredChannels = this.channelFormControl.valueChanges.pipe(
            startWith(''),
            map((value) =>
              channels.filter((valueFilter) =>
                valueFilter.name
                  .toLowerCase()
                  .includes(value.toString().toLowerCase()),
              ),
            ),
          );
        }

        // Hide loading
        this.spinner.hide();
      },
      error: (err) => {
        this.processError(err);
      },
    });
  }

  /**
   * Load video list
   */
  public loadVideoListByChannel(channel: YoutubeChannel) {
    // Prepare search condition
    const channelSearch = new SearchNumber();
    channelSearch.eq = channel.id;

    const sco = new YoutubeChannelVideoSCO();
    sco.channelId = channelSearch;
    sco.maxRecordPerPage = 20;

    // Show loading
    // this.spinner.show();

    // Get list
    this.backendService.searchChannelVideo(sco).subscribe({
      next: async (res) => {
        // Get data
        const responseAPi: any = res;
        const typeResponse: ApiReponse<YoutubeVideo> = responseAPi;
        if (typeResponse.data != null) {
          this.videos = typeResponse.data.elements;
        } else {
          this.videos = new Array<YoutubeVideo>();
        }

        if (channel.channelId === this.currentUser.username) {
          this.isPrivateChannel = true;
        } else {
          this.isPrivateChannel = false;
        }

        // Hide loading
        this.spinner.hide();
      },
      error: (err) => {
        this.processError(err);
      },
    });
  }

  /**
   * Load private video
   */
  public getPrivateVideo() {
    // Show loading
    this.spinner.show();

    // Get list
    this.backendService.getPrivateVideo().subscribe({
      next: async (res) => {
        // Get data
        const responseAPi: any = res;
        const typeResponse: ApiReponse<YoutubeVideo> = responseAPi;
        if (typeResponse.data != null) {
          this.videos = typeResponse.data.elements;
          this.isHavePrivateVideo = true;
        } else {
          this.videos = new Array<YoutubeVideo>();
          this.isHavePrivateVideo = false;
        }

        // Set private channel flag = true
        this.isPrivateChannel = true;

        // Hide loading
        this.spinner.hide();
      },
      error: (err) => {
        this.processError(err);
      },
    });
  }

  ///////////////////
  // GENERAL CHECK //
  ///////////////////
  /**
   * Validate form
   */
  public isFormValid() {
    let result = true;
    if (this.channelFormControl.status === 'INVALID') {
      result = false;
      this.channelFormControl.setValue('');
    }
    return result;
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
