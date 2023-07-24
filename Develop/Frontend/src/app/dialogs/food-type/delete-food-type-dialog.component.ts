import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-delete-food-type-dialog',
  templateUrl: './delete-food-type-dialog.component.html',
  styleUrls: ['./delete-food-type-dialog.component.css'],
})
export class DeleteFoodTypeDialogComponent implements OnInit {
  @Input() title: string;
  @Input() message: string;
  @Input() btnOkText: string;
  @Input() btnCancelText: string;

  constructor(private activeModal: NgbActiveModal) {}

  ngOnInit() {}

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
