import { Component, Input, OnInit } from '@angular/core';
import { YoutubeVideo } from '@app/models/youtubeVideo';
import { BackendService } from '@app/services/backend.service';
import { SpinnerService } from '@app/services/spinner.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';

@Component({
  templateUrl: 'delete-youtube-video-dialog.component.html',
})
export class DeleteYoutubeVideoDialogComponent implements OnInit {
  @Input() title: string;
  @Input() message: string;
  @Input() btnOkText: string;
  @Input() btnCancelText: string;
  @Input() video: YoutubeVideo;

  constructor(
    private backendService: BackendService,
    private toastr: ToastrService,
    private activeModal: NgbActiveModal,
    private spinner: SpinnerService,
  ) {}

  ngOnInit() {}

  public decline() {
    // Return false
    this.activeModal.close(false);
  }

  public accept() {
    // Show loading
    this.spinner.show();

    this.backendService.deletePrivateVideo(this.video.videoId).subscribe(
      (data) => {
        // Send toast success
        this.toastr.success(
          'Your video ' + this.video.videoId + ' is successful deleted',
        );

        // Hide loading
        this.spinner.hide();

        // Return true
        this.activeModal.close(true);
      },
      (error) => {
        // Hide loading
        this.spinner.hide();

        // Send error toast message
        this.toastr.error(error);

        // Return false
        this.activeModal.close(false);
      },
    );
  }

  public dismiss() {
    // Return false
    this.activeModal.dismiss();
  }
}
