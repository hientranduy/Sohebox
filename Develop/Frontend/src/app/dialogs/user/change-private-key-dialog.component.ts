import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AlertService } from '@app/commons/alert/alert.service';
import { User } from '@app/models/user';
import { AuthenticationService } from '@app/services/authentication.service';
import { BackendService } from '@app/services/backend.service';
import { SpinnerService } from '@app/services/spinner.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
@Component({
  templateUrl: 'change-private-key-dialog.component.html',
})
export class ChangePrivateKeyDialogComponent implements OnInit {
  @Input() title: string;
  @Input() message: string;
  @Input() messageError: string;
  @Input() btnOkText: string;
  @Input() btnCancelText: string;

  currentUser: User;

  oldPrivateKey: string;
  newPrivateKey: string;
  retypePrivateKey: string;
  validData: boolean;
  changePrivateKeyForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private activeModal: NgbActiveModal,
    private backendService: BackendService,
    private alertService: AlertService,
    private spinner: SpinnerService,
    private authenticationService: AuthenticationService,
  ) {
    // Get logged user
    this.authenticationService.currentUser.subscribe(
      (x) => (this.currentUser = x),
    );
  }

  ngOnInit() {}

  public accept() {
    // Validate data
    this.validData = true;
    if (!this.newPrivateKey || !this.retypePrivateKey) {
      this.message = null;
      this.messageError = 'Missing input';
      this.validData = false;
    } else if (this.newPrivateKey !== this.retypePrivateKey) {
      this.message = null;
      this.messageError =
        'Your new private key is not map with retype private key';
      this.validData = false;
    } else if (this.newPrivateKey.length < 6) {
      this.message = null;
      this.messageError = 'Private key must be at least 6 characters';
      this.validData = false;
    }

    if (this.validData) {
      // Show loading
      this.spinner.show();

      // Change private key
      this.changePrivateKeyForm = this.formBuilder.group({
        oldPrivateKey: [this.oldPrivateKey],
        newPrivateKey: [this.newPrivateKey],
      });
      this.backendService
        .changePrivatekey(this.changePrivateKeyForm.value)
        .subscribe(
          (data) => {
            this.alertService.success('Private key change successful', true);

            this.spinner.hide();

            // Close dialog
            this.activeModal.close(true);
          },
          (error) => {
            this.spinner.hide();

            this.messageError = error;
          },
        );
    }
  }

  public decline() {
    this.activeModal.close(false);
  }

  public dismiss() {
    this.activeModal.dismiss();
  }
}
