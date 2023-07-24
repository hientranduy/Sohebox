import { Component, Input, OnInit } from '@angular/core';
import { Account } from '@app/models/account';
import { BackendService } from '@app/services/backend.service';
import { SpinnerService } from '@app/services/spinner.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';

@Component({
  templateUrl: 'delete-account-dialog.component.html',
})
export class DeleteAccountDialogComponent implements OnInit {
  @Input() title: string;
  @Input() message: string;
  @Input() btnOkText: string;
  @Input() btnCancelText: string;
  @Input() account: Account;

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

    const accountInfo =
      this.account.accountType.typeCode + '<' + this.account.accountName + '>';
    this.backendService.deleteAccount(this.account.id).subscribe(
      (data) => {
        // Send toast success
        this.toastr.success(
          'Your account ' + accountInfo + ' is successful deleted',
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
