import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AuthenticationService, UserService } from '@app/user/_service';
import { AlertService } from '@app/_common/alert';
import { SpinnerService } from '@app/_common/_services';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { User } from '../_models';

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
    private userService: UserService,
    private alertService: AlertService,
    private spinner: SpinnerService,
    private authenticationService: AuthenticationService
  ) {
    // Get logged user
    this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
  }

  ngOnInit() {
  }

  public accept() {
    // Validate data
    this.validData = true;
    if (!this.newPrivateKey || !this.retypePrivateKey) {
      this.message = null;
      this.messageError = 'Missing input';
      this.validData = false;
    } else if (this.newPrivateKey !== this.retypePrivateKey) {
      this.message = null;
      this.messageError = 'Your new private key is not map with retype private key';
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
        newPrivateKey: [this.newPrivateKey]
      });
      this.userService.changePrivatekey(this.changePrivateKeyForm.value)
        .subscribe(
          data => {
            this.alertService.success('Private key change successful', true);

            this.spinner.hide();

            // Close dialog
            this.activeModal.close(true);
          },
          error => {
            this.spinner.hide();

            this.messageError = error;
          });
    }
  }

  public decline() {
    this.activeModal.close(false);
  }

  public dismiss() {
    this.activeModal.dismiss();
  }
}
