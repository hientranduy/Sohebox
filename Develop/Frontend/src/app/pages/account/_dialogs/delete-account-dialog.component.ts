import { Component, Input, OnInit } from '@angular/core';
import { SpinnerService } from '@app/_common/_services';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { Account } from '../_models';
import { AccountService } from '../_services';

@Component({
  templateUrl: 'delete-account-dialog.component.html',
})
export class DeleteAccountDialogComponent implements OnInit {

  @Input() title: string;
  @Input() message: string;
  @Input() btnOkText: string;
  @Input() btnCancelText: string;
  @Input() account: Account;

  isLoading: Boolean;

  constructor(
    private accountService: AccountService,
    private toastr: ToastrService,
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

    // Show loading
    this.isLoading = true;
    this.spinner.show();

    const accountInfo = this.account.accountType.typeCode + '<' + this.account.accountName + '>';
    this.accountService.deleteAccount(this.account.id)
      .subscribe(
        data => {
          // Send toast success
          this.toastr.success('Your account ' + accountInfo + ' is successful deleted');

          // Hide loading
          this.isLoading = false;
          this.spinner.hide();

          // Return true
          this.activeModal.close(true);
        },
        error => {
          // Hide loading
          this.isLoading = false;
          this.spinner.hide();

          // Send error toast message
          this.toastr.error(error);

          // Return false
          this.activeModal.close(false);
        });
  }

  public dismiss() {
    // Return false
    this.activeModal.dismiss();
  }
}
