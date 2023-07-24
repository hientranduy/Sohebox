import { Component, Input, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { YoutubeVideo } from '@app/models/youtubeVideo';
import { BackendService } from '@app/services/backend.service';
import { SpinnerService } from '@app/services/spinner.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-add-youtube-video-dialog',
  templateUrl: './add-youtube-video-dialog.component.html',
  styleUrls: ['./add-youtube-video-dialog.component.css'],
})
export class AddYoutubeVideoDialogComponent implements OnInit {
  constructor(
    private activeModal: NgbActiveModal,
    private backendService: BackendService,
    private toastr: ToastrService,
    private spinner: SpinnerService,
  ) {}

  // Form value
  @Input() title: string;
  @Input() message: string;
  @Input() messageError: string;
  @Input() btnOkText: string;
  @Input() btnCancelText: string;

  // Field : channel ID
  videoIdFormControl = new FormControl('', [Validators.required]);

  validVideoId: string;

  ngOnInit() {}

  /////////////////////////////////////
  // FORM BUTTON CONTROL             //
  /////////////////////////////////////
  /**
   * Click decline button
   */
  public decline() {
    this.activeModal.close(false);
  }

  /**
   * Click dismiss button
   */
  public dismiss() {
    this.activeModal.dismiss();
  }

  /**
   * Click accept button
   */
  public accept() {
    if (this.isFormValid()) {
      if (this.isVideoValid()) {
        // Show loading
        this.spinner.show();

        const video: YoutubeVideo = new YoutubeVideo();
        video.videoId = this.validVideoId;

        // Add
        this.backendService.addPrivateVideo(video).subscribe(
          (data) => {
            // Send success toast message
            this.toastr.success(
              'New video ' +
                this.videoIdFormControl.value +
                ' is added successful',
            );

            // Hide loading
            this.spinner.hide();

            // Close dialog
            this.activeModal.close(true);
          },
          (error) => {
            // Hide loading
            this.spinner.hide();

            // Send error message to dialog
            this.message = null;
            this.messageError = error;
          },
        );
      } else {
        this.message = null;
        this.messageError = 'Not a valid youtube video or URL';
      }
    } else {
      this.message = null;
      this.messageError = 'Invalid fields, please check your input';
    }
  }

  /**
   * Validate all fields
   */
  public isFormValid() {
    let result = true;
    if (this.videoIdFormControl.status === 'INVALID') {
      result = false;
    }
    return result;
  }

  /**
   * Validate video Id or URL
   */
  public isVideoValid() {
    let result = false;
    const inputString = this.videoIdFormControl.value;
    this.validVideoId = '';

    if (
      inputString.includes('youtube.com/watch?') ||
      inputString.includes('youtu.be/') ||
      inputString.includes('youtube.com/embed/')
    ) {
      if (inputString.includes('youtube.com/watch?')) {
        this.validVideoId = inputString.split('v=')[1].split('&')[0];
      }
      if (inputString.includes('youtu.be/')) {
        this.validVideoId = inputString.split('be/')[1];
      }
      if (inputString.includes('youtube.com/embed/')) {
        this.validVideoId = inputString.split('embed/')[1];
      }

      result = true;
    } else {
      if (inputString.length >= 10 && inputString.length <= 13) {
        this.validVideoId = inputString;
        result = true;
      }
    }

    return result;
  }
}
