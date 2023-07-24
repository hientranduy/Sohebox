import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UtilsService } from '@app/services/utils.service';

@Component({
  selector: 'app-media',
  templateUrl: './media.component.html',
  styleUrls: ['./media.component.css'],
})
export class MediaComponent implements OnInit {
  selectOpion = 1;

  /**
   *  Constructor
   */
  constructor(
    private route: ActivatedRoute,
    public utilsService: UtilsService,
  ) {}

  ngOnInit() {}

  ///////////////////
  // SELECT OPTION //
  ///////////////////
  public selectYoutubeChannel() {
    this.selectOpion = 1;
  }
  get isYoutubeChannelOpen() {
    return this.selectOpion === 1;
  }

  public selectMediaPlayer() {
    this.selectOpion = 2;
  }
  get isMediaPlayerOpen() {
    return this.selectOpion === 2;
  }

  public selectPhimMoiPlayer() {
    this.selectOpion = 3;
  }
  get isPhimMoiPlayerOpen() {
    return this.selectOpion === 3;
  }
}
