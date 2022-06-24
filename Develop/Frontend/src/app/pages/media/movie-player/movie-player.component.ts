import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-movie-player',
  templateUrl: './movie-player.component.html',
  styleUrls: ['./movie-player.component.css']
})
export class MoviePlayerComponent implements OnInit {

  // Field movide URL
  movieUrlValue: String;
  movieUrlValuePlay: String = 'http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4';

  constructor(
    private toastr: ToastrService
  ) { }

  ngOnInit(): void {
    this.toastr.info('Playing URL:' + this.movieUrlValuePlay);
  }

  /**
   * Play URL when have input change
   */
  public processMovieUrl() {
    if (this.movieUrlValue != null && this.movieUrlValue !== this.movieUrlValuePlay) {
      this.movieUrlValuePlay = this.movieUrlValue;

      this.toastr.info('Playing URL:' + this.movieUrlValuePlay);
    }
  }
}
