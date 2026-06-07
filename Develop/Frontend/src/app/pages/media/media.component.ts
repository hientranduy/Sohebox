import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UtilsService } from '@app/services/utils.service';

@Component({
  selector: 'app-media',
  templateUrl: './media.component.html',
})
export class MediaComponent implements OnInit {
  selectOpion = 1;

  /**
   *  Constructor
   */
  constructor(
    private route: ActivatedRoute,
    public utilsService: UtilsService,
  ) { }

  ngOnInit() { }

  ///////////////////
  // SELECT OPTION //
  ///////////////////
  public selectYoutubeChannel() {
    this.selectOpion = 1;
  }
  get isYoutubeChannelOpen() {
    return this.selectOpion === 1;
  }
}
