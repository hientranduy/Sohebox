import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AuthenticationService, UserService } from '@app/user/_service';
import { AlertService } from '@app/_common/alert';
import { SpinnerService } from '@app/_common/_services';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { User } from '../_models';

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
    private userService: UserService,
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
        username: [this.currentUser.username],
        firstName: [this.firstName],
        lastName: [this.lastName],
      });

      // Update user
      this.spinner.show();
      this.userService.updateUser(this.updateUserForm.value).subscribe(
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
