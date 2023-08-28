import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AlertService } from '@app/commons/alert/alert.service';
import { User } from '@app/models/user';
import { AuthenticationService } from '@app/services/authentication.service';
import { BackendService } from '@app/services/backend.service';
import { SpinnerService } from '@app/services/spinner.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  templateUrl: 'update-infor-dialog.component.html',
})
export class UpdateInforDialogComponent implements OnInit {
  @Input() title: string;
  @Input() message: string;
  @Input() messageError: string;
  @Input() btnOkText: string;
  @Input() btnCancelText: string;
  firstName: string;
  lastName: string;
  validData: boolean;
  updateUserForm: FormGroup;
  currentUser: User;

  constructor(
    private authenticationService: AuthenticationService,
    private formBuilder: FormBuilder,
    private activeModal: NgbActiveModal,
    private backendService: BackendService,
    private alertService: AlertService,
    private spinner: SpinnerService,
  ) {
    this.authenticationService.currentUser.subscribe(
      (x) => (this.currentUser = x),
    );
  }

  ngOnInit() {
    this.firstName = this.currentUser.firstName;
    this.lastName = this.currentUser.lastName;
  }

  public decline() {
    this.activeModal.close(false);
  }

  public accept() {
    this.validData = true;

    if (!this.firstName || !this.lastName) {
      this.message = null;
      this.messageError = 'First name and last name must be not null';
      this.validData = false;
    }

    if (this.validData) {
      // Prepare update user form
      this.updateUserForm = this.formBuilder.group({
        firstName: [this.firstName],
        lastName: [this.lastName],
      });

      // Update user
      this.spinner.show();
      this.backendService.updateUser(this.updateUserForm.value).subscribe(
        (data) => {
          // Send alert message
          this.alertService.success('User update successful', true);

          // Update current storage user
          this.currentUser.firstName = this.firstName;
          this.currentUser.lastName = this.lastName;
          localStorage.setItem('currentUser', JSON.stringify(this.currentUser));

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
