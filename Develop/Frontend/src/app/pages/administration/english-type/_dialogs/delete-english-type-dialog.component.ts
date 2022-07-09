import { Component, Input, OnInit } from '@angular/core';
import { SpinnerService } from '@app/_common/_services';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-delete-english-type-dialog',
  templateUrl: './delete-english-type-dialog.component.html',
  styleUrls: ['./delete-english-type-dialog.component.css']
})

export class DeleteEnglishTypeDialogComponent implements OnInit {

  @Input() title: string;
  @Input() message: string;
  @Input() btnOkText: string;
  @Input() btnCancelText: string;

  constructor(
    private activeModal: NgbActiveModal,
    private spinner: SpinnerService
  ) { }

  ngOnInit() {
  }

  public decline() {
    // Return false
    this.activeModal.close(false);
  }

  public accept() {
    // Return true
    this.activeModal.close(true);
  }

  public dismiss() {
    // Return false
    this.activeModal.dismiss();
  }
}
