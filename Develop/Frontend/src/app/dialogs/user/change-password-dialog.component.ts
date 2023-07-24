import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AlertService } from '@app/commons/alert/alert.service';
import { BackendService } from '@app/services/backend.service';
import { SpinnerService } from '@app/services/spinner.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  templateUrl: 'change-password-dialog.component.html',
})
export class ChangePasswordDialogComponent implements OnInit {
  @Input() title: string;
  @Input() message: string;
  @Input() messageError: string;
  @Input() btnOkText: string;
  @Input() btnCancelText: string;
  newPassword: string;
  retypeNewPassword: string;
  validData: boolean;
  changePasswordForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private activeModal: NgbActiveModal,
    private backendService: BackendService,
    private alertService: AlertService,
    private spinner: SpinnerService,
  ) {}

  ngOnInit() {}

  public decline() {
    this.activeModal.close(false);
  }

  public accept() {
    // Validate data
    this.validData = true;
    if (!this.newPassword || !this.retypeNewPassword) {
      this.message = null;
      this.messageError = 'Password must be not null';
      this.validData = false;
    } else if (this.newPassword !== this.retypeNewPassword) {
      this.message = null;
      this.messageError = 'Your retype password is not map with new password';
      this.validData = false;
    } else if (this.newPassword.length < 6) {
      this.message = null;
      this.messageError = 'Password must be at least 6 characters';
      this.validData = false;
    }

    if (this.validData) {
      // Prepare change password form
      this.changePasswordForm = this.formBuilder.group({
        newPassword: [this.newPassword],
      });

      // Change password
      this.spinner.show();
      this.backendService
        .changePasswordByLoggedUser(this.changePasswordForm.value)
        .subscribe(
          (data) => {
            // Send alert message
            this.alertService.success('Password change successful', true);
            this.spinner.hide();
          },
          (error) => {
            this.spinner.hide();
            this.alertService.error(error);
          },
        );

      // Close dialog
      this.activeModal.close(true);
    }
  }

  public dismiss() {
    this.activeModal.dismiss();
  }
}
