import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService, UserService } from '@app/user/_service';
import { AlertService } from '@app/_common/alert';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { SpinnerService } from '@app/_common/_services';

@Component({
  templateUrl: 'delete-confirmation-dialog.component.html',
})
export class DeleteConfirmationDialogComponent implements OnInit {
  @Input() title: string;
  @Input() message: string;
  @Input() btnOkText: string;
  @Input() btnCancelText: string;

  constructor(
    private activeModal: NgbActiveModal,
    private router: Router,
    private authenticationService: AuthenticationService,
    private userService: UserService,
    private alertService: AlertService,
    private spinner: SpinnerService,
  ) {}

  ngOnInit() {}

  public decline() {
    this.activeModal.close(false);
  }

  public accept() {
    // Delete user
    this.spinner.show();
    this.userService.deleteLoggedUser().subscribe(
      (data) => {
        // Logout current user
        this.authenticationService.logout();

        // Send alert message
        this.alertService.success('User delete successful', true);

        // Navigate to login page
        this.router.navigate(['/login']);

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

  public dismiss() {
    this.activeModal.dismiss();
  }
}
